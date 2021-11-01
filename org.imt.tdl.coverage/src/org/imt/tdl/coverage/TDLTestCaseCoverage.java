package org.imt.tdl.coverage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gemoc.trace.commons.model.trace.SequentialStep;
import org.eclipse.gemoc.trace.commons.model.trace.Step;
import org.eclipse.gemoc.trace.commons.model.trace.Trace;

public class TDLTestCaseCoverage {

	private Trace trace;
	
	public HashMap<EObject, String> tcObjectCoverageStatus = TDLCoverageUtil.getInstance().objectCoverageStatus;
	int numOfCoveredObjs = 0;
	private double tcCoveragePercentage;
	
	//calculating the coverage of the test case based on the model execution trace
	public void calculateTCCoverage (HashMap<EObject, String> tsObjectCoverageStatus) {
		Step rootStep = trace.getRootStep();
		if (rootStep instanceof SequentialStep) {
			SequentialStep step = (SequentialStep) rootStep;
			if (step.getMseoccurrence() != null) {
				EObject object = step.getMseoccurrence().getMse().getCaller();
				numOfCoveredObjs++;
				tcObjectCoverageStatus.replace(object, TDLCoverageUtil.COVERED);
				if (tsObjectCoverageStatus.get(object) != TDLCoverageUtil.COVERED) {
					tsObjectCoverageStatus.replace(object, TDLCoverageUtil.COVERED);
				}
			}
			if (step.getSubSteps() != null) {
				for (int i=0; i < step.getSubSteps().size(); i++) {
					calculateObjectCoverage(step.getSubSteps().get(i), tsObjectCoverageStatus);
				}
			}
		}
		
		this.tcCoveragePercentage = (numOfCoveredObjs*100)/TDLCoverageUtil.getInstance().getModelSize();
	}
	
	public void calculateObjectCoverage(Object rootStep, HashMap<EObject, String> tsObjectCoverageStatus) {
		if (rootStep instanceof SequentialStep) {
			SequentialStep step = (SequentialStep) rootStep;
			if (step.getMseoccurrence() != null) {
				EObject object = step.getMseoccurrence().getMse().getCaller();
				numOfCoveredObjs++;
				tcObjectCoverageStatus.replace(object, TDLCoverageUtil.COVERED);
				if (tsObjectCoverageStatus.get(object) != TDLCoverageUtil.COVERED) {
					tsObjectCoverageStatus.replace(object, TDLCoverageUtil.COVERED);
				}
			}
			if (step.getSubSteps() != null) {
				for (int i=0; i < step.getSubSteps().size(); i++) {
					calculateObjectCoverage(step.getSubSteps().get(i), tsObjectCoverageStatus);
				}
			}
		}
	}
	
	public Trace getTrace() {
		return trace;
	}

	public void setTrace(Trace trace) {
		this.trace = trace;
	}

	public double getTcCoveragePercentage() {
		return tcCoveragePercentage;
	}

	public void setTcCoveragePercentage(double tcCoveragePercentage) {
		this.tcCoveragePercentage = tcCoveragePercentage;
	}
}
