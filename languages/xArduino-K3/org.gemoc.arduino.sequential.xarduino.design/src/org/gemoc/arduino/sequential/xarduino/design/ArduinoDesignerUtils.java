package org.gemoc.arduino.sequential.xarduino.design;

import org.eclipse.core.internal.resources.Project;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.ui.business.api.session.IEditingSession;
import org.eclipse.sirius.ui.business.api.session.SessionUIManager;

/**
 * Utility for arduino designer.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 *
 */
public final class ArduinoDesignerUtils {

	/**
	 * Constructor.
	 */
	private ArduinoDesignerUtils() {
		// nothing to do here
	}

	/**
	 * Gets the opened {@link Project}.
	 * 
	 * @return the opened {@link Project} if nay, <code>null</code> otherwise
	 */
	public static Project getOpenedProject() {
		Project res = null;

		for (IEditingSession session : SessionUIManager.INSTANCE
				.getUISessions()) {
			for (Resource resource : session.getSession()
					.getSemanticResources()) {
				for (EObject eObj : resource.getContents()) {
					if (eObj instanceof Project
							&& eObj.eResource().getURI().isPlatformResource()) {
						res = (Project) eObj;
						break;
					}
				}
			}
		}

		return res;
	}
	
	public static Project getContainingProject(EObject eObj) {
		Project res = null;

		EObject current = eObj.eContainer();
		while (current != null) {
			if (current instanceof Project) {
				res = (Project) current;
				break;
			}
			current = current.eContainer();
		}

		return res;
	}

}
