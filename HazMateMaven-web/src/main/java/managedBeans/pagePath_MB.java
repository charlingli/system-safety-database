/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbMenuFacadeLocal;
import ejb.DbPageFacadeLocal;
import entities.DbMenu;
import entities.DbPage;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Admin
 *
 */
@Named(value = "pagePath_MB")
@RequestScoped
public class pagePath_MB {

    @EJB
    private DbMenuFacadeLocal dbMenuFacade;

    @EJB
    private DbPageFacadeLocal dbPageFacade;

    public pagePath_MB() {
    }

    public String getPagePath() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        String viewId = ctx.getViewRoot().getViewId();
        String viewTitle = "";
        int iend = viewId.indexOf(".");
        if (iend != -1) {
            viewTitle = viewId.substring(0, iend);
        }

        DbPage tempPage = dbPageFacade.retrievePageName(viewTitle);

        String pageName = tempPage.getPageName();
        DbMenu tempMenu = dbMenuFacade.find(tempPage.getMenuId().getMenuId());
        String pagePath = "";

        if (tempMenu.getMenuType().equals("M")) {
            pagePath = tempMenu.getMenuName() + " > " + pageName;
        } else if (tempMenu.getMenuType().equals("S")) {
            DbMenu parMenu = dbMenuFacade.find(tempMenu.getParentMenu());
            pagePath = parMenu.getMenuName() + " > " + tempMenu.getMenuName() + " > " + pageName;
        }

        return pagePath;
    }

    public String getPageHtmlPath() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        String viewId = ctx.getViewRoot().getViewId();
        String viewTitle = "";
        int iend = viewId.indexOf(".");
        if (iend != -1) {
            viewTitle = viewId.substring(0, iend);
        }

        DbPage tempPage = dbPageFacade.retrievePageName(viewTitle);
        String pageName = tempPage.getPageName();
        DbMenu tempMenu = dbMenuFacade.find(tempPage.getMenuId().getMenuId());
        String pagePath = "";

        if (tempMenu.getMenuType().equals("M")) {
            //pagePath = tempMenu.getMenuName() + " > " + pageName;
            pagePath = "<p><h4><strong> " + tempMenu.getMenuName() + " > " + "<span class=\"navigationPath\"> " + pageName + " </span></strong></h4></p>";
        } else if (tempMenu.getMenuType().equals("S")) {
            DbMenu parMenu = dbMenuFacade.find(tempMenu.getParentMenu());
            //pagePath = parMenu.getMenuName() + " > " + tempMenu.getMenuName() + " > " + pageName;
            pagePath = "<p><h4><strong> " + parMenu.getMenuName() + " > " + tempMenu.getMenuName() + " > "
                    + "<span class=\"navigationPath\"> " + pageName + " </span></strong></h4></p>";
        }

        return pagePath;
    }

    public String buildPageHtmlPath(String path) {
        String result = "";
        List<String> pathList = Arrays.asList(path.split(","));
        if (pathList.size() > 1) {
            result += "<p><h4><strong> ";
            for (int i = 1; i <= pathList.size(); i++) {
                if (i != pathList.size()) {
                    result += pathList.get(i - 1) + " > ";
                } else {
                    result += "<span class=\"navigationPath\"> " + pathList.get(i - 1) + " </span></strong></h4></p>";
                }
            }
        }
        return result;
    }
}
