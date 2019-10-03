package cn.com.agree.naha.designer.components.apassword;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.python.pydev.parser.jython.ast.Assign;
import org.python.pydev.parser.jython.ast.Num;
import org.python.pydev.parser.jython.ast.exprType;
import org.python.pydev.parser.jython.ast.stmtType;

import cn.com.agree.naha.designer.model.SingleLineComponent;
import cn.com.agree.naha.designer.parser.ParserUtils;

public class APasswordField extends SingleLineComponent
{
	private static final long serialVersionUID = 1L;

	public List generateCode(String parentId)
	{
		StringBuffer line1 = new StringBuffer();
		line1.append(getId()).append("=APasswordField(");
		Rectangle rect = getBounds();
		line1.append(rect.x).append(",").append(rect.y).append(",").append(
				rect.width).append(")");

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
		// ¹¹Ôìº¯Êý
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
	}
}
