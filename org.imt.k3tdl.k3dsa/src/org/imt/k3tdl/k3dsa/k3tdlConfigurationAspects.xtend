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
import org.etsi.mts.tdl.ParameterBinding
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.resource.Resource
import org.etsi.mts.tdl.DataType

@Aspect(className=GateType)
class GateTypeAspect {
}

@Aspect(className=GateInstance)
class GateInstanceAspect {
	private String initialMUT_Path = ""
	
	private Object receivedOutput = null
	private Object expectedOutput = null
	
	private LauncherFactory gateLauncher
	
	def void setInitialMUTPath (String path){
		_self.initialMUT_Path = path
	}
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
		//when asserting ocl query validation result:
		//if the expected result is String, use the labeled result of validation 
		//else use the object retrieved by ocl interpreter
		if (_self.name.equals('oclMUTGate')){
			if (argument instanceof LiteralValueUse){
				_self.receivedOutput = _self.gateLauncher.OCLLauncher.resultAsString
			}else{
				_self.receivedOutput = _self.gateLauncher.OCLLauncher.resultAsObject
			}
		}
		//if the argument is a string
		if (argument instanceof LiteralValueUse){
			var expected = (argument as LiteralValueUse).value
			expected = expected.substring(1, expected.length - 1) // remove the quotation marks
			_self.expectedOutput = expected;
			print("Start assertion:")
			if (_self.receivedOutput != null && _self.receivedOutput.toString.equals(_self.expectedOutput.toString)) {
				println("Test case PASSED")
			} else if (_self.receivedOutput == null) {
				println("Test case FIALED: No response received from MUT")
			} else {
				println("Test case FAILED: The expected response is not received from MUT")
			}
		}else if (argument instanceof DataInstanceUse){
			_self.expectedOutput = (argument as DataInstanceUse).transformToEMFObject();
			if (_self.receivedOutput instanceof DataInstanceUse){
				_self.receivedOutput = (_self.receivedOutput as DataInstanceUse).transformToEMFObject();
			}
			//TODO: compare expectedOutput with receivedOutput
		}
		println("Received output: " + _self.receivedOutput);
		println();
	}

	@Step
	def void sendArgument2sut(DataUse argument) {
		println("The MUT component received data")
		if (argument instanceof DataInstanceUse) {
			if ((argument as DataInstanceUse).dataInstance.name == 'runModel') {
				println("Request for running MUT")
				_self.gateLauncher.executeGenericCommand();
			} else if ((argument as DataInstanceUse).dataInstance.name == 'resetModel') {
				println("Request for resetting the model to its initial state")
				_self.gateLauncher.MUTResource = 
					(new ResourceSetImpl()).getResource(URI.createURI(_self.initialMUT_Path), true);
			}else if ((argument as DataInstanceUse).dataInstance.name == 'getModelState') {
				println("Request for getting the MUT")
				_self.receivedOutput = _self.gateLauncher.MUTResource
			}else if ((argument as DataInstanceUse).dataInstance.name == 'newState') {
				println("Request for setting MUT in a new State")
				_self.setModelState(argument as DataInstanceUse);
			}// if the message is an OCL query
			else if ((argument as DataInstanceUse).dataInstance.dataType.name == 'OCL') {
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
	def boolean isEcoreType(DataType dataType){
		return true;
	}
	def void setModelState(DataInstanceUse newState){
		//get the current MUTResource
		var newMUTResource = _self.gateLauncher.MUTResource;
		var EMFObjectForNewState = newState.transformToEMFObject()
		//TODO: change the required variables of MUTResource
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
