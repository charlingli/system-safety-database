/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Admin
 */
@FacesValidator("pageIndexValidator")
public class pageIndexValidator implements Validator {

    static String numberRegex = "^[0-9]{1,10}$";
    static Pattern pattern = Pattern.compile(numberRegex, Pattern.CASE_INSENSITIVE);
    static Matcher matcher;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        if (value != null) {
            matcher = pattern.matcher(value.toString());

            if (!matcher.matches()) {
                
                FacesMessage msg = new FacesMessage("Page Index Error","Only numbers are allowed.");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);

            }

        }

    }
}
