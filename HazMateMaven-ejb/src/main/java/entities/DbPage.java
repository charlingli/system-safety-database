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
@Table(name = "db_page")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbPage.findAll", query = "SELECT d FROM DbPage d")
    , @NamedQuery(name = "DbPage.findByPageId", query = "SELECT d FROM DbPage d WHERE d.pageId = :pageId")
    , @NamedQuery(name = "DbPage.findByPageName", query = "SELECT d FROM DbPage d WHERE d.pageName = :pageName")
    , @NamedQuery(name = "DbPage.findByPageLocation", query = "SELECT d FROM DbPage d WHERE d.pageLocation = :pageLocation")
    , @NamedQuery(name = "DbPage.findByIndexPage", query = "SELECT d FROM DbPage d WHERE d.indexPage = :indexPage")
    , @NamedQuery(name = "DbPage.findByPageIcon", query = "SELECT d FROM DbPage d WHERE d.pageIcon = :pageIcon")})
public class DbPage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pageId")
    private Integer pageId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "pageName")
    private String pageName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "pageLocation")
    private String pageLocation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indexPage")
    private int indexPage;
    @Size(max = 45)
    @Column(name = "pageIcon")
    private String pageIcon;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "dbPage")
    private List<DbRolePage> dbRolePageList;
    @JoinColumn(name = "menuId", referencedColumnName = "menuId")
    @ManyToOne(optional = false)
    private DbMenu menuId;

    public DbPage() {
    }

    public DbPage(Integer pageId) {
        this.pageId = pageId;
    }

    public DbPage(Integer pageId, String pageName, String pageLocation, int indexPage) {
        this.pageId = pageId;
        this.pageName = pageName;
        this.pageLocation = pageLocation;
        this.indexPage = indexPage;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageLocation() {
        return pageLocation;
    }

    public void setPageLocation(String pageLocation) {
        this.pageLocation = pageLocation;
    }

    public int getIndexPage() {
        return indexPage;
    }

    public void setIndexPage(int indexPage) {
        this.indexPage = indexPage;
    }

    public String getPageIcon() {
        return pageIcon;
    }

    public void setPageIcon(String pageIcon) {
        this.pageIcon = pageIcon;
    }

    @XmlTransient
    public List<DbRolePage> getDbRolePageList() {
        return dbRolePageList;
    }

    public void setDbRolePageList(List<DbRolePage> dbRolePageList) {
        this.dbRolePageList = dbRolePageList;
    }

    public DbMenu getMenuId() {
        return menuId;
    }

    public void setMenuId(DbMenu menuId) {
        this.menuId = menuId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pageId != null ? pageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbPage)) {
            return false;
        }
        DbPage other = (DbPage) object;
        if ((this.pageId == null && other.pageId != null) || (this.pageId != null && !this.pageId.equals(other.pageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbPage[ pageId=" + pageId + " ]";
    }
    
}
