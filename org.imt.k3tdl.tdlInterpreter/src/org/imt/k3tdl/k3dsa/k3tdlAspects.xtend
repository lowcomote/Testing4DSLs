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
	TestDescription enabledTestCase
	TestConfiguration enabledConfiguration
	
	TDLTestSuiteResult testPackageResults = new TDLTestSuiteResult
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
			_self.testPackageResults.setTestSuiteName = _self.name
    		for (TestDescription tc:_self.testcases) {
    			_self.enabledTestCase = tc;
    			_self.enabledConfiguration = tc.testConfiguration;
    			val TDLTestCaseResult verdict = _self.enabledTestCase.executeTestCase()
    			_self.testPackageResults.addResult(verdict)
    			//for coverage, only considering passed and failed test cases
    			if (verdict.value != TDLTestResultUtil.INCONCLUSIVE){
    				_self.testSuiteCoverage.addTCCoverage(_self.enabledTestCase.testCaseCoverage)
    			}
    			println()
    		}
    		
    		TDLTestResultUtil.getInstance.setTestSuiteResult = _self.testPackageResults		
    		TDLCoverageUtil.instance.testSuiteCoverage = _self.testSuiteCoverage
    		TDLCoverageUtil.instance.DSLPath = _self.testcases.get(0).testConfiguration.DSLPath
    		  		
		} catch (TDLRuntimeException nt){
			println("Stopped due "+nt.message)	
		}
	}
}
@Aspect (className = TestDescription)
class TestDescriptionAspect{
	public EngineFactory launcher = new EngineFactory()
	public TDLTestCaseResult testCaseResult = new TDLTestCaseResult
	public TDLTestCaseCoverage testCaseCoverage = new TDLTestCaseCoverage();
	
	@Step
	def TDLTestCaseResult executeTestCase(){
		println("Start test case execution: " + _self.name)
		_self.testCaseResult.testCaseName = _self.name
		_self.testConfiguration.activateConfiguration(_self.launcher)
		_self.behaviourDescription.callBehavior()
		val modelExecutionResult = _self.testConfiguration.stopModelExecutionEngine(_self.launcher)
		if (modelExecutionResult !== null && modelExecutionResult.contains(TDLTestResultUtil.FAIL)){
			_self.testCaseResult.value = TDLTestResultUtil.FAIL
			_self.testCaseResult.description = modelExecutionResult.substring(modelExecutionResult.indexOf(":")+1)
		}
		if (_self.testCaseResult.value.equals(TDLTestResultUtil.PASS)) {
			println("Test case PASSED")
		}else{
			println("Test case FAILED")
		}
		
		//save the model execution trace and the MUTResource related to this test case
		_self.testCaseCoverage.testCaseName = _self.name
		_self.testCaseCoverage.trace = _self.launcher.executionTrace
    	_self.testCaseCoverage.MUTResource = _self.launcher.MUTResource
		
		return _self.testCaseResult
	}
	
	//this method is called from TDL runner
	@Step
	def TDLTestCaseResult executeTestCase(String MUTPath){
		_self.launcher.MUTPath = MUTPath
		_self.testCaseResult.testCaseName = _self.name
		_self.testConfiguration.activateConfiguration(_self.launcher, MUTPath)
		_self.behaviourDescription.callBehavior()
		val modelExecutionResult = _self.testConfiguration.stopModelExecutionEngine(_self.launcher)
		if (modelExecutionResult !== null && modelExecutionResult.contains(TDLTestResultUtil.FAIL)){
			_self.testCaseResult.value = modelExecutionResult
		}
		if (_self.testCaseResult.value.equals(TDLTestResultUtil.PASS)) {
			println("Test case PASSED")
		}else{
			println("Test case FAILED")
		}
		
		//save the model execution trace related to this test case and its MUT
		//this is required when calculating the coverage
		TDLCoverageUtil.instance.testSuiteCoverage = new TDLTestSuiteCoverage
		_self.testCaseCoverage.trace = _self.launcher.executionTrace
    	_self.testCaseCoverage.MUTResource = _self.launcher.MUTResource
		TDLCoverageUtil.instance.DSLPath = _self.testConfiguration.DSLPath
		
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
	final static String DSL_GATE = "reactiveGate";
	final static String OCL_GATE = "oclGate";
	
	def void setUpLauncher (EngineFactory launcher){
		if (_self.connection.exists[c|c.endPoint.exists[g|g.gate.name.equals(GENERIC_GATE)]]) {
			launcher.setUp(EngineFactory.GENERIC);
		}
		if (_self.connection.exists[c|c.endPoint.exists[g|g.gate.name.equals(DSL_GATE)]]) {
			launcher.setUp(EngineFactory.DSL_SPECIFIC);
		}
		if (_self.connection.exists[c|c.endPoint.exists[g|g.gate.name.equals(OCL_GATE)]]){
			launcher.setUp(EngineFactory.OCL);
		}
	}
	def String stopModelExecutionEngine(EngineFactory launcher){
		if (_self.connection.exists[c|c.endPoint.exists[g|g.gate.name.equals(DSL_GATE)]]) {
			return launcher.executeDSLSpecificCommand("STOP", null, null);
		}
	}
}
class TDLRuntimeException extends Exception {
	new(String message) {
		super(message)
	}					
}