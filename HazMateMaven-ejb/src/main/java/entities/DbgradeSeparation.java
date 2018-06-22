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
@Table(name = "db_gradeSeparation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbgradeSeparation.findAll", query = "SELECT d FROM DbgradeSeparation d")
    , @NamedQuery(name = "DbgradeSeparation.findByGradeSeparationId", query = "SELECT d FROM DbgradeSeparation d WHERE d.gradeSeparationId = :gradeSeparationId")
    , @NamedQuery(name = "DbgradeSeparation.findByGradeSeparationName", query = "SELECT d FROM DbgradeSeparation d WHERE d.gradeSeparationName = :gradeSeparationName")})
public class DbgradeSeparation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "gradeSeparationId")
    private Integer gradeSeparationId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "gradeSeparationName")
    private String gradeSeparationName;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "locationGradeSeparation")
    private List<DbLocation> dbLocationList;

    public DbgradeSeparation() {
    }

    public DbgradeSeparation(Integer gradeSeparationId) {
        this.gradeSeparationId = gradeSeparationId;
    }

    public DbgradeSeparation(Integer gradeSeparationId, String gradeSeparationName) {
        this.gradeSeparationId = gradeSeparationId;
        this.gradeSeparationName = gradeSeparationName;
    }

    public Integer getGradeSeparationId() {
        return gradeSeparationId;
    }

    public void setGradeSeparationId(Integer gradeSeparationId) {
        this.gradeSeparationId = gradeSeparationId;
    }

    public String getGradeSeparationName() {
        return gradeSeparationName;
    }

    public void setGradeSeparationName(String gradeSeparationName) {
        this.gradeSeparationName = gradeSeparationName;
    }

    @XmlTransient
    public List<DbLocation> getDbLocationList() {
        return dbLocationList;
    }

    public void setDbLocationList(List<DbLocation> dbLocationList) {
        this.dbLocationList = dbLocationList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gradeSeparationId != null ? gradeSeparationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbgradeSeparation)) {
            return false;
        }
        DbgradeSeparation other = (DbgradeSeparation) object;
        if ((this.gradeSeparationId == null && other.gradeSeparationId != null) || (this.gradeSeparationId != null && !this.gradeSeparationId.equals(other.gradeSeparationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return gradeSeparationName;
    }
    
}
