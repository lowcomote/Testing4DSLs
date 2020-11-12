package org.imt.ale.customLaunchConfig;

import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.gemoc.executionframework.engine.commons.EngineContextException;
import org.eclipse.gemoc.ale.interpreted.engine.AleEngine;
import org.eclipse.gemoc.executionframework.engine.commons.sequential.SequentialRunConfiguration;
import org.eclipse.gemoc.executionframework.engine.ui.Activator;
import org.eclipse.gemoc.xdsmlframework.api.core.ExecutionMode;
import org.eclipse.gemoc.executionframework.engine.commons.GenericModelExecutionContext;
import org.eclipse.gemoc.ale.interpreted.engine.sirius.ALESiriusInterpreter;
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;
import org.eclipse.gemoc.ale.interpreted.engine.sirius.ALESiriusInterpreterProviderAddon;
import org.eclipse.gemoc.dsl.debug.ide.launch.AbstractDSLLaunchConfigurationDelegate;
import org.eclipse.gemoc.dsl.debug.ide.sirius.ui.launch.AbstractDSLLaunchConfigurationDelegateSiriusUI;

public class CustomLauncher{
	public SequentialRunConfiguration runConfiguration;
	public ExecutionMode executionMode;
	private String _modelLocationText;
	private String _siriusRepresentationLocationText;
	private String _delayText;
	private String _languageCombo;
	private String _entryPointModelElementText;
	private String _entryPointMethodText;
	private String _animationFirstBreak;
	private String _modelInitializationMethodText;
	private String _modelInitializationArgumentsText;
	public static String GENERIC_COMMAND = "Generic";
	public static String DSL_SPECIFIC_COMMAND = "DSL-Specific";
	public static String OCL_COMMAND = "OCL";
	
	//definition of a new configuration of ALE Engine for running a specific model
	public void setALEConfiguration(String MUTAddress, String DSLAddress) throws CoreException, EngineContextException {
		// Create a new Launch Configuration
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = manager.getLaunchConfigurationType("org.eclipse.gemoc.ale.interpreted.engine.ui.launcher");
		ILaunchConfigurationWorkingCopy configuration = type.newInstance(null, "Run MUT");

		// Set its attributes
		configuration.setAttribute(AbstractDSLLaunchConfigurationDelegate.RESOURCE_URI,
				this._modelLocationText);
		configuration.setAttribute(AbstractDSLLaunchConfigurationDelegateSiriusUI.SIRIUS_RESOURCE_URI,
				this._siriusRepresentationLocationText);
		configuration.setAttribute(SequentialRunConfiguration.LAUNCH_DELAY, Integer.parseInt(_delayText));
		configuration.setAttribute(SequentialRunConfiguration.LAUNCH_SELECTED_LANGUAGE, _languageCombo);
		configuration.setAttribute(SequentialRunConfiguration.LAUNCH_MODEL_ENTRY_POINT, _entryPointModelElementText);
		configuration.setAttribute(SequentialRunConfiguration.LAUNCH_METHOD_ENTRY_POINT, _entryPointMethodText);
		configuration.setAttribute(SequentialRunConfiguration.LAUNCH_INITIALIZATION_METHOD,
				_modelInitializationMethodText);
		configuration.setAttribute(SequentialRunConfiguration.LAUNCH_INITIALIZATION_ARGUMENTS,
				_modelInitializationArgumentsText);
		configuration.setAttribute(SequentialRunConfiguration.LAUNCH_BREAK_START, _animationFirstBreak);
		// DebugModelID for sequential engine
		configuration.setAttribute(SequentialRunConfiguration.DEBUG_MODEL_ID, Activator.DEBUG_MODEL_ID);

		// Launch the configuration
		//configuration.launch(ILaunchManager.RUN_MODE, new NullProgressMonitor());
		
		this.runConfiguration = new SequentialRunConfiguration(configuration);
		//setting the executionMode
	}
	//definition of a new configuration of Event Manager for interacting with a specific model
	public void setEventManagerConfiguration(String DSLAddress, String MUTAddress) {}
	//definition of a new configuration of OCL Engine for querying on a specific model
	public void setOCLInterpreterConfiguration(String DSLAddress, String MUTAddress) {}
	public void executeCommand(String commandType) {
		if (commandType.equals(GENERIC_COMMAND)) {
			try {
				createExecutionEngine(this.runConfiguration, this.executionMode);
			}catch(CoreException ce){
				System.out.println(ce.getMessage());
			}catch(EngineContextException ece){
				System.out.println(ece.getMessage());
			}
		}else if(commandType.equals(DSL_SPECIFIC_COMMAND)) {
			//TODO: Calling the event manager
		}else if (commandType.equals(OCL_COMMAND)) {
			//TODO: Calling the OCL Engine
		}
	}
	protected AleEngine createExecutionEngine(SequentialRunConfiguration runConfiguration, ExecutionMode executionMode)
			throws CoreException, EngineContextException {
		
		AleEngine engine = new AleEngine();
		
		GenericModelExecutionContext<SequentialRunConfiguration> executioncontext = new GenericModelExecutionContext<SequentialRunConfiguration>(runConfiguration, executionMode);
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
	//TODO: Creating Event Manager
	//TODO: Creating OCL Engine
}