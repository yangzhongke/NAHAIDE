package cn.com.agree.naha.designer.components.amaskedit;

import org.eclipse.draw2d.IFigure;

import cn.com.agree.naha.designer.components.aedit.AEditPart;

public class AMaskEditPart extends AEditPart
{
	protected IFigure createFigure()
	{
		return new AMaskEditFigure();
	}
}
