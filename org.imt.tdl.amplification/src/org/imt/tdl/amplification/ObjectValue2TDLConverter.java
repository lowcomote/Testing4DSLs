package org.imt.tdl.amplification;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.etsi.mts.tdl.DataInstanceUse;
import org.etsi.mts.tdl.DataType;
import org.etsi.mts.tdl.DataUse;
import org.etsi.mts.tdl.LiteralValueUse;
import org.etsi.mts.tdl.Member;
import org.etsi.mts.tdl.MemberAssignment;
import org.etsi.mts.tdl.SimpleDataInstance;
import org.etsi.mts.tdl.SimpleDataType;
import org.etsi.mts.tdl.StaticDataUse;
import org.etsi.mts.tdl.StructuredDataInstance;
import org.etsi.mts.tdl.StructuredDataType;

public class ObjectValue2TDLConverter {
	
	org.etsi.mts.tdl.Package tdlTestSuite;
	org.etsi.mts.tdl.tdlFactory tdlFactory = org.etsi.mts.tdl.tdlFactory.eINSTANCE;
	
	public ObjectValue2TDLConverter (org.etsi.mts.tdl.Package testSuite) {
		this.tdlTestSuite = testSuite;
	}
	public DataUse convertEObject2TDLData(EObject eobject){
		String eobjectTypeName = eobject.eClass().getName();
		StructuredDataType objectTdlType = (StructuredDataType) getTDLDataType(eobjectTypeName);
		StructuredDataInstance objectTdlInstance = tdlFactory.createStructuredDataInstance();
		objectTdlInstance.setDataType(objectTdlType);
		//if the object has a name, use its name for the created tdl instance
		if (eobject.eClass().getEStructuralFeature("name") != null) {
			EStructuralFeature nameFeature = eobject.eClass().getEStructuralFeature("name");
			objectTdlInstance.setName(eobject.eGet(nameFeature).toString());
		}else {
			objectTdlInstance.setName(eobjectTypeName + "_" + (int) Math.random());
		}
		//for each feature of the eobject, create a member assignment
		for (EStructuralFeature efeature:eobject.eClass().getEAllStructuralFeatures()) {
			Optional<Member> mOptional = objectTdlType.allMembers().stream().filter(m -> getValidName(m.getName()).equals(efeature.getName())).findFirst();
			if (mOptional.isPresent()) {
				MemberAssignment ma = tdlFactory.createMemberAssignment();
				ma.setMember(mOptional.get());
				ma.setMemberSpec(convertFeatureValue2TDLData(eobject, efeature));
				objectTdlInstance.getMemberAssignment().add(ma);
			}
		}
		tdlTestSuite.getPackagedElement().add(objectTdlInstance);
		DataInstanceUse messageArg = tdlFactory.createDataInstanceUse();
		messageArg.setDataInstance(objectTdlInstance);
		return messageArg;
	}
	
	private StaticDataUse convertFeatureValue2TDLData(EObject eobject, EStructuralFeature efeature) {
		//if the feature value is a literal, create LiteralValueUse
		if (efeature.getEType().getName().equals("EBooleanObject") || efeature.getEType().getName().equals("EBoolean")) {
			if (efeature.isMany()){//several values must be created for the feature
				@SuppressWarnings("unchecked")
				List<Boolean> eValues = (List<Boolean>) eobject.eGet(efeature);
				DataInstanceUse tdlValues = tdlFactory.createDataInstanceUse();
				for (Boolean eValue:eValues) {
					LiteralValueUse tdlValue = tdlFactory.createLiteralValueUse();
					tdlValue.setValue(eValue.toString());
					tdlValues.getItem().add(tdlValue);
				}
				return tdlValues;
			}else {
				boolean eValue = (boolean) eobject.eGet(efeature);
				LiteralValueUse tdlValue = tdlFactory.createLiteralValueUse();
				tdlValue.setValue("" + eValue);
				return tdlValue;
			}
		}
		else if (efeature.getEType().getName().equals("EIntegerObject") || efeature.getEType().getName().equals("EInt")) {
			if (efeature.isMany()){//several values must be created for the feature
				@SuppressWarnings("unchecked")
				List<Integer> eValues = (List<Integer>) eobject.eGet(efeature);
				DataInstanceUse tdlValues = tdlFactory.createDataInstanceUse();
				for (Integer eValue:eValues) {
					LiteralValueUse tdlValue = tdlFactory.createLiteralValueUse();
					tdlValue.setValue(eValue.toString());
					tdlValues.getItem().add(tdlValue);
				}
				return tdlValues;
			}else {
				int eValue = (int) eobject.eGet(efeature);
				LiteralValueUse tdlValue = tdlFactory.createLiteralValueUse();
				tdlValue.setValue("" + eValue);
				return tdlValue;
			}
		}
		else if (efeature.getEType().getName().equals("EFloatObject") || efeature.getEType().getName().equals("EFloat")) {
			if (efeature.isMany()){//several values must be created for the feature
				@SuppressWarnings("unchecked")
				List<Float> eValues = (List<Float>) eobject.eGet(efeature);
				DataInstanceUse tdlValues = tdlFactory.createDataInstanceUse();
				for (Float eValue:eValues) {
					LiteralValueUse tdlValue = tdlFactory.createLiteralValueUse();
					tdlValue.setValue(eValue.toString());
					tdlValues.getItem().add(tdlValue);
				}
				return tdlValues;
			}else {
				float eValue = (float) eobject.eGet(efeature);
				LiteralValueUse tdlValue = tdlFactory.createLiteralValueUse();
				tdlValue.setValue("" + eValue);
				return tdlValue;
			}
		}
		else if (efeature.getEType().getName().equals("EString")) {
			if (efeature.isMany()){//several values must be created for the feature
				@SuppressWarnings("unchecked")
				List<String> eValues = (List<String>) eobject.eGet(efeature);
				DataInstanceUse tdlValues = tdlFactory.createDataInstanceUse();
				for (String eValue:eValues) {
					LiteralValueUse tdlValue = tdlFactory.createLiteralValueUse();
					tdlValue.setValue(eValue.toString());
					tdlValues.getItem().add(tdlValue);
				}
				return tdlValues;
			}else {
				String eValue = (String) eobject.eGet(efeature);
				LiteralValueUse tdlValue = tdlFactory.createLiteralValueUse();
				tdlValue.setValue("" + eValue);
				return tdlValue;
			}
		}
		else {//feature value is a reference to another object
			if (efeature.isMany()){//several values must be created for the object
				@SuppressWarnings("unchecked")
				List<EObject> eValues = (List<EObject>) eobject.eGet(efeature);
				DataInstanceUse tdlValues = tdlFactory.createDataInstanceUse();
				for (EObject eValue:eValues) {
					//TODO: find for a dataInstance equivalent to eobject, if found use it otherwise create it and add it to the test suite
				}
				return tdlValues;
			}else {
				EObject eValue = (EObject) eobject.eGet(efeature);
				//TODO
			}
		}
		return null;
	}
	public DataUse convertBoolean2TDLData(boolean boolValue){
		SimpleDataType tdlBooleanType = (SimpleDataType) getTDLDataType("EBoolean");
		SimpleDataInstance tdlBooleanInstance = tdlFactory.createSimpleDataInstance();
		tdlBooleanInstance.setDataType(tdlBooleanType);
		tdlBooleanInstance.setName("" + boolValue);
		tdlTestSuite.getPackagedElement().add(tdlBooleanInstance);
		DataInstanceUse messageArg = tdlFactory.createDataInstanceUse();
		messageArg.setDataInstance(tdlBooleanInstance);
		return messageArg;
	}
	
	public DataUse convertInteger2TDLData(int intValue){
		SimpleDataType tdlIntType = (SimpleDataType) getTDLDataType("EInt");
		SimpleDataInstance tdlIntInstance = tdlFactory.createSimpleDataInstance();
		tdlIntInstance.setDataType(tdlIntType);
		tdlIntInstance.setName("" + intValue);
		tdlTestSuite.getPackagedElement().add(tdlIntInstance);
		DataInstanceUse messageArg = tdlFactory.createDataInstanceUse();
		messageArg.setDataInstance(tdlIntInstance);
		return messageArg;
	}
	
	public DataUse convertFloat2TDLData(float floatValue){
		SimpleDataType tdlFloatType = (SimpleDataType) getTDLDataType("EFloat");
		SimpleDataInstance tdlFloatInstance = tdlFactory.createSimpleDataInstance();
		tdlFloatInstance.setDataType(tdlFloatType);
		tdlFloatInstance.setName("" + floatValue);
		tdlTestSuite.getPackagedElement().add(tdlFloatInstance);
		DataInstanceUse messageArg = tdlFactory.createDataInstanceUse();
		messageArg.setDataInstance(tdlFloatInstance);
		return messageArg;
	}
	
	public DataUse convertString2TDLData(String stringValue){
		SimpleDataType tdlStringType = (SimpleDataType) getTDLDataType("EString");
		SimpleDataInstance tdlStringInstance = tdlFactory.createSimpleDataInstance();
		tdlStringInstance.setDataType(tdlStringType);
		tdlStringInstance.setName(stringValue);
		tdlTestSuite.getPackagedElement().add(tdlStringInstance);
		DataInstanceUse messageArg = tdlFactory.createDataInstanceUse();
		messageArg.setDataInstance(tdlStringInstance);
		return messageArg;
	}
	
	private DataType getTDLDataType (String typeName) {
		Iterator<EObject> iterator = tdlTestSuite.eAllContents();
		while (iterator.hasNext()) {
			EObject eObject = (EObject) iterator.next();
			if (eObject instanceof DataType) {
				DataType tdlType = (DataType) eObject;
				if (getValidName(tdlType.getName()).equals(typeName)) {
					return tdlType;
				}
			}
		}
		return null;
	}
	
	private String getValidName(String name){
		if (name.startsWith("_")){
			return name.substring(1);
		}
		return name;
	}
	
}
