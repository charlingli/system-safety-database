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
@Table(name = "db_hazard_cause")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbHazardCause.findAll", query = "SELECT d FROM DbHazardCause d")
    , @NamedQuery(name = "DbHazardCause.findByHazardId", query = "SELECT d FROM DbHazardCause d WHERE d.dbHazardCausePK.hazardId = :hazardId")
    , @NamedQuery(name = "DbHazardCause.findByCauseId", query = "SELECT d FROM DbHazardCause d WHERE d.dbHazardCausePK.causeId = :causeId")
    , @NamedQuery(name = "DbHazardCause.findByDbHazardCauseDummyvar", query = "SELECT d FROM DbHazardCause d WHERE d.dbHazardCauseDummyvar = :dbHazardCauseDummyvar")})
public class DbHazardCause implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DbHazardCausePK dbHazardCausePK;
    @Column(name = "db_hazard_cause_dummyvar")
    private Short dbHazardCauseDummyvar;
    @JoinColumn(name = "causeId", referencedColumnName = "causeId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DbCause dbCause;
    @JoinColumn(name = "hazardId", referencedColumnName = "hazardId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DbHazard dbHazard;

    public DbHazardCause() {
    }

    public DbHazardCause(DbHazardCausePK dbHazardCausePK) {
        this.dbHazardCausePK = dbHazardCausePK;
    }

    public DbHazardCause(String hazardId, int causeId) {
        this.dbHazardCausePK = new DbHazardCausePK(hazardId, causeId);
    }

    public DbHazardCausePK getDbHazardCausePK() {
        return dbHazardCausePK;
    }

    public void setDbHazardCausePK(DbHazardCausePK dbHazardCausePK) {
        this.dbHazardCausePK = dbHazardCausePK;
    }

    public Short getDbHazardCauseDummyvar() {
        return dbHazardCauseDummyvar;
    }

    public void setDbHazardCauseDummyvar(Short dbHazardCauseDummyvar) {
        this.dbHazardCauseDummyvar = dbHazardCauseDummyvar;
    }

    public DbCause getDbCause() {
        return dbCause;
    }

    public void setDbCause(DbCause dbCause) {
        this.dbCause = dbCause;
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
        hash += (dbHazardCausePK != null ? dbHazardCausePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbHazardCause)) {
            return false;
        }
        DbHazardCause other = (DbHazardCause) object;
        if ((this.dbHazardCausePK == null && other.dbHazardCausePK != null) || (this.dbHazardCausePK != null && !this.dbHazardCausePK.equals(other.dbHazardCausePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbHazardCause[ dbHazardCausePK=" + dbHazardCausePK + " ]";
    }
    
}
