package org.etsi.mts.tdl.graphical.sirius.part;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.etsi.mts.tdl.graphical.sirius.figure.ViewNodeContainerImageFigureDesc;

public class TopLevelImageNodeListWithHeaderEditPart extends TopLevelNodeListWithHeaderEditPart {

	public TopLevelImageNodeListWithHeaderEditPart(View view) {
		super(view);
	}
	
	@Override
	protected IFigure createNodeShape() {
        IFigure shapeFigure = new ViewNodeContainerImageFigureDesc((View) getAdapter(View.class));
    	return shapeFigure;
	}
	
	@Override
	protected void configureBorder(IFigure shapeFigure) {
		if (shapeFigure instanceof Shape)
			((Shape) shapeFigure).setOutline(false);
	}

}
