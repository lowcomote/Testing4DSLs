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
	
	private List<SBFLMeasures> elementsSBFLMeasures = new ArrayList<SBFLMeasures>();

	public static final String OCHIAI = "ochiai";
	public static final String TARANTULA = "tarantula";
	public static final String OCHIAI2 = "ochiai2";
	public static final String BRAUNBANQUET = "braunbanquet";
	public static final String MOUNTFORD = "mountford";
	public static final String ARITHMETICMEAN = "arithmeticmean";
	public static final String ZOLTAR = "zoltar";
	public static final String SIMPLEMATCHING = "simplematching";
	public static final String RUSSELRAO = "russelrao";
	public static final String KULCYNSKI2 = "kulcynski2";
	public static final String COHEN = "cohen";
	public static final String PIERCE = "pierce";
	public static final String BARONIETAL = "baronietal";
	public static final String PHI = "phi";
	public static final String ROGERSTANIMOTO = "rogerstanimoto";
	public static final String OP2 = "op2";
	public static final String BARINEL = "barinel";
	public static final String DSTAR = "DStar";
	public static final String CONFIDENCE = "confidence";
	
	public ModelElementSuspiciousness() {
		this.testSuiteResult = TestResultUtil.getInstance().getTestPackageResult();
		this.errorVector = this.testSuiteResult.getResults();
		this.testSuiteCoverage = TDLCoverageUtil.getInstance().getTestSuiteCoverage();
		this.coverageMatix = this.testSuiteCoverage.coverageInfos;
		//the last row of the matrix contains coverage percentages which is not required here, so should be removed 
		this.coverageMatix.remove(this.coverageMatix.size()-1);
		this.elementsSBFLMeasures.clear();
	}
	
	public void calculateMeasures() {
		for (int i=0; i<coverageMatix.size(); i++) {
			EObject modelElement = coverageMatix.get(i).getModelObject();
			ArrayList<String> elementCoverageStatus = coverageMatix.get(i).getCoverage();
			//if the element is not coverable, remove it from the matrix
			if (elementCoverageStatus.get(elementCoverageStatus.size()-1) == TDLCoverageUtil.NOT_COVERABLE) {
				coverageMatix.remove(i);
			}else {
				SBFLMeasures elementSBFLMeasures = new SBFLMeasures();
				elementSBFLMeasures.setModelObject(modelElement);
				elementSBFLMeasures.setMetaclass(modelElement.eClass());
				elementSBFLMeasures.setNF(this.testSuiteResult.getNumOfFailedTestCases());
				elementSBFLMeasures.setNS(this.testSuiteResult.getNumOfPassedTestCases());

				int NCF = 0;//number of failed test cases that cover a coverable model element
				int NUF = 0;//number of failed test cases that do not cover a coverable model element
				int NCS = 0;//number of successful test cases that cover a coverable model element 
				int NUS = 0;//number of successful test cases that do not cover a coverable model element 
				int NC = 0;//total number of test cases that cover a coverable model element
				int NU = 0;//total number of test cases that do not cover a coverable model element 
				for (int j=0; j<elementCoverageStatus.size()-1; j++) {
					elementSBFLMeasures.getCoverage().add(elementCoverageStatus.get(j));
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
				elementSBFLMeasures.setNC(NC);
				elementSBFLMeasures.setNCF(NCF);
				elementSBFLMeasures.setNCS(NCS);
				elementSBFLMeasures.setNU(NU);
				elementSBFLMeasures.setNUF(NUF);
				elementSBFLMeasures.setNUS(NUS);
				this.elementsSBFLMeasures.add(elementSBFLMeasures);
			}		
		}
	}
	
	//reference: https://github.com/javitroya/SBFL_MT/blob/6f623acab0d4b673314feca58d72cac705dc0967/Spectrum-based_FaultLoc_MT/SpecBased_FaultLoc_MT/src/es/us/eii/fault/localization/mt/main/FaultLocalizationMT_Main.java#L1031
	public double getSuspiciousness(SBFLMeasures elementMeasures, String technique){
		Double susp = 0.0;
		if (technique.equals(ModelElementSuspiciousness.OCHIAI)){
			if (elementMeasures.getNCF() == 0.0){
				susp = 0.0;
			} else if (Math.sqrt(elementMeasures.getNF()*(elementMeasures.getNCF()+elementMeasures.getNCS()))==0.0){
				susp = 1.0;
			} else {
				susp = (double) elementMeasures.getNCF() / 
						Math.sqrt(elementMeasures.getNF()*(elementMeasures.getNCF()+elementMeasures.getNCS()));
			}
		}else if (technique.equals(ModelElementSuspiciousness.TARANTULA)){
			/*According to Empirical Evaluation of the Tarantula Automatic FaultLocalization Technique,
			* If any of the denominators evaluates to zero, we assign zero to that fraction. */
			if (elementMeasures.getNF()==0 || elementMeasures.getNCS() == 0.0 && elementMeasures.getNCF()==0.0){
				susp = 0.0;
			}else if (elementMeasures.getNS() == 0.0){
				susp = (((double)elementMeasures.getNCF()/elementMeasures.getNF())  / 
						((double)elementMeasures.getNCF()/elementMeasures.getNF()));
			} else {
				susp = (((double)elementMeasures.getNCF()/elementMeasures.getNF())  / 
						(((double)elementMeasures.getNCF()/elementMeasures.getNF()) + 
						 ((double)elementMeasures.getNCS()/elementMeasures.getNS())));
			}
		} else if (technique.equals(ModelElementSuspiciousness.OCHIAI2)){
			if (elementMeasures.getNCF() * elementMeasures.getNUS() == 0.0){
				susp = 0.0;
			} else if (Math.sqrt((elementMeasures.getNCF() + elementMeasures.getNCS()) * 
					(elementMeasures.getNUS() + elementMeasures.getNUF()) * 
					(elementMeasures.getNCF() + elementMeasures.getNUF()) *
					(elementMeasures.getNCS() + elementMeasures.getNUS()))==0.0){
				susp = 1.0;
			} else {
				susp = (double)(elementMeasures.getNCF() * elementMeasures.getNUS()) / 
						(0.1 + Math.sqrt((elementMeasures.getNCF() + elementMeasures.getNCS()) * 
						(elementMeasures.getNUS() + elementMeasures.getNUF()) * 
						(elementMeasures.getNCF() + elementMeasures.getNUF()) *
						(elementMeasures.getNCS() + elementMeasures.getNUS())));
			}
		} else if (technique.equals(ModelElementSuspiciousness.BRAUNBANQUET)){
			susp =  ((double) elementMeasures.getNCF() / 
					Math.max(elementMeasures.getNCF() + elementMeasures.getNCS(), 
							elementMeasures.getNCF() + elementMeasures.getNUF()));
		} else if (technique.equals(ModelElementSuspiciousness.MOUNTFORD)){
			if (elementMeasures.getNCF() == 0.0){
				susp = 0.0;
			} else if (0.5 * ((elementMeasures.getNCF()*elementMeasures.getNCS())+
					(elementMeasures.getNCF()*elementMeasures.getNUF())) + 
					(elementMeasures.getNCS() * elementMeasures.getNUF())==0.0){
				susp = 1.0;
			} else {
				susp = (double) elementMeasures.getNCF() / (0.5 * ((elementMeasures.getNCF()*elementMeasures.getNCS())+
						(elementMeasures.getNCF()*elementMeasures.getNUF())) + 
						(elementMeasures.getNCS() * elementMeasures.getNUF()));
			}
		} else if (technique.equals(ModelElementSuspiciousness.ARITHMETICMEAN)){
			if (2*((elementMeasures.getNCF()*elementMeasures.getNUS())-
					(elementMeasures.getNUF()*elementMeasures.getNCS())) == 0.0){
				susp = 0.0;
			} else if ((elementMeasures.getNCF()+elementMeasures.getNCS())*
					(elementMeasures.getNUS()+elementMeasures.getNUF())+
					(elementMeasures.getNCF()+elementMeasures.getNUF())*
					(elementMeasures.getNCS()+elementMeasures.getNUS())==0.0){
				susp = 1.0;
			} else {
				susp = (double) (2*((elementMeasures.getNCF()*elementMeasures.getNUS())-
						(elementMeasures.getNUF()*elementMeasures.getNCS()))/
								((elementMeasures.getNCF()+elementMeasures.getNCS())*
								(elementMeasures.getNUS()+elementMeasures.getNUF())+
								(elementMeasures.getNCF()+elementMeasures.getNUF())*
								(elementMeasures.getNCS()+elementMeasures.getNUS())));					
			}
		} else if (technique.equals(ModelElementSuspiciousness.ZOLTAR)){
			if (elementMeasures.getNCF() == 0.0){
				susp = 0.0;
			} else if (elementMeasures.getNCF()+elementMeasures.getNUF()+elementMeasures.getNCS()+
					((double)(10000*elementMeasures.getNUF()*elementMeasures.getNCS())/elementMeasures.getNCF())==0.0){
				susp = 1.0;
			} else {
				susp =  ((double)elementMeasures.getNCF() / 
						(elementMeasures.getNCF()+elementMeasures.getNUF()+elementMeasures.getNCS()+
								((double)(10000*elementMeasures.getNUF()*elementMeasures.getNCS())/elementMeasures.getNCF())));
			}
		} 
		else if (technique.equals(ModelElementSuspiciousness.SIMPLEMATCHING)){
			susp =  ((double)(elementMeasures.getNCF()+elementMeasures.getNUS())/
					(elementMeasures.getNCF()+elementMeasures.getNCS()+elementMeasures.getNUS()+elementMeasures.getNUF()));
		} 
		else if (technique.equals(ModelElementSuspiciousness.RUSSELRAO)){
			susp =  ((double) elementMeasures.getNCF() / 
					(elementMeasures.getNCF()+elementMeasures.getNUF()+elementMeasures.getNCS()+elementMeasures.getNUS()));
		} 
		else if (technique.equals(ModelElementSuspiciousness.KULCYNSKI2)){
			if (elementMeasures.getNCF() == 0.0){
				susp = 0.0;
			} else if (elementMeasures.getNCF()+elementMeasures.getNUF() ==0.0 || elementMeasures.getNCF()+elementMeasures.getNCS()==0.0){
				susp = 1.0;
			} else {
				susp = 0.5 * (((double)elementMeasures.getNCF()/(elementMeasures.getNCF()+elementMeasures.getNUF()))+
						 ((double)elementMeasures.getNCF()/(elementMeasures.getNCF()+elementMeasures.getNCS())));
			}				
		} 
		else if (technique.equals(ModelElementSuspiciousness.COHEN)){
			if (2*elementMeasures.getNCF()*elementMeasures.getNUS()-
					2*elementMeasures.getNUF()*elementMeasures.getNCS() == 0.0){
				susp = 0.0;
			} else if ((elementMeasures.getNCF()+elementMeasures.getNCS())*
					(elementMeasures.getNUS()+elementMeasures.getNCS())+
					(elementMeasures.getNCF()+elementMeasures.getNUF())*
					(elementMeasures.getNUF()+elementMeasures.getNUS()) ==0.0){
				susp = 1.0;
			} else {
				susp =  ((double)(2*elementMeasures.getNCF()*elementMeasures.getNUS()-
						2*elementMeasures.getNUF()*elementMeasures.getNCS())/
						((elementMeasures.getNCF()+elementMeasures.getNCS())*
								(elementMeasures.getNUS()+elementMeasures.getNCS())+
								(elementMeasures.getNCF()+elementMeasures.getNUF())*
								(elementMeasures.getNUF()+elementMeasures.getNUS())));
			}
		} 
		else if (technique.equals(ModelElementSuspiciousness.PIERCE)) {
			if (elementMeasures.getNCF() * elementMeasures.getNUF() + 
					elementMeasures.getNUF() * elementMeasures.getNCS() == 0.0){
				susp = 0.0;
			} else if (elementMeasures.getNCF() * elementMeasures.getNUF() + 
					2*elementMeasures.getNUF() * elementMeasures.getNUS() + 
					elementMeasures.getNCS() * elementMeasures.getNUS() ==0.0){
				susp = 1.0;
			} else {
				susp = ((double)(elementMeasures.getNCF() * elementMeasures.getNUF() + 
						elementMeasures.getNUF() * elementMeasures.getNCS()) / 
						(elementMeasures.getNCF() * elementMeasures.getNUF() + 
								2*elementMeasures.getNUF() * elementMeasures.getNUS() + 
								elementMeasures.getNCS() * elementMeasures.getNUS()));					
			}
		}
		else if (technique.equals(ModelElementSuspiciousness.BARONIETAL)){
			susp = ((double)Math.sqrt(elementMeasures.getNCF() * elementMeasures.getNUS())+elementMeasures.getNCF())/
					(Math.sqrt(elementMeasures.getNCF() * elementMeasures.getNUS()) 
							+ elementMeasures.getNCF() + elementMeasures.getNCS() + elementMeasures.getNUF());
		}  
		else if (technique.equals(ModelElementSuspiciousness.PHI)){
			if (elementMeasures.getNCF() * elementMeasures.getNUS() - 
					elementMeasures.getNUF()*elementMeasures.getNCS() == 0.0){
				susp = 0.0;
			} else if (Math.sqrt((elementMeasures.getNCF()+elementMeasures.getNCS())*
					(elementMeasures.getNCF()+elementMeasures.getNUF())*
					(elementMeasures.getNCS()+elementMeasures.getNUS())*
					(elementMeasures.getNUF()+elementMeasures.getNUS())) ==0.0){
				susp = 1.0;
			} else {
				susp = (double)(elementMeasures.getNCF() * elementMeasures.getNUS() - 
						elementMeasures.getNUF()*elementMeasures.getNCS())/
						(Math.sqrt((elementMeasures.getNCF()+elementMeasures.getNCS())*
								(elementMeasures.getNCF()+elementMeasures.getNUF())*
								(elementMeasures.getNCS()+elementMeasures.getNUS())*
								(elementMeasures.getNUF()+elementMeasures.getNUS())));
			}
		} 
		else if (technique.equals(ModelElementSuspiciousness.ROGERSTANIMOTO)){
			susp = ((double)(elementMeasures.getNCF() + elementMeasures.getNUS())/
					(elementMeasures.getNCF() + elementMeasures.getNUS() + 
							2*(elementMeasures.getNUF() + elementMeasures.getNCS())));
		}
		else if (technique.equals(ModelElementSuspiciousness.OP2)){
			susp = (elementMeasures.getNCF() - ((double) elementMeasures.getNCS() / (elementMeasures.getNS() + 1)));
		} 
		else if (technique.equals(ModelElementSuspiciousness.BARINEL)){
			susp = (1 - ((double) elementMeasures.getNCS() / (elementMeasures.getNCS() + elementMeasures.getNCF())));
		} 
		else if (technique.equals(ModelElementSuspiciousness.DSTAR)){
			if (Math.pow(elementMeasures.getNCF(),2) == 0.0){
				susp = 0.0;
			} else if ((elementMeasures.getNCS() + elementMeasures.getNF() - elementMeasures.getNCF()) ==0.0){
				susp = 1.0;
			} else {
				susp = (double) Math.pow(elementMeasures.getNCF(),2) /
						(elementMeasures.getNCS() + elementMeasures.getNF() - elementMeasures.getNCF());
			}				
		}
		else if (technique.equals(ModelElementSuspiciousness.CONFIDENCE)){
			if ((elementMeasures.getNF())==0){
				susp = ((double) elementMeasures.getNCS()/elementMeasures.getNS());
			} else if ((elementMeasures.getNS())==0){
				susp = ((double) elementMeasures.getNCF()/elementMeasures.getNF());
			} else {
				susp = Math.max((double)elementMeasures.getNCF()/elementMeasures.getNF(),
						(double)elementMeasures.getNCS()/elementMeasures.getNS());
			}		
		}
		return susp;
	}
	
	public void calculateRanks() {
		List<SBFLMeasures> measures = new ArrayList<SBFLMeasures>();
		measures.addAll(this.elementsSBFLMeasures);
		Collections.sort(measures, Collections.reverseOrder());
		int rank = 1;
		int num = 0;
		for (int i=0; i<measures.size(); i++) {	
			String technique = measures.get(i).currentTechnique;
			if (i > 0) {
				if (measures.get(i).getSusp() == measures.get(i-1).getSusp()) {
					measures.get(i).getRank().put(technique, rank);
					num++;
				}else {
					rank = rank + num;
					measures.get(i).getRank().put(technique, rank);
				}
			}else {
				measures.get(i).getRank().put(technique, rank);
				num++;
			}
			for (SBFLMeasures originalOperands: this.elementsSBFLMeasures) {
				if (originalOperands.getMetaclass() == measures.get(i).getMetaclass()
						&& originalOperands.getModelObject() == measures.get(i).getModelObject()) {
					originalOperands.getRank().put(technique, rank);
					break;
				}
			}
		}
	}
	
	public List<SBFLMeasures> getElementsSBFLMeasures() {
		return elementsSBFLMeasures;
	}

	public void setElementsSBFLMeasures(List<SBFLMeasures> elementsSBFLMeasures) {
		this.elementsSBFLMeasures = elementsSBFLMeasures;
	}
}
