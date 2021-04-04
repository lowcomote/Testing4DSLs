package org.imt.tdl.executionEngine;

import org.eclipse.emf.ecore.resource.Resource;

public interface IExecutionEngine {
	
	public void setUp(String MUTPath, String DSLPath);
	public String executeModel();
	public void setModelResource(Resource resource);
	public Resource getModelResource();
}
