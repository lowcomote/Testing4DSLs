package org.imt.tdl.coverage;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

public class TDLTestSuiteCoverage {

	private List<TDLTestCaseCoverage> tcCoverages = new ArrayList<>();
	
	private List<String> tsObjectCoverageStatus = new ArrayList<>();

	public List<TestCoverageInfo> coverageInfos = new ArrayList<>();
	private TestCoverageInfo overallResult = new TestCoverageInfo();
	
	//for every test case of the test suite, add its coverage to the list
	public void addTCCoverage(TDLTestCaseCoverage tcCoverage) {
		this.tcCoverages.add(tcCoverage);
	}
	public List<TDLTestCaseCoverage> getTCCoverages() {
		return tcCoverages;
	}
	
	//Calculating coverage of the test suite based on its test cases coverage
	public void calculateTSCoverage() {
		int numOfCoveredObjs = 0;
		for (TDLTestCaseCoverage tcCoverageObj : tcCoverages) {
			tcCoverageObj.calculateTCCoverage();
			
			this.overallResult.getCoverage().add(tcCoverageObj.getTcCoveragePercentage() + "");
			//if it is the first test case, copy the whole test case object coverage status for the test suite
			if (this.tsObjectCoverageStatus.size() == 0) {
				this.tsObjectCoverageStatus.addAll(tcCoverageObj.tcObjectCoverageStatus);
				numOfCoveredObjs = tcCoverageObj.numOfCoveredObjs;
			}else {
				for (int i=0; i<tcCoverageObj.tcObjectCoverageStatus.size(); i++) {
					String tcCoverage = tcCoverageObj.tcObjectCoverageStatus.get(i);
					if (tcCoverage == TDLCoverageUtil.COVERED & this.tsObjectCoverageStatus.get(i) != TDLCoverageUtil.COVERED) {
						this.tsObjectCoverageStatus.set(i, TDLCoverageUtil.COVERED);
						numOfCoveredObjs++;
					}
				}
			}
		}
		
		double tsCoveragePercentage = (numOfCoveredObjs*100)/TDLCoverageUtil.getInstance().getModelSize();
		this.overallResult.getCoverage().add(tsCoveragePercentage + "");
		this.setCoverageInfos();
		System.out.println("Test suite coverage: " + tsCoveragePercentage);
	}

	public void setCoverageInfos() {
		List<EObject> modelObjects = TDLCoverageUtil.getInstance().modelObjects;
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
