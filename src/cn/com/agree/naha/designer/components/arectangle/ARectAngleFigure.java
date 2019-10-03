package cn.com.agree.naha.designer.components.arectangle;

import org.eclipse.draw2d.Graphics;

import cn.com.agree.naha.designer.common.MetricsUtils;
import cn.com.agree.naha.designer.figures.ComponentFigure;

public class ARectAngleFigure extends ComponentFigure
{
	public ARectAngleFigure()
	{
		super();
	}
	
	protected void paintFigure(Graphics g)
	{
		super.paintFigure(g);
		g.setLineWidth(MetricsUtils.CHARWIDTH);
		g.drawRectangle(getBounds());
	}
}
