package org.etsi.mts.tdl.resources;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.etsi.mts.tdl.util.tdlResourceFactoryImpl;
import org.etsi.mts.tdl.util.tdlResourceImpl;

public class TDLResourceFactory extends tdlResourceFactoryImpl implements Factory {
	
	@Override
	public Resource createResource(URI uri) {
		Resource result = new TDLResource(uri);
		return result;
	}

}

class TDLResource extends tdlResourceImpl {

	public TDLResource(URI uri) {
		super(uri);
	}
	
	@Override
	protected boolean useUUIDs() {
		return true;
	}
	
}