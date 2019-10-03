package cn.com.agree.naha.designer.components;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.agree.naha.designer.model.Component;

public class NameStrategy
{
	public static String getModelClass(String packageName, String id)
	{
		return packageName + "." + id;
	}

	public static String getFigureClass(String packageName, String id)
	{
		return packageName + "." + id + "Figure";
	}

	public static String getPartClass(String packageName, String id)
	{
		return packageName + "." + id + "Part";
	}

	public static String getCreateComponentMethodName(Component component)
	{
		StringBuffer sb = new StringBuffer();
		ComponentInfo componentInfo = ComponentDefLoader.getLoader()
				.loadByModel(component.getClass());

		// 控件创建方法名等于create_控件类型id_控件实例id
		sb.append("create_").append(componentInfo.getId()).append("_").append(
				component.getId());
		return sb.toString();
	}

	public static ComponentInfo getComponentInfoByCreateMethod(String methodName)
	{
		// 控件创建方法名等于create_控件类型id_控件实例id
		Pattern p = Pattern.compile("create_(.+)?_(.+)?$");
		Matcher m = p.matcher(methodName);
		m.matches();
		String componentTypeId = m.group(1);
		ComponentInfo info = ComponentDefLoader.getLoader().loadById(
				componentTypeId);
		return info;
	}

	public static String getComponentIdByCreateMethod(String methodName)
	{
		// 控件创建方法名等于create_控件类型id_控件实例id
		Pattern p = Pattern.compile("create_(.+)?_(.+)?$");
		Matcher m = p.matcher(methodName);
		m.matches();
		return m.group(2);

	}
}
