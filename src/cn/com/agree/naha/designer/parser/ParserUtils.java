package cn.com.agree.naha.designer.parser;

import org.eclipse.jface.text.Document;
import org.python.pydev.core.Tuple;
import org.python.pydev.parser.PyParser;
import org.python.pydev.parser.PyParser.ParserInfo;
import org.python.pydev.parser.jython.SimpleNode;
import org.python.pydev.parser.jython.ast.Assign;
import org.python.pydev.parser.jython.ast.Attribute;
import org.python.pydev.parser.jython.ast.Call;
import org.python.pydev.parser.jython.ast.Expr;
import org.python.pydev.parser.jython.ast.FunctionDef;
import org.python.pydev.parser.jython.ast.Module;
import org.python.pydev.parser.jython.ast.Name;
import org.python.pydev.parser.jython.ast.NameTok;
import org.python.pydev.parser.jython.ast.Num;
import org.python.pydev.parser.jython.ast.Str;
import org.python.pydev.parser.jython.ast.UnaryOp;
import org.python.pydev.parser.jython.ast.exprType;
import org.python.pydev.parser.jython.ast.stmtType;

import cn.com.agree.naha.designer.common.ComponentUtils;

public class ParserUtils
{
	public static final String INITCODESEPRATOR = "#�±ߴ������������ֹ��޸�";

	public static final String CUSTOMCODESEPRATOR = "#�±��ǿͻ���������";

	/**
	 * �õ�����code���﷨��(�Ѿ�ȥ�����ڵ㣬�˴�����ֵ�ǵ�һ���ڵ������)
	 * 
	 * @param code
	 * @return
	 */
	public static stmtType[] getStmts(String code)
	{
		Document document = new Document(code);
		ParserInfo parserInfo = new PyParser.ParserInfo(document, false, 11);
		Tuple<SimpleNode, Throwable> tuple1 = PyParser
				.reparseDocument(parserInfo);
		Tuple<SimpleNode, Throwable> tuple = tuple1;
		if (tuple.o2 != null)
		{
			throw new RuntimeException(tuple.o2);
		}
		Module module1 = (Module) tuple.o1;
		Module module = module1;
		stmtType[] stmts = module.body;
		return stmts;
	}

	/**
	 * ��һϵ��������ҳ�������Ϊname�ķ���
	 * 
	 * @param stmts
	 * @param name
	 * @return
	 */
	public static FunctionDef findFuncDef(stmtType[] stmts, String name)
	{
		for (stmtType stmt : stmts)
		{
			if (stmt instanceof FunctionDef)
			{
				FunctionDef funcDef = (FunctionDef) stmt;
				NameTok funcnameTok = (NameTok) funcDef.name;
				if (funcnameTok.id.equals(name))
				{
					return funcDef;
				}
			}
		}
		return null;

	}

	/**
	 * �õ��������Ĳ���
	 * 
	 * @param expr
	 * @return
	 */
	public static exprType[] getArgs(Expr expr)
	{
		Call settextCall = (Call) expr.value;
		return settextCall.args;
	}

	/**
	 * �õ����캯���Ĳ���
	 * 
	 * @param expr
	 * @return
	 */
	public static exprType[] getArgs(Assign expr)
	{
		Call settextCall = (Call) expr.value;
		return settextCall.args;
	}

	/**
	 * �õ�stmts�з�����ΪmethodName��Set�����Ĳ�����������Ψһһ������ExprType
	 * 
	 * @param stmts
	 * @param methodName
	 * @return
	 */
	public static exprType getSetterArgExpr(stmtType[] stmts, String methodName)
	{
		for (stmtType stmt : stmts)
		{
			if (!(stmt instanceof Expr))
			{
				continue;
			}
			Expr expr = (Expr) stmt;
			exprType value = expr.value;
			if (!(value instanceof Call))
			{
				continue;
			}
			Call call = (Call) value;

			Attribute func = (Attribute) call.func;
			NameTok nametok = (NameTok) func.attr;
			if (nametok.id.equals(methodName))
			{
				exprType[] args = getArgs(expr);
				return args[0];
			}
		}
		return null;
	}

	/**
	 * �õ�stmts�з�����ΪmethodName��Set�����Ĳ�����������Ψһһ�������ַ���ֵ
	 * 
	 * @param stmts
	 * @param methodName
	 * @return
	 */
	public static String getSetterStrValue(stmtType[] stmts, String methodName)
	{
		Str arg = (Str) getSetterArgExpr(stmts, methodName);
		if (arg == null)
		{
			return "";
		}
		return arg.s;
	}

	/**
	 * �õ�stmts�з�����ΪmethodName��Set�����Ĳ�����������Ψһһ�����Ĳ���ֵ
	 * 
	 * @param stmts
	 * @param methodName
	 * @return
	 */
	public static boolean getSetterBoolValue(stmtType[] stmts, String methodName)
	{
		Name arg = (Name) getSetterArgExpr(stmts, methodName);
		if (arg == null)
		{
			return false;
		}
		String value = arg.id;
		return ComponentUtils.fromPYBool(value);
	}

	/**
	 * �õ�stmts�з�����ΪmethodName��Set�����Ĳ�����������Ψһһ������Intֵ
	 * 
	 * @param stmts
	 * @param methodName
	 * @return
	 */
	public static int getSetterIntValue(stmtType[] stmts, String methodName)
	{
		exprType arg = getSetterArgExpr(stmts, methodName);
		Num argNum = null;
		if (arg instanceof UnaryOp)
		{
			// ���ΪUnaryOp���ͣ���˵��Ϊ����
			UnaryOp uopArg = (UnaryOp) arg;
			argNum = (Num) uopArg.operand;
			argNum.num = "-" + argNum.num;
		} else
		{
			argNum = (Num) arg;
		}
		
		if (argNum == null)
		{
			return 0;
		}
		String value = argNum.num;
		return Integer.parseInt(value);
	}

	/**
	 * �õ�stmts�з�����ΪmethodName��Set�����Ĳ�����������Ψһһ����ΪList���ͣ���Stringֵ
	 * 
	 * @param stmts
	 * @param methodName
	 * @return
	 */
	public static String getSetterListValue(stmtType[] stmts, String methodName)
	{
		org.python.pydev.parser.jython.ast.List arg = (org.python.pydev.parser.jython.ast.List) getSetterArgExpr(
				stmts, methodName);
		if (arg == null)
		{
			return "";
		}
		exprType[] elts = arg.elts;
		String sbItems = ComponentUtils.pyListToMultiLineString(elts);
		return sbItems;
	}

	/**
	 * �õ�stmts���¼������ΪeventName���¼�����������
	 * 
	 * @param stmts
	 * @param eventName
	 * @return
	 */
	public static String getEventHandlerMethodName(stmtType[] stmts,
			String eventName)
	{
		for (stmtType stmt : stmts)
		{
			if (!(stmt instanceof Assign))
			{
				continue;
			}
			Assign eventAssign = (Assign) stmt;
			exprType[] eventNameTargets = eventAssign.targets;
			exprType componentId = eventNameTargets[0];
			if (!(componentId instanceof Attribute))
			{
				continue;
			}
			Attribute eventAttr = (Attribute) componentId;
			NameTok eventNameTok = (NameTok) eventAttr.attr;
			// �¼�������
			if (eventNameTok.id.equals(eventName))
			{
				Name eventMethodName = (Name) eventAssign.value;
				// �¼�����������
				String eventMethodId = eventMethodName.id;
				return eventMethodId;
			}

		}
		return null;
	}
}
