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
    private String excelPassword;
    private String excelNumberOfRows;
    private String thresholdSimilarity;
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

    public String getExcelPassword() {
        return excelPassword;
    }

    public void setExcelPassword(String excelPassword) {
        this.excelPassword = excelPassword;
    }

    public String getExcelNumberOfRows() {
        return excelNumberOfRows;
    }

    public void setExcelNumberOfRows(String excelNumberOfRows) {
        this.excelNumberOfRows = excelNumberOfRows;
    }

    public String getThresholdSimilarity() {
        return thresholdSimilarity;
    }

    public void setThresholdSimilarity(String thresholdSimilarity) {
        this.thresholdSimilarity = thresholdSimilarity;
    }

    @PostConstruct
    public void init() {
        infoBox = true;
        editBox = false;
        licenseText = dbsystemParametersFacade.find(1).getSystemLicense();
        managerEmail = dbsystemParametersFacade.find(1).getSystemAdminEmail();
        excelPassword = dbsystemParametersFacade.find(1).getExcelLayoutPassword();
        excelNumberOfRows = Integer.toString(dbsystemParametersFacade.find(1).getExcelLayoutRows());
        thresholdSimilarity = Integer.toString(dbsystemParametersFacade.find(1).getSimilarityThreshold());
        setSystemParamObj(dbsystemParametersFacade.find(1));
    }

    public void editParameters() {
        infoBox = false;
        editBox = true;
    }

    public void saveParameters() {
        systemParamObj.setExcelLayoutRows(Integer.parseInt(excelNumberOfRows));
        systemParamObj.setSimilarityThreshold(Integer.parseInt(thresholdSimilarity));
        dbsystemParametersFacade.edit(systemParamObj);
        init();
    }

    public void cancel() {
        licenseText = "";
        managerEmail = "";
        excelPassword = "";
        excelNumberOfRows = "";
        thresholdSimilarity = "";
        systemParamObj = new DbsystemParameters();
        init();
    }
}
