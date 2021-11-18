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
	
	private int numOfCoverableElements;
	public int numOfCoveredObjs;
	double tcCoveragePercentage;
	
	//calculating the coverage of the test case based on the model execution trace
	public void calculateTCCoverage () {
		//find coverable objects using the MUTResource of the test case
		findNotCoverableObjects();

		Step<?> rootStep = this.trace.getRootStep();
		calculateObjectCoverage(rootStep);
		checkContainmentRelations(TDLCoverageUtil.getInstance().modelObjects.get(0));
	}

	public void findNotCoverableObjects() {
		TDLCoverageUtil.getInstance().modelObjects.clear();
		TreeIterator<EObject> modelContents = this.MUTResource.getAllContents();
		while (modelContents.hasNext()) {
			EObject modelObject = modelContents.next();
			TDLCoverageUtil.getInstance().modelObjects.add(modelObject);
			if (TDLCoverageUtil.getInstance().coverableClasses.contains(modelObject.eClass().getName())) {
				this.tcObjectCoverageStatus.add(TDLCoverageUtil.COVERABLE);
				this.numOfCoverableElements++;
			}
			else {
				this.tcObjectCoverageStatus.add(TDLCoverageUtil.NOT_COVERABLE);
			}
		}
	}
	
	public void calculateObjectCoverage(Object rootStep) {
		if (rootStep instanceof SequentialStep) {
			SequentialStep<?, ?> step = (SequentialStep<?, ?>) rootStep;
			if (step.getMseoccurrence() != null) {
				EObject object = step.getMseoccurrence().getMse().getCaller();
				int objectIndex = TDLCoverageUtil.getInstance().modelObjects.indexOf(object);
				String objectCoverage = this.tcObjectCoverageStatus.get(objectIndex);
				if (objectCoverage != TDLCoverageUtil.COVERED && objectCoverage != TDLCoverageUtil.NOT_COVERABLE) {
					this.tcObjectCoverageStatus.set(objectIndex, TDLCoverageUtil.COVERED);
					this.numOfCoveredObjs++;
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
		if (rootObject.eContents().size()>0) {
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
				this.tcObjectCoverageStatus.set(rootObjectIndex, TDLCoverageUtil.COVERED);
				if (!TDLCoverageUtil.getInstance().coverableClasses.contains(rootObject.eClass().getName())) {
					TDLCoverageUtil.getInstance().coverableClasses.add(rootObject.eClass().getName());
				}
				this.numOfCoveredObjs++;
			}
		}
		//check containment relations for the next elements
		if (rootObjectIndex < TDLCoverageUtil.getInstance().modelObjects.size() - 1) {
			checkContainmentRelations(TDLCoverageUtil.getInstance().modelObjects.get(rootObjectIndex + 1));
		}
	}
	
	public int getNumOfCoverableElements() {
		//this returns the size of the model in terms of its coverable elements
		return this.numOfCoverableElements;
	}
	
	public void addNumOfCoveredObjs() {
		this.numOfCoveredObjs++;
	}
	
	public void calculateCoveragePercentage() {
		this.tcCoveragePercentage = (double)(this.numOfCoveredObjs*100)/this.numOfCoverableElements;
		System.out.println(this.testCaseName + " coverage: " + this.tcCoveragePercentage);
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
