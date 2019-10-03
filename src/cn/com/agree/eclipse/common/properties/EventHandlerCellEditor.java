package cn.com.agree.eclipse.common.properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;

public class EventHandlerCellEditor extends TextCellEditor
{
	public EventHandlerCellEditor(Composite composite, final String handlerName)
	{
		super(composite);
		text.addMouseListener(new MouseAdapter() {

			public void mouseDoubleClick(MouseEvent e)
			{
				String obj = (String) getValue();
				if (StringUtils.isEmpty(obj))
				{
					setValue(handlerName);
				}

			}

		});
	}
}
