package org.etsi.mts.tdl.graphical.labels.resource;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Triple;

public class AdaptiveLinkingResource extends LazyLinkingResource {
	@Override
	protected EObject getEObject(String uriFragment, Triple<EObject, EReference, INode> triple) throws AssertionError {
		List<EObject> linkedObjects = getLinkingService().getLinkedObjects(
				triple.getFirst(), 
				triple.getSecond(),
				triple.getThird());
		
		if (!linkedObjects.isEmpty()) {
			EObject newObject = linkedObjects.get(0);
			if (!triple.getSecond().getEType().isInstance(newObject)) {
				//Was replaced
				return null;
			}
		}
		return super.getEObject(uriFragment, triple);
	}
}
