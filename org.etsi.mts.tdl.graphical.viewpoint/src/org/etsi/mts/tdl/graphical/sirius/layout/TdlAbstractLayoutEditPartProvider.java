package org.etsi.mts.tdl.graphical.sirius.layout;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.AbstractLayoutEditPartProvider;

public abstract class TdlAbstractLayoutEditPartProvider extends AbstractLayoutEditPartProvider {

	@Override
	public boolean provides(IOperation operation) {
		return true;
	}

	@Override
	public Command layoutEditParts(GraphicalEditPart containerEditPart, IAdaptable layoutHint) {
		return layoutEditParts(containerEditPart.getChildren(), layoutHint);
	}

	protected void addSetBoundsCommand(Dimension resizeDelta, Point moveDelta, double scale, EditPart editPart, CompoundCommand cc) {
		resizeDelta.width *= scale;
		resizeDelta.height *= scale;
		
		//This seems to be the right way to resize nodes, but doesn't work
		//ChangeBoundsRequest resRequest = new ChangeBoundsRequest(RequestConstants.REQ_RESIZE);
		//resRequest.setSizeDelta(resizeDelta);
		//resRequest.setResizeDirection(PositionConstants.CENTER);
		//resRequest.setEditParts(editPart);
		//cc.add(editPart.getCommand(resRequest));
		
		moveDelta.x *= scale;
		moveDelta.y *= scale;
		
		ChangeBoundsRequest request = new ChangeBoundsRequest(RequestConstants.REQ_MOVE);
		// Those 2 should really be in a separate request/command, but
		// doing so would cancel all but the last modification (move)
		request.setSizeDelta(resizeDelta);
		request.setResizeDirection(PositionConstants.CENTER);
		request.setMoveDelta(moveDelta);
		request.setEditParts(editPart);
		
		cc.add(editPart.getCommand(request));
		
	}

}
