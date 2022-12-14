/*
 * generated by Xtext 2.26.0
 */
package org.imt.tdl.coverage.xtext.ide;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.xtext.util.Modules2;
import org.imt.tdl.coverage.xtext.COVRuntimeModule;
import org.imt.tdl.coverage.xtext.COVStandaloneSetup;

/**
 * Initialization support for running Xtext languages as language servers.
 */
public class COVIdeSetup extends COVStandaloneSetup {

	@Override
	public Injector createInjector() {
		return Guice.createInjector(Modules2.mixin(new COVRuntimeModule(), new COVIdeModule()));
	}
	
}
