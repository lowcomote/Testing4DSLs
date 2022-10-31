package org.imt.tdl.sbfl.evaluation;

import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TestDescription;
import org.imt.k3tdl.interpreter.TestDescriptionAspect;
import org.imt.tdl.coverage.TDLCoverageUtil;
import org.imt.tdl.coverage.TDLTestCaseCoverage;
import org.imt.tdl.coverage.TDLTestSuiteCoverage;
import org.imt.tdl.testResult.TDLTestCaseResult;
import org.imt.tdl.testResult.TDLTestResultUtil;
import org.imt.tdl.testResult.TDLTestSuiteResult;
import org.imt.tdl.utilities.PathHelper;

public class MutationTestRunner {
	
	private PathHelper pathHelper;
	
	private TDLTestSuiteResult testSuiteResult;
	private TDLTestSuiteCoverage testSuiteCoverage;

	public MutationTestRunner() {
		testSuiteResult = new TDLTestSuiteResult();
		testSuiteCoverage= new TDLTestSuiteCoverage();
	}
	
	public void runTestAndCalculateCoverage(Package testPackage, String mutantPath) {
		pathHelper = new PathHelper(testPackage);
		pathHelper.findModelAndDSLPathOfTestSuite();
		String dslName = pathHelper.getDSLName();
		
		testSuiteResult.setTestSuite(testPackage);
		
		for (int i=0; i<testPackage.getPackagedElement().size(); i++) {
			if (testPackage.getPackagedElement().get(i) instanceof TestDescription testCase) {
				//for minijava mutants, the mutant must be changed to enable the test case execution ('main' method required)
				String mutantTestPath = mutantPath;
				if (dslName.equals("org.imt.xminijava.Xminijava")) {
					mutantTestPath = mutantPath.substring(0, mutantPath.indexOf(".model")) + "_" + testCase.getName() + ".model";
					(new MiniJavaMutationTestHelper()).addMainClassToMutant(
							pathHelper.getModelUnderTestPath().toString(), mutantPath, mutantTestPath);
				}
				TestDescriptionAspect.executeTestCase(testCase, mutantTestPath);
				TDLTestCaseResult testCaseResult = TestDescriptionAspect.testCaseResult(testCase);
				TDLTestCaseCoverage testCaseCoverage = TestDescriptionAspect.testCaseCoverage(testCase);
				if (testCaseResult.getValue() == TDLTestResultUtil.INCONCLUSIVE) {
					//for minijava models, inconclusive tests can be considered as failed and will be used in the coverage and SBFL
					if (dslName.equals("org.imt.xminijava.Xminijava")) {
						testCaseResult.setValue(TDLTestResultUtil.FAIL);
						testCaseCoverage.setTestCase(testCase);
						testCaseCoverage.setTrace(TestDescriptionAspect.launcher(testCase).getExecutionTrace());
						testCaseCoverage.setMUTResource(TestDescriptionAspect.launcher(testCase).getMUTResource());
						testSuiteResult.addResult(testCaseResult);
					}
				}else {
					testSuiteCoverage.addTCCoverage(testCaseCoverage);
				}
				testSuiteResult.addResult(testCaseResult);
			}
		}
		//keep test result and test coverage for killed mutants
		if (testSuiteResult.getNumOfFailedTestCases() != 0) {
			TDLCoverageUtil.getInstance().setTestSuiteCoverage(testSuiteCoverage);
			pathHelper.findModelAndDSLPathOfTestSuite();
			String DSLPath = pathHelper.getDSLPath().toString();
			if (TDLCoverageUtil.getInstance().getDSLPath() == null || !TDLCoverageUtil.getInstance().getDSLPath().equals(DSLPath)) {
			    TDLCoverageUtil.getInstance().setDSLPath(DSLPath);
			    TDLCoverageUtil.getInstance().runCoverageComputation();
			}
			else {
				testSuiteCoverage.calculateTSCoverage();
			}
		}
	}
	
	public TDLTestSuiteResult getTestSuiteResult() {
		return testSuiteResult;
	}

	public void setTestSuiteResult(TDLTestSuiteResult testSuiteResult) {
		this.testSuiteResult = testSuiteResult;
	}

	public TDLTestSuiteCoverage getTestSuiteCoverage() {
		return testSuiteCoverage;
	}

	public void setTestSuiteCoverage(TDLTestSuiteCoverage testSuiteCoverage) {
		this.testSuiteCoverage = testSuiteCoverage;
	}
}
