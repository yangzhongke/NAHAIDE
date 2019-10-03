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

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class StandardComboBoxPropertyDescriptor extends PropertyDescriptor
{

	private String[] displayStrings;

	private Object[] items;

	public StandardComboBoxPropertyDescriptor(Object id, String displayName,
			String[] displayStrings, Object[] items)
	{
		super(id, displayName);
		this.displayStrings = displayStrings;
		this.items = items;
	}	
	
	protected StandardComboBoxPropertyDescriptor(Object id, String displayName)
	{
		super(id, displayName);
	}
	
	

	protected void setDisplayStrings(String[] displayStrings)
	{
		this.displayStrings = displayStrings;
	}


	protected void setItems(Object[] items)
	{
		this.items = items;
	}


	public CellEditor createPropertyEditor(Composite parent)
	{
		CellEditor editor = new StandardComboBoxCellEditor(parent,
				displayStrings, items);
		if (getValidator() != null)
		{
			editor.setValidator(getValidator());
		}
		return editor;
	}

}
