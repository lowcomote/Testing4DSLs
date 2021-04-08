package org.imt.tdl.executionEngine;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.gemoc.executionframework.engine.commons.EngineContextException;
import org.eclipse.gemoc.executionframework.event.manager.GenericEventManager;
import org.imt.tdl.oclInterpreter.OCLInterpreter;

public class EngineFactory{
	
	private String DSLPath;
	private String MUTPath;
	
	private IExecutionEngine engineLauncher;
	private OCLInterpreter oclLauncher;
	private GenericEventManager eventManager;
	
	public final static String GENERIC = "Generic";
	public final static String DSL_SPECIFIC = "DSL-Specific";
	public final static String OCL = "OCL";

	public void setUp(String commandType) throws CoreException, EngineContextException {
		if (commandType.equals(GENERIC)) {
			String engineType = this.getEngineType();
			if (engineType=="ale") {
				this.engineLauncher = new ALEEngineLauncher();	
			}else if(engineType=="k3") {
				this.engineLauncher = new JavaEngineLauncher();
			}
			this.engineLauncher.setUp(this.MUTPath, this.DSLPath);
		}else if(commandType.equals(DSL_SPECIFIC)) {
			if (this.eventManager == null) {
				//this.eventManager = new GenericEventManager();
			}
		}else if (commandType.equals(OCL)) {
			if (this.engineLauncher==null) {
				System.out.println("There is no model under execution. You have to run the model first.");
			}else if (this.oclLauncher == null) {
				this.oclLauncher = new OCLInterpreter();
				this.oclLauncher.setUp();
			}
		}
	}
	public String executeGenericCommand() throws CoreException, EngineContextException {
		return this.engineLauncher.executeModel();
	}
	public String executeOCLCommand (String query){
		//send the query without quotation marks
		return this.oclLauncher.runQuery(this.engineLauncher.getModelResource(), query.substring(1, query.length()-1));
	}
	public String executeDSLSpecificCommand(String eventName, Map<String, Object> parameters) {
		//TODO: Calling the event manager
		return null;
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
	public void setMUTResource(Resource MUTResource) {
		this.engineLauncher.setModelResource(MUTResource);
	}
	public Resource getMUTResource() {
		return this.engineLauncher.getModelResource();
	}
	public ArrayList<EObject> getOCLResultAsObject() {
		return this.oclLauncher.getResultAsObject();
	}
	public ArrayList<String> getOCLResultAsString(){
		return this.oclLauncher.getResultAsString();
	}
}