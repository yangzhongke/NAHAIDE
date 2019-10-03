package cn.com.agree.naha.designer.components.aradiobtn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.python.pydev.parser.jython.ast.Assign;
import org.python.pydev.parser.jython.ast.Global;
import org.python.pydev.parser.jython.ast.NameTok;
import org.python.pydev.parser.jython.ast.Num;
import org.python.pydev.parser.jython.ast.exprType;
import org.python.pydev.parser.jython.ast.stmtType;

import cn.com.agree.eclipse.common.properties.BooleanPropertyDescriptor;
import cn.com.agree.naha.designer.common.ComponentUtils;
import cn.com.agree.naha.designer.model.ASingleLineTextComponent;
import cn.com.agree.naha.designer.parser.ParserUtils;

import com.cownew.ctk.common.StringUtils;

public class ARadioButton extends ASingleLineTextComponent
{
	private static final long serialVersionUID = 1L;

	public static final String CHECKED = "CHECKED";

	public static final String GROUPID = "GROUPID";

	private static IPropertyDescriptor[] descriptors = new IPropertyDescriptor[] {
			new BooleanPropertyDescriptor(CHECKED, "是否选中"),
			new TextPropertyDescriptor(GROUPID, "所属组") };

	private boolean checked;

	private String groupId;

	public ARadioButton()
	{
		super();
		setChecked(true);
		groupId = "btngroup";
	}

	public boolean isChecked()
	{
		return checked;
	}

	public void setChecked(boolean checked)
	{

		Boolean oldValue = Boolean.valueOf(isChecked());
		Boolean b = Boolean.valueOf(checked);

		this.checked = checked;

		this.firePropertyChange(CHECKED, oldValue, b);
	}

	public String getGroupId()
	{
		return groupId;
	}

	public void setGroupId(String groupId)
	{
		String oldValue = getGroupId();
		getForm().removeGlobalVar(oldValue);
		this.groupId = groupId;
		getForm().addGlobalVar(groupId);
		this.firePropertyChange(GROUPID, oldValue, groupId);
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
		if (id.equals(GROUPID))
		{
			return groupId;
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
		if (id.equals(GROUPID))
		{
			setGroupId((String) value);
		}
	}

	public List generateCode(String parentId)
	{
		List<String> list = new ArrayList<String>();

		list.add("global " + getGroupId() + "," + getId());
		StringBuffer lineGroup1 = new StringBuffer();
		lineGroup1.append("if(").append(getGroupId()).append("==None):");
		list.add(lineGroup1.toString());

		StringBuffer lineGroup2 = new StringBuffer();
		lineGroup2.append("    ").append(getGroupId()).append("=ARadioGroup()");
		list.add(lineGroup2.toString());

		StringBuffer line1 = new StringBuffer();
		line1.append(getId()).append("=ARadioButton(");
		Rectangle rect = getBounds();
		line1.append(rect.x).append(",").append(rect.y).append(",").append(
				rect.width).append(")");

		StringBuffer line2 = new StringBuffer();
		String t = StringUtils.doubleQuoted(getText());
		line2.append(getId()).append(".settext(").append(t).append(")");

		StringBuffer line3 = new StringBuffer();
		line3.append(parentId).append(".add(").append(getId()).append(")");
		StringBuffer line4 = new StringBuffer();
		line4.append(getGroupId()).append(".add(").append(getId()).append(")");

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

		// global定义的第一个变量就是groupid
		Global global = (Global) stmts[0];
		NameTok nametok = (NameTok) global.names[0];
		setGroupId(nametok.id);

		// 构造函数
		exprType[] createArgs = ParserUtils.getArgs((Assign) stmts[2]);
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

	}
}
