package cn.com.agree.eclipse.common.properties;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * 
 * <DL><DT><B>
 * 标题.
 * </B></DT><p><DD>
 * 事件描述器，双击此属性以后立即以“id+事件名”的形式填充句柄名
 * </DD></DL><p>
 * 
 * <DL><DT><B>使用范例</B></DT><p><DD>
 * 使用范例说明
 * </DD></DL><p>
 * 
 * @author 杨中科
 * @version 1.00, 2007-7-26  上午11:15:09
 *
 */
public class EventHandlerPropertyDescriptor extends PropertyDescriptor
{

	private String handlerName;

	public EventHandlerPropertyDescriptor(Object id, String displayName,
			String handlerName)
	{
		super(id, displayName);
		this.handlerName = handlerName;
	}

	public CellEditor createPropertyEditor(Composite parent)
	{
		CellEditor editor = new EventHandlerCellEditor(parent,handlerName);
		if (getValidator() != null)
		{
			editor.setValidator(getValidator());
		}
		return editor;
	}

}
