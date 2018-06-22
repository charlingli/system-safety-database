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
@Table(name = "db_hazardContext")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbhazardContext.findAll", query = "SELECT d FROM DbhazardContext d")
    , @NamedQuery(name = "DbhazardContext.findByHazardContextId", query = "SELECT d FROM DbhazardContext d WHERE d.hazardContextId = :hazardContextId")
    , @NamedQuery(name = "DbhazardContext.findByHazardContextName", query = "SELECT d FROM DbhazardContext d WHERE d.hazardContextName = :hazardContextName")})
public class DbhazardContext implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "hazardContextId")
    private Integer hazardContextId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "hazardContextName")
    private String hazardContextName;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "hazardContextId")
    private List<DbHazard> dbHazardList;

    public DbhazardContext() {
    }

    public DbhazardContext(Integer hazardContextId) {
        this.hazardContextId = hazardContextId;
    }

    public DbhazardContext(Integer hazardContextId, String hazardContextName) {
        this.hazardContextId = hazardContextId;
        this.hazardContextName = hazardContextName;
    }

    public Integer getHazardContextId() {
        return hazardContextId;
    }

    public void setHazardContextId(Integer hazardContextId) {
        this.hazardContextId = hazardContextId;
    }

    public String getHazardContextName() {
        return hazardContextName;
    }

    public void setHazardContextName(String hazardContextName) {
        this.hazardContextName = hazardContextName;
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
        hash += (hazardContextId != null ? hazardContextId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbhazardContext)) {
            return false;
        }
        DbhazardContext other = (DbhazardContext) object;
        if ((this.hazardContextId == null && other.hazardContextId != null) || (this.hazardContextId != null && !this.hazardContextId.equals(other.hazardContextId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return hazardContextName;
    }
    
}
