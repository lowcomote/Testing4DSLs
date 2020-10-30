package org.imt.k3tdl.k3dsa

import fr.inria.diverse.k3.al.annotationprocessor.Aspect
import fr.inria.diverse.k3.al.annotationprocessor.Step
import org.etsi.mts.tdl.TestConfiguration
import org.etsi.mts.tdl.ComponentType
import org.etsi.mts.tdl.GateType
import org.etsi.mts.tdl.GateReference

@Aspect (className = TestConfiguration)
class TestConfigurationAspect{
	@Step
	def void activateConfiguration(){
		
	}
}
@Aspect (className = ComponentType)
class ComponentTypeAspect{
	@Step
	def void activateComponent(){
		
	}
}
@Aspect (className = GateType)
class GateTypeAspect{
	@Step
	def void activateGate(){
		
	}
}
@Aspect (className = GateReference)
class GateReferenceAspect{
	@Step
	def void referenceToGate(){
		
	}
}