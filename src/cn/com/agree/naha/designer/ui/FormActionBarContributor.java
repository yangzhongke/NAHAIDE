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
 * <DT><B> ����. </B></DT>
 * <p>
 * <DD> ��ҳ�༭���еĶ�ҳ�л���ʱ��̬�ı乤�����еİ�ťͼ�� </DD>
 * </DL>
 * <p>
 * 
 * <DL>
 * <DT><B>ʹ�÷���</B></DT>
 * <p>
 * <DD> ʹ�÷���˵�� </DD>
 * </DL>
 * <p>
 * 
 * @author ���п�
 * @author ��ͬ�Ƽ�
 * @version 1.00, 2007-5-30 ����02:21:27
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
		// ��ҳ���л���ʱ��setActivePage�ᱻ���ã���ʱҪ���¹�����
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

	// ����Դ��ҳ�Ĺ�����
	private void hookSrcEditorActions(PyEdit structuredtexteditor,
			IActionBars iactionbars)
	{
		for (int i = 0; i < WORKBENCH_ACTION_IDS.length; i++)
			iactionbars.setGlobalActionHandler(WORKBENCH_ACTION_IDS[i],
					structuredtexteditor.getAction(TEXTEDITOR_ACTION_IDS[i]));
	}

	// ����ͼ��ҳ�Ĺ�����
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