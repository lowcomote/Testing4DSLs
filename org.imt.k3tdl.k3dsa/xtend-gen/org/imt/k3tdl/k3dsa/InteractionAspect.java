package org.imt.k3tdl.k3dsa;

import fr.inria.diverse.k3.al.annotationprocessor.Aspect;
import fr.inria.diverse.k3.al.annotationprocessor.Step;
import org.etsi.mts.tdl.Interaction;
import org.imt.k3tdl.k3dsa.InteractionAspectInteractionAspectProperties;

@Aspect(className = Interaction.class)
@SuppressWarnings("all")
public class InteractionAspect {
  @Step
  public static void activateInteraction(final Interaction _self) {
    final org.imt.k3tdl.k3dsa.InteractionAspectInteractionAspectProperties _self_ = org.imt.k3tdl.k3dsa.InteractionAspectInteractionAspectContext.getSelf(_self);
    // #DispatchPointCut_before# void activateInteraction()
    if (_self instanceof org.etsi.mts.tdl.Interaction){
    	fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand command = new fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand() {
    		@Override
    		public void execute() {
    			org.imt.k3tdl.k3dsa.InteractionAspect._privk3_activateInteraction(_self_, (org.etsi.mts.tdl.Interaction)_self);
    		}
    	};
    	fr.inria.diverse.k3.al.annotationprocessor.stepmanager.IStepManager stepManager = fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepManagerRegistry.getInstance().findStepManager(_self);
    	if (stepManager != null) {
    		stepManager.executeStep(_self, new Object[] {_self}, command, "Interaction", "activateInteraction");
    	} else {
    		command.execute();
    	}
    	;
    };
  }
  
  protected static void _privk3_activateInteraction(final InteractionAspectInteractionAspectProperties _self_, final Interaction _self) {
  }
}
