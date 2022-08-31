package org.imt.tdl.testResult;

import java.util.ArrayList;
import java.util.List;

import org.etsi.mts.tdl.Package;

public class TDLTestSuiteResult {
	
	private Package testSuite;
	private List<TDLTestCaseResult> testCaseResults;
	
	public TDLTestSuiteResult() {
		testCaseResults = new ArrayList<>();
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
	public void addResult(TDLTestCaseResult result) {
		testCaseResults.add(result);
	}
	public List<TDLTestCaseResult> getTestCaseResults() {
		return testCaseResults;
	}
	
	public int getNumOfPassedTestCases() {
		return (int) testCaseResults.stream()
				.filter(r -> r.getValue() == TDLTestResultUtil.PASS).count();
	}
	
	public int getNumOfFailedTestCases() {
		return (int) testCaseResults.stream()
				.filter(r -> r.getValue() == TDLTestResultUtil.FAIL).count();
	}
	
	public int getNumOfInconclusiveTestCases() {
		return (int) testCaseResults.stream()
				.filter(r -> r.getValue() == TDLTestResultUtil.INCONCLUSIVE).count();
	}
	
	public String getTestSuiteResultValue() {
		return getNumOfPassedTestCases() == testCaseResults.size() ?
			TDLTestResultUtil.PASS : TDLTestResultUtil.FAIL;
	}
}
