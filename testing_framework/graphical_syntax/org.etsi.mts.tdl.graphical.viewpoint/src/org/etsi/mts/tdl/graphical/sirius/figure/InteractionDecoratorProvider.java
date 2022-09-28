package org.etsi.mts.tdl.graphical.sirius.figure;

import org.eclipse.draw2d.ArrowLocator;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.CreateDecoratorsOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.DEdge;
import org.etsi.mts.tdl.Interaction;
import org.etsi.mts.tdl.Message;
import org.etsi.mts.tdl.Target;
import org.etsi.mts.tdl.graphical.sirius.EditPartConfiguration;

public class InteractionDecoratorProvider extends AbstractProvider implements IDecoratorProvider {

	@Override
	public boolean provides(IOperation operation) {
        if (!(operation instanceof CreateDecoratorsOperation)) {
            return false;
        }

        IDecoratorTarget decoratorTarget = ((CreateDecoratorsOperation) operation).getDecoratorTarget();
        View view = (View) decoratorTarget.getAdapter(View.class);
        if (view instanceof Edge) {
        	String mappingName = EditPartConfiguration.getMappingName(view.getElement());
        	if (mappingName.equals("interaction"))
        		return true;
        }
        
		return false;
	}

	@Override
	public void createDecorators(IDecoratorTarget decoratorTarget) {
		GraphicalEditPart hostEditPart = (GraphicalEditPart) decoratorTarget.getAdapter(GraphicalEditPart.class);
		PolylineConnection hostFigure = (PolylineConnection)hostEditPart.getFigure();
		
		DEdge edge = (DEdge) ((IGraphicalEditPart)hostEditPart).resolveSemanticElement();
		Message i = (Message) ((Target)edge.getTarget()).eContainer();
		
		if (i.isIsTrigger()) {
			PolylineDecoration targetFigure = new PolylineDecoration();
			
			PointList template = new PointList();
			template.addPoint(-1, -1);
			template.addPoint(0, 0);
			template.addPoint(-1, 1);
			template.addPoint(0, 0);
			template.addPoint(-2, 0);
			template.addPoint(-3, 1);
			template.addPoint(-2, 0);
			template.addPoint(-3, -1);
			targetFigure.setTemplate(template);
			
			targetFigure.setLineAttributes(hostFigure.getLineAttributes());
			targetFigure.setFill(false);
			
			ArrowLocator targetLocator = new ArrowLocator(hostFigure, ConnectionLocator.TARGET);
			hostFigure.add(targetFigure, targetLocator);
		}
		if (i.getTarget().size() > 1) {
			PolygonDecoration sourceFigure = new PolygonDecoration();
			PointList template = new PointList();
			template.addPoint(0, -1);
			template.addPoint(0, 1);
			template.addPoint(1, 1);
			template.addPoint(1, -1);
			sourceFigure.setTemplate(template);
			sourceFigure.setLineAttributes(hostFigure.getLineAttributes());
			ArrowLocator sourceLocator = new ArrowLocator(hostFigure, ConnectionLocator.SOURCE);
			hostFigure.add(sourceFigure, sourceLocator);
		}
	}

}
