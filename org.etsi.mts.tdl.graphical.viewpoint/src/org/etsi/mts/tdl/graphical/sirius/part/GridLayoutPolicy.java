package org.etsi.mts.tdl.graphical.sirius.part;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

class GridLayoutPolicy extends ConstrainedLayoutEditPolicy {

	private GraphicalEditPart target;
	private LayoutManager layoutManager;

	public GridLayoutPolicy(GraphicalEditPart target, LayoutManager layoutManager) {
		this.target = target;
		this.layoutManager = layoutManager;
	}

	@Override
	protected Object getConstraintFor(Point point) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object getConstraintFor(Rectangle rect) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected EditPolicy createChildEditPolicy(EditPart child) {
		//No independent drag
		return new ResizableEditPolicy() {
			@Override
			public EditPart getTargetEditPart(Request request) {
				if (RequestConstants.REQ_SELECTION.equals(request.getType()))
					return target;
				return super.getTargetEditPart(request);
			}
		};
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {

		TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();

		AbstractTransactionalCommand c = new AbstractTransactionalCommand(editingDomain,
				DiagramUIMessages.SetLocationCommand_Label_Resize, null) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {
				layoutManager.layout(target.getFigure());
				return CommandResult.newOKCommandResult();
			}
		};

		return new ICommandProxy(c);
	}
	
}