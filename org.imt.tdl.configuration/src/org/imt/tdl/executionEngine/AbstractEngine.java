package org.imt.tdl.executionEngine;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.gemoc.dsl.debug.ide.launch.AbstractDSLLaunchConfigurationDelegate;
import org.eclipse.gemoc.dsl.debug.ide.sirius.ui.launch.AbstractDSLLaunchConfigurationDelegateSiriusUI;
import org.eclipse.gemoc.executionframework.engine.commons.EngineContextException;
import org.eclipse.gemoc.executionframework.engine.commons.sequential.SequentialRunConfiguration;
import org.eclipse.gemoc.xdsmlframework.api.core.ExecutionMode;

public abstract class AbstractEngine implements ISequentialExecutionEngine{
	
	protected ExecutionMode executionMode;
	protected String _modelLocation;
	protected String _siriusRepresentationLocation;
	protected String _delay;
	protected String _language;
	protected String _entryPointModelElement;
	protected String _entryPointMethod;
	protected Boolean _animationFirstBreak;
	protected String _modelInitializationMethod;
	protected String _modelInitializationArguments;
	
	private Resource MUTResource = null;
	
	protected ILaunchConfiguration launchConfiguration;
	protected SequentialRunConfiguration runConfiguration;
	protected CustomModelExecutionContext executioncontext;
	
	@Override
	public void setUp(String MUTPath, String DSLPath) {
		this._modelLocation = MUTPath;
		this._siriusRepresentationLocation = MUTPath.split("/")[1] + "/representations.aird";
		this._delay = "0";
		this._language = this.getDslName(DSLPath);
		this._entryPointModelElement = "/";
		this._entryPointMethod = getModelEntryPointMethodName();
		this._animationFirstBreak = true;
		this._modelInitializationMethod = getModelInitializationMethodName();
		this._modelInitializationArguments = "";
		this.executionMode = ExecutionMode.Animation;
		this.createLaunchConfiguration();
		this.configureEngine();
	}
	
	protected abstract String getModelInitializationMethodName();
	protected abstract String getModelEntryPointMethodName();
	
	private void createLaunchConfiguration() {
		// Create a new Launch Configuration
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = manager.getLaunchConfigurationType("org.eclipse.gemoc.execution.sequential.javaengine.ui.launcher");
		ILaunchConfigurationWorkingCopy configuration = null;
		try {
			configuration = type.newInstance(null, "Debug MUT");
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
		
		//configuration.setAttribute(SequentialRunConfiguration.DEBUG_MODEL_ID, Activator.DEBUG_MODEL_ID);
		configuration.setAttribute(SequentialRunConfiguration.DEBUG_MODEL_ID, "org.eclipse.gemoc.execution.sequential.javaengine.ui.debugModel");
		try {
			this.launchConfiguration = configuration.doSave();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void configureEngine() {
		try {
			this.runConfiguration = new SequentialRunConfiguration(this.launchConfiguration);
			this.executioncontext = new CustomModelExecutionContext(this.runConfiguration, this.executionMode);
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (EngineContextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.executioncontext.getExecutionPlatform().getModelLoader().setProgressMonitor(new NullProgressMonitor());
		if (!this.executioncontext.modelInitialized()) {
			this.executioncontext.initializeResourceModel();
		}
		this.MUTResource = this.executioncontext.getResourceModel();
	}
	
	@Override
	public void setModelResource(Resource resource) {
		this.MUTResource = resource;
	}
	@Override
	public Resource getModelResource() {
		return this.MUTResource;
	}
	private String getDslName(String dslFilePath) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		return dsl.getEntry("name").getValue().toString();
	}
}
