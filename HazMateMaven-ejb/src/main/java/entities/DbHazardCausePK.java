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
 * @author Juan David
 */
@Embeddable
public class DbHazardCausePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "hazardId")
    private String hazardId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "causeId")
    private int causeId;

    public DbHazardCausePK() {
    }

    public DbHazardCausePK(String hazardId, int causeId) {
        this.hazardId = hazardId;
        this.causeId = causeId;
    }

    public String getHazardId() {
        return hazardId;
    }

    public void setHazardId(String hazardId) {
        this.hazardId = hazardId;
    }

    public int getCauseId() {
        return causeId;
    }

    public void setCauseId(int causeId) {
        this.causeId = causeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hazardId != null ? hazardId.hashCode() : 0);
        hash += (int) causeId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbHazardCausePK)) {
            return false;
        }
        DbHazardCausePK other = (DbHazardCausePK) object;
        if ((this.hazardId == null && other.hazardId != null) || (this.hazardId != null && !this.hazardId.equals(other.hazardId))) {
            return false;
        }
        if (this.causeId != other.causeId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbHazardCausePK[ hazardId=" + hazardId + ", causeId=" + causeId + " ]";
    }
    
}
