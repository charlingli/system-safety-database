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
public class DbHazardSbsPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "hazardId")
    private String hazardId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "sbsId")
    private String sbsId;

    public DbHazardSbsPK() {
    }

    public DbHazardSbsPK(String hazardId, String sbsId) {
        this.hazardId = hazardId;
        this.sbsId = sbsId;
    }

    public String getHazardId() {
        return hazardId;
    }

    public void setHazardId(String hazardId) {
        this.hazardId = hazardId;
    }

    public String getSbsId() {
        return sbsId;
    }

    public void setSbsId(String sbsId) {
        this.sbsId = sbsId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hazardId != null ? hazardId.hashCode() : 0);
        hash += (sbsId != null ? sbsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbHazardSbsPK)) {
            return false;
        }
        DbHazardSbsPK other = (DbHazardSbsPK) object;
        if ((this.hazardId == null && other.hazardId != null) || (this.hazardId != null && !this.hazardId.equals(other.hazardId))) {
            return false;
        }
        if ((this.sbsId == null && other.sbsId != null) || (this.sbsId != null && !this.sbsId.equals(other.sbsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbHazardSbsPK[ hazardId=" + hazardId + ", sbsId=" + sbsId + " ]";
    }
    
}
