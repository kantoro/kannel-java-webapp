package kg.freesms.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import kg.freesms.entity.Group;
import kg.freesms.facade.GroupFacade;

@FacesConverter("groupConverter")
public class GroupConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if(value != null && value.trim().length() > 0) {
			GroupFacade groupFacade = new GroupFacade();
			int groupId;
			
			try {
				groupId = Integer.parseInt(value);
				return groupFacade.findGroup(groupId);
			} catch (NumberFormatException exception) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Type the Group name and select it (or use the dropdow)", "Type the Group name and select it (or use the dropdow)"));
			}
		} else {
            return null;
        }
	}
	
	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		
		if(object != null) {
            return String.valueOf(((Group) object).getId());
        }
        else {
            return null;
        }
	}
	
}
