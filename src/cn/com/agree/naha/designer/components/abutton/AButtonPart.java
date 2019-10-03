package cn.com.agree.naha.designer.components.abutton;

import org.eclipse.draw2d.IFigure;

import cn.com.agree.naha.designer.figures.ComponentFigure;
import cn.com.agree.naha.designer.model.Component;
import cn.com.agree.naha.designer.parts.ComponentPart;

public class AButtonPart extends ComponentPart
{

	protected IFigure createFigure()
	{
		return new AButtonFigure();
	}

	protected void doRefreshFigure(ComponentFigure figure, Component component)
	{
		AButtonFigure bf = (AButtonFigure) figure;
		AButton btn = (AButton) component;
		bf.setText(btn.getText());
	}
}
