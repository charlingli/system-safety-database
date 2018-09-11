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
 * @author David Ortega <david.ortega@levelcrossings.vic.gov.au>
 */
@Embeddable
public class DbControlHazardPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "hazardId")
    private String hazardId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "controlId")
    private int controlId;

    public DbControlHazardPK() {
    }

    public DbControlHazardPK(String hazardId, int controlId) {
        this.hazardId = hazardId;
        this.controlId = controlId;
    }

    public String getHazardId() {
        return hazardId;
    }

    public void setHazardId(String hazardId) {
        this.hazardId = hazardId;
    }

    public int getControlId() {
        return controlId;
    }

    public void setControlId(int controlId) {
        this.controlId = controlId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hazardId != null ? hazardId.hashCode() : 0);
        hash += (int) controlId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbControlHazardPK)) {
            return false;
        }
        DbControlHazardPK other = (DbControlHazardPK) object;
        if ((this.hazardId == null && other.hazardId != null) || (this.hazardId != null && !this.hazardId.equals(other.hazardId))) {
            return false;
        }
        if (this.controlId != other.controlId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbControlHazardPK[ hazardId=" + hazardId + ", controlId=" + controlId + " ]";
    }
    
}
