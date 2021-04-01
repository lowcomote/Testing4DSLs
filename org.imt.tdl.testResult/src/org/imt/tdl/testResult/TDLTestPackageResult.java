package org.imt.tdl.testResult;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

public class TDLTestPackageResult {
	
	private String testPackageName;
	
	private List<TDLTestCaseResult> results = new ArrayList<>();
	
	public String getTestPackageName() {
		return testPackageName;
	}
	public void setTestPackageName(String name) {
		this.testPackageName = name;
	}
	public void addResult(TDLTestCaseResult result) {
		this.results.add(result);
	}
	public List<TDLTestCaseResult> getResults() {
		return results;
	}
	
	public int getNumOfFailedTestCases() {
		int numFailedTests = 0;
		for (TDLTestCaseResult result : results) {
			if (result.getValue().equals("FAIL")) {
				numFailedTests++;
			}
		}
		return numFailedTests;
	}
	public int getNumOfInconclusiveTestCases() {
		int num = 0;
		for (TDLTestCaseResult result : results) {
			if (result.getValue().equals("INCONCLUSIVE")) {
				num++;
			}
		}
		return num;
	}
}
