package org.imt.tdl.coverage;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;

public interface IDSLSpecificCoverage {
	public List<String> getNewCoverableClasses();
	public void ignoreModelObjects(Resource MUTResource);
	public void specializeCoverage(TDLTestCaseCoverage testCaseCoverage);
}
