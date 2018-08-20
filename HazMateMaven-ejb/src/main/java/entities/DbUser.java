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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "db_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbUser.findAll", query = "SELECT d FROM DbUser d")
    , @NamedQuery(name = "DbUser.findByUserId", query = "SELECT d FROM DbUser d WHERE d.userId = :userId")
    , @NamedQuery(name = "DbUser.findByFirstName", query = "SELECT d FROM DbUser d WHERE d.firstName = :firstName")
    , @NamedQuery(name = "DbUser.findByLastName", query = "SELECT d FROM DbUser d WHERE d.lastName = :lastName")
    , @NamedQuery(name = "DbUser.findByPassword", query = "SELECT d FROM DbUser d WHERE d.password = :password")
    , @NamedQuery(name = "DbUser.findByMobileNumber", query = "SELECT d FROM DbUser d WHERE d.mobileNumber = :mobileNumber")
    , @NamedQuery(name = "DbUser.findByUserEmail", query = "SELECT d FROM DbUser d WHERE d.userEmail = :userEmail")
    , @NamedQuery(name = "DbUser.findByUserStatus", query = "SELECT d FROM DbUser d WHERE d.userStatus = :userStatus")
    , @NamedQuery(name = "DbUser.findByCompany", query = "SELECT d FROM DbUser d WHERE d.company = :company")})
public class DbUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "userId")
    private Integer userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "firstName")
    private String firstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "lastName")
    private String lastName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "mobileNumber")
    private String mobileNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "userEmail")
    private String userEmail;
    @Basic(optional = false)
    @NotNull
    @Column(name = "userStatus")
    private short userStatus;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "company")
    private String company;
    @JoinColumn(name = "roleId", referencedColumnName = "roleId")
    @ManyToOne(optional = false)
    private DbRole roleId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dbUser")
    private List<DbuserPreferences> dbuserPreferencesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "wfUserIdAdd")
    private List<DbwfHeader> dbwfHeaderList;
    @OneToMany(mappedBy = "wfUserIdUpdate")
    private List<DbwfHeader> dbwfHeaderList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "wfUserIdApprover")
    private List<DbwfLine> dbwfLineList;

    public DbUser() {
    }

    public DbUser(Integer userId) {
        this.userId = userId;
    }

    public DbUser(Integer userId, String firstName, String lastName, String password, String mobileNumber, String userEmail, short userStatus, String company) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.userEmail = userEmail;
        this.userStatus = userStatus;
        this.company = company;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public short getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(short userStatus) {
        this.userStatus = userStatus;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public DbRole getRoleId() {
        return roleId;
    }

    public void setRoleId(DbRole roleId) {
        this.roleId = roleId;
    }

    @XmlTransient
    public List<DbuserPreferences> getDbuserPreferencesList() {
        return dbuserPreferencesList;
    }

    public void setDbuserPreferencesList(List<DbuserPreferences> dbuserPreferencesList) {
        this.dbuserPreferencesList = dbuserPreferencesList;
    }

    @XmlTransient
    public List<DbwfHeader> getDbwfHeaderList() {
        return dbwfHeaderList;
    }

    public void setDbwfHeaderList(List<DbwfHeader> dbwfHeaderList) {
        this.dbwfHeaderList = dbwfHeaderList;
    }

    @XmlTransient
    public List<DbwfHeader> getDbwfHeaderList1() {
        return dbwfHeaderList1;
    }

    public void setDbwfHeaderList1(List<DbwfHeader> dbwfHeaderList1) {
        this.dbwfHeaderList1 = dbwfHeaderList1;
    }

    @XmlTransient
    public List<DbwfLine> getDbwfLineList() {
        return dbwfLineList;
    }

    public void setDbwfLineList(List<DbwfLine> dbwfLineList) {
        this.dbwfLineList = dbwfLineList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbUser)) {
            return false;
        }
        DbUser other = (DbUser) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbUser[ userId=" + userId + " ]";
    }
}
