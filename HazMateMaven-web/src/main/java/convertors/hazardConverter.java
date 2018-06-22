/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convertors;

import entities.DbHazard;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Juan David
 */
@FacesConverter("hazardConverter")
public class hazardConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        DbHazard hazardObj = new DbHazard();
        hazardObj.setHazardId(value);
        return hazardObj;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            return String.valueOf(((DbHazard) value).getHazardId());
        } else {
            return null;
        }
    }

}
