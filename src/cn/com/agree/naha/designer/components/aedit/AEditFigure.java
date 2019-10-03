package cn.com.agree.naha.designer.components.aedit;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;

import cn.com.agree.naha.designer.figures.ComponentFigure;

public class AEditFigure extends ComponentFigure
{
	private Label label;
	private String text;
	
	public AEditFigure()
	{
		super();
		label = new Label();	
		add(label);
	}

	public void setText(String text)
	{
		this.text = text;
		this.repaint();
	}
	
	protected void paintFigure(Graphics g)
	{
		super.paintFigure(g);
		label.setBounds(getBounds());
		label.setText("["+text+"]");
	}
}
