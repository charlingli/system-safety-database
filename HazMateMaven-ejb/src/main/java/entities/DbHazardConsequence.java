/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
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
@Table(name = "db_hazard_consequence")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbHazardConsequence.findAll", query = "SELECT d FROM DbHazardConsequence d")
    , @NamedQuery(name = "DbHazardConsequence.findByHazardId", query = "SELECT d FROM DbHazardConsequence d WHERE d.dbHazardConsequencePK.hazardId = :hazardId")
    , @NamedQuery(name = "DbHazardConsequence.findByConsequenceId", query = "SELECT d FROM DbHazardConsequence d WHERE d.dbHazardConsequencePK.consequenceId = :consequenceId")
    , @NamedQuery(name = "DbHazardConsequence.findByDbHazardConsequenceDummyvar", query = "SELECT d FROM DbHazardConsequence d WHERE d.dbHazardConsequenceDummyvar = :dbHazardConsequenceDummyvar")})
public class DbHazardConsequence implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DbHazardConsequencePK dbHazardConsequencePK;
    @Column(name = "db_hazard_consequence_dummyvar")
    private Short dbHazardConsequenceDummyvar;
    @JoinColumn(name = "consequenceId", referencedColumnName = "consequenceId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DbConsequence dbConsequence;
    @JoinColumn(name = "hazardId", referencedColumnName = "hazardId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DbHazard dbHazard;

    public DbHazardConsequence() {
    }

    public DbHazardConsequence(DbHazardConsequencePK dbHazardConsequencePK) {
        this.dbHazardConsequencePK = dbHazardConsequencePK;
    }

    public DbHazardConsequence(String hazardId, int consequenceId) {
        this.dbHazardConsequencePK = new DbHazardConsequencePK(hazardId, consequenceId);
    }

    public DbHazardConsequencePK getDbHazardConsequencePK() {
        return dbHazardConsequencePK;
    }

    public void setDbHazardConsequencePK(DbHazardConsequencePK dbHazardConsequencePK) {
        this.dbHazardConsequencePK = dbHazardConsequencePK;
    }

    public Short getDbHazardConsequenceDummyvar() {
        return dbHazardConsequenceDummyvar;
    }

    public void setDbHazardConsequenceDummyvar(Short dbHazardConsequenceDummyvar) {
        this.dbHazardConsequenceDummyvar = dbHazardConsequenceDummyvar;
    }

    public DbConsequence getDbConsequence() {
        return dbConsequence;
    }

    public void setDbConsequence(DbConsequence dbConsequence) {
        this.dbConsequence = dbConsequence;
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
        hash += (dbHazardConsequencePK != null ? dbHazardConsequencePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbHazardConsequence)) {
            return false;
        }
        DbHazardConsequence other = (DbHazardConsequence) object;
        if ((this.dbHazardConsequencePK == null && other.dbHazardConsequencePK != null) || (this.dbHazardConsequencePK != null && !this.dbHazardConsequencePK.equals(other.dbHazardConsequencePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbHazardConsequence[ dbHazardConsequencePK=" + dbHazardConsequencePK + " ]";
    }
    
}
