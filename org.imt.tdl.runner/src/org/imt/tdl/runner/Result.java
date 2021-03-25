package org.imt.tdl.runner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.etsi.mts.tdl.Message;
import org.etsi.mts.tdl.TestDescription;

public class Result {
	private int numExecutedTests = 0;
	private int numFailedTests = 0;
	private Map<String, Boolean> tests = new HashMap<>();
	private List<Failure> failures = new ArrayList<>();
	
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
		this.failures.add(failure);
	}
	public List<Failure> getFailures(){
		//the Failures describing tests that failed and the problems they encountered
		return this.failures;
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
	@Override
    public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Result)) {
			return false;
		}
		Result r = (Result) o;
		if (this.numExecutedTests == r.numExecutedTests && this.numFailedTests == this.numFailedTests &&
				this.tests.equals(r.tests) && this.failures.equals(r.failures)) {
			return true;
		}
		return false;
	}
}
