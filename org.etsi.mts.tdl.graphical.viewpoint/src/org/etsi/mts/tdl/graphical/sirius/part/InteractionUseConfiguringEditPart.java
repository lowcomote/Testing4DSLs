package org.etsi.mts.tdl.graphical.sirius.part;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderedNodeFigure;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.ui.edit.api.part.AbstractNotSelectableShapeNodeEditPart;
import org.eclipse.sirius.diagram.ui.tools.api.figure.SVGWorkspaceImageFigure;
import org.eclipse.sirius.ext.draw2d.figure.ITransparentFigure;
import org.eclipse.sirius.ext.gmf.runtime.gef.ui.figures.AbstractTransparentNode;
import org.eclipse.sirius.ext.gmf.runtime.gef.ui.figures.SiriusWrapLabel;

/**
 * This class should be mapped to an "abstract" sub-node of interaction use. The
 * node need not have a style as it will not be visible. The first label of the
 * interaction use is the label of the container. Rest of the labels should be
 * sub-nodes with square styles.
 */
public class InteractionUseConfiguringEditPart extends AbstractNotSelectableShapeNodeEditPart {

	public InteractionUseConfiguringEditPart(View view) {
		super(view);
	}

	@Override
	protected NodeFigure createNodeFigure() {
		AbstractTransparentNode f = new AbstractTransparentNode(){};
		return f;
	}
	
	@Override
	public void activate() {
		super.activate();
		final IFigure figure = getFigure();
		
		IFigure imgContainer = findImageContainer(figure);
		if (imgContainer != null) {
			imgContainer.setOpaque(true);
		}
		
		for (Object child: figure.getParent().getChildren()) {
			if (child instanceof BorderedNodeFigure) {
				((BorderedNodeFigure) child).getMainFigure().setLayoutManager(new StackLayout());
				IFigure labelParent = findLabel((IFigure) child).getParent();
				labelParent.setLayoutManager(new ConstrainedToolbarLayout());
				if (labelParent instanceof ITransparentFigure) {
					((ITransparentFigure) labelParent).setTransparent(true);
					((ITransparentFigure) labelParent).setSiriusAlpha(0);
					((Shape)labelParent).setOutline(false);
				}
			}
		}
		
		XYLayout xyLayout = new XYLayout(){
			@Override
			protected Dimension calculatePreferredSize(IFigure f, int wHint, int hHint) {
				return layout(f, false, wHint, hHint);
			}
			@Override
			public void layout(IFigure parent) {
				layout(parent, true, -1, -1);
			}
			private Dimension layout(IFigure parent, boolean move, int wHint, int hHint) {
				List children = parent.getChildren();
				int y = 0;
				int width = wHint != -1 ? wHint : parent.getParent().getClientArea().width;
				for (Object c : children) {
					IFigure f = (IFigure) c;
					if (figure == f)
						continue;
					
					IFigure sizeFigure = findLabel(f);
					if (sizeFigure == null)
						sizeFigure = f;
					
					Dimension size = sizeFigure.getPreferredSize().getCopy();
					//Margins/spacing
					size.expand(10, 5);
					
					if (y == 0)
						//Leave space for container label
						y = size.height;
					
					if (move) {
						Rectangle rect = new Rectangle(0, y, size.width , size.height);
						rect.width = width;
						if (!rect.equals(f.getBounds())) {
							f.setBounds(rect);
							f.invalidateTree();
						}
					}
					
					y += size.height;
				}
				return new Dimension(width, 2*y);
			}
		};
		figure.getParent().setLayoutManager(xyLayout);
		
	}
	
	private SiriusWrapLabel findLabel(IFigure f) {
		for (Object c : f.getChildren())
			if (c instanceof SiriusWrapLabel)
				return (SiriusWrapLabel) c;
			else {
				SiriusWrapLabel label = findLabel((IFigure) c);
				if (label != null)
					return label;
			}
		return null;
	}
	
	private IFigure findImageContainer(IFigure f) {
		for (Object c: f.getChildren())
			if (c instanceof SVGWorkspaceImageFigure)
				return f;
		if (f.getParent() == null)
			return null;
		return findImageContainer(f.getParent());
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		
	}

}
