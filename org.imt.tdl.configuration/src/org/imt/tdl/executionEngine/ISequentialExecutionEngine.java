package org.imt.tdl.executionEngine;

public interface ISequentialExecutionEngine extends IExecutionEngine {
	
	public String executeModelSynchronous();
	public String executeModelAsynchronous();
	public String stopAsynchronousExecution();
}
