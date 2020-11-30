package org.imt.tdl.defaultPackage.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
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
	private tdlFactory factory;
	private Package commonPackage;
	private Map<String , DataType> commonTypes = new HashMap<String , DataType>();
	private DataType oclType;
	private List<DataInstance> verdictInstances = new ArrayList<DataInstance>();
	private List<DataType> TypesForGeneralEvents = new ArrayList<DataType>();
	private Map<String, AnnotationType> annotations = new HashMap<String, AnnotationType>();
	
	private String dslName;
	private EPackage metamodelRootElement;
	private ModelUnit aleSemanticRootElement;
	private BehavioralInterface interfaceRootElement;
	private Package dslSpecificPackage;
	private Map<String, DataType> dslSpecificTypes = new HashMap<String, DataType>();
	private DataType modelState;
	private List<DataType> dslInterfaceTypes = new ArrayList<DataType>();
	
	private Package testConfigurationPackage;
	private Map<String, GateType> gateTypes = new HashMap<String, GateType>();
	private Map<String, ComponentType> componentTypes = new HashMap<String, ComponentType>();
	private Map<String, GateInstance> gateInstances = new HashMap<String, GateInstance>();
	private Map<String, TestConfiguration> configurations = new HashMap<String, TestConfiguration>();
	
	private Package testDesignPackage;
	
	public TDLCodeGenerator(String dslFilePath) {
		System.out.println("Start TDL Code generation");
		this.factory = tdlFactory.eINSTANCE;
		
		System.out.println("Start common package generation");
		generateCommonPackage();
		System.out.println("common package generated successfully");
		
		System.out.println("Start dsl-specific package generation");
		this.dslName = getDslName(dslFilePath);
		this.metamodelRootElement = getMetamodelRootElement(dslFilePath);
		//this.aleSemanticRootElement = getAleSemanticsRootElement(dslFilePath);
		//System.out.println(this.aleSemanticRootElement.getName());
		this.interfaceRootElement = getBehavioralInterfaceRootElement(dslFilePath);
		generateDSLSpecificPackage();
		System.out.println("dsl-specific package generated successfully");
		
		System.out.println("Start test configuration package generation");
		generateTestConfigurationPackage();
		System.out.println("test configuration package generated successfully");
		
		System.out.println("Start test design package generation");
		generateTestDesignPackage();
		System.out.println("test design package generated successfully");
		
		System.out.println("start saving packages");
		savePackage();
		System.out.println("all packages are saved successfully");
	}
	
	//Common package generation	
	private void generateCommonPackage(){
		this.commonPackage = factory.createPackage();
		this.commonPackage.setName("commonPackage");
		generatePrimitiveDataTypes();
		generateTypeForOCL();
		generateVerdicts();
		generateTypeForGeneralEvents();
		generateAnnotations();
	}
	private void generatePrimitiveDataTypes() {
		SimpleDataType String = factory.createSimpleDataType();
		String.setName("String");
		SimpleDataType Integer = factory.createSimpleDataType();
		Integer.setName("Integer");
		SimpleDataType Boolean = factory.createSimpleDataType();
		Boolean.setName("Boolean");
		this.commonPackage.getPackagedElement().add(String);
		this.commonPackage.getPackagedElement().add(Integer);
		this.commonPackage.getPackagedElement().add(Boolean);
		
		this.commonTypes.put(String.getName(), String);
		this.commonTypes.put(Integer.getName(), Integer);
		this.commonTypes.put(Boolean.getName(), Boolean);
	}
	private void generateTypeForOCL() {
		StructuredDataType OCL = factory.createStructuredDataType();
		OCL.setName("OCL");
		Member query = factory.createMember();
		query.setName("query");
		DataType queryType = this.commonTypes.get("String");
		query.setDataType(queryType);
		OCL.getMember().add(query);
		this.commonPackage.getPackagedElement().add(OCL);
		
		this.oclType = OCL;
	}
	private void generateVerdicts() {
		SimpleDataType Verdict = factory.createSimpleDataType();
		Verdict.setName("Verdict");
		SimpleDataInstance PASS = factory.createSimpleDataInstance();
		PASS.setName("PASS");
		PASS.setDataType(Verdict);
		SimpleDataInstance FAIL = factory.createSimpleDataInstance();
		FAIL.setName("FAIL");
		FAIL.setDataType(Verdict);
		SimpleDataInstance INCONCLUSINVE = factory.createSimpleDataInstance();
		INCONCLUSINVE.setName("INCONCLUSINVE");
		INCONCLUSINVE.setDataType(Verdict);
		this.commonPackage.getPackagedElement().add(Verdict);
		this.commonPackage.getPackagedElement().add(PASS);
		this.commonPackage.getPackagedElement().add(FAIL);
		this.commonPackage.getPackagedElement().add(INCONCLUSINVE);
		
		this.verdictInstances.add(PASS);
		this.verdictInstances.add(FAIL);
		this.verdictInstances.add(INCONCLUSINVE);
	}
	private void generateTypeForGeneralEvents() {
		SimpleDataType runModel = factory.createSimpleDataType();
		runModel.setName("runModel");
		SimpleDataInstance runMUT = factory.createSimpleDataInstance();
		runMUT.setName("runMUT");
		runMUT.setDataType(runModel);
		this.commonPackage.getPackagedElement().add(runModel);
		this.commonPackage.getPackagedElement().add(runMUT);
		
		SimpleDataType getState = factory.createSimpleDataType();
		getState.setName("getState");
		SimpleDataInstance getModelState = factory.createSimpleDataInstance();
		getModelState.setName("getModelState");
		getModelState.setDataType(getState);
		this.commonPackage.getPackagedElement().add(getState);
		this.commonPackage.getPackagedElement().add(getModelState);
		
		this.TypesForGeneralEvents.add(runModel);
		this.TypesForGeneralEvents.add(getState);
	}
	private void generateAnnotations() {
		AnnotationType MUTPath = factory.createAnnotationType();
		MUTPath.setName("MUTPath");
		this.commonPackage.getPackagedElement().add(MUTPath);
		this.annotations.put(MUTPath.getName(), MUTPath);
	}
	public void saveCommonPackage(Injector injector, ResourceSet rs) {
		Resource r = rs.createResource(URI.createURI(this.commonPackage.getName()+ ".tdlan2"));
		r.getContents().add(this.commonPackage);
		try {
			r.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		r.unload();
		rs = null;
	}
	
	//DSL-Specific package generation	
	private void generateDSLSpecificPackage(){
		this.dslSpecificPackage = factory.createPackage();
		this.dslSpecificPackage.setName(this.dslName + "-SpecificPackage");
		generateDSLSpecificImports();
		generateDSLSpecificTypes();
		generateTypeForModelState();
		if (this.interfaceRootElement != null) {
			generateTypeForDSLInterfaces();
		}
		generateTypeForSetState();
	}
	private void generateDSLSpecificImports() {
		ElementImport commonPackageImport = factory.createElementImport();
		commonPackageImport.setImportedPackage(this.commonPackage);
		this.dslSpecificPackage.getImport().add(commonPackageImport);
	}
	private void generateDSLSpecificTypes() {
		//TODO: Which entities of metamodel have to be transformed to TDL types?
	}
	private void generateTypeForDSLInterfaces() {
		AnnotationType acceptedEvent = factory.createAnnotationType();
		acceptedEvent.setName("AcceptedEvent");
		AnnotationType exposedEvent = factory.createAnnotationType();
		exposedEvent.setName("ExposedEvent");
		this.dslSpecificPackage.getPackagedElement().add(acceptedEvent);
		this.dslSpecificPackage.getPackagedElement().add(exposedEvent);
		
		for (int i=0; i<this.interfaceRootElement.getEvents().size();i++) {
			Event event = this.interfaceRootElement.getEvents().get(i);
			StructuredDataType typeForEvent = factory.createStructuredDataType();
			typeForEvent.setName(event.getName());
			Annotation annotation = factory.createAnnotation();
			if (event.getType() == EventType.ACCEPTED) {
				annotation.setKey(acceptedEvent);
			}else if (event.getType() ==  EventType.EXPOSED) {
				annotation.setKey(exposedEvent);
			}
			annotation.setAnnotatedElement(typeForEvent);
			typeForEvent.getAnnotation().add(annotation);
			for (int j=0; j<event.getParams().size();j++) {
				//the parameters are the metamodel elements that have to be transformed to TDL types
			}
			this.dslSpecificPackage.getPackagedElement().add(typeForEvent);
			this.dslInterfaceTypes.add(typeForEvent);
		}
	}
	private void generateTypeForModelState() {
		StructuredDataType modelState = factory.createStructuredDataType();
		modelState.setName("modelState");
		//TODO: Define the members based on the model state
		this.dslSpecificPackage.getPackagedElement().add(modelState);
		this.modelState = modelState;
	}
	private void generateTypeForSetState() {
		StructuredDataType setState = factory.createStructuredDataType();
		setState.setName("setState");
		Member state = factory.createMember();
		state.setName("state");
		state.setDataType(this.modelState);
		setState.getMember().add(state);
		this.dslSpecificPackage.getPackagedElement().add(setState);
		this.TypesForGeneralEvents.add(setState);
	}
	
	protected String getDslName(String dslFilePath) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		String[] dslFullName = dsl.getEntry("name").getValue().split("\\.");
		return dslFullName[dslFullName.length-1];
	}
	protected static EPackage getMetamodelRootElement(String dslFilePath) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		String metamodelPath = dsl.getEntry("ecore").getValue();
		Resource metamodelRes = (new ResourceSetImpl()).getResource(URI.createURI(metamodelPath), true);
		EPackage metamodelRootElement = (EPackage) metamodelRes.getContents().get(0);
		return metamodelRootElement;
	}
	//TODO: ModelUnit is defined in ale metamodel but Unit is used in ale files??
	protected static ModelUnit getAleSemanticsRootElement(String dslFilePath) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		String interpreterPath = dsl.getEntry("ale").getValue();
		Resource interpreterRes = (new ResourceSetImpl()).getResource(URI.createURI(interpreterPath), true);
		ModelUnit interpreterRootClass = (ModelUnit) interpreterRes.getContents().get(0);
		return interpreterRootClass;
	}
	private BehavioralInterface getBehavioralInterfaceRootElement(String dslFilePath) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		String interfacePath = dsl.getEntry("behavioralInterface").getValue();
		Resource interfaceRes = (new ResourceSetImpl()).getResource(URI.createURI(interfacePath), true);
		BehavioralInterface interfaceRootElement = (BehavioralInterface) interfaceRes.getContents().get(0);
		return interfaceRootElement;
	}
	public void saveDslSpecificPackage(Injector injector, ResourceSet rs) {
		Resource r = rs.createResource(URI.createURI(this.dslSpecificPackage.getName()+ ".tdlan2"));
		r.getContents().add(this.dslSpecificPackage);
		try {
			r.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		r.unload();
		rs = null;
	}
	
	//test configuration generation
	private void generateTestConfigurationPackage() {
		this.testConfigurationPackage = factory.createPackage();
		this.testConfigurationPackage.setName("testConfiguration");
		generateConfigurationImports();
		generateGateTypes();
		generateComponentTypes();
		generateConfigurations();
	}
	private void generateConfigurationImports() {
		ElementImport commonPackageImport = factory.createElementImport();
		commonPackageImport.setImportedPackage(this.commonPackage);
		ElementImport dslSpecificPackageImport = factory.createElementImport();
		dslSpecificPackageImport.setImportedPackage(this.dslSpecificPackage);
		this.testConfigurationPackage.getImport().add(commonPackageImport);
		this.testConfigurationPackage.getImport().add(dslSpecificPackageImport);
	}
	private void generateGateTypes() {
		GateType genericGate = factory.createGateType();
		genericGate.setName("genericGate");
		genericGate.setKind(GateTypeKind.MESSAGE);
		List<DataType> genericGateDataTypes = new ArrayList<DataType>();
		genericGateDataTypes.addAll(this.TypesForGeneralEvents);
		genericGate.getDataType().addAll(genericGateDataTypes);
		this.testConfigurationPackage.getPackagedElement().add(genericGate);
		this.gateTypes.put(genericGate.getName(), genericGate);
		
		if (this.dslInterfaceTypes.size()>0) {
			GateType dslSpecificGate = factory.createGateType();
			dslSpecificGate.setName("dslSpecificGate");
			dslSpecificGate.setKind(GateTypeKind.MESSAGE);
			dslSpecificGate.getDataType().addAll(this.dslInterfaceTypes);
			this.testConfigurationPackage.getPackagedElement().add(dslSpecificGate);
			this.gateTypes.put(dslSpecificGate.getName(), dslSpecificGate);
		}
	
		GateType oclGate = factory.createGateType();
		oclGate.setName("oclGate");
		oclGate.setKind(GateTypeKind.MESSAGE);
		oclGate.getDataType().add(this.oclType);
		this.testConfigurationPackage.getPackagedElement().add(oclGate);
		this.gateTypes.put(oclGate.getName(), oclGate);
	}
	private void generateComponentTypes() {
		ComponentType testSystem = factory.createComponentType();
		testSystem.setName("TestSystem");
		generateGateInstances(testSystem);
		this.testConfigurationPackage.getPackagedElement().add(testSystem);
		this.componentTypes.put(testSystem.getName(), testSystem);
		
		ComponentType MUT = factory.createComponentType();
		MUT.setName("MUT");
		generateGateInstances(MUT);
		this.testConfigurationPackage.getPackagedElement().add(MUT);
		this.componentTypes.put(MUT.getName(), MUT);
	}
	private void generateGateInstances(ComponentType component) {
		String gateName = "MUTGate";
		if (component.getName().contains("Test")) {
			gateName = "TestGate";
		}
		GateInstance genericGate = factory.createGateInstance();
		genericGate.setName("generic" + gateName);
		genericGate.setType(this.gateTypes.get("genericGate"));
		component.getGateInstance().add(genericGate);
		this.gateInstances.put(genericGate.getName(), genericGate);

		if (this.gateTypes.get("dslSpecificGate") != null) {
			GateInstance dslSpecificGate = factory.createGateInstance();
			dslSpecificGate.setName("dslSpecific"+ gateName);
			dslSpecificGate.setType(this.gateTypes.get("dslSpecificGate"));
			component.getGateInstance().add(dslSpecificGate);
			this.gateInstances.put(dslSpecificGate.getName(), dslSpecificGate);
		}
		
		GateInstance oclGate = factory.createGateInstance();
		oclGate.setName("ocl"+ gateName);
		oclGate.setType(this.gateTypes.get("oclGate"));
		component.getGateInstance().add(oclGate);
		this.gateInstances.put(oclGate.getName(), oclGate);
	}
	private void generateConfigurations() {
		//generate one generic test configuration
		TestConfiguration genericConfiguration = factory.createTestConfiguration();
		genericConfiguration.setName("genericConfiguration");
		generateComponentInstances(genericConfiguration);
		generateConnection(genericConfiguration, "generic");
		this.testConfigurationPackage.getPackagedElement().add(genericConfiguration);
		this.configurations.put(genericConfiguration.getName(), genericConfiguration);
		
		//if the dsl has an interface and specific gate types are generated for it,
		//a new test configuration has to be defined
		if (this.gateTypes.get("dslSpecificGate") != null) {
			TestConfiguration dslSpecificConfiguration = factory.createTestConfiguration();
			dslSpecificConfiguration.setName("dslSpecificConfiguration");
			generateComponentInstances(dslSpecificConfiguration);
			//two connections are required, one for generic communication, another for dsl-specific commands
			generateConnection(dslSpecificConfiguration, "generic");
			generateConnection(dslSpecificConfiguration, "dslSpecific");
			this.testConfigurationPackage.getPackagedElement().add(dslSpecificConfiguration);
			this.configurations.put(dslSpecificConfiguration.getName(), dslSpecificConfiguration);
		}
		
		//another test configuration for ocl
		TestConfiguration oclConfiguration = factory.createTestConfiguration();
		oclConfiguration.setName("oclConfiguration");
		generateComponentInstances(oclConfiguration);
		//two connections are required, one for generic communication, another for ocl commands
		generateConnection(oclConfiguration, "generic");
		generateConnection(oclConfiguration, "ocl");
		this.testConfigurationPackage.getPackagedElement().add(oclConfiguration);
		this.configurations.put(oclConfiguration.getName(), oclConfiguration);
	}
	private void generateComponentInstances(TestConfiguration configuration) {
		//generate one component instance as TESTER
		ComponentInstance tester = factory.createComponentInstance();
		tester.setName("tester");
		tester.setRole(ComponentInstanceRole.TESTER);
		tester.setType(this.componentTypes.get("TestSystem"));
		configuration.getComponentInstance().add(tester);
		
		//generate one component instance for Model-Under Test (MUT) as SUT
		ComponentInstance mutInstance = factory.createComponentInstance();
		mutInstance.setName(this.dslName.toLowerCase());
		mutInstance.setRole(ComponentInstanceRole.SUT);
		mutInstance.setType(this.componentTypes.get("MUT"));
		Annotation mutPathAnnotation = factory.createAnnotation();
		mutPathAnnotation.setAnnotatedElement(mutInstance);
		mutPathAnnotation.setKey(this.annotations.get("MUTPath"));
		mutPathAnnotation.setValue("TODO: Put the address of Model-Under Test here");
		mutInstance.getAnnotation().add(mutPathAnnotation);
		configuration.getComponentInstance().add(mutInstance);
	}
	private void generateConnection(TestConfiguration configuration, String configurationType) {
		//retrieve the component instances of the current test configuration
		List<ComponentInstance> configComponentInstances = configuration.getComponentInstance();
		ComponentInstance testerComponent = factory.createComponentInstance();
		ComponentInstance MUTComponent = factory.createComponentInstance();
		for (int i=0; i<configComponentInstances.size();i++) {
			if (configComponentInstances.get(i).getName().equals("tester")) {
				testerComponent = configComponentInstances.get(i);
			}else {
				MUTComponent = configComponentInstances.get(i);
			}
		}
		//generate a connection for the test configuration based on its type
		Connection gateConnection = factory.createConnection();
		GateReference referenceToTestGate = factory.createGateReference();
		referenceToTestGate.setComponent(testerComponent);
		referenceToTestGate.setGate(this.gateInstances.get(configurationType + "TestGate"));
		GateReference referenceToMUTGate = factory.createGateReference();
		referenceToMUTGate.setComponent(MUTComponent);
		referenceToMUTGate.setGate(this.gateInstances.get(configurationType + "MUTGate"));
		
		gateConnection.getEndPoint().add(referenceToTestGate);
		gateConnection.getEndPoint().add(referenceToMUTGate);
		configuration.getConnection().add(gateConnection);
	}
	public void saveTestConfigurationPackage(Injector injector, ResourceSet rs) {
		Resource r = rs.createResource(URI.createURI(this.testConfigurationPackage.getName()+ ".tdlan2"));
		r.getContents().add(this.testConfigurationPackage);
		try {
			r.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		r.unload();
		rs = null;
	}
	//Test design package generation
	private void generateTestDesignPackage() {
		this.testDesignPackage = factory.createPackage();
		this.testDesignPackage.setName("testDesignPackage-template");
		generateTestDesignImports();
	}
	private void generateTestDesignImports() {
		ElementImport commonPackageImport = factory.createElementImport();
		commonPackageImport.setImportedPackage(this.commonPackage);
		ElementImport dslSpecificPackageImport = factory.createElementImport();
		dslSpecificPackageImport.setImportedPackage(this.dslSpecificPackage);
		ElementImport testConfigurationImport = factory.createElementImport();
		testConfigurationImport.setImportedPackage(this.testConfigurationPackage);
		this.testDesignPackage.getImport().add(commonPackageImport);
		this.testDesignPackage.getImport().add(dslSpecificPackageImport);
		this.testDesignPackage.getImport().add(testConfigurationImport);
	}

	public void savePackage() {
		Injector injector = new TDLan2StandaloneSetup().createInjectorAndDoEMFRegistration();
		ResourceSet rs = injector.getInstance(ResourceSet.class);
		//this.saveCommonPackage(injector, rs);
		//this.saveDslSpecificPackage(injector, rs);
		//this.saveTestConfigurationPackage(injector, rs);
		Resource commonPackageRes = rs.createResource(URI.createURI(this.commonPackage.getName()+ ".tdlan2"));
		Resource dslSpecificPackageRes = rs.createResource(URI.createURI(this.dslSpecificPackage.getName()+ ".tdlan2"));
		Resource configurationRes = rs.createResource(URI.createURI(this.testConfigurationPackage.getName()+ ".tdlan2"));
		Resource testDesignPackageRes = rs.createResource(URI.createURI(this.testDesignPackage.getName()+ ".tdlan2"));
		commonPackageRes.getContents().add(this.commonPackage);
		dslSpecificPackageRes.getContents().add(this.dslSpecificPackage);
		configurationRes.getContents().add(this.testConfigurationPackage);
		testDesignPackageRes.getContents().add(this.testDesignPackage);
		try {
			commonPackageRes.save(null);
			dslSpecificPackageRes.save(null);
			configurationRes.save(null);
			testDesignPackageRes.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		commonPackageRes.unload();
		dslSpecificPackageRes.unload();
		configurationRes.unload();
		testDesignPackageRes.unload();
		rs = null;
	}
}
