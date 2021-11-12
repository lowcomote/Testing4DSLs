package org.imt.tdl.coverage;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

public class TestCoverageInfo {
	
	private EClass metaclass;
	private EObject modelObject;
	private ArrayList<String> coverage = new ArrayList<>();
	
	public EObject getModelObject() {
		return modelObject;
	}
	public void setModelObject(EObject modelObject) {
		this.modelObject = modelObject;
		this.metaclass = modelObject.eClass();
	}
	public ArrayList<String> getCoverage() {
		return coverage;
	}
	public void setCoverage(ArrayList<String> coverage) {
		this.coverage = coverage;
	}
	public EClass getMetaclass() {
		return metaclass;
	}
	public void setMetaclass(EClass metaclass) {
		this.metaclass = metaclass;
	}
}
