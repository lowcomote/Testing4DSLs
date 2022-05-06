package org.imt.tdl.amplification.evaluation;

import java.io.IOException;
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
import org.imt.tdl.mutation.MutationRuntimeException;
import org.imt.tdl.mutation.MutationScoreCalculator;
import org.imt.tdl.mutation.utilities.PathHelper;
import org.imt.tdl.testResult.TDLTestResultUtil;

/*
 * This class is an implementation of our evaluation for MODELS2022 paper
 * The evaluation protocol is as follows:
 * 1. Running the original test suite on the mutants
 * 2. Removing test cases (if needed) to reach mutation score between 40% and 80%
 * 3. Running the amplification on the selected test cases
 */
public class Evaluation4MODELSPaper {

	Resource testSuiteRes;
	Package tdlTestSuite;
	List<TestDescription> testCases = new ArrayList<>();
	
	MutationScoreCalculator scoreCalculator;
	double mutationScore ;
	
	public void selectTestsForEvaluation(IFile testSuiteFile) throws MutationRuntimeException {
		PathHelper.getInstance().setTestSuiteFile(testSuiteFile);
		testSuiteRes = PathHelper.getInstance().getTestSuiteResource();
		tdlTestSuite = PathHelper.getInstance().getTestSuite();
		
		//calculating the mutation score of the manually-written test suite (i.e., the input test suite)
		scoreCalculator = new MutationScoreCalculator(tdlTestSuite);
		if (scoreCalculator.noMutantsExists) {
			String message = "Amplification Stopped: There is no available mutant";
			System.out.println(message);
			throw (new MutationRuntimeException(message));
		}
		String result = scoreCalculator.runTestSuiteOnOriginalModel();
		if (result == TDLTestResultUtil.FAIL) {
			String message = "Amplification Stopped: The manually-written test suite is failed on the original model under test.";
			System.out.println(message);
			throw (new MutationRuntimeException(message));
		}
		
		System.out.println("\nCalculating the mutation score of the input test suite");
		testCases = tdlTestSuite.getPackagedElement().stream().filter(p -> p instanceof TestDescription).
				map(p -> (TestDescription) p).collect(Collectors.toList());
		
		//running all tests on all mutants
		testCases.forEach(t -> scoreCalculator.runTestCaseOnAllMutants(t));
		scoreCalculator.calculateOverallMutationScore();
		mutationScore = scoreCalculator.getOverallMutationScore();
		//if the mutation score is less than 40%, it is not a good input for the experiment
		if (mutationScore < 0.4) {
			String message = "Amplification Stopped: The mutation score of the input test suite must be higher than 40%";
			System.out.println(message);
			throw (new MutationRuntimeException(message));
		}
		if (mutationScore > 0.8) {
			makeTestSuiteWeaker();
			saveSelectedTestCases();
		}
	}
	
	private void makeTestSuiteWeaker() throws MutationRuntimeException {
		int index = 0;
		while (index <testCases.size() && !(mutationScore >= 0.4 && mutationScore <= 0.8)) {
			TestDescription testCase = testCases.get(index);
			List<String> mutantsKilledByTestCase = scoreCalculator.testCase_killedMutant.get(testCase.getName());
			int numOfMutantsNotKilledByOtherTests = mutantsKilledByTestCase.stream().filter(m -> mutantNotKilledByOtherTests(testCase, m)).toList().size();
			if (numOfMutantsNotKilledByOtherTests == 0) {
				//no change in the mutation score
				tdlTestSuite.getPackagedElement().remove(testCase);
			}
			else if (numOfMutantsNotKilledByOtherTests > 0) {
				int numOfKilledMutants = scoreCalculator.getNumOfKilledMutants() - numOfMutantsNotKilledByOtherTests;
				double newScore = (double) (numOfKilledMutants) / scoreCalculator.getNumOfMutants();
				//remove the test case if the new score is not lower than 40%
				if (newScore >= 0.4) {
					tdlTestSuite.getPackagedElement().remove(testCase);
					scoreCalculator.setNumOfKilledMutants(numOfKilledMutants);
					scoreCalculator.calculateOverallMutationScore();
					mutationScore = scoreCalculator.getOverallMutationScore();
				}
			}
			index++;
		}
		if (mutationScore < 0.4 || mutationScore > 0.8) {
			String message = "Amplification Stopped: It is not possible to make test suite weaker with the current threshold (between 40% and 80%)";
			System.out.println(message);
			throw (new MutationRuntimeException(message));
		}
	}

	private boolean mutantNotKilledByOtherTests(TestDescription testCase, String mutant) {
		List<TestDescription> otherTests = testCases.stream().
				filter(t -> !EcoreUtil.equals(t, testCase)).toList();
		for (TestDescription otherTest : otherTests) {
			if (scoreCalculator.testCase_killedMutant.get(otherTest.getName()).contains(mutant)) {
				return false;
			}
		}
		return true;
	}

	private void saveSelectedTestCases() {
		String sourcePath = testSuiteRes.getURI().toString();
		String extension = ".tdlan2";
		if (sourcePath.endsWith(".xmi")) {
			extension = ".xmi";
		}
		
		String outputPath = sourcePath.substring(0, sourcePath.lastIndexOf("/")+1) + PathHelper.getInstance().getTestSuiteFileName() + "_selected" + extension;
		Resource weakTestSuiteRes = (new ResourceSetImpl()).createResource(URI.createURI(outputPath));
		//all the new elements are in the testSuiteRes
		weakTestSuiteRes.getContents().addAll(EcoreUtil.copyAll(testSuiteRes.getContents()));
		try {
			weakTestSuiteRes.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		testSuiteRes.unload();
		weakTestSuiteRes.unload();
	}
}
