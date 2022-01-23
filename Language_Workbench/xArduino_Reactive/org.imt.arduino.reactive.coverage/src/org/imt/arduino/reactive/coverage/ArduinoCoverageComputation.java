package org.imt.arduino.reactive.coverage;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.imt.arduino.reactive.arduino.AnalogPin;
import org.imt.arduino.reactive.arduino.Block;
import org.imt.arduino.reactive.arduino.Board;
import org.imt.arduino.reactive.arduino.DigitalPin;
import org.imt.arduino.reactive.arduino.Instruction;
import org.imt.arduino.reactive.arduino.Module;
import org.imt.arduino.reactive.arduino.Pin;
import org.imt.arduino.reactive.arduino.Project;
import org.imt.arduino.reactive.arduino.Sketch;
import org.imt.tdl.coverage.TDLCoverageUtil;
import org.imt.tdl.coverage.TDLTestCaseCoverage;
import org.imt.tdl.coverage.dslSpecific.IDSLSpecificCoverage;

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
		this.modelObjects = testCaseCoverage.getModelObjects();
		for (int i=0; i<this.modelObjects.size(); i++) {
			EObject modelObject = this.modelObjects.get(i);
			String coverage = this.testCaseCoverage.getTcObjectCoverageStatus().get(i);
			if (modelObject instanceof Project && coverage != TDLCoverageUtil.COVERED) {
				projectCoverage ((Project) modelObject);
			}
			else if (modelObject instanceof Sketch && coverage != TDLCoverageUtil.COVERED) {
				sketchCoverage ((Sketch) modelObject);
			}
			else if (modelObject instanceof Block && coverage != TDLCoverageUtil.COVERED) {
				blockCoverage ((Block) modelObject);
			}
			//The board and its contained elements must be ignored from coverage computation because only the sketch is important
			else if (modelObject instanceof Board) {
				boardCoverage ((Board) modelObject);
			}
			else if (modelObject instanceof Pin) {
				pinCoverage ((Pin) modelObject);
			}
			else if (modelObject instanceof Module) {
				moduleCoverage ((Module) modelObject);
			}
		}
	}

	private void boardCoverage(Board modelObject) {
		// TODO Auto-generated method stub
		Board board = (Board) modelObject;
		int index = this.modelObjects.indexOf(board);
		this.testCaseCoverage.getTcObjectCoverageStatus().set(index, TDLCoverageUtil.NOT_COVERABLE);
	}

	private void projectCoverage(Project project) {
		//Project is the root element of the model, so if there is at least one covered element, it is also covered
		if (this.testCaseCoverage.getTcObjectCoverageStatus().contains(TDLCoverageUtil.COVERED)) {
			int index = this.modelObjects.indexOf(project);
			this.testCaseCoverage.getTcObjectCoverageStatus().set(index, TDLCoverageUtil.COVERED);
		}	
	}

	private void sketchCoverage(Sketch modelObject) {
		//if at least one of the contained objects of the sketch is covered, the sketch is also covered
		Sketch sketch = (Sketch) modelObject;
		Block block = sketch.getBlock();
		if (block != null) {
			int blockIndex = this.modelObjects.indexOf(block);
			String blockCoverage = this.testCaseCoverage.getTcObjectCoverageStatus().get(blockIndex);
			if (blockCoverage != TDLCoverageUtil.COVERED) {
				blockCoverage(block);
				blockCoverage = this.testCaseCoverage.getTcObjectCoverageStatus().get(blockIndex);
			}
			if (blockCoverage == TDLCoverageUtil.COVERED) {
				int sketchIndex = this.modelObjects.indexOf(sketch);
				this.testCaseCoverage.getTcObjectCoverageStatus().set(sketchIndex, TDLCoverageUtil.COVERED);
			}
		}
	}
	
	private void blockCoverage(Block modelObject) {
		//if at least one of its instructions is covered, the block is also covered
		Block block = (Block) modelObject;
		for (Instruction instruction: block.getInstructions()) {
			int instructionIndex = this.modelObjects.indexOf(instruction);
			String instrcutionCoverge = this.testCaseCoverage.getTcObjectCoverageStatus().get(instructionIndex);
			if (instrcutionCoverge == TDLCoverageUtil.COVERED) {
				int blockIndex = this.modelObjects.indexOf(block);
				this.testCaseCoverage.getTcObjectCoverageStatus().set(blockIndex, TDLCoverageUtil.COVERED); 
				break;
			}
		}
		int blockIndex = this.modelObjects.indexOf(block);
		String blockCoverage = this.testCaseCoverage.getTcObjectCoverageStatus().get(blockIndex);
		if (blockCoverage != TDLCoverageUtil.COVERED) {
			this.testCaseCoverage.getTcObjectCoverageStatus().set(blockIndex, TDLCoverageUtil.NOT_COVERED);
		}
	}
	
	private void pinCoverage(Pin pin) {
		//ignoring board-related elements for coverage computation
		int pinIndex = this.modelObjects.indexOf(pin);
		this.testCaseCoverage.getTcObjectCoverageStatus().set(pinIndex, TDLCoverageUtil.NOT_COVERABLE);
//		if (pin instanceof DigitalPin) {
//			digitalPinCoverage ((DigitalPin) pin);
//		}
//		else if (pin instanceof AnalogPin) {
//			analogPinCoverage ((AnalogPin) pin);
//		}
	}

	@SuppressWarnings("unused")
	private void digitalPinCoverage(DigitalPin pin) {
		//if the pin has a module, the coverage of the module is equals to the coverage of its container pin
		int pinIndex = this.modelObjects.indexOf(pin);
		String pinCoverage = this.testCaseCoverage.getTcObjectCoverageStatus().get(pinIndex);
		if (pin.getModule() != null) {
			int moduleIndex =  this.modelObjects.indexOf(pin.getModule());
			String moduleCoverage = this.testCaseCoverage.getTcObjectCoverageStatus().get(moduleIndex);
			if (pinCoverage == TDLCoverageUtil.COVERED && moduleCoverage != TDLCoverageUtil.COVERED) {
				this.testCaseCoverage.getTcObjectCoverageStatus().set(moduleIndex, TDLCoverageUtil.COVERED);
			}
			else if (pinCoverage != TDLCoverageUtil.COVERED && moduleCoverage == TDLCoverageUtil.COVERED) {
				this.testCaseCoverage.getTcObjectCoverageStatus().set(pinIndex, TDLCoverageUtil.COVERED);
			}
		}
	}

	@SuppressWarnings("unused")
	private void analogPinCoverage(AnalogPin pin) {
		//if the pin has a module, the coverage of the module is equals to the coverage of its container pin
		int pinIndex = this.modelObjects.indexOf(pin);
		String pinCoverage = this.testCaseCoverage.getTcObjectCoverageStatus().get(pinIndex);
		if (pin.getModule() != null) {
			int moduleIndex =  this.modelObjects.indexOf(pin.getModule());
			String moduleCoverage = this.testCaseCoverage.getTcObjectCoverageStatus().get(moduleIndex);
			if (moduleCoverage == TDLCoverageUtil.COVERED) {
				this.testCaseCoverage.getTcObjectCoverageStatus().set(pinIndex, TDLCoverageUtil.COVERED);
			}
		}
	}

	private void moduleCoverage(Module modelObject) {
		//ignoring the board related elements in coverage computation
		Module module = (Module) modelObject;
		int moduleIndex = this.modelObjects.indexOf(module);
		this.testCaseCoverage.getTcObjectCoverageStatus().set(moduleIndex, TDLCoverageUtil.NOT_COVERABLE);
		
		//if the container of the module i.e., a pin is covered, the module is also covered
//		int pinIndex  =  this.modelObjects.indexOf(module.eContainer());
//		String pinCoverage  = this.testCaseCoverage.getTcObjectCoverageStatus().get(pinIndex);
//		if (pinCoverage == TDLCoverageUtil.COVERED) {
//			this.testCaseCoverage.getTcObjectCoverageStatus().set(moduleIndex, TDLCoverageUtil.COVERED);
//		}
	}
	
	@Override
	public void ignoreModelObjects(Resource MUTResource) {
		
	}
}
