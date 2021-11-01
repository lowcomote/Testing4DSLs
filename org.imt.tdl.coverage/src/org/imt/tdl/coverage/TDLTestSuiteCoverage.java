package org.imt.tdl.coverage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

public class TDLTestSuiteCoverage {

	private List<TDLTestCaseCoverage> tcCoverages = new ArrayList<>();
	
	public HashMap<EObject, String> tsObjectCoverageStatus = TDLCoverageUtil.getInstance().objectCoverageStatus;
	private double tsCoveragePercentage;
	
	//for every test case of the test suite, add its coverage to the list
	public void addTCCoverage(TDLTestCaseCoverage tcCoverage) {
		this.tcCoverages.add(tcCoverage);
	}
	public List<TDLTestCaseCoverage> getTCCoverages() {
		return tcCoverages;
	}
	
	//Calculating coverage of the test suite based on its test cases coverage
	public void calculateTSCoverage() {
		for (TDLTestCaseCoverage tcCoverage : tcCoverages) {
			//we send the tsObjectCoverageStatus to calculate the test suite coverage while calculating the test cases coverages
			tcCoverage.calculateTCCoverage(tsObjectCoverageStatus);
		}
		
		int numOfCoveredObjs = 0;
		Iterator<String> statusValues = tsObjectCoverageStatus.values().iterator();
		while (statusValues.hasNext()) {
			if (statusValues.next() == TDLCoverageUtil.COVERED) {
				numOfCoveredObjs++;
			}
		}

		this.tsCoveragePercentage = (numOfCoveredObjs*100)/TDLCoverageUtil.getInstance().getModelSize();
	}
	
	public double getTsCoveragePercentage() {
		return tsCoveragePercentage;
	}
	public void setTsCoveragePercentage(double coveragePercentage) {
		this.tsCoveragePercentage = coveragePercentage;
	}
}
