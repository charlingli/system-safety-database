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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "db_control")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbControl.findAll", query = "SELECT d FROM DbControl d")
    , @NamedQuery(name = "DbControl.findByControlId", query = "SELECT d FROM DbControl d WHERE d.controlId = :controlId")})
public class DbControl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "controlId")
    private Integer controlId;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "controlDescription")
    private String controlDescription;
    @OneToMany(mappedBy = "dbControl", orphanRemoval=true)
    private List<DbControlHazard> dbControlHazardList;
    @JoinColumn(name = "controlHierarchyId", referencedColumnName = "controlHierarchyId")
    @ManyToOne(optional = false)
    private DbcontrolHierarchy controlHierarchyId;
    @JoinColumn(name = "ownerId", referencedColumnName = "ownerId")
    @ManyToOne(optional = false)
    private DbOwners ownerId;

    public DbControl() {
    }

    public DbControl(Integer controlId) {
        this.controlId = controlId;
    }

    public DbControl(Integer controlId, String controlDescription) {
        this.controlId = controlId;
        this.controlDescription = controlDescription;
    }

    public Integer getControlId() {
        return controlId;
    }

    public void setControlId(Integer controlId) {
        this.controlId = controlId;
    }

    public String getControlDescription() {
        return controlDescription;
    }

    public void setControlDescription(String controlDescription) {
        this.controlDescription = controlDescription;
    }

    @XmlTransient
    public List<DbControlHazard> getDbControlHazardList() {
        return dbControlHazardList;
    }

    public void setDbControlHazardList(List<DbControlHazard> dbControlHazardList) {
        this.dbControlHazardList = dbControlHazardList;
    }

    public DbcontrolHierarchy getControlHierarchyId() {
        return controlHierarchyId;
    }

    public void setControlHierarchyId(DbcontrolHierarchy controlHierarchyId) {
        this.controlHierarchyId = controlHierarchyId;
    }

    public DbOwners getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(DbOwners ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (controlId != null ? controlId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbControl)) {
            return false;
        }
        DbControl other = (DbControl) object;
        if ((this.controlId == null && other.controlId != null) || (this.controlId != null && !this.controlId.equals(other.controlId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbControl[ controlId=" + controlId + " ]";
    }
    
}
