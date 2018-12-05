/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author David Ortega <david.ortega@levelcrossings.vic.gov.au>
 */
@Entity
@Table(name = "db_hazard")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbHazard.findAll", query = "SELECT d FROM DbHazard d")
    , @NamedQuery(name = "DbHazard.findByHazardId", query = "SELECT d FROM DbHazard d WHERE d.hazardId = :hazardId")
    , @NamedQuery(name = "DbHazard.findByRiskCurrentScore", query = "SELECT d FROM DbHazard d WHERE d.riskCurrentScore = :riskCurrentScore")
    , @NamedQuery(name = "DbHazard.findByRiskTargetScore", query = "SELECT d FROM DbHazard d WHERE d.riskTargetScore = :riskTargetScore")
    , @NamedQuery(name = "DbHazard.findByHazardDate", query = "SELECT d FROM DbHazard d WHERE d.hazardDate = :hazardDate")
    , @NamedQuery(name = "DbHazard.findByHazardWorkshop", query = "SELECT d FROM DbHazard d WHERE d.hazardWorkshop = :hazardWorkshop")
    , @NamedQuery(name = "DbHazard.findByHazardReview", query = "SELECT d FROM DbHazard d WHERE d.hazardReview = :hazardReview")
    , @NamedQuery(name = "DbHazard.findByLegacyId", query = "SELECT d FROM DbHazard d WHERE d.legacyId = :legacyId")
    , @NamedQuery(name = "DbHazard.findByAddedDateTime", query = "SELECT d FROM DbHazard d WHERE d.addedDateTime = :addedDateTime")
    , @NamedQuery(name = "DbHazard.findByUpdatedDateTime", query = "SELECT d FROM DbHazard d WHERE d.updatedDateTime = :updatedDateTime")
    , @NamedQuery(name = "DbHazard.findByUserIdAdd", query = "SELECT d FROM DbHazard d WHERE d.userIdAdd = :userIdAdd")
    , @NamedQuery(name = "DbHazard.findByUserIdUpdate", query = "SELECT d FROM DbHazard d WHERE d.userIdUpdate = :userIdUpdate")})
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
    @Column(name = "riskCurrentScore")
    private int riskCurrentScore;
    @Basic(optional = false)
    @NotNull
    @Column(name = "riskTargetScore")
    private int riskTargetScore;
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
    @JoinColumn(name = "hazardActivity", referencedColumnName = "activityId")
    @ManyToOne(optional = false)
    private DbhazardActivity hazardActivity;
    @JoinColumn(name = "riskCurrentFrequencyId", referencedColumnName = "riskFrequencyId")
    @ManyToOne(optional = false)
    private DbriskFrequency riskCurrentFrequencyId;
    @JoinColumn(name = "riskCurrentSeverityId", referencedColumnName = "riskSeverityId")
    @ManyToOne(optional = false)
    private DbriskSeverity riskCurrentSeverityId;
    @JoinColumn(name = "hazardContextId", referencedColumnName = "hazardContextId")
    @ManyToOne(optional = false)
    private DbhazardContext hazardContextId;
    @JoinColumn(name = "hazardLocation", referencedColumnName = "locationId")
    @ManyToOne(optional = false)
    private DbLocation hazardLocation;
    @JoinColumn(name = "hazardStatusId", referencedColumnName = "hazardStatusId")
    @ManyToOne(optional = false)
    private DbhazardStatus hazardStatusId;
    @JoinColumn(name = "hazardSystemStatus", referencedColumnName = "systemStatusId")
    @ManyToOne(optional = false)
    private DbhazardSystemStatus hazardSystemStatus;
    @JoinColumn(name = "hazardTypeId", referencedColumnName = "hazardTypeId")
    @ManyToOne(optional = false)
    private DbhazardType hazardTypeId;
    @JoinColumn(name = "ownerId", referencedColumnName = "ownerId")
    @ManyToOne(optional = false)
    private DbOwners ownerId;
    @JoinColumn(name = "riskClassId", referencedColumnName = "riskClassId")
    @ManyToOne(optional = false)
    private DbriskClass riskClassId;
    @JoinColumn(name = "riskTargetFrequencyId", referencedColumnName = "riskFrequencyId")
    @ManyToOne(optional = false)
    private DbriskFrequency riskTargetFrequencyId;
    @JoinColumn(name = "riskTargetSeverityId", referencedColumnName = "riskSeverityId")
    @ManyToOne(optional = false)
    private DbriskSeverity riskTargetSeverityId;

    public DbHazard() {
    }

    public DbHazard(String hazardId) {
        this.hazardId = hazardId;
    }

    public DbHazard(String hazardId, String hazardDescription, int riskCurrentScore, int riskTargetScore, Date hazardDate, String hazardWorkshop, String hazardReview) {
        this.hazardId = hazardId;
        this.hazardDescription = hazardDescription;
        this.riskCurrentScore = riskCurrentScore;
        this.riskTargetScore = riskTargetScore;
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

    public int getRiskCurrentScore() {
        return riskCurrentScore;
    }

    public void setRiskCurrentScore(int riskCurrentScore) {
        this.riskCurrentScore = riskCurrentScore;
    }

    public int getRiskTargetScore() {
        return riskTargetScore;
    }

    public void setRiskTargetScore(int riskTargetScore) {
        this.riskTargetScore = riskTargetScore;
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

    public DbhazardActivity getHazardActivity() {
        return hazardActivity;
    }

    public void setHazardActivity(DbhazardActivity hazardActivity) {
        this.hazardActivity = hazardActivity;
    }

    public DbriskFrequency getRiskCurrentFrequencyId() {
        return riskCurrentFrequencyId;
    }

    public void setRiskCurrentFrequencyId(DbriskFrequency riskCurrentFrequencyId) {
        this.riskCurrentFrequencyId = riskCurrentFrequencyId;
    }

    public DbriskSeverity getRiskCurrentSeverityId() {
        return riskCurrentSeverityId;
    }

    public void setRiskCurrentSeverityId(DbriskSeverity riskCurrentSeverityId) {
        this.riskCurrentSeverityId = riskCurrentSeverityId;
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

    public DbhazardSystemStatus getHazardSystemStatus() {
        return hazardSystemStatus;
    }

    public void setHazardSystemStatus(DbhazardSystemStatus hazardSystemStatus) {
        this.hazardSystemStatus = hazardSystemStatus;
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

    public DbriskFrequency getRiskTargetFrequencyId() {
        return riskTargetFrequencyId;
    }

    public void setRiskTargetFrequencyId(DbriskFrequency riskTargetFrequencyId) {
        this.riskTargetFrequencyId = riskTargetFrequencyId;
    }

    public DbriskSeverity getRiskTargetSeverityId() {
        return riskTargetSeverityId;
    }

    public void setRiskTargetSeverityId(DbriskSeverity riskTargetSeverityId) {
        this.riskTargetSeverityId = riskTargetSeverityId;
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
        return (this.hazardId != null && other.hazardId != null) && this.hazardId.equals(other.hazardId) && this.hazardContextId.equals(other.hazardContextId)
                && this.hazardDescription.equals(other.hazardDescription) && this.hazardLocation.equals(other.hazardLocation) && this.hazardActivity.equals(other.hazardActivity)
                && this.ownerId.equals(other.ownerId) && this.hazardTypeId.equals(other.hazardTypeId) && this.hazardStatusId.equals(other.hazardStatusId)
                && this.riskClassId.equals(other.riskClassId) && this.riskCurrentFrequencyId.equals(other.riskCurrentFrequencyId)
                && this.riskCurrentSeverityId.equals(other.riskCurrentSeverityId) && this.riskCurrentScore == other.riskCurrentScore
                && this.riskTargetFrequencyId.equals(other.riskTargetFrequencyId) && this.riskCurrentSeverityId.equals(other.riskCurrentSeverityId)
                && this.riskTargetScore == other.riskTargetScore && this.hazardComment.equals(other.hazardComment) && this.hazardDate.equals(other.hazardDate)
                && this.hazardWorkshop.equals(other.hazardWorkshop) && this.hazardReview.equals(other.hazardReview) && this.legacyId.equals(other.legacyId);
    }
}
