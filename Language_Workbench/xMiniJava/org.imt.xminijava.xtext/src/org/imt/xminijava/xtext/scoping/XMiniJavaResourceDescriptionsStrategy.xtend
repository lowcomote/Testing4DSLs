package org.imt.xminijava.xtext.scoping

import com.google.inject.Singleton

import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy
import org.eclipse.xtext.util.IAcceptor
import org.imt.minijava.xminiJava.Block

@Singleton
class XMiniJavaResourceDescriptionsStrategy extends DefaultResourceDescriptionStrategy {

	override createEObjectDescriptions(EObject e, IAcceptor<IEObjectDescription> acceptor) {
		if (e instanceof Block) {
			// don't index contents of a block
			false
		} else
			super.createEObjectDescriptions(e, acceptor)
	}
}
