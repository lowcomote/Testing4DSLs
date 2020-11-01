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
	@Step
	def void tester_receive(DataUse argument){
		_self.testerIncomingData.add(argument)
	}
	@Step
	def void sut_receive(DataUse argument){
		_self.sutIncomingData.add(argument)
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
	@Step
	def void connectGates(){
		
	}
}