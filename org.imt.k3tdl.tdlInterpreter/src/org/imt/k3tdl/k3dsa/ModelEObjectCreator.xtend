package org.imt.k3tdl.k3dsa

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import org.etsi.mts.tdl.DataInstanceUse
import org.etsi.mts.tdl.DataUse
import org.etsi.mts.tdl.LiteralValueUse
import org.etsi.mts.tdl.MemberAssignment
import org.etsi.mts.tdl.StructuredDataInstance

import static org.imt.k3tdl.k3dsa.DataTypeAspect.*

import static extension org.imt.k3tdl.k3dsa.DataInstanceUseAspect.*

class ModelEObjectCreator {
	
	Resource MUTResource;
	EPackage rootEPackage;
	boolean isAssertion;
	String DSLPath;
	
	def EObject createEObject(DataInstanceUse TDLObject, Resource MUTResource, boolean isAssertion, String DSLPath){
		this.MUTResource = MUTResource
		this.isAssertion = isAssertion
		this.DSLPath = DSLPath
		//create an EObject using the factory of its EClass
		rootEPackage = MUTResource.contents.get(0).eClass.EPackage
		return createEObject(TDLObject);
	}
	
	def EObject createEObject(DataInstanceUse TDLObject){
		//create an EObject using the factory of its EClass
		val String eclassName = getValidName(TDLObject.dataInstance.dataType)
		val EClass eobjectType = rootEPackage.getEClassifier(eclassName) as EClass
		if (eobjectType === null){
			return null;
		}
		var EObject newEObject = rootEPackage.EFactoryInstance.create(eobjectType)
		//assign the value of its attributes
		setEObjectFeatures(TDLObject, newEObject, eobjectType)
		return newEObject
	}
	
	def void setEObjectFeatures(DataInstanceUse TDLObject, EObject newEObject, EClass eobjectType){
		if (TDLObject.dataInstance instanceof StructuredDataInstance && 
			(TDLObject.dataInstance as StructuredDataInstance).memberAssignment.size>0){//check the member assignments
			val StructuredDataInstance dataInstance = TDLObject.dataInstance as StructuredDataInstance
			for (MemberAssignment memberAssignment: dataInstance.memberAssignment){
				val eStructuralFeature = eobjectType.getEStructuralFeature(getValidName(memberAssignment.member.name))
				if (eStructuralFeature !== null){
					val memberValue = memberAssignment.memberSpec
					setFeatureValue(newEObject, eStructuralFeature, getTdlValues(memberValue))
				}
			}
		}
		for (i : 0 ..<TDLObject.argument.size){//check the parameter bindings
			val parameterBinding = TDLObject.argument.get(i)
			val eStructuralFeature = eobjectType.getEStructuralFeature(getValidName(parameterBinding.parameter.name))
			if (eStructuralFeature !== null){
				val parameterValue = parameterBinding.dataUse
				setFeatureValue(newEObject, eStructuralFeature, getTdlValues(parameterValue))
			}
		}
	}
	
	def List<DataUse> getTdlValues (DataUse dataUse){
		var List<DataUse> tdlValues = new ArrayList
		if (dataUse instanceof DataInstanceUse){
			val dataInstanceUse = dataUse as DataInstanceUse
			if (dataInstanceUse.item=== null || dataInstanceUse.item.size <= 0){
				tdlValues.add(dataInstanceUse)
			}else{
				for (i:0 ..<dataInstanceUse.item.size){
					tdlValues.add(dataInstanceUse.item.get(i))
				}
			}
		}else if (dataUse instanceof LiteralValueUse){
			tdlValues.add(dataUse)
		}
		return tdlValues
	}
	
	def void setFeatureValue (EObject newEObject, EStructuralFeature feature, List<DataUse> featureTdlValues){
		//all the values must be from the same type:
		//(1) if they are dataInstanceUse, it means they are references to EObjects of the model under test
		if (featureTdlValues.get(0) instanceof DataInstanceUse){
			var List<EObject> featureValues = new ArrayList
			for (DataUse tdlValue : featureTdlValues){
				val EObject featureValue = (tdlValue as DataInstanceUse).getMatchedMUTElement(MUTResource, isAssertion, DSLPath)
				if (featureValue !== null){//if the EObject found in the model, add it as a value for the feature
					featureValues.add(featureValue)
				}else{//if the EObject is not found in the model, we must create it and then add it as a value for the feature
					featureValues.add(createEObject(tdlValue as DataInstanceUse))
				}
			}
			if (!featureValues.empty){
				if (feature.isMany){//the feature value is a list of eobjects
					newEObject.eSet(feature, featureValues)
				}else{//the feature value is only one eobject
					newEObject.eSet(feature, featureValues.get(0))
				}
			}
		}
		//if they are LiteralValueUse, it means they are primitives
		else if (featureTdlValues.get(0) instanceof LiteralValueUse){
			if (feature.EType.name.equals("EIntegerObject") || feature.EType.name.equals("EInt")){
				if (!feature.isMany){//a single integer must be set as the value
					var featureValue = getLiteralValue(featureTdlValues.get(0) as LiteralValueUse)
					Integer.parseInt(featureValue)
					newEObject.eSet(feature, Integer.parseInt(featureValue))
				}else{//a list of integers must be set as the value
					var List<Integer> featureValues = new ArrayList
					for (DataUse tdlValue : featureTdlValues){
						var featureValue = getLiteralValue(tdlValue as LiteralValueUse)
			        	featureValues.add(Integer.parseInt(featureValue))
					}
					newEObject.eSet(feature, featureValues)
				}
			} else if (feature.EType.name.equals("EBooleanObject") || feature.EType.name.equals("EBoolean")){//TODO: must be tested
				if (!feature.isMany){//a single boolean must be set as the value
					var featureValue = getLiteralValue(featureTdlValues.get(0) as LiteralValueUse)
					newEObject.eSet(feature, Boolean.parseBoolean(featureValue))
				}else{//a list of booleans must be set as the value
					var List<Boolean> featureValues = new ArrayList
					for (DataUse tdlValue : featureTdlValues){
						var featureValue = getLiteralValue(tdlValue as LiteralValueUse)
			        	featureValues.add(Boolean.parseBoolean(featureValue))
					}
					newEObject.eSet(feature, featureValues)
				}
			} else {
				if (!feature.isMany){//a single string must be set as the value
					var featureValue = getLiteralValue(featureTdlValues.get(0) as LiteralValueUse)
					newEObject.eSet(feature, featureValue)
				}else{//a list of strings must be set as the value
					var List<String> featureValues = new ArrayList
					for (DataUse tdlValue : featureTdlValues){
						var featureValue = getLiteralValue(tdlValue as LiteralValueUse)
			        	featureValues.add(featureValue)
					}
					newEObject.eSet(feature, featureValues)
				}
			} 
		}
	}
	
	def String getLiteralValue(LiteralValueUse literalValue){
		var featureValue = literalValue.value
		if (featureValue.startsWith("\"") || featureValue.startsWith("'")){
    		featureValue = featureValue.substring(1, featureValue.length-1)//remove quotation marks
    	}
    	return featureValue
	}
	
	def String getValidName(String name){
		if (name.startsWith("_")){
			return name.substring(1)
		}
		return name
	}
}