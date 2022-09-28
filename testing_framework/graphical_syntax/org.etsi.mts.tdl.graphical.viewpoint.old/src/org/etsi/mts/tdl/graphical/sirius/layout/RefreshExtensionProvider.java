package org.etsi.mts.tdl.graphical.sirius.layout;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartListener;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.ArrangeRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.business.api.refresh.IRefreshExtension;
import org.eclipse.sirius.diagram.business.api.refresh.IRefreshExtensionProvider;
import org.eclipse.sirius.diagram.description.DiagramDescription;
import org.eclipse.sirius.ui.business.api.dialect.DialectEditor;
import org.eclipse.sirius.ui.business.api.session.IEditingSession;
import org.eclipse.sirius.ui.business.api.session.SessionUIManager;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;

public class RefreshExtensionProvider implements IRefreshExtensionProvider {

	@Override
	public boolean provides(DDiagram viewPoint) {
		DiagramDescription desc = viewPoint.getDescription();
//		if (desc != null)
//			return SequenceDiagramFreeformLayoutProvider.TEST_DESCRIPTION_DIAGRAM_ID.equals(desc.getName());
		return false;
	}

	@Override
	public IRefreshExtension getRefreshExtension(DDiagram viewPoint) {
		return new RefreshExtension();
	}

}


class RefreshExtension implements IRefreshExtension {

	@Override
	public void beforeRefresh(DDiagram dDiagram) {}

	@Override
	public void postRefresh(DDiagram dDiagram) {

		EObject target = ((DSemanticDecorator)dDiagram).getTarget();
		Session session = target == null ? null : SessionManager.INSTANCE.getSession(target);
		IEditingSession uiSession = session == null ? null : SessionUIManager.INSTANCE.getUISession(session);
		if (uiSession != null)
			for (final DialectEditor e: uiSession.getEditors()) {
				final IDiagramWorkbenchPart dPart = (IDiagramWorkbenchPart) e;
				final DiagramRootEditPart root = (DiagramRootEditPart) dPart.getDiagramGraphicalViewer().getRootEditPart();

				if (root.getContents() == null) {
					EditPartListener l = new EditPartListener.Stub(){
						@Override
						public void childAdded(EditPart child, int index) {
							root.removeEditPartListener(this);
							runArrangeCommand(e, dPart, root);
						}
					};
					root.addEditPartListener(l);
				} else
					runArrangeCommand(e, dPart, root);
			}
	}
	
	private void runArrangeCommand(final IEditorPart editor, final IDiagramWorkbenchPart dPart, DiagramRootEditPart root) {
		
		EditPart dEditPart = root.getContents();

		ArrangeRequest arrReq = new ArrangeRequest(RequestConstants.REQ_ARRANGE_DEFERRED);
		arrReq.setViewAdaptersToArrange(dEditPart.getChildren());
		final Command command = dEditPart.getCommand(arrReq);
		
		//GMF helper will fail to map view to edit-part if some other editor is active
		final IWorkbenchPage page = editor.getSite().getPage();
		if (editor.equals(page.getActiveEditor()))
			dPart.getDiagramEditDomain().getDiagramCommandStack().execute(command);
		
	}
}