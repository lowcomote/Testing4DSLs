package org.imt.k3tdl.k3dsa;

import fr.inria.diverse.k3.al.annotationprocessor.Aspect;
import fr.inria.diverse.k3.al.annotationprocessor.Step;
import org.etsi.mts.tdl.DataUse;
import org.imt.k3tdl.k3dsa.DataUseAspectDataUseAspectProperties;

@Aspect(className = DataUse.class)
@SuppressWarnings("all")
public class DataUseAspect {
  @Step
  public static void setData(final DataUse _self) {
    final org.imt.k3tdl.k3dsa.DataUseAspectDataUseAspectProperties _self_ = org.imt.k3tdl.k3dsa.DataUseAspectDataUseAspectContext.getSelf(_self);
    // #DispatchPointCut_before# void setData()
    if (_self instanceof org.etsi.mts.tdl.DataUse){
    	fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand command = new fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepCommand() {
    		@Override
    		public void execute() {
    			org.imt.k3tdl.k3dsa.DataUseAspect._privk3_setData(_self_, (org.etsi.mts.tdl.DataUse)_self);
    		}
    	};
    	fr.inria.diverse.k3.al.annotationprocessor.stepmanager.IStepManager stepManager = fr.inria.diverse.k3.al.annotationprocessor.stepmanager.StepManagerRegistry.getInstance().findStepManager(_self);
    	if (stepManager != null) {
    		stepManager.executeStep(_self, new Object[] {_self}, command, "DataUse", "setData");
    	} else {
    		command.execute();
    	}
    	;
    };
  }
  
  protected static void _privk3_setData(final DataUseAspectDataUseAspectProperties _self_, final DataUse _self) {
  }
}
