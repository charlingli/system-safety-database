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
    private List<DbwfLine> listMyWF;
    private List<DbwfLine> selectWF;
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

    public List<DbwfLine> getListMyWF() {
        return listMyWF;
    }

    public void setListMyWF(List<DbwfLine> listMyWF) {
        this.listMyWF = listMyWF;
    }

    public List<DbwfLine> getSelectWF() {
        return selectWF;
    }

    public void setSelectWF(List<DbwfLine> selectWF) {
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
        setListMyWF(dbwfLineFacade.findActiveByUser(activeUser.getUserId()));
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
    
    public void sendDecision(String approvalComment) {
        DbwfLine tmpLine = dbwfLineFacade.findByIdAndUser(new DbwfLine(new DbwfLinePK(getApprovalWF().getWfId(), "")), activeUser.getUserId());
        tmpLine.setWfApproverDecisionId(new DbwfDecision(getApprovalDecision()));
        tmpLine.setWfApprovalComment(approvalComment);
        tmpLine.setWfDateTimeDecision(new Date());
        dbwfLineFacade.edit(tmpLine);
        
        if (getApprovalDecision().equals("A")) {
            System.out.println("Sending approval of " + getApprovalWF().getWfId() + " with message " + approvalComment);
            dbwfHeaderFacade.approvalProcess(new DbwfHeader(getApprovalWF().getWfId()), "userApproval");
        } else if (getApprovalDecision().equals("R")) {
            System.out.println("Sending rejection of " + getApprovalWF().getWfId() + " with message " + approvalComment);
            dbwfHeaderFacade.rejectionProcess(new DbwfHeader(getApprovalWF().getWfId()), "userApproval");
        } else if (getApprovalDecision().equals("I")) {
            System.out.println("Sending request of " + getApprovalWF().getWfId() + " with message " + approvalComment);
            dbwfHeaderFacade.reviewProcess(new DbwfHeader(getApprovalWF().getWfId()), "userApproval");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "No logic associated with this decision type."));
        }
        setApprovalWF(null);
        init();
    }
    
    public void prepareDecisions(List<DbwfHeader> wfItems, String decisionId) {
        if (wfItems.size() < 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "No items selected for approval."));
        }
        setApprovalDecision(decisionId);
    }
    
    public void sendDecisions(String approvalComment) {
        for (DbwfLine wfItem : selectWF) {
            wfItem.setWfApproverDecisionId(new DbwfDecision(getApprovalDecision()));
            wfItem.setWfApprovalComment(approvalComment);
            wfItem.setWfDateTimeDecision(new Date());
            dbwfLineFacade.edit(wfItem);

            if (getApprovalDecision().equals("A")) {
                System.out.println("Sending approval of " + wfItem.getDbwfHeader().getWfId() + " with message " + approvalComment);
                dbwfHeaderFacade.approvalProcess(new DbwfHeader(wfItem.getDbwfHeader().getWfId()), "userApproval");
            } else if (getApprovalDecision().equals("R")) {
                System.out.println("Sending rejection of " + wfItem.getDbwfHeader().getWfId() + " with message " + approvalComment);
                dbwfHeaderFacade.rejectionProcess(new DbwfHeader(wfItem.getDbwfHeader().getWfId()), "userApproval");
            } else if (getApprovalDecision().equals("I")) {
                System.out.println("Sending request of " + wfItem.getDbwfHeader().getWfId() + " with message " + approvalComment);
                dbwfHeaderFacade.reviewProcess(new DbwfHeader(wfItem.getDbwfHeader().getWfId()), "userApproval");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "No logic associated with this decision type."));
            }
        }
        setApprovalWF(null);
        init();
    }
    
    public String editHazard(DbwfHeader wfHeader) {
        setDetailHazard(dbHazardFacade.findByName("hazardId", wfHeader.getWfObjectId()).get(0));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("hazardRelObj", getDetailHazard());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("hazardRelWF", wfHeader);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("redirectionSource", "EditHazard");
        return "/data/hazards/editHazard";
    }
}