package org.imt.tdl.executionEngine;

import org.eclipse.gemoc.trace.commons.model.trace.Step;
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionEngine;
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;

public class TestEngineAddon implements IEngineAddon{
	@Override
	public void aboutToExecuteStep(IExecutionEngine<?> engine, Step<?> stepToExecute) {
		// TODO Auto-generated method stub
		IEngineAddon.super.aboutToExecuteStep(engine, stepToExecute);
	}
	@Override
	public void stepExecuted(IExecutionEngine<?> engine, Step<?> stepExecuted) {
		// TODO Auto-generated method stub
		IEngineAddon.super.stepExecuted(engine, stepExecuted);
	}
}
