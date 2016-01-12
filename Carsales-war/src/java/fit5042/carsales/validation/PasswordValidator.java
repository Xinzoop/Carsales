/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.carsales.validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Confirm Password
 * @author zipv5_000
 */
@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object password = value.toString();
        
        System.out.println("password:" + password);
        Object confirm = component.getAttributes().get("confirm");
        if(null == password || null == confirm)
            return;
        System.out.println("confirm:" + confirm);
        if(!password.equals(confirm)){
            throw new ValidatorException(new FacesMessage("Passwords are not equal."));
        }
    }
    
}
