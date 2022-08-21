package org.imt.tdl.libraryGenerator;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.dsl.Dsl;
import org.etsi.mts.tdl.Annotation;
import org.etsi.mts.tdl.AnnotationType;
import org.etsi.mts.tdl.ComponentInstance;
import org.etsi.mts.tdl.ComponentInstanceRole;
import org.etsi.mts.tdl.ComponentType;
import org.etsi.mts.tdl.Connection;
import org.etsi.mts.tdl.ElementImport;
import org.etsi.mts.tdl.GateInstance;
import org.etsi.mts.tdl.GateReference;
import org.etsi.mts.tdl.GateType;
import org.etsi.mts.tdl.GateTypeKind;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TestConfiguration;
import org.etsi.mts.tdl.tdlFactory;

public class TestConfigurationGenerator {
	private String dslName;
	private String dslID;
	
	private tdlFactory factory;
	private Package testConfigurationPackage;
	private CommonPackageGenerator commonPackageGenerator;
	private DSLSpecificEventsGenerator dslSpecificEventsGenerator;
	private DSLSpecificTypesGenerator dslSpecificTypesGenerator;
	
	private Map<String, GateType> gateTypes = new HashMap<String, GateType>();
	private Map<String, ComponentType> componentTypes = new HashMap<String, ComponentType>();
	private Map<String, AnnotationType> annotations = new HashMap<String, AnnotationType>();
	private Map<String, TestConfiguration> configurations = new HashMap<String, TestConfiguration>();
	
	private final static String GENERIC_GATE_TYPE = "genericGateType";
	private final static String DSL_GATE_TYPE = "reactiveGateType";
	private final static String OCL_GATE_TYPE = "oclGateType";
	private final static String GENERIC_GATE = "genericGate";
	private final static String DSL_GATE = "reactiveGate";
	private final static String OCL_GATE = "oclGate";
	
	public TestConfigurationGenerator(String dslFilePath) throws IOException {
		factory = tdlFactory.eINSTANCE;
		
		dslSpecificTypesGenerator = new DSLSpecificTypesGenerator(dslFilePath);
		System.out.println("dsl-specific types package generated successfully");
		
		dslSpecificEventsGenerator = dslSpecificTypesGenerator.getDslSpecificEventsGenerator();
		commonPackageGenerator = dslSpecificTypesGenerator.getCommonPackageGenerator();
		dslName = dslSpecificEventsGenerator.getDslName(dslFilePath);
		dslID = getDslID(dslFilePath);
		generateTestConfigurationPackage();
	}
	private void generateTestConfigurationPackage() {
		testConfigurationPackage = factory.createPackage();
		testConfigurationPackage.setName("testConfiguration");
		generateImports();
		generateGateTypes();
		generateComponentTypes();
		generateAnnotations();
		generateConfigurations();
	}
	private void generateImports() {
		ElementImport commonPackageImport = factory.createElementImport();
		commonPackageImport.setImportedPackage(commonPackageGenerator.getCommonPackage());
		ElementImport dslSpecificPackageImport = factory.createElementImport();
		dslSpecificPackageImport.setImportedPackage(dslSpecificEventsGenerator.getDslSpecificEventsPackage());
		testConfigurationPackage.getImport().add(commonPackageImport);
		testConfigurationPackage.getImport().add(dslSpecificPackageImport);
	}
	private void generateGateTypes() {
		GateType genericGateType = factory.createGateType();
		genericGateType.setName(GENERIC_GATE_TYPE);
		genericGateType.setKind(GateTypeKind.MESSAGE);
		genericGateType.getDataType().add(commonPackageGenerator.getModelExecutionCommand());
		testConfigurationPackage.getPackagedElement().add(genericGateType);
		gateTypes.put(genericGateType.getName(), genericGateType);
		if (dslSpecificEventsGenerator.getTypesOfDslInterfaces().size()>0) {
			GateType dslSpecificGateType = factory.createGateType();
			dslSpecificGateType.setName(DSL_GATE_TYPE);
			dslSpecificGateType.setKind(GateTypeKind.MESSAGE);
			dslSpecificGateType.getDataType().addAll(dslSpecificEventsGenerator.getTypesOfDslInterfaces());
			testConfigurationPackage.getPackagedElement().add(dslSpecificGateType);
			gateTypes.put(dslSpecificGateType.getName(), dslSpecificGateType);
		}
	
		GateType oclGateType = factory.createGateType();
		oclGateType.setName(OCL_GATE_TYPE);
		oclGateType.setKind(GateTypeKind.MESSAGE);
		oclGateType.getDataType().add(commonPackageGenerator.getOCLType());
		testConfigurationPackage.getPackagedElement().add(oclGateType);
		gateTypes.put(oclGateType.getName(), oclGateType);
	}
	private void generateComponentTypes() {
		ComponentType testSystem = factory.createComponentType();
		testSystem.setName("TestSystem");
		generateGateInstances(testSystem);
		testConfigurationPackage.getPackagedElement().add(testSystem);
		componentTypes.put(testSystem.getName(), testSystem);
		
		ComponentType MUT = factory.createComponentType();
		MUT.setName("MUT");
		generateGateInstances(MUT);
		testConfigurationPackage.getPackagedElement().add(MUT);
		componentTypes.put(MUT.getName(), MUT);
	}
	private void generateGateInstances(ComponentType component) {
		GateInstance genericGate = factory.createGateInstance();
		genericGate.setName(GENERIC_GATE);
		genericGate.setType(gateTypes.get(GENERIC_GATE_TYPE));
		component.getGateInstance().add(genericGate);

		if (gateTypes.get(DSL_GATE_TYPE) != null) {
			GateInstance dslSpecificGate = factory.createGateInstance();
			dslSpecificGate.setName(DSL_GATE);
			dslSpecificGate.setType(gateTypes.get(DSL_GATE_TYPE));
			component.getGateInstance().add(dslSpecificGate);
		}
		
		GateInstance oclGate = factory.createGateInstance();
		oclGate.setName(OCL_GATE);
		oclGate.setType(gateTypes.get(OCL_GATE_TYPE));
		component.getGateInstance().add(oclGate);
	}
	private void generateAnnotations() {
		AnnotationType MUTPath = factory.createAnnotationType();
		MUTPath.setName("MUTPath");
		testConfigurationPackage.getPackagedElement().add(MUTPath);
		annotations.put(MUTPath.getName(), MUTPath);
		
		AnnotationType DSLName = factory.createAnnotationType();
		DSLName.setName("DSLName");
		testConfigurationPackage.getPackagedElement().add(DSLName);
		annotations.put(DSLName.getName(), DSLName);
	}
	private void generateConfigurations() {
		//generate one generic test configuration
		TestConfiguration genericConfiguration = factory.createTestConfiguration();
		genericConfiguration.setName("genericConfiguration");
		generateComponentInstances(genericConfiguration);
		generateConnection(genericConfiguration, GENERIC_GATE);
		generateConnection(genericConfiguration, OCL_GATE);
		testConfigurationPackage.getPackagedElement().add(genericConfiguration);
		configurations.put(genericConfiguration.getName(), genericConfiguration);
		
		//if the dsl has an interface and specific gate types are generated for it,
		//a new test configuration has to be defined
		if (gateTypes.get(DSL_GATE_TYPE) != null) {
			TestConfiguration reactiveConfiguration = factory.createTestConfiguration();
			reactiveConfiguration.setName("reactiveConfiguration");
			generateComponentInstances(reactiveConfiguration);
			generateConnection(reactiveConfiguration, DSL_GATE);
			generateConnection(reactiveConfiguration, OCL_GATE);
			testConfigurationPackage.getPackagedElement().add(reactiveConfiguration);
			configurations.put(reactiveConfiguration.getName(), reactiveConfiguration);
		}
	}
	
	private void generateComponentInstances(TestConfiguration configuration) {
		//generate one component instance as TESTER
		ComponentInstance tester = factory.createComponentInstance();
		tester.setName("tester");
		tester.setRole(ComponentInstanceRole.TESTER);
		tester.setType(componentTypes.get("TestSystem"));
		configuration.getComponentInstance().add(tester);
		
		//generate one component instance for Model-Under Test (MUT) as SUT
		ComponentInstance mutInstance = factory.createComponentInstance();
		mutInstance.setName(dslName.toLowerCase());
		mutInstance.setRole(ComponentInstanceRole.SUT);
		mutInstance.setType(componentTypes.get("MUT"));
		Annotation mutPathAnnotation = factory.createAnnotation();
		mutPathAnnotation.setAnnotatedElement(mutInstance);
		mutPathAnnotation.setKey(annotations.get("MUTPath"));
		mutPathAnnotation.setValue("\'TODO: Put the address of the Model-Under Test here\'");
		mutInstance.getAnnotation().add(mutPathAnnotation);
		Annotation DSLNameAnnotation = factory.createAnnotation();
		DSLNameAnnotation.setAnnotatedElement(mutInstance);
		DSLNameAnnotation.setKey(annotations.get("DSLName"));
		DSLNameAnnotation.setValue("\'" + dslID + "\'");
		mutInstance.getAnnotation().add(DSLNameAnnotation);

		configuration.getComponentInstance().add(mutInstance);
	}
	private void generateConnection(TestConfiguration configuration, String gateName) {
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
		GateInstance gate = null;
		for (int i=0; i<testerComponent.getType().getGateInstance().size();i++) {
			gate = testerComponent.getType().getGateInstance().get(i);
			if (gate.getName().equals(gateName)) {
				referenceToTestGate.setGate (gate);
			}
		}
		GateReference referenceToMUTGate = factory.createGateReference();
		referenceToMUTGate.setComponent(MUTComponent);
		for (int i=0; i<MUTComponent.getType().getGateInstance().size();i++) {
			gate = MUTComponent.getType().getGateInstance().get(i);
			if (gate.getName().equals(gateName)) {
				referenceToMUTGate.setGate (gate);
			}
		}		
		gateConnection.getEndPoint().add(referenceToTestGate);
		gateConnection.getEndPoint().add(referenceToMUTGate);
		configuration.getConnection().add(gateConnection);
	}
	
	public DSLSpecificTypesGenerator getDslSpecificTypesGenerator() {
		return dslSpecificTypesGenerator;
	}
	public Package getTestConfigurationPackage() {
		return testConfigurationPackage;
	}
	public Map<String, GateType> getGateTypes(){
		return gateTypes;
	}
	public Map<String, ComponentType> getComponentTypes(){
		return componentTypes;
	}
	public Map<String, TestConfiguration> getTestConfigurations(){
		return configurations;
	}
	protected String getDslID(String dslFilePath) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		return dsl.getEntry("name").getValue();
	}
}
