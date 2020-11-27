package org.imt.tdl.defaultPackage.generator;

import java.io.IOException;


import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.etsi.mts.tdl.ElementImport;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TDLan2StandaloneSetup;
import org.etsi.mts.tdl.tdlFactory;

import com.google.inject.Injector;

public class TestDesignPackageGenerator {
	private tdlFactory factory;
	private Package testDesignPackage;
	private CommonPackageGenerator commonPackageGenerator;
	private DSLSpecificPackageGenerator dslSpecificPackageGenerator;
	private TestConfigurationGenerator testConfigurationPackageGenerator;
	
	public TestDesignPackageGenerator(IFile dslFile) {
		this.factory = tdlFactory.eINSTANCE;
		this.testConfigurationPackageGenerator = new TestConfigurationGenerator(dslFile);
		this.dslSpecificPackageGenerator = this.testConfigurationPackageGenerator.getDSLSpecificPackageGenerator();
		this.commonPackageGenerator = this.dslSpecificPackageGenerator.getCommonPackageGenerator();
		generateTestDesignPackage();
		savePackage();
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

	public void savePackage() {
		Injector injector = new TDLan2StandaloneSetup().createInjectorAndDoEMFRegistration();
		ResourceSet rs = injector.getInstance(ResourceSet.class);
		this.testConfigurationPackageGenerator.savePackage(injector, rs);
		Resource r = rs.createResource(URI.createURI(this.testDesignPackage.getName()+ ".tdlan2"));
		r.getContents().add(this.testDesignPackage);
		try {
			r.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		r.unload();
		rs = null;
	}
}
