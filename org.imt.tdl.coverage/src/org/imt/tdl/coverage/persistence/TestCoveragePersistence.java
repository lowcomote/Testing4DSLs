package org.imt.tdl.coverage.persistence;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionContext;
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionEngine;
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;

public class TestCoveragePersistence implements IEngineAddon{
	
	@Override
	public void engineStopped(IExecutionEngine<?> engine) {
		IExecutionContext<?, ?, ?> _executionContext = null;
		Resource testSutieResource = null;
		if (_executionContext == null) {
			_executionContext = engine.getExecutionContext();
			testSutieResource = getCopyOfTestSuite(_executionContext);
		}
	   //create test coverage according to the TDLTestCoverage.ecore structure
	   Package copiedTestSuite = (Package) testSutieResource.getContents().get(0);
	   TestSuiteCoverage testSuiteResult = TDLTestReportFactory.eINSTANCE.createTestSuiteResult();
	   testSuiteResult.setTestSuite(copiedTestSuite);
	   for (TDLTestCaseResult tcResultObject : TDLTestResultUtil.getInstance().getTestSuiteResult().getTestCaseResults()) {
		   String testCaseName = tcResultObject.getTestCaseName();
		   Optional<PackageableElement> optionalTC = copiedTestSuite.getPackagedElement().stream().filter(p -> p instanceof TestDescription).
			filter(t -> t.getName().equals(testCaseName)).findFirst();
		   if (optionalTC.isPresent()) {
			   TestDescription copiedTestCase = (TestDescription) optionalTC.get();
			   TestCaseResult testCaseResult = TDLTestReportFactory.eINSTANCE.createTestCaseResult();
			   testCaseResult.setTestCase(copiedTestCase);
			   String testCaseVerdict = tcResultObject.getValue();
			   if (testCaseVerdict == TDLTestResultUtil.PASS) {
				   testCaseResult.setValue(Verdicts.PASS);
			   }else if (testCaseVerdict == TDLTestResultUtil.FAIL) {
				   testCaseResult.setValue(Verdicts.FAIL);
			   }else if (testCaseVerdict == TDLTestResultUtil.INCONCLUSIVE) {
				   testCaseResult.setValue(Verdicts.INCONCLUSIVE);
			   }
			   testCaseResult.setDescription(tcResultObject.getDescription());
			   for (int i=0; i<tcResultObject.getTdlMessages().size(); i++) {
				   TDLMessageResult messageResultObject = tcResultObject.getTdlMessages().get(i);
				   TestMessageResult testMessageResult = TDLTestReportFactory.eINSTANCE.createTestMessageResult();
				   testMessageResult.setMessage(findEquivalentTDLMessage(copiedTestCase, i));
				   String testMsgVerdict = messageResultObject.getValue();
				   if (testMsgVerdict == TDLTestResultUtil.PASS) {
					   testMessageResult.setValue(Verdicts.PASS);
				   }else if (testMsgVerdict == TDLTestResultUtil.FAIL) {
					   testMessageResult.setValue(Verdicts.FAIL);
				   }else if (testMsgVerdict == TDLTestResultUtil.INCONCLUSIVE) {
					   testMessageResult.setValue(Verdicts.INCONCLUSIVE);
				   }
				   testMessageResult.setDescription(messageResultObject.getDescription());
				   testMessageResult.setTdlMessageId(messageResultObject.getTdlMessageId());
				   testCaseResult.getTestMessageResults().add(testMessageResult);
			   }
			   testSuiteResult.getTestCaseResults().add(testCaseResult);
		   }
	   }
	   
	   //create a resource for the test result
	   URI testReportURI = URI.createURI(
				_executionContext.getWorkspace().getExecutionPath().toString() + "/testReport.xmi", false);
	   Resource testResultResource = (new ResourceSetImpl()).createResource(testReportURI);
	   testResultResource.getContents().add(testSuiteResult);
	   //saving resources
	   try {
			testResultResource.save(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
		
	private Resource getCopyOfTestSuite(IExecutionContext<?, ?, ?> _executionContext) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		String projectName = _executionContext.getWorkspace().getProjectPath().toString().substring(1);
		IProject[] projects = root.getProjects();
		IProject testSuiteProject = null;
		for (int i=0; i<projects.length; i++) {
			if (projects[i].getName().equals(projectName)) {
				testSuiteProject = projects[i];
			}
		}
		String testSuiteProjectAbsolutePath = testSuiteProject.getLocation().toString().replace("/" + projectName, "");
		String copiedModelFolderPath = testSuiteProjectAbsolutePath + _executionContext.getWorkspace().getExecutionPath().toString();
		File modelFile = new File(copiedModelFolderPath);
		for (File file: modelFile.listFiles()) {
			if (file.getName().endsWith(".tdlan2")) {
				String modelPath = file.getPath().replace(testSuiteProjectAbsolutePath.replaceAll("/", "\\\\"), "").replaceAll("\\\\", "/");
				return (new ResourceSetImpl()).getResource(URI.createURI(modelPath), true);
			}
		}
		return null;
	}
}
