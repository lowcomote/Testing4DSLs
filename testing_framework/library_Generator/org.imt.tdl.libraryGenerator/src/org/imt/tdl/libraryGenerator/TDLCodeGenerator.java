package org.imt.tdl.libraryGenerator;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.dsl.Dsl;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TDLan2StandaloneSetup;

import com.google.inject.Injector;

public class TDLCodeGenerator {
	
	public static final String[] tokenNames = new String[] {
	        "Package", "{", "}", "with", "perform", "action", "(", ",", ")", "on", "test", "objectives", ":", ";", "name", "time", "label", "constraints", "Action", "alternatively", "or", "Annotation", "*", "?", "=", "assert", "otherwise", "set", "verdict", "to", "->", "[", "]", "times", "repeat", "break", "Note", "create", "of", "type", "bind", "Component", "Type", "having", "if", "else", "connect", "as", "Map", "in", ".", "new", "containing", "Use", "Signature", "Collection", "default", "+", "-", "/", "mod", ">", "<", ">=", "<=", "==", "!=", "and", "xor", "not", "size", "Import", "all", "from", "Function", "returns", "instance", "returned", "Predefined", "gate", "Gate", "accepts", "sends", "triggers", "calls", "responds", "response", "interrupt", "optional", "mapped", "omit", "argument", "optionally", "run", "parallel", "parameter", "every", "component", "is", "quiet", "for", "terminate", "where", "it", "assigned", "Test", "Configuration", "Description", "Implementation", "uses", "configuration", "execute", "bindings", "Objective", "description", "Time", "out", "timer", "start", "stop", "variable", "waits", "extends", "SUT", "Tester", "Message", "Procedure", "In", "Out", "Exception", "last", "previous", "first"
	    };
	
	private CommonPackageGenerator commonPackageGenerator;
	private Package commonPackage;
	private DSLSpecificEventsGenerator dslSpecificEventsGenerator;
	private Package dslSpecificEventsPackage;
	private DSLSpecificTypesGenerator dslSpecificTypesGenerator; 
	private Package dslSpecificTypesPackage;
	private TestConfigurationGenerator testConfigurationGenerator;
	private Package testConfigurationPackage;
	private TestSuitePackageGenerator testSuitePackageGenerator;
	private Package testSuitePackage;
	
	public TDLCodeGenerator(String dslFilePath, String tdlProjectPath) throws IOException {		
		System.out.println("Start TDL Code generation");
		dslSpecificTypesGenerator = new DSLSpecificTypesGenerator(dslFilePath);
		dslSpecificTypesPackage = dslSpecificTypesGenerator.generateDslSpecificTypes();
		
		commonPackageGenerator = new CommonPackageGenerator(dslSpecificTypesGenerator);
		commonPackage = commonPackageGenerator.generateCommonPackage();;
		
		if (hasBehavioralInterface(dslFilePath)) {
			dslSpecificEventsGenerator = new DSLSpecificEventsGenerator(dslFilePath, dslSpecificTypesGenerator);
			dslSpecificEventsPackage = dslSpecificEventsGenerator.generateDSLSpecificEventsPackage(dslFilePath);
		}
		
		testConfigurationGenerator = new TestConfigurationGenerator(dslFilePath, 
				commonPackageGenerator, dslSpecificEventsGenerator);
		testConfigurationPackage = testConfigurationGenerator.generateTestConfigurationPackage();
		
		testSuitePackageGenerator = new TestSuitePackageGenerator(dslFilePath, commonPackage, 
				dslSpecificTypesPackage, dslSpecificEventsPackage, testConfigurationPackage);
		testSuitePackage = testSuitePackageGenerator.generateTestSuitePackage();
				
		System.out.println("start saving packages");
		savePackages(tdlProjectPath);
		System.out.println("all packages are saved successfully");
	}

	public void savePackages(String tdlProjectPath) throws IOException {
		Injector injector = new TDLan2StandaloneSetup().createInjectorAndDoEMFRegistration();
		ResourceSet rs = injector.getInstance(ResourceSet.class);
		
		Resource commonPackageRes = rs.createResource(URI.createURI(tdlProjectPath + "/generated/" + commonPackage.getName()+ ".tdlan2"));
		commonPackageRes.getContents().add(commonPackage);
		
		Resource dslSpecificEventsRes = null;
		if (dslSpecificEventsPackage!=null) {
			dslSpecificEventsRes = rs.createResource(URI.createURI(tdlProjectPath + "/generated/" + dslSpecificEventsPackage.getName()+ ".tdlan2"));
			dslSpecificEventsRes.getContents().add(dslSpecificEventsPackage);
		}
		Resource dslSpecificTypesRes = rs.createResource(URI.createURI(tdlProjectPath + "/generated/" + dslSpecificTypesPackage.getName()+ ".tdlan2"));
		dslSpecificTypesRes.getContents().add(dslSpecificTypesPackage);
		
		Resource configurationRes = rs.createResource(URI.createURI(tdlProjectPath + "/generated/" + testConfigurationPackage.getName()+ ".tdlan2"));
		configurationRes.getContents().add(testConfigurationPackage);
		
		Resource testSuitePackageRes = rs.createResource(URI.createURI(tdlProjectPath + "/" + testSuitePackage.getName()+ ".tdlan2"));
		testSuitePackageRes.getContents().add(testSuitePackage);
		
		commonPackageRes.save(Collections.EMPTY_MAP);
		if (dslSpecificEventsRes != null) {
			dslSpecificEventsRes.save(Collections.EMPTY_MAP);
		}
		dslSpecificTypesRes.save(Collections.EMPTY_MAP);
		configurationRes.save(Collections.EMPTY_MAP);
		testSuitePackageRes.save(Collections.EMPTY_MAP);
		
		commonPackageRes.unload();
		if (dslSpecificEventsRes != null) {
			dslSpecificEventsRes.unload();
		}
		dslSpecificTypesRes.unload();
		configurationRes.unload();
		testSuitePackageRes.unload();
		rs = null;
	}
	
	protected boolean hasBehavioralInterface(String dslFilePath) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		return dsl.getEntry("behavioralInterface") != null ? true : false;
	}
}
