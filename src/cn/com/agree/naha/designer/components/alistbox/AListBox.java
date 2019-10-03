package cn.com.agree.naha.designer.components.alistbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

public class AListBox extends BoxComponent
{
	private static final long serialVersionUID = 1L;

	public static final String ITEMS = "ITEMS";

	private static IPropertyDescriptor[] descriptors = new IPropertyDescriptor[] { new MultiLineStringPropertyDescriptor(
			ITEMS, "数据项") };

	private String items;

	public AListBox()
	{
		super();
		items = "";
	}

	public String getItems()
	{
		return items;
	}

	public void setItems(String items)
	{
		String oldValue = getItems();
		this.items = items;
		this.firePropertyChange(ITEMS, oldValue, items);
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
		if (id.equals(ITEMS))
		{
			return getItems();
		}
		return null;

	}

	public void setPropertyValue(Object id, Object value)
	{
		super.setPropertyValue(id, value);
		if (id.equals(ITEMS))
		{
			setItems((String) value);
		}
	}

	public List generateCode(String parentId)
	{
		StringBuffer line1 = new StringBuffer();
		line1.append(getId()).append("=AListBox(");
		Rectangle rect = getBounds();

		line1.append(rect.x).append(",").append(rect.y).append(",").append(
				rect.height).append(",").append(rect.width).append(")");

		String paramItems = ComponentUtils.multiLineToList(getItems());

		StringBuffer line2 = new StringBuffer();
		line2.append(parentId).append(".add(").append(getId()).append(")");

		StringBuffer line3 = new StringBuffer();
		line3.append(getId()).append(".setitems(").append(paramItems).append(
				")");

		List<String> list = new ArrayList<String>();
		list.add("global " + getId());
		list.add(line1.toString());
		list.add(line2.toString());
		list.add(line3.toString());

		return list;
	}

	public Map<String, String> getEventDef()
	{
		Map<String, String> map = super.getEventDef();
		map.put("onitemclick", "(index)");
		map.put("onselectchange", "(previndex,index)");
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
		Num heightStmt = (Num) createArgs[2];
		Num widthStmt = (Num) createArgs[3];

		int x = Integer.parseInt(xStmt.num);
		int y = Integer.parseInt(yStmt.num);
		int height = Integer.parseInt(heightStmt.num);
		int width = Integer.parseInt(widthStmt.num);

		Rectangle bounds = new Rectangle();
		bounds.x = x;
		bounds.y = y;
		bounds.width = width;
		bounds.height = height;
		setBounds(bounds);

		// setitems语句
		String sbItems = ParserUtils.getSetterListValue(stmts, "setitems");
		setItems(sbItems);
	}

}