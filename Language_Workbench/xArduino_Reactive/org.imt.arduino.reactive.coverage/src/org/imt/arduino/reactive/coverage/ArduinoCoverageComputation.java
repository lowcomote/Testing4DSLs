package org.imt.arduino.reactive.coverage;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.imt.arduino.reactive.arduino.ArduinoPackage;
import org.imt.tdl.coverage.dslSpecific.DSLSpecificCoverageRule;
import org.imt.tdl.coverage.dslSpecific.IDSLSpecificCoverage;

public class ArduinoCoverageComputation implements IDSLSpecificCoverage{

	@Override
	public EList<DSLSpecificCoverageRule> getDSLSpecificCoverageRules() {
		//a Project is covered if at least one of its Sketches are covered
		DSLSpecificCoverageRule rule4project = new DSLSpecificCoverageRule();
		rule4project.setContext(ArduinoPackage.eINSTANCE.getProject());
		rule4project.setContainerCoverageByOne(ECollections.asEList(ArduinoPackage.eINSTANCE.getProject_Sketches()));
		
		//a Sketch is covered if at least one of its Blocks is covered
		DSLSpecificCoverageRule rule4sketch = new DSLSpecificCoverageRule();
		rule4sketch.setContext(ArduinoPackage.eINSTANCE.getSketch());
		rule4sketch.setContainerCoverageByOne(ECollections.asEList(ArduinoPackage.eINSTANCE.getSketch_Block()));
		
		//a Block is covered if at least one of its instructions is covered
		DSLSpecificCoverageRule rule4block = new DSLSpecificCoverageRule();
		rule4block.setContext(ArduinoPackage.eINSTANCE.getBlock());
		rule4block.setContainerCoverageByOne(ECollections.asEList(ArduinoPackage.eINSTANCE.getBlock_Instructions()));
		
		//set dependencies between rules
		rule4project.addDependency(rule4sketch);
		rule4sketch.addDependency(rule4block);
		
		//ignore physical-related elements from coverage computation
		DSLSpecificCoverageRule rule4board = new DSLSpecificCoverageRule();
		rule4board.setContext(ArduinoPackage.eINSTANCE.getBoard());
		rule4board.ignoreClass_subClassesFromCoverage();
		
		DSLSpecificCoverageRule rule4pin = new DSLSpecificCoverageRule();
		rule4pin.setContext(ArduinoPackage.eINSTANCE.getPin());
		rule4pin.ignoreClass_subClassesFromCoverage();
		
		DSLSpecificCoverageRule rule4module = new DSLSpecificCoverageRule();
		rule4module.setContext(ArduinoPackage.eINSTANCE.getModule());
		rule4module.ignoreClass_subClassesFromCoverage();
		
		return ECollections.asEList(rule4project, rule4sketch, rule4block, rule4board, rule4module, rule4pin);
	}

	
	@Override
	public void ignoreModelObjects(Resource MUTResource) {
		
	}
}
