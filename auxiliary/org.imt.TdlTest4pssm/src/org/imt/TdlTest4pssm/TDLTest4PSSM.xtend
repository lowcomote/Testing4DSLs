package org.imt.TdlTest4pssm

import java.io.File

import java.io.IOException
import java.util.Collections
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.BehavioralInterface
import org.etsi.mts.tdl.Annotation
import org.etsi.mts.tdl.AnnotationType
import org.etsi.mts.tdl.ComponentInstance
import org.etsi.mts.tdl.ComponentInstanceRole
import org.etsi.mts.tdl.ComponentType
import org.etsi.mts.tdl.Connection
import org.etsi.mts.tdl.ElementImport
import org.etsi.mts.tdl.GateReference
import org.etsi.mts.tdl.LiteralValueUse
import org.etsi.mts.tdl.MemberAssignment
import org.etsi.mts.tdl.Package
import org.etsi.mts.tdl.StructuredDataInstance
import org.etsi.mts.tdl.StructuredDataType
import org.etsi.mts.tdl.TestConfiguration
import org.etsi.mts.tdl.TestDescription
import org.etsi.mts.tdl.tdlFactory
import org.etsi.mts.tdl.tdlPackage
import org.etsi.mts.tdl.util.tdlResourceFactoryImpl
import org.imt.pssm.model.statemachines.Behavior
import org.imt.pssm.model.statemachines.Signal
import org.imt.pssm.model.statemachines.StateMachine
import org.imt.pssm.model.statemachines.StatemachinesPackage
import org.etsi.mts.tdl.BehaviourDescription
import org.etsi.mts.tdl.DataInstanceUse
import org.etsi.mts.tdl.DataInstance
import org.etsi.mts.tdl.Message

class TDLTest4PSSM {
	def static void main(String[] args) {
		
	}
	val static PLUGIN_PATH = "/org.imt.TdlTest4pssm"
	
	def static URI getBehavioralInterfaceURI() {
		URI::createFileURI(PLUGIN_PATH + "/InterpretedStateMachines.bi")
	}
	def static URI getStateMachineURI(StateMachine stateMachine) {
		URI::createFileURI(PLUGIN_PATH + "/models/" + stateMachine.name + ".xmi")
	}
	def static URI getTDLPackageURI(String packageName) {
		URI::createFileURI(PLUGIN_PATH + "/generatedTDLPackages/" + packageName + ".tdlan2")
	}
	def static URI getTDLTestSuiteURI() {
		URI::createFileURI(PLUGIN_PATH + "/TDLTestSuite.xmi")
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
	
	static tdlFactory TDL_FACTORY = tdlFactory.eINSTANCE
	private var Package commonPackage
	private var Package pssmTypesPackage
	private var Package pssmEventsPackage
	private var Package testConfigurationPackage 
	private var Package tdlTestSutiePackage 
	
	def void transformTestSuite() {
		val rs = new ResourceSetImpl
		//load the existing TDL packages (generated previously)
		loadTdlPackages
		generateTDLTestSuitePackage
		
		val p = StatemachinesPackage.eINSTANCE
		rs.getPackageRegistry().put(p.getNsURI(), p);
		rs.resourceFactoryRegistry.extensionToFactoryMap.put("xmi", new XMIResourceFactoryImpl)
		
		val pssmModelsPath = "platform:/resource" + PLUGIN_PATH + "/models"
		val File pssmModelsFolder = new File(pssmModelsPath);
		for (File pssmModel : pssmModelsFolder.listFiles()) {
			val res = rs.getResource(URI.createFileURI(pssmModel.path), true)
			val stateMachine = res.contents.get(0).eContents.get(0) as StateMachine
			generateTestDescription(stateMachine) 
		}
		savePackages
	}
	
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
	def TestDescription generateTestDescription (StateMachine stateMachine){
		generateDataInstances(stateMachine)
		val configuration = generateTestConfiguration(stateMachine)
		
		var TestDescription testCase = TDL_FACTORY.createTestDescription
		testCase.name = stateMachine.name + "_test"
		testCase.testConfiguration = configuration
		var behaviorDescription = TDL_FACTORY.createBehaviourDescription
		testCase.behaviourDescription = behaviorDescription
		var behavior = TDL_FACTORY.createCompoundBehaviour
		behaviorDescription.behaviour = behavior
		var block = TDL_FACTORY.createBlock
		behavior.block = block

		//the first message should send the _run event
		generateMessage4runEvent(stateMachine, configuration)
		
		
		//TODO: generating messages based on the pssm model
		//generateMessage4signal_receivedEvent(stateMachine, signal, configuration)
		//generateMessage4behavior_effectedEvent(behavior, configuration)
		return null
	}

	def void generateDataInstances(StateMachine stateMachine){
		generateStateMachineTDLInstance(stateMachine)
		//signals are defined outside the statemachine
		stateMachine.eContainer.eAllContents.filter[e | e instanceof Signal].forEach[s | generateSignalTDLInstance(s as Signal)]
		stateMachine.eAllContents.filter[e | e instanceof Behavior].forEach[b | generateBehaviorTDLInstance(b as Behavior)]
	}
	
	def void generateStateMachineTDLInstance(StateMachine stateMachine){
		//generate one data instance representing the stateMachine
		val stateMachineTDLType = pssmTypesPackage.packagedElement.findFirst[e | e.name.equals("StateMachine")] as StructuredDataType
		var StructuredDataInstance stateMachineInstance = TDL_FACTORY.createStructuredDataInstance()
		stateMachineInstance.setName(stateMachine.name)
		stateMachineInstance.setDataType(stateMachineTDLType)
		
		val member = stateMachineTDLType.allMembers.findFirst[m | m.name.equals("_name")]
		var MemberAssignment memberAssign = TDL_FACTORY.createMemberAssignment
		memberAssign.setMember(member)
		var LiteralValueUse valueUse = TDL_FACTORY.createLiteralValueUse
		valueUse.value = stateMachine.name
		memberAssign.setMemberSpec(valueUse);
		stateMachineInstance.getMemberAssignment().add(memberAssign);
		tdlTestSutiePackage.packagedElement.add(stateMachineInstance)
	}
	
	def void generateSignalTDLInstance(Signal signal){
		if (tdlTestSutiePackage.packagedElement.findFirst[e | e.name.equals(signal.name)] != null){
			//generate one data instance representing the signals
			val signalTDLType = pssmTypesPackage.packagedElement.findFirst[e | e.name.equals("Signal")] as StructuredDataType
			var StructuredDataInstance signalInstance = TDL_FACTORY.createStructuredDataInstance()
			signalInstance.setName(signal.name)
			signalInstance.setDataType(signalTDLType)
			
			val member = signalTDLType.allMembers.findFirst[m | m.name.equals("_name")]
			var MemberAssignment memberAssign = TDL_FACTORY.createMemberAssignment
			memberAssign.setMember(member)
			var LiteralValueUse valueUse = TDL_FACTORY.createLiteralValueUse
			valueUse.value = signal.name
			memberAssign.setMemberSpec(valueUse);
			signalInstance.getMemberAssignment().add(memberAssign)
			tdlTestSutiePackage.packagedElement.add(signalInstance)
			
			//TODO: check if the signal has attributes
		}	
	}
	
	def void generateBehaviorTDLInstance(Behavior behavior){
		if (tdlTestSutiePackage.packagedElement.findFirst[e | e.name.equals(behavior.name)] != null){
			//generate one data instance representing the behaviors
			val behaviorTDLType = pssmTypesPackage.packagedElement.findFirst[e | e.name.equals("Behavior")] as StructuredDataType
			var StructuredDataInstance behaviorInstance = TDL_FACTORY.createStructuredDataInstance()
			behaviorInstance.setName(behavior.name)
			behaviorInstance.setDataType(behaviorTDLType)
			
			val member = behaviorTDLType.allMembers.findFirst[m | m.name.equals("_name")]
			var MemberAssignment memberAssign = TDL_FACTORY.createMemberAssignment
			memberAssign.setMember(member)
			var LiteralValueUse valueUse = TDL_FACTORY.createLiteralValueUse
			valueUse.value = behavior.name
			memberAssign.setMemberSpec(valueUse);
			behaviorInstance.getMemberAssignment().add(memberAssign)
			tdlTestSutiePackage.packagedElement.add(behaviorInstance)	
		}
	}
	
	def TestConfiguration generateTestConfiguration(StateMachine stateMachine){
		//generate a specific test configuration for each PSSM model
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
		mutPathAnnotation.setKey(MUTPathAnnotation as AnnotationType)
		mutPathAnnotation.setValue("\'" + stateMachine.stateMachineURI.toString + "\'")
		mutInstance.getAnnotation().add(mutPathAnnotation);
		
		var Annotation dslPathAnnotation = TDL_FACTORY.createAnnotation()
		dslPathAnnotation.setAnnotatedElement(mutInstance);
		val DSLPathAnnotation = testConfigurationPackage.packagedElement.findFirst[
			e | e instanceof AnnotationType && e.name.equals("DSLPath")
		]
		dslPathAnnotation.setKey(DSLPathAnnotation as AnnotationType)
		dslPathAnnotation.setValue("\'platform:/plugin/org.imt.pssm.xdsml/InterpretedStatemachines.dsl'")
		mutInstance.getAnnotation().add(dslPathAnnotation)
		dslSpecificConfiguration.getComponentInstance().add(mutInstance)
		
		//generate connection between the gates
		var Connection gateConnection = TDL_FACTORY.createConnection()
		var GateReference referenceToTestGate = TDL_FACTORY.createGateReference()
		referenceToTestGate.setComponent(testerInstance)
		referenceToTestGate.setGate(testComponent.allGates.findFirst[g | g.name.equals("dslSpecificTestGate")])
		var GateReference referenceToMUTGate = TDL_FACTORY.createGateReference()
		referenceToMUTGate.setComponent(mutInstance)
		referenceToMUTGate.setGate(testComponent.allGates.findFirst[g | g.name.equals("dslSpecificMUTGate")])
		
		gateConnection.getEndPoint().add(referenceToTestGate)
		gateConnection.getEndPoint().add(referenceToMUTGate)
		dslSpecificConfiguration.getConnection().add(gateConnection)
		
		testConfigurationPackage.getPackagedElement().add(dslSpecificConfiguration)
		return dslSpecificConfiguration
	}
	def Message generateMessage4runEvent(StateMachine stateMachine, TestConfiguration configuration){
		val testerGate = configuration.connection.get(0).endPoint.findFirst[g | g.gate.name.equals("dslSpecificTestGate")]
		val mutGate = configuration.connection.get(0).endPoint.findFirst[g | g.gate.name.equals("dslSpecificMUTGate")]
		var mutTarget = TDL_FACTORY.createTarget
		mutTarget.targetGate = mutGate
		
		var runMessage = TDL_FACTORY.createMessage
		runMessage.sourceGate = testerGate
		runMessage.target.add(mutTarget)
		var messageArgument = TDL_FACTORY.createDataInstanceUse
		val runEventInstance = pssmEventsPackage.packagedElement.findFirst[e | 
			e instanceof StructuredDataInstance && e.name.equals("_run")
		] as StructuredDataInstance
		messageArgument.dataInstance = runEventInstance
		var argParameter = TDL_FACTORY.createParameterBinding
		argParameter.parameter = (runEventInstance.dataType as StructuredDataType).member.findFirst[m |
			m.name.equals("state_machine")
		]
		var parameterValue = TDL_FACTORY.createDataInstanceUse
		parameterValue.dataInstance = tdlTestSutiePackage.packagedElement.findFirst[i | 
			i instanceof DataInstance && i.name.equals(stateMachine.name)
		] as DataInstance
		messageArgument.argument.add(argParameter)
		return runMessage
	}
	
	def Message generateMessage4signal_receivedEvent(StateMachine stateMachine, Signal signal, TestConfiguration configuration){
		val testerGate = configuration.connection.get(0).endPoint.findFirst[g | g.gate.name.equals("dslSpecificTestGate")]
		val mutGate = configuration.connection.get(0).endPoint.findFirst[g | g.gate.name.equals("dslSpecificMUTGate")]
		var mutTarget = TDL_FACTORY.createTarget
		mutTarget.targetGate = mutGate
		
		var signal_receivedMessage = TDL_FACTORY.createMessage
		signal_receivedMessage.sourceGate = testerGate
		signal_receivedMessage.target.add(mutTarget)
		
		var messageArgument = TDL_FACTORY.createDataInstanceUse
		val signal_receivedEventInstance = pssmEventsPackage.packagedElement.findFirst[e | 
			e instanceof StructuredDataInstance && e.name.equals("signal_received")
		] as StructuredDataInstance
		messageArgument.dataInstance = signal_receivedEventInstance
		
		var argParameter1 = TDL_FACTORY.createParameterBinding
		argParameter1.parameter = (signal_receivedEventInstance.dataType as StructuredDataType).member.findFirst[m |
			m.name.equals("state_machine")
		]
		var parameterValue1 = TDL_FACTORY.createDataInstanceUse
		parameterValue1.dataInstance = tdlTestSutiePackage.packagedElement.findFirst[i | 
			i instanceof DataInstance && i.name.equals(stateMachine.name)
		] as DataInstance
		messageArgument.argument.add(argParameter1)
		
		var argParameter2 = TDL_FACTORY.createParameterBinding
		argParameter1.parameter = (signal_receivedEventInstance.dataType as StructuredDataType).member.findFirst[m |
			m.name.equals("signal")
		]
		var parameterValue2 = TDL_FACTORY.createDataInstanceUse
		parameterValue2.dataInstance = tdlTestSutiePackage.packagedElement.findFirst[i | 
			i instanceof DataInstance && i.name.equals(signal.name)
		] as DataInstance
		messageArgument.argument.add(argParameter2)
		
		return signal_receivedMessage
	}
	
		def Message generateMessage4behavior_effectedEvent(Behavior behavior, TestConfiguration configuration){
		val testerGate = configuration.connection.get(0).endPoint.findFirst[g | g.gate.name.equals("dslSpecificTestGate")]
		val mutGate = configuration.connection.get(0).endPoint.findFirst[g | g.gate.name.equals("dslSpecificMUTGate")]
		var testerTarget = TDL_FACTORY.createTarget
		testerTarget.targetGate = testerGate
		
		var behavior_effectedMessage = TDL_FACTORY.createMessage
		behavior_effectedMessage.sourceGate = mutGate
		behavior_effectedMessage.target.add(testerTarget)
		var messageArgument = TDL_FACTORY.createDataInstanceUse
		val runEventInstance = pssmEventsPackage.packagedElement.findFirst[e | 
			e instanceof StructuredDataInstance && e.name.equals("behavior_effected")
		] as StructuredDataInstance
		messageArgument.dataInstance = runEventInstance
		var argParameter = TDL_FACTORY.createParameterBinding
		argParameter.parameter = (runEventInstance.dataType as StructuredDataType).member.findFirst[m |
			m.name.equals("behavior")
		]
		var parameterValue = TDL_FACTORY.createDataInstanceUse
		parameterValue.dataInstance = tdlTestSutiePackage.packagedElement.findFirst[i | 
			i instanceof DataInstance && i.name.equals(behavior.name)
		] as DataInstance
		messageArgument.argument.add(argParameter)
		return behavior_effectedMessage
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