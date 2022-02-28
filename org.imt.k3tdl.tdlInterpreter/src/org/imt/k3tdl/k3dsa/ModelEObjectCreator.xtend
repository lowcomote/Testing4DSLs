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
					val EObject featureValue = memberValue.getMatchedMUTElement(MUTResource, isAssertion, DSLPath)
					if (featureValue !== null){
						newEObject.eSet(eStructuralFeature, featureValue)
					}else{
						newEObject.eSet(eStructuralFeature, createEObject(memberValue))
					}
				}
			}
		}
		for (i : 0 ..<TDLObject.argument.size){//check the parameter bindings
			val parameterBinding = TDLObject.argument.get(i)
			val eStructuralFeature = eobjectType.getEStructuralFeature(getValidName(parameterBinding.parameter.name))
			if (eStructuralFeature !== null && parameterBinding.dataUse instanceof DataInstanceUse){
				val parameterValue = parameterBinding.dataUse as DataInstanceUse
				val EObject featureValue = parameterValue.getMatchedMUTElement(MUTResource, isAssertion, DSLPath)
				if (featureValue !== null){
					newEObject.eSet(eStructuralFeature, featureValue)
				}else{
					newEObject.eSet(eStructuralFeature, createEObject(parameterValue))
				}
			}
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