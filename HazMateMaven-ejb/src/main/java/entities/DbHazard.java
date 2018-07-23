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
 * @author Juan David
 */
@Entity
@Table(name = "db_hazard")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbHazard.findAll", query = "SELECT d FROM DbHazard d")
    , @NamedQuery(name = "DbHazard.findByHazardId", query = "SELECT d FROM DbHazard d WHERE d.hazardId = :hazardId")
    , @NamedQuery(name = "DbHazard.findByRiskScore", query = "SELECT d FROM DbHazard d WHERE d.riskScore = :riskScore")
    , @NamedQuery(name = "DbHazard.findByHazardDate", query = "SELECT d FROM DbHazard d WHERE d.hazardDate = :hazardDate")
    , @NamedQuery(name = "DbHazard.findByHazardWorkshop", query = "SELECT d FROM DbHazard d WHERE d.hazardWorkshop = :hazardWorkshop")
    , @NamedQuery(name = "DbHazard.findByHazardReview", query = "SELECT d FROM DbHazard d WHERE d.hazardReview = :hazardReview")})
public class DbHazard implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "hazardId")
    private String hazardId;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "hazardDescription")
    private String hazardDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "riskScore")
    private int riskScore;
    @Lob
    @Size(max = 65535)
    @Column(name = "hazardComment")
    private String hazardComment;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hazardDate")
    @Temporal(TemporalType.DATE)
    private Date hazardDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "hazardWorkshop")
    private String hazardWorkshop;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "hazardReview")
    private String hazardReview;
    @Size(max = 20)
    @Column(name = "legacyId")
    private String legacyId;
    @Column(name = "addedDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date addedDateTime;
    @Column(name = "updatedDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDateTime;
    @Column(name = "userIdAdd")
    private Integer userIdAdd;
    @Column(name = "userIdUpdate")
    private Integer userIdUpdate;
    @OneToMany(mappedBy = "dbHazard")
    private List<DbHazardConsequence> dbHazardConsequenceList;
    @OneToMany(mappedBy = "dbHazard")
    private List<DbControlHazard> dbControlHazardList;
    @JoinColumn(name = "hazardActivity", referencedColumnName = "activityId")
    @ManyToOne(optional = false)
    private DbhazardActivity hazardActivity;
    @JoinColumn(name = "hazardContextId", referencedColumnName = "hazardContextId")
    @ManyToOne(optional = false)
    private DbhazardContext hazardContextId;
    @JoinColumn(name = "hazardLocation", referencedColumnName = "locationId")
    @ManyToOne(optional = false)
    private DbLocation hazardLocation;
    @JoinColumn(name = "hazardStatusId", referencedColumnName = "hazardStatusId")
    @ManyToOne(optional = false)
    private DbhazardStatus hazardStatusId;
    @JoinColumn(name = "hazardTypeId", referencedColumnName = "hazardTypeId")
    @ManyToOne(optional = false)
    private DbhazardType hazardTypeId;
    @JoinColumn(name = "ownerId", referencedColumnName = "ownerId")
    @ManyToOne(optional = false)
    private DbOwners ownerId;
    @JoinColumn(name = "riskClassId", referencedColumnName = "riskClassId")
    @ManyToOne(optional = false)
    private DbriskClass riskClassId;
    @JoinColumn(name = "riskFrequencyId", referencedColumnName = "riskFrequencyId")
    @ManyToOne(optional = false)
    private DbriskFrequency riskFrequencyId;
    @JoinColumn(name = "riskSeverityId", referencedColumnName = "riskSeverityId")
    @ManyToOne(optional = false)
    private DbriskSeverity riskSeverityId;
    @OneToMany(mappedBy = "dbHazard")
    private List<DbHazardSbs> dbHazardSbsList;
    @OneToMany(mappedBy = "dbHazard")
    private List<DbHazardCause> dbHazardCauseList;

    public DbHazard() {
    }

    public DbHazard(String hazardId) {
        this.hazardId = hazardId;
    }

    public DbHazard(String hazardId, String hazardDescription, int riskScore, Date hazardDate, String hazardWorkshop, String hazardReview) {
        this.hazardId = hazardId;
        this.hazardDescription = hazardDescription;
        this.riskScore = riskScore;
        this.hazardDate = hazardDate;
        this.hazardWorkshop = hazardWorkshop;
        this.hazardReview = hazardReview;
    }

    public String getHazardId() {
        return hazardId;
    }

    public void setHazardId(String hazardId) {
        this.hazardId = hazardId;
    }

    public String getHazardDescription() {
        return hazardDescription;
    }

    public void setHazardDescription(String hazardDescription) {
        this.hazardDescription = hazardDescription;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(int riskScore) {
        this.riskScore = riskScore;
    }

    public String getHazardComment() {
        return hazardComment;
    }

    public void setHazardComment(String hazardComment) {
        this.hazardComment = hazardComment;
    }

    public Date getHazardDate() {
        return hazardDate;
    }

    public void setHazardDate(Date hazardDate) {
        this.hazardDate = hazardDate;
    }

    public String getHazardWorkshop() {
        return hazardWorkshop;
    }

    public void setHazardWorkshop(String hazardWorkshop) {
        this.hazardWorkshop = hazardWorkshop;
    }

    public String getHazardReview() {
        return hazardReview;
    }

    public void setHazardReview(String hazardReview) {
        this.hazardReview = hazardReview;
    }

    @XmlTransient
    public List<DbHazardConsequence> getDbHazardConsequenceList() {
        return dbHazardConsequenceList;
    }

    public void setDbHazardConsequenceList(List<DbHazardConsequence> dbHazardConsequenceList) {
        this.dbHazardConsequenceList = dbHazardConsequenceList;
    }

    @XmlTransient
    public List<DbControlHazard> getDbControlHazardList() {
        return dbControlHazardList;
    }

    public void setDbControlHazardList(List<DbControlHazard> dbControlHazardList) {
        this.dbControlHazardList = dbControlHazardList;
    }

    public DbhazardActivity getHazardActivity() {
        return hazardActivity;
    }

    public void setHazardActivity(DbhazardActivity hazardActivity) {
        this.hazardActivity = hazardActivity;
    }

    public DbhazardContext getHazardContextId() {
        return hazardContextId;
    }

    public void setHazardContextId(DbhazardContext hazardContextId) {
        this.hazardContextId = hazardContextId;
    }

    public DbLocation getHazardLocation() {
        return hazardLocation;
    }

    public void setHazardLocation(DbLocation hazardLocation) {
        this.hazardLocation = hazardLocation;
    }

    public DbhazardStatus getHazardStatusId() {
        return hazardStatusId;
    }

    public void setHazardStatusId(DbhazardStatus hazardStatusId) {
        this.hazardStatusId = hazardStatusId;
    }

    public DbhazardType getHazardTypeId() {
        return hazardTypeId;
    }

    public void setHazardTypeId(DbhazardType hazardTypeId) {
        this.hazardTypeId = hazardTypeId;
    }

    public DbOwners getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(DbOwners ownerId) {
        this.ownerId = ownerId;
    }

    public DbriskClass getRiskClassId() {
        return riskClassId;
    }

    public void setRiskClassId(DbriskClass riskClassId) {
        this.riskClassId = riskClassId;
    }

    public DbriskFrequency getRiskFrequencyId() {
        return riskFrequencyId;
    }

    public void setRiskFrequencyId(DbriskFrequency riskFrequencyId) {
        this.riskFrequencyId = riskFrequencyId;
    }

    public DbriskSeverity getRiskSeverityId() {
        return riskSeverityId;
    }

    public void setRiskSeverityId(DbriskSeverity riskSeverityId) {
        this.riskSeverityId = riskSeverityId;
    }

    @XmlTransient
    public List<DbHazardSbs> getDbHazardSbsList() {
        return dbHazardSbsList;
    }

    public void setDbHazardSbsList(List<DbHazardSbs> dbHazardSbsList) {
        this.dbHazardSbsList = dbHazardSbsList;
    }

    @XmlTransient
    public List<DbHazardCause> getDbHazardCauseList() {
        return dbHazardCauseList;
    }

    public void setDbHazardCauseList(List<DbHazardCause> dbHazardCauseList) {
        this.dbHazardCauseList = dbHazardCauseList;
    }

    public String getLegacyId() {
        return legacyId;
    }

    public void setLegacyId(String legacyId) {
        this.legacyId = legacyId;
    }

    public Date getAddedDateTime() {
        return addedDateTime;
    }

    public void setAddedDateTime(Date addedDateTime) {
        this.addedDateTime = addedDateTime;
    }

    public Date getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(Date updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public Integer getUserIdAdd() {
        return userIdAdd;
    }

    public void setUserIdAdd(Integer userIdAdd) {
        this.userIdAdd = userIdAdd;
    }

    public Integer getUserIdUpdate() {
        return userIdUpdate;
    }

    public void setUserIdUpdate(Integer userIdUpdate) {
        this.userIdUpdate = userIdUpdate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hazardId != null ? hazardId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbHazard)) {
            return false;
        }
        DbHazard other = (DbHazard) object;
        if ((this.hazardId == null && other.hazardId != null) || (this.hazardId != null && !this.hazardId.equals(other.hazardId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbHazard[ hazardId=" + hazardId + " ]";
    }

    public boolean equalsContent(Object object) {
        DbHazard other = (DbHazard) object;
        if ((this.hazardId != null && other.hazardId != null) && this.hazardId.equals(other.hazardId) && this.hazardContextId.equals(other.hazardContextId)
                && this.hazardDescription.equals(other.hazardDescription) && this.hazardLocation.equals(other.hazardLocation) && this.hazardActivity.equals(other.hazardActivity)
                && this.ownerId.equals(other.ownerId) && this.hazardTypeId.equals(other.hazardTypeId) && this.hazardStatusId.equals(other.hazardStatusId)
                && this.riskClassId.equals(other.riskClassId) && this.riskFrequencyId.equals(other.riskFrequencyId) && this.riskSeverityId.equals(other.riskSeverityId)
                && this.riskScore == other.riskScore && this.hazardComment.equals(other.hazardComment) && this.hazardDate.equals(other.hazardDate)
                && this.hazardWorkshop.equals(other.hazardWorkshop) && this.hazardReview.equals(other.hazardReview) && this.legacyId.equals(other.legacyId)) {
            return true;
        }
        return false;
    }

}
