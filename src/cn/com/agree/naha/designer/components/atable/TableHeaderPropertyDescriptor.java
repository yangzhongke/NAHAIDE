package cn.com.agree.naha.designer.components.atable;

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

import cn.com.agree.eclipse.common.properties.IntegerCellEditor;
import cn.com.agree.naha.designer.common.TableLabelProviderAdapter;

public class TableHeaderPropertyDescriptor extends PropertyDescriptor
{

	public TableHeaderPropertyDescriptor(Object id, String displayName)
	{
		super(id, displayName);
		setLabelProvider(new TableHeaderLabelProvider());
	}

	public CellEditor createPropertyEditor(Composite parent)
	{
		CellEditor editor = new TableHeaderCellEditor(parent);
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

class TableHeaderLabelProvider extends LabelProvider
{

	public String getText(Object element)
	{
		HeaderInfo[] infos = (HeaderInfo[]) element;
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0, n = infos.length; i < n; i++)
		{
			HeaderInfo info = infos[i];
			sb.append("(").append(info.getCaption()).append(",").append(
					info.getWidth()).append(")");
		}
		sb.append("]");
		return sb.toString();
	}
}

class TableHeaderCellEditor extends DialogCellEditor
{

	public TableHeaderCellEditor(Composite parent)
	{
		super(parent);
	}

	protected Object openDialogBox(Control ctrl)
	{
		HeaderInfo[] oldValue = (HeaderInfo[]) getValue();
		TableHeaderDialog dlg = new TableHeaderDialog(ctrl.getShell(), oldValue);
		if (dlg.open() == TableHeaderDialog.OK)
		{
			return dlg.getValue();
		}
		return oldValue;
	}

}

class TableHeaderDialog extends Dialog
{
	private static final String WIDTH = "Width";

	private static final String CAPTION = "Caption";

	private TableViewer viewer;

	private List<HeaderInfo> datas;

	protected TableHeaderDialog(Shell shell, HeaderInfo[] initDatas)
	{
		super(shell);
		this.datas = new ArrayList<HeaderInfo>(initDatas.length);
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
		colKey.setText("列标题");
		colKey.setWidth(200);
		TableColumn colWidth = new TableColumn(table, SWT.NULL);
		colWidth.setText("宽度");
		colWidth.setWidth(50);
		viewer.setColumnProperties(new String[] { CAPTION, WIDTH });
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new TableLabelProviderAdapter() {

			public String getColumnText(Object element, int columnIndex)
			{
				HeaderInfo info = (HeaderInfo) element;
				if (columnIndex == 0)
				{
					return info.getCaption();
				} else if (columnIndex == 1)
				{
					return Integer.toString(info.getWidth());
				}
				return "";
			}

		});
		viewer.setCellEditors(new CellEditor[] { new TextCellEditor(table),
				new IntegerCellEditor(table) });

		viewer.setInput(datas);
		viewer.setCellModifier(new ICellModifier() {

			public boolean canModify(Object element, String property)
			{
				return true;
			}

			public Object getValue(Object element, String property)
			{
				HeaderInfo info = (HeaderInfo) element;
				if (property.equals(CAPTION))
				{
					return info.getCaption();
				} else if (property.equals(WIDTH))
				{
					return Integer.toString(info.getWidth());
				}
				return null;
			}

			public void modify(Object element, String property, Object value)
			{
				TableItem item = (TableItem) element;
				HeaderInfo info = (HeaderInfo) item.getData();
				if (property.equals(CAPTION))
				{
					info.setCaption(ObjectUtils.toString(value));
				} else if (property.equals(WIDTH))
				{
					if (value == null)
					{
						// 如果输入的不是数字，则会为null
						return;
					}
					Integer i = (Integer) value;
					info.setWidth(i.intValue());

				}
				viewer.refresh();
			}

		});

		Button btnAdd = new Button(composite, SWT.PUSH);
		btnAdd.setText("添加");
		btnAdd.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e)
			{
				HeaderInfo info = new HeaderInfo("标题", 5);
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

		// 使用GridData.FILL_BOTH保证表格占据主区域
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

	public HeaderInfo[] getValue()
	{
		return datas.toArray(new HeaderInfo[datas.size()]);
	}
}