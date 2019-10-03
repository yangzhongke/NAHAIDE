package cn.com.agree.naha.designer.components.ahline;

import org.eclipse.draw2d.IFigure;

import cn.com.agree.naha.designer.figures.ComponentFigure;
import cn.com.agree.naha.designer.model.Component;
import cn.com.agree.naha.designer.parts.ComponentPart;

public class AHLinePart extends ComponentPart
{

	protected IFigure createFigure()
	{
		return new AHLineFigure();
	}

	protected void doRefreshFigure(ComponentFigure figure, Component component)
	{
		
	}
}
