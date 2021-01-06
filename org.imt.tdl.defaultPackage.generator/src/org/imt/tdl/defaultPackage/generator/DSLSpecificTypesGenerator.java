package org.imt.tdl.defaultPackage.generator;

import java.io.IOException;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.m2m.atl.common.ATLExecutionException;
import org.eclipse.m2m.atl.core.ATLCoreException;
import org.eclipse.m2m.atl.core.emf.EMFModel;
import org.eclipse.xtext.GrammarUtil;
import org.etsi.mts.tdl.parser.antlr.*;
import org.etsi.mts.tdl.DataType;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.PackageableElement;
import org.imt.atl.ecore2tdl.files.Ecore2tdl;

public class DSLSpecificTypesGenerator {
	
	private Package dslSpecificTypesPackage;
	private Map<String, DataType> dslSpecificTypes = new HashMap<String, DataType>();
	
	private DSLSpecificEventsGenerator dslSpecificEventsGenerator;
	private CommonPackageGenerator commonPackageGenerator;
	
	public DSLSpecificTypesGenerator (String dslFilePath) throws IOException{
		generateDslSpecificTypes(dslFilePath);
		
		this.commonPackageGenerator = new CommonPackageGenerator();
		this.commonPackageGenerator.setDslSpecificTypesPackage(this.dslSpecificTypesPackage);
		this.commonPackageGenerator.setDslSpecificTypes(this.dslSpecificTypes);
		this.commonPackageGenerator.generateCommonPackage();
		System.out.println("common package generated successfully");
		
		this.dslSpecificEventsGenerator = new DSLSpecificEventsGenerator(dslFilePath);
		this.dslSpecificEventsGenerator.setDslSpecificTypesPackage(this.dslSpecificTypesPackage);
		this.dslSpecificEventsGenerator.setDslSpecificTypes(this.dslSpecificTypes);
		this.dslSpecificEventsGenerator.generateDSLSpecificEventsPackage(dslFilePath);
		System.out.println("dsl-specific events package generated successfully");
	}
	//TODO: generating types for the required elements not all of them
	private void generateDslSpecificTypes(String dslFilePath) throws IOException {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		String metamodelPath = dsl.getEntry("ecore").getValue().replaceFirst("resource", "plugin");
		String IN_model_path = metamodelPath;
		try {
			Ecore2tdl runner = new Ecore2tdl();
			runner.loadModels(IN_model_path);
			runner.doEcore2tdl(new NullProgressMonitor());
			EMFModel outModel = (EMFModel) runner.getOutModel();
			Resource dslTypesRes = outModel.getResource();
			this.dslSpecificTypesPackage = (Package) dslTypesRes.getContents().get(0);
			for (int i=0; i<this.dslSpecificTypesPackage.getPackagedElement().size();i++) {
				PackageableElement p = this.dslSpecificTypesPackage.getPackagedElement().get(i);
				if (p instanceof DataType) {
					this.dslSpecificTypes.put(p.getName().toLowerCase(), (DataType) p);
				}
			}
		} catch (ATLCoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ATLExecutionException e) {
			e.printStackTrace();
		}
	}
	public CommonPackageGenerator getCommonPackageGenerator() {
		return this.commonPackageGenerator;
	}
	public DSLSpecificEventsGenerator getDslSpecificEventsGenerator() {
		return this.dslSpecificEventsGenerator;
	}
	public Package getDslSpecificTypesPackage() {
		return this.dslSpecificTypesPackage;
	}
}
