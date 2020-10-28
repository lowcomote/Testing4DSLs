package org.imt.k3tdl.k3dsa

import fr.inria.diverse.k3.al.annotationprocessor.Aspect
import fr.inria.diverse.k3.al.annotationprocessor.InitializeModel
import fr.inria.diverse.k3.al.annotationprocessor.Main
import fr.inria.diverse.k3.al.annotationprocessor.Step
import fr.inria.diverse.k3.al.annotationprocessor.OverrideAspectMethod
import org.etsi.mts.tdl.Package
import org.etsi.mts.tdl.TestDescription
import org.etsi.mts.tdl.TestConfiguration
import org.etsi.mts.tdl.ComponentType
import org.etsi.mts.tdl.GateType
import org.etsi.mts.tdl.GateReference
import org.etsi.mts.tdl.Interaction
import org.etsi.mts.tdl.DataUse
import static extension org.imt.k3tdl.k3dsa.TestDescriptionAspect.*
import static extension org.imt.k3tdl.k3dsa.BehaviourDescriptionAspect.*
import static extension org.imt.k3tdl.k3dsa.BehaviourAspect.*
import java.util.List
import java.util.ArrayList
import org.etsi.mts.tdl.BehaviourDescription
import org.etsi.mts.tdl.Behaviour
import org.etsi.mts.tdl.AtomicBehaviour
import org.etsi.mts.tdl.CombinedBehaviour
import org.etsi.mts.tdl.PeriodicBehaviour
import org.etsi.mts.tdl.ExceptionalBehaviour
import org.etsi.mts.tdl.ActionBehaviour
import org.etsi.mts.tdl.VerdictAssignment
import org.etsi.mts.tdl.Stop
import org.etsi.mts.tdl.Break
import org.etsi.mts.tdl.TestDescriptionReference
import org.etsi.mts.tdl.TimerOperation
import org.etsi.mts.tdl.TimeOperation
import org.etsi.mts.tdl.Assertion
import org.etsi.mts.tdl.Assignment
import org.etsi.mts.tdl.InlineAction
import org.etsi.mts.tdl.ActionReference
import org.etsi.mts.tdl.TimerStart
import org.etsi.mts.tdl.ProcedureCall
import org.etsi.mts.tdl.Message
import org.etsi.mts.tdl.TimerStop
import org.etsi.mts.tdl.TimeOut
import org.etsi.mts.tdl.Wait
import org.etsi.mts.tdl.Quiescence
import org.etsi.mts.tdl.SingleCombinedBehaviour
import org.etsi.mts.tdl.MultipleCombinedBehaviour
import org.etsi.mts.tdl.BoundedLoopBehaviour
import org.etsi.mts.tdl.UnboundedLoopBehaviour
import org.etsi.mts.tdl.CompoundBehaviour
import org.etsi.mts.tdl.OptionalBehaviour
import org.etsi.mts.tdl.ConditionalBehaviour
import org.etsi.mts.tdl.AlternativeBehaviour
import org.etsi.mts.tdl.ParallelBehaviour
import org.etsi.mts.tdl.DefaultBehaviour
import org.etsi.mts.tdl.InterruptBehaviour

@Aspect(className = Package)
class PackageAspect {
	
	public List<TestDescription> testcases = new ArrayList<TestDescription>;
	public TestDescription enabledTestCase
	public String verdictValue
	
	@Step
	@InitializeModel
	def void initializeModel(){
		for (Object o : _self.packagedElement.filter[p | p instanceof TestDescription]){
			_self.testcases.add(o as TestDescription)
		}
		if (_self.testcases.size == 0){
			println("There is no test case in the package " + _self.name + "to be executed")
		}
	}
	@Step
	@Main
	def void main(){
		try {
    		for (TestDescription tc:_self.testcases) {
    			_self.enabledTestCase = tc;
    			_self.enabledTestCase.executeTestCase()
    		}    		
		} catch (TDLRuntimeException nt){
			println("Stopped due "+nt.message)	
		}
	}
}
@Aspect (className = TestDescription)
class TestDescriptionAspect{
	public Interaction currentInteraction
	@Step
	def void executeTestCase(){
		println("Start test case execution: " + _self.name)
		_self.behaviourDescription.callBehaviour()
	}
}
@Aspect (className = BehaviourDescription)
class BehaviourDescriptionAspect{
	@Step
	def void callBehaviour(){
		println("Calling target Behaviour: " + _self.behaviour.name)
		_self.behaviour.performBehaviour()
	}
}
@Aspect (className = Behaviour)
class BehaviourAspect{
	@Step
	def void performBehaviour(){
		println("Performing target Behaviour: " + _self.name)
	}
}
@Aspect (className = AtomicBehaviour)
class AtomicBehaviourAspect extends BehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Atomic Behaviour: " + _self.name)
	}
}
@Aspect (className = CombinedBehaviour)
class CombinedBehaviourAspect extends BehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Combined Behaviour: " + _self.name)
	}
}
@Aspect (className = PeriodicBehaviour)
class PeriodicBehaviourAspect extends BehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Periodic Behaviour: " + _self.name)
	}
}
@Aspect (className = ExceptionalBehaviour)
class ExceptionalBehaviourAspect extends BehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Exceptional Behaviour: " + _self.name)
	}
}
@Aspect (className = ActionBehaviour)
class ActionBehaviourAspect extends AtomicBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Action Behaviour: " + _self.name)
	}
}
@Aspect (className = VerdictAssignment)
class VerdictAssignmentAspect extends AtomicBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Verdict Assignment Behaviour: " + _self.name)
	}
}
@Aspect (className = Stop)
class StopAspect extends AtomicBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Stop Behaviour: " + _self.name)
	}
}
@Aspect (className = Break)
class BreakAspect extends AtomicBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing BreakBehaviour: " + _self.name)
	}
}
@Aspect (className = TestDescriptionReference)
class TestDescriptionReferenceAspect extends AtomicBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Test Description Reference Behaviour: " + _self.name)
	}
}
@Aspect (className = Interaction)
class InteractoinAspect extends AtomicBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Interaction Behaviour: " + _self.name)
	}
}
@Aspect (className = TimerOperation)
class TimerOperationAspect extends AtomicBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Timer Operation Behaviour: " + _self.name)
	}
}
@Aspect (className = TimeOperation)
class TimeOperationAspect extends AtomicBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Time Operation Behaviour: " + _self.name)
	}
}
@Aspect (className = Assertion)
class AssertionAspect extends ActionBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Assertion Behaviour: " + _self.name)
	}
}
@Aspect (className = Assignment)
class AssignmentAspect extends ActionBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Assignment Behaviour: " + _self.name)
	}
}
@Aspect (className = InlineAction)
class InlineActionAspect extends ActionBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Inline Action Behaviour: " + _self.name)
	}
}
@Aspect (className = ActionReference)
class ActionReferenceAspect extends ActionBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Action Reference Behaviour: " + _self.name)
	}
}
@Aspect (className = ProcedureCall)
class ProcedureCallAspect extends InteractoinAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Procedure Call Behaviour: " + _self.name)
	}
}
@Aspect (className = Message)
class MessageAspect extends InteractoinAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Message Behaviour: " + _self.name)
	}
}
@Aspect (className = TimerStart)
class TimerStartAspect extends TimerOperationAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Timer Start Behaviour: " + _self.name)
	}
}
@Aspect (className = TimerStop)
class TimerStopAspect extends TimerOperationAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Timer Stop Behaviour: " + _self.name)
	}
}
@Aspect (className = TimeOut)
class TimeOutAspect extends TimerOperationAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Time Out Behaviour: " + _self.name)
	}
}
@Aspect (className = Wait)
class WaitAspect extends TimeOperationAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Wait Behaviour: " + _self.name)
	}
}
@Aspect (className = Quiescence)
class QuiescenceAspect extends TimeOperationAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Quiescence Behaviour: " + _self.name)
	}
}
@Aspect (className = SingleCombinedBehaviour)
class SingleCombinedBehaviourAspect extends CombinedBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Single Combined Behaviour: " + _self.name)
	}
}
@Aspect (className = MultipleCombinedBehaviour)
class MultipleCombinedBehaviourAspect extends CombinedBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Multiple Combined Behaviour: " + _self.name)
	}
}
@Aspect (className = BoundedLoopBehaviour)
class BoundedLoopBehaviourAspect extends SingleCombinedBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Bounded Loop Behaviour: " + _self.name)
	}
}
@Aspect (className = UnboundedLoopBehaviour)
class UnBoundedLoopBehaviourAspect extends SingleCombinedBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Bounded Loop Behaviour: " + _self.name)
	}
}
@Aspect (className = CompoundBehaviour)
class CompoundBehaviourAspect extends SingleCombinedBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Compound Behaviour: " + _self.name)
	}
}
@Aspect (className = OptionalBehaviour)
class OptionalBehaviourAspect extends SingleCombinedBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Optional Behaviour: " + _self.name)
	}
}
@Aspect (className = ConditionalBehaviour)
class ConditionalBehaviourAspect extends MultipleCombinedBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Conditional Behaviour: " + _self.name)
	}
}
@Aspect (className = AlternativeBehaviour)
class AlternativeBehaviourAspect extends MultipleCombinedBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Alternative Behaviour: " + _self.name)
	}
}
@Aspect (className = ParallelBehaviour)
class ParallelBehaviourAspect extends MultipleCombinedBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Parallel Behaviour: " + _self.name)
	}
}
@Aspect (className = DefaultBehaviour)
class DefaultBehaviourAspect extends ExceptionalBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Default Behaviour: " + _self.name)
	}
}
@Aspect (className = InterruptBehaviour)
class InterruptBehaviourAspect extends ExceptionalBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehaviour(){
		println("Performing Interrupt Behaviour: " + _self.name)
	}
}
@Aspect (className = TestConfiguration)
class TestConfigurationAspect{
	@Step
	def void activateConfiguration(){
		
	}
}
@Aspect (className = ComponentType)
class ComponentTypeAspect{
	@Step
	def void activateComponent(){
		
	}
}
@Aspect (className = GateType)
class GateTypeAspect{
	@Step
	def void activateGate(){
		
	}
}
@Aspect (className = GateReference)
class GateReferenceAspect{
	@Step
	def void referenceToGate(){
		
	}
}

@Aspect (className = DataUse)
class DataUseAspect{
	@Step
	def void setData(){
		
	}
}
class TDLRuntimeException extends Exception {
	new(String message) {
		super(message)
	}					
}