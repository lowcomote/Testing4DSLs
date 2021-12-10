package org.imt.tdl.sbfl.evaluation;

import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TestDescription;
import org.imt.k3tdl.k3dsa.TestConfigurationAspect;
import org.imt.k3tdl.k3dsa.TestDescriptionAspect;
import org.imt.tdl.coverage.TDLCoverageUtil;
import org.imt.tdl.coverage.TDLTestCaseCoverage;
import org.imt.tdl.coverage.TDLTestSuiteCoverage;
import org.imt.tdl.testResult.TDLTestCaseResult;
import org.imt.tdl.testResult.TDLTestSuiteResult;

public class MutationTestRunner {
	
	private TDLTestSuiteResult testSuiteResult;
	private TDLTestSuiteCoverage testSuiteCoverage;
	private String DSLPath = "";

	public MutationTestRunner() {
		this.testSuiteResult = new TDLTestSuiteResult();
		this.testSuiteCoverage= new TDLTestSuiteCoverage();
	}
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
		}
		//keep test result and test coverage for killed mutants
		if (this.testSuiteResult.getNumOfFailedTestCases() != 0) {
			TDLCoverageUtil.getInstance().setTestSuiteCoverage(this.testSuiteCoverage);
			if (TDLCoverageUtil.getInstance().getDSLPath() == null || !TDLCoverageUtil.getInstance().getDSLPath().equals(this.DSLPath)) {
			    TDLCoverageUtil.getInstance().setDSLPath(this.DSLPath);
			    TDLCoverageUtil.getInstance().runCoverageComputation();
			}
			else {
				this.testSuiteCoverage.calculateTSCoverage();
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

	public String getDSLPath() {
		return DSLPath;
	}

	public void setDSLPath(String dSLPath) {
		DSLPath = dSLPath;
	}
}
