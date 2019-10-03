package cn.com.agree.naha.designer.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

import cn.com.agree.naha.designer.model.Component;

public class ComponentSetConstraintCommand extends Command
{
	private final Rectangle newEditorBounds;

	private Rectangle oldTermBounds;

	private final ChangeBoundsRequest request;

	private final Component component;

	public ComponentSetConstraintCommand(Component component,
			ChangeBoundsRequest req, Rectangle newBounds)
	{
		if (component == null || req == null || newBounds == null)
		{
			throw new IllegalArgumentException();
		}
		this.component = component;
		this.request = req;
		this.newEditorBounds = newBounds.getCopy();
		setLabel("移动或者缩放");
	}

	public boolean canExecute()
	{
		Object type = request.getType();
		return RequestConstants.REQ_MOVE.equals(type)
				|| RequestConstants.REQ_MOVE_CHILDREN.equals(type)
				|| RequestConstants.REQ_RESIZE.equals(type) 
				|| RequestConstants.REQ_RESIZE_CHILDREN.equals(type)
				||RequestConstants.REQ_ALIGN_CHILDREN.equals(type);
	}

	public void execute()
	{
		oldTermBounds = component.getBounds();
		redo();
	}

	public void redo()
	{
		component.setBounds(component.caculateBounds(newEditorBounds));
	}

	public void undo()
	{
		component.setBounds(oldTermBounds);
	}
}
