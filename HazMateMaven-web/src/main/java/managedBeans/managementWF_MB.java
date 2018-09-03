/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import customObjects.searchObject;
import ejb.DbUserFacadeLocal;
import ejb.DbwfHeaderFacadeLocal;
import ejb.DbwfLineFacadeLocal;
import entities.DbwfHeader;
import entities.DbUser;
import entities.DbwfLine;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author lxra
 * 
 */
@Named(value = "managementWF_MB")
@ViewScoped
public class managementWF_MB implements Serializable {

    @EJB
    private DbUserFacadeLocal dbUserFacade;

    @EJB
    private DbwfLineFacadeLocal dbwfLineFacade;

    @EJB
    private DbwfHeaderFacadeLocal dbwfHeaderFacade;
    
    private List<DbwfHeader> listWF;
    private List<DbwfHeader> selectWF;
    private DbwfHeader detailWF;
    private List<DbwfLine> detailLine;
    private DbwfHeader requestWF;
    private String requestInfo;
    private boolean searchBox;
    private boolean dataTable;
    private boolean cancelBtn;
    private boolean infoBox;
    private String wfID;
    private String wfStatus;
    private String wfAddDT;
    private DbUser wfAddUser;
    private String wfUpdateDT;
    private DbUser wfUpdateUser; 
    private String wfObjectID;
    private String wfObjectName;
    private String wfComment1;
    private String wfComment2;
    private String wfComment3;
    private String wfComment4;

    public managementWF_MB() {
    }

    public List<DbwfHeader> getListWF() {
        return listWF;
    }

    public void setListWF(List<DbwfHeader> listWF) {
        this.listWF = listWF;
    }

    public List<DbwfHeader> getSelectWF() {
        return selectWF;
    }

    public void setSelectWF(List<DbwfHeader> selectWF) {
        this.selectWF = selectWF;
    }

    public DbwfHeader getDetailWF() {
        return detailWF;
    }

    public void setDetailWF(DbwfHeader detailWF) {
        this.detailWF = detailWF;
    }

    public List<DbwfLine> getDetailLine() {
        return detailLine;
    }

    public void setDetailLine(List<DbwfLine> detailLine) {
        this.detailLine = detailLine;
    }

    public DbwfHeader getRequestWF() {
        return requestWF;
    }

    public void setRequestWF(DbwfHeader requestWF) {
        this.requestWF = requestWF;
    }

    public String getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(String requestInfo) {
        this.requestInfo = requestInfo;
    }

    public boolean isSearchBox() {
        return searchBox;
    }

    public void setSearchBox(boolean searchBox) {
        this.searchBox = searchBox;
    }

    public boolean isDataTable() {
        return dataTable;
    }

    public void setDataTable(boolean dataTable) {
        this.dataTable = dataTable;
    }

    public boolean isCancelBtn() {
        return cancelBtn;
    }

    public void setCancelBtn(boolean cancelBtn) {
        this.cancelBtn = cancelBtn;
    }

    public boolean isInfoBox() {
        return infoBox;
    }

    public void setInfoBox(boolean infoBox) {
        this.infoBox = infoBox;
    }

    public String getWfID() {
        return wfID;
    }

    public void setWfID(String wfID) {
        this.wfID = wfID;
    }

    public String getWfStatus() {
        return wfStatus;
    }

    public void setWfStatus(String wfStatus) {
        this.wfStatus = wfStatus;
    }

    public String getWfAddDT() {
        return wfAddDT;
    }

    public void setWfAddDT(String wfAddDT) {
        this.wfAddDT = wfAddDT;
    }

    public DbUser getWfAddUser() {
        return wfAddUser;
    }

    public void setWfAddUser(DbUser wfAddUser) {
        this.wfAddUser = wfAddUser;
    }

    public String getWfUpdateDT() {
        return wfUpdateDT;
    }

    public void setWfUpdateDT(String wfUpdateDT) {
        this.wfUpdateDT = wfUpdateDT;
    }

    public DbUser getWfUpdateUser() {
        return wfUpdateUser;
    }

    public void setWfUpdateUser(DbUser wfUpdateUser) {
        this.wfUpdateUser = wfUpdateUser;
    }

    public String getWfObjectID() {
        return wfObjectID;
    }

    public void setWfObjectID(String wfObjectID) {
        this.wfObjectID = wfObjectID;
    }

    public String getWfObjectName() {
        return wfObjectName;
    }

    public void setWfObjectName(String wfObjectName) {
        this.wfObjectName = wfObjectName;
    }

    public String getWfComment1() {
        return wfComment1;
    }

    public void setWfComment1(String wfComment1) {
        this.wfComment1 = wfComment1;
    }

    public String getWfComment2() {
        return wfComment2;
    }

    public void setWfComment2(String wfComment2) {
        this.wfComment2 = wfComment2;
    }

    public String getWfComment3() {
        return wfComment3;
    }

    public void setWfComment3(String wfComment3) {
        this.wfComment3 = wfComment3;
    }

    public String getWfComment4() {
        return wfComment4;
    }

    public void setWfComment4(String wfComment4) {
        this.wfComment4 = wfComment4;
    }
    
    @PostConstruct
    public void init() {
        searchBox = true;
        dataTable = false;
        cancelBtn = false;
        infoBox = false;
    }
    
    private List<searchObject> createSearchList() {
        List<searchObject> searchList = new ArrayList<>();
        searchObject tmpSearchObj = new searchObject();
        if (!"".equals(wfID)) {
            tmpSearchObj.setEntity1Name("DbwfHeader");
            tmpSearchObj.setFieldName("wfId");
            tmpSearchObj.setFieldType("string");
            tmpSearchObj.setRelationType("like");
            tmpSearchObj.setUserInput(wfID);
            searchList.add(tmpSearchObj);
            tmpSearchObj = new searchObject();
        }

        if (!"".equals(wfObjectID)) {
            tmpSearchObj.setEntity1Name("DbwfHeader");
            tmpSearchObj.setFieldName("wfObjectId");
            tmpSearchObj.setFieldType("string");
            tmpSearchObj.setRelationType("like");
            tmpSearchObj.setUserInput(wfObjectID);
            searchList.add(tmpSearchObj);
            tmpSearchObj = new searchObject();
        }

        if (!"".equals(wfComment1)) {
            tmpSearchObj.setEntity1Name("DbwfHeader");
            tmpSearchObj.setFieldName("wfComment1");
            tmpSearchObj.setFieldType("string");
            tmpSearchObj.setRelationType("like");
            tmpSearchObj.setUserInput(wfComment1);
            searchList.add(tmpSearchObj);
            tmpSearchObj = new searchObject();
        }

        if (!"".equals(wfComment2)) {
            tmpSearchObj.setEntity1Name("DbwfHeader");
            tmpSearchObj.setFieldName("wfComment2");
            tmpSearchObj.setFieldType("string");
            tmpSearchObj.setRelationType("like");
            tmpSearchObj.setUserInput(wfComment2);
            searchList.add(tmpSearchObj);
            tmpSearchObj = new searchObject();
        }

        if (!"".equals(wfComment3)) {
            tmpSearchObj.setEntity1Name("DbwfHeader");
            tmpSearchObj.setFieldName("wfComment3");
            tmpSearchObj.setFieldType("string");
            tmpSearchObj.setRelationType("like");
            tmpSearchObj.setUserInput(wfComment3);
            searchList.add(tmpSearchObj);
            tmpSearchObj = new searchObject();
        }

        if (!"".equals(wfComment4)) {
            tmpSearchObj.setEntity1Name("DbwfHeader");
            tmpSearchObj.setFieldName("wfComment4");
            tmpSearchObj.setFieldType("string");
            tmpSearchObj.setRelationType("like");
            tmpSearchObj.setUserInput(wfComment4);
            searchList.add(tmpSearchObj);
        }

        return searchList;
    }
    
    public void searchWorkflows() {
        listWF = dbwfHeaderFacade.findWorkflowsByFieldsOnly(createSearchList());
    }
    
    public void showDetail(DbwfHeader wfObject) {
        setDetailWF(wfObject);
        setDetailLine(dbwfLineFacade.findAllOfWF(wfObject.getWfId()));
    }
    
    public void approveItems(List<DbwfHeader> wfItems) {
        if (wfItems.size() < 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "No items selected for approval."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Approvals sent for all items."));
            for (DbwfHeader wfItem : wfItems) {
                dbwfHeaderFacade.approvalProcess(wfItem, "adminApproval");
            }
        }
    }
    
    public void approveItem(DbwfHeader wfItem) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Approval sent for item."));
        System.out.println(wfItem.getWfId());
        dbwfHeaderFacade.approvalProcess(wfItem, "adminApproval");
    }
    
    public void rejectItems(List<DbwfHeader> wfItems) {
        for (DbwfHeader wfItem : wfItems) {
//            dbwfHeaderFacade.approvalProcess(wfItem, "adminApproval");
        }
        System.out.println("Not yet supported");
    }
    
    public void rejectItem(DbwfHeader wfItem) {
        System.out.println("Not yet supported");
    }
    
    public void requestItem(DbwfHeader wfItem) {
        System.out.println("Not yet supported");
        setRequestWF(wfItem);
    }
    
    public void sendRequest() {
        System.out.println("Not yet supported");
        System.out.println("Sending request of " + getRequestWF().getWfId() + " with message " + requestInfo);
        setRequestInfo("");
        setRequestWF(null);
    }
}
