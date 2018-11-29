/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import customObjects.fileHeaderObject;
import customObjects.searchObject;
import customObjects.treeNodeObject;
import ejb.DbFilesFacadeLocal;
import ejb.DbHazardFacadeLocal;
import ejb.DbHazardFilesFacadeLocal;
import ejb.DbUserFacadeLocal;
import ejb.DbtreeLevel1FacadeLocal;
import ejb.DbwfDecisionFacadeLocal;
import ejb.DbwfHeaderFacadeLocal;
import ejb.DbwfLineFacadeLocal;
import ejb.DbwfTypeFacadeLocal;
import entities.DbControlHazard;
import entities.DbHazard;
import entities.DbHazardCause;
import entities.DbHazardConsequence;
import entities.DbHazardSbs;
import entities.DbwfHeader;
import entities.DbUser;
import entities.DbtreeLevel1;
import entities.DbtreeLevel2;
import entities.DbtreeLevel3;
import entities.DbtreeLevel4;
import entities.DbtreeLevel5;
import entities.DbtreeLevel6;
import entities.DbwfDecision;
import entities.DbwfLine;
import entities.DbwfLinePK;
import entities.DbwfType;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
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
    private DbHazardFilesFacadeLocal dbHazardFilesFacade;

    @EJB
    private DbFilesFacadeLocal dbFilesFacade;

    @EJB
    private DbtreeLevel1FacadeLocal dbtreeLevel1Facade;

    @EJB
    private DbHazardFacadeLocal dbHazardFacade;

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
    private String approvalComment;
    private String multipleComment;
    private String approvalDecision;
    private DbHazard detailHazard;
    private List<DbHazardCause> detailCauses;
    private List<DbHazardConsequence> detailConsequences;
    private List<DbControlHazard> detailControls;
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
    private List<DbwfType> listwfType;
    private List<DbwfDecision> listwfDecision;
    private DbUser activeUser;
    private List<fileHeaderObject> fileHeaders;

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

    public String getApprovalComment() {
        return approvalComment;
    }

    public void setApprovalComment(String approvalComment) {
        this.approvalComment = approvalComment;
    }

    public String getMultipleComment() {
        return multipleComment;
    }

    public void setMultipleComment(String multipleComment) {
        this.multipleComment = multipleComment;
    }

    public String getApprovalDecision() {
        return approvalDecision;
    }

    public void setApprovalDecision(String approvalDecision) {
        this.approvalDecision = approvalDecision;
    }

    public DbHazard getDetailHazard() {
        return detailHazard;
    }

    public void setDetailHazard(DbHazard detailHazard) {
        this.detailHazard = detailHazard;
    }

    public List<DbHazardCause> getDetailCauses() {
        return detailCauses;
    }

    public void setDetailCauses(List<DbHazardCause> detailCauses) {
        this.detailCauses = detailCauses;
    }

    public List<DbHazardConsequence> getDetailConsequences() {
        return detailConsequences;
    }

    public void setDetailConsequences(List<DbHazardConsequence> detailConsequences) {
        this.detailConsequences = detailConsequences;
    }

    public List<DbControlHazard> getDetailControls() {
        return detailControls;
    }

    public void setDetailControls(List<DbControlHazard> detailControls) {
        this.detailControls = detailControls;
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

    public List<fileHeaderObject> getFileHeaders() {
        return fileHeaders;
    }

    public void setFileHeaders(List<fileHeaderObject> fileHeaders) {
        this.fileHeaders = fileHeaders;
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
        if (!"".equals(wfStatus)) {
            tmpSearchObj.setEntity1Name("DbwfHeader");
            tmpSearchObj.setFieldName("wfStatus");
            tmpSearchObj.setFieldType("string");
            tmpSearchObj.setRelationType("like");
            tmpSearchObj.setUserInput(wfStatus);
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
    
    public void showHazard(DbwfHeader wfHeader) {
        setDetailHazard(dbHazardFacade.findByName("hazardId", wfHeader.getWfObjectId()).get(0));
        detailCauses = dbHazardFacade.getHazardCause(detailHazard.getHazardId());
        detailConsequences = dbHazardFacade.getHazardConsequence(detailHazard.getHazardId());
        detailControls = dbHazardFacade.getControlHazard(detailHazard.getHazardId());
        fileHeaders = dbHazardFilesFacade.findHeadersForHazard(detailHazard.getHazardId());
    }
    
    public void prepareDecision(DbwfHeader wfItem, String decisionId) {
        setApprovalWF(wfItem);
        setApprovalDecision(decisionId);
    }
    
    public void sendDecision() {
        if (getApprovalComment().length() < 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "You must leave a comment justifying your decision."));
        } else {
            if (getApprovalDecision().equals("A")) {
                dbwfHeaderFacade.approvalProcess(new DbwfHeader(getApprovalWF().getWfId(), getApprovalComment()), "adminApproval");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Approval sent for workflow " + getApprovalWF().getWfId() + "."));
            } else if (getApprovalDecision().equals("R")) {
                dbwfHeaderFacade.rejectionProcess(new DbwfHeader(getApprovalWF().getWfId(), getApprovalComment()), "adminApproval");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Rejection sent for workflow " + getApprovalWF().getWfId() + "."));
            } else if (getApprovalDecision().equals("I")) {
                dbwfHeaderFacade.reviewProcess(new DbwfHeader(getApprovalWF().getWfId(), getApprovalComment()), "adminApproval");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Request for information sent for workflow " + getApprovalWF().getWfId() + "."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "No logic associated with this decision type."));
            }
            setApprovalWF(null);
            setApprovalComment(null);
            searchWorkflows();
            init();
        }
    }
    
    public void prepareDecisions(List<DbwfHeader> wfItems, String decisionId) {
        if (wfItems.size() < 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "No items selected for approval."));
        }
        setApprovalDecision(decisionId);
    }
    
    public void sendDecisions() {
        if (approvalComment.length() < 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "You must leave a comment justifying your decision."));
        } else {
            boolean validRequest = true;
            String wfNotOpenMessage = "";
            for (DbwfHeader wfItem : selectWF) {
                if (wfItem.getWfStatus().equals("O")) {
                    if (getApprovalDecision().equals("A")) {
                        dbwfHeaderFacade.approvalProcess(new DbwfHeader(wfItem.getWfId(), getMultipleComment()), "adminApproval");
                    } else if (getApprovalDecision().equals("R")) {
                        dbwfHeaderFacade.rejectionProcess(new DbwfHeader(wfItem.getWfId(), getMultipleComment()), "adminApproval");
                    } else if (getApprovalDecision().equals("I")) {
                        if (wfItem.getWfCompleteMethod().equals("HazardApprovalWF")) {
                            dbwfHeaderFacade.reviewProcess(new DbwfHeader(wfItem.getWfId(), getMultipleComment()), "adminApproval");
                        } else {
                            validRequest = false;
                        }
                    }
                } else {
                    wfNotOpenMessage = ", however, some have already completed";
                }
            }
            if (validRequest) {
                if (getApprovalDecision().equals("A")) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Approvals sent for workflows" + wfNotOpenMessage + "."));
                } else if (getApprovalDecision().equals("R")) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Rejections sent for workflows" + wfNotOpenMessage + "."));
                } else if (getApprovalDecision().equals("I")) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Requests for information sent for workflows" + wfNotOpenMessage + "."));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "No logic associated with this decision type."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning:", "Workflows for suggestions and deletions cannot request information."));
            }
            setApprovalWF(null);
            setMultipleComment(null);
            searchWorkflows();
            init();
        }
    }
    
    public List<String> viewSbsCondensed() {
        List<DbHazardSbs> listDbHazardSbs = dbHazardFacade.getSbs(detailHazard.getHazardId());

        List<String> sbsChildren = new ArrayList<>();
        List<String> nodeNames = new ArrayList<>();
        String previousNode = "";
        String currentNode;

        for (DbHazardSbs check : listDbHazardSbs) {
            if (sbsChildren.isEmpty()) {
                previousNode = check.getDbHazardSbsPK().getSbsId();
                sbsChildren.add(previousNode);
            } else {
                currentNode = check.getDbHazardSbsPK().getSbsId();
                if (!currentNode.startsWith(previousNode)) {
                    sbsChildren.add(currentNode);
                    previousNode = currentNode;
                }
            }
        }
        
        for (String nodeId : sbsChildren) {
            List<treeNodeObject> treeHazardSbsList = new ArrayList<>();
            String nodeName = "";
            String parts[] = nodeId.split("\\.");
            if (nodeId.equals("")) {
                nodeName = "";
            } else {
                for (int i = 1; i <= parts.length; i++) {
                    switch (i) {
                        case 1:
                            DbtreeLevel1 tmpDbLvl1 = dbtreeLevel1Facade.findByIndex(Integer.parseInt(parts[0]));
                            if (tmpDbLvl1.getTreeLevel1Name() != null) {
                                treeHazardSbsList.add(new treeNodeObject(nodeId,
                                        tmpDbLvl1.getTreeLevel1Name()));
                                nodeName = treeHazardSbsList.get(0).getNodeName();
                            }
                            break;
                        case 2:
                            DbtreeLevel2 tmpDbLvl2 = dbtreeLevel1Facade.findByIndex(Integer.parseInt(parts[0]),
                                    Integer.parseInt(parts[1]));
                            if (tmpDbLvl2.getTreeLevel2Name() != null) {
                                treeHazardSbsList.add(new treeNodeObject(nodeId,
                                        tmpDbLvl2.getTreeLevel2Name()));
                                nodeName = treeHazardSbsList.get(1).getNodeName();
                            }
                            break;
                        case 3:
                            DbtreeLevel3 tmpDbLvl3 = dbtreeLevel1Facade.findByIndex(Integer.parseInt(parts[0]),
                                    Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                            if (tmpDbLvl3.getTreeLevel3Name() != null) {
                                treeHazardSbsList.add(new treeNodeObject(nodeId,
                                        tmpDbLvl3.getTreeLevel3Name()));
                                nodeName = treeHazardSbsList.get(2).getNodeName();
                            }
                            break;
                        case 4:
                            DbtreeLevel4 tmpDbLvl4 = dbtreeLevel1Facade.findByIndex(Integer.parseInt(parts[0]),
                                    Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
                            if (tmpDbLvl4.getTreeLevel4Name() != null) {
                                treeHazardSbsList.add(new treeNodeObject(nodeId,
                                        tmpDbLvl4.getTreeLevel4Name()));
                                nodeName = treeHazardSbsList.get(3).getNodeName();
                            }
                            break;
                        case 5:
                            DbtreeLevel5 tmpDbLvl5 = dbtreeLevel1Facade.findByIndex(Integer.parseInt(parts[0]),
                                    Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]),
                                    Integer.parseInt(parts[4]));
                            if (tmpDbLvl5.getTreeLevel5Name() != null) {
                                treeHazardSbsList.add(new treeNodeObject(nodeId,
                                        tmpDbLvl5.getTreeLevel5Name()));
                                nodeName = treeHazardSbsList.get(4).getNodeName();
                            }
                            break;
                        case 6:
                            DbtreeLevel6 tmpDbLvl6 = dbtreeLevel1Facade.findByIndex(Integer.parseInt(parts[0]),
                                    Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]),
                                    Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));
                            if (tmpDbLvl6.getTreeLevel6Name() != null) {
                                treeHazardSbsList.add(new treeNodeObject(nodeId,
                                        tmpDbLvl6.getTreeLevel6Name()));
                                nodeName = treeHazardSbsList.get(5).getNodeName();
                            }
                            break;
                    }
                }
            }
            nodeNames.add(nodeName);
        }
        return nodeNames;
    }
    
    public boolean checkRequest() {
        return selectWF.stream().noneMatch(h -> h.getWfCompleteMethod().equals("HazardApprovalWF"));
    }
    
    public String parseSize(int fileSize) {
        // Return a string for readability of the size field in tables
        int order = 0;
        String[] suffix = new String[3];
        suffix[0] = "B";
        suffix[1] = "kB";
        suffix[2] = "MB";
        double formatSize = fileSize;
        while (formatSize / 1000 > 1) {
            formatSize = formatSize / 1000;
            order++;
        }
        DecimalFormat df = new DecimalFormat("#.###");
        return Double.valueOf(df.format(formatSize)).toString() + " " + suffix[order];
    }
    
    public void handleDownload(fileHeaderObject file) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        
        ec.responseReset();
        ec.setResponseContentType(ec.getMimeType(file.getFileName() + "." + file.getFileExtension()));
        ec.setResponseContentLength(file.getFileSize());
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + file.getFileName() + "." + file.getFileExtension() + "\"");
        
        try {
            byte[] fileBlob = dbFilesFacade.findFileFromId(file.getFileId()).get(0).getFileBlob();
            OutputStream os = ec.getResponseOutputStream();
            os.write(fileBlob);
        } catch (IOException e) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", e.getMessage()));
        }
        fc.responseComplete();
    }
}