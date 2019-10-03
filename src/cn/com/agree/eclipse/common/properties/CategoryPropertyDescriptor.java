package cn.com.agree.eclipse.common.properties;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

/**
 * Ϊ����IPropertyDescriptor���ӿ��Զ��Ƶ�category����ü̳���
 * @author ���п�
 *
 */
public class CategoryPropertyDescriptor implements IPropertyDescriptor
{
	private IPropertyDescriptor propdesc;
	private String category;
	public CategoryPropertyDescriptor(IPropertyDescriptor propdesc,
			String category)
	{
		super();
		this.propdesc = propdesc;	
		this.category = category;
	}
	public CellEditor createPropertyEditor(Composite parent)
	{
		return propdesc.createPropertyEditor(parent);
	}
	public String getCategory()
	{
		return category;
	}
	public String getDescription()
	{
		return propdesc.getDescription();
	}
	public String getDisplayName()
	{
		return propdesc.getDisplayName();
	}
	public String[] getFilterFlags()
	{
		return propdesc.getFilterFlags();
	}
	public Object getHelpContextIds()
	{
		return propdesc.getHelpContextIds();
	}
	public Object getId()
	{
		return propdesc.getId();
	}
	public ILabelProvider getLabelProvider()
	{
		return propdesc.getLabelProvider();
	}
	public boolean isCompatibleWith(IPropertyDescriptor anotherProperty)
	{
		return propdesc.isCompatibleWith(anotherProperty);
	}
}