package org.imt.tdl.defaultPackage.generator;

import java.io.IOException;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.SimpleDataInstance;
import org.etsi.mts.tdl.tdlFactory;

import com.google.inject.Injector;

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
		System.out.println("Start common package generation");
		this.factory = tdlFactory.eINSTANCE;
		generateCommonPackage();
		System.out.println("common package generated successfully");
	}
	private void generateCommonPackage(){
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
		getModelState.setDataType(getState);
		this.commonPackage.getPackagedElement().add(getState);
		this.commonPackage.getPackagedElement().add(getModelState);
		
		this.TypesForGeneralEvents.add(runModel);
		this.TypesForGeneralEvents.add(getState);
	}
	private void generateAnnotations() {
		AnnotationType MUTPath = factory.createAnnotationType();
		MUTPath.setName("MUTPath");
		this.commonPackage.getPackagedElement().add(MUTPath);
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
	
	public void savePackage(Injector injector, ResourceSet rs) {
		Resource r = rs.createResource(URI.createURI(this.commonPackage.getName()+ ".tdlan2"));
		r.getContents().add(this.commonPackage);
		try {
			r.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		r.unload();
		rs = null;
	}
}
