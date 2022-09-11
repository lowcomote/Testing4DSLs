package org.etsi.mts.tdl.graphical.sirius;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.sirius.diagram.DDiagramElementContainer;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.DNode;
import org.eclipse.sirius.diagram.DNodeListElement;
import org.eclipse.sirius.viewpoint.description.IdentifiedElement;
import org.etsi.mts.tdl.graphical.sirius.part.Util;

public class EditPartConfiguration {

	public static String getMappingName(EObject viewElement) {
		String mappingName = null;
		if (viewElement instanceof DNode)
			mappingName = ((DNode) viewElement).getActualMapping().getName();
		else if (viewElement instanceof DEdge)
			return ((IdentifiedElement)((DEdge) viewElement).getActualMapping()).getName();
		else if (viewElement instanceof DDiagramElementContainer)
			mappingName = ((DDiagramElementContainer) viewElement).getActualMapping().getName();
		else if (viewElement instanceof DNodeListElement)
			mappingName = ((DNodeListElement) viewElement).getActualMapping().getName();
		return mappingName;
	}
	
	public static boolean needsDoubleBorder(IGraphicalEditPart editPart) {
		String mappingName = Util.getMappingId(editPart);
		if (mappingName == null)
			return false;
		if (mappingName.equals("structuredDataType")
				|| mappingName.equals("simpleDataType")
				|| mappingName.equals("annotationType")
				|| mappingName.equals("time"))
			return true;
		return false;
	}

	public static boolean needsBottomSeparator(IGraphicalEditPart editPart) {
		if (isLastPart(editPart))
			return false;
		String mappingName = Util.getMappingId(editPart);
		if (mappingName == null)
			return false;
		if (mappingName.equals("function.name")
				|| mappingName.equals("function.returnType"))
			return false;
		return true;
	}
	
	public static boolean needsTopSeparator(IGraphicalEditPart editPart) {
		String mappingName = Util.getMappingId(editPart);
		if (mappingName == null)
			return false;
		if (mappingName.equals("function.parameter"))
			return true;
		return false;
	}
	
	public static boolean isLastPart(IGraphicalEditPart editPart) {
		String mappingName = Util.getMappingId(editPart);
		if (mappingName == null)
			return false;
		if (mappingName.equals("package.imports")
				|| mappingName.equals("componentType.variables")
				|| mappingName.equals("action.body")
				|| mappingName.equals("function.body")
				|| mappingName.equals("testObjective.objectiveURI")
				|| mappingName.equals("structuredDataType.member")
				|| mappingName.equals("structuredDataInstance.memberAssignment")
				|| mappingName.equals("simpleDataInstance.name")
				|| mappingName.equals("dataResourceMapping.resourceURI")
				|| mappingName.equals("dataElementMapping.parameterMapping")
				|| mappingName.equals("testDescription.behaviour")
				)
			return true;
		return false;
	}
}
