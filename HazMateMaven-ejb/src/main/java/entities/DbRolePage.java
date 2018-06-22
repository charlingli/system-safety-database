/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan David
 */
@Entity
@Table(name = "db_role_page")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbRolePage.findAll", query = "SELECT d FROM DbRolePage d")
    , @NamedQuery(name = "DbRolePage.findByRoleId", query = "SELECT d FROM DbRolePage d WHERE d.dbRolePagePK.roleId = :roleId")
    , @NamedQuery(name = "DbRolePage.findByPageId", query = "SELECT d FROM DbRolePage d WHERE d.dbRolePagePK.pageId = :pageId")
    , @NamedQuery(name = "DbRolePage.findByAddPermission", query = "SELECT d FROM DbRolePage d WHERE d.addPermission = :addPermission")
    , @NamedQuery(name = "DbRolePage.findByUpdatePermission", query = "SELECT d FROM DbRolePage d WHERE d.updatePermission = :updatePermission")
    , @NamedQuery(name = "DbRolePage.findByDeletePermission", query = "SELECT d FROM DbRolePage d WHERE d.deletePermission = :deletePermission")})
public class DbRolePage implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DbRolePagePK dbRolePagePK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "addPermission")
    private String addPermission;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "updatePermission")
    private String updatePermission;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "deletePermission")
    private String deletePermission;
    @JoinColumn(name = "pageId", referencedColumnName = "pageId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DbPage dbPage;
    @JoinColumn(name = "roleId", referencedColumnName = "roleId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DbRole dbRole;

    public DbRolePage() {
    }

    public DbRolePage(DbRolePagePK dbRolePagePK) {
        this.dbRolePagePK = dbRolePagePK;
    }

    public DbRolePage(DbRolePagePK dbRolePagePK, String addPermission, String updatePermission, String deletePermission) {
        this.dbRolePagePK = dbRolePagePK;
        this.addPermission = addPermission;
        this.updatePermission = updatePermission;
        this.deletePermission = deletePermission;
    }

    public DbRolePage(int roleId, int pageId) {
        this.dbRolePagePK = new DbRolePagePK(roleId, pageId);
    }

    public DbRolePagePK getDbRolePagePK() {
        return dbRolePagePK;
    }

    public void setDbRolePagePK(DbRolePagePK dbRolePagePK) {
        this.dbRolePagePK = dbRolePagePK;
    }

    public String getAddPermission() {
        return addPermission;
    }

    public void setAddPermission(String addPermission) {
        this.addPermission = addPermission;
    }

    public String getUpdatePermission() {
        return updatePermission;
    }

    public void setUpdatePermission(String updatePermission) {
        this.updatePermission = updatePermission;
    }

    public String getDeletePermission() {
        return deletePermission;
    }

    public void setDeletePermission(String deletePermission) {
        this.deletePermission = deletePermission;
    }

    public DbPage getDbPage() {
        return dbPage;
    }

    public void setDbPage(DbPage dbPage) {
        this.dbPage = dbPage;
    }

    public DbRole getDbRole() {
        return dbRole;
    }

    public void setDbRole(DbRole dbRole) {
        this.dbRole = dbRole;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dbRolePagePK != null ? dbRolePagePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbRolePage)) {
            return false;
        }
        DbRolePage other = (DbRolePage) object;
        if ((this.dbRolePagePK == null && other.dbRolePagePK != null) || (this.dbRolePagePK != null && !this.dbRolePagePK.equals(other.dbRolePagePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbRolePage[ dbRolePagePK=" + dbRolePagePK + " ]";
    }
    
}
