package cn.com.agree.naha.designer.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.cownew.ctk.common.EnvironmentUtils;

public class Form extends Element
{

	static final long serialVersionUID = 1;

	public static String COMPONENTS = "components";

	private List<Component> components;

	private Set<String> globalVars;

	private String customCode;

	public Form()
	{
		super();
		components = new ArrayList<Component>();

		// 使用LinkedHashSet保证取变量的顺序是确定的
		globalVars = new LinkedHashSet<String>();
		globalVars.add("trade");

		StringBuffer sb = new StringBuffer();
		String NL = EnvironmentUtils.getLineSeparator();
		sb.append("def aftermain():").append(NL);
		sb.append("    pass").append(NL);
		this.customCode = sb.toString();
	}

	public void addComponent(Component component)
	{
		components.add(component);
		fireStructureChange(COMPONENTS, components);
	}

	public void removeComponent(Component component)
	{
		components.remove(component);
		removeGlobalVar(component.getId());
		fireStructureChange(COMPONENTS, components);
	}

	public void addGlobalVar(String var)
	{
		globalVars.add(var);
	}

	public void removeGlobalVar(String var)
	{
		globalVars.remove(var);
	}

	public List<Component> getComponents()
	{
		return this.components;
	}

	public void setGlobalVars(Set<String> globalVars)
	{
		this.globalVars = globalVars;
	}

	public Set<String> getGlobalVars()
	{
		return globalVars;
	}

	public String getCustomCode()
	{
		return customCode;
	}

	public void appendCustomCode(String code)
	{
		String NL = EnvironmentUtils.getLineSeparator();
		this.customCode = this.customCode + NL + code + NL;
	}

	public void setCustomCode(String customCode)
	{
		this.customCode = customCode.trim();
	}
}