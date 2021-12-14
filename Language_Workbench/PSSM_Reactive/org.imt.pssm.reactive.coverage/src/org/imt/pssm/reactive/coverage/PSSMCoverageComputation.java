package org.imt.pssm.reactive.coverage;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.imt.pssm.reactive.model.statemachines.BooleanBinaryExpression;
import org.imt.pssm.reactive.model.statemachines.BooleanConstraint;
import org.imt.pssm.reactive.model.statemachines.BooleanExpression;
import org.imt.pssm.reactive.model.statemachines.BooleanUnaryExpression;
import org.imt.pssm.reactive.model.statemachines.CallEventType;
import org.imt.pssm.reactive.model.statemachines.Constraint;
import org.imt.pssm.reactive.model.statemachines.CustomSystem;
import org.imt.pssm.reactive.model.statemachines.EventType;
import org.imt.pssm.reactive.model.statemachines.Expression;
import org.imt.pssm.reactive.model.statemachines.FinalState;
import org.imt.pssm.reactive.model.statemachines.IntegerComparisonExpression;
import org.imt.pssm.reactive.model.statemachines.IntegerConstraint;
import org.imt.pssm.reactive.model.statemachines.Operation;
import org.imt.pssm.reactive.model.statemachines.Pseudostate;
import org.imt.pssm.reactive.model.statemachines.Region;
import org.imt.pssm.reactive.model.statemachines.Signal;
import org.imt.pssm.reactive.model.statemachines.SignalEventType;
import org.imt.pssm.reactive.model.statemachines.State;
import org.imt.pssm.reactive.model.statemachines.StateMachine;
import org.imt.pssm.reactive.model.statemachines.StringComparisonExpression;
import org.imt.pssm.reactive.model.statemachines.StringConstraint;
import org.imt.pssm.reactive.model.statemachines.Transition;
import org.imt.pssm.reactive.model.statemachines.Trigger;
import org.imt.pssm.reactive.model.statemachines.Vertex;
import org.imt.tdl.coverage.TDLCoverageUtil;
import org.imt.tdl.coverage.TDLTestCaseCoverage;
import org.imt.tdl.coverage.dslSpecific.IDSLSpecificCoverage;

public class PSSMCoverageComputation implements IDSLSpecificCoverage{
	
	private List<EObject> modelObjects ;
	private TDLTestCaseCoverage testCaseCoverage;
	
	@Override
	public List<String> getNewCoverableClasses() {
		List<String> coverableClasses = new ArrayList<>();
		coverableClasses.add(CustomSystem.class.getName());
		coverableClasses.add(StateMachine.class.getName());
		coverableClasses.add(Region.class.getName());
		coverableClasses.add(Vertex.class.getName());
		coverableClasses.add(State.class.getName());
		coverableClasses.add(FinalState.class.getName());
		coverableClasses.add(Pseudostate.class.getName());
		coverableClasses.add(Constraint.class.getName());
		coverableClasses.add(BooleanConstraint.class.getName());
		coverableClasses.add(IntegerConstraint.class.getName());
		coverableClasses.add(StringConstraint.class.getName());
		coverableClasses.add(Expression.class.getName());
		coverableClasses.add(BooleanExpression.class.getName());
		coverableClasses.add(BooleanUnaryExpression.class.getName());
		coverableClasses.add(BooleanBinaryExpression.class.getName());
		coverableClasses.add(IntegerComparisonExpression.class.getName());
		coverableClasses.add(StringComparisonExpression.class.getName());
		coverableClasses.add(Trigger.class.getName());
		coverableClasses.add(EventType.class.getName());
		coverableClasses.add(SignalEventType.class.getName());
		coverableClasses.add(CallEventType.class.getName());
		coverableClasses.add(Signal.class.getName());
		coverableClasses.add(Operation.class.getName());
		return coverableClasses;
	}
	
	@Override
	public void specializeCoverage(TDLTestCaseCoverage testCaseCoverage) {
		this.testCaseCoverage = testCaseCoverage;
		this.modelObjects = testCaseCoverage.getModelObjects();
		for (int i=0; i<this.modelObjects.size(); i++) {
			EObject modelObject = this.modelObjects.get(i);
			String coverage = this.testCaseCoverage.getTcObjectCoverageStatus().get(i);
			if (modelObject instanceof CustomSystem && coverage != TDLCoverageUtil.COVERED) {
				customSystemCoverage ((CustomSystem) modelObject);
			}
			else if (modelObject instanceof StateMachine && coverage != TDLCoverageUtil.COVERED) {
				stateMachineCoverage ((StateMachine) modelObject);
			}
			else if (modelObject instanceof Region && coverage != TDLCoverageUtil.COVERED) {
				regionCoverage ((Region) modelObject);
			}
			else if (modelObject instanceof Vertex && coverage != TDLCoverageUtil.COVERED) {
				vertexCoverage ((Vertex) modelObject);
			}
			else if (modelObject instanceof Transition) {
				transitionCoverage((Transition) modelObject);
			}
			else if (modelObject instanceof Constraint && coverage != TDLCoverageUtil.COVERED) {
				constraintCoverage ((Constraint) modelObject);
			}
			else if (modelObject instanceof Expression && coverage != TDLCoverageUtil.COVERED) {
				expressionCoverage ((Expression) modelObject);
			}
			else if (modelObject instanceof Trigger && coverage != TDLCoverageUtil.COVERED) {
				triggerCoverage ((Trigger) modelObject);
			}
		}
	}

	private void customSystemCoverage(CustomSystem customSystem) {
		//a customSystem is covered when its inner stateMachine is covered
		int index = this.modelObjects.indexOf(customSystem.getStatemachine());
		String smCoverage = this.testCaseCoverage.getTcObjectCoverageStatus().get(index);
		if (smCoverage != TDLCoverageUtil.COVERED) {
			//if stateMachine is not covered, check its coverage first
			smCoverage = stateMachineCoverage(customSystem.getStatemachine());
		}
		if (smCoverage == TDLCoverageUtil.COVERED) {
			index = this.modelObjects.indexOf(customSystem);
			this.testCaseCoverage.getTcObjectCoverageStatus().set(index, TDLCoverageUtil.COVERED);			
		}
	}

	private String stateMachineCoverage(StateMachine statemachine) {
		//a stateMachine is covered when all of its inner regions are covered
		for (Region innerRegion: statemachine.getRegions()) {
			int index = this.modelObjects.indexOf(innerRegion);
			String regionCoverage = this.testCaseCoverage.getTcObjectCoverageStatus().get(index);
			//if the region is not covered, first check its coverage
			if (regionCoverage != TDLCoverageUtil.COVERED) {
				regionCoverage = regionCoverage(innerRegion);
			}
			//if after checking its coverage, it is still not covered, the state machine is also not covered
			if (regionCoverage != TDLCoverageUtil.COVERED)  {
				index = this.modelObjects.indexOf(statemachine);
				this.testCaseCoverage.getTcObjectCoverageStatus().set(index, TDLCoverageUtil.NOT_COVERED);				
				return TDLCoverageUtil.NOT_COVERED;
			}
		}
		//all the regions are covered, so the state machine is covered
		int index = this.modelObjects.indexOf(statemachine);
		this.testCaseCoverage.getTcObjectCoverageStatus().set(index, TDLCoverageUtil.COVERED);
		
		
		return TDLCoverageUtil.COVERED;
	}

	private String regionCoverage(Region innerRegion) {
		for (Transition transition: innerRegion.getTransitions()) {
			int index = this.modelObjects.indexOf(transition);
			String transitionCoverage = this.testCaseCoverage.getTcObjectCoverageStatus().get(index);
			//if at least one transition is covered, the region is entered, so it is covered too
			if (transitionCoverage == TDLCoverageUtil.COVERED) {
				index = this.modelObjects.indexOf(innerRegion);
				this.testCaseCoverage.getTcObjectCoverageStatus().set(index, TDLCoverageUtil.COVERED);
				return TDLCoverageUtil.COVERED;
			}
		}
		//if there is no covered transition for the region, the region is not covered
		int index = this.modelObjects.indexOf(innerRegion);
		this.testCaseCoverage.getTcObjectCoverageStatus().set(index, TDLCoverageUtil.NOT_COVERED);
		return TDLCoverageUtil.NOT_COVERED;
	}

	private void transitionCoverage(Transition transition) {
		int index = this.modelObjects.indexOf(transition);
		String coverage = this.testCaseCoverage.getTcObjectCoverageStatus().get(index);
		//if a transition is covered, check its related elements
		if (coverage == TDLCoverageUtil.COVERED) {
			//When a transition is covered, its source and target states are also covered
			vertexCoverage(transition, transition.getSource());
			vertexCoverage(transition, transition.getTarget());
			
			//When a transition is covered, its constraint (if any) is also covered
			if (transition.getConstraint() != null) {
				constraintCoverage(transition, transition.getConstraint());
			}
			
			//When a transition is covered, if it has only one trigger, the trigger and its eventType are also covered 
			if (transition.getTriggers().size() == 1) {
				triggerCoverage (transition, transition.getTriggers().get(0));
			}
		}
	}
	
	private void constraintCoverage(Constraint constraint) {
		int index = this.modelObjects.indexOf(constraint);
		if (this.testCaseCoverage.getTcObjectCoverageStatus().get(index) == TDLCoverageUtil.NOT_COVERABLE) {
			this.testCaseCoverage.getTcObjectCoverageStatus().set(index, TDLCoverageUtil.NOT_COVERED);			
		}
	}
	
	private void constraintCoverage(Transition coveredTransition, Constraint constraint) {
		int index = this.modelObjects.indexOf(constraint);
		if (this.testCaseCoverage.getTcObjectCoverageStatus().get(index) != TDLCoverageUtil.COVERED) {
			this.testCaseCoverage.getTcObjectCoverageStatus().set(index, TDLCoverageUtil.COVERED);						
		}
		if (constraint instanceof BooleanConstraint) {
			expressionCoverage(constraint, ((BooleanConstraint) constraint).getExpression());
		}
		else if (constraint instanceof IntegerConstraint) {
			expressionCoverage(constraint, ((IntegerConstraint) constraint).getExpression());
		}
		else if (constraint instanceof StringConstraint) {
			expressionCoverage(constraint, ((StringConstraint) constraint).getExpression());
		}
	}
	
	private void expressionCoverage(Expression expression) {
		int index = this.modelObjects.indexOf(expression);
		if (this.testCaseCoverage.getTcObjectCoverageStatus().get(index) == TDLCoverageUtil.NOT_COVERABLE) {
			this.testCaseCoverage.getTcObjectCoverageStatus().set(index, TDLCoverageUtil.NOT_COVERED);			
		}
	}
	
	private void expressionCoverage(Constraint constraint, Expression expression) {
		int index = this.modelObjects.indexOf(expression);
		if (this.testCaseCoverage.getTcObjectCoverageStatus().get(index) != TDLCoverageUtil.COVERED) {
			this.testCaseCoverage.getTcObjectCoverageStatus().set(index, TDLCoverageUtil.COVERED);						
		}
	}
	
	private void vertexCoverage(Vertex vertex) {
		int index = this.modelObjects.indexOf(vertex);
		if (this.testCaseCoverage.getTcObjectCoverageStatus().get(index) == TDLCoverageUtil.NOT_COVERABLE) {
			this.testCaseCoverage.getTcObjectCoverageStatus().set(index, TDLCoverageUtil.NOT_COVERED);			
		}
	}
	
	private void vertexCoverage(Transition coveredTransition, Vertex vertex) {
		int index = this.modelObjects.indexOf(vertex);
		if (this.testCaseCoverage.getTcObjectCoverageStatus().get(index) != TDLCoverageUtil.COVERED) {
			this.testCaseCoverage.getTcObjectCoverageStatus().set(index, TDLCoverageUtil.COVERED);						
		}
	}
	
	private void triggerCoverage(Trigger trigger) {
		int index = this.modelObjects.indexOf(trigger);
		if (this.testCaseCoverage.getTcObjectCoverageStatus().get(index) == TDLCoverageUtil.NOT_COVERABLE) {
			this.testCaseCoverage.getTcObjectCoverageStatus().set(index, TDLCoverageUtil.NOT_COVERED);			
		}
		EventType triggerEventType = trigger.getEventType();
		if (triggerEventType instanceof SignalEventType) {
			signalEventTypeCoverage ((SignalEventType) triggerEventType);	
		}
		else if (triggerEventType instanceof CallEventType) {
			callEventTypeCoverage ((CallEventType) triggerEventType);
		}
	}
	
	private void triggerCoverage(Transition coveredTransition, Trigger trigger) {
		int index = this.modelObjects.indexOf(trigger);
		if (this.testCaseCoverage.getTcObjectCoverageStatus().get(index) != TDLCoverageUtil.COVERED) {
			this.testCaseCoverage.getTcObjectCoverageStatus().set(index, TDLCoverageUtil.COVERED);						
		}
		EventType triggerEventType = trigger.getEventType();
		if (triggerEventType instanceof SignalEventType) {
			signalEventTypeCoverage (trigger, (SignalEventType) triggerEventType);	
		}
		else if (triggerEventType instanceof CallEventType) {
			callEventTypeCoverage (trigger, (CallEventType) triggerEventType);
		}
	}
	
	private void signalEventTypeCoverage(SignalEventType signalEventType) {
		int index = this.modelObjects.indexOf(signalEventType);
		if (this.testCaseCoverage.getTcObjectCoverageStatus().get(index) == TDLCoverageUtil.NOT_COVERABLE) {
			this.testCaseCoverage.getTcObjectCoverageStatus().set(index, TDLCoverageUtil.NOT_COVERED);			
		}
		signalCoverage(signalEventType.getSignal());
	}
	
	private void signalEventTypeCoverage(Trigger coveredTrigger, SignalEventType signalEventType) {
		int index = this.modelObjects.indexOf(signalEventType);
		if (this.testCaseCoverage.getTcObjectCoverageStatus().get(index) != TDLCoverageUtil.COVERED) {
			this.testCaseCoverage.getTcObjectCoverageStatus().set(index, TDLCoverageUtil.COVERED);						
		}
		signalCoverage(signalEventType, signalEventType.getSignal());
	}
	
	private void callEventTypeCoverage(CallEventType callEventType) {
		int index = this.modelObjects.indexOf(callEventType);
		if (this.testCaseCoverage.getTcObjectCoverageStatus().get(index) == TDLCoverageUtil.NOT_COVERABLE) {
			this.testCaseCoverage.getTcObjectCoverageStatus().set(index, TDLCoverageUtil.NOT_COVERED);			
		}
		operationCoverage(callEventType.getOperation());
	}
	
	private void callEventTypeCoverage(Trigger coveredTrigger, CallEventType callEventType) {
		int index = this.modelObjects.indexOf(callEventType);
		if (this.testCaseCoverage.getTcObjectCoverageStatus().get(index) != TDLCoverageUtil.COVERED) {
			this.testCaseCoverage.getTcObjectCoverageStatus().set(index, TDLCoverageUtil.COVERED);						
		}
		operationCoverage(callEventType, callEventType.getOperation());
	}
	
	private void signalCoverage(Signal signal) {
		int index = this.modelObjects.indexOf(signal);
		if (this.testCaseCoverage.getTcObjectCoverageStatus().get(index) == TDLCoverageUtil.NOT_COVERABLE) {
			this.testCaseCoverage.getTcObjectCoverageStatus().set(index, TDLCoverageUtil.NOT_COVERED);			
		}
	}
	
	private void signalCoverage(SignalEventType coveredEventType, Signal signal) {
		int index = this.modelObjects.indexOf(signal);
		if (this.testCaseCoverage.getTcObjectCoverageStatus().get(index) != TDLCoverageUtil.COVERED) {
			this.testCaseCoverage.getTcObjectCoverageStatus().set(index, TDLCoverageUtil.COVERED);			
		}
	}
	
	private void operationCoverage(Operation operation) {
		int index = this.modelObjects.indexOf(operation);
		if (this.testCaseCoverage.getTcObjectCoverageStatus().get(index) == TDLCoverageUtil.NOT_COVERABLE) {
			this.testCaseCoverage.getTcObjectCoverageStatus().set(index, TDLCoverageUtil.NOT_COVERED);			
		}
	}
	
	private void operationCoverage(CallEventType coveredEventType, Operation operation) {
		int index = this.modelObjects.indexOf(operation);
		if (this.testCaseCoverage.getTcObjectCoverageStatus().get(index) != TDLCoverageUtil.COVERED) {
			this.testCaseCoverage.getTcObjectCoverageStatus().set(index, TDLCoverageUtil.COVERED);
		}
	}

	@Override
	public void ignoreModelObjects(Resource MUTResource) {
		// TODO Auto-generated method stub
		
	}
}

	
