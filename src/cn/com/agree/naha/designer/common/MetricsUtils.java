package cn.com.agree.naha.designer.common;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Shell;

import cn.com.agree.naha.designer.Activator;

public class MetricsUtils
{
	final public static int CHARWIDTH ;

	final public static int CHARHEIGHT;

	public static int getTermCols()
	{
		return 80;
	}

	public static int getTermLines()
	{
		return 24;
	}

	public static int getTermHeight()
	{
		return getTermLines() * CHARHEIGHT;
	}

	public static int getTermWidth()
	{
		return getTermCols() * CHARWIDTH;
	}

	public static int termWidthToEditorWidth(int width)
	{
		return width * CHARWIDTH;
	}

	public static int termHeightToEditorHeight(int height)
	{
		return height * CHARHEIGHT;
	}

	public static int termXToEditorX(int x)
	{
		return x * CHARWIDTH;
	}

	public static int termYToEditorY(int y)
	{
		return y * CHARHEIGHT;
	}

	public static Rectangle termBoundsToEditorBounds(Rectangle rect)
	{
		Rectangle tRect = new Rectangle();
		tRect.height = termHeightToEditorHeight(rect.height);
		tRect.width = termWidthToEditorWidth(rect.width);
		tRect.x = termXToEditorX(rect.x);
		tRect.y = termYToEditorY(rect.y);
		return tRect;
	}

	public static int editorWidthToTermWidth(int width)
	{
		return width / CHARWIDTH;
	}

	public static int editorHeightToTermHeight(int height)
	{
		return height / CHARHEIGHT;
	}

	public static int editorXToTermX(int x)
	{
		return x / CHARWIDTH;
	}

	public static int editorYToTermY(int y)
	{
		return y / CHARHEIGHT;
	}

	public static Rectangle editorBoundsToTermBounds(Rectangle rect)
	{
		Rectangle eRect = new Rectangle();
		int h = editorHeightToTermHeight(rect.height);
		int w = editorWidthToTermWidth(rect.width);
		int x = editorXToTermX(rect.x);
		int y = editorYToTermY(rect.y);				
		
		eRect.height = h;
		eRect.width = w;
		eRect.x = x;
		eRect.y = y;
		return eRect;
	}
	
	static
	{
		Shell shell = Activator.getShell();
		GC gc = new GC(shell);
		FontMetrics fm = gc.getFontMetrics();
		CHARWIDTH = fm.getAverageCharWidth();
		CHARHEIGHT = fm.getHeight();
	}

}
