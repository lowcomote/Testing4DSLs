package org.imt.ale.customLaunchConfig;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.gemoc.executionframework.engine.commons.EngineContextException;
import org.eclipse.ocl.ParserException;
import org.eclipse.gemoc.ale.interpreted.engine.AleEngine;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.gemoc.execution.sequential.javaengine.PlainK3ExecutionEngine;

public class CustomLauncher{
	
	private String DSLPath;
	private String MUTPath;
	private Resource MUTResource;
	
	private JavaEngineLauncher javaEngineLauncher;
	private PlainK3ExecutionEngine javaEngine;
	private ALEEngineLauncher aleEngineLauncher;
	private AleEngine aleEngine;
	private OCLLauncher oclLauncher;
	private EventManagerLauncher eventManagerLauncher;
	
	public final static String GENERIC = "Generic";
	public final static String DSL_SPECIFIC = "DSL-Specific";
	public final static String OCL = "OCL";

	public void setUp(String configurationType) throws CoreException, EngineContextException {
		this.MUTResource = (new ResourceSetImpl()).getResource(URI.createURI(this.MUTPath), true);
		if (configurationType.equals(GENERIC)) {
			String engineType = this.getEngineType();
			if (engineType=="ale") {
				if (this.aleEngineLauncher == null) {
					System.out.println("Gemoc ALE engine setup");
					this.aleEngineLauncher = new ALEEngineLauncher();
					this.aleEngineLauncher.setUp(this.MUTPath, this.DSLPath);
				}
			}else if(engineType=="k3") {
				if (this.javaEngineLauncher == null) {
					System.out.println("Gemoc java engine setup");
					this.javaEngineLauncher = new JavaEngineLauncher();
					this.javaEngineLauncher.setUp(this.MUTPath, this.DSLPath);
				}
			}
		}else if(configurationType.equals(DSL_SPECIFIC)) {
			if (this.eventManagerLauncher == null) {
				System.out.println("Event manager setup");
				this.eventManagerLauncher = new EventManagerLauncher();
				this.eventManagerLauncher.setUp();
			}
		}else if (configurationType.equals(OCL)) {
			if (this.oclLauncher == null) {
				System.out.println("OCL engine setup");
				this.oclLauncher = new OCLLauncher();
				this.oclLauncher.setUp();
			}
		}
	}
	public void executeGenericCommand() throws CoreException, EngineContextException {
		System.out.println("Start executing generic command");
		String engineType = this.getEngineType();
		if (engineType=="ale") {
			this.aleEngine = this.aleEngineLauncher.createExecutionEngine();
			this.MUTResource = this.aleEngine.getExecutionContext().getResourceModel();
		}else if(engineType=="k3") {
			this.javaEngine = this.javaEngineLauncher.createExecutionEngine();
			this.MUTResource = this.javaEngine.getExecutionContext().getResourceModel();
		}
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
	private String getEngineType() {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(this.DSLPath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		if (dsl.getEntry("metaprog") != null) {
			String metaprog = dsl.getEntry("metaprog").getValue();
			if (metaprog.contains("ale")) {
				return "ale";
			}else if(metaprog.contains("kermeta3")) {
				return "k3";
			}
		}
		return null;
	}
	public void setDSLPath (String DSLPath) {
		this.DSLPath = DSLPath;
	}
	public void setMUTPath (String MUTPath) {
		this.MUTPath = MUTPath;
	}
}