package cn.com.agree.naha.designer.model;

import org.eclipse.draw2d.geometry.Rectangle;

import cn.com.agree.naha.designer.common.MetricsUtils;

public abstract class BoxComponent extends Component
{
	private static final long serialVersionUID = 1L;
	
	public Rectangle caculateBounds(Rectangle editorRect)
	{		
		Rectangle tRect = MetricsUtils.editorBoundsToTermBounds(editorRect);
		//���ؼ����Ϸ���ȥ�����û�û���ϳ������ʱ��width��height��Ϊ��ֵ
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
