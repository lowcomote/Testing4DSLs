package org.imt.k3tdl.k3dsa

import fr.inria.diverse.k3.al.annotationprocessor.Aspect
import fr.inria.diverse.k3.al.annotationprocessor.OverrideAspectMethod
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
import static extension org.imt.k3tdl.k3dsa.MemberAssignmentAspect.*
import static extension org.imt.k3tdl.k3dsa.ParameterBindingAspect.*
import static extension org.imt.k3tdl.k3dsa.StructuredDataInstanceAspect.*
import static extension org.imt.k3tdl.k3dsa.TestConfigurationAspect.*
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl
import com.sun.jdi.Value
import com.sun.jdi.PrimitiveType
import java.util.Properties
import org.eclipse.emf.ecore.xmi.impl.RootXMLContentHandlerImpl.Describer

@Aspect (className = DataType)
class DataTypeAspect{
	def boolean isDynamicType() {
		if (_self instanceof StructuredDataType){
			val StructuredDataType type = _self as StructuredDataType
			for (i : 0 ..<type.annotation.size){
				if (type.annotation.get(i).key.name.toString.contains("dynamic")) {
					return true;
				}
			}
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
			for (EClassifier classifier: metamodelRootElement.EClassifiers){
				if (classifier.name.equals(_self.getEcoreName) && !classifier.eClass.abstract){
					return true;
				}
			}
		}
		return false;
	}
	def String getEcoreName(){
		var tdlName = _self.name
		if (_self.name.startsWith("_")){
			return tdlName.substring(1)
		}
		return tdlName
	}
}
@Aspect (className = DataInstance)
class DataInstanceAspect{
	def EObject getMatchedMUTElement(EObject rootElement, boolean isAssertion){
		
	}
}
@Aspect (className = SimpleDataInstance)
class SimpleDataInstanceAspect extends DataInstanceAspect{
	@OverrideAspectMethod
	def EObject getMatchedMUTElement(EObject rootElement, boolean isAssertion){
		println("The " + _self.name + " element cannot be found since it has no identifier")
		println("Please specify the values of its static features")
		return null;
	}
}
@Aspect (className = StructuredDataInstance)
class StructuredDataInstanceAspect extends DataInstanceAspect{
	@OverrideAspectMethod
	def EObject getMatchedMUTElement(EObject rootElement, boolean isAssertion){
		//find matched elements based on the memberAssignments of dataInstance
		for (i : 0 ..<_self.memberAssignment.size){
			if (isAssertion){//all the arguments (static and dynamic) have to be matched
				if (_self.memberAssignment.get(i).getMatchedMember(rootElement)==null){
					return null;
				}
			}else{//only static arguments have to be matched
				if (!_self.memberAssignment.get(i).member.dataType.isDynamicType()){
					if (_self.memberAssignment.get(i).getMatchedMember(rootElement)==null){
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
		val String DSLPath = (_self.eContainer as Message).parentTestDescription.testConfiguration.DSLPath
		//if data type is abstract return null
		if (!_self.dataInstance.dataType.isConcreteEcoreType(DSLPath)){
			println("The " + _self.dataInstance.name + " element is abstract")
			println("Only concrete elements can be found in the model under test")
			return null;
		}
		val dataTypeName = _self.dataInstance.dataType.getEcoreName
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
			rootElement = dataIns.getMatchedMUTElement(rootElement, isAssertion)
			if (rootElement == null){
				return null;
			}
		}
		//find matched elements based on the parameter bindings of dataInstanceUse
		for (i : 0 ..<_self.argument.size){
			val parameterBinding = _self.argument.get(i);
			if (isAssertion){//all the arguments (static and dynamic) have to be matched
				if (parameterBinding.getMatchedParameter(rootElement)==null){
					return null;
				}
			}else{//only static arguments have to be matched
				if (!parameterBinding.parameter.dataType.isDynamicType()){
					if (parameterBinding.getMatchedParameter(rootElement)==null){
						return null;
					}
				}
			}			
		}
		//all the parameters are matched with the attributes of the rootElement
		return rootElement;
	}
	def boolean setMatchedMUTElement(Resource MUTResource){
		//the second parameter is isAssertion that has to be set as false
		//so only static elements will be matched to then set the values of its dynamic features
		var EObject matchedObject = _self.getMatchedMUTElement(MUTResource, false)
		if (matchedObject == null){
			println("There is no matched object in the model under test")
			return false;
		}
		if (_self.dataInstance instanceof StructuredDataInstance){
			//some attributes are set as member assignments for dataInstance
			//so find the matched element based on the dataInstance
			val dataIns = _self.dataInstance as StructuredDataInstance
			for (i : 0 ..<dataIns.memberAssignment.size){
				var memberAssig = dataIns.memberAssignment.get(i)
				if (memberAssig.member.dataType.isDynamicType){
					var EObject matchedMember = memberAssig.getMatchedMember(matchedObject)
					if (matchedMember == null){
						return false;
					}
					if (memberAssig.memberSpec instanceof LiteralValueUse){
						var memberValue = (memberAssig.memberSpec as LiteralValueUse).value;
						(matchedMember as Properties).setProperty(memberAssig.member.name, memberValue)
						//TODO: Setting the value in the MUT
					}
				}
			}
		}
		//find matched elements based on the parameter bindings of dataInstanceUse
		for (i : 0 ..<_self.argument.size){
			var parameterBinding = _self.argument.get(i);
			if (parameterBinding.parameter.dataType.isDynamicType){
				var EObject matchedParameter = parameterBinding.getMatchedParameter(matchedObject)
				if (matchedParameter == null){
					return false;
				}
				if (parameterBinding.dataUse instanceof LiteralValueUse){
					var parameterValue = (parameterBinding.dataUse as LiteralValueUse).value;
					(matchedParameter as Properties).setProperty(parameterBinding.parameter.name, parameterValue)
					//TODO: setting the values
				}
			}			
		}
	}
}
@Aspect (className = MemberAssignment)
class MemberAssignmentAspect{
	def EObject getMatchedMember(EObject rootElement){
		for (i : 0 ..< rootElement.eContents.size){
			//if (rootElement.eContents.get(i))
		}
		val propertyValue = (rootElement as Properties).getProperty(_self.member.name)
		//if (propertyValue != null && propertyValue )
		//comparing the values
		//TODO: the parameter with exactEquivalent annotation should be equal to the real element
		//TODO: the parameter with partialEquivalent annotation would be contained in the real element
		return null;
	} 
}
@Aspect (className = ParameterBinding)
class ParameterBindingAspect{
	def EObject getMatchedParameter(EObject rootElement){
		//TODO: the parameter with exactEquivalent annotation should be equal to the real element
		//TODO: the parameter with partialEquivalent annotation would be contained in the real element
		return null;
	} 
}