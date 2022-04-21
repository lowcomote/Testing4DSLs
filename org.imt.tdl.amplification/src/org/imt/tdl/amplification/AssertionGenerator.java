package org.imt.tdl.amplification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.gemoc.executionframework.event.model.event.EventOccurrence;
import org.etsi.mts.tdl.Block;
import org.etsi.mts.tdl.CompoundBehaviour;
import org.etsi.mts.tdl.DataUse;
import org.etsi.mts.tdl.GateReference;
import org.etsi.mts.tdl.Message;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.Target;
import org.etsi.mts.tdl.TestDescription;
import org.etsi.mts.tdl.tdlFactory;
import org.imt.k3tdl.k3dsa.TestDescriptionAspect;
import org.imt.tdl.amplification.utilities.Event2TDLConverter;
import org.imt.tdl.observer.ModelExecutionObserver;
import org.imt.tdl.testResult.TDLTestCaseResult;
import org.imt.tdl.testResult.TDLTestResultUtil;

public class AssertionGenerator extends ModelExecutionObserver{

	Package testSuite;
	List<EventOccurrence> exposedEventOccurrences = Collections.synchronizedList(new ArrayList<>());
	
	@SuppressWarnings("static-access")
	public boolean generateAssertionsForTestCase(TestDescription tdlTestCase) {
		testSuite = (Package) tdlTestCase.eContainer();
		TestDescriptionAspect testCaseRunner = new TestDescriptionAspect();
		
		//activating test case configuration and attaching a model execution observer to the configured model execution engine
		testCaseRunner.activateConfiguration(tdlTestCase);
		modelExecutionEngine = testCaseRunner.launcher(tdlTestCase).getActiveEngine();
		modelExecutionEngine.attach(this);
		//run the test case
		final Runnable testRunner = new Thread() {
			  @Override 
			  public void run() { 
				  testCaseRunner.executeTestCase(tdlTestCase);
			  }
			};

		final ExecutorService executor = Executors.newSingleThreadExecutor();
		@SuppressWarnings("rawtypes")
		final Future future = executor.submit(testRunner);
		executor.shutdown(); // This does not cancel the already-scheduled task.

		try { 
		  future.get(10, TimeUnit.SECONDS); 
		}
		catch (InterruptedException ie) { 
			ie.printStackTrace();
		}
		catch (ExecutionException ee) { 
			ee.printStackTrace();
		}
		catch (TimeoutException te) { 
			//te.printStackTrace();
			System.out.println("TimeoutException -> There is an infinite loop in the test case or model under test");
			future.cancel(true);
		}
		if (!executor.isTerminated()) {
		    executor.shutdownNow(); // If you want to stop the code that hasn't finished
		}
		TDLTestCaseResult result = testCaseRunner.testCaseResult(tdlTestCase);
		//if the new test case cannot be executed completely, or the model did not expose any event occurrence (i.e., no assertion can be generated)
		//the test case must be ignored from the list of new test cases
		if (result.getValue() == TDLTestResultUtil.INCONCLUSIVE || exposedEventOccurrences.size() == 0) {
			return false;
		}
		
		GateReference sourceGate = getSourceGate(tdlTestCase);
		Target targetGate = getTargetGate(tdlTestCase);
		if (sourceGate == null || targetGate == null) {
			return false;
		}
		
		//create an assertion for each exposed event occurrence in the test case
		//its source and target gates must be inverse of existing messages
		for (EventOccurrence exposedEvent:exposedEventOccurrences) {
			Message assertion = tdlFactory.eINSTANCE.createMessage();
			assertion.setSourceGate(targetGate.getTargetGate());
			Target target = tdlFactory.eINSTANCE.createTarget();
			target.setTargetGate(sourceGate);
			assertion.getTarget().add(target);
			assertion.setArgument(createExpectedData(exposedEvent));
			//NOTE: This is an assumption that we only consider test cases containing only Messages
			try {
				Block assertionContainer =  getTestCaseMainBlock(tdlTestCase);
				assertionContainer.getBehaviour().add(assertion);
			}catch (ClassCastException e) {
				System.out.println("ClassCastException: Cannot add the assertion Message to the test case");
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	private DataUse createExpectedData(EventOccurrence exposedEvent) {
		return (new Event2TDLConverter(testSuite)).
				convertEventOccurrence2TdlDataUse(exposedEvent);
	}

	@Override
	public void update(EventOccurrence e) {
		exposedEventOccurrences.add(e);
	}
	
	private Block getTestCaseMainBlock(TestDescription testCase) {
		return ((CompoundBehaviour) testCase.getBehaviourDescription().getBehaviour()).getBlock();
	}
	
	private GateReference getSourceGate (TestDescription testCase) {
		Block messagesContainer = getTestCaseMainBlock(testCase);
		try {
			Message tdlMessage = (Message) messagesContainer.getBehaviour().get(0);
			return tdlMessage.getSourceGate();
		}catch (ClassCastException e) {
			return null;
		}
	}
	
	private Target getTargetGate (TestDescription testCase) {
		Block messagesContainer = getTestCaseMainBlock(testCase);
		try {
			Message tdlMessage = (Message) messagesContainer.getBehaviour().get(0);
			return tdlMessage.getTarget().get(0);
		}catch (ClassCastException e) {
			return null;
		}
	}
}
