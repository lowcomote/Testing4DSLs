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
import static extension org.imt.k3tdl.k3dsa.GateInstanceAspect.*
import org.eclipse.core.runtime.CoreException
import org.eclipse.gemoc.executionframework.engine.commons.EngineContextException
import org.etsi.mts.tdl.DataInstanceUse

@Aspect (className = GateType)
class GateTypeAspect{
	@Step
	def void defineGateType(){
		
	}
}
@Aspect (className = GateInstance)
class GateInstanceAspect{
	public List<DataUse> testerIncomingData = new ArrayList<DataUse>
	public List<DataUse> sutIncomingData = new ArrayList<DataUse>
	public String MUTPath = ""
	public String DSLPath = ""
	CustomLauncher launcher = new CustomLauncher();
	//set the launcher attributes based on the gate type 
	def void configureLauncher(String MUTPath, String DSLPath){
		_self.MUTPath = MUTPath;
		_self.DSLPath = DSLPath;
		if (_self.name.equals('genericMUTGate')){
			//call the launcher to configure the GEMOC ALE engine
			try{
				_self.launcher.setALEConfiguration(_self.MUTPath, _self.DSLPath);
			}catch(CoreException ce){
				System.out.println(ce.message);
			}catch(EngineContextException ece){
				System.out.println(ece.message);
			}
		}else if (_self.name.equals('dslSpecificMUTGate')){
			//call the launcher to configure the event manager
			_self.launcher.setEventManagerConfiguration(_self.MUTPath, _self.DSLPath);
		}else if (_self.name.equals('oclMUTGate')){
			//call the launcher to configure the OCL Interpreter
			_self.launcher.setOCLInterpreterConfiguration(_self.MUTPath, _self.DSLPath);
		}
	}
	@Step
	def void tester_receive(DataUse argument){
		_self.testerIncomingData.add(argument)
	}
	@Step
	def void sut_receive(DataUse argument){
		_self.sutIncomingData.add(argument)
		if (argument instanceof DataInstanceUse){
			if ((argument as DataInstanceUse).dataInstance.name == 'runMUT'){
				_self.launcher.executeCommand(CustomLauncher.GENERIC_COMMAND);
			}else if ((argument as DataInstanceUse).dataInstance.name == 'setModelState'){
				//TODO: Set the model state
			}else if ((argument as DataInstanceUse).dataInstance.name == 'getModelState'){
				//TODO: Get the model state
			}
			//if the message is an OCL query
			else if ((argument as DataInstanceUse).dataInstance.dataType.name == 'OCL'){
				_self.launcher.executeCommand(CustomLauncher.OCL_COMMAND);
			}//otherwise the message is an event conforming to the behavioral interface of the DSL
			else{
				_self.launcher.executeCommand(CustomLauncher.DSL_SPECIFIC_COMMAND);
			}
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
	def void setMUTpath(GateInstance gate, String MUTPath, String DSLPath){
		gate.configureLauncher(MUTPath, DSLPath);
	}
}