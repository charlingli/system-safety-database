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
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Juan David
 */
@Named(value = "menus_MB")
@ViewScoped
public class menus_MB implements Serializable {

    @EJB
    private DbMenuFacadeLocal dbMenuFacade;

    private List<DbMenu> listMenus;
    private List<parentMenuObject> listParents;
    private DbMenu menuObjectVariable;
    private String menuParent;
    private String menuIndex;
    private boolean menuParentDisabled;
    private List<Integer> availableIndices;
    private boolean addFlag;
    private boolean editFlag;

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

    public List<Integer> getAvailableIndices() {
        return availableIndices;
    }

    public void setAvailableIndices(List<Integer> availableIndices) {
        this.availableIndices = availableIndices;
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

    public menus_MB() {
    }

    @PostConstruct
    public void init() {
        listMenus = dbMenuFacade.findAll();
        menuObjectVariable = new DbMenu();
        menuParent = "";
        menuIndex = "";
        addFlag = false;
        editFlag = false;
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
        listParents = new ArrayList<>();
        if (menuObjectVariable.getMenuType().equals("S")) {
            if (menuParent == null) {
                menuParent = listMenus.get(0).getMenuId().toString();
            }
            List<DbMenu> mainMenuList = dbMenuFacade.findAllMainMenus();
            if (!mainMenuList.isEmpty()) {
                for (DbMenu tmpMenu : mainMenuList) {
                    if (!tmpMenu.getMenuId().equals(menuObjectVariable.getMenuId())) {
                        tmpParent.setMenuId(tmpMenu.getMenuId().toString());
                        tmpParent.setMenuName(tmpMenu.getMenuName());
                        listParents.add(tmpParent);
                        tmpParent = new parentMenuObject();
                    }
                }
            }
            menuParentDisabled = false;
        } else {
            listParents = new ArrayList<>();
            menuParentDisabled = true;
            tmpParent.setMenuId("0");
            tmpParent.setMenuName("No Parent");
            listParents.add(tmpParent);
        }
    }
    
    public void showAdd() {
        menuObjectVariable = new DbMenu();
        menuObjectVariable.setMenuType("M");
        menuParent = listMenus.get(0).getMenuId().toString();
        loadParents();
        findAvailableIndices();
        addFlag = true;
    }

    public void addMenu() {
        if (menuParent.isEmpty() || "".equals(menuParent) || menuObjectVariable.getMenuType().equals("M")) {
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
        addFlag = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "The menu has been successfully added."));
    }

    public void deleteMenu(DbMenu menuIn) {
        List<DbMenu> tmpListMenusByParent = dbMenuFacade.findMenusByParent(menuIn.getMenuId());
        List<DbMenu> tmpListPagesByParent = dbMenuFacade.findPagesByParent(menuIn.getMenuId());
        boolean validator = true;
        if (!tmpListMenusByParent.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The menu has submenus linked to it!"));
            validator = false;
        } else if (!tmpListPagesByParent.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The menu has pages linked to it!"));
            validator = false;
        }
        if (validator) {
            dbMenuFacade.remove(menuIn);
            init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "The menu has been successfully deleted."));
        }
    }

    public void editMenu(DbMenu menuIn) {
        this.menuObjectVariable = menuIn;
        Integer tmpMenuIndex = menuObjectVariable.getIndexMenu();
        menuIndex = tmpMenuIndex.toString();
        menuParent = String.valueOf(menuObjectVariable.getParentMenu());
        loadParents();
        findAvailableIndices();
        editFlag = true;
    }

    public void editMenu() {
        if (menuParent.isEmpty() || "".equals(menuParent) || menuObjectVariable.getMenuType().equals("M")) {
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
        editFlag = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "The menu has been successfully edited."));
    }
    
    public List<String> findAvailableIndices() {
        availableIndices = new ArrayList<>();
        if (menuObjectVariable.getMenuType().equals("M")) {
            availableIndices = IntStream.range(1, 20).map(i -> i * 1000).boxed().collect(Collectors.toList());
            List<DbMenu> takenMenus = dbMenuFacade.findAllMainMenus();
            for (Integer takenIndex : takenMenus.stream().map(DbMenu::getIndexMenu).collect(Collectors.toList())) {
                if (availableIndices.contains(takenIndex) && !takenIndex.equals(menuObjectVariable.getIndexMenu())) {
                    availableIndices.remove(takenIndex);
                }
            }
        } else if (menuObjectVariable.getMenuType().equals("S")) {
            if (menuParent.equals("0")) {
                menuParent = String.valueOf(listMenus.get(0).getMenuId());
            }
            int parentIndex = dbMenuFacade.findByName("menuId", menuParent).get(0).getIndexMenu();
            availableIndices = IntStream.range(1, 10).map(i -> i * 100 + parentIndex).boxed().collect(Collectors.toList());
            List<DbMenu> takenMenus = dbMenuFacade.findMenusByParent(Integer.parseInt(menuParent));
            for (Integer takenIndex : takenMenus.stream().map(DbMenu::getIndexMenu).collect(Collectors.toList())) {
                if (availableIndices.contains(takenIndex) && !takenIndex.equals(menuObjectVariable.getIndexMenu())) {
                    availableIndices.remove(takenIndex);
                }
            }
        }
        return availableIndices.stream().map(i -> i.toString()).collect(Collectors.toList());
    }
    
   public void cleanVariables() {
        menuObjectVariable = new DbMenu();
        menuParent = "";
        menuIndex = "";
        init();
    }
}
