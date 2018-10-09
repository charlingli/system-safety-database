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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lxra
 */
@Entity
@Table(name = "db_quality")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbQuality.findAll", query = "SELECT d FROM DbQuality d")
    , @NamedQuery(name = "DbQuality.findByUserId", query = "SELECT d FROM DbQuality d WHERE d.dbQualityPK.userId = :userId")
    , @NamedQuery(name = "DbQuality.findByHazardId", query = "SELECT d FROM DbQuality d WHERE d.dbQualityPK.hazardId = :hazardId")
    , @NamedQuery(name = "DbQuality.findByRating", query = "SELECT d FROM DbQuality d WHERE d.rating = :rating")
    , @NamedQuery(name = "DbQuality.findByWeighting", query = "SELECT d FROM DbQuality d WHERE d.weighting = :weighting")})
public class DbQuality implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DbQualityPK dbQualityPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rating")
    private int rating;
    @Basic(optional = false)
    @NotNull
    @Column(name = "weighting")
    private int weighting;

    public DbQuality() {
    }

    public DbQuality(DbQualityPK dbQualityPK) {
        this.dbQualityPK = dbQualityPK;
    }

    public DbQuality(DbQualityPK dbQualityPK, int rating, int weighting) {
        this.dbQualityPK = dbQualityPK;
        this.rating = rating;
        this.weighting = weighting;
    }

    public DbQuality(int userId, String hazardId) {
        this.dbQualityPK = new DbQualityPK(userId, hazardId);
    }

    public DbQualityPK getDbQualityPK() {
        return dbQualityPK;
    }

    public void setDbQualityPK(DbQualityPK dbQualityPK) {
        this.dbQualityPK = dbQualityPK;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getWeighting() {
        return weighting;
    }

    public void setWeighting(int weighting) {
        this.weighting = weighting;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dbQualityPK != null ? dbQualityPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbQuality)) {
            return false;
        }
        DbQuality other = (DbQuality) object;
        if ((this.dbQualityPK == null && other.dbQualityPK != null) || (this.dbQualityPK != null && !this.dbQualityPK.equals(other.dbQualityPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbQuality[ dbQualityPK=" + dbQualityPK + " ]";
    }
    
}
