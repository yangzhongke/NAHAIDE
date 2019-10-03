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

import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.widgets.Composite;

public abstract class ObjectComboBoxCellEditor extends ComboBoxCellEditor
{
	protected static final int NO_SELECTION = -1;

	private static final String[] EMPTY_ITEMS = new String[0];

	private int startingIndex = -1;

	private Object startingValue;

	public ObjectComboBoxCellEditor()
	{
		/* empty */
	}

	public ObjectComboBoxCellEditor(Composite parent, String[] items, int style)
	{
		super(parent, items != null ? items : EMPTY_ITEMS, style);
	}

	public ObjectComboBoxCellEditor(Composite parent)
	{
		this(parent, null, 8);
	}

	public ObjectComboBoxCellEditor(Composite parent, String[] items)
	{
		this(parent, items, 8);
	}

	protected final boolean isCorrect(Object value)
	{
		String eMsg = isCorrectObject(value);
		if (eMsg == null || eMsg.length() == 0)
			return super.isCorrect(value);
		setErrorMessage(eMsg);
		return false;
	}

	protected abstract String isCorrectObject(Object object);

	protected abstract Object doGetObject(int i);

	protected Object doGetValue()
	{
		int selectedIndex = ((Integer) super.doGetValue()).intValue();
		if (selectedIndex == startingIndex)
			return startingValue;
		startingIndex = selectedIndex;
		startingValue = doGetObject(selectedIndex);
		return startingValue;
	}

	protected abstract int doGetIndex(Object object);

	protected final void doSetEditorSelection(int selection)
	{
		super.doSetValue(new Integer(selection));
	}

	protected int getSelectionIndex()
	{
		return ((Integer) super.doGetValue()).intValue();
	}

	protected void doSetObject(Object value)
	{
		/* empty */
	}

	protected final void doSetValue(Object value)
	{
		doSetObject(value);
		startingIndex = doGetIndex(value);
		startingValue = value;
		doSetEditorSelection(startingIndex);
	}

}
