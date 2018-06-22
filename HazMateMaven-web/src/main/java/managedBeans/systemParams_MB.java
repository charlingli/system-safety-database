/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbsystemParametersFacadeLocal;
import entities.DbsystemParameters;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Juan David
 */
@Named(value = "systemParams_MB")
@ViewScoped
public class systemParams_MB implements Serializable {

    @EJB
    private DbsystemParametersFacadeLocal dbsystemParametersFacade;

    private String licenseText;
    private String managerEmail;
    private boolean infoBox;
    private boolean editBox;
    private DbsystemParameters systemParamObj;

    public systemParams_MB() {
    }

    public String getLicenseText() {
        return licenseText;
    }

    public void setLicenseText(String licenseText) {
        this.licenseText = licenseText;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public boolean isInfoBox() {
        return infoBox;
    }

    public void setInfoBox(boolean infoBox) {
        this.infoBox = infoBox;
    }

    public boolean isEditBox() {
        return editBox;
    }

    public void setEditBox(boolean editBox) {
        this.editBox = editBox;
    }

    public DbsystemParameters getSystemParamObj() {
        return systemParamObj;
    }

    public void setSystemParamObj(DbsystemParameters systemParamObj) {
        this.systemParamObj = systemParamObj;
    }

    @PostConstruct
    public void init() {
        infoBox = true;
        editBox = false;
        licenseText = dbsystemParametersFacade.find(1).getSystemLicense();
        managerEmail = dbsystemParametersFacade.find(1).getSystemAdminEmail();
        setSystemParamObj(dbsystemParametersFacade.find(1));
    }
    
    public void editParameters(){
        infoBox = false;
        editBox = true;
    }
    
    public void saveParameters(){
        dbsystemParametersFacade.edit(systemParamObj);
        init();
    }
    
    public void cancel(){
        licenseText = "";
        managerEmail = "";
        systemParamObj = new DbsystemParameters();
        init();
    }
}
