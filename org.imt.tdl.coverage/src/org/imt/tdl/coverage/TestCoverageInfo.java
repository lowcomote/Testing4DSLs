package org.imt.tdl.coverage;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EObject;

public class TestCoverageInfo {
	
	private EObject modelObject;
	private ArrayList<String> coverage = new ArrayList<>();
	
	public EObject getModelObject() {
		return modelObject;
	}
	public void setModelObject(EObject modelObject) {
		this.modelObject = modelObject;
	}
	public ArrayList<String> getCoverage() {
		return coverage;
	}
	public void setCoverage(ArrayList<String> coverage) {
		this.coverage = coverage;
	}
}
