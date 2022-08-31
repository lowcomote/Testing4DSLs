package org.etsi.mts.tdl.graphical.labels.naming;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.etsi.mts.tdl.Element;
import org.etsi.mts.tdl.NamedElement;

public class QNameProvider extends DefaultDeclarativeQualifiedNameProvider  {
	@Override
	public QualifiedName getFullyQualifiedName(EObject obj) {
		if (obj instanceof NamedElement) {
			String name = ((Element) obj).getName();
			if (name != null)
				return QualifiedName.create(((Element) obj).getName());
		}
		return super.getFullyQualifiedName(obj);
	}
}
