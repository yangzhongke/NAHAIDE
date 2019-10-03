package cn.com.agree.naha.designer.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import cn.com.agree.naha.designer.model.Component;
import cn.com.agree.naha.designer.model.Form;
import cn.com.agree.naha.designer.policies.ComponentIdManager;

public class CreateComponentCommand extends Command
{

	private Form form;

	private Component component;

	private Rectangle bounds;	

	public CreateComponentCommand(Form form, Component component, Rectangle bounds)
	{
		super();
		this.form = form;
		this.component = component;
		this.component.setForm(form);
		this.bounds = bounds;
	}

	public String getLabel()
	{
		return "创建组件";
	}

	public void execute()
	{
		//自动生成控件id
		ComponentIdManager mgr = new ComponentIdManager(form);
		String id = mgr.generateId(component);
		component.setId(id);
		
		component.setBounds(component.caculateBounds(bounds));
		form.addComponent(this.component);
	}

	public void undo()
	{
		form.removeComponent(this.component);
	}

	public void redo()
	{
		execute();
	}
}