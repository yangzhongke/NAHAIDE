package cn.com.agree.naha.designer.ui;

import java.util.EventObject;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.python.pydev.editor.PyEdit;

import cn.com.agree.naha.designer.Activator;
import cn.com.agree.naha.designer.parser.FormToPy;

public class FormMultiPageEditor extends MultiPageEditorPart
{

	private FormEditor formEditor;

	private PyEdit pyEditor;

	private DesignerActionRegistry actionRegistry;

	private ISelectionListener selectionListener;

	private CommandStackListener commandStackListener;

	private EditDomain editDomain;

	private boolean isDirty = false;

	public FormMultiPageEditor()
	{
		super();
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException
	{
		super.init(site, input);
		IFile file = ((IFileEditorInput) input).getFile();
		setPartName(file.getName());

		initActionRegistry();
		initEditDomain();
		initCommandStackListener();
		initSelectionListener();
	}

	public boolean isDirty()
	{
		if (!isDirty && !super.isDirty())
			return false;
		return true;
	}

	private void initEditDomain()
	{
		editDomain = new DefaultEditDomain(this);
	}

	private void initActionRegistry()
	{
		actionRegistry = new DesignerActionRegistry(this);
	}

	//一般用来供FormActionBarContributor调用
	public DesignerActionRegistry getActionRegistry()
	{
		return actionRegistry;
	}

	//增加界面中组件的选择监听器
	private void initSelectionListener()
	{
		selectionListener = new ISelectionListener() {

			public void selectionChanged(IWorkbenchPart iworkbenchpart,
					ISelection iselection)
			{
				//当被选择的组件发生变化的时候更新Action
				getActionRegistry().updateEditPartActions();
			}
		};
		ISelectionService iselectionservice = getSite().getWorkbenchWindow()
				.getSelectionService();
		iselectionservice.addSelectionListener(selectionListener);
	}

	private void initCommandStackListener()
	{
		commandStackListener = new CommandStackListener() {

			public void commandStackChanged(EventObject eventobject)
			{
				FormMultiPageEditor.this.handleCommandStackChanged();
			}
		};
		getCommandStack().addCommandStackListener(commandStackListener);
	}

	private void handleCommandStackChanged()
	{
		getActionRegistry().updateStackActions();
		if (!isDirty() && getCommandStack().isDirty())
		{
			isDirty = true;
			firePropertyChange(257);
		}
	}

	public CommandStack getCommandStack()
	{
		return editDomain.getCommandStack();
	}

	@Override
	protected void createPages()
	{
		formEditor = new FormEditor(this);
		pyEditor = new PyEdit();

		try
		{
			addPage(formEditor, getEditorInput());
			addPage(pyEditor, getEditorInput());
		} catch (PartInitException e)
		{
			Activator.logException(e);
		}

		setPageText(0, "界面");
		setPageText(1, "代码");
	}

	@Override
	public void doSave(IProgressMonitor monitor)
	{
		if (formEditor.isDirty())
		{
			formEditor.doSave(monitor);
		}
		if (pyEditor.isDirty())
		{
			pyEditor.doSave(monitor);
		}

		getCommandStack().markSaveLocation();
		isDirty = false;
		firePropertyChange(257);

	}

	@Override
	protected void pageChange(int newPageIndex)
	{
		super.pageChange(newPageIndex);
		if (newPageIndex == 0)
		{
			try
			{
				String oldCode = FormToPy.getAsCode(formEditor.getForm());
				String newCode = pyEditor.getDocument().get();
				if (!oldCode.equals(newCode))
				{
					formEditor.setCode(newCode);
				}

			} catch (Exception e)
			{
				Activator.logException(e);
			}

		}
		if (newPageIndex == 1)
		{
			String oldCode = pyEditor.getDocument().get();
			String newCode = FormToPy.getAsCode(formEditor.getForm());
			if (!oldCode.equals(newCode))
			{
				pyEditor.getDocument().set(newCode);
			}
		}
	}

	@Override
	public void doSaveAs()
	{
	}

	@Override
	public boolean isSaveAsAllowed()
	{
		return false;
	}

}
