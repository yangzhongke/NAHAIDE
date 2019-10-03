package cn.com.agree.naha.designer.ui;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;

import cn.com.agree.naha.designer.Activator;
import cn.com.agree.naha.designer.model.Form;
import cn.com.agree.naha.designer.parser.FormToPy;

public class FormCreationWizard extends Wizard implements INewWizard
{

	private WizardPage wizardPage;

	private IStructuredSelection selection;

	private IWorkbench workbench;

	public void init(IWorkbench workbench, IStructuredSelection currentSelection)
	{
		this.workbench = workbench;
		this.selection = currentSelection;
	}

	public void addPages()
	{
		this.wizardPage = new WizardPage(this.workbench, this.selection);
		addPage(this.wizardPage);
	}

	public boolean performFinish()
	{
		return this.wizardPage.finish();
	}
}

class WizardPage extends WizardNewFileCreationPage
{

	private IWorkbench workbench;

	public WizardPage(IWorkbench aWorkbench, IStructuredSelection selection)
	{
		super("赞同公司 NAHA 界面编辑器", selection);
		this.setTitle("创建新的NAHA界面");
		this.workbench = aWorkbench;
	}

	public boolean finish()
	{
		final IFile newFile = createNewFile();
		if (newFile == null)
			return false;

		IWorkbenchWindow dwindow = workbench.getActiveWorkbenchWindow();
		final IWorkbenchPage page = dwindow.getActivePage();
		if (page == null)
		{
			return true;
		}

		dwindow.getShell().getDisplay().asyncExec(new Runnable() {

			public void run()
			{
				try
				{
					IDE.setDefaultEditor(newFile, FormEditor.ID);
					IDE.openEditor(page, newFile);
				} catch (PartInitException e)
				{
					Activator.logException(e);
				}
			}

		});

		return true;
	}

	public void createControl(Composite parent)
	{
		super.createControl(parent);
		this.setFileName("*.py");
		setPageComplete(validatePage());
	}

	protected InputStream getInitialContents()
	{
		Form form = new Form();

		InputStream istream = null;
		try
		{
			String code = FormToPy.getAsCode(form);

			istream = new ByteArrayInputStream(code.getBytes());

		} catch (Exception e)
		{
			Activator.logException(e);
		}
		return istream;
	}
}