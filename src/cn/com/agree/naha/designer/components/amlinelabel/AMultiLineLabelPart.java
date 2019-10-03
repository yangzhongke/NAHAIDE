package cn.com.agree.naha.designer.components.amlinelabel;

import org.eclipse.draw2d.IFigure;

import cn.com.agree.naha.designer.figures.ComponentFigure;
import cn.com.agree.naha.designer.model.Component;
import cn.com.agree.naha.designer.parts.ComponentPart;

public class AMultiLineLabelPart extends ComponentPart
{

	protected IFigure createFigure()
	{
		return new AMultiLineLabelFigure();
	}

	protected void doRefreshFigure(ComponentFigure figure, Component component)
	{
		AMultiLineLabelFigure lf = (AMultiLineLabelFigure) figure;
		AMultiLineLabel label = (AMultiLineLabel) component;
		lf.setText(label.getText());
	}
}
