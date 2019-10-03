package cn.com.agree.eclipse.common.properties;

import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetSorter;

public class SeqSortPropertySheetPage extends PropertySheetPage
{

	public SeqSortPropertySheetPage()
	{
		super();
		setSorter(new SequencePropertySheetSorter());
	}

}

class SequencePropertySheetSorter extends PropertySheetSorter
{
	public int compare(IPropertySheetEntry entryA, IPropertySheetEntry entryB)
	{
		return 0;
	}

	public int compareCategories(String categoryA, String categoryB)
	{
		return 0;
	}
}
