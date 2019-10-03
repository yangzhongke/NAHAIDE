package cn.com.agree.naha.designer.ui;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.requests.CreationFactory;

import cn.com.agree.naha.designer.model.ElementFactory;

public class FormTemplateTransferDropTargetListener extends
		TemplateTransferDropTargetListener
{

	public FormTemplateTransferDropTargetListener(EditPartViewer viewer)
	{
		super(viewer);
	}

	protected CreationFactory getFactory(Object template)
	{
		return new ElementFactory(template);
	}
}