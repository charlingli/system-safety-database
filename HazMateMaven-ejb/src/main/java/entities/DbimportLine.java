/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
    @Column(name = "hazardContextId")
    private Integer hazardContextId;
    @Size(max = 70)
    @Column(name = "hazardContext")
    private String hazardContext;
    @Lob
    @Size(max = 65535)
    @Column(name = "hazardDescription")
    private String hazardDescription;
    @Column(name = "hazardLocationId")
    private Integer hazardLocationId;
    @Size(max = 45)
    @Column(name = "hazardLocation")
    private String hazardLocation;
    @Column(name = "hazardActivityId")
    private Integer hazardActivityId;
    @Size(max = 45)
    @Column(name = "hazardActivity")
    private String hazardActivity;
    @Column(name = "hazardOwnerId")
    private Integer hazardOwnerId;
    @Size(max = 100)
    @Column(name = "hazardOwner")
    private String hazardOwner;
    @Column(name = "hazardTypeId")
    private Integer hazardTypeId;
    @Size(max = 45)
    @Column(name = "hazardType")
    private String hazardType;
    @Column(name = "hazardStatusId")
    private Integer hazardStatusId;
    @Size(max = 45)
    @Column(name = "hazardStatus")
    private String hazardStatus;
    @Column(name = "hazardRiskClassId")
    private Integer hazardRiskClassId;
    @Size(max = 45)
    @Column(name = "hazardRiskClass")
    private String hazardRiskClass;
    @Column(name = "hazardCurrentFrequencyId")
    private Integer hazardCurrentFrequencyId;
    @Column(name = "hazardCurrentSeverityId")
    private Integer hazardCurrentSeverityId;
    @Column(name = "hazardTargetFrequencyId")
    private Integer hazardTargetFrequencyId;
    @Column(name = "hazardTargetSeverityId")
    private Integer hazardTargetSeverityId;
    @Lob
    @Size(max = 65535)
    @Column(name = "hazardComment")
    private String hazardComment;
    @Column(name = "hazardDate")
    @Temporal(TemporalType.DATE)
    private Date hazardDate;
    @Size(max = 500)
    @Column(name = "hazardWorkshop")
    private String hazardWorkshop;
    @Size(max = 20)
    @Column(name = "hazardLegacyId")
    private String hazardLegacyId;
    @Size(max = 20)
    @Column(name = "hazardHFReview")
    private String hazardHFReview;
    @Lob
    @Size(max = 65535)
    @Column(name = "hazardSbs")
    private String hazardSbs;
    @Size(max = 20)
    @Column(name = "relationType")
    private String relationType;
    @Column(name = "relationId")
    private Integer relationId;
    @Lob
    @Size(max = 65535)
    @Column(name = "relationDescription")
    private String relationDescription;
    @Column(name = "controlOwnerId")
    private Integer controlOwnerId;
    @Size(max = 100)
    @Column(name = "controlOwner")
    private String controlOwner;
    @Column(name = "controlHierarchyId")
    private Integer controlHierarchyId;
    @Size(max = 45)
    @Column(name = "controlHierarchy")
    private String controlHierarchy;
    @Column(name = "controlRecommendId")
    private Integer controlRecommendId;
    @Size(max = 45)
    @Column(name = "controlRecommend")
    private String controlRecommend;
    @Lob
    @Size(max = 65535)
    @Column(name = "controlJustify")
    private String controlJustify;
    @Size(max = 20)
    @Column(name = "controlType")
    private String controlType;
    @Size(max = 20)
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

    public DbimportLine(String processId, int processIdLine) {
        this.dbimportLinePK = new DbimportLinePK(processId, processIdLine);
    }

    public DbimportLinePK getDbimportLinePK() {
        return dbimportLinePK;
    }

    public void setDbimportLinePK(DbimportLinePK dbimportLinePK) {
        this.dbimportLinePK = dbimportLinePK;
    }

    public Integer getHazardContextId() {
        return hazardContextId;
    }

    public void setHazardContextId(Integer hazardContextId) {
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

    public Integer getHazardLocationId() {
        return hazardLocationId;
    }

    public void setHazardLocationId(Integer hazardLocationId) {
        this.hazardLocationId = hazardLocationId;
    }

    public String getHazardLocation() {
        return hazardLocation;
    }

    public void setHazardLocation(String hazardLocation) {
        this.hazardLocation = hazardLocation;
    }

    public Integer getHazardActivityId() {
        return hazardActivityId;
    }

    public void setHazardActivityId(Integer hazardActivityId) {
        this.hazardActivityId = hazardActivityId;
    }

    public String getHazardActivity() {
        return hazardActivity;
    }

    public void setHazardActivity(String hazardActivity) {
        this.hazardActivity = hazardActivity;
    }

    public Integer getHazardOwnerId() {
        return hazardOwnerId;
    }

    public void setHazardOwnerId(Integer hazardOwnerId) {
        this.hazardOwnerId = hazardOwnerId;
    }

    public String getHazardOwner() {
        return hazardOwner;
    }

    public void setHazardOwner(String hazardOwner) {
        this.hazardOwner = hazardOwner;
    }

    public Integer getHazardTypeId() {
        return hazardTypeId;
    }

    public void setHazardTypeId(Integer hazardTypeId) {
        this.hazardTypeId = hazardTypeId;
    }

    public String getHazardType() {
        return hazardType;
    }

    public void setHazardType(String hazardType) {
        this.hazardType = hazardType;
    }

    public Integer getHazardStatusId() {
        return hazardStatusId;
    }

    public void setHazardStatusId(Integer hazardStatusId) {
        this.hazardStatusId = hazardStatusId;
    }

    public String getHazardStatus() {
        return hazardStatus;
    }

    public void setHazardStatus(String hazardStatus) {
        this.hazardStatus = hazardStatus;
    }

    public Integer getHazardRiskClassId() {
        return hazardRiskClassId;
    }

    public void setHazardRiskClassId(Integer hazardRiskClassId) {
        this.hazardRiskClassId = hazardRiskClassId;
    }

    public String getHazardRiskClass() {
        return hazardRiskClass;
    }

    public void setHazardRiskClass(String hazardRiskClass) {
        this.hazardRiskClass = hazardRiskClass;
    }

    public Integer getHazardCurrentFrequencyId() {
        return hazardCurrentFrequencyId;
    }

    public void setHazardCurrentFrequencyId(Integer hazardCurrentFrequencyId) {
        this.hazardCurrentFrequencyId = hazardCurrentFrequencyId;
    }

    public Integer getHazardCurrentSeverityId() {
        return hazardCurrentSeverityId;
    }

    public void setHazardCurrentSeverityId(Integer hazardCurrentSeverityId) {
        this.hazardCurrentSeverityId = hazardCurrentSeverityId;
    }

    public Integer getHazardTargetFrequencyId() {
        return hazardTargetFrequencyId;
    }

    public void setHazardTargetFrequencyId(Integer hazardTargetFrequencyId) {
        this.hazardTargetFrequencyId = hazardTargetFrequencyId;
    }

    public Integer getHazardTargetSeverityId() {
        return hazardTargetSeverityId;
    }

    public void setHazardTargetSeverityId(Integer hazardTargetSeverityId) {
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

    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public String getRelationDescription() {
        return relationDescription;
    }

    public void setRelationDescription(String relationDescription) {
        this.relationDescription = relationDescription;
    }

    public Integer getControlOwnerId() {
        return controlOwnerId;
    }

    public void setControlOwnerId(Integer controlOwnerId) {
        this.controlOwnerId = controlOwnerId;
    }

    public String getControlOwner() {
        return controlOwner;
    }

    public void setControlOwner(String controlOwner) {
        this.controlOwner = controlOwner;
    }

    public Integer getControlHierarchyId() {
        return controlHierarchyId;
    }

    public void setControlHierarchyId(Integer controlHierarchyId) {
        this.controlHierarchyId = controlHierarchyId;
    }

    public String getControlHierarchy() {
        return controlHierarchy;
    }

    public void setControlHierarchy(String controlHierarchy) {
        this.controlHierarchy = controlHierarchy;
    }

    public Integer getControlRecommendId() {
        return controlRecommendId;
    }

    public void setControlRecommendId(Integer controlRecommendId) {
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
