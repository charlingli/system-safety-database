/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbControlFacadeLocal;
import ejb.DbHazardFacadeLocal;
import ejb.DbOwnersFacadeLocal;
import ejb.DbcontrolHierarchyFacadeLocal;
import entities.DbControl;
import entities.DbHazard;
import entities.DbOwners;
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
@Named(value = "control_MB")
@ViewScoped
public class control_MB implements Serializable {

    @EJB
    private DbHazardFacadeLocal dbHazardFacade;

    @EJB
    private DbOwnersFacadeLocal dbOwnersFacade;

    @EJB
    private DbcontrolHierarchyFacadeLocal dbcontrolHierarchyFacade;

    @EJB
    private DbControlFacadeLocal dbControlFacade;
    
    private List<DbControl> listDbControl;
    private List<DbcontrolHierarchy> listDbControlHierarchy;
    private List<DbOwners> listDbOwners;
    private List<DbControl> filteredControls;

    private DbControl controlObject = new DbControl();
    private DbcontrolHierarchy controlHierarchyObject = new DbcontrolHierarchy();
    private DbOwners ownersObject = new DbOwners();
    
    private DbControl selectedControl;
    private List<DbHazard> selectedHazards;

    private int controlHierarchyId;
    private int ownerId;

    private boolean addFlag = false;
    private boolean editFlag = false;
    private boolean addButton = false;
    private boolean editButton = false;
    private boolean deleteButton = false;

    public control_MB() {
    }

    public List<DbControl> getListDbControl() {
        return listDbControl;
    }

    public void setListDbControl(List<DbControl> listDbControl) {
        this.listDbControl = listDbControl;
    }

    public DbControl getControlObject() {
        return controlObject;
    }

    public void setControlObject(DbControl controlObject) {
        this.controlObject = controlObject;
    }

    public List<DbcontrolHierarchy> getListDbControlHierarchy() {
        return listDbControlHierarchy;
    }

    public void setListDbControlHierarchy(List<DbcontrolHierarchy> listDbControlHierarchy) {
        this.listDbControlHierarchy = listDbControlHierarchy;
    }

    public List<DbOwners> getListDbOwners() {
        return listDbOwners;
    }

    public void setListDbOwners(List<DbOwners> listDbOwners) {
        this.listDbOwners = listDbOwners;
    }

    public DbControl getSelectedControl() {
        return selectedControl;
    }

    public void setSelectedControl(DbControl selectedControl) {
        this.selectedControl = selectedControl;
    }

    public List<DbHazard> getSelectedHazards() {
        return selectedHazards;
    }

    public void setSelectedHazards(List<DbHazard> selectedHazards) {
        this.selectedHazards = selectedHazards;
    }

    public int getControlHierarchyId() {
        return controlHierarchyId;
    }

    public void setControlHierarchyId(int controlHierarchyId) {
        this.controlHierarchyId = controlHierarchyId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
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

    public List<DbControl> getFilteredControls() {
        return filteredControls;
    }

    public void setFilteredControls(List<DbControl> filteredControls) {
        this.filteredControls = filteredControls;
    }

    @PostConstruct
    public void init() {
        listDbControl = dbControlFacade.findAll();
        listDbControlHierarchy = dbcontrolHierarchyFacade.findAll();
        listDbOwners = dbOwnersFacade.findAll();
    }

    public void addControl() {
        try {
            fillControlObject();
            dbControlFacade.create(controlObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Added", "The control has been successfully added"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error:" + e.getMessage()));
        } finally {
            reinitialize();
            addFlag = false; 
        }
    }

    public void editControl() {
        try {
            fillControlObject();
            dbControlFacade.edit(controlObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modified", "The control has been successfully modified"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error:" + e.getMessage()));
        } finally {
            reinitialize();
            editFlag = false;
            addButton = false;
            deleteButton = false;
        }

    }

    public void deleteControl(DbControl controlObject) {
        try {
            dbControlFacade.remove(controlObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Removed", "The control has been successfully removed"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error:" + e.getMessage()));
        } finally {
            init();
        }
    }

    public void showAdd() {
        addFlag = true;
        addButton = true;
        editButton = true;
    }

    public void showEdit(DbControl controlObject) {
        editFlag = true;
        addButton = true;
        deleteButton = true;
        this.controlObject = controlObject;
        controlHierarchyId = controlObject.getControlHierarchyId().getControlHierarchyId();
        ownerId = controlObject.getOwnerId().getOwnerId();
    }

    public void cancel() {
        addFlag = false;
        editFlag = false;

        addButton = false;
        editButton = false;
        deleteButton = false;

        reinitialize();
    }

    public void fillControlObject() {
        controlHierarchyObject.setControlHierarchyId(controlHierarchyId);
        ownersObject.setOwnerId(ownerId);

        controlObject.setControlHierarchyId(controlHierarchyObject);
        controlObject.setOwnerId(ownersObject);
    }

    public void reinitialize() {
        controlObject = new DbControl();
        init();
        controlHierarchyId = -1;
        ownerId = -1;
    }
    
    public void showLinkedHazards(DbControl selectedControl) {
        setSelectedControl(selectedControl);
        setSelectedHazards(dbHazardFacade.getHazardsFromCause(selectedControl.getControlId()));
    }
}
