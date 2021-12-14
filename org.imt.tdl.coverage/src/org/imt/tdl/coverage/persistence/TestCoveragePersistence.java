package org.imt.tdl.coverage.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionContext;
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionEngine;
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;
import org.etsi.mts.tdl.PackageableElement;
import org.etsi.mts.tdl.TestDescription;
import org.imt.tdl.coverage.TDLCoverageUtil;
import org.imt.tdl.coverage.TDLTestCaseCoverage;

import TDLTestCoverage.CoverageStatus;
import TDLTestCoverage.ModelObjectCoverageStatus;
import TDLTestCoverage.TDLTestCoverageFactory;
import TDLTestCoverage.TestCaseCoverage;
import TDLTestCoverage.TestSuiteCoverage;

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
	   org.etsi.mts.tdl.Package copiedTestSuite = (org.etsi.mts.tdl.Package) testSutieResource.getContents().get(0);
	   TestSuiteCoverage testSuiteCoverage = TDLTestCoverageFactory.eINSTANCE.createTestSuiteCoverage();
	   testSuiteCoverage.setTestSuite(copiedTestSuite);
	   testSuiteCoverage.setCoveragePercentage(TDLCoverageUtil.getInstance().getTestSuiteCoverage().getTsCoveragePercentage());
	   for (TDLTestCaseCoverage tcCoverageObject : TDLCoverageUtil.getInstance().getTestSuiteCoverage().getTcCoverages()) {
		   String testCaseName = tcCoverageObject.getTestCaseName();
		   Optional<PackageableElement> optionalTC = copiedTestSuite.getPackagedElement().stream().filter(p -> p instanceof TestDescription).
			filter(t -> t.getName().equals(testCaseName)).findFirst();
		   if (optionalTC.isPresent()) {
			   TestDescription copiedTestCase = (TestDescription) optionalTC.get();
			   TestCaseCoverage testCaseCoverage = TDLTestCoverageFactory.eINSTANCE.createTestCaseCoverage();
			   testCaseCoverage.setTestCase(copiedTestCase);
			   testCaseCoverage.setCoveragePercentage(tcCoverageObject.getTcCoveragePercentage());
			   for (int i=0; i<tcCoverageObject.getModelObjects().size(); i++) {
				   ModelObjectCoverageStatus tsModelObjectCoverageStatus = TDLTestCoverageFactory.eINSTANCE.createModelObjectCoverageStatus();
				   ModelObjectCoverageStatus tcModelObjectCoverageStatus = TDLTestCoverageFactory.eINSTANCE.createModelObjectCoverageStatus();
				   
				   EObject modelObject = tcCoverageObject.getModelObjects().get(i);
				   tsModelObjectCoverageStatus.setModelObject(modelObject);
				   tcModelObjectCoverageStatus.setModelObject(modelObject);
				   String tsCoverage = TDLCoverageUtil.getInstance().getTestSuiteCoverage().getTsObjectCoverageStatus().get(i);
				   String tcCoverage = tcCoverageObject.getTcObjectCoverageStatus().get(i);
				   tsModelObjectCoverageStatus.setCoverageStatus(getCoverageStatus(tsCoverage));
				   tcModelObjectCoverageStatus.setCoverageStatus(getCoverageStatus(tcCoverage));
				   
				   testSuiteCoverage.getTsObjectCoverageStatus().add(tsModelObjectCoverageStatus);
				   testCaseCoverage.getTcObjectCoverageStatus().add(tcModelObjectCoverageStatus);
			   }
			   testSuiteCoverage.getTestCaseCoverages().add(testCaseCoverage);
		   }
	   }
	   
	   //create a resource for the test coverage
	   URI testCoverageURI = URI.createURI(
				_executionContext.getWorkspace().getExecutionPath().toString() + "/testCoverage.xmi", false);
	   Resource testCoverageResource = (new ResourceSetImpl()).createResource(testCoverageURI);
	   testCoverageResource.getContents().add(testSuiteCoverage);
	   //saving resources
	   try {
		   testCoverageResource.save(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
		
	private CoverageStatus getCoverageStatus(String coverage) {
		if (coverage == TDLCoverageUtil.COVERED) {
			return CoverageStatus.COVERED;
		}
		else if (coverage == TDLCoverageUtil.NOT_COVERED) {
			return CoverageStatus.NOTCOVERED;
		}
		else if (coverage == TDLCoverageUtil.NOT_COVERABLE) {
			return CoverageStatus.NOTCOVERABLE;
		}
		return null;
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
