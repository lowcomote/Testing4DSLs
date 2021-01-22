package org.imt.launchConfiguration.impl;

import org.eclipse.emf.ecore.resource.Resource;
import org.imt.launchConfiguration.ILauncher;

public abstract class AbstractLauncher implements ILauncher{
	private Resource MUTResource = null;
	@Override
	public void setModelResource(Resource resource) {
		this.MUTResource = resource;
	}
	@Override
	public Resource getModelResource() {
		return this.MUTResource;
	}
}
