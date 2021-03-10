package org.imt.tdl.runner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.etsi.mts.tdl.Message;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.TestDescription;
import org.imt.k3tdl.k3dsa.TestDescriptionAspect;

public class TDLCore {
	private Result result = new Result();
	
	public Result run(Package testPackage, String MUTPath) {
		for (int i=0; i<testPackage.getPackagedElement().size(); i++) {
			Object o = testPackage.getPackagedElement().get(i);
			if (o instanceof TestDescription) {
				TestDescription testCase = (TestDescription) o;
				result.addTest(testCase);
				TestDescriptionAspect testCaseRunner = new TestDescriptionAspect();
				testCaseRunner.executeTestCase(testCase, MUTPath);
				result.addNumExecutedTests();
				HashMap<Message, String> verdict = testCaseRunner.verdict(testCase);//result of the test case assertions
				if (verdict.values().contains("FAIL")) {
					result.addNumFailedTests();
					List<Message> failedAssertions = new ArrayList<>();
					for (HashMap.Entry<Message, String> set : verdict.entrySet()) {
					    if (set.getValue().toString().equals("FAIL")) {
					    	failedAssertions.add(set.getKey());
					    }
					}
					result.addFailure(testCase, failedAssertions);
				}	
			}
		}
		return result;
	}
}
