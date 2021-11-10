package org.imt.tdl.faultLocalization;

import java.util.ArrayList;
import java.util.Collections;
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
	
	private List<SBFLParameters> elementsSBFLOperands;

	public ModelElementSuspiciousness() {
		this.testSuiteResult = TestResultUtil.getInstance().getTestPackageResult();
		this.errorVector = this.testSuiteResult.getResults();
		this.testSuiteCoverage = TDLCoverageUtil.getInstance().getTestSuiteCoverage();
		this.coverageMatix = this.testSuiteCoverage.coverageInfos;
		//the last row of the matrix contains coverage percentages which is not required here, so should be removed 
		this.coverageMatix.remove(this.coverageMatix.size()-1);
		this.elementsSBFLOperands = new ArrayList<SBFLParameters>();
	}
	
	public void calculateSuspiciousness() {
		for (int i=0; i<coverageMatix.size(); i++) {
			EObject modelElement = coverageMatix.get(i).getModelObject();
			ArrayList<String> elementCoverageStatus = coverageMatix.get(i).getCoverage();
			//if the element is not coverable, remove it from the matrix
			if (elementCoverageStatus.get(elementCoverageStatus.size()-1) == TDLCoverageUtil.NOT_COVERABLE) {
				coverageMatix.remove(i);
			}else {
				SBFLParameters elementSBFLParameters = new SBFLParameters();
				elementSBFLParameters.setModelObject(modelElement);
				elementSBFLParameters.setMetaclass(modelElement.eClass());
				elementSBFLParameters.setNF(this.testSuiteResult.getNumOfFailedTestCases());
				elementSBFLParameters.setNS(this.testSuiteResult.getNumOfPassedTestCases());

				int NCF = 0;//number of failed test cases that cover a coverable model element
				int NUF = 0;//number of failed test cases that do not cover a coverable model element
				int NCS = 0;//number of successful test cases that cover a coverable model element 
				int NUS = 0;//number of successful test cases that do not cover a coverable model element 
				int NC = 0;//total number of test cases that cover a coverable model element
				int NU = 0;//total number of test cases that do not cover a coverable model element 
				for (int j=0; j<elementCoverageStatus.size()-1; j++) {
					elementSBFLParameters.getCoverage().add(elementCoverageStatus.get(j));
					String testCaseName = this.testSuiteCoverage.getTCCoverages().get(j).testCaseName;
					//find the result of the test case
					String testCaseVerdict = "";
					for (TDLTestCaseResult testResult:this.errorVector) {
						if (testResult.getTestCaseName().equals(testCaseName)) {
							testCaseVerdict = testResult.getValue();
							break;
						}
					}
					if (elementCoverageStatus.get(j) == TDLCoverageUtil.COVERED) {
						NC++;
						if (testCaseVerdict == TestResultUtil.PASS) {
							NCS++;
						}else if (testCaseVerdict == TestResultUtil.FAIL) {
							NCF++;
						}
					}else if (elementCoverageStatus.get(j) == TDLCoverageUtil.NOT_COVERED) {
						NU++;
						if (testCaseVerdict == TestResultUtil.PASS) {
							NUS++;
						}else if (testCaseVerdict == TestResultUtil.FAIL) {
							NUF++;
						}
					}
				}
				elementSBFLParameters.setNC(NC);
				elementSBFLParameters.setNCF(NCF);
				elementSBFLParameters.setNCS(NCS);
				elementSBFLParameters.setNU(NU);
				elementSBFLParameters.setNUF(NUF);
				elementSBFLParameters.setNUS(NUS);
				//calculate suspiciousness
				elementSBFLParameters.setSusp(TarantulaTechnique(elementSBFLParameters));
				
				this.elementsSBFLOperands.add(elementSBFLParameters);
			}		
		}
		//calculate rank
		calculateRanks();
	}
	
	private void calculateRanks() {
		List<SBFLParameters> operands = new ArrayList<SBFLParameters>();
		operands.addAll(this.elementsSBFLOperands);
		Collections.sort(operands, Collections.reverseOrder());
		int rank = 1;
		int num = 0;
		for (int i=0; i<operands.size(); i++) {	
			if (i > 0) {
				if (operands.get(i).getSusp() == operands.get(i-1).getSusp()) {
					operands.get(i).setRank(rank);
					num++;
				}else {
					rank = rank + num;
					operands.get(i).setRank(rank);
				}
			}else {
				operands.get(i).setRank(rank);
				num++;
			}
			for (SBFLParameters originalOperands: this.elementsSBFLOperands) {
				if (originalOperands.getMetaclass() == operands.get(i).getMetaclass()
						&& originalOperands.getModelObject() == operands.get(i).getModelObject()) {
					originalOperands.setRank(rank);
					break;
				}
			}
		}
	}

	private double TarantulaTechnique(SBFLParameters operands) {
		if (operands.getNF() == 0) {
			return 0;
		}
		if (operands.getNS() == 0) {
			return 0;
		}
		double denominator = ((operands.getNCF()/operands.getNF())+(operands.getNCS()/operands.getNS()));
		if (denominator == 0) {
			return 0;
		}
		return (operands.getNCF()/operands.getNF())/denominator;
	}
	
	
	public List<SBFLParameters> getElementsSBFLOperands() {
		return elementsSBFLOperands;
	}

	public void setElementsSBFLOperands(List<SBFLParameters> elementsSBFLOperands) {
		this.elementsSBFLOperands = elementsSBFLOperands;
	}
}
