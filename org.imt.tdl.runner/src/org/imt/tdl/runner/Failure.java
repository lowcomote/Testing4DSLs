package org.imt.tdl.runner;

import org.etsi.mts.tdl.TestDescription;

public class Failure {
	private String testHeader;
	private String failedTestName;
	
	public void setTestHeader(TestDescription testCase) {
		failedTestName= testCase.getName();
		testHeader = "Failed test case: " + testCase.getName();
	}
	public String getTestHeader() {
		//a user-understandable label for the test
		return testHeader;
	}
	public String getFailedTestName() {
		return failedTestName;
	}
	@Override
    public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Failure)) {
			return false;
		}
		Failure f = (Failure) o;
		if (testHeader.equals(f.testHeader) && failedTestName.equals(f.failedTestName)) {
			return true;
		}
		return false;
	}
}
