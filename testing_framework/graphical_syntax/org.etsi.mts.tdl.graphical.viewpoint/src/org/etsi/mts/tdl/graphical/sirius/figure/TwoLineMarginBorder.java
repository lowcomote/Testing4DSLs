package org.etsi.mts.tdl.graphical.sirius.figure;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.gmf.runtime.draw2d.ui.figures.OneLineBorder;

public class TwoLineMarginBorder extends OneLineBorder {

	private int position1;
	private int position2;
	private Insets margin;

	public TwoLineMarginBorder(int position1, int position2) {
		super(1, position1);
		this.position1 = position1;
		this.position2 = position2;
	}
	
	public void setMargin(int t, int l, int b, int r) {
		margin = new Insets(t, l, b, r);
	}
	
	@Override
	public Insets getInsets(IFigure figure) {
		setPosition(position1);
		Insets insets = super.getInsets(figure);
		setPosition(position2);
		insets.add(super.getInsets(figure));
		insets.add(margin);
		return insets;
	}
	
	@Override
	public void paint(IFigure figure, Graphics graphics, Insets insets) {
		setPosition(position1);
		super.paint(figure, graphics, insets);
		setPosition(position2);
		super.paint(figure, graphics, insets);
	}

}
