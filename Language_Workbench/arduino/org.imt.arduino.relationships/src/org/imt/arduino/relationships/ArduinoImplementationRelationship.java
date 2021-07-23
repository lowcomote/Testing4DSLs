package org.imt.arduino.relationships;

import static org.eclipse.gemoc.executionframework.event.manager.IImplementationRelationship.loadBehavioralInterface;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.Event;
import org.eclipse.gemoc.executionframework.event.manager.SimpleImplementationRelationship;

public class ArduinoImplementationRelationship extends SimpleImplementationRelationship {
	
	private static Map<String, String> computeEventToMethodMap() {
		final Map<String, String> result = new HashMap<>();
		result.put("run", "org.imt.arduino.interpreter.SketchAspect.execute");
		result.put("button_pressed", "org.imt.arduino.interpreter.PushButtonAspect.press");
		result.put("button_released", "org.imt.arduino.interpreter.PushButtonAspect.release");
		result.put("led_level_changed", "org.imt.arduino.SetLed.execute");
		return result;
	}
	private static Set<String> computeRunToCompletionMap(List<Event> events) {
		final Set<String> result = new HashSet<>();
		events.stream().filter(e -> e.getName().equals("call_run")).findFirst()
				.ifPresent(e -> result.add(e.eResource().getURIFragment(e)));
		events.stream().filter(e -> e.getName().equals("call_button_pressed")).findFirst()
				.ifPresent(e -> result.add(e.eResource().getURIFragment(e)));
		events.stream().filter(e -> e.getName().equals("call_button_released")).findFirst()
				.ifPresent(e -> result.add(e.eResource().getURIFragment(e)));
		return result;
	}
	public ArduinoImplementationRelationship() {
		this(loadBehavioralInterface("platform:/plugin/org.imt.arduino.relationships/Arduino.bi"));
	}

	public ArduinoImplementationRelationship(BehavioralInterface behavioralInterface) {
		//TODO: we manually set the name of the DSL as the last argument but it has to be the rule executor
		super(behavioralInterface, computeRunToCompletionMap(behavioralInterface.getEvents()), computeEventToMethodMap(), "org.imt.arduino.Arduino");
	}
}
