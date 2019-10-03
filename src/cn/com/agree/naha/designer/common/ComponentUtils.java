package cn.com.agree.naha.designer.common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.python.pydev.parser.jython.ast.Str;
import org.python.pydev.parser.jython.ast.exprType;

import cn.com.agree.naha.designer.Activator;

import com.cownew.ctk.common.EnvironmentUtils;
import com.cownew.ctk.io.ResourceUtils;

public class ComponentUtils
{
	public static IPropertyDescriptor[] mergePropDesc(
			IPropertyDescriptor[] desc1, IPropertyDescriptor[] desc2)
	{
		IPropertyDescriptor[] ret = new IPropertyDescriptor[desc1.length
				+ desc2.length];
		System.arraycopy(desc1, 0, ret, 0, desc1.length);
		System.arraycopy(desc2, 0, ret, desc1.length, desc2.length);
		return ret;
	}

	/**
	 * 转换为python的 bool
	 * 
	 * @param b
	 * @return
	 */
	public static String toPYBool(boolean b)
	{
		return b ? "True" : "False";
	}

	/**
	 * 将Python格式的bool转换为java boolean
	 * 
	 * @param value
	 * @return
	 */
	public static boolean fromPYBool(String value)
	{
		String lv = value.toLowerCase();
		return Boolean.valueOf(lv);
	}

	/**
	 * 将java的多行文本转换为Python的List
	 * 
	 * @param s
	 * @return
	 */
	public static String multiLineToList(String s)
	{
		String[] lines = s.split(EnvironmentUtils.getLineSeparator());
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0, n = lines.length; i < n; i++)
		{
			sb.append("\"").append(lines[i]).append("\"");
			if (i < n - 1)
			{
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 将python的list转换为多行文本
	 * @param elts
	 * @return
	 */
	public static String pyListToMultiLineString(exprType[] elts)
	{
		StringBuffer sbItems = new StringBuffer();
		for (int i = 0, n = elts.length; i < n; i++)
		{
			Str itemStr = (Str) elts[i];
			String v = itemStr.s;
			sbItems.append(v);
			if (i < n - 1)
			{
				sbItems.append(EnvironmentUtils.getLineSeparator());
			}
		}
		return sbItems.toString();
	}

	public static void saveToFile(IFile file, String value,
			IProgressMonitor monitor) throws CoreException
	{
		InputStream stream = null;
		try
		{
			stream = new ByteArrayInputStream(value.getBytes());

			if (file.exists())
			{
				file.setContents(stream, true, true, monitor);
			} else
			{
				file.create(stream, true, monitor);
			}
			stream.close();
		} catch (IOException e)
		{
			Activator.logException(e);
		} finally
		{
			ResourceUtils.close(stream);
		}
	}

	public static String parseString(InputStream in)
	{
		BufferedReader buffer = null;
		try
		{
			buffer = new BufferedReader(new InputStreamReader(in));
			String tempstr = "";
			StringBuffer sb = new StringBuffer();
			while ((tempstr = buffer.readLine()) != null)
			{
				sb.append(tempstr).append(EnvironmentUtils.getLineSeparator());
			}
			return sb.toString();
		} catch (IOException e)
		{
			Activator.logException(e);
		} finally
		{
			ResourceUtils.close(buffer);
		}
		throw new RuntimeException();
	}
}
