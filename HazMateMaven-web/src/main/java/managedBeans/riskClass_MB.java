/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbriskClassFacadeLocal;
import entities.DbHazard;
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
 * @author alan8
 */
@Named(value = "riskClass_MB")
@ViewScoped
public class riskClass_MB implements Serializable {

    @EJB
    private DbriskClassFacadeLocal dbriskClassFacade;

    private List<DbriskClass> listDbRiskClass;
    private List<DbHazard> listDbHazard;
    private List<DbriskClass> existingRiskClass; 
    
    private DbriskClass riskClassObject = new DbriskClass();

    private boolean addFlag = false;
    private boolean editFlag = false;
    private boolean addButton = false;
    private boolean editButton = false;
    private boolean deleteButton = false;
    
    String prevRiskClassName; 
    
    public riskClass_MB() {
    }

    public List<DbriskClass> getListDbRiskClass() {
        return listDbRiskClass;
    }

    public void setListDbRiskClass(List<DbriskClass> listDbRiskClass) {
        this.listDbRiskClass = listDbRiskClass;
    }

    public DbriskClass getRiskClassObject() {
        return riskClassObject;
    }

    public void setRiskClassObject(DbriskClass riskClassObject) {
        this.riskClassObject = riskClassObject;
    }

    public boolean isAddFlag() {
        return addFlag;
    }

    public void setAddFlag(boolean addFlag) {
        this.addFlag = addFlag;
    }

    public boolean isEditFlag() {
        return editFlag;
    }

    public void setEditFlag(boolean editFlag) {
        this.editFlag = editFlag;
    }

    public boolean isAddButton() {
        return addButton;
    }

    public void setAddButton(boolean addButton) {
        this.addButton = addButton;
    }

    public boolean isEditButton() {
        return editButton;
    }

    public void setEditButton(boolean editButton) {
        this.editButton = editButton;
    }

    public boolean isDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(boolean deleteButton) {
        this.deleteButton = deleteButton;
    }

    @PostConstruct
    public void init() {
        listDbRiskClass = dbriskClassFacade.findAll();
    }

    public void addRiskClass() {
        existingRiskClass = dbriskClassFacade.findByName("riskClassName", riskClassObject.getRiskClassName());
        
        if (existingRiskClass.isEmpty()) {
            dbriskClassFacade.create(riskClassObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The risk class has been successfully added."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The risk class name exists already!"));
            return;
        }
        
        riskClassObject = new DbriskClass();
        init();

    }

    public void editRiskClass() {
        existingRiskClass = dbriskClassFacade.findByName("riskClassName", riskClassObject.getRiskClassName());
        
        if (existingRiskClass.isEmpty() || existingRiskClass.get(0).getRiskClassName().equals(prevRiskClassName)) {
            dbriskClassFacade.edit(riskClassObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The risk class has been successfully edited."));
        } else { 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The risk class name exists already!"));
            return;
        }
        riskClassObject = new DbriskClass();
        init();
        editFlag = false;
        addButton = false;
        deleteButton = false;
    }

    public void deleteRiskClass(DbriskClass riskClassObject) {
        listDbHazard = dbriskClassFacade.checkRiskClass(riskClassObject.getRiskClassId());

        if (listDbHazard.isEmpty()) {
            dbriskClassFacade.remove(riskClassObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The risk class has been successfully removed."));
        } else {
            error();
            return;
        }
        init();
    }

    public void showAdd() {
        addFlag = true;
        addButton = true;
        editButton = true;
    }

    public void showEdit(DbriskClass riskClassObject) {
        editFlag = true;
        addButton = true;
        deleteButton = true;

        this.riskClassObject = riskClassObject;
        prevRiskClassName = riskClassObject.getRiskClassName(); 
    }

    public void cancel() {
        addFlag = false;
        editFlag = false;

        addButton = false;
        editButton = false;
        deleteButton = false;

        riskClassObject = new DbriskClass();
    }
    
    public void error() {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The risk class is currently assigned to one or more hazards."));
    }
    
}
