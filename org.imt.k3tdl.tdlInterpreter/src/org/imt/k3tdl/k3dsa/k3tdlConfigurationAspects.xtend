package org.imt.k3tdl.k3dsa

import fr.inria.diverse.k3.al.annotationprocessor.Aspect



import fr.inria.diverse.k3.al.annotationprocessor.Step
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.etsi.mts.tdl.DataInstanceUse
import org.etsi.mts.tdl.DataUse
import org.etsi.mts.tdl.GateInstance
import org.etsi.mts.tdl.GateType
import org.etsi.mts.tdl.LiteralValueUse
import org.etsi.mts.tdl.StaticDataUse
import org.imt.tdl.configuration.impl.EngineFactory

import static extension org.imt.k3tdl.k3dsa.DataInstanceAspect.*
import static extension org.imt.k3tdl.k3dsa.DataInstanceUseAspect.*
import static extension org.imt.k3tdl.k3dsa.DataTypeAspect.*
import java.util.ArrayList
import java.util.Map
import java.util.HashMap

@Aspect(className=GateType)
class GateTypeAspect {
}

@Aspect(className=GateInstance)
class GateInstanceAspect {
	public String MUTPath = ""
	public String DSLPath = ""
	
	private Object receivedOutput = null
	private Object expectedOutput = null
	
	private EngineFactory gateLauncher

	def void setLauncher(EngineFactory launcher) {
		_self.gateLauncher = launcher;
	}
	@Step
	def boolean assertArgument(DataUse argument) {
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
			if (_self.receivedOutput != null) {
				if (_self.gateLauncher.OCLResultAsString.size==1){
					val String result = _self.gateLauncher.OCLResultAsString.get(0)
					_self.receivedOutput = result.subSequence(1, result.length-1)
				}
				if (_self.receivedOutput.toString.equals(_self.expectedOutput.toString)){
					println("Assertion PASSED")
					return true
				}else{
					println("Assertion FIALED: The expected response is not received from MUT")
					return false
				}
			} else if (_self.receivedOutput == null) {
				println("Assertion FIALED: No response received from MUT")
				return false
			} else {
				println("Assertion FAILED: The expected response is not received from MUT")
				return false
			}
		}
		//if the argument is an element/a list of elements
		else if (argument instanceof DataInstanceUse){
			val arg = argument as DataInstanceUse
			var Resource MUTResource = null;
			if (_self.receivedOutput instanceof Resource){
				MUTResource = _self.receivedOutput as Resource//the MUTResource is the received output
			}
			if (_self.name.equals('oclMUTGate')){
				MUTResource = _self.gateLauncher.MUTResource//the MUT objects are the received output
			}
			else if (arg.dataInstance.dataType.isExposedEvent(_self.DSLPath)){//an execution rule is the received output 
				//TODO: what should be set here??
			}
			var boolean assertionFailed = false
			var ArrayList<EObject> matchedMUTElements = new ArrayList<EObject>();
			var ArrayList<EObject> notMatchedElements = new ArrayList<EObject>();
			if (arg.item != null && arg.item.size > 0){//there is a list of objects in the expected output
				for (i : 0 ..<arg.item.size){
					val EObject matchedObject = (arg.item.get(i) as DataInstanceUse).
						getMatchedMUTElement(MUTResource as Resource, true, _self.DSLPath)		
					if (matchedObject == null){
						notMatchedElements.add(arg.item.get(i))
						assertionFailed = true
					}else{
						matchedMUTElements.add(matchedObject)
					}
				}
			}else{//there is only one object in the expected output
				val EObject matchedObject = (arg as DataInstanceUse).
					getMatchedMUTElement(MUTResource as Resource, true, _self.DSLPath)
				if (matchedObject == null){
					notMatchedElements.add(arg)
					assertionFailed = true
				}else{
					matchedMUTElements.add(matchedObject)	
				}
			}
			if (assertionFailed){
				println("Assertion FAILED: The expected response is not received from MUT")
				println("The following elements are not matched: " + (notMatchedElements.get(0) as DataInstanceUse).dataInstance.name)
				return false
			}else if(_self.name.equals('oclMUTGate')){
				val Object[] receivedObjects = _self.gateLauncher.OCLResultAsObject
				if (receivedObjects.elementsEqual(matchedMUTElements)){
					println("Assertion PASSED")
					return true
				}else{
					println("Assertion FAILED: The expected response is not received from MUT")
					println("Received result: " + _self.gateLauncher.OCLResultAsString)
					return false
				}
			}else{
				println("Assertion PASSED")
				return true
			}
		}
	}

	@Step
	def void sendArgument2sut(DataUse argument) {
		if (argument instanceof DataInstanceUse) {
			var arg = (argument as DataInstanceUse)
			if (arg.dataInstance.name == 'runModel') {
				println("--Start MUT Execution:")
				_self.gateLauncher.executeGenericCommand();
				println("--MUT executed successfully")
			}else if (arg.dataInstance.name == 'resetModel') {
				_self.gateLauncher.MUTResource = 
					(new ResourceSetImpl()).getResource(URI.createURI(_self.MUTPath), true);
			}else if (arg.dataInstance.name == 'getModelState') {
				_self.receivedOutput = _self.gateLauncher.MUTResource
			}else if (arg.dataInstance.dataType.isConcreteEcoreType(_self.DSLPath)){//request for setting the model state
				_self.setModelState(arg);
			}else if (arg.dataInstance.dataType.name == 'OCL') {
				// extracting the query from the argument and sending for validation
				var query = argument.argument.get(0).dataUse as LiteralValueUse;
				_self.gateLauncher.executeOCLCommand(query.value);				
			}else if (arg.dataInstance.dataType.isAcceptedEvent(_self.DSLPath)){
				//the message is an event conforming to the behavioral interface of the DSL
				_self.gateLauncher.executeDSLSpecificCommand(arg.dataInstance.name, _self.getEventParameters(arg));
			}
		}
	}
	@Step
	def boolean setModelState(DataInstanceUse arg){
		//get the current MUTResource
		var MUTResource = _self.gateLauncher.MUTResource;
		var boolean status = false;
		if (arg.item != null && arg.item.size > 0){
			for (i : 0 ..<arg.item.size){
				status = (arg.item.get(i) as DataInstanceUse).setMatchedMUTElement(MUTResource, _self.DSLPath)
				if (!status){
					println("the specified model state doesn't match the model under test")
					return false;
				}
			}
		}else{
			status = arg.setMatchedMUTElement(MUTResource, _self.DSLPath);
			if (!status){
				println("the specified model state doesn't match the model under test")
				return false
			}
		}
		return status;
	}
	//retrieve a map of the parameter name and its corresponding model element
	def Map<String, Object> getEventParameters(DataInstanceUse event){
		var Map<String, Object> parameters = new HashMap;
		for (i : 0 ..<event.argument.size){//the parameterBindings of the event
			val DataUse parameter = event.argument.get(i).dataUse
			if (parameter instanceof DataInstanceUse){
				val DataInstanceUse param = parameter
				//put the name of the parameter along with its matched object in the MUTResource
				 parameters.put(param.dataInstance.name, 
				 	param.getMatchedMUTElement(_self.gateLauncher.MUTResource, true, _self.DSLPath))
			}			
		}
		return parameters
	}
}