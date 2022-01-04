package org.imt.tdl.sbfl.evaluation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.compare.AttributeChange;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.ReferenceChange;
import org.eclipse.emf.compare.internal.spec.MatchSpec;
import org.eclipse.emf.compare.match.IMatchEngine;
import org.eclipse.emf.compare.rcp.EMFCompareRCPPlugin;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

public class FaultyObjectFinder {
	
	public EObject findFaultyObjectOfMutant(Resource mutant, Resource originalModel) {					
		final IComparisonScope scope = new DefaultComparisonScope(mutant, originalModel, null);		
		IMatchEngine.Factory.Registry registry = EMFCompareRCPPlugin.getDefault().getMatchEngineFactoryRegistry();	
		Comparison comparison = EMFCompare.builder().setMatchEngineFactoryRegistry(registry).build().compare(scope);
		EList<Diff> diffs = comparison.getDifferences();
		if (diffs.size() == 1) {
			return getDiffObject(diffs.get(0));
		}
		
		//Filter diffs that are pointing to objects of the mutant
		List<Diff> mutantDiffs = new ArrayList<>();
		for (Diff diff:diffs) {
			if (diff instanceof AttributeChange && ((MatchSpec) diff.eContainer()).getLeft().eResource() == mutant) {
				mutantDiffs.add(diff);
			}
			if (diff instanceof ReferenceChange && ((ReferenceChange) diff).getValue().eResource() == mutant) {
				mutantDiffs.add(diff);
			}
		}
		
		//Filter diffs that do not have any equivalence and requirement
		List<Diff> mutantDiffsFiltered = mutantDiffs.stream().filter(md -> (md.getRequires() == null || md.getRequires().size() == 0) && 
				(md.getEquivalence() == null || md.getEquivalence().getDifferences().size() == 0)).collect(Collectors.toList());
		if (mutantDiffsFiltered.size() == 1) {
			return getDiffObject(mutantDiffsFiltered.get(0));
		}
		else if (mutantDiffsFiltered.size() == 0) {
			//Filter diff objects that is because of CHANGE
			mutantDiffsFiltered = mutantDiffs.stream().filter(md -> md.getKind() == DifferenceKind.CHANGE).collect(Collectors.toList());
			if (mutantDiffsFiltered.size() == 1) {
				return getDiffObject(mutantDiffsFiltered.get(0));
			}
			else if (mutantDiffsFiltered.size() == 0) {
				System.out.println("no main diffs");
				return null;
			}
		}
		System.out.println("several main diffs");
		return null;
	}
	
	@SuppressWarnings("restriction")
	private EObject getDiffObject(Diff diff) {
		if (diff instanceof AttributeChange) {
			return ((MatchSpec)diff.eContainer()).getLeft();
		}
		else if (diff instanceof ReferenceChange) {
			ReferenceChange refDiff = (ReferenceChange) diff;
			if (refDiff.getReference().isContainment()) {
				return refDiff.getValue();
			}
			else {
				return ((MatchSpec)refDiff.eContainer()).getLeft();
			}
		}
		return null;
	}
}