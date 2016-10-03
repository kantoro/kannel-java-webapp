package kg.freesms.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import kg.freesms.entity.Phone;
import kg.freesms.facade.PhoneFacade;

@FacesConverter("phoneConverter")
public class PhoneConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if(value != null && value.trim().length() > 0) {
			PhoneFacade phoneFacade = new PhoneFacade();
			int phoneId;
			
			try {
				phoneId = Integer.parseInt(value);
				return phoneFacade.findPhone(phoneId);
			} catch (NumberFormatException exception) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Type the Phone number and select it (or use the dropdow)", "Type the Phone number and select it (or use the dropdow)"));
			}
		} else {
            return null;
        }
	}
	
	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		
		if(object != null) {
            return String.valueOf(((Phone) object).getId());
        }
        else {
            return null;
        }
	}
	
}
