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
@Table(name = "db_constructionType")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbconstructionType.findAll", query = "SELECT d FROM DbconstructionType d")
    , @NamedQuery(name = "DbconstructionType.findByConstructionTypeId", query = "SELECT d FROM DbconstructionType d WHERE d.constructionTypeId = :constructionTypeId")
    , @NamedQuery(name = "DbconstructionType.findByConstructionTypeName", query = "SELECT d FROM DbconstructionType d WHERE d.constructionTypeName = :constructionTypeName")})
public class DbconstructionType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "constructionTypeId")
    private Integer constructionTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "constructionTypeName")
    private String constructionTypeName;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "locationConstructionType")
    private List<DbLocation> dbLocationList;

    public DbconstructionType() {
    }

    public DbconstructionType(Integer constructionTypeId) {
        this.constructionTypeId = constructionTypeId;
    }

    public DbconstructionType(Integer constructionTypeId, String constructionTypeName) {
        this.constructionTypeId = constructionTypeId;
        this.constructionTypeName = constructionTypeName;
    }

    public Integer getConstructionTypeId() {
        return constructionTypeId;
    }

    public void setConstructionTypeId(Integer constructionTypeId) {
        this.constructionTypeId = constructionTypeId;
    }

    public String getConstructionTypeName() {
        return constructionTypeName;
    }

    public void setConstructionTypeName(String constructionTypeName) {
        this.constructionTypeName = constructionTypeName;
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
        hash += (constructionTypeId != null ? constructionTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbconstructionType)) {
            return false;
        }
        DbconstructionType other = (DbconstructionType) object;
        if ((this.constructionTypeId == null && other.constructionTypeId != null) || (this.constructionTypeId != null && !this.constructionTypeId.equals(other.constructionTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return constructionTypeName;
    }
    
}
