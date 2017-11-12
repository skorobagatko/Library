package com.skorobahatko.library.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.ResourceBundle;

@FacesValidator("loginValidator")
public class LoginValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String value = (String) o;
        if (value.trim().length() == 0) {
            sendError("welcome_name_required");
        }
        if (value.length() < 5) {
            sendError("welcome_name_length_error");
        }
    }

    private void sendError(String msg) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesMessage message = new FacesMessage(resourceBundle.getString(msg));
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(message);
    }
}
