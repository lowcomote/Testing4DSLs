package org.imt.pssm.relationships;


import static org.eclipse.gemoc.executionframework.event.manager.IImplementationRelationship.loadBehavioralInterface;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.Event;
import org.eclipse.gemoc.executionframework.event.manager.SimpleImplementationRelationship;

public class InterpretedStateMachinesImplementationRelationship extends SimpleImplementationRelationship {

	private static Map<String, String> computeEventToMethodMap() {
		final Map<String, String> result = new HashMap<>();
		result.put("run", "org.imt.pssm.interpreter.StateMachineAspect.run");
		result.put("signal_received", "org.imt.pssm.interpreter.StateMachineAspect.eventOccurrenceReceived");
		result.put("behavior_effected", "org.imt.pssm.model.statemachines.Behavior.execute");
		return result;
	}
	
	private static Set<String> computeRunToCompletionMap(List<Event> events) {
		final Set<String> result = new HashSet<>();
		events.stream().filter(e -> e.getName().equals("call_signal_received")).findFirst()
				.ifPresent(e -> result.add(e.eResource().getURIFragment(e)));
		events.stream().filter(e -> e.getName().equals("call_run")).findFirst()
				.ifPresent(e -> result.add(e.eResource().getURIFragment(e)));
		return result;
	}

	public InterpretedStateMachinesImplementationRelationship() {
		this((BehavioralInterface) loadBehavioralInterface(
				"platform:/plugin/org.imt.pssm.relationships/InterpretedStateMachines.bi"));
	}

	public InterpretedStateMachinesImplementationRelationship(BehavioralInterface behavioralInterface) {
		//TODO: we manually set the name of the DSL as the last argument but it has to be the rule executor
		super(behavioralInterface, computeRunToCompletionMap(behavioralInterface.getEvents()), computeEventToMethodMap(), "org.imt.pssm.InterpretedStatemachines");
	}
}
