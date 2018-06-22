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
public class DbHazardConsequencePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "hazardId")
    private String hazardId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "consequenceId")
    private int consequenceId;

    public DbHazardConsequencePK() {
    }

    public DbHazardConsequencePK(String hazardId, int consequenceId) {
        this.hazardId = hazardId;
        this.consequenceId = consequenceId;
    }

    public String getHazardId() {
        return hazardId;
    }

    public void setHazardId(String hazardId) {
        this.hazardId = hazardId;
    }

    public int getConsequenceId() {
        return consequenceId;
    }

    public void setConsequenceId(int consequenceId) {
        this.consequenceId = consequenceId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hazardId != null ? hazardId.hashCode() : 0);
        hash += (int) consequenceId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbHazardConsequencePK)) {
            return false;
        }
        DbHazardConsequencePK other = (DbHazardConsequencePK) object;
        if ((this.hazardId == null && other.hazardId != null) || (this.hazardId != null && !this.hazardId.equals(other.hazardId))) {
            return false;
        }
        if (this.consequenceId != other.consequenceId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbHazardConsequencePK[ hazardId=" + hazardId + ", consequenceId=" + consequenceId + " ]";
    }
    
}
