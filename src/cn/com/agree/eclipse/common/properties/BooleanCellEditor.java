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

public class BooleanCellEditor extends StandardComboBoxCellEditor
{
	protected static final int TRUE_INDEX = 0;

	protected static final int FALSE_INDEX = 1;

	public BooleanCellEditor(Composite parent)
	{
		super(parent, new String[] { "true", "false" }, new Object[] {
				Boolean.TRUE, Boolean.FALSE });
	}

	protected String isCorrectObject(Object value)
	{
		if (value == null || value instanceof Boolean)
			return null;
		return "only boolean value is valid!";
	}

}
