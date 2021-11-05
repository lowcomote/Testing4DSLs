package org.imt.tdl.configuration;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.gemoc.executionframework.engine.commons.EngineContextException;
import org.eclipse.gemoc.trace.commons.model.trace.State;
import org.eclipse.gemoc.trace.commons.model.trace.Step;
import org.eclipse.gemoc.trace.commons.model.trace.Trace;
import org.eclipse.gemoc.trace.commons.model.trace.TracedObject;
import org.imt.tdl.eventBasedEngine.IEventBasedExecutionEngine;
import org.imt.tdl.eventBasedEngine.K3EventManagerLauncher;
import org.imt.tdl.oclInterpreter.OCLInterpreter;
import org.imt.tdl.sequentialEngine.ALEEngineLauncher;
import org.imt.tdl.sequentialEngine.ISequentialExecutionEngine;
import org.imt.tdl.sequentialEngine.JavaEngineLauncher;

public class EngineFactory{
	
	private String DSLPath;
	private String MUTPath;
	
	private ISequentialExecutionEngine engineLauncher;
	private OCLInterpreter oclLauncher;
	private IEventBasedExecutionEngine eventManagerLauncher;
	
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
			this.eventManagerLauncher = new K3EventManagerLauncher();
			this.eventManagerLauncher.setUp(this.MUTPath, this.DSLPath);
		}else if (commandType.equals(OCL)) {
			if (this.oclLauncher == null) {
				this.oclLauncher = new OCLInterpreter();
				this.oclLauncher.setUp();
			}
		}
	}
	public String executeModel(Boolean sync) throws CoreException, EngineContextException {
		if (sync) {
			IDebugTarget[] debugTargets = DebugPlugin.getDefault().getLaunchManager().getDebugTargets();
			if (debugTargets.length > 0) {
				//we are in the Debug mode, so debug the model under test
				this.engineLauncher.launchModelDebugger();
				return "PASS: Debugging of the model under test launched successfully";
			}else {
				//we are in the Run mode, so run the model under test
				return this.engineLauncher.executeModelSynchronous();
			}
		}
		return this.engineLauncher.executeModelAsynchronous();
	}

	public String stopAsyncExecution() {
		return this.engineLauncher.stopAsynchronousExecution();
	}
	
	public String executeOCLCommand (String query){
		//send the query without quotation marks
		return this.oclLauncher.runQuery(getMUTResource(), query.substring(1, query.length()-1));
	}
	
	public String executeDSLSpecificCommand(String eventType, String eventName, Map<String, Object> parameters) {
		if (!this.eventManagerLauncher.isEngineStarted()) {
			IDebugTarget[] debugTargets = DebugPlugin.getDefault().getLaunchManager().getDebugTargets();
			if (debugTargets.length > 0) {
				//we are in the Debug mode, so debug the model under test
				this.eventManagerLauncher.launchModelDebugger();
			}else {
				this.eventManagerLauncher.startEngine();
			}
		}
		
		switch (eventType) {
		case "ACCEPTED":
			return this.eventManagerLauncher.processAcceptedEvent(eventName, parameters);
		case "EXPOSED":
			return this.eventManagerLauncher.assertExposedEvent(eventName, parameters);
		case "STOP":
			if (this.oclLauncher != null) {
				//if the test case contains OCL queries, just stop the engine regardless of its status
				return this.eventManagerLauncher.sendStopEvent(false);
			}else {
				//stop the engine and set the result based on the execution engine status
				return this.eventManagerLauncher.sendStopEvent(true);
			}
		default:
			break;
		}
		return "FAIL";
	}
	
	public Trace<Step<?>, TracedObject<?>, State<?, ?>> getExecutionTrace() {
		if (this.engineLauncher != null) {
			return this.engineLauncher.getExecutionTrace();
		}else if (this.eventManagerLauncher != null) {
			return this.eventManagerLauncher.getExecutionTrace();
		}
		return null;
	}
	
	private String getEngineType() {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(this.DSLPath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		if (dsl.getEntry("k3") != null) {
			return "k3";
		}else if (dsl.getEntry("ale") != null) {
			return "ale";
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
		if (this.engineLauncher != null) {
			return this.engineLauncher.getModelResource();
		}
		if (this.eventManagerLauncher != null) {
			return this.eventManagerLauncher.getModelResource();
		}
		return (new ResourceSetImpl()).getResource(URI.createURI(MUTPath), true);
	}
	public ArrayList<EObject> getOCLResultAsObject() {
		return this.oclLauncher.getResultAsObject();
	}
	public ArrayList<String> getOCLResultAsString(){
		return this.oclLauncher.getResultAsString();
	}
}