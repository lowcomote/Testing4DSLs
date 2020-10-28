package org.imt.k3tdl.k3dsa;

import fr.inria.diverse.k3.al.annotationprocessor.Aspect;
import fr.inria.diverse.k3.al.annotationprocessor.Step;
import org.etsi.mts.tdl.GateReference;
import org.imt.k3tdl.k3dsa.GateReferenceAspectGateReferenceAspectProperties;

@Aspect(className = GateReference.class)
@SuppressWarnings("all")
public class GateReferenceAspect {
  @Step
  public static void referenceToGate(final GateReference _self) {
    final org.imt.k3tdl.k3dsa.GateReferenceAspectGateReferenceAspectProperties _self_ = org.imt.k3tdl.k3dsa.GateReferenceAspectGateReferenceAspectContext.getSelf(_self);
    // #DispatchPointCut_before# void referenceToGate()
    if (_self instanceof org.etsi.mts.tdl.GateReference){
    	fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand command = new fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand() {
    		@Override
    		public void execute() {
    			org.imt.k3tdl.k3dsa.GateReferenceAspect._privk3_referenceToGate(_self_, (org.etsi.mts.tdl.GateReference)_self);
    		}
    	};
    	fr.inria.diverse.k3.al.annotationprocessor.stepmanager.IStepManager stepManager = fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepManagerRegistry.getInstance().findStepManager(_self);
    	if (stepManager != null) {
    		stepManager.executeStep(_self, new Object[] {_self}, command, "GateReference", "referenceToGate");
    	} else {
    		command.execute();
    	}
    	;
    };
  }
  
  protected static void _privk3_referenceToGate(final GateReferenceAspectGateReferenceAspectProperties _self_, final GateReference _self) {
  }
}
