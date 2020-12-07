package org.imt.ale.customLaunchConfig;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.gemoc.executionframework.engine.commons.EngineContextException;
import org.eclipse.ocl.ParserException;
import org.eclipse.gemoc.ale.interpreted.engine.AleEngine;

public class CustomLauncher{
	
	private Resource MUTResource;
	private ALEEngineLauncher aleEngineLauncher;
	private AleEngine aleEngine;
	private OCLLauncher oclLauncher;
	private EventManagerLauncher eventManagerLauncher;
	
	public final static String GENERIC = "Generic";
	public final static String DSL_SPECIFIC = "DSL-Specific";
	public final static String OCL = "OCL";

	public void setUp(String configurationType, String MUTPath) throws CoreException, EngineContextException {
		System.out.println("Start configuring the "+ configurationType + " engine");
		if (configurationType.equals(GENERIC)) {
			this.aleEngineLauncher = new ALEEngineLauncher();
			this.aleEngineLauncher.setUp(MUTPath);
		}else if(configurationType.equals(DSL_SPECIFIC)) {
			this.eventManagerLauncher = new EventManagerLauncher();
			this.eventManagerLauncher.setUp();
		}else if (configurationType.equals(OCL)) {
			this.oclLauncher = new OCLLauncher();
			this.oclLauncher.setUp();
		}
	}
	public void executeGenericCommand() throws CoreException, EngineContextException {
		System.out.println("Start executing generic command");
		this.aleEngine = this.aleEngineLauncher.createExecutionEngine();
		this.MUTResource = this.aleEngine.getExecutionContext().getResourceModel();
	}
	public Object executeOCLCommand (String query) throws ParserException{
		System.out.println("Start executing ocl command");
		return this.oclLauncher.runQuery(this.MUTResource, query);
	}
	public void executeDSLSpecificCommand(String command) {
		System.out.println("Start executing dsl-specific command");
		//TODO: Calling the event manager
	}
}