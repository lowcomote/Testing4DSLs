package org.imt.tdl.runner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.imt.tdl.testResult.TDLTestCaseResult;

public class Result {
	
	private int numExecutedTests = 0;
	private int numFailedTests = 0;
	private Map<String, Boolean> tests_verdicts = new HashMap<>();
	private List<Failure> failures = new ArrayList<>();
	
	public void addTest(String testCase, boolean verdict) {
		tests_verdicts.put(testCase, verdict);
		numExecutedTests++;
	}
	
	public Map<String, Boolean> getTests_verdicts(){
		return tests_verdicts;
	}
	
	public void addFailure(TDLTestCaseResult testCaseVerdict) {
		Failure failure = new Failure();
		failure.setTestHeader(testCaseVerdict.getTestCase());
		failure.setMessage(testCaseVerdict.getDescription());
		failures.add(failure);
		numFailedTests++;
	}
	
	public List<Failure> getFailures(){
		//the Failures describing tests that failed and the problems they encountered
		return failures;
	}
	
	public int getRunCount() {
		return numExecutedTests;
	}
	public int getFailureCount() {
		//the number of tests that failed during the run
		return numFailedTests;
	}
	
	public boolean wasSuccessful() {
		if (numFailedTests==0) {
			return true;
		}
		return false;
	}
	
	@Override
    public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Result)) {
			return false;
		}
		Result r = (Result) o;
		if (numExecutedTests == r.numExecutedTests && numFailedTests == r.numFailedTests &&
				tests_verdicts.equals(r.tests_verdicts) && failures.equals(r.failures)) {
			return true;
		}
		return false;
	}
}
