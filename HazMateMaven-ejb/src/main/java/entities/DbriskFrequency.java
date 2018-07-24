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
@Table(name = "db_riskFrequency")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbriskFrequency.findAll", query = "SELECT d FROM DbriskFrequency d")
    , @NamedQuery(name = "DbriskFrequency.findByRiskFrequencyId", query = "SELECT d FROM DbriskFrequency d WHERE d.riskFrequencyId = :riskFrequencyId")
    , @NamedQuery(name = "DbriskFrequency.findByFrequencyScore", query = "SELECT d FROM DbriskFrequency d WHERE d.frequencyScore = :frequencyScore")})
public class DbriskFrequency implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "riskFrequencyId")
    private Integer riskFrequencyId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "frequencyScore")
    private String frequencyScore;
    @Basic(optional = false)
    @NotNull
    @Column(name = "frequencyValue")
    private int frequencyValue;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "riskFrequencyId")
    private List<DbHazard> dbHazardList;

    public DbriskFrequency() {
    }

    public DbriskFrequency(Integer riskFrequencyId) {
        this.riskFrequencyId = riskFrequencyId;
    }

    public DbriskFrequency(Integer riskFrequencyId, String frequencyScore, int frequencyValue) {
        this.riskFrequencyId = riskFrequencyId;
        this.frequencyScore = frequencyScore;
        this.frequencyValue = frequencyValue;
    }

    public Integer getRiskFrequencyId() {
        return riskFrequencyId;
    }

    public void setRiskFrequencyId(Integer riskFrequencyId) {
        this.riskFrequencyId = riskFrequencyId;
    }

    public String getFrequencyScore() {
        return frequencyScore;
    }

    public void setFrequencyScore(String frequencyScore) {
        this.frequencyScore = frequencyScore;
    }
    
    public int getFrequencyValue() {
        return frequencyValue;
    }

    public void setFrequencyValue(int frequencyValue) {
        this.frequencyValue = frequencyValue;
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
        hash += (riskFrequencyId != null ? riskFrequencyId.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbriskFrequency)) {
            return false;
        }
        DbriskFrequency other = (DbriskFrequency) object;
        if ((this.riskFrequencyId == null && other.riskFrequencyId != null) || (this.riskFrequencyId != null && !this.riskFrequencyId.equals(other.riskFrequencyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return frequencyScore;
    }

}
