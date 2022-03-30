package org.imt.tdl.amplification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrenceArgument;
import org.eclipse.gemoc.executionframework.value.model.value.BooleanAttributeValue;
import org.eclipse.gemoc.executionframework.value.model.value.BooleanObjectAttributeValue;
import org.eclipse.gemoc.executionframework.value.model.value.FloatAttributeValue;
import org.eclipse.gemoc.executionframework.value.model.value.FloatObjectAttributeValue;
import org.eclipse.gemoc.executionframework.value.model.value.IntegerAttributeValue;
import org.eclipse.gemoc.executionframework.value.model.value.IntegerObjectAttributeValue;
import org.eclipse.gemoc.executionframework.value.model.value.SingleObjectValue;
import org.eclipse.gemoc.executionframework.value.model.value.SingleReferenceValue;
import org.eclipse.gemoc.executionframework.value.model.value.StringAttributeValue;
import org.eclipse.gemoc.executionframework.value.model.value.Value;
import org.eclipse.gemoc.executionframework.value.model.value.ValuePackage;
import org.etsi.mts.tdl.Block;
import org.etsi.mts.tdl.CompoundBehaviour;
import org.etsi.mts.tdl.DataInstance;
import org.etsi.mts.tdl.DataInstanceUse;
import org.etsi.mts.tdl.DataUse;
import org.etsi.mts.tdl.GateReference;
import org.etsi.mts.tdl.MemberAssignment;
import org.etsi.mts.tdl.Message;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.ParameterBinding;
import org.etsi.mts.tdl.StructuredDataInstance;
import org.etsi.mts.tdl.Target;
import org.etsi.mts.tdl.TestDescription;
import org.etsi.mts.tdl.tdlFactory;
import org.imt.k3tdl.k3dsa.TestDescriptionAspect;
import org.imt.tdl.observer.ModelExecutionObserver;
import org.imt.tdl.testResult.TDLTestCaseResult;
import org.imt.tdl.testResult.TDLTestResultUtil;

public class AssertionGenerator extends ModelExecutionObserver{

	org.etsi.mts.tdl.Package testSuite;
	List<EventOccurrence> exposedEventOccurrences = new ArrayList<>();
	
	@SuppressWarnings("static-access")
	public boolean generateAssertionsForTestCase(TestDescription tdlTestCase) {
		testSuite = (Package) tdlTestCase.eContainer();
		TestDescriptionAspect testCaseRunner = new TestDescriptionAspect();
		
		//activating test case configuration and attaching a model execution observer to the configured model execution engine
		testCaseRunner.activateConfiguration(tdlTestCase);
		modelExecutionEngine = testCaseRunner.launcher(tdlTestCase).getActiveEngine();
		modelExecutionEngine.attach(this);
		//run the test case
		TDLTestCaseResult result = testCaseRunner.executeTestCase(tdlTestCase);
		//if the new test case cannot be executed completely, it must be ignored from the list of new test cases
		if (result.getValue() == TDLTestResultUtil.INCONCLUSIVE) {
			return false;
		}
		
		GateReference sourceGate = getSourceGate(tdlTestCase);
		Target targetGate = getTargetGate(tdlTestCase);
		if (sourceGate == null || targetGate == null) {
			return false;
		}
		
		//create an assertion for each exposed event occurrence in the test case
		//its source and target gates must be inverse of existing messages
		for (EventOccurrence exposedEvent:exposedEventOccurrences) {
			Message assertion = tdlFactory.eINSTANCE.createMessage();
			assertion.setSourceGate(targetGate.getTargetGate());
			Target target = tdlFactory.eINSTANCE.createTarget();
			target.setTargetGate(sourceGate);
			assertion.getTarget().add(target);
			assertion.setArgument(createExpectedData(exposedEvent));
			//NOTE: This is an assumption that we only consider test cases containing only Messages
			try {
				Block assertionContainer =  getTestCaseMainBlock(tdlTestCase);
				assertionContainer.getBehaviour().add(assertion);
			}catch (ClassCastException e) {
				System.out.println("ClassCastException: Cannot add the assertion Message to the test case");
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	private DataUse createExpectedData(EventOccurrence exposedEvent) {
		DataInstanceUse expectedData = tdlFactory.eINSTANCE.createDataInstanceUse();
		//finding a tdl data instance with the name of the exposed event 
		//(the TDL library generator must generated it in the xDSL-specific events package)
		Optional<DataInstance> diOptional = testSuite.getPackagedElement().stream().
				filter(p -> p instanceof DataInstance).map(p -> (DataInstance) p).
				filter(d -> d.getName().equals(exposedEvent.getEvent().getName())).findFirst();
		if (diOptional.isEmpty()) {
			return null;
		}
		DataInstance di = diOptional.get();
		/*if the data instance is a structured data instance, 
		 * it means it has parameters that have to be bind 
		 * based on the values of the arguments of the exposed event 
		 */
		if (di instanceof StructuredDataInstance) {
			StructuredDataInstance sdi = (StructuredDataInstance) di;
			for (MemberAssignment ma: sdi.getMemberAssignment()) {
				//finding the event argument related to the member assignment 
				Optional<EventOccurrenceArgument> eoaOptional = exposedEvent.getArgs().stream().
					filter(a -> a.getParameter().getName().equals(ma.getMember().getName())).findFirst();
				if (eoaOptional.isEmpty()) {
					return null;
				}
				//get the value of the argument
				Value eventOccuArgValue = (eoaOptional.get()).getValue();
				//create parameterBindings for the created dataInstanceUse for each eventOccurrenceArgument
				ParameterBinding pb = tdlFactory.eINSTANCE.createParameterBinding();
				pb.setParameter(ma.getMember());
				pb.setDataUse(getTdlValueOfObjectValue(eventOccuArgValue));
				expectedData.getArgument().add(pb);
			}
		}
		expectedData.setDataInstance(di);
		return expectedData;
	}

	private DataUse getTdlValueOfObjectValue(Value eventOccuArgValue) {
		EObject2TDLConverter converter = new EObject2TDLConverter(testSuite);
		switch (eventOccuArgValue.eClass().getClassifierID()) {
		case ValuePackage.SINGLE_REFERENCE_VALUE:
			return converter.convertEObject2TDLData(((SingleReferenceValue) eventOccuArgValue).getReferenceValue());
		case ValuePackage.SINGLE_OBJECT_VALUE:
			return converter.convertEObject2TDLData(((SingleObjectValue) eventOccuArgValue).getObjectValue());
		case ValuePackage.BOOLEAN_ATTRIBUTE_VALUE:
			return converter.convertBoolean2TDLData(((BooleanAttributeValue) eventOccuArgValue).isAttributeValue());
		case ValuePackage.BOOLEAN_OBJECT_ATTRIBUTE_VALUE:
			return converter.convertBoolean2TDLData(((BooleanObjectAttributeValue) eventOccuArgValue).getAttributeValue());
		case ValuePackage.INTEGER_ATTRIBUTE_VALUE:
			return converter.convertInteger2TDLData(((IntegerAttributeValue) eventOccuArgValue).getAttributeValue());
		case ValuePackage.INTEGER_OBJECT_ATTRIBUTE_VALUE:
			return converter.convertInteger2TDLData(((IntegerObjectAttributeValue) eventOccuArgValue).getAttributeValue());
		case ValuePackage.FLOAT_ATTRIBUTE_VALUE:
			return converter.convertFloat2TDLData(((FloatAttributeValue) eventOccuArgValue).getAttributeValue());
		case ValuePackage.FLOAT_OBJECT_ATTRIBUTE_VALUE:
			return converter.convertFloat2TDLData(((FloatObjectAttributeValue) eventOccuArgValue).getAttributeValue());
		case ValuePackage.STRING_ATTRIBUTE_VALUE:
			return converter.convertString2TDLData(((StringAttributeValue) eventOccuArgValue).getAttributeValue());
		}
		return null;
	}

	@Override
	public void update(EventOccurrence e) {
		exposedEventOccurrences.add(e);
	}
	
	private Block getTestCaseMainBlock(TestDescription testCase) {
		return ((CompoundBehaviour) testCase.getBehaviourDescription().getBehaviour()).getBlock();
	}
	
	private GateReference getSourceGate (TestDescription testCase) {
		Block messagesContainer = getTestCaseMainBlock(testCase);
		try {
			Message tdlMessage = (Message) messagesContainer.getBehaviour().get(0);
			return tdlMessage.getSourceGate();
		}catch (ClassCastException e) {
			return null;
		}
	}
	
	private Target getTargetGate (TestDescription testCase) {
		Block messagesContainer = getTestCaseMainBlock(testCase);
		try {
			Message tdlMessage = (Message) messagesContainer.getBehaviour().get(0);
			return tdlMessage.getTarget().get(0);
		}catch (ClassCastException e) {
			return null;
		}
	}
}
