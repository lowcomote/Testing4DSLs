package org.gemoc.arduino.sequential.xarduino.relationships;

import static org.eclipse.gemoc.executionframework.event.manager.IImplementationRelationship.loadBehavioralInterface;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gemoc.executionframework.event.manager.EPLSubtypingRelationship;
import org.eclipse.gemoc.executionframework.event.manager.SubtypingRuleSubscriber;
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.gemoc.sequential.model.arduino.ArduinoPackage;
import org.gemoc.sequential.model.arduino.Led;
import org.gemoc.sequential.model.arduino.PushButton;
import org.gemoc.sequential.model.arduino.Sketch;

import com.google.common.collect.Streams;

public class EPLArduinoSubtypingRelationship2 extends EPLSubtypingRelationship {

	private static List<SubtypingRuleSubscriber> computeRuleSubscribers() {
		final List<SubtypingRuleSubscriber> result = new ArrayList<>();
		result.add(new OnStart());
		result.add(new OnButtonPushed());
		result.add(new OnLedOffOn());
		result.add(new OnLedOn());
		result.add(new OnLedOff());
		return result;
	}

	public EPLArduinoSubtypingRelationship2() {
		this(loadBehavioralInterface("platform:/plugin/org.gemoc.arduino.sequential.xarduino.relationships/ButtonLight.bi"),
				loadBehavioralInterface("platform:/plugin/org.gemoc.arduino.sequential.xarduino.relationships/Arduino.bi"));
	}

	public EPLArduinoSubtypingRelationship2(BehavioralInterface supertype, BehavioralInterface subtype) {
		super(supertype, subtype, computeRuleSubscribers());
	}

	@Override
	protected boolean isLocal(Class<?> clazz) {
		if (ArduinoPackage.eINSTANCE.getEClassifiers().stream().map(c -> c.getInstanceClass())
				.anyMatch(c -> Arrays.asList(clazz.getInterfaces()).contains(c))) {
			return true;
		}
		return super.isLocal(clazz);
	}

	static public class OnStart extends SubtypingRuleSubscriber {
		@Override
		public String getStatement() {
			return "select * from EPLEventOccurrence(event.name='start')";
		}

		public void update(Object eventOccurrence) {
			final Map<String, Object> parameters = new HashMap<>();
			final Sketch sketch = Streams.stream(executedResource.getAllContents())
					.filter(o -> o instanceof Sketch)
					.findFirst().map(b -> (Sketch) b).orElse(null);
			if (sketch != null) {
				parameters.put("sketch", sketch);
				consumeEventOccurrence(createAcceptedEventOccurrence("run", parameters));
			}
		}
	}

	static public class OnButtonPushed extends SubtypingRuleSubscriber {
		@Override
		public String getStatement() {
			return "select args('button_id') from EPLEventOccurrence(event.name='button_pushed')";
		}

		public void update(Object buttonId) {
			final Map<String, Object> parameters = new HashMap<>();
			final PushButton button = Streams.stream(executedResource.getAllContents())
					.filter(o -> o instanceof PushButton)
					.filter(b -> ((PushButton) b).getName().equals(buttonId))
					.findFirst().map(b -> (PushButton) b).orElse(null);
			if (button != null) {
				parameters.put("button", button);
				consumeEventOccurrence(createAcceptedEventOccurrence("button_pressed", parameters));
				consumeEventOccurrence(createAcceptedEventOccurrence("button_released", parameters));
			}
		}
	}

	static public class OnLedOffOn extends SubtypingRuleSubscriber {
		@Override
		public String getStatement() {
			return "select ledon.args('led').name? from pattern "
					+ "[every ledoff=EPLEventOccurrence(event.name='led_level_changed', args('level')=0) -> "
					+ "ledon=EPLEventOccurrence(event.name='led_level_changed', args('level')=1)]#length(2) where "
					+ "ledon.args('led').name?=ledoff.args('led').name?";
		}

		public void update(Object ledon) {
				final Map<String, Object> parameters = new HashMap<>();
				parameters.put("light_id", ledon);
				consumeEventOccurrence(createExposedEventOccurrence("light_blinked", parameters));
		}
	}

	static public class OnLedOn extends SubtypingRuleSubscriber {
		@Override
		public String getStatement() {
			return "select args('led') as led from EPLEventOccurrence(event.name='led_level_changed', args('level')=1)";
		}

		public void update(Object led) {
			final Map<String, Object> parameters = new HashMap<>();
			parameters.put("light_id", ((Led) led).getName());
			consumeEventOccurrence(createExposedEventOccurrence("light_on", parameters));
		}
	}

	static public class OnLedOff extends SubtypingRuleSubscriber {
		@Override
		public String getStatement() {
			return "select args('led') as led from EPLEventOccurrence(event.name='led_level_changed', args('level')=0)";
		}

		public void update(Object led) {
			final Map<String, Object> parameters = new HashMap<>();
			parameters.put("light_id", ((Led) led).getName());
			consumeEventOccurrence(createExposedEventOccurrence("light_off", parameters));
		}
	}
}
