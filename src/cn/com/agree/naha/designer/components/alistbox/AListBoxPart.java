package cn.com.agree.naha.designer.components.alistbox;

import org.eclipse.draw2d.IFigure;

import cn.com.agree.naha.designer.figures.ComponentFigure;
import cn.com.agree.naha.designer.model.Component;
import cn.com.agree.naha.designer.parts.ComponentPart;

public class AListBoxPart extends ComponentPart
{

	protected IFigure createFigure()
	{
		return new AListBoxFigure();
	}

	protected void doRefreshFigure(ComponentFigure figure, Component component)
	{
		AListBoxFigure bf = (AListBoxFigure) figure;
		AListBox btn = (AListBox) component;
		bf.setItems(btn.getItems());
	}
}
