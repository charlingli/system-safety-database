/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.DbHazardFacadeLocal;
import ejb.DbwfHeaderFacadeLocal;
import ejb.DbwfLineFacadeLocal;
import entities.DbControlHazard;
import entities.DbHazard;
import entities.DbHazardCause;
import entities.DbHazardConsequence;
import entities.DbwfHeader;
import entities.DbUser;
import entities.DbwfDecision;
import entities.DbwfLine;
import entities.DbwfLinePK;
import java.io.Serializable;
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
@Named(value = "userWF_MB")
@ViewScoped
public class userWF_MB implements Serializable {

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
    private DbUser activeUser;
    private boolean isAdminUser;

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

    public boolean isIsAdminUser() {
        return isAdminUser;
    }

    public void setIsAdminUser(boolean isAdminUser) {
        this.isAdminUser = isAdminUser;
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
    }
    
    public void prepareDecision(DbwfLine wfItem, String decisionId) {
        setApprovalWF(wfItem.getDbwfHeader());
        setApprovalDecision(decisionId);
    }
    
    public void prepareDecisionHeader(DbwfHeader wfItem, String decisionId) {
        setApprovalWF(wfItem);
        setApprovalDecision(decisionId);
    }
    
    public void sendDecision(String approvalComment) {
        if (approvalComment.length() < 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "You must leave a comment justifying your decision."));
        } else {
            DbwfLine tmpLine = dbwfLineFacade.findByIdAndUser(new DbwfLine(new DbwfLinePK(getApprovalWF().getWfId(), "")), activeUser.getUserId());
            tmpLine.setWfApproverDecisionId(new DbwfDecision(getApprovalDecision()));
            tmpLine.setWfApprovalComment(approvalComment);
            tmpLine.setWfDateTimeDecision(new Date());

            if (getApprovalDecision().equals("A")) {
                dbwfHeaderFacade.approvalProcess(new DbwfHeader(getApprovalWF().getWfId()), "userApproval");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Approval sent for workflows."));
            } else if (getApprovalDecision().equals("R")) {
                dbwfHeaderFacade.rejectionProcess(new DbwfHeader(getApprovalWF().getWfId()), "userApproval");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Rejection sent for workflows."));
            } else if (getApprovalDecision().equals("I")) {
                dbwfHeaderFacade.reviewProcess(new DbwfHeader(getApprovalWF().getWfId()), "userApproval");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Request for information sent for workflows."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "No logic associated with this decision type."));
            }
            setApprovalWF(null);
            init();
        }
    }
    
    public void sendRejection(String approvalComment) {
        if (approvalComment.length() < 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "You must leave a comment justifying your decision."));
        } else {
            dbwfHeaderFacade.rejectionProcess(new DbwfHeader(getApprovalWF().getWfId()), "userApproval");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Rejection sent for workflows."));
            setApprovalWF(null);
            init();
        }
    }
    
    public void prepareDecisions(List<DbwfHeader> wfItems, String decisionId) {
        if (wfItems.size() < 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "No items selected for approval."));
        }
        setApprovalDecision(decisionId);
    }
    
    public void sendDecisions(String approvalComment) {
        if (approvalComment.length() < 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "You must leave a comment justifying your decision."));
        } else {
            boolean validRequest = true;
            for (DbwfLine wfItem : selectWF) {
                wfItem.setWfApproverDecisionId(new DbwfDecision(getApprovalDecision()));
                wfItem.setWfApprovalComment(approvalComment);
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
            init();
        }
    }
    
    public void sendRejections(String approvalComment) {
        if (approvalComment.length() < 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "You must leave a comment justifying your decision."));
        } else {
            for (DbwfHeader wfItem : selectWFHeader) {
                dbwfHeaderFacade.rejectionProcess(new DbwfHeader(wfItem.getWfId()), "userApproval");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Rejection sent for workflows."));
            }
            setApprovalWF(null);
            init();
        }
    }
    
    public String editHazard(DbwfHeader wfHeader) {
        setDetailHazard(dbHazardFacade.findByName("hazardId", wfHeader.getWfObjectId()).get(0));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("hazardRelObj", getDetailHazard());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("hazardRelWF", wfHeader);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("redirectionSource", "EditHazard");
        return "/data/hazards/editHazard";
    }
    
    public int getWFProposedListSize() {
        return listOpenWF.size();
    }
    
    public int getWFAddressListSize() {
        return listMyWF.size();
    }
}