package org.imt.arduino.reactive.coverage;

import java.util.List;



import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.imt.arduino.reactive.arduino.AnalogPin;
import org.imt.arduino.reactive.arduino.Block;
import org.imt.arduino.reactive.arduino.DigitalPin;
import org.imt.arduino.reactive.arduino.Instruction;
import org.imt.arduino.reactive.arduino.Pin;
import org.imt.arduino.reactive.arduino.Project;
import org.imt.arduino.reactive.arduino.Sketch;
import org.imt.arduino.reactive.arduino.Module;
import org.imt.tdl.coverage.IDSLSpecificCoverage;
import org.imt.tdl.coverage.TDLCoverageUtil;
import org.imt.tdl.coverage.TDLTestCaseCoverage;

public class ArduinoCoverageComputation implements IDSLSpecificCoverage{

	private List<EObject> modelObjects;
	private TDLTestCaseCoverage testCaseCoverage;
	
	@Override
	public List<String> getNewCoverableClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void specializeCoverage(TDLTestCaseCoverage testCaseCoverage) {
		this.testCaseCoverage = testCaseCoverage;
		this.modelObjects = testCaseCoverage.modelObjects;
		for (int i=0; i<this.modelObjects.size(); i++) {
			EObject modelObject = this.modelObjects.get(i);
			String coverage = this.testCaseCoverage.tcObjectCoverageStatus.get(i);
			if (modelObject instanceof Project && coverage != TDLCoverageUtil.COVERED) {
				projectCoverage ((Project) modelObject);
			}
			else if (modelObject instanceof Sketch && coverage != TDLCoverageUtil.COVERED) {
				sketchCoverage ((Sketch) modelObject);
			}
			else if (modelObject instanceof Block && coverage != TDLCoverageUtil.COVERED) {
				blockCoverage ((Block) modelObject);
			}
			else if (modelObject instanceof Pin && coverage != TDLCoverageUtil.COVERED) {
				pinCoverage ((Pin) modelObject);
			}
			else if (modelObject instanceof Module && coverage != TDLCoverageUtil.COVERED) {
				moduleCoverage ((Module) modelObject);
			}
		}
	}

	private void projectCoverage(Project project) {
		//Project is the root element of the model, so if there is at least one covered element, it is also covered
		if (this.testCaseCoverage.tcObjectCoverageStatus.contains(TDLCoverageUtil.COVERED)) {
			int index = this.modelObjects.indexOf(project);
			this.testCaseCoverage.tcObjectCoverageStatus.set(index, TDLCoverageUtil.COVERED);
		}	
	}

	private void sketchCoverage(Sketch modelObject) {
		//if at least one of the contained objects of the sketch is covered, the sketch is also covered
		Sketch sketch = (Sketch) modelObject;
		Block block = sketch.getBlock();
		if (block != null) {
			int blockIndex = this.modelObjects.indexOf(block);
			String blockCoverage = this.testCaseCoverage.tcObjectCoverageStatus.get(blockIndex);
			if (blockCoverage != TDLCoverageUtil.COVERED) {
				blockCoverage(block);
				blockCoverage = this.testCaseCoverage.tcObjectCoverageStatus.get(blockIndex);
			}
			if (blockCoverage == TDLCoverageUtil.COVERED) {
				int sketchIndex = this.modelObjects.indexOf(sketch);
				this.testCaseCoverage.tcObjectCoverageStatus.set(sketchIndex, TDLCoverageUtil.COVERED);
			}
		}
	}
	
	private void blockCoverage(Block modelObject) {
		//if at least one of its instructions is covered, the block is also covered
		Block block = (Block) modelObject;
		for (Instruction instruction: block.getInstructions()) {
			int instructionIndex = this.modelObjects.indexOf(instruction);
			String instrcutionCoverge = this.testCaseCoverage.tcObjectCoverageStatus.get(instructionIndex);
			if (instrcutionCoverge == TDLCoverageUtil.COVERED) {
				int blockIndex = this.modelObjects.indexOf(block);
				this.testCaseCoverage.tcObjectCoverageStatus.set(blockIndex, TDLCoverageUtil.COVERED); 
				break;
			}
		}
		int blockIndex = this.modelObjects.indexOf(block);
		String blockCoverage = this.testCaseCoverage.tcObjectCoverageStatus.get(blockIndex);
		if (blockCoverage != TDLCoverageUtil.COVERED) {
			this.testCaseCoverage.tcObjectCoverageStatus.set(blockIndex, TDLCoverageUtil.NOT_COVERED);
		}
	}
	
	private void pinCoverage(Pin pin) {
		if (pin instanceof DigitalPin) {
			digitalPinCoverage ((DigitalPin) pin);
		}
		else if (pin instanceof AnalogPin) {
			analogPinCoverage ((AnalogPin) pin);
		}
	}

	private void digitalPinCoverage(DigitalPin pin) {
		//if the pin has a module, the coverage of the module is equals to the coverage of its container pin
		int pinIndex = this.modelObjects.indexOf(pin);
		String pinCoverage = this.testCaseCoverage.tcObjectCoverageStatus.get(pinIndex);
		if (pin.getModule() != null) {
			int moduleIndex =  this.modelObjects.indexOf(pin.getModule());
			String moduleCoverage = this.testCaseCoverage.tcObjectCoverageStatus.get(moduleIndex);
			if (pinCoverage == TDLCoverageUtil.COVERED && moduleCoverage != TDLCoverageUtil.COVERED) {
				this.testCaseCoverage.tcObjectCoverageStatus.set(moduleIndex, TDLCoverageUtil.COVERED);
			}
			else if (pinCoverage != TDLCoverageUtil.COVERED && moduleCoverage == TDLCoverageUtil.COVERED) {
				this.testCaseCoverage.tcObjectCoverageStatus.set(pinIndex, TDLCoverageUtil.COVERED);
			}
		}
	}

	private void analogPinCoverage(AnalogPin pin) {
		//if the pin has a module, the coverage of the module is equals to the coverage of its container pin
		int pinIndex = this.modelObjects.indexOf(pin);
		String pinCoverage = this.testCaseCoverage.tcObjectCoverageStatus.get(pinIndex);
		if (pin.getModule() != null) {
			int moduleIndex =  this.modelObjects.indexOf(pin.getModule());
			String moduleCoverage = this.testCaseCoverage.tcObjectCoverageStatus.get(moduleIndex);
			if (moduleCoverage == TDLCoverageUtil.COVERED) {
				this.testCaseCoverage.tcObjectCoverageStatus.set(pinIndex, TDLCoverageUtil.COVERED);
			}
		}
	}

	private void moduleCoverage(Module modelObject) {
		//if the container of the module i.e., a pin is covered, the module is also covered
		Module module = (Module) modelObject;
		int moduleIndex = this.modelObjects.indexOf(module);
		int pinIndex  =  this.modelObjects.indexOf(module.eContainer());
		String pinCoverage  = this.testCaseCoverage.tcObjectCoverageStatus.get(pinIndex);
		if (pinCoverage == TDLCoverageUtil.COVERED) {
			this.testCaseCoverage.tcObjectCoverageStatus.set(moduleIndex, TDLCoverageUtil.COVERED);
		}
	}
	
	@Override
	public void ignoreModelObjects(Resource MUTResource) {
		// TODO Auto-generated method stub
		
	}
}
