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
import javax.persistence.JoinColumns;
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
 * @author lxra
 */
@Entity
@Table(name = "db_importLineError")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbimportLineError.findAll", query = "SELECT d FROM DbimportLineError d")
    , @NamedQuery(name = "DbimportLineError.findByProcessId", query = "SELECT d FROM DbimportLineError d WHERE d.dbimportLineErrorPK.processId = :processId")
    , @NamedQuery(name = "DbimportLineError.findByProcessIdLine", query = "SELECT d FROM DbimportLineError d WHERE d.dbimportLineErrorPK.processIdLine = :processIdLine")
    , @NamedQuery(name = "DbimportLineError.findByProcessIdLineError", query = "SELECT d FROM DbimportLineError d WHERE d.dbimportLineErrorPK.processIdLineError = :processIdLineError")
    , @NamedQuery(name = "DbimportLineError.findByProcessErrorStatus", query = "SELECT d FROM DbimportLineError d WHERE d.processErrorStatus = :processErrorStatus")})
public class DbimportLineError implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DbimportLineErrorPK dbimportLineErrorPK;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "processErrorLocation")
    private String processErrorLocation;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "processErrorStatus")
    private String processErrorStatus;
    @JoinColumn(name = "processErrorCode", referencedColumnName = "errorId")
    @ManyToOne(optional = false)
    private DbimportErrorCode processErrorCode;
    @JoinColumns({
        @JoinColumn(name = "processId", referencedColumnName = "processId", insertable = false, updatable = false)
        , @JoinColumn(name = "processIdLine", referencedColumnName = "processIdLine", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private DbimportLine dbimportLine;

    public DbimportLineError() {
    }

    public DbimportLineError(DbimportLineErrorPK dbimportLineErrorPK) {
        this.dbimportLineErrorPK = dbimportLineErrorPK;
    }

    public DbimportLineError(DbimportLineErrorPK dbimportLineErrorPK, String processErrorLocation, String processErrorStatus) {
        this.dbimportLineErrorPK = dbimportLineErrorPK;
        this.processErrorLocation = processErrorLocation;
        this.processErrorStatus = processErrorStatus;
    }

    public DbimportLineError(String processId, int processIdLine, int processIdLineError) {
        this.dbimportLineErrorPK = new DbimportLineErrorPK(processId, processIdLine, processIdLineError);
    }

    public DbimportLineErrorPK getDbimportLineErrorPK() {
        return dbimportLineErrorPK;
    }

    public void setDbimportLineErrorPK(DbimportLineErrorPK dbimportLineErrorPK) {
        this.dbimportLineErrorPK = dbimportLineErrorPK;
    }

    public String getProcessErrorLocation() {
        return processErrorLocation;
    }

    public void setProcessErrorLocation(String processErrorLocation) {
        this.processErrorLocation = processErrorLocation;
    }

    public String getProcessErrorStatus() {
        return processErrorStatus;
    }

    public void setProcessErrorStatus(String processErrorStatus) {
        this.processErrorStatus = processErrorStatus;
    }

    public DbimportErrorCode getProcessErrorCode() {
        return processErrorCode;
    }

    public void setProcessErrorCode(DbimportErrorCode processErrorCode) {
        this.processErrorCode = processErrorCode;
    }

    public DbimportLine getDbimportLine() {
        return dbimportLine;
    }

    public void setDbimportLine(DbimportLine dbimportLine) {
        this.dbimportLine = dbimportLine;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dbimportLineErrorPK != null ? dbimportLineErrorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbimportLineError)) {
            return false;
        }
        DbimportLineError other = (DbimportLineError) object;
        if ((this.dbimportLineErrorPK == null && other.dbimportLineErrorPK != null) || (this.dbimportLineErrorPK != null && !this.dbimportLineErrorPK.equals(other.dbimportLineErrorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbimportLineError[ dbimportLineErrorPK=" + dbimportLineErrorPK + " ]";
    }
    
}
