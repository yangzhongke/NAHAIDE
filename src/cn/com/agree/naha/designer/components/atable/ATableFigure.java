package cn.com.agree.naha.designer.components.atable;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;

import cn.com.agree.naha.designer.figures.ComponentFigure;

public class ATableFigure extends ComponentFigure
{
	private HeaderInfo[] header;

	private Label innerList = null;

	public ATableFigure()
	{
		super();
		setBorder(new LineBorder());
		innerList = new Label();		
		innerList.setLabelAlignment(Label.LEFT);
		innerList.setTextAlignment(Label.TOP);
		add(innerList);
	}

	public void setHeader(HeaderInfo[] header)
	{
		this.header = header;
		repaint();
	}

	protected void paintFigure(Graphics graphics)
	{
		super.paintFigure(graphics);
		innerList.setBounds(getBounds());
		StringBuffer sb = new StringBuffer();
		for (int i = 0, n = header.length; i < n; i++)
		{
			HeaderInfo info = header[i];
			String caption = info.getCaption();
			sb.append(caption);
			sb.append("|");
		}
		innerList.setText(sb.toString());

	}
}
