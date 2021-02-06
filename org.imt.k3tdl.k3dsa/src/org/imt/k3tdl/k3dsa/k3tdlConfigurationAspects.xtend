package org.imt.k3tdl.k3dsa

import fr.inria.diverse.k3.al.annotationprocessor.Aspect


import fr.inria.diverse.k3.al.annotationprocessor.Step

import org.etsi.mts.tdl.ComponentType
import org.etsi.mts.tdl.GateType
import org.etsi.mts.tdl.GateReference
import org.etsi.mts.tdl.GateInstance
import org.etsi.mts.tdl.ComponentInstance
import org.etsi.mts.tdl.Connection
import org.etsi.mts.tdl.DataUse
import static extension org.imt.k3tdl.k3dsa.DataInstanceUseAspect.*
import org.etsi.mts.tdl.DataInstanceUse
import org.etsi.mts.tdl.LiteralValueUse
import org.imt.launchConfiguration.impl.LauncherFactory
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.etsi.mts.tdl.DataType
import org.eclipse.emf.ecore.EPackage
import org.eclipse.gemoc.dsl.Dsl
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.etsi.mts.tdl.StaticDataUse
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import java.util.ArrayList

@Aspect(className=GateType)
class GateTypeAspect {
}

@Aspect(className=GateInstance)
class GateInstanceAspect {
	public String MUTPath = ""
	public String DSLPath = ""
	
	private Object receivedOutput = null
	private Object expectedOutput = null
	
	private LauncherFactory gateLauncher

	@Step
	// setting up the related launcher based on the gate type 
	def void configureLauncher(LauncherFactory launcher) {
		_self.gateLauncher = launcher;
		if (_self.name.equals('genericMUTGate')) {
			_self.gateLauncher.setUp(LauncherFactory.GENERIC);
		} else if (_self.name.equals('dslSpecificMUTGate')) {
			_self.gateLauncher.setUp(LauncherFactory.DSL_SPECIFIC);
		} else if (_self.name.equals('oclMUTGate')) {
			_self.gateLauncher.setUp(LauncherFactory.OCL);
		}
	}

	@Step
	def void assertArgument(DataUse argument) {
		//if the argument is a string
		if (argument instanceof LiteralValueUse){			
			var expected = (argument as LiteralValueUse).value
			expected = expected.substring(1, expected.length - 1) // remove the quotation marks
			_self.expectedOutput = expected;
			//when asserting ocl query validation result:
			//if the expected result is String, use the labeled result of validation 
			if (_self.name.equals('oclMUTGate')){
				_self.receivedOutput = _self.gateLauncher.OCLResultAsString
			}
			print("Start assertion:")
			if (_self.receivedOutput != null && _self.receivedOutput.toString.equals(_self.expectedOutput.toString)) {
				println("Test case PASSED")
			} else if (_self.receivedOutput == null) {
				println("Test case FIALED: No response received from MUT")
			} else {
				println("Test case FAILED: The expected response is not received from MUT")
			}
		}
		//if the argument is an element/a list of elements
		else if (argument instanceof DataInstanceUse){
			val arg = argument as DataInstanceUse
			var boolean assertionFailed = false
			var EObject[] matchedMUTElements = null
			var StaticDataUse[] notMatchedElements = null
			if (arg.item != null){
				for (i : 0 ..<arg.item.size){//there is a list of objects in the expected output
					if (_self.receivedOutput instanceof Resource){//the MUTResource is the received output
						val EObject matchedObject = (arg.item.get(i) as DataInstanceUse).getMatchedMUTElement(_self.receivedOutput as Resource)		
						if (matchedObject == null){
							notMatchedElements.add(arg.item.get(i))
							assertionFailed = true
						}
					}else if (_self.name.equals('oclMUTGate')){//the MUT objects are the received output
						val EObject matchedObject = (arg.item.get(i) as DataInstanceUse).getMatchedMUTElement(_self.gateLauncher.MUTResource)		
						if (matchedObject == null){
							notMatchedElements.add(arg.item.get(i))
							assertionFailed = true
						}else{
							matchedMUTElements.add(matchedObject)
						}
					}
				}
			}else{//there is only one object in the expected output
				if (_self.receivedOutput instanceof Resource){//the MUTResource is the received output
					if ((arg as DataInstanceUse).getMatchedMUTElement(_self.receivedOutput as Resource) == null){
						notMatchedElements.add(arg)
						assertionFailed = true
					}else if (_self.name.equals('oclMUTGate')){//the MUT objects are the received output
						val EObject matchedObject = (arg as DataInstanceUse).getMatchedMUTElement(_self.gateLauncher.MUTResource)		
						if (matchedObject == null){
							notMatchedElements.add(arg)
							assertionFailed = true
						}else{
							matchedMUTElements.add(matchedObject)
						}	
					}
				}
			}
			if (assertionFailed){
				println("Test case FAILED: The expected response is not received from MUT")
				println("The following elements are not matched: " + notMatchedElements.toString)
			}else if(_self.name.equals('oclMUTGate')){
				val Object[] receivedObjects = _self.gateLauncher.OCLResultAsObject
				if (receivedObjects.elementsEqual(matchedMUTElements)){
					println("Test case PASSED")
				}else{
					println("Test case FAILED: The expected response is not received from MUT")
					println("Received result: " + _self.gateLauncher.OCLResultAsString)
				}
			}else{
				println("Test case PASSED")
			}
		}
		println();
	}

	@Step
	def void sendArgument2sut(DataUse argument) {
		println("The MUT component received data")
		if (argument instanceof DataInstanceUse) {
			var arg = (argument as DataInstanceUse)
			if (arg.item != null || _self.isEcoreType(arg.dataInstance.dataType)){
				println("Request for setting MUT in a new State")
				_self.setModelState(arg);
			}else if (arg.dataInstance.name == 'runModel') {
				println("Request for running MUT")
				_self.gateLauncher.executeGenericCommand();
			}else if (arg.dataInstance.name == 'resetModel') {
				println("Request for resetting the model to its initial state")
				_self.gateLauncher.MUTResource = 
					(new ResourceSetImpl()).getResource(URI.createURI(_self.MUTPath), true);
			}else if (arg.dataInstance.name == 'getModelState') {
				println("Request for getting the MUT")
				_self.receivedOutput = _self.gateLauncher.MUTResource
			}else if (arg.dataInstance.dataType.name == 'OCL') {
				println("Sending the data to the OCL engine")
				// extracting the query from the argument and sending for validation
				var query = argument.argument.get(0).dataUse as LiteralValueUse;
				_self.gateLauncher.executeOCLCommand(query.value);				
			} // otherwise the message is an event conforming to the behavioral interface of the DSL
			else {
				println("Sending the data to the Event Manager")
				// TODO: Sending the related argument
				_self.gateLauncher.executeDSLSpecificCommand("");
			}
			println("Sending the data done!")
		}
	}
	def boolean isEcoreType(DataType dataType) {
		var tdlTypeName = dataType.name
		if (dataType.name.startsWith("_")){
			tdlTypeName = dataType.name.substring(1)
		}
		var dslRes = (new ResourceSetImpl()).getResource(URI.createURI(_self.DSLPath), true);
		var dsl = dslRes.getContents().get(0) as Dsl;
		if (dsl.getEntry("ecore") != null) {
			var metamodelPath = dsl.getEntry("ecore").getValue().replaceFirst("resource", "plugin");
			var metamodelRes = (new ResourceSetImpl()).getResource(URI.createURI(metamodelPath), true);
			var metamodelRootElement = metamodelRes.getContents().get(0) as EPackage;
			for (EClassifier classifier: metamodelRootElement.EClassifiers){
				if (classifier.name.equals(tdlTypeName)){
					return true;
				}
			}
		}
		return false;
	}
	def void setModelState(DataInstanceUse arg){
		//get the current MUTResource
		var newMUTResource = _self.gateLauncher.MUTResource;
		if (arg.item != null){
			for (i : 0 ..<arg.item.size){
				(arg.item.get(i) as DataInstanceUse).setMatchedMUTElement(newMUTResource)
			}
		}else{
			arg.setMatchedMUTElement(newMUTResource);
		}
		_self.gateLauncher.MUTResource = newMUTResource;
	}
}

@Aspect(className=ComponentType)
class ComponentTypeAspect {
}

@Aspect(className=ComponentInstance)
class ComponentInstanceAspect {
}

@Aspect(className=GateReference)
class GateReferenceAspect {
}

@Aspect(className=Connection)
class ConnectionAspect {
}
