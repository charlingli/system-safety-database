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
 * @author lxra
 */
@Entity
@Table(name = "db_hazard_files")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbHazardFiles.findAll", query = "SELECT d FROM DbHazardFiles d")
    , @NamedQuery(name = "DbHazardFiles.findByHazardId", query = "SELECT d FROM DbHazardFiles d WHERE d.dbHazardFilesPK.hazardId = :hazardId")
    , @NamedQuery(name = "DbHazardFiles.findByFileId", query = "SELECT d FROM DbHazardFiles d WHERE d.dbHazardFilesPK.fileId = :fileId")
    , @NamedQuery(name = "DbHazardFiles.findByDbHazardFilesDummyvar", query = "SELECT d FROM DbHazardFiles d WHERE d.dbHazardFilesDummyvar = :dbHazardFilesDummyvar")})
public class DbHazardFiles implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DbHazardFilesPK dbHazardFilesPK;
    @Column(name = "db_hazard_files_dummyvar")
    private Short dbHazardFilesDummyvar;
    @JoinColumn(name = "fileId", referencedColumnName = "fileId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DbFiles dbFiles;

    public DbHazardFiles() {
    }

    public DbHazardFiles(DbHazardFilesPK dbHazardFilesPK) {
        this.dbHazardFilesPK = dbHazardFilesPK;
    }

    public DbHazardFiles(String hazardId, int fileId) {
        this.dbHazardFilesPK = new DbHazardFilesPK(hazardId, fileId);
    }

    public DbHazardFilesPK getDbHazardFilesPK() {
        return dbHazardFilesPK;
    }

    public void setDbHazardFilesPK(DbHazardFilesPK dbHazardFilesPK) {
        this.dbHazardFilesPK = dbHazardFilesPK;
    }

    public Short getDbHazardFilesDummyvar() {
        return dbHazardFilesDummyvar;
    }

    public void setDbHazardFilesDummyvar(Short dbHazardFilesDummyvar) {
        this.dbHazardFilesDummyvar = dbHazardFilesDummyvar;
    }

    public DbFiles getDbFiles() {
        return dbFiles;
    }

    public void setDbFiles(DbFiles dbFiles) {
        this.dbFiles = dbFiles;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dbHazardFilesPK != null ? dbHazardFilesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbHazardFiles)) {
            return false;
        }
        DbHazardFiles other = (DbHazardFiles) object;
        if ((this.dbHazardFilesPK == null && other.dbHazardFilesPK != null) || (this.dbHazardFilesPK != null && !this.dbHazardFilesPK.equals(other.dbHazardFilesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbHazardFiles[ dbHazardFilesPK=" + dbHazardFilesPK + " ]";
    }
    
}
