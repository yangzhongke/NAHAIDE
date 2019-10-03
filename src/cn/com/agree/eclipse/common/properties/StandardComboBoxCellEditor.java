/*******************************************************************************
 * Copyright (c) 2001, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package cn.com.agree.eclipse.common.properties;

import org.eclipse.swt.widgets.Composite;

public class StandardComboBoxCellEditor extends ObjectComboBoxCellEditor
{

	protected Object[] fItems;

	public StandardComboBoxCellEditor(Composite parent)
	{
		super(parent);
	}

	public StandardComboBoxCellEditor(Composite parent,
			String[] displayStrings, Object[] items)
	{
		super(parent, displayStrings);
		fItems = items;
	}

	public void setItems(String[] displayStrings, Object[] items)
	{
		fItems = items;
		this.setItems(displayStrings);
	}

	protected String isCorrectObject(Object value)
	{
		if (value == null || doGetIndex(value) != -1)
			return null;
		return "invalid value:" + value;
	}

	protected Object doGetObject(int index)
	{
		return (fItems != null && index >= 0 && index < fItems.length ? fItems[index]
				: null);
	}

	protected int doGetIndex(Object value)
	{
		if (fItems != null)
		{
			for (int i = 0; i < fItems.length; i++)
			{
				if (fItems[i] == null)
				{
					if (value == null)
						return i;
				} else if (fItems[i].equals(value))
					return i;
			}
		}
		return -1;
	}

}
