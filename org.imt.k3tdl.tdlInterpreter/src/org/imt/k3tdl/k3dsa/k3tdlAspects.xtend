package org.imt.k3tdl.k3dsa

import fr.inria.diverse.k3.al.annotationprocessor.Aspect
import fr.inria.diverse.k3.al.annotationprocessor.InitializeModel
import fr.inria.diverse.k3.al.annotationprocessor.Main
import fr.inria.diverse.k3.al.annotationprocessor.Step
import java.util.ArrayList
import java.util.Arrays
import java.util.List
import org.eclipse.core.runtime.IConfigurationElement
import org.eclipse.core.runtime.Platform
import org.eclipse.emf.common.util.EList
import org.etsi.mts.tdl.Annotation
import org.etsi.mts.tdl.Package
import org.etsi.mts.tdl.TestConfiguration
import org.etsi.mts.tdl.TestDescription
import org.imt.tdl.configuration.EngineFactory
import org.imt.tdl.coverage.TDLCoverageUtil
import org.imt.tdl.coverage.TDLTestCaseCoverage
import org.imt.tdl.coverage.TDLTestSuiteCoverage
import org.imt.tdl.testResult.TDLTestCaseResult

import static extension org.imt.k3tdl.k3dsa.BehaviourDescriptionAspect.*
import static extension org.imt.k3tdl.k3dsa.TestConfigurationAspect.*
import static extension org.imt.k3tdl.k3dsa.TestDescriptionAspect.*
import org.imt.tdl.testResult.TDLTestSuiteResult
import org.imt.tdl.testResult.TDLTestResultUtil

@Aspect(className = Package)
class PackageAspect {
	
	List<TestDescription> testcases = new ArrayList<TestDescription>
	TDLTestSuiteResult testSuiteResult = new TDLTestSuiteResult
	TDLTestSuiteCoverage testSuiteCoverage = new TDLTestSuiteCoverage
	
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
			_self.testSuiteResult.testSuite = _self
			_self.testSuiteCoverage.testSuite = _self
    		for (TestDescription testCase:_self.testcases) {
    			val TDLTestCaseResult testCaseResult = testCase.executeTestCase()
    			_self.testSuiteResult.addResult(testCaseResult)
    			//for coverage, only considering passed and failed test cases
    			if (testCaseResult.value != TDLTestResultUtil.INCONCLUSIVE){
    				_self.testSuiteCoverage.addTCCoverage(testCase.testCaseCoverage)
    			}
    			println()
    		}
    		
    		TDLTestResultUtil.instance.setTestSuiteResult = _self.testSuiteResult		
    		TDLCoverageUtil.instance.testSuiteCoverage = _self.testSuiteCoverage
    		TDLCoverageUtil.instance.DSLPath = _self.testcases.get(0).testConfiguration.DSLPath
    		TDLCoverageUtil.instance.runCoverageComputation
    		  		
		} catch (TDLRuntimeException nt){
			println("Stopped due "+nt.message)	
		}
		
		println("Test suite execution has been finished successfully.")
	}
}
@Aspect (className = TestDescription)
class TestDescriptionAspect{
	public EngineFactory launcher = new EngineFactory
	public TDLTestCaseResult testCaseResult = new TDLTestCaseResult
	public TDLTestCaseCoverage testCaseCoverage = new TDLTestCaseCoverage
	
	@Step
	def TDLTestCaseResult executeTestCase(){
		if (!_self.launcher.launcherIsTuned){
			_self.activateConfiguration()
		}
		return _self.runTestAndReturnResult
	}
	
	//this method is called from other codes (not GEMOC engine)
	def TDLTestCaseResult executeTestCase(String MUTPath){
		_self.activateConfiguration(MUTPath)
		return _self.runTestAndReturnResult
	}
	
	def void activateConfiguration(){
		_self.testConfiguration.activateConfiguration(_self.launcher)
	}
	
	def void activateConfiguration(String MUTPath){
		_self.launcher = new EngineFactory
		_self.testCaseResult = new TDLTestCaseResult
		_self.testCaseCoverage = new TDLTestCaseCoverage
		_self.testConfiguration.activateConfiguration(_self.launcher, MUTPath)
	}
	
	def TDLTestCaseResult runTestAndReturnResult(){
		println("Start test case execution: " + _self.name)
		_self.testCaseResult.testCase = _self
		_self.behaviourDescription.callBehavior()
		val modelExecutionResult = _self.testConfiguration.stopModelExecutionEngine(_self.launcher)
		if (modelExecutionResult !== null && modelExecutionResult.contains(TDLTestResultUtil.FAIL)){
			_self.testCaseResult.value = TDLTestResultUtil.FAIL
			_self.testCaseResult.description = modelExecutionResult.substring(modelExecutionResult.indexOf(":")+1)
		}
		println("Test case "+ _self.name + ": " + _self.testCaseResult.value)
		if (_self.testCaseResult.value != TDLTestResultUtil.INCONCLUSIVE){
			//save the model execution trace and the MUTResource related to this test case if its result is not INCONCLUSIVE
			_self.testCaseCoverage.testCase = _self
			_self.testCaseCoverage.trace = _self.launcher.executionTrace
	    	_self.testCaseCoverage.MUTResource = _self.launcher.MUTResource
		}
		return _self.testCaseResult
	}
}

@Aspect (className = TestConfiguration)
class TestConfigurationAspect{
	public String MUTPath;
	public String DSLPath;

	def void activateConfiguration(EngineFactory launcher){
		//finding the address of MUT From the annotations of the SUT component (the component with role==0)
		val sutComponent = _self.componentInstance.filter[ci | ci.role.toString.equals("SUT")].get(0)
		for (Annotation a:sutComponent.annotation){
			if (a.key.name.equals('MUTPath')){
				_self.MUTPath = a.value.substring(1, a.value.length-1)
				launcher.MUTPath = _self.MUTPath
			}else if (a.key.name.equals('DSLName')){
				val DSLName = a.value.substring(1, a.value.length-1)
				_self.DSLPath = _self.getDSLPath(DSLName)
				launcher.DSLPath = _self.DSLPath
			}
		}
		_self.setUpLauncher(launcher)
	}

	def void activateConfiguration(EngineFactory launcher, String MUTPath){
		_self.MUTPath = MUTPath
		launcher.MUTPath = _self.MUTPath
		for (Annotation a:_self.componentInstance.filter[ci | ci.role.toString == "SUT"].get(0).annotation){
			if (a.key.name == 'DSLName'){
				val DSLName = a.value.substring(1, a.value.length-1)
				_self.DSLPath = _self.getDSLPath(DSLName)
				launcher.DSLPath = _self.DSLPath
			}
		}
		_self.setUpLauncher(launcher)
	}
	
	def String getDSLPath (String DSLName){
		val IConfigurationElement language = Arrays
				.asList(Platform.getExtensionRegistry()
						.getConfigurationElementsFor("org.eclipse.gemoc.gemoc_language_workbench.xdsml"))
				.stream().filter(l | l.getAttribute("xdsmlFilePath").endsWith(".dsl")
						&& l.getAttribute("name").equals(DSLName))
				.findFirst().orElse(null);
		
		if (language !== null) {
			var xdsmlFilePath = language.getAttribute("xdsmlFilePath");
			if (!xdsmlFilePath.startsWith("platform:/plugin")) {
				xdsmlFilePath = "platform:/plugin" + xdsmlFilePath;
			}
			return xdsmlFilePath
		}
	}
	
	final static String GENERIC_GATE = "genericGate";
	final static String REACTIVE_GATE = "reactiveGate";
	final static String OCL_GATE = "oclGate";
	
	def void setUpLauncher (EngineFactory launcher){
		if (_self.connection.exists[c|c.endPoint.exists[g|g.gate.name.equals(GENERIC_GATE)]]) {
			launcher.setUp(EngineFactory.GENERIC);
		}
		if (_self.connection.exists[c|c.endPoint.exists[g|g.gate.name.equals(REACTIVE_GATE)]]) {
			launcher.setUp(EngineFactory.DSL_SPECIFIC);
		}
		if (_self.connection.exists[c|c.endPoint.exists[g|g.gate.name.equals(OCL_GATE)]]){
			launcher.setUp(EngineFactory.OCL);
		}
	}
	
	def String stopModelExecutionEngine(EngineFactory launcher){
		if (_self.connection.exists[c|c.endPoint.exists[g|g.gate.name.equals(REACTIVE_GATE)]]) {
			return launcher.executeDSLSpecificCommand("STOP", null, null);
		}
	}
}
class TDLRuntimeException extends Exception {
	new(String message) {
		super(message)
	}					
}