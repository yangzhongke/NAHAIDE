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

		// �ؼ���������������create_�ؼ�����id_�ؼ�ʵ��id
		sb.append("create_").append(componentInfo.getId()).append("_").append(
				component.getId());
		return sb.toString();
	}

	public static ComponentInfo getComponentInfoByCreateMethod(String methodName)
	{
		// �ؼ���������������create_�ؼ�����id_�ؼ�ʵ��id
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
		// �ؼ���������������create_�ؼ�����id_�ؼ�ʵ��id
		Pattern p = Pattern.compile("create_(.+)?_(.+)?$");
		Matcher m = p.matcher(methodName);
		m.matches();
		return m.group(2);

	}
}
