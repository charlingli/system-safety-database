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
        hazardObject = new DbHazard();
        consequenceObject = new DbConsequence();
    }

    public void addConsequence() {
        try {
            if (hazardObject.getHazardId().equals("null")) {
                consequenceObject.setHazardId(null);
                dbConsequenceFacade.create(consequenceObject);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Added", "The cause has been successfully added"));
            } else if (!dbHazardFacade.validateHazardId(hazardObject.getHazardId()).isEmpty()) {
                consequenceObject.setHazardId(hazardObject.getHazardId());
                dbConsequenceFacade.create(consequenceObject);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Added", "The cause has been successfully added"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "The hazardId does not exist"));
                return;
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error:" + e.getMessage()));
        }
        consequenceObject = new DbConsequence();
        init();
        addFlag = false;

    }

    public void editConsequence() {

        try {
            if (hazardObject.getHazardId().equals("null")) {
                consequenceObject.setHazardId(null);
                dbConsequenceFacade.edit(consequenceObject);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Added", "The cause has been successfully edited"));
            } else if (!dbHazardFacade.validateHazardId(hazardObject.getHazardId()).isEmpty()) {
                consequenceObject.setHazardId(hazardObject.getHazardId());
                dbConsequenceFacade.edit(consequenceObject);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Added", "The cause has been successfully edited"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "The hazardId does not exist"));
                return;
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error:" + e.getMessage()));
        }
        consequenceObject = new DbConsequence();
        init();
        editFlag = false;

    }

    public void deleteConsequence(DbConsequence consequenceObject) {
        try {
            dbConsequenceFacade.remove(consequenceObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Removed", "The consequence has been successfully removed"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error:" + e.getMessage()));
        } finally {
            init();
        }
    }

    public void showAdd() {
        addFlag = true;
    }

    public void showEdit(DbConsequence consequenceObject) {
        editFlag = true;
        this.consequenceObject = consequenceObject;
        hazardObject = new DbHazard(consequenceObject.getHazardId());
    }

    public void cancel() {
        addFlag = false;
        editFlag = false;

        consequenceObject = new DbConsequence();
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
