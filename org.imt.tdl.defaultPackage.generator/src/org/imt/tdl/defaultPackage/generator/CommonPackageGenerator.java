package org.imt.tdl.defaultPackage.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.SimpleDataInstance;
import org.etsi.mts.tdl.tdlFactory;

import org.etsi.mts.tdl.SimpleDataType;
import org.etsi.mts.tdl.StructuredDataType;
import org.etsi.mts.tdl.AnnotationType;
import org.etsi.mts.tdl.DataInstance;
import org.etsi.mts.tdl.DataType;
import org.etsi.mts.tdl.ElementImport;
import org.etsi.mts.tdl.Member;

public class CommonPackageGenerator {
	private tdlFactory factory;
	private Package commonPackage;
	private Package dslSpecificTypesPackage;
	private Map<String, DataType> dslSpecificTypes = new HashMap<String, DataType>();
	
	private DataType oclType;
	private List<DataInstance> verdictInstances = new ArrayList<DataInstance>();
	private List<DataType> TypesForGeneralEvents = new ArrayList<DataType>();
	private Map<String, AnnotationType> annotations = new HashMap<String, AnnotationType>();

	public CommonPackageGenerator() {
		this.factory = tdlFactory.eINSTANCE;
	}
	public void generateCommonPackage(){
		this.commonPackage = factory.createPackage();
		this.commonPackage.setName("common");
		generateImports();
		generateTypeForOCL();
		generateVerdicts();
		generateTypeForGeneralEvents();
		generateAnnotations();
	}
	private void generateImports() {
		ElementImport dslSpecificTypesPackageImport = factory.createElementImport();
		dslSpecificTypesPackageImport.setImportedPackage(this.dslSpecificTypesPackage);
		this.commonPackage.getImport().add(dslSpecificTypesPackageImport);
	}
	private void generateTypeForOCL() {
		StructuredDataType OCL = factory.createStructuredDataType();
		OCL.setName("OCL");
		Member query = factory.createMember();
		query.setName("query");
		DataType queryType = this.dslSpecificTypes.get("EString".toLowerCase());
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
		
		AnnotationType DSLPath = factory.createAnnotationType();
		DSLPath.setName("DSLPath");
		this.commonPackage.getPackagedElement().add(DSLPath);
		this.annotations.put(DSLPath.getName(), DSLPath);
	}
	public Package getCommonPackage() {
		return this.commonPackage;
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
	public void setDslSpecificTypes(Map<String, DataType> dslSpecificTypes) {
		this.dslSpecificTypes = dslSpecificTypes;
	}
	public void setDslSpecificTypesPackage (Package typesPackage) {
		this.dslSpecificTypesPackage = typesPackage;
	}
}
