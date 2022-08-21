package org.imt.tdl.testResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.etsi.mts.tdl.TestDescription;

public class TDLTestCaseResult {
	
	private TestDescription testCase;

	private String value;
	
	private String description;
	
	private List<TDLMessageResult> tdlMessageResults;
	
	private int messageNumber = 0;
	
	public TDLTestCaseResult() {
		value = TDLTestResultUtil.PASS;
		tdlMessageResults = new ArrayList<TDLMessageResult>();
	}
	
	public TestDescription getTestCase() {
		return testCase;
	}

	public void setTestCase(TestDescription testCase) {
		this.testCase = testCase;
	}
	
	public String getTestCaseName() {
		return testCase.getName();
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public List<TDLMessageResult> getTdlMessages() {
		return tdlMessageResults;
	}
	
	public List<TDLMessageResult> getFailedTdlMessages() {
		return tdlMessageResults.stream().filter(r -> r.getFailure()).collect(Collectors.toList());
	}
	
	public int getNumOfPassedtdlMessages() {
		return (int) tdlMessageResults.stream()
				.filter(r -> r.getValue() == TDLTestResultUtil.PASS).count();
	}
	
	public int getNumOfFailures() {
		return (int) tdlMessageResults.stream().filter(r -> r.getFailure()).count();
	}

	public void addTdlMessage(TDLMessageResult messageResult) {
		messageResult.setTdlMessageId("Message#" + (++messageNumber));
		tdlMessageResults.add(messageResult);
	}
}
