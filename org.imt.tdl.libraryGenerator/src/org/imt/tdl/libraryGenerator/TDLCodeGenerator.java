package org.imt.tdl.libraryGenerator;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.etsi.mts.tdl.TDLan2StandaloneSetup;

import com.google.inject.Injector;

public class TDLCodeGenerator {
	public static final String[] tokenNames = new String[] {
	        "Package", "{", "}", "with", "perform", "action", "(", ",", ")", "on", "test", "objectives", ":", ";", "name", "time", "label", "constraints", "Action", "alternatively", "or", "Annotation", "*", "?", "=", "assert", "otherwise", "set", "verdict", "to", "->", "[", "]", "times", "repeat", "break", "Note", "create", "of", "type", "bind", "Component", "Type", "having", "if", "else", "connect", "as", "Map", "in", ".", "new", "containing", "Use", "Signature", "Collection", "default", "+", "-", "/", "mod", ">", "<", ">=", "<=", "==", "!=", "and", "xor", "not", "size", "Import", "all", "from", "Function", "returns", "instance", "returned", "Predefined", "gate", "Gate", "accepts", "sends", "triggers", "calls", "responds", "response", "interrupt", "optional", "mapped", "omit", "argument", "optionally", "run", "parallel", "parameter", "every", "component", "is", "quiet", "for", "terminate", "where", "it", "assigned", "Test", "Configuration", "Description", "Implementation", "uses", "configuration", "execute", "bindings", "Objective", "description", "Time", "out", "timer", "start", "stop", "variable", "waits", "extends", "SUT", "Tester", "Message", "Procedure", "In", "Out", "Exception", "last", "previous", "first"
	    };
	private CommonPackageGenerator commonPackageGenerator;
	private DSLSpecificEventsGenerator dslSpecificEventsGenerator;
	private DSLSpecificTypesGenerator dslSpecificTypesGenerator; 
	private TestConfigurationGenerator testConfigurationPackageGenerator;
	private TestSuitePackageGenerator testSuitePackageGenerator;
	
	public TDLCodeGenerator(String dslFilePath, String tdlProjectPath) throws IOException {		
		System.out.println("Start TDL Code generation");
		
		this.testSuitePackageGenerator = new TestSuitePackageGenerator(dslFilePath);
		System.out.println("Test suite package generated successfully");
		
		this.testConfigurationPackageGenerator = this.testSuitePackageGenerator.getTestConfigurationGenerator();
		this.dslSpecificTypesGenerator = this.testConfigurationPackageGenerator.getDslSpecificTypesGenerator();
		this.dslSpecificEventsGenerator = this.dslSpecificTypesGenerator.getDslSpecificEventsGenerator();
		this.commonPackageGenerator = this.dslSpecificTypesGenerator.getCommonPackageGenerator();
				
		System.out.println("start saving packages");
		savePackages(tdlProjectPath);
		System.out.println("all packages are saved successfully");
	}

	public void savePackages(String tdlProjectPath) throws IOException {
		Injector injector = new TDLan2StandaloneSetup().createInjectorAndDoEMFRegistration();
		ResourceSet rs = injector.getInstance(ResourceSet.class);
		
		Resource commonPackageRes = rs.createResource(URI.createURI(tdlProjectPath + "/generated/" + this.commonPackageGenerator.getCommonPackage().getName()+ ".tdlan2"));
		Resource dslSpecificPackageRes = rs.createResource(URI.createURI(tdlProjectPath + "/generated/" + this.dslSpecificEventsGenerator.getDslSpecificEventsPackage().getName()+ ".tdlan2"));
		Resource requiredTypesRes = rs.createResource(URI.createURI(tdlProjectPath + "/generated/" + this.dslSpecificTypesGenerator.getDslSpecificTypesPackage().getName()+ ".tdlan2"));
		Resource configurationRes = rs.createResource(URI.createURI(tdlProjectPath + "/generated/" + this.testConfigurationPackageGenerator.getTestConfigurationPackage().getName()+ ".tdlan2"));
		Resource testSuitePackageRes = rs.createResource(URI.createURI(tdlProjectPath + "/" + this.testSuitePackageGenerator.getTestSuitePackage().getName()+ ".tdlan2"));

		commonPackageRes.getContents().add(this.commonPackageGenerator.getCommonPackage());
		dslSpecificPackageRes.getContents().add(this.dslSpecificEventsGenerator.getDslSpecificEventsPackage());
		requiredTypesRes.getContents().add(this.dslSpecificTypesGenerator.getDslSpecificTypesPackage());
		configurationRes.getContents().add(this.testConfigurationPackageGenerator.getTestConfigurationPackage());
		testSuitePackageRes.getContents().add(this.testSuitePackageGenerator.getTestSuitePackage());
		
		commonPackageRes.save(Collections.EMPTY_MAP);
		dslSpecificPackageRes.save(Collections.EMPTY_MAP);
		requiredTypesRes.save(Collections.EMPTY_MAP);
		configurationRes.save(Collections.EMPTY_MAP);
		testSuitePackageRes.save(Collections.EMPTY_MAP);
		
		commonPackageRes.unload();
		dslSpecificPackageRes.unload();
		requiredTypesRes.unload();
		configurationRes.unload();
		testSuitePackageRes.unload();
		rs = null;
	}
}
