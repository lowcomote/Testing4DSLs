package org.imt.tdl.runner;

import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TestDescription;
import org.imt.k3tdl.k3dsa.TestDescriptionAspect;
import org.imt.tdl.testResult.TDLTestCaseResult;

public class TDLCore {
	

	public Result run(Package testPackage, String artifactPath) {
		Result result = new Result();
		artifactPath = artifactPath.replace("\\", "/");
		System.out.println("Model under test: " + artifactPath);
		for (int i=0; i<testPackage.getPackagedElement().size(); i++) {
			Object o = testPackage.getPackagedElement().get(i);
			if (o instanceof TestDescription) {
				TestDescription testCase = (TestDescription) o;
				TestDescriptionAspect testCaseRunner = new TestDescriptionAspect();
				System.out.println("Test case: " + testCase.getName());
				testCaseRunner.executeTestCase(testCase, artifactPath);
				result.addNumExecutedTests();
				TDLTestCaseResult verdict = testCaseRunner.testCaseResult(testCase);
				if (verdict.getValue() == "FAIL") {
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

}
