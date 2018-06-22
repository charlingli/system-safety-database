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
@Table(name = "db_cause")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbCause.findAll", query = "SELECT d FROM DbCause d")
    , @NamedQuery(name = "DbCause.findByCauseId", query = "SELECT d FROM DbCause d WHERE d.causeId = :causeId")
    , @NamedQuery(name = "DbCause.findByHazardId", query = "SELECT d FROM DbCause d WHERE d.hazardId = :hazardId")})
public class DbCause implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "causeId")
    private Integer causeId;
    @Size(max = 20)
    @Column(name = "hazardId")
    private String hazardId;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "causeDescription")
    private String causeDescription;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dbCause")
    private List<DbHazardCause> dbHazardCauseList;

    public DbCause() {
    }

    public DbCause(Integer causeId) {
        this.causeId = causeId;
    }

    public DbCause(Integer causeId, String causeDescription) {
        this.causeId = causeId;
        this.causeDescription = causeDescription;
    }

    public Integer getCauseId() {
        return causeId;
    }

    public void setCauseId(Integer causeId) {
        this.causeId = causeId;
    }

    public String getHazardId() {
        return hazardId;
    }

    public void setHazardId(String hazardId) {
        this.hazardId = hazardId;
    }

    public String getCauseDescription() {
        return causeDescription;
    }

    public void setCauseDescription(String causeDescription) {
        this.causeDescription = causeDescription;
    }

    @XmlTransient
    public List<DbHazardCause> getDbHazardCauseList() {
        return dbHazardCauseList;
    }

    public void setDbHazardCauseList(List<DbHazardCause> dbHazardCauseList) {
        this.dbHazardCauseList = dbHazardCauseList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (causeId != null ? causeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbCause)) {
            return false;
        }
        DbCause other = (DbCause) object;
        if ((this.causeId == null && other.causeId != null) || (this.causeId != null && !this.causeId.equals(other.causeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbCause[ causeId=" + causeId + " ]";
    }
    
}
