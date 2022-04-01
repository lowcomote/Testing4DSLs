package org.imt.tdl.amplification.evaluation;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.etsi.mts.tdl.Annotation;
import org.etsi.mts.tdl.ComponentInstance;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TestDescription;
import org.imt.k3tdl.k3dsa.TestDescriptionAspect;
import org.imt.tdl.testResult.TDLTestCaseResult;
import org.imt.tdl.testResult.TDLTestResultUtil;

public class mutationScoreCalculator {
	
	Package testSuite;
	
	private static String KILLED = "killed";
	private static String ALIVE = "alive";
	
	private HashMap<String, String> mutant_status = new HashMap<>();
	
	int numOfMutants;
	int numOfKilledMutants;
	double mutationScore;
	
	public mutationScoreCalculator(Package testSuite) {
		this.testSuite = testSuite;
		findWorkspacePath();
		findMutants();
	}
	
	public double calculateInitialMutationScore() {
		List<TestDescription> testCases = testSuite.getPackagedElement().stream().filter(p -> p instanceof TestDescription).
			map(p -> (TestDescription) p).collect(Collectors.toList());
		testCases.forEach(t -> runTestCaseOnMutants(t));
		calculateMutationScore();
		System.out.print("The mutation score of the input test suite is: " + mutationScore);
		return mutationScore;
	}
	
	public double getMutationScore() {
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
				numOfKilledMutants++;
			}
		}
	}
	
	public boolean testCaseImprovesMutationScore (TestDescription testCase) {
		int pastNumOfKilledMutants = numOfKilledMutants;
		runTestCaseOnMutants(testCase);
		if (numOfKilledMutants > pastNumOfKilledMutants) {
			double previousScore = mutationScore;
			calculateMutationScore();
			System.out.print("The test case " + testCase.getName() + " has improved the mutation score by: " + (mutationScore - previousScore));
			System.out.print("- previous mutation score: " + previousScore);
			System.out.print("- new mutation score: " + mutationScore);
			return true;
		}
		return false;
	}
	
	String seedModelPath;
	String workspacePath;
	IProject mutantsProject;
	String mutantsFolderPath;
	
	private void findWorkspacePath () {
		Optional<TestDescription> testCase = testSuite.getPackagedElement().stream().filter(p -> p instanceof TestDescription).
		map(p -> (TestDescription) p).findFirst();
		Optional<ComponentInstance> sutComponent = testCase.get().getTestConfiguration().getComponentInstance().
				stream().filter(ci -> ci.getRole().toString().equals("SUT")).findFirst();
		for (Annotation a:sutComponent.get().getAnnotation()){
			if (a.getKey().getName().equals("MUTPath")){
				seedModelPath = a.getValue().substring(1, a.getValue().length()-1);
			}
		}
	}
	
	private void findMutants() {
		String projectName = seedModelPath.substring(1, seedModelPath.lastIndexOf("/"));
		mutantsProject =  ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		File modelFolder = new File(mutantsProject.getLocation() + "\\mutants");
		for (File file : modelFolder.listFiles()) {
			pathsHelper(projectName, file);
		}
	}
	
	private void pathsHelper(String projectName, File file) {
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
				pathsHelper(projectName, innerFile);
			}
		}
	}
	
	private void calculateMutationScore() {
		mutationScore = (double) numOfKilledMutants/numOfMutants;
	}
}
