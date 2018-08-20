/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author lxra
 */
@Embeddable
public class DbwfLinePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "wfId")
    private String wfId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "wfLineId")
    private String wfLineId;

    public DbwfLinePK() {
    }

    public DbwfLinePK(String wfId, String wfLineId) {
        this.wfId = wfId;
        this.wfLineId = wfLineId;
    }

    public String getWfId() {
        return wfId;
    }

    public void setWfId(String wfId) {
        this.wfId = wfId;
    }

    public String getWfLineId() {
        return wfLineId;
    }

    public void setWfLineId(String wfLineId) {
        this.wfLineId = wfLineId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wfId != null ? wfId.hashCode() : 0);
        hash += (wfLineId != null ? wfLineId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbwfLinePK)) {
            return false;
        }
        DbwfLinePK other = (DbwfLinePK) object;
        if ((this.wfId == null && other.wfId != null) || (this.wfId != null && !this.wfId.equals(other.wfId))) {
            return false;
        }
        if ((this.wfLineId == null && other.wfLineId != null) || (this.wfLineId != null && !this.wfLineId.equals(other.wfLineId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbwfLinePK[ wfId=" + wfId + ", wfLineId=" + wfLineId + " ]";
    }
    
}
