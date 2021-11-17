package org.imt.tdl.coverage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gemoc.trace.commons.model.trace.SequentialStep;
import org.eclipse.gemoc.trace.commons.model.trace.Step;
import org.eclipse.gemoc.trace.commons.model.trace.Trace;

public class TDLTestCaseCoverage {

	public String testCaseName;
	private Resource MUTResource;
	private Trace<?, ?, ?> trace;
	
	public List<String> tcObjectCoverageStatus = new ArrayList<>();
	int numOfCoveredObjs = 0;
	private double tcCoveragePercentage;
	
	//calculating the coverage of the test case based on the model execution trace
	public void calculateTCCoverage () {
		//find coverable objects using the MUTResource of the test case
		TDLCoverageUtil.getInstance().setMUTResource(this.MUTResource);
		TDLCoverageUtil.getInstance().findNotCoverableObjects();
		this.tcObjectCoverageStatus.addAll(TDLCoverageUtil.getInstance().objectCoverageStatus);

		Step<?> rootStep = this.trace.getRootStep();
		calculateObjectCoverage(rootStep);
		checkContainmentRelations(TDLCoverageUtil.getInstance().modelObjects.get(0));
		this.tcCoveragePercentage = (this.numOfCoveredObjs*100)/TDLCoverageUtil.getInstance().getNumOfCoverableElements();
		System.out.println(this.testCaseName + " coverage: " + this.tcCoveragePercentage);
	}

	public void calculateObjectCoverage(Object rootStep) {
		if (rootStep instanceof SequentialStep) {
			SequentialStep<?, ?> step = (SequentialStep<?, ?>) rootStep;
			if (step.getMseoccurrence() != null) {
				EObject object = step.getMseoccurrence().getMse().getCaller();
				int objectIndex = TDLCoverageUtil.getInstance().modelObjects.indexOf(object);
				String objectCoverage = this.tcObjectCoverageStatus.get(objectIndex);
				if (objectCoverage != TDLCoverageUtil.COVERED && objectCoverage != TDLCoverageUtil.NOT_COVERABLE) {
					this.numOfCoveredObjs++;
					this.tcObjectCoverageStatus.set(objectIndex, TDLCoverageUtil.COVERED);
				}
			}
			if (step.getSubSteps() != null) {
				for (int i=0; i < step.getSubSteps().size(); i++) {
					calculateObjectCoverage(step.getSubSteps().get(i));
				}
			}
		}
	}
	
	private void checkContainmentRelations(EObject rootObject) {
		int rootObjectIndex = TDLCoverageUtil.getInstance().modelObjects.indexOf(rootObject);
		String coverage = this.tcObjectCoverageStatus.get(rootObjectIndex);
		if (coverage == TDLCoverageUtil.COVERABLE && rootObject.eContents().size()>0) {
			for (int j=0; j<rootObject.eContents().size(); j++) {
				EObject containmentRef = rootObject.eContents().get(j);
				int refIndexInObjectList = TDLCoverageUtil.getInstance().modelObjects.indexOf(containmentRef);
				if (this.tcObjectCoverageStatus.get(refIndexInObjectList) == TDLCoverageUtil.COVERABLE) {
					checkContainmentRelations(containmentRef);
				}
			}
			//if all containments are COVERED, set the object as COVERED
			boolean covered = true;
			for (int j=0; j<rootObject.eContents().size(); j++) {
				EObject containmentRef = rootObject.eContents().get(j);
				int refIndexInObjectList = TDLCoverageUtil.getInstance().modelObjects.indexOf(containmentRef);
				if (this.tcObjectCoverageStatus.get(refIndexInObjectList) != TDLCoverageUtil.COVERED) {
					covered = false;
					break;
				}
			}
			if (covered) {
				this.numOfCoveredObjs++;
				this.tcObjectCoverageStatus.set(rootObjectIndex, TDLCoverageUtil.COVERED);
			}
		}
		//check containment relations for the next elements
		if (rootObjectIndex < TDLCoverageUtil.getInstance().modelObjects.size() - 1) {
			checkContainmentRelations(TDLCoverageUtil.getInstance().modelObjects.get(rootObjectIndex + 1));
		}
	}
	
	public Trace<?, ?, ?> getTrace() {
		return trace;
	}

	public void setTrace(Trace<?, ?, ?> trace) {
		this.trace = trace;
	}

	public double getTcCoveragePercentage() {
		return this.tcCoveragePercentage;
	}

	public void setTcCoveragePercentage(double tcCoveragePercentage) {
		this.tcCoveragePercentage = tcCoveragePercentage;
	}

	public Resource getMUTResource() {
		return this.MUTResource;
	}

	public void setMUTResource(Resource MUTResource) {
		this.MUTResource = MUTResource;
	}
}
