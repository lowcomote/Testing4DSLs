package org.imt.pssm.relationships;


import static org.eclipse.gemoc.executionframework.event.manager.IImplementationRelationship.loadBehavioralInterface;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.eclipse.gemoc.executionframework.event.manager.SimpleImplementationRelationship;

public class InterpretedStateMachinesImplementationRelationship extends SimpleImplementationRelationship {

	private static Map<String, String> computeEventToMethodMap() {
		final Map<String, String> result = new HashMap<>();
		result.put("run", "org.imt.pssm.interpreter.StateMachineAspect.run");
		result.put("signal_received", "org.imt.pssm.interpreter.StateMachineAspect.eventOccurrenceReceived");
		result.put("callOperation_received", "org.imt.pssm.interpreter.StateMachineAspect.callOperationReceived");
		result.put("behavior_effected", "org.imt.pssm.model.statemachines.Behavior.execute");
		return result;
	}
	
	private static Set<String> computeRunToCompletionMap() {
		final Set<String> result = new HashSet<>();
		result.add("org.imt.pssm.interpreter.StateMachineAspect.run");
		result.add("org.imt.pssm.interpreter.StateMachineAspect.eventOccurrenceReceived");
		result.add("org.imt.pssm.interpreter.StateMachineAspect.callOperationReceived");
		return result;
	}

	public InterpretedStateMachinesImplementationRelationship() {
		this((BehavioralInterface) loadBehavioralInterface(
				"platform:/plugin/org.imt.pssm.relationships/InterpretedStateMachines.bi"));
	}

	public InterpretedStateMachinesImplementationRelationship(BehavioralInterface behavioralInterface) {
		//TODO: we manually set the name of the DSL as the last argument but it has to be the rule executor
		super(behavioralInterface, computeRunToCompletionMap(), computeEventToMethodMap(), "org.imt.pssm.InterpretedStatemachines");
	}
}
