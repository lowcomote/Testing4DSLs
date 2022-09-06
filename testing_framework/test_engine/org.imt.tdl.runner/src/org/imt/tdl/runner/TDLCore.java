package org.imt.tdl.runner;

import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TestDescription;
import org.imt.k3tdl.k3dsa.TestDescriptionAspect;
import org.imt.tdl.testResult.TDLTestCaseResult;

public class TDLCore {
	
	//this method is called from WODEL tool
	public Result run(Package testPackage, String artifactPath) {
		Result result = new Result();
		artifactPath = artifactPath.replace("\\", "/");
		System.out.println("\\Model under test: " + artifactPath);
		for (int i=0; i<testPackage.getPackagedElement().size(); i++) {
			if (testPackage.getPackagedElement().get(i) instanceof TestDescription testCase) {
				System.out.println("Test case: " + testCase.getName());
				TestDescriptionAspect.executeTestCase(testCase, artifactPath);
				TDLTestCaseResult verdict = TestDescriptionAspect.testCaseResult(testCase);
				if (verdict.getValue().contains("FAIL")) {
					result.addTest(testCase.getName(), false);
					result.addFailure(verdict);
				}else {
					result.addTest(testCase.getName(), true);
				}
			}
		}
		return result;
	}
}
