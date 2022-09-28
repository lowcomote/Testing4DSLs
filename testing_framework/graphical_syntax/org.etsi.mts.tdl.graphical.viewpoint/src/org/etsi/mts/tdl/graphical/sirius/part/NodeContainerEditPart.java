package org.etsi.mts.tdl.graphical.sirius.part;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.ui.edit.api.part.AbstractDiagramContainerEditPart;

public class NodeContainerEditPart extends AbstractDiagramContainerEditPart {

	public NodeContainerEditPart(View view) {
		super(view);
	}
	
	@Override
	protected NodeFigure createMainFigure() {
		NodeFigure mainfigure = super.createMainFigure();
		IFigure primaryShape = getPrimaryShape();
		if (primaryShape instanceof RoundedRectangle)
			((RoundedRectangle) primaryShape).setOutline(false);
		if (primaryShape instanceof RectangleFigure)
			((RectangleFigure) primaryShape).setOutline(false);
		return mainfigure;
	}
	
	@Override
	protected void addDropShadow(NodeFigure figure, IFigure shape) {}
	
    public DragTracker getDragTracker(Request request) {
    	DragTracker dt = getCustomDragTracker(request);
    	if (dt != null)
    		return dt;
    	return super.getDragTracker(request);
    }
    
    protected DragTracker getCustomDragTracker(Request request) {
    	EditPart p = getParent();
    	while (!(p.getParent() instanceof DiagramEditPart))
    		p = p.getParent();
        return p.getDragTracker(request);
    }

}
