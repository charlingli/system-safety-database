/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lxra
 */
@Entity
@Table(name = "db_wfLine")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbwfLine.findAll", query = "SELECT d FROM DbwfLine d")
    , @NamedQuery(name = "DbwfLine.findByWfId", query = "SELECT d FROM DbwfLine d WHERE d.dbwfLinePK.wfId = :wfId")
    , @NamedQuery(name = "DbwfLine.findByWfLineId", query = "SELECT d FROM DbwfLine d WHERE d.dbwfLinePK.wfLineId = :wfLineId")
    , @NamedQuery(name = "DbwfLine.findByWfApprovalComment", query = "SELECT d FROM DbwfLine d WHERE d.wfApprovalComment = :wfApprovalComment")
    , @NamedQuery(name = "DbwfLine.findByWfDateTimeDecision", query = "SELECT d FROM DbwfLine d WHERE d.wfDateTimeDecision = :wfDateTimeDecision")})
public class DbwfLine implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DbwfLinePK dbwfLinePK;
    @Size(max = 250)
    @Column(name = "wfApprovalComment")
    private String wfApprovalComment;
    @Column(name = "wfDateTimeDecision")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wfDateTimeDecision;
    @JoinColumn(name = "wfUserIdApprover", referencedColumnName = "userId")
    @ManyToOne(optional = false)
    private DbUser wfUserIdApprover;
    @JoinColumn(name = "wfApproverDecisionId", referencedColumnName = "wfDecisionId")
    @ManyToOne(optional = false)
    private DbwfDecision wfApproverDecisionId;
    @JoinColumn(name = "wfId", referencedColumnName = "wfId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DbwfHeader dbwfHeader;

    public DbwfLine() {
    }

    public DbwfLine(DbwfLinePK dbwfLinePK) {
        this.dbwfLinePK = dbwfLinePK;
    }

    public DbwfLine(String wfId, String wfLineId) {
        this.dbwfLinePK = new DbwfLinePK(wfId, wfLineId);
    }

    public DbwfLinePK getDbwfLinePK() {
        return dbwfLinePK;
    }

    public void setDbwfLinePK(DbwfLinePK dbwfLinePK) {
        this.dbwfLinePK = dbwfLinePK;
    }

    public String getWfApprovalComment() {
        return wfApprovalComment;
    }

    public void setWfApprovalComment(String wfApprovalComment) {
        this.wfApprovalComment = wfApprovalComment;
    }

    public Date getWfDateTimeDecision() {
        return wfDateTimeDecision;
    }

    public void setWfDateTimeDecision(Date wfDateTimeDecision) {
        this.wfDateTimeDecision = wfDateTimeDecision;
    }

    public DbUser getWfUserIdApprover() {
        return wfUserIdApprover;
    }

    public void setWfUserIdApprover(DbUser wfUserIdApprover) {
        this.wfUserIdApprover = wfUserIdApprover;
    }

    public DbwfDecision getWfApproverDecisionId() {
        return wfApproverDecisionId;
    }

    public void setWfApproverDecisionId(DbwfDecision wfApproverDecisionId) {
        this.wfApproverDecisionId = wfApproverDecisionId;
    }

    public DbwfHeader getDbwfHeader() {
        return dbwfHeader;
    }

    public void setDbwfHeader(DbwfHeader dbwfHeader) {
        this.dbwfHeader = dbwfHeader;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dbwfLinePK != null ? dbwfLinePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbwfLine)) {
            return false;
        }
        DbwfLine other = (DbwfLine) object;
        if ((this.dbwfLinePK == null && other.dbwfLinePK != null) || (this.dbwfLinePK != null && !this.dbwfLinePK.equals(other.dbwfLinePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbwfLine[ dbwfLinePK=" + dbwfLinePK + " ]";
    }
    
}
