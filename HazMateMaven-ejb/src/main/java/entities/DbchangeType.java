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
@Table(name = "db_changeType")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbchangeType.findAll", query = "SELECT d FROM DbchangeType d")
    , @NamedQuery(name = "DbchangeType.findByChangeTypeId", query = "SELECT d FROM DbchangeType d WHERE d.changeTypeId = :changeTypeId")
    , @NamedQuery(name = "DbchangeType.findByChangeTypeName", query = "SELECT d FROM DbchangeType d WHERE d.changeTypeName = :changeTypeName")})
public class DbchangeType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "changeTypeId")
    private Integer changeTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "changeTypeName")
    private String changeTypeName;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "locationChangeType")
    private List<DbLocation> dbLocationList;

    public DbchangeType() {
    }

    public DbchangeType(Integer changeTypeId) {
        this.changeTypeId = changeTypeId;
    }

    public DbchangeType(Integer changeTypeId, String changeTypeName) {
        this.changeTypeId = changeTypeId;
        this.changeTypeName = changeTypeName;
    }

    public Integer getChangeTypeId() {
        return changeTypeId;
    }

    public void setChangeTypeId(Integer changeTypeId) {
        this.changeTypeId = changeTypeId;
    }

    public String getChangeTypeName() {
        return changeTypeName;
    }

    public void setChangeTypeName(String changeTypeName) {
        this.changeTypeName = changeTypeName;
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
        hash += (changeTypeId != null ? changeTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbchangeType)) {
            return false;
        }
        DbchangeType other = (DbchangeType) object;
        if ((this.changeTypeId == null && other.changeTypeId != null) || (this.changeTypeId != null && !this.changeTypeId.equals(other.changeTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return changeTypeName;
    }
    
}
