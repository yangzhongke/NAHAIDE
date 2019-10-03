package cn.com.agree.naha.designer.model;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import cn.com.agree.naha.designer.common.ComponentUtils;
import cn.com.agree.naha.designer.model.SingleLineComponent;

public abstract class ASingleLineTextComponent extends SingleLineComponent
{
	private static final long serialVersionUID = 1L;
	
	public static final String TEXT = "TEXT";
	
	private static IPropertyDescriptor[] descriptors = 
		new IPropertyDescriptor[] { new TextPropertyDescriptor(
				TEXT, "ÎÄ±¾") };
	
	private String text;

	public ASingleLineTextComponent()
	{
		super();
		text = this.getClass().getSimpleName();
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{		
		String oldValue = getText();
		this.text = text;
		this.firePropertyChange(TEXT,oldValue,text);
	}

	public IPropertyDescriptor[] getPropertyDescriptors()
	{
		IPropertyDescriptor[] sd = super.getPropertyDescriptors();
		return ComponentUtils.mergePropDesc(sd,descriptors);
	}

	public Object getPropertyValue(Object id)
	{
		Object v = super.getPropertyValue(id);
		if (v!=null)
		{
			return v;
		}
		if(id.equals(TEXT))
		{
			return getText();
		}
		return null;

	}
	
	public void setPropertyValue(Object id, Object value)
	{
		super.setPropertyValue(id,value);
		if(id.equals(TEXT))
		{
			setText((String) value);
		}
	}
}
