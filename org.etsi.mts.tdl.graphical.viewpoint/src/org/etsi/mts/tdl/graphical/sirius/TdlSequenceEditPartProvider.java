package org.etsi.mts.tdl.graphical.sirius;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.AbstractEditPartProvider;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.DDiagramElementContainer;
import org.eclipse.sirius.diagram.DNode;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.etsi.mts.tdl.graphical.sirius.part.CombinedFragmentLabelEditPart;
import org.etsi.mts.tdl.graphical.sirius.part.InteractionUseConfiguringEditPart;
import org.etsi.mts.tdl.graphical.sirius.part.TopLevelNodeListWithHeaderEditPart;

public class TdlSequenceEditPartProvider extends AbstractEditPartProvider {
	@Override
	protected Class getEdgeEditPartClass(View view) {
		return super.getEdgeEditPartClass(view);
	}
	
	@Override
	protected Class getNodeEditPartClass(View view) {
		EObject viewElement = view.getElement();
		if (viewElement instanceof DSemanticDecorator) {
			EObject e = ((DSemanticDecorator) viewElement).getTarget();
			if (e instanceof org.etsi.mts.tdl.Element) {
				String mappingName = EditPartConfiguration.getMappingName(viewElement);
				if (mappingName != null) {
					view.eContainer();
					if (mappingName.equals("assertion.config") ||
							mappingName.equals("verdictAssignment.config") ||
							mappingName.equals("timerOperation.config") ||
							mappingName.equals("actionReference.config") ||
							mappingName.equals("inlineAction.config") ||
							mappingName.equals("assignment.config") ||
							mappingName.equals("testDescriptionReference.config") ||
							mappingName.equals("timeOperation.config")
							) {
						return InteractionUseConfiguringEditPart.class;
					} else if (mappingName.equals("boundedLoopBehaviour") ||
							mappingName.equals("periodicBehaviour")) {
						if (!mappingName.equals(EditPartConfiguration.getMappingName(((View)view.eContainer()).getElement())))
							return CombinedFragmentLabelEditPart.class;
					} else if (mappingName.equals("componentInstance")) {
						if (!mappingName.equals(EditPartConfiguration.getMappingName(((View)view.eContainer()).getElement()))) {
							return TopLevelNodeListWithHeaderEditPart.class;
						}
					}
				}
			}
		}
		return super.getNodeEditPartClass(view);
	}
}
