package org.imt.tdl.eventManager;

import java.util.Map;

import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;	

public interface IImplementationRelationship {
	Map<String, String> computeEventToMethodMap();
	
	BehavioralInterface getBehavioralInterface();
	
	void processEventOccurrence(EventOccurrence eventOccurrence);
	
	void processCallNotification(BehavioralUnitNotification callNotification);
	
}
