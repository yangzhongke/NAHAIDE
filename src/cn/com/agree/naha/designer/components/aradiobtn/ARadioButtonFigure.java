package cn.com.agree.naha.designer.components.aradiobtn;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;

import cn.com.agree.naha.designer.figures.ComponentFigure;

public class ARadioButtonFigure extends ComponentFigure
{
	private Label label;
	private boolean checked;
	private String text;
	public ARadioButtonFigure()
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

	public void setChecked(boolean checked)
	{
		this.checked = checked;
		this.repaint();
	}

	public void setGroupId(String groupId)
	{
		this.repaint();
	}
	
	protected void paintFigure(Graphics g)
	{
		super.paintFigure(g);
		label.setBounds(getBounds());
		if(checked)
		{
			label.setText("¡ñ"+text);
		}
		else
		{
			label.setText("¡ð"+text);
		}
	}
}
