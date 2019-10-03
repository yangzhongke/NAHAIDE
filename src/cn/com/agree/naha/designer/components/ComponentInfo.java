package cn.com.agree.naha.designer.components;

public class ComponentInfo
{
	private String name;

	private String id;

	private String packageName;

	private String icon;

	public String getIcon()
	{
		return icon;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getPackageName()
	{
		return packageName;
	}

	public void setPackageName(String packageName)
	{
		this.packageName = packageName;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPartClass()
	{
		return NameStrategy.getPartClass(packageName, id);
	}

	public String getFigureClass()
	{
		return NameStrategy.getFigureClass(packageName, id);
	}

	public String getModelClass()
	{
		return NameStrategy.getModelClass(packageName, id);
	}

}
