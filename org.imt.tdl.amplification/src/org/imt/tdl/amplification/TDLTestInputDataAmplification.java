package org.imt.tdl.amplification;

import java.util.ArrayList;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.etsi.mts.tdl.Block;
import org.etsi.mts.tdl.CompoundBehaviour;
import org.etsi.mts.tdl.DataInstanceUse;
import org.etsi.mts.tdl.DataType;
import org.etsi.mts.tdl.DataUse;
import org.etsi.mts.tdl.LiteralValueUse;
import org.etsi.mts.tdl.Message;
import org.etsi.mts.tdl.ParameterBinding;
import org.etsi.mts.tdl.StaticDataUse;
import org.etsi.mts.tdl.StructuredDataInstance;
import org.etsi.mts.tdl.TestDescription;
import org.etsi.mts.tdl.tdlFactory;

public class TDLTestInputDataAmplification {
	
	List<LiteralValueUse> boolLiterals = new ArrayList<LiteralValueUse>();
	List<LiteralValueUse> stringLiterals = new ArrayList<LiteralValueUse>();
	List<LiteralValueUse> intLiterals = new ArrayList<LiteralValueUse>();
	List<LiteralValueUse> floatLiterals = new ArrayList<LiteralValueUse>();
	int numOfNewTests = 0;
	
	private static String BOOLMODIFICATION = "BooleanModification";
	private static String STRINGMODIFICATION = "StringModification";
	private static String INTMODIFICATION = "IntegerModification";
	private static String FLOATMODIFICATION = "FloatModification";
	private static String EVENTDUPLICATION = "EventDuplication";
	private static String EVENTCREATION = "EventCreation";
	private static String EVENTDELETION = "EventDeletion";
	private static String EVENTPERMUTATION = "EventPermutation";
	private static String EVENTMODIFICATION = "EventModification";
	
	public List<TestDescription> generateNewTestsByInputModification (TestDescription tdlTestCase) {
		List<TestDescription> generatedTestsByModification = new ArrayList<>();
		generatedTestsByModification.addAll(modifyLiteralData (tdlTestCase));
		generatedTestsByModification.addAll(modifyExchangedEvents (tdlTestCase));
		return generatedTestsByModification;
	}

	/* four operators can be applied:
	 * 1. addition : duplication of existing & creation of new events based on the interface
	 * 2. deletion
	 * 3. reordering
	 * 4. modification of event parameters values with other values for the same parameter
	 * 
	 * NOTE: We assume a TDL test case only contains a series of Message elements each sending an accepted event to the model under test
	 */
	private Collection<? extends TestDescription> modifyExchangedEvents(TestDescription tdlTestCase) {
		List<TestDescription> generatedTestsByModification = new ArrayList<>();
		Block messagesContainer = ((CompoundBehaviour) tdlTestCase.getBehaviourDescription().getBehaviour()).getBlock();
		List<Message> tdlMessages = messagesContainer.getBehaviour().stream().map(b -> (Message) b).collect(Collectors.toList());
		//1.1. addition-duplication of existing messages
		for (Message tdlMessage: tdlMessages) {
			TestDescription copyTdlTestCase = copyTdlTestCase(tdlTestCase, EVENTDUPLICATION);
			Block copyContainer = ((CompoundBehaviour) copyTdlTestCase.getBehaviourDescription().getBehaviour()).getBlock();
			copyContainer.getBehaviour().add(copyTdlMessage(tdlMessage));
			generatedTestsByModification.add(copyTdlTestCase);
		}
		//1.2. addition-creation of new events
		/* for the accepted events of the behavioral interface that are not used in the test cases, create new events
		 * ?? what if the EObjects used as event parameters are not existed in the model under test?
		 * ?? should we first check if there is the required EObjects in the model?
		 */
		//TODO
		//2. deletion
		for (Message tdlMessage: tdlMessages) {
			TestDescription copyTdlTestCase = copyTdlTestCase(tdlTestCase, EVENTDELETION);
			Block copyContainer = ((CompoundBehaviour) copyTdlTestCase.getBehaviourDescription().getBehaviour()).getBlock();
			copyContainer.getBehaviour().remove(tdlMessage);
			generatedTestsByModification.add(copyTdlTestCase);
		}
		//3. permutation
		TestDescription copyTdlTestCase = copyTdlTestCase(tdlTestCase, EVENTPERMUTATION);
		Block copyContainer = ((CompoundBehaviour) copyTdlTestCase.getBehaviourDescription().getBehaviour()).getBlock();
		List<Message> copyMessages = copyContainer.getBehaviour().stream().map(b -> (Message) b).collect(Collectors.toList());
		Collections.shuffle(copyMessages);
		copyContainer.getBehaviour().clear();
		copyContainer.getBehaviour().addAll(copyMessages);
		generatedTestsByModification.add(copyTdlTestCase);
		
		//4. modification of event parameters values with other values for the same parameter
		//NOTE: Event parameter values are indeed model objects (according to behavioral interface metalanguage)
		//?? should we only use the existing TDL elements related to objects or we should generate new ones for the missing ones?
//		Package testSuite = (Package) tdlTestCase.container();
//		copyTdlTestCase = copyTdlTestCase(tdlTestCase, EVENTMODIFICATION);
//		List<DataInstanceUse> eobjectRefrences = findEObjectRefsInTestCase (copyTdlTestCase);
//		for (DataInstanceUse eobjectReference:eobjectRefrences) {
//			StructuredDataInstance tdlEobject = (StructuredDataInstance) eobjectReference.getDataInstance();
//			
//		}
		return generatedTestsByModification;
	}

	//finding parameterBindings that their value are eobject instances
	private List<DataInstanceUse> findEObjectRefsInTestCase(TestDescription tdlTestCase) {
		Block messagesContainer = ((CompoundBehaviour) tdlTestCase.getBehaviourDescription().getBehaviour()).getBlock();
		List<Message> tdlMessages = messagesContainer.getBehaviour().stream().map(b -> (Message) b).collect(Collectors.toList());
		List<DataInstanceUse> exchangedEvents = tdlMessages.stream().map(m -> (DataInstanceUse) m.getArgument()).collect(Collectors.toList());
		List<DataInstanceUse> eobjectRefrences = new ArrayList<>();
		for (DataInstanceUse eventInstance: exchangedEvents) {
			Iterator<EObject> iterator = eventInstance.eAllContents();
			while (iterator.hasNext()) {
				EObject object = iterator.next();
				if (object instanceof ParameterBinding && ((ParameterBinding) object).getDataUse() instanceof DataInstanceUse) {
					DataInstanceUse reference = (DataInstanceUse)((ParameterBinding) object).getDataUse();
					eobjectRefrences.addAll(getDirectEObjectReferences(reference));
				}
			}
		}
		return eobjectRefrences;
	}
	
	private List<DataInstanceUse> getDirectEObjectReferences(DataInstanceUse reference){
		List<DataInstanceUse> eobjectRefrences = new ArrayList<>();
		if (reference.getItem() != null && reference.getItem().size()==0) {
			for (StaticDataUse item: reference.getItem()) {
				if (item instanceof DataInstanceUse) {
					eobjectRefrences.addAll(getDirectEObjectReferences((DataInstanceUse)item));
				}	
			}
		}
		else if (reference.getDataInstance() != null){
			eobjectRefrences.add(reference);
		}
		return eobjectRefrences;
	}

	private List<TestDescription> modifyLiteralData(TestDescription tdlTestCase) {
		List<TestDescription> generatedTestsByModification = new ArrayList<>();
		Iterator<EObject> iterator = tdlTestCase.eAllContents();
		while (iterator.hasNext()) {
			EObject object = iterator.next();
			if (object instanceof DataInstanceUse) {
				findLiteralFeaturesOfData((DataInstanceUse) object);
			}
		}
		if (boolLiterals.size()>0) {
			generatedTestsByModification.addAll(mutateBooleanData(tdlTestCase));
		}
		else if (stringLiterals.size()>0) {
			generatedTestsByModification.addAll(mutateStringData(tdlTestCase));
		}
		else if (intLiterals.size()>0) {
			generatedTestsByModification.addAll(mutateIntegerData(tdlTestCase));
		}
		else if (floatLiterals.size()>0) {
			generatedTestsByModification.addAll(mutateFloatData(tdlTestCase));
		}
		return generatedTestsByModification;
	}
	
	private Collection<? extends TestDescription> mutateBooleanData(TestDescription tdlTestCase) {
		List<TestDescription> generatedTestsByModification = new ArrayList<>();
		for (LiteralValueUse boolLiteral:boolLiterals) {
			if (getLiteralValue(boolLiteral).equals("true")) {
				boolLiteral.setValue("false");
			}
			else if (getLiteralValue(boolLiteral).equals("false")) {
				boolLiteral.setValue("true");
			}
			generatedTestsByModification.add(copyTdlTestCase(tdlTestCase, BOOLMODIFICATION));
		}
		return generatedTestsByModification;
	}

	/*four operators: 
	 * 1. add a random char, 
	 * 2. remove a random char, 
	 * 3. replace a random char and 
	 * 4. replace the string by a fully random string of the same size
	 */
	private Collection<? extends TestDescription> mutateStringData(TestDescription tdlTestCase) {
		List<TestDescription> generatedTestsByModification = new ArrayList<>();
		for (LiteralValueUse stringLiteral:stringLiterals) {
			String value = getLiteralValue(stringLiteral);
			StringBuilder sb = new StringBuilder(value);
			Random rand = new Random();
			int randomIndex = rand.nextInt(sb.length());
			// 1. add a random char
			sb.append(RandomStringUtils.randomAlphanumeric(1));
			stringLiteral.setValue(sb.toString());
			generatedTestsByModification.add(copyTdlTestCase(tdlTestCase, STRINGMODIFICATION));
			//2. remove a random char
			sb = new StringBuilder(value);
			sb.deleteCharAt(randomIndex);
			stringLiteral.setValue(sb.toString());
			generatedTestsByModification.add(copyTdlTestCase(tdlTestCase, STRINGMODIFICATION));
			//3. replace a random char
			sb = new StringBuilder(value);
			sb.replace(randomIndex, randomIndex + 1, RandomStringUtils.randomAlphanumeric(1));
			stringLiteral.setValue(sb.toString());
			generatedTestsByModification.add(copyTdlTestCase(tdlTestCase, STRINGMODIFICATION));
			//4. replace the string by a fully random string of the same size
			stringLiteral.setValue(RandomStringUtils.randomAlphanumeric(value.length()));
			generatedTestsByModification.add(copyTdlTestCase(tdlTestCase, STRINGMODIFICATION));
		}
		return generatedTestsByModification;
	}

	/* five operators for numeric values: 
	 * plus 1, minus 1, multiply by 2, divide by 2, and replacement by an existing literal of the same type
	 */
	private Collection<? extends TestDescription> mutateIntegerData(TestDescription tdlTestCase) {
		List<TestDescription> generatedTestsByModification = new ArrayList<>();
		for (LiteralValueUse intLiteral:intLiterals) {
			int value = Integer.parseInt(getLiteralValue(intLiteral));
			//1. value plus 1
			intLiteral.setValue("'" + (value + 1) + "'");
			generatedTestsByModification.add(copyTdlTestCase(tdlTestCase, INTMODIFICATION));
			//2. value minus 1
			intLiteral.setValue("'" + (value - 1) + "'");
			generatedTestsByModification.add(copyTdlTestCase(tdlTestCase, INTMODIFICATION));
			//3. value multiply by 2
			intLiteral.setValue("'" + (value * 2) + "'");
			generatedTestsByModification.add(copyTdlTestCase(tdlTestCase, INTMODIFICATION));
			//4. value divide by 2
			intLiteral.setValue("'" + (value / 2) + "'");
			generatedTestsByModification.add(copyTdlTestCase(tdlTestCase, INTMODIFICATION));
			//5. replacement by an existing literal of the same type
			List<LiteralValueUse> otherValues = intLiterals.stream().filter(i -> i != intLiteral).toList();
			for (LiteralValueUse otherValue: otherValues) {
				intLiteral.setValue(otherValue.getValue());
				generatedTestsByModification.add(copyTdlTestCase(tdlTestCase, INTMODIFICATION));
			}
		}
		return generatedTestsByModification;
	}

	private Collection<? extends TestDescription> mutateFloatData(TestDescription tdlTestCase) {
		List<TestDescription> generatedTestsByModification = new ArrayList<>();
		for (LiteralValueUse floatLiteral:floatLiterals) {
			float value = Float.parseFloat(getLiteralValue(floatLiteral));
			//1. value plus 1
			floatLiteral.setValue("'" + (value + 1) + "'");
			generatedTestsByModification.add(copyTdlTestCase(tdlTestCase, FLOATMODIFICATION));
			//2. value minus 1
			floatLiteral.setValue("'" + (value - 1) + "'");
			generatedTestsByModification.add(copyTdlTestCase(tdlTestCase, FLOATMODIFICATION));
			//3. value multiply by 2
			floatLiteral.setValue("'" + (value * 2) + "'");
			generatedTestsByModification.add(copyTdlTestCase(tdlTestCase, FLOATMODIFICATION));
			//4. value divide by 2
			floatLiteral.setValue("'" + (float)(value / 2) + "'");
			generatedTestsByModification.add(copyTdlTestCase(tdlTestCase, FLOATMODIFICATION));
			//5. replacement by an existing literal of the same type
			Random rand = new Random();
			int randomIndex = rand.nextInt(floatLiterals.size());
			while (randomIndex == floatLiterals.indexOf(floatLiteral)) {
				randomIndex = rand.nextInt(floatLiterals.size());
			}
			String exValue = floatLiterals.get(randomIndex).getValue();
			floatLiteral.setValue(exValue);
			generatedTestsByModification.add(copyTdlTestCase(tdlTestCase, FLOATMODIFICATION));
		}
		return generatedTestsByModification;
	}

	private void findLiteralFeaturesOfData (DataInstanceUse dataInstanceUse){
		//finding literals that their value is directly used in the test case
		//(ignoring memberAssignments and only considering parameterBindings)
		for (ParameterBinding pb : dataInstanceUse.getArgument()) {
			DataUse parameterValue = pb.getDataUse();
			if (!pb.getParameter().getName().equals("_name")) {
				if (parameterValue instanceof LiteralValueUse) {
					classifyLiteralBasedOnType ((LiteralValueUse) parameterValue, pb.getParameter().getDataType());
				}else if (parameterValue instanceof DataInstanceUse) {
					findLiteralFeaturesOfData((DataInstanceUse) parameterValue);
				}
			}
		}
	}
	
	private void classifyLiteralBasedOnType (LiteralValueUse literal, DataType type) {
		String typeName = type.getName();
		if (typeName.equals("EBooleanObject") || typeName.equals("EBoolean")) {
			boolLiterals.add(literal);
		}
		else if (typeName.equals("EString")) {
			stringLiterals.add(literal);
		}
		else if (typeName.equals("EIntegerObject") || typeName.equals("EInt")) {
			intLiterals.add(literal);
		}
		else if (typeName.equals("EFloatObject") || typeName.equals("EFloat")) {
			floatLiterals.add(literal);
		}
	}
	
	private TestDescription copyTdlTestCase(TestDescription testCase, String modificationOperator) {
		TestDescription copyTdlTestCase = tdlFactory.eINSTANCE.createTestDescription();
		copyTdlTestCase.setName(testCase.getName() + (numOfNewTests++) + "_" + modificationOperator);
		copyTdlTestCase.setTestConfiguration(testCase.getTestConfiguration());
		copyTdlTestCase.setBehaviourDescription(EcoreUtil.copy(testCase.getBehaviourDescription()));
		return copyTdlTestCase;
	}
	
	private Message copyTdlMessage (Message message) {
		Message copyMessage = tdlFactory.eINSTANCE.createMessage();
		copyMessage.setSourceGate(message.getSourceGate());
		copyMessage.getTarget().addAll(EcoreUtil.copyAll(message.getTarget()));
		copyMessage.setArgument(EcoreUtil.copy(message.getArgument()));
		copyMessage.setIsTrigger(message.isIsTrigger());
		copyMessage.setTimeLabel(EcoreUtil.copy(message.getTimeLabel()));
		return copyMessage;
	}
	
	private String getLiteralValue (LiteralValueUse literalValue) {
		String value = literalValue.getValue();
		if (value.startsWith("\"") || value.startsWith("'")){
			return value.substring(1, value.length()-1);//remove quotation marks
    	}
		return value;
	}
	
//	private static boolean compareModels(Resource model1, Resource model2) {
//		IComparisonScope scope = new DefaultComparisonScope(model1, model2, null);
//		Comparison comparison = EMFCompare.builder().build().compare(scope);
//
//		List<Diff> differences = comparison.getDifferences();
//
//		if (differences.size() == 0) {
//			return true;
//		}
//
//		return false;
//	}
}
