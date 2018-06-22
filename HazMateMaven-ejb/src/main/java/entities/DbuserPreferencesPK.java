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
public class DbuserPreferencesPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "userId")
    private int userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "pageName")
    private String pageName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tableName")
    private String tableName;

    public DbuserPreferencesPK() {
    }

    public DbuserPreferencesPK(int userId, String pageName, String tableName) {
        this.userId = userId;
        this.pageName = pageName;
        this.tableName = tableName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userId;
        hash += (pageName != null ? pageName.hashCode() : 0);
        hash += (tableName != null ? tableName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbuserPreferencesPK)) {
            return false;
        }
        DbuserPreferencesPK other = (DbuserPreferencesPK) object;
        if (this.userId != other.userId) {
            return false;
        }
        if ((this.pageName == null && other.pageName != null) || (this.pageName != null && !this.pageName.equals(other.pageName))) {
            return false;
        }
        if ((this.tableName == null && other.tableName != null) || (this.tableName != null && !this.tableName.equals(other.tableName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbuserPreferencesPK[ userId=" + userId + ", pageName=" + pageName + ", tableName=" + tableName + " ]";
    }
    
}
