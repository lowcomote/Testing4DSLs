package org.imt.xminijava.xtext.scoping

import com.google.inject.Inject

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.resource.IContainer
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.imt.minijava.xminiJava.XminiJavaPackage
import org.imt.minijava.xminiJava.Class

class XMiniJavaIndex {
	@Inject ResourceDescriptionsProvider rdp
	@Inject IContainer.Manager cm

	def getVisibleExternalClassesDescriptions(EObject o) {
		val allVisibleClasses =
			o.getVisibleClassesDescriptions
		val allExportedClasses =
			o.getExportedClassesEObjectDescriptions
		val difference = allVisibleClasses.toSet
		difference.removeAll(allExportedClasses.toSet)
		return difference.toMap[qualifiedName]
	}

	def getVisibleClassesDescriptions(EObject o) {
		o.getVisibleEObjectDescriptions(XminiJavaPackage.eINSTANCE.class_)
	}

	def getVisibleEObjectDescriptions(EObject o, EClass type) {
		o.getVisibleContainers.map [ container |
			container.getExportedObjectsByType(type)
		].flatten
	}

	def getVisibleContainers(EObject o) {
		val index = rdp.getResourceDescriptions(o.eResource)
		val rd = index.getResourceDescription(o.eResource.URI)
		cm.getVisibleContainers(rd, index)
	}

	def getResourceDescription(EObject o) {
		val index = rdp.getResourceDescriptions(o.eResource)
		index.getResourceDescription(o.eResource.URI)
	}

	def getExportedEObjectDescriptions(EObject o) {
		o.getResourceDescription.getExportedObjects
	}

	def getExportedClassesEObjectDescriptions(EObject o) {
		o.getResourceDescription.getExportedObjectsByType(XminiJavaPackage.eINSTANCE.class_)
	}

}
