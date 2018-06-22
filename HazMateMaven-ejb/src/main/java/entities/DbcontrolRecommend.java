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
@Table(name = "db_controlRecommend")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbcontrolRecommend.findAll", query = "SELECT d FROM DbcontrolRecommend d")
    , @NamedQuery(name = "DbcontrolRecommend.findByControlRecommendId", query = "SELECT d FROM DbcontrolRecommend d WHERE d.controlRecommendId = :controlRecommendId")
    , @NamedQuery(name = "DbcontrolRecommend.findByControlRecommendName", query = "SELECT d FROM DbcontrolRecommend d WHERE d.controlRecommendName = :controlRecommendName")
    , @NamedQuery(name = "DbcontrolRecommend.findByControlJustifyRequired", query = "SELECT d FROM DbcontrolRecommend d WHERE d.controlJustifyRequired = :controlJustifyRequired")})
public class DbcontrolRecommend implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "controlRecommendId")
    private Integer controlRecommendId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "controlRecommendName")
    private String controlRecommendName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "controlJustifyRequired")
    private String controlJustifyRequired;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "controlRecommendId")
    private List<DbControlHazard> dbControlHazardList;

    public DbcontrolRecommend() {
    }

    public DbcontrolRecommend(Integer controlRecommendId) {
        this.controlRecommendId = controlRecommendId;
    }

    public DbcontrolRecommend(Integer controlRecommendId, String controlRecommendName, String controlJustifyRequired) {
        this.controlRecommendId = controlRecommendId;
        this.controlRecommendName = controlRecommendName;
        this.controlJustifyRequired = controlJustifyRequired;
    }

    public Integer getControlRecommendId() {
        return controlRecommendId;
    }

    public void setControlRecommendId(Integer controlRecommendId) {
        this.controlRecommendId = controlRecommendId;
    }

    public String getControlRecommendName() {
        return controlRecommendName;
    }

    public void setControlRecommendName(String controlRecommendName) {
        this.controlRecommendName = controlRecommendName;
    }

    public String getControlJustifyRequired() {
        return controlJustifyRequired;
    }

    public void setControlJustifyRequired(String controlJustifyRequired) {
        this.controlJustifyRequired = controlJustifyRequired;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (controlRecommendId != null ? controlRecommendId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbcontrolRecommend)) {
            return false;
        }
        DbcontrolRecommend other = (DbcontrolRecommend) object;
        if ((this.controlRecommendId == null && other.controlRecommendId != null) || (this.controlRecommendId != null && !this.controlRecommendId.equals(other.controlRecommendId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return controlRecommendName;
    }

    @XmlTransient
    public List<DbControlHazard> getDbControlHazardList() {
        return dbControlHazardList;
    }

    public void setDbControlHazardList(List<DbControlHazard> dbControlHazardList) {
        this.dbControlHazardList = dbControlHazardList;
    }

}
