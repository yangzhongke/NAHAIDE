package cn.com.agree.eclipse.common.properties;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class IntegerPropertyDescriptor extends PropertyDescriptor
{

	public IntegerPropertyDescriptor(Object id, String displayName)
	{
		super(id, displayName);
	}

	public CellEditor createPropertyEditor(Composite parent)
	{
		CellEditor editor = new IntegerCellEditor(parent);
		if (getValidator() != null)
		{
			editor.setValidator(getValidator());
		}
		return editor;
	}

}
