/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbgradeSeparationFacadeLocal;
import entities.DbgradeSeparation;
import entities.DbLocation;
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
@Named(value = "gradeSeparation_MB")
@ViewScoped
public class gradeSeparation_MB implements Serializable {

    @EJB
    private DbgradeSeparationFacadeLocal dbgradeSeparationFacade;

    private List<DbgradeSeparation> listDbGradeSeparation;
    private List<DbLocation> listDbLocation;
    private List<DbgradeSeparation> existingGradeSeparation; 
    
    private DbgradeSeparation gradeSeparationObject = new DbgradeSeparation();

    private boolean addFlag = false;
    private boolean editFlag = false;
    private boolean addButton = false;
    private boolean editButton = false;
    private boolean deleteButton = false;
    
    String prevSeparationName; 
    
    public gradeSeparation_MB() {
    }

    public List<DbgradeSeparation> getListDbGradeSeparation() {
        return listDbGradeSeparation;
    }

    public void setListDbGradeSeparation(List<DbgradeSeparation> listDbGradeSeparation) {
        this.listDbGradeSeparation = listDbGradeSeparation;
    }

    public DbgradeSeparation getGradeSeparationObject() {
        return gradeSeparationObject;
    }

    public void setGradeSeparationObject(DbgradeSeparation gradeSeparationObject) {
        this.gradeSeparationObject = gradeSeparationObject;
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
        listDbGradeSeparation = dbgradeSeparationFacade.findAll();

    }

    public void addGradeSeparation() {
        existingGradeSeparation = dbgradeSeparationFacade.findByName("gradeSeparationName", gradeSeparationObject.getGradeSeparationName());
        
        if (existingGradeSeparation.isEmpty()) {
            dbgradeSeparationFacade.create(gradeSeparationObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The grade separation has been successfully added."));
        } else { 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The grade separation name already exists!"));
            return;
        }

        gradeSeparationObject = new DbgradeSeparation();
        init();

    }

    public void editGradeSeparation() {
        existingGradeSeparation = dbgradeSeparationFacade.findByName("gradeSeparationName", gradeSeparationObject.getGradeSeparationName());

        if (existingGradeSeparation.isEmpty() || existingGradeSeparation.get(0).getGradeSeparationName().equals(prevSeparationName)) {
            dbgradeSeparationFacade.edit(gradeSeparationObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The grade separation has been successfully edited."));
        } else { 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The grade separation name already exists!"));
            return;
        }
        gradeSeparationObject = new DbgradeSeparation();
        init();
        editFlag = false;
        addButton = false;
        deleteButton = false;
    }

    public void deleteGradeSeparation(DbgradeSeparation gradeSeparationObject) {
        listDbLocation = dbgradeSeparationFacade.checkGradeSeparation(gradeSeparationObject.getGradeSeparationId());

        if (listDbLocation.isEmpty()) {
            dbgradeSeparationFacade.remove(gradeSeparationObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The grade separation has been successfully deleted."));
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

    public void showEdit(DbgradeSeparation gradeSeparationObject) {
        editFlag = true;
        addButton = true;
        deleteButton = true;

        this.gradeSeparationObject = gradeSeparationObject;
        prevSeparationName = gradeSeparationObject.getGradeSeparationName(); 
    }

    public void cancel() {
        addFlag = false;
        editFlag = false;

        addButton = false;
        editButton = false;
        deleteButton = false;

        gradeSeparationObject = new DbgradeSeparation();
    }
    
    public void error() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The grade separation is currently assigned to one or more locations."));
    }
}
