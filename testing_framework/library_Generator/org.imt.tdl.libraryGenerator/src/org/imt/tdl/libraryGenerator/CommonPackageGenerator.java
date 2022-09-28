package org.imt.tdl.libraryGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.etsi.mts.tdl.AnyValue;
import org.etsi.mts.tdl.DataInstance;
import org.etsi.mts.tdl.DataType;
import org.etsi.mts.tdl.ElementImport;
import org.etsi.mts.tdl.Member;
import org.etsi.mts.tdl.MemberAssignment;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.SimpleDataInstance;
import org.etsi.mts.tdl.SimpleDataType;
import org.etsi.mts.tdl.StructuredDataInstance;
import org.etsi.mts.tdl.StructuredDataType;
import org.etsi.mts.tdl.UnassignedMemberTreatment;
import org.etsi.mts.tdl.tdlFactory;

public class CommonPackageGenerator {
	
	private tdlFactory factory;
	private Package commonPackage;
	private Package dslSpecificTypesPackage;
	private Map<String, DataType> dslSpecificTypes = new HashMap<String, DataType>();
	
	private DataType oclType;
	private List<DataInstance> verdictInstances = new ArrayList<DataInstance>();
	private DataType modelExecutionCommand;

	public CommonPackageGenerator(DSLSpecificTypesGenerator dslSpecificTypesGenerator) {
		factory = tdlFactory.eINSTANCE;
		dslSpecificTypesPackage = dslSpecificTypesGenerator.getDslSpecificTypesPackage();
		dslSpecificTypes = dslSpecificTypesGenerator.getDslSpecificTypes();
	}
	
	public Package generateCommonPackage(){
		commonPackage = factory.createPackage();
		commonPackage.setName("common");
		generateImports();
		generateTypeForOCL();
		generateVerdicts();
		generateTypeForGeneralEvents();
		System.out.println("common package generated successfully");
		return commonPackage;
	}
	
	private void generateImports() {
		ElementImport dslSpecificTypesPackageImport = factory.createElementImport();
		dslSpecificTypesPackageImport.setImportedPackage(dslSpecificTypesPackage);
		commonPackage.getImport().add(dslSpecificTypesPackageImport);
	}
	
	private void generateTypeForOCL() {
		StructuredDataType OCL = factory.createStructuredDataType();
		OCL.setName("OCL");
		Member context = factory.createMember();
		context.setName("context");
		DataType contextType = dslSpecificTypes.get("EObject".toLowerCase());
		context.setDataType(contextType);
		OCL.getMember().add(context);
		Member query = factory.createMember();
		query.setName("query");
		DataType queryType = dslSpecificTypes.get("EString".toLowerCase());
		query.setDataType(queryType);
		OCL.getMember().add(query);
		commonPackage.getPackagedElement().add(OCL);
		oclType = OCL;
		
		StructuredDataInstance oclQuery = factory.createStructuredDataInstance();
		oclQuery.setName("oclQuery");
		oclQuery.setDataType(OCL);
		oclQuery.setUnassignedMember(UnassignedMemberTreatment.ANY_VALUE);
		MemberAssignment contextAssign = factory.createMemberAssignment();
		contextAssign.setMember(context);
		AnyValue anyValue = factory.createAnyValue();
		anyValue.setName("?");
		contextAssign.setMemberSpec(anyValue);
		oclQuery.getMemberAssignment().add(contextAssign);
		MemberAssignment queryAssign = factory.createMemberAssignment();
		queryAssign.setMember(query);
		anyValue = factory.createAnyValue();
		anyValue.setName("?");
		queryAssign.setMemberSpec(anyValue);
		oclQuery.getMemberAssignment().add(queryAssign);
		commonPackage.getPackagedElement().add(oclQuery);
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
		commonPackage.getPackagedElement().add(Verdict);
		commonPackage.getPackagedElement().add(PASS);
		commonPackage.getPackagedElement().add(FAIL);
		commonPackage.getPackagedElement().add(INCONCLUSINVE);
		
		verdictInstances.add(PASS);
		verdictInstances.add(FAIL);
		verdictInstances.add(INCONCLUSINVE);
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

		commonPackage.getPackagedElement().add(modelExecutionCommand);
		commonPackage.getPackagedElement().add(runModel);
		commonPackage.getPackagedElement().add(runModelAsynchronous);
		commonPackage.getPackagedElement().add(stopModelExecution);
		commonPackage.getPackagedElement().add(resetModel);
		commonPackage.getPackagedElement().add(getModelState);
		
		this.modelExecutionCommand = modelExecutionCommand;
	}
	
	public Package getCommonPackage() {
		return commonPackage;
	}
	
	public DataType getOCLType() {
		return oclType;
	}
	
	public DataType getModelExecutionCommand() {
		return modelExecutionCommand;
	}
}
