package org.imt.tdl.runner;

import java.util.ArrayList;



import java.util.HashMap;
import java.util.List;

import org.etsi.mts.tdl.Message;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TestDescription;
import org.imt.k3tdl.k3dsa.TestDescriptionAspect;

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
				HashMap<Message, Boolean> verdict = testCaseRunner.verdict(testCase);//result of the test case assertions
				if (verdict.values().contains(false)) {
					result.addTest(testCase.getName(), false);
					result.addNumFailedTests();
					List<Message> failedAssertions = new ArrayList<>();
					for (HashMap.Entry<Message, Boolean> set : verdict.entrySet()) {
					    if (set.getValue().toString().equals(false)) {
					    	failedAssertions.add(set.getKey());
					    }
					}
					result.addFailure(testCase, failedAssertions);
				}
				result.addTest(testCase.getName(), true);
			}
		}
		return result;
	}

}
