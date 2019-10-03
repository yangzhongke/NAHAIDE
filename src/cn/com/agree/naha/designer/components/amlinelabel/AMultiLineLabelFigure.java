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
	 * �ж�һ���ַ����Ƿ��������ַ���Ҳ���Ǻ��а�����ֵ������ַ���
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
			// ����128�Ļ�����ɸ���
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
	 * �и��ַ�������str�и�Ϊ���Ϊwidth�����ֽ�Ϊ��λ���ĵȿ��ַ�������֤���Ĳ����п� �и���ϰ��ջ��з��������
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
			// �����width���и����������ַ���������ǰŲһ��λ��
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
