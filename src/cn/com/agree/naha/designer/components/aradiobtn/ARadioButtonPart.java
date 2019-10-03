package cn.com.agree.naha.designer.components.aradiobtn;

import java.util.List;

import org.eclipse.draw2d.IFigure;

import cn.com.agree.naha.designer.figures.ComponentFigure;
import cn.com.agree.naha.designer.model.Component;
import cn.com.agree.naha.designer.parts.ComponentPart;

public class ARadioButtonPart extends ComponentPart
{

	protected IFigure createFigure()
	{
		return new ARadioButtonFigure();
	}

	protected void doRefreshFigure(ComponentFigure figure, Component component)
	{
		ARadioButtonFigure cbf = (ARadioButtonFigure) figure;
		ARadioButton btn = (ARadioButton) component;
		cbf.setText(btn.getText());
		cbf.setChecked(btn.isChecked());
		cbf.setGroupId(btn.getGroupId());
		boolean checked = btn.isChecked();
		cbf.setChecked(checked);

		List<Component> components = btn.getForm().getComponents();
		for (Component comp : components)
		{
			if (comp instanceof ARadioButton)
			{
				ARadioButton rb = (ARadioButton) comp;
				if (rb == component)
				{
					continue;
				}

				if (!rb.getGroupId().equals(btn.getGroupId()))
				{
					continue;
				}

				if (checked)
				{
					rb.setChecked(false);
				}

			}
		}
	}
}
