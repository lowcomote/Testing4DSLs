package org.imt.tdl.testResult;

import java.util.HashMap;


import org.etsi.mts.tdl.DataUse;

public class TDLMessageResult {
	
	private String tdlMessageId;
	
	private String value;
	
	private String description;
	//<expectedData, receivedDta>
	private HashMap<DataUse, DataUse> oracle;
	
	private boolean failure;
	
	public TDLMessageResult(String tdlMessageId, String value, String description, HashMap<DataUse, DataUse> oracle) {
		this.tdlMessageId = tdlMessageId;
		this.value = value;
		if (value == TDLTestResultUtil.INCONCLUSIVE) {
			this.failure = true;
		}
		this.description = description;
		this.oracle = oracle;
	}
	public String getTdlMessageId() {
		if (this.tdlMessageId == null) {
			return "NULL";
		}
		return this.tdlMessageId;
	}
	
	public void setTdlMessageId(String name) {
		this.tdlMessageId = name;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getDescription() {
		if (this.description == null) {
			return "NULL";
		}
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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
