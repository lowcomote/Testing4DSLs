package org.imt.k3tdl.k3dsa

import fr.inria.diverse.k3.al.annotationprocessor.Aspect
import fr.inria.diverse.k3.al.annotationprocessor.InitializeModel
import fr.inria.diverse.k3.al.annotationprocessor.Main
import fr.inria.diverse.k3.al.annotationprocessor.Step
import fr.inria.diverse.k3.al.annotationprocessor.OverrideAspectMethod
import org.etsi.mts.tdl.Package
import org.etsi.mts.tdl.TestDescription
import static extension org.imt.k3tdl.k3dsa.TestDescriptionAspect.*
import static extension org.imt.k3tdl.k3dsa.BehaviourDescriptionAspect.*
import static extension org.imt.k3tdl.k3dsa.TestConfigurationAspect.*
import java.util.List
import java.util.ArrayList
import org.etsi.mts.tdl.TestConfiguration

@Aspect(className = Package)
class PackageAspect {
	
	public List<TestDescription> testcases = new ArrayList<TestDescription>;
	public TestDescription enabledTestCase;
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
			println("Stopped due "+nt.message)	
		}
	}
}
@Aspect (className = TestDescription)
class TestDescriptionAspect{
	@Step
	def void executeTestCase(){
		println("Start test case execution: " + _self.name)
		_self.testConfiguration.activateConfiguration()
		_self.behaviourDescription.callBehavior()
	}
}
@Aspect (className = TestConfiguration)
class TestConfigurationAspect{
	public TestConfiguration enabledConfiguration
	@Step
	def void activateConfiguration(){
		_self.enabledConfiguration = _self
	}
}
class TDLRuntimeException extends Exception {
	new(String message) {
		super(message)
	}					
}