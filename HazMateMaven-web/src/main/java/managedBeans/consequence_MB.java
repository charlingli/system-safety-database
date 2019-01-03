/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbConsequenceFacadeLocal;
import ejb.DbHazardFacadeLocal;
import ejb.DbindexedWordFacadeLocal;
import entities.DbConsequence;
import entities.DbHazard;
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
@Named(value = "consequence_MB")
@ViewScoped
public class consequence_MB implements Serializable {

    @EJB
    private DbindexedWordFacadeLocal dbindexedWordFacade;

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
        addFlag = false;
        editFlag = false;
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
        init();
    }

    public void deleteConsequence(DbConsequence consequenceObject) {
        try {
            dbConsequenceFacade.remove(consequenceObject);
            dbindexedWordFacade.removeObject(String.valueOf(consequenceObject.getConsequenceId()), "consequence");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The consequence has been successfully deleted."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", e.getMessage()));
        } finally {
            init();
        }
    }

    public void showAdd() {
        addFlag = true;
        hazardObject = new DbHazard();
        consequenceObject = new DbConsequence();
    }

    public void showEdit(DbConsequence existingConsequence) {
        editFlag = true;
        consequenceObject = existingConsequence;
        hazardObject = new DbHazard(consequenceObject.getHazardId());
    }

    public void cancel() {
        addFlag = false;
        editFlag = false;

        consequenceObject = new DbConsequence();
        hazardObject = new DbHazard();
    }
    
    public void showLinkedHazards(DbConsequence selectedConsequence) {
        setSelectedConsequence(selectedConsequence);
        setSelectedHazards(dbHazardFacade.getHazardsFromConsequence(selectedConsequence.getConsequenceId()));
    }
    
    public void processPage() {
        if (addFlag) {
            addConsequence();
        } else if (editFlag) {
            editConsequence();
        }
    }

}
