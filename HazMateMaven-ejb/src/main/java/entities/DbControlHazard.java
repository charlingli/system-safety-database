/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan David
 */
@Entity
@Table(name = "db_control_hazard")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbControlHazard.findAll", query = "SELECT d FROM DbControlHazard d")
    , @NamedQuery(name = "DbControlHazard.findByHazardId", query = "SELECT d FROM DbControlHazard d WHERE d.dbControlHazardPK.hazardId = :hazardId")
    , @NamedQuery(name = "DbControlHazard.findByControlId", query = "SELECT d FROM DbControlHazard d WHERE d.dbControlHazardPK.controlId = :controlId")})
public class DbControlHazard implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DbControlHazardPK dbControlHazardPK;
    @Lob
    @Size(max = 65535)
    @Column(name = "controlJustify")
    private String controlJustify;
    @JoinColumn(name = "controlId", referencedColumnName = "controlId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DbControl dbControl;
    @JoinColumn(name = "controlRecommendId", referencedColumnName = "controlRecommendId")
    @ManyToOne(optional = false)
    private DbcontrolRecommend controlRecommendId;
    @JoinColumn(name = "hazardId", referencedColumnName = "hazardId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DbHazard dbHazard;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "controlType")
    private String controlType;

    public DbControlHazard() {
    }

    public DbControlHazard(DbControlHazardPK dbControlHazardPK) {
        this.dbControlHazardPK = dbControlHazardPK;
    }

    public DbControlHazard(DbControlHazardPK dbControlHazardPK, String controlJustify) {
        this.dbControlHazardPK = dbControlHazardPK;
        this.controlJustify = controlJustify;
    }

    public DbControlHazard(String hazardId, int controlId) {
        this.dbControlHazardPK = new DbControlHazardPK(hazardId, controlId);
    }

    public DbControlHazardPK getDbControlHazardPK() {
        return dbControlHazardPK;
    }

    public void setDbControlHazardPK(DbControlHazardPK dbControlHazardPK) {
        this.dbControlHazardPK = dbControlHazardPK;
    }

    public String getControlJustify() {
        return controlJustify;
    }

    public void setControlJustify(String controlJustify) {
        this.controlJustify = controlJustify;
    }

    public DbControl getDbControl() {
        return dbControl;
    }

    public void setDbControl(DbControl dbControl) {
        this.dbControl = dbControl;
    }

    public DbcontrolRecommend getControlRecommendId() {
        return controlRecommendId;
    }

    public void setControlRecommendId(DbcontrolRecommend controlRecommendId) {
        this.controlRecommendId = controlRecommendId;
    }

    public DbHazard getDbHazard() {
        return dbHazard;
    }

    public void setDbHazard(DbHazard dbHazard) {
        this.dbHazard = dbHazard;
    }
    
      public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dbControlHazardPK != null ? dbControlHazardPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbControlHazard)) {
            return false;
        }
        DbControlHazard other = (DbControlHazard) object;
        if ((this.dbControlHazardPK == null && other.dbControlHazardPK != null) || (this.dbControlHazardPK != null && !this.dbControlHazardPK.equals(other.dbControlHazardPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbControlHazard[ dbControlHazardPK=" + dbControlHazardPK + " ]";
    }
    
}
