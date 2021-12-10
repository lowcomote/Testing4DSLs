package org.imt.tdl.sbfl.evaluation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TestDescription;
import org.imt.tdl.coverage.TDLTestSuiteCoverage;
import org.imt.tdl.faultLocalization.SBFLMeasures;
import org.imt.tdl.faultLocalization.SuspiciousnessRanking;
import org.imt.tdl.testResult.TDLTestSuiteResult;

import appliedMutations.AppMutation;
import appliedMutations.InformationChanged;
import appliedMutations.Mutations;

public class SBFLEvaluation {

	private List<String> mutants = new ArrayList<>();
	private List<String> registeries = new ArrayList<>();	
	private HashMap<String, String> mutantRegistry = new HashMap<>();
	private Package testSuite = null;
	
	private HashMap<String, TDLTestSuiteResult> mutantVerdict = new HashMap<>();
	private HashMap<String, TDLTestSuiteCoverage> mutantCoverage = new HashMap<>();
	
	IProject mutantsProject;
	IProject testSuiteProject;
	
	public SBFLEvaluation (IProject mutantsProject, IProject testSuiteProject) {
		this.mutantsProject = mutantsProject;
		this.testSuiteProject = testSuiteProject;
	}
	public void evaluateSBFLTechniques() {
		//finding mutants and mapping them to their registry
		findMutantRegistryMapping(this.mutantsProject);
		
		//finding test suite and run it on mutants to find live ones and keep verdict and coverage of killed ones
		String testResourcePath = "platform:/resource/"+ testSuiteProject.getName() + "/";
		File testsFolder = new File(testSuiteProject.getLocation().toString());	
		for (File testFile : testsFolder.listFiles()) {
			this.testSuite = getTestSuite(testResourcePath, testFile);
		}
		
		testMutants();
		for (String mutant:this.mutantRegistry.keySet()) {
			localizeFaultOfMutant(mutant);
		}
	}
	
	String workspacePath;
	
	private void findMutantRegistryMapping(IProject mutantsProject) {
		File projectFolder = new File(mutantsProject.getLocation() + "\\model");
		for (File file : projectFolder.listFiles()) {
			pathsHelper(mutantsProject.getName(), file);
		}
		for (String mutantPath:this.mutants) {
			String mutator = getMutator(mutantPath);
			String mutantName = mutantPath.substring(mutantPath.lastIndexOf("\\") + 1, mutantPath.indexOf(".model"));
			Optional<String> registery = this.registeries.stream().filter(r -> r.contains(mutator) && r.contains(mutantName + "Registry")).findFirst();
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
		String[] filePathSections = filePath.split("\\\\");
		if (filePath.contains("Registry.model")) {
			return filePathSections[filePathSections.length-3];
		}else {
			return filePathSections[filePathSections.length-2];
		}
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
	
	private void testMutants() {
		for (String mutant:this.mutants) {
			MutationTestRunner testRunner = new MutationTestRunner();
			testRunner.runTestAndCalculateCoverage(this.testSuite, mutant);
			if (testRunner.getTestSuiteResult().getNumOfFailedTestCases() == 0) {
				//the mutant is alive, so it must be removed from the evaluation data
				this.mutantRegistry.remove(mutant);
			}
			else {
				//the mutant is killed, so its test result and its coverage must be kept
				this.mutantVerdict.put(mutant, testRunner.getTestSuiteResult());
				this.mutantCoverage.put(mutant, testRunner.getTestSuiteCoverage());
			}
		}
	}
	
	public void localizeFaultOfMutant(String mutant) {
		TDLTestSuiteResult testSuiteResult = this.mutantVerdict.get(mutant);
		TDLTestSuiteCoverage testSuiteCoverage = this.mutantCoverage.get(mutant);
		EObject faultyObject = getFaultyObjectOfMutant(mutant);
		int indexOfFaultyObject = testSuiteCoverage.getModelObjects().indexOf(faultyObject);
		
		SuspiciousnessRanking suspComputing = new SuspiciousnessRanking(testSuiteResult, testSuiteCoverage);
		suspComputing.calculateMeasures();
		List<SBFLMeasures> mutantSBFLMeasures = suspComputing.getElementsSBFLMeasures();
		for (String sbflTechnique : suspComputing.sbflTechniques) {
			for (SBFLMeasures measure:mutantSBFLMeasures) {
				measure.currentTechnique = sbflTechnique;
				double susp = suspComputing.getSuspiciousness(measure);
				measure.getSusp().put(sbflTechnique, susp);
			}
			suspComputing.calculateRanks();
			Integer rank = mutantSBFLMeasures.get(indexOfFaultyObject).getRank().get(sbflTechnique);
			System.out.println("Rank of object " + indexOfFaultyObject + " calculated by " + sbflTechnique + ": " + rank);
		}

		SBFLMeasures measures4faultyObject = mutantSBFLMeasures.get(indexOfFaultyObject);
		for (String sbflTechnique : suspComputing.sbflTechniques) {
			suspComputing.measureEXAMScores(measures4faultyObject, sbflTechnique);
		}
	}
	
	private EObject getFaultyObjectOfMutant(String mutant) {
		String mutantRegistryPath = "platform:/resource" + this.mutantRegistry.get(mutant).replace("\\", "/");
		Resource registryResource = (new ResourceSetImpl()).getResource(URI.createURI(mutantRegistryPath), true);
		Mutations mutations = (Mutations) registryResource.getContents().get(0);
		Optional<AppMutation> informationChangedMutation = mutations.getMuts().stream().filter(m -> m instanceof InformationChanged).findFirst();
		if (informationChangedMutation.isPresent()) {
			return ((InformationChanged)informationChangedMutation.get()).getObject();
		}
		return null;
	}
}
