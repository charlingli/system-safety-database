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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Admin
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
    
    @PostConstruct
    public void init() {
    
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

}
