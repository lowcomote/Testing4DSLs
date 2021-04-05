package org.imt.tdl.eventManager;

import java.util.Collections;

import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedTransferQueue;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.gemoc.execution.sequential.javaengine.PlainK3ExecutionEngine;
import org.eclipse.gemoc.executionframework.engine.commons.EngineContextException;
import org.eclipse.gemoc.executionframework.event.manager.GenericEventManager;
import org.eclipse.gemoc.executionframework.event.manager.IImplementationRelationship;
import org.eclipse.gemoc.executionframework.event.manager.ISubtypingRelationship;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;
import org.imt.tdl.executionEngine.ALEEngineLauncher;
import org.imt.tdl.executionEngine.IExecutionEngine;
import org.imt.tdl.executionEngine.JavaEngineLauncher;

public class K3EventManagerLauncher {
	
	private String DSLPath;
	private String MUTPath;
	
	private final String implemRelId = "org.eclipse.gemoc.executionframework.event.implementationrelationship";
	private final String subtypeRelId = "org.eclipse.gemoc.executionframework.event.subtypingrelationship";
	
	private List<IImplementationRelationship> implementationRelationships;
	private List<ISubtypingRelationship> subtypingRelationships;
	
	public void setUp(String DSLPath, String MUTPath, Set<String> implRelIds, Set<String> subRelIds) {
		this.DSLPath = DSLPath;
		this.MUTPath = MUTPath;
		extractInformation(implRelIds, subRelIds);
	}
	//find all the implementation classes
	protected void extractInformation(Set<String> implRelIds, Set<String> subRelIds) {
		implementationRelationships = implRelIds.stream()
				.map(id -> getRelationshipInstance(id, implemRelId, IImplementationRelationship.class))
				.filter(r -> r != null).collect(Collectors.toList());
		subtypingRelationships = subRelIds.stream()
				.map(id -> getRelationshipInstance(id, subtypeRelId, ISubtypingRelationship.class))
				.filter(r -> r != null).collect(Collectors.toList());
	}
	@SuppressWarnings("unchecked")
	private <T> T getRelationshipInstance(String relationshipId, String extensionPointId, Class<T> asType) {
		IConfigurationElement[] relationships = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(extensionPointId);
		for (IConfigurationElement relationship : relationships) {
			if (relationship.getAttribute("id").equals(relationshipId)) {
				try {
					return (T) relationship.createExecutableExtension("class");
				} catch (IllegalArgumentException | CoreException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	//launch the event manager
	public void launch() {	
		JavaEngineLauncher engineLauncher = new JavaEngineLauncher();
		engineLauncher.setUp(this.MUTPath, this.DSLPath);
		PlainK3ExecutionEngine engine = null;
		try {
			engine = engineLauncher.createExecutionEngine();
		} catch (EngineContextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String languageName = engineLauncher.getDslName(this.DSLPath);
		Resource MUTResource = engineLauncher.getModelResource();
		GenericEventManager eventManager = new GenericEventManager(
				languageName, MUTResource, implementationRelationships, subtypingRelationships);
		engine.getExecutionContext().getExecutionPlatform().addEngineAddon(eventManager);
		
		LinkedTransferQueue<EventOccurrence> eventOccurrences = new LinkedTransferQueue<>();
	}

	public List<IImplementationRelationship> getImplementationRelationships() {
		return implementationRelationships;
	}
	public List<ISubtypingRelationship> getSubtypingRelationships() {
		return subtypingRelationships;
	}
	public void setDSLPath (String DSLPath) {
		this.DSLPath = DSLPath;
	}
	public void setMUTPath (String MUTPath) {
		this.MUTPath = MUTPath;
	}
}
