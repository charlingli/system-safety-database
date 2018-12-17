/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import entities.*;
import javax.ejb.EJB;
import ejb.DbRoleFacadeLocal;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 *
 * @author alan8
 */
@Named(value = "roles_MB")
@ViewScoped
public class roles_MB implements Serializable {

    @EJB
    private DbRoleFacadeLocal dbRoleFacade;
    private List<DbRole> listDbRole;
    private DbRole roleObject;
    private String strStatus;
    private List listDbUser;
    private boolean wfApprover;
    
    private boolean addFlag;
    private boolean editFlag;

    public roles_MB() {

    }
 
   public List<DbRole> getListDbRole() {
        return listDbRole;
    }

    public void setListDbRole(List<DbRole> listDbRole) {
        this.listDbRole = listDbRole;
    }

    public DbRole getRoleObject() {
        return roleObject;
    }

    public void setRoleObject(DbRole roleObject) {
        this.roleObject = roleObject;
    }

    public String getStrStatus() {
        return strStatus;
    }

    public void setStrStatus(String strStatus) {
        this.strStatus = strStatus;
    }

    public boolean isWfApprover() {
        return wfApprover;
    }

    public void setWfApprover(boolean wfApprover) {
        this.wfApprover = wfApprover;
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
    
    @PostConstruct
    public void init() {
        listDbRole = dbRoleFacade.findAll();
        roleObject = new DbRole();
        wfApprover = false;
        addFlag = false;
        editFlag = false;
    }
    
    public void showAdd() {
        roleObject = new DbRole();
        addFlag = true;
    }

    public void add() {
        if (duplicateValidations()) {
            error1();
        }
        setWfApprover();
        dbRoleFacade.create(roleObject);
        roleObject = new DbRole();      //re-initialise roleObject for future calls of this method
        init();     //Update listDbRole so that the newly created role appears in the roles table
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "The role has been successfully added. Remember to use the permissions page to assign access."));
    }

    public void delete(DbRole roleObject) {
        listDbUser = dbRoleFacade.checkUser(roleObject.getRoleId());
        if (listDbUser.isEmpty()) {
            dbRoleFacade.remove(roleObject);
        } else {
            error();
            return;
        }
        init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "The role has been successfully deleted."));
    }

    public void edit(DbRole roleObject) {
        this.roleObject = roleObject;
        strStatus = Short.toString(this.roleObject.getRoleStatus()); //pre-populate role status field 
        System.out.println(strStatus);
        if (roleObject.getRoleWFApprover().equals("Y")) 
            wfApprover = true;
        editFlag = true;
    }

    public void edit() {
        if (duplicateValidations()) {
            error1();
        }
        setWfApprover();
        dbRoleFacade.edit(roleObject);
        roleObject = new DbRole();
        init();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: ", "The role has been successfully edited."));
    }

    public void cancel() {
        init();
    }

    public String printStatus(Short roleStatus) {
        switch (roleStatus) {
            case 0:
                strStatus = "Inactive";
                break;
            case 1:
                strStatus = "Active";
                break;
            default:
                strStatus = "NULL";
        }
        return strStatus;
    }
    
    public void setWfApprover(){
        if (wfApprover){
            roleObject.setRoleWFApprover("Y");
        } else {
            roleObject.setRoleWFApprover("N");
        }
    }
    
    public boolean duplicateValidations() {
        List<DbRole> validateDuplicates = dbRoleFacade.findByName("roleName", roleObject.getRoleName());
        if (!validateDuplicates.isEmpty() && validateDuplicates.get(0).equals(roleObject)) {
                return false;
        }
        return !validateDuplicates.isEmpty();
    }

    public void error() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The role is currently assigned to one or more users!"));
    }

    public void error1() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "The role name already exists!"));
    }

}
