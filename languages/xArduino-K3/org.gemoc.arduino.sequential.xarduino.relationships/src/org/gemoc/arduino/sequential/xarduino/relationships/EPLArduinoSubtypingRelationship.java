package org.gemoc.arduino.sequential.xarduino.relationships;

import static org.eclipse.gemoc.executionframework.event.manager.IImplementationRelationship.loadBehavioralInterface;




import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gemoc.xdsmlframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.eclipse.gemoc.executionframework.event.manager.EPLSubtypingRelationship;
import org.eclipse.gemoc.executionframework.event.manager.SubtypingRuleSubscriber;
import org.gemoc.sequential.model.arduino.ArduinoPackage;
import org.gemoc.sequential.model.arduino.PushButton;
import org.gemoc.sequential.model.arduino.Sketch;

import com.google.common.collect.Streams;

public class EPLArduinoSubtypingRelationship extends EPLSubtypingRelationship {

	private static List<SubtypingRuleSubscriber> computeRuleSubscribers() {
		final List<SubtypingRuleSubscriber> result = new ArrayList<>();
		result.add(new OnActivateSketch());
		result.add(new OnActivateButton());
		result.add(new OnLedOffOn());
		return result;
	}

	public EPLArduinoSubtypingRelationship() {
		this(loadBehavioralInterface("platform:/plugin/org.gemoc.arduino.sequential.xarduino.relationships/Activatable.bi"),
				loadBehavioralInterface("platform:/plugin/org.gemoc.arduino.sequential.xarduino.relationships/Arduino.bi"));
	}

	public EPLArduinoSubtypingRelationship(BehavioralInterface supertype, BehavioralInterface subtype) {
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
	
	static public class OnActivateSketch extends SubtypingRuleSubscriber {
		@Override
		public String getStatement() {
			return "select args('id') from EPLEventOccurrence(event.name='activate')";
		}

		public void update(Object sketchId) {
			final Sketch sketch = Streams.stream(executedResource.getAllContents())
					.filter(o -> o instanceof Sketch)
					.map(b -> (Sketch) b)
					.filter(s -> s.getName().equals(sketchId))
					.findFirst().orElse(null);
			if (sketch != null) {
				final Map<String, Object> parameters = new HashMap<>();
				parameters.put("sketch", sketch);
				consumeEventOccurrence(createAcceptedEventOccurrence("run", parameters));
			}
		}
	}

	static public class OnActivateButton extends SubtypingRuleSubscriber {
		@Override
		public String getStatement() {
			return "select args('id') from EPLEventOccurrence(event.name='activate')";
		}

		public void update(Object buttonId) {
			final PushButton button = Streams.stream(executedResource.getAllContents())
					.filter(o -> o instanceof PushButton)
					.map(b -> (PushButton) b)
					.filter(b -> b.getName().equals(buttonId))
					.findFirst().orElse(null);
			if (button != null) {
				final Map<String, Object> parameters = new HashMap<>();
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
			+ "ledon=EPLEventOccurrence(event.name='led_level_changed', args('level')=1)] where "
			+ "ledon.args('led').name?=ledoff.args('led').name?";
		}
		public void update(Object ledon) {
				final Map<String, Object> parameters = new HashMap<>();
				parameters.put("id", ledon);
				consumeEventOccurrence(createExposedEventOccurrence("activated", parameters));
		}
	}
}
