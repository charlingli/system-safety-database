/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbLocationFacadeLocal;
import ejb.DbProjectFacadeLocal;
import ejb.DbchangeTypeFacadeLocal;
import ejb.DbconstructionTypeFacadeLocal;
import ejb.DbgradeSeparationFacadeLocal;
import entities.DbHazard;
import entities.DbLocation;
import entities.DbProject;
import entities.DbchangeType;
import entities.DbconstructionType;
import entities.DbgradeSeparation;
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
@Named(value = "location_MB")
@ViewScoped
public class location_MB implements Serializable {

    @EJB
    private DbProjectFacadeLocal dbProjectFacade;

    @EJB
    private DbchangeTypeFacadeLocal dbchangeTypeFacade;

    @EJB
    private DbconstructionTypeFacadeLocal dbconstructionTypeFacade;

    @EJB
    private DbgradeSeparationFacadeLocal dbgradeSeparationFacade;

    @EJB
    private DbLocationFacadeLocal dbLocationFacade;

    private List<DbLocation> listDbLocation;
    private List<DbconstructionType> listDbConstructionType;
    private List<DbgradeSeparation> listDbGradeSeparation;
    private List<DbchangeType> listDbChangeType;
    private List<DbProject> listDbProject;
    private List<DbHazard> listDbHazard;
    private List<DbLocation> existingAbbrev; 
    private List<DbLocation> existingLocationName; 
    private List<DbLocation> filteredLocations;
    
    private DbLocation locationObject = new DbLocation();
    private DbconstructionType constructionTypeObject = new DbconstructionType();
    private DbgradeSeparation gradeSeparationObject = new DbgradeSeparation();
    private DbchangeType changeTypeObject = new DbchangeType();
    private DbProject projectObject = new DbProject();

    private int constructionTypeId;
    private int gradeSeparationId;
    private int changeTypeId;
    private int projectId;

    private boolean addFlag = false;
    private boolean editFlag = false;
    
    String prevLocationName; 
    
    public location_MB() {
    }

    public List<DbLocation> getListDbLocation() {
        return listDbLocation;
    }

    public void setListDbLocation(List<DbLocation> listDbLocation) {
        this.listDbLocation = listDbLocation;
    }

    public DbLocation getLocationObject() {
        return locationObject;
    }

    public void setLocationObject(DbLocation locationObject) {
        this.locationObject = locationObject;
    }

    public List<DbconstructionType> getListDbConstructionType() {
        return listDbConstructionType;
    }

    public void setListDbConstructionType(List<DbconstructionType> listDbConstructionType) {
        this.listDbConstructionType = listDbConstructionType;
    }

    public List<DbgradeSeparation> getListDbGradeSeparation() {
        return listDbGradeSeparation;
    }

    public void setListDbGradeSeparation(List<DbgradeSeparation> listDbGradeSeparation) {
        this.listDbGradeSeparation = listDbGradeSeparation;
    }

    public List<DbchangeType> getListDbChangeType() {
        return listDbChangeType;
    }

    public void setListDbChangeType(List<DbchangeType> listDbChangeType) {
        this.listDbChangeType = listDbChangeType;
    }

    public int getConstructionTypeId() {
        return constructionTypeId;
    }

    public void setConstructionTypeId(int constructionTypeId) {
        this.constructionTypeId = constructionTypeId;
    }

    public int getGradeSeparationId() {
        return gradeSeparationId;
    }

    public void setGradeSeparationId(int gradeSeparationId) {
        this.gradeSeparationId = gradeSeparationId;
    }

    public int getChangeTypeId() {
        return changeTypeId;
    }

    public void setChangeTypeId(int changeTypeId) {
        this.changeTypeId = changeTypeId;
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

    public List<DbProject> getListDbProject() {
        return listDbProject;
    }

    public void setListDbProject(List<DbProject> listDbProject) {
        this.listDbProject = listDbProject;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public List<DbLocation> getFilteredLocations() {
        return filteredLocations;
    }

    public void setFilteredLocations(List<DbLocation> filteredLocations) {
        this.filteredLocations = filteredLocations;
    }

    @PostConstruct
    public void init() {
        listDbLocation = dbLocationFacade.findAll();
        listDbConstructionType = dbconstructionTypeFacade.findAll();
        listDbGradeSeparation = dbgradeSeparationFacade.findAll();
        listDbChangeType = dbchangeTypeFacade.findAll();
        listDbProject = dbProjectFacade.findAll();
    }

    public void addLocation() {
        fillLocationObject();
        existingAbbrev = dbLocationFacade.checkLocationAbbrev(locationObject.getLocationAbbrev()); 
        existingLocationName = dbLocationFacade.findByName("locationName", locationObject.getLocationName());
        
        if (existingAbbrev.isEmpty()) {
            if (existingLocationName.isEmpty()) {
                dbLocationFacade.create(locationObject);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The location has been successfully added."));
            }
            else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The location name already exists!"));
                return; 
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The location abbreviation is already in use!"));
            return;
        }
        
        locationObject = new DbLocation();
        init();
        clearLocationObject();
    }

    public void editLocation() {
        fillLocationObject();
        existingLocationName = dbLocationFacade.findByName("locationName", locationObject.getLocationName());
        
        if (existingLocationName.isEmpty() || existingLocationName.get(0).getLocationName().equals(prevLocationName)) {
            dbLocationFacade.edit(locationObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The location has been successfully edited."));
        } else { 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The location name already exists."));
            return; 
        }
        
        listDbLocation = dbLocationFacade.findAll(); 
        init();

        editFlag = false;
    }

    public void deleteLocation(DbLocation locationObject) {
        listDbHazard = dbLocationFacade.checkLocation(locationObject.getLocationId());

        if (listDbHazard.isEmpty()) {
            dbLocationFacade.remove(locationObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The location has been successfully removed."));
        } else {
            error();
            return;
        }
        init();
    }

    public void showAdd() {
        addFlag = true;
        clearLocationObject();
        
    }

    public void showEdit(DbLocation locationObject) {
        editFlag = true;

        this.locationObject = locationObject;
        prevLocationName = locationObject.getLocationName(); 
        
        constructionTypeId = locationObject.getLocationConstructionType().getConstructionTypeId();
        gradeSeparationId = locationObject.getLocationGradeSeparation().getGradeSeparationId();
        changeTypeId = locationObject.getLocationChangeType().getChangeTypeId();
        projectId = locationObject.getProjectId().getProjectId();

    }

    public void cancel() {
        addFlag = false;
        editFlag = false;
        
        locationObject = new DbLocation();
    }

    public void fillLocationObject() {
        constructionTypeObject.setConstructionTypeId(constructionTypeId);
        gradeSeparationObject.setGradeSeparationId(gradeSeparationId);
        changeTypeObject.setChangeTypeId(changeTypeId);
        projectObject.setProjectId(projectId);

        locationObject.setLocationConstructionType(constructionTypeObject);
        locationObject.setLocationGradeSeparation(gradeSeparationObject);
        locationObject.setLocationChangeType(changeTypeObject);
        locationObject.setProjectId(projectObject);
    }
    
    public void clearLocationObject() {
        constructionTypeId = -1;
        gradeSeparationId = -1;
        changeTypeId = -1;
        projectId = -1;
    }

    public void error() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The location is currently assigned to one or more hazards."));

    }
}
