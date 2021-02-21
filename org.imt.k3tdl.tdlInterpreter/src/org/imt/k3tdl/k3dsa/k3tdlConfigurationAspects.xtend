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

	@Step
	def void setLauncher(EngineFactory launcher) {
		_self.gateLauncher = launcher;
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
			var Resource MUTResource = null;
			if (_self.receivedOutput instanceof Resource){
				MUTResource = _self.receivedOutput as Resource//the MUTResource is the received output
			}else if (_self.name.equals('oclMUTGate')){
				MUTResource = _self.gateLauncher.MUTResource//the MUT objects are the received output
			}
			var boolean assertionFailed = false
			var ArrayList<EObject> matchedMUTElements = new ArrayList<EObject>();
			var ArrayList<EObject> notMatchedElements = new ArrayList<EObject>();
			if (arg.item != null && arg.item.size > 0){//there is a list of objects in the expected output
				for (i : 0 ..<arg.item.size){
					val EObject matchedObject = (arg.item.get(i) as DataInstanceUse).
						getMatchedMUTElement(MUTResource as Resource, true)		
					if (matchedObject == null){
						notMatchedElements.add(arg.item.get(i))
						assertionFailed = true
					}else{
						matchedMUTElements.add(matchedObject)
					}
				}
			}else{//there is only one object in the expected output
				val EObject matchedObject = (arg as DataInstanceUse).
					getMatchedMUTElement(MUTResource as Resource, true)
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
			}else if(_self.name.equals('oclMUTGate')){
				val Object[] receivedObjects = _self.gateLauncher.OCLResultAsObject
				if (receivedObjects.elementsEqual(matchedMUTElements)){
					println("Assertion PASSED")
				}else{
					println("Assertion FAILED: The expected response is not received from MUT")
					println("Received result: " + _self.gateLauncher.OCLResultAsString)
				}
			}else{
				println("Assertion PASSED")
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
			}else if (arg.dataInstance.dataType.isConcreteEcoreType(_self.DSLPath)){
				_self.setModelState(arg);
			}else if (arg.dataInstance.dataType.name == 'OCL') {
				// extracting the query from the argument and sending for validation
				var query = argument.argument.get(0).dataUse as LiteralValueUse;
				_self.gateLauncher.executeOCLCommand(query.value);				
			} // otherwise the message is an event conforming to the behavioral interface of the DSL
			else {
				// TODO: Sending the related argument
				_self.gateLauncher.executeDSLSpecificCommand("");
			}
		}
	}
	def boolean setModelState(DataInstanceUse arg){
		//get the current MUTResource
		//TODO: Get the in-memory MUTResource
		var MUTResource = _self.gateLauncher.MUTResource;
		var boolean status = false;
		if (arg.item != null && arg.item.size > 0){
			for (i : 0 ..<arg.item.size){
				status = (arg.item.get(i) as DataInstanceUse).setMatchedMUTElement(MUTResource)
				if (!status){
					println("the specified model state doesn't match the model under test")
					return false;
				}
			}
		}else{
			status = arg.setMatchedMUTElement(MUTResource);
			if (!status){
				println("the specified model state doesn't match the model under test")
				return false;
			}
		}
		return status;
	}
}