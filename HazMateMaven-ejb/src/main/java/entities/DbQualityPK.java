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
public class DbQualityPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "userId")
    private int userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "hazardId")
    private String hazardId;

    public DbQualityPK() {
    }

    public DbQualityPK(int userId, String hazardId) {
        this.userId = userId;
        this.hazardId = hazardId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getHazardId() {
        return hazardId;
    }

    public void setHazardId(String hazardId) {
        this.hazardId = hazardId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userId;
        hash += (hazardId != null ? hazardId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbQualityPK)) {
            return false;
        }
        DbQualityPK other = (DbQualityPK) object;
        if (this.userId != other.userId) {
            return false;
        }
        if ((this.hazardId == null && other.hazardId != null) || (this.hazardId != null && !this.hazardId.equals(other.hazardId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbQualityPK[ userId=" + userId + ", hazardId=" + hazardId + " ]";
    }
    
}
