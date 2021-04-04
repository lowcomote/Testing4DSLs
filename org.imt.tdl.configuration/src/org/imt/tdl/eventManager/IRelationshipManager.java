package org.imt.tdl.eventManager;

import java.util.Set;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.Event;

public interface IRelationshipManager {
	
	void registerImplementationRelationship(IImplementationRelationship implementationRelationship);
	
	void unregisterImplementationRelationship(IImplementationRelationship implementationRelationship);
	
	void notifyEventOccurrence(EventOccurrence eventOccurrence);
	
	void notifyCallRequest(ICallRequest callRequest);
	
	void notifyCall(BehavioralUnitNotification callNotification);

	Set<Event> getEvents();
}
