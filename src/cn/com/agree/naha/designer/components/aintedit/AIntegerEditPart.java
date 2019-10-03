package cn.com.agree.naha.designer.components.aintedit;

import org.eclipse.draw2d.IFigure;

import cn.com.agree.naha.designer.components.aedit.AEditPart;

public class AIntegerEditPart extends AEditPart
{
	protected IFigure createFigure()
	{
		return new AIntegerEditFigure();
	}
}
