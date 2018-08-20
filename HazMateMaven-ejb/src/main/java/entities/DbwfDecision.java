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
 * @author lxra
 */
@Entity
@Table(name = "db_wfDecision")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbwfDecision.findAll", query = "SELECT d FROM DbwfDecision d")
    , @NamedQuery(name = "DbwfDecision.findByWfDecisionId", query = "SELECT d FROM DbwfDecision d WHERE d.wfDecisionId = :wfDecisionId")
    , @NamedQuery(name = "DbwfDecision.findByWfDecisionName", query = "SELECT d FROM DbwfDecision d WHERE d.wfDecisionName = :wfDecisionName")})
public class DbwfDecision implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "wfDecisionId")
    private String wfDecisionId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "wfDecisionName")
    private String wfDecisionName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "wfApproverDecisionId")
    private List<DbwfLine> dbwfLineList;

    public DbwfDecision() {
    }

    public DbwfDecision(String wfDecisionId) {
        this.wfDecisionId = wfDecisionId;
    }

    public DbwfDecision(String wfDecisionId, String wfDecisionName) {
        this.wfDecisionId = wfDecisionId;
        this.wfDecisionName = wfDecisionName;
    }

    public String getWfDecisionId() {
        return wfDecisionId;
    }

    public void setWfDecisionId(String wfDecisionId) {
        this.wfDecisionId = wfDecisionId;
    }

    public String getWfDecisionName() {
        return wfDecisionName;
    }

    public void setWfDecisionName(String wfDecisionName) {
        this.wfDecisionName = wfDecisionName;
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
        hash += (wfDecisionId != null ? wfDecisionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbwfDecision)) {
            return false;
        }
        DbwfDecision other = (DbwfDecision) object;
        if ((this.wfDecisionId == null && other.wfDecisionId != null) || (this.wfDecisionId != null && !this.wfDecisionId.equals(other.wfDecisionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbwfDecision[ wfDecisionId=" + wfDecisionId + " ]";
    }
    
}
