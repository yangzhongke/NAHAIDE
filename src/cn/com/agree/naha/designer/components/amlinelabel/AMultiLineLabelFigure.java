package cn.com.agree.naha.designer.components.amlinelabel;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.SystemUtils;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;

import cn.com.agree.naha.designer.common.MetricsUtils;
import cn.com.agree.naha.designer.figures.ComponentFigure;

public class AMultiLineLabelFigure extends ComponentFigure
{
	private Label label;

	private String text;

	public AMultiLineLabelFigure()
	{
		super();
		label = new Label();
		label.setLabelAlignment(PositionConstants.LEFT);
		label.setTextAlignment(PositionConstants.TOP);
		add(label);
	}

	public void setText(String text)
	{
		this.text = text;
		this.repaint();
	}

	protected void paintFigure(Graphics gc)
	{
		super.paintFigure(gc);
		label.setBounds(getBounds());
		int cWidth = MetricsUtils.editorWidthToTermWidth(getBounds().width);
		label.setText(breakLine(text, cWidth - 2));
	}

	/**
	 * 判断一个字符串是否是破损字符，也就是含有半个汉字的破损字符串
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isbrokenstr(byte[] str)
	{
		int mchcount = 0;
		for (int i = 0, n = str.length; i < n; i++)
		{
			byte c = str[i];
			// 大于128的会溢出成负数
			if (c <= 0)
			{
				mchcount++;
			}
		}
		if ((mchcount % 2) == 0)
		{
			return false;
		} else
		{
			return true;
		}
	}

	/**
	 * 
	 * 切割字符串，将str切割为宽度为width（以字节为单位）的等宽字符串，保证中文不被切开 切割完毕按照换行符中心组合
	 * 
	 * @param str
	 * @return
	 */
	private static String breakLine(String str, int width)
	{
		if (width <= 0)
		{
			return "";
		}
		StringBuffer sb = new StringBuffer();
		byte[] leftstr = str.getBytes();
		while (leftstr.length > 0)
		{
			byte[] tmpstr = ArrayUtils.subarray(leftstr, 0, width);
			String LN = SystemUtils.LINE_SEPARATOR;
			// 如果从width处切割会造成破损字符串，则向前挪一个位置
			if (isbrokenstr(tmpstr))
			{
				byte[] ss = ArrayUtils.subarray(leftstr, 0, width - 1);
				sb.append(new String(ss)).append(LN);
				leftstr = ArrayUtils.subarray(leftstr, width - 1,
						leftstr.length);
			} else
			{
				sb.append(new String(tmpstr)).append(LN);
				leftstr = ArrayUtils.subarray(leftstr, width, leftstr.length);
			}
		}

		return sb.toString();
	}
}
