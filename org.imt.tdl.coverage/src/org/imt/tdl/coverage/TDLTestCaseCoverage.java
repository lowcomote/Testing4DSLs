package org.imt.tdl.coverage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.gemoc.trace.commons.model.trace.SequentialStep;
import org.eclipse.gemoc.trace.commons.model.trace.Step;
import org.eclipse.gemoc.trace.commons.model.trace.Trace;
import org.etsi.mts.tdl.TestDescription;
import org.imt.tdl.coverage.dslSpecific.DSLSpecificCoverageRule;

public class TDLTestCaseCoverage {

	private TestDescription testCase;
	private Resource MUTResource;
	private Trace<?, ?, ?> trace;
	
	private EList<DSLSpecificCoverageRule> dslSpecificCoverageRules;

	private List<EObject> modelObjects;
	private List<String> tcObjectCoverageStatus;
	
	double tcCoveragePercentage;
	int numOfCoveredObjs;
	int numOfNotCoverableElements;
	
	public TDLTestCaseCoverage() {
		modelObjects = new ArrayList<>();
		tcObjectCoverageStatus = new ArrayList<>();	
		numOfCoveredObjs= 0 ;
		numOfNotCoverableElements = 0;
	}
	//calculating the coverage of the test case based on the model execution trace and dsl-specific coverage rules
	public void calculateTCCoverage () {
		//find coverable objects using the MUTResource of the test case
		findNotCoverableObjects();
		Step<?> rootStep = trace.getRootStep();
		calculateObjectCoverage(rootStep);
		changeCoverable2notCovered();
	}

	private void findNotCoverableObjects() {
		TreeIterator<EObject> modelContents = MUTResource.getAllContents();
		while (modelContents.hasNext()) {
			EObject modelObject = modelContents.next();
			modelObjects.add(modelObject);
			if (TDLCoverageUtil.getInstance().isClassCoverable(modelObject.eClass())) {
				tcObjectCoverageStatus.add(TDLCoverageUtil.COVERABLE);
			}
			else {
				tcObjectCoverageStatus.add(TDLCoverageUtil.NOT_COVERABLE);
			}
		}
	}
	
	private void calculateObjectCoverage(Object rootStep) {
		if (rootStep instanceof SequentialStep) {
			SequentialStep<?, ?> step = (SequentialStep<?, ?>) rootStep;
			if (step.getMseoccurrence() != null) {
				EObject object = step.getMseoccurrence().getMse().getCaller();
				int objectIndex = modelObjects.indexOf(object);
				if (objectIndex != -1){
					String objectCoverage = tcObjectCoverageStatus.get(objectIndex);
					if (objectCoverage != TDLCoverageUtil.COVERED && objectCoverage != TDLCoverageUtil.NOT_COVERABLE) {
						tcObjectCoverageStatus.set(objectIndex, TDLCoverageUtil.COVERED);
						if (dslSpecificCoverageRules != null && 
							dslSpecificCoverageRules.stream().filter(r -> r.getContext().equals(object.eClass())).count()>0) {
							applyDSLSpecificCoverageRules(object);
						}
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
	
	private void applyDSLSpecificCoverageRules(EObject object) {
		List<DSLSpecificCoverageRule> relatedRules = dslSpecificCoverageRules.stream().
				filter(r -> r.getContext().equals(object.eClass())).collect(Collectors.toList());
		for (DSLSpecificCoverageRule rule:relatedRules) {
			for (EReference eRefrence : rule.getImpliesReferenceCoverage()) {
				Object referencedObject = object.eGet(eRefrence);
				if (referencedObject != null) {
					if (referencedObject instanceof EObject) {
						checkCoverage4referencedObject((EObject) referencedObject);
					}
					else if (referencedObject instanceof EObjectContainmentEList<?>) {
						((EObjectContainmentEList<?>) referencedObject).forEach(o -> checkCoverage4referencedObject((EObject) o));
					}
				}
			}
		}
	}
	
	private void checkCoverage4referencedObject(EObject refrencedObject) {
		int refrencedObjectIndex = modelObjects.indexOf(refrencedObject);
		if (refrencedObjectIndex != -1){
			String refrencedObjectCoverage = tcObjectCoverageStatus.get(refrencedObjectIndex);
			if (refrencedObjectCoverage != TDLCoverageUtil.COVERED && 
					refrencedObjectCoverage != TDLCoverageUtil.NOT_COVERABLE) {
				tcObjectCoverageStatus.set(refrencedObjectIndex, TDLCoverageUtil.COVERED);
				if (dslSpecificCoverageRules != null && 
					dslSpecificCoverageRules.stream().filter(r -> r.getContext().equals(refrencedObject.eClass())).count()>0) {
					applyDSLSpecificCoverageRules(refrencedObject);
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
	
	@SuppressWarnings("unused")
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
	
	public void calculateCoveragePercentage() {
		int numOfCoverableElements = this.tcObjectCoverageStatus.size() - this.numOfNotCoverableElements;
		tcCoveragePercentage = (double)(this.numOfCoveredObjs*100)/numOfCoverableElements;
		BigDecimal bd = new BigDecimal(tcCoveragePercentage).setScale(2, RoundingMode.HALF_UP);
		tcCoveragePercentage = bd.doubleValue();
		//System.out.println(this.testCase.getName() + " coverage: " + tcCoveragePercentage);
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

	public TestDescription getTestCase() {
		return testCase;
	}

	public EList<DSLSpecificCoverageRule> getDslSpecificCoverageRules() {
		return dslSpecificCoverageRules;
	}
	public void setDslSpecificCoverageRules(EList<DSLSpecificCoverageRule> dslSpecificCoverageRules) {
		this.dslSpecificCoverageRules = dslSpecificCoverageRules;
	}
	public void setTestCase(TestDescription testCase) {
		this.testCase = testCase;
	}
	public String getTestCaseName() {
		return testCase.getName();
	}
	public List<EObject> getModelObjects() {
		return modelObjects;
	}
	public List<String> getTcObjectCoverageStatus() {
		return tcObjectCoverageStatus;
	}
	public double getTcCoveragePercentage() {
		return tcCoveragePercentage;
	}
	
}
