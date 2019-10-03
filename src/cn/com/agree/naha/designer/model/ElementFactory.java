package cn.com.agree.naha.designer.model;

import org.eclipse.gef.requests.CreationFactory;

import cn.com.agree.naha.designer.Activator;

//TODO:如果不用来兼容用户自定义控件，则可以使用SimpleFactory代替
public class ElementFactory implements CreationFactory
{

	private Object template;

	public ElementFactory(Object template)
	{
		this.template = template;
	}

	public Object getNewObject()
	{
		try
		{
			return ((Class) template).newInstance();
		} catch (Exception e)
		{
			Activator.logException(e);
			return null;
		}
	}

	public Object getObjectType()
	{
		return template;
	}
}