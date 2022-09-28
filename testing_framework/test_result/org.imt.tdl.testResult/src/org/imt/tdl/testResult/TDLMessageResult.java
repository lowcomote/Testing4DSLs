package org.imt.tdl.testResult;

import java.util.HashMap;


import org.etsi.mts.tdl.DataUse;
import org.etsi.mts.tdl.Message;

public class TDLMessageResult {
	
	private Message message;
	
	private String tdlMessageId;
	
	private String value;
	
	private String description;
	//<expectedData, receivedDta>
	private HashMap<DataUse, DataUse> oracle;
	
	private boolean failure;
	
	public TDLMessageResult(Message message, String value, String description, HashMap<DataUse, DataUse> oracle) {
		this.message = message;
		this.value = value;
		if (value == TDLTestResultUtil.INCONCLUSIVE) {
			failure = true;
		}
		this.description = description;
		this.oracle = oracle;
	}
	
	public String getTdlMessageId() {
		return tdlMessageId != null ? tdlMessageId : "NULL";
	}
	
	public void setTdlMessageId(String name) {
		tdlMessageId = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getDescription() {
		return description != null ? description : "NULL";
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public HashMap<DataUse, DataUse> getOracle() {
		return oracle;
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
	
	public Message getMessage() {
		return message;
	}
	
	public void setMessage(Message message) {
		this.message = message;
	}
}
