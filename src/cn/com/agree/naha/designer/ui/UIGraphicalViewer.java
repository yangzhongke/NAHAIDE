package cn.com.agree.naha.designer.ui;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.ui.parts.GraphicalViewerImpl;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import cn.com.agree.naha.designer.common.MetricsUtils;

public class UIGraphicalViewer extends GraphicalViewerImpl
{
	private IFigure figure;

	public final Control createControl(Composite parent)
	{
		FigureCanvas canvas = new FigureCanvas(parent, getLightweightSystem());
		canvas.setVerticalScrollBarVisibility(FigureCanvas.NEVER);
		canvas.setHorizontalScrollBarVisibility(FigureCanvas.NEVER);
		canvas.setSize(MetricsUtils.getTermWidth(), MetricsUtils
				.getTermHeight());

		super.setControl(canvas);
		installRootFigure();
		return canvas;
	}

	protected FigureCanvas getFigureCanvas()
	{
		return (FigureCanvas) getControl();
	}

	protected void installRootFigure()
	{
		if (getFigureCanvas() != null)
		{
			getFigureCanvas().setContents(figure);
		}

	}

	protected void setRootFigure(IFigure figure)
	{
		this.figure = figure;
		installRootFigure();
	}
}
