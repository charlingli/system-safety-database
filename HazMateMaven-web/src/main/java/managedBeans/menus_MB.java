/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbMenuFacadeLocal;
import entities.DbMenu;
import customObjects.parentMenuObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Juan David
 */
@Named(value = "menus_MB")
@SessionScoped
public class menus_MB implements Serializable {

    @EJB
    private DbMenuFacadeLocal dbMenuFacade;

    private List<DbMenu> listMenus;
    private List<parentMenuObject> listParents;
    private DbMenu menuObjectVariable;
    private String menuParent;
    private String menuIndex;
    private boolean menuParentDisabled;

    public menus_MB() {
    }

    @PostConstruct
    public void init() {
        listMenus = dbMenuFacade.findAll();
        menuObjectVariable = new DbMenu();
        menuParent = "";
        menuIndex = "";
    }

    public List<DbMenu> getListMenus() {
        return listMenus;
    }

    public void setListMenus(List<DbMenu> listMenus) {
        this.listMenus = listMenus;
    }

    public DbMenu getMenuObjectVariable() {
        return menuObjectVariable;
    }

    public void setMenuObjectVariable(DbMenu menuObjectVariable) {
        this.menuObjectVariable = menuObjectVariable;
    }

    public String getMenuParent() {
        return menuParent;
    }

    public void setMenuParent(String menuParent) {
        this.menuParent = menuParent;
    }

    public String getMenuIndex() {
        return menuIndex;
    }

    public void setMenuIndex(String menuIndex) {
        this.menuIndex = menuIndex;
    }

    public List<parentMenuObject> getListParents() {
        return listParents;
    }

    public void setListParents(List<parentMenuObject> listParents) {
        this.listParents = listParents;
    }

    public boolean getMenuParentDisabled() {
        return menuParentDisabled;
    }

    public void setMenuParentDisabled(boolean menuParentDisabled) {
        this.menuParentDisabled = menuParentDisabled;
    }

    //Translate menu types M or S to improve user experience
    public String menuType(String inType) {
        String outType = "";

        switch (inType) {
            case "M":
                outType = "Main Menu";
                break;
            case "S":
                outType = "Submenu";
                break;
        }

        return outType;
    }

    //Bring the parent name from the database
    public String parentMenu(String inParent) {
        int inIntParent = 0;
        DbMenu outMenu = new DbMenu();
        String outParent = "";
        inIntParent = Integer.parseInt(inParent);

        if (inIntParent > 0) {
            outMenu = dbMenuFacade.findOneMenu(inIntParent);
            outParent = outMenu.getMenuName();
        }

        return outParent;
    }

    //Load the parents from the database when the menu type is S (Submenu)
    public void loadParents() {
        parentMenuObject tmpParent = new parentMenuObject();
        List<DbMenu> mainMenuList = new ArrayList<>();
        listParents = new ArrayList<>();

        if (menuObjectVariable.getMenuType().equals("S")) {
            mainMenuList = dbMenuFacade.findAllMainMenus();
            if (!mainMenuList.isEmpty()) {
                for (DbMenu tmpMenu : mainMenuList) {
                    tmpParent.setMenuId(tmpMenu.getMenuId().toString());
                    tmpParent.setMenuName(tmpMenu.getMenuName());
                    listParents.add(tmpParent);
                    tmpParent = new parentMenuObject();
                }
            }
            menuParentDisabled = false;
        } else {
            listParents = new ArrayList<>();
            menuParentDisabled = true;
            tmpParent.setMenuId("0");
            tmpParent.setMenuName("No Parent");
            listParents.add(tmpParent);
            tmpParent = new parentMenuObject();
        }
    }

    public String addMenu() {
        String returnAction = "";
        if (generalValidations()) {
            if (menuParent.isEmpty() || "".equals(menuParent)) {
                menuObjectVariable.setParentMenu(0);
            } else {
                menuObjectVariable.setParentMenu(Integer.parseInt(menuParent));
            }

            menuObjectVariable.setIndexMenu(Integer.parseInt(menuIndex));

            if ("".equals(menuObjectVariable.getMenuIcon())) {
                menuObjectVariable.setMenuIcon(null);
            }

            dbMenuFacade.create(menuObjectVariable);
            cleanVariables();
            returnAction = "viewMenus";
        }
        return returnAction;
    }

    public void deleteMenu(DbMenu menuIn) {
        List<DbMenu> tmpListMenusByParent = dbMenuFacade.findMenusByParent(menuIn.getMenuId());
        List<DbMenu> tmpListPagesByParent = dbMenuFacade.findPagesByParent(menuIn.getMenuId());

        boolean validator = true;

        if (!tmpListMenusByParent.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Menu Error", "Menu Error: Menu has at least one SubMenu, "
                            + "change the SubMenus to a different parent and try again."));
            validator = false;
        } else if (!tmpListPagesByParent.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Menu Error", "Menu Error: Menu has at least one Page, "
                            + "change the page(s) to a different parent and try again."));
            validator = false;
        }

        if (validator) {
            dbMenuFacade.remove(menuIn);
            init();
        }
    }

    public String editMenu(DbMenu menuIn) {
        this.menuObjectVariable = menuIn;
        loadParents();
        Integer tmpMenuIndex = menuObjectVariable.getIndexMenu();
        menuIndex = tmpMenuIndex.toString();
        Integer tmpMenuParent = menuObjectVariable.getParentMenu();
        menuParent = tmpMenuParent.toString();
        return "editMenus";
    }

    public String editMenu() {
        String returnAction = "";
        if (generalValidations()) {
            if (menuParent.isEmpty() || "".equals(menuParent)) {
                menuObjectVariable.setParentMenu(0);
            } else {
                menuObjectVariable.setParentMenu(Integer.parseInt(menuParent));
            }

            menuObjectVariable.setIndexMenu(Integer.parseInt(menuIndex));

            if ("".equals(menuObjectVariable.getMenuIcon())) {
                menuObjectVariable.setMenuIcon(null);
            }

            dbMenuFacade.edit(menuObjectVariable);
            cleanVariables();
            returnAction = "viewMenus";
        }
        return returnAction;
    }

    public boolean generalValidations() {
        boolean flag = true;
        int tmpValue = Integer.parseInt(menuIndex);

        if (menuObjectVariable.getMenuType().equals("M")) {
            if (tmpValue % 1000 != 0 || tmpValue == 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Menu Index Error", "Menu Index: Menu Index must be in multiples of 1000."));
                flag = false;
            } else if (dbMenuFacade.findOneMenuByIndex(tmpValue).getMenuId() != null && 
                    !Objects.equals(dbMenuFacade.findOneMenuByIndex(tmpValue).getMenuId(), menuObjectVariable.getMenuId())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Menu Index Error", "Menu Index: The selected Index Menu is alredy used."));
                flag = false;
            }
        } else if (menuObjectVariable.getMenuType().equals("S")) {
            int tmpValueParent = dbMenuFacade.findOneMenu(Integer.parseInt(menuParent)).getIndexMenu();
            int tmpValueParentMax = tmpValueParent + 1000;
            if (tmpValue < tmpValueParent || tmpValue > tmpValueParentMax) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Menu Index Error", "Menu Index: The Index Menu SubMenu be between "
                                + tmpValueParent + " and " + tmpValueParentMax));
                flag = false;
            } else if (tmpValue % 100 != 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Menu Index Error", "Menu Index: SubMenu Index must be in multiples of 100."));
                flag = false;
            } else if (dbMenuFacade.findOneMenuByIndex(tmpValue).getMenuId() != null && 
                    !Objects.equals(dbMenuFacade.findOneMenuByIndex(tmpValue).getMenuId(), menuObjectVariable.getMenuId())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Menu Index Error", "Menu Index: The selected Index SubMenu is alredy used."));
                flag = false;
            }
        }
        return flag;
    }
    
   public void cleanVariables() {
        menuObjectVariable = new DbMenu();
        menuParent = "";
        menuIndex = "";
        init();
    }

    public String cancel() {
        cleanVariables();
        return "viewMenus";
    }

}
