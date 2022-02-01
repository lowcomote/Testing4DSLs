package org.imt.tdl.coverage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.etsi.mts.tdl.Package;
import org.imt.tdl.coverage.dslSpecific.DSLSpecificCoverageHandler;
import org.imt.tdl.coverage.dslSpecific.IDSLSpecificCoverage;

public class TDLTestSuiteCoverage {

	private Package testSuite;
	private List<TDLTestCaseCoverage> tcCoverages;

	private List<EObject> modelObjects;
	private List<String> tsObjectCoverageStatus;

	double tsCoveragePercentage;
	int numOfCoveredObjs;
	int numOfNotCoverableElements;
	
	public List<ObjectCoverageStatus> coverageOfModelObjects;
	private ObjectCoverageStatus overallResult;
	
	public TDLTestSuiteCoverage() {
		this.tcCoverages = new ArrayList<>();
		this.modelObjects = new ArrayList<>();
		this.tsObjectCoverageStatus = new ArrayList<>();
		this.tsCoveragePercentage = 0;
		this.numOfCoveredObjs= 0 ;
		this.numOfNotCoverableElements = 0;
		this.coverageOfModelObjects = new ArrayList<>();
		this.overallResult = new ObjectCoverageStatus();
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
		//System.out.println("Number of Coverable Classes: " + TDLCoverageUtil.getInstance().coverableClasses.size());
		//for each test case, first calculate coverage using the generic tool
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
			tcCoverageObj.calculateCoveragePercentage();
			this.overallResult.getCoverage().add(tcCoverageObj.getTcCoveragePercentage() + "");
			
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
		if (this.tsObjectCoverageStatus.size() != 0) {
			countNumOfElements();
			//System.out.println("\n" + "Model size (n. of EObjects): " + this.modelObjects.size() + "\n");
			calculateCoveragePercentage();
		}
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
		//System.out.println("number of coveredObjects: " + this.numOfCoveredObjs);
		//System.out.println("number of coverableElements: " + numOfCoverableElements);
		tsCoveragePercentage = (double)(this.numOfCoveredObjs*100)/numOfCoverableElements;
		BigDecimal bd = new BigDecimal(tsCoveragePercentage).setScale(2, RoundingMode.HALF_UP);
		tsCoveragePercentage = bd.doubleValue();
		//System.out.println("Test suite coverage: " + tsCoveragePercentage);
		this.overallResult.getCoverage().add(tsCoveragePercentage + "");
		this.setCoverageInfos();
	}

	public void setCoverageInfos() {
		List<EObject> modelObjects = this.modelObjects;
		//for each model object, the coverage information must be set
		for (int i=0; i<modelObjects.size(); i++) {
			ObjectCoverageStatus objectCoverage = new ObjectCoverageStatus();
			objectCoverage.setModelObject(modelObjects.get(i));
			objectCoverage.setMetaclass(modelObjects.get(i).eClass());
			for (TDLTestCaseCoverage tcCoverageObj : this.tcCoverages) {
				String tcCoverage = tcCoverageObj.getTcObjectCoverageStatus().get(i);
				if (tcCoverage == TDLCoverageUtil.COVERABLE) {
					tcCoverage = TDLCoverageUtil.NOT_COVERED;
				}
				objectCoverage.getCoverage().add(tcCoverage);
			}
			String tsCoverage = this.tsObjectCoverageStatus.get(i);
			if (tsCoverage == TDLCoverageUtil.COVERABLE) {
				tsCoverage = TDLCoverageUtil.NOT_COVERED;
			}
			objectCoverage.getCoverage().add(tsCoverage);
			this.coverageOfModelObjects.add(objectCoverage);
		}
		//add the overall result as the last row of the info array
		this.overallResult.setMetaclass(null);
		this.overallResult.setModelObject(null);
		this.coverageOfModelObjects.add(this.overallResult);
	}
	
	public List<ObjectCoverageStatus> getCoverageOfModelObjects(){
		return this.coverageOfModelObjects;
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
	public List<TDLTestCaseCoverage> getTcCoverages() {
		return tcCoverages;
	}
	public double getTsCoveragePercentage() {
		return tsCoveragePercentage;
	}
	
	public List<String> getTsObjectCoverageStatus() {
		return tsObjectCoverageStatus;
	}
}