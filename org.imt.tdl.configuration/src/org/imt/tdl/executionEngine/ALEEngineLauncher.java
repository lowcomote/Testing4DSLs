package org.imt.tdl.executionEngine;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.Launch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecoretools.ale.core.env.IAleEnvironment;
import org.eclipse.emf.ecoretools.ale.core.parser.ParsedFile;
import org.eclipse.emf.ecoretools.ale.implementation.ExtendedClass;
import org.eclipse.emf.ecoretools.ale.implementation.Method;
import org.eclipse.emf.ecoretools.ale.implementation.ModelUnit;
import org.eclipse.gemoc.ale.interpreted.engine.AleEngine;
import org.eclipse.gemoc.ale.interpreted.engine.Helper;
import org.eclipse.gemoc.dsl.debug.ide.adapter.DSLThreadAdapter;
import org.eclipse.gemoc.execution.sequential.javaengine.ui.launcher.GemocSourceLocator;
import org.eclipse.gemoc.executionframework.engine.commons.DslHelper;
import org.eclipse.gemoc.executionframework.engine.commons.EngineContextException;
import org.imt.gemoc.engine.custom.launcher.CustomALELauncher;
import org.imt.gemoc.engine.custom.launcher.CustomK3Launcher;

public class ALEEngineLauncher extends AbstractEngine{
	private AleEngine aleEngine = null;
	
	@Override
	public String debugModel() {
		IDebugTarget[] debugTargets = DebugPlugin.getDefault().getLaunchManager().getDebugTargets();
		IThread[] testCaseDebuggerThreads = null;
		try {
			testCaseDebuggerThreads = debugTargets[0].getThreads();
		} catch (DebugException e1) {
			e1.printStackTrace();
		}
		//get the thread running the test case debugger to suspend it during model debugging
		DSLThreadAdapter testCaseDebugThread = (DSLThreadAdapter) testCaseDebuggerThreads[0];
		
		this.executioncontext.setResourceModel(this.getModelResource());
		CustomALELauncher launcher = new CustomALELauncher();
		launcher.executioncontext = this.executioncontext;
		Launch debugLaunch = new Launch(this.launchConfiguration, ILaunchManager.DEBUG_MODE, new GemocSourceLocator());
		DebugPlugin.getDefault().getLaunchManager().addLaunch(debugLaunch);	
		try {
			//launch the debugger for the model under test
			launcher.launch(launchConfiguration, ILaunchManager.DEBUG_MODE, debugLaunch, new NullProgressMonitor());
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		//suspend the test case debugger while the model debugger is running
		while (!debugLaunch.isTerminated()) {	
			synchronized (testCaseDebugThread) {
				if (!testCaseDebugThread.isSuspended()) {
					try {
						testCaseDebugThread.suspend();
					} catch (DebugException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
			}
		}
		return "PASS: The model debugging started";
	}
	
	@Override
	public String executeModelSynchronous() {
		try{
			this.aleEngine = createExecutionEngine();
		}catch (EngineContextException e) {
			e.printStackTrace();
			return "FAIL: Cannot execute the model under test";
		} catch (CoreException e) {
				e.printStackTrace();
		}
		this.aleEngine.startSynchronous();
		this.setModelResource(this.aleEngine.getExecutionContext().getResourceModel());
		this.aleEngine.dispose();
		return "PASS: The model under test executed successfully";
	}
	
	@Override
	public String executeModelAsynchronous() {
		try{
			this.aleEngine = createExecutionEngine();
		}catch (EngineContextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "FAIL: Cannot execute the model under test";
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.aleEngine.start();
		return "The engine is running";
	}
	
	@Override
	public String stopAsynchronousExecution() {
		this.aleEngine.stop();
		this.setModelResource(this.aleEngine.getExecutionContext().getResourceModel());
		this.aleEngine.dispose();
		return "PASS: The model under test executed successfully";
	}
	
	private AleEngine createExecutionEngine() throws EngineContextException, CoreException{
		//if the resource is updated (e.g., the value of its dynamic features are set by the test case)
		//then the execution context should be updated
		this.executioncontext.setResourceModel(this.getModelResource());
		CustomALELauncher launcher = new CustomALELauncher();
		launcher.executioncontext = this.executioncontext;
		
		return (AleEngine) launcher.createExecutionEngine(this.runConfiguration, this.executionMode);
	}
	@Override
	protected String getModelEntryPointMethodName(){
		EClass target = null;
		final EClass finalTarget = target;
		org.eclipse.gemoc.dsl.Dsl language = DslHelper.load(this._language);
		IAleEnvironment environment = Helper.gemocDslToAleDsl(language);
		
		List<ParsedFile<ModelUnit>> parsedSemantics = environment.getBehaviors().getParsedFiles();
		List<Method> mainOperations =
    		parsedSemantics
	    	.stream()
	    	.filter(sem -> sem.getRoot() != null)
	    	.map(sem -> sem.getRoot())
	    	.flatMap(unit -> unit.getClassExtensions().stream())
	    	.filter(xtdCls -> finalTarget == null || finalTarget == xtdCls.getBaseClass())
	    	.flatMap(xtdCls -> xtdCls.getMethods().stream())
    		.filter(op -> op.getTags().contains("main"))
    		.collect(Collectors.toList());
		
		Iterator it = mainOperations.iterator();
		return provideMethodLabel(it.next());
	}
	@Override
	protected String getModelInitializationMethodName() {
		if (this._language != null && this._entryPointMethod != null) {

			List<String> segments = Arrays.asList(this._entryPointMethod.split("::"));
			if (segments.size() >= 2) {
				String tagetClassName = segments.get(segments.size() - 2);
				org.eclipse.gemoc.dsl.Dsl language = DslHelper.load(this._language);

				try(IAleEnvironment environment = Helper.gemocDslToAleDsl(language)) {
					Optional<Method> initOperation = Optional.empty();
					try {
						List<ParsedFile<ModelUnit>> parsedSemantics =environment.getBehaviors().getParsedFiles();
						initOperation = parsedSemantics.stream().filter(sem -> sem.getRoot() != null)
								.map(sem -> sem.getRoot()).flatMap(unit -> unit.getClassExtensions().stream())
								.filter(xtdCls -> xtdCls.getBaseClass().getName().equals(tagetClassName))
								.flatMap(xtdCls -> xtdCls.getMethods().stream()).filter(op -> op.getTags().contains("init"))
								.findFirst();
					} catch (Exception e) {
					}
	
					if (initOperation.isPresent()) {
						return provideMethodLabel(initOperation.get());
					}
				}
			}
		}
		return "";
	}
	public String provideMethodLabel(Object element) {
		
		if(element instanceof Method) {
			Method mtd = (Method) element;
			ExtendedClass base = (ExtendedClass) mtd.eContainer();
			
			if(base.getBaseClass() != mtd.getOperationRef().getEContainingClass()) {
				return provideClassLabel(base.getBaseClass()) + "::" + mtd.getOperationRef().getName();
			}
			else {
				return provideClassLabel(mtd.getOperationRef());
			}
		}
		
		return provideClassLabel(element);
	}
	public String provideClassLabel(Object element) {
		if(element instanceof ENamedElement){
			StringBuilder sb = new StringBuilder();
			if(((ENamedElement)element).eContainer() != null){
				sb.append(provideClassLabel(((ENamedElement)element).eContainer()));
				sb.append("::");
			}
			sb.append(((ENamedElement)element).getName());
			return sb.toString();
		}
		else return element == null ? "" : element.toString();
	}
}
