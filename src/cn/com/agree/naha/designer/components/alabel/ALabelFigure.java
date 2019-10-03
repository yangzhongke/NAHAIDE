package cn.com.agree.naha.designer.components.alabel;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;

import cn.com.agree.naha.designer.figures.ComponentFigure;

public class ALabelFigure extends ComponentFigure
{
	private Label label;
	private String text;
	
	public ALabelFigure()
	{
		super();
		label = new Label();
		label.setLabelAlignment(PositionConstants.LEFT);
		add(label);
	}

	public void setText(String text)
	{
		this.text = text;
		this.repaint();
	}

	protected void paintFigure(Graphics gc)
	{
		super.paintFigure(gc);
		label.setBounds(getBounds());
		label.setText(text);
	}
}
