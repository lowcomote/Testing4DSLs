package org.etsi.mts.tdl.graphical.sirius.part;

import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.sirius.diagram.ui.edit.api.part.IDiagramContainerEditPart;
import org.eclipse.swt.SWT;

class GridLayoutConfigurer {
	
	public void createDefaultEditPolicies(GraphicalEditPart editPart, LayoutManager layoutManager) {
		editPart.removeEditPolicy(EditPolicy.LAYOUT_ROLE);
		editPart.installEditPolicy(EditPolicy.LAYOUT_ROLE, new GridLayoutPolicy(editPart, layoutManager));
	}

	public void disableOutlines(EditPart childEditPart) {
		if (childEditPart instanceof GraphicalEditPart) {
			IFigure f = ((GraphicalEditPart) childEditPart).getFigure();
			if (!(childEditPart instanceof IDiagramContainerEditPart))
				disableOutlines(f);
		}
	}

	public void disableOutlines(IFigure f) {
		Util.disableOutlines(f);
	}
	
	public Object getLayoutConstraint(IFigure childFigure, Object constraint) {
		if (constraint != null && !(constraint instanceof GridData)) {
			constraint = getLayoutConstraint(childFigure);
		}
		return constraint;
	}
	
	public Object getLayoutConstraint(IFigure f) {
		GridData gridData = new GridData(SWT.FILL, GridData.FILL, true, true, 1, 1);
		return gridData;
	}
}