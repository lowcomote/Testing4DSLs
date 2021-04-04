package org.imt.tdl.eventManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrenceType;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import  org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.Event;

public class RelationshipManager implements IRelationshipManager {

	private final IEventManager eventManager;
	
	private final Map<String, BehavioralInterface> uriToBehavioralInterface = new HashMap<>();

	private final Set<IImplementationRelationship> implementationRelationships = new HashSet<>();
	private final Map<String, List<IImplementationRelationship>> inputImplementationRelationships = new HashMap<>();

	public RelationshipManager(IEventManager eventManager) {
		this.eventManager = eventManager;
	}

	public void setExecutedResource(Resource executedResource) {
		//inputSubtypingRelationships.values().stream().flatMap(l -> l.stream())
				//.forEach(r -> r.setExecutedResource(executedResource));
	}
	
	/*
	 * Called by the event manager when it receives event occurrences and by
	 * relationships when a detected pattern
	 */
	@Override
	public void notifyEventOccurrence(EventOccurrence eventOccurrence) {
		final Event event = eventOccurrence.getEvent();
		final BehavioralInterface behavioralInterface = (BehavioralInterface) event.eContainer();
		final String uri = behavioralInterface.eResource().getURI().toString();
		if (eventOccurrence.getType() == EventOccurrenceType.ACCEPTED) {
			inputImplementationRelationships.computeIfAbsent(uri, bi -> new ArrayList<>())
					.forEach(r -> r.processEventOccurrence(eventOccurrence));
		} else {
			eventManager.emitEventOccurrence(eventOccurrence);
		}
	}

	@Override
	public void notifyCallRequest(ICallRequest callRequest) {
		eventManager.processCallRequest(callRequest);
	}

	@Override
	public void notifyCall(BehavioralUnitNotification callNotification) {
		implementationRelationships.forEach(rel -> rel.processCallNotification(callNotification));
	}

	/*
	 * Relationship registration stuff
	 */
	@Override
	public void registerImplementationRelationship(IImplementationRelationship implementationRelationship) {
		implementationRelationships.add(implementationRelationship);
		final String uri = implementationRelationship.getBehavioralInterface().eResource().getURI().toString();
		final List<IImplementationRelationship> relationships = inputImplementationRelationships.computeIfAbsent(uri,
				k -> new ArrayList<>());
		relationships.add(implementationRelationship);
		//implementationRelationship.configure(e -> notifyEventOccurrence(e), cr -> notifyCallRequest(cr));
		uriToBehavioralInterface.computeIfAbsent(uri, k -> implementationRelationship.getBehavioralInterface());
	}

	@Override
	public void unregisterImplementationRelationship(IImplementationRelationship implementationRelationship) {
		final String uri = implementationRelationship.getBehavioralInterface().eResource().getURI().toString();
		final List<IImplementationRelationship> relationships = inputImplementationRelationships.get(uri);
		if (relationships != null) {
			relationships.remove(implementationRelationship);
		}
		implementationRelationships.remove(implementationRelationship);
	}

	private Set<BehavioralInterface> getAllBehavioralInterfaces() {
		final Set<BehavioralInterface> result = new HashSet<>();
		final BiConsumer<String, List<?>> c = (k, v) -> {
			if (v != null && !v.isEmpty()) {
				result.add(uriToBehavioralInterface.get(k));
			}
		};
		inputImplementationRelationships.forEach(c);
		return result;
	}

	@Override
	public Set<Event> getEvents() {
		return getAllBehavioralInterfaces().stream().flatMap(i -> i.getEvents().stream()).collect(Collectors.toSet());
	}
}
