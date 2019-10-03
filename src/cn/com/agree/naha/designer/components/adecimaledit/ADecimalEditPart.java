package cn.com.agree.naha.designer.components.adecimaledit;

import org.eclipse.draw2d.IFigure;

import cn.com.agree.naha.designer.components.aedit.AEditPart;

public class ADecimalEditPart extends AEditPart
{

	protected IFigure createFigure()
	{
		return new ADecimalEditFigure();
	}
	
	
}
