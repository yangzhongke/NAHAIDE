package cn.com.agree.naha.designer.components.aintedit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;

import com.cownew.ctk.common.NumberUtils;
import com.cownew.ctk.common.StringUtils;

import cn.com.agree.naha.designer.components.aedit.AEdit;

public class AIntegerEdit extends AEdit
{
	private static final long serialVersionUID = 1L;

	protected boolean isTextValid(String text)
	{
		boolean isInt = NumberUtils.isInteger(text);
		return super.isTextValid(text)&&isInt;
	}
	
	public List generateCode(String parentId)
	{
		StringBuffer line1 = new StringBuffer();
		line1.append(getId()).append("=AIntegerEdit(");
		Rectangle rect = getBounds();
		line1.append(rect.x).append(",").append(rect.y)
		  .append(",").append(rect.width).append(")");
		
		StringBuffer line2 = new StringBuffer();
		line2.append(getId()).append(".settext(")
			.append(StringUtils.doubleQuoted(getText())).append(")");
		
		StringBuffer line3 = new StringBuffer();
		line3.append(parentId).append(".add(")
			.append(getId()).append(")");
		
		List<String> list = new ArrayList<String>();
		list.add("global "+getId());
		list.add(line1.toString());
		list.add(line2.toString());
		list.add(line3.toString());
		
		return list;
	}
}
