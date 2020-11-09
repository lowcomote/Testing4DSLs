package org.etsi.mts.tdl.scoping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.scoping.IScope;

/**
 * This class contains custom scoping description.
 * 
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#scoping on
 * how and when to use it
 * 
 */
public class TDLan2ScopeProvider extends TDLScopeProvider {

	@Override
	public IScope getScope(EObject context, EReference reference) {
		return super.getScope(context, reference);
	}

}