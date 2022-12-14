package org.imt.tdl.mutation;

import java.io.File;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.etsi.mts.tdl.ComponentInstanceRole;
import org.etsi.mts.tdl.GateReference;
import org.etsi.mts.tdl.Interaction;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TestDescription;
import org.imt.k3tdl.interpreter.TestDescriptionAspect;
import org.imt.tdl.testResult.TDLTestCaseResult;
import org.imt.tdl.testResult.TDLTestResultUtil;
import org.imt.tdl.utilities.PathHelper;

public class MutationScoreCalculator {
	
	PathHelper pathHelper;
	MutantGenerator mutantGenerator;
	
	Package testSuite;
	List<TestDescription> testCases = new ArrayList<>();
	
	private static String KILLED = "killed";
	private static String ALIVE = "alive";
	
	private HashMap<String, String> mutant_status = new HashMap<>();
	private HashMap<TestDescription, Long> testCase_executionTime = new HashMap<>();
	private HashMap<TestDescription, Integer> testCase_numOfAssertions = new HashMap<>();

	public HashMap<String, List<String>> testCase_killedMutant = new HashMap<>();
	
	int numOfKilledMutants;
	double mutationScore;
	
	double timeoutFactor;
	int timeoutConstant;
	
	public MutationScoreCalculator(Package testSuite) {
		pathHelper = new PathHelper(testSuite);
		pathHelper.findModelAndDSLPathOfTestSuite();
		mutantGenerator = new MutantGenerator(pathHelper.getModelUnderTestPath(), pathHelper.getDSLPath());
		mutantGenerator.findMutants().forEach(m -> mutant_status.put(m, ALIVE));
		
		this.testSuite = testSuite;
		testCases = testSuite.getPackagedElement().stream().filter(p -> p instanceof TestDescription).
				map(p -> (TestDescription) p).collect(Collectors.toList());
		//default values of pitest tool
		timeoutFactor = 1.25;
		timeoutConstant = 4000;
	}

	public String runTestSuiteOnOriginalModel() {
		System.out.println("Run test suite on the original model");
		for (TestDescription testCase:testCases) {
			String result = runTestCaseOnOriginalModel(testCase);
			if (result == TDLTestResultUtil.FAIL) {
				return TDLTestResultUtil.FAIL;
			}
		}
		return TDLTestResultUtil.PASS;
	}
	
	public String runTestCaseOnOriginalModel (TestDescription testCase) {
		String modelPath = pathHelper.getModelUnderTestPath().toString();
		long start = System.currentTimeMillis();
		TDLTestCaseResult result = TestDescriptionAspect.executeTestCase(testCase, modelPath);
		long stop = System.currentTimeMillis();
		if (result.getValue() != TDLTestResultUtil.PASS) {
			return TDLTestResultUtil.FAIL;
		}
		testCase_executionTime.put(testCase, (stop-start));
		testCase_numOfAssertions.put(testCase, getNumOfAssertions(testCase));
		return TDLTestResultUtil.PASS;
	}
	
	public double calculateInitialMutationScore() {
		System.out.println("\nCalculating the mutation score of the input test suite");
		testCases.forEach(t -> runTestCaseOnAliveMutants(t));
		calculateOverallMutationScore();
		System.out.println("The mutation score of the input test suite is: " + mutationScore);
		printMutationAnalysisResult();
		return mutationScore;
	}
	
	public void runTestCaseOnAllMutants(TestDescription testCase) {
		//using default values for timeout from pitest tool
		//for timeoutConstant, it is calculated based on the waiting times used in the event manager:
		//at the first of configuration and for each assertion 5000 waiting time
		int timeoutConstant = testCase_numOfAssertions.get(testCase) * 5000 + 5000;
		long timeout = (long) (testCase_executionTime.get(testCase) * timeoutFactor + timeoutConstant);
		
		//run the test case only on alive mutants
		for (String mutant:mutant_status.keySet()) {
			String mutantPath = Paths.get(mutant).toString();
			TDLTestCaseResult result = null;
			final Runnable testRunner = new Thread() {
				  @Override 
				  public void run() { 
					  TestDescriptionAspect.executeTestCase(testCase, mutantPath);
				  }
				};

			final ExecutorService executor = Executors.newSingleThreadExecutor();
			@SuppressWarnings("rawtypes")
			final Future future = executor.submit(testRunner);
			executor.shutdown(); // This does not cancel the already-scheduled task.
			try { 
			  future.get(timeout, TimeUnit.MILLISECONDS);
			  //if there is no exception, get the result
			  result = TestDescriptionAspect.testCaseResult(testCase);
			}
			catch (InterruptedException ie) { 
				ie.printStackTrace();
			}
			catch (ExecutionException ee) { 
				ee.printStackTrace();
			}
			catch (TimeoutException te) { 
				//te.printStackTrace();
				System.out.println("TimeoutException -> There is an infinite loop in the mutant");
				future.cancel(true);
			}
			if (!executor.isTerminated()) {
			    executor.shutdownNow(); // If you want to stop the code that hasn't finished
			}
			TestDescriptionAspect.launcher(testCase).disposeResources();
			if (result == null || result.getValue() == TDLTestResultUtil.FAIL) {				
				keepTestCaseKilledMutantMapping(testCase.getName(), mutant);
				if (mutant_status.get(mutant) != KILLED) {
					mutant_status.replace(mutant, KILLED);
					numOfKilledMutants++;
				}	
			}
		}
	}
	
	public void runTestCaseOnAliveMutants(TestDescription testCase) {
		Set<String> aliveMutants = new HashSet<>();
		if (numOfKilledMutants == 0) {
			aliveMutants = mutant_status.keySet();
		}
		else {
			aliveMutants = mutant_status.keySet().stream().filter(mutant -> mutant_status.get(mutant) == ALIVE).collect(Collectors.toSet());
		}
		//using default values for timeout from pitest tool
		//for timeoutConstant, it is calculated based on the waiting times used in the event manager:
		//at the first of configuration and for each assertion 5000 waiting time
		int timeoutConstant = testCase_numOfAssertions.get(testCase) * 5000 + 5000;
		long timeout = (long) (testCase_executionTime.get(testCase) * timeoutFactor + timeoutConstant);
		
		//run the test case only on alive mutants
		for (String mutant:aliveMutants) {
			String mutantPath = Paths.get(mutant).toString();
			TDLTestCaseResult result = null;
			final Runnable testRunner = new Thread() {
				  @Override 
				  public void run() { 
					  TestDescriptionAspect.executeTestCase(testCase, mutantPath);
				  }
				};

			final ExecutorService executor = Executors.newSingleThreadExecutor();
			@SuppressWarnings("rawtypes")
			final Future future = executor.submit(testRunner);
			executor.shutdown(); // This does not cancel the already-scheduled task.
			try { 
			  future.get(timeout, TimeUnit.MILLISECONDS);
			  //if there is no exception, get the result
			  result = TestDescriptionAspect.testCaseResult(testCase);
			}
			catch (InterruptedException ie) { 
				ie.printStackTrace();
			}
			catch (ExecutionException ee) { 
				ee.printStackTrace();
			}
			catch (TimeoutException te) { 
				//te.printStackTrace();
				System.out.println("TimeoutException -> There is an infinite loop in the mutant");
				future.cancel(true);
				TestDescriptionAspect.launcher(testCase).disposeResources();
			}
			if (!executor.isTerminated()) {
			    executor.shutdownNow(); // If you want to stop the code that hasn't finished
			}
			if (result == null || result.getValue() == TDLTestResultUtil.FAIL) {
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
		runTestCaseOnAliveMutants(testCase);
		if (numOfKilledMutants > pastNumOfKilledMutants) {
			double previousScore = mutationScore;
			calculateOverallMutationScore();
			System.out.println("The test case " + testCase.getName() + " has improved the mutation score by: " + (mutationScore - previousScore));
			System.out.println("- previous mutation score: " + previousScore);
			System.out.println("- new mutation score: " + mutationScore + "\n");
			return true;
		}
		return false;
	}
	
	public void calculateOverallMutationScore() {
		mutationScore = (double) numOfKilledMutants/mutantGenerator.numOfMutants;
	}
	
	public void printMutationAnalysisResult() {
		int numOfMutants = mutantGenerator.numOfMutants;
		//saving results into a .txt file
		String outputFilePath = pathHelper.getWorkspacePath() + File.separator
				+ pathHelper.getTestSuiteProjectName() + File.separator 
				+ pathHelper.getTestSuiteFileName() + 
				"_mutationReport.txt";
		StringBuilder sb = new StringBuilder();
		sb.append("Number of generated mutants: " + numOfMutants + "\n");
		sb.append("Number of killed mutants: " + numOfKilledMutants + "\n");
		sb.append("--------------------------------------------------\n");
		for (String testCase:testCase_killedMutant.keySet()) {
			sb.append("Original test case: " + testCase + "\n");
			int j = 1;
			for (String mutant:testCase_killedMutant.get(testCase)) {
				sb.append("Killed mutant " + (j++) + ": " + mutant + "\n");
			}
		}
		if (numOfKilledMutants < numOfMutants) {
			sb.append("--------------------------------------------------\n");
			sb.append("Number of alive mutants: " + (numOfMutants - numOfKilledMutants) + "\n");
			int j = 1;
			for (String mutant:getAliveMutants()) {
				sb.append("Alive mutant " + (j++) + ": " + mutant + "\n");
			}
		}
		
		try {
			Path filePath = Paths.get(outputFilePath);
			Files.writeString(filePath,sb);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean noMutantsExists() {
		return mutantGenerator.noMutantsExists;
	}
	
	public int getNumOfMutants() {
		return mutantGenerator.numOfMutants;
	}
	
	public void setNumOfKilledMutants(int numOfKilledMutants) {
		this.numOfKilledMutants = numOfKilledMutants;
	}
	
	public int getNumOfKilledMutants() {
		return numOfKilledMutants;
	}
	
	public double getOverallMutationScore() {
		return mutationScore;
	}
	
	public double getTestCaseMutationScore(TestDescription testCase) {
		int numOfKilledMutants = testCase_killedMutant.get(testCase.getName()).size();
		double mutationScore = (double) numOfKilledMutants/mutantGenerator.numOfMutants;
		return mutationScore;
	}
	
	public Set<String> getAliveMutants(){
		return mutant_status.keySet().stream().
				filter(mutant -> mutant_status.get(mutant) == ALIVE).collect(Collectors.toSet());
	}
	
	public void setTestCase_numOfAssertions(TestDescription testCase, int numOfAssertions) {
		testCase_numOfAssertions.put(testCase, numOfAssertions);
	}
	
	public int getNumOfAssertions(TestDescription tdlTestCase){
		TreeIterator<EObject> iterator = tdlTestCase.getBehaviourDescription().getBehaviour().eAllContents();
		int numOfAssertions = 0;
		while (iterator.hasNext()) {
			EObject eobject = iterator.next();
			if (eobject instanceof Interaction interaction) {
				GateReference sourceGate = interaction.getSourceGate();
				if (sourceGate.getComponent().getRole() == ComponentInstanceRole.SUT) {
					numOfAssertions++;
				}
			}
		}
		return numOfAssertions;
	}
}
