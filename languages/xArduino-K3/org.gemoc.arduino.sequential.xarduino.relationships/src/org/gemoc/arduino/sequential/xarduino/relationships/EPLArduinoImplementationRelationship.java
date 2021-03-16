package org.gemoc.arduino.sequential.xarduino.relationships;

import static org.eclipse.gemoc.executionframework.event.manager.IImplementationRelationship.loadBehavioralInterface;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gemoc.executionframework.event.manager.EPLImplementationRelationship;
import org.eclipse.gemoc.executionframework.event.manager.ImplementationRuleSubscriber;
import org.eclipse.gemoc.executionframework.event.manager.SimpleCallRequest;
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.gemoc.sequential.model.arduino.Led;
import org.gemoc.sequential.model.arduino.SetLed;
public class EPLArduinoImplementationRelationship extends EPLImplementationRelationship {
	
	private static final String RULE_EXECUTOR_ID = "org.eclipse.gemoc.execution.sequential.javaengine.k3_rule_executor";
	
	private static List<ImplementationRuleSubscriber> computeRuleSubscribers() {
		final List<ImplementationRuleSubscriber> result = new ArrayList<>();
		result.add(new OnRun());
		result.add(new OnButtonPressed());
		result.add(new OnButtonReleased());
		result.add(new OnSetLed());
		return result;
	}

	public EPLArduinoImplementationRelationship() {
		this(loadBehavioralInterface("platform:/plugin/org.gemoc.arduino.sequential.xarduino.relationships/Arduino.bi"));
	}

	public EPLArduinoImplementationRelationship(BehavioralInterface behavioralInterface) {
		super(behavioralInterface, computeRuleSubscribers());
	}
	
	static public class OnRun extends ImplementationRuleSubscriber {
		@Override
		public String getStatement() {
			return "select args('sketch') as sketch from EPLEventOccurrence(event.name='run')";
		}
		
		public void update(Object sketch) {
			final String name = "org.gemoc.arduino.sequential.xarduino.aspects.SketchAspect.execute";
			final List<Object> arguments = Arrays.asList(new Object[] {sketch});
			final boolean rtc = false;
			//consumeCallRequest(new SimpleCallRequest(name, arguments, rtc, RULE_EXECUTOR_ID));
			consumeCallRequest(new SimpleCallRequest(name, arguments, rtc));
		}
	}
	
	static public class OnButtonPressed extends ImplementationRuleSubscriber {
		@Override
		public String getStatement() {
			return "select args('button') as button from EPLEventOccurrence(event.name='button_pressed')";
		}
		
		public void update(Object button) {
			final String name = "org.gemoc.arduino.sequential.xarduino.aspects.PushButtonAspect.press";
			final List<Object> arguments = Arrays.asList(new Object[] {button});
			final boolean rtc = true;
			//consumeCallRequest(new SimpleCallRequest(name, arguments, rtc, RULE_EXECUTOR_ID));
			consumeCallRequest(new SimpleCallRequest(name, arguments, rtc));
		}
	}
	
	static public class OnButtonReleased extends ImplementationRuleSubscriber {
		@Override
		public String getStatement() {
			return "select args('button') as button from EPLEventOccurrence(event.name='button_released')";
		}
		
		public void update(Object button) {
			final String name = "org.gemoc.arduino.sequential.xarduino.aspects.PushButtonAspect.release";
			final List<Object> arguments = Arrays.asList(new Object[] {button});
			final boolean rtc = true;
			//consumeCallRequest(new SimpleCallRequest(name, arguments, rtc, RULE_EXECUTOR_ID));
			consumeCallRequest(new SimpleCallRequest(name, arguments, rtc));
		}
	}
	
	static public class OnSetLed extends ImplementationRuleSubscriber {
		@Override
		public String getStatement() {
			return "select arguments('_self') as setled from " +
					"ReturnNotification(behavioralUnit='org.gemoc.arduino.sequential.xarduino.arduino.SetLed.execute')";
		}
		
		public void update(Object setled) {
			final Map<String, Object> parameters = new HashMap<>();
			final Led led = ((SetLed) setled).getLed();
			parameters.put("led", led);
			parameters.put("level", led.getLevel());
			consumeEventOccurrence(createEventOccurrence("led_level_changed", parameters));
		}
	}
}
