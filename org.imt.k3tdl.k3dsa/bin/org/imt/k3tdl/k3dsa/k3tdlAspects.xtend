package org.imt.k3tdl.k3dsa

import fr.inria.diverse.k3.al.annotationprocessor.Aspect
import fr.inria.diverse.k3.al.annotationprocessor.InitializeModel
import fr.inria.diverse.k3.al.annotationprocessor.Main
import fr.inria.diverse.k3.al.annotationprocessor.Step
import org.etsi.mts.tdl.Package
import org.etsi.mts.tdl.TestDescription
import org.etsi.mts.tdl.TestConfiguration
import org.etsi.mts.tdl.ComponentType
import org.etsi.mts.tdl.GateType
import org.etsi.mts.tdl.GateReference
import org.etsi.mts.tdl.Interaction
import org.etsi.mts.tdl.DataUse
import static extension org.imt.k3tdl.k3dsa.TestDescriptionAspect.*
import org.etsi.mts.tdl.Assertion
import java.util.List
import java.util.ArrayList

@Aspect(className = Package)
class PackageAspect {
	
	public List<TestDescription> testcases = new ArrayList<TestDescription>;
	public TestDescription enabledTestCase
	public Interaction currentInteraction
	public String verdictValue
	
	@Step
	@InitializeModel
	def void initializeModel(){
		for (Object o : _self.packagedElement.filter[p | p instanceof TestDescription]){
			_self.testcases.add(o as TestDescription)
		}
		if (_self.testcases.size == 0){
			println("There is no test case in the package " + _self.name + "to be executed")
		}
	}
	@Step
	@Main
	def void main(){
		try {
    		for (TestDescription tc:_self.testcases) {
    			_self.enabledTestCase = tc;
    			_self.enabledTestCase.executeTestCase()
    		}    		
		} catch (TDLRuntimeException nt){
			println("Stopped due to "+nt.message)
		}
	}
}
@Aspect (className = TestDescription)
class TestDescriptionAspect{
	@Step
	def void executeTestCase(){
		
	}
}
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
@Aspect (className = Interaction)
class InteractionAspect{
	@Step
	def void activateInteraction(){
		
	}
}
@Aspect (className = Assertion)
class AssertionAspect{
	@Step
	def void assertVerdict(){
		
	}
}
@Aspect (className = DataUse)
class DataUseAspect{
	@Step
	def void setData(){
		
	}
}
class TDLRuntimeException extends Exception {
	new(String message) {
		super(message)
	}					
}