/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbwfDecisionFacadeLocal;
import entities.DbwfDecision;
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
@Named(value = "workflowDecision_MB")
@ViewScoped
public class workflowDecision_MB implements Serializable {

    @EJB
    private DbwfDecisionFacadeLocal dbwfDecisionFacade;

    private List<DbwfDecision> listDbWfDecision;
    private List<DbwfDecision> existingWfDecision; 
    
    private DbwfDecision wfDecisionObject = new DbwfDecision();

    private boolean addFlag = false;
    private boolean editFlag = false;
    private boolean addButton = false;
    private boolean editButton = false;
    private boolean deleteButton = false;
    
    String prevWfDecisionName; 
    
    public workflowDecision_MB() {
    }

    public List<DbwfDecision> getListDbWfDecision() {
        return listDbWfDecision;
    }

    public void setListDbWfDecision(List<DbwfDecision> listDbWfDecision) {
        this.listDbWfDecision = listDbWfDecision;
    }

    public DbwfDecision getWfDecisionObject() {
        return wfDecisionObject;
    }

    public void setWfDecisionObject(DbwfDecision wfDecisionObject) {
        this.wfDecisionObject = wfDecisionObject;
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
        listDbWfDecision = dbwfDecisionFacade.findAll();
    }

    public void addwfDecision() {
        existingWfDecision = dbwfDecisionFacade.findByName("wfDecisionId", wfDecisionObject.getWfDecisionId());
        
        if (existingWfDecision.isEmpty()) {
            dbwfDecisionFacade.create(wfDecisionObject);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The workflow decision ID exists already."));
            return;
        }
        
        wfDecisionObject = new DbwfDecision();
        init();

    }

    public void editwfDecision() {
        existingWfDecision = dbwfDecisionFacade.findByName("wfDecisionName", wfDecisionObject.getWfDecisionName());
        
        if (existingWfDecision.isEmpty() || existingWfDecision.get(0).getWfDecisionName().equals(prevWfDecisionName)) {
            dbwfDecisionFacade.edit(wfDecisionObject);
        } else { 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The workflow decision ID exists already."));
            return;
        }
        wfDecisionObject = new DbwfDecision();
        init();
        editFlag = false;
        addButton = false;
        deleteButton = false;
    }

    public void deletewfDecision(DbwfDecision wfDecisionObject) {
        existingWfDecision = dbwfDecisionFacade.checkWfDecision(wfDecisionObject.getWfDecisionId());

        if (existingWfDecision.isEmpty()) {
            dbwfDecisionFacade.remove(wfDecisionObject);
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

    public void showEdit(DbwfDecision wfDecisionObject) {
        editFlag = true;
        addButton = true;
        deleteButton = true;

        this.wfDecisionObject = wfDecisionObject;
        prevWfDecisionName = wfDecisionObject.getWfDecisionName(); 
    }

    public void cancel() {
        addFlag = false;
        editFlag = false;

        addButton = false;
        editButton = false;
        deleteButton = false;

        wfDecisionObject = new DbwfDecision();
    }
    
    public void error() {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The workflow decision is currently assigned to one or more workflows."));
    }
    
}
