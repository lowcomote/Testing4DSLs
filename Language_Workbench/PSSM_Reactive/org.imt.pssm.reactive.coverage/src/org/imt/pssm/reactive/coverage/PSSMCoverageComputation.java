package org.imt.pssm.reactive.coverage;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.imt.pssm.reactive.model.statemachines.StatemachinesPackage;
import org.imt.tdl.coverage.dslSpecific.DSLSpecificCoverageRule;
import org.imt.tdl.coverage.dslSpecific.IDSLSpecificCoverage;

public class PSSMCoverageComputation implements IDSLSpecificCoverage{

	
	@Override
	public EList<DSLSpecificCoverageRule> getDSLSpecificCoverageRules() {
		//a customSystem is covered when its inner stateMachine is covered
		DSLSpecificCoverageRule rule4customSystem = new DSLSpecificCoverageRule();
		rule4customSystem.setContext(StatemachinesPackage.eINSTANCE.getCustomSystem());
		rule4customSystem.setReferenceCoverage(StatemachinesPackage.eINSTANCE.getCustomSystem_Statemachine());
		
		//a stateMachine is covered when all of its inner regions are covered
		DSLSpecificCoverageRule rule4stateMachine = new DSLSpecificCoverageRule();
		rule4stateMachine.setContext(StatemachinesPackage.eINSTANCE.getStateMachine());
		rule4stateMachine.setContainerCoverageByAll(StatemachinesPackage.eINSTANCE.getStateMachine_Regions());
		
		//a region is covered if at least one of its transitions is covered
		DSLSpecificCoverageRule rule4region = new DSLSpecificCoverageRule();
		rule4region.setContext(StatemachinesPackage.eINSTANCE.getRegion());
		rule4region.setContainerCoverageByOne(StatemachinesPackage.eINSTANCE.getRegion_Transitions());
		
		//if a transition is covered, its source state, target state, and constraint are also covered
		DSLSpecificCoverageRule rule4transition = new DSLSpecificCoverageRule();
		rule4transition.setContext(StatemachinesPackage.eINSTANCE.getTransition());
		rule4transition.setReferenceCoverage(ECollections.asEList(StatemachinesPackage.eINSTANCE.getTransition_Source(),
				StatemachinesPackage.eINSTANCE.getTransition_Target(),
				StatemachinesPackage.eINSTANCE.getTransition_Constraint(),
				StatemachinesPackage.eINSTANCE.getTransition_Triggers()));
		
		//if a constraint is covered, its expression is also covered
		DSLSpecificCoverageRule rule4booleanConstraint = new DSLSpecificCoverageRule();
		rule4booleanConstraint.setContext(StatemachinesPackage.eINSTANCE.getBooleanConstraint());
		rule4booleanConstraint.setReferenceCoverage(StatemachinesPackage.eINSTANCE.getBooleanConstraint_Expression());
		
		DSLSpecificCoverageRule rule4integerConstraint = new DSLSpecificCoverageRule();
		rule4integerConstraint.setContext(StatemachinesPackage.eINSTANCE.getIntegerConstraint());
		rule4integerConstraint.setReferenceCoverage(StatemachinesPackage.eINSTANCE.getIntegerConstraint_Expression());

		DSLSpecificCoverageRule rule4stringConstraint = new DSLSpecificCoverageRule();
		rule4stringConstraint.setContext(StatemachinesPackage.eINSTANCE.getStringConstraint());
		rule4stringConstraint.setReferenceCoverage(StatemachinesPackage.eINSTANCE.getStringConstraint_Expression());
		
		//if a trigger is covered, its EventType is also covered
		DSLSpecificCoverageRule rule4trigger = new DSLSpecificCoverageRule();
		rule4trigger.setContext(StatemachinesPackage.eINSTANCE.getTrigger());
		rule4trigger.setReferenceCoverage(StatemachinesPackage.eINSTANCE.getTrigger_EventType());
		
		//if a SignalEventType is covered, its Signal is also covered
		DSLSpecificCoverageRule rule4signalEvent = new DSLSpecificCoverageRule();
		rule4signalEvent.setContext(StatemachinesPackage.eINSTANCE.getSignalEventType());
		rule4signalEvent.setReferenceCoverage(StatemachinesPackage.eINSTANCE.getSignalEventType_Signal());
		
		//if a CallEventType is covered, its Operation is also covered
		DSLSpecificCoverageRule rule4callEvent = new DSLSpecificCoverageRule();
		rule4callEvent.setContext(StatemachinesPackage.eINSTANCE.getCallEventType());
		rule4callEvent.setReferenceCoverage(StatemachinesPackage.eINSTANCE.getCallEventType_Operation());
		
		//set dependencies between rules
		rule4customSystem.addDependency(rule4stateMachine);
		rule4stateMachine.addDependency(rule4region);
		rule4region.addDependency(rule4transition);
		
		return ECollections.asEList(rule4customSystem, rule4stateMachine, rule4region, rule4transition, rule4trigger, rule4stringConstraint,
				rule4booleanConstraint, rule4integerConstraint, rule4callEvent, rule4signalEvent);
	}
	
	@Override
	public void ignoreModelObjects(Resource MUTResource) {
		// TODO Auto-generated method stub
		
	}
}

	
