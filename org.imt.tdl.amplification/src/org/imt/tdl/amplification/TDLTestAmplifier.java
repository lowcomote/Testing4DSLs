package org.imt.tdl.amplification;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
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
	
	MutationScoreCalculator scoreCalculator;
	int initialNumOfKilledMutants;
	double initialMutationScore;
	
	int numNewTests;
	
	public void amplifyTestSuite(IFile testSuiteFile) {
		PathHelper.getInstance().setTestSuiteFile(testSuiteFile);
		testSuiteRes = PathHelper.getInstance().getTestSuiteResource();
		Package tdlTestSuite = PathHelper.getInstance().getTestSuite();
		
		//calculating the mutation score of the manually-written test suite (i.e., the input test suite)
		scoreCalculator = new MutationScoreCalculator(tdlTestSuite);
		initialNumOfKilledMutants = 0;
		initialMutationScore = 0;
		if (!scoreCalculator.noMutantsExists) {
			initialMutationScore = scoreCalculator.calculateInitialMutationScore();
			initialNumOfKilledMutants = scoreCalculator.getNumOfKilledMutants();
		}
		
		List<TestDescription> tdlTestCases = tdlTestSuite.getPackagedElement().stream().
				filter(p -> p instanceof TestDescription).map(t -> (TestDescription) t).collect(Collectors.toList());
		List<TestDescription> newTdlTestCases = new ArrayList<>();
		numNewTests = 0;
		
		for (TestDescription testCase: tdlTestCases) {
			TestDescription copyTdlTestCase = tdlFactory.eINSTANCE.createTestDescription();
			copyTdlTestCase.setName(testCase.getName());
			copyTdlTestCase.setTestConfiguration(testCase.getTestConfiguration());
			copyTdlTestCase.setBehaviourDescription(EcoreUtil.copy(testCase.getBehaviourDescription()));
			
			System.out.println("\nPhase (1): Removing assertions from the test case");
			AssertionRemover assertionRemover = new AssertionRemover();
			assertionRemover.removeAssertionsFromTestCase(copyTdlTestCase);
			
			System.out.println("Phase (2): Modifying test input Data to generate new test cases");
			TDLTestInputDataAmplification mutator = new TDLTestInputDataAmplification(tdlTestSuite, copyTdlTestCase);
			newTdlTestCases = mutator.generateNewTestsByInputModification();
			System.out.println("Done: #of generated test cases by input modification = " + newTdlTestCases.size());
			
			System.out.println("\nPhase (3): Running new tests and generating assertions");
			int i = newTdlTestCases.size();
			for (TestDescription newTestCase: newTdlTestCases) {
				tdlTestSuite.getPackagedElement().add(newTestCase);
				AssertionGenerator generator = new AssertionGenerator();
				boolean result = generator.generateAssertionsForTestCase(newTestCase);
				//check whether the assertion can be generated for the new test case
				if (!result) {
					tdlTestSuite.getPackagedElement().remove(newTestCase);
					i--;
				}
				//check whether the new test case improves the mutation score, if there is any mutants
				else if (!scoreCalculator.noMutantsExists){
					boolean improvement = scoreCalculator.testCaseImprovesMutationScore(newTestCase);
					if (!improvement) {
						tdlTestSuite.getPackagedElement().remove(newTestCase);
						i--;
					}
				}
			}
			System.out.println("Done: #of valid generated test cases = " + i);
			numNewTests += i;
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
		System.out.println("- initial number of killed mutants: " + initialMutationScore);
		System.out.println("- total number of killed mutants: " + scoreCalculator.getNumOfKilledMutants());
		System.out.println("Total number of test cases improving mutation score: " + numNewTests);
		System.out.println("- initial mutation score : " + initialMutationScore);
		System.out.println("- final mutation score : " + scoreCalculator.getMutationScore());
		System.out.println("=> improvement in the mutation score : " + (scoreCalculator.getMutationScore() - initialMutationScore));
		System.out.println("--------------------------------------------------");
		
		//saving results into a .txt file
		try {
			FileOutputStream fos = new FileOutputStream(outputFilePath);
			PrintStream fileOut = new PrintStream(fos);
			//System.setOut(fileOut);
			fileOut.println("Total number of mutants: " + scoreCalculator.getNumOfMutants());
			fileOut.println("- initial number of killed mutants: " + initialMutationScore);
			fileOut.println("- total number of killed mutants: " + scoreCalculator.getNumOfKilledMutants());
			fileOut.println("Total number of test cases improving mutation score: " + numNewTests);
			fileOut.println("- initial mutation score : " + initialMutationScore);
			fileOut.println("- final mutation score : " + scoreCalculator.getMutationScore());
			fileOut.println("=> improvement in the mutation score : " + (scoreCalculator.getMutationScore() - initialMutationScore));
			fileOut.println("--------------------------------------------------");
			for (String testCase:scoreCalculator.testCase_killedMutant.keySet()) {
				fileOut.println("Test case: " + testCase);
				int i = 0;
				for (String mutant:scoreCalculator.testCase_killedMutant.get(testCase)) {
					fileOut.println("Killed mutant " + (i++) + ": " + mutant);
				}
				fileOut.println();
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
