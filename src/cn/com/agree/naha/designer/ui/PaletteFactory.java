package cn.com.agree.naha.designer.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.jface.resource.ImageDescriptor;

import cn.com.agree.naha.designer.Activator;
import cn.com.agree.naha.designer.components.ComponentDefLoader;
import cn.com.agree.naha.designer.components.ComponentInfo;

public class PaletteFactory
{

	private static PaletteContainer createControlGroup(PaletteRoot root)
	{
		PaletteGroup controlGroup = new PaletteGroup("Control Group");

		ToolEntry tool = new SelectionToolEntry();
		root.setDefaultEntry(tool);

		controlGroup.add(tool);
		controlGroup.add(new MarqueeToolEntry());
		return controlGroup;
	}

	private static PaletteContainer createComponentsDrawer()
	{

		PaletteDrawer drawer = new PaletteDrawer("Components", null);

		List<PaletteEntry> entries = new ArrayList<PaletteEntry>();

		ComponentInfo[] infos = ComponentDefLoader.getLoader().loadAll();

		for (int i = 0, n = infos.length; i < n; i++)
		{
			ComponentInfo info = infos[i];

			Class modelClass = null;
			try
			{
				modelClass = Class.forName(info.getModelClass());
			} catch (ClassNotFoundException e)
			{
				Activator.logException(e);
			}

			ImageDescriptor icon = Activator.getImageDescriptor(info.getIcon());

			ToolEntry tool = new CombinedTemplateCreationEntry(info.getName(),
					"", modelClass, new SimpleFactory(modelClass), icon, /*
																			 * small
																			 * icon
																			 */
					icon /* large icon */
			);
			entries.add(tool);
		}

		drawer.addAll(entries);
		return drawer;
	}

	private static List<PaletteEntry> createCategories(PaletteRoot root)
	{
		List<PaletteEntry> categories = new ArrayList<PaletteEntry>();

		categories.add(createControlGroup(root));
		categories.add(createComponentsDrawer());

		return categories;
	}

	public static PaletteRoot createPalette()
	{
		PaletteRoot paletteRoot = new PaletteRoot();
		paletteRoot.addAll(createCategories(paletteRoot));
		return paletteRoot;
	}

}