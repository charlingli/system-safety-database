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

/**
 *
 * @author Juan David
 */
@Embeddable
public class DbRolePagePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "roleId")
    private int roleId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pageId")
    private int pageId;

    public DbRolePagePK() {
    }

    public DbRolePagePK(int roleId, int pageId) {
        this.roleId = roleId;
        this.pageId = pageId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) roleId;
        hash += (int) pageId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbRolePagePK)) {
            return false;
        }
        DbRolePagePK other = (DbRolePagePK) object;
        if (this.roleId != other.roleId) {
            return false;
        }
        if (this.pageId != other.pageId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbRolePagePK[ roleId=" + roleId + ", pageId=" + pageId + " ]";
    }
    
}
