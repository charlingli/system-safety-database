/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbriskSeverityFacadeLocal;
import entities.DbHazard;
import entities.DbriskSeverity;
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
@Named(value = "riskSeverity_MB")
@ViewScoped
public class riskSeverity_MB implements Serializable {

    @EJB
    private DbriskSeverityFacadeLocal dbriskSeverityFacade;

    private List<DbriskSeverity> listDbRiskSeverity;
    private List<DbHazard> listDbHazard;
    private List<DbriskSeverity> existingSeverity; 
    
    private DbriskSeverity riskSeverityObject = new DbriskSeverity();

    private boolean addFlag = false;
    private boolean editFlag = false;
    private boolean addButton = false;
    private boolean editButton = false;
    private boolean deleteButton = false;
    
    String prevSeverityScore; 
    
    public riskSeverity_MB() {
    }

    public List<DbriskSeverity> getListDbRiskSeverity() {
        return listDbRiskSeverity;
    }

    public void setListDbRiskSeverity(List<DbriskSeverity> listDbRiskSeverity) {
        this.listDbRiskSeverity = listDbRiskSeverity;
    }

    public DbriskSeverity getRiskSeverityObject() {
        return riskSeverityObject;
    }

    public void setRiskSeverityObject(DbriskSeverity riskSeverityObject) {
        this.riskSeverityObject = riskSeverityObject;
    }

    public boolean isAddFlag() {
        return addFlag;
    }

    public void setAddFlag(boolean addFlag) {
        this.addFlag = addFlag;
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

    public boolean isEditFlag() {
        return editFlag;
    }

    public void setEditFlag(boolean editFlag) {
        this.editFlag = editFlag;
    }

    @PostConstruct
    public void init() {
        listDbRiskSeverity = dbriskSeverityFacade.findAll();
    }

    public void addRiskSeverity() {
        existingSeverity = dbriskSeverityFacade.findByName("severityScore", riskSeverityObject.getSeverityScore());
        
        if (existingSeverity.isEmpty()) {
            dbriskSeverityFacade.create(riskSeverityObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info:", "The risk severity has been successfully added."));
            if (riskSeverityObject.getSeverityValue() > 5 || riskSeverityObject.getSeverityValue() < 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning:", "The severity value provided cannot be mapped to the risk matrix and will not be used to calculate a risk score."));
            }
        } else { 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The risk severity already exists!"));
            return;
        }

        riskSeverityObject = new DbriskSeverity();
        init();

    }

    public void editRiskSeverity() {
        existingSeverity = dbriskSeverityFacade.findByName("severityScore", riskSeverityObject.getSeverityScore());
        
        if (existingSeverity.isEmpty() || existingSeverity.get(0).getSeverityScore().equals(prevSeverityScore)) {
            dbriskSeverityFacade.edit(riskSeverityObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The risk severity has been successfully edited."));
        } else { 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The risk severity already exists!"));
            return;
        }
        riskSeverityObject = new DbriskSeverity();
        init();
        editFlag = false;
        addButton = false;
        deleteButton = false;
    }

    public void deleteRiskSeverity(DbriskSeverity riskSeverityObject) {
        listDbHazard = dbriskSeverityFacade.checkRiskSeverity(riskSeverityObject.getRiskSeverityId());

        if (listDbHazard.isEmpty()) {
            dbriskSeverityFacade.remove(riskSeverityObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The risk severity has been successfully deleted."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The risk severity is currently assigned to one or more hazards."));
            return;
        }
        init();
    }

    public void showAdd() {
        addFlag = true;
        addButton = true;
        editButton = true;
    }

    public void showEdit(DbriskSeverity riskSeverityObject) {
        editFlag = true;
        addButton = true;
        deleteButton = true;

        this.riskSeverityObject = riskSeverityObject;
        prevSeverityScore = riskSeverityObject.getSeverityScore(); 
    }

    public void cancel() {
        addFlag = false;
        editFlag = false;

        addButton = false;
        editButton = false;
        deleteButton = false;

        riskSeverityObject = new DbriskSeverity();
    }
}
