package org.imt.tdl.coverage.dslSpecific;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.imt.tdl.coverage.TDLCoverageUtil;

public class DSLSpecificCoverageRule {
	
	private EClass context;
	//?? maybe a map would be needed
	private EList<EReference> impliesReferenceCoverage;

	public EClass getContext() {
		return context;
	}
	public void setContext(EClass context) {
		this.context = context;
	}
	public EList<EReference> getImpliesReferenceCoverage() {
		return impliesReferenceCoverage;
	}
	public void setImpliesReferenceCoverage(EList<EReference> impliesReferenceCoverage) {
		this.impliesReferenceCoverage = impliesReferenceCoverage;
		impliesReferenceCoverage.forEach(r -> updateCoverableClasses(r));
	}
	
	private void updateCoverableClasses(EReference r) {
		if (!TDLCoverageUtil.getInstance().isClassCoverable((EClass) r.getEType())) {
			TDLCoverageUtil.getInstance().addNewCoverableClass((EClass) r.getEType());
		}
	}
	
}
