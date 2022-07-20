package org.imt.pssm.reactive.coverage;

import org.eclipse.emf.ecore.resource.Resource;
import org.imt.tdl.coverage.dslSpecific.IDSLSpecificCoverage;

import DSLSpecificCoverage.DomainSpecificCoverage;

public class PSSMCoverageComputation implements IDSLSpecificCoverage{

	
	@Override
	public DomainSpecificCoverage getDomainSpecificCoverage() {
		//a customSystem is covered when its inner stateMachine is covered
		//a stateMachine is covered when one of its inner regions are covered
		//a region is covered if at least one of its transitions is covered
		//if a transition is covered, its source state, target state, and constraint are also covered
		//if a constraint is covered, its expression is also covered
		//if a trigger is covered, its EventType is also covered
		//if a SignalEventType is covered, its Signal is also covered
		//if a CallEventType is covered, its Operation is also covered
		//set dependencies between rules
		
		return null;
	}
	
	@Override
	public void ignoreModelObjects(Resource MUTResource) {
		// TODO Auto-generated method stub
		
	}
}

	
