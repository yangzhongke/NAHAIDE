package cn.com.agree.naha.designer.components.abizcombobox;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.python.pydev.parser.jython.ast.Assign;
import org.python.pydev.parser.jython.ast.Num;
import org.python.pydev.parser.jython.ast.Str;
import org.python.pydev.parser.jython.ast.Tuple;
import org.python.pydev.parser.jython.ast.exprType;
import org.python.pydev.parser.jython.ast.stmtType;

import cn.com.agree.eclipse.common.properties.IntegerPropertyDescriptor;
import cn.com.agree.naha.designer.common.ComponentUtils;
import cn.com.agree.naha.designer.model.SingleLineComponent;
import cn.com.agree.naha.designer.parser.ParserUtils;

import com.cownew.ctk.common.StringUtils;

public class ABizComboBox extends SingleLineComponent
{
	private static final long serialVersionUID = 1L;

	public static final String ITEMS = "ITEMS";

	public static final String KEYCAPTION = "KEYCAPTION";

	public static final String VALUECAPTION = "VALUECAPTION";

	public static final String KEYWIDTH = "KEYWIDTH";

	public static final String VALUEWIDTH = "VALUEWIDTH";

	private static IPropertyDescriptor[] descriptors = new IPropertyDescriptor[] {
			new BizComboItemsPropertyDescriptor(ITEMS, "数据项"),
			new TextPropertyDescriptor(KEYCAPTION, "键标题"),
			new TextPropertyDescriptor(VALUECAPTION, "值标题"),
			new IntegerPropertyDescriptor(KEYWIDTH, "键宽度"),
			new IntegerPropertyDescriptor(VALUEWIDTH, "值宽度") };

	private KeyValue[] items;

	private String keyCaption;

	private String valueCaption;

	private int keyWidth;

	private int valueWidth;

	public ABizComboBox()
	{
		super();
		items = new KeyValue[0];
		keyCaption = "";
		valueCaption = "";
		keyWidth = 5;
		valueWidth = 5;
	}

	public KeyValue[] getItems()
	{
		return items;
	}

	public void setItems(KeyValue[] items)
	{
		this.items = items;
		firePropertyChange(ITEMS, null, this.items);
	}

	public String getKeyCaption()
	{
		return keyCaption;
	}

	public void setKeyCaption(String keyCaption)
	{
		this.keyCaption = keyCaption;
		firePropertyChange(KEYCAPTION, null, keyCaption);
	}

	public String getValueCaption()
	{
		return valueCaption;
	}

	public void setValueCaption(String valueCaption)
	{
		this.valueCaption = valueCaption;
		firePropertyChange(VALUECAPTION, null, valueCaption);
	}

	public int getKeyWidth()
	{
		return keyWidth;
	}

	public void setKeyWidth(int keyWidth)
	{
		this.keyWidth = keyWidth;
		firePropertyChange(KEYWIDTH, null, keyWidth);
	}

	public int getValueWidth()
	{
		return valueWidth;
	}

	public void setValueWidth(int valueWidth)
	{
		this.valueWidth = valueWidth;
		firePropertyChange(VALUEWIDTH, null, keyWidth);
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
		} else if (id.equals(KEYCAPTION))
		{
			return getKeyCaption();
		} else if (id.equals(VALUECAPTION))
		{
			return getValueCaption();
		} else if (id.equals(KEYWIDTH))
		{
			return getKeyWidth();
		} else if (id.equals(VALUEWIDTH))
		{
			return getValueWidth();
		}
		return null;

	}

	public void setPropertyValue(Object id, Object value)
	{
		super.setPropertyValue(id, value);
		if (id.equals(ITEMS))
		{
			setItems((KeyValue[]) value);
		} else if (id.equals(KEYCAPTION))
		{
			setKeyCaption((String) value);
		} else if (id.equals(VALUECAPTION))
		{
			setValueCaption((String) value);
		} else if (id.equals(KEYWIDTH))
		{
			Integer i = (Integer) value;
			setKeyWidth(i.intValue());
		} else if (id.equals(VALUEWIDTH))
		{
			Integer i = (Integer) value;
			setValueWidth(i.intValue());
		}
	}

	public List generateCode(String parentId)
	{
		StringBuffer line1 = new StringBuffer();
		line1.append(getId()).append("=ABizComboBox(");
		Rectangle rect = getBounds();
		line1.append(rect.x).append(",").append(rect.y).append(",").append(
				rect.width).append(",");
		line1.append("[(").append(StringUtils.doubleQuoted(getKeyCaption()))
				.append(",").append(getKeyWidth()).append("),(").append(
						StringUtils.doubleQuoted(getValueCaption()))
				.append(",").append(getValueWidth()).append(")],");
		line1.append(infosToList(getItems()));
		line1.append(")");

		StringBuffer line2 = new StringBuffer();
		line2.append(parentId).append(".add(").append(getId()).append(")");

		List<String> list = new ArrayList<String>();
		list.add("global " + getId());
		list.add(line1.toString());
		list.add(line2.toString());

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
		org.python.pydev.parser.jython.ast.List headerStmt = (org.python.pydev.parser.jython.ast.List) createArgs[3];
		fillHeaderAttr(headerStmt);
		org.python.pydev.parser.jython.ast.List itemsStmt = (org.python.pydev.parser.jython.ast.List) createArgs[4];
		int x = Integer.parseInt(xStmt.num);
		int y = Integer.parseInt(yStmt.num);
		int width = Integer.parseInt(widthStmt.num);
		Rectangle bounds = new Rectangle();
		bounds.x = x;
		bounds.y = y;
		bounds.width = width;
		bounds.height = 1;
		setBounds(bounds);
		setItems(listToInfos(itemsStmt));
	}

	// 填充表头
	private void fillHeaderAttr(
			org.python.pydev.parser.jython.ast.List headerStmt)
	{
		exprType[] elts = headerStmt.elts;
		Tuple tupleKey = (Tuple) elts[0];
		Tuple tupleValue = (Tuple) elts[0];
		Str strKeyCaption = (Str) tupleKey.elts[0];
		Num numKeyWidth = (Num) tupleKey.elts[1];
		Str strValueCaption = (Str) tupleValue.elts[0];
		Num numValueWidth = (Num) tupleValue.elts[1];

		setKeyCaption(strKeyCaption.s);
		setKeyWidth(Integer.parseInt(numKeyWidth.num));
		setValueCaption(strValueCaption.s);
		setValueWidth(Integer.parseInt(numValueWidth.num));
	}

	private static String infosToList(KeyValue[] infos)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0, n = infos.length; i < n; i++)
		{
			KeyValue info = infos[i];
			sb.append("(");
			sb.append(StringUtils.doubleQuoted(info.getKey())).append(",")
					.append(StringUtils.doubleQuoted(info.getValue()));
			sb.append(")");
			if (i < n - 1)
			{
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	private static KeyValue[] listToInfos(
			org.python.pydev.parser.jython.ast.List list)
	{
		exprType[] elts = list.elts;
		KeyValue[] infos = new KeyValue[elts.length];
		for (int i = 0, n = elts.length; i < n; i++)
		{
			Tuple itemTuple = (Tuple) elts[i];
			String key = ((Str) itemTuple.elts[0]).s;
			String value = ((Str) itemTuple.elts[1]).s;
			KeyValue info = new KeyValue(key, value);
			infos[i] = info;
		}
		return infos;
	}
}
