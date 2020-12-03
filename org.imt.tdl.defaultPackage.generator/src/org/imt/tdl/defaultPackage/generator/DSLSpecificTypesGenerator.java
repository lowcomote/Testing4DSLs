package org.imt.tdl.defaultPackage.generator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.m2m.atl.common.ATLExecutionException;
import org.eclipse.m2m.atl.core.ATLCoreException;
import org.etsi.mts.tdl.DataType;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.PackageableElement;
import org.imt.atl.ecore2tdl.files.Ecore2tdl;

public class DSLSpecificTypesGenerator {
	
	private Package dslSpecificTypesPackage;
	private DSLSpecificPackageGenerator dslSpecificPackageGenerator;
	private Map<String, DataType> dslSpecificTypes = new HashMap<String, DataType>();
	private String dslTypesPackagePath = "./dslSpecificTypes.tdlan2";
	
	public DSLSpecificTypesGenerator (String dslFilePath) throws IOException{
		System.out.println("Start dsl-specific types package generation");
		generateDSLSpecificTypes(dslFilePath);
		loadDSLSpecificTyeps();
		System.out.println("dsl-specific types package generated successfully");
		this.dslSpecificPackageGenerator = new DSLSpecificPackageGenerator(dslFilePath);
		this.dslSpecificPackageGenerator.setDslSpecificTypes(dslSpecificTypes);
		this.dslSpecificPackageGenerator.setDSLSpecificTypePackage(this.dslSpecificTypesPackage);
	}
	//TODO: generating types for the required elements not all of them
	private void generateDSLSpecificTypes(String dslFilePath) throws IOException {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		String metamodelPath = dsl.getEntry("ecore").getValue();
		String IN_model_path = metamodelPath;
		String OUT_model_path = this.dslTypesPackagePath;
		try {
			Ecore2tdl runner = new Ecore2tdl();
			runner.loadModels(IN_model_path);
			runner.doEcore2tdl(new NullProgressMonitor());
			runner.saveModels(OUT_model_path);
		} catch (ATLCoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ATLExecutionException e) {
			e.printStackTrace();
		}
	}
	private void loadDSLSpecificTyeps() throws IOException {
		Resource dslTypesRes = (new ResourceSetImpl()).getResource(URI.createURI(this.dslTypesPackagePath), true);
		this.dslSpecificTypesPackage = (Package) dslTypesRes.getContents().get(0);
		for (int i=0; i<this.dslSpecificTypesPackage.getPackagedElement().size();i++) {
			PackageableElement p = this.dslSpecificTypesPackage.getPackagedElement().get(i);
			if (p instanceof DataType) {
				this.dslSpecificTypes.put(p.getName(), (DataType) p);
			}
		}
	}
	public DSLSpecificPackageGenerator getDSLSpecificPackageGenerator() {
		return this.dslSpecificPackageGenerator;
	}
}
