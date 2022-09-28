package org.imt.arduino.nonreactive.interpreter

import fr.inria.diverse.k3.al.annotationprocessor.Aspect
import fr.inria.diverse.k3.al.annotationprocessor.InitializeModel
import fr.inria.diverse.k3.al.annotationprocessor.Main
import fr.inria.diverse.k3.al.annotationprocessor.OverrideAspectMethod
import fr.inria.diverse.k3.al.annotationprocessor.Step
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.util.EcoreUtil
import org.imt.arduino.nonreactive.arduino.ArduinoBoard
import org.imt.arduino.nonreactive.arduino.ArduinoCommunicationModule
import org.imt.arduino.nonreactive.arduino.BinaryBooleanExpression
import org.imt.arduino.nonreactive.arduino.BinaryIntegerExpression
import org.imt.arduino.nonreactive.arduino.Block
import org.imt.arduino.nonreactive.arduino.BluetoothTransceiver
import org.imt.arduino.nonreactive.arduino.BooleanConstant
import org.imt.arduino.nonreactive.arduino.BooleanExpression
import org.imt.arduino.nonreactive.arduino.BooleanModuleGet
import org.imt.arduino.nonreactive.arduino.BooleanVariable
import org.imt.arduino.nonreactive.arduino.BooleanVariableRef
import org.imt.arduino.nonreactive.arduino.ChangeType
import org.imt.arduino.nonreactive.arduino.Constant
import org.imt.arduino.nonreactive.arduino.Control
import org.imt.arduino.nonreactive.arduino.Delay
import org.imt.arduino.nonreactive.arduino.Expression
import org.imt.arduino.nonreactive.arduino.If
import org.imt.arduino.nonreactive.arduino.Instruction
import org.imt.arduino.nonreactive.arduino.IntegerConstant
import org.imt.arduino.nonreactive.arduino.IntegerExpression
import org.imt.arduino.nonreactive.arduino.IntegerModuleGet
import org.imt.arduino.nonreactive.arduino.IntegerVariable
import org.imt.arduino.nonreactive.arduino.IntegerVariableRef
import org.imt.arduino.nonreactive.arduino.Module
import org.imt.arduino.nonreactive.arduino.ModuleAssignment
import org.imt.arduino.nonreactive.arduino.ModuleInstruction
import org.imt.arduino.nonreactive.arduino.Pin
import org.imt.arduino.nonreactive.arduino.Project
import org.imt.arduino.nonreactive.arduino.Repeat
import org.imt.arduino.nonreactive.arduino.Sketch
import org.imt.arduino.nonreactive.arduino.Time
import org.imt.arduino.nonreactive.arduino.UnaryBooleanExpression
import org.imt.arduino.nonreactive.arduino.UnaryBooleanOperatorKind
import org.imt.arduino.nonreactive.arduino.Utilities
import org.imt.arduino.nonreactive.arduino.Variable
import org.imt.arduino.nonreactive.arduino.VariableAssignment
import org.imt.arduino.nonreactive.arduino.VariableDeclaration
import org.imt.arduino.nonreactive.arduino.VariableRef
import org.imt.arduino.nonreactive.arduino.WaitFor
import org.imt.arduino.nonreactive.arduino.While

import static extension org.imt.arduino.nonreactive.interpreter.Block_ExecutableAspect.*
import static extension org.imt.arduino.nonreactive.interpreter.BluetoothTransceiver_PushAspect.*
import static extension org.imt.arduino.nonreactive.interpreter.BooleanVariable_EvaluableAspect.*
import static extension org.imt.arduino.nonreactive.interpreter.Control_EvaluableAspect.*
import static extension org.imt.arduino.nonreactive.interpreter.Expression_EvaluableAspect.*
import static extension org.imt.arduino.nonreactive.interpreter.If_EvaluableAspect.*
import static extension org.imt.arduino.nonreactive.interpreter.Instruction_ExecutableAspect.*
import static extension org.imt.arduino.nonreactive.interpreter.Instruction_UtilitesAspect.*
import static extension org.imt.arduino.nonreactive.interpreter.IntegerVariable_EvaluableAspect.*
import static extension org.imt.arduino.nonreactive.interpreter.Pin_EvaluableAspect.*

@Aspect(className=Instruction)
class Instruction_UtilitesAspect {
	private def Project getProject(Module module) {
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
	
	def protected Pin getPin(Module module) {
		if (module.eContainer instanceof Pin){
			return module.eContainer as Pin
		}
		var Pin pin = null
		
		val project = _self.getProject(module)

		for (board : project.boards) {
			if (board !== null && board instanceof ArduinoBoard) {
				var ArduinoBoard arduinoBoard = board as ArduinoBoard
				for (analogPin : arduinoBoard.analogPins) {
					if (analogPin.module == module) {
						return analogPin
					}
				}
				for (digitalPin : arduinoBoard.digitalPins) {
					if (digitalPin.module == module) {
						return digitalPin
					}
				}
			}
		}
		
		return pin
	}
}

@Aspect(className=Instruction)
class Instruction_ExecutableAspect extends Instruction_UtilitesAspect{
	def void execute() {
	}
	
	def void finalize() {
	}
}

@Aspect(className=Project)
class Project_ExecutableAspect {
	
	def void execute() {
		val sketches = _self.sketches
		//while(true) {
			sketches.forEach[s|s.block.execute]
		//}
	}
	
	@Main
	def void main() {
		val start = System.nanoTime
		_self.execute
		val stop = System.nanoTime
		//println("time to execute " + (stop - start))
	}
	
	@Step
	def void setup() {
		_self.eAllContents().forEach[o|{
			if (o instanceof IntegerVariable) {
				o.value = o.initialValue
			} else if (o instanceof BooleanVariable) {
				o.value = o.initialValue
			} 
		}]
	}
	
	@InitializeModel
	def void initializeModel(EList<String> args){
		_self.setup
	}
}

@Aspect(className=Sketch)
class SketchAspect{
	
	@Step
	def void execute(){
		//while(true) {
			_self.block.execute
		//}
	}
}
@Aspect(className=Module)
class ModuleAspect {
	
	@Step
	def void execute(){
		
	}
	def protected Project getProject() {
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
	
	def protected Pin getPin() {
		var Pin pin = null
		
		val project = _self.getProject()

		for (board : project.boards) {
			if (board !== null && board instanceof ArduinoBoard) {
				var ArduinoBoard arduinoBoard = board as ArduinoBoard
				for (analogPin : arduinoBoard.analogPins) {
					if (analogPin.module == _self) {
						return analogPin
					}
				}
				for (digitalPin : arduinoBoard.digitalPins) {
					if (digitalPin.module == _self) {
						return digitalPin
					}
				}
			}
		}
		
		return pin
	}
}

@Aspect(className=VariableAssignment)
class VariableAssignment_ExecutableAspect extends Instruction_ExecutableAspect {
	@Step
	@OverrideAspectMethod
	def void execute() {
		val variable = _self.variable	
		val value = _self.operand.evaluate
		
		if (variable instanceof IntegerVariable){
			variable.initialValue = value as Integer
			variable.value = value as Integer
		}
		if (variable instanceof BooleanVariable){
			variable.value = value as Boolean
		}
	}
}

@Aspect(className=Block)
class Block_ExecutableAspect {
	def void execute() {
		for (Instruction i : _self.instructions) {
			i.execute
		}
//		_self.instructions.forEach[i|i.execute]
	}
}

@Aspect(className=ModuleInstruction)
class ModuleInstruction_ExecutableAspect extends Instruction_ExecutableAspect {	
	@OverrideAspectMethod
	def void execute() {
	}
}

@Aspect(className=ModuleAssignment)
class ModuleAssignment_ExecutableAspect extends ModuleInstruction_ExecutableAspect {
	@Step
	@OverrideAspectMethod
	def void execute() {
		val Pin pin = _self.getPin(_self.module)
		val previousValue = pin.level
		//println("Changing the level of the pin " + pin.name + ":")
		//println("The current level is: " + pin.level)
		if (_self.operand instanceof IntegerExpression){
			pin.level = _self.operand.evaluate as Integer
		}
		if (_self.operand instanceof BooleanExpression){
			if (_self.operand.evaluate as Boolean){
				pin.level = HIGH
			} else {
				pin.level = LOW
			}
		}
		if (pin.level != previousValue){
			pin.changeLevel
		}
		//println("The new level is: " + pin.level)
		//FIXME Here it is dirty but I think we should 'transmit' the value in the module itself as the wire should do in true life
		if (_self.module instanceof BluetoothTransceiver){
			(_self.module as BluetoothTransceiver).dataToSend.add(pin.level)
			//FIXME temporary solution
			(_self.module as BluetoothTransceiver).push
		}
	}
}

@Aspect(className=ArduinoCommunicationModule)
abstract class ArduinoCommunicationModule_PushAspect {
	abstract def void push()
} 

@Aspect(className=BluetoothTransceiver)
abstract class BluetoothTransceiver_PushAspect extends ArduinoCommunicationModule_PushAspect {
	
	public EList<Integer> dataToSend
	public EList<Integer> dataReceived
	
	@Step
	@OverrideAspectMethod
	def void push(){
		_self.connectedTransceiver.forEach[t|
			val l = t.dataReceived
			_self.dataToSend.forEach[i|l.add(i)]
		]
		_self.dataToSend.clear
	}
} 

@Aspect(className=VariableDeclaration)
class VariableDeclaration_ExecutableAspect extends Instruction_ExecutableAspect {
	@Step
	@OverrideAspectMethod
	def void execute() {
		switch (_self.variable){
			IntegerVariable : (_self.variable as IntegerVariable).value = (_self.variable as IntegerVariable).initialValue	
			BooleanVariable : (_self.variable as BooleanVariable).value = (_self.variable as BooleanVariable).initialValue
		}
	}
}

@Aspect(className=Control)
class Control_ExecutableAspect extends Instruction_ExecutableAspect {
	@OverrideAspectMethod
	def void execute() {
	}
}

@Aspect(className=Control)
class Control_EvaluableAspect extends Instruction_ExecutableAspect {
	def Boolean evaluate() {
	}
}

@Aspect(className=If)
class If_EvaluableAspect extends Control_EvaluableAspect {
	@OverrideAspectMethod
	def Boolean evaluate() { 
		var Boolean resCond = false
		if (_self.condition instanceof BooleanExpression){
			resCond = _self.condition.evaluate as Boolean
		}
		return resCond
	}
}

@Aspect(className=If)
class If_ExecutableAspect extends Control_ExecutableAspect {
	@Step
	@OverrideAspectMethod
	def void execute() {
		if (_self.evaluate) {
			_self.block.execute
		} else {
			if (_self.elseBlock !== null) {
				_self.elseBlock.execute
			}
		}
	}
}

@Aspect(className=Repeat)
class Repeat_EvaluableAspect extends Control_EvaluableAspect {
	var Integer i = 0

	@OverrideAspectMethod
	def Boolean evaluate() {
		var Boolean resCond = false
		resCond = (_self.i  < _self.iteration)
		_self.i = _self.i+1
		return resCond
	}

	@OverrideAspectMethod
	def void finalize() {
		_self.i = 0
		return
	}
}

@Aspect(className=Repeat)
class Repeat_ExecutableAspect extends Control_ExecutableAspect  {
	@Step
	@OverrideAspectMethod
	def void execute() {
		while (_self.evaluate) {
			_self.block.execute
		}
		_self.finalize
	}
}

@Aspect(className=While)
class While_EvaluableAspect extends Control_EvaluableAspect {
	@OverrideAspectMethod
	def Boolean evaluate() {
		var Boolean resCond = _self.condition.evaluate as Boolean
		return resCond
	}
}

@Aspect(className=While)
class While_ExecutableAspect extends Control_ExecutableAspect {
	@Step
	@OverrideAspectMethod
	def void execute() {
		while (_self.evaluate) {
			_self.block.execute
		}
	}
}

@Aspect(className=Utilities)
class Utilities_ExecutableAspect extends Instruction_ExecutableAspect {
	@OverrideAspectMethod
	def void execute() {
	}
}

@Aspect(className=WaitFor)
class WaitFor_ExecutableAspect extends Utilities_ExecutableAspect { 	
	boolean waiting = false
	boolean moduleActivated = false;
	int value;
	
	@Step
	@OverrideAspectMethod
	def void execute() {
		_self.value = _self.pin.level		
		_self.waiting = true
		while (!_self.isValidated) {
			_self.moduleActivated = false			
			_self.loop			
		}
		if ( _self.isValidated) {
			_self.moduleActivated = false
			_self.waiting = false
			return
		}
		_self.moduleActivated = false
		_self.waiting = false
	}
	
	private def boolean isValidated() {
		if (_self.mode == ChangeType.CHANGE) {
			return _self.value != _self.pin.level
		} else if (_self.mode == ChangeType.RISING) {
			return _self.value < _self.pin.level
		} else if (_self.mode == ChangeType.FALLING) {
			return _self.value > _self.pin.level
		} else {			
			return false
		}			
	}
	
	
	private def void loop() {
		Thread.sleep(10)
	}
	
	def void setActivated() {
		if (_self.waiting) {
			_self.moduleActivated = true
		}
	}
}

@Aspect(className=BinaryIntegerExpression)
class BinaryIntegerExpression_EvaluableAspect extends Expression_EvaluableAspect {
	@OverrideAspectMethod
	def Object evaluate() {		
		var Integer res
		var bLeft = false
		var iLeft = 0
		switch (_self.left){
			BooleanExpression: bLeft = _self.left.evaluate as Boolean
			IntegerExpression: iLeft = _self.left.evaluate as Integer
		}
		var bRight = false
		var iRight = 0
		switch (_self.right){
			BooleanExpression: bRight = _self.right.evaluate as Boolean
			IntegerExpression: iRight = _self.right.evaluate as Integer
		}
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
			case MINUS: {
				res = iLeft - iRight
			}
			case MUL: {
				res = iLeft * iRight
			}
			case PLUS: {				
				res = iLeft + iRight
			}
			case POURCENT: {
				res = iLeft % iRight
			}
			default: {
				throw new IllegalStateException("Operator "
					+ _self.operator + " not simulated yet.")
			}
		}
		return res
	}
}

@Aspect(className=BooleanModuleGet) 
class BooleanModuleGet_ExecutableAspect extends Expression_EvaluableAspect{
	@Step
	@OverrideAspectMethod
	def Object evaluate() {
		
		val pin = _self.instruction.getPin(_self.module)
		if (pin.level == 0){
			return false
		}
		return true
	}
}

@Aspect(className=BooleanConstant) 
class BooleanConstant_ExecutableAspect extends Expression_EvaluableAspect{
	@OverrideAspectMethod
	def Object evaluate() {
		return _self.value
	}
}

@Aspect(className=IntegerConstant) 
class IntegerConstant_ExecutableAspect extends Expression_EvaluableAspect{
	@OverrideAspectMethod
	def Object evaluate() {
		return _self.value
	}
}	

@Aspect(className=IntegerModuleGet) 
class IntegerModuleGet_ExecutableAspect extends Expression_EvaluableAspect{
	@Step
	@OverrideAspectMethod
	def Object evaluate() {
		
		//FIXME Here it is dirty but I think we should 'transmit' the value in the module itself as the wire should do in true life
		if (_self.module instanceof BluetoothTransceiver){
			val l = (_self.module as BluetoothTransceiver).dataReceived
			val res = l.head
			if (res !== null) {
				l.remove(0)
				return res
			} else {
				return 0
			}
		}
		
		val pin = _self.instruction.getPin(_self.module)
		return pin.level
	}
}

@Aspect(className=BinaryBooleanExpression)
class BinaryBooleanExpression_EvaluableAspect extends Expression_EvaluableAspect {
	@OverrideAspectMethod
	def Object evaluate() {
		var leftIsBoolean = false
		var rightIsBoolean = false
		var Boolean res
		var bLeft = false
		var iLeft = 0
		switch (_self.left){
			BooleanExpression: {
				bLeft = _self.left.evaluate as Boolean
				leftIsBoolean = true
			}
			IntegerExpression: iLeft = _self.left.evaluate as Integer
		}
		var bRight = false
		var iRight = 0
		switch (_self.right){			
			BooleanExpression: {
				bRight = _self.right.evaluate as Boolean 
				rightIsBoolean = true
			}
			IntegerExpression: {
				iRight = _self.right.evaluate as Integer				
			}
		}
		if (leftIsBoolean != rightIsBoolean) {
			throw new IllegalArgumentException("left operand type does not match right operand type.")
		}
		switch (_self.operator) {
			case AND: {
				if (bLeft) {
					res = bRight
				} else {
					res = Boolean.FALSE
				}
			}
			case OR: {
				if(bLeft) {
					res = Boolean.TRUE
				}
				else {
					res = bRight
				}
			}
			case DIFFERENT: {
				res = !(bLeft.equals(bRight))
			}
			case EQUAL: {
				if (leftIsBoolean) {
					res = (bLeft.equals(bRight))
				} else {
					res = (iLeft.equals(iRight))
				}				
			}
			case INF: {
				res = iLeft < iRight
			}
			case INF_OR_EQUAL: {
				res = iLeft <= iRight
			}
			case SUP: {
				res = iLeft > iRight
			}
			case SUP_OR_EQUAL: {
				res = iLeft >= iRight
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
class Constant_EvaluableAspect extends Expression_EvaluableAspect {
	@OverrideAspectMethod
	def Object evaluate() {
		switch _self{
			BooleanConstant: return (_self as BooleanConstant).value
			IntegerConstant: return (_self as IntegerConstant).value
			default: throw new ClassCastException("type not expected: "+_self.eClass.name)
		}
	}
}

@Aspect(className=Delay)
class Delay_ExecutableAspect extends Utilities_ExecutableAspect {
	@Step
	@OverrideAspectMethod
	def void execute() {
		try {
			if (_self.unit == Time.MICRO_SECOND){
				val value = _self.value / 1000;
				Thread.sleep(value)
			}
			else{
				Thread.sleep(_self.value)
			}
		} catch (InterruptedException e) {
			System.out.println("InterruptedException thrown because of Delay")
		}
	}
}

@Aspect(className=Pin)
class Pin_EvaluableAspect {
		
	public static final Integer LOW = 0
	public static final Integer HIGH = 1023
	
	@Step
	def void changeLevel(){
		val pinContainer = _self.project
		if (pinContainer !== null){
			pinContainer.pinChanges.add(EcoreUtil.copy(_self))
		}
		println("The level of " + _self.name + " pin changed to " + _self.level)
	}
	
	private def Project getProject() {
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

@Aspect(className=Variable)
abstract class Variable_EvaluableAspect {
	def abstract Object evaluate()
}

@Aspect(className=IntegerVariable)
class IntegerVariable_EvaluableAspect extends Variable_EvaluableAspect {
	public Integer value
	
	@OverrideAspectMethod
	def Object evaluate(){
		return _self.value
	}
}

@Aspect(className=BooleanVariable)
class BooleanVariable_EvaluableAspect extends Variable_EvaluableAspect  {	
	public Boolean value
	
	@OverrideAspectMethod
	def Object evaluate(){
		return _self.value
	}
}

@Aspect(className=VariableRef)
class VariableRef_EvaluableAspect extends Expression_EvaluableAspect{
	@OverrideAspectMethod
	def Object evaluate(){
		switch _self{
			BooleanVariableRef: return (_self as BooleanVariableRef).variable.evaluate
			IntegerVariableRef: {
				return (_self as IntegerVariableRef).variable.evaluate			
			}
			default: throw new ClassCastException("type not expected: "+_self.eClass.name)
		}
	}
}

@Aspect(className=Expression)
abstract class Expression_EvaluableAspect {
	
	def protected Instruction getInstruction() {
		var Instruction instruction = null

		var current = _self.eContainer()
		while (current !== null) {
			if (current instanceof Instruction) {
				instruction = current as Instruction
				return instruction
			}
			current = current.eContainer()
		}
		
		return instruction
	}
	
	def Object evaluate() {		
	}
}

@Aspect(className=UnaryBooleanExpression)
class UnaryBooleanExpression_EvaluableAspect extends Expression_EvaluableAspect {
	def Object evaluate() {
		if (_self.operator == UnaryBooleanOperatorKind.NOT) {		
			return ! (_self.operand.evaluate as Boolean)	
		}
		
		return _self.operand.evaluate
	}
}
