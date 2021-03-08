package org.imt.tdl.configuration.impl;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionEngine;
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;

public class SetMUTResoureAddon implements IEngineAddon{
	private Resource MUTResource;
	public SetMUTResoureAddon(Resource MUTResource) {
		this.MUTResource = MUTResource;
	}
	@Override
	public void engineAboutToStart(IExecutionEngine<?> executionEngine)  {

	}
}
