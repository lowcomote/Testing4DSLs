package org.imt.tdl.configuration.impl;

import org.eclipse.core.runtime.CoreException;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.ale.interpreted.engine.AleEngine;
import org.eclipse.gemoc.ale.interpreted.engine.sirius.ALESiriusInterpreter;
import org.eclipse.gemoc.ale.interpreted.engine.sirius.ALESiriusInterpreterProviderAddon;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.gemoc.dsl.debug.ide.launch.AbstractDSLLaunchConfigurationDelegate;
import org.eclipse.gemoc.dsl.debug.ide.sirius.ui.launch.AbstractDSLLaunchConfigurationDelegateSiriusUI;
import org.eclipse.gemoc.executionframework.engine.commons.EngineContextException;
import org.eclipse.gemoc.executionframework.engine.commons.GenericModelExecutionContext;
import org.eclipse.gemoc.executionframework.engine.commons.sequential.SequentialRunConfiguration;
import org.eclipse.gemoc.executionframework.engine.ui.Activator;
import org.eclipse.gemoc.xdsmlframework.api.core.ExecutionMode;
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;
import org.imt.tdl.configuration.IExecutionEngine;

public class ALEEngineLauncher extends AbstractEngine{

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
		//this._entryPointMethod = "bpmn::Microflow::main";
		this._entryPointMethod = "fsm::StateMachine::main";
		this._animationFirstBreak = true;
		//this._modelInitializationMethod = "bpmn::Microflow::initializeModel";
		this._modelInitializationMethod = "fsm::StateMachine::initializeModel";
		//this._modelInitializationArguments = "";
		this._modelInitializationArguments = "000101010";
		this.executionMode = ExecutionMode.Run;
		this.configureEngine();
	}
	//definition of a new configuration of ALE Engine for running a specific model
	private void configureEngine(){
		// Create a new Launch Configuration
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = manager.getLaunchConfigurationType("org.eclipse.gemoc.ale.interpreted.engine.ui.launcher");
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
		configuration.setAttribute(SequentialRunConfiguration.DEBUG_MODEL_ID, Activator.DEBUG_MODEL_ID);	
		try {
			this.runConfiguration = new SequentialRunConfiguration(configuration);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void executeModel() {
		AleEngine aleEngine = createExecutionEngine();
		//add a custom addon to set the model resource as the model to be executed
		aleEngine.getExecutionContext().getExecutionPlatform().addEngineAddon(
				new SetMUTResoureAddon(this.getModelResource()));
		aleEngine.startSynchronous();
		this.setModelResource(aleEngine.getExecutionContext().getResourceModel());
	}
	private AleEngine createExecutionEngine(){
		AleEngine engine = new AleEngine();
		GenericModelExecutionContext<SequentialRunConfiguration> executioncontext = null;
		try {
			executioncontext = new GenericModelExecutionContext<SequentialRunConfiguration>(this.runConfiguration, this.executionMode);
		} catch (EngineContextException e) {
			e.printStackTrace();
		}
		executioncontext.initializeResourceModel(); // load model
		engine.initialize(executioncontext);
		
		// declare this engine as available for ale: queries in the odesign
		ALESiriusInterpreter.getDefault().addAleEngine(engine);
		// create and add addon to unregister when the engine will be disposed
		IEngineAddon aleRTDInterpreter = new ALESiriusInterpreterProviderAddon();
		Activator.getDefault().getMessaggingSystem().debug("Enabled implicit addon: "+ aleRTDInterpreter.getAddonID(), Activator.PLUGIN_ID);
		engine.getExecutionContext().getExecutionPlatform().addEngineAddon(aleRTDInterpreter);
		return engine;
	}
	private String getDslName(String dslFilePath) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		return dsl.getEntry("name").getValue().toString();
	}
}
