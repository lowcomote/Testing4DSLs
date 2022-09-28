package org.etsi.mts.tdl.graphical.sirius.part;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.impl.NodeImpl;
import org.eclipse.sirius.diagram.model.business.internal.spec.DNodeListSpec;
import org.eclipse.sirius.diagram.model.business.internal.spec.DNodeSpec;
import org.eclipse.sirius.ext.gmf.runtime.gef.ui.figures.SiriusWrapLabel;
import org.eclipse.sirius.viewpoint.DMappingBased;

public class Util {

	public static void disableOutlines(IFigure f) {
		
		if (f instanceof RectangleFigure)
			((RectangleFigure) f).setOutline(false);
		if (f instanceof RoundedRectangle)
			((RoundedRectangle) f).setOutline(false);
		
		if (f instanceof SiriusWrapLabel) {
			LayoutManager layout = f.getParent().getLayoutManager();
			if (layout == null)
				f.getParent().setLayoutManager(new StackLayout());
		}
		
		for (Object c: f.getChildren())
			disableOutlines((IFigure) c);
	}
	
    @SuppressWarnings({ "unchecked", "restriction", "rawtypes" })
	public static List getModelChildren(Object model) {
    	//this determines the order of compartments
    	//the current approach is not ideal and makes some unsound assumptions 
    	//but it does the job for the time being
        if(model!=null && model instanceof View){
        	ArrayList children = new ArrayList();
        	EList vc = ((View)model).getVisibleChildren();
        	for (Object c : vc) {
        		if (((NodeImpl)c).getElement() instanceof DNodeSpec || 
        				(((NodeImpl)c).getElement() instanceof DNodeListSpec) 
        				&& ((DNodeListSpec)((NodeImpl)c).getElement()).getActualMapping().getName().endsWith(".name")) {
        			children.add(c);
        		}        		
        	}
        	for (Object c : vc) {
        		if (!children.contains(c)) {
        			children.add(c);
        		}        		
        	}
            return children;
        }
        return Collections.EMPTY_LIST;
    }
    
    public static String getMappingId(IGraphicalEditPart part) {
		EObject semantic = part.resolveSemanticElement();
		if (semantic instanceof DMappingBased)
			return ((DMappingBased)semantic).getMapping().getName();
    	return null;
    }
}
