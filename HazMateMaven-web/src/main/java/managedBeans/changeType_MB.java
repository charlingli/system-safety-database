/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbchangeTypeFacadeLocal;
import entities.DbLocation;
import entities.DbchangeType;
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
@Named(value = "changeType_MB")
@ViewScoped
public class changeType_MB implements Serializable {

    @EJB
    private DbchangeTypeFacadeLocal dbchangeTypeFacade;

    private List<DbchangeType> listDbChangeType;
    private List<DbLocation> listDbLocation; 
    private List<DbchangeType> existingChangeType;
    
    private DbchangeType changeTypeObject = new DbchangeType();

    private boolean addFlag = false;
    private boolean editFlag = false;
    private boolean addButton = false;
    private boolean editButton = false;
    private boolean deleteButton = false;
    
    String prevChangeTypeName; 
    
    public changeType_MB() {
    }

    public List<DbchangeType> getListDbChangeType() {
        return listDbChangeType;
    }

    public void setListDbChangeType(List<DbchangeType> listDbChangeType) {
        this.listDbChangeType = listDbChangeType;
    }

    public DbchangeType getChangeTypeObject() {
        return changeTypeObject;
    }

    public void setChangeTypeObject(DbchangeType changeTypeObject) {
        this.changeTypeObject = changeTypeObject;
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
        listDbChangeType = dbchangeTypeFacade.findAll();
    }

    public void addChangeType() {
        existingChangeType = dbchangeTypeFacade.findByName("changeTypeName", changeTypeObject.getChangeTypeName());
        
        if (existingChangeType.isEmpty()) {
            dbchangeTypeFacade.create(changeTypeObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The change type has been successfully added."));
        } else { 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The change type name already exists!"));
            return;
        }
        changeTypeObject = new DbchangeType();
        init();

    }

    public void editChangeType() {
        existingChangeType = dbchangeTypeFacade.findByName("changeTypeName", changeTypeObject.getChangeTypeName());
        
        if (existingChangeType.isEmpty() || existingChangeType.get(0).getChangeTypeName().equals(prevChangeTypeName)) {
            dbchangeTypeFacade.edit(changeTypeObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The change type has been successfully edited."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The change type name already exists!"));
            return;
        }
        changeTypeObject = new DbchangeType();
        init();
        editFlag = false;
        addButton = false;
        deleteButton = false;
    }

    public void deleteChangeType(DbchangeType changeTypeObject) {
        listDbLocation = dbchangeTypeFacade.checkChangeType(changeTypeObject.getChangeTypeId());
        
        if (listDbLocation.isEmpty())   {
            dbchangeTypeFacade.remove(changeTypeObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The change type has been successfully deleted."));
        }
        else    {
            error(); 
            return;
        }
        
        init();
    }

    public void showAdd() {
        addFlag = true;
        addButton = true;
        editButton = true;
    }

    public void showEdit(DbchangeType changeTypeObject) {
        editFlag = true;
        addButton = true;
        deleteButton = true;

        this.changeTypeObject = changeTypeObject;
        prevChangeTypeName = changeTypeObject.getChangeTypeName();
    }

    public void cancel() {
        addFlag = false;
        editFlag = false;

        addButton = false;
        editButton = false;
        deleteButton = false;

        changeTypeObject = new DbchangeType();
    }
    
    public void error() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The change type is currently assigned to one or more locations."));
    }
}
