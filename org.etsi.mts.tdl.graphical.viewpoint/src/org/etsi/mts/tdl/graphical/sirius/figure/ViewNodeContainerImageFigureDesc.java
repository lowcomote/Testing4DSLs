package org.etsi.mts.tdl.graphical.sirius.figure;

import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.ui.tools.api.figure.IWorkspaceImageFigure;
import org.eclipse.sirius.diagram.ui.tools.api.figure.ViewNodeContainerRectangleFigureDesc;

public class ViewNodeContainerImageFigureDesc extends ViewNodeContainerRectangleFigureDesc {
	
	
	public ViewNodeContainerImageFigureDesc(View view) {
		super(view);
		MarginBorder border = (MarginBorder) getBorder();
		border.getInsets(this).top = 15;
		border.getInsets(this).left = 10;
		border.getInsets(this).right = 10;
	}
    
    @Override
    public void setBounds(Rectangle rect) {
    	IWorkspaceImageFigure imgfigure = (IWorkspaceImageFigure)getParent().getChildren().get(0);
    	
    	double imageAspectRatio = imgfigure.getImageAspectRatio();
    	final int newHeight = (int) (rect.width / imageAspectRatio);
    	final int newWidth = (int) (rect.height * imageAspectRatio);
    	
    	Rectangle rect2 = rect.getCopy();
    	if (newHeight > rect.height) {
    		rect2.height = newHeight;
    	} else {
    		rect2.width = newWidth;
    	}
    	
    	super.setBounds(rect2.getCopy());
    	imgfigure.setBounds(rect2.getCopy());
    	getParent().setBounds(rect2.getCopy());
    	getParent().getParent().setBounds(rect2.getCopy());
    }

}
