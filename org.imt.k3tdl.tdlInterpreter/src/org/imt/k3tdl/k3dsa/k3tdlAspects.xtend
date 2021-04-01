package org.imt.k3tdl.k3dsa

import fr.inria.diverse.k3.al.annotationprocessor.Aspect

import fr.inria.diverse.k3.al.annotationprocessor.InitializeModel
import fr.inria.diverse.k3.al.annotationprocessor.Main
import fr.inria.diverse.k3.al.annotationprocessor.Step
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.common.util.EList
import org.etsi.mts.tdl.Annotation
import org.etsi.mts.tdl.Package
import org.etsi.mts.tdl.TestConfiguration
import org.etsi.mts.tdl.TestDescription
import org.imt.tdl.configuration.impl.EngineFactory
import org.imt.tdl.testResult.TDLTestCaseResult
import org.imt.tdl.testResult.TDLTestPackageResult
import org.imt.tdl.testResult.ui.TDLTestResultsView

import static extension org.imt.k3tdl.k3dsa.BehaviourDescriptionAspect.*
import static extension org.imt.k3tdl.k3dsa.TestConfigurationAspect.*
import static extension org.imt.k3tdl.k3dsa.TestDescriptionAspect.*
import static extension org.imt.k3tdl.k3dsa.PackageAspect.*
import org.imt.tdl.testResult.TestResultUtil

@Aspect(className = Package)
class PackageAspect {
	
	public TDLTestPackageResult testPackageResults = new TDLTestPackageResult
	public List<TestDescription> testcases = new ArrayList<TestDescription>
	public TestDescription enabledTestCase
	public TestConfiguration enabledConfiguration
	
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
			_self.testPackageResults.testPackageName = _self.name
    		for (TestDescription tc:_self.testcases) {
    			_self.enabledTestCase = tc;
    			_self.enabledConfiguration = tc.testConfiguration;
    			val TDLTestCaseResult result = _self.enabledTestCase.executeTestCase()
    			_self.testPackageResults.addResult(result)
    			println()
    		}
    		TestResultUtil.instance.testPackageResult = _self.testPackageResults
    		//var TDLTestResultsView view = new TDLTestResultsView(_self.testPackageResults);   		
		} catch (TDLRuntimeException nt){
			println("Stopped due "+nt.message)	
		}
	}
}
@Aspect (className = TestDescription)
class TestDescriptionAspect{
	public EngineFactory launcher = new EngineFactory()
	public TDLTestCaseResult testCaseResult = new TDLTestCaseResult
	@Step
	def TDLTestCaseResult executeTestCase(){
		println("Start test case execution: " + _self.name)
		_self.testCaseResult.testCaseName = _self.name
		_self.testConfiguration.activateConfiguration(_self.launcher)
		_self.behaviourDescription.callBehavior()
		if (_self.testCaseResult.numOfFailures > 0) {
			println("Test case FAILED")
		}else{
			println("Test case PASSED")
		}
		return _self.testCaseResult
	}
	//this method is called from TDL runner
	@Step
	def TDLTestCaseResult executeTestCase(String MUTPath){
		_self.launcher.MUTPath = MUTPath
		_self.testConfiguration.activateConfiguration(_self.launcher, MUTPath)
		_self.behaviourDescription.callBehavior()
		return _self.testCaseResult
	}
}
@Aspect (className = TestConfiguration)
class TestConfigurationAspect{
	public String MUTPath;
	public String DSLPath;
	@Step
	def void activateConfiguration(EngineFactory launcher){
		//finding the address of MUT From the annotations of the SUT component (the component with role==0)
		for (Annotation a:_self.componentInstance.filter[ci | ci.role.toString == "SUT"].get(0).annotation){
			if (a.key.name == 'MUTPath'){
				_self.MUTPath = a.value.substring(1, a.value.length-1)
				launcher.MUTPath = _self.MUTPath
			}else if (a.key.name == 'DSLPath'){
				_self.DSLPath = a.value.substring(1, a.value.length-1)
				launcher.DSLPath = _self.DSLPath
			}
		}
		_self.setUpLauncher(launcher)
	}
	@Step
	def void activateConfiguration(EngineFactory launcher, String MUTPath){
		_self.MUTPath = MUTPath
		launcher.MUTPath = _self.MUTPath
		for (Annotation a:_self.componentInstance.filter[ci | ci.role.toString == "SUT"].get(0).annotation){
			if (a.key.name == 'DSLPath'){
				_self.DSLPath = a.value.substring(1, a.value.length-1)
				launcher.DSLPath = _self.DSLPath
			}
		}
		_self.setUpLauncher(launcher)
	}
	def void setUpLauncher (EngineFactory launcher){
		if (_self.connection.exists[c|c.endPoint.exists[g|g.gate.name.contains('generic')]]) {
			launcher.setUp(EngineFactory.GENERIC);
		}
		if (_self.connection.exists[c|c.endPoint.exists[g|g.gate.name.contains('dslSpecific')]]) {
			launcher.setUp(EngineFactory.DSL_SPECIFIC);
		}
		if (_self.connection.exists[c|c.endPoint.exists[g|g.gate.name.contains('ocl')]]){
			launcher.setUp(EngineFactory.OCL);
		}
	}
}
class TDLRuntimeException extends Exception {
	new(String message) {
		super(message)
	}					
}