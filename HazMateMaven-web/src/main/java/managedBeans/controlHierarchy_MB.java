/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbcontrolHierarchyFacadeLocal;
import entities.DbControl;
import entities.DbcontrolHierarchy;
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
@Named(value = "controlHierarchy_MB")
@ViewScoped
public class controlHierarchy_MB implements Serializable {

    @EJB
    private DbcontrolHierarchyFacadeLocal dbcontrolHierarchyFacade;

    private List<DbcontrolHierarchy> listDbControlHierarchy;
    private List<DbControl> listDbControl;
    private List<DbcontrolHierarchy> existingHierarchy;
    
    private DbcontrolHierarchy controlHierarchyObject = new DbcontrolHierarchy();

    private boolean addFlag = false;
    private boolean editFlag = false;
    private boolean addButton = false;
    private boolean editButton = false;
    private boolean deleteButton = false;
    
    String prevHierarchyName; 
    
    public controlHierarchy_MB() {
    }

    public List<DbcontrolHierarchy> getListDbControlHierarchy() {
        return listDbControlHierarchy;
    }

    public void setListDbControlHierarchy(List<DbcontrolHierarchy> listDbControlHierarchy) {
        this.listDbControlHierarchy = listDbControlHierarchy;
    }

    public DbcontrolHierarchy getControlHierarchyObject() {
        return controlHierarchyObject;
    }

    public void setControlHierarchyObject(DbcontrolHierarchy controlHierarchyObject) {
        this.controlHierarchyObject = controlHierarchyObject;
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
        listDbControlHierarchy = dbcontrolHierarchyFacade.findAll();
    }

    public void addControlHierarchy() {
        existingHierarchy = dbcontrolHierarchyFacade.findByName("controlHierarchyName", controlHierarchyObject.getControlHierarchyName()); 
        
        if (existingHierarchy.isEmpty()) {
            dbcontrolHierarchyFacade.create(controlHierarchyObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The control hierarchy has been successfully added."));
        } else { 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The control hierarchy name already exists!"));
            return; 
        }
            
        controlHierarchyObject = new DbcontrolHierarchy();
        init();

    }

    public void editControlHierarchy() {
        existingHierarchy = dbcontrolHierarchyFacade.findByName("controlHierarchyName", controlHierarchyObject.getControlHierarchyName()); 
        
        if (existingHierarchy.isEmpty() || existingHierarchy.get(0).getControlHierarchyName().equals(prevHierarchyName)) {
            dbcontrolHierarchyFacade.edit(controlHierarchyObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The control hierarchy has been successfully edited."));
        } else { 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The control hierarchy name already exists!"));
            return; 
        }
        controlHierarchyObject = new DbcontrolHierarchy();
        init();
        editFlag = false;
        addButton = false;
        deleteButton = false;
    }

    public void deleteControlHierarchy(DbcontrolHierarchy controlHierarchyObject) {
        listDbControl = dbcontrolHierarchyFacade.checkControlHierarchy(controlHierarchyObject.getControlHierarchyId());

        if (listDbControl.isEmpty()) {
            dbcontrolHierarchyFacade.remove(controlHierarchyObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The control hierarchy has been successfully deleted."));
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

    public void showEdit(DbcontrolHierarchy controlHierarchyObject) {
        editFlag = true;
        addButton = true;
        deleteButton = true;

        this.controlHierarchyObject = controlHierarchyObject;
        prevHierarchyName = controlHierarchyObject.getControlHierarchyName(); 
    }

    public void cancel() {
        addFlag = false;
        editFlag = false;

        addButton = false;
        editButton = false;
        deleteButton = false;

        controlHierarchyObject = new DbcontrolHierarchy();
    }

    public void error() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The control hierarchy is currently assigned to one or more controls."));

    }
}
