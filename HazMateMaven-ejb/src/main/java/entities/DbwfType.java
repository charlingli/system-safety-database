/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lxra
 */
@Entity
@Table(name = "db_wfType")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbwfType.findAll", query = "SELECT d FROM DbwfType d")
    , @NamedQuery(name = "DbwfType.findByWfTypeId", query = "SELECT d FROM DbwfType d WHERE d.wfTypeId = :wfTypeId")
    , @NamedQuery(name = "DbwfType.findByWfTypeName", query = "SELECT d FROM DbwfType d WHERE d.wfTypeName = :wfTypeName")
    , @NamedQuery(name = "DbwfType.findByWfTypeDescription", query = "SELECT d FROM DbwfType d WHERE d.wfTypeDescription = :wfTypeDescription")
    , @NamedQuery(name = "DbwfType.findByWfTypeSendEmail", query = "SELECT d FROM DbwfType d WHERE d.wfTypeSendEmail = :wfTypeSendEmail")})
public class DbwfType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "wfTypeId")
    private String wfTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "wfTypeName")
    private String wfTypeName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "wfTypeDescription")
    private String wfTypeDescription;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "wfTypeSendEmail")
    private String wfTypeSendEmail;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "wfTypeId")
    private List<DbwfHeader> dbwfHeaderList;

    public DbwfType() {
    }

    public DbwfType(String wfTypeId) {
        this.wfTypeId = wfTypeId;
    }

    public DbwfType(String wfTypeId, String wfTypeName, String wfTypeDescription, String wfTypeSendEmail) {
        this.wfTypeId = wfTypeId;
        this.wfTypeName = wfTypeName;
        this.wfTypeDescription = wfTypeDescription;
        this.wfTypeSendEmail = wfTypeSendEmail;
    }

    public String getWfTypeId() {
        return wfTypeId;
    }

    public void setWfTypeId(String wfTypeId) {
        this.wfTypeId = wfTypeId;
    }

    public String getWfTypeName() {
        return wfTypeName;
    }

    public void setWfTypeName(String wfTypeName) {
        this.wfTypeName = wfTypeName;
    }

    public String getWfTypeDescription() {
        return wfTypeDescription;
    }

    public void setWfTypeDescription(String wfTypeDescription) {
        this.wfTypeDescription = wfTypeDescription;
    }

    public String getWfTypeSendEmail() {
        return wfTypeSendEmail;
    }

    public void setWfTypeSendEmail(String wfTypeSendEmail) {
        this.wfTypeSendEmail = wfTypeSendEmail;
    }

    @XmlTransient
    public List<DbwfHeader> getDbwfHeaderList() {
        return dbwfHeaderList;
    }

    public void setDbwfHeaderList(List<DbwfHeader> dbwfHeaderList) {
        this.dbwfHeaderList = dbwfHeaderList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wfTypeId != null ? wfTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbwfType)) {
            return false;
        }
        DbwfType other = (DbwfType) object;
        if ((this.wfTypeId == null && other.wfTypeId != null) || (this.wfTypeId != null && !this.wfTypeId.equals(other.wfTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbwfType[ wfTypeId=" + wfTypeId + " ]";
    }
    
}
