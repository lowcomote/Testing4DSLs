package org.imt.tdl.runner;

import java.util.List;

import org.etsi.mts.tdl.Message;
import org.etsi.mts.tdl.TestDescription;

public class Failure {
	private String testHeader;
	private String message;
	
	public void setMessage(List<Message> failedAssertions) {
		for (int i=0; i<failedAssertions.size(); i++) {
			Message assertion = failedAssertions.get(i);
			this.message = "The SUT did not send the expected output\n"+
					"The expected output is: " + assertion.getArgument().toString();
		}
	}
	public String getMessage() {
		return this.message;
	}
	public void setTestHeader(TestDescription testCase) {
		this.testHeader = "Failed test case: " + testCase.getName();
	}
	public String getTestHeader() {
		//a user-understandable label for the test
		return this.testHeader;
	}
}
