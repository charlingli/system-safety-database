/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import customObjects.treeNodeObject;
import customObjects.searchObject;
import ejb.*;
import javax.inject.Named;
import java.io.Serializable;
import javax.ejb.EJB;
import java.util.List;
import entities.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.Visibility;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.mindmap.DefaultMindmapNode;
import org.primefaces.model.mindmap.MindmapNode;

/**
 *
 * @author alan8
 */
@Named(value = "hazardTable_MB")
@ViewScoped
public class hazardTable_MB implements Serializable {

    @EJB
    private DbuserPreferencesFacadeLocal dbuserPreferencesFacade;
    @EJB
    private DbProjectFacadeLocal dbProjectFacade;
    @EJB
    private DbOwnersFacadeLocal dbOwnersFacade;
    @EJB
    private DbLocationFacadeLocal dbLocationFacade;
    @EJB
    private DbHazardFacadeLocal dbHazardFacade;
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

    private DbHazard selectedHazard;
    private List<String> selectedHazardCauses;
    private List<String> selectedHazardConsequences;
    private List<String> selectedHazardControls;
    private DashboardModel DMSearch;

    private List<DbtreeLevel1> listTreeLevel1;
    private String hazardId;
    private TreeNode TNRoot;
    private TreeNode[] TNSelectedNodes;
    private MindmapNode MMRoot;
    private MindmapNode MMSelectedNode;
    private List<String> MMRootData;
    private List<treeNodeObject> treeCheckedNodesList;
    private List<treeNodeObject> treeHazardSbsList;
    private List<searchObject> searchCompositeList;

    private List<DbHazard> listDbHazard;
    private List<DbHazard> filterDbHazard;
    private List<DbHazard> searchDbHazard;

    private List<DbHazardCause> listHazardCauses;
    private List<DbHazardConsequence> listHazardConsequences;

    private List<DbControlHazard> listDbControlHazard;
    private List<DbControl> listDbControl;
    private List<String> currentControls;
    private List<String> controlType; //Otherwise known as control hierarchy names
    private List<String> controlOwner;
    private List<String> controlRecommend;
    private List<String> controlJustify;

    private List<DbConsequence> listDbConsequence;
    private List<Integer> consequenceHazardId;
    private DbConsequence consequenceObject = new DbConsequence();

    private List<DbCause> listDbCause;
    private List<Integer> causeHazardId;
    private DbCause causeObject = new DbCause();

    private List<DbHazardSbs> listDbHazardSbs;
    private List<String> sbsId;
    private DbHazardSbs hazardSbsObject = new DbHazardSbs();

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
    private List<DbcontrolHierarchy> listControlTypes;
    private List<DbcontrolRecommend> listControlRecommendations;
    private List<DbhazardStatus> listHazardStatuses;
    private List<String> listProjectIds;
    private List<String> listLocationIds;
    private List<String> listGradeSeparationIds;
    private List<String> listConstructionTypeIds;
    private List<String> listChangeTypeIds;
    private List<String> listHazardActivityIds;
    private List<String> listHazardContextIds;
    private List<String> listHazardTypeIds;
    private List<String> listHazardOwnerIds;
    private List<String> listControlTypeIds;
    private List<String> listControlRecommendationIds;
    private List<String> listHazardStatusIds;

    private List<DbHazardCause> listDbHazardCause;
    private DbHazardCause hazardCauseObject = new DbHazardCause();
    private List<String> causeDescription;

    private List<DbHazardConsequence> listDbHazardConsequence;
    private DbHazardConsequence hazardConsequenceObject = new DbHazardConsequence();
    private List<String> consequenceDescription;

    private String[] selectedProjects;
    private String[] selectedLocations;
    private String[] selectedGradeSeparations;
    private String[] selectedConstructionTypes;
    private String[] selectedChangeTypes;
    private String[] selectedHazardActivities;
    private String[] selectedHazardContexts;
    private String[] selectedHazardTypes;
    private String[] selectedHazardStatuses;
    private String[] selectedHazardOwners;
    private String[] selectedHazardReview;
    private String[] selectedRiskClasses;
    private String[] selectedRiskFrequencies;
    private String[] selectedRiskSeverities;
    private String[] selectedControlTypes;
    private String[] selectedControlOwners;
    private String[] selectedControlRecommendations;
    private String searchedCauses;
    private String searchedConsequences;
    private String selectedHazardDescription;
    private String selectedHazardComments;
    private String selectedControlJustification;
    private String selectedRiskScore;
    private String selectedRiskScoreType;
    private String selectedControlDescription;
    private List<Boolean> toggleColumns;
    private List<DbuserPreferences> userPreferences;
    private DbuserPreferences currentPreferences = new DbuserPreferences();
    private DbuserPreferencesPK currentPreferencesPK = new DbuserPreferencesPK();
    private DbUser activeUser = new DbUser();
    private String activeUserName;
    private boolean isTableEmpty;
    private boolean isMapPopulated;
    private List<DbtreeLevel2> level2Children;
    private List<DbtreeLevel3> level3Children;
    private List<DbtreeLevel4> level4Children;
    private List<DbtreeLevel5> level5Children;
    private List<DbtreeLevel6> level6Children;

    public DbHazard getSelectedHazard() {
        return selectedHazard;
    }

    public void setSelectedHazard(DbHazard selectedHazard) {
        this.selectedHazard = selectedHazard;
    }

    public List<String> getSelectedHazardCauses() {
        return selectedHazardCauses;
    }

    public void setSelectedHazardCauses(List<String> selectedHazardCauses) {
        this.selectedHazardCauses = selectedHazardCauses;
    }

    public List<String> getSelectedHazardConsequences() {
        return selectedHazardConsequences;
    }

    public void setSelectedHazardConsequences(List<String> selectedHazardConsequences) {
        this.selectedHazardConsequences = selectedHazardConsequences;
    }

    public List<String> getSelectedHazardControls() {
        return selectedHazardControls;
    }

    public void setSelectedHazardControls(List<String> selectedHazardControls) {
        this.selectedHazardControls = selectedHazardControls;
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

    public boolean isIsTableEmpty() {
        return isTableEmpty;
    }

    public void setIsTableEmpty(boolean isTableEmpty) {
        this.isTableEmpty = isTableEmpty;
    }

    public hazardTable_MB() {
    }

    public List<DbHazard> getListDbHazard() {
        return listDbHazard;
    }

    public void setListDbHazard(List<DbHazard> listDbHazard) {
        this.listDbHazard = listDbHazard;
    }

    public List<DbHazard> getFilterDbHazard() {
        return filterDbHazard;
    }

    public void setFilterDbHazard(List<DbHazard> filterDbHazard) {
        this.filterDbHazard = filterDbHazard;
    }

    public List<DbHazard> getSearchDbHazard() {
        return searchDbHazard;
    }

    public void setSearchDbHazard(List<DbHazard> searchDbHazard) {
        this.searchDbHazard = searchDbHazard;
    }

    public List<DbHazardCause> getListHazardCauses() {
        return listHazardCauses;
    }

    public void setListHazardCauses(List<DbHazardCause> listHazardCauses) {
        this.listHazardCauses = listHazardCauses;
    }

    public List<DbHazardConsequence> getListHazardConsequences() {
        return listHazardConsequences;
    }

    public void setListHazardConsequences(List<DbHazardConsequence> listHazardConsequences) {
        this.listHazardConsequences = listHazardConsequences;
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

    public List<DbConsequence> getListDbConsequence() {
        return listDbConsequence;
    }

    public void setListDbConsequence(List<DbConsequence> listDbConsequence) {
        this.listDbConsequence = listDbConsequence;
    }

    public List<Integer> getConsequenceHazardId() {
        return consequenceHazardId;
    }

    public void setConsequenceHazardId(List<Integer> consequenceHazardId) {
        this.consequenceHazardId = consequenceHazardId;
    }

    public DbConsequence getConsequenceObject() {
        return consequenceObject;
    }

    public void setConsequenceObject(DbConsequence consequenceObject) {
        this.consequenceObject = consequenceObject;
    }

    public List<DbCause> getListDbCause() {
        return listDbCause;
    }

    public void setListDbCause(List<DbCause> listDbCause) {
        this.listDbCause = listDbCause;
    }

    public DbCause getCauseObject() {
        return causeObject;
    }

    public void setCauseObject(DbCause causeObject) {
        this.causeObject = causeObject;
    }

    public List<DbHazardSbs> getListDbHazardSbs() {
        return listDbHazardSbs;
    }

    public void setListDbHazardSbs(List<DbHazardSbs> listDbHazardSbs) {
        this.listDbHazardSbs = listDbHazardSbs;
    }

    public List<String> getSbsId() {
        return sbsId;
    }

    public void setSbsId(List<String> sbsId) {
        this.sbsId = sbsId;
    }

    public DbHazardSbs getHazardSbsObject() {
        return hazardSbsObject;
    }

    public void setHazardSbsObject(DbHazardSbs hazardSbsObject) {
        this.hazardSbsObject = hazardSbsObject;
    }

    public List<Integer> getCauseHazardId() {
        return causeHazardId;
    }

    public void setCauseHazardId(List<Integer> causeHazardId) {
        this.causeHazardId = causeHazardId;
    }

    public DbControlHazard getControlHazardObject() {
        return controlHazardObject;
    }

    public void setControlHazardObject(DbControlHazard controlHazardObject) {
        this.controlHazardObject = controlHazardObject;
    }

    public DbControl getControlObject() {
        return controlObject;
    }

    public void setControlObject(DbControl controlObject) {
        this.controlObject = controlObject;
    }

    public DbcontrolHierarchy getControlHierObject() {
        return controlHierObject;
    }

    public void setControlHierObject(DbcontrolHierarchy controlHierObject) {
        this.controlHierObject = controlHierObject;
    }

    public List<DbLocation> getListLocations() {
        return listLocations;
    }

    public void setListLocations(List<DbLocation> listLocations) {
        this.listLocations = listLocations;
    }

    public List<DbProject> getListProjects() {
        return listProjects;
    }

    public void setListProjects(List<DbProject> listProjects) {
        this.listProjects = listProjects;
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

    public List<DbcontrolHierarchy> getListControlTypes() {
        return listControlTypes;
    }

    public void setListControlTypes(List<DbcontrolHierarchy> listControlTypes) {
        this.listControlTypes = listControlTypes;
    }

    public List<DbcontrolRecommend> getListControlRecommendations() {
        return listControlRecommendations;
    }

    public void setListControlRecommendations(List<DbcontrolRecommend> listControlRecommendations) {
        this.listControlRecommendations = listControlRecommendations;
    }

    public List<DbhazardStatus> getListHazardStatuses() {
        return listHazardStatuses;
    }

    public void setListHazardStatuses(List<DbhazardStatus> listHazardStatuses) {
        this.listHazardStatuses = listHazardStatuses;
    }

    public List<String> getListProjectIds() {
        return listProjectIds;
    }

    public void setListProjectIds(List<String> listProjectIds) {
        this.listProjectIds = listProjectIds;
    }

    public List<String> getListLocationIds() {
        return listLocationIds;
    }

    public void setListLocationIds(List<String> listLocationIds) {
        this.listLocationIds = listLocationIds;
    }

    public List<String> getListGradeSeparationIds() {
        return listGradeSeparationIds;
    }

    public void setListGradeSeparationIds(List<String> listGradeSeparationIds) {
        this.listGradeSeparationIds = listGradeSeparationIds;
    }

    public List<String> getListConstructionTypeIds() {
        return listConstructionTypeIds;
    }

    public void setListConstructionTypeIds(List<String> listConstructionTypeIds) {
        this.listConstructionTypeIds = listConstructionTypeIds;
    }

    public List<String> getListChangeTypeIds() {
        return listChangeTypeIds;
    }

    public void setListChangeTypeIds(List<String> listChangeTypeIds) {
        this.listChangeTypeIds = listChangeTypeIds;
    }

    public List<String> getListHazardActivityIds() {
        return listHazardActivityIds;
    }

    public void setListHazardActivityIds(List<String> listHazardActivityIds) {
        this.listHazardActivityIds = listHazardActivityIds;
    }

    public List<String> getListHazardContextIds() {
        return listHazardContextIds;
    }

    public void setListHazardContextIds(List<String> listHazardContextIds) {
        this.listHazardContextIds = listHazardContextIds;
    }

    public List<String> getListHazardTypeIds() {
        return listHazardTypeIds;
    }

    public void setListHazardTypeIds(List<String> listHazardTypeIds) {
        this.listHazardTypeIds = listHazardTypeIds;
    }

    public List<String> getListHazardOwnerIds() {
        return listHazardOwnerIds;
    }

    public void setListHazardOwnerIds(List<String> listHazardOwnerIds) {
        this.listHazardOwnerIds = listHazardOwnerIds;
    }

    public List<String> getListControlTypeIds() {
        return listControlTypeIds;
    }

    public void setListControlTypeIds(List<String> listControlTypeIds) {
        this.listControlTypeIds = listControlTypeIds;
    }

    public List<String> getListControlRecommendationIds() {
        return listControlRecommendationIds;
    }

    public void setListControlRecommendationIds(List<String> listControlRecommendationIds) {
        this.listControlRecommendationIds = listControlRecommendationIds;
    }

    public List<String> getListHazardStatusIds() {
        return listHazardStatusIds;
    }

    public void setListHazardStatusIds(List<String> listHazardStatusIds) {
        this.listHazardStatusIds = listHazardStatusIds;
    }

    public List<DbHazardCause> getListDbHazardCause() {
        return listDbHazardCause;
    }

    public void setListDbHazardCause(List<DbHazardCause> listDbHazardCause) {
        this.listDbHazardCause = listDbHazardCause;
    }

    public List<DbHazardConsequence> getListDbHazardConsequence() {
        return listDbHazardConsequence;
    }

    public void setListDbHazardConsequence(List<DbHazardConsequence> listDbHazardConsequence) {
        this.listDbHazardConsequence = listDbHazardConsequence;
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

    public List<String> getConsequenceDescription() {
        return consequenceDescription;
    }

    public void setConsequenceDescription(List<String> consequenceDescription) {
        this.consequenceDescription = consequenceDescription;
    }

    public String[] getSelectedLocations() {
        return selectedLocations;
    }

    public void setSelectedLocations(String[] selectedLocations) {
        this.selectedLocations = selectedLocations;
    }

    public String[] getSelectedProjects() {
        return selectedProjects;
    }

    public void setSelectedProjects(String[] selectedProjects) {
        this.selectedProjects = selectedProjects;
    }

    public String[] getSelectedGradeSeparations() {
        return selectedGradeSeparations;
    }

    public void setSelectedGradeSeparations(String[] selectedGradeSeparations) {
        this.selectedGradeSeparations = selectedGradeSeparations;
    }

    public String[] getSelectedConstructionTypes() {
        return selectedConstructionTypes;
    }

    public void setSelectedConstructionTypes(String[] selectedConstructionTypes) {
        this.selectedConstructionTypes = selectedConstructionTypes;
    }

    public String[] getSelectedChangeTypes() {
        return selectedChangeTypes;
    }

    public void setSelectedChangeTypes(String[] selectedChangeTypes) {
        this.selectedChangeTypes = selectedChangeTypes;
    }

    public String[] getSelectedHazardActivities() {
        return selectedHazardActivities;
    }

    public void setSelectedHazardActivities(String[] selectedHazardActivities) {
        this.selectedHazardActivities = selectedHazardActivities;
    }

    public String[] getSelectedHazardContexts() {
        return selectedHazardContexts;
    }

    public void setSelectedHazardContexts(String[] selectedHazardContexts) {
        this.selectedHazardContexts = selectedHazardContexts;
    }

    public String[] getSelectedHazardTypes() {
        return selectedHazardTypes;
    }

    public void setSelectedHazardTypes(String[] selectedHazardTypes) {
        this.selectedHazardTypes = selectedHazardTypes;
    }

    public String[] getSelectedHazardStatuses() {
        return selectedHazardStatuses;
    }

    public void setSelectedHazardStatuses(String[] selectedHazardStatuses) {
        this.selectedHazardStatuses = selectedHazardStatuses;
    }

    public String[] getSelectedHazardOwners() {
        return selectedHazardOwners;
    }

    public void setSelectedHazardOwners(String[] selectedHazardOwners) {
        this.selectedHazardOwners = selectedHazardOwners;
    }

    public String[] getSelectedHazardReview() {
        return selectedHazardReview;
    }

    public void setSelectedHazardReview(String[] selectedHazardReview) {
        this.selectedHazardReview = selectedHazardReview;
    }

    public String[] getSelectedRiskClasses() {
        return selectedRiskClasses;
    }

    public void setSelectedRiskClasses(String[] selectedRiskClasses) {
        this.selectedRiskClasses = selectedRiskClasses;
    }

    public String[] getSelectedRiskFrequencies() {
        return selectedRiskFrequencies;
    }

    public void setSelectedRiskFrequencies(String[] selectedRiskFrequencies) {
        this.selectedRiskFrequencies = selectedRiskFrequencies;
    }

    public String[] getSelectedRiskSeverities() {
        return selectedRiskSeverities;
    }

    public void setSelectedRiskSeverities(String[] selectedRiskSeverities) {
        this.selectedRiskSeverities = selectedRiskSeverities;
    }

    public String[] getSelectedControlTypes() {
        return selectedControlTypes;
    }

    public void setSelectedControlTypes(String[] selectedControlTypes) {
        this.selectedControlTypes = selectedControlTypes;
    }

    public String[] getSelectedControlOwners() {
        return selectedControlOwners;
    }

    public void setSelectedControlOwners(String[] selectedControlOwners) {
        this.selectedControlOwners = selectedControlOwners;
    }

    public String[] getSelectedControlRecommendations() {
        return selectedControlRecommendations;
    }

    public void setSelectedControlRecommendations(String[] selectedControlRecommendations) {
        this.selectedControlRecommendations = selectedControlRecommendations;
    }

    public String getSelectedRiskScore() {
        return selectedRiskScore;
    }

    public void setSelectedRiskScore(String selectedRiskScore) {
        this.selectedRiskScore = selectedRiskScore;
    }

    public String getSelectedRiskScoreType() {
        return selectedRiskScoreType;
    }

    public void setSelectedRiskScoreType(String selectedRiskScoreType) {
        this.selectedRiskScoreType = selectedRiskScoreType;
    }

    public String getSelectedControlDescription() {
        return selectedControlDescription;
    }

    public void setSelectedControlDescription(String selectedControlDescription) {
        this.selectedControlDescription = selectedControlDescription;
    }

    public String getSearchedCauses() {
        return searchedCauses;
    }

    public void setSearchedCauses(String searchedCauses) {
        this.searchedCauses = searchedCauses;
    }

    public String getSearchedConsequences() {
        return searchedConsequences;
    }

    public void setSearchedConsequences(String searchedConsequences) {
        this.searchedConsequences = searchedConsequences;
    }

    public String getSelectedHazardDescription() {
        return selectedHazardDescription;
    }

    public void setSelectedHazardDescription(String selectedHazardDescription) {
        this.selectedHazardDescription = selectedHazardDescription;
    }

    public String getSelectedHazardComments() {
        return selectedHazardComments;
    }

    public void setSelectedHazardComments(String selectedHazardComments) {
        this.selectedHazardComments = selectedHazardComments;
    }

    public String getSelectedControlJustification() {
        return selectedControlJustification;
    }

    public void setSelectedControlJustification(String selectedControlJustification) {
        this.selectedControlJustification = selectedControlJustification;
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

    public List<DbtreeLevel1> getListTreeLevel1() {
        return listTreeLevel1;
    }

    public void setListTreeLevel1(List<DbtreeLevel1> listTreeLevel1) {
        this.listTreeLevel1 = listTreeLevel1;
    }

    public TreeNode getTNRoot() {
        return TNRoot;
    }

    public void setTNRoot(TreeNode TNRoot) {
        this.TNRoot = TNRoot;
    }

    public String getHazardId() {
        return hazardId;
    }

    public void setHazardId(String hazardId) {
        this.hazardId = hazardId;
    }

    public MindmapNode getMMRoot() {
        return MMRoot;
    }

    public void setMMRoot(MindmapNode MMRoot) {
        this.MMRoot = MMRoot;
    }

    public MindmapNode getMMSelectedNode() {
        return MMSelectedNode;
    }

    public void setMMSelectedNode(MindmapNode MMSelectedNode) {
        this.MMSelectedNode = MMSelectedNode;
    }

    public List<String> getMMRootData() {
        return MMRootData;
    }

    public void setMMRootData(List<String> MMRootData) {
        this.MMRootData = MMRootData;
    }

    public List<treeNodeObject> getTreeCheckedNodesList() {
        return treeCheckedNodesList;
    }

    public void setTreeCheckedNodesList(List<treeNodeObject> treeCheckedNodesList) {
        this.treeCheckedNodesList = treeCheckedNodesList;
    }

    public List<treeNodeObject> getTreeHazardSbsList() {
        return treeHazardSbsList;
    }

    public void setTreeHazardSbsList(List<treeNodeObject> treeHazardSbsList) {
        this.treeHazardSbsList = treeHazardSbsList;
    }

    public List<searchObject> getSearchCompositeObject() {
        return searchCompositeList;
    }

    public void setSearchCompositeObject(List<searchObject> searchCompositeList) {
        this.searchCompositeList = searchCompositeList;
    }

    public TreeNode[] getTNSelectedNodes() {
        return TNSelectedNodes;
    }

    public void setTNSelectedNodes(TreeNode[] TNSelectedNodes) {
        this.TNSelectedNodes = TNSelectedNodes;
    }

    public DashboardModel getCurrentSearchModel() {
        return DMSearch;
    }

    public void setCurrentSearchModel(DashboardModel DMSearch) {
        this.DMSearch = DMSearch;
    }

    public List<DbtreeLevel2> getLevel2Children() {
        return level2Children;
    }

    public void setLevel2Children(List<DbtreeLevel2> level2Children) {
        this.level2Children = level2Children;
    }

    public List<DbtreeLevel3> getLevel3Children() {
        return level3Children;
    }

    public void setLevel3Children(List<DbtreeLevel3> level3Children) {
        this.level3Children = level3Children;
    }

    public List<DbtreeLevel4> getLevel4Children() {
        return level4Children;
    }

    public void setLevel4Children(List<DbtreeLevel4> level4Children) {
        this.level4Children = level4Children;
    }

    public List<DbtreeLevel5> getLevel5Children() {
        return level5Children;
    }

    public void setLevel5Children(List<DbtreeLevel5> level5Children) {
        this.level5Children = level5Children;
    }

    public List<DbtreeLevel6> getLevel6Children() {
        return level6Children;
    }

    public void setLevel6Children(List<DbtreeLevel6> level6Children) {
        this.level6Children = level6Children;
    }

    @PostConstruct
    public void init() {
        activeUser = (DbUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("activeUser");
        setActiveUserName(activeUser.getFirstName() + " " + activeUser.getLastName());

        listDbHazard = dbHazardFacade.findAllHazards();

        setIsTableEmpty(true);

        listProjects = dbProjectFacade.findAll();
        listProjectIds = new ArrayList<>();
        for (DbProject check : listProjects) {
            listProjectIds.add(check.getProjectId().toString());
        }
        listChangeTypes = dbchangeTypeFacade.findAll();
        listChangeTypeIds = new ArrayList<>();
        for (DbchangeType check : listChangeTypes) {
            listChangeTypeIds.add(check.getChangeTypeId().toString());
        }
        listConstructionTypes = dbconstructionTypeFacade.findAll();
        listConstructionTypeIds = new ArrayList<>();
        for (DbconstructionType check : listConstructionTypes) {
            listConstructionTypeIds.add(check.getConstructionTypeId().toString());
        }
        listGradeSeparations = dbgradeSeparationFacade.findAll();
        listGradeSeparationIds = new ArrayList<>();
        for (DbgradeSeparation check : listGradeSeparations) {
            listGradeSeparationIds.add(check.getGradeSeparationId().toString());
        }
        listLocations = dbLocationFacade.findAll();
        listLocationIds = new ArrayList<>();
        for (DbLocation check : listLocations) {
            listLocationIds.add(check.getLocationId().toString());
        }
        listHazardActivities = dbhazardActivityFacade.findAll();
        listHazardActivityIds = new ArrayList<>();
        for (DbhazardActivity check : listHazardActivities) {
            listHazardActivityIds.add(check.getActivityId().toString());
        }
        listHazardContexts = dbhazardContextFacade.findAll();
        listHazardContextIds = new ArrayList<>();
        for (DbhazardContext check : listHazardContexts) {
            listHazardContextIds.add(check.getHazardContextId().toString());
        }
        listHazardTypes = dbhazardTypeFacade.findAll();
        listHazardTypeIds = new ArrayList<>();
        for (DbhazardType check : listHazardTypes) {
            listHazardTypeIds.add(check.getHazardTypeId().toString());
        }
        listHazardOwners = dbOwnersFacade.findAll();
        listHazardOwnerIds = new ArrayList<>();
        for (DbOwners check : listHazardOwners) {
            listHazardOwnerIds.add(check.getOwnerId().toString());
        }
        listRiskClasses = dbriskClassFacade.findAll();
        listControlTypes = dbcontrolHierarchyFacade.findAll();
        listControlTypeIds = new ArrayList<>();
        for (DbcontrolHierarchy check : listControlTypes) {
            listControlTypeIds.add(check.getControlHierarchyId().toString());
        }
        listControlRecommendations = dbcontrolRecommendFacade.findAll();
        listControlRecommendationIds = new ArrayList<>();
        for (DbcontrolRecommend check : listControlRecommendations) {
            listControlRecommendationIds.add(check.getControlRecommendId().toString());
        }
        listHazardStatuses = dbhazardStatusFacade.findAll();
        listHazardStatusIds = new ArrayList<>();
        for (DbhazardStatus check : listHazardStatuses) {
            listHazardStatusIds.add(check.getHazardStatusId().toString());
        }

        listTreeLevel1 = dbtreeLevel1Facade.findAll();
        createTree();

        // Initialise column visibility preferences
        Boolean[] columnVisibility = new Boolean[30];
        Arrays.fill(columnVisibility, Boolean.TRUE);
        setToggleColumns(Arrays.asList(columnVisibility));

        isMapPopulated = false;
        
        try {
            setUserPreferences(dbuserPreferencesFacade.getSpecificPreference(activeUser.getUserId(), "Hazard Search", "Hazard Table"));
            for (DbuserPreferences preferenceOption : getUserPreferences()) {
                if (preferenceOption.getDbuserPreferencesPK().getPageName().equals("Hazard Search") && preferenceOption.getDbuserPreferencesPK().getTableName().equals("Hazard Table")) {
                    String[] tempParsingString = preferenceOption.getUserPreferences().split(", ");
                    Boolean[] tempParsingList = new Boolean[tempParsingString.length];
                    for (int i = 0; i < tempParsingString.length; i++) {
                        Array.set(tempParsingList, i, Boolean.parseBoolean(tempParsingString[i]));
                    }
                    setToggleColumns(Arrays.asList(tempParsingList));
                    break;
                }
            }
        } catch (Exception e) {
        }

        DMSearch = new DefaultDashboardModel();
        DashboardColumn column1 = new DefaultDashboardColumn();
        DashboardColumn column2 = new DefaultDashboardColumn();

        DMSearch.addColumn(column1);
        DMSearch.addColumn(column2);
    }

    public List<String> viewControlHazard(String hazardId) {
        listDbControlHazard = dbHazardFacade.getControlHazard(hazardId); //Collect ControlHazard/s belonging to specific hazardId

        //listDbControl = new ArrayList();
        currentControls = new ArrayList<>();
        controlType = new ArrayList<>();
        controlOwner = new ArrayList<>();
        controlRecommend = new ArrayList<>();
        controlJustify = new ArrayList<>();

        //The following loop extracts controlObjects from the list of ControlHazards so that their attributes can be stored for viewing
        for (int i = 0; i < listDbControlHazard.size(); i++) {
            controlHazardObject = listDbControlHazard.get(i);
            controlObject = controlHazardObject.getDbControl();
            currentControls.add(controlObject.getControlDescription());
            //listDbControl.add(controlObject);

            controlType.add(controlObject.getControlHierarchyId().getControlHierarchyName()); //Collect control type (hierarchy) of controls belonging to specific hazardId
            controlOwner.add(controlObject.getOwnerId().getOwnerName());
            controlRecommend.add(controlHazardObject.getControlRecommendId().getControlRecommendName());
            controlJustify.add(controlHazardObject.getControlJustify());
        }

        //return listDbControl;
        return currentControls;
    }

    public List<String> viewSbs(String hazardId) {
        listDbHazardSbs = dbHazardFacade.getSbs(hazardId);
        sbsId = new ArrayList<>();
        for (int i = 0; i < listDbHazardSbs.size(); i++) {
            hazardSbsObject = listDbHazardSbs.get(i);
            sbsId.add(hazardSbsObject.getDbHazardSbsPK().getSbsId());
        }
        return sbsId;
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

    public void exportAllToFile(List<DbHazard> listToExport) {
        // Setting up the file
        boolean complete = false;
        String filename = "HazMate_HazardTable_Export_Subset.xls";
        if (listToExport.equals(listDbHazard)) {
            filename = "HazMate_HazardTable_Export_Complete.xls";
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("hazardTable");
        CreationHelper createHelper = workbook.getCreationHelper();

        // Creating formats and fonts
        HSSFFont defaultFont = workbook.createFont();
        defaultFont.setFontHeightInPoints((short) 10);

        HSSFFont titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);

        HSSFFont detailHeadingFont = workbook.createFont();
        detailHeadingFont.setBold(true);
        detailHeadingFont.setFontHeightInPoints((short) 12);

        HSSFFont detailPrintFont = workbook.createFont();
        detailPrintFont.setItalic(true);
        detailPrintFont.setFontHeightInPoints((short) 12);

        HSSFFont headingFont = workbook.createFont();
        headingFont.setBold(true);
        headingFont.setFontHeightInPoints((short) 10);

        HSSFCellStyle defaultStyle = workbook.createCellStyle();
        defaultStyle.setAlignment(HorizontalAlignment.CENTER);
        defaultStyle.setFont(defaultFont);
        defaultStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        defaultStyle.setWrapText(true);

        HSSFCellStyle defaultRotate = workbook.createCellStyle();
        defaultRotate.cloneStyleFrom(defaultStyle);
        defaultRotate.setRotation((short) 90);

        HSSFCellStyle defaultDateRotate = workbook.createCellStyle();
        defaultDateRotate.cloneStyleFrom(defaultRotate);
        defaultDateRotate.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yy hh:mm"));

        HSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.cloneStyleFrom(defaultStyle);
        titleStyle.setFont(titleFont);

        HSSFCellStyle detailHeadingStyle = workbook.createCellStyle();
        detailHeadingStyle.cloneStyleFrom(defaultStyle);
        detailHeadingStyle.setAlignment(HorizontalAlignment.RIGHT);
        detailHeadingStyle.setFont(detailHeadingFont);

        HSSFCellStyle detailPrintStyle = workbook.createCellStyle();
        detailPrintStyle.cloneStyleFrom(defaultStyle);
        detailPrintStyle.setAlignment(HorizontalAlignment.LEFT);
        detailPrintStyle.setFont(detailPrintFont);

        HSSFCellStyle detailDateStyle = workbook.createCellStyle();
        detailDateStyle.cloneStyleFrom(detailPrintStyle);
        detailDateStyle.setAlignment(HorizontalAlignment.LEFT);
        detailDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yy hh:mm"));

        HSSFCellStyle headingDefaultStyle = workbook.createCellStyle();
        headingDefaultStyle.cloneStyleFrom(defaultStyle);
        headingDefaultStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIME.getIndex());
        headingDefaultStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headingDefaultStyle.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());

        HSSFCellStyle headingDefaultRotate = workbook.createCellStyle();
        headingDefaultRotate.cloneStyleFrom(headingDefaultStyle);
        headingDefaultRotate.setRotation((short) 90);

        HSSFCellStyle headingRiskStyle = workbook.createCellStyle();
        headingRiskStyle.cloneStyleFrom(headingDefaultStyle);
        headingRiskStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.ORANGE.getIndex());
        headingRiskStyle.setFillPattern(FillPatternType.BIG_SPOTS);

        HSSFCellStyle headingRiskRotate = workbook.createCellStyle();
        headingRiskRotate.cloneStyleFrom(headingRiskStyle);
        headingRiskRotate.setRotation((short) 90);

        HSSFCellStyle headingControlStyle = workbook.createCellStyle();
        headingControlStyle.cloneStyleFrom(headingDefaultStyle);
        headingControlStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.SKY_BLUE.getIndex());
        headingControlStyle.setFillPattern(FillPatternType.SQUARES);

        HSSFCellStyle headingControlRotate = workbook.createCellStyle();
        headingControlRotate.cloneStyleFrom(headingControlStyle);
        headingControlRotate.setRotation((short) 90);

        HSSFCellStyle headingCausalityStyle = workbook.createCellStyle();
        headingCausalityStyle.cloneStyleFrom(headingDefaultStyle);
        headingCausalityStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.YELLOW.getIndex());
        headingCausalityStyle.setFillPattern(FillPatternType.DIAMONDS);

        HSSFCellStyle headingCausalityRotate = workbook.createCellStyle();
        headingCausalityRotate.cloneStyleFrom(headingCausalityStyle);
        headingCausalityRotate.setRotation((short) 90);

        // Set default styles
        for (int i = 0; i < 27; i++) {
            sheet.setDefaultColumnStyle(i, defaultStyle);
        }

        // Generate frozen rows
        HSSFRow titleRow = sheet.createRow((short) 0);
        titleRow.setHeight((short) -1);
        CellRangeAddress titleBlock = new CellRangeAddress(0, 0, 0, 27);
        sheet.addMergedRegion(titleBlock);
        HSSFCell titleCell = titleRow.createCell((short) 0);
        titleCell.setCellStyle(titleStyle);
        titleCell.setCellValue("HazMate - Hazard Table Subset Export");
        if (complete) {
            titleCell.setCellValue("HazMate - Hazard Table Complete Export");
        }

        HSSFRow detailRow = sheet.createRow((short) 1);
        detailRow.setHeight((short) -1);
        CellRangeAddress detailTimeHeadingBlock = new CellRangeAddress(1, 1, 0, 1);
        sheet.addMergedRegion(detailTimeHeadingBlock);
        HSSFCell detailTimeHeading = detailRow.createCell(0);
        detailTimeHeading.setCellStyle(detailHeadingStyle);
        detailTimeHeading.setCellValue("Timestamp:");

        CellRangeAddress detailTimeBlock = new CellRangeAddress(1, 1, 2, 3);
        sheet.addMergedRegion(detailTimeBlock);
        HSSFCell detailTime = detailRow.createCell(2);
        detailTime.setCellStyle(detailDateStyle);
        detailTime.setCellValue(new Date());

        HSSFCell detailUserHeading = detailRow.createCell(4);
        detailUserHeading.setCellStyle(detailPrintStyle);
        detailUserHeading.setCellValue("User:");

        CellRangeAddress detailUserBlock = new CellRangeAddress(1, 1, 5, 11);
        sheet.addMergedRegion(detailUserBlock);
        HSSFCell detailUser = detailRow.createCell(5);
        detailUser.setCellStyle(detailPrintStyle);
        detailUser.setCellValue(getActiveUserName());

        CellRangeAddress detailDisclaimerBlock = new CellRangeAddress(1, 1, 12, 27);
        sheet.addMergedRegion(detailDisclaimerBlock);
        HSSFCell detailDisclaimer = detailRow.createCell(12);
        detailDisclaimer.setCellStyle(detailPrintStyle);
        detailDisclaimer.setCellValue("Exported (" + listToExport.size() + " entries out of " + listDbHazard.size() + ").");

        HSSFRow headingRow = sheet.createRow((short) 2);
        HSSFCell headingID = headingRow.createCell(0);
        headingID.setCellStyle(headingDefaultRotate);
        headingID.setCellValue("Hazard ID");
        sheet.setColumnWidth(0, 6 * 256);

        HSSFCell headingHD = headingRow.createCell(1);
        headingHD.setCellStyle(headingDefaultStyle);
        headingHD.setCellValue("Hazard Description");
        sheet.setColumnWidth(1, 24 * 256);

        HSSFCell headingSC = headingRow.createCell(2);
        headingSC.setCellStyle(headingDefaultStyle);
        headingSC.setCellValue("SBS Code(s)");
        sheet.setColumnWidth(2, 24 * 256);

        HSSFCell headingHL = headingRow.createCell(3);
        headingHL.setCellStyle(headingDefaultStyle);
        headingHL.setCellValue("Source Location");
        sheet.setColumnWidth(3, 16 * 256);

        HSSFCell headingSP = headingRow.createCell(4);
        headingSP.setCellStyle(headingDefaultRotate);
        headingSP.setCellValue("Source Project");
        sheet.setColumnWidth(4, 16 * 256);

        HSSFCell headingGS = headingRow.createCell(5);
        headingGS.setCellStyle(headingDefaultRotate);
        headingGS.setCellValue("Grade Separation");
        sheet.setColumnWidth(5, 6 * 256);

        HSSFCell headingCN = headingRow.createCell(6);
        headingCN.setCellStyle(headingDefaultRotate);
        headingCN.setCellValue("Construction Type");
        sheet.setColumnWidth(6, 6 * 256);

        HSSFCell headingCH = headingRow.createCell(7);
        headingCH.setCellStyle(headingDefaultRotate);
        headingCH.setCellValue("Change Type");
        sheet.setColumnWidth(7, 6 * 256);

        HSSFCell headingHA = headingRow.createCell(8);
        headingHA.setCellStyle(headingDefaultRotate);
        headingHA.setCellValue("Activity Type");
        sheet.setColumnWidth(8, 6 * 256);

        HSSFCell headingHT = headingRow.createCell(9);
        headingHT.setCellStyle(headingDefaultRotate);
        headingHT.setCellValue("Hazard Type");
        sheet.setColumnWidth(9, 6 * 256);

        HSSFCell headingHC = headingRow.createCell(10);
        headingHC.setCellStyle(headingDefaultStyle);
        headingHC.setCellValue("Hazard Context");
        sheet.setColumnWidth(10, 16 * 256);

        HSSFCell headingHO = headingRow.createCell(11);
        headingHO.setCellStyle(headingDefaultRotate);
        headingHO.setCellValue("Hazard Owner");
        sheet.setColumnWidth(11, 6 * 256);

        HSSFCell headingCA = headingRow.createCell(12);
        headingCA.setCellStyle(headingCausalityStyle);
        headingCA.setCellValue("Immediate Causes");
        sheet.setColumnWidth(12, 24 * 256);

        HSSFCell headingCQ = headingRow.createCell(13);
        headingCQ.setCellStyle(headingCausalityStyle);
        headingCQ.setCellValue("Immediate Consequences");
        sheet.setColumnWidth(13, 24 * 256);

        HSSFCell headingRC = headingRow.createCell(14);
        headingRC.setCellStyle(headingRiskStyle);
        headingRC.setCellValue("Risk Class");
        sheet.setColumnWidth(14, 16 * 256);

        HSSFCell headingRS = headingRow.createCell(15);
        headingRS.setCellStyle(headingRiskRotate);
        headingRS.setCellValue("Risk Severity");
        sheet.setColumnWidth(15, 6 * 256);

        HSSFCell headingRF = headingRow.createCell(16);
        headingRF.setCellStyle(headingRiskRotate);
        headingRF.setCellValue("Risk Frequency");
        sheet.setColumnWidth(16, 6 * 256);

        HSSFCell headingRR = headingRow.createCell(17);
        headingRR.setCellStyle(headingRiskRotate);
        headingRR.setCellValue("Risk Score");
        sheet.setColumnWidth(17, 6 * 256);

        HSSFCell headingCD = headingRow.createCell(18);
        headingCD.setCellStyle(headingControlStyle);
        headingCD.setCellValue("Control Description");
        sheet.setColumnWidth(18, 24 * 256);

        HSSFCell headingCT = headingRow.createCell(19);
        headingCT.setCellStyle(headingControlRotate);
        headingCT.setCellValue("Control Type");
        sheet.setColumnWidth(19, 6 * 256);

        HSSFCell headingCO = headingRow.createCell(20);
        headingCO.setCellStyle(headingControlRotate);
        headingCO.setCellValue("Control Owner");
        sheet.setColumnWidth(20, 6 * 256);

        HSSFCell headingCR = headingRow.createCell(21);
        headingCR.setCellStyle(headingControlRotate);
        headingCR.setCellValue("Control Recommendation");
        sheet.setColumnWidth(21, 6 * 256);

        HSSFCell headingCJ = headingRow.createCell(22);
        headingCJ.setCellStyle(headingControlStyle);
        headingCJ.setCellValue("Control Justification");
        sheet.setColumnWidth(22, 24 * 256);

        HSSFCell headingHS = headingRow.createCell(23);
        headingHS.setCellStyle(headingDefaultRotate);
        headingHS.setCellValue("Hazard Status");
        sheet.setColumnWidth(23, 6 * 256);

        HSSFCell headingDT = headingRow.createCell(24);
        headingDT.setCellStyle(headingDefaultRotate);
        headingDT.setCellValue("Date of Entry");
        sheet.setColumnWidth(24, 6 * 256);

        HSSFCell headingHW = headingRow.createCell(25);
        headingHW.setCellStyle(headingDefaultStyle);
        headingHW.setCellValue("Source Workshop");
        sheet.setColumnWidth(25, 16 * 256);

        HSSFCell headingRD = headingRow.createCell(26);
        headingRD.setCellStyle(headingDefaultRotate);
        headingRD.setCellValue("Hazard Review Due?");
        sheet.setColumnWidth(26, 6 * 256);

        HSSFCell headingHM = headingRow.createCell(27);
        headingHM.setCellStyle(headingDefaultStyle);
        headingHM.setCellValue("Hazard Comments");
        sheet.setColumnWidth(27, 24 * 256);

        // Freeze header columns
        sheet.createFreezePane(0, 3);

        // Start entering data
        // Need to use a variable to count how the merged cells add to the row count
        int rowcount = 0;
        for (int i = 0; i < listToExport.size(); i++) {
            HSSFRow entryRow = sheet.createRow((short) i + rowcount + 3);
            HSSFCell entryID = entryRow.createCell(0);
            entryID.setCellValue(listToExport.get(i).getHazardId());
            entryID.setCellStyle(defaultRotate);
            HSSFCell entryHD = entryRow.createCell(1);
            entryHD.setCellValue(listToExport.get(i).getHazardDescription());
            HSSFCell entrySC = entryRow.createCell(2);
            if (viewSbsCondensed(listToExport.get(i).getHazardId()).size() > 0) {
                String listSC = getNodeLastById(viewSbsCondensed(listToExport.get(i).getHazardId()).get(0));
                if (viewSbsCondensed(listToExport.get(i).getHazardId()).size() > 1) {
                    for (int j = 1; j < viewSbsCondensed(listToExport.get(i).getHazardId()).size(); j++) {
                        listSC += "\n" + (getNodeLastById(viewSbsCondensed(listToExport.get(i).getHazardId()).get(j)));
                    }
                }
                entrySC.setCellValue(listSC);
            }
            HSSFCell entryHL = entryRow.createCell(3);
            entryHL.setCellValue(listToExport.get(i).getHazardLocation().getLocationName());
            HSSFCell entryHP = entryRow.createCell(4);
            entryHP.setCellValue(listToExport.get(i).getHazardLocation().getProjectId().getProjectName());
            HSSFCell entryGS = entryRow.createCell(5);
            entryGS.setCellValue(listToExport.get(i).getHazardLocation().getLocationGradeSeparation().getGradeSeparationName());
            entryGS.setCellStyle(defaultRotate);
            HSSFCell entryCN = entryRow.createCell(6);
            entryCN.setCellValue(listToExport.get(i).getHazardLocation().getLocationConstructionType().getConstructionTypeName());
            entryCN.setCellStyle(defaultRotate);
            HSSFCell entryCH = entryRow.createCell(7);
            entryCH.setCellValue(listToExport.get(i).getHazardLocation().getLocationChangeType().getChangeTypeName());
            entryCH.setCellStyle(defaultRotate);
            HSSFCell entryHA = entryRow.createCell(8);
            entryHA.setCellValue(listToExport.get(i).getHazardActivity().getActivityName());
            entryHA.setCellStyle(defaultRotate);
            HSSFCell entryHT = entryRow.createCell(9);
            entryHT.setCellValue(listToExport.get(i).getHazardTypeId().getHazardTypeName());
            entryHT.setCellStyle(defaultRotate);
            HSSFCell entryHC = entryRow.createCell(10);
            entryHC.setCellValue(listToExport.get(i).getHazardContextId().getHazardContextName());
            HSSFCell entryHO = entryRow.createCell(11);
            entryHO.setCellValue(listToExport.get(i).getOwnerId().getOwnerName());
            entryHO.setCellStyle(defaultRotate);
            HSSFCell entryCA = entryRow.createCell(12);
            String listCA = viewHazardCause(listToExport.get(i).getHazardId()).get(0);
            if (viewHazardCause(listToExport.get(i).getHazardId()).size() > 1) {
                for (int j = 0; j < viewHazardCause(listToExport.get(i).getHazardId()).size(); j++) {
                    listCA += "\n" + (viewHazardCause(listToExport.get(i).getHazardId()).get(j));
                }
            }
            entryCA.setCellValue(listCA);
            HSSFCell entryCQ = entryRow.createCell(13);
            String listCQ = viewHazardConsequence(listToExport.get(i).getHazardId()).get(0);
            if (viewHazardCause(listToExport.get(i).getHazardId()).size() > 1) {
                for (int j = 0; j < viewHazardConsequence(listToExport.get(i).getHazardId()).size(); j++) {
                    listCQ += "\n" + (viewHazardConsequence(listToExport.get(i).getHazardId()).get(j));
                }
            }
            entryCQ.setCellValue(listCQ);
            HSSFCell entryRC = entryRow.createCell(14);
            entryRC.setCellValue(listToExport.get(i).getRiskClassId().getRiskClassName());
            HSSFCell entryRS = entryRow.createCell(15);
            entryRS.setCellValue(listToExport.get(i).getRiskSeverityId().getSeverityScore());
            entryRS.setCellStyle(defaultRotate);
            HSSFCell entryRF = entryRow.createCell(16);
            entryRF.setCellValue(listToExport.get(i).getRiskFrequencyId().getFrequencyScore());
            entryRF.setCellStyle(defaultRotate);
            HSSFCell entryRR = entryRow.createCell(17);
            entryRR.setCellValue(listToExport.get(i).getRiskScore());
            entryRR.setCellStyle(defaultRotate);
            int rowcurrent = i + rowcount + 3;
            if (viewControlHazard(listToExport.get(i).getHazardId()).size() > 0) {
                HSSFCell entryCD = entryRow.createCell(18);
                entryCD.setCellValue(viewControlHazard(listToExport.get(i).getHazardId()).get(0));
                HSSFCell entryCT = entryRow.createCell(19);
                entryCT.setCellStyle(defaultRotate);
                entryCT.setCellValue(controlType.get(0));
                HSSFCell entryCO = entryRow.createCell(20);
                entryCO.setCellStyle(defaultRotate);
                entryCO.setCellValue(controlOwner.get(0));
                HSSFCell entryCR = entryRow.createCell(21);
                entryCR.setCellStyle(defaultRotate);
                entryCR.setCellValue(controlRecommend.get(0));
                HSSFCell entryCJ = entryRow.createCell(22);
                entryCJ.setCellValue(controlJustify.get(0));
                if (viewControlHazard(listToExport.get(i).getHazardId()).size() > 1) {
                    for (int j = 1; j < viewControlHazard(listToExport.get(i).getHazardId()).size(); j++) {
                        HSSFRow controlRow = sheet.createRow(i + j + rowcount + 3);
                        HSSFCell controlCD = controlRow.createCell(18);
                        controlCD.setCellValue(viewControlHazard(listToExport.get(i).getHazardId()).get(j));
                        HSSFCell controlCT = controlRow.createCell(19);
                        controlCT.setCellStyle(defaultRotate);
                        controlCT.setCellValue(controlType.get(j));
                        HSSFCell controlCO = controlRow.createCell(20);
                        controlCO.setCellStyle(defaultRotate);
                        controlCO.setCellValue(controlOwner.get(j));
                        HSSFCell controlCR = controlRow.createCell(21);
                        controlCR.setCellStyle(defaultRotate);
                        controlCR.setCellValue(controlRecommend.get(j));
                        HSSFCell controlCJ = controlRow.createCell(22);
                        controlCJ.setCellValue(controlJustify.get(j));
                        rowcount++;
                    }
                    for (int j = 0; j < 27; j++) {
                        if (j < 17 | j > 21) {
                            CellRangeAddress cellBlock = new CellRangeAddress(rowcurrent, rowcurrent + rowcount, j, j);
                            sheet.addMergedRegion(cellBlock);
                        }
                    }
                }
            }
            HSSFCell entryHS = entryRow.createCell(23);
            entryHS.setCellStyle(defaultRotate);
            entryHS.setCellValue(listToExport.get(i).getHazardStatusId().getHazardStatusName());
            HSSFCell entryDT = entryRow.createCell(24);
            entryDT.setCellStyle(defaultDateRotate);
            entryDT.setCellValue(listToExport.get(i).getHazardDate());
            HSSFCell entryHW = entryRow.createCell(25);
            entryHW.setCellStyle(defaultRotate);
            entryHW.setCellValue(listToExport.get(i).getHazardWorkshop());
            HSSFCell entryRD = entryRow.createCell(26);
            entryRD.setCellValue(listToExport.get(i).getHazardReview());
            HSSFCell entryHM = entryRow.createCell(27);
            entryHM.setCellValue(listToExport.get(i).getHazardComment());
        }

        try {
            // Prepare response.
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.setResponseContentType("application/vnd.ms-excel");
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

            // Write file to response body.
            workbook.write(externalContext.getResponseOutputStream());

            // Inform JSF that response is completed and it thus doesn't have to navigate.
            facesContext.responseComplete();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(hazardTable_MB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(hazardTable_MB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<String> viewHazardCause(String hazardId) {
        listDbHazardCause = dbHazardFacade.getHazardCause(hazardId);
        causeDescription = new ArrayList<>();
        for (int i = 0; i < listDbHazardCause.size(); i++) {
            hazardCauseObject = listDbHazardCause.get(i);
            causeObject = hazardCauseObject.getDbCause();
            causeDescription.add(causeObject.getCauseDescription());
        }
        return causeDescription;
    }

    public List<String> viewHazardConsequence(String hazardId) {
        listDbHazardConsequence = dbHazardFacade.getHazardConsequence(hazardId);
        consequenceDescription = new ArrayList<>();
        for (int i = 0; i < listDbHazardConsequence.size(); i++) {
            hazardConsequenceObject = listDbHazardConsequence.get(i);
            consequenceObject = hazardConsequenceObject.getDbConsequence();
            consequenceDescription.add(consequenceObject.getConsequenceDescription());
        }
        return consequenceDescription;
    }

    public String getNodeNameById(String nodeId) {
        treeHazardSbsList = new ArrayList<>();
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

    public String getNodeLastById(String nodeId) {
        treeHazardSbsList = new ArrayList<>();
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
        return nodeName;
    }

    public void constructSearchObject() {
        // Initialising a couple of variables
        boolean containsText = false;
        boolean containsTree = false;
        treeCheckedNodesList = new ArrayList<>();
        searchCompositeList = new ArrayList<>();

        // Clearing widgets from current search fields
        int currentWidgetCount = 0;
        List<String> currentWidgets;
        for (int i = 0; i < DMSearch.getColumnCount(); i++) {
            while (DMSearch.getColumn(i).getWidgetCount() > 0) {
                currentWidgets = DMSearch.getColumn(i).getWidgets();
                DMSearch.getColumn(i).removeWidget(currentWidgets.get(0));
            }
        }

        // Start the grind of finding each entry to each field
        if (!getSelectedHazardDescription().isEmpty()) {
            containsText = true;
            searchObject searchObject = new searchObject();
            searchObject.setFieldName("hazardDescription");
            searchObject.setFieldType("string");
            searchObject.setRelationType("like");
            searchObject.setEntity1Name("DbHazard");
            searchObject.setUserInput(getSelectedHazardDescription());
            searchCompositeList.add(searchObject);
            for (int i = 0; i < DMSearch.getColumnCount(); i++) {
                if (DMSearch.getColumn(i).getWidgetCount() > currentWidgetCount) {
                    currentWidgetCount = DMSearch.getColumn(i).getWidgetCount();
                } else {
                    DMSearch.getColumn(i).addWidget("HDPanel");
                    break;
                }
            }
        }
        if (!getSearchedCauses().isEmpty()) {
            containsText = true;
            searchObject searchObject = new searchObject();
            searchObject.setFieldName("causeDescription");
            searchObject.setFieldType("string");
            searchObject.setRelationType("like");
            searchObject.setEntity1Name("DbCause");
            searchObject.setUserInput(getSearchedCauses());
            searchCompositeList.add(searchObject);
            for (int i = 0; i < DMSearch.getColumnCount(); i++) {
                if (DMSearch.getColumn(i).getWidgetCount() > currentWidgetCount) {
                    currentWidgetCount = DMSearch.getColumn(i).getWidgetCount();
                } else {
                    DMSearch.getColumn(i).addWidget("CAPanel");
                    break;
                }
            }
        }
        if (!getSearchedConsequences().isEmpty()) {
            containsText = true;
            searchObject searchObject = new searchObject();
            searchObject.setFieldName("consequenceDescription");
            searchObject.setFieldType("string");
            searchObject.setRelationType("like");
            searchObject.setEntity1Name("DbConsequence");
            searchObject.setUserInput(getSearchedConsequences());
            searchCompositeList.add(searchObject);
            for (int i = 0; i < DMSearch.getColumnCount(); i++) {
                if (DMSearch.getColumn(i).getWidgetCount() > currentWidgetCount) {
                    currentWidgetCount = DMSearch.getColumn(i).getWidgetCount();
                } else {
                    DMSearch.getColumn(i).addWidget("CQPanel");
                    break;
                }
            }
        }
        if (getTNSelectedNodes() != null && getTNSelectedNodes().length > 0) {
            containsTree = true;
            treeCheckedNodesList = new ArrayList<>();
            DbtreeLevel1 tmpTreeNode = dbtreeLevel1Facade.findByName(TNRoot.getChildren().get(0).toString());
            Integer rootId = tmpTreeNode.getTreeLevel1Index();

            for (TreeNode node : getTNSelectedNodes()) {
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

            for (int i = 0; i < DMSearch.getColumnCount(); i++) {
                if (DMSearch.getColumn(i).getWidgetCount() > currentWidgetCount) {
                    currentWidgetCount = DMSearch.getColumn(i).getWidgetCount();
                } else {
                    DMSearch.getColumn(i).addWidget("SCPanel");
                    break;
                }
            }
        }
        if (getSelectedLocations() != null && getSelectedLocations().length > 0) {
            containsText = true;
            String userInputString = "";
            searchObject searchObject = new searchObject();
            searchObject.setFieldName("locationId");
            searchObject.setFieldType("int");
            searchObject.setRelationType("in");
            searchObject.setEntity1Name("DbHazard");
            searchObject.setEntity2Name("hazardLocation");
            for (String selectedOption : getSelectedLocations()) {
                userInputString += selectedOption + ",";
            }
            searchObject.setUserInput(userInputString.substring(0, userInputString.length() - 1));
            searchCompositeList.add(searchObject);
            for (int i = 0; i < DMSearch.getColumnCount(); i++) {
                if (DMSearch.getColumn(i).getWidgetCount() > currentWidgetCount) {
                    currentWidgetCount = DMSearch.getColumn(i).getWidgetCount();
                } else {
                    DMSearch.getColumn(i).addWidget("HLPanel");
                    break;
                }
            }
        }
        if (getSelectedProjects() != null && getSelectedProjects().length > 0) {
            containsText = true;
            String userInputString = "";
            searchObject searchObject = new searchObject();
            searchObject.setFieldName("projectId");
            searchObject.setFieldType("int");
            searchObject.setRelationType("in");
            searchObject.setEntity1Name("DbHazard");
            searchObject.setEntity2Name("hazardLocation");
            searchObject.setEntity3Name("projectId");
            for (String selectedOption : getSelectedProjects()) {
                userInputString += selectedOption + ",";
            }
            searchObject.setUserInput(userInputString.substring(0, userInputString.length() - 1));
            searchCompositeList.add(searchObject);
            for (int i = 0; i < DMSearch.getColumnCount(); i++) {
                if (DMSearch.getColumn(i).getWidgetCount() > currentWidgetCount) {
                    currentWidgetCount = DMSearch.getColumn(i).getWidgetCount();
                } else {
                    DMSearch.getColumn(i).addWidget("HPPanel");
                    break;
                }
            }
        }
        if (getSelectedGradeSeparations() != null && getSelectedGradeSeparations().length > 0) {
            containsText = true;
            String userInputString = "";
            searchObject searchObject = new searchObject();
            searchObject.setFieldName("gradeSeparationId");
            searchObject.setFieldType("int");
            searchObject.setRelationType("in");
            searchObject.setEntity1Name("DbHazard");
            searchObject.setEntity2Name("hazardLocation");
            searchObject.setEntity3Name("locationGradeSeparation");
            for (String selectedOption : getSelectedGradeSeparations()) {
                userInputString += selectedOption + ",";
            }
            searchObject.setUserInput(userInputString.substring(0, userInputString.length() - 1));
            searchCompositeList.add(searchObject);
            for (int i = 0; i < DMSearch.getColumnCount(); i++) {
                if (DMSearch.getColumn(i).getWidgetCount() > currentWidgetCount) {
                    currentWidgetCount = DMSearch.getColumn(i).getWidgetCount();
                } else {
                    DMSearch.getColumn(i).addWidget("GSPanel");
                    break;
                }
            }
        }
        if (getSelectedConstructionTypes() != null && getSelectedConstructionTypes().length > 0) {
            containsText = true;
            String userInputString = "";
            searchObject searchObject = new searchObject();
            searchObject.setFieldName("constructionTypeId");
            searchObject.setFieldType("int");
            searchObject.setRelationType("in");
            searchObject.setEntity1Name("DbHazard");
            searchObject.setEntity2Name("hazardLocation");
            searchObject.setEntity3Name("locationConstructionType");
            for (String selectedOption : getSelectedConstructionTypes()) {
                userInputString += selectedOption + ",";
            }
            searchObject.setUserInput(userInputString.substring(0, userInputString.length() - 1));
            searchCompositeList.add(searchObject);
            for (int i = 0; i < DMSearch.getColumnCount(); i++) {
                if (DMSearch.getColumn(i).getWidgetCount() > currentWidgetCount) {
                    currentWidgetCount = DMSearch.getColumn(i).getWidgetCount();
                } else {
                    DMSearch.getColumn(i).addWidget("CNPanel");
                    break;
                }
            }
        }
        if (getSelectedChangeTypes() != null && getSelectedChangeTypes().length > 0) {
            containsText = true;
            String userInputString = "";
            searchObject searchObject = new searchObject();
            searchObject.setFieldName("changeTypeId");
            searchObject.setFieldType("int");
            searchObject.setRelationType("in");
            searchObject.setEntity1Name("DbHazard");
            searchObject.setEntity2Name("hazardLocation");
            searchObject.setEntity3Name("locationChangeType");
            for (String selectedOption : getSelectedChangeTypes()) {
                userInputString += selectedOption + ",";
            }
            searchObject.setUserInput(userInputString.substring(0, userInputString.length() - 1));
            searchCompositeList.add(searchObject);
            for (int i = 0; i < DMSearch.getColumnCount(); i++) {
                if (DMSearch.getColumn(i).getWidgetCount() > currentWidgetCount) {
                    currentWidgetCount = DMSearch.getColumn(i).getWidgetCount();
                } else {
                    DMSearch.getColumn(i).addWidget("CHPanel");
                    break;
                }
            }
        }
        if (getSelectedHazardActivities() != null && getSelectedHazardActivities().length > 0) {
            containsText = true;
            String userInputString = "";
            searchObject searchObject = new searchObject();
            searchObject.setFieldName("activityId");
            searchObject.setFieldType("int");
            searchObject.setRelationType("in");
            searchObject.setEntity1Name("DbHazard");
            searchObject.setEntity2Name("hazardActivity");
            for (String selectedOption : getSelectedHazardActivities()) {
                userInputString += selectedOption + ",";
            }
            searchObject.setUserInput(userInputString.substring(0, userInputString.length() - 1));
            searchCompositeList.add(searchObject);
            for (int i = 0; i < DMSearch.getColumnCount(); i++) {
                if (DMSearch.getColumn(i).getWidgetCount() > currentWidgetCount) {
                    currentWidgetCount = DMSearch.getColumn(i).getWidgetCount();
                } else {
                    DMSearch.getColumn(i).addWidget("HAPanel");
                    break;
                }
            }
        }
        if (getSelectedHazardContexts() != null && getSelectedHazardContexts().length > 0) {
            containsText = true;
            String userInputString = "";
            searchObject searchObject = new searchObject();
            searchObject.setFieldName("hazardContextId");
            searchObject.setFieldType("int");
            searchObject.setRelationType("in");
            searchObject.setEntity1Name("DbHazard");
            searchObject.setEntity2Name("hazardContextId");
            for (String selectedOption : getSelectedHazardContexts()) {
                userInputString += selectedOption + ",";
            }
            searchObject.setUserInput(userInputString.substring(0, userInputString.length() - 1));
            searchCompositeList.add(searchObject);
            for (int i = 0; i < DMSearch.getColumnCount(); i++) {
                if (DMSearch.getColumn(i).getWidgetCount() > currentWidgetCount) {
                    currentWidgetCount = DMSearch.getColumn(i).getWidgetCount();
                } else {
                    DMSearch.getColumn(i).addWidget("HCPanel");
                    break;
                }
            }
        }
        if (getSelectedHazardTypes() != null && getSelectedHazardTypes().length > 0) {
            containsText = true;
            String userInputString = "";
            searchObject searchObject = new searchObject();
            searchObject.setFieldName("hazardTypeId");
            searchObject.setFieldType("int");
            searchObject.setRelationType("in");
            searchObject.setEntity1Name("DbHazard");
            searchObject.setEntity2Name("hazardTypeId");
            for (String selectedOption : getSelectedHazardTypes()) {
                userInputString += selectedOption + ",";
            }
            searchObject.setUserInput(userInputString.substring(0, userInputString.length() - 1));
            searchCompositeList.add(searchObject);
            for (int i = 0; i < DMSearch.getColumnCount(); i++) {
                if (DMSearch.getColumn(i).getWidgetCount() > currentWidgetCount) {
                    currentWidgetCount = DMSearch.getColumn(i).getWidgetCount();
                } else {
                    DMSearch.getColumn(i).addWidget("HTPanel");
                    break;
                }
            }
        }
        if (getSelectedHazardStatuses() != null && getSelectedHazardStatuses().length > 0) {
            containsText = true;
            String userInputString = "";
            searchObject searchObject = new searchObject();
            searchObject.setFieldName("hazardStatusId");
            searchObject.setFieldType("int");
            searchObject.setRelationType("in");
            searchObject.setEntity1Name("DbHazard");
            searchObject.setEntity2Name("hazardStatusId");
            for (String selectedOption : getSelectedHazardStatuses()) {
                userInputString += selectedOption + ",";
            }
            searchObject.setUserInput(userInputString.substring(0, userInputString.length() - 1));
            searchCompositeList.add(searchObject);
            for (int i = 0; i < DMSearch.getColumnCount(); i++) {
                if (DMSearch.getColumn(i).getWidgetCount() > currentWidgetCount) {
                    currentWidgetCount = DMSearch.getColumn(i).getWidgetCount();
                } else {
                    DMSearch.getColumn(i).addWidget("HSPanel");
                    break;
                }
            }
        }
        if (getSelectedHazardOwners() != null && getSelectedHazardOwners().length > 0) {
            containsText = true;
            String userInputString = "";
            searchObject searchObject = new searchObject();
            searchObject.setFieldName("ownerId");
            searchObject.setFieldType("int");
            searchObject.setRelationType("in");
            searchObject.setEntity1Name("DbHazard");
            searchObject.setEntity2Name("ownerId");
            for (String selectedOption : getSelectedHazardOwners()) {
                userInputString += selectedOption + ",";
            }
            searchObject.setUserInput(userInputString.substring(0, userInputString.length() - 1));
            searchCompositeList.add(searchObject);
            for (int i = 0; i < DMSearch.getColumnCount(); i++) {
                if (DMSearch.getColumn(i).getWidgetCount() > currentWidgetCount) {
                    currentWidgetCount = DMSearch.getColumn(i).getWidgetCount();
                } else {
                    DMSearch.getColumn(i).addWidget("HOPanel");
                    break;
                }
            }
        }
        if (!getSelectedHazardComments().isEmpty()) {
            containsText = true;
            searchObject searchObject = new searchObject();
            searchObject.setFieldName("hazardComment");
            searchObject.setFieldType("string");
            searchObject.setRelationType("like");
            searchObject.setEntity1Name("DbHazard");
            searchObject.setUserInput(getSelectedHazardComments());
            searchCompositeList.add(searchObject);
            for (int i = 0; i < DMSearch.getColumnCount(); i++) {
                if (DMSearch.getColumn(i).getWidgetCount() > currentWidgetCount) {
                    currentWidgetCount = DMSearch.getColumn(i).getWidgetCount();
                } else {
                    DMSearch.getColumn(i).addWidget("CMPanel");
                    break;
                }
            }
        }
        if (!getSelectedControlDescription().isEmpty()) {
            containsText = true;
            searchObject searchObject = new searchObject();
            searchObject.setFieldName("controlDescription");
            searchObject.setFieldType("string");
            searchObject.setRelationType("like");
            searchObject.setEntity1Name("DbControl");
            searchObject.setUserInput(getSelectedControlDescription());
            searchCompositeList.add(searchObject);
            for (int i = 0; i < DMSearch.getColumnCount(); i++) {
                if (DMSearch.getColumn(i).getWidgetCount() > currentWidgetCount) {
                    currentWidgetCount = DMSearch.getColumn(i).getWidgetCount();
                } else {
                    DMSearch.getColumn(i).addWidget("CDPanel");
                    break;
                }
            }
        }
        if (getSelectedControlTypes() != null && getSelectedControlTypes().length > 0) {
            containsText = true;
            String userInputString = "";
            searchObject searchObject = new searchObject();
            searchObject.setFieldName("controlHierarchyId");
            searchObject.setFieldType("int");
            searchObject.setRelationType("in");
            searchObject.setEntity1Name("DbControl");
            searchObject.setEntity2Name("controlHierarchyId");
            for (String selectedOption : getSelectedControlTypes()) {
                userInputString += selectedOption + ",";
            }
            searchObject.setUserInput(userInputString.substring(0, userInputString.length() - 1));
            searchCompositeList.add(searchObject);
            for (int i = 0; i < DMSearch.getColumnCount(); i++) {
                if (DMSearch.getColumn(i).getWidgetCount() > currentWidgetCount) {
                    currentWidgetCount = DMSearch.getColumn(i).getWidgetCount();
                } else {
                    DMSearch.getColumn(i).addWidget("CTPanel");
                    break;
                }
            }
        }
        if (getSelectedControlOwners() != null && getSelectedControlOwners().length > 0) {
            containsText = true;
            String userInputString = "";
            searchObject searchObject = new searchObject();
            searchObject.setFieldName("ownerId");
            searchObject.setFieldType("int");
            searchObject.setRelationType("in");
            searchObject.setEntity1Name("DbControl");
            searchObject.setEntity2Name("ownerId");
            for (String selectedOption : getSelectedControlOwners()) {
                userInputString += selectedOption + ",";
            }
            searchObject.setUserInput(userInputString.substring(0, userInputString.length() - 1));
            searchCompositeList.add(searchObject);
            for (int i = 0; i < DMSearch.getColumnCount(); i++) {
                if (DMSearch.getColumn(i).getWidgetCount() > currentWidgetCount) {
                    currentWidgetCount = DMSearch.getColumn(i).getWidgetCount();
                } else {
                    DMSearch.getColumn(i).addWidget("COPanel");
                    break;
                }
            }
        }
        if (getSelectedControlRecommendations() != null && getSelectedControlRecommendations().length > 0) {
            containsText = true;
            String userInputString = "";
            searchObject searchObject = new searchObject();
            searchObject.setFieldName("controlRecommendId");
            searchObject.setFieldType("int");
            searchObject.setRelationType("in");
            searchObject.setEntity1Name("DbControlHazard");
            searchObject.setEntity2Name("controlRecommendId");
            for (String selectedOption : getSelectedControlRecommendations()) {
                userInputString += selectedOption + ",";
            }
            searchObject.setUserInput(userInputString.substring(0, userInputString.length() - 1));
            searchCompositeList.add(searchObject);
            for (int i = 0; i < DMSearch.getColumnCount(); i++) {
                if (DMSearch.getColumn(i).getWidgetCount() > currentWidgetCount) {
                    currentWidgetCount = DMSearch.getColumn(i).getWidgetCount();
                } else {
                    DMSearch.getColumn(i).addWidget("CRPanel");
                    break;
                }
            }
        }
        if (!getSelectedControlJustification().isEmpty()) {
            containsText = true;
            searchObject searchObject = new searchObject();
            searchObject.setFieldName("controlJustify");
            searchObject.setFieldType("string");
            searchObject.setRelationType("like");
            searchObject.setEntity1Name("DbControlHazard");
            searchObject.setUserInput(getSelectedControlJustification());
            searchCompositeList.add(searchObject);
            for (int i = 0; i < DMSearch.getColumnCount(); i++) {
                if (DMSearch.getColumn(i).getWidgetCount() > currentWidgetCount) {
                    currentWidgetCount = DMSearch.getColumn(i).getWidgetCount();
                } else {
                    DMSearch.getColumn(i).addWidget("CJPanel");
                    break;
                }
            }
        }
        if (containsText & containsTree) {
            searchDbHazard = dbHazardFacade.findHazardsByFieldsAndSbs(searchCompositeList, treeCheckedNodesList);
        } else if (containsText & !containsTree) {
            searchDbHazard = dbHazardFacade.findHazardsByFields(searchCompositeList);
        } else if (!containsText & containsTree) {
            searchDbHazard = dbHazardFacade.findHazardsBySbs(treeCheckedNodesList);
        } else {
            searchDbHazard = dbHazardFacade.findAllHazards();
        }
        if (!searchDbHazard.isEmpty()) {
            setIsTableEmpty(false);
        } else {
            setIsTableEmpty(true);
        }
    }

    public void createTree() {
        DbtreeLevel1 tmpResultObjLevel1 = dbtreeLevel1Facade.find(1); //This line might modified to get a dynamic tree
        TNRoot = new DefaultTreeNode("Root", null);
        if (!tmpResultObjLevel1.getTreeLevel1Name().isEmpty()) {
            TreeNode nodeLevel1 = new DefaultTreeNode(tmpResultObjLevel1.getTreeLevel1Name(), TNRoot);
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

    public void onToggle(ToggleEvent e) {
        getToggleColumns().set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
        currentPreferencesPK.setPageName("Hazard Search");
        currentPreferencesPK.setTableName("Hazard Table");
        currentPreferencesPK.setUserId(activeUser.getUserId());
        currentPreferences.setDbuserPreferencesPK(currentPreferencesPK);
        currentPreferences.setUserPreferences(getToggleColumns().toString().substring(1, getToggleColumns().toString().length() - 1));
        if (getUserPreferences().isEmpty()) {
            dbuserPreferencesFacade.create(currentPreferences);
        } else {
            dbuserPreferencesFacade.edit(currentPreferences);
        }
        setUserPreferences(dbuserPreferencesFacade.getSpecificPreference(activeUser.getUserId(), "Hazard Search", "Hazard Table"));
    }

    public void resetFields() {
        List<String> currentWidgets;
        for (int i = 0; i < DMSearch.getColumnCount(); i++) {
            while (DMSearch.getColumn(i).getWidgetCount() > 0) {
                currentWidgets = DMSearch.getColumn(i).getWidgets();
                DMSearch.getColumn(i).removeWidget(currentWidgets.get(0));
            }
        }
        setTNSelectedNodes(null);
        setTreeCheckedNodesList(null);
        setSelectedHazardDescription(null);
        setSearchedCauses(null);
        setSearchedConsequences(null);
        setSelectedLocations(null);
        setSelectedProjects(null);
        setSelectedGradeSeparations(null);
        setSelectedConstructionTypes(null);
        setSelectedChangeTypes(null);
        setSelectedHazardActivities(null);
        setSelectedHazardContexts(null);
        setSelectedHazardTypes(null);
        setSelectedHazardStatuses(null);
        setSelectedHazardOwners(null);
        setSelectedHazardComments(null);
        setSelectedControlDescription(null);
        setSelectedControlTypes(null);
        setSelectedControlOwners(null);
        setSelectedControlRecommendations(null);
        setSelectedControlJustification(null);
        setIsTableEmpty(true);
    }

    @SuppressWarnings("unchecked")
    public void onNodeSelect(SelectEvent event) {
        MMSelectedNode = (MindmapNode) event.getObject();
        MMRootData = new ArrayList<>();
        if (MMSelectedNode.getChildren().isEmpty()) {
            if (MMSelectedNode.getLabel().startsWith("Control:")) {
                MMRootData = (List<String>) MMSelectedNode.getData();
                MMSelectedNode.addNode(new DefaultMindmapNode("ID: " + MMRootData.get(0), MMRootData.get(0), "FFBA00", true));
                MMSelectedNode.addNode(new DefaultMindmapNode("Description: " + MMRootData.get(1), MMRootData.get(1), "FFB864", true));
                MMSelectedNode.addNode(new DefaultMindmapNode("Type: " + MMRootData.get(2), MMRootData.get(2), "FFA539", true));
                MMSelectedNode.addNode(new DefaultMindmapNode("Owner: " + MMRootData.get(3), MMRootData.get(3), "C66C00", true));
                MMSelectedNode.addNode(new DefaultMindmapNode("Recommendation: " + MMRootData.get(4), MMRootData.get(4), "9B5400", true));
                MMSelectedNode.addNode(new DefaultMindmapNode("Justification: " + MMRootData.get(5), MMRootData.get(5), "FF3900", true));
            } else if (MMSelectedNode.getLabel().startsWith("Cause:")) {
                MMRootData = (List<String>) MMSelectedNode.getData();
                MMSelectedNode.addNode(new DefaultMindmapNode("ID: " + MMRootData.get(0), MMRootData.get(0), "0370AB", true));
                MMSelectedNode.addNode(new DefaultMindmapNode("Description: " + MMRootData.get(1), MMRootData.get(1), "2B9AD6", true));
            } else if (MMSelectedNode.getLabel().startsWith("Consequence:")) {
                MMRootData = (List<String>) MMSelectedNode.getData();
                MMSelectedNode.addNode(new DefaultMindmapNode("ID: " + MMRootData.get(0), MMRootData.get(0), "0370AB", true));
                MMSelectedNode.addNode(new DefaultMindmapNode("Description: " + MMRootData.get(1), MMRootData.get(1), "024467", true));
            }
        }
    }

    public void onNodeDblselect(SelectEvent event) {
        MMSelectedNode = (MindmapNode) event.getObject();
    }

    @SuppressWarnings("unchecked")
    public void constructVisualisation(DbHazard selectedHazard) {
        try {
            if (isMapPopulated) {
                if (MMRoot.getLabel().equals("Hazard: " + selectedHazard.getHazardId())) {
                    return;
                }
            }
            MMRoot = new DefaultMindmapNode("Hazard: " + selectedHazard.getHazardId(), selectedHazard.getHazardDescription(), "D20005", false);
            isMapPopulated = true;
            
            List MMRootCA = viewHazardCause(selectedHazard.getHazardId());
            selectedHazardCauses = MMRootCA;
            for (int i = 0; i < MMRootCA.size(); i++) {
                MMRootData = new ArrayList<>();
                MMRootData.add(listDbHazardCause.get(i).getDbCause().getCauseId().toString());
                MMRootData.add(listDbHazardCause.get(i).getDbCause().getCauseDescription());
                MMRoot.addNode(new DefaultMindmapNode("Cause: " + listDbHazardCause.get(i).getDbCause().getCauseId(), MMRootData, "0892DC", true));
            }

            List MMRootCQ = viewHazardConsequence(selectedHazard.getHazardId());
            selectedHazardConsequences = MMRootCQ;
            for (int i = 0; i < MMRootCQ.size(); i++) {
                MMRootData = new ArrayList<>();
                MMRootData.add(listDbHazardConsequence.get(i).getDbConsequence().getConsequenceId().toString());
                MMRootData.add(listDbHazardConsequence.get(i).getDbConsequence().getConsequenceDescription());
                MMRoot.addNode(new DefaultMindmapNode("Consequence: " + listDbHazardConsequence.get(i).getDbConsequence().getConsequenceId(), MMRootData, "025886", true));
            }

            List MMRootCO = viewControlHazard(selectedHazard.getHazardId());
            selectedHazardControls = MMRootCO;
            for (int i = 0; i < MMRootCO.size(); i++) {
                MMRootData = new ArrayList<>();
                MMRootData.add(listDbControlHazard.get(i).getDbControl().getControlId().toString());
                MMRootData.add(listDbControlHazard.get(i).getDbControl().getControlDescription());
                MMRootData.add(listDbControlHazard.get(i).getDbControl().getControlHierarchyId().getControlHierarchyName());
                MMRootData.add(listDbControlHazard.get(i).getDbControl().getOwnerId().getOwnerName());
                MMRootData.add(listDbControlHazard.get(i).getControlRecommendId().getControlRecommendName());
                MMRootData.add(listDbControlHazard.get(i).getControlJustify());
                MMRoot.addNode(new DefaultMindmapNode("Control: " + listDbControlHazard.get(i).getDbControl().getControlId(), MMRootData, "FF8B00", true));
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }

    public String showTreeToString(List<treeNodeObject> treeCheckedNodesList) {
        String nodeNames = "";
        String previousNode = "";
        String currentNode = "";

        for (treeNodeObject node : treeCheckedNodesList) {
            if (nodeNames.isEmpty()) {
                previousNode = node.getNodeId();
                nodeNames = previousNode;
            } else {
                currentNode = node.getNodeId();
                if (!currentNode.startsWith(previousNode)) {
                    nodeNames = String.join(", ", nodeNames, currentNode);
                    previousNode = currentNode;
                }
            }
        }
        return nodeNames;
    }
}
