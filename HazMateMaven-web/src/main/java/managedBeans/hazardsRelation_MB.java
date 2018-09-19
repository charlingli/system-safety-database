/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import customObjects.searchObject;
import customObjects.validateIdObject;
import ejb.DbCauseFacadeLocal;
import ejb.DbConsequenceFacadeLocal;
import ejb.DbControlFacadeLocal;
import ejb.DbControlHazardFacadeLocal;
import ejb.DbHazardCauseFacadeLocal;
import ejb.DbHazardConsequenceFacadeLocal;
import ejb.DbHazardFacadeLocal;
import ejb.DbUserFacadeLocal;
import ejb.DbcontrolRecommendFacadeLocal;
import ejb.DbwfHeaderFacadeLocal;
import entities.DbCause;
import entities.DbConsequence;
import entities.DbControl;
import entities.DbControlHazard;
import entities.DbControlHazardPK;
import entities.DbHazard;
import entities.DbHazardCause;
import entities.DbHazardCausePK;
import entities.DbHazardConsequence;
import entities.DbHazardConsequencePK;
import entities.DbUser;
import entities.DbcontrolRecommend;
import entities.DbwfHeader;
import entities.DbwfType;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Juan David
 *
 */
@Named(value = "hazardsRelation_MB")
@ViewScoped
public class hazardsRelation_MB implements Serializable {

    @EJB
    private DbwfHeaderFacadeLocal dbwfHeaderFacade;
    @EJB
    private DbUserFacadeLocal dbUserFacade;
    @EJB
    private DbCauseFacadeLocal dbCauseFacade;
    @EJB
    private DbHazardCauseFacadeLocal dbHazardCauseFacade;
    @EJB
    private DbConsequenceFacadeLocal dbConsequenceFacade;
    @EJB
    private DbHazardConsequenceFacadeLocal dbHazardConsequenceFacade;
    @EJB
    private DbControlFacadeLocal dbControlFacade;
    @EJB
    private DbControlHazardFacadeLocal dbControlHazardFacade;
    @EJB
    private DbHazardFacadeLocal dbHazardFacade;
    @EJB
    private DbcontrolRecommendFacadeLocal dbcontrolRecommendFacade;

    private List<DbHazard> listHazards;
    private List<DbHazardCause> listHazardCauses;
    private List<DbCause> listUnrelatedCauses;
    private List<DbCause> listSelectedCauses;
    private List<DbHazardConsequence> listHazardConsqs;
    private List<DbConsequence> listUnrelatedConsqs;
    private List<DbConsequence> listSelectedConsqs;
    private List<DbControlHazard> listHazardControls;
    private List<DbControl> listUnrelatedControls;
    private List<DbControl> listSelectedControls;
    private List<DbcontrolRecommend> listControlRecommends;
    private DbHazard hazardObject = new DbHazard();
    private DbControlHazard controlHazardObject;
    private boolean searchBox;
    private boolean dataTable;
    private boolean cancelBtn;
    private boolean infoBox;
    private boolean addCause;
    private boolean addConq;
    private boolean addControl;
    private boolean addControlJustify;
    private boolean txtControlIdHazard;
    private String hazardId;
    private String hazardDescríption;
    private String hazardComment;
    private String hazardWorkshop;
    private String controlRecommendId;
    private String controlType;
    private String controlJustify;
    private String controlStatus;
    private String controlSaveAction;
    private String redirectionSource;

    public hazardsRelation_MB() {
    }

    public List<DbHazard> getListHazards() {
        return listHazards;
    }

    public void setListHazards(List<DbHazard> listHazards) {
        this.listHazards = listHazards;
    }

    public List<DbHazardCause> getListHazardCauses() {
        return listHazardCauses;
    }

    public void setListHazardCauses(List<DbHazardCause> listHazardCauses) {
        this.listHazardCauses = listHazardCauses;
    }

    public List<DbCause> getListUnrelatedCauses() {
        return listUnrelatedCauses;
    }

    public void setListUnrelatedCauses(List<DbCause> listUnrelatedCauses) {
        this.listUnrelatedCauses = listUnrelatedCauses;
    }

    public List<DbCause> getListSelectedCauses() {
        return listSelectedCauses;
    }

    public void setListSelectedCauses(List<DbCause> listSelectedCauses) {
        this.listSelectedCauses = listSelectedCauses;
    }

    public List<DbHazardConsequence> getListHazardConsqs() {
        return listHazardConsqs;
    }

    public void setListHazardConsqs(List<DbHazardConsequence> listHazardConsqs) {
        this.listHazardConsqs = listHazardConsqs;
    }

    public List<DbConsequence> getListUnrelatedConsqs() {
        return listUnrelatedConsqs;
    }

    public void setListUnrelatedConsqs(List<DbConsequence> listUnrelatedConsqs) {
        this.listUnrelatedConsqs = listUnrelatedConsqs;
    }

    public List<DbConsequence> getListSelectedConsqs() {
        return listSelectedConsqs;
    }

    public List<DbControlHazard> getListHazardControls() {
        return listHazardControls;
    }

    public void setListHazardControls(List<DbControlHazard> listHazardControls) {
        this.listHazardControls = listHazardControls;
    }

    public void setListSelectedConsqs(List<DbConsequence> listSelectedConsqs) {
        this.listSelectedConsqs = listSelectedConsqs;
    }

    public List<DbControl> getListUnrelatedControls() {
        return listUnrelatedControls;
    }

    public void setListUnrelatedControls(List<DbControl> listUnrelatedControls) {
        this.listUnrelatedControls = listUnrelatedControls;
    }

    public List<DbControl> getListSelectedControls() {
        return listSelectedControls;
    }

    public void setListSelectedControls(List<DbControl> listSelectedControls) {
        this.listSelectedControls = listSelectedControls;
    }

    public DbHazard getHazardObject() {
        return hazardObject;
    }

    public void setHazardObject(DbHazard hazardObject) {
        this.hazardObject = hazardObject;
    }

    public DbControlHazard getControlHazardObject() {
        return controlHazardObject;
    }

    public void setControlHazardObject(DbControlHazard controlHazardObject) {
        this.controlHazardObject = controlHazardObject;
    }

    public List<DbcontrolRecommend> getListControlRecommends() {
        return listControlRecommends;
    }

    public void setListControlRecommends(List<DbcontrolRecommend> listControlRecommends) {
        this.listControlRecommends = listControlRecommends;
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

    public boolean isAddCause() {
        return addCause;
    }

    public void setAddCause(boolean addCause) {
        this.addCause = addCause;
    }

    public boolean isAddConq() {
        return addConq;
    }

    public void setAddConq(boolean addConq) {
        this.addConq = addConq;
    }

    public boolean isAddControl() {
        return addControl;
    }

    public void setAddControl(boolean addControl) {
        this.addControl = addControl;
    }

    public boolean isAddControlJustify() {
        return addControlJustify;
    }

    public void setAddControlJustify(boolean addControlJustify) {
        this.addControlJustify = addControlJustify;
    }

    public boolean isTxtControlIdHazard() {
        return txtControlIdHazard;
    }

    public void setTxtControlIdHazard(boolean txtControlIdHazard) {
        this.txtControlIdHazard = txtControlIdHazard;
    }

    public String getHazardId() {
        return hazardId;
    }

    public void setHazardId(String hazardId) {
        this.hazardId = hazardId;
    }

    public String getHazardDescríption() {
        return hazardDescríption;
    }

    public void setHazardDescríption(String hazardDescríption) {
        this.hazardDescríption = hazardDescríption;
    }

    public String getHazardComment() {
        return hazardComment;
    }

    public void setHazardComment(String hazardComment) {
        this.hazardComment = hazardComment;
    }

    public String getHazardWorkshop() {
        return hazardWorkshop;
    }

    public void setHazardWorkshop(String hazardWorkshop) {
        this.hazardWorkshop = hazardWorkshop;
    }

    public String getControlRecommendId() {
        return controlRecommendId;
    }

    public void setControlRecommendId(String controlRecommendId) {
        this.controlRecommendId = controlRecommendId;
    }

    public String getControlJustify() {
        return controlJustify;
    }

    public void setControlJustify(String controlJustify) {
        this.controlJustify = controlJustify;
    }

    public String getControlStatus() {
        return controlStatus;
    }

    public void setControlStatus(String controlStatus) {
        this.controlStatus = controlStatus;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }
    
    @PostConstruct
    public void init() {
        searchBox = true;
        dataTable = false;
        cancelBtn = false;
        infoBox = false;
        addCause = false;
        addConq = false;
        addControl = false;
        addControlJustify = false;
        controlHazardObject = new DbControlHazard();
        //Validate if this component has been called due to another page redirection.
        redirectedPage();
    }

    public void searchHazards() {
        listHazards = dbHazardFacade.findHazardsByFieldsOnly(createSearchList());
        dataTable = true;
        cancelBtn = true;
    }

    public void includeRelations(DbHazard hazardIn) {
        this.hazardObject = hazardIn;
        listHazardCauses = dbHazardCauseFacade.findByHazardId(hazardObject.getHazardId());
        listHazardConsqs = dbHazardConsequenceFacade.findByHazardId(hazardObject.getHazardId());
        listHazardControls = dbControlHazardFacade.findByHazardId(hazardObject.getHazardId());
        searchBox = false;
        dataTable = false;
        infoBox = true;
    }

    public void addCauseVisible() {
        listUnrelatedCauses = dbCauseFacade.findUnrelatedByHazardId(hazardObject.getHazardId());
        addCause = true;
    }

    public void addCauseCancel() {
        addCause = false;
    }

    public List<DbCause> showUnrelCauses(String query) {
        List<DbCause> filteredCauses = new ArrayList<>();

        for (int i = 0; i < listUnrelatedCauses.size(); i++) {
            DbCause findCause = listUnrelatedCauses.get(i);
            if (findCause.getCauseDescription().toLowerCase().contains(query.toLowerCase())) {
                filteredCauses.add(findCause);
            }
        }
        return filteredCauses;
    }

    public void addHazardCauses() {
        listSelectedCauses.sort(Comparator.comparingInt(DbCause::getCauseId));
        for (int x = 0; x < listSelectedCauses.size(); x++) {
            if (x != 0) {
                if (listSelectedCauses.get(x).equals(listSelectedCauses.get(x - 1))) {
                    listSelectedCauses.remove(x);
                    x = -1;
                }
            }
        }

        for (DbCause tmpCause : listSelectedCauses) {
            DbHazardCause tmpHazardCause = new DbHazardCause();
            DbHazardCausePK tmpHazardCausePK
                    = new DbHazardCausePK(hazardObject.getHazardId(), tmpCause.getCauseId());
            tmpHazardCause.setDbHazardCausePK(tmpHazardCausePK);
            tmpHazardCause.setDbCause(tmpCause);
            tmpHazardCause.setDbHazard(hazardObject);
            tmpHazardCause.setDbHazardCauseDummyvar(null);
            dbHazardCauseFacade.create(tmpHazardCause);
        }

        listHazardCauses = dbHazardCauseFacade.findByHazardId(hazardObject.getHazardId());
        listSelectedCauses = new ArrayList<>();
        addCause = false;
    }

    public void deleteHazardCauses(DbHazardCause hazardCauseIn) {
        int rowsDeleted = dbHazardCauseFacade.customRemove(hazardCauseIn);
        listHazardCauses = dbHazardCauseFacade.findByHazardId(hazardObject.getHazardId());
        listUnrelatedCauses = dbCauseFacade.findUnrelatedByHazardId(hazardObject.getHazardId());
    }

    public void addConquenceVisible() {
        listUnrelatedConsqs = dbConsequenceFacade.findUnrelatedByHazardId(hazardObject.getHazardId());
        addConq = true;
    }

    public void addConquenceCancel() {
        addConq = false;
    }

    public List<DbConsequence> showUnrelConsqs(String query) {
        List<DbConsequence> filteredConsqs = new ArrayList<>();

        for (int i = 0; i < listUnrelatedConsqs.size(); i++) {
            DbConsequence findConsqs = listUnrelatedConsqs.get(i);
            if (findConsqs.getConsequenceDescription().toLowerCase().contains(query.toLowerCase())) {
                filteredConsqs.add(findConsqs);
            }
        }
        return filteredConsqs;
    }

    public void addHazardConsequences() {
        listSelectedConsqs.sort(Comparator.comparingInt(DbConsequence::getConsequenceId));
        DbConsequence antConsq = new DbConsequence();
        for (int x = 0; x < listSelectedConsqs.size(); x++) {
            if (x != 0) {
                if (listSelectedConsqs.get(x).equals(listSelectedConsqs.get(x - 1))) {
                    listSelectedConsqs.remove(x);
                    x = -1;
                }
            }
        }

        for (DbConsequence tmpConsqs : listSelectedConsqs) {
            DbHazardConsequence tmpHazardConsqs = new DbHazardConsequence();
            DbHazardConsequencePK tmpHazardConsqsPK
                    = new DbHazardConsequencePK(hazardObject.getHazardId(), tmpConsqs.getConsequenceId());
            tmpHazardConsqs.setDbHazardConsequencePK(tmpHazardConsqsPK);
            tmpHazardConsqs.setDbConsequence(tmpConsqs);
            tmpHazardConsqs.setDbHazard(hazardObject);
            tmpHazardConsqs.setDbHazardConsequenceDummyvar(null);
            dbHazardConsequenceFacade.create(tmpHazardConsqs);
        }

        listHazardConsqs = dbHazardConsequenceFacade.findByHazardId(hazardObject.getHazardId());
        listSelectedConsqs = new ArrayList<>();
        addConq = false;
    }

    public void deleteHazardConsqs(DbHazardConsequence hazardConsqsIn) {
        int rowsDeteled = dbHazardConsequenceFacade.customRemove(hazardConsqsIn);
        listHazardConsqs = dbHazardConsequenceFacade.findByHazardId(hazardObject.getHazardId());
        listUnrelatedConsqs = dbConsequenceFacade.findUnrelatedByHazardId(hazardObject.getHazardId());
    }

    public void addControlVisible() {
        listUnrelatedControls = dbControlFacade.findUnrelatedByHazardId(hazardObject.getHazardId());
        listControlRecommends = dbcontrolRecommendFacade.findAll();
        addControl = true;
        txtControlIdHazard = false;
        controlSaveAction = "Save";
    }

    public void addControlCancel() {
        addControl = false;
    }

    public void editControlVisible(DbControlHazard dbControlHazardIn) {
        listControlRecommends = dbcontrolRecommendFacade.findAll();
        addControl = true;
        txtControlIdHazard = true;
        controlSaveAction = "Edit";
        setControlHazardObject(dbControlHazardIn);
        setControlRecommendId(controlHazardObject.getControlRecommendId().getControlRecommendId().toString());
        setControlType(controlHazardObject.getControlType());
        setControlJustify(controlHazardObject.getControlJustify());
        if (controlHazardObject.getControlJustify() != null) {
            addControlJustify = true;
        }
    }

    public List<DbControl> showUnrelControls(String query) {
        List<DbControl> filteredControls = new ArrayList<>();

        for (int i = 0; i < listUnrelatedControls.size(); i++) {
            DbControl findControl = listUnrelatedControls.get(i);
            if (findControl.getControlDescription().toLowerCase().contains(query.toLowerCase())) {
                filteredControls.add(findControl);
            }
        }
        return filteredControls;
    }

    public void loadJustify() {
        DbcontrolRecommend tmpCtlRecommend = dbcontrolRecommendFacade.find(Integer.parseInt(controlRecommendId));
        if (tmpCtlRecommend.getControlJustifyRequired().equals("Y")) {
            addControlJustify = true;
        } else if (tmpCtlRecommend.getControlJustifyRequired().equals("N")) {
            addControlJustify = false;
        }
    }

    public void addHazardControls() {
        DbcontrolRecommend tmpCtlRecommend = dbcontrolRecommendFacade.find(Integer.parseInt(controlRecommendId));
        controlHazardObject.setDbHazard(hazardObject);
        controlHazardObject.setControlType(controlType);
        controlHazardObject.setControlExistingOrProposed(controlStatus);
        controlHazardObject.setDbControlHazardPK(
                new DbControlHazardPK(hazardObject.getHazardId(), controlHazardObject.getDbControl().getControlId()));
        controlHazardObject.setControlRecommendId(new DbcontrolRecommend(Integer.parseInt(controlRecommendId)));
        if ("".equals(controlJustify) && tmpCtlRecommend.getControlJustifyRequired().equals("N")) {
            controlHazardObject.setControlJustify(null);
        } else if (!"".equals(controlJustify) && tmpCtlRecommend.getControlJustifyRequired().equals("Y")) {
            controlHazardObject.setControlJustify(controlJustify);
        } else if (!"".equals(controlJustify) && tmpCtlRecommend.getControlJustifyRequired().equals("N")) {
            controlHazardObject.setControlJustify(null);
        } else if ("".equals(controlJustify) && tmpCtlRecommend.getControlJustifyRequired().equals("Y")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Justification: ", "The selected recommendation requires a justification."));
        }

        if (controlSaveAction.equals("Save")) {
            dbControlHazardFacade.create(controlHazardObject);
        } else if (controlSaveAction.equals("Edit")) {
            dbControlHazardFacade.edit(controlHazardObject);
        }

        listHazardControls = dbControlHazardFacade.findByHazardId(hazardObject.getHazardId());
        controlHazardObject = new DbControlHazard();
        controlJustify = "";
        controlRecommendId = "";
        addControl = false;
        addControlJustify = false;
    }

    public void deleteHazardControls(DbControlHazard controlHazardIn) {
        int rowsDeleted = dbControlHazardFacade.customRemove(controlHazardIn);
        listHazardControls = dbControlHazardFacade.findByHazardId(hazardObject.getHazardId());
        listUnrelatedControls = dbControlFacade.findUnrelatedByHazardId(hazardObject.getHazardId());
    }

    public void sendToApproval() {
        hazardId = "";
        hazardDescríption = "";
        hazardComment = "";
        hazardWorkshop = "";
        searchBox = true;
        dataTable = false;
        infoBox = false;
        cancelBtn = false;
        addCause = false;
        addConq = false;
        addControl = false;
        addControlJustify = false;
        hazardObject = new DbHazard();
        controlHazardObject = new DbControlHazard();
        listSelectedCauses = new ArrayList<>();
        listSelectedConsqs = new ArrayList<>();
        listSelectedControls = new ArrayList<>();
        triggerWorkFlow();
    }

    private List<searchObject> createSearchList() {
        List<searchObject> searchList = new ArrayList<>();
        searchObject tmpSearchObj = new searchObject();
        if (!"".equals(hazardId)) {
            tmpSearchObj.setEntity1Name("DbHazard");
            tmpSearchObj.setFieldName("hazardId");
            tmpSearchObj.setFieldType("string");
            tmpSearchObj.setRelationType("like");
            tmpSearchObj.setUserInput(hazardId);
            searchList.add(tmpSearchObj);
            tmpSearchObj = new searchObject();
        }

        if (!"".equals(hazardDescríption)) {
            tmpSearchObj.setEntity1Name("DbHazard");
            tmpSearchObj.setFieldName("hazardDescription");
            tmpSearchObj.setFieldType("string");
            tmpSearchObj.setRelationType("like");
            tmpSearchObj.setUserInput(hazardDescríption);
            searchList.add(tmpSearchObj);
            tmpSearchObj = new searchObject();
        }

        if (!"".equals(hazardComment)) {
            tmpSearchObj.setEntity1Name("DbHazard");
            tmpSearchObj.setFieldName("hazardComment");
            tmpSearchObj.setFieldType("string");
            tmpSearchObj.setRelationType("like");
            tmpSearchObj.setUserInput(hazardComment);
            searchList.add(tmpSearchObj);
            tmpSearchObj = new searchObject();
        }

        if (!"".equals(hazardWorkshop)) {
            tmpSearchObj.setEntity1Name("DbHazard");
            tmpSearchObj.setFieldName("hazardWorkshop");
            tmpSearchObj.setFieldType("string");
            tmpSearchObj.setRelationType("like");
            tmpSearchObj.setUserInput(hazardWorkshop);
            searchList.add(tmpSearchObj);
        }
        return searchList;
    }

    //This method controls the behaviour when the hazard relations has been called due to a redirection page from add or edit hazard.
    private void redirectedPage() {
        DbHazard initialHazard = (DbHazard) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("hazardRelObj");
        if (initialHazard != null) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("hazardRelObj");
            setHazardId(initialHazard.getHazardId());
            searchHazards();
            includeRelations(initialHazard);
            redirectionSource = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("redirectionSource");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("redirectionSource");
        }
    }

    //Triggers the work flow whenever a new hazard has been added, edited or simply the hazard relations have changed. 
    private void triggerWorkFlow() {
        if (redirectionSource.equals("AddHazard")) {
            List<DbUser> listApprovers = dbUserFacade.getUsersByRole("App. Manager");
            if (!listApprovers.isEmpty()) {
                DbwfHeader wfObj = new DbwfHeader();
                wfObj.setWfTypeId(new DbwfType("W3"));
                wfObj.setWfStatus("O");
                wfObj.setWfAddedDateTime(new Date());
                wfObj.setWfUserIdAdd((DbUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("activeUser"));
                wfObj.setWfObjectId(hazardObject.getHazardId());
                wfObj.setWfObjectName("Hazard");
                wfObj.setWfComment1("This flow was created to approve a new hazard in the ssd.");
                wfObj.setWfCompleteMethod("HazardApprovalWF");
                validateIdObject result = dbwfHeaderFacade.newWorkFlow(listApprovers, wfObj, "WKF-HRD");
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("./../../admin/masterMenu.xhtml");
                    
                } catch (IOException ex) {
                    Logger.getLogger(hazardsRelation_MB.class.getName()).log(Level.SEVERE, null, ex);
                }
                //System.out.println(result.getAnswerString());
            } else {
                System.out.println("managedBeans.hazardsRelation_MB.triggerWorkFlow() -> There are not users with the role 'App. Manager'");
            }
        }
    }
}
