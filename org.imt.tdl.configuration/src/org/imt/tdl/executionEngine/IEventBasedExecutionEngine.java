package org.imt.tdl.executionEngine;

import java.util.Map;

public interface IEventBasedExecutionEngine extends IExecutionEngine {
	
	public void startEngine();
	public String processAcceptedEvent(String eventName, Map<String, Object> parameters);
	public String assertExposedEvent(String eventName, Map<String, Object> parameters);
	public String sendStopEvent();
}
