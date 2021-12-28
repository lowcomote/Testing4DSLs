package org.imt.tdl.sbfl.evaluation;

import org.eclipse.emf.compare.AttributeChange;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.ReferenceChange;
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
		for (Diff diff:comparison.getDifferences()) {
			if (diff instanceof AttributeChange) {
				
			}
			else if (diff instanceof ReferenceChange) {
				
			}
		}
		comparison.getDifferences();
		return null;
	}
}