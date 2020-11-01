package org.imt.k3tdl.k3dsa

import fr.inria.diverse.k3.al.annotationprocessor.Aspect
import fr.inria.diverse.k3.al.annotationprocessor.Step
import fr.inria.diverse.k3.al.annotationprocessor.OverrideAspectMethod
import org.etsi.mts.tdl.Time
import org.etsi.mts.tdl.Timer
import org.etsi.mts.tdl.TimeConstraint
import org.etsi.mts.tdl.TimeLabelUseKind
import org.etsi.mts.tdl.TimeLabelUse
import org.etsi.mts.tdl.TimeLabel
import org.imt.k3tdl.k3dsa.SimpleDataTypeAspect
import org.imt.k3tdl.k3dsa.DynamicDataUseAspect

@Aspect (className = Time)
class TimeAspect extends SimpleDataTypeAspect{
	def void op(){
		
	}
}
@Aspect (className = TimeLabel)
class TimeLabelAspect{
	def void op(){
		
	}
}
@Aspect (className = TimeLabelUse)
class TimeLabelUseAspect extends DynamicDataUseAspect{
	def void op(){
		
	}
}
@Aspect (className = TimeConstraint)
class TimeConstraintAspect{
	def void op(){
		
	}
}
@Aspect (className = Timer)
class TimerAspect{
	def void op(){
		
	}
}