package org.imt.tdl.coverage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.etsi.mts.tdl.Package;

public class TDLTestSuiteCoverage {

	private Package testSuite;
	private List<TDLTestCaseCoverage> tcCoverages;
	
	private List<EObject> modelObjects;
	private List<String> tsObjectCoverageStatus;

	double tsCoveragePercentage;
	int numOfCoveredObjs;
	int numOfNotCoverableElements;
	
	public List<TestCoverageInfo> coverageInfos;
	private TestCoverageInfo overallResult;
	
	public TDLTestSuiteCoverage() {
		this.tcCoverages = new ArrayList<>();
		this.modelObjects = new ArrayList<>();
		this.tsObjectCoverageStatus = new ArrayList<>();
		this.tsCoveragePercentage = 0;
		this.numOfCoveredObjs= 0 ;
		this.numOfNotCoverableElements = 0;
		this.coverageInfos = new ArrayList<>();
		this.overallResult = new TestCoverageInfo();
	}
	//for every test case of the test suite, add its coverage to the list
	public void addTCCoverage(TDLTestCaseCoverage tcCoverage) {
		this.tcCoverages.add(tcCoverage);
	}
	public List<TDLTestCaseCoverage> getTCCoverages() {
		return this.tcCoverages;
	}
	
	//Calculating coverage of the test suite based on its test cases coverage
	public void calculateTSCoverage() {
		DSLSpecificCoverageHandler dslSpecificCoverageHandler = new DSLSpecificCoverageHandler();
		IDSLSpecificCoverage dslSpecificCoverage = dslSpecificCoverageHandler.getDSLSpecificCoverage();
		if (dslSpecificCoverage != null) {
			TDLCoverageUtil.getInstance().updateCoverableClasses(dslSpecificCoverage.getNewCoverableClasses());
		}
		System.out.println("Number of Coverable Classes: " + TDLCoverageUtil.getInstance().coverableClasses.size());
		//foreach test case, first calculate coverage using the generic tool
		//then, if the DSL provides a dsl-specific coverage tool, specialize the coverage based on it
		for (TDLTestCaseCoverage tcCoverageObj : this.tcCoverages) {
			if (dslSpecificCoverage != null) {
				dslSpecificCoverage.ignoreModelObjects(tcCoverageObj.getMUTResource());
				tcCoverageObj.calculateTCCoverage();
				dslSpecificCoverage.specializeCoverage(tcCoverageObj);
			}
			else {
				tcCoverageObj.calculateTCCoverage();
			}
			tcCoverageObj.countNumOfElements();
			this.overallResult.getCoverage().add(tcCoverageObj.getCoveragePercentage() + "");
			
			//if it is the first test case, copy the whole test case object coverage status for the test suite
			if (this.tsObjectCoverageStatus.size() == 0) {
				this.modelObjects.addAll(tcCoverageObj.getModelObjects());
				this.tsObjectCoverageStatus.addAll(tcCoverageObj.getTcObjectCoverageStatus());
			}else {
				for (int i=0; i<tcCoverageObj.getTcObjectCoverageStatus().size(); i++) {
					String tcCoverage = tcCoverageObj.getTcObjectCoverageStatus().get(i);
					if (tcCoverage == TDLCoverageUtil.COVERED & this.tsObjectCoverageStatus.get(i) != TDLCoverageUtil.COVERED) {
						this.tsObjectCoverageStatus.set(i, TDLCoverageUtil.COVERED);
					}
				}
			}
		}
		countNumOfElements();
		System.out.println("\n" + "Model size (n. of EObjects): " + this.modelObjects.size() + "\n");
		calculateCoveragePercentage();
	}
	
	private void countNumOfElements() {
		this.numOfCoveredObjs = 0;
		this.numOfNotCoverableElements = 0;
		for (String coverage:this.tsObjectCoverageStatus) {
			if (coverage == TDLCoverageUtil.NOT_COVERABLE) {
				this.numOfNotCoverableElements++;
			}
			else if (coverage == TDLCoverageUtil.COVERED) {
				this.numOfCoveredObjs++;
			}
		}
	}
	
	public void calculateCoveragePercentage() {
		int numOfCoverableElements = this.tsObjectCoverageStatus.size() - this.numOfNotCoverableElements;
		System.out.println("number of coveredObjects: " + this.numOfCoveredObjs);
		System.out.println("number of coverableElements: " + numOfCoverableElements);
		double tsCoveragePercentage = (double)(this.numOfCoveredObjs*100)/numOfCoverableElements;
		BigDecimal bd = new BigDecimal(tsCoveragePercentage).setScale(2, RoundingMode.HALF_UP);
		tsCoveragePercentage = bd.doubleValue();
		System.out.println("Test suite coverage: " + tsCoveragePercentage);
		this.overallResult.getCoverage().add(tsCoveragePercentage + "");
		this.setCoverageInfos();
	}

	public void setCoverageInfos() {
		List<EObject> modelObjects = this.modelObjects;
		//for each model object, the coverage information must be set
		for (int i=0; i<modelObjects.size(); i++) {
			TestCoverageInfo cInfo = new TestCoverageInfo();
			cInfo.setModelObject(modelObjects.get(i));
			cInfo.setMetaclass(modelObjects.get(i).eClass());
			for (TDLTestCaseCoverage tcCoverageObj : this.tcCoverages) {
				String tcCoverage = tcCoverageObj.getTcObjectCoverageStatus().get(i);
				if (tcCoverage == TDLCoverageUtil.COVERABLE) {
					tcCoverage = TDLCoverageUtil.NOT_COVERED;
				}
				cInfo.getCoverage().add(tcCoverage);
			}
			String tsCoverage = this.tsObjectCoverageStatus.get(i);
			if (tsCoverage == TDLCoverageUtil.COVERABLE) {
				tsCoverage = TDLCoverageUtil.NOT_COVERED;
			}
			cInfo.getCoverage().add(tsCoverage);
			this.coverageInfos.add(cInfo);
		}
		//add the overall result as the last row of the info array
		this.overallResult.setMetaclass(null);
		this.overallResult.setModelObject(null);
		this.coverageInfos.add(this.overallResult);
	}
	
	public List<TestCoverageInfo> getCoverageInfos(){
		return this.coverageInfos;
	}
	public Package getTestSuite() {
		return testSuite;
	}
	public void setTestSuite(Package testSuite) {
		this.testSuite = testSuite;
	}
	public String getTestSuiteName() {
		return testSuite.getName();
	}
	public List<EObject> getModelObjects() {
		return modelObjects;
	}
	public List<EObject> getModelObjectsWithoutRuntimeState() {
		for (EObject eobject: this.modelObjects) {
			EClass eobjectType = eobject.eClass();
			Optional<EClass> eclass = TDLCoverageUtil.getInstance().getDynamicClasses().stream().
					filter(c -> c.getName().equals(eobjectType.getName())).findFirst();
			if (eclass.isPresent()) {
				eobjectType.getEAllStructuralFeatures().forEach(f -> clearRuntimeDataOfFeature(eobject, f));
			}
			else {
				eclass = TDLCoverageUtil.getInstance().getClassesWithDynamicFeatures().stream().
						filter(c -> c.getName().equals(eobjectType.getName())).findFirst();
				if (eclass.isPresent()) {
					List<EStructuralFeature> dynamicFeatures = eobjectType.getEAllStructuralFeatures().stream().
							filter(f -> isDynamicFeature(f)).collect(Collectors.toList());
					dynamicFeatures.forEach(f -> clearRuntimeDataOfFeature(eobject, f));
				}
			}
		}
		return this.modelObjects;
	}
	
	private boolean isDynamicFeature(EStructuralFeature feature) {
		List<EAnnotation> featureDynamicAnnotations = feature.getEAnnotations().stream().
				filter(a -> a.getSource().equals("dynamic") || a.getSource().equals("aspect")).collect(Collectors.toList());
		return (featureDynamicAnnotations != null && featureDynamicAnnotations.size() > 0);
	}
	
	private void clearRuntimeDataOfFeature(EObject object, EStructuralFeature feature) {
		if (feature.eResource().getResourceSet() == null) {
			object.eSet(feature, feature.getDefaultValue());
		}else {
			TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(feature);
			try{
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						object.eSet(feature, feature.getDefaultValue());
					}
		   		});
	   		}catch(IllegalArgumentException e){
				e.printStackTrace();
			}
		}
	}
}
