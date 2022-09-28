package org.imt.tdl.coverage.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionContext;
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionEngine;
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;
import org.etsi.mts.tdl.PackageableElement;
import org.etsi.mts.tdl.TestDescription;
import org.imt.tdl.coverage.TDLCoverageUtil;
import org.imt.tdl.coverage.TDLTestCaseCoverage;
import org.imt.tdl.coverage.TDLTestSuiteCoverage;

import TDLTestCoverage.CoverageStatus;
import TDLTestCoverage.ModelObjectCoverageStatus;
import TDLTestCoverage.TDLTestCoverageFactory;
import TDLTestCoverage.TestCaseCoverage;
import TDLTestCoverage.TestSuiteCoverage;

public class TestCoveragePersistence implements IEngineAddon{
	
	String pathToReportsFiles;
	Resource MUTResource;
	private List<EObject> modelObjects = new ArrayList<>();
	
	@Override
	public void engineStopped(IExecutionEngine<?> engine) {
		IExecutionContext<?, ?, ?> _executionContext = engine.getExecutionContext();
		pathToReportsFiles = _executionContext.getWorkspace().getExecutionPath().toString();
		Resource testSutieResource = getCopyOfTestSuite(_executionContext);

		if (TDLCoverageUtil.getInstance().getTestSuiteCoverage().getTsCoveragePercentage() == 0) {
			TDLCoverageUtil.getInstance().runCoverageComputation();
		}
		
	   //create test coverage according to the TDLTestCoverage.ecore structure
	   org.etsi.mts.tdl.Package copiedTestSuite = (org.etsi.mts.tdl.Package) testSutieResource.getContents().get(0);
	   TDLTestSuiteCoverage tsCoveragObject = TDLCoverageUtil.getInstance().getTestSuiteCoverage();
	   TestSuiteCoverage testSuiteCoverage = TDLTestCoverageFactory.eINSTANCE.createTestSuiteCoverage();
	   testSuiteCoverage.setTestSuite(copiedTestSuite);
	   testSuiteCoverage.setCoveragePercentage(tsCoveragObject.getTsCoveragePercentage());
	   
	   for (TDLTestCaseCoverage tcCoverageObject : tsCoveragObject.getTcCoverages()) {
		   String testCaseName = tcCoverageObject.getTestCaseName();
		   Optional<PackageableElement> optionalTC = copiedTestSuite.getPackagedElement().stream().
				   filter(p -> p instanceof TestDescription).
				   filter(t -> t.getName().equals(testCaseName)).findFirst();
		   if (optionalTC.isEmpty()) { break; }
		   TestDescription copiedTestCase = (TestDescription) optionalTC.get();
		   TestCaseCoverage testCaseCoverage = TDLTestCoverageFactory.eINSTANCE.createTestCaseCoverage();
		   testCaseCoverage.setTestCase(copiedTestCase);
		   testCaseCoverage.setCoveragePercentage(tcCoverageObject.getTcCoveragePercentage());
		   copyMUTResource(tcCoverageObject.getMUTResource(), copiedTestCase.getName());
		   for (int i=0; i<tcCoverageObject.getModelObjects().size(); i++) {
			   ModelObjectCoverageStatus tcModelObjectCoverageStatus = TDLTestCoverageFactory.eINSTANCE.createModelObjectCoverageStatus();
			   EObject modelObject = tcCoverageObject.getModelObjects().get(i);
			   tcModelObjectCoverageStatus.setModelObject(getEObjectFromCopiedMUT(modelObject));
			   String tcCoverage = tcCoverageObject.getTcObjectCoverageStatus().get(i);
			   tcModelObjectCoverageStatus.setCoverageStatus(getCoverageStatus(tcCoverage));
			   testCaseCoverage.getTcObjectCoverageStatus().add(tcModelObjectCoverageStatus);
		   }
		   testSuiteCoverage.getTestCaseCoverages().add(testCaseCoverage);
	   }
	   
	   for (int i=0; i<tsCoveragObject.getModelObjects().size(); i++) {
		   ModelObjectCoverageStatus tsModelObjectCoverageStatus = TDLTestCoverageFactory.eINSTANCE.createModelObjectCoverageStatus();
		   EObject modelObject = tsCoveragObject.getModelObjects().get(i);
		   tsModelObjectCoverageStatus.setModelObject(getEObjectFromCopiedMUT(modelObject));
		   String tsCoverage = tsCoveragObject.getTsObjectCoverageStatus().get(i);
		   tsModelObjectCoverageStatus.setCoverageStatus(getCoverageStatus(tsCoverage));
		   testSuiteCoverage.getTsObjectCoverageStatus().add(tsModelObjectCoverageStatus);
	   }
	   
	   //create a resource for the test coverage
	   URI testCoverageURI = URI.createURI(pathToReportsFiles + "/testCoverage.xmi", false);
	   Resource testCoverageResource = (new ResourceSetImpl()).createResource(testCoverageURI);
	   testCoverageResource.getContents().add(testSuiteCoverage);
	   //saving resources
	   try {
		   testCoverageResource.save(null);
		} catch (IOException e) {
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
		String copiedModelFolderPath = testSuiteProjectAbsolutePath + pathToReportsFiles;
		File modelFile = new File(copiedModelFolderPath);
		for (File file: modelFile.listFiles()) {
			if (file.getName().endsWith(".tdlan2")) {
				String modelPath = file.getPath().replace(testSuiteProjectAbsolutePath.replaceAll("/", "\\\\"), "").replaceAll("\\\\", "/");
				return (new ResourceSetImpl()).getResource(URI.createURI(modelPath), true);
			}
		}
		return null;
	}
	
	//save the model under test if it is not saved or if it is different from the current saved file
	private void copyMUTResource (Resource resource, String testID) {
		URI modelURI = null;
		if (MUTResource == null) {
			modelURI = URI.createURI(pathToReportsFiles + "/modelUnderTest.xmi", false);
		}
		//the test case uses a different model under test
		else if (!EcoreUtil.equals(MUTResource.getContents().get(0), resource.getContents().get(0))) {
			modelURI = URI.createURI(pathToReportsFiles + "/modelUnderTest_" + testID + ".xmi", false);
		}
		//the model under test is already copied, so do nothing
		else {return;}
		
		MUTResource = (new ResourceSetImpl()).createResource(modelURI);
		MUTResource.getContents().addAll(EcoreUtil.copyAll(resource.getContents()));
	    try {
	    	MUTResource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    TreeIterator<EObject> modelContents = MUTResource.getAllContents();
		while (modelContents.hasNext()) {
			modelObjects.add(modelContents.next());
		}
	}
	
	private EObject getEObjectFromCopiedMUT (EObject eobject) {
		EcoreUtil.resolveAll(eobject);
		if (modelObjects.stream().filter(o -> EcoreUtil.equals(o, eobject)).findFirst().isEmpty()) {
			System.out.println("cannot find eobject " + eobject);
		}
		return modelObjects.stream().filter(o -> EcoreUtil.equals(o, eobject)).findFirst().get();
	}
}
