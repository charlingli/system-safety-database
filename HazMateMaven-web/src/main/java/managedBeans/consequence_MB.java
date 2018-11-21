/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbConsequenceFacadeLocal;
import ejb.DbHazardFacadeLocal;
import entities.DbConsequence;
import entities.DbHazard;
import java.io.Serializable;
import java.util.ArrayList;
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
@Named(value = "consequence_MB")
@ViewScoped
public class consequence_MB implements Serializable {

    @EJB
    private DbHazardFacadeLocal dbHazardFacade;

    @EJB
    private DbConsequenceFacadeLocal dbConsequenceFacade;

    private List<DbConsequence> listDbConsequence;
    private List<DbConsequence> filteredConsequences;
    private List<DbHazard> listHazards;
    private DbHazard hazardObject;
    private DbConsequence consequenceObject;
    private DbConsequence selectedConsequence;
    private List<DbHazard> selectedHazards;

    private boolean addFlag = false;
    private boolean editFlag = false;
    private boolean addButton = false;
    private boolean editButton = false;
    private boolean deleteButton = false;

    public consequence_MB() {
    }

    public List<DbConsequence> getListDbConsequence() {
        return listDbConsequence;
    }

    public void setListDbConsequence(List<DbConsequence> listDbConsequence) {
        this.listDbConsequence = listDbConsequence;
    }

    public DbConsequence getConsequenceObject() {
        return consequenceObject;
    }

    public void setConsequenceObject(DbConsequence consequenceObject) {
        this.consequenceObject = consequenceObject;
    }

    public DbConsequence getSelectedConsequence() {
        return selectedConsequence;
    }

    public void setSelectedConsequence(DbConsequence selectedConsequence) {
        this.selectedConsequence = selectedConsequence;
    }

    public List<DbHazard> getSelectedHazards() {
        return selectedHazards;
    }

    public void setSelectedHazards(List<DbHazard> selectedHazards) {
        this.selectedHazards = selectedHazards;
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

    public List<DbConsequence> getFilteredConsequences() {
        return filteredConsequences;
    }

    public void setFilteredConsequences(List<DbConsequence> filteredConsequences) {
        this.filteredConsequences = filteredConsequences;
    }

    public List<DbHazard> getListHazards() {
        return listHazards;
    }

    public void setListHazards(List<DbHazard> listHazards) {
        this.listHazards = listHazards;
    }

    public DbHazard getHazardObject() {
        return hazardObject;
    }

    public void setHazardObject(DbHazard hazardObject) {
        this.hazardObject = hazardObject;
    }

    @PostConstruct
    public void init() {
        listDbConsequence = dbConsequenceFacade.findAll();
        listHazards = dbHazardFacade.findAll();
        hazardObject = new DbHazard();
        consequenceObject = new DbConsequence();
    }

    public void addConsequence() {
        try {
            if (hazardObject.getHazardId() != null) {
                if (!dbHazardFacade.validateHazardId(hazardObject.getHazardId()).isEmpty()) {
                    consequenceObject.setHazardId(hazardObject.getHazardId());
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "This hazard does not exist!"));
                    return;
                }
            }
            dbConsequenceFacade.create(consequenceObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The consequence has been successfully added."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", e.getMessage()));
        }
        consequenceObject = new DbConsequence();
        init();

    }

    public void editConsequence() {
        try {
            if (hazardObject.getHazardId() != null) {
                if (!dbHazardFacade.validateHazardId(hazardObject.getHazardId()).isEmpty()) {
                    consequenceObject.setHazardId(hazardObject.getHazardId());
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "This hazard does not exist!"));
                    return;
                }
            }
            dbConsequenceFacade.edit(consequenceObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The consequence has been successfully edited."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", e.getMessage()));
        }
        consequenceObject = new DbConsequence();
        init();
        editFlag = false;
        addButton = false;
        deleteButton = false;

    }

    public void deleteConsequence(DbConsequence consequenceObject) {
        try {
            dbConsequenceFacade.remove(consequenceObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The consequence has been successfully removed."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", e.getMessage()));
        } finally {
            init();
        }
    }

    public void showAdd() {
        addFlag = true;
        addButton = true;
        editButton = true;
    }

    public void showEdit(DbConsequence consequenceObject) {
        editFlag = true;
        this.consequenceObject = consequenceObject;
        hazardObject = new DbHazard(consequenceObject.getHazardId());
        addButton = true;
        deleteButton = true;
    }

    public void cancel() {
        addFlag = false;
        editFlag = false;

        addButton = false;
        editButton = false;
        deleteButton = false;

        consequenceObject = new DbConsequence();
        hazardObject = new DbHazard();
    }
    
    public void showLinkedHazards(DbConsequence selectedConsequence) {
        setSelectedConsequence(selectedConsequence);
        setSelectedHazards(dbHazardFacade.getHazardsFromConsequence(selectedConsequence.getConsequenceId()));
    }

}
