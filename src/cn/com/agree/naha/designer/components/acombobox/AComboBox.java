package cn.com.agree.naha.designer.components.acombobox;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.python.pydev.parser.jython.ast.Assign;
import org.python.pydev.parser.jython.ast.Num;
import org.python.pydev.parser.jython.ast.exprType;
import org.python.pydev.parser.jython.ast.stmtType;

import cn.com.agree.eclipse.common.properties.IntegerPropertyDescriptor;
import cn.com.agree.eclipse.common.properties.MultiLineStringPropertyDescriptor;
import cn.com.agree.naha.designer.common.ComponentUtils;
import cn.com.agree.naha.designer.model.SingleLineComponent;
import cn.com.agree.naha.designer.parser.ParserUtils;

import com.cownew.ctk.common.EnvironmentUtils;

public class AComboBox extends SingleLineComponent
{
	private static final long serialVersionUID = 1L;

	public static final String ITEMS = "ITEMS";

	public static final String SELECTEDINDEX = "SELECTEDINDEX";

	public static final String SHOWCOUNT = "SHOWCOUNT";

	private static IPropertyDescriptor[] descriptors = new IPropertyDescriptor[] {
			new MultiLineStringPropertyDescriptor(ITEMS, "数据项"),
			new IntegerPropertyDescriptor(SELECTEDINDEX, "被选中项索引"),
			new IntegerPropertyDescriptor(SHOWCOUNT, "显示的数目") };

	private String items;

	private int selectedIndex;

	private int showcount;

	public AComboBox()
	{
		super();
		items = "";
		selectedIndex = -1;
		showcount = 3;
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

	public int getShowcount()
	{
		return showcount;
	}

	public void setShowcount(int showcount)
	{
		Integer oldValue = new Integer(getShowcount());
		this.showcount = showcount;
		this.firePropertyChange(SHOWCOUNT, oldValue, new Integer(showcount));
	}

	public int getSelectedIndex()
	{
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex)
	{
		String[] itemArray = items.split(EnvironmentUtils.getLineSeparator());
		// 允许为负值，表示不选择
		if (selectedIndex >= itemArray.length)
		{
			return;
		}
		Integer oldValue = new Integer(getSelectedIndex());
		this.selectedIndex = selectedIndex;
		this.firePropertyChange(SELECTEDINDEX, oldValue, new Integer(
				selectedIndex));
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
		if (id.equals(SELECTEDINDEX))
		{
			return new Integer(getSelectedIndex());
		}
		if (id.equals(SHOWCOUNT))
		{
			return new Integer(getShowcount());
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
		if (id.equals(SELECTEDINDEX))
		{
			Integer i = (Integer) value;
			setSelectedIndex(i.intValue());
		}
		if (id.equals(SHOWCOUNT))
		{
			Integer i = (Integer) value;
			setShowcount(i.intValue());
		}
	}

	public List generateCode(String parentId)
	{
		StringBuffer line1 = new StringBuffer();
		line1.append(getId()).append("=AComboBox(");
		Rectangle rect = getBounds();
		line1.append(rect.x).append(",").append(rect.y).append(",").append(
				rect.width).append(")");

		String paramItems = ComponentUtils.multiLineToList(getItems());
		StringBuffer line2 = new StringBuffer();
		line2.append(getId()).append(".setitems(").append(paramItems).append(
				")");

		StringBuffer line3 = new StringBuffer();
		line3.append(getId()).append(".setshowcount(").append(getShowcount())
				.append(")");
		
		StringBuffer line4 = new StringBuffer();
		line4.append(getId()).append(".setselectedindex(").append(getSelectedIndex())
				.append(")");

		StringBuffer line5 = new StringBuffer();
		line5.append(parentId).append(".add(").append(getId()).append(")");

		List<String> list = new ArrayList<String>();
		list.add("global " + getId());
		list.add(line1.toString());
		list.add(line2.toString());
		list.add(line3.toString());
		list.add(line4.toString());
		list.add(line5.toString());

		return list;
	}

	@Override
	public void fillAttr(String id, stmtType[] stmts)
	{
		super.fillAttr(id,stmts);
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

		// setitems语句
		String sbItems = ParserUtils.getSetterListValue(stmts, "setitems");
		setItems(sbItems);

		// setshowcount语句
		int showcount = ParserUtils.getSetterIntValue(stmts, "setshowcount");
		setShowcount(showcount);
		
//		//setselectedindex语句
		int si = ParserUtils.getSetterIntValue(stmts, "setselectedindex");
		setSelectedIndex(si);
	}

}
