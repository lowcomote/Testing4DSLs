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
import DSLSpecificCoverage.CoverageByContainment;
import DSLSpecificCoverage.Context;
import DSLSpecificCoverage.CoveredContainee;
import DSLSpecificCoverage.Ignore;
import DSLSpecificCoverage.IgnoreIfContained;
import DSLSpecificCoverage.CoverageByReference;
import DSLSpecificCoverage.Rule;

public class DSLSpecificCoverageExecutor {

	private TDLTestCaseCoverage testCaseCoverage;
	
	public DSLSpecificCoverageExecutor (TDLTestCaseCoverage testCaseCoverage) {
		this.testCaseCoverage = testCaseCoverage;
	}
	//apply all the rules on the object (NOTE: rule's context = object type)
	public void applyCoverageRules(EList<Rule> rules, List<EObject> eObjects) {
		for (Rule rule:rules) {
			if (rule instanceof CoverageByReference) {
				updateCoverableClasses((CoverageByReference) rule);
				eObjects.forEach(object -> 
					inferReferenceCoverage((CoverageByReference) rule, object));
			}
			else if (rule instanceof CoverageByContainment) {
				updateCoverableClasses((CoverageByContainment) rule);
				eObjects.forEach(object -> 
					inferContainerCoverage((CoverageByContainment) rule, object));
			}
			else if (rule instanceof IgnoreIfContained) {
				eObjects.forEach(object -> 
					runIgnoreIfContainedRule((IgnoreIfContained) rule, object));
			}
			else if (rule instanceof Ignore) {
				updateCoverableClasses((Ignore) rule);
				eObjects.forEach(object -> 
					runIgnoreRule((Ignore) rule, object));
			}
		}
	}
	
	private void updateCoverableClasses(CoverageByContainment rule) {
		addCoverableClass(((Context) rule.eContainer()).getMetaclass());
	}

	private void updateCoverableClasses(CoverageByReference rule) {
		TDLCoverageUtil.getInstance().addCoverableClass(
				(EClass) rule.getReference().getEType());
	}
	
	private void updateCoverableClasses(Ignore rule) {
		if (rule.isIgnoreIfSubtyped()) {
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

	private void inferReferenceCoverage(CoverageByReference r, EObject object) {
		EReference ref = (EReference) getMatchedFeature(object, r.getReference().getName());
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

	private void inferContainerCoverage(CoverageByContainment r, EObject object) {
		EReference ref = (EReference) getMatchedFeature(object, r.getContainmentReference().getName());
		if (ref == null) { return;}
		
		Object containedObject = object.eGet(ref);
		if (containedObject == null) { return; }
		
		if (containedObject instanceof EObject) {
			EObject containee = (EObject) containedObject;
			testCaseCoverage.setObjectCoverage(
					object, testCaseCoverage.getObjectCoverage(containee));
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
	
	private void runIgnoreIfContainedRule(IgnoreIfContained rule, EObject object) {
		if (rule.getCondition() == ConditionType.INCLUSION) {
			//ignore EObjects contained by one of the ContainerType classes
			if (rule.getContainerType().stream().
				anyMatch(c -> c.getName().equals(object.eContainer().eClass().getName()))) {
				testCaseCoverage.setObjectNotCoverable(object);
			}
		}
		else if (rule.getCondition() == ConditionType.EXCLUSION) {
			//ignore EObjects that are not contained by any of the ContainerType classes
			if (!rule.getContainerType().stream().
				anyMatch(c -> c.getName().equals(object.eContainer().eClass().getName()))) {
				testCaseCoverage.setObjectNotCoverable(object);
			}
		}
	}
	
	private void runIgnoreRule(Ignore rule, EObject object) {
		if (!rule.isIgnoreIfSubtyped() && !object.eClass().getName().equals(
				((Context) rule.eContainer()).getMetaclass().getName())) {
			return;
		}
		testCaseCoverage.setObjectNotCoverable(object);
	}
	
	private EStructuralFeature getMatchedFeature(EObject rootElement, String featureName){
		EStructuralFeature matchedFeature = rootElement.eClass().getEAllStructuralFeatures().stream().
				filter(f -> f.getName().equals(featureName)).findFirst().get();
		return matchedFeature;
	}
}
