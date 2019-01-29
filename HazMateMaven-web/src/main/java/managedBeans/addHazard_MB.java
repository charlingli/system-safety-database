/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import customObjects.fileHeaderObject;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;

import customObjects.treeNodeObject;
import ejb.DbFilesFacadeLocal;
import ejb.DbHazardFacadeLocal;
import ejb.DbHazardFilesFacadeLocal;
import ejb.DbHazardSbsFacadeLocal;
import ejb.DbLocationFacadeLocal;
import ejb.DbOwnersFacadeLocal;
import ejb.DbglobalIdFacadeLocal;
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
import entities.DbFiles;
import entities.DbHazard;
import entities.DbHazardFiles;
import entities.DbHazardFilesPK;
import entities.DbHazardSbs;
import entities.DbHazardSbsPK;
import entities.DbLocation;
import entities.DbOwners;
import entities.DbUser;
import entities.DbhazardActivity;
import entities.DbhazardContext;
import entities.DbhazardStatus;
import entities.DbhazardSystemStatus;
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
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import static org.apache.commons.io.IOUtils.toByteArray;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author alan8 & David Ortega
 */
@Named(value = "addHazard_MB")
@ViewScoped
public class addHazard_MB implements Serializable {

    @EJB
    private DbHazardFilesFacadeLocal dbHazardFilesFacade;
    @EJB
    private DbFilesFacadeLocal dbFilesFacade;
    @EJB
    private DbglobalIdFacadeLocal dbglobalIdFacade;
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

    private DbHazard hazardObject;
    private DbHazard savedHazardObject;

    private int activityId;
    private int locationId;
    private int statusId;
    private int typeId;
    private int ownerId;
    private int hazardContextId;
    private int currentFreqId;
    private int currentSeverityId;
    private int targetFreqId;
    private int targetSeverityId;
    private int riskClassId;

    /*Variables relevant to SBS tree*/
    private TreeNode root;
    private TreeNode[] selectedNodes;
    private List<treeNodeObject> treeCheckedNodesList;
    private List<treeNodeObject> savedCheckedNodesList;

    /*Variables relevant to files*/
    private List<fileHeaderObject> listFiles;
    private List<fileHeaderObject> checkedFiles;
    private List<fileHeaderObject> savedFiles;
    private boolean filesChanged;
    private String uploadName;
    private String uploadDescription;

    public addHazard_MB() {
    }

    public DbHazard getHazardObject() {
        return hazardObject;
    }

    public void setHazardObject(DbHazard hazardObject) {
        this.hazardObject = hazardObject;
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

    public int getCurrentFreqId() {
        return currentFreqId;
    }

    public void setCurrentFreqId(int currentFreqId) {
        this.currentFreqId = currentFreqId;
    }

    public int getCurrentSeverityId() {
        return currentSeverityId;
    }

    public void setCurrentSeverityId(int curentSeverityId) {
        this.currentSeverityId = curentSeverityId;
    }
    
    public int getTargetFreqId() {
        return targetFreqId;
    }

    public void setTargetFreqId(int freqId) {
        this.targetFreqId = freqId;
    }

    public int getTargetSeverityId() {
        return targetSeverityId;
    }

    public void setTargetSeverityId(int severityId) {
        this.targetSeverityId = severityId;
    }

    public int getRiskClassId() {
        return riskClassId;
    }

    public void setRiskClassId(int riskClassId) {
        this.riskClassId = riskClassId;
    }

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

    public List<fileHeaderObject> getListFiles() {
        return listFiles;
    }

    public void setListFiles(List<fileHeaderObject> listFiles) {
        this.listFiles = listFiles;
    }

    public List<fileHeaderObject> getCheckedFiles() {
        return checkedFiles;
    }

    public void setCheckedFiles(List<fileHeaderObject> checkedFiles) {
        this.checkedFiles = checkedFiles;
    }

    public String getUploadName() {
        return uploadName;
    }

    public void setUploadName(String uploadName) {
        this.uploadName = uploadName;
    }

    public String getUploadDescription() {
        return uploadDescription;
    }

    public void setUploadDescription(String uploadDescription) {
        this.uploadDescription = uploadDescription;
    }

    @PostConstruct
    public void init() {
        createTree();
        hazardObject = new DbHazard();
        savedHazardObject = new DbHazard();
        listDbHazardActivity = dbhazardActivityFacade.findAll();
        listDbLocation = dbLocationFacade.findAll();
        listDbHazardStatus = dbhazardStatusFacade.findAll();
        listDbhazardType = dbhazardTypeFacade.findAll();
        listDbOwners = dbOwnersFacade.findAll();
        listDbHazardContext = dbhazardContextFacade.findAll();
        listDbRiskFrequency = dbriskFrequencyFacade.findAll();
        listDbRiskSeverity = dbriskSeverityFacade.findAll();
        listDbRiskClass = dbriskClassFacade.findAll();
        listFiles = dbFilesFacade.listAllHeaders();
        savedFiles = new ArrayList<>();
        checkedFiles = new ArrayList<>();

    }

    @PreDestroy
    public void validateCompletion() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The hazard has been saved. Please assign causes, consequences, and controls."));
    }
    
    //Processing the complete page, when the hazard is new it will be created otherwise it will be edited.
    public void processPage() {
        if (selectedNodes != null && selectedNodes.length > 0) {
            try {
                if (hazardObject.getHazardId() == null) {
                    addHazard();
                    addSBS();
                    addFiles();
                } else {
                    fillHazardObject();
                    displaySelectedMultiple1(selectedNodes);
                    addFiles();
                    if (hazardObject.equalsContent(savedHazardObject) && compareLists(treeCheckedNodesList, savedCheckedNodesList)) {
                        //The content of both is the SAME
                        if (filesChanged) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Files have been linked to the hazard."));
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "No changes have been made."));
                        }
                    } else if (!hazardObject.equalsContent(savedHazardObject) && compareLists(treeCheckedNodesList, savedCheckedNodesList)) {
                        //The content of the object CHANGED but the tree is still the SAME
                        dbHazardFacade.edit(hazardObject);
                        saveHazardObject(hazardObject);
                        if (filesChanged) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The hazard has been edited and files have been linked successfully."));
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The hazard has been edited successfully."));
                        }
                    } else if (hazardObject.equalsContent(savedHazardObject) && !compareLists(treeCheckedNodesList, savedCheckedNodesList)) {
                        //The content of the object is the SAME but the tree CHANGED
                        dbHazardSbsFacade.removeHazardSbs(hazardObject.getHazardId());
                        addSBS();
                        savedCheckedNodesList = treeCheckedNodesList;
                        if (filesChanged) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The sbs tree has been edited and files have been linked successfully."));
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The sbs tree has been edited successfully."));
                        }
                    } else if (!hazardObject.equalsContent(savedHazardObject) && !compareLists(treeCheckedNodesList, savedCheckedNodesList)) {
                        //Both Changed
                        dbHazardFacade.edit(hazardObject);
                        dbHazardSbsFacade.removeHazardSbs(hazardObject.getHazardId());
                        addSBS();
                        saveHazardObject(hazardObject);
                        savedCheckedNodesList = treeCheckedNodesList;
                        if (filesChanged) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The hazard object and sbs tree have been edited, and files have been linked successfully."));
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The hazard object and sbs tree have been edited successfully."));
                        }
                    }
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", e.getMessage()));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Please select at least one SBS node!"));
        }
    }

    //This method populates the Hazrd object, creates the consecutive Id and persist the object in the database.
    public void addHazard() {
        //Setting objects into the hazard object.
        fillHazardObject();

        //Getting the next available consecutive
        List<DbLocation> returnedLocationList = dbLocationFacade.getLocationAbbrev(hazardObject.getHazardLocation().getLocationId());
        String key1 = returnedLocationList.get(0).getProjectId().getProjectAbbrev();
        String key2 = returnedLocationList.get(0).getLocationAbbrev();
        hazardObject.setHazardId(dbglobalIdFacade.nextConsecutive(key1, key2, "-", 4).getAnswerString());

        //Calculating the current and target risk score and persisting the object in the database.
        hazardObject.setRiskCurrentScore(dbHazardFacade.calculateRiskScore(dbriskFrequencyFacade.find(currentFreqId).getFrequencyValue(), dbriskSeverityFacade.find(currentSeverityId).getSeverityValue()));
        hazardObject.setRiskTargetScore(dbHazardFacade.calculateRiskScore(dbriskFrequencyFacade.find(targetFreqId).getFrequencyValue(), dbriskSeverityFacade.find(targetSeverityId).getSeverityValue()));

        //Setting the audit fields
        DbUser activeUser = (DbUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("activeUser");
        hazardObject.setAddedDateTime(new Date());
        hazardObject.setUserIdAdd(activeUser.getUserId());

        //Updating the system status on pending
        hazardObject.setHazardSystemStatus(new DbhazardSystemStatus(1));

        dbHazardFacade.create(hazardObject);
        saveHazardObject(hazardObject);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The hazard " + hazardObject.getHazardId() + " has been sucessfully added! You may edit it further or move to assign causes, consequences, and controls."));
    }

    //Filling and asociating all the related objects into the Hazard Object.
    public void fillHazardObject() {
        hazardObject.setHazardActivity(new DbhazardActivity(activityId));
        hazardObject.setHazardLocation(new DbLocation(locationId));
        hazardObject.setHazardStatusId(new DbhazardStatus(statusId));
        hazardObject.setHazardTypeId(new DbhazardType(typeId));
        hazardObject.setOwnerId(new DbOwners(ownerId));
        hazardObject.setHazardContextId(new DbhazardContext(hazardContextId));
        hazardObject.setRiskTargetFrequencyId(new DbriskFrequency(targetFreqId));
        hazardObject.setRiskTargetSeverityId(new DbriskSeverity(targetSeverityId));
        hazardObject.setRiskCurrentFrequencyId(new DbriskFrequency(currentFreqId));
        hazardObject.setRiskCurrentSeverityId(new DbriskSeverity(currentSeverityId));
        hazardObject.setRiskClassId(new DbriskClass(riskClassId));
    }

    //Creating the relations between the hazard and the Sbs nodes.
    public void addSBS() {
        displaySelectedMultiple1(selectedNodes);
        DbHazardSbsPK hazardSbsPKObject = new DbHazardSbsPK();
        DbHazardSbs hazardSbsObject = new DbHazardSbs();
        DbHazard hazardFKObject = new DbHazard();

        hazardSbsPKObject.setHazardId(hazardObject.getHazardId());
        hazardFKObject.setHazardId(hazardObject.getHazardId());
        hazardSbsObject.setDbHazard(hazardFKObject);

        for (int i = 0; i < treeCheckedNodesList.size(); i++) {
            hazardSbsPKObject.setSbsId(treeCheckedNodesList.get(i).getNodeId());
            hazardSbsObject.setDbHazardSbsPK(hazardSbsPKObject);
            dbHazardSbsFacade.create(hazardSbsObject);
        }
        savedCheckedNodesList = treeCheckedNodesList;
    }

    public void displaySelectedMultiple1(TreeNode[] nodes) {
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
                    tmpNode.setRowKey(node.getRowKey());
                    treeCheckedNodesList.add(tmpNode);
                } else {
                    String parts[] = node.getData().toString().split(" ", 2);
                    treeNodeObject tmpNode = new treeNodeObject();
                    tmpNode.setNodeId(rootId.toString() + "." + parts[0]);
                    tmpNode.setNodeName(parts[1]);
                    tmpNode.setRowKey(node.getRowKey());
                    treeCheckedNodesList.add(tmpNode);
                }
            }
        }
    }

    public void reinitialize() {
        hazardObject = new DbHazard();

        activityId = -1;
        locationId = -1;
        statusId = -1;
        typeId = -1;
        ownerId = -1;
        hazardContextId = -1;
        currentFreqId = -1;
        currentSeverityId = -1;
        targetFreqId = -1;
        targetSeverityId = -1;
        riskClassId = -1;

        clearTree();
        collapsingOrExpanding(root, false);
    }

    public String assignRelations() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("hazardRelObj", hazardObject);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("redirectionSource", "AddHazard");
        return "/data/relations/hazardsRelation";
    }

    public void clearTree() {

        List<TreeNode> listChildrenL1;
        List<TreeNode> listChildrenL2;
        List<TreeNode> listChildrenL3;
        List<TreeNode> listChildrenL4;
        List<TreeNode> listChildrenL5;
        List<TreeNode> listChildrenL6;

        selectedNodes = null;
        listChildrenL1 = root.getChildren();

        for (TreeNode tmpTreeNodeL1 : listChildrenL1) {
            tmpTreeNodeL1.setSelected(false);
            listChildrenL2 = tmpTreeNodeL1.getChildren(); //new list of children
            if (!listChildrenL2.isEmpty()) {
                for (TreeNode tmpTreeNodeL2 : listChildrenL2) {
                    tmpTreeNodeL2.setSelected(false); //set selected to false within children
                    listChildrenL3 = tmpTreeNodeL2.getChildren();
                    if (!listChildrenL3.isEmpty()) {
                        for (TreeNode tmpTreeNodeL3 : listChildrenL3) {
                            tmpTreeNodeL3.setSelected(false);
                            listChildrenL4 = tmpTreeNodeL3.getChildren();
                            if (!listChildrenL4.isEmpty()) {
                                for (TreeNode tmpTreeNodeL4 : listChildrenL4) {
                                    tmpTreeNodeL4.setSelected(false);
                                    listChildrenL5 = tmpTreeNodeL4.getChildren();
                                    if (!listChildrenL5.isEmpty()) {
                                        for (TreeNode tmpTreeNodeL5 : listChildrenL5) {
                                            tmpTreeNodeL5.setSelected(false);
                                            listChildrenL6 = tmpTreeNodeL5.getChildren();
                                            if (!listChildrenL6.isEmpty()) {
                                                for (TreeNode tmpTreeNodeL6 : listChildrenL6) {
                                                    tmpTreeNodeL6.setSelected(false);
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

    public void createTree() {
        DbtreeLevel1 tmpResultObjLevel1 = dbtreeLevel1Facade.find(1); //This line might modified to get a dynamic tree
        root = new DefaultTreeNode("Root", null);
        if (!tmpResultObjLevel1.getTreeLevel1Name().isEmpty()) {
            TreeNode nodeLevel1 = new DefaultTreeNode(tmpResultObjLevel1.getTreeLevel1Name(), root);
            List<DbtreeLevel2> tmpListLevel2
                    = dbtreeLevel2Facade.findByLevel1Id(tmpResultObjLevel1.getTreeLevel1Id());
            if (!tmpListLevel2.isEmpty()) {
                tmpListLevel2.sort(Comparator.comparingInt(DbtreeLevel2::getTreeLevel2Index));
                for (DbtreeLevel2 tmpGOLevel2 : tmpListLevel2) {
                    Integer level2No = tmpGOLevel2.getTreeLevel2Index();
                    String levelLabel2 = level2No.toString() + ".";
                    TreeNode nodeLevel2 = new DefaultTreeNode(levelLabel2 + " "
                            + tmpGOLevel2.getTreeLevel2Name(), nodeLevel1);
                    List<DbtreeLevel3> tmpListLevel3
                            = dbtreeLevel3Facade.findByLevel2Id(tmpGOLevel2.getDbtreeLevel2PK().getTreeLevel2Id());
                    if (!tmpListLevel3.isEmpty()) {
                        tmpListLevel3.sort(Comparator.comparingInt(DbtreeLevel3::getTreeLevel3Index));
                        for (DbtreeLevel3 tmpGOLevel3 : tmpListLevel3) {
                            Integer level3No = tmpGOLevel3.getTreeLevel3Index();
                            String levelLabel3 = levelLabel2 + level3No.toString() + ".";
                            TreeNode nodeLevel3 = new DefaultTreeNode(levelLabel3 + " "
                                    + tmpGOLevel3.getTreeLevel3Name(), nodeLevel2);
                            List<DbtreeLevel4> tmpListLevel4
                                    = dbtreeLevel4Facade.findByLevel3Id(tmpGOLevel3.getDbtreeLevel3PK().getTreeLevel3Id());
                            if (!tmpListLevel4.isEmpty()) {
                                tmpListLevel4.sort(Comparator.comparingInt(DbtreeLevel4::getTreeLevel4Index));
                                for (DbtreeLevel4 tmpGOLevel4 : tmpListLevel4) {
                                    Integer level4No = tmpGOLevel4.getTreeLevel4Index();
                                    String levelLabel4 = levelLabel3 + level4No.toString() + ".";
                                    TreeNode nodeLevel4 = new DefaultTreeNode(levelLabel4 + " "
                                            + tmpGOLevel4.getTreeLevel4Name(), nodeLevel3);
                                    List<DbtreeLevel5> tmpListLevel5
                                            = dbtreeLevel5Facade.findByLevel4Id(tmpGOLevel4.getDbtreeLevel4PK().getTreeLevel4Id());
                                    if (!tmpListLevel5.isEmpty()) {
                                        tmpListLevel5.sort(Comparator.comparingInt(DbtreeLevel5::getTreeLevel5Index));
                                        for (DbtreeLevel5 tmpGOLevel5 : tmpListLevel5) {
                                            Integer level5No = tmpGOLevel5.getTreeLevel5Index();
                                            String levelLabel5 = levelLabel4 + level5No.toString() + ".";
                                            TreeNode nodeLevel5 = new DefaultTreeNode(levelLabel5 + " "
                                                    + tmpGOLevel5.getTreeLevel5Name(), nodeLevel4);
                                            List<DbtreeLevel6> tmpListLevel6
                                                    = dbtreeLevel6Facade.findByLevel5Id(tmpGOLevel5.getDbtreeLevel5PK().getTreeLevel5Id());
                                            if (!tmpListLevel6.isEmpty()) {
                                                tmpListLevel6.sort(Comparator.comparingInt(DbtreeLevel6::getTreeLevel6Index));
                                                for (DbtreeLevel6 tmpGOLevel6 : tmpListLevel6) {
                                                    Integer level6No = tmpGOLevel6.getTreeLevel6Index();
                                                    String levelLabel6 = levelLabel5 + level6No.toString() + ".";
                                                    TreeNode nodeLevel6 = new DefaultTreeNode(levelLabel6 + " "
                                                            + tmpGOLevel6.getTreeLevel6Name(), nodeLevel5);
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

    public Date todaysDate() {
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }

    private void saveHazardObject(DbHazard inputObj) {
        savedHazardObject.setHazardId(inputObj.getHazardId());
        savedHazardObject.setHazardContextId(inputObj.getHazardContextId());
        savedHazardObject.setHazardDescription(inputObj.getHazardDescription());
        savedHazardObject.setHazardLocation(inputObj.getHazardLocation());
        savedHazardObject.setHazardActivity(inputObj.getHazardActivity());
        savedHazardObject.setOwnerId(inputObj.getOwnerId());
        savedHazardObject.setHazardTypeId(inputObj.getHazardTypeId());
        savedHazardObject.setHazardStatusId(inputObj.getHazardStatusId());
        savedHazardObject.setRiskClassId(inputObj.getRiskClassId());
        savedHazardObject.setRiskCurrentFrequencyId(inputObj.getRiskCurrentFrequencyId());
        savedHazardObject.setRiskCurrentSeverityId(inputObj.getRiskCurrentSeverityId());
        savedHazardObject.setRiskCurrentScore(inputObj.getRiskCurrentScore());
        savedHazardObject.setRiskTargetFrequencyId(inputObj.getRiskTargetFrequencyId());
        savedHazardObject.setRiskTargetSeverityId(inputObj.getRiskTargetSeverityId());
        savedHazardObject.setRiskTargetScore(inputObj.getRiskTargetScore());
        savedHazardObject.setHazardComment(inputObj.getHazardComment());
        savedHazardObject.setHazardDate(inputObj.getHazardDate());
        savedHazardObject.setHazardWorkshop(inputObj.getHazardWorkshop());
        savedHazardObject.setHazardReview(inputObj.getHazardReview());
        savedHazardObject.setLegacyId(inputObj.getLegacyId());
        savedHazardObject.setAddedDateTime(inputObj.getAddedDateTime());
        savedHazardObject.setUpdatedDateTime(inputObj.getUpdatedDateTime());
        savedHazardObject.setUserIdAdd(inputObj.getUserIdAdd());
        savedHazardObject.setUserIdUpdate(inputObj.getUserIdUpdate());
    }

    public boolean compareLists(List<treeNodeObject> leftList, List<treeNodeObject> rightList) {
        leftList.sort(Comparator.comparing(treeNodeObject::getRowKey));
        rightList.sort(Comparator.comparing(treeNodeObject::getRowKey));
        if (leftList.size() != rightList.size()) {
            return false;
        } else {
            for (int i = 0; i < leftList.size(); i++) {
                if (!leftList.get(i).getNodeId().equals(rightList.get(i).getNodeId())) {
                    return false;
                }
            }
        }
        return true;
    }

    public void collapsingOrExpanding(TreeNode n, boolean option) {
        if (n.getChildren().size() == 0) {
            n.setSelected(false);
        } else {
            for (TreeNode s : n.getChildren()) {
                collapsingOrExpanding(s, option);
            }
            n.setExpanded(option);
            n.setSelected(false);
        }
    }

    public void handleUpload(FileUploadEvent event) {
        try {
            UploadedFile rawFile = event.getFile();
            InputStream fileStream = rawFile.getInputstream();
            String[] rawName = rawFile.getFileName().split("\\.");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < rawName.length - 1; i++) {
                sb.append(rawName[i]);
            }
            String fileName = sb.toString();
            String fileExtension = rawName[rawName.length - 1];

            if (uploadName.length() > 0) {
                fileName = uploadName;
            }

            if (dbFilesFacade.findHeadersForDuplicate(fileName, fileExtension).size() >= 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "'" + fileName + "." + fileExtension + "' already exists in the database!"));
            } else {
                DbFiles newFile = new DbFiles();
                newFile.setFileName(fileName);
                newFile.setFileExtension(fileExtension);
                newFile.setFileSize(rawFile.getContents().length);
                newFile.setFileBlob(toByteArray(fileStream));
                newFile.setFileDescription(uploadDescription);
                dbFilesFacade.create(newFile);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "'" + fileName + "." + fileExtension + "' has been successfully uploaded."));
            }

            listFiles = (List<fileHeaderObject>) (Object) dbFilesFacade.listAllHeaders();
            init();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", e.getMessage()));
        }
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
        RequestContext.getCurrentInstance().execute("PF('fileNameOverlay').hide();");
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

    private void addFiles() {
        filesChanged = false;
        if (!checkedFiles.containsAll(savedFiles) || !savedFiles.containsAll(checkedFiles)) {
            filesChanged = true;
            if (checkedFiles.isEmpty() && !savedFiles.isEmpty()) {
                for (fileHeaderObject tmpFile: savedFiles) {
                    unlinkFile(tmpFile);
                }
            } else if (!checkedFiles.isEmpty() && savedFiles.isEmpty()) {
                for (fileHeaderObject tmpFile: checkedFiles) {
                    linkFile(tmpFile);
                }
            } else if (!checkedFiles.isEmpty() && !savedFiles.isEmpty()) {
                for (fileHeaderObject tmpFile: savedFiles) { 
                    if (!checkedFiles.contains(tmpFile)) {
                        unlinkFile(tmpFile);
                    }
                }
                for (fileHeaderObject tmpFile : checkedFiles) {
                    if (!savedFiles.contains(tmpFile)) {
                        linkFile(tmpFile);
                    }
                }
            }
            savedFiles = dbHazardFilesFacade.findHeadersForHazard(hazardObject.getHazardId());
        }
    }

    private void linkFile(fileHeaderObject fileHeader) {
        DbHazardFiles tmpHazardFiles = new DbHazardFiles();
        DbHazardFilesPK tmpHazardFilesPK = new DbHazardFilesPK(hazardObject.getHazardId(), fileHeader.getFileId());
        tmpHazardFiles.setDbHazardFilesPK(tmpHazardFilesPK);
        tmpHazardFiles.setDbHazardFilesDummyvar(null);
        dbHazardFilesFacade.create(tmpHazardFiles);
    }

    private void unlinkFile(fileHeaderObject fileHeader) {
        dbHazardFilesFacade.customRemove(hazardObject.getHazardId(), fileHeader.getFileId());
    }
}
