package org.imt.tdl.testResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.etsi.mts.tdl.DataInstanceUse;
import org.etsi.mts.tdl.DataUse;

public class TDLTestCaseResult {
	
	private String testCaseName;
	
	private String value;
	
	private List<TDLMessageResult> tdlMessages;
	
	private int messageNumber = 0;
	
	public TDLTestCaseResult() {
		this.value = "PASS";
		this.tdlMessages = new ArrayList<TDLMessageResult>();
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
	
	public List<TDLMessageResult> getTdlMessages() {
		return this.tdlMessages;
	}
	public List<TDLMessageResult> getFailedTdlMessages() {
		List<TDLMessageResult> failedTdlMessages = new ArrayList<>();
		for (TDLMessageResult tdlMessage : this.tdlMessages) {
			if (tdlMessage.getFailure() == true) {
				failedTdlMessages.add(tdlMessage);
			}
		}
		return failedTdlMessages;
	}
	public int getNumOfPassedtdlMessages() {
		int passed = 0;
		for (TDLMessageResult tdlMessage : tdlMessages) {
			if (tdlMessage.getValue() == true) {
				passed++;
			}
		}
		return passed;
	}
	
	public int getNumOfFailures() {
		int failures = 0;
		for (TDLMessageResult tdlMessage : tdlMessages) {
			if (tdlMessage.getFailure() == true) {
				failures++;
			}
		}
		return failures;
	}

	public void addTdlMessage(String tdlMessageName, boolean value, boolean error, String message, HashMap<DataUse, DataUse> oracle) {
		TDLMessageResult testInfo = new TDLMessageResult();
		if (tdlMessageName == null) {
			this.messageNumber++;
			tdlMessageName = "Message#" + this.messageNumber;
		}
		testInfo.setTdlMessageName(tdlMessageName);
		testInfo.setValue(value);
		testInfo.setMessage(message);
		testInfo.setOracle(oracle);
		testInfo.setFailure(error);
		this.tdlMessages.add(testInfo);
	}
	
}
