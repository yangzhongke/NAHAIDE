package cn.com.agree.naha.designer.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import cn.com.agree.naha.designer.model.Form;
import cn.com.agree.naha.designer.policies.FormLayoutEditPolicy;

public class FormPart extends AbstractGraphicalEditPart implements
		PropertyChangeListener
{

	protected List getModelChildren()
	{
		return ((Form) this.getModel()).getComponents();
	}

	public void activate()
	{
		if (isActive())
		{
			return;
		}
		super.activate();
		((Form) getModel()).addPropertyChangeListener(this);
	}

	public void deactivate()
	{
		if (!isActive())
		{
			return;
		}
		super.deactivate();
		((Form) getModel()).removePropertyChangeListener(this);
	}

	protected IFigure createFigure()
	{
		Figure figure = new FreeformLayer();
		figure.setLayoutManager(new FreeformLayout());
		return figure;
	}

	protected void createEditPolicies()
	{
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new FormLayoutEditPolicy());
	}

	public void propertyChange(PropertyChangeEvent event)
	{
		String prop = event.getPropertyName();
		if (Form.COMPONENTS.equals(prop))
		{
			refreshChildren();
		}
	}
}