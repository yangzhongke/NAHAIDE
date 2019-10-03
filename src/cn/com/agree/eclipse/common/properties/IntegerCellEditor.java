package cn.com.agree.eclipse.common.properties;

import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

public class IntegerCellEditor extends TextCellEditor
{
	public IntegerCellEditor(Composite composite)
	{
		super(composite);
		setValidator(new ICellEditorValidator() {
			public String isValid(Object object)
			{
				if (object instanceof Integer)
					return null;
				String string = (String) object;
				try
				{
					Integer.parseInt(string);
				} catch (NumberFormatException exception)
				{
					return exception.getMessage();
				}
				return null;
			}
		});
	}

	protected Object doGetValue()
	{
		Object obj = super.doGetValue();
		if(obj==null||obj.toString().trim().length()<=0)
		{
			return Integer.valueOf(0);
		}
		return Integer.valueOf(obj.toString());
	}

	protected void doSetValue(Object value)
	{
		if(value==null)
		{
			super.doSetValue(null);
		}
		super.doSetValue(value.toString());
	}
	

}
