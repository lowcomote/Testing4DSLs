package org.imt.xminijava.xtext.validation

import com.google.inject.Inject

import org.eclipse.emf.ecore.EObject
import org.imt.minijava.xminiJava.AccessLevel
import org.imt.minijava.xminiJava.Class
import org.imt.minijava.xminiJava.Member

import static extension org.eclipse.xtext.EcoreUtil2.*
import org.imt.xminijava.xtext.typing.XMiniJavaTypeConformance

class XMiniJavaAccessibility {

	@Inject extension XMiniJavaTypeConformance

	def isAccessibleFrom(Member member, EObject context) {
		val contextClass = context.getContainerOfType(Class)
		val memberClass = member.getContainerOfType(Class)
		switch (contextClass) {
			case contextClass === memberClass:
				true
			case contextClass.isSubclassOf(memberClass):
				member.access != AccessLevel.PRIVATE
			default:
				member.access == AccessLevel.PUBLIC
		}
	}
}
