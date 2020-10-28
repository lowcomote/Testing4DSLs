package org.imt.k3tdl.k3dsa;

import fr.inria.diverse.k3.al.annotationprocessor.Aspect;
import fr.inria.diverse.k3.al.annotationprocessor.Step;
import org.etsi.mts.tdl.TestConfiguration;
import org.imt.k3tdl.k3dsa.TestConfigurationAspectTestConfigurationAspectProperties;

@Aspect(className = TestConfiguration.class)
@SuppressWarnings("all")
public class TestConfigurationAspect {
  @Step
  public static void activateConfiguration(final TestConfiguration _self) {
    final org.imt.k3tdl.k3dsa.TestConfigurationAspectTestConfigurationAspectProperties _self_ = org.imt.k3tdl.k3dsa.TestConfigurationAspectTestConfigurationAspectContext.getSelf(_self);
    // #DispatchPointCut_before# void activateConfiguration()
    if (_self instanceof org.etsi.mts.tdl.TestConfiguration){
    	fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand command = new fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand() {
    		@Override
    		public void execute() {
    			org.imt.k3tdl.k3dsa.TestConfigurationAspect._privk3_activateConfiguration(_self_, (org.etsi.mts.tdl.TestConfiguration)_self);
    		}
    	};
    	fr.inria.diverse.k3.al.annotationprocessor.stepmanager.IStepManager stepManager = fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepManagerRegistry.getInstance().findStepManager(_self);
    	if (stepManager != null) {
    		stepManager.executeStep(_self, new Object[] {_self}, command, "TestConfiguration", "activateConfiguration");
    	} else {
    		command.execute();
    	}
    	;
    };
  }
  
  protected static void _privk3_activateConfiguration(final TestConfigurationAspectTestConfigurationAspectProperties _self_, final TestConfiguration _self) {
  }
}
