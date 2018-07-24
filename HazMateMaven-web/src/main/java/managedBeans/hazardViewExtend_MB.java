/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import customObjects.treeNodeObject;
import ejb.DbHazardFacadeLocal;
import ejb.DbLocationFacadeLocal;
import ejb.DbOwnersFacadeLocal;
import ejb.DbProjectFacadeLocal;
import ejb.DbchangeTypeFacadeLocal;
import ejb.DbconstructionTypeFacadeLocal;
import ejb.DbcontrolHierarchyFacadeLocal;
import ejb.DbcontrolRecommendFacadeLocal;
import ejb.DbgradeSeparationFacadeLocal;
import ejb.DbhazardActivityFacadeLocal;
import ejb.DbhazardContextFacadeLocal;
import ejb.DbhazardStatusFacadeLocal;
import ejb.DbhazardTypeFacadeLocal;
import ejb.DbriskClassFacadeLocal;
import ejb.DbtreeLevel1FacadeLocal;
import ejb.DbuserPreferencesFacadeLocal;
import entities.DbCause;
import entities.DbConsequence;
import entities.DbControl;
import entities.DbControlHazard;
import entities.DbHazard;
import entities.DbHazardCause;
import entities.DbHazardConsequence;
import entities.DbHazardSbs;
import entities.DbLocation;
import entities.DbOwners;
import entities.DbProject;
import entities.DbUser;
import entities.DbchangeType;
import entities.DbconstructionType;
import entities.DbcontrolHierarchy;
import entities.DbcontrolRecommend;
import entities.DbgradeSeparation;
import entities.DbhazardActivity;
import entities.DbhazardContext;
import entities.DbhazardStatus;
import entities.DbhazardType;
import entities.DbriskClass;
import entities.DbtreeLevel1;
import entities.DbtreeLevel2;
import entities.DbtreeLevel3;
import entities.DbtreeLevel4;
import entities.DbtreeLevel5;
import entities.DbtreeLevel6;
import entities.DbuserPreferences;
import entities.DbuserPreferencesPK;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 *
 * @author lxra
 */
@Named(value = "hazardViewExtend_MB")
@ViewScoped
public class hazardViewExtend_MB implements Serializable {

    @EJB
    private DbHazardFacadeLocal dbHazardFacade;
    @EJB
    private DbtreeLevel1FacadeLocal dbtreeLevel1Facade;
    @EJB
    private DbProjectFacadeLocal dbProjectFacade;
    @EJB
    private DbOwnersFacadeLocal dbOwnersFacade;
    @EJB
    private DbLocationFacadeLocal dbLocationFacade;
    @EJB
    private DbchangeTypeFacadeLocal dbchangeTypeFacade;
    @EJB
    private DbconstructionTypeFacadeLocal dbconstructionTypeFacade;
    @EJB
    private DbgradeSeparationFacadeLocal dbgradeSeparationFacade;
    @EJB
    private DbcontrolHierarchyFacadeLocal dbcontrolHierarchyFacade;
    @EJB
    private DbcontrolRecommendFacadeLocal dbcontrolRecommendFacade;
    @EJB
    private DbhazardActivityFacadeLocal dbhazardActivityFacade;
    @EJB
    private DbhazardContextFacadeLocal dbhazardContextFacade;
    @EJB
    private DbhazardStatusFacadeLocal dbhazardStatusFacade;
    @EJB
    private DbhazardTypeFacadeLocal dbhazardTypeFacade;
    @EJB
    private DbriskClassFacadeLocal dbriskClassFacade;
    
    @ManagedProperty("#{hazard.list}")
    private List<DbHazard> hazardSearchedList;
    private List<treeNodeObject> treeHazardSbsList;
    private List<DbHazardSbs> listDbHazardSbs;
    private List<DbHazardCause> listDbHazardCause;
    private DbHazardCause hazardCauseObject = new DbHazardCause();
    private List<String> causeDescription;
    private DbCause causeObject = new DbCause();
    private List<DbHazardConsequence> listDbHazardConsequence;
    private DbHazardConsequence hazardConsequenceObject = new DbHazardConsequence();
    private List<String> consequenceDescription;
    private DbConsequence consequenceObject = new DbConsequence();
    private List<DbControlHazard> listDbControlHazard;
    private List<DbControl> listDbControl;
    private List<String> currentControls;
    private List<String> controlType;
    private List<String> controlHierarchy; //Otherwise known as control hierarchy names
    private List<String> controlOwner;
    private List<String> controlRecommend;
    private List<String> controlJustify;
    private DbControlHazard controlHazardObject = new DbControlHazard();
    private DbControl controlObject = new DbControl();
    private DbcontrolHierarchy controlHierObject = new DbcontrolHierarchy();
    private List<DbProject> listProjects;
    private List<DbLocation> listLocations;
    private List<DbgradeSeparation> listGradeSeparations;
    private List<DbconstructionType> listConstructionTypes;
    private List<DbchangeType> listChangeTypes;
    private List<DbhazardActivity> listHazardActivities;
    private List<DbhazardContext> listHazardContexts;
    private List<DbhazardType> listHazardTypes;
    private List<DbOwners> listHazardOwners;
    private List<DbriskClass> listRiskClasses;
    private List<DbcontrolHierarchy> listControlHierarchies;
    private List<DbcontrolRecommend> listControlRecommendations;
    private List<String> listControlTypes;
    private List<DbhazardStatus> listHazardStatuses;
    private List<Boolean> toggleColumns;
    private List<DbuserPreferences> userPreferences;
    private DbuserPreferences currentPreferences = new DbuserPreferences();
    private DbuserPreferencesPK currentPreferencesPK = new DbuserPreferencesPK();
    private DbUser activeUser = new DbUser();
    private String activeUserName;
    private String nHazards;
    
    public hazardViewExtend_MB() {
    }

    public List<DbHazard> getHazardSearchedList() {
        return hazardSearchedList;
    }

    public void setHazardSearchedList(List<DbHazard> hazardSearchedList) {
        this.hazardSearchedList = hazardSearchedList;
    }

    public List<treeNodeObject> getTreeHazardSbsList() {
        return treeHazardSbsList;
    }

    public void setTreeHazardSbsList(List<treeNodeObject> treeHazardSbsList) {
        this.treeHazardSbsList = treeHazardSbsList;
    }

    public List<DbHazardSbs> getListDbHazardSbs() {
        return listDbHazardSbs;
    }

    public void setListDbHazardSbs(List<DbHazardSbs> listDbHazardSbs) {
        this.listDbHazardSbs = listDbHazardSbs;
    }

    public List<DbHazardCause> getListDbHazardCause() {
        return listDbHazardCause;
    }

    public void setListDbHazardCause(List<DbHazardCause> listDbHazardCause) {
        this.listDbHazardCause = listDbHazardCause;
    }

    public DbHazardCause getHazardCauseObject() {
        return hazardCauseObject;
    }

    public void setHazardCauseObject(DbHazardCause hazardCauseObject) {
        this.hazardCauseObject = hazardCauseObject;
    }

    public List<String> getCauseDescription() {
        return causeDescription;
    }

    public void setCauseDescription(List<String> causeDescription) {
        this.causeDescription = causeDescription;
    }

    public List<DbHazardConsequence> getListDbHazardConsequence() {
        return listDbHazardConsequence;
    }

    public void setListDbHazardConsequence(List<DbHazardConsequence> listDbHazardConsequence) {
        this.listDbHazardConsequence = listDbHazardConsequence;
    }

    public DbHazardConsequence getHazardConsequenceObject() {
        return hazardConsequenceObject;
    }

    public void setHazardConsequenceObject(DbHazardConsequence hazardConsequenceObject) {
        this.hazardConsequenceObject = hazardConsequenceObject;
    }

    public List<String> getConsequenceDescription() {
        return consequenceDescription;
    }

    public void setConsequenceDescription(List<String> consequenceDescription) {
        this.consequenceDescription = consequenceDescription;
    }

    public List<String> getCurrentControls() {
        return currentControls;
    }

    public void setCurrentControls(List<String> currentControls) {
        this.currentControls = currentControls;
    }

    public List<String> getControlType() {
        return controlType;
    }

    public void setControlType(List<String> controlType) {
        this.controlType = controlType;
    }

    public List<String> getControlHierarchy() {
        return controlHierarchy;
    }

    public void setControlHierarchy(List<String> controlHierarchy) {
        this.controlHierarchy = controlHierarchy;
    }

    public List<String> getControlOwner() {
        return controlOwner;
    }

    public void setControlOwner(List<String> controlOwner) {
        this.controlOwner = controlOwner;
    }

    public List<String> getControlRecommend() {
        return controlRecommend;
    }

    public void setControlRecommend(List<String> controlRecommend) {
        this.controlRecommend = controlRecommend;
    }

    public List<String> getControlJustify() {
        return controlJustify;
    }

    public void setControlJustify(List<String> controlJustify) {
        this.controlJustify = controlJustify;
    }

    public DbControl getControlObject() {
        return controlObject;
    }

    public void setControlObject(DbControl controlObject) {
        this.controlObject = controlObject;
    }

    public List<DbControlHazard> getListDbControlHazard() {
        return listDbControlHazard;
    }

    public void setListDbControlHazard(List<DbControlHazard> listDbControlHazard) {
        this.listDbControlHazard = listDbControlHazard;
    }

    public List<DbControl> getListDbControl() {
        return listDbControl;
    }

    public void setListDbControl(List<DbControl> listDbControl) {
        this.listDbControl = listDbControl;
    }

    public List<DbProject> getListProjects() {
        return listProjects;
    }

    public void setListProjects(List<DbProject> listProjects) {
        this.listProjects = listProjects;
    }

    public List<DbLocation> getListLocations() {
        return listLocations;
    }

    public void setListLocations(List<DbLocation> listLocations) {
        this.listLocations = listLocations;
    }

    public List<DbgradeSeparation> getListGradeSeparations() {
        return listGradeSeparations;
    }

    public void setListGradeSeparations(List<DbgradeSeparation> listGradeSeparations) {
        this.listGradeSeparations = listGradeSeparations;
    }

    public List<DbconstructionType> getListConstructionTypes() {
        return listConstructionTypes;
    }

    public void setListConstructionTypes(List<DbconstructionType> listConstructionTypes) {
        this.listConstructionTypes = listConstructionTypes;
    }

    public List<DbchangeType> getListChangeTypes() {
        return listChangeTypes;
    }

    public void setListChangeTypes(List<DbchangeType> listChangeTypes) {
        this.listChangeTypes = listChangeTypes;
    }

    public List<DbhazardActivity> getListHazardActivities() {
        return listHazardActivities;
    }

    public void setListHazardActivities(List<DbhazardActivity> listHazardActivities) {
        this.listHazardActivities = listHazardActivities;
    }

    public List<DbhazardContext> getListHazardContexts() {
        return listHazardContexts;
    }

    public void setListHazardContexts(List<DbhazardContext> listHazardContexts) {
        this.listHazardContexts = listHazardContexts;
    }

    public List<DbhazardType> getListHazardTypes() {
        return listHazardTypes;
    }

    public void setListHazardTypes(List<DbhazardType> listHazardTypes) {
        this.listHazardTypes = listHazardTypes;
    }

    public List<DbOwners> getListHazardOwners() {
        return listHazardOwners;
    }

    public void setListHazardOwners(List<DbOwners> listHazardOwners) {
        this.listHazardOwners = listHazardOwners;
    }

    public List<DbriskClass> getListRiskClasses() {
        return listRiskClasses;
    }

    public void setListRiskClasses(List<DbriskClass> listRiskClasses) {
        this.listRiskClasses = listRiskClasses;
    }

    public List<DbcontrolHierarchy> getListControlHierarchies() {
        return listControlHierarchies;
    }

    public void setListControlHierarchies(List<DbcontrolHierarchy> listControlHierarchies) {
        this.listControlHierarchies = listControlHierarchies;
    }

    public List<DbcontrolRecommend> getListControlRecommendations() {
        return listControlRecommendations;
    }

    public void setListControlRecommendations(List<DbcontrolRecommend> listControlRecommendations) {
        this.listControlRecommendations = listControlRecommendations;
    }

    public List<String> getListControlTypes() {
        return listControlTypes;
    }

    public void setListControlTypes(List<String> listControlTypes) {
        this.listControlTypes = listControlTypes;
    }

    public List<DbhazardStatus> getListHazardStatuses() {
        return listHazardStatuses;
    }

    public void setListHazardStatuses(List<DbhazardStatus> listHazardStatuses) {
        this.listHazardStatuses = listHazardStatuses;
    }

    public List<Boolean> getToggleColumns() {
        return toggleColumns;
    }

    public void setToggleColumns(List<Boolean> toggleColumns) {
        this.toggleColumns = toggleColumns;
    }

    public List<DbuserPreferences> getUserPreferences() {
        return userPreferences;
    }

    public void setUserPreferences(List<DbuserPreferences> userPreferences) {
        this.userPreferences = userPreferences;
    }

    public DbuserPreferences getCurrentPreferences() {
        return currentPreferences;
    }

    public void setCurrentPreferences(DbuserPreferences currentPreferences) {
        this.currentPreferences = currentPreferences;
    }

    public DbuserPreferencesPK getCurrentPreferencesPK() {
        return currentPreferencesPK;
    }

    public void setCurrentPreferencesPK(DbuserPreferencesPK currentPreferencesPK) {
        this.currentPreferencesPK = currentPreferencesPK;
    }

    public DbUser getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(DbUser activeUser) {
        this.activeUser = activeUser;
    }

    public String getActiveUserName() {
        return activeUserName;
    }

    public void setActiveUserName(String activeUserName) {
        this.activeUserName = activeUserName;
    }

    public String getnHazards() {
        return nHazards;
    }

    public void setnHazards(String nHazards) {
        this.nHazards = nHazards;
    }

    @PostConstruct
    public void init() {
        activeUser = (DbUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("activeUser");
        setActiveUserName(activeUser.getFirstName() + " " + activeUser.getLastName());
        hazardSearchedList = (List<DbHazard>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("hazardList");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("hazardList");

        setnHazards(Integer.toString(hazardSearchedList.size()));
        
        listProjects = dbProjectFacade.findAll();
        listChangeTypes = dbchangeTypeFacade.findAll();
        listConstructionTypes = dbconstructionTypeFacade.findAll();
        listGradeSeparations = dbgradeSeparationFacade.findAll();
        listLocations = dbLocationFacade.findAll();
        listHazardActivities = dbhazardActivityFacade.findAll();
        listHazardContexts = dbhazardContextFacade.findAll();
        listHazardTypes = dbhazardTypeFacade.findAll();
        listHazardOwners = dbOwnersFacade.findAll();
        listHazardStatuses = dbhazardStatusFacade.findAll();
        listRiskClasses = dbriskClassFacade.findAll();
        listControlHierarchies = dbcontrolHierarchyFacade.findAll();
        listControlRecommendations = dbcontrolRecommendFacade.findAll();
        
        listControlTypes = new ArrayList<>();
        listControlTypes.add("P");
        listControlTypes.add("M");

//        // Initialise column visibility preferences
//        Boolean[] columnVisibility = new Boolean[27];
//        Arrays.fill(columnVisibility, Boolean.TRUE);
//        setToggleColumns(Arrays.asList(columnVisibility));
//        
//        
//        try {
//            setUserPreferences(dbuserPreferencesFacade.getSpecificPreference(activeUser.getUserId(), "Hazard JD", "Extended Table"));
//            for (DbuserPreferences preferenceOption : getUserPreferences()) {
//                if (preferenceOption.getDbuserPreferencesPK().getPageName().equals("Hazard JD") && preferenceOption.getDbuserPreferencesPK().getTableName().equals("Extended Table")) {
//                    String[] tempParsingString = preferenceOption.getUserPreferences().split(", ");
//                    Boolean[] tempParsingList = new Boolean[tempParsingString.length];
//                    for (int i = 0; i < tempParsingString.length; i+= 1) {
//                        Array.set(tempParsingList, i, Boolean.parseBoolean(tempParsingString[i]));
//                    }
//                    setToggleColumns(Arrays.asList(tempParsingList));
//                    break;
//                }
//            }
//        } catch (Exception e) {
//        }
    }
    
    public List<String> viewHazardCause(String hazardId) {
        listDbHazardCause = dbHazardFacade.getHazardCause(hazardId);
        causeDescription = new ArrayList<>();
        for (int i = 0; i < listDbHazardCause.size(); i+= 1) {
            hazardCauseObject = listDbHazardCause.get(i);
            causeObject = hazardCauseObject.getDbCause();
            causeDescription.add(causeObject.getCauseDescription());
        }
        return causeDescription;
    }

    public List<String> viewHazardConsequence(String hazardId) {
        listDbHazardConsequence = dbHazardFacade.getHazardConsequence(hazardId);
        consequenceDescription = new ArrayList<>();
        for (int i = 0; i < listDbHazardConsequence.size(); i+= 1) {
            hazardConsequenceObject = listDbHazardConsequence.get(i);
            consequenceObject = hazardConsequenceObject.getDbConsequence();
            consequenceDescription.add(consequenceObject.getConsequenceDescription());
        }
        return consequenceDescription;
    }
    
    public String getNodeLastById(String nodeId) {
        treeHazardSbsList = new ArrayList<>();
        String nodeName = "";
        String parts[] = nodeId.split("\\.");
        if (nodeId.equals("")) {
            nodeName = "";
        } else {
            for (int i = 1; i <= parts.length; i+= 1) {
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
        return nodeName;
    }
    
    public List<String> viewSbsCondensed(String hazardId) {
        listDbHazardSbs = dbHazardFacade.getSbs(hazardId);

        List<String> sbsChildren = new ArrayList<>();
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
        return sbsChildren;
    }
    
    public String getNodeNameById(String nodeId) {
        treeHazardSbsList = new ArrayList<>();
        String nodeName = "";
        String parts[] = nodeId.split("\\.");
        for (int i = 1; i <= parts.length; i+= 1) {
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
    
    public List<String> viewControlDescription(String hazardId) {
        listDbControlHazard = dbHazardFacade.getControlHazard(hazardId);
        currentControls = new ArrayList<>();
        for (int i = 0; i < listDbControlHazard.size(); i+= 1) {
            controlHazardObject = listDbControlHazard.get(i);
            controlObject = controlHazardObject.getDbControl();
            currentControls.add(controlObject.getControlDescription());
        }
        return currentControls;
    }
    
    public List<String> viewControlHierarchy(String hazardId) {
        listDbControlHazard = dbHazardFacade.getControlHazard(hazardId);
        controlHierarchy = new ArrayList<>();
        for (int i = 0; i < listDbControlHazard.size(); i+= 1) {
            controlHazardObject = listDbControlHazard.get(i);
            controlObject = controlHazardObject.getDbControl();
            controlHierarchy.add(controlObject.getControlHierarchyId().getControlHierarchyName());
        }
        return controlHierarchy;
    }
    
    public List<String> viewControlType(String hazardId) {
        listDbControlHazard = dbHazardFacade.getControlHazard(hazardId);
        controlType = new ArrayList<>();
        for (int i = 0; i < listDbControlHazard.size(); i+= 1) {
            controlHazardObject = listDbControlHazard.get(i);
            switch (controlHazardObject.getControlType()) {
                case "P":
                    controlType.add("Preventive");
                    break;
                default:
                    controlType.add("Mitigative");
                    break;
            }
        }
        return controlType;
    }
    
    public List<String> viewControlOwner(String hazardId) {
        listDbControlHazard = dbHazardFacade.getControlHazard(hazardId);
        controlOwner = new ArrayList<>();
        for (int i = 0; i < listDbControlHazard.size(); i+= 1) {
            controlHazardObject = listDbControlHazard.get(i);
            controlObject = controlHazardObject.getDbControl();
            controlOwner.add(controlObject.getOwnerId().getOwnerName());
        }
        return controlOwner;
    }
    
    public List<String> viewControlRecommend(String hazardId) {
        listDbControlHazard = dbHazardFacade.getControlHazard(hazardId);
        controlRecommend = new ArrayList<>();
        for (int i = 0; i < listDbControlHazard.size(); i+= 1) {
            controlHazardObject = listDbControlHazard.get(i);
            controlObject = controlHazardObject.getDbControl();
            controlRecommend.add(controlHazardObject.getControlRecommendId().getControlRecommendName());
        }
        return controlRecommend;
    }
    
    public List<String> viewControlJustify(String hazardId) {
        listDbControlHazard = dbHazardFacade.getControlHazard(hazardId);
        controlJustify = new ArrayList<>();
        for (int i = 0; i < listDbControlHazard.size(); i+= 1) {
            controlHazardObject = listDbControlHazard.get(i);
            controlObject = controlHazardObject.getDbControl();
            controlJustify.add(controlHazardObject.getControlJustify());
        }
        return controlJustify;
    }
    
    public void testFunction() {
        System.out.println("Check Complete");
    }
    
    public void exportToFile() {
        System.out.println("Export called");
        String[] hNames = {"Hazard ID", "Hazard Description", "SBS Code", "Source Project", 
            "Source Location", "Source Grade Separation", "Source Construction Type", 
            "Source Change Type", "Hazard Context", "Hazard Activity", "Hazard Type", 
            "Hazard Owner", "Causes", "Consequences", "Risk Category", "Risk Frequency", 
            "Risk Severity", "Risk Score", "Control Description", "Control Hierarchy",
            "Control Type", "Control Owner", "Control Recommendation", "Control Justification", 
            "Date of Entry", "Workshop of Entry", "Comments"};
        
        List<List<String>> dHazards = new ArrayList<>();
        
        System.out.println("Constructing data");
        long start = System.currentTimeMillis();
        for (int i = 0; i < hNames.length; i += 1) {
            List<String> tList = new ArrayList<>();
            for (int j = 0; j < hazardSearchedList.size(); j += 1) {
                switch(i) {
                    case 0:
                        tList.add(hazardSearchedList.get(j).getHazardId());
                        break;
                    case 1:
                        tList.add(hazardSearchedList.get(j).getHazardDescription());
                        break;
                    case 2:
                        List<String> sList = viewSbsCondensed(hazardSearchedList.get(j).getHazardId());
                        String sString = "";
                        for (int k = 0; k < sList.size(); k += 1) {
                            sString += getNodeLastById(sList.get(k)) + "\n";
                        }
                        tList.add(sString.substring(0, sString.length() - 1));
                    case 3:
                        tList.add(hazardSearchedList.get(j).getHazardLocation().getProjectId().getProjectName());
                        break;
                    case 4:
                        tList.add(hazardSearchedList.get(j).getHazardLocation().getLocationName());
                        break;
                    case 5:
                        tList.add(hazardSearchedList.get(j).getHazardLocation().getLocationGradeSeparation().getGradeSeparationName());
                        break;
                    case 6:
                        tList.add(hazardSearchedList.get(j).getHazardLocation().getLocationConstructionType().getConstructionTypeName());
                        break;
                    case 7:
                        tList.add(hazardSearchedList.get(j).getHazardLocation().getLocationChangeType().getChangeTypeName());
                        break;
                    case 8:
                        tList.add(hazardSearchedList.get(j).getHazardContextId().getHazardContextName());
                        break;
                    case 9:
                        tList.add(hazardSearchedList.get(j).getHazardActivity().getActivityName());
                        break;
                    case 10:
                        tList.add(hazardSearchedList.get(j).getHazardTypeId().getHazardTypeName());
                        break;
                    case 11:
                        tList.add(hazardSearchedList.get(j).getOwnerId().getOwnerName());
                        break;
                    case 12:
                        List<String> caList = viewHazardCause(hazardSearchedList.get(j).getHazardId());
                        String caString = "";
                        for (int k = 0; k < caList.size(); k += 1) {
                            caString += caList.get(k) + "\n";
                        }
                        tList.add(caString.substring(0, caString.length() - 1));
                        break;
                    case 13:
                        List<String> cqList = viewHazardConsequence(hazardSearchedList.get(j).getHazardId());
                        String cqString = "";
                        for (int k = 0; k < cqList.size(); k += 1) {
                            cqString += cqList.get(k) + "\n";
                        }
                        tList.add(cqString.substring(0, cqString.length() - 1));
                        break;
                    case 14:
                        tList.add(hazardSearchedList.get(j).getRiskClassId().getRiskClassName());
                        break;
                    case 15:
                        tList.add(hazardSearchedList.get(j).getRiskFrequencyId().getFrequencyScore());
                        break;
                    case 16:
                        tList.add(hazardSearchedList.get(j).getRiskSeverityId().getSeverityScore());
                        break;
                    case 17:
                        tList.add(Integer.toString(hazardSearchedList.get(j).getRiskScore()));
                        break;
                    case 18:
                        List<String> cdList = viewControlDescription(hazardSearchedList.get(j).getHazardId());
                        String cdString = "";
                        for (int k = 0; k < cdList.size(); k += 1) {
                            cdString += cdList.get(k) + "\n";
                        }
                        tList.add(cdString.substring(0, cdString.length() - 1));
                        break;
                    case 19:
                        List<String> chList = viewControlHierarchy(hazardSearchedList.get(j).getHazardId());
                        String chString = "";
                        for (int k = 0; k < chList.size(); k += 1) {
                            chString += chList.get(k) + "\n";
                        }
                        tList.add(chString.substring(0, chString.length() - 1));
                        break;
                    case 20:
                        List<String> ctList = viewControlType(hazardSearchedList.get(j).getHazardId());
                        String ctString = "";
                        for (int k = 0; k < ctList.size(); k += 1) {
                            ctString += ctList.get(k) + "\n";
                        }
                        tList.add(ctString.substring(0, ctString.length() - 1));
                        break;
                    case 21:
                        List<String> coList = viewControlOwner(hazardSearchedList.get(j).getHazardId());
                        String coString = "";
                        for (int k = 0; k < coList.size(); k += 1) {
                            coString += coList.get(k) + "\n";
                        }
                        tList.add(coString.substring(0, coString.length() - 1));
                        break;
                    case 22:
                        List<String> crList = viewControlRecommend(hazardSearchedList.get(j).getHazardId());
                        String crString = "";
                        for (int k = 0; k < crList.size(); k += 1) {
                            crString += crList.get(k) + "\n";
                        }
                        tList.add(crString.substring(0, crString.length() - 1));
                        break;
                    case 23:
                        List<String> cjList = viewControlJustify(hazardSearchedList.get(j).getHazardId());
                        String cjString = "";
                        for (int k = 0; k < cjList.size(); k += 1) {
                            cjString += cjList.get(k) + "\n";
                        }
                        tList.add(cjString.substring(0, cjString.length() - 1));
                        break;
                    case 24:
                        tList.add(hazardSearchedList.get(j).getHazardDate().toString());
                        break;
                    case 25:
                        tList.add(hazardSearchedList.get(j).getHazardWorkshop());
                        break;
                    case 26:
                        tList.add(hazardSearchedList.get(j).getHazardComment());
                        break;
                }
            }
            dHazards.add(tList);
        }
        long elapsedTimeMillis = System.currentTimeMillis()-start;
        System.out.println(elapsedTimeMillis);
        
        System.out.println("Building workbook");
        String filename = "ssd_export_log.xlsx";
        Workbook wb = new XSSFWorkbook();
        Sheet sh = wb.createSheet("Hazards Log");
        CreationHelper ch = wb.getCreationHelper();
        
        XSSFFont hf = (XSSFFont) wb.createFont();
        hf.setBold(true);
        hf.setFontHeightInPoints((short) 13);
        XSSFCellStyle hs = (XSSFCellStyle) wb.createCellStyle();
        XSSFColor hc = new XSSFColor(new java.awt.Color(72, 185, 199));
        hs.setFont(hf);
        hs.setFillForegroundColor(hc);
        hs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        XSSFFont df = (XSSFFont) wb.createFont();
        df.setFontHeightInPoints((short) 11);
        XSSFCellStyle ds = (XSSFCellStyle) wb.createCellStyle();
        ds.setFont(df);
        ds.setWrapText(true);
        ds.setVerticalAlignment(VerticalAlignment.CENTER);
        
        XSSFRow hRow = (XSSFRow) sh.createRow(0);
        
        System.out.println("Populating data");
        start = System.currentTimeMillis();
        for (int i = 0; i < hazardSearchedList.size(); i += 1) {
            XSSFRow dRow = (XSSFRow) sh.createRow(i + 1);
            for (int j = 0; j < hNames.length; j += 1) {
                XSSFCell dCell = dRow.createCell(j);
                dCell.setCellValue(ch.createRichTextString(dHazards.get(j).get(i)));
                dCell.setCellStyle(ds);
            }
        }
        elapsedTimeMillis = System.currentTimeMillis()-start;
        System.out.println(elapsedTimeMillis);
        
        start = System.currentTimeMillis();
        for (int i = 0; i < hNames.length; i += 1) {
            XSSFCell hCell = hRow.createCell(i);
            hCell.setCellValue(ch.createRichTextString(hNames[i]));
            hCell.setCellStyle(hs);
            sh.autoSizeColumn(i);
        }
        elapsedTimeMillis = System.currentTimeMillis()-start;
        System.out.println(elapsedTimeMillis);
        
        System.out.println("Writing workbook");
        
        start = System.currentTimeMillis();
        try {
            // Prepare response.
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.setResponseContentType("application/vnd.ms-excel");
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

            // Write file to response body.
            wb.write(externalContext.getResponseOutputStream());

            // Inform JSF that response is completed and it thus doesn't have to navigate.
            facesContext.responseComplete();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(hazardViewExtend_MB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(hazardViewExtend_MB.class.getName()).log(Level.SEVERE, null, ex);
        }
        elapsedTimeMillis = System.currentTimeMillis()-start;
        System.out.println(elapsedTimeMillis);
    }
    
//    public void onToggle(ToggleEvent e) {
//        getToggleColumns().set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
//        currentPreferencesPK.setPageName("Hazard JD");
//        currentPreferencesPK.setTableName("Extended Table");
//        currentPreferencesPK.setUserId(activeUser.getUserId());
//        currentPreferences.setDbuserPreferencesPK(currentPreferencesPK);
//        currentPreferences.setUserPreferences(getToggleColumns().toString().substring(1, getToggleColumns().toString().length() - 1));
//        if (getUserPreferences().isEmpty()) {
//            dbuserPreferencesFacade.create(currentPreferences);
//        } else {
//            dbuserPreferencesFacade.edit(currentPreferences);
//        }
//        setUserPreferences(dbuserPreferencesFacade.getSpecificPreference(activeUser.getUserId(), "Hazard JD", "Extended Table"));
//    }
}
