/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbhazardContextFacadeLocal;
import entities.DbHazard;
import entities.DbhazardContext;
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
@Named(value = "hazardContext_MB")
@ViewScoped
public class hazardContext_MB implements Serializable{

    @EJB
    private DbhazardContextFacadeLocal dbhazardContextFacade;

    private List<DbhazardContext> listDbHazardContext; 
    private List<DbHazard> listDbHazard; 
    private List<DbhazardContext> existingContextName; 
    private List<DbhazardContext> filteredContexts; 
    
    private DbhazardContext hazardContextObject = new DbhazardContext(); 
    
    private boolean addFlag = false;  
    private boolean editFlag = false; 
    
    String prevContextName; 
    
    public hazardContext_MB() {
    }

    public List<DbhazardContext> getFilteredContexts() {
        return filteredContexts;
    }

    public void setFilteredContexts(List<DbhazardContext> filteredContexts) {
        this.filteredContexts = filteredContexts;
    }

    public List<DbhazardContext> getListDbHazardContext() {
        return listDbHazardContext;
    }

    public void setListDbHazardContext(List<DbhazardContext> listDbHazardContext) {
        this.listDbHazardContext = listDbHazardContext;
    }

    public DbhazardContext getHazardContextObject() {
        return hazardContextObject;
    }

    public void setHazardContextObject(DbhazardContext hazardContextObject) {
        this.hazardContextObject = hazardContextObject;
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
    
    @PostConstruct 
    public void init() { 
        listDbHazardContext = dbhazardContextFacade.findAll(); 
    }
    
    public void addHazardContext() {
        existingContextName = dbhazardContextFacade.findByName("hazardContextName", hazardContextObject.getHazardContextName());
        
        if (existingContextName.isEmpty()) {
            dbhazardContextFacade.create(hazardContextObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Added", "The context has been successfully added"));
        } else { 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The hazard context name already exists"));
            return;
        }
        hazardContextObject = new DbhazardContext();
        init();
        addFlag = false; 
        
    }
    
    public void editHazardContext() { 
        existingContextName = dbhazardContextFacade.findByName("hazardContextName", hazardContextObject.getHazardContextName());
        
        if (existingContextName.isEmpty() || (existingContextName.get(0).getHazardContextName().equals(prevContextName))) {
            dbhazardContextFacade.edit(hazardContextObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Added", "The context has been successfully modified"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The hazard context name already exists"));
            return;
        }
        
        hazardContextObject = new DbhazardContext(); 
        init(); 
        editFlag = false;
         
    }
    
    
    public void deleteHazardContext(DbhazardContext hazardContextObject){
        listDbHazard = dbhazardContextFacade.checkHazardContext(hazardContextObject.getHazardContextId());
        
        if (listDbHazard.isEmpty()) {
        dbhazardContextFacade.remove(hazardContextObject);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Added", "The context has been successfully removed"));
        }
        else    {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The hazard context is currently assigned to one or more hazards.")); 
            return; 
        }
        init(); 
    }
    
    public void showAdd(){
        addFlag = true; 
         
    }
    
    public void showEdit(DbhazardContext hazardContextObject){ 
        editFlag = true; 
        
        this.hazardContextObject = hazardContextObject; 
        prevContextName = hazardContextObject.getHazardContextName(); 
    }
    
    public void cancel(){
        addFlag = false; 
        editFlag = false;
        
        hazardContextObject = new DbhazardContext(); 
    }
    
}
