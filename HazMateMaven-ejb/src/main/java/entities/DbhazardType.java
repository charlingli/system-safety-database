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
@Table(name = "db_hazardType")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbhazardType.findAll", query = "SELECT d FROM DbhazardType d")
    , @NamedQuery(name = "DbhazardType.findByHazardTypeId", query = "SELECT d FROM DbhazardType d WHERE d.hazardTypeId = :hazardTypeId")
    , @NamedQuery(name = "DbhazardType.findByHazardTypeName", query = "SELECT d FROM DbhazardType d WHERE d.hazardTypeName = :hazardTypeName")})
public class DbhazardType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "hazardTypeId")
    private Integer hazardTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "hazardTypeName")
    private String hazardTypeName;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "hazardTypeId")
    private List<DbHazard> dbHazardList;

    public DbhazardType() {
    }

    public DbhazardType(Integer hazardTypeId) {
        this.hazardTypeId = hazardTypeId;
    }

    public DbhazardType(Integer hazardTypeId, String hazardTypeName) {
        this.hazardTypeId = hazardTypeId;
        this.hazardTypeName = hazardTypeName;
    }

    public Integer getHazardTypeId() {
        return hazardTypeId;
    }

    public void setHazardTypeId(Integer hazardTypeId) {
        this.hazardTypeId = hazardTypeId;
    }

    public String getHazardTypeName() {
        return hazardTypeName;
    }

    public void setHazardTypeName(String hazardTypeName) {
        this.hazardTypeName = hazardTypeName;
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
        hash += (hazardTypeId != null ? hazardTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbhazardType)) {
            return false;
        }
        DbhazardType other = (DbhazardType) object;
        if ((this.hazardTypeId == null && other.hazardTypeId != null) || (this.hazardTypeId != null && !this.hazardTypeId.equals(other.hazardTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return hazardTypeName;
    }
    
}
