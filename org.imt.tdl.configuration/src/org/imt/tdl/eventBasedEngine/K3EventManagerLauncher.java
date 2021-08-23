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
package org.imt.tdl.eventBasedEngine;

import java.util.HashSet;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.Launch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gemoc.dsl.Dsl;
import org.eclipse.gemoc.dsl.debug.ide.adapter.DSLThreadAdapter;
import org.eclipse.gemoc.dsl.debug.ide.launch.AbstractDSLLaunchConfigurationDelegate;
import org.eclipse.gemoc.execution.eventBasedEngine.EventBasedExecutionEngine;
import org.eclipse.gemoc.execution.eventBasedEngine.EventBasedRunConfiguration;
import org.eclipse.gemoc.execution.sequential.javaengine.ui.Activator;
import org.eclipse.gemoc.execution.sequential.javaengine.ui.launcher.GemocSourceLocator;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.BehavioralInterface;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.Event;
import org.eclipse.gemoc.executionframework.behavioralinterface.behavioralInterface.EventParameter;
import org.eclipse.gemoc.executionframework.engine.commons.EngineContextException;
import org.eclipse.gemoc.executionframework.event.manager.EventManagerUtils;
import org.eclipse.gemoc.executionframework.event.manager.GenericEventManager;
import org.eclipse.gemoc.executionframework.event.manager.IEventManagerListener;
import org.eclipse.gemoc.executionframework.event.model.event.EventFactory;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrenceArgument;
import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrenceType;
import org.eclipse.gemoc.executionframework.value.model.value.BooleanAttributeValue;
import org.eclipse.gemoc.executionframework.value.model.value.BooleanObjectAttributeValue;
import org.eclipse.gemoc.executionframework.value.model.value.FloatAttributeValue;
import org.eclipse.gemoc.executionframework.value.model.value.FloatObjectAttributeValue;
import org.eclipse.gemoc.executionframework.value.model.value.IntegerAttributeValue;
import org.eclipse.gemoc.executionframework.value.model.value.IntegerObjectAttributeValue;
import org.eclipse.gemoc.executionframework.value.model.value.SingleObjectValue;
import org.eclipse.gemoc.executionframework.value.model.value.SingleReferenceValue;
import org.eclipse.gemoc.executionframework.value.model.value.StringAttributeValue;
import org.eclipse.gemoc.executionframework.value.model.value.Value;
import org.eclipse.gemoc.executionframework.value.model.value.ValuePackage;
import org.eclipse.gemoc.xdsmlframework.api.core.EngineStatus.RunStatus;
import org.eclipse.gemoc.xdsmlframework.api.core.ExecutionMode;
import org.eclipse.gemoc.xdsmlframework.api.core.IExecutionEngine;
import org.eclipse.gemoc.xdsmlframework.api.engine_addon.IEngineAddon;
import org.imt.gemoc.engine.custom.launcher.CustomEventBasedLauncher;
import org.imt.tdl.testResult.TestResultUtil;

public class K3EventManagerLauncher implements IEventBasedExecutionEngine{
	
	private String MUTPath;
	private String DSLPath;
	private Resource MUTResource = null;
	
	private ILaunchConfiguration launchConf;
	private EventBasedRunConfiguration runConf;
	private EventBasedExecutionEngine executionEngine = null;
	
	private CustomEventBasedLauncher launcher;
	private boolean isDebugMode = false;
	
	// progress monitor used during launch; useful for operations that wish to
	// contribute to the progress bar
	protected IProgressMonitor launchProgressMonitor = null;

	private final ILaunchConfigurationType launchType = DebugPlugin.getDefault().getLaunchManager()
			.getLaunchConfigurationType("org.eclipse.gemoc.execution.sequential.javaengine.ui.launcher");

	@Override
	public void setUp(String MUTPath, String DSLPath){
		this.MUTPath = MUTPath;
		this.DSLPath = DSLPath;
		final String languageName = this.getDslName(DSLPath);
		final String implemRelId = this.getImplRel(DSLPath);
		final String subtypeRelId = this.getSubRel(DSLPath);
		final Set<String> implRelIds = new HashSet<>();
		implRelIds.add(implemRelId);
		final Set<String> subtypeRelIds =  new HashSet<>();
		subtypeRelIds.add(subtypeRelId);
		
		try {
			this.launchConf = getLaunchConfiguration(MUTPath, languageName, implRelIds, subtypeRelIds);
			this.runConf = new EventBasedRunConfiguration(launchConf);
		}catch (CoreException e) {
			e.printStackTrace();
		}
		this.launcher = new CustomEventBasedLauncher();
	}
	
	private GenericEventManager eventManager = null;
	private LinkedTransferQueue<EventOccurrence> eventOccurrences = new LinkedTransferQueue<EventOccurrence>();
	
	
	@Override
	public String processAcceptedEvent(String eventName, Map<String, Object> parameters) {
		EventOccurrence eventOccurrence = createEventOccurance(EventOccurrenceType.ACCEPTED, eventName, parameters);	
		this.eventManager.processEventOccurrence(eventOccurrence);
		if (isDebugMode) {
			IDebugTarget[] debugTargets = DebugPlugin.getDefault().getLaunchManager().getDebugTargets();
			IThread[] testCaseDebuggerThreads = null;
			try {
				testCaseDebuggerThreads = debugTargets[0].getThreads();
			} catch (DebugException e) {
				e.printStackTrace();
			}
			//get the thread running the test case debugger to suspend it during model debugging
			DSLThreadAdapter testCaseDebugThread = (DSLThreadAdapter) testCaseDebuggerThreads[0];
			while (this.executionEngine.getRunningStatus() == RunStatus.Running) {
				try {
					testCaseDebugThread.suspend();
				} catch (DebugException e) {
					e.printStackTrace();
				}
			}
		}
		return "PASS";
	}

	@Override
	public String assertExposedEvent(String eventName, Map<String, Object> parameters) {
		EventOccurrence eventOccurrence = createEventOccurance(EventOccurrenceType.EXPOSED, eventName, parameters);
		if (eventOccurrence == null) {
			return "FAIL: The expected event does not match to the interface or its parameters does not exist in the MUT";
		}
		EventOccurrence occ;
		try {
			occ = this.eventOccurrences.poll(10000, TimeUnit.MILLISECONDS);
			if (occ != null && this.equalEventOccurrences(occ, eventOccurrence)) {
				return "PASS";
			}else {
				String result= "FAIL: The expected event is not received from MUT";
				if (occ == null) {
					result += "\nThere is no received event";
				}else {
					result += "\nThe received event is:\n" + this.eventOccurenceToString(occ);
				}
				return result;
			}
		} catch (InterruptedException e) {
			return e.getMessage();
		}
	}
	
	private ILaunchConfiguration getLaunchConfiguration(String MUTPath, String languageName, Set<String> implRelIds, Set<String> subtypeRelIds) throws CoreException {
		final ILaunchConfigurationWorkingCopy configuration = launchType.newInstance(null, "event_basedTesting");
		configuration.setAttribute(AbstractDSLLaunchConfigurationDelegate.RESOURCE_URI, MUTPath);
		configuration.setAttribute(EventBasedRunConfiguration.LAUNCH_SELECTED_LANGUAGE, languageName);
		configuration.setAttribute(EventBasedRunConfiguration.WAIT_FOR_EVENT, true);
		configuration.setAttribute(EventBasedRunConfiguration.IMPL_REL_IDS, implRelIds);
		configuration.setAttribute(EventBasedRunConfiguration.SUBTYPE_REL_IDS, subtypeRelIds);
		configuration.setAttribute(EventBasedRunConfiguration.LAUNCH_BREAK_START, true);
		configuration.setAttribute(EventBasedRunConfiguration.DEBUG_MODEL_ID, Activator.DEBUG_MODEL_ID);
		return configuration;
	}

	@Override
	public void startEngine() {
		try {
			this.executionEngine = (EventBasedExecutionEngine) launcher.createExecutionEngine(this.runConf, ExecutionMode.Run);
		}catch (CoreException | EngineContextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String PLUGIN_ID = "org.eclipse.gemoc.execution.sequential.javaengine.ui"; 		
		Job job = new Job(getDebugJobName()) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				executionEngine.startSynchronous();
				return new Status(IStatus.OK, PLUGIN_ID, "Execution started");
			}
		};
		final TransferQueue<Object> queue = new LinkedTransferQueue<>();
		this.executionEngine.getExecutionContext().getExecutionPlatform().addEngineAddon(new IEngineAddon() {
			@Override
			public void engineInitialized(IExecutionEngine<?> executionEngine) {
				queue.add(new Object());
			}
		});
		job.schedule();
		addEventManagerListener(queue);
	}
	
	@Override
	public void launchModelDebugger() {
		isDebugMode = true;
		Launch debugLaunch = new Launch(launchConf, ILaunchManager.DEBUG_MODE, new GemocSourceLocator());
		DebugPlugin.getDefault().getLaunchManager().addLaunch(debugLaunch);	
		try{
			launcher.launch(launchConf, ILaunchManager.DEBUG_MODE, debugLaunch, new NullProgressMonitor());
			this.executionEngine = (EventBasedExecutionEngine) launcher.getExecutionEngine();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final TransferQueue<Object> queue = new LinkedTransferQueue<>();
		this.executionEngine.getExecutionContext().getExecutionPlatform().addEngineAddon(new IEngineAddon() {
			@Override
			public void engineInitialized(IExecutionEngine<?> executionEngine) {
				queue.add(new Object());
			}
		});
		addEventManagerListener(queue);
	}
	
	private void addEventManagerListener(TransferQueue<Object> queue) {
		try {
			if (queue.poll(5000, TimeUnit.MILLISECONDS) != null) {
				this.eventManager = (GenericEventManager) this.executionEngine.getAddonsTypedBy(GenericEventManager.class).stream().findFirst().orElse(null);
				this.eventManager.addListener(new IEventManagerListener() {
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
	}
	
	private void suspendTestCaseDebugger() {
		IDebugTarget[] debugTargets = DebugPlugin.getDefault().getLaunchManager().getDebugTargets();
		IThread[] testCaseDebuggerThreads = null;
		try {
			testCaseDebuggerThreads = debugTargets[0].getThreads();
		} catch (DebugException e) {
			e.printStackTrace();
		}
		//get the thread running the test case debugger to suspend it during model debugging
		DSLThreadAdapter testCaseDebugThread = (DSLThreadAdapter) testCaseDebuggerThreads[0];
		while (this.executionEngine.getRunningStatus() == RunStatus.Running) {
			try {
				testCaseDebugThread.suspend();
			} catch (DebugException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public String sendStopEvent(boolean checkStatus) {
		String result = null;
		if (checkStatus) {
			if (this.executionEngine.getRunningStatus() == RunStatus.WaitingForEvent) {
				if (this.eventOccurrences.size()>0) {
					result = "FAIL:There are extra received events";
				}else {
					result = "PASS";
				}
			}else if (this.executionEngine.getRunningStatus() == RunStatus.Running) {
				result = "FAIL:Infinite loop in the Model";
			}
		}
		else {
			result = "PASS";
		}
		this.executionEngine.stop();
		this.executionEngine.dispose();
		return result;
	}
	
	private EventOccurrence createEventOccurance(EventOccurrenceType eventType, String eventName, Map<String, Object> parameters) {
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
		eventOccurance.setType(eventType);
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
			if (parameters.get(paramName) == null) {
				return null;
			}
			argument.setValue((Value) EventManagerUtils.convertObjectToValue(parameters.get(paramName)));
			eventOccurance.getArgs().add(argument);
		}
		return eventOccurance;
	}
	
	private Boolean equalEventOccurrences(EventOccurrence e1, EventOccurrence e2) {
		if (!e1.getEvent().getName().equals(e2.getEvent().getName())) {
			return false;
		}
		if (!e1.getType().equals(e2.getType())){
			return false;
		}
		if (e1.getArgs().size() != e2.getArgs().size()) {
			return false;
		}
		for (int i=0; i<e1.getArgs().size(); i++) {
			EventOccurrenceArgument e1Arg = e1.getArgs().get(i);
			EventOccurrenceArgument e2Arg = e2.getArgs().get(i);
			if (!e1Arg.getParameter().getName().equals(e2Arg.getParameter().getName())) {
				return false;
			}
			if (!e1Arg.getParameter().getType().equals(e2Arg.getParameter().getType())) {
				return false;
			}
			EObject value1;
			EObject value2;
			switch (e1Arg.getValue().eClass().getClassifierID()) {
			case ValuePackage.SINGLE_REFERENCE_VALUE:
				value1 = ((SingleReferenceValue) e1Arg.getValue()).getReferenceValue();
				value2 = ((SingleReferenceValue) e2Arg.getValue()).getReferenceValue();
				if (value1 == value2) {
					return true;
				}
				String svalue1 = value1.toString();
				String svalue2 = value2.toString();
				if (svalue1.contains("(") && svalue2.contains("(")) {
					svalue1 = svalue1.replace(svalue1.substring(svalue1.indexOf("@"), svalue1.indexOf("(")), "_");
					svalue2 = svalue2.replace(svalue2.substring(svalue2.indexOf("@"), svalue2.indexOf("(")), "_");
				}else {
					svalue1 = svalue1.substring(0, svalue1.indexOf("@"));
					svalue2 = svalue2.substring(0, svalue2.indexOf("@"));
				}
				if (svalue1.equals(svalue2)) {
					return true;
				}
				break;
			case ValuePackage.SINGLE_OBJECT_VALUE:
				value1 = ((SingleObjectValue) e1Arg.getValue()).getObjectValue();
				value2 = ((SingleObjectValue) e2Arg.getValue()).getObjectValue();
				if (value1.toString().equals(value2.toString())) {
					return true;
				}
				break;
			case ValuePackage.BOOLEAN_ATTRIBUTE_VALUE:
				if (((BooleanAttributeValue) e1Arg.getValue()).isAttributeValue()
						== ((BooleanAttributeValue) e2Arg.getValue()).isAttributeValue()){
					return true;
				}
				break;
			case ValuePackage.BOOLEAN_OBJECT_ATTRIBUTE_VALUE:
				if (((BooleanObjectAttributeValue) e1Arg.getValue()).getAttributeValue()
						== ((BooleanObjectAttributeValue) e2Arg.getValue()).getAttributeValue()){
					return true;
				}
				break;
			case ValuePackage.INTEGER_ATTRIBUTE_VALUE:
				if (((IntegerAttributeValue) e1Arg.getValue()).getAttributeValue()
						== ((IntegerAttributeValue) e2Arg.getValue()).getAttributeValue()){
					return true;
				}
				break;
			case ValuePackage.INTEGER_OBJECT_ATTRIBUTE_VALUE:
				if (((IntegerObjectAttributeValue) e1Arg.getValue()).getAttributeValue()
						== ((IntegerObjectAttributeValue) e2Arg.getValue()).getAttributeValue()){
					return true;
				}
				break;
			case ValuePackage.FLOAT_ATTRIBUTE_VALUE:
				if (((FloatAttributeValue) e1Arg.getValue()).getAttributeValue()
						== ((FloatAttributeValue) e2Arg.getValue()).getAttributeValue()){
					return true;
				}
				break;
			case ValuePackage.FLOAT_OBJECT_ATTRIBUTE_VALUE:
				if (((FloatObjectAttributeValue) e1Arg.getValue()).getAttributeValue()
						== ((FloatObjectAttributeValue) e2Arg.getValue()).getAttributeValue()){
					return true;
				}
				break;
			case ValuePackage.STRING_ATTRIBUTE_VALUE:
				if (((StringAttributeValue) e1Arg.getValue()).getAttributeValue()
						.equals(((StringAttributeValue) e2Arg.getValue()).getAttributeValue())){
					return true;
				}
				break;
			}
		}
		return false;
	}
	
	private String eventOccurenceToString (EventOccurrence occurrence) {
		String result = occurrence.getEvent().getName();
		if (occurrence.getArgs().size()>0) {
			result += " (";
		}
		for (int i=0; i<occurrence.getArgs().size(); i++) {
			EventOccurrenceArgument arg = occurrence.getArgs().get(i);
			EObject value;
			switch (arg.getValue().eClass().getClassifierID()) {
			case ValuePackage.SINGLE_REFERENCE_VALUE:
				value = ((SingleReferenceValue) arg.getValue()).getReferenceValue();
				String label = TestResultUtil.getInstance().eObjectLabelProvider(value);
				result += label.substring(label.lastIndexOf(":")+1);
				break;
			case ValuePackage.SINGLE_OBJECT_VALUE:
				value = ((SingleObjectValue) arg.getValue()).getObjectValue();
				label = TestResultUtil.getInstance().eObjectLabelProvider(value);
				result += label.substring(label.lastIndexOf(":")+1);
				break;
			case ValuePackage.BOOLEAN_ATTRIBUTE_VALUE:
				result += ((BooleanAttributeValue) arg.getValue()).isAttributeValue();
				break;
			case ValuePackage.BOOLEAN_OBJECT_ATTRIBUTE_VALUE:
				result += ((BooleanObjectAttributeValue) arg.getValue()).getAttributeValue().toString();
				break;
			case ValuePackage.INTEGER_ATTRIBUTE_VALUE:
				result += ((IntegerAttributeValue) arg.getValue()).getAttributeValue();
				break;
			case ValuePackage.INTEGER_OBJECT_ATTRIBUTE_VALUE:
				result += ((IntegerObjectAttributeValue) arg.getValue()).getAttributeValue().toString();
				break;
			case ValuePackage.FLOAT_ATTRIBUTE_VALUE:
				result += ((FloatAttributeValue) arg.getValue()).getAttributeValue();
				break;
			case ValuePackage.FLOAT_OBJECT_ATTRIBUTE_VALUE:
				result += ((FloatObjectAttributeValue) arg.getValue()).getAttributeValue().toString();
				break;
			case ValuePackage.STRING_ATTRIBUTE_VALUE:
				result += ((StringAttributeValue) arg.getValue()).getAttributeValue();
				break;
			}
			if (i < occurrence.getArgs().size()-1) {
				result += ", ";
			}
		}
		if (occurrence.getArgs().size()>0) {
			result += ")";
		}
		return result;
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

	private String getDslName(String dslFilePath) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		return dsl.getEntry("name").getValue().toString();
	}
	
	private String getImplRel(String dslFilePath) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		if (dsl.getEntry("implemRelId") == null) {
			return null;
		}
		return dsl.getEntry("implemRelId").getValue().toString();
	}
	
	private String getSubRel(String dslFilePath) {
		Resource dslRes = (new ResourceSetImpl()).getResource(URI.createURI(dslFilePath), true);
		Dsl dsl = (Dsl)dslRes.getContents().get(0);
		if (dsl.getEntry("subtypeRelId") == null) {
			return null;
		}
		return dsl.getEntry("subtypeRelId").getValue().toString();
	}
	
	public void setModelResource(Resource resource) {
		this.MUTResource = resource;
	}
	
	public Resource getModelResource() {
		if (this.executionEngine == null) {
			this.MUTResource = (new ResourceSetImpl()).getResource(URI.createURI(MUTPath), true);
		}else{
			this.MUTResource = this.executionEngine.getExecutionContext().getResourceModel();
		}
		return this.MUTResource;
	}
	
	private String getDebugJobName() {
		return "Gemoc debug job";
	}

	@Override
	public Boolean isEngineStarted() {
		return this.executionEngine != null;
	}
}
