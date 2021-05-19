package org.imt.tdl.libraryGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.SimpleDataInstance;
import org.etsi.mts.tdl.tdlFactory;

import org.etsi.mts.tdl.SimpleDataType;
import org.etsi.mts.tdl.StructuredDataInstance;
import org.etsi.mts.tdl.StructuredDataType;
import org.etsi.mts.tdl.UnassignedMemberTreatment;
import org.etsi.mts.tdl.AnnotationType;
import org.etsi.mts.tdl.AnyValue;
import org.etsi.mts.tdl.DataInstance;
import org.etsi.mts.tdl.DataType;
import org.etsi.mts.tdl.ElementImport;
import org.etsi.mts.tdl.Member;
import org.etsi.mts.tdl.MemberAssignment;

public class CommonPackageGenerator {
	private tdlFactory factory;
	private Package commonPackage;
	private Package dslSpecificTypesPackage;
	private Map<String, DataType> dslSpecificTypes = new HashMap<String, DataType>();
	
	private DataType oclType;
	private List<DataInstance> verdictInstances = new ArrayList<DataInstance>();
	private DataType modelExecutionCommand;

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
		
		StructuredDataInstance oclQuery = factory.createStructuredDataInstance();
		oclQuery.setName("oclQuery");
		oclQuery.setDataType(OCL);
		oclQuery.setUnassignedMember(UnassignedMemberTreatment.ANY_VALUE);
		MemberAssignment queryAssign = factory.createMemberAssignment();
		queryAssign.setMember(query);
		AnyValue anyValue = factory.createAnyValue();
		anyValue.setName("?");
		queryAssign.setMemberSpec(anyValue);
		oclQuery.getMemberAssignment().add(queryAssign);
		this.commonPackage.getPackagedElement().add(oclQuery);
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
		SimpleDataType modelExecutionCommand = factory.createSimpleDataType();
		modelExecutionCommand.setName("modelExecutionCommand");
		SimpleDataInstance runModel = factory.createSimpleDataInstance();
		runModel.setName("runModel");
		runModel.setDataType(modelExecutionCommand);
		SimpleDataInstance runModelAsynchronous = factory.createSimpleDataInstance();
		runModelAsynchronous.setName("runModelAsynchronous");
		runModelAsynchronous.setDataType(modelExecutionCommand);
		SimpleDataInstance stopModelExecution = factory.createSimpleDataInstance();
		stopModelExecution.setName("stopModelExecution");
		stopModelExecution.setDataType(modelExecutionCommand);
		SimpleDataInstance resetModel = factory.createSimpleDataInstance();
		resetModel.setName("resetModel");
		resetModel.setDataType(modelExecutionCommand);
		SimpleDataInstance getModelState = factory.createSimpleDataInstance();
		getModelState.setName("getModelState");
		getModelState.setDataType(modelExecutionCommand);

		this.commonPackage.getPackagedElement().add(modelExecutionCommand);
		this.commonPackage.getPackagedElement().add(runModel);
		this.commonPackage.getPackagedElement().add(runModelAsynchronous);
		this.commonPackage.getPackagedElement().add(stopModelExecution);
		this.commonPackage.getPackagedElement().add(resetModel);
		this.commonPackage.getPackagedElement().add(getModelState);
		
		this.modelExecutionCommand = modelExecutionCommand;
	}
	public Package getCommonPackage() {
		return this.commonPackage;
	}
	public DataType getOCLType() {
		return this.oclType;
	}
	public DataType getModelExecutionCommand() {
		return this.modelExecutionCommand;
	}
	public void setDslSpecificTypes(Map<String, DataType> dslSpecificTypes) {
		this.dslSpecificTypes = dslSpecificTypes;
	}
	public void setDslSpecificTypesPackage (Package typesPackage) {
		this.dslSpecificTypesPackage = typesPackage;
	}
}
