package org.imt.tdl.coverage.dslSpecific;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.imt.tdl.coverage.TDLCoverageUtil;
import org.imt.tdl.coverage.TDLTestCaseCoverage;

import DSLSpecificCoverage.ConditionType;
import DSLSpecificCoverage.ContainerCoverageInference;
import DSLSpecificCoverage.Context;
import DSLSpecificCoverage.CoveredContainee;
import DSLSpecificCoverage.EClassIgnorance;
import DSLSpecificCoverage.EObjectIgnorance;
import DSLSpecificCoverage.ReferenceCoverageInference;
import DSLSpecificCoverage.Rule;

public class DSLSpecificCoverageExecutor {

	private TDLTestCaseCoverage testCaseCoverage;
	
	public DSLSpecificCoverageExecutor (TDLTestCaseCoverage testCaseCoverage) {
		this.testCaseCoverage = testCaseCoverage;
	}
	//apply all the rules on the object (NOTE: rule's context = object type)
	public void applyCoverageRules(EList<Rule> rules, List<EObject> eObjects) {
		for (Rule rule:rules) {
			if (rule instanceof ReferenceCoverageInference) {
				updateCoverableClasses((ReferenceCoverageInference) rule);
				eObjects.forEach(object -> 
					inferReferenceCoverage((ReferenceCoverageInference) rule, object));
			}
			else if (rule instanceof ContainerCoverageInference) {
				updateCoverableClasses((ContainerCoverageInference) rule);
				eObjects.forEach(object -> 
					inferContainerCoverage((ContainerCoverageInference) rule, object));
			}
			else if (rule instanceof EObjectIgnorance) {
				eObjects.forEach(object -> 
					runEObjectIgnoranceRule((EObjectIgnorance) rule, object));
			}
			else if (rule instanceof EClassIgnorance) {
				updateCoverableClasses((EClassIgnorance) rule);
				eObjects.forEach(object -> 
					runEClassIgnoranceRule((EClassIgnorance) rule, object));
			}
		}
	}
	
	//check if the context of the container is specified in the rule
	private void updateCoverableClasses(ContainerCoverageInference rule) {
		if (!rule.getContainerContext().isEmpty()) {
			rule.getContainerContext().forEach(c -> addCoverableClass(c));
		}
		else {
			addCoverableClass((EClass) rule.getContainementReference().eContainer());
		}
	}

	private void updateCoverableClasses(ReferenceCoverageInference rule) {
		TDLCoverageUtil.getInstance().addCoverableClass(
				(EClass) rule.getEReference().getEType());
	}
	
	private void updateCoverableClasses(EClassIgnorance rule) {
		if (rule.isIgnoreSubclasses()) {
			TDLCoverageUtil.getInstance().removeCoverableClass_subClass(
					((Context) rule.eContainer()).getMetaclass());
		}
		else {
			TDLCoverageUtil.getInstance().removeCoverableClass(
					((Context) rule.eContainer()).getMetaclass());
		}
	}
	
	private void addCoverableClass(EClass c) {
		TDLCoverageUtil.getInstance().addCoverableClass(c);
	}

	private void inferReferenceCoverage(ReferenceCoverageInference r, EObject object) {
		EReference ref = (EReference) getMatchedFeature(object, r.getEReference().getName());
		if (ref == null) {return; }
		
		Object referencedObject = object.eGet(ref);
		if (referencedObject == null) { return; }
		
		if (referencedObject instanceof EObject) {
			testCaseCoverage.setObjectCoverage(
					(EObject) referencedObject, testCaseCoverage.getObjectCoverage(object));
		}
		else if (referencedObject instanceof EObjectContainmentEList<?>) {
			((EObjectContainmentEList<?>) referencedObject).
				forEach(o -> testCaseCoverage.setObjectCoverage(
						(EObject) o, testCaseCoverage.getObjectCoverage(object)));
		}
	}

	private void inferContainerCoverage(ContainerCoverageInference r, EObject object) {
		//check if the object container is the one expected by the rule
		if (!r.getContainerContext().stream().anyMatch
				(c -> c.getName().equals(object.eContainer().eClass().getName()))) {
			return;
		}
		
		EReference ref = (EReference) getMatchedFeature(object.eContainer(), r.getContainementReference().getName());
		if (ref == null) { return;}
		
		Object containedObject = object.eContainer().eGet(ref);
		if (containedObject == null) { return; }
		
		if (containedObject instanceof EObject) {
			EObject containee = (EObject) containedObject;
			testCaseCoverage.setObjectCoverage(
					containee.eContainer(), testCaseCoverage.getObjectCoverage(containee));
		}
		else if (containedObject instanceof EObjectContainmentEList<?>) {
			//if several objects are contained, set coverage based on the rule's multiplicity
			if (r.getMultiplicity() == CoveredContainee.ONE) {
				coverContainerIfOneContaineeCovered((EObjectContainmentEList<?>) containedObject);
			}
			else {
				coverContainerIfAllContaineeCovered((EObjectContainmentEList<?>) containedObject);
			}
		}
	}
	
	private void coverContainerIfOneContaineeCovered(EObjectContainmentEList<?> containedObjects) {
		String containeeCoverage = TDLCoverageUtil.NOT_COVERED;
		for (Object containee:containedObjects) {
			containeeCoverage = testCaseCoverage.getObjectCoverage((EObject) containee);
			if (containeeCoverage == TDLCoverageUtil.COVERED) {
				break;
			}
		}
		testCaseCoverage.setObjectCoverage(
				((EObject) containedObjects.get(0)).eContainer(), containeeCoverage);
	}
	
	private void coverContainerIfAllContaineeCovered(EObjectContainmentEList<?> containedObjects) {
		int coveredContaineeCounter = 0;
		for (Object containee:containedObjects) {
			String containeeCoverage = testCaseCoverage.getObjectCoverage((EObject) containee);
			if (containeeCoverage == TDLCoverageUtil.COVERED) {
				coveredContaineeCounter++;
			}
		}
		if (coveredContaineeCounter == containedObjects.size()) {
			testCaseCoverage.setObjectCoverage(((EObject) containedObjects.get(0)).eContainer(), TDLCoverageUtil.COVERED);
		}
	}
	
	private void runEObjectIgnoranceRule(EObjectIgnorance rule, EObject object) {
		if (rule.getCondition() == ConditionType.INCLUSION) {
			//ignore EObjects contained by one of the containerContext classes
			if (rule.getContainerContext().stream().
				anyMatch(c -> c.getName().equals(object.eContainer().eClass().getName()))) {
				testCaseCoverage.setObjectCoverage(object, TDLCoverageUtil.NOT_COVERABLE);
			}
		}
		else if (rule.getCondition() == ConditionType.EXCLUSION) {
			//ignore EObjects that are not contained by any of the containerContext classes
			if (!rule.getContainerContext().stream().
				anyMatch(c -> c.getName().equals(object.eContainer().eClass().getName()))) {
				testCaseCoverage.setObjectCoverage(object, TDLCoverageUtil.NOT_COVERABLE);
			}
		}
	}
	
	private void runEClassIgnoranceRule(EClassIgnorance rule, EObject object) {
		testCaseCoverage.setObjectNotCoverable(object);
	}
	
	private EStructuralFeature getMatchedFeature(EObject rootElement, String featureName){
		EStructuralFeature matchedFeature = rootElement.eClass().getEAllStructuralFeatures().stream().
				filter(f -> f.getName().equals(featureName)).findFirst().get();
		return matchedFeature;
	}
}
