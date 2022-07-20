package org.imt.tdl.coverage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gemoc.trace.commons.model.trace.SequentialStep;
import org.eclipse.gemoc.trace.commons.model.trace.Step;
import org.eclipse.gemoc.trace.commons.model.trace.Trace;
import org.etsi.mts.tdl.TestDescription;
import org.imt.tdl.coverage.dslSpecific.DSLSpecificCoverageExecutor;

import DSLSpecificCoverage.ContainerCoverageInference;
import DSLSpecificCoverage.Context;
import DSLSpecificCoverage.CoverageIgnorance;
import DSLSpecificCoverage.CoveredContainee;
import DSLSpecificCoverage.ReferenceCoverageInference;
import DSLSpecificCoverage.Rule;

public class TDLTestCaseCoverage {

	private TestDescription testCase;
	private Resource MUTResource;
	private Trace<?, ?, ?> trace;
	
	private Map<Context, List<EObject>> coverageContext_eobjects= new HashMap<>();

	private List<EObject> modelObjects;
	private List<String> tcObjectCoverageStatus;
	private List<String> tcObjectCoverageStatusCopy;
	
	double tcCoveragePercentage;
	int numOfCoveredObjs;
	int numOfNotCoverableElements;
	
	public TDLTestCaseCoverage() {
		modelObjects = new ArrayList<>();
		tcObjectCoverageStatus = new ArrayList<>();
		tcObjectCoverageStatusCopy = new ArrayList<>();
		numOfCoveredObjs= 0 ;
		numOfNotCoverableElements = 0;
	}
	
	//calculating the coverage of the test case based on the model execution trace and dsl-specific coverage rules
	public void calculateTCCoverage () {
		//if there is a dsl-specific coverage, initialize the map
		if (TDLCoverageUtil.getInstance().getDslSpecificCoverage() != null) {
			TDLCoverageUtil.getInstance().getDslSpecificCoverage().getMetaclasses().
				forEach(c -> coverageContext_eobjects.put(c, new ArrayList<>()));
		}
		analyzeEObjects();
		Step<?> rootStep = trace.getRootStep();
		calculateObjectCoverage(rootStep);
		if (!coverageContext_eobjects.isEmpty()) {
			runDSLSpecificCoverage();
		}
		changeCoverable2notCovered();
	}

	private void analyzeEObjects() {
		//find coverable objects using the MUTResource of the test case
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
	
	private void runDSLSpecificCoverage() {
		//find the model objects that are of type of the contexts
		
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
//		coverageContext_eobjects.entrySet().forEach(entry -> 
//			entry.getValue().addAll(modelObjects.stream().
//				filter(object -> entry.getKey().getMetaclass().isSuperTypeOf(object.eClass())).
//				collect(Collectors.toList())));
		//apply domain specific coverage while the coverage matrix changes
		while (isCoverageMatrixChanged()) {
			coverageContext_eobjects.entrySet().stream().filter(entry -> !entry.getValue().isEmpty()).
				forEach(entry -> entry.getValue().
					forEach(o -> applyCoverageRules(entry.getKey().getRules(), o)));
		}
	}

	//apply all the rules on the object (NOTE: rule's context = object type)
	private void applyCoverageRules(EList<Rule> rules, EObject object) {
		for (Rule rule:rules) {
			if (rule instanceof ReferenceCoverageInference) {
				inferReferenceCoverage((ReferenceCoverageInference) rule, object);
			}
			else if (rule instanceof ContainerCoverageInference) {
				inferContainerCoverage((ContainerCoverageInference) rule, object);
			}
		}
	}
	
	private void inferReferenceCoverage(ReferenceCoverageInference r, EObject object) {
		Object referencedObject = object.eGet(r.getAssociationReference());
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

	private void inferContainerCoverage(ContainerCoverageInference r, EObject object) {
		//check if the object container is the one expected by the rule
		if (object.eContainer().eClass().getName() == r.getContainerMetaclass().getName()) {
			Object containedObject = object.eContainer().eGet(r.getContainementReference());
			if (containedObject != null) {
				if (containedObject instanceof EObject) {
					EObject containee = (EObject) containedObject;
					setReferencedObjectCoverage(containee.eContainer(), getObjectCoverage(containee));
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
		}
	}
	
	private String getObjectCoverage(EObject object) {
		int objectIndex = modelObjects.indexOf(object);
		if (objectIndex != -1){
			return tcObjectCoverageStatus.get(objectIndex);
		}
		return null;
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
	
	private boolean isCoverageMatrixChanged() {
        List<String> newCopy = new ArrayList<>(tcObjectCoverageStatus);
        boolean result = !Objects.equals(tcObjectCoverageStatusCopy, newCopy);
        tcObjectCoverageStatusCopy = newCopy;
        return result;
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
