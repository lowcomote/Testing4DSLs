package org.etsi.mts.tdl.graphical.labels.scoping;

import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.ImportUriGlobalScopeProvider;
import org.eclipse.xtext.scoping.impl.SelectableBasedScope;
import org.etsi.mts.tdl.ElementImport;
import org.etsi.mts.tdl.Package;
import org.etsi.mts.tdl.tdlPackage;

import com.google.common.base.Predicate;
import com.google.inject.Inject;

public class ConfigurableScopeProvider extends ImportUriGlobalScopeProvider {

	@Inject
	private IResourceServiceProvider resourceServiceProvider;
	
	private Map<URI, LinkedHashSet<URI>> importedUris = new Hashtable<URI, LinkedHashSet<URI>>();
	
	@Override
	protected LinkedHashSet<URI> getImportedUris(Resource resource) {
		
		URI uri = resource.getURI();

		ResourceSet rs = new ResourceSetImpl();
		Resource realResource = rs.getResource(uri, true);
		
		LinkedHashSet<URI> imports = new LinkedHashSet<URI>();
		importedUris.put(uri, imports);
		
		imports.add(uri);

		//Add resource URIs of imported packages
		for (EObject e: realResource.getContents()) {
			if (tdlPackage.eINSTANCE.getPackage().isInstance(e)) {
				Package p = (Package) e;
				addImports(p, imports);
			}
		}
		
		return imports;
	}
	
	private void addImports(Package p, LinkedHashSet<URI> imports) {
		for (ElementImport pi: p.getImport()) {
			Package ip = pi.getImportedPackage();
			URI iUri = EcoreUtil.getURI(ip).trimFragment();
			imports.add(iUri);
			addImports(ip, imports);
		}
	}
	
	@Override
	protected IScope createLazyResourceScope(IScope parent, URI uri,
			IResourceDescriptions descriptions, EClass type,
			Predicate<IEObjectDescription> filter, boolean ignoreCase) {

		ResourceSet rs = new ResourceSetImpl();
		Resource resource = rs.getResource(uri, true);
		
		IResourceDescription description = resourceServiceProvider.getResourceDescriptionManager().getResourceDescription(resource);
		IScope scope = SelectableBasedScope.createScope(parent, description, filter, type, ignoreCase);
		
		return scope;
	}
}
