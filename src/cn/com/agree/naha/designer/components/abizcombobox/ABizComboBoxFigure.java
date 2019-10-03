package cn.com.agree.naha.designer.components.abizcombobox;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;

import cn.com.agree.naha.designer.figures.ComponentFigure;

public class ABizComboBoxFigure extends ComponentFigure
{
	private Label innerList = null;

	public ABizComboBoxFigure()
	{
		super();
		setBorder(new LineBorder());
		innerList = new Label();
		innerList.setLabelAlignment(Label.LEFT);
		innerList.setTextAlignment(Label.TOP);
		add(innerList);
	}

	protected void paintFigure(Graphics graphics)
	{
		super.paintFigure(graphics);
		innerList.setBounds(getBounds());

		innerList.setText("<biz>");

	}
}
