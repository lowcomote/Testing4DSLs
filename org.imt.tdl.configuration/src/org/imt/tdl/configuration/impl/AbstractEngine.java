package org.imt.tdl.configuration.impl;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.executionframework.engine.commons.DslHelper;
import org.eclipse.gemoc.executionframework.engine.commons.K3DslHelper;
import org.imt.tdl.configuration.IExecutionEngine;
import org.osgi.framework.Bundle;

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
