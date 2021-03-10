package org.imt.tdl.configuration.impl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
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
		Resource model = this._resourceModel;
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(model);
	    domain.getCommandStack().execute(new RecordingCommand(domain) {
	        @Override
	        protected void doExecute() {
	        	model.getContents().clear();
	    		model.getContents().addAll(modifiedResource.getContents());
	        }
	    });	
	}
	public boolean modelInitialized() {
		if (this._resourceModel==null) {
			return false;
		}
		return true;
	}
}