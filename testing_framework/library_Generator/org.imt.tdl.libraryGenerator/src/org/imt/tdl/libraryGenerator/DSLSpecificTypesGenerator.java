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
import org.etsi.mts.tdl.DataType;
import org.etsi.mts.tdl.Member;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.PackageableElement;
import org.etsi.mts.tdl.SimpleDataType;
import org.etsi.mts.tdl.StructuredDataType;
import org.etsi.mts.tdl.tdlFactory;
import org.imt.atl.ecore2tdl.files.Ecore2tdl;

public class DSLSpecificTypesGenerator {

	private String dslFilePath;
	private Package dslSpecificTypesPackage;
	private Map<String, DataType> dslSpecificTypes = new HashMap<String, DataType>();
	private List<DataType> dynamicTypes = new ArrayList<>();
	
	public DSLSpecificTypesGenerator (String dslFilePath) throws IOException{
		this.dslFilePath = dslFilePath;
	}

	public Package generateDslSpecificTypes() throws IOException {
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
			dslSpecificTypesPackage = getDSLSpecificTypesPackage(dslTypesRes);
			for (int i=0; i<dslSpecificTypesPackage.getPackagedElement().size();i++) {
				PackageableElement p = dslSpecificTypesPackage.getPackagedElement().get(i);
				if (p instanceof DataType dataType) {
					dslSpecificTypes.put(p.getName().toLowerCase(), dataType);
					//recognizing dynamic data types for being used as model state
					if (p instanceof StructuredDataType sDataType) {
						if (isDynamic(sDataType)) {
							dynamicTypes.add(sDataType);
						}else if (sDataType.getExtension() != null) {
							DataType superClass = (DataType) sDataType.getExtension().getExtending();
							if (isDynamic(superClass) || dynamicTypes.contains(superClass)) {
								dynamicTypes.add(sDataType);
							}
							//TODO: For the updated version of TDL, the following if condition must be used
//							if (type.getExtension().stream().map(e -> (DataType) e.getExtending()).
//								anyMatch(e -> isDynamic(e) || dynamicTypes.contains(e))) {
//								dynamicTypes.add(type);
//							}
						}
					}
				}
			}
			//generating a simple type for EObject
			SimpleDataType eobjectType = tdlFactory.eINSTANCE.createSimpleDataType();
			eobjectType.setName("EObject");
			dslSpecificTypesPackage.getPackagedElement().add(eobjectType);
			dslSpecificTypes.put(eobjectType.getName().toLowerCase(), eobjectType);
		} catch (ATLCoreException e) {
			e.printStackTrace();
		} catch (ATLExecutionException e) {
			e.printStackTrace();
		}
		System.out.println("dsl-specific types package generated successfully");
		return dslSpecificTypesPackage;
	}
	
	private Package getDSLSpecificTypesPackage(Resource dslTypesRes) {
		Package firstPackage = (Package) dslTypesRes.getContents().get(0);
		if (dslTypesRes.getContents().size()==1) {
			return firstPackage;
		}
		for (int i=1; i<dslTypesRes.getContents().size(); i++) {
			Package iPackage = (Package) dslTypesRes.getContents().get(i);
			firstPackage.getPackagedElement().addAll(iPackage.getPackagedElement());
		}
		return firstPackage;
	}

	private boolean isDynamic(DataType dataType) {
		if (dataType instanceof StructuredDataType type) {
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
	
	public Map<String, DataType> getDslSpecificTypes() {
		return dslSpecificTypes;
	}
	
	public Package getDslSpecificTypesPackage () {
		return dslSpecificTypesPackage;
	}
	
	public List<DataType> getDynamicTypes(){
		return dynamicTypes;
	}
}
