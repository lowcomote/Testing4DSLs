package org.etsi.mts.tdl.graphical.labels.serializer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.etsi.mts.tdl.NamedElement;

public class DataCrossReferenceSerializer implements org.eclipse.xtext.serializer.tokens.ICrossReferenceSerializer {

	@Override
	public boolean isValid(EObject context, CrossReference crossref, EObject target, INode node,
			Acceptor errorAcceptor) {
		return true;
	}

	@Override
	public String serializeCrossRef(EObject context, CrossReference crossref, EObject target, INode node,
			Acceptor errorAcceptor) {
		if (target instanceof NamedElement)
			return ((NamedElement) target).getName();
		return "<unresolved reference>";
	}

}
