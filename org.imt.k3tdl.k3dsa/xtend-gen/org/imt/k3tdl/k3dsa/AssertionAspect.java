package org.imt.k3tdl.k3dsa;

import fr.inria.diverse.k3.al.annotationprocessor.Aspect;
import fr.inria.diverse.k3.al.annotationprocessor.Step;
import org.etsi.mts.tdl.Assertion;
import org.imt.k3tdl.k3dsa.AssertionAspectAssertionAspectProperties;

@Aspect(className = Assertion.class)
@SuppressWarnings("all")
public class AssertionAspect {
  @Step
  public static void assertVerdict(final Assertion _self) {
    final org.imt.k3tdl.k3dsa.AssertionAspectAssertionAspectProperties _self_ = org.imt.k3tdl.k3dsa.AssertionAspectAssertionAspectContext.getSelf(_self);
    // #DispatchPointCut_before# void assertVerdict()
    if (_self instanceof org.etsi.mts.tdl.Assertion){
    	fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand command = new fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand() {
    		@Override
    		public void execute() {
    			org.imt.k3tdl.k3dsa.AssertionAspect._privk3_assertVerdict(_self_, (org.etsi.mts.tdl.Assertion)_self);
    		}
    	};
    	fr.inria.diverse.k3.al.annotationprocessor.stepmanager.IStepManager stepManager = fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepManagerRegistry.getInstance().findStepManager(_self);
    	if (stepManager != null) {
    		stepManager.executeStep(_self, new Object[] {_self}, command, "Assertion", "assertVerdict");
    	} else {
    		command.execute();
    	}
    	;
    };
  }
  
  protected static void _privk3_assertVerdict(final AssertionAspectAssertionAspectProperties _self_, final Assertion _self) {
  }
}
