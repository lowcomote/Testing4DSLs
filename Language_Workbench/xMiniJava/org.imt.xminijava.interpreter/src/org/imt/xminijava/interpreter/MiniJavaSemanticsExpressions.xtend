package org.imt.xminijava.interpreter

import fr.inria.diverse.k3.al.annotationprocessor.Aspect


import fr.inria.diverse.k3.al.annotationprocessor.OverrideAspectMethod
import org.imt.minijava.xminiJava.BooleanValue
import org.imt.minijava.xminiJava.IntegerValue
import org.imt.minijava.xminiJava.XminiJavaFactory
import org.imt.minijava.xminiJava.State
import org.imt.minijava.xminiJava.StringValue
import org.imt.minijava.xminiJava.Value
import org.imt.minijava.xminiJava.And
import org.imt.minijava.xminiJava.BoolConstant
import org.imt.minijava.xminiJava.Class
import org.imt.minijava.xminiJava.ClassRef
import org.imt.minijava.xminiJava.Division
import org.imt.minijava.xminiJava.Equality
import org.imt.minijava.xminiJava.Expression
import org.imt.minijava.xminiJava.Field
import org.imt.minijava.xminiJava.FieldAccess
import org.imt.minijava.xminiJava.Inequality
import org.imt.minijava.xminiJava.Inferior
import org.imt.minijava.xminiJava.InferiorOrEqual
import org.imt.minijava.xminiJava.IntConstant
import org.imt.minijava.xminiJava.Method
import org.imt.minijava.xminiJava.MethodCall
import org.imt.minijava.xminiJava.Minus
import org.imt.minijava.xminiJava.Multiplication
import org.imt.minijava.xminiJava.Neg
import org.imt.minijava.xminiJava.Not
import org.imt.minijava.xminiJava.Null
import org.imt.minijava.xminiJava.Or
import org.imt.minijava.xminiJava.Parameter
import org.imt.minijava.xminiJava.Plus
import org.imt.minijava.xminiJava.StringConstant
import org.imt.minijava.xminiJava.Superior
import org.imt.minijava.xminiJava.SuperiorOrEqual
import org.imt.minijava.xminiJava.SymbolRef
import org.imt.minijava.xminiJava.This
import org.imt.minijava.xminiJava.TypeRef

import static extension org.imt.xminijava.interpreter.BlockAspect.*
import static extension org.imt.xminijava.interpreter.ContextAspect.*
import static extension org.imt.xminijava.interpreter.MethodAspect.*
import static extension org.imt.xminijava.interpreter.ParameterAspect.*
import static extension org.imt.xminijava.interpreter.StateAspect.*
import static extension org.imt.xminijava.interpreter.TypeRefAspect.*
import static extension org.imt.xminijava.interpreter.ValueAspect.*
import static extension org.imt.xminijava.interpreter.ValueToStringAspect.*
import static extension org.imt.xminijava.interpreter.MethodSortofStatementAspect.*
import org.imt.minijava.xminiJava.ObjectInstance
import org.imt.minijava.xminiJava.ObjectRefValue
import org.imt.minijava.xminiJava.NewObject
import org.imt.minijava.xminiJava.NewArray
import org.imt.minijava.xminiJava.ArrayLength
import org.imt.minijava.xminiJava.ArrayRefValue
import org.imt.minijava.xminiJava.ArrayAccess
import org.imt.minijava.xminiJava.IntegerTypeRef
import org.imt.minijava.xminiJava.BooleanTypeRef
import org.imt.minijava.xminiJava.StringTypeRef
import org.imt.minijava.xminiJava.NullValue
import java.util.Map
import java.util.HashMap
import fr.inria.diverse.k3.al.annotationprocessor.Step

@Aspect(className=Expression)
class ExpressionAspect {

	def Value evaluateExpression(State state) {
		throw new RuntimeException('''evaluate should be overriden for type «_self.class.name»''')
	}
}

@Aspect(className=Neg)
class NegAspect extends ExpressionAspect {
	@OverrideAspectMethod
	def Value evaluateExpression(State state) {
		val intabsvalue = (_self.expression.evaluateExpression(state) as IntegerValue).value
		return XminiJavaFactory::eINSTANCE.createIntegerValue => [value = -intabsvalue]
	}
}

@Aspect(className=Null)
class NullAspect extends ExpressionAspect {
	@OverrideAspectMethod
	def Value evaluateExpression(State state) {
		return XminiJavaFactory::eINSTANCE.createNullValue
	}
}

@Aspect(className=Minus)
class MinusAspect extends ExpressionAspect {
	@OverrideAspectMethod
	def Value evaluateExpression(State state) {
		val left = (_self.left.evaluateExpression(state) as IntegerValue).value
		val right = (_self.right.evaluateExpression(state) as IntegerValue).value
		return XminiJavaFactory::eINSTANCE.createIntegerValue => [value = left - right]
	}
}

@Aspect(className=Multiplication)
class MultiplicationAspect extends ExpressionAspect {
	@OverrideAspectMethod
	def Value evaluateExpression(State state) {
		val left = (_self.left.evaluateExpression(state) as IntegerValue).value
		val right = (_self.right.evaluateExpression(state) as IntegerValue).value
		return XminiJavaFactory::eINSTANCE.createIntegerValue => [value = left * right]
	}
}

@Aspect(className=Division)
class DivisionAspect extends ExpressionAspect {
	@OverrideAspectMethod
	def Value evaluateExpression(State state) {
		val left = (_self.left.evaluateExpression(state) as IntegerValue).value
		val right = (_self.right.evaluateExpression(state) as IntegerValue).value
		return XminiJavaFactory::eINSTANCE.createIntegerValue => [value = left / right]
	}
}

@Aspect(className=Plus)
class PlusAspect extends ExpressionAspect {
	@OverrideAspectMethod
	def Value evaluateExpression(State state) {
		val left = _self.left.evaluateExpression(state)
		val right = _self.right.evaluateExpression(state)
		if (left instanceof StringValue || right instanceof StringValue) {
			return XminiJavaFactory::eINSTANCE.createStringValue => [
				value = left.customToString + right.customToString
			]
		} else if (left instanceof IntegerValue) {
			if (right instanceof IntegerValue) {
				return XminiJavaFactory::eINSTANCE.createIntegerValue => [
					value = left.value + right.value
				]
			}
		}
		throw new RuntimeException('''Unsupported plus operands: «left» + «right».''')
	}
}

@Aspect(className=Or)
class OrAspect extends ExpressionAspect {
	@OverrideAspectMethod
	def Value evaluateExpression(State state) {
		val left = _self.left.evaluateExpression(state)
		val right = _self.right.evaluateExpression(state)
		if (left instanceof BooleanValue) {
			if (right instanceof BooleanValue) {
				return XminiJavaFactory::eINSTANCE.createBooleanValue => [
					value = left.value || right.value
				]
			}
		}
		throw new RuntimeException('''Unsupported or operands: «left» || «right».''')
	}
}

@Aspect(className=And)
class AndAspect extends ExpressionAspect {
	@OverrideAspectMethod
	def Value evaluateExpression(State state) {
		val left = _self.left.evaluateExpression(state)
		val right = _self.right.evaluateExpression(state)
		if (left instanceof BooleanValue) {
			if (right instanceof BooleanValue) {
				return XminiJavaFactory::eINSTANCE.createBooleanValue => [
					value = left.value && right.value
				]
			}
		}
		throw new RuntimeException('''Unsupported or operands: «left» && «right».''')
	}
}

@Aspect(className=Inferior)
class InferiorAspect extends ExpressionAspect {
	@OverrideAspectMethod
	def Value evaluateExpression(State state) {
		val left = (_self.left.evaluateExpression(state) as IntegerValue).value
		val right = (_self.right.evaluateExpression(state) as IntegerValue).value
		return XminiJavaFactory::eINSTANCE.createBooleanValue => [
			value = left < right
		]
	}
}

@Aspect(className=InferiorOrEqual)
class InferiorOrEqualAspect extends ExpressionAspect {
	@OverrideAspectMethod
	def Value evaluateExpression(State state) {
		val left = (_self.left.evaluateExpression(state) as IntegerValue).value
		val right = (_self.right.evaluateExpression(state) as IntegerValue).value
		return XminiJavaFactory::eINSTANCE.createBooleanValue => [
			value = left <= right
		]
	}

}

@Aspect(className=SuperiorOrEqual)
class SuperiorOrEqualAspect extends ExpressionAspect {
	@OverrideAspectMethod
	def Value evaluateExpression(State state) {
		val left = (_self.left.evaluateExpression(state) as IntegerValue).value
		val right = (_self.right.evaluateExpression(state) as IntegerValue).value
		return XminiJavaFactory::eINSTANCE.createBooleanValue => [
			value = left >= right
		]
	}
}

@Aspect(className=Superior)
class SuperiorAspect extends ExpressionAspect {
	@OverrideAspectMethod
	def Value evaluateExpression(State state) {
		val left = (_self.left.evaluateExpression(state) as IntegerValue).value
		val right = (_self.right.evaluateExpression(state) as IntegerValue).value
		return XminiJavaFactory::eINSTANCE.createBooleanValue => [
			value = left > right
		]
	}
}

@Aspect(className=Not)
class NotAspect extends ExpressionAspect {
	@OverrideAspectMethod
	def Value evaluateExpression(State state) {
		val expr = (_self.expression.evaluateExpression(state) as BooleanValue).value
		return XminiJavaFactory::eINSTANCE.createBooleanValue => [
			value = !expr
		]
	}
}

@Aspect(className=Equality)
class EqualityAspect extends ExpressionAspect {

	@OverrideAspectMethod
	def Value evaluateExpression(State state) {
		val left = _self.left.evaluateExpression(state)
		val right = _self.right.evaluateExpression(state)

		val boolean result = if (left instanceof IntegerValue) {
				left.value === (right as IntegerValue).value
			} else if (left instanceof StringValue) {
				left.value == (right as StringValue).value
			} else if (left instanceof BooleanValue) {
				left.value === (right as BooleanValue).value
			} else if (left instanceof NullValue) {
				right instanceof NullValue
			} else if (left instanceof ObjectRefValue) {
				(right instanceof ObjectRefValue) && left.instance === (right as ObjectRefValue).instance
			} else {
				throw new RuntimeException('''Type unsupported for equality operator: «left.class»''')
			}

		return XminiJavaFactory::eINSTANCE.createBooleanValue => [
			value = result
		]
	}
}

@Aspect(className=Inequality)
class InequalityAspect extends ExpressionAspect {

	@OverrideAspectMethod
	def Value evaluateExpression(State state) {
		val left = _self.left.evaluateExpression(state)
		val right = _self.right.evaluateExpression(state)

		val boolean result = if (left instanceof IntegerValue) {
				left.value !== (right as IntegerValue).value
			} else if (left instanceof StringValue) {
				left.value != (right as StringValue).value
			} else if (left instanceof BooleanValue) {
				left.value !== (right as BooleanValue).value
			} else if (left instanceof NullValue) {
				! (right instanceof NullValue)
			} else if (left instanceof ObjectRefValue) {
				!(right instanceof ObjectRefValue) || left.instance !== (right as ObjectRefValue).instance
			} else {
				throw new RuntimeException('''Type unsupported for inequality operator: «left.class»''')
			}
		return XminiJavaFactory::eINSTANCE.createBooleanValue => [
			value = result
		]
	}
}

@Aspect(className=TypeRef)
class TypeRefAspect {
	def boolean compare(TypeRef other) { return _self.eClass == other.eClass }
}

@Aspect(className=ClassRef)
class ClassRefAspect extends TypeRefAspect {
	@OverrideAspectMethod
	def boolean compare(TypeRef other) {
		if (other instanceof ClassRef) {
			return _self.referencedClass == other.referencedClass
		} else {
			return false;
		}
	}
}

@Aspect(className=Parameter)
class ParameterAspect {
	def boolean compare(Parameter other) {
		return _self.name == other.name && _self.typeRef.compare(other.typeRef)
	}
}

@Aspect(className=Method)
class MethodAspect {

	Map<Class, Method> cache = new HashMap

	def Method findOverride(Class c) {

		if (!_self.cache.containsKey(c)) {
			val result = if (c.members.contains(_self)) {
					_self
				} else {

					val candidate = c.members.filter(Method).findFirst [
						it.name == _self.name && it.params.size == _self.params.size &&
							it.typeRef.compare(_self.typeRef) && it.params.forall [ p1 |
								_self.params.exists [ p2 |
									p1.compare(p2)
								]
							]
					]

					if (candidate !== null) {
						candidate
					} else if (c.superClass !== null) {
						_self.findOverride(c.superClass)
					} else {
						null
					}
				}
			_self.cache.put(c, result)
		}

		return _self.cache.get(c)
	}
}

@Aspect(className=MethodCall)
class MethodCallExpressionAspect extends ExpressionAspect {
	@Step
	@OverrideAspectMethod
	def Value evaluateExpression(State state) {
		val realReceiver = (_self.receiver.evaluateExpression(state) as ObjectRefValue).instance
		val realMethod = _self.method.findOverride(realReceiver.type as Class)
		val newContext = XminiJavaFactory::eINSTANCE.createContext
		for (arg : _self.args) {
			val param = realMethod.params.get(_self.args.indexOf(arg))
			val binding = XminiJavaFactory::eINSTANCE.createSymbolBinding => [
				symbol = param
				value = arg.evaluateExpression(state)
			]
			newContext.bindings.add(binding)
		}

		val call = XminiJavaFactory::eINSTANCE.createMethodCall2 => [methodcall = _self]
		state.pushNewFrame(realReceiver, call, newContext)
		realMethod.call(state)
		val returnValue = state.findCurrentFrame.returnValue
		state.popCurrentFrame
		return returnValue

	}
}

@Aspect(className=FieldAccess)
class FieldAccessAspect extends ExpressionAspect {
	@OverrideAspectMethod
	def Value evaluateExpression(State state) {
		val realReceiver = (_self.receiver.evaluateExpression(state) as ObjectRefValue).instance as ObjectInstance
		val binding = realReceiver.fieldbindings.findFirst[it.field === _self.field]
		if (binding === null){
			return null
		}
		else if (binding.value instanceof NullValue){
			val nullValue = XminiJavaFactory::eINSTANCE.createNullValue
			return nullValue
		}
		else if (binding.value instanceof StringValue){
			val stringValue = XminiJavaFactory::eINSTANCE.createStringValue =>[
				value = (binding.value as StringValue).value
			]
			return stringValue
		}
		else if (binding.value instanceof IntegerValue){
			val intValue = XminiJavaFactory::eINSTANCE.createIntegerValue =>[
				value = (binding.value as IntegerValue).value
			]
			return intValue
		}
		else if (binding.value instanceof BooleanValue){
			val boolValue = XminiJavaFactory::eINSTANCE.createBooleanValue =>[
				value = (binding.value as BooleanValue).value
			]
			return boolValue
		}
		else if (binding.value instanceof ObjectRefValue){
			val objRefValue = XminiJavaFactory::eINSTANCE.createObjectRefValue =>[
				instance = (binding.value as ObjectRefValue).instance
			]
			return objRefValue
		}
		else if (binding.value instanceof ArrayRefValue){
			val arrayRefValue = XminiJavaFactory::eINSTANCE.createArrayRefValue =>[
				instance = (binding.value as ArrayRefValue).instance
			]
			return arrayRefValue
		}
	}
}

@Aspect(className=This)
class ThisAspect extends ExpressionAspect {
	@OverrideAspectMethod
	def Value evaluateExpression(State state) {
		val currentInstance = state.findCurrentFrame.instance
		if (currentInstance === null) {
			throw new RuntimeException('''"this" is not valid in the current context''')
		} else {
			return XminiJavaFactory::eINSTANCE.createObjectRefValue => [instance = currentInstance]
		}
	}
}

@Aspect(className=SymbolRef)
class SymbolRefAspect extends ExpressionAspect {
	@OverrideAspectMethod
	def Value evaluateExpression(State state) {
		state.findCurrentContext.findBinding(_self.symbol).value.copy
	}
}

@Aspect(className=NewObject)
class NewObjectAspect extends ExpressionAspect {
	@OverrideAspectMethod
	def Value evaluateExpression(State state) {

		// Creating instance with default bindings
		val result = XminiJavaFactory::eINSTANCE.createObjectInstance => [type = _self.type]
		state.objectsHeap.add(result)
		for (f : result.type.members.filter(Field)) {
			if (f.defaultValue !== null) {
				val v = f.defaultValue.evaluateExpression(state)
				result.fieldbindings.add(XminiJavaFactory::eINSTANCE.createFieldBinding => [
					field = f;
					value = v
				])
			}
		}

		// Find constructor
		val Method constructor = _self.type.members.filter(Method).findFirst [
			it.name === null && it.params.size === _self.args.size
		]

		// If any, call it
		if (constructor !== null) {

			// Create a context with constructor parameters bindings
			val newContext = XminiJavaFactory::eINSTANCE.createContext
			for (arg : _self.args) {
				val Parameter param = constructor.params.get(_self.args.indexOf(arg))
				val binding = XminiJavaFactory::eINSTANCE.createSymbolBinding => [
					symbol = param;
					value = (arg as Expression).evaluateExpression(state)
				]
				newContext.bindings.add(binding)
			}

			// Make the constructor call in new frame
			val call = XminiJavaFactory::eINSTANCE.createNewCall => [^new = _self]
			state.pushNewFrame(result, call, newContext)
			constructor.body.evaluateStatement(state)
			state.popCurrentFrame

		}

		// Return constructed instance
		return XminiJavaFactory::eINSTANCE.createObjectRefValue => [instance = result]
	}
}

@Aspect(className=NewArray)
class NewArrayAspect extends ExpressionAspect {
	@OverrideAspectMethod
	def Value evaluateExpression(State state) {
		// Creating array with size
		val result = XminiJavaFactory::eINSTANCE.createArrayInstance
		result.size = (_self.size.evaluateExpression(state) as IntegerValue).value
		state.arraysHeap.add(result)

		// Filling array with default values
		val defaultValue = switch (_self.type) {
			IntegerTypeRef: XminiJavaFactory::eINSTANCE.createIntegerValue => [value = 0]
			BooleanTypeRef: XminiJavaFactory::eINSTANCE.createBooleanValue => [value = false]
			StringTypeRef: XminiJavaFactory::eINSTANCE.createNullValue
			ClassRef: XminiJavaFactory::eINSTANCE.createNullValue
		}
		for (i : 1 .. result.size) {
			result.value.add(defaultValue.copy)
		}

		// Return constructed array
		return XminiJavaFactory::eINSTANCE.createArrayRefValue => [instance = result]
	}
}

@Aspect(className=StringConstant)
class StringConstantAspect extends ExpressionAspect {
	@OverrideAspectMethod
	def Value evaluateExpression(State state) {
		return XminiJavaFactory::eINSTANCE.createStringValue => [
			value = _self.value
		]
	}
}

@Aspect(className=IntConstant)
class IntConstantAspect extends ExpressionAspect {
	@OverrideAspectMethod
	def Value evaluateExpression(State state) {
		return XminiJavaFactory::eINSTANCE.createIntegerValue => [
			value = _self.value
		]
	}
}

@Aspect(className=BoolConstant)
class BoolConstantAspect extends ExpressionAspect {
	@OverrideAspectMethod
	def Value evaluateExpression(State state) {
		return XminiJavaFactory::eINSTANCE.createBooleanValue => [
			value = _self.value.equalsIgnoreCase("true")
		]
	}
}

@Aspect(className=ArrayLength)
class ArrayLengthAspect extends ExpressionAspect {
	@OverrideAspectMethod
	def Value evaluateExpression(State state) {
		val arrayRef = _self.array.evaluateExpression(state) as ArrayRefValue
		val size = arrayRef.instance.size
		return XminiJavaFactory::eINSTANCE.createIntegerValue => [
			value = size
		]
	}
}

@Aspect(className=ArrayAccess)
class ArrayAccessAspect extends ExpressionAspect {
	@OverrideAspectMethod
	def Value evaluateExpression(State state) {
		val array = (_self.object.evaluateExpression(state) as ArrayRefValue).instance
		val index = (_self.index.evaluateExpression(state) as IntegerValue).value
		return array.value.get(index).copy
	}

}
