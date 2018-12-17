/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbhazardStatusFacadeLocal;
import entities.DbHazard;
import entities.DbhazardStatus;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author alan8
 */

@Named(value = "hazardStatus_MB")
@ViewScoped
public class hazardStatus_MB implements Serializable{

    @EJB
    private DbhazardStatusFacadeLocal dbhazardStatusFacade;

    private List<DbhazardStatus> listDbHazardStatus; 
    private List<DbHazard>  listDbHazard;
    private List<DbhazardStatus> existingName;
    
    private DbhazardStatus hazardStatusObject = new DbhazardStatus(); 
            
    private boolean addFlag = false;  
    private boolean editFlag = false; 
    private boolean addButton = false;
    private boolean editButton = false; 
    private boolean deleteButton = false;  
    
    String prevStatusName; 
            
    public hazardStatus_MB() {
    }

    public List<DbhazardStatus> getListDbHazardStatus() {
        return listDbHazardStatus;
    }

    public void setListDbHazardStatus(List<DbhazardStatus> listDbHazardStatus) {
        this.listDbHazardStatus = listDbHazardStatus;
    }

    public DbhazardStatus getHazardStatusObject() {
        return hazardStatusObject;
    }

    public void setHazardStatusObject(DbhazardStatus hazardStatusObject) {
        this.hazardStatusObject = hazardStatusObject;
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
        listDbHazardStatus = dbhazardStatusFacade.findAll(); 
    }
    
    public void addHazardStatus() {
        existingName = dbhazardStatusFacade.findByName("hazardStatusName", hazardStatusObject.getHazardStatusName());
        
        if (existingName.isEmpty()) {
            dbhazardStatusFacade.create(hazardStatusObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The hazard status has been successfully added."));
        } else { 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The hazard status name already exists!"));
            return;
        }
        hazardStatusObject = new DbhazardStatus();
        init();
        
    }
    
    public void editHazardStatus() { 
        existingName = dbhazardStatusFacade.findByName("hazardStatusName", hazardStatusObject.getHazardStatusName());
        
        if (existingName.isEmpty() || (existingName.get(0).getHazardStatusName().equals(prevStatusName))) {
            dbhazardStatusFacade.edit(hazardStatusObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The hazard status has been successfully edited."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The hazard status name already exists!"));
            return;
        }
        
        hazardStatusObject = new DbhazardStatus(); 
        init(); 
        editFlag = false;
        addButton = false;
        deleteButton = false; 
    }
    
    
    public void deleteHazardStatus(DbhazardStatus hazardStatusObject){
        listDbHazard = dbhazardStatusFacade.checkHazardStatus(hazardStatusObject.getHazardStatusId());
        
        if (listDbHazard.isEmpty()) {
            dbhazardStatusFacade.remove(hazardStatusObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The hazard status has been successfully deleted."));
        }
        else    {
            error(); 
            return; 
        }
        init(); 
    }
    
    public void showAdd(){
        addFlag = true; 
        addButton = true;
        editButton = true; 
    }
    
    public void showEdit(DbhazardStatus hazardStatusObject){ 
        editFlag = true; 
        addButton = true; 
        deleteButton = true; 
                
        this.hazardStatusObject = hazardStatusObject; 
        prevStatusName = hazardStatusObject.getHazardStatusName(); 
    }
    
    public void cancel(){
        addFlag = false; 
        editFlag = false;
        
        addButton = false; 
        editButton = false; 
        deleteButton = false; 
        
        hazardStatusObject = new DbhazardStatus(); 
    }
    
    public void error() {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The hazard status is currently assigned to one or more hazards."));
    }
}
