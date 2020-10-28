package org.imt.k3tdl.k3dsa;

import fr.inria.diverse.k3.al.annotationprocessor.Aspect;
import fr.inria.diverse.k3.al.annotationprocessor.Step;
import org.etsi.mts.tdl.TestDescription;
import org.imt.k3tdl.k3dsa.TestDescriptionAspectTestDescriptionAspectProperties;

@Aspect(className = TestDescription.class)
@SuppressWarnings("all")
public class TestDescriptionAspect {
  @Step
  public static void executeTestCase(final TestDescription _self) {
    final org.imt.k3tdl.k3dsa.TestDescriptionAspectTestDescriptionAspectProperties _self_ = org.imt.k3tdl.k3dsa.TestDescriptionAspectTestDescriptionAspectContext.getSelf(_self);
    // #DispatchPointCut_before# void executeTestCase()
    if (_self instanceof org.etsi.mts.tdl.TestDescription){
    	fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand command = new fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand() {
    		@Override
    		public void execute() {
    			org.imt.k3tdl.k3dsa.TestDescriptionAspect._privk3_executeTestCase(_self_, (org.etsi.mts.tdl.TestDescription)_self);
    		}
    	};
    	fr.inria.diverse.k3.al.annotationprocessor.stepmanager.IStepManager stepManager = fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepManagerRegistry.getInstance().findStepManager(_self);
    	if (stepManager != null) {
    		stepManager.executeStep(_self, new Object[] {_self}, command, "TestDescription", "executeTestCase");
    	} else {
    		command.execute();
    	}
    	;
    };
  }
  
  public static void newMed(final TestDescription _self) {
    final org.imt.k3tdl.k3dsa.TestDescriptionAspectTestDescriptionAspectProperties _self_ = org.imt.k3tdl.k3dsa.TestDescriptionAspectTestDescriptionAspectContext.getSelf(_self);
    // #DispatchPointCut_before# void newMed()
    if (_self instanceof org.etsi.mts.tdl.TestDescription){
    	org.imt.k3tdl.k3dsa.TestDescriptionAspect._privk3_newMed(_self_, (org.etsi.mts.tdl.TestDescription)_self);
    };
  }
  
  protected static void _privk3_executeTestCase(final TestDescriptionAspectTestDescriptionAspectProperties _self_, final TestDescription _self) {
  }
  
  protected static void _privk3_newMed(final TestDescriptionAspectTestDescriptionAspectProperties _self_, final TestDescription _self) {
  }
}
