/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author David Ortega <david.ortega@levelcrossings.vic.gov.au>
 */
@Entity
@Table(name = "db_systemParameters")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbsystemParameters.findAll", query = "SELECT d FROM DbsystemParameters d")
    , @NamedQuery(name = "DbsystemParameters.findBySystemParameterId", query = "SELECT d FROM DbsystemParameters d WHERE d.systemParameterId = :systemParameterId")
    , @NamedQuery(name = "DbsystemParameters.findBySystemAdminEmail", query = "SELECT d FROM DbsystemParameters d WHERE d.systemAdminEmail = :systemAdminEmail")
    , @NamedQuery(name = "DbsystemParameters.findBySimilarityThreshold", query = "SELECT d FROM DbsystemParameters d WHERE d.similarityThreshold = :similarityThreshold")
    , @NamedQuery(name = "DbsystemParameters.findByExcelLayoutPassword", query = "SELECT d FROM DbsystemParameters d WHERE d.excelLayoutPassword = :excelLayoutPassword")
    , @NamedQuery(name = "DbsystemParameters.findByExcelLayoutRows", query = "SELECT d FROM DbsystemParameters d WHERE d.excelLayoutRows = :excelLayoutRows")})
public class DbsystemParameters implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "systemParameterId")
    private Integer systemParameterId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "systemAdminEmail")
    private String systemAdminEmail;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "systemLicense")
    private String systemLicense;
    @Basic(optional = false)
    @NotNull
    @Column(name = "similarityThreshold")
    private int similarityThreshold;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "excelLayoutPassword")
    private String excelLayoutPassword;
    @Basic(optional = false)
    @NotNull
    @Column(name = "excelLayoutRows")
    private int excelLayoutRows;

    public DbsystemParameters() {
    }

    public DbsystemParameters(Integer systemParameterId) {
        this.systemParameterId = systemParameterId;
    }

    public DbsystemParameters(Integer systemParameterId, String systemAdminEmail, String systemLicense, int similarityThreshold, String excelLayoutPassword, int excelLayoutRows) {
        this.systemParameterId = systemParameterId;
        this.systemAdminEmail = systemAdminEmail;
        this.systemLicense = systemLicense;
        this.similarityThreshold = similarityThreshold;
        this.excelLayoutPassword = excelLayoutPassword;
        this.excelLayoutRows = excelLayoutRows;
    }

    public Integer getSystemParameterId() {
        return systemParameterId;
    }

    public void setSystemParameterId(Integer systemParameterId) {
        this.systemParameterId = systemParameterId;
    }

    public String getSystemAdminEmail() {
        return systemAdminEmail;
    }

    public void setSystemAdminEmail(String systemAdminEmail) {
        this.systemAdminEmail = systemAdminEmail;
    }

    public String getSystemLicense() {
        return systemLicense;
    }

    public void setSystemLicense(String systemLicense) {
        this.systemLicense = systemLicense;
    }

    public int getSimilarityThreshold() {
        return similarityThreshold;
    }

    public void setSimilarityThreshold(int similarityThreshold) {
        this.similarityThreshold = similarityThreshold;
    }

    public String getExcelLayoutPassword() {
        return excelLayoutPassword;
    }

    public void setExcelLayoutPassword(String excelLayoutPassword) {
        this.excelLayoutPassword = excelLayoutPassword;
    }

    public int getExcelLayoutRows() {
        return excelLayoutRows;
    }

    public void setExcelLayoutRows(int excelLayoutRows) {
        this.excelLayoutRows = excelLayoutRows;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (systemParameterId != null ? systemParameterId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbsystemParameters)) {
            return false;
        }
        DbsystemParameters other = (DbsystemParameters) object;
        if ((this.systemParameterId == null && other.systemParameterId != null) || (this.systemParameterId != null && !this.systemParameterId.equals(other.systemParameterId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbsystemParameters[ systemParameterId=" + systemParameterId + " ]";
    }
    
}
