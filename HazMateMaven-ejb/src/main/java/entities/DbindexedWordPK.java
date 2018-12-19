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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "objectType")
    private String objectType;

    public DbindexedWordPK() {
    }

    public DbindexedWordPK(String objectId, int objectLineNo, String objectType) {
        this.objectId = objectId;
        this.objectLineNo = objectLineNo;
        this.objectType = objectType;
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

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (objectId != null ? objectId.hashCode() : 0);
        hash += (int) objectLineNo;
        hash += (objectType != null ? objectType.hashCode() : 0);
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
        if ((this.objectType == null && other.objectType != null) || (this.objectType != null && !this.objectType.equals(other.objectType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbindexedWordPK[ objectId=" + objectId + ", objectLineNo=" + objectLineNo + ", objectType=" + objectType + " ]";
    }
    
}
