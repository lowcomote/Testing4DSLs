package org.imt.tdl.defaultPackage.generator;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.etsi.mts.tdl.AnyValue;
import org.etsi.mts.tdl.DataType;
import org.etsi.mts.tdl.ElementImport;
import org.etsi.mts.tdl.MemberAssignment;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.StructuredDataInstance;
import org.etsi.mts.tdl.StructuredDataType;
import org.etsi.mts.tdl.TDLan2StandaloneSetup;
import org.etsi.mts.tdl.UnassignedMemberTreatment;
import org.etsi.mts.tdl.tdlFactory;

import com.google.inject.Injector;

public class TestDesignPackageGenerator {
	private tdlFactory factory;
	private Package testDesignPackage;
	private CommonPackageGenerator commonPackageGenerator;
	private DSLSpecificPackageGenerator dslSpecificPackageGenerator;
	private RequiredTypesGenerator requiredTypesGenerator;
	private TestConfigurationGenerator testConfigurationPackageGenerator;
	
	public TestDesignPackageGenerator(String dslFilePath, String tdlProjectPath) throws IOException {
		this.factory = tdlFactory.eINSTANCE;
		this.testConfigurationPackageGenerator = new TestConfigurationGenerator(dslFilePath, tdlProjectPath);
		System.out.println("test configuration package generated successfully");
		
		this.requiredTypesGenerator = this.testConfigurationPackageGenerator.getRequiredTypesGenerator();
		this.dslSpecificPackageGenerator = this.requiredTypesGenerator.getDSLSpecificPackageGenerator();
		this.commonPackageGenerator = this.requiredTypesGenerator.getCommonPackageGenerator();
		generateTestDesignPackage();
	}
	private void generateTestDesignPackage() {
		this.testDesignPackage = factory.createPackage();
		this.testDesignPackage.setName("testDesignPackage_template");
		generateImports();
		generateDataInstances();
	}
	private void generateImports() {
		ElementImport commonPackageImport = factory.createElementImport();
		commonPackageImport.setImportedPackage(this.commonPackageGenerator.getCommonPackage());
		ElementImport dslSpecificPackageImport = factory.createElementImport();
		dslSpecificPackageImport.setImportedPackage(this.dslSpecificPackageGenerator.getDSLSpecificPackage());
		ElementImport testConfigurationImport = factory.createElementImport();
		testConfigurationImport.setImportedPackage(this.testConfigurationPackageGenerator.getTestConfigurationPackage());
		this.testDesignPackage.getImport().add(commonPackageImport);
		this.testDesignPackage.getImport().add(dslSpecificPackageImport);
		this.testDesignPackage.getImport().add(testConfigurationImport);
	}
	private void generateDataInstances() {
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
			this.testDesignPackage.getPackagedElement().add(oclQuery);
		}
		if (this.dslSpecificPackageGenerator.getTypeOfModelState()!=null) {
			generateInstanceForModelState("givenState");
			generateInstanceForModelState("expectedState");
		}
	}
	private void generateInstanceForModelState (String instanceName) {
		StructuredDataType modelStateType = (StructuredDataType) this.dslSpecificPackageGenerator.getTypeOfModelState();
		
		StructuredDataInstance modelStateInstance = factory.createStructuredDataInstance();
		modelStateInstance.setName(instanceName);
		modelStateInstance.setDataType(modelStateType);
		modelStateInstance.setUnassignedMember(UnassignedMemberTreatment.ANY_VALUE);
		for (int i=0; i < modelStateType.getMember().size(); i++) {
			MemberAssignment memberAssignment = factory.createMemberAssignment();
			memberAssignment.setMember(modelStateType.getMember().get(i));
			AnyValue anyValue = factory.createAnyValue();
			anyValue.setName("?");
			memberAssignment.setMemberSpec(anyValue);
			modelStateInstance.getMemberAssignment().add(memberAssignment);
		}
		this.testDesignPackage.getPackagedElement().add(modelStateInstance);
	}
	public Package getTestDesignPackage() {
		return this.testDesignPackage;
	}
	public TestConfigurationGenerator getTestConfigurationGenerator() {
		return this.testConfigurationPackageGenerator;
	}
}
