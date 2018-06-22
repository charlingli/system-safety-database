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
import javax.persistence.Lob;
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
@Table(name = "db_consequence")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbConsequence.findAll", query = "SELECT d FROM DbConsequence d")
    , @NamedQuery(name = "DbConsequence.findByConsequenceId", query = "SELECT d FROM DbConsequence d WHERE d.consequenceId = :consequenceId")
    , @NamedQuery(name = "DbConsequence.findByHazardId", query = "SELECT d FROM DbConsequence d WHERE d.hazardId = :hazardId")})
public class DbConsequence implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "consequenceId")
    private Integer consequenceId;
    @Size(max = 20)
    @Column(name = "hazardId")
    private String hazardId;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "consequenceDescription")
    private String consequenceDescription;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dbConsequence")
    private List<DbHazardConsequence> dbHazardConsequenceList;

    public DbConsequence() {
    }

    public DbConsequence(Integer consequenceId) {
        this.consequenceId = consequenceId;
    }

    public DbConsequence(Integer consequenceId, String consequenceDescription) {
        this.consequenceId = consequenceId;
        this.consequenceDescription = consequenceDescription;
    }

    public Integer getConsequenceId() {
        return consequenceId;
    }

    public void setConsequenceId(Integer consequenceId) {
        this.consequenceId = consequenceId;
    }

    public String getHazardId() {
        return hazardId;
    }

    public void setHazardId(String hazardId) {
        this.hazardId = hazardId;
    }

    public String getConsequenceDescription() {
        return consequenceDescription;
    }

    public void setConsequenceDescription(String consequenceDescription) {
        this.consequenceDescription = consequenceDescription;
    }

    @XmlTransient
    public List<DbHazardConsequence> getDbHazardConsequenceList() {
        return dbHazardConsequenceList;
    }

    public void setDbHazardConsequenceList(List<DbHazardConsequence> dbHazardConsequenceList) {
        this.dbHazardConsequenceList = dbHazardConsequenceList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consequenceId != null ? consequenceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbConsequence)) {
            return false;
        }
        DbConsequence other = (DbConsequence) object;
        if ((this.consequenceId == null && other.consequenceId != null) || (this.consequenceId != null && !this.consequenceId.equals(other.consequenceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbConsequence[ consequenceId=" + consequenceId + " ]";
    }
    
}
