/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbMenuFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import entities.DbMenu;
import entities.DbPage;
import entities.DbUser;
import customObjects.menuObject;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author Juan David
 */
@Named(value = "menuDynamic_MB")
@SessionScoped
public class menuDynamic_MB implements Serializable {

    @EJB
    private DbMenuFacadeLocal dbMenuFacade;

    private List<DbPage> pageList;
    private List<menuObject> menuObjectList;
    private MenuModel menuContent;

    public menuDynamic_MB() {
    }

    public MenuModel getMenuContent() {
        return menuContent;
    }

    public void setMenuContent(MenuModel menuContent) {
        this.menuContent = menuContent;
    }

    public List<DbPage> getPageList() {
        return pageList;
    }

    public void setPageList(List<DbPage> pageList) {
        this.pageList = pageList;
    }

    @PostConstruct
    public void init() {
        menuContent = new DefaultMenuModel();
        menuObjectList = new ArrayList<>();
        setPermissions();
    }

    private void setPermissions() {
        //Bring the current user and seacrhing for the allowed pages for that user.
        DbUser loggedUser = (DbUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("activeUser");
        pageList = dbMenuFacade.customUserMenu(loggedUser.getUserId());
        //Validating that all pages have a correct navigation path.
        validatePageNavigation();

        //Go over each user page retrieved 
        for (DbPage page : pageList) {
            //Bring the first parent menu for the page
            DbMenu firstMenuRetrieved = dbMenuFacade.findOneMenu(page.getMenuId().getMenuId());
            //If the type is S (Submenu) then the Mainmenu will be looked for and both will be inserted in the List
            if (firstMenuRetrieved.getMenuType().equals("S")) {
                insertObjectList(firstMenuRetrieved.getMenuId(), firstMenuRetrieved.getMenuName(), firstMenuRetrieved.getMenuType(), "",
                        firstMenuRetrieved.getMenuIcon(), firstMenuRetrieved.getIndexMenu(), firstMenuRetrieved.getParentMenu());
                DbMenu mainMenuRetrieved = dbMenuFacade.findOneMenu(firstMenuRetrieved.getParentMenu());
                if (mainMenuRetrieved.getMenuType().equals("M")) {
                    insertObjectList(mainMenuRetrieved.getMenuId(), mainMenuRetrieved.getMenuName(), mainMenuRetrieved.getMenuType(), "",
                            mainMenuRetrieved.getMenuIcon(), mainMenuRetrieved.getIndexMenu(), mainMenuRetrieved.getParentMenu());
                }
            } else {
                if (firstMenuRetrieved.getMenuType().equals("M")) {
                    insertObjectList(firstMenuRetrieved.getMenuId(), firstMenuRetrieved.getMenuName(), firstMenuRetrieved.getMenuType(), "",
                            firstMenuRetrieved.getMenuIcon(), firstMenuRetrieved.getIndexMenu(), firstMenuRetrieved.getParentMenu());
                }
            }
            insertObjectList(0, page.getPageName(), "P", page.getPageLocation(), page.getPageIcon(), page.getIndexPage(), page.getMenuId().getMenuId());
        }
        createMenuTree();
    }

    private void validatePageNavigation() {
        for (int x = 0; x < pageList.size(); x++) {
            String fullPath = pageList.get(x).getPageLocation() + ".xhtml";

            try {
                if (FacesContext.getCurrentInstance().getExternalContext().getResource(fullPath) == null) {
                    pageList.remove(x);
                    //since the list was reorganized the itertor may start from 0
                    x = -1;
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(menuDynamic_MB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void insertObjectList(int menuId, String Name, String Type, String Location, String Icon, int Index, int parentId) {
        boolean insert = true;

        //Validating if the item is already in the lsit
        for (menuObject menuTmp : menuObjectList) {
            if (menuTmp.getItemIndex() == Index) {
                insert = false;
                break;
            }
        }

        if (insert) {
            menuObject tempMenuObject = new menuObject();
            tempMenuObject.setMenuId(menuId);
            tempMenuObject.setMenuName(Name);
            tempMenuObject.setItemType(Type);
            tempMenuObject.setItemLocation(Location);
            tempMenuObject.setItemIcon(Icon);
            tempMenuObject.setItemIndex(Index);
            tempMenuObject.setParentMenu(parentId);
            menuObjectList.add(tempMenuObject);
        }
    }

    private void createMenuTree() {
        menuObjectList.sort(Comparator.comparingInt(menuObject::getItemIndex));

        DefaultSubMenu firstSubmenu = new DefaultSubMenu();
        DefaultSubMenu secondSubmenu = new DefaultSubMenu();
        DefaultMenuItem item = new DefaultMenuItem();

        for (menuObject firstMenuTemp : menuObjectList) {
            if (firstMenuTemp.getItemType().equals("M")) {
                firstSubmenu = new DefaultSubMenu();
                firstSubmenu.setLabel(firstMenuTemp.getMenuName());
                firstSubmenu.setIcon(firstMenuTemp.getItemIcon());
                for (menuObject secondMenuTemp : menuObjectList) {
                    if (secondMenuTemp.getItemType().equals("S") && secondMenuTemp.getParentMenu() == firstMenuTemp.getMenuId()) {
                        secondSubmenu = new DefaultSubMenu();
                        secondSubmenu.setLabel(secondMenuTemp.getMenuName());
                        secondSubmenu.setIcon(secondMenuTemp.getItemIcon());
                        firstSubmenu.addElement(secondSubmenu);
                        for (menuObject itemTemp : menuObjectList) {
                            if (itemTemp.getItemType().equals("P") && itemTemp.getParentMenu() == secondMenuTemp.getMenuId()) {
                                item = new DefaultMenuItem(itemTemp.getMenuName());
                                item.setIcon(itemTemp.getItemIcon());
                                item.setOutcome(itemTemp.getItemLocation());
                                secondSubmenu.addElement(item);
                            }
                        }
                    } else if (secondMenuTemp.getItemType().equals("P") && secondMenuTemp.getParentMenu() == firstMenuTemp.getMenuId()) {
                        item = new DefaultMenuItem(secondMenuTemp.getMenuName());
                        item.setIcon(secondMenuTemp.getItemIcon());
                        item.setOutcome(secondMenuTemp.getItemLocation());
                        firstSubmenu.addElement(item);
                    }
                }
                menuContent.addElement(firstSubmenu);
            }
        }
    }

}
