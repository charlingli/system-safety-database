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
 * @author Charling Li
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
    
    private List<DbRole> listRoles;
    private DbRolePage rolePageObject;
    private DbRolePagePK rolePagePKObject;
    private DbRole roleObject;
    private DbPage pageObject;
    private List<permissionsObject> checkedRolePermissions;
    private List<permissionsObject> savedRolePermissions;

    public role_page_MB() {
    }

    public List<DbRole> getListRoles() {
        return listRoles;
    }

    public void setListRoles(List<DbRole> listRoles) {
        this.listRoles = listRoles;
    }

    public List<permissionsObject> getCheckedRolePermissions() {
        return checkedRolePermissions;
    }

    public void setCheckedRolePermissions(List<permissionsObject> checkedRolePermissions) {
        this.checkedRolePermissions = checkedRolePermissions;
    }

    @PostConstruct
    public void init() {
        listRoles = dbRoleFacade.findAll();
        List<String> listRawPermissions = dbRolePageFacade.listPermissions();
        
        int numRoles = listRoles.size();
        checkedRolePermissions = new ArrayList<>();
        savedRolePermissions = new ArrayList<>();
        for (String permissionSet : listRawPermissions) {
            // Loop through everything in the role_page table to format into a checkbox-grid-friendly structure
            HashMap<String, Boolean> checkedPagePermissions = new HashMap<>();
            HashMap<String, Boolean> savedPagePermissions = new HashMap<>();
            
            // Since the query returns a comma-delimited string of permissions grouped by page name, split to list
            List<String> permissionLine = Arrays.asList(permissionSet.split(", "));
            for (int i = 0; i < numRoles; i++) {
                // Use the custom class below to cast the string items into a page name and booleans for the checkboxes
                checkedPagePermissions.put(listRoles.get(i).getRoleName(), Boolean.parseBoolean(permissionLine.get(i + 1)));
                savedPagePermissions.put(listRoles.get(i).getRoleName(), Boolean.parseBoolean(permissionLine.get(i + 1)));
            }
            permissionsObject cpo = new permissionsObject(Integer.parseInt(permissionLine.get(0)), checkedPagePermissions);
            permissionsObject spo = new permissionsObject(Integer.parseInt(permissionLine.get(0)), savedPagePermissions);

            // Save twice so we can check if any changes have occurred later
            checkedRolePermissions.add(cpo);
            savedRolePermissions.add(spo);
        }
    }
    
    public void changePermissions() {
        String responseSTR = "No changes have been made.";
        for (int i = 0; i < checkedRolePermissions.size(); i ++) {
            for (int j = 0; j < listRoles.size(); j ++) {
                if (Boolean.logicalXor(checkedRolePermissions.get(i).permission.get(listRoles.get(j).getRoleName()), savedRolePermissions.get(i).permission.get(listRoles.get(j).getRoleName()))) {
                    // Logical XOR: 1 & 1 =  0, 0 & 0 = 0, 1 & 0 = 1, 0 & 1 = 1
                    //  This lets us determine if a change has occurred between the checked and saved lists
                    rolePageObject = new DbRolePage();
                    rolePagePKObject = new DbRolePagePK();
                    roleObject = new DbRole();
                    pageObject = new DbPage();
                    
                    permissionsObject po = checkedRolePermissions.get(i);
                    
                    rolePagePKObject.setPageId(po.pageId);
                    rolePagePKObject.setRoleId(listRoles.get(j).getRoleId());
                    roleObject.setRoleId(rolePagePKObject.getRoleId());
                    pageObject.setPageId(rolePagePKObject.getPageId());

                    rolePageObject.setAddPermission("Y");
                    rolePageObject.setUpdatePermission("Y");
                    rolePageObject.setDeletePermission("Y");

                    rolePageObject.setDbPage(pageObject);
                    rolePageObject.setDbRole(roleObject);
                    rolePageObject.setDbRolePagePK(rolePagePKObject);
                    
                    if (po.permission.get(listRoles.get(j).getRoleName()).equals(true)) {
                        dbRolePageFacade.create(rolePageObject);
                    } else {
                        dbRolePageFacade.remove(rolePageObject);
                    }
                    
                    responseSTR = "Access permissions have been updated!";
                }
            }
        }
        init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", responseSTR));
    }
    
    public void resetPermissions() {
        init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "All changes have been reset."));
    }
    
    public String getPageName(int id) {
        return dbPageFacade.findByName("pageId", Integer.toString(id)).get(0).getPageName();
    }
    
    public class permissionsObject {
        public Integer pageId;
        public HashMap<String, Boolean> permission;
        
        public permissionsObject(Integer pageId, HashMap<String, Boolean> permission) {
            this.pageId = pageId;
            this.permission = permission;
        }

        public HashMap<String, Boolean> getPermission() {
            return permission;
        }

        public void setPermission(HashMap<String, Boolean> permission) {
            this.permission = permission;
        }

        public Integer getPageId() {
            return pageId;
        }

        public void setPageId(Integer pageId) {
            this.pageId = pageId;
        }
        
    }
}
