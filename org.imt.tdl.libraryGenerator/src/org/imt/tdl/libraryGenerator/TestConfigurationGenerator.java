package org.imt.tdl.libraryGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.etsi.mts.tdl.Annotation;
import org.etsi.mts.tdl.AnnotationType;
import org.etsi.mts.tdl.ComponentInstance;
import org.etsi.mts.tdl.ComponentInstanceRole;
import org.etsi.mts.tdl.ComponentType;
import org.etsi.mts.tdl.Connection;
import org.etsi.mts.tdl.DataType;
import org.etsi.mts.tdl.ElementImport;
import org.etsi.mts.tdl.GateInstance;
import org.etsi.mts.tdl.GateReference;
import org.etsi.mts.tdl.GateType;
import org.etsi.mts.tdl.GateTypeKind;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TestConfiguration;
import org.etsi.mts.tdl.tdlFactory;

import com.google.inject.Injector;

public class TestConfigurationGenerator {
	private String dslName;
	private tdlFactory factory;
	private Package testConfigurationPackage;
	private CommonPackageGenerator commonPackageGenerator;
	private DSLSpecificEventsGenerator dslSpecificEventsGenerator;
	private DSLSpecificTypesGenerator dslSpecificTypesGenerator;
	
	private Map<String, GateType> gateTypes = new HashMap<String, GateType>();
	private Map<String, ComponentType> componentTypes = new HashMap<String, ComponentType>();
	private Map<String, AnnotationType> annotations = new HashMap<String, AnnotationType>();
	private Map<String, TestConfiguration> configurations = new HashMap<String, TestConfiguration>();
	
	public TestConfigurationGenerator(String dslFilePath) throws IOException {
		this.factory = tdlFactory.eINSTANCE;
		
		this.dslSpecificTypesGenerator = new DSLSpecificTypesGenerator(dslFilePath);
		System.out.println("dsl-specific types package generated successfully");
		
		this.dslSpecificEventsGenerator = this.dslSpecificTypesGenerator.getDslSpecificEventsGenerator();
		this.commonPackageGenerator = this.dslSpecificTypesGenerator.getCommonPackageGenerator();
		this.dslName = this.dslSpecificEventsGenerator.getDslName(dslFilePath);
		generateTestConfigurationPackage();
	}
	private void generateTestConfigurationPackage() {
		this.testConfigurationPackage = factory.createPackage();
		this.testConfigurationPackage.setName("testConfiguration");
		generateImports();
		generateGateTypes();
		generateComponentTypes();
		generateAnnotations();
		generateConfigurations();
	}
	private void generateImports() {
		ElementImport commonPackageImport = factory.createElementImport();
		commonPackageImport.setImportedPackage(this.commonPackageGenerator.getCommonPackage());
		ElementImport dslSpecificPackageImport = factory.createElementImport();
		dslSpecificPackageImport.setImportedPackage(this.dslSpecificEventsGenerator.getDslSpecificEventsPackage());
		this.testConfigurationPackage.getImport().add(commonPackageImport);
		this.testConfigurationPackage.getImport().add(dslSpecificPackageImport);
	}
	private void generateGateTypes() {
		GateType genericGateType = factory.createGateType();
		genericGateType.setName("genericGateType");
		genericGateType.setKind(GateTypeKind.MESSAGE);
		genericGateType.getDataType().add(this.commonPackageGenerator.getModelExecutionCommand());
		this.testConfigurationPackage.getPackagedElement().add(genericGateType);
		this.gateTypes.put(genericGateType.getName(), genericGateType);
		if (this.dslSpecificEventsGenerator.getTypesOfDslInterfaces().size()>0) {
			GateType dslSpecificGateType = factory.createGateType();
			dslSpecificGateType.setName("dslSpecificGateType");
			dslSpecificGateType.setKind(GateTypeKind.MESSAGE);
			dslSpecificGateType.getDataType().addAll(this.dslSpecificEventsGenerator.getTypesOfDslInterfaces());
			this.testConfigurationPackage.getPackagedElement().add(dslSpecificGateType);
			this.gateTypes.put(dslSpecificGateType.getName(), dslSpecificGateType);
		}
	
		GateType oclGateType = factory.createGateType();
		oclGateType.setName("oclGateType");
		oclGateType.setKind(GateTypeKind.MESSAGE);
		oclGateType.getDataType().add(this.commonPackageGenerator.getOCLType());
		this.testConfigurationPackage.getPackagedElement().add(oclGateType);
		this.gateTypes.put(oclGateType.getName(), oclGateType);
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
		GateInstance genericGate = factory.createGateInstance();
		genericGate.setName("genericGate");
		genericGate.setType(this.gateTypes.get("genericGateType"));
		component.getGateInstance().add(genericGate);

		if (this.gateTypes.get("dslSpecificGateType") != null) {
			GateInstance dslSpecificGate = factory.createGateInstance();
			dslSpecificGate.setName("dslSpecificGate");
			dslSpecificGate.setType(this.gateTypes.get("dslSpecificGateType"));
			component.getGateInstance().add(dslSpecificGate);
		}
		
		GateInstance oclGate = factory.createGateInstance();
		oclGate.setName("oclGate");
		oclGate.setType(this.gateTypes.get("oclGateType"));
		component.getGateInstance().add(oclGate);
	}
	private void generateAnnotations() {
		AnnotationType MUTPath = factory.createAnnotationType();
		MUTPath.setName("MUTPath");
		this.testConfigurationPackage.getPackagedElement().add(MUTPath);
		this.annotations.put(MUTPath.getName(), MUTPath);
		
		AnnotationType DSLName = factory.createAnnotationType();
		DSLName.setName("DSLName");
		this.testConfigurationPackage.getPackagedElement().add(DSLName);
		this.annotations.put(DSLName.getName(), DSLName);
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
		if (this.gateTypes.get("dslSpecificGateType") != null) {
			TestConfiguration dslSpecificConfiguration = factory.createTestConfiguration();
			dslSpecificConfiguration.setName("dslSpecificConfiguration");
			generateComponentInstances(dslSpecificConfiguration);
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
		mutPathAnnotation.setValue("\'TODO: Put the address of the Model-Under Test here\'");
		mutInstance.getAnnotation().add(mutPathAnnotation);
		Annotation DSLNameAnnotation = factory.createAnnotation();
		DSLNameAnnotation.setAnnotatedElement(mutInstance);
		DSLNameAnnotation.setKey(this.annotations.get("DSLName"));
		DSLNameAnnotation.setValue("\'TODO: Put the name of the DSL\'");
		mutInstance.getAnnotation().add(DSLNameAnnotation);

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
		GateInstance gate = null;
		for (int i=0; i<testerComponent.getType().getGateInstance().size();i++) {
			gate = testerComponent.getType().getGateInstance().get(i);
			if (gate.getName().equals(configurationType + "Gate")) {
				referenceToTestGate.setGate (gate);
			}
		}
		GateReference referenceToMUTGate = factory.createGateReference();
		referenceToMUTGate.setComponent(MUTComponent);
		for (int i=0; i<MUTComponent.getType().getGateInstance().size();i++) {
			gate = MUTComponent.getType().getGateInstance().get(i);
			if (gate.getName().equals(configurationType + "Gate")) {
				referenceToMUTGate.setGate (gate);
			}
		}		
		gateConnection.getEndPoint().add(referenceToTestGate);
		gateConnection.getEndPoint().add(referenceToMUTGate);
		configuration.getConnection().add(gateConnection);
	}
	
	public DSLSpecificTypesGenerator getDslSpecificTypesGenerator() {
		return this.dslSpecificTypesGenerator;
	}
	public Package getTestConfigurationPackage() {
		return this.testConfigurationPackage;
	}
	public Map<String, GateType> getGateTypes(){
		return this.gateTypes;
	}
	public Map<String, ComponentType> getComponentTypes(){
		return this.componentTypes;
	}
	public Map<String, TestConfiguration> getTestConfigurations(){
		return this.configurations;
	}
}
