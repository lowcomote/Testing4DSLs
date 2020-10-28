package org.imt.k3tdl.k3dsa;

import fr.inria.diverse.k3.al.annotationprocessor.Aspect;
import fr.inria.diverse.k3.al.annotationprocessor.Step;
import org.etsi.mts.tdl.ComponentType;
import org.imt.k3tdl.k3dsa.ComponentTypeAspectComponentTypeAspectProperties;

@Aspect(className = ComponentType.class)
@SuppressWarnings("all")
public class ComponentTypeAspect {
  @Step
  public static void activateComponent(final ComponentType _self) {
    final org.imt.k3tdl.k3dsa.ComponentTypeAspectComponentTypeAspectProperties _self_ = org.imt.k3tdl.k3dsa.ComponentTypeAspectComponentTypeAspectContext.getSelf(_self);
    // #DispatchPointCut_before# void activateComponent()
    if (_self instanceof org.etsi.mts.tdl.ComponentType){
    	fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand command = new fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand() {
    		@Override
    		public void execute() {
    			org.imt.k3tdl.k3dsa.ComponentTypeAspect._privk3_activateComponent(_self_, (org.etsi.mts.tdl.ComponentType)_self);
    		}
    	};
    	fr.inria.diverse.k3.al.annotationprocessor.stepmanager.IStepManager stepManager = fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepManagerRegistry.getInstance().findStepManager(_self);
    	if (stepManager != null) {
    		stepManager.executeStep(_self, new Object[] {_self}, command, "ComponentType", "activateComponent");
    	} else {
    		command.execute();
    	}
    	;
    };
  }
  
  protected static void _privk3_activateComponent(final ComponentTypeAspectComponentTypeAspectProperties _self_, final ComponentType _self) {
  }
}
