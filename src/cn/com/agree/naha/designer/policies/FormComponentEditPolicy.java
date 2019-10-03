package cn.com.agree.naha.designer.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import cn.com.agree.naha.designer.commands.DeleteComponentCommand;
import cn.com.agree.naha.designer.model.Component;
import cn.com.agree.naha.designer.model.Form;

public class FormComponentEditPolicy extends ComponentEditPolicy
{

	protected Command createDeleteCommand(GroupRequest request)
	{
		Object parent = getHost().getParent().getModel();
		Form form = (Form) parent;
		Component component = (Component) getHost().getModel();
		DeleteComponentCommand deleteCommand = new DeleteComponentCommand(form,
				component);
		return deleteCommand;
	}
}