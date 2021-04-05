package org.imt.tdl.executionEngine;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

public abstract class AbstractEngine implements IExecutionEngine{
	private Resource MUTResource = null;
	
	@Override
	public void setUp(String MUTPath, String DSLPath) {
		this.MUTResource = (new ResourceSetImpl()).getResource(URI.createURI(MUTPath), true);
	}
	@Override
	public void setModelResource(Resource resource) {
		this.MUTResource = resource;
	}
	@Override
	public Resource getModelResource() {
		return this.MUTResource;
	}
}
