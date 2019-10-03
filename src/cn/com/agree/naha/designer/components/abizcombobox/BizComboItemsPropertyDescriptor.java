package cn.com.agree.naha.designer.components.abizcombobox;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import cn.com.agree.naha.designer.common.TableLabelProviderAdapter;

public class BizComboItemsPropertyDescriptor extends PropertyDescriptor
{

	public BizComboItemsPropertyDescriptor(Object id, String displayName)
	{
		super(id, displayName);
		setLabelProvider(new BizComboItemsLabelProvider());
	}

	public CellEditor createPropertyEditor(Composite parent)
	{
		CellEditor editor = new BizComboItemsCellEditor(parent);
		if (getValidator() != null)
		{
			editor.setValidator(getValidator());
		}
		return editor;
	}

	protected ICellEditorValidator getValidator()
	{
		return new ICellEditorValidator() {

			public String isValid(Object value)
			{
				if (value == null)
				{
					return "Cannot be null";
				}
				return null;
			}

		};
	}

}

class BizComboItemsLabelProvider extends LabelProvider
{

	public String getText(Object element)
	{
		KeyValue[] infos = (KeyValue[]) element;
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0, n = infos.length; i < n; i++)
		{
			KeyValue info = infos[i];
			sb.append("(").append(info.getKey()).append(",").append(
					info.getValue()).append(")");
		}
		sb.append("]");
		return sb.toString();
	}
}

class BizComboItemsCellEditor extends DialogCellEditor
{

	public BizComboItemsCellEditor(Composite parent)
	{
		super(parent);
	}

	protected Object openDialogBox(Control ctrl)
	{
		KeyValue[] oldValue = (KeyValue[]) getValue();
		BizComboItemsDialog dlg = new BizComboItemsDialog(ctrl.getShell(),
				oldValue);
		if (dlg.open() == BizComboItemsDialog.OK)
		{
			return dlg.getValue();
		}
		return oldValue;
	}

}

class BizComboItemsDialog extends Dialog
{
	private static final String KEY = "Key";

	private static final String VALUE = "Value";

	private TableViewer viewer;

	private List<KeyValue> datas;

	protected BizComboItemsDialog(Shell shell, KeyValue[] initDatas)
	{
		super(shell);
		this.datas = new ArrayList<KeyValue>(initDatas.length);
		for (int i = 0, n = initDatas.length; i < n; i++)
		{
			this.datas.add(initDatas[i]);
		}
	}

	protected Control createDialogArea(Composite parent)
	{
		Composite composite = (Composite) super.createDialogArea(parent);

		viewer = new TableViewer(composite, SWT.MULTI | SWT.BORDER
				| SWT.FULL_SELECTION);
		Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		TableColumn colKey = new TableColumn(table, SWT.NULL);
		colKey.setText("键");
		colKey.setWidth(200);
		TableColumn colValue = new TableColumn(table, SWT.NULL);
		colValue.setText("值");
		colValue.setWidth(50);
		viewer.setColumnProperties(new String[] { VALUE, KEY });
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new TableLabelProviderAdapter() {

			public String getColumnText(Object element, int columnIndex)
			{
				KeyValue info = (KeyValue) element;
				if (columnIndex == 0)
				{
					return info.getKey();
				} else if (columnIndex == 1)
				{
					return info.getValue();
				}
				return "";
			}

		});
		viewer.setCellEditors(new CellEditor[] { new TextCellEditor(table),
				new TextCellEditor(table) });

		viewer.setInput(datas);
		viewer.setCellModifier(new ICellModifier() {

			public boolean canModify(Object element, String property)
			{
				return true;
			}

			public Object getValue(Object element, String property)
			{
				KeyValue info = (KeyValue) element;
				if (property.equals(VALUE))
				{
					return info.getKey();
				} else if (property.equals(KEY))
				{
					return info.getValue();
				}
				return null;
			}

			public void modify(Object element, String property, Object value)
			{
				TableItem item = (TableItem) element;
				KeyValue info = (KeyValue) item.getData();
				if (property.equals(VALUE))
				{
					info.setKey(ObjectUtils.toString(value));
				} else if (property.equals(KEY))
				{
					info.setValue((String) value);
				}
				viewer.refresh();
			}

		});

		Button btnAdd = new Button(composite, SWT.PUSH);
		btnAdd.setText("添加");
		btnAdd.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e)
			{
				KeyValue info = new KeyValue("", "");
				datas.add(info);
				viewer.setInput(datas);
			}

		});

		Button btnRemove = new Button(composite, SWT.PUSH);
		btnRemove.setText("删除");
		btnRemove.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e)
			{
				IStructuredSelection selection = (IStructuredSelection) viewer
						.getSelection();
				Object element = selection.getFirstElement();
				datas.remove(element);
				viewer.setInput(datas);
			}
		});

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);

		//使用GridData.FILL_BOTH保证表格占据主区域
		GridData gdTable = new GridData(GridData.FILL_BOTH);
		gdTable.horizontalSpan = 2;
		table.setLayoutData(gdTable);

		GridData gdAdd = new GridData();
		btnAdd.setLayoutData(gdAdd);

		GridData gdRemove = new GridData();
		btnRemove.setLayoutData(gdRemove);

		return composite;
	}

	protected Point getInitialSize()
	{
		return new Point(300, 200);
	}

	public KeyValue[] getValue()
	{
		return datas.toArray(new KeyValue[datas.size()]);
	}
}