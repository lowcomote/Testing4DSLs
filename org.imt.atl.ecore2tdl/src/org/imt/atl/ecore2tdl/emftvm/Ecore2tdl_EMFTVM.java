package org.imt.atl.ecore2tdl.emftvm;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.m2m.atl.core.ATLCoreException;
import org.eclipse.m2m.atl.emftvm.EmftvmFactory;
import org.eclipse.m2m.atl.emftvm.ExecEnv;
import org.eclipse.m2m.atl.emftvm.Metamodel;
import org.eclipse.m2m.atl.emftvm.Model;
import org.eclipse.m2m.atl.emftvm.util.DefaultModuleResolver;
import org.eclipse.m2m.atl.emftvm.util.ModuleResolver;
import org.eclipse.m2m.atl.emftvm.util.TimingData;

public class Ecore2tdl_EMFTVM {
	
	ExecEnv env = EmftvmFactory.eINSTANCE.createExecEnv();
	ResourceSet rs = new ResourceSetImpl();
	
	Model inModel;
	Model inOutModel;
	Model outModel;
	
	public void loadModels(String inModelPath) throws ATLCoreException {
		// Load metamodels
		Metamodel ecoreMetamodel = EmftvmFactory.eINSTANCE.createMetamodel();
		ecoreMetamodel.setResource(rs.getResource(URI.createURI("http://www.eclipse.org/emf/2002/Ecore"), true));
		env.registerMetaModel("METAMODEL", ecoreMetamodel);
		
		Metamodel tdlMetamodel = EmftvmFactory.eINSTANCE.createMetamodel();
		tdlMetamodel.setResource(rs.getResource(URI.createURI("http://www.etsi.org/spec/TDL/1.4.1"), true));
		env.registerMetaModel("METAMODEL", tdlMetamodel);
		
		// Load models
		this.inModel = EmftvmFactory.eINSTANCE.createModel();
		this.inModel.setResource(rs.getResource(URI.createURI(inModelPath, true), true));
		env.registerInputModel("IN", this.inModel);

		this.inOutModel = EmftvmFactory.eINSTANCE.createModel();
		this.inOutModel.setResource(rs.getResource(URI.createURI("platform:/plugin/org.imt.atl.ecore2tdl/models/trace.xmi", true), true));
		env.registerInOutModel("INOUT", this.inOutModel);

		this.outModel = EmftvmFactory.eINSTANCE.createModel();
		this.outModel.setResource(rs.createResource(URI.createFileURI("platform:/plugin/org.imt.atl.ecore2tdl/models/out.xmi")));
		env.registerOutputModel("OUT", this.outModel);	
	}
	public void doEcore2tdl() {
		// Load and run module
		ModuleResolver mr = new DefaultModuleResolver("platform:/plugin/org.imt.atl.ecore2tdl/transformation/", new ResourceSetImpl());
		TimingData td = new TimingData();
		env.loadModule(mr, "ecore2tdl");
		td.finishLoading();
		env.run(td);
		td.finish();
	}
	public void saveModels() throws IOException {
		// Save models
		this.inOutModel.getResource().save(Collections.emptyMap());
		this.outModel.getResource().save(Collections.emptyMap());
	}
	public Resource getOutModel() {
		return this.outModel.getResource();
	}
	public Resource getTraceModel() {
		return this.inOutModel.getResource();
	}
}
