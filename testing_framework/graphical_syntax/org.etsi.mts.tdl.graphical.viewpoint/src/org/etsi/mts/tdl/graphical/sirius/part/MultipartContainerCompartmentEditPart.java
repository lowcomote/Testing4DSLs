package org.etsi.mts.tdl.graphical.sirius.part;

import java.util.List;

import org.eclipse.draw2d.CompoundBorder;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ScrollPane;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ListCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.etsi.mts.tdl.graphical.sirius.EditPartConfiguration;
import org.etsi.mts.tdl.graphical.sirius.layout.LayoutConstraintProvider;

public class MultipartContainerCompartmentEditPart extends ListCompartmentEditPart {

	private GridLayout layoutManager;
	private GridLayoutConfigurer gridLayoutConfigurer = new GridLayoutConfigurer();

	public MultipartContainerCompartmentEditPart(View view) {
		super(view);
		layoutManager = new GridLayout();
		layoutManager.marginWidth = layoutManager.marginHeight = layoutManager.horizontalSpacing = layoutManager.verticalSpacing = 0;
	}

	@Override
	protected boolean hasModelChildrenChanged(Notification evt) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public IFigure createFigure() {
		ResizableCompartmentFigure mainFigure = (ResizableCompartmentFigure) super.createFigure();
		for (Object child: mainFigure.getChildren()) {
			if (child instanceof ScrollPane)
				((ScrollPane) child).setBorder(null);
		}
		
		mainFigure.getContentPane().setLayoutManager(layoutManager);
		mainFigure.getContentPane().setBorder(null);
		
		if (EditPartConfiguration.needsDoubleBorder(this)) {
			CompoundBorder marginLineBorder = new CompoundBorder(new MarginBorder(3), new LineBorder(1));
			CompoundBorder border = new CompoundBorder(new LineBorder(1), marginLineBorder);
			mainFigure.setBorder(border);
		} else
			mainFigure.setBorder(new LineBorder(1));
		
		return mainFigure;
	}
	
	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		//Disable outline of container label compartment
		for (Object f: getFigure().getParent().getChildren())
			if (f instanceof IFigure && !f.equals(getFigure()))
				Util.disableOutlines((IFigure) f);
	}
	
	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		gridLayoutConfigurer.createDefaultEditPolicies(this, layoutManager);
	}
	
	@Override
	public void setLayoutConstraint(EditPart child, IFigure childFigure, Object constraint) {
		if (child instanceof LayoutConstraintProvider)
			constraint = ((LayoutConstraintProvider) child).getConstraint(layoutManager);
		else
			constraint = gridLayoutConfigurer.getLayoutConstraint(childFigure, constraint);
		super.setLayoutConstraint(child, childFigure, constraint);
	}
	
	@Override
	protected List getModelChildren() {
        return Util.getModelChildren(getModel());
    }
	
	@Override
	protected void addChild(EditPart childEditPart, int index) {
		super.addChild(childEditPart, index);
		gridLayoutConfigurer.disableOutlines(childEditPart);
	}
}