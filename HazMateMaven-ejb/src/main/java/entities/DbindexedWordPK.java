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
public class DbindexedWordPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "objectId")
    private String objectId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "objectLineNo")
    private int objectLineNo;

    public DbindexedWordPK() {
    }

    public DbindexedWordPK(String objectId, int objectLineNo) {
        this.objectId = objectId;
        this.objectLineNo = objectLineNo;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getObjectLineNo() {
        return objectLineNo;
    }

    public void setObjectLineNo(int objectLineNo) {
        this.objectLineNo = objectLineNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (objectId != null ? objectId.hashCode() : 0);
        hash += (int) objectLineNo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbindexedWordPK)) {
            return false;
        }
        DbindexedWordPK other = (DbindexedWordPK) object;
        if ((this.objectId == null && other.objectId != null) || (this.objectId != null && !this.objectId.equals(other.objectId))) {
            return false;
        }
        if (this.objectLineNo != other.objectLineNo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbindexedWordPK[ objectId=" + objectId + ", objectLineNo=" + objectLineNo + " ]";
    }
    
}
