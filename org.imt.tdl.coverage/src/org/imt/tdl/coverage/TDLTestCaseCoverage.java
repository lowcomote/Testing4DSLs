package org.imt.tdl.coverage;

import java.util.HashMap;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.trace.commons.model.trace.SequentialStep;
import org.eclipse.gemoc.trace.commons.model.trace.State;
import org.eclipse.gemoc.trace.commons.model.trace.Step;
import org.eclipse.gemoc.trace.commons.model.trace.Trace;

public class TDLTestCaseCoverage {

	private String MUTPath;
	private Trace trace;
	
	private TreeIterator<EObject> modelContents;
	private HashMap<EObject, String> objectCoverage = new HashMap<>();
	
	private final static String COVERED = "Covered";
	private final static String NOT_COVERED = "Not-covered";
	private final static String UNCOVERABLE = "Uncoverable";
	
	//calculating the coverage of the test case based on the model execution trace
	public void calculateTCCoverage () {
		//list model elements
		Resource MUTResource = (new ResourceSetImpl()).getResource(URI.createURI(this.MUTPath), true);
		this.modelContents = MUTResource.getAllContents();
		
		Step rootStep = trace.getRootStep();
		if (rootStep instanceof SequentialStep) {
			SequentialStep step = (SequentialStep) rootStep;
			if (step.getMseoccurrence() != null) {
				objectCoverage.put(step.getMseoccurrence().getMse().getCaller(), COVERED);
			}
			for (int i=0; i < step.getSubSteps().size(); i++) {
				calculateObjectCoverage(step.getSubSteps().get(i));
			}
		}
	}
	
	public void calculateObjectCoverage(Object rootStep) {
		if (rootStep instanceof SequentialStep) {
			SequentialStep step = (SequentialStep) rootStep;
			if (step.getMseoccurrence() != null) {
				objectCoverage.put(step.getMseoccurrence().getMse().getCaller(), COVERED);
			}
			for (int i=0; i < step.getSubSteps().size(); i++) {
				calculateObjectCoverage(step.getSubSteps().get(i));
			}
		}
	}
	
	public Trace getTrace() {
		return trace;
	}

	public void setTrace(Trace trace) {
		this.trace = trace;
	}

	public String getMUTPath() {
		return MUTPath;
	}

	public void setMUTPath(String mUTPath) {
		MUTPath = mUTPath;
	}
}
