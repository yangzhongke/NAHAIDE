/*
 * cn.com.agree.naha.designer.ui.DesignerActionRegistry.java
 *
 * Copyright(C) 2007 Agree Tech, All rights reserved.
 * 
 * Created on 2007-5-28 上午09:34:03 by 杨中科
 */

package cn.com.agree.naha.designer.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.DeleteAction;
import org.eclipse.gef.ui.actions.PrintAction;
import org.eclipse.gef.ui.actions.RedoAction;
import org.eclipse.gef.ui.actions.SaveAction;
import org.eclipse.gef.ui.actions.SelectAllAction;
import org.eclipse.gef.ui.actions.StackAction;
import org.eclipse.gef.ui.actions.UndoAction;
import org.eclipse.gef.ui.actions.UpdateAction;
import org.eclipse.jface.action.IAction;

import cn.com.agree.naha.designer.actions.CopyAction;
import cn.com.agree.naha.designer.actions.PasteAction;

/**
 * 
 * <DL>
 * <DT><B> 标题. </B></DT>
 * <p>
 * <DD> 图形设计器的Action注册器 </DD>
 * </DL>
 * <p>
 * 
 * <DL>
 * <DT><B>使用范例</B></DT>
 * <p>
 * <DD> 使用范例说明 </DD>
 * </DL>
 * <p>
 * 
 * @author 杨中科
 * @author 赞同科技
 * @version 1.00, 2007-5-30 下午02:20:09
 * 
 */
public class DesignerActionRegistry extends ActionRegistry
{
	private FormMultiPageEditor editorPart;

	private List editPartActionIds;

	private List stackActionIds;

	public DesignerActionRegistry(FormMultiPageEditor ieditorpart)
	{
		initEditorPart(ieditorpart);
		initActionLists();
		initActions();
	}

	private void initEditorPart(FormMultiPageEditor ieditorpart)
	{
		editorPart = ieditorpart;
	}

	private void initActionLists()
	{
		editPartActionIds = new ArrayList();
		stackActionIds = new ArrayList();
	}

	private void initActions()
	{
		addStackAction(new UndoAction(editorPart));
		addStackAction(new RedoAction(editorPart));
		addEditPartAction(new DeleteAction(
				(org.eclipse.ui.IWorkbenchPart) editorPart));
		addEditPartAction(new SelectAllAction(editorPart));
		addEditPartAction(new CopyAction(editorPart));
		addEditPartAction(new PasteAction(editorPart));
		addEditPartAction(new SaveAction(editorPart));
		registerAction(new PrintAction(editorPart));
	}

	private void addEditPartAction(IAction action)
	{
		registerAction(action);
		editPartActionIds.add(action.getId());
	}

	private void addStackAction(StackAction stackaction)
	{
		registerAction(stackaction);
		stackActionIds.add(stackaction.getId());
	}

	private void updateActions(List list)
	{
		Iterator iterator = list.iterator();
		while (iterator.hasNext())
		{
			org.eclipse.jface.action.IAction iaction = getAction(iterator
					.next());
			if (iaction != null && iaction instanceof UpdateAction)
				((UpdateAction) iaction).update();
		}
	}

	// 更新编辑器中的Action
	public void updateStackActions()
	{
		updateActions(stackActionIds);
	}

	// 更新编辑器中的Action
	public void updateEditPartActions()
	{
		updateActions(editPartActionIds);
	}

}
