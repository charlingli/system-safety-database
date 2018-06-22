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
@Table(name = "db_riskClass")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbriskClass.findAll", query = "SELECT d FROM DbriskClass d")
    , @NamedQuery(name = "DbriskClass.findByRiskClassId", query = "SELECT d FROM DbriskClass d WHERE d.riskClassId = :riskClassId")
    , @NamedQuery(name = "DbriskClass.findByRiskClassName", query = "SELECT d FROM DbriskClass d WHERE d.riskClassName = :riskClassName")})
public class DbriskClass implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "riskClassId")
    private Integer riskClassId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "riskClassName")
    private String riskClassName;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "riskClassId")
    private List<DbHazard> dbHazardList;

    public DbriskClass() {
    }

    public DbriskClass(Integer riskClassId) {
        this.riskClassId = riskClassId;
    }

    public DbriskClass(Integer riskClassId, String riskClassName) {
        this.riskClassId = riskClassId;
        this.riskClassName = riskClassName;
    }

    public Integer getRiskClassId() {
        return riskClassId;
    }

    public void setRiskClassId(Integer riskClassId) {
        this.riskClassId = riskClassId;
    }

    public String getRiskClassName() {
        return riskClassName;
    }

    public void setRiskClassName(String riskClassName) {
        this.riskClassName = riskClassName;
    }

    @XmlTransient
    public List<DbHazard> getDbHazardList() {
        return dbHazardList;
    }

    public void setDbHazardList(List<DbHazard> dbHazardList) {
        this.dbHazardList = dbHazardList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (riskClassId != null ? riskClassId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbriskClass)) {
            return false;
        }
        DbriskClass other = (DbriskClass) object;
        if ((this.riskClassId == null && other.riskClassId != null) || (this.riskClassId != null && !this.riskClassId.equals(other.riskClassId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return riskClassName;
    }
    
}
