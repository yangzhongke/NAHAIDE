package cn.com.agree.naha.designer.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.python.pydev.parser.jython.ast.stmtType;

import com.cownew.ctk.common.EnvironmentUtils;
import com.cownew.ctk.common.StringUtils;

import cn.com.agree.eclipse.common.properties.CategoryPropertyDescriptor;
import cn.com.agree.eclipse.common.properties.EventHandlerPropertyDescriptor;
import cn.com.agree.naha.designer.common.ComponentUtils;
import cn.com.agree.naha.designer.components.ComponentDefLoader;
import cn.com.agree.naha.designer.components.ComponentInfo;
import cn.com.agree.naha.designer.parser.ParserUtils;
import cn.com.agree.naha.designer.policies.ComponentIdManager;

abstract public class Component extends Element implements IPropertySource
{

	static final long serialVersionUID = 4;

	public static final String BOUNDS = "BOUNDS";

	public static final String TYPE = "控件类型";

	public static final String ID = "ID";

	private Rectangle bounds;

	private String id = "component";

	private Form form;

	private Map<String, String> eventHandlerMap;

	public Component()
	{
		super();
		eventHandlerMap = new HashMap<String, String>();
	}

	/**
	 * 事件处理定义，key为事件的名称，value为对应的事件处理方法名（不包含方法签名）
	 * 
	 * @return
	 */
	final public Map<String, String> getEventHandler()
	{
		return eventHandlerMap;
	}

	public Form getForm()
	{
		return form;
	}

	public void setForm(Form form)
	{
		this.form = form;
	}

	public Rectangle getBounds()
	{
		return bounds;
	}

	public void setBounds(Rectangle bounds)
	{
		this.bounds = bounds;
		firePropertyChange(BOUNDS, null, bounds);
	}

	public void setId(String id)
	{
		if (this.id.equals(id))
		{
			return;
		}
		if (getForm() == null)
		{
			throw new IllegalArgumentException(
					"在设定id之前请首先设定组件的父Form，因为要校验id是否重复");
		}

		ComponentIdManager idMgr = new ComponentIdManager(getForm());
		// 防止id重名
		if (idMgr.isIdConflicted(id, this))
		{
			return;
		}

		// 除去旧名
		getForm().removeGlobalVar(this.id);
		// 添加新名
		getForm().addGlobalVar(id);

		this.id = id;
		firePropertyChange(ID, null, id);
	}

	public String getId()
	{
		return id;
	}

	public Object getEditableValue()
	{
		return this;
	}

	public IPropertyDescriptor[] getPropertyDescriptors()
	{
		TextPropertyDescriptor idDesc = new TextPropertyDescriptor(ID, "Id");
		idDesc.setValidator(new ICellEditorValidator() {

			public String isValid(Object value)
			{
				String id = (String) value;
				ComponentIdManager idMgr = new ComponentIdManager(getForm());
				// 防止id重名
				if (idMgr.isIdConflicted(id, Component.this))
				{
					return "控件Id：" + id + "已经存在";
				}
				return null;
			}

		});
		PropertyDescriptor typeDesc = new PropertyDescriptor(TYPE, "控件类型");
		IPropertyDescriptor[] descriptors = new IPropertyDescriptor[] { idDesc,
				typeDesc };

		// 事件属性
		Map<String, String> eventDef = this.getEventDef();
		IPropertyDescriptor[] eventPropDesc = new IPropertyDescriptor[eventDef
				.size()];
		Set<String> eventDefKeySet = eventDef.keySet();
		int i = 0;
		for (String eventName : eventDefKeySet)
		{
			//事件句柄名默认为组件id+事件名称
			String handlerName = getId()+eventName;
			eventPropDesc[i] = new CategoryPropertyDescriptor(
					new EventHandlerPropertyDescriptor(eventName, eventName,handlerName), "事件");
			i++;
		}

		return ComponentUtils.mergePropDesc(descriptors, eventPropDesc);
	}

	public Object getPropertyValue(Object id)
	{
		if (id.equals(ID))
		{
			return getId();
		}
		// 由于字符终端控件很多看起来差不多，为了便于开发人员分辨，在属性处显示此控件的类型
		else if (id.equals(TYPE))
		{
			String modelClass = this.getClass().getName();
			ComponentDefLoader loader = ComponentDefLoader.getLoader();
			ComponentInfo info = loader.loadByModel(modelClass);
			return info.getName();
		}
		// 如果是事件定义
		else if (getEventDef().containsKey(id))
		{
			String method = getEventHandler().get(id);
			if (method == null)
				return "";
			return method;
		} else
		{
			return null;
		}
	}

	public boolean isPropertySet(Object id)
	{
		return true;
	}

	public void resetPropertyValue(Object id)
	{

	}

	public void setPropertyValue(Object id, Object value)
	{
		if (id == ID)
		{
			setId((String) value);
		}
		// 如果是事件定义
		else if (getEventDef().containsKey(id))
		{
			String methodName = (String) value;
			getEventHandler().put((String) id, methodName);
			if (!StringUtils.isEmpty(methodName))
			{
				StringBuffer sb = new StringBuffer();
				String NL = EnvironmentUtils.getLineSeparator();
				String signature = getEventDef().get(id);
				sb.append("def ").append(methodName).append(signature).append(
						":").append(NL);
				sb.append("    pass").append(NL);
				getForm().appendCustomCode(sb.toString());
			}

		}
	}

	/**
	 * 得到事件定义，key为事件名称，value为方法签名（包括括号）
	 * 
	 * @return
	 */
	public Map<String, String> getEventDef()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("ongrabfocus", "()");
		map.put("onfocuslost", "()");
		return map;
	}

	/**
	 * 复制部件，一般用来实现部件的复制、粘贴，详见PasteAction
	 */
	public Component clone()
	{
		try
		{
			Component newComponent = this.getClass().newInstance();

			// 因为在设置id属性的时候需要form，所以首先设定form属性
			newComponent.setForm(getForm());

			IPropertyDescriptor[] props = newComponent.getPropertyDescriptors();
			for (int i = 0, n = props.length; i < n; i++)
			{
				IPropertyDescriptor prop = props[i];
				Object propId = prop.getId();
				Object value = getPropertyValue(propId);
				newComponent.setPropertyValue(propId, value);
			}
			Rectangle newBounds = getBounds().getCopy();
			newComponent.setBounds(newBounds);
			return newComponent;
		} catch (InstantiationException e)
		{
			throw new RuntimeException(e);
		} catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * 调整要设置的editorRect为合理的termRect（不超出边界，如果空间大小受限，则同样调整大小）
	 * 
	 * @param termRect
	 * @return
	 */
	public abstract Rectangle caculateBounds(Rectangle editorRect);

	/**
	 * 生成代码片段(生成代码的逻辑本不应该和模型混在一起，不过由于代码生成逻辑
	 * 目前不是很复杂，所以暂时这么做，以后如果由于支持WXPython等原因需要分开
	 * 的时候可以定义一个ICodeGen接口，然后在模型的getadapter中返回)
	 * 
	 * @param parentId
	 *            父控件的id
	 * @return 代码段，List中每一个元素是一个类型为String的代码行
	 */
	public abstract List generateCode(String parentId);

	public void fillAttr(String id, stmtType[] stmts)
	{
		Map<String, String> eventDef = getEventDef();
		Set<String> keySet = eventDef.keySet();
		for (String eventHandlerName : keySet)
		{
			String methodName = ParserUtils.getEventHandlerMethodName(stmts,
					eventHandlerName);
			if (methodName != null)
			{
				getEventHandler().put(eventHandlerName, methodName);
			}

		}

	};
}