package org.imt.tdl.faultLocalization;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

public class SBFLParameters implements Comparable<SBFLParameters>{

	private EClass metaclass;
	private EObject modelObject;
	private ArrayList<String> coverage = new ArrayList<>();
	
	private int NCF;//number of failed test cases that cover a coverable model element
	private int NUF;//number of failed test cases that do not cover a coverable model element
	private int NCS;//number of successful test cases that cover a coverable model element 
	private int NUS;//number of successful test cases that do not cover a coverable model element 
	private int NC;//total number of test cases that cover a coverable model element
	private int NU;//total number of test cases that do not cover a coverable model element 
	private int NS;//total number of successful test cases
	private int NF;//total number of failed test cases
	private double Susp;//Suspiciousness score
	private int rank;//Suspiciousness rank
	
	public EClass getMetaclass() {
		return metaclass;
	}
	public void setMetaclass(EClass metaclass) {
		this.metaclass = metaclass;
	}
	public EObject getModelObject() {
		return modelObject;
	}
	public void setModelObject(EObject modelObject) {
		this.modelObject = modelObject;
	}
	public int getNCF() {
		return NCF;
	}
	public void setNCF(int nCF) {
		NCF = nCF;
	}
	public int getNUF() {
		return NUF;
	}
	public void setNUF(int nUF) {
		NUF = nUF;
	}
	public int getNCS() {
		return NCS;
	}
	public void setNCS(int nCS) {
		NCS = nCS;
	}
	public int getNUS() {
		return NUS;
	}
	public void setNUS(int nUS) {
		NUS = nUS;
	}
	public int getNC() {
		return NC;
	}
	public void setNC(int nC) {
		NC = nC;
	}
	public int getNU() {
		return NU;
	}
	public void setNU(int nU) {
		NU = nU;
	}
	public int getNS() {
		return NS;
	}
	public void setNS(int nS) {
		NS = nS;
	}
	public int getNF() {
		return NF;
	}
	public void setNF(int nF) {
		NF = nF;
	}
	public double getSusp() {
		return Susp;
	}
	public void setSusp(double susp) {
		Susp = susp;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public ArrayList<String> getCoverage() {
		return coverage;
	}
	public void setCoverage(ArrayList<String> coverage) {
		this.coverage = coverage;
	}
	@Override
	public int compareTo(SBFLParameters o) {
		return Double.compare(this.Susp, o.Susp);
	}
}
