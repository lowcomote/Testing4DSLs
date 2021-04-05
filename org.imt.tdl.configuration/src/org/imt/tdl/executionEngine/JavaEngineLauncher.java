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
	
	private SequentialRunConfiguration runConfiguration;
	private ExecutionMode executionMode;
	
	private String _modelLocation;
	private String _siriusRepresentationLocation;
	private String _delay;
	private String _language;
	private String _entryPointModelElement;
	private String _entryPointMethod;
	private Boolean _animationFirstBreak;
	private String _modelInitializationMethod;
	private String _modelInitializationArguments;
	@Override
	public void setUp(String MUTPath, String DSLPath){
		if (this.getModelResource()==null) {
			super.setUp(MUTPath, DSLPath);
		}
		this._modelLocation = this.getModelResource().getURI().toString();
		this._siriusRepresentationLocation = this.getModelResource().getURI().toString().split("/")[1] + "/representations.aird";
		this._delay = "0";
		this._language = this.getDslName(DSLPath);
		this._entryPointModelElement = "/";
		this._entryPointMethod = getModelEntryPointMethodName();
		this._animationFirstBreak = true;
		this._modelInitializationMethod = getModelInitializationMethodName();
		this._modelInitializationArguments = "";
		this.executionMode = ExecutionMode.Run;
		this.configureEngine();
	}
	//definition of a new configuration of Gemoc java Engine for running a specific model
	private void configureEngine(){
		// Create a new Launch Configuration
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = manager.getLaunchConfigurationType("org.eclipse.gemoc.execution.sequential.javaengine.ui.launcher");
		ILaunchConfigurationWorkingCopy configuration = null;
		try {
			configuration = type.newInstance(null, "Run MUT");
		} catch (CoreException e) {
			e.printStackTrace();
		}
		// Set its attributes
		configuration.setAttribute(AbstractDSLLaunchConfigurationDelegate.RESOURCE_URI, this._modelLocation);
		configuration.setAttribute(AbstractDSLLaunchConfigurationDelegateSiriusUI.SIRIUS_RESOURCE_URI, this._siriusRepresentationLocation);
		configuration.setAttribute(SequentialRunConfiguration.LAUNCH_DELAY, Integer.parseInt(this._delay));
		configuration.setAttribute(SequentialRunConfiguration.LAUNCH_SELECTED_LANGUAGE, this._language);
		configuration.setAttribute(SequentialRunConfiguration.LAUNCH_MODEL_ENTRY_POINT, this._entryPointModelElement);
		configuration.setAttribute(SequentialRunConfiguration.LAUNCH_METHOD_ENTRY_POINT, this._entryPointMethod);
		configuration.setAttribute(SequentialRunConfiguration.LAUNCH_INITIALIZATION_METHOD, this._modelInitializationMethod);
		configuration.setAttribute(SequentialRunConfiguration.LAUNCH_INITIALIZATION_ARGUMENTS, this._modelInitializationArguments);
		configuration.setAttribute(SequentialRunConfiguration.LAUNCH_BREAK_START, this._animationFirstBreak);
		// DebugModelID for sequential engine
		configuration.setAttribute(SequentialRunConfiguration.DEBUG_MODEL_ID, Activator.DEBUG_MODEL_ID);
		try {
			this.runConfiguration = new SequentialRunConfiguration(configuration);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	@Override
	public String executeModel() {
		PlainK3ExecutionEngine javaEngine = null;
		try{
			javaEngine = createExecutionEngine();
		}catch (EngineContextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "FAIL: Cannot execute the model under test";
		}
		javaEngine.startSynchronous();
		this.setModelResource(javaEngine.getExecutionContext().getResourceModel());
		return "PASS: The model under test executed successfully";
	}
	public PlainK3ExecutionEngine createExecutionEngine() throws EngineContextException{
		// create and initialize engine
		PlainK3ExecutionEngine engine = new PlainK3ExecutionEngine();
		CustomModelExecutionContext executioncontext = null;
		executioncontext = new CustomModelExecutionContext(this.runConfiguration, this.executionMode);
		
		executioncontext.getExecutionPlatform().getModelLoader().setProgressMonitor(new NullProgressMonitor());
		if (!executioncontext.modelInitialized()) {
			executioncontext.initializeResourceModel();
		}
		executioncontext.setResourceModel(this.getModelResource());
		engine.initialize(executioncontext);
		return engine;
	}
	public String getDslName(String dslFilePath) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		return dsl.getEntry("name").getValue().toString();
	}
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
