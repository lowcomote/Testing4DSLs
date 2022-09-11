package org.etsi.mts.tdl.graphical.sirius;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.AbstractEditPartProvider;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.etsi.mts.tdl.graphical.sirius.part.MultiPartLabelEditPart;
import org.etsi.mts.tdl.graphical.sirius.part.MultipartContainerCompartmentEditPart;
import org.etsi.mts.tdl.graphical.sirius.part.NodeContainerEditPart;
import org.etsi.mts.tdl.graphical.sirius.part.NodeListWithHeaderEditPart;
import org.etsi.mts.tdl.graphical.sirius.part.TopLevelNodeListWithHeaderEditPart;

public class TdlEditPartProvider extends AbstractEditPartProvider {
	@Override
	protected Class getEdgeEditPartClass(View view) {
		// TODO Auto-generated method stub
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
					if (mappingName.equals("package") ||
							mappingName.equals("componentType") ||
							mappingName.equals("testConfiguration") ||
							mappingName.equals("action") || 
							mappingName.equals("function") || 
							mappingName.equals("testObjective") || 
							mappingName.equals("structuredDataType") ||
							mappingName.equals("structuredDataInstance") ||
							mappingName.equals("dataResourceMapping") ||
							mappingName.equals("dataElementMapping") ||
							mappingName.equals("testDescription")
							) {
						if (mappingName.equals(EditPartConfiguration.getMappingName(((View)view.eContainer()).getElement()))) {
							if (!view.getChildren().isEmpty())
								return MultipartContainerCompartmentEditPart.class;
						}
					} else if (mappingName.equals("package.imports") || mappingName.equals("package.packagedElements") ||
							mappingName.equals("componentType.timers") || mappingName.equals("componentType.variables") ||
							mappingName.equals("action.parameter") || mappingName.equals("function.parameter") ||
							mappingName.equals("action.body") || mappingName.equals("testObjective.description") ||
							mappingName.equals("testObjective.objectiveURI") || mappingName.equals("structuredDataType.member")  ||
							mappingName.equals("structuredDataInstance.memberAssignment") || mappingName.equals("dataResourceMapping.resourceURI") || 
							mappingName.equals("dataElementMapping.parameterMapping") || mappingName.equals("testDescription.parameter") || 
							mappingName.equals("testDescription.objective") || mappingName.equals("testDescription.configuration") ||
							mappingName.equals("testDescription.behaviour") || mappingName.equals("package.name") || 
							mappingName.equals("action.name") || mappingName.equals("componentType.name") ||
							mappingName.equals("testConfiguration.name") || mappingName.equals("testObjective.name") ||
							mappingName.equals("structuredDataType.name") || mappingName.equals("structuredDataInstance.name") ||
							mappingName.equals("dataResourceMapping.name") || mappingName.equals("dataElementMapping.name") ||
							mappingName.equals("testDescription.name") || mappingName.equals("function.name") ||
							mappingName.equals("function.body")
							) {
						if (!mappingName.equals(EditPartConfiguration.getMappingName(((View)view.eContainer()).getElement())))
							return NodeListWithHeaderEditPart.class;
						
					} else if (mappingName.equals("testConfiguration.componentInstance")
							|| mappingName.equals("simpleDataInstance")
							|| mappingName.equals("simpleDataType")
							|| mappingName.equals("annotationType")
							|| mappingName.equals("time")
							) {
						if (!mappingName.equals(EditPartConfiguration.getMappingName(((View)view.eContainer()).getElement()))) {
							return TopLevelNodeListWithHeaderEditPart.class;
						}
					} else if (mappingName.equals("action.name")) {
						if (!mappingName.equals(EditPartConfiguration.getMappingName(((View)view.eContainer()).getElement()))) {
						}
					} else if (false
							|| mappingName.equals("testConfiguration.configuration")
							|| mappingName.equals("testBehaviour.behaviour")) {
						if (!mappingName.equals(EditPartConfiguration.getMappingName(((View)view.eContainer()).getElement())))
							return NodeContainerEditPart.class;
					} else if (mappingName.equals("function.returnType")) {
						if (!mappingName.equals(EditPartConfiguration.getMappingName(((View)view.eContainer()).getElement())))
							return MultiPartLabelEditPart.class;
					} else if (mappingName.equals("gateType")) {
						if (!mappingName.equals(EditPartConfiguration.getMappingName(((View)view.eContainer()).getElement()))) {
							return TopLevelNodeListWithHeaderEditPart.class;
						}
					} else if (mappingName.equals("gateTypeGateType")) {
						if (!mappingName.equals(EditPartConfiguration.getMappingName(((View)view.eContainer()).getElement()))) {
						}
					} else if (mappingName.equals("gateTypeDataType")) {
						if (!mappingName.equals(EditPartConfiguration.getMappingName(((View)view.eContainer()).getElement()))) {
						}
					}
				}
			}
		}
		// TODO Auto-generated method stub
		return super.getNodeEditPartClass(view);
	}
}
