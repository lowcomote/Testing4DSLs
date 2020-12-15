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
import org.eclipse.xtext.GrammarUtil;
import org.etsi.mts.tdl.parser.antlr.*;
import org.etsi.mts.tdl.DataType;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.PackageableElement;
import org.imt.atl.ecore2tdl.files.Ecore2tdl;

public class RequiredTypesGenerator {
	
	private Package requiredTypesPackage;
	private Map<String, DataType> requiredTypes = new HashMap<String, DataType>();
	
	private DSLSpecificPackageGenerator dslSpecificPackageGenerator;
	private CommonPackageGenerator commonPackageGenerator;
	
	public RequiredTypesGenerator (String dslFilePath, String tdlProjectPath) throws IOException{
		generateRequiredTypes(dslFilePath, tdlProjectPath);
		loadRequiredTyeps(tdlProjectPath);
		
		this.commonPackageGenerator = new CommonPackageGenerator();
		this.commonPackageGenerator.setRequiredTypesPackage(this.requiredTypesPackage);
		this.commonPackageGenerator.setRequiredTypes(this.requiredTypes);
		this.commonPackageGenerator.generateCommonPackage();
		System.out.println("common package generated successfully");
		
		this.dslSpecificPackageGenerator = new DSLSpecificPackageGenerator(dslFilePath);
		this.dslSpecificPackageGenerator.setRequiredTypesPackage(this.requiredTypesPackage);
		this.dslSpecificPackageGenerator.setRequiredTypes(this.requiredTypes);
		this.dslSpecificPackageGenerator.generateDSLSpecificPackage(dslFilePath);
		System.out.println("dsl-specific package generated successfully");
	}
	//TODO: generating types for the required elements not all of them
	private void generateRequiredTypes(String dslFilePath, String tdlProjectPath) throws IOException {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		String metamodelPath = dsl.getEntry("ecore").getValue();
		String IN_model_path = metamodelPath;
		String OUT_model_path = tdlProjectPath;
		try {
			Ecore2tdl runner = new Ecore2tdl();
			runner.loadModels(IN_model_path);
			runner.doEcore2tdl(new NullProgressMonitor());
			//TODO: Change saveModels in order to return a resource of the generated output and not to save it
			runner.saveModels(OUT_model_path);
		} catch (ATLCoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ATLExecutionException e) {
			e.printStackTrace();
		}
	}
	private void loadRequiredTyeps(String tdlProjectPath) throws IOException {
		Resource dslTypesRes = (new ResourceSetImpl()).getResource(URI.createURI(tdlProjectPath), true);
		this.requiredTypesPackage = (Package) dslTypesRes.getContents().get(0);
		for (int i=0; i<this.requiredTypesPackage.getPackagedElement().size();i++) {
			PackageableElement p = this.requiredTypesPackage.getPackagedElement().get(i);
			if (p instanceof DataType) {
				this.requiredTypes.put(p.getName().toLowerCase(), (DataType) p);
			}
		}
	}
	public CommonPackageGenerator getCommonPackageGenerator() {
		return this.commonPackageGenerator;
	}
	public DSLSpecificPackageGenerator getDSLSpecificPackageGenerator() {
		return this.dslSpecificPackageGenerator;
	}
	public Package getRequiredTypesPackage() {
		return this.requiredTypesPackage;
	}
}
