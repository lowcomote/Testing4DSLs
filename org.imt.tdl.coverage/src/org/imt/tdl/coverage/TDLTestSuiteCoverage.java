package org.imt.tdl.coverage;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

public class TDLTestSuiteCoverage {

	private List<TDLTestCaseCoverage> tcCoverages = new ArrayList<>();
	
	public List<EObject> modelObjects = new ArrayList<>();
	private List<String> tsObjectCoverageStatus = new ArrayList<>();

	public List<TestCoverageInfo> coverageInfos = new ArrayList<>();
	private TestCoverageInfo overallResult = new TestCoverageInfo();
	double tsCoveragePercentage;
	
	//for every test case of the test suite, add its coverage to the list
	public void addTCCoverage(TDLTestCaseCoverage tcCoverage) {
		this.tcCoverages.add(tcCoverage);
	}
	public List<TDLTestCaseCoverage> getTCCoverages() {
		return tcCoverages;
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
		for (TDLTestCaseCoverage tcCoverageObj : tcCoverages) {
			if (dslSpecificCoverage != null) {
				dslSpecificCoverage.ignoreModelObjects(tcCoverageObj.getMUTResource());
				tcCoverageObj.calculateTCCoverage();
				dslSpecificCoverage.specializeCoverage(tcCoverageObj);
			}
			else {
				tcCoverageObj.calculateTCCoverage();
			}
			tcCoverageObj.countNumOfElements();
			double tcCoveragePercentage = tcCoverageObj.getCoveragePercentage();
			this.overallResult.getCoverage().add(tcCoveragePercentage + "");
			//if it is the first test case, copy the whole test case object coverage status for the test suite
			if (this.tsObjectCoverageStatus.size() == 0) {
				this.modelObjects.addAll(tcCoverageObj.modelObjects);
				this.tsObjectCoverageStatus.addAll(tcCoverageObj.tcObjectCoverageStatus);
			}else {
				for (int i=0; i<tcCoverageObj.tcObjectCoverageStatus.size(); i++) {
					String tcCoverage = tcCoverageObj.tcObjectCoverageStatus.get(i);
					if (tcCoverage == TDLCoverageUtil.COVERED & this.tsObjectCoverageStatus.get(i) != TDLCoverageUtil.COVERED) {
						this.tsObjectCoverageStatus.set(i, TDLCoverageUtil.COVERED);
					}
				}
			}
		}
		countNumOfElements();
		calculateCoveragePercentage();
	}
	
	int numOfCoveredObjs;
	int numOfNotCoverableElements;
	
	public void countNumOfElements() {
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
	public int getNumOfCoverableElements() {
		//this returns the size of the model in terms of its coverable elements
		return this.tsObjectCoverageStatus.size() - this.numOfNotCoverableElements;
	}
	
	public int getNumOfCoveredObjs() {
		return this.numOfCoveredObjs;
	}
	
	public void calculateCoveragePercentage() {
		double tsCoveragePercentage = Math.ceil((double)(getNumOfCoveredObjs()*100)/getNumOfCoverableElements());
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
				String tcCoverage = tcCoverageObj.tcObjectCoverageStatus.get(i);
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
}
