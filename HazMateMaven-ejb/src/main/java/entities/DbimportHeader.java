/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author David Ortega <david.ortega@levelcrossings.vic.gov.au>
 */
@Entity
@Table(name = "db_importHeader")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbimportHeader.findAll", query = "SELECT d FROM DbimportHeader d")
    , @NamedQuery(name = "DbimportHeader.findByProcessId", query = "SELECT d FROM DbimportHeader d WHERE d.processId = :processId")
    , @NamedQuery(name = "DbimportHeader.findByDateTime", query = "SELECT d FROM DbimportHeader d WHERE d.dateTime = :dateTime")
    , @NamedQuery(name = "DbimportHeader.findByUserId", query = "SELECT d FROM DbimportHeader d WHERE d.userId = :userId")
    , @NamedQuery(name = "DbimportHeader.findByFileName", query = "SELECT d FROM DbimportHeader d WHERE d.fileName = :fileName")
    , @NamedQuery(name = "DbimportHeader.findByTotalLines", query = "SELECT d FROM DbimportHeader d WHERE d.totalLines = :totalLines")
    , @NamedQuery(name = "DbimportHeader.findByProcessStatus", query = "SELECT d FROM DbimportHeader d WHERE d.processStatus = :processStatus")})
public class DbimportHeader implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "processId")
    private String processId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "userId")
    private int userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "fileName")
    private String fileName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "totalLines")
    private int totalLines;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "processStatus")
    private String processStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dbimportHeader")
    private List<DbimportLine> dbimportLineList;

    public DbimportHeader() {
    }

    public DbimportHeader(String processId) {
        this.processId = processId;
    }

    public DbimportHeader(String processId, Date dateTime, int userId, String fileName, int totalLines, String processStatus) {
        this.processId = processId;
        this.dateTime = dateTime;
        this.userId = userId;
        this.fileName = fileName;
        this.totalLines = totalLines;
        this.processStatus = processStatus;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(int totalLines) {
        this.totalLines = totalLines;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    @XmlTransient
    public List<DbimportLine> getDbimportLineList() {
        return dbimportLineList;
    }

    public void setDbimportLineList(List<DbimportLine> dbimportLineList) {
        this.dbimportLineList = dbimportLineList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (processId != null ? processId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbimportHeader)) {
            return false;
        }
        DbimportHeader other = (DbimportHeader) object;
        if ((this.processId == null && other.processId != null) || (this.processId != null && !this.processId.equals(other.processId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbimportHeader[ processId=" + processId + " ]";
    }
    
}
