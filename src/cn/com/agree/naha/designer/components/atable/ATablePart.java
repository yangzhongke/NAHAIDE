package cn.com.agree.naha.designer.components.atable;

import org.eclipse.draw2d.IFigure;

import cn.com.agree.naha.designer.figures.ComponentFigure;
import cn.com.agree.naha.designer.model.Component;
import cn.com.agree.naha.designer.parts.ComponentPart;

public class ATablePart extends ComponentPart
{

	protected IFigure createFigure()
	{
		return new ATableFigure();
	}

	protected void doRefreshFigure(ComponentFigure figure, Component component)
	{
		ATableFigure bf = (ATableFigure) figure;
		ATable cmb = (ATable) component;
		bf.setBounds(cmb.getBounds());
		bf.setHeader(cmb.getHeader());
	}
}
