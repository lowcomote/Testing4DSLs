package org.imt.tdl.defaultPackage.generator;

import java.io.IOException;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.imt.atl.ecore2tdl.files.Ecore2tdl;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecoretools.ale.implementation.ModelUnit;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.Event;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.EventType;
import org.eclipse.m2m.atl.common.ATLExecutionException;
import org.eclipse.m2m.atl.core.ATLCoreException;
import org.etsi.mts.tdl.Annotation;
import org.etsi.mts.tdl.AnnotationType;
import org.etsi.mts.tdl.ComponentInstance;
import org.etsi.mts.tdl.ComponentInstanceRole;
import org.etsi.mts.tdl.ComponentType;
import org.etsi.mts.tdl.Connection;
import org.etsi.mts.tdl.DataInstance;
import org.etsi.mts.tdl.DataType;
import org.etsi.mts.tdl.ElementImport;
import org.etsi.mts.tdl.GateInstance;
import org.etsi.mts.tdl.GateReference;
import org.etsi.mts.tdl.GateType;
import org.etsi.mts.tdl.GateTypeKind;
import org.etsi.mts.tdl.Member;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.SimpleDataInstance;
import org.etsi.mts.tdl.SimpleDataType;
import org.etsi.mts.tdl.StructuredDataType;
import org.etsi.mts.tdl.TDLan2StandaloneSetup;
import org.etsi.mts.tdl.TestConfiguration;
import org.etsi.mts.tdl.tdlFactory;

import com.google.inject.Injector;

public class TDLCodeGenerator {
	public static final String[] tokenNames = new String[] {
	        "Package", "{", "}", "with", "perform", "action", "(", ",", ")", "on", "test", "objectives", ":", ";", "name", "time", "label", "constraints", "Action", "alternatively", "or", "Annotation", "*", "?", "=", "assert", "otherwise", "set", "verdict", "to", "->", "[", "]", "times", "repeat", "break", "Note", "create", "of", "type", "bind", "Component", "Type", "having", "if", "else", "connect", "as", "Map", "in", ".", "new", "containing", "Use", "Signature", "Collection", "default", "+", "-", "/", "mod", ">", "<", ">=", "<=", "==", "!=", "and", "xor", "not", "size", "Import", "all", "from", "Function", "returns", "instance", "returned", "Predefined", "gate", "Gate", "accepts", "sends", "triggers", "calls", "responds", "response", "interrupt", "optional", "mapped", "omit", "argument", "optionally", "run", "parallel", "parameter", "every", "component", "is", "quiet", "for", "terminate", "where", "it", "assigned", "Test", "Configuration", "Description", "Implementation", "uses", "configuration", "execute", "bindings", "Objective", "description", "Time", "out", "timer", "start", "stop", "variable", "waits", "extends", "SUT", "Tester", "Message", "Procedure", "In", "Out", "Exception", "last", "previous", "first"
	    };
	private CommonPackageGenerator commonPackageGenerator;
	private DSLSpecificPackageGenerator dslSpecificPackageGenerator;
	private RequiredTypesGenerator requiredTypesGenerator; 
	private TestConfigurationGenerator testConfigurationPackageGenerator;
	private TestDesignPackageGenerator testDesignPackageGenerator;
	
	public TDLCodeGenerator(String dslFilePath, String tdlProjectPath) throws IOException {
		System.out.println("Start TDL Code generation");
		
		this.testDesignPackageGenerator = new TestDesignPackageGenerator(dslFilePath,tdlProjectPath);
		System.out.println("test design package generated successfully");
		
		this.testConfigurationPackageGenerator = this.testDesignPackageGenerator.getTestConfigurationGenerator();
		this.requiredTypesGenerator = this.testConfigurationPackageGenerator.getRequiredTypesGenerator();
		this.dslSpecificPackageGenerator = this.requiredTypesGenerator.getDSLSpecificPackageGenerator();
		this.commonPackageGenerator = this.requiredTypesGenerator.getCommonPackageGenerator();
		
		System.out.println("start saving packages");
		savePackages(tdlProjectPath);
		System.out.println("all packages are saved successfully");
	}

	public void savePackages(String tdlProjectPath) throws IOException {
		Injector injector = new TDLan2StandaloneSetup().createInjectorAndDoEMFRegistration();
		ResourceSet rs = injector.getInstance(ResourceSet.class);
		
		Resource commonPackageRes = rs.createResource(URI.createURI(tdlProjectPath + "/generated/" + this.commonPackageGenerator.getCommonPackage().getName()+ ".tdlan2"));
		Resource dslSpecificPackageRes = rs.createResource(URI.createURI(tdlProjectPath + "/generated/" + this.dslSpecificPackageGenerator.getDSLSpecificPackage().getName()+ ".tdlan2"));
		Resource requiredTypesRes = rs.createResource(URI.createURI(tdlProjectPath + "/generated/" + this.requiredTypesGenerator.getRequiredTypesPackage().getName()+ ".tdlan2"));
		Resource configurationRes = rs.createResource(URI.createURI(tdlProjectPath + "/generated/" + this.testConfigurationPackageGenerator.getTestConfigurationPackage().getName()+ ".tdlan2"));
		Resource testDesignPackageRes = rs.createResource(URI.createURI(tdlProjectPath + "/generated/" + this.testDesignPackageGenerator.getTestDesignPackage().getName()+ ".tdlan2"));
		
		commonPackageRes.getContents().add(this.commonPackageGenerator.getCommonPackage());
		dslSpecificPackageRes.getContents().add(this.dslSpecificPackageGenerator.getDSLSpecificPackage());
		requiredTypesRes.getContents().add(this.requiredTypesGenerator.getRequiredTypesPackage());
		configurationRes.getContents().add(this.testConfigurationPackageGenerator.getTestConfigurationPackage());
		testDesignPackageRes.getContents().add(this.testDesignPackageGenerator.getTestDesignPackage());
		
		commonPackageRes.save(null);
		dslSpecificPackageRes.save(null);
		requiredTypesRes.save(null);
		configurationRes.save(null);
		testDesignPackageRes.save(null);
		
		commonPackageRes.unload();
		dslSpecificPackageRes.unload();
		requiredTypesRes.unload();
		configurationRes.unload();
		testDesignPackageRes.unload();
		rs = null;
	}
}
