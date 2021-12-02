package org.imt.arduino.reactive.coverage;

import java.util.List;


import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.imt.arduino.reactive.arduino.AnalogPin;
import org.imt.arduino.reactive.arduino.DigitalPin;
import org.imt.arduino.reactive.arduino.Pin;
import org.imt.arduino.reactive.arduino.Project;
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
			if (modelObject instanceof Project) {
				projectCoverage ((Project) modelObject);
			}
			if (modelObject instanceof Pin) {
				pinCoverage ((Pin) modelObject);
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
		int index = this.modelObjects.indexOf(pin);
		String pinCoverage = this.testCaseCoverage.tcObjectCoverageStatus.get(index);
		if (pin.getModule() != null) {
			int moduleIndex =  this.modelObjects.indexOf(pin.getModule());
			this.testCaseCoverage.tcObjectCoverageStatus.set(moduleIndex, pinCoverage);
		}
	}

	private void analogPinCoverage(AnalogPin pin) {
		//if the pin has a module, the coverage of the module is equals to the coverage of its container pin
		int index = this.modelObjects.indexOf(pin);
		String pinCoverage = this.testCaseCoverage.tcObjectCoverageStatus.get(index);
		if (pin.getModule() != null) {
			int moduleIndex =  this.modelObjects.indexOf(pin.getModule());
			this.testCaseCoverage.tcObjectCoverageStatus.set(moduleIndex, pinCoverage);
		}
	}

	@Override
	public void ignoreModelObjects(Resource MUTResource) {
		// TODO Auto-generated method stub
		
	}
}
