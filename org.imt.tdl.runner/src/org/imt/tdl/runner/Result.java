package org.imt.tdl.runner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.etsi.mts.tdl.Message;
import org.etsi.mts.tdl.TestDescription;

public class Result {
	private int numExecutedTests = 0;
	private int numFailedTests = 0;
	private Map<String, Boolean> tests;
	private List<Failure> failures;
	
	public void addTest(String testCase, boolean verdict) {
		this.tests.put(testCase, verdict);
	}
	public Map<String, Boolean> getTests(){
		return this.tests;
	}
	public void addFailure(TestDescription testCase, List<Message> failedAssertions) {
		Failure failure = new Failure();
		failure.setTestHeader(testCase);
		failure.setMessage(failedAssertions);
	}
	public List<Failure> getFailures(){
		//the Failures describing tests that failed and the problems they encountered
		return failures;
	}
	protected void addNumExecutedTests() {
		this.numExecutedTests++;
	}
	protected void addNumFailedTests() {
		this.numFailedTests++;
	}
	public int getRunCount() {
		return this.numExecutedTests;
	}
	public int getFailureCount() {
		//the number of tests that failed during the run
		return this.numFailedTests;
	}
	public boolean wasSuccessful() {
		if (this.numFailedTests==0) {
			return true;
		}
		return false;
	}
}
