/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbCauseFacadeLocal;
import ejb.DbHazardFacadeLocal;
import entities.DbCause;
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
@Named(value = "cause_MB")
@ViewScoped
public class cause_MB implements Serializable {

    @EJB
    private DbHazardFacadeLocal dbHazardFacade;

    @EJB
    private DbCauseFacadeLocal dbCauseFacade;

    private List<DbCause> listDbCause;
    private List<DbCause> filteredCauses;
    private List<DbHazard> listHazards;
    private DbHazard hazardObject;
    private DbCause causeObject;
    private DbCause selectedCause;
    private List<DbHazard> selectedHazards;

    private boolean addFlag = false;
    private boolean editFlag = false;
    private boolean addButton = false;
    private boolean editButton = false;
    private boolean deleteButton = false;

    public cause_MB() {
    }

    public List<DbCause> getListDbCause() {
        return listDbCause;
    }

    public void setListDbCause(List<DbCause> listDbCause) {
        this.listDbCause = listDbCause;
    }

    public DbCause getCauseObject() {
        return causeObject;
    }

    public void setCauseObject(DbCause causeObject) {
        this.causeObject = causeObject;
    }

    public DbCause getSelectedCause() {
        return selectedCause;
    }

    public void setSelectedCause(DbCause selectedCause) {
        this.selectedCause = selectedCause;
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

    public List<DbCause> getFilteredCauses() {
        return filteredCauses;
    }

    public void setFilteredCauses(List<DbCause> filteredCauses) {
        this.filteredCauses = filteredCauses;
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
        listDbCause = dbCauseFacade.findAll();
        listHazards = dbHazardFacade.findAll();
        hazardObject = new DbHazard();
        causeObject = new DbCause();
    }

    public void addCause() {
        try {
            if (hazardObject.getHazardId().equals("null")) {
                causeObject.setHazardId(null);
                dbCauseFacade.create(causeObject);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Added", "The cause has been successfully added"));
            } else if (!dbHazardFacade.validateHazardId(hazardObject.getHazardId()).isEmpty()) {
                causeObject.setHazardId(hazardObject.getHazardId());
                dbCauseFacade.create(causeObject);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Added", "The cause has been successfully added"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "The hazardId does not exist"));
                return;
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error:" + e.getMessage()));
        }
        causeObject = new DbCause();
        hazardObject = new DbHazard();
        init();
    }

    public void editCause() {
        try {
            if (hazardObject.getHazardId().equals("null")) {
                causeObject.setHazardId(null);
                dbCauseFacade.edit(causeObject);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Added", "The cause has been successfully edited"));
            } else if (!dbHazardFacade.validateHazardId(hazardObject.getHazardId()).isEmpty()) {
                causeObject.setHazardId(hazardObject.getHazardId());
                dbCauseFacade.edit(causeObject);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Added", "The cause has been successfully edited"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "The hazardId does not exist"));
                return;
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error:" + e.getMessage()));
        }
        causeObject = new DbCause();
        hazardObject = new DbHazard();
        init();
        editFlag = false;
        addButton = false;
        deleteButton = false;
    }

    public void deleteCause(DbCause causeObject) {
        try {
            dbCauseFacade.remove(causeObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Removed", "The cause has been successfully removed"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error:" + e.getMessage()));
        } finally {
            init();
        }
    }

    public void showAdd() {
        addFlag = true;
        addButton = true;
        editButton = true;
    }

    public void showEdit(DbCause causeObject) {
        editFlag = true;
        this.causeObject = causeObject;
        hazardObject = new DbHazard(causeObject.getHazardId());
        addButton = true;
        deleteButton = true;
    }

    public void cancel() {
        addFlag = false;
        editFlag = false;

        addButton = false;
        editButton = false;
        deleteButton = false;

        causeObject = new DbCause();
        hazardObject = new DbHazard();
    }
    
    public void showLinkedHazards(DbCause selectedCause) {
        setSelectedCause(selectedCause);
        setSelectedHazards(dbHazardFacade.getHazardsFromCause(selectedCause.getCauseId()));
    }
    
}
