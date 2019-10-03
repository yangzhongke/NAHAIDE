package cn.com.agree.naha.designer.parser;

import org.python.pydev.parser.jython.ast.Assign;
import org.python.pydev.parser.jython.ast.FunctionDef;
import org.python.pydev.parser.jython.ast.Name;
import org.python.pydev.parser.jython.ast.NameTok;
import org.python.pydev.parser.jython.ast.exprType;
import org.python.pydev.parser.jython.ast.stmtType;

import cn.com.agree.naha.designer.Activator;
import cn.com.agree.naha.designer.components.ComponentInfo;
import cn.com.agree.naha.designer.components.NameStrategy;
import cn.com.agree.naha.designer.model.Component;
import cn.com.agree.naha.designer.model.Form;

public class PyToForm
{
	public static Form getAsForm(String string) throws Exception
	{
		Form form = new Form();
		String[] codeSegs = string.split(ParserUtils.CUSTOMCODESEPRATOR + "|"
				+ ParserUtils.INITCODESEPRATOR);

		String headCode = codeSegs[0];
		String initCode = codeSegs[1];
		String custCode = codeSegs[2];

		parseHeadCode(form, headCode);
		parseInitCode(form, initCode);

		form.setCustomCode(custCode);

		return form;
	}

	private static void parseHeadCode(Form form, String headCode)
	{
		stmtType[] stmts = ParserUtils.getStmts(headCode);
		for (stmtType stmt : stmts)
		{
			if (stmt instanceof Assign)
			{
				Assign assign = (Assign) stmt;
				exprType[] targets = assign.targets;
				Name varName = (Name) targets[0];
				// exprType value = assign.value;
				form.addGlobalVar(varName.id);
			}
		}
	}

	private static void parseInitCode(Form form, String initCode)
	{
		stmtType[] stmts = ParserUtils.getStmts(initCode);
		for (stmtType stmt : stmts)
		{
			FunctionDef functionDef = (FunctionDef) stmt;
			NameTok nameTok = (NameTok) functionDef.name;
			String methodName = nameTok.id;
			if (methodName.equals("main"))
			{
				continue;
			}
			if (!methodName.startsWith("create_"))
			{
				// 创建控件方法必须以create_开头
				continue;
			}
			ComponentInfo componentInfo = NameStrategy
					.getComponentInfoByCreateMethod(methodName);
			String componentId = NameStrategy
					.getComponentIdByCreateMethod(methodName);
			String modelClass = componentInfo.getModelClass();
			try
			{
				Component component = (Component) Class.forName(modelClass)
						.newInstance();
				component.setForm(form);
				component.setId(componentId);

				component.fillAttr(componentId, functionDef.body);

				form.addComponent(component);
			} catch (Exception e)
			{
				// 捕捉所有异常，这样一个控件的异常不会扩散到整个界面
				Activator.logException(e);
			}
		}

	}
}
