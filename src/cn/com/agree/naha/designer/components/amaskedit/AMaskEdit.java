package cn.com.agree.naha.designer.components.amaskedit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.python.pydev.parser.jython.ast.Assign;
import org.python.pydev.parser.jython.ast.Num;
import org.python.pydev.parser.jython.ast.Str;
import org.python.pydev.parser.jython.ast.exprType;
import org.python.pydev.parser.jython.ast.stmtType;

import cn.com.agree.naha.designer.common.ComponentUtils;
import cn.com.agree.naha.designer.components.aedit.AEdit;
import cn.com.agree.naha.designer.parser.ParserUtils;

import com.cownew.ctk.common.StringUtils;

public class AMaskEdit extends AEdit
{
	private static final long serialVersionUID = 1L;

	public static final String MASK = "MASK";

	private static IPropertyDescriptor[] descriptors = new IPropertyDescriptor[] { new TextPropertyDescriptor(
			MASK, "格式") };

	private String mask;

	public AMaskEdit()
	{
		super();
		mask = "";
	}

	public String getMask()
	{
		return mask;
	}

	public void setMask(String mask)
	{
		String oldValue = getMask();
		this.mask = mask;
		this.firePropertyChange(MASK, oldValue, mask);
	}

	public IPropertyDescriptor[] getPropertyDescriptors()
	{
		IPropertyDescriptor[] sd = super.getPropertyDescriptors();
		return ComponentUtils.mergePropDesc(sd, descriptors);
	}

	protected boolean isTextValid(String text)
	{
		//TODO:根据mask来判断是否正确
		return true;
	}

	public Object getPropertyValue(Object id)
	{
		Object v = super.getPropertyValue(id);
		if (v != null)
		{
			return v;
		}
		if (id.equals(MASK))
		{
			return getMask();
		}
		return null;

	}

	public void setPropertyValue(Object id, Object value)
	{
		super.setPropertyValue(id, value);
		if (id.equals(MASK))
		{
			setMask((String) value);
		}
	}

	public List generateCode(String parentId)
	{
		StringBuffer line1 = new StringBuffer();
		line1.append(getId()).append("=AMaskEdit(");
		Rectangle rect = getBounds();
		line1.append(rect.x).append(",").append(rect.y).append(",").append(
				rect.width).append(",").append(
				StringUtils.doubleQuoted(getMask())).append(")");

		StringBuffer line2 = new StringBuffer();
		line2.append(getId()).append(".settext(").append(
				StringUtils.doubleQuoted(getText())).append(")");

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
		Num widthStmt = (Num) createArgs[2];
		Str maskStr = (Str) createArgs[3];

		int x = Integer.parseInt(xStmt.num);
		int y = Integer.parseInt(yStmt.num);
		int width = Integer.parseInt(widthStmt.num);
		String mask = maskStr.s;
		setMask(mask);

		Rectangle bounds = new Rectangle();
		bounds.x = x;
		bounds.y = y;
		bounds.width = width;
		bounds.height = 1;
		setBounds(bounds);

		// settext语句
		String text = ParserUtils.getSetterStrValue(stmts, "settext");
		setText(text);

	}
}
