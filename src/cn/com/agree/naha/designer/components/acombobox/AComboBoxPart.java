package cn.com.agree.naha.designer.components.acombobox;

import org.eclipse.draw2d.IFigure;

import cn.com.agree.naha.designer.figures.ComponentFigure;
import cn.com.agree.naha.designer.model.Component;
import cn.com.agree.naha.designer.parts.ComponentPart;

public class AComboBoxPart extends ComponentPart
{

	protected IFigure createFigure()
	{
		return new AComboBoxFigure();
	}

	protected void doRefreshFigure(ComponentFigure figure, Component component)
	{
		AComboBoxFigure bf = (AComboBoxFigure) figure;
		AComboBox btn = (AComboBox) component;
		bf.setItems(btn.getItems());
		bf.setSelectedIndex(btn.getSelectedIndex());
	}
}
