package cn.com.agree.naha.designer.components.ahline;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;

import cn.com.agree.naha.designer.common.MetricsUtils;
import cn.com.agree.naha.designer.figures.ComponentFigure;

public class AHLineFigure extends ComponentFigure
{
	private Label label;
	
	public AHLineFigure()
	{
		super();
		label = new Label();
		label.setLabelAlignment(PositionConstants.LEFT);
		add(label);
	}
	
	protected void paintFigure(Graphics g)
	{
		super.paintFigure(g);
		label.setBounds(getBounds());
		int w = MetricsUtils.editorWidthToTermWidth(getBounds().width);
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<w/2;i++)
		{
			sb.append("йд");
		}
		label.setText(sb.toString());
	}
}
