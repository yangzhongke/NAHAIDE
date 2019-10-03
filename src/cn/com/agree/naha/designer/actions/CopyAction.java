/*
 * cn.com.agree.naha.designer.actions.CopyAction.java
 *
 * Copyright(C) 2007 Agree Tech, All rights reserved.
 * 
 * Created on 2007-5-30 ����01:39:19 by ���п�
 */

package cn.com.agree.naha.designer.actions;

import java.util.List;

import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;

import cn.com.agree.naha.designer.parts.FormPart;

public class CopyAction extends SelectionAction
{
	public CopyAction(IWorkbenchPart part)
	{
		super(part);
	}

	protected void init()
	{
		setId(ActionFactory.COPY.getId());
	}

	public void run()
	{
		Clipboard.getDefault().setContents(getSelectedObjects());
	}

	@Override
	protected boolean calculateEnabled()
	{
		List list = getSelectedObjects();
		// �����ǰû��ѡ���κ�Ԫ�ص�ʱ�򣬵�ǰ�Ĵ��ڣ���FormPartҲ�ᱻ����list��
		// ������ʱ����Ӧ�ò�����
		if (list.size() == 1 && list.get(0) instanceof FormPart)
		{
			return false;
		}
		return getSelectedObjects().size() > 0;
	}
}