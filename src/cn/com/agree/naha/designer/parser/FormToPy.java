package cn.com.agree.naha.designer.parser;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.com.agree.naha.designer.components.NameStrategy;
import cn.com.agree.naha.designer.model.Component;
import cn.com.agree.naha.designer.model.Form;

import com.cownew.ctk.common.EnvironmentUtils;
import com.cownew.ctk.common.StringUtils;

public class FormToPy
{

	private static final String NL = EnvironmentUtils.getLineSeparator();

	public static String getAsCode(Form form)
	{

		List<Component> components = form.getComponents();

		StringBuffer sbCtrlCreate = new StringBuffer();

		for (Component component : components)
		{
			String createMethodName = NameStrategy
					.getCreateComponentMethodName(component);

			// 控件创建方法名
			sbCtrlCreate.append("def ").append(createMethodName).append("():")
					.append(NL);

			List list = component.generateCode("trade");
			for (int j = 0, m = list.size(); j < m; j++)
			{
				String codeLine = (String) list.get(j);
				sbCtrlCreate.append("    ").append(codeLine).append(NL);
			}
			// 生成事件定义
			Map<String, String> eventHandler = component.getEventHandler();
			Set<String> eventKeySet = eventHandler.keySet();
			for (String eventName : eventKeySet)
			{
				String methodName = eventHandler.get(eventName);
				if (!StringUtils.isEmpty(methodName))
				{
					sbCtrlCreate.append("    ").append(component.getId())
							.append(".").append(eventName).append("=").append(
									methodName).append(NL);
				}

			}
		}

		StringBuffer sbMain = new StringBuffer();
		sbMain.append("def main():").append(NL);
		sbMain.append("    global trade").append(NL);
		sbMain.append("    trade=newtrade()").append(NL);
		for (Component component : components)
		{
			String createMethodName = NameStrategy
					.getCreateComponentMethodName(component);
			sbMain.append("    ").append(createMethodName).append("()").append(
					NL);
		}
		sbMain.append("    aftermain()").append(NL);

		// 产生代码头部分
		StringBuffer sbHead = new StringBuffer();
		sbHead.append("#coding:GBK").append(NL);
		sbHead.append("from ACurses import *").append(NL);
		sbHead.append("from ACursesEx import *").append(NL);
		sbHead.append("from FrontEnd import *").append(NL);
		// 全局变量
		Iterator it = form.getGlobalVars().iterator();
		while (it.hasNext())
		{
			String var = (String) it.next();
			sbHead.append(var).append("=None").append(NL);
		}

		StringBuffer sbCode = new StringBuffer();
		sbCode.append(sbHead).append(NL);
		sbCode.append(ParserUtils.INITCODESEPRATOR).append(NL);
		sbCode.append(sbMain).append(NL);
		sbCode.append(sbCtrlCreate).append(NL);

		sbCode.append(ParserUtils.CUSTOMCODESEPRATOR).append(NL);
		sbCode.append(form.getCustomCode());

		return sbCode.toString();
	}
}
