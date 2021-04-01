package org.imt.tdl.testResult;

import java.util.HashMap;

import org.etsi.mts.tdl.DataUse;

public class TDLMessageResult {
	
	private String tdlMessageName;
	
	private boolean value;
	
	private String message;
	//<expectedData, receivedDta>
	private HashMap<DataUse, DataUse> oracle;
	
	private boolean failure;
	
	public String getTdlMessageName() {
		if (this.tdlMessageName == null) {
			return "NULL";
		}
		return this.tdlMessageName;
	}
	
	public void setTdlMessageName(String name) {
		this.tdlMessageName = name;
	}
	
	public boolean getValue() {
		return value;
	}
	
	public void setValue(boolean value) {
		this.value = value;
	}
	
	public String getMessage() {
		if (this.message == null) {
			return "NULL";
		}
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public HashMap<DataUse, DataUse> getOracle() {
		return this.oracle;
	}
	
	public void setOracle(HashMap<DataUse, DataUse> oracle) {
		this.oracle = oracle;
	}
	
	public boolean getFailure() {
		return failure;
	}
	
	public void setFailure(boolean failure) {
		this.failure = failure;
	}
}
