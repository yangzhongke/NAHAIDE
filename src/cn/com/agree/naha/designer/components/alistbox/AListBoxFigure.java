package cn.com.agree.naha.designer.components.alistbox;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;

import cn.com.agree.naha.designer.figures.ComponentFigure;

public class AListBoxFigure extends ComponentFigure
{
	private String items;
	
	private Label innerList = null;
	
	public AListBoxFigure()
	{
		super();
		setBorder(new LineBorder());
		
		innerList = new Label();
		innerList.setLabelAlignment(Label.LEFT);
		innerList.setTextAlignment(Label.TOP);
		add(innerList);
	}

	public void setItems(String items)
	{
		this.items = items;
		this.repaint();
	}
	
	protected void paintFigure(Graphics graphics)
	{
	    super.paintFigure(graphics);	    
	    innerList.setBounds(getBounds());	
		//Label���Է��ô����з��Ķ������֣����Կ����Զ�ʵ�ֶ���Ч��
		innerList.setText(items);		
	}
}
