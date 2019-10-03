package cn.com.agree.naha.designer.components.abizcombobox;

import org.eclipse.draw2d.IFigure;

import cn.com.agree.naha.designer.figures.ComponentFigure;
import cn.com.agree.naha.designer.model.Component;
import cn.com.agree.naha.designer.parts.ComponentPart;

public class ABizComboBoxPart extends ComponentPart
{

	protected IFigure createFigure()
	{
		return new ABizComboBoxFigure();
	}

	protected void doRefreshFigure(ComponentFigure figure, Component component)
	{
		ABizComboBoxFigure bf = (ABizComboBoxFigure) figure;
		ABizComboBox cmb = (ABizComboBox) component;
		bf.setBounds(cmb.getBounds());
	}
}
