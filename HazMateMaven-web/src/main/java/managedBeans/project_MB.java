/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbProjectFacadeLocal;
import entities.DbLocation;
import entities.DbProject;
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
@Named(value = "project_MB")
@ViewScoped
public class project_MB implements Serializable {

    @EJB
    private DbProjectFacadeLocal dbProjectFacade;

    private List<DbProject> listDbProject; 
    private List<DbLocation> listDbLocation; 
    private List<DbProject> existingAbbrev; 
    private List<DbProject> existingProjectName;  
            
    private DbProject projectObject = new DbProject(); 
    
    private boolean addFlag = false;  
    private boolean editFlag = false; 
    private boolean addButton = false;
    private boolean editButton = false; 
    private boolean deleteButton = false;  
    
    String prevProjectName; 
    
    public project_MB() {
    }

    public List<DbProject> getListDbProject() {
        return listDbProject;
    }

    public void setListDbProject(List<DbProject> listDbProject) {
        this.listDbProject = listDbProject;
    }

    public DbProject getProjectObject() {
        return projectObject;
    }

    public void setProjectObject(DbProject projectObject) {
        this.projectObject = projectObject;
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
    public void init()  {
        listDbProject = dbProjectFacade.findAll(); 
        
    }
    
    public void addProject() {
        existingProjectName = dbProjectFacade.findByName("projectName", projectObject.getProjectName());
        existingAbbrev = dbProjectFacade.checkProjectAbbrev(projectObject.getProjectAbbrev()); 
        
        if (existingAbbrev.isEmpty()) {
            if (existingProjectName.isEmpty()) {
                dbProjectFacade.create(projectObject);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The project has been successfully added."));
            } else { 
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The project name already exists!"));
                return;
            }
        } else { 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The project abbreviation is already in use."));
            return; 
        }
        
        projectObject = new DbProject();
        init();
        
    }
    
    public void editProject() { 
        existingProjectName = dbProjectFacade.findByName("projectName", projectObject.getProjectName());
        
        if (existingProjectName.isEmpty() || existingProjectName.get(0).getProjectName().equals(prevProjectName)) {
            dbProjectFacade.edit(projectObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The project has been successfully edited."));
        } else { 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The project name already exists!"));
            return;
        }
        projectObject = new DbProject(); 
        init(); 

        editFlag = false;
        addButton = false;
        deleteButton = false; 
    }
    
    
    public void deleteProject(DbProject projectObject){
        listDbLocation = dbProjectFacade.checkProject(projectObject.getProjectId());
        
        if (listDbLocation.isEmpty()) {
            dbProjectFacade.remove(projectObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The project has been successfully deleted."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The project is currently assigned to one or more locations."));
            return; 
        }
        init(); 
        addButton = false; 
        editButton = false; 
        deleteButton = false; 
    }
    
    public void showAdd(){
        addFlag = true; 
        addButton = true;
        editButton = true; 
    }
    
    public void showEdit(DbProject projectObject){ 
        editFlag = true; 
        addButton = true; 
        deleteButton = true; 
                
        this.projectObject = projectObject; 
        prevProjectName = projectObject.getProjectName(); 
    }
    
    public void cancel(){
        addFlag = false; 
        editFlag = false;
        
        addButton = false; 
        editButton = false; 
        deleteButton = false; 
        
        projectObject = new DbProject(); 
    }
    
}
