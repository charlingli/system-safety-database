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
@Table(name = "db_location")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbLocation.findAll", query = "SELECT d FROM DbLocation d")
    , @NamedQuery(name = "DbLocation.findByLocationId", query = "SELECT d FROM DbLocation d WHERE d.locationId = :locationId")
    , @NamedQuery(name = "DbLocation.findByLocationName", query = "SELECT d FROM DbLocation d WHERE d.locationName = :locationName")})
public class DbLocation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "locationId")
    private Integer locationId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "locationName")
    private String locationName;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "locationDescription")
    private String locationDescription;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "locationAbbrev")
    private String locationAbbrev;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "hazardLocation")
    private List<DbHazard> dbHazardList;
    @JoinColumn(name = "locationChangeType", referencedColumnName = "changeTypeId")
    @ManyToOne(optional = false)
    private DbchangeType locationChangeType;
    @JoinColumn(name = "locationConstructionType", referencedColumnName = "constructionTypeId")
    @ManyToOne(optional = false)
    private DbconstructionType locationConstructionType;
    @JoinColumn(name = "locationGradeSeparation", referencedColumnName = "gradeSeparationId")
    @ManyToOne(optional = false)
    private DbgradeSeparation locationGradeSeparation;
    @JoinColumn(name = "projectId", referencedColumnName = "projectId")
    @ManyToOne(optional = false)
    private DbProject projectId;

    public DbLocation() {
    }

    public DbLocation(Integer locationId) {
        this.locationId = locationId;
    }

    public DbLocation(Integer locationId, String locationName, String locationDescription) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.locationDescription = locationDescription;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    @XmlTransient
    public List<DbHazard> getDbHazardList() {
        return dbHazardList;
    }

    public void setDbHazardList(List<DbHazard> dbHazardList) {
        this.dbHazardList = dbHazardList;
    }

    public DbchangeType getLocationChangeType() {
        return locationChangeType;
    }

    public void setLocationChangeType(DbchangeType locationChangeType) {
        this.locationChangeType = locationChangeType;
    }

    public DbconstructionType getLocationConstructionType() {
        return locationConstructionType;
    }

    public void setLocationConstructionType(DbconstructionType locationConstructionType) {
        this.locationConstructionType = locationConstructionType;
    }

    public DbgradeSeparation getLocationGradeSeparation() {
        return locationGradeSeparation;
    }

    public void setLocationGradeSeparation(DbgradeSeparation locationGradeSeparation) {
        this.locationGradeSeparation = locationGradeSeparation;
    }

    public DbProject getProjectId() {
        return projectId;
    }

    public void setProjectId(DbProject projectId) {
        this.projectId = projectId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (locationId != null ? locationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbLocation)) {
            return false;
        }
        DbLocation other = (DbLocation) object;
        if ((this.locationId == null && other.locationId != null) || (this.locationId != null && !this.locationId.equals(other.locationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return locationName;
    }

    public String getLocationAbbrev() {
        return locationAbbrev;
    }

    public void setLocationAbbrev(String locationAbbrev) {
        this.locationAbbrev = locationAbbrev;
    }

}
