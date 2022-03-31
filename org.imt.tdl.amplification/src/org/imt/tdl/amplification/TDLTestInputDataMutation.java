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
import org.etsi.mts.tdl.TestDescription;
import org.etsi.mts.tdl.tdlFactory;

public class TDLTestInputDataMutation {
	
	List<LiteralValueUse> boolLiterals = new ArrayList<LiteralValueUse>();
	List<LiteralValueUse> stringLiterals = new ArrayList<LiteralValueUse>();
	List<LiteralValueUse> intLiterals = new ArrayList<LiteralValueUse>();
	List<LiteralValueUse> floatLiterals = new ArrayList<LiteralValueUse>();
	int numOfNewTests = 0;
	
	private static String BOOLMUTATION = "BooleanMutation";
	private static String STRINGMUTATION = "StringMutation";
	private static String INTMUTATION = "IntegerMutation";
	private static String FLOATMUTATION = "FloatMutation";
	private static String EVENTDUPLICATION = "EventDuplication";
	private static String EVENTCREATION = "EventCreation";
	private static String EVENTDELETION = "EventDeletion";
	private static String EVENTPERMUTATION = "EventPermutation";
	private static String EVENTMODIFICATION = "EventModification";
	
	public List<TestDescription> generateNewTestsByInputMutation (TestDescription tdlTestCase) {
		List<TestDescription> generatedTestsByMutation = new ArrayList<>();
		generatedTestsByMutation.addAll(mutateLiteralData (tdlTestCase));
		generatedTestsByMutation.addAll(mutateExchangedEvents (tdlTestCase));
		return generatedTestsByMutation;
	}

	/* four operators can be applied:
	 * 1. addition : duplication of existing & creation of new events based on the interface
	 * 2. deletion
	 * 3. reordering
	 * 4. modification of event parameters based on model elements used in the event parameters
	 * 
	 * NOTE: We assume a TDL test case only contains a series of Message elements each sending an accepted event to the model under test
	 */
	private Collection<? extends TestDescription> mutateExchangedEvents(TestDescription tdlTestCase) {
		List<TestDescription> generatedTestsByMutation = new ArrayList<>();
		Block messagesContainer = ((CompoundBehaviour) tdlTestCase.getBehaviourDescription().getBehaviour()).getBlock();
		List<Message> tdlMessages = messagesContainer.getBehaviour().stream().map(b -> (Message) b).collect(Collectors.toList());
		//1.1. addition-duplication of existing messages
		for (Message tdlMessage: tdlMessages) {
			TestDescription copyTdlTestCase = copyTdlTestCase(tdlTestCase, EVENTDUPLICATION);
			Block copyContainer = ((CompoundBehaviour) copyTdlTestCase.getBehaviourDescription().getBehaviour()).getBlock();
			copyContainer.getBehaviour().add(copyTdlMessage(tdlMessage));
			generatedTestsByMutation.add(copyTdlTestCase);
		}
		//1.2. addition-creation of new events
		//TODO
		//2. deletion
		for (Message tdlMessage: tdlMessages) {
			TestDescription copyTdlTestCase = copyTdlTestCase(tdlTestCase, EVENTDELETION);
			Block copyContainer = ((CompoundBehaviour) copyTdlTestCase.getBehaviourDescription().getBehaviour()).getBlock();
			copyContainer.getBehaviour().remove(tdlMessage);
			generatedTestsByMutation.add(copyTdlTestCase);
		}
		//3. permutation
		TestDescription copyTdlTestCase = copyTdlTestCase(tdlTestCase, EVENTPERMUTATION);
		Block copyContainer = ((CompoundBehaviour) copyTdlTestCase.getBehaviourDescription().getBehaviour()).getBlock();
		List<Message> copyMessages = copyContainer.getBehaviour().stream().map(b -> (Message) b).collect(Collectors.toList());
		Collections.shuffle(copyMessages);
		copyContainer.getBehaviour().clear();
		copyContainer.getBehaviour().addAll(copyMessages);
		generatedTestsByMutation.add(copyTdlTestCase);
		//4. modification
		//TODO
		return generatedTestsByMutation;
	}

	private List<TestDescription> mutateLiteralData(TestDescription tdlTestCase) {
		List<TestDescription> generatedTestsByMutation = new ArrayList<>();
		Iterator<EObject> iterator = tdlTestCase.eAllContents();
		while (iterator.hasNext()) {
			EObject object = iterator.next();
			if (object instanceof DataInstanceUse) {
				findLiteralFeaturesOfData((DataInstanceUse) object);
			}
		}
		if (boolLiterals.size()>0) {
			generatedTestsByMutation.addAll(mutateBooleanData(tdlTestCase));
		}
		else if (stringLiterals.size()>0) {
			generatedTestsByMutation.addAll(mutateStringData(tdlTestCase));
		}
		else if (intLiterals.size()>0) {
			generatedTestsByMutation.addAll(mutateIntegerData(tdlTestCase));
		}
		else if (floatLiterals.size()>0) {
			generatedTestsByMutation.addAll(mutateFloatData(tdlTestCase));
		}
		return generatedTestsByMutation;
	}
	
	private Collection<? extends TestDescription> mutateBooleanData(TestDescription tdlTestCase) {
		List<TestDescription> generatedTestsByMutation = new ArrayList<>();
		for (LiteralValueUse boolLiteral:boolLiterals) {
			if (getLiteralValue(boolLiteral).equals("true")) {
				boolLiteral.setValue("false");
			}
			else if (getLiteralValue(boolLiteral).equals("false")) {
				boolLiteral.setValue("true");
			}
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase, BOOLMUTATION));
		}
		return generatedTestsByMutation;
	}

	/*four operators: 
	 * 1. add a random char, 
	 * 2. remove a random char, 
	 * 3. replace a random char and 
	 * 4. replace the string by a fully random string of the same size
	 */
	private Collection<? extends TestDescription> mutateStringData(TestDescription tdlTestCase) {
		List<TestDescription> generatedTestsByMutation = new ArrayList<>();
		for (LiteralValueUse stringLiteral:stringLiterals) {
			String value = getLiteralValue(stringLiteral);
			StringBuilder sb = new StringBuilder(value);
			Random rand = new Random();
			int randomIndex = rand.nextInt(sb.length());
			// 1. add a random char
			sb.append(RandomStringUtils.randomAlphanumeric(1));
			stringLiteral.setValue(sb.toString());
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase, STRINGMUTATION));
			//2. remove a random char
			sb = new StringBuilder(value);
			sb.deleteCharAt(randomIndex);
			stringLiteral.setValue(sb.toString());
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase, STRINGMUTATION));
			//3. replace a random char
			sb = new StringBuilder(value);
			sb.replace(randomIndex, randomIndex + 1, RandomStringUtils.randomAlphanumeric(1));
			stringLiteral.setValue(sb.toString());
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase, STRINGMUTATION));
			//4. replace the string by a fully random string of the same size
			stringLiteral.setValue(RandomStringUtils.randomAlphanumeric(value.length()));
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase, STRINGMUTATION));
		}
		return generatedTestsByMutation;
	}

	/* five operators for numeric values: 
	 * plus 1, minus 1, multiply by 2, divide by 2, and replacement by an existing literal of the same type
	 */
	private Collection<? extends TestDescription> mutateIntegerData(TestDescription tdlTestCase) {
		List<TestDescription> generatedTestsByMutation = new ArrayList<>();
		for (LiteralValueUse intLiteral:intLiterals) {
			int value = Integer.parseInt(getLiteralValue(intLiteral));
			//1. value plus 1
			intLiteral.setValue("'" + (value + 1) + "'");
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase, INTMUTATION));
			//2. value minus 1
			intLiteral.setValue("'" + (value - 1) + "'");
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase, INTMUTATION));
			//3. value multiply by 2
			intLiteral.setValue("'" + (value * 2) + "'");
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase, INTMUTATION));
			//4. value divide by 2
			intLiteral.setValue("'" + (value / 2) + "'");
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase, INTMUTATION));
			//5. replacement by an existing literal of the same type
			Random rand = new Random();
			int randomIndex = rand.nextInt(intLiterals.size());
			while (randomIndex == intLiterals.indexOf(intLiteral)) {
				randomIndex = rand.nextInt(intLiterals.size());
			}
			String exValue = intLiterals.get(randomIndex).getValue();
			intLiteral.setValue(exValue);
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase, INTMUTATION));
		}
		return generatedTestsByMutation;
	}

	private Collection<? extends TestDescription> mutateFloatData(TestDescription tdlTestCase) {
		List<TestDescription> generatedTestsByMutation = new ArrayList<>();
		for (LiteralValueUse floatLiteral:floatLiterals) {
			float value = Float.parseFloat(getLiteralValue(floatLiteral));
			//1. value plus 1
			floatLiteral.setValue("'" + (value + 1) + "'");
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase, FLOATMUTATION));
			//2. value minus 1
			floatLiteral.setValue("'" + (value - 1) + "'");
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase, FLOATMUTATION));
			//3. value multiply by 2
			floatLiteral.setValue("'" + (value * 2) + "'");
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase, FLOATMUTATION));
			//4. value divide by 2
			floatLiteral.setValue("'" + (float)(value / 2) + "'");
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase, FLOATMUTATION));
			//5. replacement by an existing literal of the same type
			Random rand = new Random();
			int randomIndex = rand.nextInt(floatLiterals.size());
			while (randomIndex == floatLiterals.indexOf(floatLiteral)) {
				randomIndex = rand.nextInt(floatLiterals.size());
			}
			String exValue = floatLiterals.get(randomIndex).getValue();
			floatLiteral.setValue(exValue);
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase, FLOATMUTATION));
		}
		return generatedTestsByMutation;
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
	
	private TestDescription copyTdlTestCase(TestDescription testCase, String mutationOperator) {
		TestDescription copyTdlTestCase = tdlFactory.eINSTANCE.createTestDescription();
		copyTdlTestCase.setName(testCase.getName() + (numOfNewTests++) + "_" + mutationOperator);
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
}
