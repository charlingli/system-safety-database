/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Juan David
 */
@Entity
@Table(name = "db_role")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbRole.findAll", query = "SELECT d FROM DbRole d")
    , @NamedQuery(name = "DbRole.findByRoleId", query = "SELECT d FROM DbRole d WHERE d.roleId = :roleId")
    , @NamedQuery(name = "DbRole.findByRoleName", query = "SELECT d FROM DbRole d WHERE d.roleName = :roleName")
    , @NamedQuery(name = "DbRole.findByRoleStatus", query = "SELECT d FROM DbRole d WHERE d.roleStatus = :roleStatus")})
public class DbRole implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "roleId")
    private Integer roleId;
    @Size(max = 45)
    @Column(name = "roleName")
    private String roleName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "roleStatus")
    private short roleStatus;
    @Size(max = 1)
    @Column(name = "roleWFApprover")
    private String roleWFApprover;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "dbRole")
    private List<DbRolePage> dbRolePageList;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "roleId")
    private List<DbUser> dbUserList;

    public DbRole() {
    }

    public DbRole(Integer roleId) {
        this.roleId = roleId;
    }

    public DbRole(Integer roleId, short roleStatus) {
        this.roleId = roleId;
        this.roleStatus = roleStatus;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public short getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(short roleStatus) {
        this.roleStatus = roleStatus;
    }

    public String getRoleWFApprover() {
        return roleWFApprover;
    }

    public void setRoleWFApprover(String roleWFApprover) {
        this.roleWFApprover = roleWFApprover;
    }

    @XmlTransient
    public List<DbRolePage> getDbRolePageList() {
        return dbRolePageList;
    }

    public void setDbRolePageList(List<DbRolePage> dbRolePageList) {
        this.dbRolePageList = dbRolePageList;
    }

    @XmlTransient
    public List<DbUser> getDbUserList() {
        return dbUserList;
    }

    public void setDbUserList(List<DbUser> dbUserList) {
        this.dbUserList = dbUserList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleId != null ? roleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbRole)) {
            return false;
        }
        DbRole other = (DbRole) object;
        if ((this.roleId == null && other.roleId != null) || (this.roleId != null && !this.roleId.equals(other.roleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return roleName;
    }
}
