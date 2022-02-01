/*
 * generated by Xtext 2.10.0
 */
package org.imt.xminijava.xtext

import com.google.inject.Binder

import com.google.inject.name.Names
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy
import org.eclipse.xtext.scoping.IScopeProvider
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider
import org.imt.xminijava.xtext.scoping.XMiniJavaImportedNamespaceAwareLocalScopeProvider
import org.imt.xminijava.xtext.scoping.XMiniJavaResourceDescriptionsStrategy

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
class XMiniJavaRuntimeModule extends AbstractXMiniJavaRuntimeModule {

	override void configureIScopeProviderDelegate(Binder binder) {
		binder.bind(IScopeProvider).annotatedWith(
			Names.named(AbstractDeclarativeScopeProvider.NAMED_DELEGATE)
		).to(
			XMiniJavaImportedNamespaceAwareLocalScopeProvider
		)
	}

	def Class<? extends IDefaultResourceDescriptionStrategy> bindIDefaultResourceDescriptionStrategy() {
		return XMiniJavaResourceDescriptionsStrategy;
	}
}
