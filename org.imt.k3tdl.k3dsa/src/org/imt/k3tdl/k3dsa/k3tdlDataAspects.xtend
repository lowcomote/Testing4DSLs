package org.imt.k3tdl.k3dsa

import fr.inria.diverse.k3.al.annotationprocessor.Aspect
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.gemoc.dsl.Dsl
import org.etsi.mts.tdl.DataElementMapping
import org.etsi.mts.tdl.DataInstanceUse
import org.etsi.mts.tdl.DataResourceMapping
import org.etsi.mts.tdl.DataType
import org.etsi.mts.tdl.DataUse
import org.etsi.mts.tdl.MappableDataElement
import org.etsi.mts.tdl.MemberReference
import org.etsi.mts.tdl.Message
import org.etsi.mts.tdl.ParameterBinding
import org.etsi.mts.tdl.ParameterMapping
import org.etsi.mts.tdl.StaticDataUse

import static extension org.imt.k3tdl.k3dsa.TestConfigurationAspect.*
import static extension org.imt.k3tdl.k3dsa.DataTypeAspect.*

@Aspect (className = DataResourceMapping)
class DataResourceMappingAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = MappableDataElement)
class MappableDataElementAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = DataElementMapping)
class DataElementMappingAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = ParameterMapping)
class ParameterMappingAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = DataType)
class DataTypeAspect extends MappableDataElementAspect{
	def boolean isConcreteEcoreType(String DSLPath) {
		var tdlTypeName = _self.name
		if (_self.name.startsWith("_")){
			tdlTypeName = _self.name.substring(1)
		}
		var dslRes = (new ResourceSetImpl()).getResource(URI.createURI(DSLPath), true);
		var dsl = dslRes.getContents().get(0) as Dsl;
		if (dsl.getEntry("ecore") != null) {
			var metamodelPath = dsl.getEntry("ecore").getValue().replaceFirst("resource", "plugin");
			var metamodelRes = (new ResourceSetImpl()).getResource(URI.createURI(metamodelPath), true);
			var metamodelRootElement = metamodelRes.getContents().get(0) as EPackage;
			for (EClassifier classifier: metamodelRootElement.EClassifiers){
				if (classifier.name.equals(tdlTypeName) && !classifier.eClass.abstract){
					return true;
				}
			}
		}
		return false;
	}
}

@Aspect (className = DataUse)
class DataUseAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = ParameterBinding)
class ParameterBindingAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = MemberReference)
class MemberReferenceAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = StaticDataUse)
class StaticDataUseAspect extends DataUseAspect{
	def void mapDataResource(){
		
	}
}
@Aspect (className = DataInstanceUse)
class DataInstanceUseAspect extends StaticDataUseAspect{
	
	def EObject getMatchedMUTElement(Resource MUTResource){
		val String DSLPath = (_self.eContainer as Message).parentTestDescription.testConfiguration.DSLPath
		if (!_self.dataInstance.dataType.isConcreteEcoreType(DSLPath)){
			return null;
		}
		//TODO: the parameter with exactEquivalent annotation should be equal to the real element
		//TODO: the parameter with partialEquivalent annotation would be contained in the real element
		//TODO: check if the dataInstanceUse is present in MUT
		//find all the parameters
		return null;
	}
	def void setMatchedMUTElement(Resource MUTResource){
		//TODO: check if the dataInstanceUse is present in MUT
		//find static parameters
		//set dynamic parameters
	}
}