/*
 * cn.com.agree.naha.designer.actions.CopyAction.java
 *
 * Copyright(C) 2007 Agree Tech, All rights reserved.
 * 
 * Created on 2007-5-30 下午01:39:19 by 杨中科
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
		// 如果当前没有选择任何元素的时候，当前的窗口，即FormPart也会被放在list中
		// 所以这时候复制应该不可用
		if (list.size() == 1 && list.get(0) instanceof FormPart)
		{
			return false;
		}
		return getSelectedObjects().size() > 0;
	}
}