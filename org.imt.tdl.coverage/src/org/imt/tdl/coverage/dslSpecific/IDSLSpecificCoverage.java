package org.imt.tdl.coverage.dslSpecific;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.imt.tdl.coverage.TDLTestCaseCoverage;

public interface IDSLSpecificCoverage {
	public List<String> getNewCoverableClasses();
	public void ignoreModelObjects(Resource MUTResource);
	public void specializeCoverage(TDLTestCaseCoverage testCaseCoverage);
}
