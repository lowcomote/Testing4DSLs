package org.imt.tdl.runner;

import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TestDescription;
import org.imt.k3tdl.k3dsa.TestConfigurationAspect;
import org.imt.k3tdl.k3dsa.TestDescriptionAspect;
import org.imt.tdl.coverage.TDLCoverageUtil;
import org.imt.tdl.coverage.TDLTestCaseCoverage;
import org.imt.tdl.coverage.TDLTestSuiteCoverage;
import org.imt.tdl.testResult.TDLTestCaseResult;
import org.imt.tdl.testResult.TDLTestResultUtil;
import org.imt.tdl.testResult.TDLTestSuiteResult;

public class TDLCore {

	TDLTestSuiteResult testSuiteResult = new TDLTestSuiteResult();
	TDLTestSuiteCoverage testSuiteCoverage = new TDLTestSuiteCoverage();
	String DSLPath = "";
	
	public void runTestAndCalculateCoverage(Package testPackage, String artifactPath) {
		artifactPath = artifactPath.replace("\\", "/");
		for (int i=0; i<testPackage.getPackagedElement().size(); i++) {
			Object o = testPackage.getPackagedElement().get(i);
			if (o instanceof TestDescription) {
				TestDescription testCase = (TestDescription) o;
				TestDescriptionAspect.executeTestCase(testCase, artifactPath);
				TDLTestCaseResult testCaseResult = TestDescriptionAspect.testCaseResult(testCase);
				TDLTestCaseCoverage testCaseCoverage = TestDescriptionAspect.testCaseCoverage(testCase);
				this.testSuiteResult.addResult(testCaseResult);
				this.testSuiteCoverage.addTCCoverage(testCaseCoverage);
				if (this.DSLPath == "") {
					this.DSLPath = TestConfigurationAspect.DSLPath(testCase.getTestConfiguration());
				}
			}
			TDLTestResultUtil.getInstance().setTestSuiteResult(this.testSuiteResult);
		    TDLCoverageUtil.getInstance().setTestSuiteCoverage(this.testSuiteCoverage);
		    TDLCoverageUtil.getInstance().setDSLPath(this.DSLPath);
		    TDLCoverageUtil.getInstance().runCoverageComputation();
		}
	}
	
	//this method is called from WODEL tool
	public Result run(Package testPackage, String artifactPath) {
		Result result = new Result();
		artifactPath = artifactPath.replace("\\", "/");
		System.out.println("\\Model under test: " + artifactPath);
		for (int i=0; i<testPackage.getPackagedElement().size(); i++) {
			Object o = testPackage.getPackagedElement().get(i);
			if (o instanceof TestDescription) {
				TestDescription testCase = (TestDescription) o;
				System.out.println("Test case: " + testCase.getName());
				TestDescriptionAspect.executeTestCase(testCase, artifactPath);
				result.addNumExecutedTests();
				TDLTestCaseResult verdict = TestDescriptionAspect.testCaseResult(testCase);
				if (verdict.getValue().contains("FAIL")) {
					result.addTest(testCase.getName(), false);
					result.addNumFailedTests();
					result.addFailure(testCase);
				}else {
					result.addTest(testCase.getName(), true);
				}
			}
		}
		return result;
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
