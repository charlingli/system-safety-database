/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import customObjects.fileHeaderObject;
import customObjects.treeNodeObject;
import ejb.DbFilesFacadeLocal;
import ejb.DbHazardFacadeLocal;
import ejb.DbHazardFilesFacadeLocal;
import ejb.DbtreeLevel1FacadeLocal;
import ejb.DbwfHeaderFacadeLocal;
import ejb.DbwfLineFacadeLocal;
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
@Named(value = "userWF_MB")
@ViewScoped
public class userWF_MB implements Serializable {

    @EJB
    private DbHazardFilesFacadeLocal dbHazardFilesFacade;

    @EJB
    private DbFilesFacadeLocal dbFilesFacade;

    @EJB
    private DbtreeLevel1FacadeLocal dbtreeLevel1Facade;

    @EJB
    private DbHazardFacadeLocal dbHazardFacade;

    @EJB
    private DbwfLineFacadeLocal dbwfLineFacade;

    @EJB
    private DbwfHeaderFacadeLocal dbwfHeaderFacade;
    
    private List<DbwfLine> listOpenWF;
    private List<DbwfHeader> listMyWF;
    private List<DbwfLine> selectWF;
    private List<DbwfHeader> selectWFHeader;
    private DbwfHeader detailWF;
    private List<DbwfLine> detailLine;
    private DbHazard detailHazard;
    private List<DbHazardCause> detailCauses;
    private List<DbHazardConsequence> detailConsequences;
    private List<DbControlHazard> detailControls;
    private DbwfHeader approvalWF;
    private String approvalDecision;
    private String approvalComment;
    private String multipleComment;
    private DbUser activeUser;
    private boolean isAdminUser;
    private List<fileHeaderObject> fileHeaders;

    public userWF_MB() {
    }

    public List<DbwfLine> getListOpenWF() {
        return listOpenWF;
    }

    public void setListOpenWF(List<DbwfLine> listOpenWF) {
        this.listOpenWF = listOpenWF;
    }

    public List<DbwfHeader> getListMyWF() {
        return listMyWF;
    }

    public void setListMyWF(List<DbwfHeader> listMyWF) {
        this.listMyWF = listMyWF;
    }

    public List<DbwfLine> getSelectWF() {
        return selectWF;
    }

    public void setSelectWF(List<DbwfLine> selectWF) {
        this.selectWF = selectWF;
    }

    public List<DbwfHeader> getSelectWFHeader() {
        return selectWFHeader;
    }

    public void setSelectWFHeader(List<DbwfHeader> selectWFHeader) {
        this.selectWFHeader = selectWFHeader;
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

    public boolean isIsAdminUser() {
        return isAdminUser;
    }

    public void setIsAdminUser(boolean isAdminUser) {
        this.isAdminUser = isAdminUser;
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
        setIsAdminUser(false);
        if (activeUser.getRoleId().getRoleId() == 1 | activeUser.getRoleId().getRoleId() == 2) {
            setIsAdminUser(true);
        }
        setListOpenWF(dbwfLineFacade.findOpenByUser(activeUser.getUserId()));
        setListMyWF(dbwfHeaderFacade.findActiveByUser(activeUser.getUserId()));
    }
    
    public void showDetail(DbwfHeader wfHeader) {
        setDetailWF(wfHeader);
        setDetailLine(dbwfLineFacade.findAllOfWF(wfHeader.getWfId()));
    }
    
    public void showHazard(DbwfHeader wfHeader) {
        setDetailHazard(dbHazardFacade.findByName("hazardId", wfHeader.getWfObjectId()).get(0));
        detailCauses = dbHazardFacade.getHazardCause(detailHazard.getHazardId());
        detailConsequences = dbHazardFacade.getHazardConsequence(detailHazard.getHazardId());
        detailControls = dbHazardFacade.getControlHazard(detailHazard.getHazardId());
        fileHeaders = dbHazardFilesFacade.findHeadersForHazard(detailHazard.getHazardId());
    }
    
    public void prepareDecision(DbwfLine wfItem, String decisionId) {
        setApprovalWF(wfItem.getDbwfHeader());
        setApprovalDecision(decisionId);
    }
    
    public void prepareDecisions(String decisionId) {
        if (getSelectWF().size() < 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "No items selected for approval."));
        }
        setApprovalDecision(decisionId);
    }
    
    public void prepareDecisionHeader(DbwfHeader wfItem, String decisionId) {
        setApprovalWF(wfItem);
        setApprovalDecision(decisionId);
    }
    
    public void prepareDecisionsHeader(String decisionId) {
        if (getSelectWFHeader().size() < 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "No items selected for approval."));
        }
        setApprovalDecision(decisionId);
    }
    public void sendDecision() {
        if (getApprovalComment().length() < 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "You must leave a comment justifying your decision."));
        } else {
            DbwfLine tmpLine = dbwfLineFacade.findByIdAndUser(new DbwfLine(new DbwfLinePK(getApprovalWF().getWfId(), "")), activeUser.getUserId());
            tmpLine.setWfApproverDecisionId(new DbwfDecision(getApprovalDecision()));
            tmpLine.setWfApprovalComment(getApprovalComment());
            tmpLine.setWfDateTimeDecision(new Date());

            if (getApprovalDecision().equals("A")) {
                dbwfLineFacade.edit(tmpLine);
                dbwfHeaderFacade.approvalProcess(new DbwfHeader(getApprovalWF().getWfId()), "userApproval");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Approval sent for workflow."));
            } else if (getApprovalDecision().equals("R")) {
                dbwfLineFacade.edit(tmpLine);
                dbwfHeaderFacade.rejectionProcess(new DbwfHeader(getApprovalWF().getWfId()), "userApproval");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Rejection sent for workflow."));
            } else if (getApprovalDecision().equals("I")) {
                dbwfLineFacade.edit(tmpLine);
                dbwfHeaderFacade.reviewProcess(new DbwfHeader(getApprovalWF().getWfId()), "userApproval");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Request for information sent for workflow."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "No logic associated with this decision type."));
            }
            setApprovalWF(null);
            setApprovalDecision(null);
            setApprovalComment(null);
            init();
        }
    }
    
    public void sendDecisions() {
        if (getMultipleComment().length() < 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "You must leave a comment justifying your decision."));
        } else {
            boolean validRequest = true;
            for (DbwfLine wfItem : getSelectWF()) {
                wfItem.setWfApproverDecisionId(new DbwfDecision(getApprovalDecision()));
                wfItem.setWfApprovalComment(getMultipleComment());
                wfItem.setWfDateTimeDecision(new Date());

                if (getApprovalDecision().equals("A")) {
                    dbwfLineFacade.edit(wfItem);
                    dbwfHeaderFacade.approvalProcess(new DbwfHeader(wfItem.getDbwfHeader().getWfId()), "userApproval");
                } else if (getApprovalDecision().equals("R")) {
                    dbwfLineFacade.edit(wfItem);
                    dbwfHeaderFacade.rejectionProcess(new DbwfHeader(wfItem.getDbwfHeader().getWfId()), "userApproval");
                } else if (getApprovalDecision().equals("I")) {
                    if (wfItem.getDbwfHeader().getWfCompleteMethod().equals("HazardApprovalWF")) {
                        dbwfLineFacade.edit(wfItem);
                        dbwfHeaderFacade.reviewProcess(new DbwfHeader(wfItem.getDbwfHeader().getWfId()), "userApproval");
                    } else {
                        validRequest = false;
                    }
                }
            }
            if (validRequest) {
                if (getApprovalDecision().equals("A")) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Approvals sent for workflows."));
                } else if (getApprovalDecision().equals("R")) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Rejections sent for workflows."));
                } else if (getApprovalDecision().equals("I")) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Requests for information sent for workflows."));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "No logic associated with this decision type."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning:", "Workflows for suggestions and deletions cannot request information."));
            }
            setApprovalWF(null);
            setApprovalDecision(null);
            setMultipleComment(null);
            init();
        }
    }
    
    public void sendCancellation() {
        if (getApprovalComment().length() < 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "You must leave a comment justifying your decision."));
        } else {
            dbwfHeaderFacade.cancellationProcess(new DbwfHeader(getApprovalWF().getWfId(), getApprovalComment()));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Cancellation sent for workflow."));
            setApprovalWF(null);
            setApprovalDecision(null);
            setApprovalComment(null);
            init();
        }
    }
    
    public void sendCancellations() {
        if (getMultipleComment().length() < 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "You must leave a comment justifying your decision."));
        } else {
            for (DbwfHeader wfItem : getSelectWFHeader()) {
                dbwfHeaderFacade.cancellationProcess(new DbwfHeader(wfItem.getWfId(), getMultipleComment()));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Cancellation sent for workflows."));
            }
            setApprovalWF(null);
            setApprovalDecision(null);
            setMultipleComment(null);
            init();
        }
    }
    
    public String editHazard(DbwfHeader wfHeader) {
        setDetailHazard(dbHazardFacade.findByName("hazardId", wfHeader.getWfObjectId()).get(0));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("hazardRelObj", getDetailHazard());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("hazardRelWF", wfHeader);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("redirectionSource", "UserWorkflow");
        return "/data/hazards/editHazard";
    }
    
    public int getWFProposedListSize() {
        return listOpenWF.size();
    }
    
    public int getWFAddressListSize() {
        return listMyWF.size();
    }
    
    public String getLatestComment(DbwfHeader wfHeader) {
        if (wfHeader.getWfStatus().equals("I")) {
            DbwfLine wfLine = dbwfLineFacade.findLatestCommentById(wfHeader.getWfId());
            return wfLine.getWfUserIdApprover().getFirstName() + " " + wfLine.getWfUserIdApprover().getLastName() + ": " + wfLine.getWfApprovalComment();
        }
        return "";
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
        return selectWF.stream().noneMatch(h -> h.getDbwfHeader().getWfCompleteMethod().equals("HazardApprovalWF"));
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