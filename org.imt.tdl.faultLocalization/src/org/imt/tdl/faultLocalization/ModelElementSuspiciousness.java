package org.imt.tdl.faultLocalization;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.imt.tdl.coverage.TDLCoverageUtil;
import org.imt.tdl.coverage.TDLTestSuiteCoverage;
import org.imt.tdl.coverage.TestCoverageInfo;
import org.imt.tdl.testResult.TDLTestCaseResult;
import org.imt.tdl.testResult.TDLTestPackageResult;
import org.imt.tdl.testResult.TestResultUtil;

public class ModelElementSuspiciousness {
	
	private final TDLTestPackageResult testSuiteResult;
	private final List<TDLTestCaseResult> errorVector;
	private final TDLTestSuiteCoverage testSuiteCoverage;
	private final List<TestCoverageInfo> coverageMatix;
	
	public ModelElementSuspiciousness() {
		this.testSuiteResult = TestResultUtil.getInstance().getTestPackageResult();
		this.errorVector = this.testSuiteResult.getResults();
		this.testSuiteCoverage = TDLCoverageUtil.getInstance().getTestSuiteCoverage();
		this.coverageMatix = this.testSuiteCoverage.coverageInfos;
		//the last row of the matrix contains coverage percentages which is not required here, so should be removed 
		this.coverageMatix.remove(this.coverageMatix.size()-1);
	}
	
	private void calculateSuspiciousness() {
		for (int i=0; i<coverageMatix.size(); i++) {
			EObject modelElement = coverageMatix.get(i).getModelObject();
			ArrayList<String> elementCoverageStatus = coverageMatix.get(i).getCoverage();
			//if the element is not coverable, remove it from the matrix
			if (elementCoverageStatus.get(elementCoverageStatus.size()-1) == TDLCoverageUtil.NOT_COVERABLE) {
				coverageMatix.remove(i);
			}else {
				SBFLOperands operands4modelElement = new SBFLOperands();
				operands4modelElement.setModelObject(modelElement);
				operands4modelElement.setMetaclass(modelElement.eClass());
				operands4modelElement.setNF(this.testSuiteResult.getNumOfFailedTestCases());
				operands4modelElement.setNS(this.testSuiteResult.getNumOfPassedTestCases());
			}		
		}
	}
	
	private int calculateNCF(int index) {
		return 0;
	}
}
