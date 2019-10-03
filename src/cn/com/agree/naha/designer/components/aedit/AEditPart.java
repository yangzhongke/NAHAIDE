package cn.com.agree.naha.designer.components.aedit;

import org.eclipse.draw2d.IFigure;

import cn.com.agree.naha.designer.figures.ComponentFigure;
import cn.com.agree.naha.designer.model.Component;
import cn.com.agree.naha.designer.parts.ComponentPart;

public class AEditPart extends ComponentPart
{

	protected IFigure createFigure()
	{
		return new AEditFigure();
	}

	protected void doRefreshFigure(ComponentFigure figure, Component component)
	{
		AEditFigure bf = (AEditFigure) figure;
		AEdit btn = (AEdit) component;
		String text = btn.getText();
		bf.setText(text);
	}
}
