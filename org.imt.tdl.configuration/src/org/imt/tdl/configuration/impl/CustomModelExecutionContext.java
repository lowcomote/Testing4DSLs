package org.imt.tdl.configuration.impl;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gemoc.executionframework.engine.commons.EngineContextException;
import org.eclipse.gemoc.executionframework.engine.commons.GenericModelExecutionContext;
import org.eclipse.gemoc.xdsmlframework.api.core.ExecutionMode;
import org.eclipse.gemoc.xdsmlframework.api.core.IRunConfiguration;

public class CustomModelExecutionContext extends GenericModelExecutionContext{
	
	@SuppressWarnings("unchecked")
	public CustomModelExecutionContext(IRunConfiguration runConfiguration, ExecutionMode executionMode)
			throws EngineContextException {
		super(runConfiguration, executionMode);
		// TODO Auto-generated constructor stub
	}
	public void setResourceModel(Resource modifiedResource) {
		this._resourceModel.getContents().clear();
		this._resourceModel.getContents().addAll(modifiedResource.getContents());
	}
	public boolean modelInitialized() {
		if (this._resourceModel==null) {
			return false;
		}
		return true;
	}
}
