package org.imt.tdl.amplification.evaluation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TestDescription;
import org.imt.k3tdl.k3dsa.TestDescriptionAspect;
import org.imt.tdl.amplification.utilities.PathHelper;
import org.imt.tdl.testResult.TDLTestCaseResult;
import org.imt.tdl.testResult.TDLTestResultUtil;

public class MutationScoreCalculator {
	
	Package testSuite;
	public boolean noMutantsExists;
	
	private static String KILLED = "killed";
	private static String ALIVE = "alive";
	
	private HashMap<String, String> mutant_status = new HashMap<>();
	public HashMap<String, List<String>> testCase_killedMutant = new HashMap<>();
	
	int numOfMutants;
	int numOfKilledMutants;

	double mutationScore;
	
	String workspacePath;
	String seedModelPath;
	IProject mutantsProject;
	String mutantsFolderPath;
	
	public MutationScoreCalculator(Package testSuite) {
		this.testSuite = testSuite;
		seedModelPath = PathHelper.getInstance().getSeedModelPath();
		workspacePath = PathHelper.getInstance().getWorkspacePath();
		findMutants();
	}
	
	public double calculateInitialMutationScore() {
		System.out.println("Calculating the mutation score of the input test suite");
		List<TestDescription> testCases = testSuite.getPackagedElement().stream().filter(p -> p instanceof TestDescription).
			map(p -> (TestDescription) p).collect(Collectors.toList());
		testCases.forEach(t -> runTestCaseOnMutants(t));
		calculateMutationScore();
		System.out.println("The mutation score of the input test suite is: " + mutationScore);
		return mutationScore;
	}

	private void runTestCaseOnMutants(TestDescription testCase) {
		Set<String> aliveMutants = new HashSet<>();
		if (numOfKilledMutants == 0) {
			aliveMutants = mutant_status.keySet();
		}
		else {
			aliveMutants = mutant_status.keySet().stream().filter(mutant -> mutant_status.get(mutant) == ALIVE).collect(Collectors.toSet());
		}
		//run the test case only on alive mutants
		for (String mutant:aliveMutants) {
			String mutantPath = mutant.replace("\\", "/");
			TDLTestCaseResult result = TestDescriptionAspect.executeTestCase(testCase, mutantPath);
			if (result.getValue() == TDLTestResultUtil.FAIL) {
				mutant_status.replace(mutant, KILLED);
				keepTestCaseKilledMutantMapping(testCase.getName(), mutant);
				numOfKilledMutants++;
			}
		}
	}
	
	private void keepTestCaseKilledMutantMapping(String testCaseName, String mutantPath) {
		List<String> killedMutants = testCase_killedMutant.get(testCaseName);
		if (killedMutants == null) {
			killedMutants = new ArrayList<>();
			killedMutants.add(mutantPath);
			testCase_killedMutant.put(testCaseName, killedMutants);
		}
		else {
			killedMutants.add(mutantPath);
			testCase_killedMutant.replace(testCaseName, killedMutants);
		}
	}

	public boolean testCaseImprovesMutationScore (TestDescription testCase) {
		int pastNumOfKilledMutants = numOfKilledMutants;
		runTestCaseOnMutants(testCase);
		if (numOfKilledMutants > pastNumOfKilledMutants) {
			double previousScore = mutationScore;
			calculateMutationScore();
			System.out.println("The test case " + testCase.getName() + " has improved the mutation score by: " + (mutationScore - previousScore));
			System.out.println("- previous mutation score: " + previousScore);
			System.out.println("- new mutation score: " + mutationScore);
			return true;
		}
		return false;
	}

	private void findMutants() {
		String projectName = seedModelPath.substring(1, seedModelPath.lastIndexOf("/"));
		mutantsProject =  ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		File modelFolder = new File(mutantsProject.getLocation() + "\\mutants");
		if (modelFolder.listFiles() == null) {
			noMutantsExists = true;
		}else {
			for (File file : modelFolder.listFiles()) {
				mutantsPathsHelper(projectName, file);
			}
		}
	}
	
	private void mutantsPathsHelper(String projectName, File file) {
		if (file.isFile() && file.getName().endsWith(".model")) {
			String filePath = file.getPath();
			if (workspacePath == null) {
				workspacePath = filePath.substring(0, filePath.lastIndexOf(projectName)-1);
			}		
			//get the relative path of the file
			filePath = filePath.replace(workspacePath, "");
			if (!filePath.equals(seedModelPath)){
				mutant_status.put(filePath, ALIVE);
				numOfMutants++;
			}
		}
		else if (file.isDirectory()){
			for (File innerFile : file.listFiles()) {
				mutantsPathsHelper(projectName, innerFile);
			}
		}
	}
	
	private void calculateMutationScore() {
		mutationScore = (double) numOfKilledMutants/numOfMutants;
	}
	
	public String getWorkspacePath() {
		return workspacePath;
	}
	
	public int getNumOfMutants() {
		return numOfMutants;
	}
	
	public int getNumOfKilledMutants() {
		return numOfKilledMutants;
	}
	
	public double getMutationScore() {
		return mutationScore;
	}
}
