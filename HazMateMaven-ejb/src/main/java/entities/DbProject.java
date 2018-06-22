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
@Table(name = "db_project")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbProject.findAll", query = "SELECT d FROM DbProject d")
    , @NamedQuery(name = "DbProject.findByProjectId", query = "SELECT d FROM DbProject d WHERE d.projectId = :projectId")
    , @NamedQuery(name = "DbProject.findByProjectName", query = "SELECT d FROM DbProject d WHERE d.projectName = :projectName")})
public class DbProject implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "projectId")
    private Integer projectId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "projectName")
    private String projectName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "projectAbbrev")
    private String projectAbbrev;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "projectId")
    private List<DbLocation> dbLocationList;

    public DbProject() {
    }

    public DbProject(Integer projectId) {
        this.projectId = projectId;
    }

    public DbProject(Integer projectId, String projectName) {
        this.projectId = projectId;
        this.projectName = projectName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @XmlTransient
    public List<DbLocation> getDbLocationList() {
        return dbLocationList;
    }

    public void setDbLocationList(List<DbLocation> dbLocationList) {
        this.dbLocationList = dbLocationList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectId != null ? projectId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbProject)) {
            return false;
        }
        DbProject other = (DbProject) object;
        if ((this.projectId == null && other.projectId != null) || (this.projectId != null && !this.projectId.equals(other.projectId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return projectName;
    }

    public String getProjectAbbrev() {
        return projectAbbrev;
    }

    public void setProjectAbbrev(String projectAbbrev) {
        this.projectAbbrev = projectAbbrev;
    }

}
