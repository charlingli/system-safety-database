/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convertors;

import entities.DbCause;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Juan David
 */
@FacesConverter("causeConverter")
public class causeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        DbCause causeObj = new DbCause();
        causeObj.setCauseId(Integer.parseInt(value));
        return causeObj;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            return String.valueOf(((DbCause) value).getCauseId());
        } else {
            return null;
        }
    }

}
