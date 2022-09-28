package org.imt.xminijava.interpreter

import fr.inria.diverse.k3.al.annotationprocessor.Aspect


import fr.inria.diverse.k3.al.annotationprocessor.OverrideAspectMethod
import fr.inria.diverse.k3.al.annotationprocessor.Step
import org.imt.minijava.xminiJava.ArrayRefValue
import org.imt.minijava.xminiJava.BooleanValue
import org.imt.minijava.xminiJava.IntegerValue
import org.imt.minijava.xminiJava.XminiJavaFactory
import org.imt.minijava.xminiJava.ObjectRefValue
import org.imt.minijava.xminiJava.State
import org.imt.minijava.xminiJava.ArrayAccess
import org.imt.minijava.xminiJava.Assignment
import org.imt.minijava.xminiJava.Block
import org.imt.minijava.xminiJava.Expression
import org.imt.minijava.xminiJava.Field
import org.imt.minijava.xminiJava.FieldAccess
import org.imt.minijava.xminiJava.ForStatement
import org.imt.minijava.xminiJava.IfStatement
import org.imt.minijava.xminiJava.Method
import org.imt.minijava.xminiJava.PrintStatement
import org.imt.minijava.xminiJava.Return
import org.imt.minijava.xminiJava.Statement
import org.imt.minijava.xminiJava.SymbolRef
import org.imt.minijava.xminiJava.VariableDeclaration
import org.imt.minijava.xminiJava.WhileStatement

import static extension org.imt.xminijava.interpreter.BlockAspect.*
import static extension org.imt.xminijava.interpreter.ContextAspect.*
import static extension org.imt.xminijava.interpreter.ExpressionAspect.*
import static extension org.imt.xminijava.interpreter.StateAspect.*
import static extension org.imt.xminijava.interpreter.ValueToStringAspect.*

@Aspect(className=Block)
class BlockAspect extends StatementAspect {

	def void evaluateStatementKeepContext(State state) {
		state.pushNewContext
		val currentFrame = state.findCurrentFrame
		var i = _self.statements.iterator
		while (i.hasNext && currentFrame.returnValue === null) {
			i.next.evaluateStatement(state)
		}
	}

	@OverrideAspectMethod
	def void evaluateStatement(State state) {
		_self.evaluateStatementKeepContext(state)
		state.popCurrentContext
	}
}

@Aspect(className=Statement)
class StatementAspect {
	@Step
	def void evaluateStatement(State state) {
		throw new RuntimeException('''evaluate should be overriden for type «_self.class.name»''')
	}
}

@Aspect(className=PrintStatement)
class PrintStatementAspect extends StatementAspect {
	@OverrideAspectMethod
	@Step
	def void evaluateStatement(State state) {
		val string = _self.expression.evaluateExpression(state).customToString
		state.println(string)
	}
}

@Aspect(className=Assignment)
class AssigmentAspect extends StatementAspect {
	@OverrideAspectMethod
	@Step
	def void evaluateStatement(State state) {
		val context = state.findCurrentContext
		val right = _self.value.evaluateExpression(state)
		val assignee = _self.assignee
		switch (assignee) {
			SymbolRef: {
				val existingBinding = context.findBinding(assignee.symbol)
				existingBinding.value = right
			}
			VariableDeclaration: {
				val binding = XminiJavaFactory::eINSTANCE.createSymbolBinding => [
					symbol = assignee
					value = right
				]
				context.bindings.add(binding)
			}
			FieldAccess: {
				val f = assignee.field as Field
				val realReceiver = (assignee.receiver.evaluateExpression(state) as ObjectRefValue).instance
				val existingBinding = realReceiver.fieldbindings.findFirst[it.field === f]
				if (existingBinding !== null) {
					existingBinding.value = right
				} else {
					val binding = XminiJavaFactory::eINSTANCE.createFieldBinding => [
						field = f
						value = right
					]
					realReceiver.fieldbindings.add(binding)
				}
			}
			ArrayAccess: {
				val array = (assignee.object.evaluateExpression(state) as ArrayRefValue).instance
				val index = (assignee.index.evaluateExpression(state) as IntegerValue).value
				array.value.set(index,right) 
			}
			default: throw new Exception("Cannot assign a value to "+assignee)
		}
	}
}

@Aspect(className=ForStatement)
class ForStatementAspect extends StatementAspect {
	@OverrideAspectMethod
	@Step
	def void evaluateStatement(State state) {
		state.pushNewContext
		for (_self.declaration.evaluateStatement(state); (_self.condition.evaluateExpression(state) as BooleanValue).
			value; _self.progression.evaluateStatement(state)) {
			_self.block.evaluateStatement(state)
		}
		state.popCurrentContext
	}
}

@Aspect(className=IfStatement)
class IfStatementAspect extends StatementAspect {
	@OverrideAspectMethod
	@Step
	def void evaluateStatement(State state) {
		if ((_self.expression.evaluateExpression(state) as BooleanValue).value) {
			_self.thenBlock.evaluateStatement(state)
		} else if (_self.elseBlock !== null) {
			_self.elseBlock.evaluateStatement(state)
		}
	}
}

@Aspect(className=WhileStatement)
class WhileStatementAspect extends StatementAspect {
	@OverrideAspectMethod
	@Step
	def void evaluateStatement(State state) {
		while ((_self.condition.evaluateExpression(state) as BooleanValue).value) {
			_self.block.evaluateStatement(state)
		}
	}
}

@Aspect(className=Expression)
class ExpressionStatementAspect extends StatementAspect {
	@OverrideAspectMethod
	@Step
	def void evaluateStatement(State state) {
		_self.evaluateExpression(state)
	}
}

@Aspect(className=Return)
class ReturnAspect extends StatementAspect {
	@OverrideAspectMethod
	@Step
	def void evaluateStatement(State state) {
		val value = _self.expression.evaluateExpression(state);
		state.findCurrentFrame.returnValue = value
	}
}

@Aspect(className=Method)
class MethodSortofStatementAspect {
	@Step
	def void call(State state) {
		_self.body.evaluateStatement(state)
	}
}