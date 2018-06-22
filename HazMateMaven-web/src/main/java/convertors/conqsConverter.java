/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convertors;

import entities.DbConsequence;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Juan David
 */
@FacesConverter("consqConverter")
public class conqsConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        DbConsequence causeObj = new DbConsequence();
        causeObj.setConsequenceId(Integer.parseInt(value));
        return causeObj;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            return String.valueOf(((DbConsequence) value).getConsequenceId());
        } else {
            return null;
        }
    }

}
