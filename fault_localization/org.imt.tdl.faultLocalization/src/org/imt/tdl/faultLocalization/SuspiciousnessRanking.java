/********************************************************************************
* Copyright (c) 2022 IMT Atlantique.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
* Javier Troya (Universidad de Sevilla, Spain) - implementation: Initial Contributor (2017)
* Faezeh Khorram (IMT Atlantique, France) - further contribution (2022)
*******************************************************************************/
package org.imt.tdl.faultLocalization;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.imt.tdl.coverage.TDLCoverageUtil;
import org.imt.tdl.coverage.TDLTestSuiteCoverage;
import org.imt.tdl.coverage.ObjectCoverageStatus;
import org.imt.tdl.testResult.TDLTestCaseResult;
import org.imt.tdl.testResult.TDLTestResultUtil;
import org.imt.tdl.testResult.TDLTestSuiteResult;

public class SuspiciousnessRanking {
	
	private final TDLTestSuiteResult testSuiteResult;
	private final List<TDLTestCaseResult> errorVector;
	private final TDLTestSuiteCoverage testSuiteCoverage;
	private List<ObjectCoverageStatus> coverageMatix = new ArrayList<ObjectCoverageStatus>();
	
	public List<ObjectCoverageStatus> getCoverageMatix() {
		return coverageMatix;
	}

	private List<SBFLMeasures> elementsSBFLMeasures = new ArrayList<SBFLMeasures>();

	public List<String> sbflTechniques = new ArrayList<>();
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
	
	public SuspiciousnessRanking() {
		setSbflTechniques();
		testSuiteResult = TDLTestResultUtil.getInstance().getTestSuiteResult();
		errorVector = testSuiteResult.getTestCaseResults();
		testSuiteCoverage = TDLCoverageUtil.getInstance().getTestSuiteCoverage();
		coverageMatix.addAll(testSuiteCoverage.coverageOfModelObjects);
		//the row of the matrix containing coverage percentages should be removed 
		coverageMatix.removeIf(element -> element.getMetaclass() == null);
		//if the element is not coverable, remove it from the matrix
		coverageMatix.removeIf(element -> 
			element.getCoverage().get(element.getCoverage().size()-1) == TDLCoverageUtil.NOT_COVERABLE);
		elementsSBFLMeasures.clear();
	}
	
	private void setSbflTechniques() {
		sbflTechniques.add(ARITHMETICMEAN);
		sbflTechniques.add(BARINEL);
		sbflTechniques.add(BARONIETAL);
		sbflTechniques.add(BRAUNBANQUET);
		sbflTechniques.add(COHEN);
		sbflTechniques.add(CONFIDENCE);
		sbflTechniques.add(PIERCE);
		sbflTechniques.add(ROGERSTANIMOTO);
		sbflTechniques.add(RUSSELRAO);
		sbflTechniques.add(SIMPLEMATCHING);
		sbflTechniques.add(DSTAR);
		sbflTechniques.add(KULCYNSKI2);
		sbflTechniques.add(MOUNTFORD);
		sbflTechniques.add(OCHIAI);
		sbflTechniques.add(OCHIAI2);
		sbflTechniques.add(OP2);
		sbflTechniques.add(PHI);
		sbflTechniques.add(TARANTULA);
		sbflTechniques.add(ZOLTAR);
		
	}

	public SuspiciousnessRanking(TDLTestSuiteResult tsResult, TDLTestSuiteCoverage tsCoverage) {
		setSbflTechniques();
		testSuiteResult = tsResult;
		errorVector = testSuiteResult.getTestCaseResults();
		testSuiteCoverage = tsCoverage;
		coverageMatix.addAll(testSuiteCoverage.coverageOfModelObjects);
		//the row of the matrix containing coverage percentages should be removed 
		coverageMatix.removeIf(element -> element.getMetaclass() == null);
		//if the element is not coverable, remove it from the matrix
		coverageMatix.removeIf(element -> 
			element.getCoverage().get(element.getCoverage().size()-1) == TDLCoverageUtil.NOT_COVERABLE);
		elementsSBFLMeasures.clear();
	}
	
	public void calculateMeasures() {
		for (int i=0; i<coverageMatix.size(); i++) {
			EObject modelElement = coverageMatix.get(i).getModelObject();
			ArrayList<String> elementCoverageStatus = coverageMatix.get(i).getCoverage();
			SBFLMeasures elementSBFLMeasures = new SBFLMeasures();
			elementSBFLMeasures.setModelObject(modelElement);
			elementSBFLMeasures.setMetaclass(modelElement.eClass());
			elementSBFLMeasures.setNF(testSuiteResult.getNumOfFailedTestCases());
			elementSBFLMeasures.setNS(testSuiteResult.getNumOfPassedTestCases());

			int NCF = 0;//number of failed test cases that cover a coverable model element
			int NUF = 0;//number of failed test cases that do not cover a coverable model element
			int NCS = 0;//number of successful test cases that cover a coverable model element 
			int NUS = 0;//number of successful test cases that do not cover a coverable model element 
			int NC = 0;//total number of test cases that cover a coverable model element
			int NU = 0;//total number of test cases that do not cover a coverable model element 
			for (int j=0; j<elementCoverageStatus.size()-1; j++) {
				elementSBFLMeasures.getCoverage().add(elementCoverageStatus.get(j));
				String testCaseName = testSuiteCoverage.getTCCoverages().get(j).getTestCaseName();
				//find the result of the test case
				String testCaseVerdict = "";
				for (TDLTestCaseResult testResult:errorVector) {
					if (testResult.getTestCaseName().equals(testCaseName)) {
						testCaseVerdict = testResult.getValue();
						break;
					}
				}
				if (elementCoverageStatus.get(j) == TDLCoverageUtil.COVERED) {
					NC++;
					if (testCaseVerdict == TDLTestResultUtil.PASS) {
						NCS++;
					}else if (testCaseVerdict == TDLTestResultUtil.FAIL) {
						NCF++;
					}
				}else if (elementCoverageStatus.get(j) == TDLCoverageUtil.NOT_COVERED) {
					NU++;
					if (testCaseVerdict == TDLTestResultUtil.PASS) {
						NUS++;
					}else if (testCaseVerdict == TDLTestResultUtil.FAIL) {
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
			elementsSBFLMeasures.add(elementSBFLMeasures);		
		}
		//to show the verdicts in the view, we add a new SBFLMeasure object at the end of the list elementsSBFLMeasures
		//this object only contains verdicts for the test cases
		SBFLMeasures testsVerdicts = new SBFLMeasures();
		for (TDLTestCaseResult tcResult:errorVector) {
			testsVerdicts.getCoverage().add(tcResult.getValue());
		}
		elementsSBFLMeasures.add(testsVerdicts);
	}
	
	//reference: https://github.com/javitroya/SBFL_MT/blob/6f623acab0d4b673314feca58d72cac705dc0967/Spectrum-based_FaultLoc_MT/SpecBased_FaultLoc_MT/src/es/us/eii/fault/localization/mt/main/FaultLocalizationMT_Main.java#L1031
	public double getSuspiciousness(SBFLMeasures elementMeasures){
		String technique = elementMeasures.currentTechnique;
		Double susp = 0.0;
		if (technique.equals(SuspiciousnessRanking.OCHIAI)){
			if (elementMeasures.getNCF() == 0.0){
				susp = 0.0;
			} else if (Math.sqrt(elementMeasures.getNF()*(elementMeasures.getNCF()+elementMeasures.getNCS()))==0.0){
				susp = 1.0;
			} else {
				susp = (double) elementMeasures.getNCF() / 
						Math.sqrt(elementMeasures.getNF()*(elementMeasures.getNCF()+elementMeasures.getNCS()));
			}
		}else if (technique.equals(SuspiciousnessRanking.TARANTULA)){
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
		} else if (technique.equals(SuspiciousnessRanking.OCHIAI2)){
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
		} else if (technique.equals(SuspiciousnessRanking.BRAUNBANQUET)){
			susp =  ((double) elementMeasures.getNCF() / 
					Math.max(elementMeasures.getNCF() + elementMeasures.getNCS(), 
							elementMeasures.getNCF() + elementMeasures.getNUF()));
		} else if (technique.equals(SuspiciousnessRanking.MOUNTFORD)){
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
		} else if (technique.equals(SuspiciousnessRanking.ARITHMETICMEAN)){
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
		} else if (technique.equals(SuspiciousnessRanking.ZOLTAR)){
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
		else if (technique.equals(SuspiciousnessRanking.SIMPLEMATCHING)){
			susp =  ((double)(elementMeasures.getNCF()+elementMeasures.getNUS())/
					(elementMeasures.getNCF()+elementMeasures.getNCS()+elementMeasures.getNUS()+elementMeasures.getNUF()));
		} 
		else if (technique.equals(SuspiciousnessRanking.RUSSELRAO)){
			susp =  ((double) elementMeasures.getNCF() / 
					(elementMeasures.getNCF()+elementMeasures.getNUF()+elementMeasures.getNCS()+elementMeasures.getNUS()));
		} 
		else if (technique.equals(SuspiciousnessRanking.KULCYNSKI2)){
			if (elementMeasures.getNCF() == 0.0){
				susp = 0.0;
			} else if (elementMeasures.getNCF()+elementMeasures.getNUF() ==0.0 || elementMeasures.getNCF()+elementMeasures.getNCS()==0.0){
				susp = 1.0;
			} else {
				susp = 0.5 * (((double)elementMeasures.getNCF()/(elementMeasures.getNCF()+elementMeasures.getNUF()))+
						 ((double)elementMeasures.getNCF()/(elementMeasures.getNCF()+elementMeasures.getNCS())));
			}				
		} 
		else if (technique.equals(SuspiciousnessRanking.COHEN)){
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
		else if (technique.equals(SuspiciousnessRanking.PIERCE)) {
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
		else if (technique.equals(SuspiciousnessRanking.BARONIETAL)){
			susp = ((double)Math.sqrt(elementMeasures.getNCF() * elementMeasures.getNUS())+elementMeasures.getNCF())/
					(Math.sqrt(elementMeasures.getNCF() * elementMeasures.getNUS()) 
							+ elementMeasures.getNCF() + elementMeasures.getNCS() + elementMeasures.getNUF());
		}  
		else if (technique.equals(SuspiciousnessRanking.PHI)){
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
		else if (technique.equals(SuspiciousnessRanking.ROGERSTANIMOTO)){
			susp = ((double)(elementMeasures.getNCF() + elementMeasures.getNUS())/
					(elementMeasures.getNCF() + elementMeasures.getNUS() + 
							2*(elementMeasures.getNUF() + elementMeasures.getNCS())));
		}
		else if (technique.equals(SuspiciousnessRanking.OP2)){
			susp = (elementMeasures.getNCF() - ((double) elementMeasures.getNCS() / (elementMeasures.getNS() + 1)));
		} 
		else if (technique.equals(SuspiciousnessRanking.BARINEL)){
			if (elementMeasures.getNCS()==0 && elementMeasures.getNCF()==0) {
				susp = (double) 1;
			}else {
				susp = (1 - ((double) elementMeasures.getNCS() / 
						(elementMeasures.getNCS() + elementMeasures.getNCF())));
			}
		} 
		else if (technique.equals(SuspiciousnessRanking.DSTAR)){
			if (Math.pow(elementMeasures.getNCF(),2) == 0.0){
				susp = 0.0;
			} else if ((elementMeasures.getNCS() + elementMeasures.getNF() - elementMeasures.getNCF()) ==0.0){
				susp = 1.0;
			} else {
				susp = (double) Math.pow(elementMeasures.getNCF(),2) /
						(elementMeasures.getNCS() + elementMeasures.getNF() - elementMeasures.getNCF());
			}				
		}
		else if (technique.equals(SuspiciousnessRanking.CONFIDENCE)){
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
		measures.addAll(elementsSBFLMeasures);
		measures.remove(measures.size()-1);
		Collections.sort(measures, Collections.reverseOrder());
		int rank = 1;
		String technique = "";
		for (int i=0; i<measures.size(); i++) {	
			technique = measures.get(i).currentTechnique;
			if (i > 0) {
				double a = measures.get(i).getSusp().get(technique);
				double b = measures.get(i-1).getSusp().get(technique);
				if (Double.compare(a, b) == 0) {
					measures.get(i).getRank().put(technique, rank);
				}else {
					rank = i + 1;
					measures.get(i).getRank().put(technique, rank);
				}
			}else {
				measures.get(i).getRank().put(technique, rank);
			}
			for (SBFLMeasures objectMeasures: elementsSBFLMeasures) {
				if (objectMeasures.getMetaclass() == measures.get(i).getMetaclass()
						&& objectMeasures.getModelObject() == measures.get(i).getModelObject()) {
					objectMeasures.getRank().put(technique, rank);
					break;
				}
			}
		}
	}
	
	public void measureEXAMScores(SBFLMeasures measures4faultyObject, String technique) { 
		int faultyObjIndex = elementsSBFLMeasures.indexOf(measures4faultyObject);
		int numOfObjects = elementsSBFLMeasures.size();
		int rank4faultyObject = measures4faultyObject.getRank().get(technique);
		int numOfTies = 0;
		//calculate number of ties based on the ranks of other objects for the same technique
		for (int i=0; i<elementsSBFLMeasures.size(); i++) {
		    if (i != faultyObjIndex && elementsSBFLMeasures.get(i).getModelObject()!=null) {
		    	if (elementsSBFLMeasures.get(i).getRank().get(technique) == rank4faultyObject) {
		    		numOfTies++;
		    	}
		    }
		}
		if (numOfTies == 0) {
			double examScore = (double)rank4faultyObject/numOfObjects;
			BigDecimal examScoreBd = new BigDecimal(examScore).setScale(3, RoundingMode.HALF_UP);
			measures4faultyObject.getBestEXAMScore().put(technique, examScoreBd.doubleValue());
			measures4faultyObject.getAverageEXAMScore().put(technique, examScoreBd.doubleValue());
			measures4faultyObject.getWorseEXAMScore().put(technique, examScoreBd.doubleValue());
		    //System.out.println("EXAM Score for " + technique + ": " + examScoreBd.doubleValue());
		    //System.out.println();
		}
		else {
		    double bestExamScore = (double)rank4faultyObject/numOfObjects;
		    BigDecimal bestExamScoreBd = new BigDecimal(bestExamScore).setScale(3, RoundingMode.HALF_UP);
		    measures4faultyObject.getBestEXAMScore().put(technique, bestExamScoreBd.doubleValue());
		    double averageExamScore = (double) (rank4faultyObject + ((double) numOfTies/2))/numOfObjects;
		    BigDecimal avgExamScoreBd = new BigDecimal(averageExamScore).setScale(3, RoundingMode.HALF_UP);
		    measures4faultyObject.getAverageEXAMScore().put(technique, avgExamScoreBd.doubleValue());
		    double worseExamScore = (double) (rank4faultyObject + numOfTies)/numOfObjects;
		    BigDecimal worseExamScoreBd = new BigDecimal(worseExamScore).setScale(3, RoundingMode.HALF_UP);
		    measures4faultyObject.getWorseEXAMScore().put(technique, worseExamScoreBd.doubleValue());
		    	
//		    System.out.println("Best EXAM Score for " + technique + ": " + bestExamScoreBd.doubleValue());
//		    System.out.println("Average EXAM Score for " + technique + ": " + avgExamScoreBd.doubleValue());
//		    System.out.println("Worse EXAM Score for " + technique + ": " + worseExamScoreBd.doubleValue());
//		    System.out.println();
		}
	}
	
	public List<SBFLMeasures> getElementsSBFLMeasures() {
		return elementsSBFLMeasures;
	}

	public void setElementsSBFLMeasures(List<SBFLMeasures> elementsSBFLMeasures) {
		this.elementsSBFLMeasures = elementsSBFLMeasures;
	}
}
