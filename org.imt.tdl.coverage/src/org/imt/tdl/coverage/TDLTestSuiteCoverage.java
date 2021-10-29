package org.imt.tdl.coverage;

import java.util.ArrayList;
import java.util.List;

public class TDLTestSuiteCoverage {

	private List<TDLTestCaseCoverage> tcCoverages = new ArrayList<>();
	
	//for every test case of the test suite, add its coverage to the list
	public void addTCCoverage(TDLTestCaseCoverage tcCoverage) {
		this.tcCoverages.add(tcCoverage);
	}
	public List<TDLTestCaseCoverage> getTCCoverages() {
		return tcCoverages;
	}
	
	//Calculating coverage of the test suite based on its test cases coverage
	public void calculateTSCoverage() {
		calculateTCCoverage();
	}
	
	private void calculateTCCoverage() {
		for (TDLTestCaseCoverage tcCoverage : tcCoverages) {
			tcCoverage.calculateTCCoverage();
		}
	}
}
