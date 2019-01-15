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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
 * @author David Ortega <david.ortega@levelcrossings.vic.gov.au>
 */
@Entity
@Table(name = "db_importLine")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbimportLine.findAll", query = "SELECT d FROM DbimportLine d")
    , @NamedQuery(name = "DbimportLine.findByProcessId", query = "SELECT d FROM DbimportLine d WHERE d.dbimportLinePK.processId = :processId")
    , @NamedQuery(name = "DbimportLine.findByProcessIdLine", query = "SELECT d FROM DbimportLine d WHERE d.dbimportLinePK.processIdLine = :processIdLine")
    , @NamedQuery(name = "DbimportLine.findByHazardContextId", query = "SELECT d FROM DbimportLine d WHERE d.hazardContextId = :hazardContextId")
    , @NamedQuery(name = "DbimportLine.findByHazardContext", query = "SELECT d FROM DbimportLine d WHERE d.hazardContext = :hazardContext")
    , @NamedQuery(name = "DbimportLine.findByHazardLocationId", query = "SELECT d FROM DbimportLine d WHERE d.hazardLocationId = :hazardLocationId")
    , @NamedQuery(name = "DbimportLine.findByHazardLocation", query = "SELECT d FROM DbimportLine d WHERE d.hazardLocation = :hazardLocation")
    , @NamedQuery(name = "DbimportLine.findByHazardActivityId", query = "SELECT d FROM DbimportLine d WHERE d.hazardActivityId = :hazardActivityId")
    , @NamedQuery(name = "DbimportLine.findByHazardActivity", query = "SELECT d FROM DbimportLine d WHERE d.hazardActivity = :hazardActivity")
    , @NamedQuery(name = "DbimportLine.findByHazardOwnerId", query = "SELECT d FROM DbimportLine d WHERE d.hazardOwnerId = :hazardOwnerId")
    , @NamedQuery(name = "DbimportLine.findByHazardOwner", query = "SELECT d FROM DbimportLine d WHERE d.hazardOwner = :hazardOwner")
    , @NamedQuery(name = "DbimportLine.findByHazardTypeId", query = "SELECT d FROM DbimportLine d WHERE d.hazardTypeId = :hazardTypeId")
    , @NamedQuery(name = "DbimportLine.findByHazardType", query = "SELECT d FROM DbimportLine d WHERE d.hazardType = :hazardType")
    , @NamedQuery(name = "DbimportLine.findByHazardStatusId", query = "SELECT d FROM DbimportLine d WHERE d.hazardStatusId = :hazardStatusId")
    , @NamedQuery(name = "DbimportLine.findByHazardStatus", query = "SELECT d FROM DbimportLine d WHERE d.hazardStatus = :hazardStatus")
    , @NamedQuery(name = "DbimportLine.findByHazardRiskClassId", query = "SELECT d FROM DbimportLine d WHERE d.hazardRiskClassId = :hazardRiskClassId")
    , @NamedQuery(name = "DbimportLine.findByHazardRiskClass", query = "SELECT d FROM DbimportLine d WHERE d.hazardRiskClass = :hazardRiskClass")
    , @NamedQuery(name = "DbimportLine.findByHazardCurrentFrequencyId", query = "SELECT d FROM DbimportLine d WHERE d.hazardCurrentFrequencyId = :hazardCurrentFrequencyId")
    , @NamedQuery(name = "DbimportLine.findByHazardCurrentSeverityId", query = "SELECT d FROM DbimportLine d WHERE d.hazardCurrentSeverityId = :hazardCurrentSeverityId")
    , @NamedQuery(name = "DbimportLine.findByHazardTargetFrequencyId", query = "SELECT d FROM DbimportLine d WHERE d.hazardTargetFrequencyId = :hazardTargetFrequencyId")
    , @NamedQuery(name = "DbimportLine.findByHazardTargetSeverityId", query = "SELECT d FROM DbimportLine d WHERE d.hazardTargetSeverityId = :hazardTargetSeverityId")
    , @NamedQuery(name = "DbimportLine.findByHazardDate", query = "SELECT d FROM DbimportLine d WHERE d.hazardDate = :hazardDate")
    , @NamedQuery(name = "DbimportLine.findByHazardWorkshop", query = "SELECT d FROM DbimportLine d WHERE d.hazardWorkshop = :hazardWorkshop")
    , @NamedQuery(name = "DbimportLine.findByHazardLegacyId", query = "SELECT d FROM DbimportLine d WHERE d.hazardLegacyId = :hazardLegacyId")
    , @NamedQuery(name = "DbimportLine.findByHazardHFReview", query = "SELECT d FROM DbimportLine d WHERE d.hazardHFReview = :hazardHFReview")
    , @NamedQuery(name = "DbimportLine.findByRelationType", query = "SELECT d FROM DbimportLine d WHERE d.relationType = :relationType")
    , @NamedQuery(name = "DbimportLine.findByRelationId", query = "SELECT d FROM DbimportLine d WHERE d.relationId = :relationId")
    , @NamedQuery(name = "DbimportLine.findByControlOwnerId", query = "SELECT d FROM DbimportLine d WHERE d.controlOwnerId = :controlOwnerId")
    , @NamedQuery(name = "DbimportLine.findByControlOwner", query = "SELECT d FROM DbimportLine d WHERE d.controlOwner = :controlOwner")
    , @NamedQuery(name = "DbimportLine.findByControlHierarchyId", query = "SELECT d FROM DbimportLine d WHERE d.controlHierarchyId = :controlHierarchyId")
    , @NamedQuery(name = "DbimportLine.findByControlHierarchy", query = "SELECT d FROM DbimportLine d WHERE d.controlHierarchy = :controlHierarchy")
    , @NamedQuery(name = "DbimportLine.findByControlRecommendId", query = "SELECT d FROM DbimportLine d WHERE d.controlRecommendId = :controlRecommendId")
    , @NamedQuery(name = "DbimportLine.findByControlRecommend", query = "SELECT d FROM DbimportLine d WHERE d.controlRecommend = :controlRecommend")
    , @NamedQuery(name = "DbimportLine.findByControlType", query = "SELECT d FROM DbimportLine d WHERE d.controlType = :controlType")
    , @NamedQuery(name = "DbimportLine.findByControlExistingOrProposed", query = "SELECT d FROM DbimportLine d WHERE d.controlExistingOrProposed = :controlExistingOrProposed")})
public class DbimportLine implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DbimportLinePK dbimportLinePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hazardContextId")
    private int hazardContextId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "hazardContext")
    private String hazardContext;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "hazardDescription")
    private String hazardDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hazardLocationId")
    private int hazardLocationId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "hazardLocation")
    private String hazardLocation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hazardActivityId")
    private int hazardActivityId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "hazardActivity")
    private String hazardActivity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hazardOwnerId")
    private int hazardOwnerId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "hazardOwner")
    private String hazardOwner;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hazardTypeId")
    private int hazardTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "hazardType")
    private String hazardType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hazardStatusId")
    private int hazardStatusId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "hazardStatus")
    private String hazardStatus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hazardRiskClassId")
    private int hazardRiskClassId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "hazardRiskClass")
    private String hazardRiskClass;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hazardCurrentFrequencyId")
    private int hazardCurrentFrequencyId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hazardCurrentSeverityId")
    private int hazardCurrentSeverityId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hazardTargetFrequencyId")
    private int hazardTargetFrequencyId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hazardTargetSeverityId")
    private int hazardTargetSeverityId;
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
    @Size(max = 20)
    @Column(name = "hazardLegacyId")
    private String hazardLegacyId;
    @Size(max = 1)
    @Column(name = "hazardHFReview")
    private String hazardHFReview;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "hazardSbs")
    private String hazardSbs;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "relationType")
    private String relationType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "relationId")
    private int relationId;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "relationDescription")
    private String relationDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "controlOwnerId")
    private int controlOwnerId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "controlOwner")
    private String controlOwner;
    @Basic(optional = false)
    @NotNull
    @Column(name = "controlHierarchyId")
    private int controlHierarchyId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "controlHierarchy")
    private String controlHierarchy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "controlRecommendId")
    private int controlRecommendId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "controlRecommend")
    private String controlRecommend;
    @Lob
    @Size(max = 65535)
    @Column(name = "controlJustify")
    private String controlJustify;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "controlType")
    private String controlType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "controlExistingOrProposed")
    private String controlExistingOrProposed;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dbimportLine")
    private List<DbimportLineError> dbimportLineErrorList;
    @JoinColumn(name = "processId", referencedColumnName = "processId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DbimportHeader dbimportHeader;

    public DbimportLine() {
    }

    public DbimportLine(DbimportLinePK dbimportLinePK) {
        this.dbimportLinePK = dbimportLinePK;
    }

    public DbimportLine(DbimportLinePK dbimportLinePK, int hazardContextId, String hazardContext, String hazardDescription, int hazardLocationId, String hazardLocation, int hazardActivityId, String hazardActivity, int hazardOwnerId, String hazardOwner, int hazardTypeId, String hazardType, int hazardStatusId, String hazardStatus, int hazardRiskClassId, String hazardRiskClass, int hazardCurrentFrequencyId, int hazardCurrentSeverityId, int hazardTargetFrequencyId, int hazardTargetSeverityId, Date hazardDate, String hazardWorkshop, String hazardSbs, String relationType, int relationId, String relationDescription, int controlOwnerId, String controlOwner, int controlHierarchyId, String controlHierarchy, int controlRecommendId, String controlRecommend, String controlType, String controlExistingOrProposed) {
        this.dbimportLinePK = dbimportLinePK;
        this.hazardContextId = hazardContextId;
        this.hazardContext = hazardContext;
        this.hazardDescription = hazardDescription;
        this.hazardLocationId = hazardLocationId;
        this.hazardLocation = hazardLocation;
        this.hazardActivityId = hazardActivityId;
        this.hazardActivity = hazardActivity;
        this.hazardOwnerId = hazardOwnerId;
        this.hazardOwner = hazardOwner;
        this.hazardTypeId = hazardTypeId;
        this.hazardType = hazardType;
        this.hazardStatusId = hazardStatusId;
        this.hazardStatus = hazardStatus;
        this.hazardRiskClassId = hazardRiskClassId;
        this.hazardRiskClass = hazardRiskClass;
        this.hazardCurrentFrequencyId = hazardCurrentFrequencyId;
        this.hazardCurrentSeverityId = hazardCurrentSeverityId;
        this.hazardTargetFrequencyId = hazardTargetFrequencyId;
        this.hazardTargetSeverityId = hazardTargetSeverityId;
        this.hazardDate = hazardDate;
        this.hazardWorkshop = hazardWorkshop;
        this.hazardSbs = hazardSbs;
        this.relationType = relationType;
        this.relationId = relationId;
        this.relationDescription = relationDescription;
        this.controlOwnerId = controlOwnerId;
        this.controlOwner = controlOwner;
        this.controlHierarchyId = controlHierarchyId;
        this.controlHierarchy = controlHierarchy;
        this.controlRecommendId = controlRecommendId;
        this.controlRecommend = controlRecommend;
        this.controlType = controlType;
        this.controlExistingOrProposed = controlExistingOrProposed;
    }

    public DbimportLine(String processId, int processIdLine) {
        this.dbimportLinePK = new DbimportLinePK(processId, processIdLine);
    }

    public DbimportLinePK getDbimportLinePK() {
        return dbimportLinePK;
    }

    public void setDbimportLinePK(DbimportLinePK dbimportLinePK) {
        this.dbimportLinePK = dbimportLinePK;
    }

    public int getHazardContextId() {
        return hazardContextId;
    }

    public void setHazardContextId(int hazardContextId) {
        this.hazardContextId = hazardContextId;
    }

    public String getHazardContext() {
        return hazardContext;
    }

    public void setHazardContext(String hazardContext) {
        this.hazardContext = hazardContext;
    }

    public String getHazardDescription() {
        return hazardDescription;
    }

    public void setHazardDescription(String hazardDescription) {
        this.hazardDescription = hazardDescription;
    }

    public int getHazardLocationId() {
        return hazardLocationId;
    }

    public void setHazardLocationId(int hazardLocationId) {
        this.hazardLocationId = hazardLocationId;
    }

    public String getHazardLocation() {
        return hazardLocation;
    }

    public void setHazardLocation(String hazardLocation) {
        this.hazardLocation = hazardLocation;
    }

    public int getHazardActivityId() {
        return hazardActivityId;
    }

    public void setHazardActivityId(int hazardActivityId) {
        this.hazardActivityId = hazardActivityId;
    }

    public String getHazardActivity() {
        return hazardActivity;
    }

    public void setHazardActivity(String hazardActivity) {
        this.hazardActivity = hazardActivity;
    }

    public int getHazardOwnerId() {
        return hazardOwnerId;
    }

    public void setHazardOwnerId(int hazardOwnerId) {
        this.hazardOwnerId = hazardOwnerId;
    }

    public String getHazardOwner() {
        return hazardOwner;
    }

    public void setHazardOwner(String hazardOwner) {
        this.hazardOwner = hazardOwner;
    }

    public int getHazardTypeId() {
        return hazardTypeId;
    }

    public void setHazardTypeId(int hazardTypeId) {
        this.hazardTypeId = hazardTypeId;
    }

    public String getHazardType() {
        return hazardType;
    }

    public void setHazardType(String hazardType) {
        this.hazardType = hazardType;
    }

    public int getHazardStatusId() {
        return hazardStatusId;
    }

    public void setHazardStatusId(int hazardStatusId) {
        this.hazardStatusId = hazardStatusId;
    }

    public String getHazardStatus() {
        return hazardStatus;
    }

    public void setHazardStatus(String hazardStatus) {
        this.hazardStatus = hazardStatus;
    }

    public int getHazardRiskClassId() {
        return hazardRiskClassId;
    }

    public void setHazardRiskClassId(int hazardRiskClassId) {
        this.hazardRiskClassId = hazardRiskClassId;
    }

    public String getHazardRiskClass() {
        return hazardRiskClass;
    }

    public void setHazardRiskClass(String hazardRiskClass) {
        this.hazardRiskClass = hazardRiskClass;
    }

    public int getHazardCurrentFrequencyId() {
        return hazardCurrentFrequencyId;
    }

    public void setHazardCurrentFrequencyId(int hazardCurrentFrequencyId) {
        this.hazardCurrentFrequencyId = hazardCurrentFrequencyId;
    }

    public int getHazardCurrentSeverityId() {
        return hazardCurrentSeverityId;
    }

    public void setHazardCurrentSeverityId(int hazardCurrentSeverityId) {
        this.hazardCurrentSeverityId = hazardCurrentSeverityId;
    }

    public int getHazardTargetFrequencyId() {
        return hazardTargetFrequencyId;
    }

    public void setHazardTargetFrequencyId(int hazardTargetFrequencyId) {
        this.hazardTargetFrequencyId = hazardTargetFrequencyId;
    }

    public int getHazardTargetSeverityId() {
        return hazardTargetSeverityId;
    }

    public void setHazardTargetSeverityId(int hazardTargetSeverityId) {
        this.hazardTargetSeverityId = hazardTargetSeverityId;
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

    public String getHazardLegacyId() {
        return hazardLegacyId;
    }

    public void setHazardLegacyId(String hazardLegacyId) {
        this.hazardLegacyId = hazardLegacyId;
    }

    public String getHazardHFReview() {
        return hazardHFReview;
    }

    public void setHazardHFReview(String hazardHFReview) {
        this.hazardHFReview = hazardHFReview;
    }

    public String getHazardSbs() {
        return hazardSbs;
    }

    public void setHazardSbs(String hazardSbs) {
        this.hazardSbs = hazardSbs;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public int getRelationId() {
        return relationId;
    }

    public void setRelationId(int relationId) {
        this.relationId = relationId;
    }

    public String getRelationDescription() {
        return relationDescription;
    }

    public void setRelationDescription(String relationDescription) {
        this.relationDescription = relationDescription;
    }

    public int getControlOwnerId() {
        return controlOwnerId;
    }

    public void setControlOwnerId(int controlOwnerId) {
        this.controlOwnerId = controlOwnerId;
    }

    public String getControlOwner() {
        return controlOwner;
    }

    public void setControlOwner(String controlOwner) {
        this.controlOwner = controlOwner;
    }

    public int getControlHierarchyId() {
        return controlHierarchyId;
    }

    public void setControlHierarchyId(int controlHierarchyId) {
        this.controlHierarchyId = controlHierarchyId;
    }

    public String getControlHierarchy() {
        return controlHierarchy;
    }

    public void setControlHierarchy(String controlHierarchy) {
        this.controlHierarchy = controlHierarchy;
    }

    public int getControlRecommendId() {
        return controlRecommendId;
    }

    public void setControlRecommendId(int controlRecommendId) {
        this.controlRecommendId = controlRecommendId;
    }

    public String getControlRecommend() {
        return controlRecommend;
    }

    public void setControlRecommend(String controlRecommend) {
        this.controlRecommend = controlRecommend;
    }

    public String getControlJustify() {
        return controlJustify;
    }

    public void setControlJustify(String controlJustify) {
        this.controlJustify = controlJustify;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public String getControlExistingOrProposed() {
        return controlExistingOrProposed;
    }

    public void setControlExistingOrProposed(String controlExistingOrProposed) {
        this.controlExistingOrProposed = controlExistingOrProposed;
    }

    @XmlTransient
    public List<DbimportLineError> getDbimportLineErrorList() {
        return dbimportLineErrorList;
    }

    public void setDbimportLineErrorList(List<DbimportLineError> dbimportLineErrorList) {
        this.dbimportLineErrorList = dbimportLineErrorList;
    }

    public DbimportHeader getDbimportHeader() {
        return dbimportHeader;
    }

    public void setDbimportHeader(DbimportHeader dbimportHeader) {
        this.dbimportHeader = dbimportHeader;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dbimportLinePK != null ? dbimportLinePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbimportLine)) {
            return false;
        }
        DbimportLine other = (DbimportLine) object;
        if ((this.dbimportLinePK == null && other.dbimportLinePK != null) || (this.dbimportLinePK != null && !this.dbimportLinePK.equals(other.dbimportLinePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbimportLine[ dbimportLinePK=" + dbimportLinePK + " ]";
    }
    
}
