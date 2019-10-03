package cn.com.agree.naha.designer.policies;

import java.util.List;

import cn.com.agree.naha.designer.model.Component;
import cn.com.agree.naha.designer.model.Form;

import com.cownew.ctk.common.NumberUtils;

/**
 * 控件Id管理器
 * @author 杨中科
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
	 * 用来生成此UI中唯一的控件id
	 * @param component
	 * @return
	 */
	public String generateId(Component component)
	{
		String modelName = component.getClass().getSimpleName().toLowerCase();
		List<Component> components = form.getComponents();

		// 同类控件的最大序列号
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
	 * id是否重复
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
