package org.etsi.mts.tdl.graphical.sirius.layout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.ZOrderRequest;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.AbstractLayoutEditPartProvider;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.description.DiagramDescription;
import org.eclipse.sirius.diagram.ui.edit.api.part.IDDiagramEditPart;
import org.eclipse.sirius.diagram.ui.tools.api.layout.provider.DefaultLayoutProvider;
import org.eclipse.sirius.diagram.ui.tools.api.layout.provider.LayoutProvider;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.viewpoint.DMappingBased;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.sirius.viewpoint.description.RepresentationElementMapping;
import org.eclipse.sirius.viewpoint.description.Viewpoint;
import org.etsi.mts.tdl.ComponentInstance;
import org.etsi.mts.tdl.Connection;
import org.etsi.mts.tdl.GateReference;
import org.etsi.mts.tdl.TestConfiguration;
import org.etsi.mts.tdl.graphical.sirius.part.InteractionUseConfiguringEditPart;
import org.etsi.mts.tdl.graphical.sirius.part.Util;

public class SequenceDiagramFreeformLayoutProvider implements LayoutProvider {

	public static final String VIEWPOINT_ID = "org.etsi.mts.tdl";
	public static final String TEST_DESCRIPTION_DIAGRAM_ID = "TestDescriptionDiagram";

	public static final String TIME_LABEL_MAPPING_ID = "timeLabel",
			TIME_CONSTRAINT_MAPPING_ID = "timeConstraint",
			GATE_REFERENCE_MAPPING_ID = "gateReference",
			COMPONENT_INSTANCE_MAPPING_ID = "componentInstance",
			COMBINED_BEHAVIOUR_MAPPING_ID = "combinedBehaviour",
			BLOCK_MAPPING_ID = "block";
	
	private SequenceFreeformLayoutEditPartProvider layoutProvider = new SequenceFreeformLayoutEditPartProvider();

	@Override
	public boolean provides(IGraphicalEditPart container) {
		return layoutProvider.provides(container);
	}

	@Override
	public AbstractLayoutEditPartProvider getLayoutNodeProvider(IGraphicalEditPart container) {
		return layoutProvider;
	}

	@Override
	public boolean isDiagramLayoutProvider() {
		return false;
	}

	static boolean isCustomizedInteractionUse(EditPart editPart) {
		if (editPart instanceof InteractionUseConfiguringEditPart)
			return true;
		for (Object child: editPart.getChildren()) {
			if (isCustomizedInteractionUse((EditPart) child))
				return true;
		}
		return false;
	}
}

class SequenceFreeformLayoutEditPartProvider extends TdlAbstractLayoutEditPartProvider {
	
	private static final int MARGIN = 20;
	
	private boolean defaultLayout = false;

	public boolean provides(IGraphicalEditPart container) {
		if (defaultLayout)
			return false;
		if (container instanceof IDDiagramEditPart) {
			Option<DDiagram> diagram = ((IDDiagramEditPart) container).resolveDDiagram();
			if (diagram.some()) {
				DDiagram d = diagram.get();
				DiagramDescription dDesc = d.getDescription();
				Viewpoint vp = (Viewpoint) dDesc.eContainer();
				if (SequenceDiagramFreeformLayoutProvider.VIEWPOINT_ID.equals(vp.getName()) &&
						SequenceDiagramFreeformLayoutProvider.TEST_DESCRIPTION_DIAGRAM_ID.equals(dDesc.getName()))
					return true;
			}
		}
		return false;
	}

	@Override
	public Command layoutEditParts(List selectedObjects, IAdaptable layoutHint) {
		
		Command defaultCommand = null;
		if (!defaultLayout) {
			try {
				defaultLayout = true;
				
				EditPart editPart = (EditPart) selectedObjects.get(0);
				while (!(editPart instanceof IDDiagramEditPart))
					editPart = (EditPart) editPart.getParent();

				DefaultLayoutProvider defaultProvider = new DefaultLayoutProvider();
				defaultCommand = defaultProvider.layoutEditParts((IGraphicalEditPart)editPart, layoutHint);
				
			} finally {
				defaultLayout = false;
			}
		}
		
		CompoundCommand cc = new CompoundCommand();
		
		if (defaultCommand != null)
			cc.add(defaultCommand);
		
		Map<IGraphicalEditPart, List<Rectangle>> attachmentLocations = new Hashtable<IGraphicalEditPart, List<Rectangle>>();

        double scale = 0;
		for (Object object : selectedObjects) {
			IGraphicalEditPart editPart = (IGraphicalEditPart) object;
			
			if (scale == 0) {
				scale = 1.0;
		        if (editPart.getRoot() instanceof DiagramRootEditPart) {
		            final ZoomManager zoomManager = ((DiagramRootEditPart) editPart.getRoot()).getZoomManager();
		            scale = zoomManager.getZoom();
		        }
			}
			
			EObject dElement = editPart.resolveSemanticElement();
			
			if (!(dElement instanceof DMappingBased))
				continue;
			RepresentationElementMapping mapping = ((DMappingBased)dElement).getMapping();
			
			if (mapping.getName().equals(SequenceDiagramFreeformLayoutProvider.TIME_CONSTRAINT_MAPPING_ID)
					|| mapping.getName().equals(SequenceDiagramFreeformLayoutProvider.TIME_LABEL_MAPPING_ID)) {
				
				IGraphicalEditPart anchorEditPart = (IGraphicalEditPart) ((ConnectionEditPart) ((GraphicalEditPart) editPart)
						.getTargetConnections().get(0)).getSource();
				
				Rectangle bounds = editPart.getFigure().getBounds();
				Rectangle anchorBounds = anchorEditPart.getFigure().getBounds();
				
				Point location = new Point();
				location.x = anchorBounds.x + anchorBounds.width + MARGIN;
				location.y = anchorBounds.y + Math.abs(anchorBounds.height - bounds.height)/2;
				
				Rectangle newBounds = bounds.getCopy();
				newBounds.x = location.x;
				newBounds.y = location.y;
				List<Rectangle> existingLocations = attachmentLocations.get(anchorEditPart);
				if (existingLocations == null)
					attachmentLocations.put(anchorEditPart, existingLocations = new ArrayList<Rectangle>());
				for (Rectangle existing : existingLocations)
					if (existing.intersects(newBounds))
						location.x = newBounds.x = existing.x + existing.width + MARGIN;
				existingLocations.add(newBounds);
				
				Point moveDelta = location.translate(-bounds.x, -bounds.y);
				moveDelta.x *= scale;
				moveDelta.y *= scale;
				
				ChangeBoundsRequest request = new ChangeBoundsRequest(RequestConstants.REQ_MOVE);
				request.setMoveDelta(moveDelta);
				
				Command c = editPart.getCommand(request);
				cc.add(c);
				
			} else if (mapping.getName().equals(SequenceDiagramFreeformLayoutProvider.COMPONENT_INSTANCE_MAPPING_ID)) {
				
				//Find GateReferences
				ComponentInstance ci = (ComponentInstance) ((DSemanticDecorator)dElement).getTarget();
				TestConfiguration conf = (TestConfiguration) ci.eContainer();
				List<GateReference> gates = new ArrayList<GateReference>();
				for (Connection c: conf.getConnection())
					for (GateReference ep: c.getEndPoint())
						if (ep.getComponent() == ci) 
							gates.add(ep);

				//Find GateReference edit parts
				Map registry = editPart.getViewer().getEditPartRegistry();
				List<IGraphicalEditPart> gParts = new ArrayList<IGraphicalEditPart>();
				for (GateReference g: gates)
					gParts.addAll(findEditParts(registry, g, SequenceDiagramFreeformLayoutProvider.GATE_REFERENCE_MAPPING_ID));
				
				Point location = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
				Point extent = new Point(0, 0);
				for (IGraphicalEditPart p : gParts) {
					Rectangle bounds = p.getFigure().getBounds();
					
					Point curLocation = new Point();
					curLocation.x = bounds.x - MARGIN/2;
					curLocation.y = bounds.y - 2*MARGIN;
					location.x = Math.min(location.x, curLocation.x);
					location.y = Math.min(location.y, curLocation.y);
					
					extent.x = Math.max(extent.x, bounds.x + bounds.width + MARGIN/2);
					extent.y = bounds.height + MARGIN;
				}
				Dimension size = new Dimension(extent.x - location.x, extent.y - location.y);

				Rectangle bounds = editPart.getFigure().getBounds();
				Dimension resizeDelta = size.getExpanded(-bounds.width, -bounds.height);
				Point moveDelta = location.translate(-bounds.x, -bounds.y);
				
				final Point NO_MOVE = new Point();
				final Dimension NO_RES = new Dimension();
				if (!NO_MOVE.equals(moveDelta) || !NO_RES.equals(resizeDelta))
					addSetBoundsCommand(resizeDelta, moveDelta, scale, editPart, cc);
				
				
				//Send component instance nodes behind gate instance nodes
				ZOrderRequest sendToBackRequest = new ZOrderRequest(ZOrderRequest.REQ_SEND_TO_BACK);
				sendToBackRequest.setPartsToOrder(Collections.singletonList(editPart));
				cc.add(editPart.getCommand(sendToBackRequest));
				
			} else if (SequenceDiagramFreeformLayoutProvider.isCustomizedInteractionUse(editPart)) {
				
				IFigure f = editPart.getFigure();
				
				Dimension size = f.getPreferredSize().getCopy();
				Rectangle bounds = f.getBounds();
				Dimension resizeDelta = size.getExpanded(-bounds.width, -bounds.height);
				resizeDelta.width = 0;
				resizeDelta.height = Math.max(resizeDelta.height, 0);

				resizeDelta.width *= scale;
				resizeDelta.height *= scale;
				
				ChangeBoundsRequest resRequest = new ChangeBoundsRequest(RequestConstants.REQ_RESIZE);
				resRequest.setSizeDelta(resizeDelta);
				resRequest.setResizeDirection(PositionConstants.SOUTH);
				resRequest.setEditParts(editPart);
				
				Command cmd = editPart.getCommand(resRequest);
				if (cmd instanceof CompoundCommand) {
					ListIterator iter = ((CompoundCommand) cmd).getCommands().listIterator();
					while (iter.hasNext())
						if (!((Command)iter.next()).canExecute())
							iter.remove();
				}
				cc.add(cmd);
				
				editPart.getFigure().invalidateTree();
			}
			
		}
		
		return cc;
	}
	
	private List<IGraphicalEditPart> findEditParts(Map registry, EObject target, String mappingId) {
		List<IGraphicalEditPart> gParts = new ArrayList<IGraphicalEditPart>();
		for (Object d: registry.keySet())
			if (d instanceof DSemanticDecorator)
				if (((DSemanticDecorator)d).getTarget() == target) {
					IGraphicalEditPart part = (IGraphicalEditPart) registry.get(d);
					if (mappingId.equals(Util.getMappingId(part)))
						gParts.add(part);
				}
		return gParts;
		
	}
	
}