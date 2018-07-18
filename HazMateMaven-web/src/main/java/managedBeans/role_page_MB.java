/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbPageFacadeLocal;
import ejb.DbRoleFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.*;
import entities.*;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import ejb.DbRolePageFacadeLocal;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author alan8
 */
@Named(value = "role_page_MB")
@SessionScoped
public class role_page_MB implements Serializable {

    @EJB
    private DbRolePageFacadeLocal dbRolePageFacade;
    @EJB
    private DbPageFacadeLocal dbPageFacade;
    @EJB
    private DbRoleFacadeLocal dbRoleFacade;

    private List<DbRolePage> listDbRolePage;
    private List<DbRolePage> filteredDbRolePage;
    private List<DbPage> listDbPage;
    private List<DbRole> listDbRole;
    private DbRolePage rolePageObject = new DbRolePage();
    private DbRole roleObject = new DbRole();
    private DbPage pageObject = new DbPage();
    private DbRolePagePK rolePagePK = new DbRolePagePK();
    private Integer roleInt;
    private Integer pageInt;
    private String strPermission;

    public role_page_MB() {
    }

    public List<DbRolePage> getListDbRolePage() {
        return listDbRolePage;
    }

    public void setListDbRolePage(List<DbRolePage> listDbRolePage) {
        this.listDbRolePage = listDbRolePage;
    }

    public List<DbPage> getListDbPage() {
        return listDbPage;
    }

    public void setListDbPage(List<DbPage> listDbPage) {
        this.listDbPage = listDbPage;
    }

    public List<DbRole> getListDbRole() {
        return listDbRole;
    }

    public void setListDbRole(List<DbRole> listDbRole) {
        this.listDbRole = listDbRole;
    }

    public DbRolePage getRolePageObject() {
        return rolePageObject;
    }

    public void setRolePageObject(DbRolePage rolePageObject) {
        this.rolePageObject = rolePageObject;
    }

    public DbRole getRoleObject() {
        return roleObject;
    }

    public void setRoleObject(DbRole roleObject) {
        this.roleObject = roleObject;
    }

    public DbPage getPageObject() {
        return pageObject;
    }

    public void setPageObject(DbPage pageObject) {
        this.pageObject = pageObject;
    }

    public DbRolePagePK getRolePagePK() {
        return rolePagePK;
    }

    public void setRolePagePK(DbRolePagePK rolePagePK) {
        this.rolePagePK = rolePagePK;
    }

    public Integer getRoleInt() {
        return roleInt;
    }

    public void setRoleInt(Integer roleInt) {
        this.roleInt = roleInt;
    }

    public Integer getPageInt() {
        return pageInt;
    }

    public void setPageInt(Integer pageInt) {
        this.pageInt = pageInt;
    }

    public List<DbRolePage> getFilteredDbRolePage() {
        return filteredDbRolePage;
    }

    public void setFilteredDbRolePage(List<DbRolePage> filteredDbRolePage) {
        this.filteredDbRolePage = filteredDbRolePage;
    }

    @PostConstruct
    public void init() {
        listDbRolePage = dbRolePageFacade.findAll();
        listDbPage = dbPageFacade.findAll();
        listDbRole = dbRoleFacade.findAll();
    }

    public String add() {
        listDbRolePage = dbRolePageFacade.checkRolePage(roleInt, pageInt);

        if (listDbRolePage.isEmpty()) {
            rolePagePK.setRoleId(roleInt);
            rolePagePK.setPageId(pageInt);
            roleObject.setRoleId(roleInt);
            pageObject.setPageId(pageInt);

            rolePageObject.setDbRolePagePK(rolePagePK);
            rolePageObject.setDbRole(roleObject);
            rolePageObject.setDbPage(pageObject);

            dbRolePageFacade.create(rolePageObject);

            rolePageObject = new DbRolePage();
            roleInt = -1;
            pageInt = -1;
        } else {
            error();
            init();
            return "addRolePage";
        }

        init();
        return "viewRolePage";
    }

    public String edit(DbRolePage rolePageObject) {
        this.rolePageObject = rolePageObject;
        return "editRolePage";
    }

    public String edit() {
        dbRolePageFacade.edit(rolePageObject);
        rolePageObject = new DbRolePage();
        init();
        return "viewRolePage";
    }

    public void delete(DbRolePage rolePageObject) {
        dbRolePageFacade.remove(rolePageObject);
        init();
    }

    public String cancel() {
        rolePageObject = new DbRolePage();
        roleInt = -1;
        pageInt = -1;
        return "viewRolePage";
    }

    public String printPermission(String permission) {

        switch (permission) {
            case "Y":
                strPermission = "Yes";
                break;
            case "N":
                strPermission = "No";
                break;
            default:
                strPermission = "NULL";
                break;

        }
        return strPermission;
    }

    public void error() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The role-page relationship already exists"));
    }

}
