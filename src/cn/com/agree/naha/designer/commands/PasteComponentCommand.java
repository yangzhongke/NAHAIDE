package cn.com.agree.naha.designer.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import cn.com.agree.naha.designer.common.MetricsUtils;
import cn.com.agree.naha.designer.model.Component;
import cn.com.agree.naha.designer.model.Form;
import cn.com.agree.naha.designer.policies.ComponentIdManager;

public class PasteComponentCommand extends Command
{

	private Component[] srcComponents;

	private Component[] newComponents;

	/**
	 * @param srcComponent
	 *            源组件
	 */
	public PasteComponentCommand(Component[] srcComponents)
	{
		super();
		this.srcComponents = srcComponents;
	}

	public String getLabel()
	{
		return "粘贴组件";
	}

	public void execute()
	{
		newComponents = new Component[srcComponents.length];
		for (int i = 0, n = srcComponents.length; i < n; i++)
		{
			Component srcComponent = srcComponents[i];
			Form form = srcComponent.getForm();
			// 克隆一个新的Component
			Component newComponent = srcComponent.clone();
			// 清空原有id
			newComponent.setId("");
			// 得到组件的屏幕Bounds
			Rectangle termBounds = newComponent.getBounds();

			// 新组件向右下角移动一个坐标，方便辨认
			termBounds.x = termBounds.x + 1;
			termBounds.y = termBounds.y + 1;

			// 得到编辑器Bounds
			Rectangle editorBounds = MetricsUtils
					.termBoundsToEditorBounds(termBounds);

			newComponent.setBounds(newComponent.caculateBounds(editorBounds));

			// 自动生成控件id
			ComponentIdManager mgr = new ComponentIdManager(form);
			String id = mgr.generateId(newComponent);
			newComponent.setId(id);
			form.addComponent(newComponent);

			newComponents[i] = newComponent;
		}

	}

	public void undo()
	{
		for (int i = 0, n = newComponents.length; i < n; i++)
		{
			Component component = newComponents[i];
			component.getForm().removeComponent(component);
		}
	}

	public void redo()
	{
		execute();
	}
}