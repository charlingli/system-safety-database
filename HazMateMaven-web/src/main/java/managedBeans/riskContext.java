/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbriskClassFacadeLocal;
import entities.DbriskClass;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Juan David
 */
@Named(value = "riskContext")
@ViewScoped
public class riskContext implements Serializable {

    @EJB
    private DbriskClassFacadeLocal dbriskContextFacade;
    private List<DbriskClass> listRiskContext;
    private DbriskClass riskContextObject;
    private boolean addFormRendered;

    public riskContext() {
    }

    public List<DbriskClass> getListRiskContext() {
        return listRiskContext;
    }

    public void setListRiskContext(List<DbriskClass> listRiskContext) {
        this.listRiskContext = listRiskContext;
    }

    public boolean isAddFormRendered() {
        return addFormRendered;
    }

    public void setAddFormRendered(boolean addFormRendered) {
        this.addFormRendered = addFormRendered;
    }

    public DbriskClass getRiskContextObject() {
        return riskContextObject;
    }

    public void setRiskContextObject(DbriskClass riskContextObject) {
        this.riskContextObject = riskContextObject;
    }

    @PostConstruct
    public void init() {
        setListRiskContext(dbriskContextFacade.findAll());
        riskContextObject = new DbriskClass();
        addFormRendered = false;
    }

    public void addRiskContext() {
        switch (validateDuplicates()) {
            case 0:
                if (riskContextObject.getRiskClassId() == null) {
                    dbriskContextFacade.create(riskContextObject);
                    init();
                } else {
                    dbriskContextFacade.edit(riskContextObject);
                    init();
                }
                break;
            case 1:
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The risk class name already exists."));
                break;
            default:
                init();
                break;
        }
    }

    public void editRiskContext(DbriskClass riskContIn) {
        setRiskContextObject(riskContIn);
        addFormRendered = true;
    }

    public void deleteRiskContext(DbriskClass riskContIn) {
        dbriskContextFacade.remove(riskContIn);
        init();
    }

    public void unHideAdd() {
        if (addFormRendered) {
            addFormRendered = false;
        } else {
            addFormRendered = true;
            riskContextObject = new DbriskClass();
        }
    }
    
    //Returns -> 0: Persist Transaction | 1: Throw an error | 2: Do not Persist
    public int validateDuplicates() {
        List<DbriskClass> temp = dbriskContextFacade.findByName("riskClassName", riskContextObject.getRiskClassName());
        if (!temp.isEmpty()) {
            if (temp.get(0).getRiskClassId() == riskContextObject.getRiskClassId()) {
                return 2;
            }
            return 1;
        }
        return 0;
    }
}
