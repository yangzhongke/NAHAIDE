package cn.com.agree.naha.designer.components;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Bundle;

import cn.com.agree.naha.designer.Activator;

import com.cownew.ctk.constant.StringConst;
import com.cownew.ctk.io.ResourceUtils;

public class ComponentDefLoader
{
	private static ComponentDefLoader instance = new ComponentDefLoader();

	public static ComponentDefLoader getLoader()
	{
		return instance;
	}

	// 以model的类名为key，以ComponentInfo为value的map
	// 为了保证面板中的控件顺序按着xml中的顺序，所以用LinkedHashMap保存顺序
	private Map<String, ComponentInfo> modelInfoMap;

	// 以控件的id为key，以ComponentInfo为value的map
	private Map<String, ComponentInfo> idInfoMap;

	private ComponentDefLoader()
	{
		modelInfoMap = new LinkedHashMap<String, ComponentInfo>(20);
		idInfoMap = new HashMap<String, ComponentInfo>(20);

		InputStream stream = null;
		try
		{
			Bundle bundle = Activator.getDefault().getBundle();
			Path path = new Path("ComponentsDef.xml");
			stream = FileLocator.openStream(bundle, path, false);
			Document doc = new SAXReader().read(new InputStreamReader(stream,
					StringConst.UTF8));
			List itemList = doc.selectNodes("//Config/Item");
			for (int i = 0, n = itemList.size(); i < n; i++)
			{
				DefaultElement beanElement = (DefaultElement) itemList.get(i);
				String name = beanElement.attributeValue("name");
				String id = beanElement.attributeValue("id");
				String packageName = beanElement.attributeValue("package");
				String icon = beanElement.attributeValue("icon");

				ComponentInfo info = new ComponentInfo();
				info.setName(name);
				info.setId(id);
				info.setPackageName(packageName);
				info.setIcon(icon);
				modelInfoMap.put(info.getModelClass(), info);
				idInfoMap.put(info.getId(), info);
			}
		} catch (UnsupportedEncodingException e)
		{
			Activator.logException(e);
		} catch (DocumentException e)
		{
			Activator.logException(e);
		} catch (IOException e)
		{
			Activator.logException(e);
		} finally
		{
			ResourceUtils.close(stream);
		}
	}

	public ComponentInfo[] loadAll()
	{
		return modelInfoMap.values().toArray(
				new ComponentInfo[modelInfoMap.size()]);
	}

	public ComponentInfo loadByModel(String modelClass)
	{
		return modelInfoMap.get(modelClass);
	}

	public ComponentInfo loadByModel(Class modelClass)
	{
		return modelInfoMap.get(modelClass.getName());
	}

	public ComponentInfo loadById(String id)
	{
		return idInfoMap.get(id);
	}
}
