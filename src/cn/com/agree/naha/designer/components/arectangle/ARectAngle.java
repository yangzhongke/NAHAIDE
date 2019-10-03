package cn.com.agree.naha.designer.components.arectangle;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.python.pydev.parser.jython.ast.Assign;
import org.python.pydev.parser.jython.ast.Num;
import org.python.pydev.parser.jython.ast.exprType;
import org.python.pydev.parser.jython.ast.stmtType;

import cn.com.agree.naha.designer.common.MetricsUtils;
import cn.com.agree.naha.designer.model.Component;
import cn.com.agree.naha.designer.parser.ParserUtils;

public class ARectAngle extends Component
{
	private static final long serialVersionUID = 1L;

	public Rectangle caculateBounds(Rectangle editorRect)
	{
		Rectangle tRect = MetricsUtils.editorBoundsToTermBounds(editorRect);
		// 当控件被拖放上去或者用户没有拖出方框的时候width和height会为负值
		if (tRect.width <= 0)
		{
			tRect.width = 10;
		}
		if (tRect.height <= 0)
		{
			tRect.height = 10;
		}
		return tRect;
	}
	public List generateCode(String parentId)
	{
		StringBuffer line1 = new StringBuffer();

		Rectangle rect = getBounds();
		int x = rect.x;
		int y = rect.y;
		int width = rect.width;
		int height = rect.height;

		line1.append(getId()).append("=ARectAngle(");

		line1.append(x).append(",").append(y).append(",").append(height)
				.append(",").append(width).append(")");

		StringBuffer line2 = new StringBuffer();
		line2.append(parentId).append(".add(").append(getId()).append(")");

		List<String> list = new ArrayList<String>();
		list.add("global "+getId());
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
	}
}
