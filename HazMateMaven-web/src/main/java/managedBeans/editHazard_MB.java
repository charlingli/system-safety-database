/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;

import customObjects.searchObject;
import customObjects.treeNodeObject;
import ejb.DbHazardFacadeLocal;
import ejb.DbHazardSbsFacadeLocal;
import ejb.DbLocationFacadeLocal;
import ejb.DbOwnersFacadeLocal;
import ejb.DbhazardActivityFacadeLocal;
import ejb.DbhazardContextFacadeLocal;
import ejb.DbhazardStatusFacadeLocal;
import ejb.DbhazardTypeFacadeLocal;
import ejb.DbriskClassFacadeLocal;
import ejb.DbriskFrequencyFacadeLocal;
import ejb.DbriskSeverityFacadeLocal;
import ejb.DbtreeLevel1FacadeLocal;
import ejb.DbtreeLevel2FacadeLocal;
import ejb.DbtreeLevel3FacadeLocal;
import ejb.DbtreeLevel4FacadeLocal;
import ejb.DbtreeLevel5FacadeLocal;
import ejb.DbtreeLevel6FacadeLocal;
import entities.DbHazard;
import entities.DbHazardSbs;
import entities.DbHazardSbsPK;
import entities.DbLocation;
import entities.DbOwners;
import entities.DbUser;
import entities.DbhazardActivity;
import entities.DbhazardContext;
import entities.DbhazardStatus;
import entities.DbhazardType;
import entities.DbriskClass;
import entities.DbriskFrequency;
import entities.DbriskSeverity;
import entities.DbtreeLevel1;
import entities.DbtreeLevel2;
import entities.DbtreeLevel3;
import entities.DbtreeLevel4;
import entities.DbtreeLevel5;
import entities.DbtreeLevel6;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author alan8
 */
@Named(value = "editHazard_MB")
@ViewScoped
public class editHazard_MB implements Serializable {

    @EJB
    private DbHazardSbsFacadeLocal dbHazardSbsFacade;
    @EJB
    private DbHazardFacadeLocal dbHazardFacade;
    @EJB
    private DbriskClassFacadeLocal dbriskClassFacade;
    @EJB
    private DbhazardContextFacadeLocal dbhazardContextFacade;
    @EJB
    private DbriskSeverityFacadeLocal dbriskSeverityFacade;
    @EJB
    private DbriskFrequencyFacadeLocal dbriskFrequencyFacade;
    @EJB
    private DbOwnersFacadeLocal dbOwnersFacade;
    @EJB
    private DbhazardTypeFacadeLocal dbhazardTypeFacade;
    @EJB
    private DbhazardStatusFacadeLocal dbhazardStatusFacade;
    @EJB
    private DbLocationFacadeLocal dbLocationFacade;
    @EJB
    private DbhazardActivityFacadeLocal dbhazardActivityFacade;
    @EJB
    private DbtreeLevel6FacadeLocal dbtreeLevel6Facade;
    @EJB
    private DbtreeLevel5FacadeLocal dbtreeLevel5Facade;
    @EJB
    private DbtreeLevel4FacadeLocal dbtreeLevel4Facade;
    @EJB
    private DbtreeLevel3FacadeLocal dbtreeLevel3Facade;
    @EJB
    private DbtreeLevel2FacadeLocal dbtreeLevel2Facade;
    @EJB
    private DbtreeLevel1FacadeLocal dbtreeLevel1Facade;

    private List<DbhazardActivity> listDbHazardActivity;
    private List<DbLocation> listDbLocation;
    private List<DbhazardStatus> listDbHazardStatus;
    private List<DbhazardType> listDbhazardType;
    private List<DbOwners> listDbOwners;
    private List<DbhazardContext> listDbHazardContext;
    private List<DbriskFrequency> listDbRiskFrequency;
    private List<DbriskSeverity> listDbRiskSeverity;
    private List<DbriskClass> listDbRiskClass;

    private DbHazard hazardObject = new DbHazard();
    private DbhazardActivity activityObject = new DbhazardActivity();
    private DbLocation locationObject = new DbLocation();
    private DbhazardStatus statusObject = new DbhazardStatus();
    private DbhazardType typeObject = new DbhazardType();
    private DbOwners ownersObject = new DbOwners();
    private DbhazardContext hazardContextObject = new DbhazardContext();
    private DbriskFrequency riskFrequencyObject = new DbriskFrequency();
    private DbriskSeverity riskSeverityObject = new DbriskSeverity();
    private DbriskClass riskClassObject = new DbriskClass();

    private int activityId;
    private int locationId;
    private int statusId;
    private int typeId;
    private int ownerId;
    private int hazardContextId;
    private int freqId;
    private int severityId;
    private int riskClassId;

    String strHazardReview;

    private boolean editFlag = false;
//    private boolean deleteButton = false;

    /*Variables relating to the Search function*/
    private List<searchObject> listSearchObject;
    private List<DbHazard> listDbHazard;

    private searchObject hazardIdObject;
    private searchObject hazardContextIdObject;
    private searchObject hazardDescriptionObject;
    private searchObject hazardLocationObject;
    private searchObject hazardActivityObject;
    private searchObject ownerIdObject;
    private searchObject hazardTypeIdObject;
    private searchObject hazardStatusIdObject;
    private searchObject riskClassIdObject;
    private searchObject legacyIdObject;

    private String selectedHazardId;
    private Date selectedHazardDate;
    private String selectedHazardContext;
    private String selectedHazardDescription;
    private String selectedHazardLocation;
    private String selectedHazardActivity;
    private String selectedOwner;
    private String selectedHazardType;
    private String selectedHazardStatus;
    private String selectedRiskClass;
    private String selectedLegacyId;
    private String redirectionSource;

    /*Variables relating to the SBS tree*/
    private TreeNode root;
    private TreeNode[] selectedNodes;
    private List<treeNodeObject> treeCheckedNodesList;
    private treeNodeObject selectedTreeNodeObject = new treeNodeObject();
    private DbHazardSbsPK hazardSbsPKObject = new DbHazardSbsPK();
    private DbHazardSbs hazardSbsObject = new DbHazardSbs();
    private DbHazard hazardFKObject = new DbHazard();

    private String editHazardId;
    private boolean treeFlag = false;
    private boolean popFlag = true;

    public editHazard_MB() {
    }

    public DbHazard getHazardObject() {
        return hazardObject;
    }

    public void setHazardObject(DbHazard hazardObject) {
        this.hazardObject = hazardObject;
    }

    public boolean isEditFlag() {
        return editFlag;
    }

    public void setEditFlag(boolean editFlag) {
        this.editFlag = editFlag;
    }

    public searchObject getHazardIdObject() {
        return hazardIdObject;
    }

    public void setHazardIdObject(searchObject hazardIdObject) {
        this.hazardIdObject = hazardIdObject;
    }

    public searchObject getHazardContextIdObject() {
        return hazardContextIdObject;
    }

    public void setHazardContextIdObject(searchObject hazardContextIdObject) {
        this.hazardContextIdObject = hazardContextIdObject;
    }

    public searchObject getHazardDescriptionObject() {
        return hazardDescriptionObject;
    }

    public void setHazardDescriptionObject(searchObject hazardDescriptionObject) {
        this.hazardDescriptionObject = hazardDescriptionObject;
    }

    public searchObject getHazardLocationObject() {
        return hazardLocationObject;
    }

    public void setHazardLocationObject(searchObject hazardLocationObject) {
        this.hazardLocationObject = hazardLocationObject;
    }

    public searchObject getHazardActivityObject() {
        return hazardActivityObject;
    }

    public void setHazardActivityObject(searchObject hazardActivityObject) {
        this.hazardActivityObject = hazardActivityObject;
    }

    public searchObject getOwnerIdObject() {
        return ownerIdObject;
    }

    public void setOwnerIdObject(searchObject ownerIdObject) {
        this.ownerIdObject = ownerIdObject;
    }

    public searchObject getHazardTypeIdObject() {
        return hazardTypeIdObject;
    }

    public void setHazardTypeIdObject(searchObject hazardTypeIdObject) {
        this.hazardTypeIdObject = hazardTypeIdObject;
    }

    public searchObject getHazardStatusIdObject() {
        return hazardStatusIdObject;
    }

    public void setHazardStatusIdObject(searchObject hazardStatusIdObject) {
        this.hazardStatusIdObject = hazardStatusIdObject;
    }

    public searchObject getRiskClassIdObject() {
        return riskClassIdObject;
    }

    public void setRiskClassIdObject(searchObject riskClassIdObject) {
        this.riskClassIdObject = riskClassIdObject;
    }

    public String getSelectedHazardId() {
        return selectedHazardId;
    }

    public void setSelectedHazardId(String selectedHazardId) {
        this.selectedHazardId = selectedHazardId;
    }

    public String getSelectedHazardContext() {
        return selectedHazardContext;
    }

    public void setSelectedHazardContext(String selectedHazardContext) {
        this.selectedHazardContext = selectedHazardContext;
    }

    public String getSelectedHazardDescription() {
        return selectedHazardDescription;
    }

    public void setSelectedHazardDescription(String selectedHazardDescription) {
        this.selectedHazardDescription = selectedHazardDescription;
    }

    public String getSelectedHazardLocation() {
        return selectedHazardLocation;
    }

    public void setSelectedHazardLocation(String selectedHazardLocation) {
        this.selectedHazardLocation = selectedHazardLocation;
    }

    public String getSelectedHazardActivity() {
        return selectedHazardActivity;
    }

    public void setSelectedHazardActivity(String selectedHazardActivity) {
        this.selectedHazardActivity = selectedHazardActivity;
    }

    public String getSelectedOwner() {
        return selectedOwner;
    }

    public void setSelectedOwner(String selectedOwner) {
        this.selectedOwner = selectedOwner;
    }

    public String getSelectedHazardType() {
        return selectedHazardType;
    }

    public void setSelectedHazardType(String selectedHazardType) {
        this.selectedHazardType = selectedHazardType;
    }

    public String getSelectedHazardStatus() {
        return selectedHazardStatus;
    }

    public void setSelectedHazardStatus(String selectedHazardStatus) {
        this.selectedHazardStatus = selectedHazardStatus;
    }

    public String getSelectedRiskClass() {
        return selectedRiskClass;
    }

    public void setSelectedRiskClass(String selectedRiskClass) {
        this.selectedRiskClass = selectedRiskClass;
    }

    public List<DbhazardContext> getListDbHazardContext() {
        return listDbHazardContext;
    }

    public void setListDbHazardContext(List<DbhazardContext> listDbHazardContext) {
        this.listDbHazardContext = listDbHazardContext;
    }

    public List<DbLocation> getListDbLocation() {
        return listDbLocation;
    }

    public void setListDbLocation(List<DbLocation> listDbLocation) {
        this.listDbLocation = listDbLocation;
    }

    public List<DbhazardActivity> getListDbHazardActivity() {
        return listDbHazardActivity;
    }

    public void setListDbHazardActivity(List<DbhazardActivity> listDbHazardActivity) {
        this.listDbHazardActivity = listDbHazardActivity;
    }

    public List<DbhazardStatus> getListDbHazardStatus() {
        return listDbHazardStatus;
    }

    public void setListDbHazardStatus(List<DbhazardStatus> listDbHazardStatus) {
        this.listDbHazardStatus = listDbHazardStatus;
    }

    public List<DbhazardType> getListDbhazardType() {
        return listDbhazardType;
    }

    public void setListDbhazardType(List<DbhazardType> listDbhazardType) {
        this.listDbhazardType = listDbhazardType;
    }

    public List<DbOwners> getListDbOwners() {
        return listDbOwners;
    }

    public void setListDbOwners(List<DbOwners> listDbOwners) {
        this.listDbOwners = listDbOwners;
    }

    public List<DbriskFrequency> getListDbRiskFrequency() {
        return listDbRiskFrequency;
    }

    public void setListDbRiskFrequency(List<DbriskFrequency> listDbRiskFrequency) {
        this.listDbRiskFrequency = listDbRiskFrequency;
    }

    public List<DbriskSeverity> getListDbRiskSeverity() {
        return listDbRiskSeverity;
    }

    public void setListDbRiskSeverity(List<DbriskSeverity> listDbRiskSeverity) {
        this.listDbRiskSeverity = listDbRiskSeverity;
    }

    public List<DbriskClass> getListDbRiskClass() {
        return listDbRiskClass;
    }

    public void setListDbRiskClass(List<DbriskClass> listDbRiskClass) {
        this.listDbRiskClass = listDbRiskClass;
    }

    public List<DbHazard> getListDbHazard() {
        return listDbHazard;
    }

    public void setListDbHazard(List<DbHazard> listDbHazard) {
        this.listDbHazard = listDbHazard;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getHazardContextId() {
        return hazardContextId;
    }

    public void setHazardContextId(int hazardContextId) {
        this.hazardContextId = hazardContextId;
    }

    public int getFreqId() {
        return freqId;
    }

    public void setFreqId(int freqId) {
        this.freqId = freqId;
    }

    public int getSeverityId() {
        return severityId;
    }

    public void setSeverityId(int severityId) {
        this.severityId = severityId;
    }

    public int getRiskClassId() {
        return riskClassId;
    }

    public void setRiskClassId(int riskClassId) {
        this.riskClassId = riskClassId;
    }
//
//    public boolean isDeleteButton() {
//        return deleteButton;
//    }

//    public void setDeleteButton(boolean deleteButton) {
//        this.deleteButton = deleteButton;
//    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode[] getSelectedNodes() {
        return selectedNodes;
    }

    public void setSelectedNodes(TreeNode[] selectedNodes) {
        this.selectedNodes = selectedNodes;
    }

    public boolean isTreeFlag() {
        return treeFlag;
    }

    public void setTreeFlag(boolean treeFlag) {
        this.treeFlag = treeFlag;
    }

    public boolean isPopFlag() {
        return popFlag;
    }

    public void setPopFlag(boolean popFlag) {
        this.popFlag = popFlag;
    }

    public String getSelectedLegacyId() {
        return selectedLegacyId;
    }

    public void setSelectedLegacyId(String selectedLegacyId) {
        this.selectedLegacyId = selectedLegacyId;
    }

    public Date getSelectedHazardDate() {
        return selectedHazardDate;
    }

    public void setSelectedHazardDate(Date selectedHazardDate) {
        this.selectedHazardDate = selectedHazardDate;
    }

    @PostConstruct
    public void init() {
        listDbHazardActivity = dbhazardActivityFacade.findAll();
        listDbLocation = dbLocationFacade.findAll();
        listDbHazardStatus = dbhazardStatusFacade.findAll();
        listDbhazardType = dbhazardTypeFacade.findAll();
        listDbOwners = dbOwnersFacade.findAll();
        listDbHazardContext = dbhazardContextFacade.findAll();
        listDbRiskFrequency = dbriskFrequencyFacade.findAll();
        listDbRiskSeverity = dbriskSeverityFacade.findAll();
        listDbRiskClass = dbriskClassFacade.findAll();
        redirectedPage();
    }

    public void constructSearchObject() {

        listSearchObject = new ArrayList<>();
        listDbHazard = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        if (!getSelectedHazardId().isEmpty()) {
            listSearchObject.add(new searchObject("hazardId", getSelectedHazardId(), "string", "DbHazard", null, null, null, "like", "Hazard ID"));
        }
        if (getSelectedHazardDate() != null) {
            listSearchObject.add(new searchObject("hazardDate", df.format(getSelectedHazardDate()), "date", "DbHazard", null, null, null, "=", "Hazard Date"));
        }
        if (getSelectedHazardContext() != null) {
            listSearchObject.add(new searchObject("hazardContextId", getSelectedHazardContext(), "int", "DbHazard", "hazardContextId", null, null, "=", "Hazard Context"));
        }
        if ( getSelectedHazardDescription() != null) {
            listSearchObject.add(new searchObject("hazardDescription", getSelectedHazardDescription(), "string", "DbHazard", null, null, null, "like", "Hazard Description"));
        }
        if (getSelectedHazardLocation() != null) {
            listSearchObject.add(new searchObject("locationId", getSelectedHazardLocation(), "int", "DbHazard", "hazardLocation", null, null, "=", "Hazard Location"));
        }
        if (getSelectedHazardActivity() != null) {
            listSearchObject.add(new searchObject("activityId", getSelectedHazardActivity(), "int", "DbHazard", "hazardActivity", null, null, "=", "Hazard Activity"));
        }
        if (getSelectedOwner() != null) {
            listSearchObject.add(new searchObject("ownerId", getSelectedOwner(), "int", "DbHazard", "ownerId", null, null, "=", "Hazard Owner"));
        }
        if (getSelectedHazardType() != null) {
            listSearchObject.add(new searchObject("hazardTypeId", getSelectedHazardType(), "int", "DbHazard", "hazardTypeId", null, null, "=", "Hazard Type"));
        }
        if (getSelectedHazardStatus() != null) {
            listSearchObject.add(new searchObject("hazardStatusId", getSelectedHazardStatus(), "int", "DbHazard", "hazardStatusId", null, null, "=", "Hazard Staus"));
        }
        if (getSelectedRiskClass() != null) {
            listSearchObject.add(new searchObject("riskClassId", getSelectedRiskClass(), "int", "DbHazard", "riskClassId", null, null, "=", "Hazard Risk"));
        }
        if (getSelectedLegacyId() != null) {
            listSearchObject.add(new searchObject("legacyId", getSelectedLegacyId(), "string", "DbHazard", null, null, null, "like", "Legacy Id"));
        }

        listDbHazard = dbHazardFacade.findHazardsByFieldsOnly(listSearchObject);
        editFlag = false;   //Close modifyTable and enable 'deleteButton' when 'Search' is pressed 
//        deleteButton = false;
        treeFlag = false; //Close the SBS tree and set the 'Edit SBS' button to generate tree 
        popFlag = true;
        //init(); //Update search dropdown lists for subsequent searches

    }

    public void editHazardSBS(TreeNode[] nodes) {
        try {
            if (nodes != null && nodes.length > 0) {
                editHazard();
                editSbs(nodes);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Success", "The associated SBS has been successfully edited!"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Please select at least one SBS node"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
        }
    }

    public void showEdit(DbHazard hazardObject) {
        editFlag = true;
//        deleteButton = true;
        this.hazardObject = hazardObject;
        activityId = hazardObject.getHazardActivity().getActivityId();
        locationId = hazardObject.getHazardLocation().getLocationId();
        statusId = hazardObject.getHazardStatusId().getHazardStatusId();
        typeId = hazardObject.getHazardTypeId().getHazardTypeId();
        ownerId = hazardObject.getOwnerId().getOwnerId();
        hazardContextId = hazardObject.getHazardContextId().getHazardContextId();
        freqId = hazardObject.getRiskFrequencyId().getRiskFrequencyId();
        severityId = hazardObject.getRiskSeverityId().getRiskSeverityId();
        riskClassId = hazardObject.getRiskClassId().getRiskClassId();
        treeFlag = false;
        popFlag = true;
    }

    public void editHazard() {
        List<DbriskFrequency> returnedFrequencyList;
        List<DbriskSeverity> returnedSeverityList;
        fillHazardObject();
        hazardObject.setRiskScore(dbriskFrequencyFacade.find(freqId).getFrequencyValue() * dbriskSeverityFacade.find(severityId).getSeverityValue());
        //Setting the audit fields
        DbUser activeUser = (DbUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("activeUser");
        hazardObject.setUpdatedDateTime(new Date());
        hazardObject.setUserIdUpdate(activeUser.getUserId());
        dbHazardFacade.edit(hazardObject);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Success", "The Hazard has been successfully edited!"));
        listDbHazard = dbHazardFacade.findHazardsByFieldsOnly(listSearchObject);//update view of hazards table by performing search again
        editFlag = false;
//        deleteButton = false;
        redirectToRelations();
    }

    public void editSbs(TreeNode[] nodes) {
        dbHazardSbsFacade.removeHazardSbs(hazardObject.getHazardId());
        displaySelectedMultiple(nodes);
        addSBS();
        treeFlag = false;
        popFlag = true;
    }

    public void addSBS() {
        hazardSbsPKObject.setHazardId(hazardObject.getHazardId());
        hazardFKObject.setHazardId(hazardObject.getHazardId());
        hazardSbsObject.setDbHazard(hazardFKObject);
        for (int i = 0; i < treeCheckedNodesList.size(); i++) {
            selectedTreeNodeObject = treeCheckedNodesList.get(i);
            hazardSbsPKObject.setSbsId(selectedTreeNodeObject.getNodeId());
            hazardSbsObject.setDbHazardSbsPK(hazardSbsPKObject);
            dbHazardSbsFacade.create(hazardSbsObject);
        }
    }

//    public void deleteHazard(DbHazard hazardObject) {
//        dbHazardFacade.remove(hazardObject);
//        listDbHazard = dbHazardFacade.findHazardsByFieldsOnly(listSearchObject);    //update view of hazards table by performing search again 
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Success", "The Hazard has been successfully deleted!"));
//    }

    public void closeTree() {
        treeFlag = false;
        popFlag = true; //render button that calls populateTree()
    }

    public void cancel() {
        editFlag = false;
//        deleteButton = false;
        treeFlag = false;
        popFlag = true;
    }

    public void clearSearch() {
        selectedHazardId = null;
        selectedHazardContext = null;
        selectedHazardDescription = null;
        selectedHazardLocation = null;
        selectedHazardActivity = null;
        selectedOwner = null;
        selectedHazardType = null;
        selectedHazardStatus = null;
        selectedRiskClass = null;
        selectedLegacyId = null;
    }

    public void fillHazardObject() {

        activityObject.setActivityId(activityId);
        locationObject.setLocationId(locationId);
        statusObject.setHazardStatusId(statusId);
        typeObject.setHazardTypeId(typeId);
        ownersObject.setOwnerId(ownerId);
        hazardContextObject.setHazardContextId(hazardContextId);
        riskFrequencyObject.setRiskFrequencyId(freqId);
        riskSeverityObject.setRiskSeverityId(severityId);
        riskClassObject.setRiskClassId(riskClassId);

        hazardObject.setHazardActivity(activityObject);
        hazardObject.setHazardLocation(locationObject);
        hazardObject.setHazardStatusId(statusObject);
        hazardObject.setHazardTypeId(typeObject);
        hazardObject.setOwnerId(ownersObject);
        hazardObject.setHazardContextId(hazardContextObject);
        hazardObject.setRiskFrequencyId(riskFrequencyObject);
        hazardObject.setRiskSeverityId(riskSeverityObject);
        hazardObject.setRiskClassId(riskClassObject);
    }

    public String printHazardReview(String hazardReview) {

        switch (hazardReview) {
            case "Y":
                strHazardReview = "Yes";
                break;
            case "N":
                strHazardReview = "No";
                break;
            default:
                strHazardReview = "NULL";
                break;

        }
        return strHazardReview;
    }

    public void displaySelectedMultiple(TreeNode[] nodes) {
        if (nodes != null && nodes.length > 0) {
            treeCheckedNodesList = new ArrayList<>();
            DbtreeLevel1 tmpTreeNode = dbtreeLevel1Facade.findByName(root.getChildren().get(0).toString());
            Integer rootId = tmpTreeNode.getTreeLevel1Index();

            for (TreeNode node : nodes) {
                if (node.getParent().toString().equals("Root")) {
                    //Add the tree Node and include the root index in each child node
                    rootId = tmpTreeNode.getTreeLevel1Index();
                    treeNodeObject tmpNode = new treeNodeObject();
                    tmpNode.setNodeId(rootId.toString() + ".");
                    tmpNode.setNodeName(node.getData().toString());
                    treeCheckedNodesList.add(tmpNode);
                } else {
                    String parts[] = node.getData().toString().split(" ", 2);
                    treeNodeObject tmpNode = new treeNodeObject();
                    tmpNode.setNodeId(rootId.toString() + "." + parts[0]);
                    tmpNode.setNodeName(parts[1]);
                    treeCheckedNodesList.add(tmpNode);
                }
            }
        }
    }

    public void populateTree() {

        treeFlag = true; //render SBS tree
        editHazardId = hazardObject.getHazardId();
        String sbsId;
        int counterLevel4 = 1;
        int counterLevel5 = 1;
        int counterLevel6 = 1;

        
        
        
        List<TreeNode> listTreeNode = new ArrayList<>();

        DbtreeLevel1 tmpResultObjLevel1 = dbtreeLevel1Facade.find(1); //This line might modified to get a dynamic tree
        root = new DefaultTreeNode("Root", null);

        if (!tmpResultObjLevel1.getTreeLevel1Name().isEmpty()) {
            TreeNode nodeLevel1 = new DefaultTreeNode(tmpResultObjLevel1.getTreeLevel1Name(), root);

            sbsId = "1.";
            if (!dbHazardSbsFacade.checkHazardSbs(editHazardId, sbsId).isEmpty()) {
                nodeLevel1.setSelected(true);
                listTreeNode.add(nodeLevel1);
            }

            List<DbtreeLevel2> tmpListLevel2
                    = dbtreeLevel2Facade.findByLevel1Id(tmpResultObjLevel1.getTreeLevel1Id());
            if (!tmpListLevel2.isEmpty()) {
                tmpListLevel2.sort(Comparator.comparingInt(DbtreeLevel2::getTreeLevel2Index));
                for (DbtreeLevel2 tmpGOLevel2 : tmpListLevel2) {
                    Integer level2No = tmpGOLevel2.getTreeLevel2Index();
                    String levelLabel2 = level2No.toString() + ".";
                    TreeNode nodeLevel2 = new DefaultTreeNode(levelLabel2 + " "
                            + tmpGOLevel2.getTreeLevel2Name(), nodeLevel1);

                    sbsId = "1." + nodeLevel2.getData().toString().substring(0, 2);
                    if (!dbHazardSbsFacade.checkHazardSbs(editHazardId, sbsId).isEmpty()) {
                        nodeLevel2.setSelected(true);
                        listTreeNode.add(nodeLevel2);
                    }

                    List<DbtreeLevel3> tmpListLevel3
                            = dbtreeLevel3Facade.findByLevel2Id(tmpGOLevel2.getDbtreeLevel2PK().getTreeLevel2Id());
                    if (!tmpListLevel3.isEmpty()) {
                        tmpListLevel3.sort(Comparator.comparingInt(DbtreeLevel3::getTreeLevel3Index));
                        for (DbtreeLevel3 tmpGOLevel3 : tmpListLevel3) {
                            Integer level3No = tmpGOLevel3.getTreeLevel3Index();
                            String levelLabel3 = levelLabel2 + level3No.toString() + ".";
                            TreeNode nodeLevel3 = new DefaultTreeNode(levelLabel3 + " "
                                    + tmpGOLevel3.getTreeLevel3Name(), nodeLevel2);

                            sbsId = "1." + nodeLevel3.getData().toString().substring(0, 4);
                            if (!dbHazardSbsFacade.checkHazardSbs(editHazardId, sbsId).isEmpty()) {
                                nodeLevel3.setSelected(true);
                                listTreeNode.add(nodeLevel3);
                            }

                            List<DbtreeLevel4> tmpListLevel4
                                    = dbtreeLevel4Facade.findByLevel3Id(tmpGOLevel3.getDbtreeLevel3PK().getTreeLevel3Id());
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
                                    if (!dbHazardSbsFacade.checkHazardSbs(editHazardId, sbsId).isEmpty()) {
                                        nodeLevel4.setSelected(true);
                                        listTreeNode.add(nodeLevel4);
                                    }

                                    List<DbtreeLevel5> tmpListLevel5
                                            = dbtreeLevel5Facade.findByLevel4Id(tmpGOLevel4.getDbtreeLevel4PK().getTreeLevel4Id());
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
                                            if (!dbHazardSbsFacade.checkHazardSbs(editHazardId, sbsId).isEmpty()) {
                                                nodeLevel5.setSelected(true);
                                                listTreeNode.add(nodeLevel5);
                                            }

                                            List<DbtreeLevel6> tmpListLevel6
                                                    = dbtreeLevel6Facade.findByLevel5Id(tmpGOLevel5.getDbtreeLevel5PK().getTreeLevel5Id());
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
                                                    if (!dbHazardSbsFacade.checkHazardSbs(editHazardId, sbsId).isEmpty()) {
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
        selectedNodes = listTreeNode.toArray(new TreeNode[listTreeNode.size()]);
        popFlag = false; //command button that calls this function is no longer rendered 
    }

    //This method controls the behaviour when the hazard relations has been called due to a redirection page from add or edit hazard.
    private void redirectedPage() {
        DbHazard initialHazard = (DbHazard) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("hazardRelObj");
        if (initialHazard != null) {
            setSelectedHazardId(initialHazard.getHazardId());
            constructSearchObject();
            showEdit(initialHazard);
            redirectionSource = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("redirectionSource");
        }
    }

    //Reditects to the hazard relations whenever the intial call came from a workflow process.
    private String redirectToRelations() {
        if (redirectionSource != null) {
            if (redirectionSource.equals("EditHazard")) {
                try {
                    return "./../../data/relations/hazardsRelation.xhtml";
                    //FacesContext.getCurrentInstance().getExternalContext().redirect("./../../data/relations/hazardsRelation.xhtml");
                //} catch (IOException ex) {
                } catch (Exception ex) {
                    Logger.getLogger(hazardsRelation_MB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    public Date todaysDate() {
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }
}
