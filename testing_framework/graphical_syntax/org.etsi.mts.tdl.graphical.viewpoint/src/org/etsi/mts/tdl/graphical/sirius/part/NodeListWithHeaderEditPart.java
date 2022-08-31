package org.etsi.mts.tdl.graphical.sirius.part;

import java.util.List;

import org.eclipse.draw2d.CompoundBorder;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.ui.edit.api.part.AbstractDiagramListEditPart;
import org.eclipse.sirius.diagram.ui.edit.api.part.IDiagramNameEditPart;
import org.eclipse.sirius.ext.gmf.runtime.gef.ui.figures.OneLineMarginBorder;
import org.etsi.mts.tdl.graphical.sirius.EditPartConfiguration;
import org.etsi.mts.tdl.graphical.sirius.figure.TwoLineMarginBorder;

public class NodeListWithHeaderEditPart extends AbstractDiagramListEditPart {

	public NodeListWithHeaderEditPart(View view) {
		super(view);
	}
	
	@Override
	protected void addDropShadow(NodeFigure figure, IFigure shape) {}
	
	protected boolean removeBorders() {
		return true;
	}
	
	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		if (removeBorders())
			Util.disableOutlines(getPrimaryShape());
	}
	
	@Override
	public void refresh() {
		super.refresh();
		replaceBorders();
	}
	
	@Override
	protected List getModelChildren() {
        return Util.getModelChildren(getModel());
    }
	
	@Override
	protected void addChildVisual(EditPart childEditPart, int index) {
		super.addChildVisual(childEditPart, index);
		if (childEditPart instanceof GraphicalEditPart)
			replaceBorder((GraphicalEditPart) childEditPart, index);
	}
	
	private void replaceBorders() {
		int index = 0;
		for (Object childEditPart: getChildren()) {
			if (childEditPart instanceof GraphicalEditPart) {
				replaceBorder((GraphicalEditPart) childEditPart, index);
				index++;
			}
		}
	}
	
	protected boolean needsBottomSeparator(GraphicalEditPart editPart) {
		return EditPartConfiguration.needsBottomSeparator((IGraphicalEditPart) editPart);
	}
	
	private void replaceBorder(GraphicalEditPart childEditPart, int index) {
		IFigure f = childEditPart.getFigure();
		String mappingName = Util.getMappingId((IGraphicalEditPart)childEditPart);
		if (mappingName.equals("gateTypeGateType")) {
			CompoundBorder marginLineBorder = new CompoundBorder(new MarginBorder(3), new LineBorder(1));
			CompoundBorder border = new CompoundBorder(new LineBorder(1), marginLineBorder);
			f.setBorder(border);
		} else if (childEditPart instanceof IDiagramNameEditPart)
			f.setBorder(new MarginBorder(index == 0 ? 5 : 0, 5, index == 0 ? 0 : 5, 5));
		else if (!needsBottomSeparator(childEditPart))
			f.setBorder(null);
		else if (EditPartConfiguration.needsTopSeparator((IGraphicalEditPart) childEditPart)) {
			f.setBorder(null);
			TwoLineMarginBorder border = new TwoLineMarginBorder(PositionConstants.TOP, PositionConstants.BOTTOM);
			border.setMargin(0, 0, 5, 0);
			f.getParent().setBorder(border);
		} else {
			OneLineMarginBorder border = new OneLineMarginBorder(PositionConstants.BOTTOM);
			border.setMargin(0, 0, 5, 0);
			f.setBorder(border);
		}
	}
	
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
