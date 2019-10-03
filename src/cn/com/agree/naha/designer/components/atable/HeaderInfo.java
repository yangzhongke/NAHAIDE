/*
 * cn.com.agree.naha.designer.components.atable.HeaderInfo.java
 * Created on 2007-7-26 …œŒÁ11:51:47 by —Ó÷–ø∆
 */

package cn.com.agree.naha.designer.components.atable;

public class HeaderInfo
{
	private String caption;

	private int width;

	public HeaderInfo(String caption, int width)
	{
		super();
		this.caption = caption;
		this.width = width;
	}

	public void setCaption(String caption)
	{
		this.caption = caption;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public String getCaption()
	{
		return caption;
	}

	public int getWidth()
	{
		return width;
	}
}
