package cn.com.agree.naha.designer.components.acheckbox;

import org.eclipse.draw2d.IFigure;

import cn.com.agree.naha.designer.figures.ComponentFigure;
import cn.com.agree.naha.designer.model.Component;
import cn.com.agree.naha.designer.parts.ComponentPart;

public class ACheckBoxPart extends ComponentPart
{

	protected IFigure createFigure()
	{
		return new ACheckBoxFigure();
	}

	protected void doRefreshFigure(ComponentFigure figure, Component component)
	{
		ACheckBoxFigure cbf = (ACheckBoxFigure) figure;
		ACheckBox label = (ACheckBox) component;
		cbf.setText(label.getText());
		cbf.setChecked(label.isChecked());
	}
}
