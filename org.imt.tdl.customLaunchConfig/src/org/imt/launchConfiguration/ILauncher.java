package org.imt.launchConfiguration;

import org.eclipse.emf.ecore.resource.Resource;

public interface ILauncher {
	
	public void setUp(String MUTPath, String DSLPath);
	public void executeModel();
	public void setModelResource(Resource resource);
	public Resource getModelResource();
}
