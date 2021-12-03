package org.imt.tdl.testResult;

import java.util.ArrayList;
import java.util.List;

public class TDLTestCaseResult {
	
	private String testCaseName;
	
	private String value;
	
	private String description;
	
	private List<TDLMessageResult> tdlMessageResults;
	
	private int messageNumber = 0;
	
	public TDLTestCaseResult() {
		this.value = TDLTestResultUtil.PASS;
		this.tdlMessageResults = new ArrayList<TDLMessageResult>();
	}
	
	public String getTestCaseName() {
		return testCaseName;
	}
	
	public void setTestCaseName(String name) {
		this.testCaseName = name;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return this.description;
	}
	
	public List<TDLMessageResult> getTdlMessages() {
		return this.tdlMessageResults;
	}
	public List<TDLMessageResult> getFailedTdlMessages() {
		List<TDLMessageResult> failedTdlMessages = new ArrayList<>();
		for (TDLMessageResult tdlMessage : this.tdlMessageResults) {
			if (tdlMessage.getFailure() == true) {
				failedTdlMessages.add(tdlMessage);
			}
		}
		return failedTdlMessages;
	}
	public int getNumOfPassedtdlMessages() {
		int passed = 0;
		for (TDLMessageResult tdlMessage : tdlMessageResults) {
			if (tdlMessage.getValue() == TDLTestResultUtil.PASS) {
				passed++;
			}
		}
		return passed;
	}
	
	public int getNumOfFailures() {
		int failures = 0;
		for (TDLMessageResult tdlMessage : tdlMessageResults) {
			if (tdlMessage.getFailure() == true) {
				failures++;
			}
		}
		return failures;
	}

	public void addTdlMessage(TDLMessageResult messageResult) {
		this.messageNumber++;
		messageResult.setTdlMessageId("Message#" + this.messageNumber);
		this.tdlMessageResults.add(messageResult);
	}
	
}
