package org.imt.xminijava.xtext.typing

import org.imt.minijava.xminiJava.Assignment
import org.imt.minijava.xminiJava.BoolConstant
import org.imt.minijava.xminiJava.BooleanTypeRef
import org.imt.minijava.xminiJava.Class
import org.imt.minijava.xminiJava.ClassRef
import org.imt.minijava.xminiJava.Expression
import org.imt.minijava.xminiJava.IntConstant
import org.imt.minijava.xminiJava.IntegerTypeRef
import org.imt.minijava.xminiJava.Method
import org.imt.minijava.xminiJava.XminiJavaFactory
import org.imt.minijava.xminiJava.XminiJavaPackage
import org.imt.minijava.xminiJava.Null
import org.imt.minijava.xminiJava.Return
import org.imt.minijava.xminiJava.StringConstant
import org.imt.minijava.xminiJava.StringTypeRef
import org.imt.minijava.xminiJava.Super
import org.imt.minijava.xminiJava.SymbolRef
import org.imt.minijava.xminiJava.This
import org.imt.minijava.xminiJava.TypeRef
import org.imt.minijava.xminiJava.VariableDeclaration

import static extension org.eclipse.xtext.EcoreUtil2.*
import org.imt.minijava.xminiJava.FieldAccess
import org.imt.minijava.xminiJava.MethodCall
import org.imt.minijava.xminiJava.VoidTypeRef
import org.imt.minijava.xminiJava.TypeDeclaration
import org.imt.minijava.xminiJava.Interface
import org.imt.minijava.xminiJava.TypedDeclaration
import org.imt.minijava.xminiJava.NewObject

class XMiniJavaTypeComputer {
	private static val factory = XminiJavaFactory.eINSTANCE
	public static val STRING_TYPE = factory.createClass => [name = 'stringType']
	public static val INT_TYPE = factory.createClass => [name = 'intType']
	public static val BOOLEAN_TYPE = factory.createClass => [name = 'booleanType']
	public static val NULL_TYPE = factory.createClass => [name = 'nullType']

	static val ep = XminiJavaPackage.eINSTANCE

	def TypeDeclaration getType(TypeRef r) {
		switch r {
			ClassRef: r.referencedClass
			IntegerTypeRef: INT_TYPE
			BooleanTypeRef: BOOLEAN_TYPE
			StringTypeRef: STRING_TYPE
			VoidTypeRef : NULL_TYPE
		}
	}

	def TypeDeclaration typeFor(Expression e) {
		switch (e) {
			SymbolRef:
				e.symbol.typeRef.type
			FieldAccess:
				e.field.typeRef.type
			MethodCall:
				e.method.typeRef.type
			NewObject:
				e.type
			This:
				e.getContainerOfType(Class)
			Super:
				e.getContainerOfType(Class).superClass
			Null:
				NULL_TYPE
			StringConstant:
				STRING_TYPE
			IntConstant:
				INT_TYPE
			BoolConstant:
				BOOLEAN_TYPE
		}
	}


	def isPrimitive(TypeDeclaration c) {
		c.eResource === null
	}

	def TypeDeclaration expectedType(Expression e) {
		val c = e.eContainer
		val f = e.eContainingFeature
		switch (c) {
			VariableDeclaration:
				c.typeRef.type
			Assignment case f == ep.assignment_Value: {
				val assignee = c.assignee
				switch (assignee) {
					VariableDeclaration: assignee.typeRef.type
					FieldAccess: assignee.typeFor
				}
			}
			Return:
				c.getContainerOfType(Method).typeRef.type
			case f == ep.ifStatement_Expression:
				BOOLEAN_TYPE
			MethodCall case f == ep.methodCall_Args: {
				if (c.method !== null) {
				if (c.method.params.size > c.args.indexOf(e))
					c.method.params.get(c.args.indexOf(e)).typeRef.type
				}
			}
			NewObject case f == ep.newObject_Args: {
				c.type.members.filter(Method).findFirst[it.name === null && it.params.size === c.args.size].params.get(c.args.indexOf(e)).typeRef.type
			}
		}
	}
}
