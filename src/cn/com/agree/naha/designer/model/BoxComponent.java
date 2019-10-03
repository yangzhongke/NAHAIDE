package cn.com.agree.naha.designer.model;

import org.eclipse.draw2d.geometry.Rectangle;

import cn.com.agree.naha.designer.common.MetricsUtils;

public abstract class BoxComponent extends Component
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
		if(tRect.height<=0)
		{
			tRect.height = 2;
		}
		return tRect;
	}
}
