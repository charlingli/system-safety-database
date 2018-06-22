/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import customObjects.validateIdObject;
import ejb.DbglobalIdFacadeLocal;
import entities.DbglobalId;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Juan David
 */
@Named(value = "autoIds_MB")
@RequestScoped
public class autoIds_MB {

    @EJB
    private DbglobalIdFacadeLocal dbglobalIdFacade;

    public autoIds_MB() {
    }

    //function for test the consecutive creation
    //For Delete
    public void testConsecutives() {
        validateIdObject tmpId = dbglobalIdFacade.nextConsecutive("PED", "", 10);
        if (tmpId.isValidationFlag()) {
            tmpId.getAnswerString();
        }
    }

}
