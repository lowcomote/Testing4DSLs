package org.imt.k3tdl.k3dsa;

import fr.inria.diverse.k3.al.annotationprocessor.Aspect;
import fr.inria.diverse.k3.al.annotationprocessor.Step;
import org.etsi.mts.tdl.GateType;
import org.imt.k3tdl.k3dsa.GateTypeAspectGateTypeAspectProperties;

@Aspect(className = GateType.class)
@SuppressWarnings("all")
public class GateTypeAspect {
  @Step
  public static void activateGate(final GateType _self) {
    final org.imt.k3tdl.k3dsa.GateTypeAspectGateTypeAspectProperties _self_ = org.imt.k3tdl.k3dsa.GateTypeAspectGateTypeAspectContext.getSelf(_self);
    // #DispatchPointCut_before# void activateGate()
    if (_self instanceof org.etsi.mts.tdl.GateType){
    	fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand command = new fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand() {
    		@Override
    		public void execute() {
    			org.imt.k3tdl.k3dsa.GateTypeAspect._privk3_activateGate(_self_, (org.etsi.mts.tdl.GateType)_self);
    		}
    	};
    	fr.inria.diverse.k3.al.annotationprocessor.stepmanager.IStepManager stepManager = fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepManagerRegistry.getInstance().findStepManager(_self);
    	if (stepManager != null) {
    		stepManager.executeStep(_self, new Object[] {_self}, command, "GateType", "activateGate");
    	} else {
    		command.execute();
    	}
    	;
    };
  }
  
  protected static void _privk3_activateGate(final GateTypeAspectGateTypeAspectProperties _self_, final GateType _self) {
  }
}
