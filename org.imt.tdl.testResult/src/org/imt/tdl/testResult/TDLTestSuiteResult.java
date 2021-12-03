package org.imt.tdl.testResult;

import java.util.ArrayList;
import java.util.List;

public class TDLTestSuiteResult {
	
	private String testSuiteName;
	
	private List<TDLTestCaseResult> testCaseResults = new ArrayList<>();
	
	public String getTestSuiteName() {
		return testSuiteName;
	}
	public void setTestSuiteName(String name) {
		this.testSuiteName = name;
	}
	public void addResult(TDLTestCaseResult result) {
		this.testCaseResults.add(result);
	}
	public List<TDLTestCaseResult> getTestCaseResults() {
		return testCaseResults;
	}
	
	public int getNumOfPassedTestCases() {
		int numPassedTests = 0;
		for (TDLTestCaseResult result : testCaseResults) {
			if (result.getValue().equals("PASS")) {
				numPassedTests++;
			}
		}
		return numPassedTests;
	}
	
	public int getNumOfFailedTestCases() {
		int numFailedTests = 0;
		for (TDLTestCaseResult result : testCaseResults) {
			if (result.getValue().equals("FAIL")) {
				numFailedTests++;
			}
		}
		return numFailedTests;
	}
	
	public int getNumOfInconclusiveTestCases() {
		int num = 0;
		for (TDLTestCaseResult result : testCaseResults) {
			if (result.getValue().equals("INCONCLUSIVE")) {
				num++;
			}
		}
		return num;
	}
}
