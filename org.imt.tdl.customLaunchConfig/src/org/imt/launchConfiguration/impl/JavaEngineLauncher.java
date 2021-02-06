package org.imt.launchConfiguration.impl;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.execution.sequential.javaengine.PlainK3ExecutionEngine;
import org.eclipse.gemoc.ale.interpreted.engine.AleEngine;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.gemoc.dsl.debug.ide.launch.AbstractDSLLaunchConfigurationDelegate;
import org.eclipse.gemoc.dsl.debug.ide.sirius.ui.launch.AbstractDSLLaunchConfigurationDelegateSiriusUI;
import org.eclipse.gemoc.executionframework.engine.commons.EngineContextException;
import org.eclipse.gemoc.executionframework.engine.commons.GenericModelExecutionContext;
import org.eclipse.gemoc.executionframework.engine.commons.sequential.ISequentialRunConfiguration;
import org.eclipse.gemoc.executionframework.engine.commons.sequential.SequentialRunConfiguration;
import org.eclipse.gemoc.executionframework.engine.ui.Activator;
import org.eclipse.gemoc.xdsmlframework.api.core.ExecutionMode;

public class JavaEngineLauncher extends AbstractLauncher{
	
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
		//TODO: The attributes have to be set in an automatic manner (for now, I simply set them)
		this._modelLocation = this.getModelResource().getURI().toString();
		this._siriusRepresentationLocation = this.getModelResource().getURI().toString().split("/")[1] + "/representations.aird";
		this._delay = "0";
		this._language = this.getDslName(DSLPath);
		this._entryPointModelElement = "/";
		this._entryPointMethod = "public static void org.eclipse.gemoc.example.k3fsm.k3dsa.FSMAspect.main(org.eclipse.gemoc.example.k3fsm.FSM)";
		this._animationFirstBreak = true;
		this._modelInitializationMethod = "org.eclipse.gemoc.example.k3fsm.k3dsa.FSMAspect.initializeModel";
		//this._modelInitializationArguments = "";
		this._modelInitializationArguments = "000101010";
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
	public void executeModel() {
		PlainK3ExecutionEngine javaEngine = createExecutionEngine();
		javaEngine.start();
		this.setModelResource(javaEngine.getExecutionContext().getResourceModel());
	}
	private PlainK3ExecutionEngine createExecutionEngine(){
		// create and initialize engine
		PlainK3ExecutionEngine executionEngine = new PlainK3ExecutionEngine();
		GenericModelExecutionContext<ISequentialRunConfiguration> executioncontext = null;
		try {
			executioncontext = new GenericModelExecutionContext<ISequentialRunConfiguration>(
					this.runConfiguration, this.executionMode);
		} catch (EngineContextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//executioncontext.getExecutionPlatform().getModelLoader().setProgressMonitor(this.launchProgressMonitor);
		executioncontext.getExecutionPlatform().getModelLoader().setProgressMonitor(new NullProgressMonitor());
		executioncontext.initializeResourceModel();
		executionEngine.initialize(executioncontext);
		return executionEngine;
	}
	private String getDslName(String dslFilePath) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		return dsl.getEntry("name").getValue().toString();
	}
}
