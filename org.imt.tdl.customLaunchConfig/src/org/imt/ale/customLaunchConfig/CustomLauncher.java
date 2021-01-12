package org.imt.ale.customLaunchConfig;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.gemoc.executionframework.engine.commons.EngineContextException;
import org.eclipse.ocl.ParserException;
import org.eclipse.gemoc.ale.interpreted.engine.AleEngine;

public class CustomLauncher{
	
	private String DSLPath;
	private String MUTPath;
	private Resource MUTResource;
	
	private JavaEngineLauncher javaEngineLauncher;
	private ALEEngineLauncher aleEngineLauncher;
	private AleEngine aleEngine;
	private OCLLauncher oclLauncher;
	private EventManagerLauncher eventManagerLauncher;
	
	public final static String GENERIC = "Generic";
	public final static String DSL_SPECIFIC = "DSL-Specific";
	public final static String OCL = "OCL";

	public void setUp(String configurationType) throws CoreException, EngineContextException {
		this.MUTResource = (new ResourceSetImpl()).getResource(URI.createURI(this.MUTPath), true);
		System.out.println("Start configuring the "+ configurationType + " engine");
		if (configurationType.equals(GENERIC)) {
			this.aleEngineLauncher = new ALEEngineLauncher();
			this.aleEngineLauncher.setUp(this.MUTPath);
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
	public Object executeOCLCommand (String query){
		System.out.println("Start executing ocl command");
		try {
			//send the query without quotation marks
			return this.oclLauncher.runQuery(this.MUTResource, query.substring(1, query.length()-1));
		}catch (ParserException e) {
            e.printStackTrace();
		}
		return null;
	}
	public void executeDSLSpecificCommand(String eventOccurance) {
		System.out.println("Start executing dsl-specific command");
		//TODO: Calling the event manager
	}
	
	public void setDSLPath (String DSLPath) {
		this.DSLPath = DSLPath;
	}
	public void setMUTPath (String MUTPath) {
		this.MUTPath = MUTPath;
	}
}