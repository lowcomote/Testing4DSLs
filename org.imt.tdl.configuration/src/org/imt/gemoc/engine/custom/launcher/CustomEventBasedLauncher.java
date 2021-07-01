/*******************************************************************************
 * Copyright (c) 2016, 2017 Inria and others.

 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Inria - initial API and implementation
 *******************************************************************************/
package org.imt.gemoc.engine.custom.launcher;

import java.util.Set;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gemoc.commons.eclipse.messagingsystem.api.MessagingSystem;
import org.eclipse.gemoc.commons.eclipse.ui.ViewHelper;
import org.eclipse.gemoc.execution.eventBasedEngine.EventBasedExecutionEngine;
import org.eclipse.gemoc.execution.eventBasedEngine.EventBasedModelExecutionContext;
import org.eclipse.gemoc.execution.eventBasedEngine.EventBasedRunConfiguration;
import org.eclipse.gemoc.execution.sequential.javaengine.ui.Activator;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.eclipse.gemoc.executionframework.engine.commons.EngineContextException;
import org.eclipse.gemoc.executionframework.engine.ui.launcher.AbstractSequentialGemocLauncher;
import org.eclipse.gemoc.executionframework.event.manager.GenericEventManager;
import org.eclipse.gemoc.executionframework.event.manager.IEventManagerListener;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;
import org.eclipse.gemoc.executionframework.ui.views.engine.EnginesStatusView;
import org.eclipse.gemoc.xdsmlframework.api.core.ExecutionMode;
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionEngine;
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;

public class CustomEventBasedLauncher
		extends AbstractSequentialGemocLauncher<EventBasedModelExecutionContext, EventBasedRunConfiguration> {

	public final static String TYPE_ID = Activator.PLUGIN_ID + ".launcher";
	public GenericEventManager eventManager = null;
	public LinkedTransferQueue<EventOccurrence> eventOccurrences = new LinkedTransferQueue<EventOccurrence>();
	
	@Override
	public IExecutionEngine<EventBasedModelExecutionContext> createExecutionEngine(
			EventBasedRunConfiguration runConfiguration, ExecutionMode executionMode)
			throws CoreException, EngineContextException {
		EventBasedExecutionEngine executionEngine = new EventBasedExecutionEngine();
		EventBasedModelExecutionContext executioncontext = new EventBasedModelExecutionContext(runConfiguration,
				executionMode);
		executioncontext.getExecutionPlatform().getModelLoader().setProgressMonitor(this.launchProgressMonitor);
		executioncontext.initializeResourceModel();
		executionEngine.initialize(executioncontext);
		String PLUGIN_ID = "org.eclipse.gemoc.execution.sequential.javaengine.ui"; 		
		Job job = new Job(getDebugJobName()) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				executionEngine.startSynchronous();
				return new Status(IStatus.OK, PLUGIN_ID, "Execution started");
			}
		};
		final TransferQueue<Object> queue = new LinkedTransferQueue<>();
		executionEngine.getExecutionContext().getExecutionPlatform().addEngineAddon(new IEngineAddon() {
			@Override
			public void engineInitialized(IExecutionEngine<?> executionEngine) {
				queue.add(new Object());
			}
		});
		job.schedule();
		try {
			if (queue.poll(5000, TimeUnit.MILLISECONDS) != null) {
				eventManager = executionEngine.getAddon(GenericEventManager.class);
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
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return executionEngine;
	}

	@Override
	protected String getLaunchConfigurationTypeID() {
		return TYPE_ID;
	}

	@Override
	protected String getDebugJobName(ILaunchConfiguration configuration, EObject firstInstruction) {
		return "Gemoc debug job";
	}

	@Override
	protected String getPluginID() {
		return Activator.PLUGIN_ID;
	}

	@Override
	public String getModelIdentifier() {
		return Activator.DEBUG_MODEL_ID;
	}

	@Override
	protected void prepareViews() {
		ViewHelper.retrieveView(EnginesStatusView.ID);
	}

	@Override
	protected EventBasedRunConfiguration parseLaunchConfiguration(ILaunchConfiguration configuration)
			throws CoreException {
		return new EventBasedRunConfiguration(configuration);
	}

	@Override
	protected MessagingSystem getMessagingSystem() {
		return Activator.getDefault().getMessaggingSystem();
	}

	@Override
	protected void setDefaultsLaunchConfiguration(ILaunchConfigurationWorkingCopy configuration) {

	}

	@Override
	protected void error(String message, Exception e) {
		Activator.error(message, e);
	}
	private String getDebugJobName() {
		return "Gemoc debug job";
	}
}
