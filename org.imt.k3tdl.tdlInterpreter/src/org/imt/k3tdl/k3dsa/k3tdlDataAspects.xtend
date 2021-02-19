package org.imt.k3tdl.k3dsa

import fr.inria.diverse.k3.al.annotationprocessor.Aspect

import fr.inria.diverse.k3.al.annotationprocessor.OverrideAspectMethod
import java.util.Properties
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.gemoc.dsl.Dsl
import org.etsi.mts.tdl.DataInstance
import org.etsi.mts.tdl.DataInstanceUse
import org.etsi.mts.tdl.DataType
import org.etsi.mts.tdl.LiteralValueUse
import org.etsi.mts.tdl.Member
import org.etsi.mts.tdl.MemberAssignment
import org.etsi.mts.tdl.Message
import org.etsi.mts.tdl.ParameterBinding
import org.etsi.mts.tdl.SimpleDataInstance
import org.etsi.mts.tdl.StructuredDataInstance
import org.etsi.mts.tdl.StructuredDataType

import static extension org.imt.k3tdl.k3dsa.DataTypeAspect.*
import static extension org.imt.k3tdl.k3dsa.MemberAspect.*
import static extension org.imt.k3tdl.k3dsa.MemberAssignmentAspect.*
import static extension org.imt.k3tdl.k3dsa.ParameterBindingAspect.*
import static extension org.imt.k3tdl.k3dsa.StructuredDataInstanceAspect.*
import static extension org.imt.k3tdl.k3dsa.TestConfigurationAspect.*
import static extension org.imt.k3tdl.k3dsa.DataInstanceUseAspect.*
import org.etsi.mts.tdl.SpecialValueUse
import fr.inria.diverse.k3.al.annotationprocessor.Step

@Aspect (className = DataType)
class DataTypeAspect{
	def boolean isDynamicType() {
		if (_self instanceof StructuredDataType){
			val StructuredDataType type = _self as StructuredDataType
			//check if the type has a 'dynamic' annotation itself
			for (i : 0 ..<type.annotation.size){
				if (type.annotation.get(i).key.name.toString.contains("dynamic")) {
					return true;
				}
			}
			//check if as least one of the members of type has a 'dynamic' annotation
			for (i : 0 ..<type.member.size){
				val Member m = type.member.get(i)
				for (j : 0 ..<m.annotation.size){
					if (m.annotation.get(j).key.name.toString.contains("dynamic")) {
						return true;
					}
				}
			}
		}
		return false;
	}
	def boolean isConcreteEcoreType(String DSLPath) {
		var dslRes = (new ResourceSetImpl()).getResource(URI.createURI(DSLPath), true);
		var dsl = dslRes.getContents().get(0) as Dsl;
		if (dsl.getEntry("ecore") != null) {
			var metamodelPath = dsl.getEntry("ecore").getValue().replaceFirst("resource", "plugin");
			var metamodelRes = (new ResourceSetImpl()).getResource(URI.createURI(metamodelPath), true);
			var metamodelRootElement = metamodelRes.getContents().get(0) as EPackage;
			return metamodelRootElement.EClassifiers.exists[c | c.name.equals(_self.getValidName) && !c.eClass.abstract]
		}
		return false;
	}
	def String getValidName(){
		var tdlName = _self.name
		if (_self.name.startsWith("_")){
			return tdlName.substring(1)
		}
		return tdlName
	}
}
@Aspect (className = DataInstance)
class DataInstanceAspect{
	def EObject getMatchedMUTElement(EObject rootElement, Resource MUTResource, boolean isAssertion){
		
	}
}
@Aspect (className = SimpleDataInstance)
class SimpleDataInstanceAspect extends DataInstanceAspect{
	@OverrideAspectMethod
	def EObject getMatchedMUTElement(EObject rootElement, Resource MUTResource, boolean isAssertion){
		println("The " + _self.name + " element cannot be found since it has no identifier")
		println("Please specify the values of its static features")
		return null;
	}
}
@Aspect (className = StructuredDataInstance)
class StructuredDataInstanceAspect extends DataInstanceAspect{
	@OverrideAspectMethod
	def EObject getMatchedMUTElement(EObject rootElement, Resource MUTResource, boolean isAssertion){
		//find matched elements based on the memberAssignments of dataInstance
		for (i : 0 ..<_self.memberAssignment.size){
			if (isAssertion){//all the arguments (static and dynamic) have to be matched
				if (!_self.memberAssignment.get(i).isMatchedMember(rootElement, MUTResource)){
					return null;
				}
			}else{//only static arguments have to be matched
				if (!_self.memberAssignment.get(i).member.dataType.isDynamicType
					&& !_self.memberAssignment.get(i).member.isDynamicMember){
					if (!_self.memberAssignment.get(i).isMatchedMember(rootElement, MUTResource)){
						return null;
					}
				}
			}
		}
		//all the members are matched with the attributes of the rootElement
		return rootElement;
	}
}
@Aspect (className = DataInstanceUse)
class DataInstanceUseAspect{
	def EObject getMatchedMUTElement(Resource MUTResource, boolean isAssertion){
		var EObject rootElement = null
		//val String DSLPath = (_self.eContainer as Message).parentTestDescription.testConfiguration.DSLPath
		val String DSLPath = "platform:/plugin/org.eclipse.gemoc.sample.ale.fsm.model/model/fsm.dsl"
		//if data type is abstract return null
		if (!_self.dataInstance.dataType.isConcreteEcoreType(DSLPath)){
			println("The " + _self.dataInstance.name + " element is abstract")
			println("Only concrete elements can be found in the model under test")
			return null;
		}
		val dataTypeName = _self.dataInstance.dataType.getValidName
		rootElement = MUTResource.getContents().get(0)
		if (!rootElement.eClass.name.equals(dataTypeName)){
			for (EObject object: rootElement.eContents){				
				if (object.eClass.name.equals(dataTypeName)){
					rootElement = object
				}
			}
		}
		if (_self.dataInstance instanceof StructuredDataInstance){
			//some attributes are set as member assignments for dataInstance
			//so find the matched element based on the dataInstance
			val dataIns = _self.dataInstance as StructuredDataInstance
			rootElement = dataIns.getMatchedMUTElement(rootElement, MUTResource, isAssertion)
			if (rootElement == null){
				return null;
			}
		}
		//find matched elements based on the parameter bindings of dataInstanceUse
		for (i : 0 ..<_self.argument.size){
			val parameterBinding = _self.argument.get(i);
			if (isAssertion){//all the arguments (static and dynamic) have to be matched
				if (!parameterBinding.isMatchedParameter(rootElement, MUTResource)){
					return null;
				}
			}else{//only static arguments have to be matched
				if (!parameterBinding.parameter.dataType.isDynamicType
					&& !(parameterBinding.parameter as Member).isDynamicMember){
					if (!parameterBinding.isMatchedParameter(rootElement, MUTResource)){
						return null;
					}
				}
			}			
		}
		//all the parameters are matched with the attributes of the rootElement
		return rootElement;
	}
	@Step
	def boolean setMatchedMUTElement(Resource MUTResource){
		//the second parameter is isAssertion that has to be set as false
		//so only static elements will be matched to then set the values of its dynamic features
		var EObject matchedObject = _self.getMatchedMUTElement(MUTResource, false)
		if (matchedObject == null){
			println("There is no matched object in the model under test")
			return false;
		}
		var boolean status = false;
		if (_self.dataInstance instanceof StructuredDataInstance){			
			//some attributes are set as member assignments for dataInstance
			//so find the matched element based on the dataInstance
			val dataIns = _self.dataInstance as StructuredDataInstance
			for (i : 0 ..<dataIns.memberAssignment.size){
				var memberAssig = dataIns.memberAssignment.get(i)
				if (memberAssig.member.dataType.isDynamicType
					|| memberAssig.member.isDynamicMember){
					status = memberAssig.setMatchedMember(matchedObject, MUTResource)
				}
			}
		}
		//find matched elements based on the parameter bindings of dataInstanceUse
		for (i : 0 ..<_self.argument.size){
			var parameterBinding = _self.argument.get(i);
			if (parameterBinding.parameter.dataType.isDynamicType
				|| (parameterBinding.parameter as Member).isDynamicMember){
				status = parameterBinding.setMatchedParameter(matchedObject, MUTResource);
			}			
		}
		return status;
	}
}
@Aspect (className = MemberAssignment)
class MemberAssignmentAspect{
	def boolean isMatchedMember(EObject rootElement, Resource MUTResource){
		val matchedFeature = rootElement.eClass.EAllStructuralFeatures.
			findFirst[f | f.name.equals(_self.member.validName)]
		val featureValue = rootElement.eGet(matchedFeature)
		if (matchedFeature == null){
			println("There is no such a property in the model under test")
			return false;
		}
		if (_self.memberSpec instanceof LiteralValueUse){// the value is a string
			var String memberValue = (_self.memberSpec as LiteralValueUse).value
			memberValue = memberValue.substring(1, memberValue.length-1)//remove quotation marks
			if (featureValue.toString.equals(memberValue)){
				return true;
			}
			return false;
		}else if (_self.memberSpec instanceof SpecialValueUse){//the value is ('?' or '*' or 'omit') that should be ignored
			return true;
		}else if (_self.memberSpec instanceof DataInstanceUse){
			//TODO: check if there are several DataInstanceUse
			val EObject matchedObject = (_self.memberSpec as DataInstanceUse).getMatchedMUTElement(MUTResource , true)
			if (matchedObject == null ){
				return false;
			}else if (!matchedObject.equals(featureValue)){
				return false;
			}else{
				return true;
			}
			//TODO: the parameter with exactEquivalent annotation should be equal to the real element
			//TODO: the parameter with partialEquivalent annotation would be contained in the real element
		}
		return false;
	} 
	def boolean setMatchedMember(EObject rootElement, Resource MUTResource){
		val matchedFeature = rootElement.eClass.EAllStructuralFeatures.
			findFirst[f | f.name.equals(_self.member.validName)]
		if (_self.memberSpec instanceof LiteralValueUse){//the value is a string
			val String memberValue = (_self.memberSpec as LiteralValueUse).value;
			try{
				rootElement.eSet(matchedFeature, memberValue)
			}catch(IllegalArgumentException e){
				println("There is no such a property in the model under test")
				return false;
			}
			return true;
		}else if (_self.memberSpec instanceof SpecialValueUse){//the value is ('?' or '*' or 'omit') that should be ignored
			return true;
		}else if (_self.memberSpec instanceof DataInstanceUse){
			//TODO: the parameter with exactEquivalent annotation should be equal to the real element
			//TODO: the parameter with partialEquivalent annotation would be contained in the real element
		}
		return false;
	} 
}
@Aspect (className = ParameterBinding)
class ParameterBindingAspect{
	def boolean isMatchedParameter(EObject rootElement, Resource MUTResource){
		val matchedFeature = rootElement.eClass.EAllStructuralFeatures.
			findFirst[f | f.name.equals((_self.parameter as Member).validName)]
		val featureValue = rootElement.eGet(matchedFeature)
		if (matchedFeature == null){
			println("There is no such a property in the model under test")
			return false;
		}
		if (_self.dataUse instanceof LiteralValueUse){// the value is a string
			val String parameterValue = (_self.dataUse as LiteralValueUse).value
			if (featureValue.toString.equals(parameterValue)){
				return true;
			}
			return false;
		}else if (_self.dataUse instanceof SpecialValueUse){//the value is ('?' or '*' or 'omit') that should be ignored
			return true;
		}else if (_self.dataUse instanceof DataInstanceUse){
			//TODO: has to check if there are several dataInstanceuse
			val EObject matchedObject = (_self.dataUse as DataInstanceUse).getMatchedMUTElement(MUTResource , true)
			if (matchedObject == null ){
				return false;
			}else if (!matchedObject.equals(featureValue)){
				return false;
			}else{
				return true;
			}
			//TODO: the parameter with exactEquivalent annotation should be equal to the real element
			//TODO: the parameter with partialEquivalent annotation would be contained in the real element
		}
		return false;
	} 
	def boolean setMatchedParameter(EObject rootElement, Resource MUTResource){
		val matchedFeature = rootElement.eClass.EAllStructuralFeatures.
			findFirst[f | f.name.equals((_self.parameter as Member).validName)]
		if (_self.dataUse instanceof LiteralValueUse){//the value is a string
			var String parameterValue = (_self.dataUse as LiteralValueUse).value
			parameterValue = parameterValue.substring(1, parameterValue.length-1)//remove quotation marks
			try{
				rootElement.eSet(matchedFeature, parameterValue)
			}catch(IllegalArgumentException e){
				println("There is no such a property in the model under test")
				return false;
			}
			return true;
		}else if (_self.dataUse instanceof SpecialValueUse){//the value is ('?' or '*' or 'omit') that should be ignored
			return true;
		}else if (_self.dataUse instanceof DataInstanceUse){
			//TODO: the parameter with exactEquivalent annotation should be equal to the real element
			//TODO: the parameter with partialEquivalent annotation would be contained in the real element
		}
		return false;
	} 
}
@Aspect (className = Member)
class MemberAspect{
	def String getValidName(){
		var tdlName = _self.name
		if (_self.name.startsWith("_")){
			return tdlName.substring(1)
		}
		return tdlName
	}
	def boolean isDynamicMember() {
		for (j : 0 ..<_self.annotation.size){
			if (_self.annotation.get(j).key.name.toString.contains("dynamic")) {
				return true;
			}
		}
		return false;
	}
}