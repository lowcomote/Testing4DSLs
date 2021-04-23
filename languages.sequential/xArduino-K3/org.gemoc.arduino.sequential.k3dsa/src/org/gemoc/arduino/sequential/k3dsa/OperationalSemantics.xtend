package org.gemoc.arduino.sequential.k3dsa

import fr.inria.diverse.k3.al.annotationprocessor.Aspect

import fr.inria.diverse.k3.al.annotationprocessor.OverrideAspectMethod
import fr.inria.diverse.k3.al.annotationprocessor.Step
import org.gemoc.sequential.model.arduino.BinaryExpression
import org.gemoc.sequential.model.arduino.Block
import org.gemoc.sequential.model.arduino.Constant
import org.gemoc.sequential.model.arduino.Control
import org.gemoc.sequential.model.arduino.Delay
import org.gemoc.sequential.model.arduino.Expression
import org.gemoc.sequential.model.arduino.If
import org.gemoc.sequential.model.arduino.Instruction
import org.gemoc.sequential.model.arduino.Module
import org.gemoc.sequential.model.arduino.ModuleGet
import org.gemoc.sequential.model.arduino.ModuleSet
import org.gemoc.sequential.model.arduino.Project
import org.gemoc.sequential.model.arduino.PushButton
import org.gemoc.sequential.model.arduino.SetLed
import org.gemoc.sequential.model.arduino.Sketch
import org.gemoc.sequential.model.arduino.UnaryExpression
import org.gemoc.sequential.model.arduino.WaitFor
import org.gemoc.sequential.model.arduino.While

import static extension org.gemoc.arduino.sequential.k3dsa.BlockAspect.*
import static extension org.gemoc.arduino.sequential.k3dsa.ExpressionAspect.*
import static extension org.gemoc.arduino.sequential.k3dsa.InstructionAspect.*
import static extension org.gemoc.arduino.sequential.k3dsa.WaitForAspect.*
import fr.inria.diverse.k3.al.annotationprocessor.Main
import fr.inria.diverse.k3.al.annotationprocessor.InitializeModel
import org.eclipse.emf.common.util.EList

@Aspect(className=Project)
class Project_ExecutableAspect {

	@Main
	def void main() {
		println("main")
		val start = System.nanoTime
		_self.sketch.block.execute
		val stop = System.nanoTime
		println("time to execute " + (stop - start))
	}
	
	@InitializeModel
	def public void initializeModel(EList<String> args){
	}
}
@Aspect(className=Module)
abstract class ModuleAspect {
	
	protected def Project getProject() {
		println("get the project of the module");
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
		println("press button");
		_self.level = 1
		_self.project.sketch.eAllContents.filter(WaitFor)
			.filter[w|w.module == _self].forEach[w|w.setActivated]
	}
	
	@Step
	def void release() {
		println("release button");
		_self.level = 0
	}
}

@Aspect(className=Sketch)
class SketchAspect {
	def void execute() {
		println("execute sketch");
		while(true) {
			_self.block.execute
		}
	}
}

@Aspect(className=Block)
class BlockAspect {
	def void execute() {
		println("execute block");
		_self.instructions.forEach[i|i.execute]
	}
}

@Aspect(className=Instruction)
class InstructionAspect {
	def void execute() {
		println("execute instruction");
	}
	
	def void finalize() {
	}
}

@Aspect(className=Control)
class ControlAspect extends InstructionAspect {
	@OverrideAspectMethod
	def void execute() {
		println("execute control instruction");
	}
}

@Aspect(className=If)
class IfAspect extends ControlAspect {
	@Step
	@OverrideAspectMethod
	def void execute() {
		println("execute if instruction");
		if (_self.condition.evaluate != 0) {
			_self.block.execute
		} else if (_self.elseBlock !== null) {
			_self.elseBlock.execute
		}
	}
}

@Aspect(className=While)
class WhileAspect extends ControlAspect {
	@Step
	@OverrideAspectMethod
	def void execute() {
		println("execute while instruction");
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
		println("execute moduleGet instruction");
	}
}

@Aspect(className=SetLed)
abstract class SetLedAspect extends ModuleSetAspect {
	@Step
	@OverrideAspectMethod
	def void execute() {
		println("execute setLed instruction");
		_self.led.level = _self.value.evaluate
		if (_self.led.level == 1){
			println("led is turned on");
		}else{
			println("led is turned off");
		}
	}
} 

@Aspect(className=Delay)
class DelayAspect extends InstructionAspect {
	@Step
	@OverrideAspectMethod
	def void execute() {
		println("execute delay instruction");
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
		println("execute waitFor instruction");
		if (_self.value !== null && _self.isValidated) {
			_self.moduleActivated = false
			_self.waiting = false
			return
		}
		_self.waiting = true
		while (!(_self.moduleActivated && _self.isValidated)) {
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
		println("waiting");
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
		println("evaluate Unary Expression");
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
		println("evaluate Binary Expression");
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
		println("evaluate constant Expression");
		return _self.value
	}
}

@Aspect(className=ModuleGet) 
class ModuleGetAspect extends ExpressionAspect{
	@OverrideAspectMethod
	def Integer evaluate() {
		println("evaluate moduleGet Expression");
		_self.module.level
	}
}
