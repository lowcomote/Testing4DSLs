package org.etsi.mts.tdl.graphical.sirius.part;

import org.eclipse.draw2d.CompoundBorder;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.ui.tools.api.figure.ViewNodeContainerRectangleFigureDesc;
import org.eclipse.sirius.ext.gmf.runtime.gef.ui.figures.SiriusWrapLabel;
import org.etsi.mts.tdl.graphical.sirius.EditPartConfiguration;

public class TopLevelNodeListWithHeaderEditPart extends NodeListWithHeaderEditPart {

	private ChangableViewNodeContainerRectangleFigureDesc shapeFigure;

	public TopLevelNodeListWithHeaderEditPart(View view) {
		super(view);
	}

    protected IFigure createNodeShape() {
        shapeFigure = new ChangableViewNodeContainerRectangleFigureDesc((View) getAdapter(View.class));
        if (EditPartConfiguration.needsDoubleBorder(this)) {
    		CompoundBorder marginLineBorder = new CompoundBorder(new MarginBorder(3), new LineBorder(1));
    		CompoundBorder border = new CompoundBorder(new LineBorder(1), marginLineBorder);
    		shapeFigure.setBorder(border);
        }
    	return shapeFigure;
    }
    
    @Override
    protected boolean removeBorders() {
    	return false;
    }
    
    @Override
    protected boolean needsBottomSeparator(GraphicalEditPart editPart) {
    	return false;
    }
	
	@Override
	protected DragTracker getCustomDragTracker(Request request) {
    	return null;
	}
	
	@Override
	public void reInitFigure() {
		super.reInitFigure();
		if (shapeFigure != null)
			for  (Object f: shapeFigure.getChildren()) {
				if (f instanceof SiriusWrapLabel) {
					shapeFigure.setLabelFigure((SiriusWrapLabel) f);
					break;
				}
			}
	}

	class ChangableViewNodeContainerRectangleFigureDesc extends ViewNodeContainerRectangleFigureDesc {
	    private SiriusWrapLabel labelFigure;
		public ChangableViewNodeContainerRectangleFigureDesc(View view) {
			super(view);
			labelFigure = super.getLabelFigure();
		}
		@Override
		public SiriusWrapLabel getLabelFigure() {
			return labelFigure;
		}
		public void setLabelFigure(SiriusWrapLabel labelFigure) {
			this.labelFigure = labelFigure;
		}
	}
}
