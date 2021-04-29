package org.imt.tdl.executionEngine;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecoretools.ale.core.env.IAleEnvironment;
import org.eclipse.emf.ecoretools.ale.core.parser.ParsedFile;
import org.eclipse.emf.ecoretools.ale.implementation.ExtendedClass;
import org.eclipse.emf.ecoretools.ale.implementation.Method;
import org.eclipse.emf.ecoretools.ale.implementation.ModelUnit;
import org.eclipse.gemoc.ale.interpreted.engine.AleEngine;
import org.eclipse.gemoc.ale.interpreted.engine.Helper;
import org.eclipse.gemoc.ale.interpreted.engine.sirius.ALESiriusInterpreter;
import org.eclipse.gemoc.ale.interpreted.engine.sirius.ALESiriusInterpreterProviderAddon;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.gemoc.dsl.debug.ide.launch.AbstractDSLLaunchConfigurationDelegate;
import org.eclipse.gemoc.dsl.debug.ide.sirius.ui.launch.AbstractDSLLaunchConfigurationDelegateSiriusUI;
import org.eclipse.gemoc.executionframework.engine.commons.DslHelper;
import org.eclipse.gemoc.executionframework.engine.commons.EngineContextException;
import org.eclipse.gemoc.executionframework.engine.commons.sequential.SequentialRunConfiguration;
import org.eclipse.gemoc.executionframework.engine.ui.Activator;
import org.eclipse.gemoc.xdsmlframework.api.core.ExecutionMode;
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;

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
	public String executeModel() {
		AleEngine aleEngine = null;
		try{
			aleEngine = createExecutionEngine();
		}catch (EngineContextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "FAIL: Cannot execute the model under test";
		}
		aleEngine.startSynchronous();
		this.setModelResource(aleEngine.getExecutionContext().getResourceModel());
		aleEngine.dispose();
		return "PASS: The model under test executed successfully";
	}
	private AleEngine createExecutionEngine() throws EngineContextException{
		AleEngine engine = new AleEngine();
		CustomModelExecutionContext executioncontext = null;
		executioncontext = new CustomModelExecutionContext(this.runConfiguration, this.executionMode);
		if (!executioncontext.modelInitialized()) {
			executioncontext.initializeResourceModel();
		}
		executioncontext.setResourceModel(this.getModelResource());
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
