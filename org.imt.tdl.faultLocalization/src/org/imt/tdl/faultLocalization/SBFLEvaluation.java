package org.imt.tdl.faultLocalization;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TestDescription;
import org.imt.tdl.coverage.TDLTestSuiteCoverage;
import org.imt.tdl.runner.TDLCore;
import org.imt.tdl.testResult.TDLTestSuiteResult;

public class SBFLEvaluation {

	private List<String> mutants = new ArrayList<>();
	private List<String> registeries = new ArrayList<>();	
	private HashMap<String, String> mutantRegistry = new HashMap<>();
	private Package testSuite = null;
	
	private HashMap<String, TDLTestSuiteResult> mutantVerdict = new HashMap<>();
	private HashMap<String, TDLTestSuiteCoverage> mutantCoverage = new HashMap<>();
	
	public void findMutants() {
		String mutantsProjectName = "";
		String testSuiteProjectName = "";
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] projects = root.getProjects();
		IProject mutantsProject = null;
		IProject testSuiteProject = null;
		for (int i=0; i<projects.length; i++) {
			if (projects[i].getName().equals(mutantsProjectName)) {
				mutantsProject = projects[i];
			}else if (projects[i].getName().equals(testSuiteProjectName)) {
				testSuiteProject = projects[i];
			}
		}
		//finding mutants and mapping them to their registry
		findMutants_Registries(mutantsProject);
		
		//finding test suite and run it on mutants to find live ones and keep verdict and coverage of killed ones
		String testResourcePath = "platform:/resource/"+ testSuiteProjectName + "/";
		File testsFolder = new File(testSuiteProject.getProjectRelativePath().toString());	
		for (File testFile : testsFolder.listFiles()) {
			this.testSuite = getTestSuite(testResourcePath, testFile);
		}
		testMutants();
		
	}

	private Package getTestSuite(String testResourcePath, File testFile) {
		if (testFile.isFile() && testFile.getName().endsWith(".tdlan2")) {
			String testFilePath = testResourcePath + testFile.getName();
			org.etsi.mts.tdl.Package testPackage = getTestPackage(testFilePath);
			if (testPackage != null) {
				return testPackage;
			}			
		}
		else if (testFile.isDirectory()){
			testResourcePath += (testFile.getName() + "/");
			for (File innerFile : testFile.listFiles()) {
				return getTestSuite(testResourcePath, innerFile);
			}
		}
		return null;
	}
	
	private org.etsi.mts.tdl.Package getTestPackage(String testFilePath) {
		Resource testSuiteRes = (new ResourceSetImpl()).getResource(URI.createURI(testFilePath), true);
		org.etsi.mts.tdl.Package testPackage = (org.etsi.mts.tdl.Package) testSuiteRes.getContents().get(0);
		for (Object element: testPackage.getPackagedElement()) {
			if (element instanceof TestDescription) {
				return testPackage;
			}
		}
		return null;
	}
	
	String workspacePath;
	
	private void findMutants_Registries(IProject mutantProject) {
		File projectFolder = new File(mutantProject.getProjectRelativePath() + "\\model");
		for (File file : projectFolder.listFiles()) {
			pathsHelper(mutantProject.getName(), file);
		}
		for (String mutantPath:this.mutants) {
			String mutator = getMutator(mutantPath);
			String mutantName = mutantPath.substring(mutantPath.lastIndexOf("\\"), mutantPath.indexOf(".model")-1);
			Optional<String> registery = this.registeries.stream().filter(r -> r.contains(mutator) && r.contains(mutantName)).findFirst();
			if (registery.isPresent()) {
				this.mutantRegistry.put(mutantPath, registery.get());
			}
		}
	}
	
	private void pathsHelper(String projectName, File file) {
		if (file.isFile() && file.getName().endsWith(".model")) {
			String filePath = file.getPath();
			if (this.workspacePath == null) {
				this.workspacePath = filePath.substring(0, filePath.lastIndexOf(projectName)-1);
			}		
			//get the relative path of the file
			filePath = filePath.replace(this.workspacePath, "");
			if (file.getName().endsWith("Registry.model")) {
				this.registeries.add(filePath);
			}else {
				this.mutants.add(filePath);
			}
		}
		else if (file.isDirectory()){
			for (File innerFile : file.listFiles()) {
				pathsHelper(projectName, innerFile);
			}
		}
	}
	
	public String getMutator (String filePath) {
		filePath = filePath.replace(this.workspacePath, "");
		String[] filePathSections = filePath.split("\\");
		if (filePath.contains("Registry.model")) {
			return filePathSections[filePathSections.length-3];
		}else {
			return filePathSections[filePathSections.length-2];
		}
	}
	
	private void testMutants() {
		TDLCore tdlCore = new TDLCore();
		for (String mutant:this.mutants) {
			tdlCore.runTestAndCalculateCoverage(this.testSuite, mutant);
			if (tdlCore.getTestSuiteResult().getNumOfFailedTestCases() == 0) {
				//the mutant is alive, so it must be removed from the evaluation data
				this.mutantRegistry.remove(mutant);
			}
			else {
				//the mutant is killed, so its test result and its coverage must be kept
				this.mutantVerdict.put(mutant, tdlCore.getTestSuiteResult());
				this.mutantCoverage.put(mutant, tdlCore.getTestSuiteCoverage());
			}
		}
	}
}
