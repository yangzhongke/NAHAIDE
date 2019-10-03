package cn.com.agree.naha.designer.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.GraphicalEditPolicy;

import cn.com.agree.naha.designer.common.MetricsUtils;
import cn.com.agree.naha.designer.figures.ComponentFigure;
import cn.com.agree.naha.designer.model.Component;
import cn.com.agree.naha.designer.policies.FormComponentEditPolicy;

abstract public class ComponentPart extends AbstractGraphicalEditPart implements
		PropertyChangeListener, GraphicalEditPart
{

	public void activate()
	{
		if (isActive())
		{
			return;
		}
		super.activate();
		((Component) getModel()).addPropertyChangeListener(this);
	}

	public void deactivate()
	{
		if (!isActive())
		{
			return;
		}
		super.deactivate();
		((Component) getModel()).removePropertyChangeListener(this);
	}

	protected void refreshVisuals()
	{
		ComponentFigure figure = (ComponentFigure) this.getFigure();
		Component component = (Component) this.getModel();

		Rectangle termBounds = component.getBounds();
		Rectangle editorBounds = MetricsUtils
				.termBoundsToEditorBounds(termBounds);

		doRefreshFigure(figure, component);

		((GraphicalEditPart) getParent()).setLayoutConstraint(this,
				getFigure(), editorBounds);
	}

	protected abstract void doRefreshFigure(ComponentFigure figure,
			Component component);

	protected void createEditPolicies()
	{
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new FormComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new GraphicalEditPolicy() {
				});
	}

	public void propertyChange(PropertyChangeEvent changeEvent)
	{
		refreshVisuals();
	}

}