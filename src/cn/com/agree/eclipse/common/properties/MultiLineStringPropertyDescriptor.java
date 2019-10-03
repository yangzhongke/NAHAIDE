package cn.com.agree.eclipse.common.properties;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.cownew.ctk.common.EnvironmentUtils;

public class MultiLineStringPropertyDescriptor extends PropertyDescriptor
{

	public MultiLineStringPropertyDescriptor(Object id, String displayName)
	{
		super(id, displayName);
		setLabelProvider(new MultiLineStringLabelProvider());
	}

	public CellEditor createPropertyEditor(Composite parent)
	{
		CellEditor editor = new MultiLineStringCellEditor(parent);
		if (getValidator() != null)
		{
			editor.setValidator(getValidator());
		}
		return editor;
	}

	protected ICellEditorValidator getValidator()
	{
		return new ICellEditorValidator() {

			public String isValid(Object value)
			{
				if (value == null)
				{
					return "Cannot be null";
				}
				return null;
			}

		};
	}

}

class MultiLineStringLabelProvider extends LabelProvider
{

	public String getText(Object element)
	{
		if (!(element instanceof String))
		{
			return null;
		}
		//各项在显示的时候以“，”分割
		String str = (String) element;
		String NL = EnvironmentUtils.getLineSeparator();
		return str.replace(NL, ",");
	}

}

class MultiLineStringCellEditor extends DialogCellEditor
{

	public MultiLineStringCellEditor(Composite parent)
	{
		super(parent);
	}

	protected Object openDialogBox(Control ctrl)
	{
		String oldValue = (String) getValue();
		if (oldValue == null)
		{
			oldValue = "";
		}
		MultiLineStringDialog dlg = new MultiLineStringDialog(ctrl.getShell(),
				oldValue);
		if (dlg.open() == MultiLineStringDialog.OK)
		{
			return dlg.getValue();
		}
		return oldValue;
	}

}

class MultiLineStringDialog extends Dialog
{
	private Text text;

	private String value;

	private String initValue;

	protected MultiLineStringDialog(Shell shell, String initValue)
	{
		super(shell);
		if (initValue == null)
		{
			this.initValue = "";
		} else
		{
			this.initValue = initValue;
		}

	}

	protected Control createDialogArea(Composite parent)
	{
		Composite composite = (Composite) super.createDialogArea(parent);

		FillLayout layout = new FillLayout();
		composite.setLayout(layout);
		text = new Text(composite, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		text.setText(initValue);

		return composite;
	}

	protected Point getInitialSize()
	{
		return new Point(300, 200);
	}

	public boolean close()
	{
		value = text.getText();
		return super.close();
	}

	public String getValue()
	{
		return value;
	}

}