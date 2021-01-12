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
import java.util.List
import java.util.ArrayList
import org.imt.ale.customLaunchConfig.CustomLauncher
import static extension org.imt.k3tdl.k3dsa.TestDescriptionAspect.*
import org.etsi.mts.tdl.DataInstanceUse
import org.etsi.mts.tdl.LiteralValueUse
import java.util.LinkedHashSet

@Aspect (className = GateType)
class GateTypeAspect{
	@Step
	def void defineGateType(){
		
	}
}
@Aspect (className = GateInstance)
class GateInstanceAspect{
	private String actualOutput = null
	private String expectedOutput= null
	private CustomLauncher gateLauncher
	
	private LinkedHashSet oclResult = new LinkedHashSet
	@Step
	//setting up the related launcher based on the gate type 
	def void configureLauncher(CustomLauncher launcher){
		_self.gateLauncher = launcher;
		if (_self.name.equals('genericMUTGate')){
			_self.gateLauncher.setUp(CustomLauncher.GENERIC);
		}else if (_self.name.equals('dslSpecificMUTGate')){
			_self.gateLauncher.setUp(CustomLauncher.DSL_SPECIFIC);
		}else if (_self.name.equals('oclMUTGate')){
			_self.gateLauncher.setUp(CustomLauncher.OCL);
		}
		println("Configuration finished successfully")
	}
	
	@Step
	def void assertArgument(DataUse argument){
		_self.expectedOutput = (argument as LiteralValueUse).value
		_self.expectedOutput = _self.expectedOutput.substring(1, _self.expectedOutput.length-1)//remove the quotation marks
		println("Start assertion:")
		//TODO: Have to be completed to support different kind of arguments (not just a string)
		if (_self.actualOutput != null && _self.actualOutput.toString.equals(_self.expectedOutput.toString)){
			println("Test case PASSED")
		}else if(_self.actualOutput == null){
			println("Test case FIALED: No response received from MUT")
		}else{
			println("Test case FAILED: The expected response is not received from MUT")
		}
	}
	@Step
	def void sendArgument2sut(DataUse argument){
		println("The MUT component received data")
		if (argument instanceof DataInstanceUse){
			if ((argument as DataInstanceUse).dataInstance.name == 'runMUT'){
				println("Sending the argument to the ALE engine")
				_self.gateLauncher.executeGenericCommand();
			}else if ((argument as DataInstanceUse).dataInstance.name == 'setModelState'){
				//TODO: Set the model state
			}else if ((argument as DataInstanceUse).dataInstance.name == 'getModelState'){
				//TODO: Get the model state
			}
			//if the message is an OCL query
			else if ((argument as DataInstanceUse).dataInstance.dataType.name == 'OCL'){
				println("Sending the argument to the OCL engine")
				//extracting the query from the argument and sending for validation
				var query = argument.argument.get(0).dataUse as LiteralValueUse;
				println("the ocl query: " + query.value);
				_self.actualOutput = _self.gateLauncher.executeOCLCommand(query.value).toString;
				println("the ocl query validation result: " + _self.actualOutput);
			}//otherwise the message is an event conforming to the behavioral interface of the DSL
			else{
				println("Sending the argument to the Event Manager")
				//TODO: Sending the related argument
				_self.gateLauncher.executeDSLSpecificCommand("");
			}
			println("Sending the argument done!")
		}
	}
}
@Aspect (className = ComponentType)
class ComponentTypeAspect{
	@Step
	def void defineComponentType(){
		
	}
}
@Aspect (className = ComponentInstance)
class ComponentInstanceAspect{

}
@Aspect (className = GateReference)
class GateReferenceAspect{
	@Step
	def void referenceToGate(){
		
	}
}
@Aspect (className = Connection)
class ConnectionAspect{
}