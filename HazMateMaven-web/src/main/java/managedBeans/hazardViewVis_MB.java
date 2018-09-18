/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbHazardFacadeLocal;
import ejb.DbUserFacadeLocal;
import entities.DbControlHazard;
import entities.DbHazard;
import entities.DbHazardCause;
import entities.DbHazardConsequence;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author lxra
 */
@Named(value = "hazardViewVis_MB")
@RequestScoped
public class hazardViewVis_MB {
    @EJB
    private DbUserFacadeLocal dbUserFacade;
    
    @EJB
    private DbHazardFacadeLocal dbHazardFacade;
    
    private List<DbHazard> listDbHazard;
    private DbHazard selectedHazard;
    private String selectedDescription;
    private List<DbHazardCause> selectedCauses;
    private List<DbHazardConsequence> selectedConsequences;
    private List<DbControlHazard> selectedPControls;
    private List<DbControlHazard> selectedMControls;
    
    public DbHazard getSelectedHazard() {
        return selectedHazard;
    }

    public void setSelectedHazard(DbHazard selectedHazard) {
        this.selectedHazard = selectedHazard;
    }

    public String getSelectedDescription() {
        return selectedDescription;
    }

    public void setSelectedDescription(String selectedDescription) {
        this.selectedDescription = selectedDescription;
    }

    public List<DbHazardCause> getSelectedCauses() {
        return selectedCauses;
    }

    public void setSelectedCauses(List<DbHazardCause> selectedCauses) {
        this.selectedCauses = selectedCauses;
    }

    public List<DbHazardConsequence> getSelectedConsequences() {
        return selectedConsequences;
    }

    public void setSelectedConsequences(List<DbHazardConsequence> selectedConsequences) {
        this.selectedConsequences = selectedConsequences;
    }

    public List<DbControlHazard> getSelectedPControls() {
        return selectedPControls;
    }

    public void setSelectedPControls(List<DbControlHazard> selectedPControls) {
        this.selectedPControls = selectedPControls;
    }

    public List<DbControlHazard> getSelectedMControls() {
        return selectedMControls;
    }

    public void setSelectedMControls(List<DbControlHazard> selectedMControls) {
        this.selectedMControls = selectedMControls;
    }

    /**
     * Creates a new instance of hazardViewVis_MB
     */
    public hazardViewVis_MB() {
        
    }
    
    public void constructObject(String hazardId) {
        listDbHazard = dbHazardFacade.findByName("hazardId", hazardId);
        setSelectedHazard(listDbHazard.get(0));
        setSelectedDescription(getSelectedHazard().getHazardDescription());
        setSelectedCauses(dbHazardFacade.getHazardCause(selectedHazard.getHazardId()));
        setSelectedConsequences(dbHazardFacade.getHazardConsequence(selectedHazard.getHazardId()));
        setSelectedMControls(dbHazardFacade.getMControlHazard(selectedHazard.getHazardId()));
        setSelectedPControls(dbHazardFacade.getPControlHazard(selectedHazard.getHazardId()));
    }
    
    public void testPageAccess() {
        System.out.println(dbUserFacade.getPageAccessForUser(1, "Users Definition"));
    }
}
