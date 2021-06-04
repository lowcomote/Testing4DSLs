package org.imt.generateTdlTest4pssm

import java.io.File
import java.io.IOException
import java.util.ArrayList
import java.util.Collections
import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.BehavioralInterface
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrenceType
import org.eclipse.gemoc.executionframework.event.model.event.EventPackage
import org.eclipse.gemoc.executionframework.event.testsuite.TestCase
import org.eclipse.gemoc.executionframework.event.testsuite.TestSuite
import org.eclipse.gemoc.executionframework.event.testsuite.TestsuitePackage
import org.eclipse.gemoc.executionframework.value.model.value.SingleObjectValue
import org.eclipse.gemoc.executionframework.value.model.value.SingleReferenceValue
import org.eclipse.gemoc.executionframework.value.model.value.ValuePackage
import org.etsi.mts.tdl.Annotation
import org.etsi.mts.tdl.AnnotationType
import org.etsi.mts.tdl.BehaviourDescription
import org.etsi.mts.tdl.Block
import org.etsi.mts.tdl.ComponentInstance
import org.etsi.mts.tdl.ComponentInstanceRole
import org.etsi.mts.tdl.ComponentType
import org.etsi.mts.tdl.CompoundBehaviour
import org.etsi.mts.tdl.Connection
import org.etsi.mts.tdl.DataInstanceUse
import org.etsi.mts.tdl.ElementImport
import org.etsi.mts.tdl.GateReference
import org.etsi.mts.tdl.LiteralValueUse
import org.etsi.mts.tdl.MemberAssignment
import org.etsi.mts.tdl.Message
import org.etsi.mts.tdl.Package
import org.etsi.mts.tdl.ParameterBinding
import org.etsi.mts.tdl.StructuredDataInstance
import org.etsi.mts.tdl.StructuredDataType
import org.etsi.mts.tdl.Target
import org.etsi.mts.tdl.TestConfiguration
import org.etsi.mts.tdl.TestDescription
import org.etsi.mts.tdl.tdlFactory
import org.imt.pssm.model.statemachines.Behavior
import org.imt.pssm.model.statemachines.CustomSystem
import org.imt.pssm.model.statemachines.SignalEventOccurrence
import org.imt.pssm.model.statemachines.State
import org.imt.pssm.model.statemachines.StateMachine
import org.imt.pssm.model.statemachines.StatemachinesPackage
import org.imt.pssm.model.statemachines.Transition
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.BehavioralInterfacePackage
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.impl.BehavioralInterfaceFactoryImpl
import org.imt.pssm.model.statemachines.CallEventOccurrence
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.Event
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.EventParameter
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrenceArgument

class ETest2TDLTest {

	private ResourceSet rs
	private BehavioralInterface bi
	private static val pluginName = "/org.imt.generateTdlTest4pssm"
	
	static tdlFactory TDL_FACTORY = tdlFactory.eINSTANCE
	private var Package commonPackage
	private var Package pssmTypesPackage
	private var Package pssmEventsPackage
	private var Package testConfigurationPackage
	private val List<Package> tdlTestSuitePackages = new ArrayList
	
	def void transformTestSuite() {
		//load the existing TDL packages (generated previously)
		loadTdlPackages
		//perform the transformation
		//for each test case: generate a separated test suite file
		PSSMTestSuite.testCases.forEach[c|
			val package = generateTDLTestSuitePackage(c.name)
			package.packagedElement.add(testCase2testDescription(package, c))
			tdlTestSuitePackages.add(package)
		]

		savePackages
	}
	
	def void loadTdlPackages(){
		val rs = new ResourceSetImpl
		
		var File tdlFile = new File(getTDLPackageURI("common").toString);
		var Resource res = rs.getResource(getTDLPackageURI("common"), true)
		commonPackage = res.contents.get(0) as Package
		
		tdlFile = new File(getTDLPackageURI("pssmSpecificTypes").toString);
		res = rs.getResource(getTDLPackageURI("pssmSpecificTypes"), true)
		pssmTypesPackage = res.contents.get(0) as Package
		
		tdlFile = new File(getTDLPackageURI("pssmSpecificEvents").toString);
		res = rs.getResource(getTDLPackageURI("pssmSpecificEvents"), true)
		pssmEventsPackage = res.contents.get(0) as Package
		
		tdlFile = new File(getTDLPackageURI("testConfiguration").toString);
		res = rs.getResource(getTDLPackageURI("testConfiguration"), true)
		testConfigurationPackage = res.contents.get(0) as Package
	}
	
	def Package generateTDLTestSuitePackage(String name){
		val tdlTestSutiePackage = TDL_FACTORY.createPackage	
		tdlTestSutiePackage.name = name.validName + "_TestSuite"
		//generate imports
		var ElementImport commonPackageImport = TDL_FACTORY.createElementImport();
		commonPackageImport.setImportedPackage(commonPackage);
		var ElementImport dslSpecificTypesPackageImport = TDL_FACTORY.createElementImport();
		dslSpecificTypesPackageImport.setImportedPackage(pssmTypesPackage);
		var ElementImport dslSpecificEventsPackageImport = TDL_FACTORY.createElementImport();
		dslSpecificEventsPackageImport.setImportedPackage(pssmEventsPackage);
		var ElementImport testConfigurationImport = TDL_FACTORY.createElementImport();
		testConfigurationImport.setImportedPackage(testConfigurationPackage);
		tdlTestSutiePackage.getImport().add(commonPackageImport)
		tdlTestSutiePackage.getImport().add(dslSpecificTypesPackageImport)
		tdlTestSutiePackage.getImport().add(dslSpecificEventsPackageImport)
		tdlTestSutiePackage.getImport().add(testConfigurationImport)
		return tdlTestSutiePackage
	}
	
	def TestDescription testCase2testDescription (Package containerPackage, TestCase testcase){
		//generate a specific test configuration for each test case (for each PSSM model)
		val CustomSystem system = EcoreUtil.resolve(testcase.model, rs) as CustomSystem
		val TestConfiguration configuration = generateTestConfiguration(system.statemachine)
		
		generateTestData(containerPackage, system)
		
		//generate a tdl test case for each PSSM test case (for each PSSM model)
		val TestDescription tdlTestCase = TDL_FACTORY.createTestDescription
		tdlTestCase.name = testcase.name.validName
		tdlTestCase.testConfiguration = configuration
		
		val BehaviourDescription bd = TDL_FACTORY.createBehaviourDescription
		tdlTestCase.behaviourDescription = bd
		
		val CompoundBehaviour cb = TDL_FACTORY.createCompoundBehaviour
		bd.behaviour = cb
		
		val Block block = TDL_FACTORY.createBlock
		cb.block = block

		val expectedBehaviors = testcase.expectedTrace.split("::")
		var int j = 0
		for (i:0..<testcase.scenario.size-1){
			val occurrence = testcase.scenario.get(i)
			if (occurrence.type.equals(EventOccurrenceType.ACCEPTED)){
				block.behaviour.add(eventOccurance2message(occurrence, configuration, containerPackage, ""))
			} else if (occurrence.type.equals(EventOccurrenceType.EXPOSED)){
				var behavior = expectedBehaviors.get(j++)
				if (behavior.contains("[")){
					behavior = behavior.substring(0, behavior.indexOf("["))
				}
				block.behaviour.add(eventOccurance2message(occurrence, configuration, containerPackage, behavior))
			}
		}

		return tdlTestCase
	}

	def TestConfiguration generateTestConfiguration(StateMachine stateMachine){
		//generate one test configuration per statemachine
		var TestConfiguration dslSpecificConfiguration = TDL_FACTORY.createTestConfiguration();
		dslSpecificConfiguration.setName("configuration4" + stateMachine.name.validName);
		
		//generate one component instance as TESTER
		var ComponentInstance testerInstance = TDL_FACTORY.createComponentInstance();
		testerInstance.setName("tester");
		testerInstance.setRole(ComponentInstanceRole.TESTER);
		var testComponent = testConfigurationPackage.packagedElement.findFirst[e | 
			e instanceof ComponentType && e.name.toString.equals("TestSystem")
		] as ComponentType 
		testerInstance.setType(testComponent);
		dslSpecificConfiguration.getComponentInstance().add(testerInstance);
		
		//generate one component instance for Model-Under Test (MUT) as SUT
		//that has annotations for MUTPath and DSLName
		var ComponentInstance mutInstance = TDL_FACTORY.createComponentInstance();
		mutInstance.setName("PSSM");
		mutInstance.setRole(ComponentInstanceRole.SUT);
		val mutComponent = testConfigurationPackage.packagedElement.findFirst[
			e | e instanceof ComponentType && e.name.equals("MUT")
		] as ComponentType
		mutInstance.setType(mutComponent);
		
		var Annotation mutPathAnnotation = TDL_FACTORY.createAnnotation();
		mutPathAnnotation.setAnnotatedElement(mutInstance);
		val mutPathAnnotationType = testConfigurationPackage.packagedElement.findFirst[
			e | e instanceof AnnotationType && e.name.equals("MUTPath")
		] as AnnotationType
		mutPathAnnotation.setKey(mutPathAnnotationType);
		mutPathAnnotation.setValue("\'" + stateMachine.name.stateMachineURI.toString + "\'");
		mutInstance.getAnnotation().add(mutPathAnnotation);
		
		var Annotation dslNameAnnotation = TDL_FACTORY.createAnnotation();
		dslNameAnnotation.setAnnotatedElement(mutInstance);
		val dslNameAnnotationType = testConfigurationPackage.packagedElement.findFirst[
			e | e instanceof AnnotationType && e.name.equals("DSLName")
		] as AnnotationType
		dslNameAnnotation.setKey(dslNameAnnotationType);
		dslNameAnnotation.setValue("\'org.imt.pssm.InterpretedStatemachines'");
		mutInstance.getAnnotation().add(dslNameAnnotation);
		dslSpecificConfiguration.getComponentInstance().add(mutInstance);
		
		//generate connection between the gates
		var Connection gateConnection = TDL_FACTORY.createConnection();
		var GateReference referenceToTestGate = TDL_FACTORY.createGateReference();
		referenceToTestGate.setComponent(testerInstance);
		referenceToTestGate.setGate(testComponent.allGates.findFirst[g | g.name.equals("dslSpecificTestGate")]);
		var GateReference referenceToMUTGate = TDL_FACTORY.createGateReference();
		referenceToMUTGate.setComponent(mutInstance);
		referenceToMUTGate.setGate(mutComponent.allGates.findFirst[g | g.name.equals("dslSpecificMUTGate")]);
		
		gateConnection.getEndPoint().add(referenceToTestGate);
		gateConnection.getEndPoint().add(referenceToMUTGate);
		dslSpecificConfiguration.getConnection().add(gateConnection);
		
		testConfigurationPackage.getPackagedElement().add(dslSpecificConfiguration)
		return dslSpecificConfiguration
	}
	
	def void generateTestData(Package testSuitePackage, CustomSystem system) {
		//create a data instance for the state machine
		var StructuredDataInstance stateMachineInstance = TDL_FACTORY.createStructuredDataInstance
		stateMachineInstance.name = system.statemachine.name.validName
		val stateMachineType = this.pssmTypesPackage.packagedElement.findFirst[t | t.name.equals("StateMachine")] as StructuredDataType
		stateMachineInstance.dataType = stateMachineType
		var MemberAssignment nameMemberAssign = TDL_FACTORY.createMemberAssignment
		nameMemberAssign.member = stateMachineType.allMembers.findFirst[m|m.name.equals("_name")]
		var LiteralValueUse nameValue = TDL_FACTORY.createLiteralValueUse
		nameValue.value = "'" + system.statemachine.name + "'"
		nameMemberAssign.memberSpec = nameValue
		stateMachineInstance.memberAssignment.add(nameMemberAssign)
		testSuitePackage.packagedElement.add(stateMachineInstance)
		
		//create a data instance for each Signal
		for (i:0..<system.signals.size){
			var StructuredDataInstance signalInstance = TDL_FACTORY.createStructuredDataInstance
			signalInstance.name = system.signals.get(i).name.validName
			val signalType = this.pssmTypesPackage.packagedElement.findFirst[t | t.name.equals("Signal")] as StructuredDataType
			signalInstance.dataType = signalType
			nameMemberAssign = TDL_FACTORY.createMemberAssignment
			nameMemberAssign.member = signalType.allMembers.findFirst[m|m.name.equals("_name")]
			nameValue = TDL_FACTORY.createLiteralValueUse
			nameValue.value = "'" + system.signals.get(i).name + "'"
			nameMemberAssign.memberSpec = nameValue
			signalInstance.memberAssignment.add(nameMemberAssign)
			testSuitePackage.packagedElement.add(signalInstance)
		}
		
		//create a data instance for each Operation
		for (i:0..<system.operations.size){
			var StructuredDataInstance opInstance = TDL_FACTORY.createStructuredDataInstance
			opInstance.name = system.operations.get(i).name.validName
			val opType = this.pssmTypesPackage.packagedElement.findFirst[t | t.name.equals("Operation")] as StructuredDataType
			opInstance.dataType = opType
			nameMemberAssign = TDL_FACTORY.createMemberAssignment
			nameMemberAssign.member = opType.allMembers.findFirst[m|m.name.equals("_name")]
			nameValue = TDL_FACTORY.createLiteralValueUse
			nameValue.value = "'" + system.operations.get(i).name + "'"
			nameMemberAssign.memberSpec = nameValue
			opInstance.memberAssignment.add(nameMemberAssign)
			testSuitePackage.packagedElement.add(opInstance)
		}
		
		//create a data instance for each Behavior
		val List<EObject> behaviors = system.statemachine.eAllContents.filter[e|e instanceof Behavior].toList
		for (i:0..<behaviors.size){
			val Behavior behavior = behaviors.get(i) as Behavior
			var String behaviorContainerName = null
			if (behavior.eContainer instanceof State){
				behaviorContainerName = (behavior.eContainer as State).name
			}else if (behavior.eContainer instanceof Transition){
				behaviorContainerName = (behavior.eContainer as Transition).name
			}
			var StructuredDataInstance behaviorInstance = TDL_FACTORY.createStructuredDataInstance
			behaviorInstance.name = behaviorContainerName.validName + "_" + behavior.name.validName
			val behaviorType = this.pssmTypesPackage.packagedElement.findFirst[t | t.name.equals("Behavior")] as StructuredDataType
			behaviorInstance.dataType = behaviorType
			nameMemberAssign = TDL_FACTORY.createMemberAssignment
			nameMemberAssign.member = behaviorType.allMembers.findFirst[m|m.name.equals("_name")]
			nameValue = TDL_FACTORY.createLiteralValueUse
			nameValue.value = "'" + behaviorContainerName + "(" + behavior.name + ")'"
			nameMemberAssign.memberSpec = nameValue
			behaviorInstance.memberAssignment.add(nameMemberAssign)
			testSuitePackage.packagedElement.add(behaviorInstance)
		}
	}
	
	def Message eventOccurance2message (EventOccurrence occurrence, TestConfiguration configuration, Package containerPackage, String expected){
		var Message tdlMsg = TDL_FACTORY.createMessage
		
		//define the source and the target gates of the message 
		val GateReference testerGateRef = configuration.connection.get(0).endPoint.findFirst[
			e | e.gate.name.equals("dslSpecificTestGate")
		]
		val GateReference mutGateRef = configuration.connection.get(0).endPoint.findFirst[
			e | e.gate.name.equals("dslSpecificMUTGate")
		] 
		var Target target = TDL_FACTORY.createTarget
		if (occurrence.type.equals(EventOccurrenceType.ACCEPTED)){
			tdlMsg.sourceGate = testerGateRef
			target.targetGate = mutGateRef
		} else if (occurrence.type.equals(EventOccurrenceType.EXPOSED)){
			tdlMsg.sourceGate = mutGateRef
			target.targetGate = testerGateRef
		}
		tdlMsg.target.add(target)
		
		//defining the message argument
		var DataInstanceUse msgArgument = TDL_FACTORY.createDataInstanceUse
		val event = EcoreUtil.resolve(occurrence.event, rs) as Event
		val biTdlEventInstance = this.pssmEventsPackage.packagedElement.findFirst[e |
			e instanceof StructuredDataInstance && e.name.contains(event.name)
		] as StructuredDataInstance
		msgArgument.dataInstance = biTdlEventInstance
		
		val biTdlEventType = biTdlEventInstance.dataType as StructuredDataType
		//create a parameterBinding for each Event parameter
		for (i:0..<biTdlEventType.member.size){
			var ParameterBinding parameterBinding = TDL_FACTORY.createParameterBinding
			parameterBinding.parameter = biTdlEventType.member.get(i)
			var DataInstanceUse parameterValue = TDL_FACTORY.createDataInstanceUse
			
			//set the value of the parameter based on the event occurrence arguments
			val occurrenceArg = occurrence.args.findFirst[a | 
				val eventParam = (EcoreUtil.resolve(a.parameter, rs) as EventParameter)
				eventParam.name.contains(biTdlEventType.member.get(i).name)
			]
			if (occurrence.type.equals(EventOccurrenceType.ACCEPTED)){
				var String occurrenceArgValueName = ""
				if (occurrenceArg.value instanceof SingleReferenceValue){
					val value = (occurrenceArg.value as SingleReferenceValue).referenceValue
					occurrenceArgValueName = (EcoreUtil.resolve(value, rs) as StateMachine).name
				}else if (occurrenceArg.value instanceof SingleObjectValue){
					var value = (occurrenceArg.value as SingleObjectValue).objectValue
					if (value instanceof SignalEventOccurrence){
						occurrenceArgValueName = (value as SignalEventOccurrence).signal.name
					}else if (value instanceof CallEventOccurrence){
						occurrenceArgValueName = (value as CallEventOccurrence).operation.name
					}
				}
				val name = occurrenceArgValueName
				parameterValue.dataInstance = containerPackage.packagedElement.findFirst[e | 
					e instanceof StructuredDataInstance && e.name.equals(name)
				] as StructuredDataInstance
			} else if (occurrence.type.equals(EventOccurrenceType.EXPOSED)){
				val behaviorName = expected.replace("(" , "_").substring(0, expected.length-1)
				parameterValue.dataInstance = containerPackage.packagedElement.findFirst[e | 
					e instanceof StructuredDataInstance && e.name.equals(behaviorName)
				] as StructuredDataInstance
			}
			parameterBinding.dataUse = parameterValue
			msgArgument.argument.add(parameterBinding)
		}
		
		tdlMsg.argument = msgArgument
		
		return tdlMsg
	}
	
	def String getValidName (String name){
		val String[] tokenNames = #['Package', '{', '}', 'with', 'perform', 'action', '(', ',', ')', 'on', 'test', 'objectives', ':', ';', 'name', 'time', 'label', 'constraints', 'Action', 'alternatively', 'or', 'Annotation', '*', '?', '=', 'assert', 'otherwise', 'set', 'verdict', 'to', '->', '[', ']', 'times', 'repeat', 'break', 'Note', 'create', 'of', 'type', 'bind', 'Component', 'Type', 'having', 'if', 'else', 'connect', 'as', 'Map', 'in', '.', 'new', 'containing', 'Use', 'Signature', 'Collection', 'default', '+', '-', '/', 'mod', '>', '<', '>=', '<=', '==', '!=', 'and', 'xor', 'not', 'size', 'Import', 'all', 'from', 'Function', 'returns', 'instance', 'returned', 'Predefined', 'gate', 'Gate', 'accepts', 'sends', 'triggers', 'calls', 'responds', 'response', 'interrupt', 'optional', 'mapped', 'omit', 'argument', 'optionally', 'run', 'parallel', 'parameter', 'every', 'component', 'is', 'quiet', 'for', 'terminate', 'where', 'it', 'assigned', 'Test', 'Configuration', 'Description', 'Implementation', 'uses', 'configuration', 'execute', 'bindings', 'Objective', 'description', 'Time', 'out', 'timer', 'start', 'stop', 'variable', 'waits', 'extends', 'SUT', 'Tester', 'Message', 'Procedure', 'In', 'Out', 'Exception', 'last', 'previous', 'first']
		var result = name
		if (result.contains('$')){
			result = result.substring(0, result.indexOf('$'))
		}
		if (result.contains('.')){
			result = result.replace('.', '_')
		}
		if (tokenNames.contains(result)){
			result = "_" + result
		}
		return result
	}
	def static URI getStateMachineURI(String stateMachineName) {
		URI::createFileURI( pluginName + "/models/" + stateMachineName + ".xmi")
	}
	def static URI getTDLPackageURI(String packageName) {
		URI::createFileURI( pluginName + "/generatedTDLPackages/" + packageName + ".tdlan2")
	}
	
	def TestSuite getPSSMTestSuite() {
		this.rs = new ResourceSetImpl
		val p1 = TestsuitePackage.eINSTANCE
		val p2 = EventPackage.eINSTANCE
		val p3 = StatemachinesPackage.eINSTANCE
		val p4 = ValuePackage.eINSTANCE
		rs.getPackageRegistry().put(p1.getNsURI(), p1);
		rs.getPackageRegistry().put(p2.getNsURI(), p2);
		rs.getPackageRegistry().put(p3.getNsURI(), p3);
		rs.getPackageRegistry().put(p4.getNsURI(), p4);
		rs.resourceFactoryRegistry.extensionToFactoryMap.put("xmi", new XMIResourceFactoryImpl)
		
		val uri = "C:/labtop/GitHub/xtdl_EventManager/org.imt.generateTdlTest4pssm/models/StateMachineTestSuite.xmi"
		var res = rs.getResource(URI.createFileURI(uri), true)
		val testSuite = res.contents.get(0) as TestSuite
		
		val biUri = "C:/labtop/GitHub/xtdl_EventManager/org.imt.generateTdlTest4pssm/models/InterpretedStateMachines.bi"
		res = rs.getResource(URI.createFileURI(biUri), true)
		res.load(Collections.emptyMap())
		bi = (res.getContents().get(0)) as BehavioralInterface
		
		return testSuite
	}
	
	def void savePackages(){
		val rs = new ResourceSetImpl
		val Resource commonRes = rs.createResource(getTDLPackageURI("common"))
		val Resource pssmTypesRes = rs.createResource(getTDLPackageURI("pssmSpecificTypes"))
		val Resource pssmEventsRes = rs.createResource(getTDLPackageURI("pssmSpecificEvents"))
		val Resource configurationRes = rs.createResource(getTDLPackageURI("testConfiguration"))
		var List<Resource> testSuiteResources = new ArrayList
		for (i:0..<tdlTestSuitePackages.size){
			val Resource testSuiteRes = rs.createResource(getTDLPackageURI(tdlTestSuitePackages.get(i).name))
			testSuiteRes.contents.add(tdlTestSuitePackages.get(i))
			testSuiteResources.add(testSuiteRes)
		}
		commonRes.contents.add(commonPackage)
		pssmTypesRes.contents.add(pssmTypesPackage)
		pssmEventsRes.contents.add(pssmEventsPackage)
		configurationRes.contents.add(testConfigurationPackage)
		
		commonRes.save(null)
		pssmTypesRes.save(null)
		pssmEventsRes.save(null)
		configurationRes.save(null)
		testSuiteResources.forEach[r | r.save(null)]
		
		commonRes.unload
		pssmTypesRes.unload
		pssmEventsRes.unload
		configurationRes.unload
		testSuiteResources.forEach[r | r.unload]
	}
}