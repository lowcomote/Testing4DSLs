package org.imt.tdl.coverage;

import java.util.ArrayList;
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
	
	public List<EObject> modelObjects = new ArrayList<>();
	public List<String> tcObjectCoverageStatus = new ArrayList<>();
	
	//calculating the coverage of the test case based on the model execution trace
	public void calculateTCCoverage () {
		//find coverable objects using the MUTResource of the test case
		findNotCoverableObjects();
		Step<?> rootStep = this.trace.getRootStep();
		calculateObjectCoverage(rootStep);
		changeCoverable2notCovered();
		//checkContainmentRelations(this.modelObjects.get(0));
	}

	private void findNotCoverableObjects() {
		TreeIterator<EObject> modelContents = this.MUTResource.getAllContents();
		while (modelContents.hasNext()) {
			EObject modelObject = modelContents.next();
			this.modelObjects.add(modelObject);
			if (TDLCoverageUtil.getInstance().coverableClasses.contains(modelObject.eClass().getName())) {
				this.tcObjectCoverageStatus.add(TDLCoverageUtil.COVERABLE);
			}
			else {
				this.tcObjectCoverageStatus.add(TDLCoverageUtil.NOT_COVERABLE);
			}
		}
	}
	
	private void calculateObjectCoverage(Object rootStep) {
		if (rootStep instanceof SequentialStep) {
			SequentialStep<?, ?> step = (SequentialStep<?, ?>) rootStep;
			if (step.getMseoccurrence() != null) {
				EObject object = step.getMseoccurrence().getMse().getCaller();
				int objectIndex = this.modelObjects.indexOf(object);
				if (objectIndex != -1) {
					String objectCoverage = this.tcObjectCoverageStatus.get(objectIndex);
					if (objectCoverage != TDLCoverageUtil.COVERED && objectCoverage != TDLCoverageUtil.NOT_COVERABLE) {
						this.tcObjectCoverageStatus.set(objectIndex, TDLCoverageUtil.COVERED);
					}
				}
			}
			if (step.getSubSteps() != null) {
				for (int i=0; i < step.getSubSteps().size(); i++) {
					calculateObjectCoverage(step.getSubSteps().get(i));
				}
			}
		}
	}
	
	private void changeCoverable2notCovered() {
		for (int i=0; i<this.tcObjectCoverageStatus.size(); i++) {
			if (this.tcObjectCoverageStatus.get(i) == TDLCoverageUtil.COVERABLE) {
				this.tcObjectCoverageStatus.set(i, TDLCoverageUtil.NOT_COVERED);
			}
		}
	}
	
	private void checkContainmentRelations(EObject rootObject) {
		int rootObjectIndex = this.modelObjects.indexOf(rootObject);
		if (rootObjectIndex != -1 && this.tcObjectCoverageStatus.get(rootObjectIndex) != TDLCoverageUtil.COVERED) {
			if (rootObject.eContents().size()>0) {
				for (int j=0; j<rootObject.eContents().size(); j++) {
					EObject containmentRef = rootObject.eContents().get(j);
					int refIndexInObjectList = this.modelObjects.indexOf(containmentRef);
					if (refIndexInObjectList != -1 && this.tcObjectCoverageStatus.get(refIndexInObjectList) == TDLCoverageUtil.NOT_COVERABLE) {
						checkContainmentRelations(containmentRef);
					}
				}
				//if all containments are COVERED, set the object as COVERED
				int numOfCovered = 0;
				int numOfNotCovered = 0;
				for (int j=0; j<rootObject.eContents().size(); j++) {
					EObject containmentRef = rootObject.eContents().get(j);
					int refIndexInObjectList = this.modelObjects.indexOf(containmentRef);
					if (refIndexInObjectList != -1 && this.tcObjectCoverageStatus.get(refIndexInObjectList) == TDLCoverageUtil.COVERED) {
						numOfCovered++;
					}
					else if (this.tcObjectCoverageStatus.get(refIndexInObjectList) == TDLCoverageUtil.NOT_COVERED) {
						numOfNotCovered++;
					}
				}
				if (numOfCovered == rootObject.eContents().size()) {
					this.tcObjectCoverageStatus.set(rootObjectIndex, TDLCoverageUtil.COVERED);
				} 
				else if (numOfCovered + numOfNotCovered == rootObject.eContents().size()) {
					this.tcObjectCoverageStatus.set(rootObjectIndex, TDLCoverageUtil.NOT_COVERED);
				}
			}
		}
		//check containment relations for the next elements
		if (rootObjectIndex < this.modelObjects.size() - 1) {
			checkContainmentRelations(this.modelObjects.get(rootObjectIndex + 1));
		}
	}
	
	int numOfCoveredObjs;
	int numOfNotCoverableElements;
	
	public void countNumOfElements() {
		this.numOfCoveredObjs = 0;
		this.numOfNotCoverableElements = 0;
		for (String coverage:this.tcObjectCoverageStatus) {
			if (coverage == TDLCoverageUtil.NOT_COVERABLE) {
				this.numOfNotCoverableElements++;
			}
			else if (coverage == TDLCoverageUtil.COVERED) {
				this.numOfCoveredObjs++;
			}
		}
	}
	
	public double getCoveragePercentage() {
		int numOfCoverableElements = this.tcObjectCoverageStatus.size() - this.numOfNotCoverableElements;
		double tcCoveragePercentage = Math.ceil((double)(this.numOfCoveredObjs*100)/numOfCoverableElements);
		System.out.println(this.testCaseName + " coverage: " + tcCoveragePercentage);
		return tcCoveragePercentage;
	}
	
	public void calculateCoveragePercentage() {
		double tcOverallCoveragePercentage = Math.ceil((double)(this.numOfCoveredObjs*100)/this.tcObjectCoverageStatus.size());
		System.out.println(this.testCaseName + " overall coverage: " + tcOverallCoveragePercentage);
	}
	
	public Trace<?, ?, ?> getTrace() {
		return trace;
	}

	public void setTrace(Trace<?, ?, ?> trace) {
		this.trace = trace;
	}

	public Resource getMUTResource() {
		return this.MUTResource;
	}

	public void setMUTResource(Resource MUTResource) {
		this.MUTResource = MUTResource;
	}
}
