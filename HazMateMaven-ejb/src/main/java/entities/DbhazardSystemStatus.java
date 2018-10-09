/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "db_hazardSystemStatus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbhazardSystemStatus.findAll", query = "SELECT d FROM DbhazardSystemStatus d")
    , @NamedQuery(name = "DbhazardSystemStatus.findBySystemStatusId", query = "SELECT d FROM DbhazardSystemStatus d WHERE d.systemStatusId = :systemStatusId")
    , @NamedQuery(name = "DbhazardSystemStatus.findBySystemStatusName", query = "SELECT d FROM DbhazardSystemStatus d WHERE d.systemStatusName = :systemStatusName")})
public class DbhazardSystemStatus implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hazardSystemStatus")
    private Collection<DbHazard> dbHazardCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "systemStatusId")
    private Integer systemStatusId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "systemStatusName")
    private String systemStatusName;

    public DbhazardSystemStatus() {
    }

    public DbhazardSystemStatus(Integer systemStatusId) {
        this.systemStatusId = systemStatusId;
    }

    public DbhazardSystemStatus(Integer systemStatusId, String systemStatusName) {
        this.systemStatusId = systemStatusId;
        this.systemStatusName = systemStatusName;
    }

    public Integer getSystemStatusId() {
        return systemStatusId;
    }

    public void setSystemStatusId(Integer systemStatusId) {
        this.systemStatusId = systemStatusId;
    }

    public String getSystemStatusName() {
        return systemStatusName;
    }

    public void setSystemStatusName(String systemStatusName) {
        this.systemStatusName = systemStatusName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (systemStatusId != null ? systemStatusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbhazardSystemStatus)) {
            return false;
        }
        DbhazardSystemStatus other = (DbhazardSystemStatus) object;
        if ((this.systemStatusId == null && other.systemStatusId != null) || (this.systemStatusId != null && !this.systemStatusId.equals(other.systemStatusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbhazardSystemStatus[ systemStatusId=" + systemStatusId + " ]";
    }

    @XmlTransient
    public Collection<DbHazard> getDbHazardCollection() {
        return dbHazardCollection;
    }

    public void setDbHazardCollection(Collection<DbHazard> dbHazardCollection) {
        this.dbHazardCollection = dbHazardCollection;
    }
    
}
