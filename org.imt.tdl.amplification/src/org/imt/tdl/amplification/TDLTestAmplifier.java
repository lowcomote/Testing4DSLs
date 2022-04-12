package org.imt.tdl.amplification;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TestDescription;
import org.etsi.mts.tdl.tdlFactory;
import org.imt.tdl.amplification.evaluation.MutationScoreCalculator;
import org.imt.tdl.amplification.utilities.PathHelper;

public class TDLTestAmplifier {
	
	Resource testSuiteRes;
	Package tdlTestSuite;
	
	MutationScoreCalculator scoreCalculator;
	int initialNumOfKilledMutants;
	double initialMutationScore;
	
	int numOfIteration;
	int numNewTests;
	Map<Integer, List<TestDescription>> iteration_ampTests = new HashMap<>();
	
	public void amplifyTestSuite(IFile testSuiteFile) {
		PathHelper.getInstance().setTestSuiteFile(testSuiteFile);
		testSuiteRes = PathHelper.getInstance().getTestSuiteResource();
		tdlTestSuite = PathHelper.getInstance().getTestSuite();
		
		//calculating the mutation score of the manually-written test suite (i.e., the input test suite)
		scoreCalculator = new MutationScoreCalculator(tdlTestSuite);
		initialNumOfKilledMutants = 0;
		initialMutationScore = 0;
		if (!scoreCalculator.noMutantsExists) {
			initialMutationScore = scoreCalculator.calculateInitialMutationScore();
			initialNumOfKilledMutants = scoreCalculator.getNumOfKilledMutants();
		}
		
		//perform test amplification if the mutation score of the input test suite is not 100%
		if (initialMutationScore < 1) {
			numOfIteration = 0;
			numNewTests = 0;
			
			List<TestDescription> initialTdlTestCases = tdlTestSuite.getPackagedElement().stream().
					filter(p -> p instanceof TestDescription).map(t -> (TestDescription) t).collect(Collectors.toList());
			
			//run test amplification on the given test cases
			amplifyTestCases(initialTdlTestCases);
			/*
			 * In DSpot, the used stop-criterion is a number of iteration (default = 3). 
			 * Our stop-criterion is a combination of number of iteration and reaching to 100% mutation score
			 * at each iteration, the previously amplified tests are amplified
			 */
			while (numOfIteration<3 && scoreCalculator.getMutationScore()<1) {
				amplifyTestCases(iteration_ampTests.get(numOfIteration-1));
			}
			
			System.out.println("\nTest Amplification has been performed successfully.");
			if (!scoreCalculator.noMutantsExists) {
				String outputFilePath = PathHelper.getInstance().getWorkspacePath() + "\\"
							+ PathHelper.getInstance().getTestSuiteProjectName() + "\\" 
							+ PathHelper.getInstance().getTestSuiteFileName() + 
							"_amplificationResult.txt";
				printMutationAnalysisResult(outputFilePath);
			}
			
			if (numNewTests > 0) {
				System.out.println("\nPhase (4): Saving new test cases");
				saveAmplifiedTestCases();
			}
		}else {
			System.out.println("As the initial mutation score is 100% there is no need for test amplification");
		}
	}

	private void amplifyTestCases(List<TestDescription> tdlTestCases) {
		List<TestDescription> amplifiedTests = new ArrayList<>();
		for (TestDescription testCase: tdlTestCases) {
			TestDescription copyTdlTestCase = tdlFactory.eINSTANCE.createTestDescription();
			copyTdlTestCase.setName(testCase.getName());
			copyTdlTestCase.setTestConfiguration(testCase.getTestConfiguration());
			copyTdlTestCase.setBehaviourDescription(EcoreUtil.copy(testCase.getBehaviourDescription()));
			
			System.out.println("\nPhase (1): Removing assertions from the test case");
			AssertionRemover assertionRemover = new AssertionRemover();
			assertionRemover.removeAssertionsFromTestCase(testCase);

			List<TestDescription> TMP = new ArrayList<>();
			System.out.println("Phase (2): Modifying test input Data to generate new test cases");
			TDLTestInputDataAmplification IAmplifier = new TDLTestInputDataAmplification(tdlTestSuite);
			TMP.addAll(IAmplifier.generateNewTestsByInputModification(testCase));
			amplifiedTests.addAll(TMP);
			System.out.println("Done: #of generated test cases by input modification = " + TMP.size());
			
			System.out.println("\nPhase (3): Running new tests and generating assertions");
			for (TestDescription newTestCase : TMP) {
				tdlTestSuite.getPackagedElement().add(newTestCase);
				AssertionGenerator generator = new AssertionGenerator();
				boolean result = generator.generateAssertionsForTestCase(newTestCase);
				//check whether the assertion can be generated for the new test case
				if (!result) {
					tdlTestSuite.getPackagedElement().remove(newTestCase);
					amplifiedTests.remove(newTestCase);
				}
				//check whether the new test case improves the mutation score, if there is any mutants
				else if (!scoreCalculator.noMutantsExists){
					boolean improvement = scoreCalculator.testCaseImprovesMutationScore(newTestCase);
					if (!improvement) {
						tdlTestSuite.getPackagedElement().remove(newTestCase);
						amplifiedTests.remove(newTestCase);
					}
				}
			}				
		}
		numNewTests += amplifiedTests.size();
		iteration_ampTests.put(numOfIteration, new ArrayList<>());
		iteration_ampTests.get(numOfIteration).addAll(amplifiedTests);
		numOfIteration++;
	}

	private void saveAmplifiedTestCases() {
		String sourcePath = testSuiteRes.getURI().toString();
		String extension = ".tdlan2";
		if (sourcePath.endsWith(".xmi")) {
			extension = ".xmi";
		}
		
		String outputPath = PathHelper.getInstance().getTestSuiteFileName() + "_amplified" + extension;
		Resource newTestSuiteRes = (new ResourceSetImpl()).createResource(URI.createURI(outputPath));
		//all the new elements are in the testSuiteRes
		newTestSuiteRes.getContents().addAll(EcoreUtil.copyAll(testSuiteRes.getContents()));
		try {
			newTestSuiteRes.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		testSuiteRes.unload();
		newTestSuiteRes.unload();
	}

	private void printMutationAnalysisResult(String outputFilePath) {
		System.out.println("Total number of mutants: " + scoreCalculator.getNumOfMutants());
		System.out.println("- initial number of killed mutants: " + initialNumOfKilledMutants);
		System.out.println("- total number of killed mutants: " + scoreCalculator.getNumOfKilledMutants());
		System.out.println("Total number of test cases improving mutation score: " + numNewTests);
		System.out.println("- initial mutation score : " + (initialMutationScore * 100) + "%");
		System.out.println("- final mutation score : " + (scoreCalculator.getMutationScore() * 100) + "%");
		System.out.println("=> improvement in the mutation score : " + (scoreCalculator.getMutationScore() - initialMutationScore)*100 + "%");
		System.out.println("--------------------------------------------------");
		
		//saving results into a .txt file
		try {
			FileOutputStream fos = new FileOutputStream(outputFilePath);
			PrintStream fileOut = new PrintStream(fos);
			fileOut.println("Total number of mutants: " + scoreCalculator.getNumOfMutants());
			fileOut.println("- initial number of killed mutants: " + initialNumOfKilledMutants);
			fileOut.println("- initial mutation score: " + (initialMutationScore * 100) + "%");
			fileOut.println("Total number of test cases improving mutation score: " + numNewTests);
			fileOut.println("- total number of killed mutants : " + scoreCalculator.getNumOfKilledMutants());
			fileOut.println("- final mutation score : " + (scoreCalculator.getMutationScore() * 100) + "%");
			fileOut.println("=> improvement in the mutation score : " + (scoreCalculator.getMutationScore() - initialMutationScore)*100 +"%");
			fileOut.println("--------------------------------------------------");
			for (int iteration = 1; iteration <= iteration_ampTests.keySet().size(); iteration++) {				
				List<TestDescription> amplifiedTests = iteration_ampTests.get(iteration-1);
				if (amplifiedTests.size()>0) {
					fileOut.println("iteration = " + iteration);
					for (int i=0; i<amplifiedTests.size(); i++) {
						TestDescription amplifiedTest = amplifiedTests.get(i);
						fileOut.println("Amplified Test Case: " + amplifiedTest.getName());
						int j = 1;
						for (String mutant:scoreCalculator.testCase_killedMutant.get(amplifiedTest.getName())) {
							fileOut.println("Killed mutant " + (j++) + ": " + mutant);
						}
						fileOut.println();
					}
				}	
			}
			fileOut.println("--------------------------------------------------");
			Set<String> aliveMutants = scoreCalculator.getAliveMutants();
			if (aliveMutants.size()>0) {
				fileOut.println("List of Alive mutants:");
				int j = 1;
				for (String mutant:aliveMutants) {
					fileOut.println("Alive mutant " + (j++) + ": " + mutant);
				}
			}
			fos.close();
			fileOut.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
