package org.imt.tdl.coverage.dslSpecific;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;

public interface IDSLSpecificCoverage {
	
	public EList<DSLSpecificCoverageRule> getDSLSpecificCoverageRules();
	public void ignoreModelObjects(Resource MUTResource);
	
}
