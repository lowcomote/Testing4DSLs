package org.imt.tdl.runner;

import org.etsi.mts.tdl.TestDescription;

public class Failure {
	private String testHeader;
	private String failedTestName;
	
	public void setTestHeader(TestDescription testCase) {
		this.failedTestName= testCase.getName();
		this.testHeader = "Failed test case: " + testCase.getName();
	}
	public String getTestHeader() {
		//a user-understandable label for the test
		return this.testHeader;
	}
	public String getFailedTestName() {
		return this.failedTestName;
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
		if (this.testHeader.equals(f.testHeader) && this.failedTestName.equals(f.failedTestName)) {
			return true;
		}
		return false;
	}
}
