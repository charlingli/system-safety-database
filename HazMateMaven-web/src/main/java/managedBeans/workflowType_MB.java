/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbwfHeaderFacadeLocal;
import ejb.DbwfTypeFacadeLocal;
import entities.DbwfHeader;
import entities.DbwfType;
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
 * @author lxra
 */
@Named(value = "workflowType_MB")
@ViewScoped
public class workflowType_MB implements Serializable {
    @EJB
    private DbwfTypeFacadeLocal dbwfTypeFacade; 
    
    @EJB
    private DbwfHeaderFacadeLocal dbwfHeaderFacade; 

    private List<DbwfHeader> listDbwfHeader;
    
    private List<DbwfType> listDbwfType;
    private List<DbwfType> existingwfType;
    
    
    private DbwfType wfTypeObject = new DbwfType();

    private boolean addFlag = false;
    private boolean editFlag = false;
    private boolean addButton = false;
    private boolean editButton = false;
    private boolean deleteButton = false;
    
    String prevwfTypeName;
    
    public workflowType_MB() {
    }

    public List<DbwfType> getListDbwfType() {
        return listDbwfType;
    }

    public void setListDbwfType(List<DbwfType> listDbwfType) {
        this.listDbwfType = listDbwfType;
    }

    public DbwfType getWfTypeObject() {
        return wfTypeObject;
    }

    public void setWfTypeObject(DbwfType wfTypeObject) {
        this.wfTypeObject = wfTypeObject;
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
        listDbwfType = dbwfTypeFacade.findAll();
    }

    public void addwfType() {
        existingwfType = dbwfTypeFacade.findByName("wfTypeId", wfTypeObject.getWfTypeId());
        if (!existingwfType.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The workflow type ID exists already."));
            return;
        }
        
        if (!dbwfHeaderFacade.wfTypesValidation(wfTypeObject.getWfTypeName())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning:", "Workflow type added, but there is no business logic associated with yet."));
        }
        dbwfTypeFacade.create(wfTypeObject);
        
        wfTypeObject = new DbwfType();
        init();

    }

    public void editwfType() {
        existingwfType = dbwfTypeFacade.findByName("wfTypeId", wfTypeObject.getWfTypeId());
        
        if (existingwfType.isEmpty() || existingwfType.get(0).getWfTypeName().equals(prevwfTypeName)) {
            dbwfTypeFacade.edit(wfTypeObject);
        } else { 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The workflow type ID exists already."));
            return;
        }
        wfTypeObject = new DbwfType();
        init();
        editFlag = false;
        addButton = false;
        deleteButton = false;
    }

    public void deletewfType(DbwfType wfTypeObject) {
        listDbwfHeader = dbwfTypeFacade.checkwfType(wfTypeObject.getWfTypeId());

        if (listDbwfHeader.isEmpty()) {
            dbwfTypeFacade.remove(wfTypeObject);
        } else {
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

    public void showEdit(DbwfType wfTypeObject) {
        editFlag = true;
        addButton = true;
        deleteButton = true;

        this.wfTypeObject = wfTypeObject;
        prevwfTypeName = wfTypeObject.getWfTypeName();
    }

    public void cancel() {
        addFlag = false;
        editFlag = false;

        addButton = false;
        editButton = false;
        deleteButton = false;

        wfTypeObject = new DbwfType();
    }
    
    public void error() {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The workflow type is currently assigned to one or more workflows."));
    }
    
}
