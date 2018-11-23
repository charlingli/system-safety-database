/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbOwnersFacadeLocal;
import entities.DbControl;
import entities.DbHazard;
import entities.DbOwners;
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
@Named(value = "owners_MB")
@ViewScoped
public class owners_MB implements Serializable {

    @EJB
    private DbOwnersFacadeLocal dbOwnersFacade;

    private List<DbOwners> listDbOwners;
    private List<DbHazard> listDbHazard;
    private List<DbControl> listDbControl;
    private List<DbOwners> filteredOwners;
    private List<DbOwners> existingOwnerName;  
            
    private DbOwners ownersObject = new DbOwners();

    private boolean addFlag = false;
    private boolean editFlag = false;
    private boolean addButton = false;
    private boolean editButton = false; 
    private boolean deleteButton = false; 
    
    String prevOwnerName; 
    
    public owners_MB() {
    }

    public List<DbOwners> getListDbOwners() {
        return listDbOwners;
    }

    public void setListDbOwners(List<DbOwners> listDbOwners) {
        this.listDbOwners = listDbOwners;
    }

    public DbOwners getOwnersObject() {
        return ownersObject;
    }

    public void setOwnersObject(DbOwners ownersObject) {
        this.ownersObject = ownersObject;
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

    public List<DbOwners> getFilteredOwners() {
        return filteredOwners;
    }

    public void setFilteredOwners(List<DbOwners> filteredOwners) {
        this.filteredOwners = filteredOwners;
    }

    @PostConstruct
    public void init() {
        listDbOwners = dbOwnersFacade.findAll();

    }

    public void addOwners() {
        try {
            existingOwnerName = dbOwnersFacade.findByName("ownerName", ownersObject.getOwnerName());
            
            if (existingOwnerName.isEmpty()) {
                dbOwnersFacade.create(ownersObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The owner has been successfully added."));
            } else { 
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The owner name already exists!"));
                return;
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error:" + e.getMessage()));
        } 
        ownersObject = new DbOwners();
        init();
    }

    public void editOwners() {
        try {
            existingOwnerName = dbOwnersFacade.findByName("ownerName", ownersObject.getOwnerName());
            
            if (existingOwnerName.isEmpty() || existingOwnerName.get(0).getOwnerName().equals(prevOwnerName)) {
                dbOwnersFacade.edit(ownersObject);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The owner has been successfully edited."));
            } else { 
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The owner name already exists"));
                return;
            }
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error:" + e.getMessage()));
        } 
        ownersObject = new DbOwners();
        init();
        editFlag = false;
        addButton = false;
        deleteButton = false; 

        

    }

    public void deleteOwners(DbOwners ownersObject) {
        try {
            listDbHazard = dbOwnersFacade.checkHazardOwners(ownersObject.getOwnerId());
            listDbControl = dbOwnersFacade.checkControlOwners(ownersObject.getOwnerId());

            if (listDbHazard.isEmpty() && listDbControl.isEmpty()) {
                dbOwnersFacade.remove(ownersObject);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The owner has been successfully removed."));
            } else {
                error();
            }
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

    public void showEdit(DbOwners ownersObject) {
        editFlag = true;
        addButton = true; 
        deleteButton = true; 

        this.ownersObject = ownersObject;
        prevOwnerName = ownersObject.getOwnerName(); 
    }

    public void cancel() {
        addFlag = false;
        editFlag = false;

        addButton = false; 
        editButton = false; 
        deleteButton = false; 
        
        ownersObject = new DbOwners();
    }

    public void error() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The owner is currently assigned to one or more hazards and/or controls."));
    }
}
