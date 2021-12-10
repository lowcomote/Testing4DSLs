package org.imt.tdl.sbfl.evaluation;

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

public class MutationTestRunner {
	
	private TDLTestSuiteResult testSuiteResult = new TDLTestSuiteResult();
	private TDLTestSuiteCoverage testSuiteCoverage= new TDLTestSuiteCoverage();
	private String DSLPath = "";

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
