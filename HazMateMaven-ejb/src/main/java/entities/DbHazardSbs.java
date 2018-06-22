/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan David
 */
@Entity
@Table(name = "db_hazard_sbs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbHazardSbs.findAll", query = "SELECT d FROM DbHazardSbs d")
    , @NamedQuery(name = "DbHazardSbs.findByHazardId", query = "SELECT d FROM DbHazardSbs d WHERE d.dbHazardSbsPK.hazardId = :hazardId")
    , @NamedQuery(name = "DbHazardSbs.findBySbsId", query = "SELECT d FROM DbHazardSbs d WHERE d.dbHazardSbsPK.sbsId = :sbsId")})
public class DbHazardSbs implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DbHazardSbsPK dbHazardSbsPK;
    @JoinColumn(name = "hazardId", referencedColumnName = "hazardId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DbHazard dbHazard;

    public DbHazardSbs() {
    }

    public DbHazardSbs(DbHazardSbsPK dbHazardSbsPK) {
        this.dbHazardSbsPK = dbHazardSbsPK;
    }

    public DbHazardSbs(String hazardId, String sbsId) {
        this.dbHazardSbsPK = new DbHazardSbsPK(hazardId, sbsId);
    }

    public DbHazardSbsPK getDbHazardSbsPK() {
        return dbHazardSbsPK;
    }

    public void setDbHazardSbsPK(DbHazardSbsPK dbHazardSbsPK) {
        this.dbHazardSbsPK = dbHazardSbsPK;
    }

    public DbHazard getDbHazard() {
        return dbHazard;
    }

    public void setDbHazard(DbHazard dbHazard) {
        this.dbHazard = dbHazard;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dbHazardSbsPK != null ? dbHazardSbsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbHazardSbs)) {
            return false;
        }
        DbHazardSbs other = (DbHazardSbs) object;
        if ((this.dbHazardSbsPK == null && other.dbHazardSbsPK != null) || (this.dbHazardSbsPK != null && !this.dbHazardSbsPK.equals(other.dbHazardSbsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbHazardSbs[ dbHazardSbsPK=" + dbHazardSbsPK + " ]";
    }
    
}
