package cn.com.agree.naha.designer.components.atable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.python.pydev.parser.jython.ast.Assign;
import org.python.pydev.parser.jython.ast.Num;
import org.python.pydev.parser.jython.ast.Str;
import org.python.pydev.parser.jython.ast.Tuple;
import org.python.pydev.parser.jython.ast.exprType;
import org.python.pydev.parser.jython.ast.stmtType;

import cn.com.agree.naha.designer.common.ComponentUtils;
import cn.com.agree.naha.designer.model.BoxComponent;
import cn.com.agree.naha.designer.parser.ParserUtils;

import com.cownew.ctk.common.StringUtils;

public class ATable extends BoxComponent
{
	private static final long serialVersionUID = 1L;

	public static final String HEADER = "HEADER";

	private static IPropertyDescriptor[] descriptors = new IPropertyDescriptor[] { new TableHeaderPropertyDescriptor(
			HEADER, "表头定义") };

	private HeaderInfo[] header;

	public ATable()
	{
		super();
		header = new HeaderInfo[0];
	}

	public HeaderInfo[] getHeader()
	{
		return header;
	}

	public void setHeader(HeaderInfo[] header)
	{
		this.header = header;
		firePropertyChange(HEADER, null, this.header);
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
		if (id.equals(HEADER))
		{
			return getHeader();
		}
		return null;

	}

	public void setPropertyValue(Object id, Object value)
	{
		super.setPropertyValue(id, value);
		if (id.equals(HEADER))
		{
			setHeader((HeaderInfo[]) value);
		}
	}

	public Map<String, String> getEventDef()
	{
		Map<String, String> map = super.getEventDef();
		map.put("onitemclick", "(selectIndex)");
		map.put("onselectchange", "(prevrowindex,rowindex)");
		return map;
	}
	
	public List generateCode(String parentId)
	{
		StringBuffer line1 = new StringBuffer();
		line1.append(getId()).append("=ATable(");
		Rectangle rect = getBounds();
		line1.append(rect.x).append(",").append(rect.y).append(",").append(
				rect.height).append(",").append(rect.width).append(",");
		line1.append(infosToList(getHeader()));
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
		Num heightStmt = (Num) createArgs[2];
		Num widthStmt = (Num) createArgs[3];
		org.python.pydev.parser.jython.ast.List headerStmt = (org.python.pydev.parser.jython.ast.List) createArgs[4];
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
		setHeader(listToInfos(headerStmt));
	}

	private static String infosToList(HeaderInfo[] infos)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0, n = infos.length; i < n; i++)
		{
			HeaderInfo info = infos[i];
			sb.append("(");
			sb.append(StringUtils.doubleQuoted(info.getCaption())).append(",")
					.append(info.getWidth());
			sb.append(")");
			if (i < n - 1)
			{
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	private static HeaderInfo[] listToInfos(
			org.python.pydev.parser.jython.ast.List list)
	{
		exprType[] elts = list.elts;
		HeaderInfo[] infos = new HeaderInfo[elts.length];
		for (int i = 0, n = elts.length; i < n; i++)
		{
			Tuple itemTuple = (Tuple) elts[i];
			String caption = ((Str) itemTuple.elts[0]).s;
			String widthStr = ((Num) itemTuple.elts[1]).num;
			int width = Integer.parseInt(widthStr);
			HeaderInfo info = new HeaderInfo(caption, width);
			infos[i] = info;
		}
		return infos;
	}
}
