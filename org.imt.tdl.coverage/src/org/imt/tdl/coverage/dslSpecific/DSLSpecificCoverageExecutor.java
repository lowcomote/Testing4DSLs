package org.imt.tdl.coverage.dslSpecific;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.imt.tdl.coverage.TDLCoverageUtil;

import DSLSpecificCoverage.ContainerCoverageInference;
import DSLSpecificCoverage.Context;
import DSLSpecificCoverage.CoverageIgnorance;
import DSLSpecificCoverage.DomainSpecificCoverage;
import DSLSpecificCoverage.ReferenceCoverageInference;
import DSLSpecificCoverage.Rule;

public class DSLSpecificCoverageExecutor {
	
	public void updateCoverableClasses(DomainSpecificCoverage dslCoverage) {
		for (Context context:dslCoverage.getMetaclasses()) {
			for (Rule rule: context.getRules()) {
				if (rule instanceof ReferenceCoverageInference) {
					addCoverableClass(((ReferenceCoverageInference) rule).getAssociationReference());
				}
				else if (rule instanceof ContainerCoverageInference) {
					addCoverableClass((EClass)((ContainerCoverageInference) rule).getContainementReference().eContainer());
				}
			}
		}
	}
	
	private void addCoverableClass(EReference r) {
		TDLCoverageUtil.getInstance().addCoverableClass((EClass) r.getEType());
	}
	
	private void addCoverableClass(EClass c) {
		TDLCoverageUtil.getInstance().addCoverableClass(c);
	}
	
	public void runCoverageIgnoranceRules(DomainSpecificCoverage dslSpecificCoverage) {
		for (Context context:dslSpecificCoverage.getMetaclasses()) {
			context.getRules().stream().filter(r -> r instanceof CoverageIgnorance).
				map(rule -> (CoverageIgnorance) rule).
				forEach(rule -> runCoverageIgnoranceRule(context , rule));
		}
	}
	
	private void runCoverageIgnoranceRule(Context context, CoverageIgnorance rule) {
		if (rule.isIgnoreSubclasses()) {
			TDLCoverageUtil.getInstance().removeCoverableClass_subClass(context.getMetaclass());
		}
		else {
			TDLCoverageUtil.getInstance().removeCoverableClass(context.getMetaclass());
		}
	}
}
