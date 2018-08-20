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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
 * @author lxra
 */
@Entity
@Table(name = "db_wfHeader")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbwfHeader.findAll", query = "SELECT d FROM DbwfHeader d")
    , @NamedQuery(name = "DbwfHeader.findByWfId", query = "SELECT d FROM DbwfHeader d WHERE d.wfId = :wfId")
    , @NamedQuery(name = "DbwfHeader.findByWfStatus", query = "SELECT d FROM DbwfHeader d WHERE d.wfStatus = :wfStatus")
    , @NamedQuery(name = "DbwfHeader.findByWfAddedDateTime", query = "SELECT d FROM DbwfHeader d WHERE d.wfAddedDateTime = :wfAddedDateTime")
    , @NamedQuery(name = "DbwfHeader.findByWfUpdatedDateTime", query = "SELECT d FROM DbwfHeader d WHERE d.wfUpdatedDateTime = :wfUpdatedDateTime")
    , @NamedQuery(name = "DbwfHeader.findByWfStatusBefUpdate", query = "SELECT d FROM DbwfHeader d WHERE d.wfStatusBefUpdate = :wfStatusBefUpdate")
    , @NamedQuery(name = "DbwfHeader.findByWfObjectId", query = "SELECT d FROM DbwfHeader d WHERE d.wfObjectId = :wfObjectId")
    , @NamedQuery(name = "DbwfHeader.findByWfObjectName", query = "SELECT d FROM DbwfHeader d WHERE d.wfObjectName = :wfObjectName")
    , @NamedQuery(name = "DbwfHeader.findByWfComment3", query = "SELECT d FROM DbwfHeader d WHERE d.wfComment3 = :wfComment3")
    , @NamedQuery(name = "DbwfHeader.findByWfComment4", query = "SELECT d FROM DbwfHeader d WHERE d.wfComment4 = :wfComment4")
    , @NamedQuery(name = "DbwfHeader.findByWfCompleteMethod", query = "SELECT d FROM DbwfHeader d WHERE d.wfCompleteMethod = :wfCompleteMethod")})
public class DbwfHeader implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "wfId")
    private String wfId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "wfStatus")
    private String wfStatus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wfAddedDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wfAddedDateTime;
    @Column(name = "wfUpdatedDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wfUpdatedDateTime;
    @Size(max = 2)
    @Column(name = "wfStatusBefUpdate")
    private String wfStatusBefUpdate;
    @Size(max = 20)
    @Column(name = "wfObjectId")
    private String wfObjectId;
    @Size(max = 45)
    @Column(name = "wfObjectName")
    private String wfObjectName;
    @Lob
    @Size(max = 65535)
    @Column(name = "wfComment1")
    private String wfComment1;
    @Lob
    @Size(max = 65535)
    @Column(name = "wfComment2")
    private String wfComment2;
    @Size(max = 150)
    @Column(name = "wfComment3")
    private String wfComment3;
    @Size(max = 150)
    @Column(name = "wfComment4")
    private String wfComment4;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "wfCompleteMethod")
    private String wfCompleteMethod;
    @JoinColumn(name = "wfUserIdAdd", referencedColumnName = "userId")
    @ManyToOne(optional = false)
    private DbUser wfUserIdAdd;
    @JoinColumn(name = "wfTypeId", referencedColumnName = "wfTypeId")
    @ManyToOne(optional = false)
    private DbwfType wfTypeId;
    @JoinColumn(name = "wfUserIdUpdate", referencedColumnName = "userId")
    @ManyToOne
    private DbUser wfUserIdUpdate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dbwfHeader")
    private List<DbwfLine> dbwfLineList;

    public DbwfHeader() {
    }

    public DbwfHeader(String wfId) {
        this.wfId = wfId;
    }

    public DbwfHeader(String wfId, String wfStatus, Date wfAddedDateTime, String wfCompleteMethod) {
        this.wfId = wfId;
        this.wfStatus = wfStatus;
        this.wfAddedDateTime = wfAddedDateTime;
        this.wfCompleteMethod = wfCompleteMethod;
    }

    public String getWfId() {
        return wfId;
    }

    public void setWfId(String wfId) {
        this.wfId = wfId;
    }

    public String getWfStatus() {
        return wfStatus;
    }

    public void setWfStatus(String wfStatus) {
        this.wfStatus = wfStatus;
    }

    public Date getWfAddedDateTime() {
        return wfAddedDateTime;
    }

    public void setWfAddedDateTime(Date wfAddedDateTime) {
        this.wfAddedDateTime = wfAddedDateTime;
    }

    public Date getWfUpdatedDateTime() {
        return wfUpdatedDateTime;
    }

    public void setWfUpdatedDateTime(Date wfUpdatedDateTime) {
        this.wfUpdatedDateTime = wfUpdatedDateTime;
    }

    public String getWfStatusBefUpdate() {
        return wfStatusBefUpdate;
    }

    public void setWfStatusBefUpdate(String wfStatusBefUpdate) {
        this.wfStatusBefUpdate = wfStatusBefUpdate;
    }

    public String getWfObjectId() {
        return wfObjectId;
    }

    public void setWfObjectId(String wfObjectId) {
        this.wfObjectId = wfObjectId;
    }

    public String getWfObjectName() {
        return wfObjectName;
    }

    public void setWfObjectName(String wfObjectName) {
        this.wfObjectName = wfObjectName;
    }

    public String getWfComment1() {
        return wfComment1;
    }

    public void setWfComment1(String wfComment1) {
        this.wfComment1 = wfComment1;
    }

    public String getWfComment2() {
        return wfComment2;
    }

    public void setWfComment2(String wfComment2) {
        this.wfComment2 = wfComment2;
    }

    public String getWfComment3() {
        return wfComment3;
    }

    public void setWfComment3(String wfComment3) {
        this.wfComment3 = wfComment3;
    }

    public String getWfComment4() {
        return wfComment4;
    }

    public void setWfComment4(String wfComment4) {
        this.wfComment4 = wfComment4;
    }

    public String getWfCompleteMethod() {
        return wfCompleteMethod;
    }

    public void setWfCompleteMethod(String wfCompleteMethod) {
        this.wfCompleteMethod = wfCompleteMethod;
    }

    public DbUser getWfUserIdAdd() {
        return wfUserIdAdd;
    }

    public void setWfUserIdAdd(DbUser wfUserIdAdd) {
        this.wfUserIdAdd = wfUserIdAdd;
    }

    public DbwfType getWfTypeId() {
        return wfTypeId;
    }

    public void setWfTypeId(DbwfType wfTypeId) {
        this.wfTypeId = wfTypeId;
    }

    public DbUser getWfUserIdUpdate() {
        return wfUserIdUpdate;
    }

    public void setWfUserIdUpdate(DbUser wfUserIdUpdate) {
        this.wfUserIdUpdate = wfUserIdUpdate;
    }

    @XmlTransient
    public List<DbwfLine> getDbwfLineList() {
        return dbwfLineList;
    }

    public void setDbwfLineList(List<DbwfLine> dbwfLineList) {
        this.dbwfLineList = dbwfLineList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wfId != null ? wfId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbwfHeader)) {
            return false;
        }
        DbwfHeader other = (DbwfHeader) object;
        if ((this.wfId == null && other.wfId != null) || (this.wfId != null && !this.wfId.equals(other.wfId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbwfHeader[ wfId=" + wfId + " ]";
    }
    
}
