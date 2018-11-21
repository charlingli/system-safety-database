/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbconstructionTypeFacadeLocal;
import entities.DbLocation;
import entities.DbconstructionType;
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
@Named(value = "constructionType_MB")
@ViewScoped
public class constructionType_MB implements Serializable {

    @EJB
    private DbconstructionTypeFacadeLocal dbconstructionTypeFacade;

    private List<DbconstructionType> listDbConstructionType;
    private List<DbLocation> listDbLocation; 
    private List<DbconstructionType> existingConstructType; 
    
    private DbconstructionType constructionTypeObject = new DbconstructionType();

    private boolean addFlag = false;
    private boolean editFlag = false;
    private boolean addButton = false;
    private boolean editButton = false;
    private boolean deleteButton = false;
    
    String prevConstructTypeName; 
    
    public constructionType_MB() {
    }

    public List<DbconstructionType> getListDbConstructionType() {
        return listDbConstructionType;
    }

    public void setListDbConstructionType(List<DbconstructionType> listDbConstructionType) {
        this.listDbConstructionType = listDbConstructionType;
    }

    public DbconstructionType getConstructionTypeObject() {
        return constructionTypeObject;
    }

    public void setConstructionTypeObject(DbconstructionType constructionTypeObject) {
        this.constructionTypeObject = constructionTypeObject;
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
        listDbConstructionType = dbconstructionTypeFacade.findAll();
        
    }

     public void addConstructionType() {
        existingConstructType = dbconstructionTypeFacade.findByName("constructionTypeName", constructionTypeObject.getConstructionTypeName());
        
        if (existingConstructType.isEmpty()) {
            dbconstructionTypeFacade.create(constructionTypeObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The construction type has been successfully added."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The construction type name already exists!"));
            return;

        }
        
        constructionTypeObject = new DbconstructionType();
        init();
        
    }
    
    public void editConstructionType() { 
        existingConstructType = dbconstructionTypeFacade.findByName("constructionTypeName", constructionTypeObject.getConstructionTypeName());
        
        if (existingConstructType.isEmpty() || existingConstructType.get(0).getConstructionTypeName().equals(prevConstructTypeName)) {
            dbconstructionTypeFacade.edit(constructionTypeObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The construction type has been successfully edited."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The construction type name already exists!"));
            return;
        }
        constructionTypeObject = new DbconstructionType(); 
        init(); 
        editFlag = false;
        addButton = false;
        deleteButton = false; 
    }
    
    
    public void deleteConstructionType(DbconstructionType constructionTypeObject){
        listDbLocation = dbconstructionTypeFacade.checkConstructionType(constructionTypeObject.getConstructionTypeId());
        
        if (listDbLocation.isEmpty())   {
            dbconstructionTypeFacade.remove(constructionTypeObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The construction type has been successfully removed."));
        }
        else {
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
    
    public void showEdit(DbconstructionType constructionTypeObject){ 
        editFlag = true; 
        addButton = true; 
        deleteButton = true; 
                
        this.constructionTypeObject = constructionTypeObject; 
        prevConstructTypeName = constructionTypeObject.getConstructionTypeName();
    }
    
    public void cancel(){
        addFlag = false; 
        editFlag = false;
        
        addButton = false; 
        editButton = false; 
        deleteButton = false; 
        
        constructionTypeObject = new DbconstructionType(); 
    }
    
    public void error() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The construction type is currently assigned to one or more locations."));
    }
}
