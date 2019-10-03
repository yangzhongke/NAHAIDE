package cn.com.agree.naha.designer.policies;

import java.util.List;

import cn.com.agree.naha.designer.model.Component;
import cn.com.agree.naha.designer.model.Form;

import com.cownew.ctk.common.NumberUtils;

/**
 * �ؼ�Id������
 * @author ���п�
 *
 */
public class ComponentIdManager
{	
	private Form form;
	public ComponentIdManager(Form form)
	{
		super();
		this.form = form;
	}
	
	/**
	 * �������ɴ�UI��Ψһ�Ŀؼ�id
	 * @param component
	 * @return
	 */
	public String generateId(Component component)
	{
		String modelName = component.getClass().getSimpleName().toLowerCase();
		List<Component> components = form.getComponents();

		// ͬ��ؼ���������к�
		int maxSeqNum = 0;
		for (Component comp : components)
		{
			String compId = comp.getId();
			if (compId.startsWith(modelName))
			{
				String tail = compId.substring(modelName.length(), 
						compId.length());
				
				if(NumberUtils.isInteger(tail))
				{
					int intTail = Integer.parseInt(tail);
					if(intTail>maxSeqNum)
					{
						maxSeqNum = intTail;
					}
				}			
			}
		}
		return modelName+(maxSeqNum+1);
	}
	
	/**
	 * id�Ƿ��ظ�
	 * @param id
	 * @param component
	 * @return
	 */
	public boolean isIdConflicted(String id,Component component)
	{
		List<Component> components = form.getComponents();
		for (Component comp : components)
		{
			String compId = comp.getId();
			if(compId.equals(id)&&component!=comp)
			{
				return true;
			}
		}
		return false;
	}

}
