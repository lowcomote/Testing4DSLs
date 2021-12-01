package org.imt.tdl.coverage;

import java.util.List;

public interface IDSLSpecificCoverage {
	public List<Class> getNewCoverableClasses();
	public void specializeCoverage(TDLTestCaseCoverage testCaseCoverage);
}
