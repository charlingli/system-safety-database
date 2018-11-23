/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbhazardActivityFacadeLocal;
import entities.DbHazard;
import entities.DbhazardActivity;
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
@Named(value = "hazardActivity_MB")
@ViewScoped
public class hazardActivity_MB implements Serializable {

    @EJB
    private DbhazardActivityFacadeLocal dbhazardActivityFacade;

    private List<DbhazardActivity> listDbHazardActivity;
    private List<DbHazard> listDbHazard;
    private List<DbhazardActivity> existingName; 
            
    private DbhazardActivity hazardActivityObject = new DbhazardActivity();
    
    private boolean addFlag = false;  
    private boolean editFlag = false; 
    private boolean addButton = false;
    private boolean editButton = false; 
    private boolean deleteButton = false; 
    
    String prevActivityName; 
    
    public hazardActivity_MB() {
    }

    public List<DbhazardActivity> getListDbHazardActivity() {
        return listDbHazardActivity;
    }

    public void setListDbHazardActivity(List<DbhazardActivity> listDbHazardActivity) {
        this.listDbHazardActivity = listDbHazardActivity;
    }

    public DbhazardActivity getHazardActivityObject() {
        return hazardActivityObject;
    }

    public void setHazardActivityObject(DbhazardActivity hazardActivityObject) {
        this.hazardActivityObject = hazardActivityObject;
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
        listDbHazardActivity = dbhazardActivityFacade.findAll(); 
        
    }
    
     public void addHazardActivity() {
        existingName = dbhazardActivityFacade.findByName("activityName", hazardActivityObject.getActivityName());
         
        if (existingName.isEmpty()) {
            dbhazardActivityFacade.create(hazardActivityObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The hazard activity has been successfully added."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The activity name already exists!"));
            return;
        }
        
        hazardActivityObject = new DbhazardActivity();
        init();
        
    }
    
    public void editHazardActivity() { 
        existingName = dbhazardActivityFacade.findByName("activityName", hazardActivityObject.getActivityName());
        
        if (existingName.isEmpty() || existingName.get(0).getActivityName().equals(prevActivityName) ) {
            dbhazardActivityFacade.edit(hazardActivityObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The hazard activity has been successfully edited."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The hazard activity name already exists!"));
            return;
        }
        hazardActivityObject = new DbhazardActivity(); 
        init(); 
        editFlag = false;
        addButton = false;
        deleteButton = false; 
    }
    
    
    public void deleteHazardActivity(DbhazardActivity hazardActivityObject){
        listDbHazard = dbhazardActivityFacade.checkHazardActivity(hazardActivityObject.getActivityId());
        
        if (listDbHazard.isEmpty()) {
            dbhazardActivityFacade.remove(hazardActivityObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The hazard activity has been successfully removed."));
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
    
    public void showEdit(DbhazardActivity hazardActivityObject){ 
        editFlag = true; 
        addButton = true; 
        deleteButton = true; 
                
        this.hazardActivityObject = hazardActivityObject;
        prevActivityName = hazardActivityObject.getActivityName();
    }
    
    public void cancel(){
        addFlag = false; 
        editFlag = false;
        
        addButton = false; 
        editButton = false; 
        deleteButton = false; 
        
        hazardActivityObject = new DbhazardActivity(); 
    }
    
    public void error() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The hazard activity is currently assigned to one or more hazards."));
    }
}
