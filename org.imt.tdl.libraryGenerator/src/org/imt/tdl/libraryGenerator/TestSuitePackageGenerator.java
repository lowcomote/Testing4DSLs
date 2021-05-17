package org.imt.tdl.libraryGenerator;

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

public class TestSuitePackageGenerator {
	private tdlFactory factory;
	private Package testSuitePackage;
	private CommonPackageGenerator commonPackageGenerator;
	private DSLSpecificEventsGenerator dslSpecificEventsGenerator;
	private DSLSpecificTypesGenerator dslSpecificTyepsGenerator;
	private TestConfigurationGenerator testConfigurationPackageGenerator;
	
	public TestSuitePackageGenerator(String dslFilePath) throws IOException {
		this.factory = tdlFactory.eINSTANCE;
		this.testConfigurationPackageGenerator = new TestConfigurationGenerator(dslFilePath);
		System.out.println("test configuration package generated successfully");
		
		this.dslSpecificTyepsGenerator = this.testConfigurationPackageGenerator.getDslSpecificTypesGenerator();
		this.dslSpecificEventsGenerator = this.dslSpecificTyepsGenerator.getDslSpecificEventsGenerator();
		this.commonPackageGenerator = this.dslSpecificTyepsGenerator.getCommonPackageGenerator();
		generateTestSuitePackage();
	}
	private void generateTestSuitePackage() {
		this.testSuitePackage = factory.createPackage();
		this.testSuitePackage.setName("testSuite");
		generateImports(this.testSuitePackage);
		//generateGenericDataInstances();
		//generateAnnotations();
	}

	private void generateImports(Package testSuitePackage) {
		ElementImport commonPackageImport = factory.createElementImport();
		commonPackageImport.setImportedPackage(this.commonPackageGenerator.getCommonPackage());
		ElementImport dslSpecificTypesPackageImport = factory.createElementImport();
		dslSpecificTypesPackageImport.setImportedPackage(this.dslSpecificTyepsGenerator.getDslSpecificTypesPackage());
		ElementImport dslSpecificEventsPackageImport = factory.createElementImport();
		dslSpecificEventsPackageImport.setImportedPackage(this.dslSpecificEventsGenerator.getDslSpecificEventsPackage());
		ElementImport testConfigurationImport = factory.createElementImport();
		testConfigurationImport.setImportedPackage(this.testConfigurationPackageGenerator.getTestConfigurationPackage());
		testSuitePackage.getImport().add(commonPackageImport);
		testSuitePackage.getImport().add(dslSpecificTypesPackageImport);
		testSuitePackage.getImport().add(dslSpecificEventsPackageImport);
		testSuitePackage.getImport().add(testConfigurationImport);
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
				this.testSuitePackage.getPackagedElement().add(dynamicTypeInstance);
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
		this.testSuitePackage.getPackagedElement().add(ExactEquivalent);
		
		AnnotationType PartialEquivalent = factory.createAnnotationType();
		PartialEquivalent.setName("PartialEquivalent");
		this.testSuitePackage.getPackagedElement().add(PartialEquivalent);
	}
	public Package getTestSuitePackage() {
		return this.testSuitePackage;
	}
	public TestConfigurationGenerator getTestConfigurationGenerator() {
		return this.testConfigurationPackageGenerator;
	}
}
