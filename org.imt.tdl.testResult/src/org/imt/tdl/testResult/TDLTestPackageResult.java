package org.imt.tdl.testResult;

import java.util.ArrayList;
import java.util.List;

public class TDLTestPackageResult {
	
	private String testPackageName;
	
	private List<TDLTestCaseResult> results = new ArrayList<>();
	
	public String getTestPackageName() {
		return testPackageName;
	}
	public void setTestPackageName(String name) {
		this.testPackageName = name;
	}
	public void addResult(TDLTestCaseResult result) {
		this.results.add(result);
	}
	public List<TDLTestCaseResult> getResults() {
		return results;
	}
	
	public int getNumOfPassedTestCases() {
		int numPassedTests = 0;
		for (TDLTestCaseResult result : results) {
			if (result.getValue().equals("PASS")) {
				numPassedTests++;
			}
		}
		return numPassedTests;
	}
	
	public int getNumOfFailedTestCases() {
		int numFailedTests = 0;
		for (TDLTestCaseResult result : results) {
			if (result.getValue().equals("FAIL")) {
				numFailedTests++;
			}
		}
		return numFailedTests;
	}
	
	public int getNumOfInconclusiveTestCases() {
		int num = 0;
		for (TDLTestCaseResult result : results) {
			if (result.getValue().equals("INCONCLUSIVE")) {
				num++;
			}
		}
		return num;
	}
}
