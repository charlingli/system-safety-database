/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import entities.DbMenu;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import ejb.DbPageFacadeLocal;
import ejb.DbMenuFacadeLocal;
import entities.DbPage;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Admin
 */
@Named(value = "pages_MB")
@ViewScoped
public class pages_MB implements Serializable {

    @EJB
    private DbMenuFacadeLocal dbMenuFacade;
    private List<DbMenu> listDbMenu;
    private DbMenu menuObject = new DbMenu();

    public List<DbMenu> getListDbMenu() {
        return listDbMenu;
    }

    public void setListDbMenu(List<DbMenu> listDbMenu) {
        this.listDbMenu = listDbMenu;
    }

    public DbMenu getMenuObject() {
        return menuObject;
    }

    public void setMenuObject(DbMenu menuObject) {
        this.menuObject = menuObject;
    }

    @EJB
    private DbPageFacadeLocal dbPageFacade;
    private List<DbPage> listDbPage;
    private List<DbPage> filteredDbPage;
    private DbPage pageObject = new DbPage();
    private String pageIndex;
    private boolean addFlag;
    private boolean editFlag;
    private String validIndexPrefix;

    public String getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }

    public DbPage getPageObject() {
        return pageObject;
    }

    public void setPageObject(DbPage pageObject) {
        this.pageObject = pageObject;
    }

    public List<DbPage> getListDbPage() {
        return listDbPage;
    }

    public void setListDbPage(List<DbPage> listDbPage) {
        this.listDbPage = listDbPage;
    }

    public List<DbPage> getFilteredDbPage() {
        return filteredDbPage;
    }

    public void setFilteredDbPage(List<DbPage> filteredDbPage) {
        this.filteredDbPage = filteredDbPage;
    }

    public boolean isAddFlag() {
        return addFlag;
    }

    public void setAddFlag(boolean addFlag) {
        this.addFlag = addFlag;
    }

    public boolean isEditFlag() {
        return editFlag;
    }

    public void setEditFlag(boolean editFlag) {
        this.editFlag = editFlag;
    }

    public String getValidIndexPrefix() {
        return validIndexPrefix;
    }

    public void setValidIndexPrefix(String validIndexPrefix) {
        this.validIndexPrefix = validIndexPrefix;
    }
    
    public pages_MB() {
    }

    @PostConstruct
    public void init() {
        listDbPage = dbPageFacade.findAllSortedByInx();
        listDbMenu = dbMenuFacade.findAll();
        addFlag = false;
        editFlag = false;
        validIndexPrefix = "";
        findValidIndices();
    }
    
    public void showAdd() {
        pageObject = new DbPage();
        menuObject = new DbMenu();
        addFlag = true;
    }

    public void add() {
        if (generalValidations()) {
            pageObject.setMenuId(menuObject);
            pageObject.setIndexPage(Integer.parseInt(pageIndex));
            if ("".equals(pageObject.getPageIcon())) {
                pageObject.setPageIcon(null);
            }
            dbPageFacade.create(pageObject);
            clearVariables();
        }
        addFlag = false;
    }

    public void edit(DbPage pageObject) {
        this.pageObject = pageObject;
        setPageIndex(Integer.toString(pageObject.getIndexPage()));
        menuObject.setMenuId(pageObject.getMenuId().getMenuId());
        editFlag = true;
    }

    public void edit() {
        pageObject.setMenuId(menuObject);
        if (generalValidations()) {
            pageObject.setIndexPage(Integer.parseInt(pageIndex));
            if ("".equals(pageObject.getPageIcon())) {
                pageObject.setPageIcon(null);
            }
            dbPageFacade.edit(pageObject);
            clearVariables();
        }
        editFlag = false;
    }

    public void delete(DbPage pageObject) {
        dbPageFacade.remove(pageObject);
        init();
    }
    
    public void findValidIndices() {
        DbMenu tempMenu = dbMenuFacade.find(menuObject.getMenuId());
        validIndexPrefix = String.valueOf(tempMenu.getIndexMenu()).substring(0, 2);
        System.out.println(validIndexPrefix);
    }

    public boolean generalValidations() {
        boolean flag = true;
        int tempIndex = Integer.parseInt(pageIndex);

        DbPage tempPage = dbPageFacade.retrievePageMatch(tempIndex);
        DbMenu tempMenu = dbMenuFacade.find(menuObject.getMenuId());

        if (tempPage.getPageId() != null && !(tempIndex == pageObject.getIndexPage())) {
            flag = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Page Index Error", "Page already exists. Please try assigning a different index."));
        }

        if (tempMenu.getMenuType().equals("M")) {
            int tempMenuMin = tempMenu.getIndexMenu() + 980;
            int tempMenuMax = tempMenu.getIndexMenu() + 999;
            if (tempMenuMin > tempIndex || tempIndex > tempMenuMax) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Page Index Error", "The Page Index should be between "
                                + tempMenuMin + " and " + tempMenuMax));
                flag = false;
            }
        } else if (tempMenu.getMenuType().equals("S")) {
            int tempMenuMin = tempMenu.getIndexMenu();
            int tempMenuMax = tempMenu.getIndexMenu() + 99;
            if (tempMenuMin > tempIndex || tempIndex > tempMenuMax) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Page Index Error", "The Page Index should be between "
                                + tempMenuMin + " and " + tempMenuMax));
                flag = false;
            }
        }
        return flag;
    }

    public void clearVariables() {
        pageObject = new DbPage();
        menuObject = new DbMenu();
        pageIndex = "";
        init();
    }

    public void cancel() {
        clearVariables();
    }
}
