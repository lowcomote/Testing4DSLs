package org.etsi.mts.tdl.graphical.sirius.part;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;

public class CombinedFragmentLabelEditPart extends MultiPartLabelEditPart {

	public CombinedFragmentLabelEditPart(View view) {
		super(view);
	}
	
	@Override
	protected NodeFigure createNodeFigure() {
		NodeFigure figure = super.createNodeFigure();
		
		figure.addPropertyChangeListener("parent", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				IFigure parent = (IFigure) evt.getNewValue();
				parent.addLayoutListener(new LayoutListener.Stub(){
					@Override
					public boolean layout(IFigure container) {
						if (container == getFigure().getParent()) {

							IFigure f = getFigure();
							IFigure ffLayer = f.getParent();
							
							Dimension prefSize = f.getPreferredSize();
							Rectangle clientArea = ffLayer.getParent().getClientArea();
							Rectangle rect = new Rectangle(clientArea.width - prefSize.width, 0, prefSize.width, prefSize.height);
							
							LayoutManager freeFormLayoutEx = ffLayer.getLayoutManager();
							freeFormLayoutEx.setConstraint(f, rect);
						}
						return false;
					}
				});
				
			}
		});
		
		return figure;
	}
	
	@Override
	public void refresh() {
		super.refresh();
		((RectangleFigure)getPrimaryShape()).setOutline(false);
	}
	
	@Override
	protected boolean needsBottomSeparator(GraphicalEditPart editPart) {
		return false;
	}

}
