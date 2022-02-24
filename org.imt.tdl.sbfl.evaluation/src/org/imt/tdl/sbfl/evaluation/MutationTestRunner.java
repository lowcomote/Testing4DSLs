package org.imt.tdl.sbfl.evaluation;

import org.etsi.mts.tdl.Annotation;
import org.etsi.mts.tdl.ComponentInstance;
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
	
	private TDLTestSuiteResult testSuiteResult;
	private TDLTestSuiteCoverage testSuiteCoverage;
	private String DSLPath = "";

	public MutationTestRunner() {
		this.testSuiteResult = new TDLTestSuiteResult();
		this.testSuiteCoverage= new TDLTestSuiteCoverage();
	}
	public void runTestAndCalculateCoverage(Package testPackage, String mutantPath) {
		mutantPath = mutantPath.replace("\\", "/");
		this.testSuiteResult.setTestSuite(testPackage);
		for (int i=0; i<testPackage.getPackagedElement().size(); i++) {
			Object o = testPackage.getPackagedElement().get(i);
			if (o instanceof TestDescription) {
				TestDescription testCase = (TestDescription) o;
				String dslName = getDSLName(testCase);
				//for minijava mutants, the mutant must be changed to enable the test case execution ('main' method required)
				String mutantTestPath = mutantPath;
				if (dslName.equals("org.imt.xminijava.Xminijava")) {
					mutantTestPath = mutantPath.substring(0, mutantPath.indexOf(".model")) + "_" + testCase.getName() + ".model";
					(new MiniJavaMutationTestHelper()).addMainClassToMutant(mutantPath, testCase, mutantTestPath);
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
						this.testSuiteResult.addResult(testCaseResult);
					}
				}else {
					this.testSuiteCoverage.addTCCoverage(testCaseCoverage);
				}
				this.testSuiteResult.addResult(testCaseResult);
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
	
	private String getDSLName(TestDescription testCase) {
		ComponentInstance sutComponent = testCase.getTestConfiguration().getComponentInstance().stream().
				filter(ci -> ci.getRole().toString().equals("SUT")).findFirst().get();
		for (Annotation a:sutComponent.getAnnotation()){
			if (a.getKey().getName().equals("DSLName")){
				return a.getValue().substring(1, a.getValue().length()-1);
			}
		}
		return null;
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
