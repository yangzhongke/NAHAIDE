package cn.com.agree.naha.designer.components.arectangle;

import org.eclipse.draw2d.IFigure;

import cn.com.agree.naha.designer.figures.ComponentFigure;
import cn.com.agree.naha.designer.model.Component;
import cn.com.agree.naha.designer.parts.ComponentPart;

public class ARectAnglePart extends ComponentPart
{

	protected IFigure createFigure()
	{
		return new ARectAngleFigure();
	}

	protected void doRefreshFigure(ComponentFigure figure, Component component)
	{
		
	}
}
