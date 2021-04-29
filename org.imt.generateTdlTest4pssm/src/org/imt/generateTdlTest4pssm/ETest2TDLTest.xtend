package org.imt.generateTdlTest4pssm

import java.io.IOException
import java.util.Collections
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.BehavioralInterface
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence
import org.eclipse.gemoc.executionframework.event.model.event.EventPackage
import org.eclipse.gemoc.executionframework.event.testsuite.TestCase
import org.eclipse.gemoc.executionframework.event.testsuite.TestSuite
import org.eclipse.gemoc.executionframework.event.testsuite.TestsuitePackage
import org.eclipse.gemoc.executionframework.value.model.value.ValuePackage
import org.etsi.mts.tdl.Annotation
import org.etsi.mts.tdl.AnnotationType
import org.etsi.mts.tdl.ComponentInstance
import org.etsi.mts.tdl.ComponentInstanceRole
import org.etsi.mts.tdl.ComponentType
import org.etsi.mts.tdl.Connection
import org.etsi.mts.tdl.ElementImport
import org.etsi.mts.tdl.GateReference
import org.etsi.mts.tdl.Message
import org.etsi.mts.tdl.Package
import org.etsi.mts.tdl.TestConfiguration
import org.etsi.mts.tdl.TestDescription
import org.etsi.mts.tdl.tdlFactory
import org.etsi.mts.tdl.tdlPackage
import org.etsi.mts.tdl.util.tdlResourceFactoryImpl
import org.imt.pssm.model.statemachines.StateMachine
import org.imt.pssm.model.statemachines.StatemachinesPackage

class ETest2TDLTest {
	def static void main(String[] args) {
		
	}
	def static URI getBehavioralInterfaceURI() {
		URI::createFileURI("/org.imt.generateTdlTest4pssm/InterpretedStateMachines.bi")
	}
	def static URI getPSSMTestSuiteURI() {
		URI::createFileURI("/org.imt.generateTdlTest4pssm/models/StateMachineTestSuite.xmi")
	}
	def static URI getStateMachineURI(StateMachine stateMachine) {
		URI::createFileURI("/org.imt.generateTdlTest4pssm/models/" + stateMachine.name + ".xmi")
	}
	def static URI getTDLPackageURI(String packageName) {
		URI::createFileURI("/org.imt.generateTdlTest4pssm/generatedTDLPackages/" + packageName + ".tdlan2")
	}
	def static URI getTDLTestSuiteURI() {
		URI::createFileURI("/org.imt.generateTdlTest4pssm/TDLTestSuite.xmi")
	}
	
	def static BehavioralInterface loadBehavioralInterface(URI uri, ResourceSet resourceSet) {
		val Resource res = resourceSet.getResource(uri, true)
		try {
			res.load(Collections.emptyMap())
		} catch (IOException e) {
			e.printStackTrace()
		}
		return (res.getContents().get(0)) as BehavioralInterface
	}
	def static TestSuite getPSSMTestSuite() {
		val rs = new ResourceSetImpl
		val p1 = TestsuitePackage.eINSTANCE
		val p2 = EventPackage.eINSTANCE
		val p3 = StatemachinesPackage.eINSTANCE
		val p4 = ValuePackage.eINSTANCE
		rs.getPackageRegistry().put(p1.getNsURI(), p1);
		rs.getPackageRegistry().put(p2.getNsURI(), p2);
		rs.getPackageRegistry().put(p3.getNsURI(), p3);
		rs.getPackageRegistry().put(p4.getNsURI(), p4);
		rs.resourceFactoryRegistry.extensionToFactoryMap.put("xmi", new XMIResourceFactoryImpl)
		val res = rs.getResource(PSSMTestSuiteURI, true)
		return res.contents.head as TestSuite
	}
	
	static tdlFactory TDL_FACTORY = tdlFactory.eINSTANCE
	private var Package commonPackage
	private var Package pssmTypesPackage
	private var Package pssmEventsPackage
	private var Package testConfigurationPackage 
	private var Package tdlTestSutiePackage 
	
	def void loadTdlPackages(){
		val rs = new ResourceSetImpl
		val p = tdlPackage.eINSTANCE
		rs.getPackageRegistry().put(p.getNsURI(), p)
		rs.resourceFactoryRegistry.extensionToFactoryMap.put("tdlan2", new tdlResourceFactoryImpl)
		var res = rs.getResource(getTDLPackageURI("common"), true)
		commonPackage = res.contents.head as Package
		res = rs.getResource(getTDLPackageURI("pssmTypes"), true)
		pssmTypesPackage = res.contents.head as Package
		res = rs.getResource(getTDLPackageURI("pssmEvents"), true)
		pssmEventsPackage = res.contents.head as Package
		res = rs.getResource(getTDLPackageURI("testConfiguration"), true)
		testConfigurationPackage = res.contents.head as Package
	}
	def void transformTestSuite() {
		val rs = new ResourceSetImpl
		//load the existing TDL packages (generated previously)
		loadTdlPackages
		//perform the transformation
		generateTDLTestSuitePackage
		val testSuite = PSSMTestSuite
		testSuite.testCases.forEach[c|tdlTestSutiePackage.packagedElement.add(testCase2testDescription(c))]

		savePackages
	}
	def void generateTDLTestSuitePackage(){
		tdlTestSutiePackage = TDL_FACTORY.createPackage	
		tdlTestSutiePackage.name = "PSSMTestSuite"
		//generate imports
		var ElementImport commonPackageImport = TDL_FACTORY.createElementImport();
		commonPackageImport.setImportedPackage(commonPackage);
		var ElementImport dslSpecificTypesPackageImport = TDL_FACTORY.createElementImport();
		dslSpecificTypesPackageImport.setImportedPackage(pssmTypesPackage);
		var ElementImport dslSpecificEventsPackageImport = TDL_FACTORY.createElementImport();
		dslSpecificEventsPackageImport.setImportedPackage(pssmEventsPackage);
		var ElementImport testConfigurationImport = TDL_FACTORY.createElementImport();
		testConfigurationImport.setImportedPackage(testConfigurationPackage);
		tdlTestSutiePackage.getImport().add(commonPackageImport);
		tdlTestSutiePackage.getImport().add(dslSpecificTypesPackageImport);
		tdlTestSutiePackage.getImport().add(dslSpecificEventsPackageImport);
		tdlTestSutiePackage.getImport().add(testConfigurationImport);
	}
	def TestDescription testCase2testDescription (TestCase testcase){
		//generate a specific test configuration for each test case (for each PSSM model)
		generateTestConfiguration(testcase.model as StateMachine)
		//parse the expected trace
		//generate tdl messages in the order of the expected trace
		return null
	}
	def Message eventOccurance2message (EventOccurrence scenario){
		val rs = new ResourceSetImpl
		val bi = loadBehavioralInterface(behavioralInterfaceURI, rs)
		return null
	}
	def TestConfiguration generateTestConfiguration(StateMachine stateMachine){
		//generate one test configuration per statemachine
		var TestConfiguration dslSpecificConfiguration = TDL_FACTORY.createTestConfiguration();
		dslSpecificConfiguration.setName("configuration4" + stateMachine.name);
		//generate one component instance as TESTER
		var ComponentInstance testerInstance = TDL_FACTORY.createComponentInstance();
		testerInstance.setName("tester");
		testerInstance.setRole(ComponentInstanceRole.TESTER);
		val testComponent = testConfigurationPackage.packagedElement.findFirst[
			e | e instanceof ComponentType && e.name.equals("TestSystem")
		] as ComponentType
		testerInstance.setType(testComponent);
		dslSpecificConfiguration.getComponentInstance().add(testerInstance);
		
		//generate one component instance for Model-Under Test (MUT) as SUT
		//that has annotations for MUTPath and DSLPath
		var ComponentInstance mutInstance = TDL_FACTORY.createComponentInstance();
		mutInstance.setName("PSSM");
		mutInstance.setRole(ComponentInstanceRole.SUT);
		val mutComponent = testConfigurationPackage.packagedElement.findFirst[
			e | e instanceof ComponentType && e.name.equals("MUT")
		] as ComponentType
		mutInstance.setType(mutComponent);
		
		var Annotation mutPathAnnotation = TDL_FACTORY.createAnnotation();
		mutPathAnnotation.setAnnotatedElement(mutInstance);
		val MUTPathAnnotation = testConfigurationPackage.packagedElement.findFirst[
			e | e instanceof AnnotationType && e.name.equals("MUTPath")
		]
		mutPathAnnotation.setKey(MUTPathAnnotation as AnnotationType);
		mutPathAnnotation.setValue("\'" + stateMachine.stateMachineURI.toString + "\'");
		mutInstance.getAnnotation().add(mutPathAnnotation);
		
		var Annotation dslPathAnnotation = TDL_FACTORY.createAnnotation();
		dslPathAnnotation.setAnnotatedElement(mutInstance);
		val DSLPathAnnotation = testConfigurationPackage.packagedElement.findFirst[
			e | e instanceof AnnotationType && e.name.equals("DSLPath")
		]
		dslPathAnnotation.setKey(DSLPathAnnotation as AnnotationType);
		dslPathAnnotation.setValue("\'platform:/plugin/org.imt.pssm.xdsml/InterpretedStatemachines.dsl'");
		mutInstance.getAnnotation().add(dslPathAnnotation);
		dslSpecificConfiguration.getComponentInstance().add(mutInstance);
		
		//generate connection between the gates
		var Connection gateConnection = TDL_FACTORY.createConnection();
		var GateReference referenceToTestGate = TDL_FACTORY.createGateReference();
		referenceToTestGate.setComponent(testerInstance);
		referenceToTestGate.setGate(testComponent.allGates.findFirst[g | g.name.equals("dslSpecificTestGate")]);
		var GateReference referenceToMUTGate = TDL_FACTORY.createGateReference();
		referenceToMUTGate.setComponent(mutInstance);
		referenceToMUTGate.setGate(testComponent.allGates.findFirst[g | g.name.equals("dslSpecificMUTGate")]);
		
		gateConnection.getEndPoint().add(referenceToTestGate);
		gateConnection.getEndPoint().add(referenceToMUTGate);
		dslSpecificConfiguration.getConnection().add(gateConnection);
		
		testConfigurationPackage.getPackagedElement().add(dslSpecificConfiguration)
		return dslSpecificConfiguration
	}
	
	def void savePackages(){
		val rs = new ResourceSetImpl
		val Resource commonRes = rs.createResource(getTDLPackageURI("common"));
		val Resource pssmTypesRes = rs.createResource(getTDLPackageURI("pssmTypes"));
		val Resource pssmEventsRes = rs.createResource(getTDLPackageURI("pssmEvents"));
		val Resource configurationRes = rs.createResource(getTDLPackageURI("pssmConfiguration"));
		val Resource testSuiteRes = rs.createResource(TDLTestSuiteURI);

		commonRes.getContents().add(commonPackage);
		pssmTypesRes.getContents().add(pssmTypesPackage);
		pssmEventsRes.getContents().add(pssmEventsPackage);
		configurationRes.getContents().add(testConfigurationPackage);
		testSuiteRes.getContents().add(tdlTestSutiePackage);
		
		commonRes.save(null);
		pssmTypesRes.save(null);
		pssmEventsRes.save(null);
		configurationRes.save(null);
		testSuiteRes.save(null);
		
		commonRes.unload();
		pssmTypesRes.unload();
		pssmEventsRes.unload();
		configurationRes.unload();
		testSuiteRes.unload();
	}
}