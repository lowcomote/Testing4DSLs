package org.imt.tdl.libraryGenerator;

import java.io.IOException;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.m2m.atl.common.ATLExecutionException;
import org.eclipse.m2m.atl.core.ATLCoreException;
import org.eclipse.m2m.atl.core.emf.EMFModel;
import org.etsi.mts.tdl.Annotation;
import org.etsi.mts.tdl.DataType;
import org.etsi.mts.tdl.Member;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.PackageableElement;
import org.etsi.mts.tdl.StructuredDataType;
import org.imt.atl.ecore2tdl.files.Ecore2tdl;

public class DSLSpecificTypesGenerator {
	
	private Package dslSpecificTypesPackage;
	private Map<String, DataType> dslSpecificTypes = new HashMap<String, DataType>();
	private List<DataType> dynamicTypes = new ArrayList<>();
	
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
					//recognizing dynamic data types for being used as model state
					if (p instanceof StructuredDataType) {
						StructuredDataType type = (StructuredDataType) p;
						if (isDynamic(type)) {
							this.dynamicTypes.add(type);
						}else if (type.getExtension() != null) {
							DataType superClass = (DataType) type.getExtension().getExtending();
							if (isDynamic(superClass) || this.dynamicTypes.contains(superClass)) {
								this.dynamicTypes.add(type);
							}
						}
					}
				}
			}
		} catch (ATLCoreException e) {
			e.printStackTrace();
		} catch (ATLExecutionException e) {
			e.printStackTrace();
		}
	}
	private boolean isDynamic(DataType dataType) {
		if (dataType instanceof StructuredDataType) {
			StructuredDataType type = (StructuredDataType) dataType;
			for (int j=0; j < type.getAnnotation().size(); j++){
				String annotation = type.getAnnotation().get(j).getKey().getName().toString();
				if (annotation.contains("dynamic")||annotation.contains("aspect")) {
					return true;
				}
			}
			for (int j=0; j < type.getMember().size(); j++) {
				Member m = type.getMember().get(j);
				for (int k=0; k < m.getAnnotation().size(); k++) {
					String annotation = m.getAnnotation().get(k).getKey().getName().toString();
					if (annotation.contains("dynamic")||annotation.contains("aspect")) {
						return true;
					}
				}
			}
		}
		return false;
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
	public List<DataType> getDynamicTypes(){
		return this.dynamicTypes;
	}
}
