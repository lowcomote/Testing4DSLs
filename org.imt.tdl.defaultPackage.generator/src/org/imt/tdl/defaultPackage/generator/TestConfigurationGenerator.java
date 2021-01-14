package org.imt.tdl.defaultPackage.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.etsi.mts.tdl.Annotation;
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
	private Map<String, GateInstance> gateInstances = new HashMap<String, GateInstance>();
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
		GateType genericGate = factory.createGateType();
		genericGate.setName("genericGate");
		genericGate.setKind(GateTypeKind.MESSAGE);
		List<DataType> genericGateDataTypes = new ArrayList<DataType>();
		genericGateDataTypes.addAll(this.commonPackageGenerator.getTypesOfGeneralEvents());
		genericGateDataTypes.addAll(this.dslSpecificEventsGenerator.getTypesOfGeneralEvents());
		genericGate.getDataType().addAll(genericGateDataTypes);
		this.testConfigurationPackage.getPackagedElement().add(genericGate);
		this.gateTypes.put(genericGate.getName(), genericGate);
		
		if (this.dslSpecificEventsGenerator.getTypesOfDslInterfaces().size()>0) {
			GateType dslSpecificGate = factory.createGateType();
			dslSpecificGate.setName("dslSpecificGate");
			dslSpecificGate.setKind(GateTypeKind.MESSAGE);
			dslSpecificGate.getDataType().addAll(this.dslSpecificEventsGenerator.getTypesOfDslInterfaces());
			this.testConfigurationPackage.getPackagedElement().add(dslSpecificGate);
			this.gateTypes.put(dslSpecificGate.getName(), dslSpecificGate);
		}
	
		GateType oclGate = factory.createGateType();
		oclGate.setName("oclGate");
		oclGate.setKind(GateTypeKind.MESSAGE);
		oclGate.getDataType().add(this.commonPackageGenerator.getOCLType());
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
		mutPathAnnotation.setKey(this.commonPackageGenerator.getAnnotations().get("MUTPath"));
		mutPathAnnotation.setValue("\'TODO: Put the address of Model-Under Test here\'");
		mutInstance.getAnnotation().add(mutPathAnnotation);
		Annotation dslPathAnnotation = factory.createAnnotation();
		dslPathAnnotation.setAnnotatedElement(mutInstance);
		dslPathAnnotation.setKey(this.commonPackageGenerator.getAnnotations().get("DSLPath"));
		dslPathAnnotation.setValue("\'platform:/plugin/...TODO: complete the address of .dsl file...\'");
		mutInstance.getAnnotation().add(dslPathAnnotation);

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
