/*
 * generated by Xtext 2.25.0
 */
package org.imt.xminijava.xtext.ide;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.xtext.util.Modules2;
import org.imt.xminijava.xtext.XMiniJavaRuntimeModule;
import org.imt.xminijava.xtext.XMiniJavaStandaloneSetup;

/**
 * Initialization support for running Xtext languages as language servers.
 */
public class XMiniJavaIdeSetup extends XMiniJavaStandaloneSetup {

	@Override
	public Injector createInjector() {
		return Guice.createInjector(Modules2.mixin(new XMiniJavaRuntimeModule(), new XMiniJavaIdeModule()));
	}
	
}
