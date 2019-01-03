/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbCauseFacadeLocal;
import ejb.DbHazardFacadeLocal;
import ejb.DbindexedWordFacadeLocal;
import entities.DbCause;
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
@Named(value = "cause_MB")
@ViewScoped
public class cause_MB implements Serializable {

    @EJB
    private DbindexedWordFacadeLocal dbindexedWordFacade;

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
        addFlag = false;
        editFlag = false;
    }

    public void addCause() { 
        try {
            if (hazardObject.getHazardId() != null) {
                if (!dbHazardFacade.validateHazardId(hazardObject.getHazardId()).isEmpty()) {
                    causeObject.setHazardId(hazardObject.getHazardId());
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "This hazard does not exist!"));
                    return;
                }
            }
            dbCauseFacade.create(causeObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The cause has been successfully added."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", e.getMessage()));
        }
        init();
    }

    public void editCause() {
        try {
            if (hazardObject.getHazardId() != null) {
                if (!dbHazardFacade.validateHazardId(hazardObject.getHazardId()).isEmpty()) {
                    causeObject.setHazardId(hazardObject.getHazardId());
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "This hazard does not exist!"));
                    return;
                }
            }
            dbCauseFacade.edit(causeObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The cause has been successfully edited."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", e.getMessage()));
        }
        init();
    }

    public void deleteCause(DbCause causeObject) {
        try {
            dbCauseFacade.remove(causeObject);
            dbindexedWordFacade.removeObject(String.valueOf(causeObject.getCauseId()), "cause");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The cause has been successfully deleted"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", e.getMessage()));
        } finally {
            init();
        }
    }

    public void showAdd() {
        addFlag = true;
        hazardObject = new DbHazard();
        causeObject = new DbCause();
    }

    public void showEdit(DbCause existingCause) {
        editFlag = true;
        causeObject = existingCause;
        hazardObject = new DbHazard(causeObject.getHazardId());
    }

    public void cancel() {
        addFlag = false;
        editFlag = false;

        causeObject = new DbCause();
        hazardObject = new DbHazard();
    }
    
    public void showLinkedHazards(DbCause selectedCause) {
        setSelectedCause(selectedCause);
        setSelectedHazards(dbHazardFacade.getHazardsFromCause(selectedCause.getCauseId()));
    }
    
    public void processPage() {
        if (addFlag) {
            addCause();
        } else if (editFlag) {
            editCause();
        }
    }
    
}
