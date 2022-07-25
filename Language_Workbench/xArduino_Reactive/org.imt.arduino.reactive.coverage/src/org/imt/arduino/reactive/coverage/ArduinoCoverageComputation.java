package org.imt.arduino.reactive.coverage;

import org.eclipse.emf.ecore.resource.Resource;
import org.imt.tdl.coverage.dslSpecific.IDSLSpecificCoverage;

import DSLSpecificCoverage.DomainSpecificCoverage;

public class ArduinoCoverageComputation implements IDSLSpecificCoverage{

	@Override
	public DomainSpecificCoverage getDomainSpecificCoverage() {
		//a Project is covered if at least one of its Sketches are covered
		//a Sketch is covered if at least one of its Blocks is covered
		//a Block is covered if at least one of its instructions is covered
		//ignore physical-related elements from coverage computation
		return null;
	}

	
	@Override
	public void ignoreModelObjects(Resource MUTResource) {
		
	}
}
