package cn.com.agree.naha.designer.components.apassword;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;

import cn.com.agree.naha.designer.common.MetricsUtils;
import cn.com.agree.naha.designer.figures.ComponentFigure;

public class APasswordFieldFigure extends ComponentFigure
{
	private Label label;
	
	public APasswordFieldFigure()
	{
		super();
		label = new Label();	
		add(label);
	}
	
	protected void paintFigure(Graphics g)
	{
		super.paintFigure(g);
		label.setBounds(getBounds());
		
		int w = MetricsUtils.editorWidthToTermWidth(getBounds().width);
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<w-2;i++)
		{
			sb.append("*");
		}
		this.label.setText("["+sb.toString()+"]");
	}
}
