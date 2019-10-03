package cn.com.agree.naha.designer.components.acheckbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.python.pydev.parser.jython.ast.Assign;
import org.python.pydev.parser.jython.ast.Num;
import org.python.pydev.parser.jython.ast.exprType;
import org.python.pydev.parser.jython.ast.stmtType;

import cn.com.agree.eclipse.common.properties.BooleanPropertyDescriptor;
import cn.com.agree.naha.designer.common.ComponentUtils;
import cn.com.agree.naha.designer.model.ASingleLineTextComponent;
import cn.com.agree.naha.designer.parser.ParserUtils;

import com.cownew.ctk.common.StringUtils;

public class ACheckBox extends ASingleLineTextComponent
{
	private static final long serialVersionUID = 1L;

	public static final String CHECKED = "CHECKED";

	private static IPropertyDescriptor[] descriptors = new IPropertyDescriptor[] { new BooleanPropertyDescriptor(
			CHECKED, "是否选中") };

	private boolean checked;

	public ACheckBox()
	{
		super();
		checked = true;
	}

	public boolean isChecked()
	{
		return checked;
	}

	public void setChecked(boolean checked)
	{
		Boolean oldValue = Boolean.valueOf(isChecked());
		this.checked = checked;
		this.firePropertyChange(CHECKED, oldValue, Boolean.valueOf(checked));
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
		if (id.equals(CHECKED))
		{
			return Boolean.valueOf(isChecked());
		}
		return null;

	}

	public void setPropertyValue(Object id, Object value)
	{
		super.setPropertyValue(id, value);
		if (id.equals(CHECKED))
		{
			Boolean b = (Boolean) value;
			setChecked(b.booleanValue());
		}
	}

	public List generateCode(String parentId)
	{
		StringBuffer line1 = new StringBuffer();
		line1.append(getId()).append("=ACheckBox(");
		Rectangle rect = getBounds();
		line1.append(rect.x).append(",").append(rect.y).append(",").append(
				rect.width).append(")");

		StringBuffer line2 = new StringBuffer();
		line2.append(getId()).append(".settext(").append(
				StringUtils.doubleQuoted(getText())).append(")");

		StringBuffer line3 = new StringBuffer();
		line3.append(getId()).append(".setchecked(").append(
				ComponentUtils.toPYBool(isChecked())).append(")");

		StringBuffer line4 = new StringBuffer();
		line4.append(parentId).append(".add(").append(getId()).append(")");

		List<String> list = new ArrayList<String>();
		list.add("global " + getId());
		list.add(line1.toString());
		list.add(line2.toString());
		list.add(line3.toString());
		list.add(line4.toString());

		return list;
	}

	public Map<String, String> getEventDef()
	{
		Map<String, String> map = super.getEventDef();
		map.put("onclick", "()");
		return map;
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
		int x = Integer.parseInt(xStmt.num);
		int y = Integer.parseInt(yStmt.num);
		int width = Integer.parseInt(widthStmt.num);
		Rectangle bounds = new Rectangle();
		bounds.x = x;
		bounds.y = y;
		bounds.width = width;
		bounds.height = 1;
		setBounds(bounds);

		// settext语句
		String text = ParserUtils.getSetterStrValue(stmts, "settext");
		setText(text);

		// setchecked语句
		boolean checked = ParserUtils.getSetterBoolValue(stmts, "setchecked");
		setChecked(checked);

	}
}
