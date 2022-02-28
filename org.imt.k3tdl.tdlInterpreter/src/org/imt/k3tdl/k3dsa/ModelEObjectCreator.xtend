package org.imt.k3tdl.k3dsa

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.gemoc.dsl.Dsl
import org.etsi.mts.tdl.DataInstanceUse
import org.etsi.mts.tdl.MemberAssignment
import org.etsi.mts.tdl.StructuredDataInstance

import static extension org.imt.k3tdl.k3dsa.DataInstanceUseAspect.*
import static extension org.imt.k3tdl.k3dsa.DataTypeAspect.*
import java.util.List
import java.util.ArrayList
import org.eclipse.emf.ecore.EStructuralFeature
import org.etsi.mts.tdl.DataUse
import org.etsi.mts.tdl.LiteralValueUse

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
		rootEPackage = getRootEPackage(DSLPath)
		return createEObject(TDLObject);
	}
	
	def EObject createEObject(DataInstanceUse TDLObject){
		//create an EObject using the factory of its EClass
		rootEPackage = getRootEPackage(DSLPath)
		val String eclassName = getValidName(TDLObject.dataInstance.dataType)
		val EClass eobjectType = rootEPackage.getEClassifier(eclassName) as EClass
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
				if (eStructuralFeature !== null && memberAssignment.memberSpec instanceof DataInstanceUse){
					val memberValue = memberAssignment.memberSpec as DataInstanceUse
					setFeatureValue(newEObject, eStructuralFeature, getTdlValues(memberValue))
				}
			}
		}
		for (i : 0 ..<TDLObject.argument.size){//check the parameter bindings
			val parameterBinding = TDLObject.argument.get(i)
			val eStructuralFeature = eobjectType.getEStructuralFeature(getValidName(parameterBinding.parameter.name))
			if (eStructuralFeature !== null && parameterBinding.dataUse instanceof DataInstanceUse){
				val parameterValue = parameterBinding.dataUse as DataInstanceUse
				setFeatureValue(newEObject, eStructuralFeature, getTdlValues(parameterValue))
			}
		}
	}
	
	def List<DataUse> getTdlValues (DataInstanceUse dataInstanceUse){
		var List<DataUse> tdlValues = new ArrayList
		if (dataInstanceUse.item=== null || dataInstanceUse.item.size <= 0){
			tdlValues.add(dataInstanceUse)
		}else{
			for (i:0 ..<dataInstanceUse.item.size){
				tdlValues.add(dataInstanceUse.item.get(i))
			}
		}
		return tdlValues
	}
	
	def void setFeatureValue (EObject newEObject, EStructuralFeature feature, List<DataUse> featureTdlValues){
		
		//all the values must be from the same type:
		//(1) if they are dataInstanceUse, it means they are references to EObjects
		if (featureTdlValues.get(0) instanceof DataInstanceUse){
			var List<EObject> featureValues = new ArrayList
			for (DataUse tdlValue : featureTdlValues){
				val EObject featureValue = (tdlValue as DataInstanceUse).getMatchedMUTElement(MUTResource, isAssertion, DSLPath)
				if (featureValue !== null){
					featureValues.add(featureValue)
				}else{
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
		if (featureTdlValues.get(0) instanceof LiteralValueUse){
			//TODO: check for different supported primitives
		}
	}
	
	def EPackage getRootEPackage(String DSLPath) {
		var dslRes = (new ResourceSetImpl()).getResource(URI.createURI(DSLPath), true);
		var dsl = dslRes.getContents().get(0) as Dsl;
		if (dsl.getEntry("ecore") !== null) {
			var metamodelPath = dsl.getEntry("ecore").getValue().replaceFirst("resource", "plugin");
			var metamodelRes = (new ResourceSetImpl()).getResource(URI.createURI(metamodelPath), true);
			return metamodelRes.getContents().get(0) as EPackage;
		}
		return null;
	}
	
	def String getValidName(String name){
		if (name.startsWith("_")){
			return name.substring(1)
		}
		return name
	}
}