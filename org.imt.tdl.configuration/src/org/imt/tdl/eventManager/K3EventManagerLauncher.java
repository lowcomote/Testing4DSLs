/*******************************************************************************
 * Copyright (c) 2016 Inria and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Inria - initial API and implementation
 *******************************************************************************/
package org.imt.tdl.eventManager;

import java.util.ArrayList;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.gemoc.dsl.debug.ide.launch.AbstractDSLLaunchConfigurationDelegate;
import org.eclipse.gemoc.execution.sequential.javaengine.EventBasedExecutionEngine;
import org.eclipse.gemoc.execution.sequential.javaengine.EventBasedModelExecutionContext;
import org.eclipse.gemoc.execution.sequential.javaengine.EventBasedRunConfiguration;
import org.eclipse.gemoc.execution.sequential.javaengine.ui.Activator;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.BehavioralInterfaceFactory;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.Event;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.EventParameter;
import org.eclipse.gemoc.executionframework.engine.commons.EngineContextException;
import org.eclipse.gemoc.executionframework.event.manager.EventManagerUtils;
import org.eclipse.gemoc.executionframework.event.manager.GenericEventManager;
import org.eclipse.gemoc.executionframework.event.manager.IEventManagerListener;
import org.eclipse.gemoc.executionframework.event.model.event.EventFactory;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrenceArgument;
import org.eclipse.gemoc.executionframework.value.model.value.SingleReferenceValue;
import org.eclipse.gemoc.executionframework.value.model.value.Value;
import org.eclipse.gemoc.executionframework.value.model.value.ValueFactory;
import org.eclipse.gemoc.xdsmlframework.api.core.ExecutionMode;
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionEngine;
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;

public class K3EventManagerLauncher {

	private String DSLPath;
	protected final List<EventBasedExecutionEngine> _executionEngines = new ArrayList<>();
	GenericEventManager eventManager = null;
	LinkedTransferQueue<EventOccurrence> eventOccurrences = null;
	// progress monitor used during launch; useful for operations that wish to
	// contribute to the progress bar
	protected IProgressMonitor launchProgressMonitor = null;

	private final ILaunchConfigurationType launchType = DebugPlugin.getDefault().getLaunchManager()
			.getLaunchConfigurationType("org.eclipse.gemoc.execution.sequential.javaengine.ui.launcher");

	private EventBasedExecutionEngine createExecutionEngine(EventBasedRunConfiguration runConfiguration, ExecutionMode executionMode)
			throws CoreException, EngineContextException {
		// create and initialize engine
		EventBasedExecutionEngine executionEngine = new EventBasedExecutionEngine();
		EventBasedModelExecutionContext executioncontext = new EventBasedModelExecutionContext(runConfiguration, executionMode);
		executioncontext.getExecutionPlatform().getModelLoader().setProgressMonitor(this.launchProgressMonitor);
		executioncontext.initializeResourceModel();
		executionEngine.initialize(executioncontext);
		return executionEngine;
	}

	private ILaunchConfiguration getLaunchConfiguration(String MUTPath, String languageName, Set<String> implRelIds, Set<String> subtypeRelIds) throws CoreException {
		final ILaunchConfigurationWorkingCopy configuration = launchType.newInstance(null, "event_basedTesting");
		configuration.setAttribute(AbstractDSLLaunchConfigurationDelegate.RESOURCE_URI, MUTPath);
		configuration.setAttribute(EventBasedRunConfiguration.LAUNCH_SELECTED_LANGUAGE, languageName);
		configuration.setAttribute(EventBasedRunConfiguration.WAIT_FOR_EVENT, true);
		configuration.setAttribute(EventBasedRunConfiguration.IMPL_REL_IDS, implRelIds);
		configuration.setAttribute(EventBasedRunConfiguration.SUBTYPE_REL_IDS, subtypeRelIds);
		configuration.setAttribute(EventBasedRunConfiguration.DEBUG_MODEL_ID, Activator.DEBUG_MODEL_ID);
		return configuration;
	}

	public final void setup(String MUTPath, String DSLPath){
		//final String languageName = configuration.getAttribute("LANGUAGE_NAME", "");
		//final Set<String> implRelIds = configuration.getAttribute("IMPL_REL_IDS", Collections.emptySet());
		//final Set<String> subtypeRelIds = configuration.getAttribute("SUBTYPE_REL_IDS", Collections.emptySet());
		this.DSLPath = DSLPath;
		final String languageName = this.getDslName(DSLPath);
		final String implemRelId = "org.imt.pssm.relationships.implementation_relationship";
		final String subtypeRelId = "org.imt.pssm.relationships.subtyping_relationship";
		final Set<String> implRelIds = new HashSet<>();
		implRelIds.add(implemRelId);
		final Set<String> subtypeRelIds =  new HashSet<>();
		subtypeRelIds.add(subtypeRelId);
			
		try {
			final ILaunchConfiguration launchConf = getLaunchConfiguration(MUTPath, languageName, implRelIds, subtypeRelIds);
			final EventBasedRunConfiguration runConf = new EventBasedRunConfiguration(launchConf);
			final EventBasedExecutionEngine engine = createExecutionEngine(runConf, ExecutionMode.Run);
			eventOccurrences = new LinkedTransferQueue<>();
			String PLUGIN_ID = "org.eclipse.gemoc.execution.sequential.javaengine.ui"; 		
			Job job = new Job(getDebugJobName()) {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					engine.startSynchronous();
					return new Status(IStatus.OK, PLUGIN_ID, "Execution started");
				}
			};
			final TransferQueue<Object> queue = new LinkedTransferQueue<>();
			engine.getExecutionContext().getExecutionPlatform().addEngineAddon(new IEngineAddon() {
				@Override
				public void engineInitialized(IExecutionEngine<?> executionEngine) {
					queue.add(new Object());
				}
			});
			job.schedule();
			boolean result = true;
			if (queue.poll(5000, TimeUnit.MILLISECONDS) != null) {
				eventManager = engine.getAddon(GenericEventManager.class);
				eventManager.addListener(new IEventManagerListener() {
					@Override
					public void eventReceived(EventOccurrence e) {
						eventOccurrences.add(e);
					}
					@Override
					public Set<BehavioralInterface> getBehavioralInterfaces() {
						return eventManager.getBehavioralInterfaces();
					}
				});					
			}
		}catch (CoreException e) {
			e.printStackTrace();
		} catch (EngineContextException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public String processAcceptedEvent(String eventName, Map<String, Object> parameters) {
		EventOccurrence eventOccurrence = createEventOccurance(eventName, parameters);
		eventManager.processEventOccurrence(eventOccurrence);
		return "PASS";
	}
	public String getExposedEvent(String eventName, Map<String, Object> parameters) {
		EventOccurrence eventOccurrence = createEventOccurance(eventName, parameters);
		try {
			EventOccurrence occ = eventOccurrences.poll(10000, TimeUnit.MILLISECONDS);
			while (occ != null && !occ.getEvent().getName().equals(eventOccurrence.getEvent().getName())) {
				occ = eventOccurrences.poll(5000, TimeUnit.MILLISECONDS);
			}
			if (occ != null && occ.getEvent().getName().equals(eventOccurrence.getEvent().getName())) {
				return "PASS";
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "FAIL: The expected event is not received from MUT";
	}
	public EventOccurrence createEventOccurance(String eventName, Map<String, Object> parameters) {
		BehavioralInterface bInterface = this.getBehavioralInterfaceRootElement(this.DSLPath);
		Event event = null;
		for (int i=0; i<bInterface.getEvents().size();i++) {
			event = bInterface.getEvents().get(i);
			if (event.getName().equals(eventName)) {
				break;
			}
		}
		if (event == null) {
			return null;
		}
		EventOccurrence eventOccurance = EventFactory.eINSTANCE.createEventOccurrence();
		eventOccurance.setEvent(event);
		Iterator<String> paramNames = parameters.keySet().iterator();
		for (int i = 0; i < parameters.size(); i++) {
			String paramName = paramNames.next();
			EventParameter parameter = null;
			for (int j = 0; j < event.getParams().size(); j++) {
				parameter = event.getParams().get(j);
				if (parameter.getName().equals(paramName)) {
					break;
				}
			}
			if (parameter == null) {
				return null;
			}
			EventOccurrenceArgument argument = EventFactory.eINSTANCE.createEventOccurrenceArgument();
			argument.setParameter(parameter);
			argument.setValue((Value) EventManagerUtils.convertObjectToValue(parameters.get(paramName)));
			eventOccurance.getArgs().add(argument);
		}
		return eventOccurance;
	}
	private BehavioralInterface getBehavioralInterfaceRootElement(String dslFilePath) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		if (dsl.getEntry("behavioralInterface") != null) {
			String interfacePath = dsl.getEntry("behavioralInterface").getValue().replaceFirst("resource", "plugin");
			Resource interfaceRes = (new ResourceSetImpl()).getResource(URI.createURI(interfacePath), true);
			BehavioralInterface interfaceRootElement = (BehavioralInterface) interfaceRes.getContents().get(0);
			return interfaceRootElement;
		}
		return null;
	}
	private String getDebugJobName() {
		return "Gemoc debug job";
	}
	private String getDslName(String dslFilePath) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		return dsl.getEntry("name").getValue().toString();
	}
}
