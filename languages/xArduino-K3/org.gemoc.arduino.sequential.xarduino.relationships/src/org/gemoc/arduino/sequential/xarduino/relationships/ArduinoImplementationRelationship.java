package org.gemoc.arduino.sequential.xarduino.relationships;

import static org.eclipse.gemoc.executionframework.event.manager.IImplementationRelationship.loadBehavioralInterface;



import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.gemoc.executionframework.event.manager.SimpleImplementationRelationship;
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.BehavioralInterface;

public class ArduinoImplementationRelationship extends SimpleImplementationRelationship {

	private static Map<String, String> computeEventToMethodMap() {
		final Map<String, String> result = new HashMap<>();
		result.put("run", "org.gemoc.arduino.sequential.xarduino.aspects.SketchAspect.execute");
		result.put("button_pressed", "org.gemoc.arduino.sequential.xarduino.aspects.PushButtonAspect.press");
		result.put("button_released", "org.gemoc.arduino.sequential.xarduino.aspects.PushButtonAspect.release");
		result.put("led_level_changed", "org.gemoc.arduino.sequential.xarduino.arduino.SetLed.execute");
		return result;
	}

	public ArduinoImplementationRelationship() {
		this(loadBehavioralInterface("platform:/plugin/org.gemoc.arduino.sequential.xarduino.relationships/Arduino.bi"));
	}

	public ArduinoImplementationRelationship(BehavioralInterface behavioralInterface) {
		super(behavioralInterface, Collections.emptySet(), computeEventToMethodMap());
		//super(behavioralInterface, Collections.emptySet(), computeEventToMethodMap(), "org.eclipse.gemoc.execution.sequential.javaengine.k3_rule_executor");
		
	}
}
