package org.imt.tdl.defaultPackage.generator;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.PackageableElement;
import org.etsi.mts.tdl.SimpleDataInstance;
import org.etsi.mts.tdl.tdlFactory;
import org.etsi.mts.tdl.util.tdlResourceFactoryImpl;

import org.etsi.mts.tdl.SimpleDataType;
import org.etsi.mts.tdl.StructuredDataType;
import org.etsi.mts.tdl.AnnotationType;
import org.etsi.mts.tdl.DataInstance;
import org.etsi.mts.tdl.DataType;
import org.etsi.mts.tdl.Member;

public class CommonPackageGenerator {
	private tdlFactory factory;
	private Package commonPackage;
	private Map<String , DataType> commonTypes = new HashMap<String , DataType>();
	private DataType oclType;
	private List<DataInstance> verdictInstances = new ArrayList<DataInstance>();
	private List<DataType> TypesForGeneralEvents = new ArrayList<DataType>();
	private Map<String, AnnotationType> annotations = new HashMap<String, AnnotationType>();

	public CommonPackageGenerator() {
		this.factory = tdlFactory.eINSTANCE;
		generateCommonPackage();
		saveCommonPackageIntoFile();
	}
	public void generateCommonPackage(){
		this.commonPackage = factory.createPackage();
		this.commonPackage.setName("commonPackage");
		generatePrimitiveDataTypes();
		generateTypeForOCL();
		generateVerdicts();
		generateTypeForGeneralEvents();
		generateAnnotations();
	}
	private void generatePrimitiveDataTypes() {
		SimpleDataType String = factory.createSimpleDataType();
		String.setName("String");
		SimpleDataType Integer = factory.createSimpleDataType();
		Integer.setName("Integer");
		SimpleDataType Boolean = factory.createSimpleDataType();
		Boolean.setName("Boolean");
		this.commonPackage.getPackagedElement().add(String);
		this.commonPackage.getPackagedElement().add(Integer);
		this.commonPackage.getPackagedElement().add(Boolean);
		
		this.commonTypes.put(String.getName(), String);
		this.commonTypes.put(Integer.getName(), Integer);
		this.commonTypes.put(Boolean.getName(), Boolean);
	}
	private void generateTypeForOCL() {
		StructuredDataType OCL = factory.createStructuredDataType();
		OCL.setName("OCL");
		Member query = factory.createMember();
		query.setName("query");
		DataType queryType = this.commonTypes.get("String");
		query.setDataType(queryType);
		OCL.getMember().add(query);
		this.commonPackage.getPackagedElement().add(OCL);
		
		this.oclType = OCL;
	}
	private void generateVerdicts() {
		SimpleDataType Verdict = factory.createSimpleDataType();
		Verdict.setName("Verdict");
		SimpleDataInstance PASS = factory.createSimpleDataInstance();
		PASS.setName("PASS");
		PASS.setDataType(Verdict);
		SimpleDataInstance FAIL = factory.createSimpleDataInstance();
		FAIL.setName("FAIL");
		FAIL.setDataType(Verdict);
		SimpleDataInstance INCONCLUSINVE = factory.createSimpleDataInstance();
		INCONCLUSINVE.setName("INCONCLUSINVE");
		INCONCLUSINVE.setDataType(Verdict);
		this.commonPackage.getPackagedElement().add(Verdict);
		this.commonPackage.getPackagedElement().add(PASS);
		this.commonPackage.getPackagedElement().add(FAIL);
		this.commonPackage.getPackagedElement().add(INCONCLUSINVE);
		
		this.verdictInstances.add(PASS);
		this.verdictInstances.add(FAIL);
		this.verdictInstances.add(INCONCLUSINVE);
	}
	private void generateTypeForGeneralEvents() {
		SimpleDataType runModel = factory.createSimpleDataType();
		runModel.setName("runModel");
		SimpleDataInstance runMUT = factory.createSimpleDataInstance();
		runMUT.setName("runMUT");
		runMUT.setDataType(runModel);
		this.commonPackage.getPackagedElement().add(runModel);
		this.commonPackage.getPackagedElement().add(runMUT);
		
		SimpleDataType getState = factory.createSimpleDataType();
		getState.setName("getState");
		SimpleDataInstance getModelState = factory.createSimpleDataInstance();
		getModelState.setName("getModelState");
		runMUT.setDataType(getState);
		this.commonPackage.getPackagedElement().add(getState);
		this.commonPackage.getPackagedElement().add(getModelState);
		
		this.TypesForGeneralEvents.add(runModel);
		this.TypesForGeneralEvents.add(getState);
	}
	private void generateAnnotations() {
		AnnotationType GIVEN = factory.createAnnotationType();
		GIVEN.setName("GIVEN");
		AnnotationType WHEN = factory.createAnnotationType();
		WHEN.setName("WHEN");
		AnnotationType THEN = factory.createAnnotationType();
		THEN.setName("THEN");
		AnnotationType MUTPath = factory.createAnnotationType();
		MUTPath.setName("MUTPath");
		this.commonPackage.getPackagedElement().add(GIVEN);
		this.commonPackage.getPackagedElement().add(WHEN);
		this.commonPackage.getPackagedElement().add(THEN);
		this.commonPackage.getPackagedElement().add(MUTPath);
		
		this.annotations.put(GIVEN.getName(), GIVEN);
		this.annotations.put(WHEN.getName(), WHEN);
		this.annotations.put(THEN.getName(), THEN);
		this.annotations.put(MUTPath.getName(), MUTPath);
	}
	public Package getCommonPackage() {
		return this.commonPackage;
	}
	public Map<String, DataType> getPrimitiveDataTypes() {
		return this.commonTypes;
	}
	public DataType getOCLType() {
		return this.oclType;
	}
	public List<DataInstance> getVerdictInstances() {
		return this.verdictInstances;
	}
	public List<DataType> getTypesOfGeneralEvents() {
		return this.TypesForGeneralEvents;
	}
	public Map<String, AnnotationType> getAnnotations() {
		return this.annotations;
	}
	public void saveCommonPackageIntoFile() {
		String extension = "tdl";
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        tdlResourceFactoryImpl factory = new tdlResourceFactoryImpl();
        m.put(extension, factory);
       
        // create a resource
        Resource commonPackageResource = factory.createResource(URI.createURI(this.commonPackage.getName() + ".tdlan2"));
        // Get the first model element and cast it to the right type
        commonPackageResource.getContents().add(this.commonPackage);
        Map options = new HashMap<>();
	    options.put(XMIResourceImpl.OPTION_ENCODING, "UTF-8");
        //save the content.
        try {
            commonPackageResource.save(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
