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
import static extension org.imt.k3tdl.k3dsa.TestDescriptionAspect.*
import org.etsi.mts.tdl.DataInstanceUse
import org.etsi.mts.tdl.LiteralValueUse
import org.imt.launchConfiguration.impl.LauncherFactory
import org.etsi.mts.tdl.ParameterBinding

@Aspect(className=GateType)
class GateTypeAspect {
}

@Aspect(className=GateInstance)
class GateInstanceAspect {
	private String receivedOutput = null
	private String expectedOutput = null
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
		if (argument instanceof LiteralValueUse){
			_self.expectedOutput = (argument as LiteralValueUse).value
			_self.expectedOutput = _self.expectedOutput.substring(1, _self.expectedOutput.length - 1) // remove the quotation marks
			print("Start assertion:")
			// TODO: Have to be completed to support different kind of arguments (not just a string)
			if (_self.receivedOutput != null && _self.receivedOutput.toString.equals(_self.expectedOutput.toString)) {
				println("Test case PASSED")
			} else if (_self.receivedOutput == null) {
				println("Test case FIALED: No response received from MUT")
			} else {
				println("Test case FAILED: The expected response is not received from MUT")
			}
		}
		// TODO: Have to be completed to support different kind of arguments (not just LiteralValueUse)
		println("Expected output: " + _self.expectedOutput);
		println("Received output: " + _self.receivedOutput);
		println();
	}

	@Step
	def void sendArgument2sut(DataUse argument) {
		println("The MUT component received data")
		if (argument instanceof DataInstanceUse) {
			if ((argument as DataInstanceUse).dataInstance.name == 'runMUT') {
				println("Sending the data to the Model Execution Engine")
				_self.gateLauncher.executeGenericCommand();
			} else if ((argument as DataInstanceUse).dataInstance.name == 'newState') {
				//the tester sends a new model state to the MUT
				_self.setModelState(argument as DataInstanceUse);
			} else if ((argument as DataInstanceUse).dataInstance.name == 'getModelState') {
				// TODO: Get the model state
			} // if the message is an OCL query
			else if ((argument as DataInstanceUse).dataInstance.dataType.name == 'OCL') {
				println("Sending the data to the OCL engine")
				// extracting the query from the argument and sending for validation
				var query = argument.argument.get(0).dataUse as LiteralValueUse;
				_self.receivedOutput = _self.gateLauncher.executeOCLCommand(query.value).toString;
			} // otherwise the message is an event conforming to the behavioral interface of the DSL
			else {
				println("Sending the data to the Event Manager")
				// TODO: Sending the related argument
				_self.gateLauncher.executeDSLSpecificCommand("");
			}
			println("Sending the data done!")
		}
	}
	def void setModelState(DataInstanceUse newState){
		for (ParameterBinding paramBinding: newState.argument){
			//The data type of the parameters of the newState are data types defined in the ecore file of the DSL
			var ecoreTypeName = "";
			if (paramBinding.parameter.dataType.name.startsWith('_')){
				ecoreTypeName = paramBinding.parameter.dataType.name.substring(1)
			}else{
				ecoreTypeName = paramBinding.parameter.dataType.name
			}
			//TODO: finding an element in the MUT that its type name is equals to ecoreTypeName
		}
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
