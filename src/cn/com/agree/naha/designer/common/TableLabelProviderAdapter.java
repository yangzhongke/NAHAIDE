/*
 * cn.com.agree.naha.designer.common.TableLabelProviderAdapter.java
 * Created on 2007-7-27 …œŒÁ09:50:12 by —Ó÷–ø∆
 */

package cn.com.agree.naha.designer.common;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

abstract public class TableLabelProviderAdapter implements ITableLabelProvider
{

	public Image getColumnImage(Object element, int columnIndex)
	{
		return null;
	}

	public String getColumnText(Object element, int columnIndex)
	{
		return null;
	}

	public void addListener(ILabelProviderListener listener)
	{
		
	}

	public void dispose()
	{
		
	}

	public boolean isLabelProperty(Object element, String property)
	{
		return false;
	}

	public void removeListener(ILabelProviderListener listener)
	{
		
	}

}
