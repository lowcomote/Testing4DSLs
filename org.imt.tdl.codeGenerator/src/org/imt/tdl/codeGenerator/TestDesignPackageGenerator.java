package org.imt.tdl.codeGenerator;

import java.io.IOException;
import java.util.List;

import org.etsi.mts.tdl.AnnotationType;
import org.etsi.mts.tdl.AnyValue;
import org.etsi.mts.tdl.AnyValueOrOmit;
import org.etsi.mts.tdl.DataType;
import org.etsi.mts.tdl.ElementImport;
import org.etsi.mts.tdl.Member;
import org.etsi.mts.tdl.MemberAssignment;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.StructuredDataInstance;
import org.etsi.mts.tdl.StructuredDataType;
import org.etsi.mts.tdl.UnassignedMemberTreatment;
import org.etsi.mts.tdl.tdlFactory;

public class TestDesignPackageGenerator {
	private tdlFactory factory;
	private Package genericTestCasesPackage;
	private Package oclTestCasesPackage;
	private CommonPackageGenerator commonPackageGenerator;
	private DSLSpecificEventsGenerator dslSpecificEventsGenerator;
	private DSLSpecificTypesGenerator dslSpecificTyepsGenerator;
	private TestConfigurationGenerator testConfigurationPackageGenerator;
	
	public TestDesignPackageGenerator(String dslFilePath) throws IOException {
		this.factory = tdlFactory.eINSTANCE;
		this.testConfigurationPackageGenerator = new TestConfigurationGenerator(dslFilePath);
		System.out.println("test configuration package generated successfully");
		
		this.dslSpecificTyepsGenerator = this.testConfigurationPackageGenerator.getDslSpecificTypesGenerator();
		this.dslSpecificEventsGenerator = this.dslSpecificTyepsGenerator.getDslSpecificEventsGenerator();
		this.commonPackageGenerator = this.dslSpecificTyepsGenerator.getCommonPackageGenerator();
		generateGenericTestCasesPackage();
		generateOclTestCasesPackage();
	}
	private void generateGenericTestCasesPackage() {
		this.genericTestCasesPackage = factory.createPackage();
		this.genericTestCasesPackage.setName("genericTestCases");
		generateImports(this.genericTestCasesPackage);
		generateGenericDataInstances();
		generateAnnotations();
	}
	private void generateOclTestCasesPackage() {
		this.oclTestCasesPackage = factory.createPackage();
		this.oclTestCasesPackage.setName("oclTestCases");
		generateImports(this.oclTestCasesPackage);
		generateOCLDataInstances();
	}
	private void generateImports(Package testCasesPackage) {
		ElementImport commonPackageImport = factory.createElementImport();
		commonPackageImport.setImportedPackage(this.commonPackageGenerator.getCommonPackage());
		ElementImport dslSpecificEventsPackageImport = factory.createElementImport();
		dslSpecificEventsPackageImport.setImportedPackage(this.dslSpecificEventsGenerator.getDslSpecificEventsPackage());
		ElementImport dslSpecificTypesPackageImport = factory.createElementImport();
		dslSpecificTypesPackageImport.setImportedPackage(this.dslSpecificTyepsGenerator.getDslSpecificTypesPackage());
		ElementImport testConfigurationImport = factory.createElementImport();
		testConfigurationImport.setImportedPackage(this.testConfigurationPackageGenerator.getTestConfigurationPackage());
		testCasesPackage.getImport().add(commonPackageImport);
		testCasesPackage.getImport().add(dslSpecificEventsPackageImport);
		testCasesPackage.getImport().add(dslSpecificTypesPackageImport);
		testCasesPackage.getImport().add(testConfigurationImport);
	}
	private void generateGenericDataInstances() {
		List<DataType> dynamicTypes = this.dslSpecificTyepsGenerator.getDynamicTypes();
		for (int i=0; i< dynamicTypes.size(); i++) {
			//generate instances for each dynamic type
			
			DataType dynamicType = dynamicTypes.get(i);
			if (!isAbstractType(dynamicType)) {
				StructuredDataInstance dynamicTypeInstance = factory.createStructuredDataInstance();
				dynamicTypeInstance.setName(dynamicType.getName().toLowerCase()+"NewState");
				dynamicTypeInstance.setDataType(dynamicType);
				if (dynamicType instanceof StructuredDataType) {
					StructuredDataType type = (StructuredDataType) dynamicType;
					for (int j=0; j < type.allMembers().size(); j++) {
						Member m = type.allMembers().get(j);
						for (int k=0; k < m.getAnnotation().size(); k++) {
							if (m.getAnnotation().get(k).getKey().getName().toString().contains("dynamic")) {
								MemberAssignment memberAssign = factory.createMemberAssignment();
								memberAssign.setMember(m);
								AnyValueOrOmit anyValueOrOmit = factory.createAnyValueOrOmit();
								memberAssign.setMemberSpec(anyValueOrOmit);
								dynamicTypeInstance.getMemberAssignment().add(memberAssign);
							}
						}
					}
				}
				this.genericTestCasesPackage.getPackagedElement().add(dynamicTypeInstance);
			}
		}
	}
	private boolean isAbstractType(DataType type) {
		for (int i=0; i<type.getAnnotation().size(); i++) {
			if (type.getAnnotation().get(i).getKey().toString().contains("abstract")){
				return true;
			}
		}
		return false;
	}
	private void generateAnnotations() {
		AnnotationType ExactEquivalent = factory.createAnnotationType();
		ExactEquivalent.setName("ExactEquivalent");
		this.genericTestCasesPackage.getPackagedElement().add(ExactEquivalent);
		
		AnnotationType PartialEquivalent = factory.createAnnotationType();
		PartialEquivalent.setName("PartialEquivalent");
		this.genericTestCasesPackage.getPackagedElement().add(PartialEquivalent);
	}
	private void generateOCLDataInstances() {
		if (this.commonPackageGenerator.getOCLType()!=null) {
			StructuredDataInstance oclQuery = factory.createStructuredDataInstance();
			oclQuery.setName("oclQuery");
			StructuredDataType OCL = (StructuredDataType) this.commonPackageGenerator.getOCLType();
			oclQuery.setDataType(OCL);
			oclQuery.setUnassignedMember(UnassignedMemberTreatment.ANY_VALUE);
			MemberAssignment query = factory.createMemberAssignment();
			query.setMember(OCL.getMember().get(0));
			AnyValue anyValue = factory.createAnyValue();
			anyValue.setName("?");
			query.setMemberSpec(anyValue);
			oclQuery.getMemberAssignment().add(query);
			this.oclTestCasesPackage.getPackagedElement().add(oclQuery);
		}
	}
	public Package getGenericTestCasesPackage() {
		return this.genericTestCasesPackage;
	}
	public Package getOCLTestCasesPackage() {
		return this.oclTestCasesPackage;
	}
	public TestConfigurationGenerator getTestConfigurationGenerator() {
		return this.testConfigurationPackageGenerator;
	}
}
