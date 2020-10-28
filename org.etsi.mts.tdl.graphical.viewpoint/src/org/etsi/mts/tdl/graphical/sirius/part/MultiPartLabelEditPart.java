package org.etsi.mts.tdl.graphical.sirius.part;

import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.SWT;
import org.etsi.mts.tdl.graphical.sirius.EditPartConfiguration;
import org.etsi.mts.tdl.graphical.sirius.layout.LayoutConstraintProvider;

public class MultiPartLabelEditPart extends TopLevelNodeListWithHeaderEditPart implements LayoutConstraintProvider {

	private GridLayout layoutManager;
	private GridLayoutConfigurer gridLayoutConfigurer = new GridLayoutConfigurer();
	
	public MultiPartLabelEditPart(View view) {
		super(view);
		layoutManager = new GridLayout(10, false) {
			@Override
			public void setConstraint(IFigure figure, Object newConstraint) {
				Object oldConstraint = getConstraint(figure);
				if (oldConstraint == null || newConstraint instanceof GridData)
					super.setConstraint(figure, newConstraint);
			}
		};
		layoutManager.marginWidth = layoutManager.marginHeight = layoutManager.horizontalSpacing = layoutManager.verticalSpacing = 0;
	}
	
	@Override
	protected IFigure createNodeShape() {
		IFigure mainFigure = super.createNodeShape();
		mainFigure.setBorder(null);
		return mainFigure;
	}
	
	@Override
	protected NodeFigure createMainFigure() {
		NodeFigure nodefigure = super.createMainFigure();
		if (nodefigure instanceof DefaultSizeNodeFigure) {
			((DefaultSizeNodeFigure) nodefigure).setDefaultSize(20, 20);
		}
		return nodefigure;
	}
	
	@Override
	protected void configureBorder(IFigure shapeFigure) {}
	
	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		gridLayoutConfigurer.createDefaultEditPolicies(this, layoutManager);
	}
	
	@Override
	public void setLayoutConstraint(EditPart child, IFigure childFigure, Object constraint) {
		constraint = gridLayoutConfigurer.getLayoutConstraint(childFigure, constraint);
		super.setLayoutConstraint(child, childFigure, constraint);
	}
	
	@Override
	public void refresh() {
		super.refresh();
		getPrimaryShape().getLabelFigure().setVisible(false);
	}
	
	@Override
	protected void addChild(EditPart childEditPart, int index) {
		super.addChild(childEditPart, index);
		
		gridLayoutConfigurer.disableOutlines(childEditPart);
		
		IFigure child = ((GraphicalEditPart)childEditPart).getFigure();
		if (child instanceof ResizableCompartmentFigure) {
			IFigure cp = ((ResizableCompartmentFigure) child).getContentPane();
			//Set grid layout to content pane
			cp.setLayoutManager(layoutManager);
			for (Object cc : cp.getChildren()) {
				IFigure cf = (IFigure) cc;
				//Set grid data constraints for content pane children
				GridData constraint = (GridData) gridLayoutConfigurer.getLayoutConstraint(cf);
				constraint.grabExcessVerticalSpace = false;
				constraint.grabExcessHorizontalSpace = false;
				layoutManager.setConstraint(cf, constraint);
			}
		}
	}
	
	@Override
	protected boolean removeBorders() {
		return true;
	}
	
	@Override
	protected boolean needsBottomSeparator(GraphicalEditPart editPart) {
		return EditPartConfiguration.needsBottomSeparator((IGraphicalEditPart) editPart);
	}

	@Override
	public Object getConstraint(LayoutManager layout) {
		GridData gridData = new GridData(SWT.CENTER, GridData.FILL, false, true, 1, 1);
		return gridData;
	}
}
