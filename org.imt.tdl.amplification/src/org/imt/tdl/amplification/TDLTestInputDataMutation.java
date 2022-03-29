package org.imt.tdl.amplification;

import java.util.ArrayList;
import java.util.List;

import org.etsi.mts.tdl.TestDescription;

public class TDLTestInputDataMutation {
	
	public List<TestDescription> generateNewTestsByInputMutation (TestDescription tdlTestCase) {
		List<TestDescription> newTestCases = new ArrayList<>();
		//TODO: definition of mutation operators, WODEL or java? 
		/*1. primitive values used in the test cases (by memberAssignment or ParameterBinding)
		 * modification
		 * replacement
		 * 
		 * 2. events
		 * addition : duplication of existing & creation of new events based on the interface
		 * deletion
		 * reordering
		 * modification of event parameters based on model elements used in the event parameters
		 */
		//TODO: create new test cases with new data
		return newTestCases;
	}
}
