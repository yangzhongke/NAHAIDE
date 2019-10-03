package cn.com.agree.naha.designer.components.abutton;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;

import cn.com.agree.naha.designer.figures.ComponentFigure;

public class AButtonFigure extends ComponentFigure
{
	private Label label;
	
	private String text;
	
	public AButtonFigure()
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
		this.label.setText("["+text+"]");
		
	}
}
