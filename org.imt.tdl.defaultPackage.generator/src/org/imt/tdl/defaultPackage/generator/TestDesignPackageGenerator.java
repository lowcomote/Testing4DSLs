package org.imt.tdl.defaultPackage.generator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.etsi.mts.tdl.ElementImport;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.tdlFactory;
import org.etsi.mts.tdl.util.tdlResourceFactoryImpl;

public class TestDesignPackageGenerator {
	private tdlFactory factory;
	private Package testDesignPackage;
	private CommonPackageGenerator commonPackageGenerator;
	private DSLSpecificPackageGenerator dslSpecificPackageGenerator;
	private TestConfigurationGenerator testConfigurationPackageGenerator;
	
	public TestDesignPackageGenerator(String dslName) {
		this.factory = tdlFactory.eINSTANCE;
		this.testConfigurationPackageGenerator = new TestConfigurationGenerator(dslName);
		this.dslSpecificPackageGenerator = this.testConfigurationPackageGenerator.getDSLSpecificPackageGenerator();
		this.commonPackageGenerator = this.dslSpecificPackageGenerator.getCommonPackageGenerator();
		generateTestDesignPackage();
		saveTestDesignPackageIntoFile();
	}
	public void generateTestDesignPackage() {
		this.testDesignPackage = factory.createPackage();
		this.testDesignPackage.setName("testDesignPackage-template");
		generateImports();
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
	public void saveTestDesignPackageIntoFile() {
		String extension = "tdl";
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        tdlResourceFactoryImpl factory = new tdlResourceFactoryImpl();
        m.put(extension, factory);
       
        // create a resource
        Resource testDesignPackageResource = factory.createResource(URI.createURI(this.testDesignPackage.getName() + ".tdlan2"));
        // Get the first model element and cast it to the right type
        testDesignPackageResource.getContents().add(this.testDesignPackage);
        Map options = new HashMap<>();
	    options.put(XMIResourceImpl.OPTION_ENCODING, "UTF-8");
        //save the content.
        try {
            testDesignPackageResource.save(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
