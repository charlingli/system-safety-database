/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author lxra
 */
@Entity
@Table(name = "db_importErrorCode")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbimportErrorCode.findAll", query = "SELECT d FROM DbimportErrorCode d")
    , @NamedQuery(name = "DbimportErrorCode.findByErrorId", query = "SELECT d FROM DbimportErrorCode d WHERE d.errorId = :errorId")
    , @NamedQuery(name = "DbimportErrorCode.findByErrorName", query = "SELECT d FROM DbimportErrorCode d WHERE d.errorName = :errorName")
    , @NamedQuery(name = "DbimportErrorCode.findByErrorDescription", query = "SELECT d FROM DbimportErrorCode d WHERE d.errorDescription = :errorDescription")
    , @NamedQuery(name = "DbimportErrorCode.findByErrorAction", query = "SELECT d FROM DbimportErrorCode d WHERE d.errorAction = :errorAction")})
public class DbimportErrorCode implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "errorId")
    private Integer errorId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "errorName")
    private String errorName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "errorDescription")
    private String errorDescription;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "errorAction")
    private String errorAction;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "processErrorCode")
    private Collection<DbimportLineError> dbimportLineErrorCollection;

    public DbimportErrorCode() {
    }

    public DbimportErrorCode(Integer errorId) {
        this.errorId = errorId;
    }

    public DbimportErrorCode(Integer errorId, String errorName, String errorDescription, String errorAction) {
        this.errorId = errorId;
        this.errorName = errorName;
        this.errorDescription = errorDescription;
        this.errorAction = errorAction;
    }

    public Integer getErrorId() {
        return errorId;
    }

    public void setErrorId(Integer errorId) {
        this.errorId = errorId;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorAction() {
        return errorAction;
    }

    public void setErrorAction(String errorAction) {
        this.errorAction = errorAction;
    }

    @XmlTransient
    public Collection<DbimportLineError> getDbimportLineErrorCollection() {
        return dbimportLineErrorCollection;
    }

    public void setDbimportLineErrorCollection(Collection<DbimportLineError> dbimportLineErrorCollection) {
        this.dbimportLineErrorCollection = dbimportLineErrorCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (errorId != null ? errorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbimportErrorCode)) {
            return false;
        }
        DbimportErrorCode other = (DbimportErrorCode) object;
        if ((this.errorId == null && other.errorId != null) || (this.errorId != null && !this.errorId.equals(other.errorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbimportErrorCode[ errorId=" + errorId + " ]";
    }
    
}
