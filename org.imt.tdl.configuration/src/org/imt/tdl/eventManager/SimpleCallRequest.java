package org.imt.tdl.eventManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleCallRequest implements ICallRequest {

private final String behavioralUnit;
	
	private final List<Object> arguments;
	
	private final boolean runToCompletion;
	
	public SimpleCallRequest(String behavioralUnit, List<Object> arguments, boolean runToCompletion) {
		this.behavioralUnit = behavioralUnit;
		this.arguments = Collections.unmodifiableList(new ArrayList<>(arguments));
		this.runToCompletion = runToCompletion;
	}
	
	public String getBehavioralUnit() {
		return behavioralUnit;
	}
	
	public List<Object> getArguments() {
		return arguments;
	}
	
	@Override
	public boolean isRunToCompletion() {
		return runToCompletion;
	}
}
