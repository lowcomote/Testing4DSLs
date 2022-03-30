package org.imt.tdl.amplification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.etsi.mts.tdl.DataInstance;
import org.etsi.mts.tdl.DataInstanceUse;
import org.etsi.mts.tdl.DataType;
import org.etsi.mts.tdl.DataUse;
import org.etsi.mts.tdl.LiteralValueUse;
import org.etsi.mts.tdl.Member;
import org.etsi.mts.tdl.MemberAssignment;
import org.etsi.mts.tdl.ParameterBinding;
import org.etsi.mts.tdl.StructuredDataInstance;
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
			if (boolLiteral.getValue().equals("true")) {
				boolLiteral.setValue("false");
			}
			else if (boolLiteral.getValue().equals("false")) {
				boolLiteral.setValue("true");
			}
			generatedTestsByMutation.add(copyTdlTestCase(tdlTestCase));
		}
		return generatedTestsByMutation;
	}

	private Collection<? extends TestDescription> mutateStringData(TestDescription tdlTestCase) {
		// TODO Auto-generated method stub
		return null;
	}

	private Collection<? extends TestDescription> mutateIntegerData(TestDescription tdlTestCase) {
		// TODO Auto-generated method stub
		return null;
	}

	private Collection<? extends TestDescription> mutateFloatData(TestDescription tdlTestCase) {
		// TODO Auto-generated method stub
		return null;
	}

	private void findLiteralFeaturesOfData (DataInstanceUse dataInstanceUse){
		DataInstance dataInstance = dataInstanceUse.getDataInstance();
		if (dataInstance instanceof StructuredDataInstance) {
			StructuredDataInstance sdi = (StructuredDataInstance) dataInstance;
			for (MemberAssignment ma: sdi.getMemberAssignment()) {
				Member member = ma.getMember();
				DataUse memberValue = ma.getMemberSpec();
				if (!member.getName().equals("_name")) {
					if (memberValue instanceof LiteralValueUse) {
						classifyLiteralBasedOnType ((LiteralValueUse) memberValue, member.getDataType());
					}else if (memberValue instanceof DataInstanceUse) {
						findLiteralFeaturesOfData((DataInstanceUse) memberValue);
					}
				}
			}
		}
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
}
