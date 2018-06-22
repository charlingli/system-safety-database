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
import javax.persistence.Lob;
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
@Table(name = "db_userPreferences")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbuserPreferences.findAll", query = "SELECT d FROM DbuserPreferences d")
    , @NamedQuery(name = "DbuserPreferences.findByUserId", query = "SELECT d FROM DbuserPreferences d WHERE d.dbuserPreferencesPK.userId = :userId")
    , @NamedQuery(name = "DbuserPreferences.findByPageName", query = "SELECT d FROM DbuserPreferences d WHERE d.dbuserPreferencesPK.pageName = :pageName")
    , @NamedQuery(name = "DbuserPreferences.findByTableName", query = "SELECT d FROM DbuserPreferences d WHERE d.dbuserPreferencesPK.tableName = :tableName")})
public class DbuserPreferences implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DbuserPreferencesPK dbuserPreferencesPK;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "userPreferences")
    private String userPreferences;
    @JoinColumn(name = "userId", referencedColumnName = "userId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DbUser dbUser;

    public DbuserPreferences() {
    }

    public DbuserPreferences(DbuserPreferencesPK dbuserPreferencesPK) {
        this.dbuserPreferencesPK = dbuserPreferencesPK;
    }

    public DbuserPreferences(DbuserPreferencesPK dbuserPreferencesPK, String userPreferences) {
        this.dbuserPreferencesPK = dbuserPreferencesPK;
        this.userPreferences = userPreferences;
    }

    public DbuserPreferences(int userId, String pageName, String tableName) {
        this.dbuserPreferencesPK = new DbuserPreferencesPK(userId, pageName, tableName);
    }

    public DbuserPreferencesPK getDbuserPreferencesPK() {
        return dbuserPreferencesPK;
    }

    public void setDbuserPreferencesPK(DbuserPreferencesPK dbuserPreferencesPK) {
        this.dbuserPreferencesPK = dbuserPreferencesPK;
    }

    public String getUserPreferences() {
        return userPreferences;
    }

    public void setUserPreferences(String userPreferences) {
        this.userPreferences = userPreferences;
    }

    public DbUser getDbUser() {
        return dbUser;
    }

    public void setDbUser(DbUser dbUser) {
        this.dbUser = dbUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dbuserPreferencesPK != null ? dbuserPreferencesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbuserPreferences)) {
            return false;
        }
        DbuserPreferences other = (DbuserPreferences) object;
        if ((this.dbuserPreferencesPK == null && other.dbuserPreferencesPK != null) || (this.dbuserPreferencesPK != null && !this.dbuserPreferencesPK.equals(other.dbuserPreferencesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbuserPreferences[ dbuserPreferencesPK=" + dbuserPreferencesPK + " ]";
    }
    
}
