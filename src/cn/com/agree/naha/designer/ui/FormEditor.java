package cn.com.agree.naha.designer.ui;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.EventObject;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.PartInitException;

import cn.com.agree.naha.designer.Activator;
import cn.com.agree.naha.designer.common.ComponentUtils;
import cn.com.agree.naha.designer.common.MetricsUtils;
import cn.com.agree.naha.designer.model.Form;
import cn.com.agree.naha.designer.parser.FormToPy;
import cn.com.agree.naha.designer.parser.PyToForm;
import cn.com.agree.naha.designer.parts.PartFactory;

import com.cownew.ctk.io.ResourceUtils;

public class FormEditor extends GraphicalEditorWithPalette
{
	public static final String ID = "cn.com.agree.naha.designer.uieditor";

	private PaletteRoot paletteRoot;

	private boolean savePreviouslyNeeded;

	private Form form = new Form();

	private FormMultiPageEditor editor;

	public FormEditor(FormMultiPageEditor editor)
	{
		super();
		this.editor = editor;
		// an EditDomain is a "session" of editing which contains things
		// like the CommandStack
		setEditDomain(new DefaultEditDomain(this));
	}

	public FormMultiPageEditor getEditor()
	{
		return editor;
	}

	protected Form getForm()
	{
		return form;
	}

	protected void configureGraphicalViewer()
	{
		super.configureGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setRootEditPart(new ScalableFreeformRootEditPart());

		viewer.setEditPartFactory(new PartFactory());

		viewer.setProperty(SnapToGrid.PROPERTY_GRID_VISIBLE, Boolean.TRUE);
		viewer.setProperty(SnapToGrid.PROPERTY_GRID_ENABLED, Boolean.TRUE);
		Dimension gridSpace = new Dimension(MetricsUtils.CHARWIDTH,
				MetricsUtils.CHARHEIGHT);
		viewer.setProperty(SnapToGrid.PROPERTY_GRID_SPACING, gridSpace);
	}

	public void commandStackChanged(EventObject event)
	{
		if (isDirty())
		{
			if (!this.savePreviouslyNeeded)
			{
				this.savePreviouslyNeeded = true;
				firePropertyChange(IEditorPart.PROP_DIRTY);
			}
		} else
		{
			savePreviouslyNeeded = false;
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
		super.commandStackChanged(event);
	}

	protected void setInput(IEditorInput input)
	{
		super.setInput(input);

		try
		{
			IStorageEditorInput storageInput = (IStorageEditorInput) input;
			InputStream istream = storageInput.getStorage().getContents();
			String code = ComponentUtils.parseString(istream);
			setCode(code);
		} catch (Exception e)
		{
			Activator.logException(e);
		}
	}

	public void setCode(String code) throws Exception
	{
		form = PyToForm.getAsForm(code);
		if (getGraphicalViewer() != null)
		{
			getGraphicalViewer().setContents(form);
		}

	}

	protected void createGraphicalViewer(Composite parent)
	{

		Display display = Display.getCurrent();

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(null);

		container.setBackground(display.getSystemColor(SWT.COLOR_BLACK));

		UIGraphicalViewer viewer = new UIGraphicalViewer();
		viewer.createControl(container);
		setGraphicalViewer(viewer);
		configureGraphicalViewer();
		hookGraphicalViewer();
		initializeGraphicalViewer();

	}

	protected void initializeGraphicalViewer()
	{
		getGraphicalViewer().setContents(this.form);

		FormTemplateTransferDropTargetListener listener = new FormTemplateTransferDropTargetListener(
				getGraphicalViewer());
		// 允许用户使用拖放的方式添加新图形，而不是选择加点击的方式
		getGraphicalViewer().addDropTargetListener(listener);
	}

	protected void initializePaletteViewer()
	{
		super.initializePaletteViewer();
		TemplateTransferDragSourceListener listener = new TemplateTransferDragSourceListener(
				getPaletteViewer());
		getPaletteViewer().addDragSourceListener(listener);
	}

	public void doSave(IProgressMonitor monitor)
	{
		ByteArrayInputStream inStream = null;
		try
		{
			IFile file = ((IFileEditorInput) getEditorInput()).getFile();
			String code = FormToPy.getAsCode(form);
			inStream = new ByteArrayInputStream(code.getBytes());
			file.setContents(inStream, true, false, monitor);
			getCommandStack().markSaveLocation();
		} catch (CoreException e)
		{
			Activator.logException(e);
		} finally
		{
			ResourceUtils.close(inStream);
		}

	}

	public boolean isDirty()
	{
		return getCommandStack().isDirty();
	}

	public boolean isSaveAsAllowed()
	{
		return false;
	}

	public void init(IEditorSite site, IEditorInput editorInput)
			throws PartInitException
	{
		if (!(editorInput instanceof IFileEditorInput))
			throw new PartInitException(
					"Invalid Input: Must be IFileEditorInput");
		super.init(site, editorInput);
		IFile file = ((IFileEditorInput) editorInput).getFile();
		setPartName(file.getName());
	}

	protected PaletteRoot getPaletteRoot()
	{
		if (this.paletteRoot == null)
		{
			this.paletteRoot = PaletteFactory.createPalette();
		}
		return this.paletteRoot;
	}

}