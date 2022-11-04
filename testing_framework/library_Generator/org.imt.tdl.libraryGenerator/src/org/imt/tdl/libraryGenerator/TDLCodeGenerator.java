package org.imt.tdl.libraryGenerator;

import java.io.IOException;
import java.nio.file.Paths;
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
	        "Package", "{", "}", "with", "perform", "action", "(", ",", ")", "on", "test"
	        , "objectives", ":", ";", "name", "time", "label", "constraints", "Constraint" ,"Action"
	        , "alternatively", "or", "Annotation", "*", "?", "=", "assert", "otherwise", "set"
	        , "verdict", "to", "->", "[", "]", "times", "repeat", "break", "Note", "create", "of"
	        , "type", "bind", "Component", "Type", "having", "if", "else", "connect", "as", "Map"
	        , "in", ".", "new", "containing", "Use", "Signature", "Collection", "default", "+", "-"
	        , "/", "mod", ">", "<", ">=", "<=", "==", "!=", "and", "xor", "not", "size", "Import"
	        , "all", "from", "Function", "returns", "instance", "returned", "Predefined", "gate"
	        , "Gate", "accepts", "sends", "triggers", "calls", "responds", "response", "interrupt"
	        , "optional", "mapped", "omit", "argument", "optionally", "run", "parallel", "parameter"
	        , "every", "component", "is", "quiet", "for", "terminate", "where", "it", "assigned"
	        , "Test", "Configuration", "Description", "Implementation", "uses", "configuration"
	        , "execute", "bindings", "Objective", "description", "Time", "out", "timer", "start"
	        , "stop", "variable", "waits", "extends", "SUT", "Tester", "Message", "Procedure", "In"
	        , "Out", "Exception", "last", "previous", "first"
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
		
		String path = Paths.get(tdlProjectPath, "generated", (commonPackage.getName()+ ".tdlan2")).toString();
		Resource commonPackageRes = rs.createResource(URI.createPlatformResourceURI(path, false));
		commonPackageRes.getContents().add(commonPackage);
		
		Resource dslSpecificEventsRes = null;
		if (dslSpecificEventsPackage!=null) {
			path = Paths.get(tdlProjectPath, "generated", dslSpecificEventsPackage.getName() + ".tdlan2").toString();
			dslSpecificEventsRes = rs.createResource(URI.createPlatformResourceURI(path, false));
			dslSpecificEventsRes.getContents().add(dslSpecificEventsPackage);
		}
		path = Paths.get(tdlProjectPath, "generated", dslSpecificTypesPackage.getName() + ".tdlan2").toString();
		Resource dslSpecificTypesRes = rs.createResource(URI.createPlatformResourceURI(path, false));
		dslSpecificTypesRes.getContents().add(dslSpecificTypesPackage);
		
		path = Paths.get(tdlProjectPath, "generated", testConfigurationPackage.getName()+ ".tdlan2").toString();
		Resource configurationRes = rs.createResource(URI.createPlatformResourceURI(path, false));
		configurationRes.getContents().add(testConfigurationPackage);
		
		path = Paths.get(tdlProjectPath, testSuitePackage.getName()+ ".tdlan2").toString();
		Resource testSuitePackageRes = rs.createResource(URI.createPlatformResourceURI(path, false));
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
