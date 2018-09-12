/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import customObjects.searchObject;
import ejb.DbUserFacadeLocal;
import ejb.DbwfDecisionFacadeLocal;
import ejb.DbwfHeaderFacadeLocal;
import ejb.DbwfLineFacadeLocal;
import ejb.DbwfTypeFacadeLocal;
import entities.DbwfHeader;
import entities.DbUser;
import entities.DbwfDecision;
import entities.DbwfLine;
import entities.DbwfLinePK;
import entities.DbwfType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
    private DbwfDecisionFacadeLocal dbwfDecisionFacade;

    @EJB
    private DbwfTypeFacadeLocal dbwfTypeFacade;

    @EJB
    private DbwfLineFacadeLocal dbwfLineFacade;

    @EJB
    private DbwfHeaderFacadeLocal dbwfHeaderFacade;
    
    private List<DbwfHeader> listWF;
    private List<DbwfHeader> selectWF;
    private DbwfHeader detailWF;
    private List<DbwfLine> detailLine;
    private DbwfHeader approvalWF;
    private String approvalDecision;
    private String wfID;
    private List<String> wfStatus;
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
    private List<DbwfType> listwfType;
    private List<DbwfDecision> listwfDecision;
    private DbUser activeUser;

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

    public DbwfHeader getApprovalWF() {
        return approvalWF;
    }

    public void setApprovalWF(DbwfHeader approvalWF) {
        this.approvalWF = approvalWF;
    }

    public String getApprovalDecision() {
        return approvalDecision;
    }

    public void setApprovalDecision(String approvalDecision) {
        this.approvalDecision = approvalDecision;
    }

    public String getWfID() {
        return wfID;
    }

    public void setWfID(String wfID) {
        this.wfID = wfID;
    }

    public List<String> getWfStatus() {
        return wfStatus;
    }

    public void setWfStatus(List<String> wfStatus) {
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

    public List<DbwfType> getListwfType() {
        return listwfType;
    }

    public void setListwfType(List<DbwfType> listwfType) {
        this.listwfType = listwfType;
    }

    public List<DbwfDecision> getListwfDecision() {
        return listwfDecision;
    }

    public void setListwfDecision(List<DbwfDecision> listwfDecision) {
        this.listwfDecision = listwfDecision;
    }
    
    @PostConstruct
    public void init() {
        activeUser = (DbUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("activeUser");
        setListwfType(dbwfTypeFacade.findAll());
        setListwfDecision(dbwfDecisionFacade.findAll());
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
            tmpSearchObj = new searchObject();
        }
        if (wfStatus.size() > 0) {
            tmpSearchObj.setEntity1Name("DbwfHeader");
            tmpSearchObj.setFieldName("wfStatus");
            tmpSearchObj.setFieldType("string");
            tmpSearchObj.setRelationType("in");
            String userInputString = "";
            for (String selectedOption : wfStatus) {
                userInputString += selectedOption + ",";
            }
            tmpSearchObj.setUserInput(userInputString);
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
    
    public void prepareDecision(DbwfHeader wfItem, String decisionId) {
        setApprovalWF(wfItem);
        setApprovalDecision(decisionId);
    }
    
    public void sendDecision(String approvalComment) {
        DbwfLine tmpLine = dbwfLineFacade.findByIdAndUser(new DbwfLine(new DbwfLinePK(getApprovalWF().getWfId(), "")), activeUser.getUserId());
        tmpLine.setWfApproverDecisionId(new DbwfDecision(getApprovalDecision()));
        tmpLine.setWfApprovalComment(approvalComment);
        tmpLine.setWfDateTimeDecision(new Date());
        dbwfLineFacade.edit(tmpLine);
        
        if (getApprovalDecision().equals("A")) {
            dbwfHeaderFacade.approvalProcess(new DbwfHeader(getApprovalWF().getWfId()), "adminApproval");
        } else if (getApprovalDecision().equals("R")) {
            dbwfHeaderFacade.rejectionProcess(new DbwfHeader(getApprovalWF().getWfId()), "adminApproval");
        } else if (getApprovalDecision().equals("I")) {
            dbwfHeaderFacade.reviewProcess(new DbwfHeader(getApprovalWF().getWfId()), "adminApproval");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "No logic associated with this decision type."));
        }
        setApprovalWF(null);
        searchWorkflows();
        init();
    }
    
    public void prepareDecisions(List<DbwfHeader> wfItems, String decisionId) {
        if (wfItems.size() < 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "No items selected for approval."));
        }
        setApprovalDecision(decisionId);
    }
    
    public void sendDecisions(String approvalComment) {
        for (DbwfHeader wfItem : selectWF) {
            DbwfLine tmpLine = dbwfLineFacade.findByIdAndUser(new DbwfLine(new DbwfLinePK(wfItem.getWfId(), "")), activeUser.getUserId());
            tmpLine.setWfApproverDecisionId(new DbwfDecision(getApprovalDecision()));
            tmpLine.setWfApprovalComment(approvalComment);
            tmpLine.setWfDateTimeDecision(new Date());
            dbwfLineFacade.edit(tmpLine);
            
            if (getApprovalDecision().equals("A")) {
                dbwfHeaderFacade.approvalProcess(new DbwfHeader(wfItem.getWfId()), "adminApproval");
            } else if (getApprovalDecision().equals("R")) {
                dbwfHeaderFacade.rejectionProcess(new DbwfHeader(wfItem.getWfId()), "adminApproval");
            } else if (getApprovalDecision().equals("I")) {
                dbwfHeaderFacade.reviewProcess(new DbwfHeader(wfItem.getWfId()), "adminApproval");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "No logic associated with this decision type."));
            }
        }
        setApprovalWF(null);
        searchWorkflows();
        init();
    }
}