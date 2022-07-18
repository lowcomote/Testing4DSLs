package org.imt.tdl.coverage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	
	private Map<DSLSpecificCoverageRule, Boolean> coverageRule_status= new HashMap<>();

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
		if (!coverageRule_status.isEmpty()) {
			applyDSLSpecificCoverageRules();
		}
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
					if (objectCoverage == TDLCoverageUtil.COVERABLE) {
						tcObjectCoverageStatus.set(objectIndex, TDLCoverageUtil.COVERED);
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
	
	private void applyDSLSpecificCoverageRules() {
		//for each rule, find the model objects that are of type of the rule's context
		//apply the rule on all objects
		for (DSLSpecificCoverageRule rule:coverageRule_status.keySet()) {
			//a rule might be already executed while checking rule dependencies, so first check its status
			if (!coverageRule_status.get(rule)) {
				modelObjects.stream().filter(o -> rule.getContext().isSuperTypeOf(o.eClass())).collect(Collectors.toList()).
					forEach(object -> applyCoverageRule(rule, object));
				coverageRule_status.replace(rule, true);
			}
		}
	}
	
	private void applyCoverageRule(DSLSpecificCoverageRule rule, EObject object) {
		//first apply dependent rules that are not already applied
		if (!rule.getDependencies().isEmpty()) {
			rule.getDependencies().stream().filter(dr -> coverageRule_status.get(dr) == false).
				forEach(dr -> applyDependentRule(dr));
		}
		
		//infer coverage of association references
		if (rule.getReferenceCoverage() != null) {
			for (EReference eRefrence : rule.getReferenceCoverage()) {
				Object referencedObject = object.eGet(eRefrence);
				if (referencedObject != null) {
					if (referencedObject instanceof EObject) {
						setReferencedObjectCoverage((EObject) referencedObject, getObjectCoverage(object));
					}
					else if (referencedObject instanceof EObjectContainmentEList<?>) {
						((EObjectContainmentEList<?>) referencedObject).
							forEach(o -> setReferencedObjectCoverage((EObject) o, getObjectCoverage(object)));
					}
				}
			}
		}
		
		//infer coverage of containers when at least one contained element must be covered
		if (rule.getContainerCoverageByOne() != null) {
			for (EReference eRefrence : rule.getContainerCoverageByOne()) {
				Object containedObject = object.eGet(eRefrence);
				if (containedObject != null) {
					if (containedObject instanceof EObject) {
						EObject containee = (EObject) containedObject;
						setReferencedObjectCoverage(containee.eContainer(), getObjectCoverage(containee));
					}
					else if (containedObject instanceof EObjectContainmentEList<?>) {
						coverContainerIfOneContaineeCovered((EObjectContainmentEList<?>) containedObject);
					}
				}
			}
		}
		
		//infer coverage of containers when all contained elements must be covered
		if (rule.getContainerCoverageByAll() != null) {
			for (EReference eRefrence : rule.getContainerCoverageByAll()) {
				Object containedObject = object.eGet(eRefrence);
				if (containedObject != null) {
					if (containedObject instanceof EObject) {
						EObject containee = (EObject) containedObject;
						setReferencedObjectCoverage(containee.eContainer(), getObjectCoverage(containee));
					}
					else if (containedObject instanceof EObjectContainmentEList<?>) {
						coverContainerIfAllContaineeCovered((EObjectContainmentEList<?>) containedObject);
					}
				}
			}
		}
	}
	
	private String getObjectCoverage(EObject object) {
		int objectIndex = modelObjects.indexOf(object);
		if (objectIndex != -1){
			return tcObjectCoverageStatus.get(objectIndex);
		}
		return null;
	}
	
	private void applyDependentRule(DSLSpecificCoverageRule dependentRule) {
		//find the model objects that are of type of the rule's context
		//apply the rule on all objects and set the rule status true
		modelObjects.stream().filter(o -> dependentRule.getContext().isSuperTypeOf(o.eClass())).collect(Collectors.toList()).
			forEach(object -> applyCoverageRule(dependentRule, object));
		coverageRule_status.replace(dependentRule, true);
	}
	
	private void setReferencedObjectCoverage(EObject refrencedObject, String coverageStatus) {
		int refrencedObjectIndex = modelObjects.indexOf(refrencedObject);
		if (refrencedObjectIndex != -1){
			//set the coverage status when it is not already set (e.g., by another rule)
			if (tcObjectCoverageStatus.get(refrencedObjectIndex) == TDLCoverageUtil.COVERABLE) {
				tcObjectCoverageStatus.set(refrencedObjectIndex, coverageStatus);
			}
		}
	}
	
	private void coverContainerIfOneContaineeCovered(EObjectContainmentEList<?> containedObjects) {
		String containeeCoverage = TDLCoverageUtil.NOT_COVERED;
		for (Object containee:containedObjects) {
			containeeCoverage = getObjectCoverage((EObject) containee);
			if (containeeCoverage == TDLCoverageUtil.COVERED) {
				break;
			}
		}
		setReferencedObjectCoverage(((EObject) containedObjects.get(0)).eContainer(), containeeCoverage);
	}
	
	private void coverContainerIfAllContaineeCovered(EObjectContainmentEList<?> containedObjects) {
		int coveredContaineeCounter = 0;
		for (Object containee:containedObjects) {
			String containeeCoverage = getObjectCoverage((EObject) containee);
			if (containeeCoverage == TDLCoverageUtil.COVERED) {
				coveredContaineeCounter++;
			}
		}
		if (coveredContaineeCounter == containedObjects.size()) {
			setReferencedObjectCoverage(((EObject) containedObjects.get(0)).eContainer(), TDLCoverageUtil.COVERED);
		}
		else {
			setReferencedObjectCoverage(((EObject) containedObjects.get(0)).eContainer(), TDLCoverageUtil.NOT_COVERED);
		}
	}
	
	private void changeCoverable2notCovered() {
		for (int i=0; i<this.tcObjectCoverageStatus.size(); i++) {
			if (this.tcObjectCoverageStatus.get(i) == TDLCoverageUtil.COVERABLE) {
				this.tcObjectCoverageStatus.set(i, TDLCoverageUtil.NOT_COVERED);
			}
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

	public void setCoverageRules(EList<DSLSpecificCoverageRule> dslSpecificCoverageRules) {
		dslSpecificCoverageRules.forEach(r -> coverageRule_status.put(r, false));
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
