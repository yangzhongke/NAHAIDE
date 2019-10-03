package cn.com.agree.eclipse.common.properties;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * 
 * <DL><DT><B>
 * ����.
 * </B></DT><p><DD>
 * �¼���������˫���������Ժ������ԡ�id+�¼���������ʽ�������
 * </DD></DL><p>
 * 
 * <DL><DT><B>ʹ�÷���</B></DT><p><DD>
 * ʹ�÷���˵��
 * </DD></DL><p>
 * 
 * @author ���п�
 * @version 1.00, 2007-7-26  ����11:15:09
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
