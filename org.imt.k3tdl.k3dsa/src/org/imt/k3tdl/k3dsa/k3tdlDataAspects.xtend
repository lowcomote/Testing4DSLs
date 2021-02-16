package org.imt.k3tdl.k3dsa

import fr.inria.diverse.k3.al.annotationprocessor.Aspect
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
import org.etsi.mts.tdl.DataUse
import org.etsi.mts.tdl.MemberReference
import org.etsi.mts.tdl.Message
import org.etsi.mts.tdl.ParameterBinding
import org.etsi.mts.tdl.StaticDataUse
import org.etsi.mts.tdl.SimpleDataInstance
import org.etsi.mts.tdl.StructuredDataInstance
import fr.inria.diverse.k3.al.annotationprocessor.OverrideAspectMethod
import org.etsi.mts.tdl.Parameter

import static extension org.imt.k3tdl.k3dsa.TestConfigurationAspect.*
import static extension org.imt.k3tdl.k3dsa.DataTypeAspect.*
import static extension org.imt.k3tdl.k3dsa.StructuredDataInstanceAspect.*
import static extension org.imt.k3tdl.k3dsa.ParameterAspect.*

@Aspect (className = DataType)
class DataTypeAspect{
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
	def EObject getMatchedMUTElement(EObject rootElement){
		
	}
}
@Aspect (className = SimpleDataInstance)
class SimpleDataInstanceAspect extends DataInstanceAspect{
	@OverrideAspectMethod
	def EObject getMatchedMUTElement(EObject rootElement){
		println("The " + _self.name + " element cannot be found since it has no identifier")
		println("Please specify the values of its static features")
		return null;
	}
}
@Aspect (className = StructuredDataInstance)
class StructuredDataInstanceAspect extends DataInstanceAspect{
	@OverrideAspectMethod
	def EObject getMatchedMUTElement(EObject rootElement){
		//find matched elements based on the memberAssignments of dataInstance
		for (i : 0 ..<_self.memberAssignment.size){
			if (!_self.memberAssignment.get(i).member.parameterMatched(rootElement)){
				return null;
			}
		}
		//all the members are matched with the attributes of the rootElement
		return rootElement;
	}
}
@Aspect (className = DataInstanceUse)
class DataInstanceUseAspect{
	def EObject getMatchedMUTElement(Resource MUTResource){
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
			rootElement = dataIns.getMatchedMUTElement(rootElement)
			if (rootElement == null){
				return null;
			}
		}
		//find matched elements based on the parameter bindings of dataInstanceUse
		for (i : 0 ..<_self.argument.size){
			if (!_self.argument.get(i).parameter.parameterMatched(rootElement)){
				return null;
			}
		}
		//all the parameters are matched with the attributes of the rootElement
		return rootElement;
	}
	def void setMatchedMUTElement(Resource MUTResource){
		//TODO: check if the dataInstanceUse is present in MUT
		//find static parameters
		//set dynamic parameters
	}
}
@Aspect (className = Parameter)
class ParameterAspect{
	def boolean parameterMatched(EObject rootElement){
		//TODO: the parameter with exactEquivalent annotation should be equal to the real element
		//TODO: the parameter with partialEquivalent annotation would be contained in the real element
		return false;
	} 
}