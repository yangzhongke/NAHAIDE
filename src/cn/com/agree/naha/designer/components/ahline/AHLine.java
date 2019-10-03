package cn.com.agree.naha.designer.components.ahline;

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

public class AHLine extends Component
{
	private static final long serialVersionUID = 1L;
	
	public Rectangle caculateBounds(Rectangle editorRect)
	{		
		Rectangle tRect = MetricsUtils.editorBoundsToTermBounds(editorRect);
		//当控件被拖放上去或者用户没有拖出方框的时候width和height会为负值
		if(tRect.width<=0)
		{
			tRect.width = 10;
		}
		tRect.height = 1;
		return tRect;
	}

	public List generateCode(String parentId)
	{
		StringBuffer line1 = new StringBuffer();
		
		Rectangle rect = getBounds();
		int startx = rect.x;
		int endx = rect.x+rect.width;
		int y = rect.y;
		
		line1.append(getId()).append("=AHLine(");
		
		line1.append(startx).append(",").append(endx)
		  .append(",").append(y).append(")");
		
		StringBuffer line2 = new StringBuffer();
		line2.append(parentId).append(".add(")
			.append(getId()).append(")");
		
		List<String> list = new ArrayList<String>();
		list.add("global "+getId());
		list.add(line1.toString());
		list.add(line2.toString());
		
		return list;
	}
	
	@Override
	public void fillAttr(String id, stmtType[] stmts)
	{
		super.fillAttr(id,stmts);
		// 构造函数
		exprType[] createArgs = ParserUtils.getArgs((Assign) stmts[1]);
		Num startxStmt = (Num) createArgs[0];
		Num endxStmt = (Num) createArgs[1];
		Num yStmt = (Num) createArgs[2];
		int startx = Integer.parseInt(startxStmt.num);
		int endx = Integer.parseInt(endxStmt.num);
		int y = Integer.parseInt(yStmt.num);
		Rectangle bounds = new Rectangle();
		bounds.x = startx;
		bounds.y = y;
		bounds.width = endx-startx;
		bounds.height = 1;
		setBounds(bounds);
	}
}
