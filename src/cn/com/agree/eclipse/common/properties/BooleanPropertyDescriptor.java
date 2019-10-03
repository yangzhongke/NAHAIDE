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
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class BooleanPropertyDescriptor extends PropertyDescriptor
{

	public BooleanPropertyDescriptor(Object propertyID,
			String propertyDisplayname)
	{
		super(propertyID, propertyDisplayname);
	}

	public CellEditor createPropertyEditor(Composite parent)
	{
		CellEditor editor = new BooleanCellEditor(parent);
		ICellEditorValidator v = getValidator();
		if (v != null)
			editor.setValidator(v);
		return editor;
	}
}
