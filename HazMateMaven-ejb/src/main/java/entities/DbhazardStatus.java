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
 * @author Juan David
 */
@Entity
@Table(name = "db_hazardStatus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbhazardStatus.findAll", query = "SELECT d FROM DbhazardStatus d")
    , @NamedQuery(name = "DbhazardStatus.findByHazardStatusId", query = "SELECT d FROM DbhazardStatus d WHERE d.hazardStatusId = :hazardStatusId")
    , @NamedQuery(name = "DbhazardStatus.findByHazardStatusName", query = "SELECT d FROM DbhazardStatus d WHERE d.hazardStatusName = :hazardStatusName")})
public class DbhazardStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "hazardStatusId")
    private Integer hazardStatusId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "hazardStatusName")
    private String hazardStatusName;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "hazardStatusId")
    private List<DbHazard> dbHazardList;

    public DbhazardStatus() {
    }

    public DbhazardStatus(Integer hazardStatusId) {
        this.hazardStatusId = hazardStatusId;
    }

    public DbhazardStatus(Integer hazardStatusId, String hazardStatusName) {
        this.hazardStatusId = hazardStatusId;
        this.hazardStatusName = hazardStatusName;
    }

    public Integer getHazardStatusId() {
        return hazardStatusId;
    }

    public void setHazardStatusId(Integer hazardStatusId) {
        this.hazardStatusId = hazardStatusId;
    }

    public String getHazardStatusName() {
        return hazardStatusName;
    }

    public void setHazardStatusName(String hazardStatusName) {
        this.hazardStatusName = hazardStatusName;
    }

    @XmlTransient
    public List<DbHazard> getDbHazardList() {
        return dbHazardList;
    }

    public void setDbHazardList(List<DbHazard> dbHazardList) {
        this.dbHazardList = dbHazardList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hazardStatusId != null ? hazardStatusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbhazardStatus)) {
            return false;
        }
        DbhazardStatus other = (DbhazardStatus) object;
        if ((this.hazardStatusId == null && other.hazardStatusId != null) || (this.hazardStatusId != null && !this.hazardStatusId.equals(other.hazardStatusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return hazardStatusName;
    }
    
}
