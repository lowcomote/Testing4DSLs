package org.imt.tdl.sequentialEngine;

import org.eclipse.gemoc.trace.commons.model.trace.State;
import org.eclipse.gemoc.trace.commons.model.trace.Step;
import org.eclipse.gemoc.trace.commons.model.trace.Trace;
import org.eclipse.gemoc.trace.commons.model.trace.TracedObject;

public interface ISequentialExecutionEngine extends IExecutionEngine {
	
	public String executeModelSynchronous();
	public String executeModelAsynchronous();
	public String stopAsynchronousExecution();
	public Trace<Step<?>, TracedObject<?>, State<?,?>> getExecutionTrace();
}
