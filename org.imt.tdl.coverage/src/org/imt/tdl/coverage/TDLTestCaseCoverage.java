package org.imt.tdl.coverage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.gemoc.trace.commons.model.trace.SequentialStep;
import org.eclipse.gemoc.trace.commons.model.trace.Step;
import org.eclipse.gemoc.trace.commons.model.trace.Trace;
import org.etsi.mts.tdl.TestDescription;
import org.imt.tdl.coverage.dslSpecific.DSLSpecificCoverageExecutor;

import DSLSpecificCoverage.ConditionType;
import DSLSpecificCoverage.ContainerCoverageInference;
import DSLSpecificCoverage.Context;
import DSLSpecificCoverage.CoveredContainee;
import DSLSpecificCoverage.DomainSpecificCoverage;
import DSLSpecificCoverage.EClassIgnorance;
import DSLSpecificCoverage.EObjectIgnorance;
import DSLSpecificCoverage.ReferenceCoverageInference;
import DSLSpecificCoverage.Rule;

public class TDLTestCaseCoverage {

	private TestDescription testCase;
	private Resource MUTResource;
	private Trace<?, ?, ?> trace;
	
	private DomainSpecificCoverage dslSpecificCoverage;
	private Map<Context, List<EObject>> coverageContext_eobjects= new HashMap<>();

	private List<EObject> modelObjects;
	private List<String> tcObjectCoverageStatus;
	private List<String> tcObjectCoverageStatus_pv;
	
	double tcCoveragePercentage;
	int numOfCoveredObjs;
	int numOfNotCoverableElements;
	
	public TDLTestCaseCoverage() {
		modelObjects = new ArrayList<>();
		tcObjectCoverageStatus = new ArrayList<>();
		tcObjectCoverageStatus_pv = new ArrayList<>();
		numOfCoveredObjs= 0 ;
		numOfNotCoverableElements = 0;
	}
	
	//calculating the coverage of the test case based on the model execution trace and dsl-specific coverage rules
	public void calculateTCCoverage () {
		//if there is a dsl-specific coverage, initialize the map
		dslSpecificCoverage = TDLCoverageUtil.getInstance().getDslSpecificCoverage();
		if (dslSpecificCoverage != null) {
			dslSpecificCoverage.getContexts().
				forEach(c -> coverageContext_eobjects.put(c, new ArrayList<>()));
		}
		listEObjects();
		Step<?> rootStep = trace.getRootStep();
		calculateObjectCoverage(rootStep);
		if (!coverageContext_eobjects.isEmpty()) {
			runDSLSpecificCoverage();
		}
		findNotCoverableObjects();
	}

	private void listEObjects() {
		//list objects of the MUTResource of the test case and set their initial status as NOT-COVERED
		TreeIterator<EObject> modelContents = MUTResource.getAllContents();
		while (modelContents.hasNext()) {
			EObject modelObject = modelContents.next();
			modelObjects.add(modelObject);
			tcObjectCoverageStatus.add(TDLCoverageUtil.NOT_COVERED);
		}
	}
	
	private void calculateObjectCoverage(Object rootStep) {
		if (rootStep instanceof SequentialStep) {
			SequentialStep<?, ?> step = (SequentialStep<?, ?>) rootStep;
			if (step.getMseoccurrence() != null) {
				EObject object = step.getMseoccurrence().getMse().getCaller();
				setObjectCoverage(object, TDLCoverageUtil.COVERED);
			}
			if (step.getSubSteps() != null) {
				for (int i=0; i < step.getSubSteps().size(); i++) {
					calculateObjectCoverage(step.getSubSteps().get(i));
				}
			}
		}
	}
	
	private void runDSLSpecificCoverage() {
		//find the eobjects related to the rule's context
		for (Entry<Context,List<EObject>> entry:coverageContext_eobjects.entrySet()) {
			String contextName = entry.getKey().getMetaclass().getName();
			for (EObject object: modelObjects) {
				String objectTypeName = object.eClass().getName();
				if (contextName.equals(objectTypeName) ||
						object.eClass().getEAllSuperTypes().stream().
							filter(st -> st.getName().equals(contextName)).findAny().isPresent()) {
					entry.getValue().add(object);
				}
			}
		}
		//apply domain specific coverage while the coverage matrix changes
		boolean isCoverageMatrixChanged = true;
		while (isCoverageMatrixChanged) {
			tcObjectCoverageStatus_pv = new ArrayList<>(tcObjectCoverageStatus);
			coverageContext_eobjects.entrySet().stream().filter(entry -> !entry.getValue().isEmpty()).
				forEach(entry -> applyCoverageRules(entry.getKey().getRules(), entry.getValue()));
			isCoverageMatrixChanged = !Objects.equals(tcObjectCoverageStatus_pv, tcObjectCoverageStatus);
		}
	}

	//apply all the rules on the object (NOTE: rule's context = object type)
	private void applyCoverageRules(EList<Rule> rules, List<EObject> eObjects) {
		DSLSpecificCoverageExecutor executor = new DSLSpecificCoverageExecutor();
		for (Rule rule:rules) {
			if (rule instanceof ReferenceCoverageInference) {
				executor.updateCoverableClasses((ReferenceCoverageInference) rule);
				eObjects.forEach(object -> 
					inferReferenceCoverage((ReferenceCoverageInference) rule, object));
			}
			else if (rule instanceof ContainerCoverageInference) {
				executor.updateCoverableClasses((ContainerCoverageInference) rule);
				eObjects.forEach(object -> 
					inferContainerCoverage((ContainerCoverageInference) rule, object));
			}
			else if (rule instanceof EObjectIgnorance) {
				eObjects.forEach(object -> 
					runEObjectIgnoranceRule((EObjectIgnorance) rule, object));
			}
			else if (rule instanceof EClassIgnorance) {
				executor.runEClassIgnoranceRule((EClassIgnorance) rule);
			}
		}
	}

	private void inferReferenceCoverage(ReferenceCoverageInference r, EObject object) {
		EReference ref = (EReference) getMatchedFeature(object, r.getEReference().getName());
		if (ref == null) {return; }
		
		Object referencedObject = object.eGet(ref);
		if (referencedObject == null) { return; }
		
		if (referencedObject instanceof EObject) {
			setObjectCoverage((EObject) referencedObject, getObjectCoverage(object));
		}
		else if (referencedObject instanceof EObjectContainmentEList<?>) {
			((EObjectContainmentEList<?>) referencedObject).
				forEach(o -> setObjectCoverage((EObject) o, getObjectCoverage(object)));
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
			setObjectCoverage(containee.eContainer(), getObjectCoverage(containee));
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
	
	private void runEObjectIgnoranceRule(EObjectIgnorance rule, EObject object) {
		if (rule.getCondition() == ConditionType.INCLUSION) {
			//ignore EObjects contained by one of the containerContext classes
			if (rule.getContainerContext().stream().
				anyMatch(c -> c.getName().equals(object.eContainer().eClass().getName()))) {
				setObjectCoverage(object, TDLCoverageUtil.NOT_COVERABLE);
			}
		}
		else if (rule.getCondition() == ConditionType.EXCLUSION) {
			//ignore EObjects that are not contained by any of the containerContext classes
			if (!rule.getContainerContext().stream().
				anyMatch(c -> c.getName().equals(object.eContainer().eClass().getName()))) {
				setObjectCoverage(object, TDLCoverageUtil.NOT_COVERABLE);
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
	
	private void setObjectCoverage(EObject object, String coverageStatus) {
		int objectIndex = modelObjects.indexOf(object);
		if (objectIndex != -1 && 
				tcObjectCoverageStatus.get(objectIndex) != TDLCoverageUtil.COVERED){
			tcObjectCoverageStatus.set(objectIndex, coverageStatus);
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
		setObjectCoverage(((EObject) containedObjects.get(0)).eContainer(), containeeCoverage);
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
			setObjectCoverage(((EObject) containedObjects.get(0)).eContainer(), TDLCoverageUtil.COVERED);
		}
	}
	
	//if not-covered eobjects are of the not-coverable types, set their status as not-coverable
	private void findNotCoverableObjects() {
		for (int i=0; i<tcObjectCoverageStatus.size(); i++) {
			if (tcObjectCoverageStatus.get(i) == TDLCoverageUtil.NOT_COVERED &&
					!TDLCoverageUtil.getInstance().isClassCoverable(modelObjects.get(i).eClass())) {
				tcObjectCoverageStatus.set(i, TDLCoverageUtil.NOT_COVERABLE);
			}
		}
	}
	
	private EStructuralFeature getMatchedFeature(EObject rootElement, String featureName){
		EStructuralFeature matchedFeature = rootElement.eClass().getEAllStructuralFeatures().stream().
				filter(f -> f.getName().equals(featureName)).findFirst().get();
		return matchedFeature;
	}
	
	public void countNumOfElements() {
		numOfCoveredObjs = 0;
		numOfNotCoverableElements = 0;
		for (String coverage:this.tcObjectCoverageStatus) {
			if (coverage == TDLCoverageUtil.NOT_COVERABLE) {
				numOfNotCoverableElements++;
			}
			else if (coverage == TDLCoverageUtil.COVERED) {
				numOfCoveredObjs++;
			}
		}
	}
	
	public void calculateCoveragePercentage() {
		int numOfCoverableElements = tcObjectCoverageStatus.size() - numOfNotCoverableElements;
		tcCoveragePercentage = (double)(numOfCoveredObjs*100)/numOfCoverableElements;
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
		return MUTResource;
	}

	public void setMUTResource(Resource MUTResource) {
		this.MUTResource = MUTResource;
	}

	public TestDescription getTestCase() {
		return testCase;
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
