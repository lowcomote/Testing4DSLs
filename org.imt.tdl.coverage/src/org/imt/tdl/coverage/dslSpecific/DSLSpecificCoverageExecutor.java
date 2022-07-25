package org.imt.tdl.coverage.dslSpecific;

import org.eclipse.emf.ecore.EClass;
import org.imt.tdl.coverage.TDLCoverageUtil;

import DSLSpecificCoverage.ContainerCoverageInference;
import DSLSpecificCoverage.Context;
import DSLSpecificCoverage.EClassIgnorance;
import DSLSpecificCoverage.ReferenceCoverageInference;

public class DSLSpecificCoverageExecutor {
	
	//check if the context of the container is specified in the rule
	public void updateCoverableClasses(ContainerCoverageInference rule) {
		if (!rule.getContainerContext().isEmpty()) {
			rule.getContainerContext().forEach(c -> addCoverableClass(c));
		}
		else {
			addCoverableClass((EClass) rule.getContainementReference().eContainer());
		}
	}

	public void updateCoverableClasses(ReferenceCoverageInference rule) {
		TDLCoverageUtil.getInstance().addCoverableClass(
				(EClass) rule.getEReference().getEType());
	}
	
	private void addCoverableClass(EClass c) {
		TDLCoverageUtil.getInstance().addCoverableClass(c);
	}
	
	public void runEClassIgnoranceRule(EClassIgnorance rule) {
		if (rule.isIgnoreSubclasses()) {
			TDLCoverageUtil.getInstance().removeCoverableClass_subClass(
					((Context) rule.eContainer()).getMetaclass());
		}
		else {
			TDLCoverageUtil.getInstance().removeCoverableClass(
					((Context) rule.eContainer()).getMetaclass());
		}
	}
}
