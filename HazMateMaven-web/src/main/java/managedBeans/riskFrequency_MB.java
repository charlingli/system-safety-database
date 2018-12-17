/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbriskFrequencyFacadeLocal;
import entities.DbHazard;
import entities.DbriskFrequency;
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
@Named(value = "riskFrequency_MB")
@ViewScoped
public class riskFrequency_MB implements Serializable {

    @EJB
    private DbriskFrequencyFacadeLocal dbriskFrequencyFacade;

    private List<DbriskFrequency> listDbRiskFrequency;
    private List<DbHazard> listDbHazard;
    private List<DbriskFrequency> existingRiskFreq; 
    
    private DbriskFrequency riskFrequencyObject = new DbriskFrequency();

    private boolean addFlag = false;
    private boolean editFlag = false;
    private boolean addButton = false;
    private boolean editButton = false;
    private boolean deleteButton = false;
    
    String prevFreqScore; 
    
    public riskFrequency_MB() {
    }

    public DbriskFrequency getRiskFrequencyObject() {
        return riskFrequencyObject;
    }

    public void setRiskFrequencyObject(DbriskFrequency riskFrequencyObject) {
        this.riskFrequencyObject = riskFrequencyObject;
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

    public List<DbriskFrequency> getListDbRiskFrequency() {
        return listDbRiskFrequency;
    }

    public void setListDbRiskFrequency(List<DbriskFrequency> listDbRiskFrequency) {
        this.listDbRiskFrequency = listDbRiskFrequency;
    }

    @PostConstruct
    public void init() {
        listDbRiskFrequency = dbriskFrequencyFacade.findAll();
//        System.out.println(listDbRiskFrequency.get(0).getFrequencyValue());
    }

    public void addRiskFrequency() {
        existingRiskFreq = dbriskFrequencyFacade.findByName("frequencyScore", riskFrequencyObject.getFrequencyScore());
        
        if (existingRiskFreq.isEmpty()) {
            dbriskFrequencyFacade.create(riskFrequencyObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The risk frequency has been successfully added."));
            if (riskFrequencyObject.getFrequencyValue()> 5 || riskFrequencyObject.getFrequencyValue() < 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning:", "The frequency value provided cannot be mapped to the risk matrix and will not be used to calculate a risk score."));
            }
        } else { 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The risk frequency already exists!"));
            return;
        }

        riskFrequencyObject = new DbriskFrequency();
        init();

    }

    public void editRiskFrequency() {
        existingRiskFreq = dbriskFrequencyFacade.findByName("frequencyScore", riskFrequencyObject.getFrequencyScore());
        
        if (existingRiskFreq.isEmpty() || existingRiskFreq.get(0).getFrequencyScore().equals(prevFreqScore)) {
            dbriskFrequencyFacade.edit(riskFrequencyObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The risk frequency has been successfully edited."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The risk frequency already exists!"));
            return;
        }
        riskFrequencyObject = new DbriskFrequency();
        init();
        editFlag = false;
        addButton = false;
        deleteButton = false;
    }

    public void deleteRiskFrequency(DbriskFrequency riskFrequencyObject) {
        listDbHazard = dbriskFrequencyFacade.checkRiskFrequency(riskFrequencyObject.getRiskFrequencyId());

        if (listDbHazard.isEmpty()) {
            dbriskFrequencyFacade.remove(riskFrequencyObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The risk frequency has been successfully deleted."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The risk frequency is currently assigned to one or more hazards."));
            return;
        }
        init();
    }

    public void showAdd() {
        addFlag = true;
        addButton = true;
        editButton = true;
    }

    public void showEdit(DbriskFrequency riskFrequencyObject) {
        editFlag = true;
        addButton = true;
        deleteButton = true;

        this.riskFrequencyObject = riskFrequencyObject;
        prevFreqScore = riskFrequencyObject.getFrequencyScore(); 
    }

    public void cancel() {
        addFlag = false;
        editFlag = false;

        addButton = false;
        editButton = false;
        deleteButton = false;

        riskFrequencyObject = new DbriskFrequency();
    }
}
