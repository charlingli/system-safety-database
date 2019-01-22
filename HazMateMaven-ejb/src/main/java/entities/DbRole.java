/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lxra
 */
@Entity
@Table(name = "db_role")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbRole.findAll", query = "SELECT d FROM DbRole d")
    , @NamedQuery(name = "DbRole.findByRoleId", query = "SELECT d FROM DbRole d WHERE d.roleId = :roleId")
    , @NamedQuery(name = "DbRole.findByRoleName", query = "SELECT d FROM DbRole d WHERE d.roleName = :roleName")
    , @NamedQuery(name = "DbRole.findByRoleStatus", query = "SELECT d FROM DbRole d WHERE d.roleStatus = :roleStatus")
    , @NamedQuery(name = "DbRole.findByRoleWFApprover", query = "SELECT d FROM DbRole d WHERE d.roleWFApprover = :roleWFApprover")
    , @NamedQuery(name = "DbRole.findByRoleDoubleScore", query = "SELECT d FROM DbRole d WHERE d.roleDoubleScore = :roleDoubleScore")})
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "roleWFApprover")
    private String roleWFApprover;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "roleDoubleScore")
    private String roleDoubleScore;

    public DbRole() {
    }

    public DbRole(Integer roleId) {
        this.roleId = roleId;
    }

    public DbRole(Integer roleId, short roleStatus, String roleWFApprover, String roleDoubleScore) {
        this.roleId = roleId;
        this.roleStatus = roleStatus;
        this.roleWFApprover = roleWFApprover;
        this.roleDoubleScore = roleDoubleScore;
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

    public String getRoleDoubleScore() {
        return roleDoubleScore;
    }

    public void setRoleDoubleScore(String roleDoubleScore) {
        this.roleDoubleScore = roleDoubleScore;
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
        return "entities.DbRole[ roleId=" + roleId + " ]";
    }
    
}
