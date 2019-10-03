package cn.com.agree.naha.designer.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import cn.com.agree.naha.designer.Activator;
import cn.com.agree.naha.designer.components.ComponentDefLoader;
import cn.com.agree.naha.designer.components.ComponentInfo;
import cn.com.agree.naha.designer.model.Component;
import cn.com.agree.naha.designer.model.Form;

public class PartFactory implements EditPartFactory
{

	public EditPart createEditPart(EditPart context, Object model)
	{
		EditPart part = null;

		if (model instanceof Form)
		{
			part = new FormPart();
		} else if (model instanceof Component)
		{
			ComponentDefLoader loader = ComponentDefLoader.getLoader();
			ComponentInfo info = loader.loadByModel(model.getClass().getName());
			try
			{
				Class clz = Class.forName(info.getPartClass());
				part = (EditPart) clz.newInstance();
			} catch (ClassNotFoundException e)
			{
				Activator.logException(e);
			} catch (InstantiationException e)
			{
				Activator.logException(e);
			} catch (IllegalAccessException e)
			{
				Activator.logException(e);
			}
		} else
		{
			return null;
		}
		part.setModel(model);

		return part;
	}
}