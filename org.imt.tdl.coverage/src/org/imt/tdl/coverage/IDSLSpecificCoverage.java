package org.imt.tdl.coverage;

public interface IDSLSpecificCoverage {
	public void updateCoverableClasses();
	public void specializeCoverage(TDLTestCaseCoverage testCaseCoverage);
}
