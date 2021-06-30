package org.imt.tdl.executionEngine;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.Launch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecoretools.ale.core.env.IAleEnvironment;
import org.eclipse.emf.ecoretools.ale.core.parser.ParsedFile;
import org.eclipse.emf.ecoretools.ale.implementation.ExtendedClass;
import org.eclipse.emf.ecoretools.ale.implementation.Method;
import org.eclipse.emf.ecoretools.ale.implementation.ModelUnit;
import org.eclipse.gemoc.ale.interpreted.engine.AleEngine;
import org.eclipse.gemoc.ale.interpreted.engine.Helper;
import org.eclipse.gemoc.ale.interpreted.engine.sirius.ALESiriusInterpreter;
import org.eclipse.gemoc.ale.interpreted.engine.sirius.ALESiriusInterpreterProviderAddon;
import org.eclipse.gemoc.execution.sequential.javaengine.PlainK3ExecutionEngine;
import org.eclipse.gemoc.execution.sequential.javaengine.ui.launcher.GemocSourceLocator;
import org.eclipse.gemoc.executionframework.engine.commons.DslHelper;
import org.eclipse.gemoc.executionframework.engine.commons.EngineContextException;
import org.eclipse.gemoc.executionframework.engine.ui.Activator;
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;
import org.imt.sequential.engine.custom.launcher.CustomALELauncher;
import org.imt.sequential.engine.custom.launcher.CustomK3Launcher;

public class ALEEngineLauncher extends AbstractEngine{
	private AleEngine aleEngine = null;
	
	@Override
	public String executeModelSynchronous() {
		IDebugTarget[] debugTargets = DebugPlugin.getDefault().getLaunchManager().getDebugTargets();
		if (debugTargets.length > 0) {
			//we are in the Debug mode, so debug the model under test
			this.executioncontext.setResourceModel(this.getModelResource());
			CustomALELauncher launcher = new CustomALELauncher();
			launcher.executioncontext = this.executioncontext;
			Launch debugLaunch = new Launch(this.launchConfiguration, ILaunchManager.DEBUG_MODE, new GemocSourceLocator());
			DebugPlugin.getDefault().getLaunchManager().addLaunch(debugLaunch);
			try{
				launcher.launch(this.launchConfiguration, ILaunchManager.DEBUG_MODE, debugLaunch, new NullProgressMonitor());
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "PASS: The model debugging started";
		}else {
			//we are in the Run mode, so run the model under test
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
			this.aleEngine.startSynchronous();
			this.setModelResource(this.aleEngine.getExecutionContext().getResourceModel());
			this.aleEngine.dispose();
			return "PASS: The model under test executed successfully";
		}
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
