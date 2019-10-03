package cn.com.agree.naha.designer.components.acombobox;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;

import cn.com.agree.naha.designer.figures.ComponentFigure;

import com.cownew.ctk.common.EnvironmentUtils;

public class AComboBoxFigure extends ComponentFigure
{
	private String items;
	
	private int selectedIndex;
	
	private Label label = null;
	
	public AComboBoxFigure()
	{
		super();		
		label = new Label();
		label.setLabelAlignment(Label.LEFT);
		add(label);
	}

	public void setItems(String items)
	{
		this.items = items;
		this.repaint();
	}
	
	public void setSelectedIndex(int selectedIndex)
	{
		this.selectedIndex = selectedIndex;
		this.repaint();
	}

	protected void paintFigure(Graphics graphics)
	{
	    super.paintFigure(graphics);	    
	    label.setBounds(getBounds());
	    
	    String[] itemArray = 
	    	items.split(EnvironmentUtils.getLineSeparator());	
	    String text;
	    if(itemArray.length>0&&selectedIndex>=0)
	    {
	    	text = itemArray[selectedIndex];
	    }
	    else
	    {
	    	text = "";
	    }
	    label.setText("<"+text+">");
	}
}
