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
@Table(name = "db_menu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbMenu.findAll", query = "SELECT d FROM DbMenu d")
    , @NamedQuery(name = "DbMenu.findByMenuId", query = "SELECT d FROM DbMenu d WHERE d.menuId = :menuId")
    , @NamedQuery(name = "DbMenu.findByMenuName", query = "SELECT d FROM DbMenu d WHERE d.menuName = :menuName")
    , @NamedQuery(name = "DbMenu.findByMenuType", query = "SELECT d FROM DbMenu d WHERE d.menuType = :menuType")
    , @NamedQuery(name = "DbMenu.findByParentMenu", query = "SELECT d FROM DbMenu d WHERE d.parentMenu = :parentMenu")
    , @NamedQuery(name = "DbMenu.findByMenuIcon", query = "SELECT d FROM DbMenu d WHERE d.menuIcon = :menuIcon")
    , @NamedQuery(name = "DbMenu.findByIndexMenu", query = "SELECT d FROM DbMenu d WHERE d.indexMenu = :indexMenu")})
public class DbMenu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "menuId")
    private Integer menuId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "menuName")
    private String menuName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "menuType")
    private String menuType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "parentMenu")
    private int parentMenu;
    @Size(max = 45)
    @Column(name = "menuIcon")
    private String menuIcon;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indexMenu")
    private int indexMenu;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "menuId")
    private List<DbPage> dbPageList;

    public DbMenu() {
    }

    public DbMenu(Integer menuId) {
        this.menuId = menuId;
    }

    public DbMenu(Integer menuId, String menuName, String menuType, int parentMenu, int indexMenu) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuType = menuType;
        this.parentMenu = parentMenu;
        this.indexMenu = indexMenu;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public int getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(int parentMenu) {
        this.parentMenu = parentMenu;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public int getIndexMenu() {
        return indexMenu;
    }

    public void setIndexMenu(int indexMenu) {
        this.indexMenu = indexMenu;
    }

    @XmlTransient
    public List<DbPage> getDbPageList() {
        return dbPageList;
    }

    public void setDbPageList(List<DbPage> dbPageList) {
        this.dbPageList = dbPageList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (menuId != null ? menuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbMenu)) {
            return false;
        }
        DbMenu other = (DbMenu) object;
        if ((this.menuId == null && other.menuId != null) || (this.menuId != null && !this.menuId.equals(other.menuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return menuName;
    }
    
}
