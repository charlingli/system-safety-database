/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Juan David
 */
@Entity
@Table(name = "db_riskSeverity")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbriskSeverity.findAll", query = "SELECT d FROM DbriskSeverity d")
    , @NamedQuery(name = "DbriskSeverity.findByRiskSeverityId", query = "SELECT d FROM DbriskSeverity d WHERE d.riskSeverityId = :riskSeverityId")
    , @NamedQuery(name = "DbriskSeverity.findBySeverityScore", query = "SELECT d FROM DbriskSeverity d WHERE d.severityScore = :severityScore")})
public class DbriskSeverity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "riskSeverityId")
    private Integer riskSeverityId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "severityScore")
    private String severityScore;
    @Basic(optional = false)
    @NotNull
    @Column(name = "severityValue")
    private int severityValue;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "riskCurrentSeverityId")
    private List<DbHazard> dbHazardList;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "riskTargetSeverityId")
    private List<DbHazard> dbHazardList1;

    public DbriskSeverity() {
    }

    public DbriskSeverity(Integer riskSeverityId) {
        this.riskSeverityId = riskSeverityId;
    }

    public DbriskSeverity(Integer riskSeverityId, String severityScore) {
        this.riskSeverityId = riskSeverityId;
        this.severityScore = severityScore;
    }

    public Integer getRiskSeverityId() {
        return riskSeverityId;
    }

    public void setRiskSeverityId(Integer riskSeverityId) {
        this.riskSeverityId = riskSeverityId;
    }

    public String getSeverityScore() {
        return severityScore;
    }

    public void setSeverityScore(String severityScore) {
        this.severityScore = severityScore;
    }

    @XmlTransient
    public List<DbHazard> getDbHazardList() {
        return dbHazardList;
    }

    public void setDbHazardList(List<DbHazard> dbHazardList) {
        this.dbHazardList = dbHazardList;
    }

    public int getSeverityValue() {
        return severityValue;
    }

    public void setSeverityValue(int severityValue) {
        this.severityValue = severityValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (riskSeverityId != null ? riskSeverityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbriskSeverity)) {
            return false;
        }
        DbriskSeverity other = (DbriskSeverity) object;
        if ((this.riskSeverityId == null && other.riskSeverityId != null) || (this.riskSeverityId != null && !this.riskSeverityId.equals(other.riskSeverityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return severityScore;
    }

}
