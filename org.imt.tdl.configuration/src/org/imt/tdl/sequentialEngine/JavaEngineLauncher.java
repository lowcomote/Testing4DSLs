package org.imt.tdl.sequentialEngine;

import java.lang.reflect.Method;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.gemoc.dsl.debug.impl.ThreadImpl;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.Launch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.gemoc.dsl.debug.ide.adapter.DSLThreadAdapter;
import org.eclipse.gemoc.execution.sequential.javaengine.PlainK3ExecutionEngine;
import org.eclipse.gemoc.execution.sequential.javaengine.ui.launcher.GemocSourceLocator;
import org.eclipse.gemoc.executionframework.engine.commons.DslHelper;
import org.eclipse.gemoc.executionframework.engine.commons.EngineContextException;
import org.eclipse.gemoc.executionframework.engine.commons.K3DslHelper;
import org.eclipse.gemoc.trace.commons.model.trace.State;
import org.eclipse.gemoc.trace.commons.model.trace.Step;
import org.eclipse.gemoc.trace.commons.model.trace.Trace;
import org.eclipse.gemoc.trace.commons.model.trace.TracedObject;
import org.eclipse.gemoc.trace.gemoc.api.IMultiDimensionalTraceAddon;
import org.eclipse.gemoc.trace.gemoc.traceaddon.GenericTraceEngineAddon;
import org.imt.gemoc.engine.custom.launcher.CustomK3Launcher;
import org.osgi.framework.Bundle;

public class JavaEngineLauncher extends AbstractEngine{
	private PlainK3ExecutionEngine javaEngine = null;
	
	@Override
	public void launchModelDebugger() {
		IDebugTarget[] debugTargets = DebugPlugin.getDefault().getLaunchManager().getDebugTargets();
		IThread[] testCaseDebuggerThreads = null;
		try {
			testCaseDebuggerThreads = debugTargets[0].getThreads();
		} catch (DebugException e) {
			e.printStackTrace();
		}
		//get the thread running the test case debugger to suspend it during model debugging
		DSLThreadAdapter testCaseDebugThread = (DSLThreadAdapter) testCaseDebuggerThreads[0];
		ThreadImpl testDebugger = (ThreadImpl) testCaseDebugThread.getTarget();
		if (testDebugger.getState().toString() == "STEPPING_INTO") {
			this.breakAtStart();
		}

		this.executioncontext.setResourceModel(this.getModelResource());
		CustomK3Launcher launcher = new CustomK3Launcher();
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
						e.printStackTrace();
					}
				}	
			}
		}
	}
	
	@Override
	public String executeModelSynchronous(){
		try{
			this.javaEngine = createExecutionEngine();
		}catch (EngineContextException e) {
			e.printStackTrace();
			return "FAIL: Cannot execute the model under test";
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		final Runnable modelRunner = new Thread() {
		  @Override 
		  public void run() { 
			  javaEngine.startSynchronous();
		  }
		};

		final ExecutorService executor = Executors.newSingleThreadExecutor();
		final Future future = executor.submit(modelRunner);
		executor.shutdown(); // This does not cancel the already-scheduled task.

		try { 
		  future.get(10, TimeUnit.SECONDS); 
		}
		catch (InterruptedException ie) { 
			ie.printStackTrace();
		}
		catch (ExecutionException ee) { 
			ee.printStackTrace();
		}
		catch (TimeoutException te) { 
			te.printStackTrace();
			future.cancel(true);
			javaEngine.stop();
			return "FAIL: TimeoutException -> There is an infinite loop in the model under test";
		}
		if (!executor.isTerminated()) {
		    executor.shutdownNow(); // If you want to stop the code that hasn't finished
		}
		this.setModelResource(this.javaEngine.getExecutionContext().getResourceModel());
		return "PASS: The model under test executed successfully";
	}
	
	@Override
	public String executeModelAsynchronous() {	
		try{
			this.javaEngine = createExecutionEngine();
		}catch (EngineContextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "FAIL: Cannot execute the model under test";
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		javaEngine.start();
		return "The engine is running";
	}
	
	@Override
	public String stopAsynchronousExecution() {
		this.javaEngine.stop();
		this.setModelResource(this.javaEngine.getExecutionContext().getResourceModel());
		return "PASS: The model under test executed successfully";
	}
	
	public PlainK3ExecutionEngine createExecutionEngine() throws EngineContextException, CoreException{
		//if the resource is updated (e.g., the value of its dynamic features are set by the test case)
		//then the execution context should be updated
		this.executioncontext.setResourceModel(this.getModelResource());
		CustomK3Launcher launcher = new CustomK3Launcher();
		launcher.executioncontext = this.executioncontext;
		
		return (PlainK3ExecutionEngine) launcher.createExecutionEngine(this.runConfiguration, this.executionMode);
	}
	@Override
	protected String getModelEntryPointMethodName(){
		Set<Class<?>> candidateAspects = K3DslHelper.getAspects(this._language);
		Iterator it = candidateAspects.iterator();
		while (it.hasNext()) {
			Class c = (Class) it.next();
			for(Method m : c.getMethods()){
				// TODO find a better search mechanism (check signature, inheritance, aspects, etc)
				if(m.isAnnotationPresent(fr.inria.diverse.k3.al.annotationprocessor.Main.class)){
					return m.toString();
				}
			}
		}
		return "";
	}
	@Override
	protected String getModelInitializationMethodName(){
		String entryPointClassName = null;
		final String prefix = "public static void ";
		int startName = prefix.length();
		int endName = this._entryPointMethod.lastIndexOf("(");
		if(endName == -1) return "";
		String entryMethod = this._entryPointMethod.substring(startName, endName);
		int lastDot = entryMethod.lastIndexOf(".");
		if(lastDot != -1){
			entryPointClassName = entryMethod.substring(0, lastDot);
		}
		
		Bundle bundle = DslHelper.getDslBundle(this._language);
		
		if(entryPointClassName != null && bundle != null){
			try {
				Class<?> entryPointClass = bundle.loadClass(entryPointClassName);
				for(Method m : entryPointClass.getMethods()){
					// TODO find a better search mechanism (check signature, inheritance, aspects, etc)
					if(m.isAnnotationPresent(fr.inria.diverse.k3.al.annotationprocessor.InitializeModel.class)){
						return entryPointClassName+"."+m.getName();
					}
				}
			} catch (ClassNotFoundException e) {}
		}
			
		return "";
	}
	private String getDebugJobName() {
		return "Gemoc debug job";
	}

	@Override
	public Trace<Step<?>, TracedObject<?>, State<?, ?>> getExecutionTrace() {
		return (Trace<Step<?>, TracedObject<?>, State<?, ?>>) this.javaEngine.getAddon(GenericTraceEngineAddon.class).getTrace();
	}
}
