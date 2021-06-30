package org.imt.tdl.executionEngine;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.Launch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.gemoc.execution.sequential.javaengine.PlainK3ExecutionEngine;
import org.eclipse.gemoc.execution.sequential.javaengine.ui.launcher.GemocSourceLocator;
import org.eclipse.gemoc.executionframework.engine.commons.DslHelper;
import org.eclipse.gemoc.executionframework.engine.commons.EngineContextException;
import org.eclipse.gemoc.executionframework.engine.commons.K3DslHelper;
import org.imt.sequential.engine.custom.launcher.CustomK3Launcher;
import org.osgi.framework.Bundle;

public class JavaEngineLauncher extends AbstractEngine{
	private PlainK3ExecutionEngine javaEngine = null;
	
	@Override
	public String executeModelSynchronous() {
		IDebugTarget[] debugTargets = DebugPlugin.getDefault().getLaunchManager().getDebugTargets();
		if (debugTargets.length > 0) {
			//we are in the Debug mode, so debug the model under test
			this.executioncontext.setResourceModel(this.getModelResource());
			CustomK3Launcher launcher = new CustomK3Launcher();
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
				this.javaEngine = createExecutionEngine();
			}catch (EngineContextException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "FAIL: Cannot execute the model under test";
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.javaEngine.startSynchronous();
			this.setModelResource(this.javaEngine.getExecutionContext().getResourceModel());
			this.javaEngine.dispose();
			return "PASS: The model under test executed successfully";
		}
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
		this.javaEngine.dispose();
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
}
