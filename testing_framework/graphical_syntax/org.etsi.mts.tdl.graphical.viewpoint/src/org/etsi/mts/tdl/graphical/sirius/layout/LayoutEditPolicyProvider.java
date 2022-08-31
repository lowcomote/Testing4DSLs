package org.etsi.mts.tdl.graphical.sirius.layout;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.requests.ArrangeRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.CreateEditPoliciesOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpolicy.IEditPolicyProvider;
import org.eclipse.sirius.diagram.ui.edit.api.part.ISiriusEditPart;
import org.eclipse.sirius.diagram.ui.graphical.edit.policies.AirResizableEditPolicy;
import org.eclipse.sirius.viewpoint.DMappingBased;
import org.eclipse.sirius.viewpoint.description.RepresentationElementMapping;

public class LayoutEditPolicyProvider implements IEditPolicyProvider {
	
    private final List<IProviderChangeListener> listeners;
    
    public LayoutEditPolicyProvider() {
        this.listeners = new ArrayList<IProviderChangeListener>();
	}

	@Override
	public boolean provides(IOperation operation) {
        if (operation instanceof CreateEditPoliciesOperation) {
            CreateEditPoliciesOperation castedOperation = (CreateEditPoliciesOperation) operation;
            EditPart editPart = castedOperation.getEditPart();
            return editPart instanceof ISiriusEditPart;
        }
		return false;
	}

	@Override
	public void createEditPolicies(EditPart editPart) {
        EditPartViewer viewer = editPart.getViewer();
        if (viewer instanceof DiagramGraphicalViewer) {
        	
        	if (editPart instanceof GraphicalEditPart) {
            	CustomInteractionUsePreferredSizeLayoutPolicy iuep = new CustomInteractionUsePreferredSizeLayoutPolicy();
                editPart.installEditPolicy(CustomInteractionUsePreferredSizeLayoutPolicy.KEY, iuep);
        	}
            

			EObject dElement = ((IGraphicalEditPart)editPart).resolveSemanticElement();
			if (dElement instanceof DMappingBased) {
				RepresentationElementMapping mapping = ((DMappingBased)dElement).getMapping();
				if (mapping.getName().equals(SequenceDiagramFreeformLayoutProvider.GATE_REFERENCE_MAPPING_ID) ||
						mapping.getName().equals(SequenceDiagramFreeformLayoutProvider.COMPONENT_INSTANCE_MAPPING_ID)) {
					HeaderAligningEditPolicy haep = new HeaderAligningEditPolicy();
					editPart.installEditPolicy(HeaderAligningEditPolicy.KEY, haep);
				}
			}
        }
	}

	@Override
	public void addProviderChangeListener(IProviderChangeListener listener) {
        this.listeners.add(listener);
	}

	@Override
	public void removeProviderChangeListener(IProviderChangeListener listener) {
        this.listeners.remove(listener);
	}

}


class CustomInteractionUsePreferredSizeLayoutPolicy extends AirResizableEditPolicy {
	public static final String KEY = "CustomInteractionUsePreferredSizeLayoutPolicy";
	@Override
	public boolean understandsRequest(Request req) {
		if (req instanceof ChangeBoundsRequest) {
			List parts = ((ChangeBoundsRequest) req).getEditParts();
			if (parts != null && !parts.isEmpty()) {
				EditPart part = (EditPart) parts.get(0);
				if (SequenceDiagramFreeformLayoutProvider.isCustomizedInteractionUse(part))
					return super.understandsRequest(req);
			}
		}
		return false;
	}
	@Override
	public Command getCommand(Request request) {
		if (!understandsRequest(request))
			return null;
		return super.getCommand(request);
	}
}


class HeaderAligningEditPolicy extends AbstractEditPolicy {
	public static final String KEY = "HeaderAligningEditPolicy";
	
	private boolean handling = false;

	@Override
	public boolean understandsRequest(Request req) {
		if (handling)
			return false;
		if (req instanceof ChangeBoundsRequest) {
			ChangeBoundsRequest cbReq = (ChangeBoundsRequest) req;
			//User moves have mouse location set, we want to react only to those
			if (cbReq.getLocation() != null)
				return true;
		}
		return false;
	}
	@Override
	public Command getCommand(Request request) {
		if (!understandsRequest(request))
			return null;
		
		handling = true;
		try {
			
			Command originalCmd = getHost().getCommand(request);
			if (originalCmd != null) {
				
				ArrangeRequest arrReq = new ArrangeRequest(RequestConstants.REQ_ARRANGE_DEFERRED);
				DiagramEditPart editPart = (DiagramEditPart) getHost().getRoot().getContents();
				arrReq.setViewAdaptersToArrange(editPart.getChildren());
				Command arrCmd = editPart.getCommand(arrReq);

				originalCmd = originalCmd.chain(arrCmd);
				
				ChangeBoundsRequest cbRequest = (ChangeBoundsRequest) request;
				cbRequest.setMoveDelta(new Point(0, 0));
				cbRequest.setSizeDelta(new Dimension(0, 0));
			}
			
			return originalCmd;
			
		} finally {
			handling = false;
		}
	}
}