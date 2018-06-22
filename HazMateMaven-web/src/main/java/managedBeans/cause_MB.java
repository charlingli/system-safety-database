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
        addFlag = false;
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
    }

    public void showEdit(DbCause causeObject) {
        editFlag = true;
        this.causeObject = causeObject;
        hazardObject = new DbHazard(causeObject.getHazardId());
    }

    public void cancel() {
        addFlag = false;
        editFlag = false;

        causeObject = new DbCause();
        hazardObject = new DbHazard();
    }

    public List<DbHazard> showHazards(String query) {
        List<DbHazard> filteredHazard = new ArrayList<>();

        for (int i = 0; i < listHazards.size(); i++) {
            DbHazard findHazard = listHazards.get(i);
            if (findHazard.getHazardDescription().toLowerCase().contains(query.toLowerCase())) {
                filteredHazard.add(findHazard);
            }
        }
        return filteredHazard;
    }

}
