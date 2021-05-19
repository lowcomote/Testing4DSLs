package org.imt.tdl.executionEngine;

import java.lang.reflect.Method;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.gemoc.dsl.debug.ide.launch.AbstractDSLLaunchConfigurationDelegate;
import org.eclipse.gemoc.dsl.debug.ide.sirius.ui.launch.AbstractDSLLaunchConfigurationDelegateSiriusUI;
import org.eclipse.gemoc.execution.sequential.javaengine.PlainK3ExecutionEngine;
import org.eclipse.gemoc.executionframework.engine.commons.DslHelper;
import org.eclipse.gemoc.executionframework.engine.commons.EngineContextException;
import org.eclipse.gemoc.executionframework.engine.commons.GenericModelExecutionContext;
import org.eclipse.gemoc.executionframework.engine.commons.K3DslHelper;
import org.eclipse.gemoc.executionframework.engine.commons.sequential.ISequentialRunConfiguration;
import org.eclipse.gemoc.executionframework.engine.commons.sequential.SequentialRunConfiguration;
import org.eclipse.gemoc.executionframework.engine.ui.Activator;
import org.eclipse.gemoc.xdsmlframework.api.core.ExecutionMode;
import org.osgi.framework.Bundle;

public class JavaEngineLauncher extends AbstractEngine{
	private PlainK3ExecutionEngine javaEngine = null;
	@Override
	public String executeModelSynchronous() {
		try{
			this.javaEngine = createExecutionEngine();
		}catch (EngineContextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "FAIL: Cannot execute the model under test";
		}
		this.javaEngine.startSynchronous();
		this.setModelResource(this.javaEngine.getExecutionContext().getResourceModel());
		this.javaEngine.dispose();
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
	public PlainK3ExecutionEngine createExecutionEngine() throws EngineContextException{
		// create and initialize engine
		PlainK3ExecutionEngine engine = new PlainK3ExecutionEngine();
		//if the resource is updated (e.g., the value of its dynamic features are set by the test case)
		//then the execution context should be updated
		this.executioncontext.setResourceModel(this.getModelResource());
		engine.initialize(this.executioncontext);
		return engine;
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
