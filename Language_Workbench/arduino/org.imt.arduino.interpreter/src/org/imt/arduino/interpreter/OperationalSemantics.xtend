package org.imt.arduino.interpreter

import fr.inria.diverse.k3.al.annotationprocessor.Aspect
import fr.inria.diverse.k3.al.annotationprocessor.OverrideAspectMethod
import fr.inria.diverse.k3.al.annotationprocessor.Step
import org.imt.arduino.BinaryExpression
import org.imt.arduino.Block
import org.imt.arduino.Constant
import org.imt.arduino.Control
import org.imt.arduino.Delay
import org.imt.arduino.Expression
import org.imt.arduino.If
import org.imt.arduino.Instruction
import org.imt.arduino.Module
import org.imt.arduino.ModuleGet
import org.imt.arduino.ModuleSet
import org.imt.arduino.Project
import org.imt.arduino.PushButton
import org.imt.arduino.SetLed
import org.imt.arduino.Sketch
import org.imt.arduino.UnaryExpression
import org.imt.arduino.WaitFor
import org.imt.arduino.While

import static extension org.imt.arduino.interpreter.BlockAspect.*
import static extension org.imt.arduino.interpreter.ExpressionAspect.*
import static extension org.imt.arduino.interpreter.InstructionAspect.*
import static extension org.imt.arduino.interpreter.WaitForAspect.*

@Aspect(className=Module)
abstract class ModuleAspect {
	
	protected def Project getProject() {
		var Project project = null
		var current = _self.eContainer()
		while (current !== null) {
			if (current instanceof Project) {
				project = current as Project
				return project
			}
			current = current.eContainer()
		}
		return project
	}
}

@Aspect(className=PushButton)
class PushButtonAspect extends ModuleAspect {
	
	@Step
	def void press() {
		println("Button " + _self.name + " pressed")
		_self.level = 1
		_self.project.sketch.eAllContents.filter(WaitFor)
			.filter[w|w.module == _self].forEach[w|w.setActivated]
	}
	
	@Step
	def void release() {
		println("Button " + _self.name + " released")
		_self.level = 0
	}
}

@Aspect(className=Sketch)
class SketchAspect {
	def void execute() {
		while(true) {
			_self.block.execute
		}
	}
}

@Aspect(className=Block)
class BlockAspect {
	def void execute() {
		_self.instructions.forEach[i|i.execute]
	}
}

@Aspect(className=Instruction)
class InstructionAspect {
	def void execute() {
	}
	
	def void finalize() {
	}
}

@Aspect(className=Control)
class ControlAspect extends InstructionAspect {
	@OverrideAspectMethod
	def void execute() {
	}
}

@Aspect(className=If)
class IfAspect extends ControlAspect {
	@Step
	@OverrideAspectMethod
	def void execute() {
		if (_self.condition.evaluate != 0) {
			_self.block.execute
		} else if (_self.elseBlock !== null) {
			_self.elseBlock.execute
		}
	}
}

@Aspect(className=While)
class WhileAspect extends ControlAspect {
	@OverrideAspectMethod
	def void execute() {
		while (_self.condition.evaluate != 0) {
			_self.block.execute
		}
	}
}

@Aspect(className=ModuleSet)
class ModuleSetAspect extends InstructionAspect {
	@Step
	@OverrideAspectMethod
	def void execute() {
	}
}

@Aspect(className=SetLed)
abstract class SetLedAspect extends ModuleSetAspect {
	@Step
	@OverrideAspectMethod
	def void execute() {
		_self.led.level = _self.value.evaluate
		println("Level of the " + _self.led.name + " LED changed to " + _self.led.level)
	}
} 

@Aspect(className=Delay)
class DelayAspect extends InstructionAspect {
	@Step
	@OverrideAspectMethod
	def void execute() {
		try {
			Thread.sleep(_self.value)
		} catch (InterruptedException e) {
			e.printStackTrace()
		}
	}
}

@Aspect(className=WaitFor)
class WaitForAspect extends InstructionAspect {
	boolean waiting = false
	boolean moduleActivated = false;
	
	@Step
	@OverrideAspectMethod
	def void execute() {
		if (_self.value !== null && _self.validated) {
			_self.moduleActivated = false
			_self.waiting = false
			return
		}
		_self.waiting = true
		while (!(_self.moduleActivated && _self.validated)) {
			_self.moduleActivated = false
			_self.loop
		}
		_self.moduleActivated = false
		_self.waiting = false
	}
	
	private def boolean isValidated() {
		return _self.value === null || _self.module.level == _self.value.value
	}
	
	@Step
	private def void loop() {
		Thread.sleep(100)
	}
	
	def void setActivated() {
		if (_self.waiting) {
			_self.moduleActivated = true
		}
	}
}

@Aspect(className=Expression)
abstract class ExpressionAspect {
	
	def abstract Integer evaluate()
}

@Aspect(className=UnaryExpression)
class UnaryExpressionAspect extends ExpressionAspect {
	@OverrideAspectMethod
	def Integer evaluate() {
		var Integer res
		var operand = _self.operand.evaluate
		switch (_self.operator) {
			case MINUS: {
				res = -operand
			}
			case NEG: {
				res = if (operand == 0) 1 else 0
			}
			default: {
				throw new IllegalStateException("Operator "
					+ _self.operator + " not simulated yet.")
			}
		}
		return res
	}
}

@Aspect(className=BinaryExpression)
class BinaryExpressionAspect extends ExpressionAspect {
	@OverrideAspectMethod
	def Integer evaluate() {
		var Integer res
		var iLeft = _self.left.evaluate
		var iRight = _self.right.evaluate
		switch (_self.operator) {
			case DIV: {
				res = iLeft / iRight
			}
			case MAX: {
				res = Math.max(iLeft, iRight)
			}
			case MIN: {
				res = Math.min(iLeft, iRight)
			}
			case SUB: {
				res = iLeft - iRight
			}
			case MUL: {
				res = iLeft * iRight
			}
			case ADD: {
				res = iLeft + iRight
			}
			case LT: {
				res = if (iLeft < iRight) 1 else 0
			}
			case LE: {
				res = if (iLeft <= iRight) 1 else 0
			}
			case EQ: {
				res = if (iLeft == iRight) 1 else 0
			}
			case GE: {
				res = if (iLeft >= iRight) 1 else 0
			}
			case GT: {
				res = if (iLeft > iRight) 1 else 0
			}
			case NEQ: {
				res = if (iLeft != iRight) 1 else 0
			}
			default: {
				throw new IllegalStateException("Operator "
					+ _self.operator + " not simulated yet.")
			}
		}
		return res
	}
}

@Aspect(className=Constant) 
class ConstantAspect extends ExpressionAspect{
	@OverrideAspectMethod
	def Integer evaluate() {
		return _self.value
	}
}

@Aspect(className=ModuleGet) 
class ModuleGetAspect extends ExpressionAspect{
	@OverrideAspectMethod
	def Integer evaluate() {
		_self.module.level
	}
}
