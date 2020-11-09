package org.imt.k3tdl.k3dsa

import fr.inria.diverse.k3.al.annotationprocessor.Aspect

import fr.inria.diverse.k3.al.annotationprocessor.Step
import fr.inria.diverse.k3.al.annotationprocessor.OverrideAspectMethod

import org.etsi.mts.tdl.Interaction
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
import org.etsi.mts.tdl.Block
import org.etsi.mts.tdl.BehaviourDescription
import static extension org.imt.k3tdl.k3dsa.BehaviourAspect.*
import static extension org.imt.k3tdl.k3dsa.BlockAspect.*
import static extension org.imt.k3tdl.k3dsa.GateInstanceAspect.*
import org.etsi.mts.tdl.GateInstance
import org.etsi.mts.tdl.Target

@Aspect (className = BehaviourDescription)
class BehaviourDescriptionAspect{
	@Step
	def void callBehavior(){
		println("Calling target Behavior: " + _self.behaviour.name)
		_self.behaviour.performBehavior()
	}
}
@Aspect (className = Behaviour)
class BehaviourAspect{
	public Behaviour enabledBehaviour;
	@Step
	def void performBehavior(){
		println("Performing target Behavior: " + _self.name)
		_self.enabledBehaviour = _self
	}
}
@Aspect (className = AtomicBehaviour)
class AtomicBehaviourAspect extends BehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Atomic Behavior: " + _self.name)
	}
}
@Aspect (className = CombinedBehaviour)
class CombinedBehaviourAspect extends BehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Combined Behavior: " + _self.name)
	}
}
@Aspect (className = PeriodicBehaviour)
class PeriodicBehaviourAspect extends BehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Periodic Behavior: " + _self.name)
		_self.block.traverseBlock()
	}
}
@Aspect (className = ExceptionalBehaviour)
class ExceptionalBehaviourAspect extends BehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Exceptional Behavior: " + _self.name)
		_self.block.traverseBlock()
	}
}
@Aspect (className = ActionBehaviour)
class ActionBehaviourAspect extends AtomicBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Action Behavior: " + _self.name)
	}
}
@Aspect (className = VerdictAssignment)
class VerdictAssignmentAspect extends AtomicBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Verdict Assignment Behavior: " + _self.name)
	}
}
@Aspect (className = Stop)
class StopAspect extends AtomicBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Stop Behavior: " + _self.name)
	}
}
@Aspect (className = Break)
class BreakAspect extends AtomicBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Break Behavior: " + _self.name)
	}
}
@Aspect (className = TestDescriptionReference)
class TestDescriptionReferenceAspect extends AtomicBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Test Description Reference Behavior: " + _self.name)
	}
}
@Aspect (className = Interaction)
class InteractoinAspect extends AtomicBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Interaction Behavior: " + _self.name)
	}
}
@Aspect (className = TimerOperation)
class TimerOperationAspect extends AtomicBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Timer Operation Behavior: " + _self.name)
	}
}
@Aspect (className = TimeOperation)
class TimeOperationAspect extends AtomicBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Time Operation Behavior: " + _self.name)
	}
}
@Aspect (className = Assertion)
class AssertionAspect extends ActionBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Assertion Behavior: " + _self.name)
	}
}
@Aspect (className = Assignment)
class AssignmentAspect extends ActionBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Assignment Behavior: " + _self.name)
	}
}
@Aspect (className = InlineAction)
class InlineActionAspect extends ActionBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Inline Action Behavior: " + _self.name)
	}
}
@Aspect (className = ActionReference)
class ActionReferenceAspect extends ActionBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Action Reference Behavior: " + _self.name)
	}
}
@Aspect (className = ProcedureCall)
class ProcedureCallAspect extends InteractoinAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Procedure Call Behavior: " + _self.name)
	}
}
@Aspect (className = Message)
class MessageAspect extends InteractoinAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Message Behavior: " + _self.name);
		for (Target t: _self.target){
			//the argument has to be sent to the SUT
			if (t.targetGate.component.role == 0){
				t.targetGate.gate.sut_receive(_self.argument)
			}else{
				t.targetGate.gate.tester_receive(_self.argument)
			}
		}
			
	}
}
@Aspect (className = TimerStart)
class TimerStartAspect extends TimerOperationAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Timer Start Behavior: " + _self.name)
	}
}
@Aspect (className = TimerStop)
class TimerStopAspect extends TimerOperationAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Timer Stop Behavior: " + _self.name)
	}
}
@Aspect (className = TimeOut)
class TimeOutAspect extends TimerOperationAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Time Out Behavior: " + _self.name)
	}
}
@Aspect (className = Wait)
class WaitAspect extends TimeOperationAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Wait Behavior: " + _self.name)
	}
}
@Aspect (className = Quiescence)
class QuiescenceAspect extends TimeOperationAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Quiescence Behavior: " + _self.name)
	}
}
@Aspect (className = SingleCombinedBehaviour)
class SingleCombinedBehaviourAspect extends CombinedBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Single Combined Behavior: " + _self.name)
		_self.block.traverseBlock()
	}
}
@Aspect (className = MultipleCombinedBehaviour)
class MultipleCombinedBehaviourAspect extends CombinedBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Multiple Combined Behavior: " + _self.name)
	}
}
@Aspect (className = BoundedLoopBehaviour)
class BoundedLoopBehaviourAspect extends SingleCombinedBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Bounded Loop Behavior: " + _self.name)
	}
}
@Aspect (className = UnboundedLoopBehaviour)
class UnBoundedLoopBehaviourAspect extends SingleCombinedBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Bounded Loop Behavior: " + _self.name)
	}
}
@Aspect (className = CompoundBehaviour)
class CompoundBehaviourAspect extends SingleCombinedBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Compound Behavior: " + _self.name)
	}
}
@Aspect (className = OptionalBehaviour)
class OptionalBehaviourAspect extends SingleCombinedBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Optional Behavior: " + _self.name)
	}
}
@Aspect (className = ConditionalBehaviour)
class ConditionalBehaviourAspect extends MultipleCombinedBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Conditional Behavior: " + _self.name)
	}
}
@Aspect (className = AlternativeBehaviour)
class AlternativeBehaviourAspect extends MultipleCombinedBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Alternative Behavior: " + _self.name)
		for (Block innerBlock : _self.block){
			for (Behaviour b :innerBlock.behaviour){
				_self.enabledBehaviour = b
				b.performBehavior()
			}
		}
	}
}
@Aspect (className = ParallelBehaviour)
class ParallelBehaviourAspect extends MultipleCombinedBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Parallel Behavior: " + _self.name)
	}
}
@Aspect (className = DefaultBehaviour)
class DefaultBehaviourAspect extends ExceptionalBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Default Behavior: " + _self.name)
	}
}
@Aspect (className = InterruptBehaviour)
class InterruptBehaviourAspect extends ExceptionalBehaviourAspect{
	@Step
	@OverrideAspectMethod
	def void performBehavior(){
		println("Performing Interrupt Behavior: " + _self.name)
	}
}
@Aspect (className = Block)
class BlockAspect{
	public String blockStatus
	@Step
	def void traverseBlock(){
		_self.blockStatus = "Activated"
		for (Behaviour b:_self.behaviour){
			b.performBehavior()
		}
	}
}