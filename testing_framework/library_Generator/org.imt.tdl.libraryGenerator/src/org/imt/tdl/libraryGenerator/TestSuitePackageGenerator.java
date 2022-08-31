package org.imt.tdl.libraryGenerator;

import java.io.IOException;

import org.etsi.mts.tdl.ElementImport;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.tdlFactory;

public class TestSuitePackageGenerator {
	private tdlFactory factory;
	private Package testSuitePackage;
	private Package commonPackage;
	private Package dslSpecificEventsPackage;
	private Package dslSpecificTypesPackage;
	private Package testConfigurationPackage;
	
	public TestSuitePackageGenerator(String dslFilePath, 
			Package commonPackage, 
			Package dslSpecificTypesPackage, 
			Package dslSpecificEventsPackage,
			Package testConfigurationPackage) throws IOException {
		factory = tdlFactory.eINSTANCE;
		this.commonPackage = commonPackage;
		this.dslSpecificTypesPackage = dslSpecificTypesPackage;
		this.dslSpecificEventsPackage = dslSpecificEventsPackage;
		this.testConfigurationPackage = testConfigurationPackage;		
	}
	
	public Package generateTestSuitePackage() {
		testSuitePackage = factory.createPackage();
		testSuitePackage.setName("testSuite");
		generateImports(testSuitePackage);
		System.out.println("Test suite package generated successfully");
		return testSuitePackage;
	}

	private void generateImports(Package testSuitePackage) {
		ElementImport commonPackageImport = factory.createElementImport();
		commonPackageImport.setImportedPackage(commonPackage);
		testSuitePackage.getImport().add(commonPackageImport);
		ElementImport dslSpecificTypesPackageImport = factory.createElementImport();
		dslSpecificTypesPackageImport.setImportedPackage(dslSpecificTypesPackage);
		testSuitePackage.getImport().add(dslSpecificTypesPackageImport);
		if (dslSpecificEventsPackage != null) {
			ElementImport dslSpecificEventsPackageImport = factory.createElementImport();
			dslSpecificEventsPackageImport.setImportedPackage(dslSpecificEventsPackage);
			testSuitePackage.getImport().add(dslSpecificEventsPackageImport);
		}	
		ElementImport testConfigurationImport = factory.createElementImport();
		testConfigurationImport.setImportedPackage(testConfigurationPackage);
		testSuitePackage.getImport().add(testConfigurationImport);
	}
	
	public Package getTestSuitePackage() {
		return testSuitePackage;
	}
}
