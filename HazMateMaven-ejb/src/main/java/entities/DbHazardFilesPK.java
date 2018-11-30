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
public class DbHazardFilesPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "hazardId")
    private String hazardId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fileId")
    private int fileId;

    public DbHazardFilesPK() {
    }

    public DbHazardFilesPK(String hazardId, int fileId) {
        this.hazardId = hazardId;
        this.fileId = fileId;
    }

    public String getHazardId() {
        return hazardId;
    }

    public void setHazardId(String hazardId) {
        this.hazardId = hazardId;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hazardId != null ? hazardId.hashCode() : 0);
        hash += (int) fileId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbHazardFilesPK)) {
            return false;
        }
        DbHazardFilesPK other = (DbHazardFilesPK) object;
        if ((this.hazardId == null && other.hazardId != null) || (this.hazardId != null && !this.hazardId.equals(other.hazardId))) {
            return false;
        }
        if (this.fileId != other.fileId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbHazardFilesPK[ hazardId=" + hazardId + ", fileId=" + fileId + " ]";
    }
    
}
