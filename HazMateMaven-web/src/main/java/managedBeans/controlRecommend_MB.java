/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbcontrolRecommendFacadeLocal;
import entities.DbControlHazard;
import entities.DbcontrolRecommend;
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
@Named(value = "controlRecommend_MB")
@ViewScoped
public class controlRecommend_MB implements Serializable {

    @EJB
    private DbcontrolRecommendFacadeLocal dbcontrolRecommendFacade;

    private List<DbcontrolRecommend> listDbControlRecommend;
    private List<DbControlHazard> listDbControlHazard;
    private List<DbcontrolRecommend> existingRecommend; 
    
    private DbcontrolRecommend controlRecommendObject = new DbcontrolRecommend();

    private boolean addFlag = false;
    private boolean editFlag = false;
    private boolean addButton = false;
    private boolean editButton = false;
    private boolean deleteButton = false;
    
    String strJustifyRequired;
    String prevRecommendName; 
    
    public controlRecommend_MB() {
    }

    public List<DbcontrolRecommend> getListDbControlRecommend() {
        return listDbControlRecommend;
    }

    public void setListDbControlRecommend(List<DbcontrolRecommend> listDbControlRecommend) {
        this.listDbControlRecommend = listDbControlRecommend;
    }

    public DbcontrolRecommend getControlRecommendObject() {
        return controlRecommendObject;
    }

    public void setControlRecommendObject(DbcontrolRecommend controlRecommendObject) {
        this.controlRecommendObject = controlRecommendObject;
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
        listDbControlRecommend = dbcontrolRecommendFacade.findAll();
    }

    public void addControlRecommend() {
        existingRecommend = dbcontrolRecommendFacade.findByName("controlRecommendName", controlRecommendObject.getControlRecommendName());
        
        if (existingRecommend.isEmpty()) {
            dbcontrolRecommendFacade.create(controlRecommendObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The control recommendation has been successfully added."));
        } else { 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The control recommendation name already exists!"));
            return;
        }

        controlRecommendObject = new DbcontrolRecommend();
        init();

    }

    public void editControlRecommend() {
        existingRecommend = dbcontrolRecommendFacade.findByName("controlRecommendName", controlRecommendObject.getControlRecommendName());
        
        if (existingRecommend.isEmpty() || existingRecommend.get(0).getControlRecommendName().equals(prevRecommendName)) {
            dbcontrolRecommendFacade.edit(controlRecommendObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The control recommendation has been successfully edited."));
        } else { 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The control recommendation name already exists!"));
            return;
        }
        controlRecommendObject = new DbcontrolRecommend();
        init();
        editFlag = false;
        addButton = false;
        deleteButton = false;
    }

    public void deleteControlRecommend(DbcontrolRecommend controlRecommendObject) {
        listDbControlHazard = dbcontrolRecommendFacade.checkControlRecommend(controlRecommendObject.getControlRecommendId());

        if (listDbControlHazard.isEmpty()) {
            dbcontrolRecommendFacade.remove(controlRecommendObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The control recommendation has been successfully deleted."));
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

    public void showEdit(DbcontrolRecommend controlRecommendObject) {
        editFlag = true;
        addButton = true;
        deleteButton = true;

        this.controlRecommendObject = controlRecommendObject;
        prevRecommendName = controlRecommendObject.getControlRecommendName(); 
    }

    public void cancel() {
        addFlag = false;
        editFlag = false;

        addButton = false;
        editButton = false;
        deleteButton = false;

        controlRecommendObject = new DbcontrolRecommend();
    }

    public void error() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The control recommendation is currently assigned to one or more controls."));

    }
    
     public String printJustifyRequired(String justifyRequired ){
       
        switch (justifyRequired) {
            case "Y": strJustifyRequired = "Yes";
            break;
            case "N": strJustifyRequired = "No";
            break;
            default: strJustifyRequired = "NULL";
            break;
                   
        }
        return strJustifyRequired;
    }
}
