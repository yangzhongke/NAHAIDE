package cn.com.agree.naha.designer.commands;

import org.eclipse.gef.commands.Command;

import cn.com.agree.naha.designer.model.Form;
import cn.com.agree.naha.designer.model.Component;

public class DeleteComponentCommand extends Command
{

	private Form form;

	private Component component;

	public DeleteComponentCommand(Form form, Component component)
	{
		super();
		this.form = form;
		this.component = component;
	}

	public String getLabel()
	{
		return "É¾³ý×é¼þ";
	}

	public void execute()
	{
		this.form.removeComponent(component);
		component.setForm(null);
	}

	public void undo()
	{
		this.form.addComponent(component);
		component.setForm(form);
	}

	public void redo()
	{
		this.execute();
	}
}