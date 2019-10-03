package cn.com.agree.naha.designer;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin
{

	// The plug-in ID
	public static final String PLUGIN_ID = "cn.com.agree.naha.designer";

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator()
	{
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception
	{
		super.start(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception
	{
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault()
	{
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path)
	{
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	public static Shell getShell()
	{
		if (getDefault().getWorkbench().getActiveWorkbenchWindow() == null)
		{
			return null;
		}
		return getDefault().getWorkbench().getActiveWorkbenchWindow()
				.getShell();
	}

	public static IWorkbenchPage getActivePage()
	{
		if (getDefault().getWorkbench().getActiveWorkbenchWindow() == null)
		{
			return null;
		}
		return getDefault().getWorkbench().getActiveWorkbenchWindow()
				.getActivePage();
	}

	public static void logException(Throwable t)
	{
		IStatus status = new Status(IStatus.ERROR, PLUGIN_ID, IStatus.ERROR, t
				.getMessage(), t);
		getDefault().getLog().log(status);
	}

	public static void logErrorMsg(Throwable t, String msg)
	{
		IStatus status = new Status(IStatus.ERROR, PLUGIN_ID, IStatus.ERROR,
				msg, t);
		getDefault().getLog().log(status);
	}
}
