package cn.com.agree.eclipse.common.properties;

import org.eclipse.jface.util.Assert;

public class EnumPropertyDescriptor extends StandardComboBoxPropertyDescriptor
{

	public EnumPropertyDescriptor(Object id, String displayName, Class enumClass)
	{
		super(id, displayName);
		Assert.isTrue(enumClass.isEnum());
		Object[] items = enumClass.getEnumConstants();
		String[] displayStrings = new String[items.length];
		for(int i=0,n=items.length;i<n;i++)
		{
			displayStrings[i] = items[i].toString();
		}
		setDisplayStrings(displayStrings);
		setItems(items);
	}
	

}
