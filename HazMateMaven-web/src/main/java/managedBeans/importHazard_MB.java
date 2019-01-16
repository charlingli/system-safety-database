/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import customObjects.treeNodeObject;
import customObjects.validateIdObject;
import ejb.DbCauseFacadeLocal;
import ejb.DbConsequenceFacadeLocal;
import ejb.DbControlFacadeLocal;
import ejb.DbControlHazardFacadeLocal;
import ejb.DbHazardCauseFacadeLocal;
import ejb.DbHazardConsequenceFacadeLocal;
import ejb.DbHazardFacadeLocal;
import ejb.DbLocationFacadeLocal;
import ejb.DbOwnersFacadeLocal;
import ejb.DbProjectFacadeLocal;
import ejb.DbUserFacadeLocal;
import ejb.DbcontrolHierarchyFacadeLocal;
import ejb.DbcontrolRecommendFacadeLocal;
import ejb.DbglobalIdFacadeLocal;
import ejb.DbhazardActivityFacadeLocal;
import ejb.DbhazardContextFacadeLocal;
import ejb.DbhazardStatusFacadeLocal;
import ejb.DbhazardTypeFacadeLocal;
import ejb.DbimportErrorCodeFacadeLocal;
import ejb.DbimportHeaderFacadeLocal;
import ejb.DbimportLineErrorFacadeLocal;
import ejb.DbimportLineFacadeLocal;
import ejb.DbriskClassFacadeLocal;
import ejb.DbriskFrequencyFacadeLocal;
import ejb.DbriskSeverityFacadeLocal;
import ejb.DbtreeLevel1FacadeLocal;
import ejb.DbtreeLevel2FacadeLocal;
import ejb.DbtreeLevel3FacadeLocal;
import ejb.DbtreeLevel4FacadeLocal;
import ejb.DbtreeLevel5FacadeLocal;
import ejb.DbtreeLevel6FacadeLocal;
import ejb.DbwfHeaderFacadeLocal;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;

import entities.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Charling Li
 */
@Named(value = "importHazard_MB")
@ViewScoped
public class importHazard_MB implements Serializable {

    @EJB
    private DbimportLineErrorFacadeLocal dbimportLineErrorFacade;

    @EJB
    private DbimportErrorCodeFacadeLocal dbimportErrorCodeFacade;

    @EJB
    private DbwfHeaderFacadeLocal dbwfHeaderFacade;

    @EJB
    private DbUserFacadeLocal dbUserFacade;

    @EJB
    private DbControlHazardFacadeLocal dbControlHazardFacade;

    @EJB
    private DbHazardConsequenceFacadeLocal dbHazardConsequenceFacade;

    @EJB
    private DbHazardCauseFacadeLocal dbHazardCauseFacade;

    @EJB
    private DbglobalIdFacadeLocal dbglobalIdFacade;

    @EJB
    private DbtreeLevel1FacadeLocal dbtreeLevel1Facade;

    @EJB
    private DbtreeLevel2FacadeLocal dbtreeLevel2Facade;

    @EJB
    private DbtreeLevel3FacadeLocal dbtreeLevel3Facade;

    @EJB
    private DbtreeLevel4FacadeLocal dbtreeLevel4Facade;

    @EJB
    private DbtreeLevel5FacadeLocal dbtreeLevel5Facade;

    @EJB
    private DbtreeLevel6FacadeLocal dbtreeLevel6Facade;

    @EJB
    private DbcontrolRecommendFacadeLocal dbcontrolRecommendFacade;

    @EJB
    private DbcontrolHierarchyFacadeLocal dbcontrolHierarchyFacade;

    @EJB
    private DbControlFacadeLocal dbControlFacade;

    @EJB
    private DbConsequenceFacadeLocal dbConsequenceFacade;

    @EJB
    private DbCauseFacadeLocal dbCauseFacade;
    
    @EJB
    private DbimportLineFacadeLocal dbimportLineFacade;

    @EJB
    private DbimportHeaderFacadeLocal dbimportHeaderFacade;
    
    @EJB
    private DbriskSeverityFacadeLocal dbriskSeverityFacade;

    @EJB
    private DbriskFrequencyFacadeLocal dbriskFrequencyFacade;

    @EJB
    private DbriskClassFacadeLocal dbriskClassFacade;

    @EJB
    private DbHazardFacadeLocal dbHazardFacade;

    @EJB
    private DbOwnersFacadeLocal dbOwnersFacade;

    @EJB
    private DbhazardStatusFacadeLocal dbhazardStatusFacade;

    @EJB
    private DbhazardTypeFacadeLocal dbhazardTypeFacade;

    @EJB
    private DbhazardContextFacadeLocal dbhazardContextFacade;

    @EJB
    private DbhazardActivityFacadeLocal dbhazardActivityFacade;

    @EJB
    private DbProjectFacadeLocal dbProjectFacade;

    @EJB
    private DbLocationFacadeLocal dbLocationFacade;
    
    private DbUser activeUser;
    
    private List<importWrapperObject> listLoadedLines;
    private List<importWrapperObject> listCheckedLines;
    
    private DbimportLine selectedLine;
    
    private TreeNode[] currentTree;
    private TreeNode root;
    
    private List<DbLocation> listHL;
    private List<DbProject> listHP;
    private List<DbhazardActivity> listHA;
    private List<DbhazardContext> listHC;
    private List<DbhazardType> listHT;
    private List<DbhazardStatus> listHS;
    private List<DbOwners> listHO;
    private List<DbriskClass> listRC;
    private List<DbriskFrequency> listRF;
    private List<DbriskSeverity> listRS;
    private List<DbcontrolHierarchy> listCH;
    private List<DbcontrolRecommend> listCR;
    
    public importHazard_MB() {
    
    }

    public List<importWrapperObject> getListLoadedLines() {
        return listLoadedLines;
    }

    public void setListLoadedLines(List<importWrapperObject> listLoadedLines) {
        this.listLoadedLines = listLoadedLines;
    }

    public List<importWrapperObject> getListCheckedLines() {
        return listCheckedLines;
    }

    public void setListCheckedLines(List<importWrapperObject> listCheckedLines) {
        this.listCheckedLines = listCheckedLines;
    }

    public DbimportLine getSelectedLine() {
        return selectedLine;
    }

    public void setSelectedLine(DbimportLine selectedLine) {
        this.selectedLine = selectedLine;
    }

    public List<DbLocation> getListHL() {
        return listHL;
    }

    public void setListHL(List<DbLocation> listHL) {
        this.listHL = listHL;
    }

    public List<DbProject> getListHP() {
        return listHP;
    }

    public void setListHP(List<DbProject> listHP) {
        this.listHP = listHP;
    }

    public List<DbhazardActivity> getListHA() {
        return listHA;
    }

    public void setListHA(List<DbhazardActivity> listHA) {
        this.listHA = listHA;
    }

    public List<DbhazardContext> getListHC() {
        return listHC;
    }

    public void setListHC(List<DbhazardContext> listHC) {
        this.listHC = listHC;
    }

    public List<DbhazardType> getListHT() {
        return listHT;
    }

    public void setListHT(List<DbhazardType> listHT) {
        this.listHT = listHT;
    }

    public List<DbhazardStatus> getListHS() {
        return listHS;
    }

    public void setListHS(List<DbhazardStatus> listHS) {
        this.listHS = listHS;
    }

    public List<DbOwners> getListHO() {
        return listHO;
    }

    public void setListHO(List<DbOwners> listHO) {
        this.listHO = listHO;
    }

    public List<DbriskClass> getListRC() {
        return listRC;
    }

    public void setListRC(List<DbriskClass> listRC) {
        this.listRC = listRC;
    }

    public List<DbriskFrequency> getListRF() {
        return listRF;
    }

    public void setListRF(List<DbriskFrequency> listRF) {
        this.listRF = listRF;
    }

    public List<DbriskSeverity> getListRS() {
        return listRS;
    }

    public void setListRS(List<DbriskSeverity> listRS) {
        this.listRS = listRS;
    }

    public List<DbcontrolHierarchy> getListCH() {
        return listCH;
    }

    public void setListCH(List<DbcontrolHierarchy> listCH) {
        this.listCH = listCH;
    }

    public List<DbcontrolRecommend> getListCR() {
        return listCR;
    }

    public void setListCR(List<DbcontrolRecommend> listCR) {
        this.listCR = listCR;
    }

    public TreeNode[] getCurrentTree() {
        return currentTree;
    }

    public void setCurrentTree(TreeNode[] currentTree) {
        this.currentTree = currentTree;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }
    
    @PostConstruct
    public void init() {
        activeUser = (DbUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("activeUser");
        
        listLoadedLines = dbimportLineFacade.findNextLinesByUser(activeUser.getUserId()).stream().map(i -> new importWrapperObject(i)).collect(Collectors.toList());
        
        setListHL(dbLocationFacade.findAll());
        setListHP(dbProjectFacade.findAll());
        setListHA(dbhazardActivityFacade.findAll());
        setListHC(dbhazardContextFacade.findAll());
        setListHT(dbhazardTypeFacade.findAll());
        setListHS(dbhazardStatusFacade.findAll());
        setListHO(dbOwnersFacade.findAll());
        setListRC(dbriskClassFacade.findAll());
        setListRF(dbriskFrequencyFacade.findAll());
        setListRS(dbriskSeverityFacade.findAll());
        setListCH(dbcontrolHierarchyFacade.findAll());
        setListCR(dbcontrolRecommendFacade.findAll());
        selectedLine = new DbimportLine();
    }
    
    public void editCell(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        String editedRow = event.getRowKey();
        String[] lineIndex = editedRow.split("\\.");
        
        String processId = lineIndex[0];
        int processIdLine = Integer.valueOf(lineIndex[1]);
        
        DbimportLine lineObject = dbimportLineFacade.findLineById(processId, processIdLine).get(0);
        
        if(newValue != null && !newValue.equals(oldValue)) {
            switch(event.getColumn().getClientId().split(":")[3]) {
                case "DTCol":
                    Date newDate = (Date) newValue;
                    lineObject.setHazardDate(newDate);
                    break;
                case "LICol":
                    lineObject.setHazardLegacyId(newValue.toString());
                    break;
                case "HDCol":
                    lineObject.setHazardDescription(newValue.toString());
                    break;
                case "HMCol":
                    lineObject.setHazardComment(newValue.toString());
                    break;
                case "HWCol":
                    lineObject.setHazardWorkshop(newValue.toString());
                    break;
                case "HCCol":
                    lineObject.setHazardContextId(Integer.valueOf(newValue.toString()));
                    lineObject.setHazardContext(dbhazardContextFacade.findByName("hazardContextId", newValue.toString()).get(0).getHazardContextName());
                    break;
                case "HLCol":
                    lineObject.setHazardLocationId(Integer.valueOf(newValue.toString()));
                    lineObject.setHazardLocation(dbLocationFacade.findByName("locationId", newValue.toString()).get(0).getLocationName());
                    break;
                case "HACol":
                    lineObject.setHazardActivityId(Integer.valueOf(newValue.toString()));
                    lineObject.setHazardActivity(dbhazardActivityFacade.findByName("activityId", newValue.toString()).get(0).getActivityName());
                    break;
                case "HTCol":
                    lineObject.setHazardTypeId(Integer.valueOf(newValue.toString()));
                    lineObject.setHazardType(dbhazardTypeFacade.findByName("hazardTypeId", newValue.toString()).get(0).getHazardTypeName());
                    break;
                case "HSCol":
                    lineObject.setHazardStatusId(Integer.valueOf(newValue.toString()));
                    lineObject.setHazardStatus(dbhazardStatusFacade.findByName("hazardStatusId", newValue.toString()).get(0).getHazardStatusName());
                    break;
                case "HOCol":
                    lineObject.setHazardOwnerId(Integer.valueOf(newValue.toString()));
                    lineObject.setHazardOwner(dbOwnersFacade.findByName("ownerId", newValue.toString()).get(0).getOwnerName());
                    break;
                case "RCCol":
                    lineObject.setHazardRiskClassId(Integer.valueOf(newValue.toString()));
                    lineObject.setHazardRiskClass(dbriskClassFacade.findByName("riskClassId", newValue.toString()).get(0).getRiskClassName());
                    break;
                case "CFCol":
                    lineObject.setHazardCurrentFrequencyId(Integer.valueOf(newValue.toString()));
                    break;
                case "CSCol":
                    lineObject.setHazardCurrentSeverityId(Integer.valueOf(newValue.toString()));
                    break;
                case "TFCol":
                    lineObject.setHazardTargetFrequencyId(Integer.valueOf(newValue.toString()));
                    break;
                case "TSCol":
                    lineObject.setHazardTargetSeverityId(Integer.valueOf(newValue.toString()));
                    break;
                case "RTCol":
                    lineObject.setRelationType(newValue.toString());
                    break;
                case "RDCol":
                    List<String> newList = (List<String>) newValue;
                    lineObject.setRelationDescription(newList.get(0));
                    switch (lineObject.getRelationType()) {
                        case "Cause":
                            lineObject.setRelationId(dbCauseFacade.findByName("causeDescription", newList.get(0)).get(0).getCauseId());
                            break;
                        case "Consequence":
                            lineObject.setRelationId(dbConsequenceFacade.findByName("consequenceDescription", newList.get(0)).get(0).getConsequenceId());
                            break;
                        case "Control":
                            DbControl newControl = dbControlFacade.findByName("controlDescription", newList.get(0)).get(0);
                            lineObject.setRelationId(newControl.getControlId());
                            lineObject.setControlOwnerId(newControl.getOwnerId().getOwnerId());
                            lineObject.setControlOwner(newControl.getOwnerId().getOwnerName());
                            lineObject.setControlHierarchyId(newControl.getControlHierarchyId().getControlHierarchyId());
                            lineObject.setControlHierarchy(newControl.getControlHierarchyId().getControlHierarchyName());
                            break;
                    }
                    break;
                case "COCol":
                    lineObject.setControlOwnerId(Integer.valueOf(newValue.toString()));
                    lineObject.setControlOwner(dbOwnersFacade.findByName("ownerId", newValue.toString()).get(0).getOwnerName());
                case "CHCol":
                    lineObject.setControlHierarchyId(Integer.valueOf(newValue.toString()));
                    lineObject.setControlHierarchy(dbcontrolHierarchyFacade.findByName("controlHierarchyId", newValue.toString()).get(0).getControlHierarchyName());
                case "CTCol":
                    lineObject.setControlType(newValue.toString());
                    break;
                case "CRCol":
                    lineObject.setControlRecommendId(Integer.valueOf(newValue.toString()));
                    lineObject.setControlRecommend(dbcontrolRecommendFacade.findByName("controlRecommendId", newValue.toString()).get(0).getControlRecommendName());
                    break;
                case "CJCol":
                    lineObject.setControlJustify(newValue.toString());
                    break;
                case "CUCol":
                    lineObject.setControlExistingOrProposed(newValue.toString());
                    break;
                default:
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Old: " + oldValue + ", New: " + newValue));
                    break;
                    
            }
            dbimportLineFacade.edit(lineObject);
        }
        listLoadedLines = dbimportLineFacade.findNextLinesByUser(activeUser.getUserId()).stream().map(i -> new importWrapperObject(i)).collect(Collectors.toList());
    }
    
    public Date todaysDate() {
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }
    
    public String convertFrequencyIdToString(int id) {
        try {
            return listRF.stream().filter(f -> f.getRiskFrequencyId().equals(id)).findFirst().get().getFrequencyScore();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
    
    public String convertSeverityIdToString(int id) {
        try {
            return listRS.stream().filter(f -> f.getRiskSeverityId().equals(id)).findFirst().get().getSeverityScore();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
    
    public List<String> listCauses(String query) {
        return dbCauseFacade.findAll().stream().filter(i -> i.getCauseDescription().contains(query.toLowerCase())).map(i -> i.getCauseDescription()).collect(Collectors.toList());
    }
    
    public List<String> listConsequences(String query) {
        return dbConsequenceFacade.findAll().stream().filter(i -> i.getConsequenceDescription().contains(query.toLowerCase())).map(i -> i.getConsequenceDescription()).collect(Collectors.toList());
    }
    
    public List<String> listControls(String query) {
        return dbControlFacade.findAll().stream().filter(i -> i.getControlDescription().contains(query.toLowerCase())).map(i -> i.getControlDescription()).collect(Collectors.toList());
    }
    
    public DbHazard createHazard(DbimportLine lineObject) {
        DbHazard hazardObject = new DbHazard();
        hazardObject.setHazardDate(lineObject.getHazardDate());
        hazardObject.setHazardDescription(lineObject.getHazardDescription());
        hazardObject.setHazardComment(lineObject.getHazardComment());
        hazardObject.setLegacyId(lineObject.getHazardLegacyId());
        hazardObject.setHazardWorkshop(lineObject.getHazardWorkshop());
        hazardObject.setHazardContextId(new DbhazardContext(lineObject.getHazardContextId()));
        hazardObject.setHazardLocation(new DbLocation(lineObject.getHazardLocationId()));
        hazardObject.setHazardActivity(new DbhazardActivity(lineObject.getHazardActivityId()));
        hazardObject.setHazardTypeId(new DbhazardType(lineObject.getHazardTypeId()));
        hazardObject.setHazardStatusId(new DbhazardStatus(lineObject.getHazardStatusId()));
        hazardObject.setOwnerId(new DbOwners(lineObject.getHazardOwnerId()));
        hazardObject.setRiskClassId(new DbriskClass(lineObject.getHazardRiskClassId()));
        hazardObject.setRiskCurrentFrequencyId(new DbriskFrequency(lineObject.getHazardCurrentFrequencyId()));
        hazardObject.setRiskCurrentSeverityId(new DbriskSeverity(lineObject.getHazardCurrentSeverityId()));
        hazardObject.setRiskTargetFrequencyId(new DbriskFrequency(lineObject.getHazardTargetFrequencyId()));
        hazardObject.setRiskTargetSeverityId(new DbriskSeverity(lineObject.getHazardTargetSeverityId()));
        hazardObject.setHazardReview("N");

        // Get the next available consecutive
        List<DbLocation> returnedLocationList = dbLocationFacade.getLocationAbbrev(lineObject.getHazardLocationId());
        String key1 = returnedLocationList.get(0).getProjectId().getProjectAbbrev();
        String key2 = returnedLocationList.get(0).getLocationAbbrev();
        hazardObject.setHazardId(dbglobalIdFacade.nextConsecutive(key1, key2, "-", 4).getAnswerString());

        // Calculate the current and target risk score and persisting the object in the database.
        hazardObject.setRiskCurrentScore(dbHazardFacade.calculateRiskScore(dbriskFrequencyFacade.find(lineObject.getHazardCurrentFrequencyId()).getFrequencyValue(), dbriskSeverityFacade.find(lineObject.getHazardCurrentSeverityId()).getSeverityValue()));
        hazardObject.setRiskTargetScore(dbHazardFacade.calculateRiskScore(dbriskFrequencyFacade.find(lineObject.getHazardTargetFrequencyId()).getFrequencyValue(), dbriskSeverityFacade.find(lineObject.getHazardTargetSeverityId()).getSeverityValue()));

        // Set the audit fields
        hazardObject.setAddedDateTime(new Date());
        hazardObject.setUserIdAdd(activeUser.getUserId());

        // Update the system status to pending
        hazardObject.setHazardSystemStatus(new DbhazardSystemStatus(1));

        // Create the hazard object
        dbHazardFacade.create(hazardObject);
        
        return hazardObject;
    }
    
    public void createRelation(DbimportLine lineObject, DbHazard hazardObject) {
        switch (lineObject.getRelationType()) {
            case "Cause":
                DbCause tmpCause = dbCauseFacade.find(lineObject.getRelationId());
                DbHazardCause tmpHazardCause = new DbHazardCause();
                DbHazardCausePK tmpHazardCausePK = new DbHazardCausePK(hazardObject.getHazardId(), lineObject.getRelationId());
                tmpHazardCause.setDbHazardCausePK(tmpHazardCausePK);
                tmpHazardCause.setDbCause(tmpCause);
                tmpHazardCause.setDbHazard(hazardObject);
                tmpHazardCause.setDbHazardCauseDummyvar(null);
                dbHazardCauseFacade.create(tmpHazardCause);
                break;
            case "Consequence":
                DbConsequence tmpConsequence = dbConsequenceFacade.find(lineObject.getRelationId());
                DbHazardConsequence tmpHazardConsequence = new DbHazardConsequence();
                DbHazardConsequencePK tmpHazardConsequencePK = new DbHazardConsequencePK(hazardObject.getHazardId(), lineObject.getRelationId());
                tmpHazardConsequence.setDbHazardConsequencePK(tmpHazardConsequencePK);
                tmpHazardConsequence.setDbConsequence(tmpConsequence);
                tmpHazardConsequence.setDbHazard(hazardObject);
                tmpHazardConsequence.setDbHazardConsequenceDummyvar(null);
                dbHazardConsequenceFacade.create(tmpHazardConsequence);
                break;
            case "Control":
                DbControlHazard controlHazardObject = new DbControlHazard();
                DbcontrolRecommend tmpCtlRecommend = dbcontrolRecommendFacade.find(lineObject.getControlRecommendId());
                controlHazardObject.setDbHazard(hazardObject);
                controlHazardObject.setControlType(lineObject.getControlType());
                controlHazardObject.setControlExistingOrProposed(lineObject.getControlExistingOrProposed());
                controlHazardObject.setDbControlHazardPK(new DbControlHazardPK(hazardObject.getHazardId(), lineObject.getRelationId()));
                controlHazardObject.setControlRecommendId(new DbcontrolRecommend(lineObject.getControlRecommendId()));
                if ("".equals(lineObject.getControlJustify()) && tmpCtlRecommend.getControlJustifyRequired().equals("N")) {
                    controlHazardObject.setControlJustify(null);
                } else if (!"".equals(lineObject.getControlJustify()) && tmpCtlRecommend.getControlJustifyRequired().equals("Y")) {
                    controlHazardObject.setControlJustify(lineObject.getControlJustify());
                } else if (!"".equals(lineObject.getControlJustify()) && tmpCtlRecommend.getControlJustifyRequired().equals("N")) {
                    controlHazardObject.setControlJustify(null);
                } else if ("".equals(lineObject.getControlJustify()) && tmpCtlRecommend.getControlJustifyRequired().equals("Y")) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Justification: ", "The selected recommendation requires a justification."));
                }
                dbControlHazardFacade.create(controlHazardObject);
                break;
        }
    }
    
    public void submitTable() {
        DbHazard currentHazard = new DbHazard();
        for (int i = 0; i < listCheckedLines.size(); i ++) {
            DbimportLine lineObject = listCheckedLines.get(i).lineObject;
            if (i == 0) {
                // Start of the submission - can't use sameLineObject since currentHazard is null
                currentHazard = createHazard(lineObject);
                createRelation(lineObject, currentHazard);
                if (i == listCheckedLines.size() - 1) {
                    triggerWorkflow(currentHazard);
                
                    // Close off the current header
                    DbimportHeader headerObject = dbimportHeaderFacade.find(lineObject.getDbimportHeader().getProcessId());
                    headerObject.setProcessStatus("S");
                    dbimportHeaderFacade.edit(headerObject);
                }
            } else if (i == listCheckedLines.size() - 1) {
                // Last line of the submission - always need to trigger a workflow and end the header
                if (!sameLineObject(lineObject, currentHazard)) {
                    currentHazard = createHazard(lineObject);
                }
                createRelation(lineObject, currentHazard);
                triggerWorkflow(currentHazard);
                
                // Close off the current header
                DbimportHeader headerObject = dbimportHeaderFacade.find(lineObject.getDbimportHeader().getProcessId());
                headerObject.setProcessStatus("S");
                dbimportHeaderFacade.edit(headerObject);
            } else {
                if (!sameLineObject(lineObject, currentHazard)) {
                    // If there is a change, trigger a workflow and begin constructing a new hazard
                    triggerWorkflow(currentHazard);
                    currentHazard = createHazard(lineObject);
                    createRelation(lineObject, currentHazard);
                } else {
                    // Otherwise don't touch the hazard, just add relations
                    createRelation(lineObject, currentHazard);
                }
            }
        }
        
        // Return to the main menu?
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./../../admin/masterMenu.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(hazardsRelation_MB.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The hazards have been sent for approval by a core user."));
    }
    
    public boolean sameLineObject(DbimportLine lineObject, DbHazard hazardObject) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd");
        if (ft.format(hazardObject.getHazardDate()).equals(ft.format(lineObject.getHazardDate())) && 
                hazardObject.getHazardDescription().equals(lineObject.getHazardDescription()) &&
                hazardObject.getHazardComment().equals(lineObject.getHazardComment()) &&
                hazardObject.getHazardActivity().getActivityId().equals(lineObject.getHazardActivityId()) &&
                hazardObject.getHazardContextId().getHazardContextId().equals(lineObject.getHazardContextId()) &&
                hazardObject.getHazardTypeId().getHazardTypeId().equals(lineObject.getHazardTypeId()) &&
                hazardObject.getHazardStatusId().getHazardStatusId().equals(lineObject.getHazardStatusId()) &&
                hazardObject.getOwnerId().getOwnerId().equals(lineObject.getHazardOwnerId()) &&
                hazardObject.getHazardWorkshop().equals(lineObject.getHazardWorkshop()) &&
                hazardObject.getRiskClassId().getRiskClassId().equals(lineObject.getHazardRiskClassId()) &&
                hazardObject.getRiskCurrentFrequencyId().getRiskFrequencyId().equals(lineObject.getHazardCurrentFrequencyId()) &&
                hazardObject.getRiskCurrentSeverityId().getRiskSeverityId().equals(lineObject.getHazardCurrentSeverityId()) &&
                hazardObject.getRiskTargetFrequencyId().getRiskFrequencyId().equals(lineObject.getHazardTargetFrequencyId()) &&
                hazardObject.getRiskTargetSeverityId().getRiskSeverityId().equals(lineObject.getHazardTargetSeverityId()) &&
                hazardObject.getLegacyId().equals(lineObject.getHazardLegacyId())) {
            return true;
        }
        return false;
    }

    //Triggers the work flow whenever a new hazard has been added or edited. 
    private void triggerWorkflow(DbHazard currentHazard) {
        List<DbUser> listApprovers = dbUserFacade.getWfApproverUsers();
        if (!listApprovers.isEmpty()) {
            createNewWf(listApprovers, currentHazard, "A new hazard was created by a user. Please review and approve, reject, or request more information.");
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "", "There are no users able to approve this workflow."));
        }
    }
    
    private void createNewWf(List<DbUser> listApp, DbHazard hazardObj, String Comment1) {
        DbwfHeader wfObj = new DbwfHeader();
        wfObj.setWfTypeId(new DbwfType("W3"));
        wfObj.setWfStatus("O");
        wfObj.setWfAddedDateTime(new Date());
        wfObj.setWfUserIdAdd((DbUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("activeUser"));
        wfObj.setWfObjectId(hazardObj.getHazardId());
        wfObj.setWfObjectName("Hazard");
        wfObj.setWfComment1(Comment1);
        wfObj.setWfCompleteMethod("HazardApprovalWF");
        validateIdObject result = dbwfHeaderFacade.newWorkFlow(listApp, wfObj, "WKF-HRD");
    }
    
    public List<String> listSbsCodes(DbimportLine lineObject) {
        return Arrays.asList(lineObject.getHazardSbs().split(",")).stream().map(i -> getNodeNameById(i)).collect(Collectors.toList());
    }
    
    public void populateTree(DbimportLine lineObject) {
        selectedLine = lineObject;
        List<String> sbsCodes = Arrays.asList(lineObject.getHazardSbs().replace(" ", "").split(","));
        
        // To account for the lack of dots, add them if the code terminates in a number before a comma
        // This is necessary because the tree tables all have full stops (e.g. 1., 2.1.) but users might
        //  not enter full stops at the end. So the below stream goes through all the codes and adds them
        //  if they're missing to allow for a good comparison
        sbsCodes = sbsCodes.stream().map(i -> {if (i.endsWith(".")) {return i;} else {return i += ".";}}).collect(Collectors.toList());
        
        String sbsId;
        int counterLevel4 = 1;
        int counterLevel5 = 1;
        int counterLevel6 = 1;

        List<TreeNode> listTreeNode = new ArrayList<>();

        // The tree starts with a 
        DbtreeLevel1 tmpResultObjLevel1 = dbtreeLevel1Facade.find(1);
        root = new DefaultTreeNode("Root", null);
        
        // If there exists a tree
        if (!tmpResultObjLevel1.getTreeLevel1Name().isEmpty()) {
            // Create a root node
            TreeNode nodeLevel1 = new DefaultTreeNode(tmpResultObjLevel1.getTreeLevel1Name(), root);

            // Set the temporary SBS id to each node in the tree
            sbsId = "1.";
            
            // If the sbs codes in the list contain the temporary node, or the
            //  temporary node starts with any in the list (i.e. the temporary
            //  node is a child of an sbs code in the list)
            if (sbsCodes.contains(sbsId) || sbsCodes.stream().parallel().anyMatch(sbsId::startsWith)) {
                // Then the node must be selected
                nodeLevel1.setSelected(true);
                
                // And the node is added to the constructed tree
                listTreeNode.add(nodeLevel1);
            }

            List<DbtreeLevel2> tmpListLevel2 = dbtreeLevel2Facade.findByLevel1Id(tmpResultObjLevel1.getTreeLevel1Id());
            if (!tmpListLevel2.isEmpty()) {
                tmpListLevel2.sort(Comparator.comparingInt(DbtreeLevel2::getTreeLevel2Index));
                for (DbtreeLevel2 tmpGOLevel2 : tmpListLevel2) {
                    Integer level2No = tmpGOLevel2.getTreeLevel2Index();
                    String levelLabel2 = level2No.toString() + ".";
                    TreeNode nodeLevel2 = new DefaultTreeNode(levelLabel2 + " "
                            + tmpGOLevel2.getTreeLevel2Name(), nodeLevel1);

                    sbsId = "1." + nodeLevel2.getData().toString().substring(0, 2);
                    if (sbsCodes.contains(sbsId) || sbsCodes.stream().parallel().anyMatch(sbsId::startsWith)) {
                        nodeLevel2.setSelected(true);
                        listTreeNode.add(nodeLevel2);
                    }

                    List<DbtreeLevel3> tmpListLevel3 = dbtreeLevel3Facade.findByLevel2Id(tmpGOLevel2.getDbtreeLevel2PK().getTreeLevel2Id());
                    if (!tmpListLevel3.isEmpty()) {
                        tmpListLevel3.sort(Comparator.comparingInt(DbtreeLevel3::getTreeLevel3Index));
                        for (DbtreeLevel3 tmpGOLevel3 : tmpListLevel3) {
                            Integer level3No = tmpGOLevel3.getTreeLevel3Index();
                            String levelLabel3 = levelLabel2 + level3No.toString() + ".";
                            TreeNode nodeLevel3 = new DefaultTreeNode(levelLabel3 + " "
                                    + tmpGOLevel3.getTreeLevel3Name(), nodeLevel2);

                            sbsId = "1." + nodeLevel3.getData().toString().substring(0, 4);
                            if (sbsCodes.contains(sbsId) || sbsCodes.stream().parallel().anyMatch(sbsId::startsWith)) {
                                nodeLevel3.setSelected(true);
                                listTreeNode.add(nodeLevel3);
                            }

                            List<DbtreeLevel4> tmpListLevel4 = dbtreeLevel4Facade.findByLevel3Id(tmpGOLevel3.getDbtreeLevel3PK().getTreeLevel3Id());
                            if (!tmpListLevel4.isEmpty()) {
                                tmpListLevel4.sort(Comparator.comparingInt(DbtreeLevel4::getTreeLevel4Index));
                                for (DbtreeLevel4 tmpGOLevel4 : tmpListLevel4) {
                                    Integer level4No = tmpGOLevel4.getTreeLevel4Index();
                                    String levelLabel4 = levelLabel3 + level4No.toString() + ".";
                                    TreeNode nodeLevel4 = new DefaultTreeNode(levelLabel4 + " "
                                            + tmpGOLevel4.getTreeLevel4Name(), nodeLevel3);

                                    if (counterLevel4 < 10) {
                                        sbsId = "1." + nodeLevel4.getData().toString().substring(0, 6);
                                    } else {
                                        sbsId = "1." + nodeLevel4.getData().toString().substring(0, 7);
                                    }
                                    counterLevel4++;
                                    if (sbsCodes.contains(sbsId) || sbsCodes.stream().parallel().anyMatch(sbsId::startsWith)) {
                                        nodeLevel4.setSelected(true);
                                        listTreeNode.add(nodeLevel4);
                                    }

                                    List<DbtreeLevel5> tmpListLevel5 = dbtreeLevel5Facade.findByLevel4Id(tmpGOLevel4.getDbtreeLevel4PK().getTreeLevel4Id());
                                    if (!tmpListLevel5.isEmpty()) {
                                        tmpListLevel5.sort(Comparator.comparingInt(DbtreeLevel5::getTreeLevel5Index));
                                        for (DbtreeLevel5 tmpGOLevel5 : tmpListLevel5) {
                                            Integer level5No = tmpGOLevel5.getTreeLevel5Index();
                                            String levelLabel5 = levelLabel4 + level5No.toString() + ".";
                                            TreeNode nodeLevel5 = new DefaultTreeNode(levelLabel5 + " "
                                                    + tmpGOLevel5.getTreeLevel5Name(), nodeLevel4);

                                            if (counterLevel5 < 10) {
                                                sbsId = "1." + nodeLevel5.getData().toString().substring(0, 8);
                                            } else {
                                                sbsId = "1." + nodeLevel5.getData().toString().substring(0, 9);
                                            }
                                            counterLevel5++;
                                            if (sbsCodes.contains(sbsId) || sbsCodes.stream().parallel().anyMatch(sbsId::startsWith)) {
                                                nodeLevel5.setSelected(true);
                                                listTreeNode.add(nodeLevel5);
                                            }

                                            List<DbtreeLevel6> tmpListLevel6 = dbtreeLevel6Facade.findByLevel5Id(tmpGOLevel5.getDbtreeLevel5PK().getTreeLevel5Id());
                                            if (!tmpListLevel6.isEmpty()) {
                                                tmpListLevel6.sort(Comparator.comparingInt(DbtreeLevel6::getTreeLevel6Index));
                                                for (DbtreeLevel6 tmpGOLevel6 : tmpListLevel6) {
                                                    Integer level6No = tmpGOLevel6.getTreeLevel6Index();
                                                    String levelLabel6 = levelLabel5 + level6No.toString() + ".";
                                                    TreeNode nodeLevel6 = new DefaultTreeNode(levelLabel6 + " "
                                                            + tmpGOLevel6.getTreeLevel6Name(), nodeLevel5);

                                                    if (counterLevel6 < 10) {
                                                        sbsId = "1." + nodeLevel6.getData().toString().substring(0, 10);
                                                    } else {
                                                        sbsId = "1." + nodeLevel6.getData().toString().substring(0, 11);
                                                    }
                                                    counterLevel6++;
                                                    if (sbsCodes.contains(sbsId) || sbsCodes.stream().parallel().anyMatch(sbsId::startsWith)) {
                                                        nodeLevel6.setSelected(true);
                                                        listTreeNode.add(nodeLevel6);
                                                    }
                                                }
                                            }

                                        }
                                    }

                                }
                            }

                        }
                    }

                }
            }

        }
        currentTree = listTreeNode.toArray(new TreeNode[listTreeNode.size()]);
    }
    
    public String getNodeNameById(String nodeId) {
        List<treeNodeObject> treeHazardSbsList = new ArrayList<>();
        String nodeName = "";
        String parts[] = nodeId.split("\\.");
        for (int i = 1; i <= parts.length; i++) {
            switch (i) {
                case 1:
                    DbtreeLevel1 tmpDbLvl1 = dbtreeLevel1Facade.findByIndex(Integer.parseInt(parts[0]));
                    if (tmpDbLvl1.getTreeLevel1Name() != null) {
                        treeHazardSbsList.add(new treeNodeObject(nodeId,
                                tmpDbLvl1.getTreeLevel1Name()));
                        nodeName += treeHazardSbsList.get(0).getNodeName();
                    }
                    break;
                case 2:
                    DbtreeLevel2 tmpDbLvl2 = dbtreeLevel1Facade.findByIndex(Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]));
                    if (tmpDbLvl2.getTreeLevel2Name() != null) {
                        treeHazardSbsList.add(new treeNodeObject(nodeId,
                                tmpDbLvl2.getTreeLevel2Name()));
                        nodeName += " - " + treeHazardSbsList.get(1).getNodeName();
                    }
                    break;
                case 3:
                    DbtreeLevel3 tmpDbLvl3 = dbtreeLevel1Facade.findByIndex(Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                    if (tmpDbLvl3.getTreeLevel3Name() != null) {
                        treeHazardSbsList.add(new treeNodeObject(nodeId,
                                tmpDbLvl3.getTreeLevel3Name()));
                        nodeName += " - " + treeHazardSbsList.get(2).getNodeName();
                    }
                    break;
                case 4:
                    DbtreeLevel4 tmpDbLvl4 = dbtreeLevel1Facade.findByIndex(Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
                    if (tmpDbLvl4.getTreeLevel4Name() != null) {
                        treeHazardSbsList.add(new treeNodeObject(nodeId,
                                tmpDbLvl4.getTreeLevel4Name()));
                        nodeName += " - " + treeHazardSbsList.get(3).getNodeName();
                    }
                    break;
                case 5:
                    DbtreeLevel5 tmpDbLvl5 = dbtreeLevel1Facade.findByIndex(Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]),
                            Integer.parseInt(parts[4]));
                    if (tmpDbLvl5.getTreeLevel5Name() != null) {
                        treeHazardSbsList.add(new treeNodeObject(nodeId,
                                tmpDbLvl5.getTreeLevel5Name()));
                        nodeName += " - " + treeHazardSbsList.get(4).getNodeName();
                    }
                    break;
                case 6:
                    DbtreeLevel6 tmpDbLvl6 = dbtreeLevel1Facade.findByIndex(Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]),
                            Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));
                    if (tmpDbLvl6.getTreeLevel6Name() != null) {
                        treeHazardSbsList.add(new treeNodeObject(nodeId,
                                tmpDbLvl6.getTreeLevel6Name()));
                        nodeName += " - " + treeHazardSbsList.get(5).getNodeName();
                    }
                    break;
            }
        }
        return nodeName;
    }
    
    public void editTree() {
        if (currentTree.length > 1) {
            String sbsCodes = Arrays.asList(currentTree).stream().map(i -> "1." + i.getData().toString().replaceAll("[^\\d+\\.]", "")).reduce("", (a, b) -> a + "," + b);
            selectedLine.setHazardSbs(sbsCodes.substring(1));
            dbimportLineFacade.edit(selectedLine);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "The SBS tree must not be empty!"));
        }
    }
    
    public class importWrapperObject {
        public DbimportLine lineObject;
        public List<DbimportLineError> listErrorObjects;
        public List<DbimportLineError> listWarningObjects;
        public String errorMessage = "";
        public String warningMessage = "";
        
        public boolean DAError;
        public boolean HDError;
        public boolean HMError;
        public boolean HWError;
        public boolean HCError;
        public boolean HLError;
        public boolean HAError;
        public boolean HTError;
        public boolean HSError;
        public boolean HOError;
        public boolean RCError;
        public boolean CFError;
        public boolean CSError;
        public boolean TFError;
        public boolean TSError;
        public boolean SBSError;
        public boolean RTError;
        public boolean RDError;
        public boolean COError;
        public boolean CHError;
        public boolean CTError;
        public boolean CRError;
        public boolean CJError;
        public boolean CUError;
        public boolean SCError;
        public boolean DAWarning;
        public boolean HDWarning;
        public boolean HMWarning;
        public boolean HWWarning;
        public boolean HCWarning;
        public boolean HLWarning;
        public boolean HAWarning;
        public boolean HTWarning;
        public boolean HSWarning;
        public boolean HOWarning;
        public boolean RCWarning;
        public boolean CFWarning;
        public boolean CSWarning;
        public boolean TFWarning;
        public boolean TSWarning;
        public boolean SBSWarning;
        public boolean RTWarning;
        public boolean RDWarning;
        public boolean COWarning;
        public boolean CHWarning;
        public boolean CTWarning;
        public boolean CRWarning;
        public boolean CJWarning;
        public boolean CUWarning;
        public boolean SCWarning;

        public DbimportLine getLineObject() {
            return lineObject;
        }

        public void setLineObject(DbimportLine lineObject) {
            this.lineObject = lineObject;
        }

        public List<DbimportLineError> getListErrorObjects() {
            return listErrorObjects;
        }

        public void setListErrorObjects(List<DbimportLineError> listErrorObjects) {
            this.listErrorObjects = listErrorObjects;
        }

        public List<DbimportLineError> getListWarningObjects() {
            return listWarningObjects;
        }

        public void setListWarningObjects(List<DbimportLineError> listWarningObjects) {
            this.listWarningObjects = listWarningObjects;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getWarningMessage() {
            return warningMessage;
        }

        public void setWarningMessage(String warningMessage) {
            this.warningMessage = warningMessage;
        }

        public boolean isDAError() {
            return DAError;
        }

        public void setDAError(boolean DAError) {
            this.DAError = DAError;
        }

        public boolean isHDError() {
            return HDError;
        }

        public void setHDError(boolean HDError) {
            this.HDError = HDError;
        }

        public boolean isHMError() {
            return HMError;
        }

        public void setHMError(boolean HMError) {
            this.HMError = HMError;
        }

        public boolean isHWError() {
            return HWError;
        }

        public void setHWError(boolean HWError) {
            this.HWError = HWError;
        }

        public boolean isHCError() {
            return HCError;
        }

        public void setHCError(boolean HCError) {
            this.HCError = HCError;
        }

        public boolean isHLError() {
            return HLError;
        }

        public void setHLError(boolean HLError) {
            this.HLError = HLError;
        }

        public boolean isHAError() {
            return HAError;
        }

        public void setHAError(boolean HAError) {
            this.HAError = HAError;
        }

        public boolean isHTError() {
            return HTError;
        }

        public void setHTError(boolean HTError) {
            this.HTError = HTError;
        }

        public boolean isHSError() {
            return HSError;
        }

        public void setHSError(boolean HSError) {
            this.HSError = HSError;
        }

        public boolean isHOError() {
            return HOError;
        }

        public void setHOError(boolean HOError) {
            this.HOError = HOError;
        }

        public boolean isRCError() {
            return RCError;
        }

        public void setRCError(boolean RCError) {
            this.RCError = RCError;
        }

        public boolean isCFError() {
            return CFError;
        }

        public void setCFError(boolean CFError) {
            this.CFError = CFError;
        }

        public boolean isCSError() {
            return CSError;
        }

        public void setCSError(boolean CSError) {
            this.CSError = CSError;
        }

        public boolean isTFError() {
            return TFError;
        }

        public void setTFError(boolean TFError) {
            this.TFError = TFError;
        }

        public boolean isTSError() {
            return TSError;
        }

        public void setTSError(boolean TSError) {
            this.TSError = TSError;
        }

        public boolean isSBSError() {
            return SBSError;
        }

        public void setSBSError(boolean SBSError) {
            this.SBSError = SBSError;
        }

        public boolean isRTError() {
            return RTError;
        }

        public void setRTError(boolean RTError) {
            this.RTError = RTError;
        }

        public boolean isRDError() {
            return RDError;
        }

        public void setRDError(boolean RDError) {
            this.RDError = RDError;
        }

        public boolean isCTError() {
            return CTError;
        }

        public void setCTError(boolean CTError) {
            this.CTError = CTError;
        }

        public boolean isCRError() {
            return CRError;
        }

        public void setCRError(boolean CRError) {
            this.CRError = CRError;
        }

        public boolean isCJError() {
            return CJError;
        }

        public void setCJError(boolean CJError) {
            this.CJError = CJError;
        }

        public boolean isCUError() {
            return CUError;
        }

        public void setCUError(boolean CUError) {
            this.CUError = CUError;
        }

        public boolean isDAWarning() {
            return DAWarning;
        }

        public void setDAWarning(boolean DAWarning) {
            this.DAWarning = DAWarning;
        }

        public boolean isHDWarning() {
            return HDWarning;
        }

        public void setHDWarning(boolean HDWarning) {
            this.HDWarning = HDWarning;
        }

        public boolean isHMWarning() {
            return HMWarning;
        }

        public void setHMWarning(boolean HMWarning) {
            this.HMWarning = HMWarning;
        }

        public boolean isHWWarning() {
            return HWWarning;
        }

        public void setHWWarning(boolean HWWarning) {
            this.HWWarning = HWWarning;
        }

        public boolean isHCWarning() {
            return HCWarning;
        }

        public void setHCWarning(boolean HCWarning) {
            this.HCWarning = HCWarning;
        }

        public boolean isHLWarning() {
            return HLWarning;
        }

        public void setHLWarning(boolean HLWarning) {
            this.HLWarning = HLWarning;
        }

        public boolean isHAWarning() {
            return HAWarning;
        }

        public void setHAWarning(boolean HAWarning) {
            this.HAWarning = HAWarning;
        }

        public boolean isHTWarning() {
            return HTWarning;
        }

        public void setHTWarning(boolean HTWarning) {
            this.HTWarning = HTWarning;
        }

        public boolean isHSWarning() {
            return HSWarning;
        }

        public void setHSWarning(boolean HSWarning) {
            this.HSWarning = HSWarning;
        }

        public boolean isHOWarning() {
            return HOWarning;
        }

        public void setHOWarning(boolean HOWarning) {
            this.HOWarning = HOWarning;
        }

        public boolean isRCWarning() {
            return RCWarning;
        }

        public void setRCWarning(boolean RCWarning) {
            this.RCWarning = RCWarning;
        }

        public boolean isCFWarning() {
            return CFWarning;
        }

        public void setCFWarning(boolean CFWarning) {
            this.CFWarning = CFWarning;
        }

        public boolean isCSWarning() {
            return CSWarning;
        }

        public void setCSWarning(boolean CSWarning) {
            this.CSWarning = CSWarning;
        }

        public boolean isTFWarning() {
            return TFWarning;
        }

        public void setTFWarning(boolean TFWarning) {
            this.TFWarning = TFWarning;
        }

        public boolean isTSWarning() {
            return TSWarning;
        }

        public void setTSWarning(boolean TSWarning) {
            this.TSWarning = TSWarning;
        }

        public boolean isSBSWarning() {
            return SBSWarning;
        }

        public void setSBSWarning(boolean SBSWarning) {
            this.SBSWarning = SBSWarning;
        }

        public boolean isRTWarning() {
            return RTWarning;
        }

        public void setRTWarning(boolean RTWarning) {
            this.RTWarning = RTWarning;
        }

        public boolean isRDWarning() {
            return RDWarning;
        }

        public void setRDWarning(boolean RDWarning) {
            this.RDWarning = RDWarning;
        }

        public boolean isCTWarning() {
            return CTWarning;
        }

        public void setCTWarning(boolean CTWarning) {
            this.CTWarning = CTWarning;
        }

        public boolean isCRWarning() {
            return CRWarning;
        }

        public void setCRWarning(boolean CRWarning) {
            this.CRWarning = CRWarning;
        }

        public boolean isCJWarning() {
            return CJWarning;
        }

        public void setCJWarning(boolean CJWarning) {
            this.CJWarning = CJWarning;
        }

        public boolean isCUWarning() {
            return CUWarning;
        }

        public void setCUWarning(boolean CUWarning) {
            this.CUWarning = CUWarning;
        }

        public boolean isCOError() {
            return COError;
        }

        public void setCOError(boolean COError) {
            this.COError = COError;
        }

        public boolean isCHError() {
            return CHError;
        }

        public void setCHError(boolean CHError) {
            this.CHError = CHError;
        }

        public boolean isCOWarning() {
            return COWarning;
        }

        public void setCOWarning(boolean COWarning) {
            this.COWarning = COWarning;
        }

        public boolean isCHWarning() {
            return CHWarning;
        }

        public void setCHWarning(boolean CHWarning) {
            this.CHWarning = CHWarning;
        }

        public boolean isSCError() {
            return SCError;
        }

        public void setSCError(boolean SCError) {
            this.SCError = SCError;
        }

        public boolean isSCWarning() {
            return SCWarning;
        }

        public void setSCWarning(boolean SCWarning) {
            this.SCWarning = SCWarning;
        }
        
        public importWrapperObject(DbimportLine lineObject) {
            this.lineObject = lineObject;
            
            List<DbimportLineError> listRawObjects = dbimportLineErrorFacade.listErrorsByLine(lineObject.getDbimportLinePK().getProcessId(), lineObject.getDbimportLinePK().getProcessIdLine());
            this.listErrorObjects = listRawObjects.stream().filter(i -> i.getProcessErrorCode().getErrorAction().equals("E")).collect(Collectors.toList());
            this.listWarningObjects = listRawObjects.stream().filter(i -> i.getProcessErrorCode().getErrorAction().equals("W")).collect(Collectors.toList());
            
            for (DbimportLineError errorObject : listErrorObjects) {
                errorMessage += errorObject.getProcessErrorCode().getErrorName().concat(" error in the ").concat(errorObject.getProcessErrorLocation().toLowerCase()).concat(" field. ");
            }
            
            for (DbimportLineError warningObject : listWarningObjects) {
                warningMessage += warningObject.getProcessErrorCode().getErrorName().concat(" warning in the ").concat(warningObject.getProcessErrorLocation().toLowerCase()).concat(" field. ");
            }
            
            this.DAError = listErrorObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardDate"));
            this.HDError = listErrorObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardDescription"));
            this.HMError = listErrorObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardComment"));
            this.HCError = listErrorObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardContext"));
            this.HLError = listErrorObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardLocation"));
            this.HAError = listErrorObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardActivity"));
            this.HTError = listErrorObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardType"));
            this.HSError = listErrorObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardStatus"));
            this.HOError = listErrorObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardOwner"));
            this.RCError = listErrorObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardRiskClass"));
            this.CFError = listErrorObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardCurrentFreq"));
            this.CSError = listErrorObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardCurrentSev"));
            this.TFError = listErrorObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardTargetFreq"));
            this.TSError = listErrorObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardTargetSev"));
            this.RDError = listErrorObjects.stream().anyMatch(i -> (i.getProcessErrorLocation().equalsIgnoreCase("relationDescription") 
                    || i.getProcessErrorLocation().equalsIgnoreCase("Cause")
                    || i.getProcessErrorLocation().equalsIgnoreCase("Consequence")
                    || i.getProcessErrorLocation().equalsIgnoreCase("Control")));
            this.COError = listErrorObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("controlOwner"));
            this.CHError = listErrorObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("controlHierarchy"));
            this.CRError = listErrorObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("controlRecommend"));
            this.CJError = listErrorObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("controlJustify"));
            this.CTError = listErrorObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("controlType"));
            this.CUError = listErrorObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("controlStatus"));
            this.SCError = listErrorObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardSbs"));
            this.DAWarning = listWarningObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardDate"));
            this.HDWarning = listWarningObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazard"));
            this.HMWarning = listWarningObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardComment"));
            this.HCWarning = listWarningObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardContext"));
            this.HLWarning = listWarningObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardLocation"));
            this.HAWarning = listWarningObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardActivity"));
            this.HTWarning = listWarningObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardType"));
            this.HSWarning = listWarningObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardStatus"));
            this.HOWarning = listWarningObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardOwner"));
            this.RCWarning = listWarningObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardRiskClass"));
            this.CFWarning = listWarningObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardCurrentFreq"));
            this.CSWarning = listWarningObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardCurrentSev"));
            this.TFWarning = listWarningObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardTargetFreq"));
            this.TSWarning = listWarningObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardTargetSev"));
            this.RDWarning = listWarningObjects.stream().anyMatch(i -> (i.getProcessErrorLocation().equalsIgnoreCase("relationDescription") 
                    || i.getProcessErrorLocation().equalsIgnoreCase("Cause")
                    || i.getProcessErrorLocation().equalsIgnoreCase("Consequence")
                    || i.getProcessErrorLocation().equalsIgnoreCase("Control")));
            this.COWarning = listWarningObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("controlOwner"));
            this.CHWarning = listWarningObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("controlHierarchy"));
            this.CRWarning = listWarningObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("controlRecommend"));
            this.CJWarning = listWarningObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("controlJustify"));
            this.CTWarning = listWarningObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("controlType"));
            this.CUWarning = listWarningObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("controlStatus"));
            this.SCWarning = listWarningObjects.stream().anyMatch(i -> i.getProcessErrorLocation().equalsIgnoreCase("hazardSbs"));
        }
    }
}
