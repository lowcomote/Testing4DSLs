package org.imt.tdl.amplification.evaluation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TestDescription;
import org.imt.tdl.amplification.TDLTestAmplifier;
import org.imt.tdl.amplification.mutation.MutationRuntimeException;
import org.imt.tdl.amplification.mutation.MutationScoreCalculator;
import org.imt.tdl.amplification.utilities.PathHelper;
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
	
	MutationScoreCalculator scoreCalculator;
	int initialNumOfKilledMutants;
	double initialMutationScore;
	
	int numOfIteration;
	int numNewTests;
	Map<Integer, List<TestDescription>> iteration_ampTests = new HashMap<>();
	
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
		
		List<TestDescription> testCases = new ArrayList<>();
		testCases = tdlTestSuite.getPackagedElement().stream().filter(p -> p instanceof TestDescription).
				map(p -> (TestDescription) p).collect(Collectors.toList());
		
		int index = -1;
		double mutationScore = 0;
		
		for (int i=0; i<testCases.size(); i++) {
			scoreCalculator.runTestCaseOnAliveMutants(testCases.get(i));
			scoreCalculator.calculateOverallMutationScore();
			mutationScore = scoreCalculator.getOverallMutationScore();
			if (mutationScore > 0.8) {
				index = i;
				break;
			}
		}
		IFile weakTestsFile = null;
		//if the mutation score is less than 40%, it is not a good input for the experiment
		if (mutationScore < 0.4) {
			String message = "Amplification Stopped: The mutation score of the input test suite must be higher than 40%";
			System.out.println(message);
			throw (new MutationRuntimeException(message));
		}
		//if the mutation score is between 40% and 80%, there is no need to remove test cases
		else if (mutationScore >= 0.4 && mutationScore <= 0.8) {
			weakTestsFile = testSuiteFile;
		}
		else if (mutationScore > 0.8) {
			//remove the test cases from the index to the end
			for (int i=index; i<testCases.size(); i++) {
				tdlTestSuite.getPackagedElement().remove(testCases.get(index));
			}
			weakTestsFile = saveSelectedTestCases();
		}

		//send it to the amplifier
		TDLTestAmplifier testAmplifier = new TDLTestAmplifier();
		testAmplifier.amplifyTestSuite(weakTestsFile);
	}
	
	private IFile saveSelectedTestCases() {
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
		
		File weakTestsFile = new File (outputPath);
		return (IFile) weakTestsFile;
	}
}
