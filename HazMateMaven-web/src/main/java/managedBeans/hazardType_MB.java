/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbhazardTypeFacadeLocal;
import entities.DbHazard;
import entities.DbhazardType;
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
@Named(value = "hazardType_MB")
@ViewScoped
public class hazardType_MB implements Serializable {

    @EJB
    private DbhazardTypeFacadeLocal dbhazardTypeFacade;

    private List<DbhazardType> listDbHazardType;
    private List<DbHazard> listDbHazard;
    private List<DbhazardType> existingType; 
    
    private DbhazardType hazardTypeObject = new DbhazardType();

    private boolean addFlag = false;
    private boolean editFlag = false;
    private boolean addButton = false;
    private boolean editButton = false;
    private boolean deleteButton = false;
    
    String prevTypeName; 
    
    public hazardType_MB() {
    }

    public List<DbhazardType> getListDbHazardType() {
        return listDbHazardType;
    }

    public void setListDbHazardType(List<DbhazardType> listDbHazardType) {
        this.listDbHazardType = listDbHazardType;
    }

    public DbhazardType getHazardTypeObject() {
        return hazardTypeObject;
    }

    public void setHazardTypeObject(DbhazardType hazardTypeObject) {
        this.hazardTypeObject = hazardTypeObject;
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
        listDbHazardType = dbhazardTypeFacade.findAll();
    }
    
    public void addHazardType() {
        existingType = dbhazardTypeFacade.findByName("hazardTypeName", hazardTypeObject.getHazardTypeName()); 
        
        if (existingType.isEmpty()) {
            dbhazardTypeFacade.create(hazardTypeObject);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The hazard type name already exists."));
            return; 
        }
        hazardTypeObject = new DbhazardType();
        init();
        
    }
    
    public void editHazardType() { 
        existingType = dbhazardTypeFacade.findByName("hazardTypeName", hazardTypeObject.getHazardTypeName());
        
        if ( existingType.isEmpty() || existingType.get(0).getHazardTypeName().equals(prevTypeName)) {
            dbhazardTypeFacade.edit(hazardTypeObject);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The hazard type name already exists."));
            return;
        }
        hazardTypeObject = new DbhazardType(); 
        init(); 
        editFlag = false;
        addButton = false;
        deleteButton = false; 
    }
    
    
    public void deleteHazardType(DbhazardType hazardTypeObject){
        listDbHazard = dbhazardTypeFacade.checkHazardType(hazardTypeObject.getHazardTypeId());
        
        if (listDbHazard.isEmpty()) {
            dbhazardTypeFacade.remove(hazardTypeObject);
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
    
    public void showEdit(DbhazardType hazardTypeObject){ 
        editFlag = true; 
        addButton = true; 
        deleteButton = true; 
                
        this.hazardTypeObject = hazardTypeObject; 
        prevTypeName = hazardTypeObject.getHazardTypeName(); 
    }
    
    public void cancel(){
        addFlag = false; 
        editFlag = false;
        
        addButton = false; 
        editButton = false; 
        deleteButton = false; 
        
        hazardTypeObject = new DbhazardType(); 
    }
    
    public void error() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The hazard type is currently assigned to one or more hazards."));
    }
}
