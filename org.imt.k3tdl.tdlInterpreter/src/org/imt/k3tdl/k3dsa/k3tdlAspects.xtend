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
import org.imt.tdl.executionEngine.EngineFactory
import org.imt.tdl.testResult.TDLTestCaseResult
import org.imt.tdl.testResult.TDLTestPackageResult
import org.imt.tdl.testResult.TestResultUtil

import static extension org.imt.k3tdl.k3dsa.BehaviourDescriptionAspect.*
import static extension org.imt.k3tdl.k3dsa.TestConfigurationAspect.*
import static extension org.imt.k3tdl.k3dsa.TestDescriptionAspect.*
import org.eclipse.core.runtime.Platform
import java.util.Arrays
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.core.runtime.IConfigurationElement

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
    			val TDLTestCaseResult verdict = _self.enabledTestCase.executeTestCase()
    			_self.testPackageResults.addResult(verdict)
    			println()
    		}
    		TestResultUtil.instance.testPackageResult = _self.testPackageResults  		
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
		_self.testConfiguration.stopModelExecutionEngine(_self.launcher)
		return _self.testCaseResult
	}
	//this method is called from TDL runner
	@Step
	def TDLTestCaseResult executeTestCase(String MUTPath){
		_self.launcher.MUTPath = MUTPath
		_self.testConfiguration.activateConfiguration(_self.launcher, MUTPath)
		_self.behaviourDescription.callBehavior()
		_self.testConfiguration.stopModelExecutionEngine(_self.launcher)
		return _self.testCaseResult
	}
}
@Aspect (className = TestConfiguration)
class TestConfigurationAspect{
	public String MUTPath;
	public String DSLPath;

	def void activateConfiguration(EngineFactory launcher){
		//finding the address of MUT From the annotations of the SUT component (the component with role==0)
		for (Annotation a:_self.componentInstance.filter[ci | ci.role.toString == "SUT"].get(0).annotation){
			if (a.key.name == 'MUTPath'){
				_self.MUTPath = a.value.substring(1, a.value.length-1)
				launcher.MUTPath = _self.MUTPath
			}else if (a.key.name == 'DSLName'){
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
		val ResourceSet resSet = new ResourceSetImpl()
		val IConfigurationElement language = Arrays
				.asList(Platform.getExtensionRegistry()
						.getConfigurationElementsFor("org.eclipse.gemoc.gemoc_language_workbench.xdsml"))
				.stream().filter(l | l.getAttribute("xdsmlFilePath").endsWith(".dsl")
						&& l.getAttribute("name").equals(DSLName))
				.findFirst().orElse(null);
		
		if (language != null) {
			var xdsmlFilePath = language.getAttribute("xdsmlFilePath");
			if (!xdsmlFilePath.startsWith("platform:/plugin")) {
				xdsmlFilePath = "platform:/plugin" + xdsmlFilePath;
			}
			return xdsmlFilePath
		}
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
	def void stopModelExecutionEngine(EngineFactory launcher){
		if (_self.connection.exists[c|c.endPoint.exists[g|g.gate.name.contains('dslSpecific')]]) {
			launcher.executeDSLSpecificCommand("STOP", null, null);
		}
	}
}
class TDLRuntimeException extends Exception {
	new(String message) {
		super(message)
	}					
}