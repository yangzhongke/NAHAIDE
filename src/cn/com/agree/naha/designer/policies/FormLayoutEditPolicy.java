package cn.com.agree.naha.designer.policies;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import cn.com.agree.naha.designer.commands.ComponentSetConstraintCommand;
import cn.com.agree.naha.designer.commands.CreateComponentCommand;
import cn.com.agree.naha.designer.model.Component;
import cn.com.agree.naha.designer.model.Form;
import cn.com.agree.naha.designer.parts.ComponentPart;

public class FormLayoutEditPolicy extends XYLayoutEditPolicy
{
	protected Command createAddCommand(EditPart child, Object constraint)
	{
		return null; // no support for adding
	}

	protected Command createChangeConstraintCommand(
			ChangeBoundsRequest request, EditPart child, Object constraint)
	{
		if (child instanceof ComponentPart && constraint instanceof Rectangle)
		{
			Component component = (Component) child.getModel();
			Rectangle rect = (Rectangle) constraint;
			// return a command that can move and/or resize a Shape
			return new ComponentSetConstraintCommand(component, request, rect);
		}
		return super.createChangeConstraintCommand(request, child, constraint);
	}

	protected Command createChangeConstraintCommand(EditPart child,
			Object constraint)
	{
		return null;
	}

	protected Command getCreateCommand(CreateRequest request)
	{
		Form form = (Form) getHost().getModel();
		Component component = (Component) request.getNewObject();
		Rectangle constraint = (Rectangle) getConstraintFor(request);

		CreateComponentCommand command = new CreateComponentCommand(form,
				component, constraint);
		return command;
	}

	protected Command getDeleteDependantCommand(Request request)
	{
		return null; // no support for deleting a dependant
	}
}