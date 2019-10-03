package cn.com.agree.naha.designer.ui;

import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.ide.IDEActionFactory;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.python.pydev.editor.PyEdit;

/**
 * 
 * <DL>
 * <DT><B> 标题. </B></DT>
 * <p>
 * <DD> 多页编辑器中的多页切换的时候动态改变工具条中的按钮图标 </DD>
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
 * @version 1.00, 2007-5-30 下午02:21:27
 * 
 */
public class FormActionBarContributor extends
		MultiPageEditorActionBarContributor
{
	ActionRegistry actionRegistry = new ActionRegistry();

	private static final String[] WORKBENCH_ACTION_IDS = {
			ActionFactory.PRINT.getId(), ActionFactory.DELETE.getId(),
			ActionFactory.SAVE.getId(), ActionFactory.UNDO.getId(),
			ActionFactory.REDO.getId(), ActionFactory.COPY.getId(),
			ActionFactory.PASTE.getId(), ActionFactory.SELECT_ALL.getId() };

	private static final String[] TEXTEDITOR_ACTION_IDS = {
			ITextEditorActionConstants.PRINT,
			ITextEditorActionConstants.DELETE, ITextEditorActionConstants.SAVE,
			ITextEditorActionConstants.UNDO, ITextEditorActionConstants.REDO,
			ITextEditorActionConstants.CUT, ITextEditorActionConstants.COPY,
			ITextEditorActionConstants.PASTE,
			ITextEditorActionConstants.SELECT_ALL,
			ITextEditorActionConstants.FIND, IDEActionFactory.BOOKMARK.getId() };

	public void setActivePage(IEditorPart ieditorpart)
	{
		// 当页面切换的时候setActivePage会被调用，这时要更新工具条
		IActionBars iactionbars = getActionBars();
		if (iactionbars != null)
		{
			if (ieditorpart instanceof FormEditor)
				hookGraphicEditorActions(((FormEditor) ieditorpart),
						iactionbars);
			else if (ieditorpart instanceof PyEdit)
				hookSrcEditorActions((PyEdit) ieditorpart, iactionbars);
			iactionbars.updateActionBars();
		}
	}

	// 更新源码页的工具条
	private void hookSrcEditorActions(PyEdit structuredtexteditor,
			IActionBars iactionbars)
	{
		for (int i = 0; i < WORKBENCH_ACTION_IDS.length; i++)
			iactionbars.setGlobalActionHandler(WORKBENCH_ACTION_IDS[i],
					structuredtexteditor.getAction(TEXTEDITOR_ACTION_IDS[i]));
	}

	// 更新图形页的工具条
	private void hookGraphicEditorActions(
			FormEditor designergraphicaleditorpage, IActionBars iactionbars)
	{
		DesignerActionRegistry designeractionregistry = designergraphicaleditorpage
				.getEditor().getActionRegistry();
		for (int i = 0; i < WORKBENCH_ACTION_IDS.length; i++)
		{
			IAction iaction = designeractionregistry
					.getAction(WORKBENCH_ACTION_IDS[i]);
			iactionbars
					.setGlobalActionHandler(WORKBENCH_ACTION_IDS[i], iaction);
		}
	}

	public void dispose()
	{
		actionRegistry.dispose();
		super.dispose();
	}

}