package com.skorobahatko.library.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.ResourceBundle;

@FacesValidator("nameValidator")
public class NameValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String value = (String) o;
        if (value.trim().length() == 0) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
                    facesContext.getViewRoot().getLocale());
            FacesMessage message = new FacesMessage(resourceBundle.getString("welcome_name_required"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
        if (value.length() < 5) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
                    facesContext.getViewRoot().getLocale());
            FacesMessage message = new FacesMessage(resourceBundle.getString("welcome_name_length_error"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
}
