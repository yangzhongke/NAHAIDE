package cn.com.agree.naha.designer.components.amlinelabel;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.python.pydev.parser.jython.ast.Assign;
import org.python.pydev.parser.jython.ast.Num;
import org.python.pydev.parser.jython.ast.exprType;
import org.python.pydev.parser.jython.ast.stmtType;

import cn.com.agree.eclipse.common.properties.MultiLineStringPropertyDescriptor;
import cn.com.agree.naha.designer.common.ComponentUtils;
import cn.com.agree.naha.designer.model.BoxComponent;
import cn.com.agree.naha.designer.parser.ParserUtils;

public class AMultiLineLabel extends BoxComponent
{
	private static final long serialVersionUID = 1L;

	public static final String TEXT = "TEXT";

	private static IPropertyDescriptor[] descriptors = new IPropertyDescriptor[] { new MultiLineStringPropertyDescriptor(
			TEXT, "文本") };

	private String text;

	public AMultiLineLabel()
	{
		super();
		text = this.getClass().getSimpleName();
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		String oldValue = getText();
		this.text = text;
		this.firePropertyChange(TEXT, oldValue, text);
	}

	public IPropertyDescriptor[] getPropertyDescriptors()
	{
		IPropertyDescriptor[] sd = super.getPropertyDescriptors();
		return ComponentUtils.mergePropDesc(sd, descriptors);
	}

	public Object getPropertyValue(Object id)
	{
		Object v = super.getPropertyValue(id);
		if (v != null)
		{
			return v;
		}
		if (id.equals(TEXT))
		{
			return getText();
		}
		return null;

	}

	public void setPropertyValue(Object id, Object value)
	{
		super.setPropertyValue(id, value);
		if (id.equals(TEXT))
		{
			setText((String) value);
		}
	}

	public List generateCode(String parentId)
	{
		StringBuffer line1 = new StringBuffer();
		line1.append(getId()).append("=AMultiLineLabel(");
		Rectangle rect = getBounds();
		line1.append(rect.x).append(",").append(rect.y).append(",").append(
				rect.height).append(",").append(rect.width).append(")");

		StringBuffer line2 = new StringBuffer();
		//因为有可能折行，所以用'''  '''来包括text，否则生成的代码会有错
		line2.append(getId()).append(".settext('''").append(getText()).append(
				"''')");

		StringBuffer line3 = new StringBuffer();
		line3.append(parentId).append(".add(").append(getId()).append(")");

		List<String> list = new ArrayList<String>();
		list.add("global " + getId());
		list.add(line1.toString());
		list.add(line2.toString());
		list.add(line3.toString());

		return list;
	}

	@Override
	public void fillAttr(String id, stmtType[] stmts)
	{
		super.fillAttr(id, stmts);
		// 构造函数
		exprType[] createArgs = ParserUtils.getArgs((Assign) stmts[1]);
		Num xStmt = (Num) createArgs[0];
		Num yStmt = (Num) createArgs[1];
		Num heightStmt = (Num) createArgs[2];
		Num widthStmt = (Num) createArgs[3];
		int x = Integer.parseInt(xStmt.num);
		int y = Integer.parseInt(yStmt.num);
		int width = Integer.parseInt(widthStmt.num);
		int height = Integer.parseInt(heightStmt.num);
		Rectangle bounds = new Rectangle();
		bounds.x = x;
		bounds.y = y;
		bounds.width = width;
		bounds.height = height;
		setBounds(bounds);

		// settext语句
		String text = ParserUtils.getSetterStrValue(stmts, "settext");
		setText(text);
	}
}
