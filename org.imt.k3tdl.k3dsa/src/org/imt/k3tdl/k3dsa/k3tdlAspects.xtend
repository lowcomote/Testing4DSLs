package org.imt.k3tdl.k3dsa

import fr.inria.diverse.k3.al.annotationprocessor.Aspect

import fr.inria.diverse.k3.al.annotationprocessor.InitializeModel
import fr.inria.diverse.k3.al.annotationprocessor.Main
import fr.inria.diverse.k3.al.annotationprocessor.Step
import org.etsi.mts.tdl.Package
import org.etsi.mts.tdl.TestDescription
import org.etsi.mts.tdl.TestConfiguration
import org.etsi.mts.tdl.Annotation
import static extension org.imt.k3tdl.k3dsa.TestDescriptionAspect.*
import static extension org.imt.k3tdl.k3dsa.BehaviourDescriptionAspect.*
import static extension org.imt.k3tdl.k3dsa.TestConfigurationAspect.*
import java.util.List
import java.util.ArrayList
import org.eclipse.emf.common.util.EList
import org.imt.ale.customLaunchConfig.CustomLauncher

@Aspect(className = Package)
class PackageAspect {
	
	public List<TestDescription> testcases = new ArrayList<TestDescription>
	public TestDescription enabledTestCase
	public TestConfiguration enabledConfiguration
	public String verdictValue
	
	@Step
	@InitializeModel
	def void initializeModel(EList<String> args){
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
    			_self.enabledConfiguration = tc.testConfiguration;
    			_self.enabledTestCase.executeTestCase()
    			println()
    		}    		
		} catch (TDLRuntimeException nt){
			println("Stopped due "+nt.message)	
		}
	}
}
@Aspect (className = TestDescription)
class TestDescriptionAspect{
	public CustomLauncher launcher = new CustomLauncher();
	@Step
	def void executeTestCase(){
		println("Start test case execution: " + _self.name)
		_self.testConfiguration.activateConfiguration()
		_self.launcher.MUTPath = _self.testConfiguration.MUTPath
		_self.launcher.DSLPath = _self.testConfiguration.DSLPath
		_self.behaviourDescription.callBehavior()
	}
}
@Aspect (className = TestConfiguration)
class TestConfigurationAspect{
	public String MUTPath;
	public String DSLPath;
	@Step
	def void activateConfiguration(){
		//finding the address of MUT From the annotations of the SUT component (the component with role==0)
		for (Annotation a:_self.componentInstance.filter[ci | ci.role.toString == "SUT"].get(0).annotation){
			if (a.key.name == 'MUTPath'){
				_self.MUTPath = a.value.substring(1, a.value.length-1)
			}else if (a.key.name == 'DSLPath'){
				_self.DSLPath = a.value.substring(1, a.value.length-1)
			}
		}
	}
}
class TDLRuntimeException extends Exception {
	new(String message) {
		super(message)
	}					
}