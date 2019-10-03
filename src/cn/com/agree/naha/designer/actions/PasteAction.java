/*
 * cn.com.agree.naha.designer.actions.CopyAction.java
 *
 * Copyright(C) 2007 Agree Tech, All rights reserved.
 * 
 * Created on 2007-5-30 ����01:39:19 by ���п�
 */

package cn.com.agree.naha.designer.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;

import cn.com.agree.naha.designer.commands.PasteComponentCommand;
import cn.com.agree.naha.designer.model.Component;
import cn.com.agree.naha.designer.parts.ComponentPart;

public class PasteAction extends SelectionAction
{
	public PasteAction(IWorkbenchPart part)
	{
		super(part);
	}

	protected void init()
	{
		setId(ActionFactory.PASTE.getId());
	}

	public void run()
	{
		List list = (List) Clipboard.getDefault().getContents();
		List newComponentList = new ArrayList();
		for (int i = 0, n = list.size(); i < n; i++)
		{
			Object item = list.get(i);
			if (item instanceof ComponentPart)
			{
				ComponentPart part = (ComponentPart) item;
				Component component = (Component) part.getModel();

				newComponentList.add(component);

			}
		}
		Component[] srcComponents = (Component[]) newComponentList
				.toArray(new Component[newComponentList.size()]);
		// ��������ճ��Ҳ��һ��command���������Ա�֤undo/redo��ԭ����
		PasteComponentCommand cmd = new PasteComponentCommand(srcComponents);
		execute(cmd);
	}

	@Override
	protected boolean calculateEnabled()
	{
		Object data = Clipboard.getDefault().getContents();
		// ͼ��༭���и��Ƶ������ComponentPart���͵�
		// ���ҷŵ�ճ�����е��Ƿ���List�е�ComponentPart
		// ���Բο�CopyAction
		if (!(data instanceof List))
		{
			return false;
		}

		List list = (List) data;
		for (int i = 0, n = list.size(); i < n; i++)
		{
			Object item = list.get(i);
			if (item instanceof ComponentPart)
			{
				return true;
			}
		}
		return false;
	}
}