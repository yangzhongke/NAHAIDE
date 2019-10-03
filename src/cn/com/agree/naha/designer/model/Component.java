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

	public static final String TYPE = "�ؼ�����";

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
	 * �¼������壬keyΪ�¼������ƣ�valueΪ��Ӧ���¼���������������������ǩ����
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
					"���趨id֮ǰ�������趨����ĸ�Form����ΪҪУ��id�Ƿ��ظ�");
		}

		ComponentIdManager idMgr = new ComponentIdManager(getForm());
		// ��ֹid����
		if (idMgr.isIdConflicted(id, this))
		{
			return;
		}

		// ��ȥ����
		getForm().removeGlobalVar(this.id);
		// �������
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
				// ��ֹid����
				if (idMgr.isIdConflicted(id, Component.this))
				{
					return "�ؼ�Id��" + id + "�Ѿ�����";
				}
				return null;
			}

		});
		PropertyDescriptor typeDesc = new PropertyDescriptor(TYPE, "�ؼ�����");
		IPropertyDescriptor[] descriptors = new IPropertyDescriptor[] { idDesc,
				typeDesc };

		// �¼�����
		Map<String, String> eventDef = this.getEventDef();
		IPropertyDescriptor[] eventPropDesc = new IPropertyDescriptor[eventDef
				.size()];
		Set<String> eventDefKeySet = eventDef.keySet();
		int i = 0;
		for (String eventName : eventDefKeySet)
		{
			//�¼������Ĭ��Ϊ���id+�¼�����
			String handlerName = getId()+eventName;
			eventPropDesc[i] = new CategoryPropertyDescriptor(
					new EventHandlerPropertyDescriptor(eventName, eventName,handlerName), "�¼�");
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
		// �����ַ��ն˿ؼ��ܶ࿴������࣬Ϊ�˱��ڿ�����Ա�ֱ棬�����Դ���ʾ�˿ؼ�������
		else if (id.equals(TYPE))
		{
			String modelClass = this.getClass().getName();
			ComponentDefLoader loader = ComponentDefLoader.getLoader();
			ComponentInfo info = loader.loadByModel(modelClass);
			return info.getName();
		}
		// ������¼�����
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
		// ������¼�����
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
	 * �õ��¼����壬keyΪ�¼����ƣ�valueΪ����ǩ�����������ţ�
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
	 * ���Ʋ�����һ������ʵ�ֲ����ĸ��ơ�ճ�������PasteAction
	 */
	public Component clone()
	{
		try
		{
			Component newComponent = this.getClass().newInstance();

			// ��Ϊ������id���Ե�ʱ����Ҫform�����������趨form����
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
	 * ����Ҫ���õ�editorRectΪ�����termRect���������߽磬����ռ��С���ޣ���ͬ��������С��
	 * 
	 * @param termRect
	 * @return
	 */
	public abstract Rectangle caculateBounds(Rectangle editorRect);

	/**
	 * ���ɴ���Ƭ��(���ɴ�����߼�����Ӧ�ú�ģ�ͻ���һ�𣬲������ڴ��������߼�
	 * Ŀǰ���Ǻܸ��ӣ�������ʱ��ô�����Ժ��������֧��WXPython��ԭ����Ҫ�ֿ�
	 * ��ʱ����Զ���һ��ICodeGen�ӿڣ�Ȼ����ģ�͵�getadapter�з���)
	 * 
	 * @param parentId
	 *            ���ؼ���id
	 * @return ����Σ�List��ÿһ��Ԫ����һ������ΪString�Ĵ�����
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