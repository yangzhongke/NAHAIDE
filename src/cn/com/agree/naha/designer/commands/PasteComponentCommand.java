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
	 *            Դ���
	 */
	public PasteComponentCommand(Component[] srcComponents)
	{
		super();
		this.srcComponents = srcComponents;
	}

	public String getLabel()
	{
		return "ճ�����";
	}

	public void execute()
	{
		newComponents = new Component[srcComponents.length];
		for (int i = 0, n = srcComponents.length; i < n; i++)
		{
			Component srcComponent = srcComponents[i];
			Form form = srcComponent.getForm();
			// ��¡һ���µ�Component
			Component newComponent = srcComponent.clone();
			// ���ԭ��id
			newComponent.setId("");
			// �õ��������ĻBounds
			Rectangle termBounds = newComponent.getBounds();

			// ����������½��ƶ�һ�����꣬�������
			termBounds.x = termBounds.x + 1;
			termBounds.y = termBounds.y + 1;

			// �õ��༭��Bounds
			Rectangle editorBounds = MetricsUtils
					.termBoundsToEditorBounds(termBounds);

			newComponent.setBounds(newComponent.caculateBounds(editorBounds));

			// �Զ����ɿؼ�id
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