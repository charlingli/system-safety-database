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
@Table(name = "db_controlHierarchy")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbcontrolHierarchy.findAll", query = "SELECT d FROM DbcontrolHierarchy d")
    , @NamedQuery(name = "DbcontrolHierarchy.findByControlHierarchyId", query = "SELECT d FROM DbcontrolHierarchy d WHERE d.controlHierarchyId = :controlHierarchyId")
    , @NamedQuery(name = "DbcontrolHierarchy.findByControlHierarchyName", query = "SELECT d FROM DbcontrolHierarchy d WHERE d.controlHierarchyName = :controlHierarchyName")})
public class DbcontrolHierarchy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "controlHierarchyId")
    private Integer controlHierarchyId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "controlHierarchyName")
    private String controlHierarchyName;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "controlHierarchyId")
    private List<DbControl> dbControlList;

    public DbcontrolHierarchy() {
    }

    public DbcontrolHierarchy(Integer controlHierarchyId) {
        this.controlHierarchyId = controlHierarchyId;
    }

    public DbcontrolHierarchy(Integer controlHierarchyId, String controlHierarchyName) {
        this.controlHierarchyId = controlHierarchyId;
        this.controlHierarchyName = controlHierarchyName;
    }

    public Integer getControlHierarchyId() {
        return controlHierarchyId;
    }

    public void setControlHierarchyId(Integer controlHierarchyId) {
        this.controlHierarchyId = controlHierarchyId;
    }

    public String getControlHierarchyName() {
        return controlHierarchyName;
    }

    public void setControlHierarchyName(String controlHierarchyName) {
        this.controlHierarchyName = controlHierarchyName;
    }

    @XmlTransient
    public List<DbControl> getDbControlList() {
        return dbControlList;
    }

    public void setDbControlList(List<DbControl> dbControlList) {
        this.dbControlList = dbControlList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (controlHierarchyId != null ? controlHierarchyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbcontrolHierarchy)) {
            return false;
        }
        DbcontrolHierarchy other = (DbcontrolHierarchy) object;
        if ((this.controlHierarchyId == null && other.controlHierarchyId != null) || (this.controlHierarchyId != null && !this.controlHierarchyId.equals(other.controlHierarchyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return controlHierarchyName;
    }
    
}
