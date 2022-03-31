package org.imt.tdl.amplification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.etsi.mts.tdl.DataInstanceUse;
import org.etsi.mts.tdl.DataType;
import org.etsi.mts.tdl.DataUse;
import org.etsi.mts.tdl.LiteralValueUse;
import org.etsi.mts.tdl.ParameterBinding;
import org.etsi.mts.tdl.TestDescription;
import org.etsi.mts.tdl.tdlFactory;

public class TDLTestInputDataMutation {
	
	List<LiteralValueUse> boolLiterals = new ArrayList<LiteralValueUse>();
	List<LiteralValueUse> stringLiterals = new ArrayList<LiteralValueUse>();
	List<LiteralValueUse> intLiterals = new ArrayList<LiteralValueUse>();
	List<LiteralValueUse> floatLiterals = new ArrayList<LiteralValueUse>();
	
	public List<TestDescription> generateNewTestsByInputMutation (TestDescription tdlTestCase) {
		//TODO: definition of mutation operators, WODEL or java? 
		/*1. primitive values used in the test cases (by memberAssignment or ParameterBinding)
		 * modification
		 * replacement
		 * 
		 * 2. events
		 * addition : duplication of existing & creation of new events based on the interface
		 * deletion
		 * reordering
		 * modification of event parameters based on model elements used in the event parameters
		 */
		//TODO: create new test cases with new data
		return mutateLiteralData (tdlTestCase);
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
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase));
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
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase));
			//2. remove a random char
			sb = new StringBuilder(value);
			sb.deleteCharAt(randomIndex);
			stringLiteral.setValue(sb.toString());
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase));
			//3. replace a random char
			sb = new StringBuilder(value);
			sb.replace(randomIndex, randomIndex + 1, RandomStringUtils.randomAlphanumeric(1));
			stringLiteral.setValue(sb.toString());
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase));
			//4. replace the string by a fully random string of the same size
			stringLiteral.setValue(RandomStringUtils.randomAlphanumeric(value.length()));
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase));
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
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase));
			//2. value minus 1
			intLiteral.setValue("'" + (value - 1) + "'");
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase));
			//3. value multiply by 2
			intLiteral.setValue("'" + (value * 2) + "'");
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase));
			//4. value divide by 2
			intLiteral.setValue("'" + (value / 2) + "'");
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase));
			//5. replacement by an existing literal of the same type
			Random rand = new Random();
			int randomIndex = rand.nextInt(intLiterals.size());
			while (randomIndex == intLiterals.indexOf(intLiteral)) {
				randomIndex = rand.nextInt(intLiterals.size());
			}
			String exValue = intLiterals.get(randomIndex).getValue();
			intLiteral.setValue(exValue);
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase));
		}
		return generatedTestsByMutation;
	}

	private Collection<? extends TestDescription> mutateFloatData(TestDescription tdlTestCase) {
		List<TestDescription> generatedTestsByMutation = new ArrayList<>();
		for (LiteralValueUse floatLiteral:floatLiterals) {
			float value = Float.parseFloat(getLiteralValue(floatLiteral));
			//1. value plus 1
			floatLiteral.setValue("'" + (value + 1) + "'");
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase));
			//2. value minus 1
			floatLiteral.setValue("'" + (value - 1) + "'");
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase));
			//3. value multiply by 2
			floatLiteral.setValue("'" + (value * 2) + "'");
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase));
			//4. value divide by 2
			floatLiteral.setValue("'" + (float)(value / 2) + "'");
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase));
			//5. replacement by an existing literal of the same type
			Random rand = new Random();
			int randomIndex = rand.nextInt(floatLiterals.size());
			while (randomIndex == floatLiterals.indexOf(floatLiteral)) {
				randomIndex = rand.nextInt(floatLiterals.size());
			}
			String exValue = floatLiterals.get(randomIndex).getValue();
			floatLiteral.setValue(exValue);
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase));
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
	
	private TestDescription copyTdlTestCase(TestDescription testCase) {
		TestDescription copyTdlTestCase = tdlFactory.eINSTANCE.createTestDescription();
		copyTdlTestCase.setName(testCase.getName());
		copyTdlTestCase.setTestConfiguration(testCase.getTestConfiguration());
		copyTdlTestCase.setBehaviourDescription(EcoreUtil.copy(testCase.getBehaviourDescription()));
		return copyTdlTestCase;
	}
	
	private String getLiteralValue (LiteralValueUse literalValue) {
		String value = literalValue.getValue();
		if (value.startsWith("\"") || value.startsWith("'")){
			return value.substring(1, value.length()-1);//remove quotation marks
    	}
		return value;
	}
}
