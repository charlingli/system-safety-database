package managedBeans;

import customObjects.*;
import ejb.DbFilesFacadeLocal;
import entities.*;
import ejb.DbHazardFacadeLocal;
import ejb.DbHazardFilesFacadeLocal;
import ejb.DbLocationFacadeLocal;
import ejb.DbOwnersFacadeLocal;
import ejb.DbProjectFacadeLocal;
import ejb.DbQualityFacadeLocal;
import ejb.DbUserFacadeLocal;
import ejb.DbchangeTypeFacadeLocal;
import ejb.DbconstructionTypeFacadeLocal;
import ejb.DbcontrolHierarchyFacadeLocal;
import ejb.DbcontrolRecommendFacadeLocal;
import ejb.DbgradeSeparationFacadeLocal;
import ejb.DbhazardActivityFacadeLocal;
import ejb.DbhazardContextFacadeLocal;
import ejb.DbhazardStatusFacadeLocal;
import ejb.DbhazardSystemStatusFacadeLocal;
import ejb.DbhazardTypeFacadeLocal;
import ejb.DbtreeLevel1FacadeLocal;
import ejb.DbwfHeaderFacadeLocal;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.primefaces.context.RequestContext;
import org.primefaces.model.TreeNode;

/**
 * @since 2018-07-07
 * @author Charling Li & David Ortega (LXRA)
 */
@Named(value = "hazardView_MB")
@ViewScoped
public class hazardView_MB implements Serializable {

    @EJB
    private DbFilesFacadeLocal dbFilesFacade;
    @EJB
    private DbHazardFilesFacadeLocal dbHazardFilesFacade;
    @EJB
    private DbhazardSystemStatusFacadeLocal dbhazardSystemStatusFacade;
    @EJB
    private DbtreeLevel1FacadeLocal dbtreeLevel1Facade;
    @EJB
    private DbwfHeaderFacadeLocal dbwfHeaderFacade;
    @EJB
    private DbUserFacadeLocal dbUserFacade;
    @EJB
    private DbQualityFacadeLocal dbQualityFacade;
    @EJB
    private DbProjectFacadeLocal dbProjectFacade;
    @EJB
    private DbLocationFacadeLocal dbLocationFacade;
    @EJB
    private DbgradeSeparationFacadeLocal dbgradeSeparationFacade;
    @EJB
    private DbconstructionTypeFacadeLocal dbconstructionTypeFacade;
    @EJB
    private DbchangeTypeFacadeLocal dbchangeTypeFacade;
    @EJB
    private DbhazardActivityFacadeLocal dbhazardActivityFacade;
    @EJB
    private DbhazardContextFacadeLocal dbhazardContextFacade;
    @EJB
    private DbhazardTypeFacadeLocal dbhazardTypeFacade;
    @EJB
    private DbhazardStatusFacadeLocal dbhazardStatusFacade;
    @EJB
    private DbOwnersFacadeLocal dbOwnersFacade;
    @EJB
    private DbHazardFacadeLocal dbHazardFacade;
    @EJB
    private DbcontrolRecommendFacadeLocal dbcontrolRecommendFacade;
    @EJB
    private DbcontrolHierarchyFacadeLocal dbcontrolHierarchyFacade;

    private DbUser activeUser;
    private String searchedHazardId;
    private String searchedHazardDescription;
    private String searchedCauses;
    private String searchedConsequences;
    private String searchedHazardComments;
    private String searchedControlDescription;
    private String searchedControlJustification;
    private String searchedLegacyId;
    private DbHazard detailHazard;
    private String detailHazardId;
    private String detailHazardDescription;
    private String detailHazardComment;
    private String hazardsQuality;
    private List<DbHazardCause> detailCauses;
    private List<DbHazardConsequence> detailConsequences;
    private List<DbControlHazard> detailControls;
    private List<DbLocation> listLocations;
    private List<DbProject> listProjects;
    private List<DbgradeSeparation> listGradeSeparations;
    private List<DbconstructionType> listConstructionTypes;
    private List<DbchangeType> listChangeTypes;
    private List<DbhazardActivity> listHazardActivities;
    private List<DbhazardContext> listHazardContexts;
    private List<DbhazardType> listHazardTypes;
    private List<DbhazardStatus> listHazardStatuses;
    private List<DbOwners> listHazardOwners;
    private List<DbcontrolHierarchy> listControlHierarchies;
    private List<DbcontrolRecommend> listControlRecommendations;
    private List<DbhazardSystemStatus> listHazardSystemStatus;
    private List<defaultViewSrchObject> listSearchedHazards;
    private List<DbHazard> hazardDetailList;
    private String[] selectedLocations;
    private String[] selectedProjects;
    private String[] selectedGradeSeparations;
    private String[] selectedConstructionTypes;
    private String[] selectedChangeTypes;
    private String[] selectedHazardActivities;
    private String[] selectedHazardContexts;
    private String[] selectedHazardTypes;
    private String[] selectedHazardStatuses;
    private String[] selectedHazardOwners;
    private String[] selectedControlHierachies;
    private String[] selectedControlOwners;
    private String[] selectedControlRecommendations;
    private TreeNode[] TNSelectedNodes;
    private String htmlCode;
    private boolean enableQueryDescr;
    private DbQuality qualityObject;
    private DbQualityPK qualityPKObject;
    private int qualityRating;
    private int displayRating;
    private int displayCount;
    private String suggestionComment;
    private String deletionComment;
    private String deletionReason;
    private List<defaultViewSrchObject> checkedHazards;
    private List<fileHeaderObject> fileHeaders;

    public hazardView_MB() {
    }

    @PostConstruct
    public void init() {
        activeUser = (DbUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("activeUser");
        setListLocations(dbLocationFacade.findAll());
        setListProjects(dbProjectFacade.findAll());
        setListGradeSeparations(dbgradeSeparationFacade.findAll());
        setListConstructionTypes(dbconstructionTypeFacade.findAll());
        setListChangeTypes(dbchangeTypeFacade.findAll());
        setListHazardActivities(dbhazardActivityFacade.findAll());
        setListHazardContexts(dbhazardContextFacade.findAll());
        setListHazardTypes(dbhazardTypeFacade.findAll());
        setListHazardStatuses(dbhazardStatusFacade.findAll());
        setListHazardOwners(dbOwnersFacade.findAll());
        setListControlHierarchies(dbcontrolHierarchyFacade.findAll());
        setListControlRecommendations(dbcontrolRecommendFacade.findAll());
        setListHazardSystemStatus(dbhazardSystemStatusFacade.findAll());
        enableQueryDescr = false;
        hazardsQuality = "A";
    }

    public DbUser getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(DbUser activeUser) {
        this.activeUser = activeUser;
    }

    public String getSearchedHazardId() {
        return searchedHazardId;
    }

    public void setSearchedHazardId(String searchedHazardId) {
        this.searchedHazardId = searchedHazardId;
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

    public String getSearchedHazardDescription() {
        return searchedHazardDescription;
    }

    public void setSearchedHazardDescription(String searchedHazardDescription) {
        this.searchedHazardDescription = searchedHazardDescription;
    }

    public String getSearchedHazardComments() {
        return searchedHazardComments;
    }

    public void setSearchedHazardComments(String searchedHazardComments) {
        this.searchedHazardComments = searchedHazardComments;
    }

    public String getSearchedControlDescription() {
        return searchedControlDescription;
    }

    public void setSearchedControlDescription(String searchedControlDescription) {
        this.searchedControlDescription = searchedControlDescription;
    }

    public String getSearchedControlJustification() {
        return searchedControlJustification;
    }

    public void setSearchedControlJustification(String searchedControlJustification) {
        this.searchedControlJustification = searchedControlJustification;
    }

    public String getSearchedLegacyId() {
        return searchedLegacyId;
    }

    public void setSearchedLegacyId(String searchedLegacyId) {
        this.searchedLegacyId = searchedLegacyId;
    }

    public DbHazard getDetailHazard() {
        return detailHazard;
    }

    public void setDetailHazard(DbHazard detailHazard) {
        this.detailHazard = detailHazard;
    }

    public String getDetailHazardId() {
        return detailHazardId;
    }

    public void setDetailHazardId(String detailHazardId) {
        this.detailHazardId = detailHazardId;
    }

    public String getDetailHazardDescription() {
        return detailHazardDescription;
    }

    public void setDetailHazardDescription(String detailHazardDescription) {
        this.detailHazardDescription = detailHazardDescription;
    }

    public String getDetailHazardComment() {
        return detailHazardComment;
    }

    public void setDetailHazardComment(String detailHazardComment) {
        this.detailHazardComment = detailHazardComment;
    }

    public String getHazardsQuality() {
        return hazardsQuality;
    }

    public void setHazardsQuality(String hazardsQuality) {
        this.hazardsQuality = hazardsQuality;
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

    public List<DbhazardStatus> getListHazardStatuses() {
        return listHazardStatuses;
    }

    public void setListHazardStatuses(List<DbhazardStatus> listHazardStatuses) {
        this.listHazardStatuses = listHazardStatuses;
    }

    public List<DbOwners> getListHazardOwners() {
        return listHazardOwners;
    }

    public void setListHazardOwners(List<DbOwners> listHazardOwners) {
        this.listHazardOwners = listHazardOwners;
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

    public List<DbhazardSystemStatus> getListHazardSystemStatus() {
        return listHazardSystemStatus;
    }

    public void setListHazardSystemStatus(List<DbhazardSystemStatus> listHazardSystemStatus) {
        this.listHazardSystemStatus = listHazardSystemStatus;
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

    public String[] getSelectedControlHierachies() {
        return selectedControlHierachies;
    }

    public void setSelectedControlHierachies(String[] selectedControlHierachies) {
        this.selectedControlHierachies = selectedControlHierachies;
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

    public TreeNode[] getTNSelectedNodes() {
        return TNSelectedNodes;
    }

    public void setTNSelectedNodes(TreeNode[] TNSelectedNodes) {
        this.TNSelectedNodes = TNSelectedNodes;
    }

    public String getHtmlCode() {
        return htmlCode;
    }

    public void setHtmlCode(String htmlCode) {
        this.htmlCode = htmlCode;
    }

    public List<defaultViewSrchObject> getListSearchedHazards() {
        return listSearchedHazards;
    }

    public void setListSearchedHazards(List<defaultViewSrchObject> listSearchedHazards) {
        this.listSearchedHazards = listSearchedHazards;
    }

    public List<DbHazard> getHazardDetailList() {
        return hazardDetailList;
    }

    public void setHazardDetailList(List<DbHazard> hazardDetailList) {
        this.hazardDetailList = hazardDetailList;
    }

    public boolean isEnableQueryDescr() {
        return enableQueryDescr;
    }

    public void setEnableQueryDescr(boolean enableQueryDescr) {
        this.enableQueryDescr = enableQueryDescr;
    }

    public DbQuality getQualityObject() {
        return qualityObject;
    }

    public void setQualityObject(DbQuality qualityObject) {
        this.qualityObject = qualityObject;
    }

    public DbQualityPK getQualityPKObject() {
        return qualityPKObject;
    }

    public void setQualityPKObject(DbQualityPK qualityPKObject) {
        this.qualityPKObject = qualityPKObject;
    }

    public int getQualityRating() {
        return qualityRating;
    }

    public void setQualityRating(int qualityRating) {
        this.qualityRating = qualityRating;
    }

    public int getDisplayRating() {
        return displayRating;
    }

    public void setDisplayRating(int displayRating) {
        this.displayRating = displayRating;
    }

    public int getDisplayCount() {
        return displayCount;
    }

    public void setDisplayCount(int displayCount) {
        this.displayCount = displayCount;
    }

    public String getSuggestionComment() {
        return suggestionComment;
    }

    public void setSuggestionComment(String suggestionComment) {
        this.suggestionComment = suggestionComment;
    }

    public String getDeletionComment() {
        return deletionComment;
    }

    public void setDeletionComment(String deletionComment) {
        this.deletionComment = deletionComment;
    }

    public String getDeletionReason() {
        return deletionReason;
    }

    public void setDeletionReason(String deletionReason) {
        this.deletionReason = deletionReason;
    }

    public List<defaultViewSrchObject> getCheckedHazards() {
        return checkedHazards;
    }

    public void setCheckedHazards(List<defaultViewSrchObject> checkedHazards) {
        this.checkedHazards = checkedHazards;
    }

    public List<fileHeaderObject> getFileHeaders() {
        return fileHeaders;
    }

    public void setFileHeaders(List<fileHeaderObject> fileHeaders) {
        this.fileHeaders = fileHeaders;
    }

    // This method creates the search object, based on the selected parameters.
    public void constructSearchObject() {
        // Initialising a couple of variables
        List<treeNodeObject> treeCheckedNodesList = new ArrayList<>();
        List<searchObject> searchCompositeList = new ArrayList<>();

        //This page method will present the approved hazards if the user is an approver
        if (activeUser.getRoleId().getRoleWFApprover().equals("Y")) {
            searchCompositeList.add(new searchObject("systemStatusId", "2", "int", "DbHazard", "hazardSystemStatus", null, null, "in", "SSD Workflow Status"));
        }

        // Start the grind of finding each entry to each field
        if (!getSearchedHazardId().isEmpty()) {
            searchCompositeList.add(new searchObject("hazardId", getSearchedHazardId(), "string", "DbHazard", null, null, null, "like", "Hazard Id"));
        }
        if (!getSearchedHazardDescription().isEmpty()) {
            searchCompositeList.add(new searchObject("hazardDescription", getSearchedHazardDescription(), "string", "DbHazard", null, null, null, "like", "Hazard Description"));
        }
        if (!getSearchedCauses().isEmpty()) {
            searchCompositeList.add(new searchObject("causeDescription", getSearchedCauses(), "string", "DbCause", null, null, null, "like", "Cause Description"));
        }
        if (!getSearchedConsequences().isEmpty()) {
            searchCompositeList.add(new searchObject("consequenceDescription", getSearchedConsequences(), "string", "DbConsequence", null, null, null, "like", "Consequence Description"));
        }
        if (!getSearchedHazardComments().isEmpty()) {
            searchCompositeList.add(new searchObject("hazardComment", getSearchedHazardComments(), "string", "DbHazard", null, null, null, "like", "Hazard Comments"));
        }
        if (!getSearchedLegacyId().isEmpty()) {
            searchCompositeList.add(new searchObject("legacyId", getSearchedLegacyId(), "string", "DbHazard", null, null, null, "like", "Hazard Legacy Id"));
        }
        if (getSelectedLocations() != null && getSelectedLocations().length > 0) {
            String joined = String.join(",", getSelectedLocations());
            searchCompositeList.add(new searchObject("locationId", joined, "int", "DbHazard", "hazardLocation", null, null, "in", "Location Id"));
        }
        if (getSelectedProjects() != null && getSelectedProjects().length > 0) {
            String joined = String.join(",", getSelectedProjects());
            searchCompositeList.add(new searchObject("projectId", joined, "int", "DbHazard", "hazardLocation", "projectId", null, "in", "Project Id"));
        }
        if (getSelectedGradeSeparations() != null && getSelectedGradeSeparations().length > 0) {
            String joined = String.join(",", getSelectedGradeSeparations());
            searchCompositeList.add(new searchObject("gradeSeparationId", joined, "int", "DbHazard", "hazardLocation", "locationGradeSeparation", null, "in", "Grade Separation"));
        }
        if (getSelectedConstructionTypes() != null && getSelectedConstructionTypes().length > 0) {
            String joined = String.join(",", getSelectedConstructionTypes());
            searchCompositeList.add(new searchObject("constructionTypeId", joined, "int", "DbHazard", "hazardLocation", "locationConstructionType", null, "in", "Construction Type"));
        }
        if (getSelectedChangeTypes() != null && getSelectedChangeTypes().length > 0) {
            String joined = String.join(",", getSelectedChangeTypes());
            searchCompositeList.add(new searchObject("changeTypeId", joined, "int", "DbHazard", "hazardLocation", "locationChangeType", null, "in", "Change Type"));
        }
        if (getSelectedHazardActivities() != null && getSelectedHazardActivities().length > 0) {
            String joined = String.join(",", getSelectedHazardActivities());
            searchCompositeList.add(new searchObject("activityId", joined, "int", "DbHazard", "hazardActivity", null, null, "in", "Activity"));
        }
        if (getSelectedHazardContexts() != null && getSelectedHazardContexts().length > 0) {
            String joined = String.join(",", getSelectedHazardContexts());
            searchCompositeList.add(new searchObject("hazardContextId", joined, "int", "DbHazard", "hazardContextId", null, null, "in", "Hazard Context"));
        }
        if (getSelectedHazardTypes() != null && getSelectedHazardTypes().length > 0) {
            String joined = String.join(",", getSelectedHazardTypes());
            searchCompositeList.add(new searchObject("hazardTypeId", joined, "int", "DbHazard", "hazardTypeId", null, null, "in", "Hazard Type"));
        }
        if (getSelectedHazardStatuses() != null && getSelectedHazardStatuses().length > 0) {
            String joined = String.join(",", getSelectedHazardStatuses());
            searchCompositeList.add(new searchObject("hazardStatusId", joined, "int", "DbHazard", "hazardStatusId", null, null, "in", "Hazard Status"));
        }
        if (getSelectedHazardOwners() != null && getSelectedHazardOwners().length > 0) {
            String joined = String.join(",", getSelectedHazardOwners());
            searchCompositeList.add(new searchObject("ownerId", joined, "int", "DbHazard", "ownerId", null, null, "in", "Hazard Owner"));
        }
        if (!getSearchedControlDescription().isEmpty()) {
            searchCompositeList.add(new searchObject("controlDescription", getSearchedControlDescription(), "string", "DbControl", null, null, null, "like", "Control Description"));
        }
        if (!getSearchedControlJustification().isEmpty()) {
            searchCompositeList.add(new searchObject("controlJustify", getSearchedControlJustification(), "string", "DbControlHazard", null, null, null, "like", "Control Justitification"));
        }
        if (getSelectedControlHierachies() != null && getSelectedControlHierachies().length > 0) {
            String joined = String.join(",", getSelectedControlHierachies());
            searchCompositeList.add(new searchObject("controlHierarchyId", joined, "int", "DbControl", "controlHierarchyId", null, null, "in", "Control Hierarchy"));
        }
        if (getSelectedControlOwners() != null && getSelectedControlOwners().length > 0) {
            String joined = String.join(",", getSelectedControlOwners());
            searchCompositeList.add(new searchObject("ownerId", joined, "int", "DbControl", "ownerId", null, null, "in", "Control Owner"));
        }
        if (getSelectedControlRecommendations() != null && getSelectedControlRecommendations().length > 0) {
            String joined = String.join(",", getSelectedControlRecommendations());
            searchCompositeList.add(new searchObject("controlRecommendId", joined, "int", "DbControlHazard", "controlRecommendId", null, null, "in", "Control Recommendation"));
        }
        if (getTNSelectedNodes() != null && getTNSelectedNodes().length > 0) {
            treeCheckedNodesList = new ArrayList<>();
            DbtreeLevel1 tmpTreeNode = dbtreeLevel1Facade.findByName("LXR Work Package/Project");
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
        }

        constructHtml(searchCompositeList, treeCheckedNodesList);
        listSearchedHazards = new ArrayList<>();
        listSearchedHazards = castToDefaultObj((List<Object[]>) (Object) dbHazardFacade.findHazards(searchCompositeList, treeCheckedNodesList, hazardsQuality, "DefaultView"));

        if (!listSearchedHazards.isEmpty()) {
            RequestContext.getCurrentInstance().execute("PF('widget_hazardsForm_fieldset').toggle()");
        }
        checkedHazards = new ArrayList<>();
    }

    public String showExtended() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("hazardList", listSearchedHazards);
        return "viewHazardExtend";
    }

    public void resetFields() {
        setSearchedHazardId(null);
        setSearchedHazardDescription(null);
        setSearchedCauses(null);
        setSearchedConsequences(null);
        setSearchedHazardComments(null);
        setSearchedLegacyId(null);
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
        setSearchedControlDescription(null);
        setSearchedControlJustification(null);
        setSelectedControlHierachies(null);
        setSelectedControlOwners(null);
        setSelectedControlRecommendations(null);
        setTNSelectedNodes(null);
        setHazardsQuality("A");
        constructHtml(new ArrayList<>(), new ArrayList<>());
        enableQueryDescr = false;
    }

    private void constructHtml(List<searchObject> listFields, List<treeNodeObject> listSbs) {
        enableQueryDescr = true;
        htmlCode = "<h3><span class=\"queryDescr\">Showing all hazards meeting the following criteria:</span></h3>";
        boolean includeAnd = false;
        if (!listFields.isEmpty()) {
            for (searchObject tmpSrch : listFields) {
                if (tmpSrch.getRelationType().equals("like")) {
                    if (includeAnd) {
                        htmlCode += "<p class=\"queryDescr_and\"><span>AND</span></p>";
                    }
                    htmlCode += "<h4 class=\"queryDescr\"><span class=\"queryDescr\">" + tmpSrch.getFieldDescription() + "</span> contains:</h4>";
                    htmlCode += "<p class=\"queryDescr_field\">" + tmpSrch.getUserInput() + "</p>";
                } else if (tmpSrch.getRelationType().equals("in")) {
                    if (includeAnd) {
                        htmlCode += "<p class=\"queryDescr_and\"><span>AND</span></p>";
                    }
                    htmlCode += "<h4 class=\"queryDescr\"><span class=\"queryDescr\">" + tmpSrch.getFieldDescription() + "</span> is equal to any in the list:</h4>";
                    htmlCode += "<p class=\"queryDescr_field\">" + convertListIds(tmpSrch.getFieldName(), tmpSrch.getUserInput()) + "</p>";
                }
                if (!includeAnd) {
                    includeAnd = true;
                }
            }
        }
        if (!listSbs.isEmpty()) {
            if (includeAnd) {
                htmlCode += "<p class=\"queryDescr_and\"><span>AND</span></p>";
            }
            htmlCode += "<h4 class=\"queryDescr\"><span class=\"queryDescr\">" + "System Breakdown Structure" + "</span> is associated with the following nodes:</h4>";
            htmlCode += "<p class=\"queryDescr_field\">" + showTreeToString(listSbs) + "</p>";
        }
        if (!hazardsQuality.equals("")) {
            if (includeAnd) {
                htmlCode += "<p class=\"queryDescr_and\"><span>AND</span></p>";
            }
            switch (hazardsQuality) {
                case "A":
                    htmlCode += "<h4 class=\"queryDescr\"><span class=\"queryDescr\">" + "Data Quality" + "</span> is:</h4>";
                    htmlCode += "<p class=\"queryDescr_field\">" + "Inclusive of missing causes, consequences, or controls." + "</p>";
                    break;
                case "C":
                    htmlCode += "<h4 class=\"queryDescr\"><span class=\"queryDescr\">" + "Data Quality" + "</span> is:</h4>";
                    htmlCode += "<p class=\"queryDescr_field\">" + "Assigned to at least one cause, consequence, and control." + "</p>";
                    break;
                case "I":
                    htmlCode += "<h4 class=\"queryDescr\"><span class=\"queryDescr\">" + "Data Quality" + "</span> is:</h4>";
                    htmlCode += "<p class=\"queryDescr_field\">" + "Missing causes, consequences, or controls." + "</p>";
                    break;
            }
        }
    }

    private String convertListIds(String listName, String stringIds) {
        StringBuilder resultantString = new StringBuilder();
        List<String> stringListIds = Arrays.asList(stringIds.split(","));
        if (null != listName) {
            switch (listName) {
                case "locationId":
                    for (String tmpId : stringListIds) {
                        List<DbLocation> result = listLocations.stream()
                                .filter(a -> a.getLocationId().equals(Integer.parseInt(tmpId)))
                                .collect(Collectors.toList());
                        resultantString.append(result.get(0).getLocationName());
                        resultantString.append(", ");
                    }
                    break;
                case "projectId":
                    for (String tmpId : stringListIds) {
                        List<DbProject> result = listProjects.stream()
                                .filter(a -> a.getProjectId().equals(Integer.parseInt(tmpId)))
                                .collect(Collectors.toList());
                        resultantString.append(result.get(0).getProjectName());
                        resultantString.append(", ");
                    }
                    break;
                case "gradeSeparationId":
                    for (String tmpId : stringListIds) {
                        List<DbgradeSeparation> result = listGradeSeparations.stream()
                                .filter(a -> a.getGradeSeparationId().equals(Integer.parseInt(tmpId)))
                                .collect(Collectors.toList());
                        resultantString.append(result.get(0).getGradeSeparationName());
                        resultantString.append(", ");
                    }
                    break;
                case "constructionTypeId":
                    for (String tmpId : stringListIds) {
                        List<DbconstructionType> result = listConstructionTypes.stream()
                                .filter(a -> a.getConstructionTypeId().equals(Integer.parseInt(tmpId)))
                                .collect(Collectors.toList());
                        resultantString.append(result.get(0).getConstructionTypeName());
                        resultantString.append(", ");
                    }
                    break;
                case "changeTypeId":
                    for (String tmpId : stringListIds) {
                        List<DbchangeType> result = listChangeTypes.stream()
                                .filter(a -> a.getChangeTypeId().equals(Integer.parseInt(tmpId)))
                                .collect(Collectors.toList());
                        resultantString.append(result.get(0).getChangeTypeName());
                        resultantString.append(", ");
                    }
                    break;
                case "activityId":
                    for (String tmpId : stringListIds) {
                        List<DbhazardActivity> result = listHazardActivities.stream()
                                .filter(a -> a.getActivityId().equals(Integer.parseInt(tmpId)))
                                .collect(Collectors.toList());
                        resultantString.append(result.get(0).getActivityName());
                        resultantString.append(", ");
                    }
                    break;
                case "hazardContextId":
                    for (String tmpId : stringListIds) {
                        List<DbhazardContext> result = listHazardContexts.stream()
                                .filter(a -> a.getHazardContextId().equals(Integer.parseInt(tmpId)))
                                .collect(Collectors.toList());
                        resultantString.append(result.get(0).getHazardContextName());
                        resultantString.append(", ");
                    }
                    break;
                case "hazardTypeId":
                    for (String tmpId : stringListIds) {
                        List<DbhazardType> result = listHazardTypes.stream()
                                .filter(a -> a.getHazardTypeId().equals(Integer.parseInt(tmpId)))
                                .collect(Collectors.toList());
                        resultantString.append(result.get(0).getHazardTypeName());
                        resultantString.append(", ");
                    }
                    break;
                case "hazardStatusId":
                    for (String tmpId : stringListIds) {
                        List<DbhazardStatus> result = listHazardStatuses.stream()
                                .filter(a -> a.getHazardStatusId().equals(Integer.parseInt(tmpId)))
                                .collect(Collectors.toList());
                        resultantString.append(result.get(0).getHazardStatusName());
                        resultantString.append(", ");
                    }
                    break;
                case "ownerId":
                    for (String tmpId : stringListIds) {
                        List<DbOwners> result = listHazardOwners.stream()
                                .filter(a -> a.getOwnerId().equals(Integer.parseInt(tmpId)))
                                .collect(Collectors.toList());
                        resultantString.append(result.get(0).getOwnerName());
                        resultantString.append(", ");
                    }
                    break;
                case "controlHierarchyId":
                    for (String tmpId : stringListIds) {
                        List<DbcontrolHierarchy> result = listControlHierarchies.stream()
                                .filter(a -> a.getControlHierarchyId().equals(Integer.parseInt(tmpId)))
                                .collect(Collectors.toList());
                        resultantString.append(result.get(0).getControlHierarchyName());
                        resultantString.append(", ");
                    }
                    break;
                case "controlRecommendId":
                    for (String tmpId : stringListIds) {
                        List<DbcontrolRecommend> result = listControlRecommendations.stream()
                                .filter(a -> a.getControlRecommendId().equals(Integer.parseInt(tmpId)))
                                .collect(Collectors.toList());
                        resultantString.append(result.get(0).getControlRecommendName());
                        resultantString.append(", ");
                    }
                    break;
                case "systemStatusId":
                    stringListIds.stream().map((tmpId) -> listHazardSystemStatus.stream()
                            .filter(a -> a.getSystemStatusId().equals(Integer.parseInt(tmpId)))
                            .collect(Collectors.toList())).map((result) -> {
                        resultantString.append(result.get(0).getSystemStatusName());
                        return result;
                    }).forEachOrdered((_item) -> {
                        resultantString.append(", ");
                    });
                    break;
                default:
                    break;
            }
        }
        return resultantString.toString().substring(0, resultantString.toString().length() - 2);
    }

    public String showTreeToString(List<treeNodeObject> treeCheckedNodes) {
        String nodeNames = "";
        String previousNode = "";
        String currentNode = "";

        for (treeNodeObject node : treeCheckedNodes) {
            if (nodeNames.isEmpty()) {
                previousNode = node.getNodeId();
                nodeNames = previousNode.substring(2) + getNodeLastNameById(previousNode);
            } else {
                currentNode = node.getNodeId();
                if (!currentNode.startsWith(previousNode)) {
                    nodeNames = String.join(", ", nodeNames, currentNode.substring(2) + " " + getNodeLastNameById(currentNode));
                    previousNode = currentNode;
                }
            }
        }
        return nodeNames;
    }

    public String getNodeLastNameById(String nodeId) {
        String nodeName = "";
        String parts[] = nodeId.split("\\.");
        if (nodeId.equals("")) {
            nodeName = "";
        } else {
            switch (parts.length) {
                case 1:
                    DbtreeLevel1 tmpDbLvl1 = dbtreeLevel1Facade.findByIndex(Integer.parseInt(parts[0]));
                    if (tmpDbLvl1.getTreeLevel1Name() != null) {
                        nodeName = tmpDbLvl1.getTreeLevel1Name();
                    }
                    break;
                case 2:
                    DbtreeLevel2 tmpDbLvl2 = dbtreeLevel1Facade.findByIndex(Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]));
                    if (tmpDbLvl2.getTreeLevel2Name() != null) {
                        nodeName = tmpDbLvl2.getTreeLevel2Name();
                    }
                    break;
                case 3:
                    DbtreeLevel3 tmpDbLvl3 = dbtreeLevel1Facade.findByIndex(Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                    if (tmpDbLvl3.getTreeLevel3Name() != null) {
                        nodeName = tmpDbLvl3.getTreeLevel3Name();
                    }
                    break;
                case 4:
                    DbtreeLevel4 tmpDbLvl4 = dbtreeLevel1Facade.findByIndex(Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
                    if (tmpDbLvl4.getTreeLevel4Name() != null) {
                        nodeName = tmpDbLvl4.getTreeLevel4Name();
                    }
                    break;
                case 5:
                    DbtreeLevel5 tmpDbLvl5 = dbtreeLevel1Facade.findByIndex(Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]),
                            Integer.parseInt(parts[4]));
                    if (tmpDbLvl5.getTreeLevel5Name() != null) {
                        nodeName = tmpDbLvl5.getTreeLevel5Name();
                    }
                    break;
                case 6:
                    DbtreeLevel6 tmpDbLvl6 = dbtreeLevel1Facade.findByIndex(Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]),
                            Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));
                    if (tmpDbLvl6.getTreeLevel6Name() != null) {
                        nodeName = tmpDbLvl6.getTreeLevel6Name();
                    }
                    break;
            }
        }
        return nodeName;
    }

    public void showDetail(String hazardId) {
        setDetailHazardId(hazardId);
        setHazardDetailList(dbHazardFacade.findByName("hazardId", getDetailHazardId()));
        detailHazard = getHazardDetailList().get(0);
        setDetailHazardDescription(detailHazard.getHazardDescription());
        setDetailHazardComment(detailHazard.getHazardComment());
        detailCauses = dbHazardFacade.getHazardCause(detailHazard.getHazardId());
        detailConsequences = dbHazardFacade.getHazardConsequence(detailHazard.getHazardId());
        detailControls = dbHazardFacade.getControlHazard(detailHazard.getHazardId());
        fileHeaders = dbHazardFilesFacade.findHeadersForHazard(hazardId);
    }

    public void clearField(String id) {
        switch (id) {
            case "HiDClear":
                setSearchedHazardId(null);
                break;
            case "HDClear":
                setSearchedHazardDescription(null);
                break;
            case "CAClear":
                setSearchedCauses(null);
                break;
            case "CQClear":
                setSearchedConsequences(null);
                break;
            case "HMClear":
                setSearchedHazardComments(null);
                break;
            case "HLiDClear":
                setSearchedLegacyId(null);
                break;
            case "HLClear":
                setSelectedLocations(null);
                break;
            case "HPClear":
                setSelectedProjects(null);
                break;
            case "GSClear":
                setSelectedGradeSeparations(null);
                break;
            case "CNClear":
                setSelectedConstructionTypes(null);
                break;
            case "CHClear":
                setSelectedChangeTypes(null);
                break;
            case "HAClear":
                setSelectedHazardActivities(null);
                break;
            case "HCClear":
                setSelectedHazardContexts(null);
                break;
            case "HTClear":
                setSelectedHazardTypes(null);
                break;
            case "HSClear":
                setSelectedHazardStatuses(null);
                break;
            case "HOClear":
                setSelectedHazardOwners(null);
                break;
            case "CDClear":
                setSearchedControlDescription(null);
                break;
            case "CJClear":
                setSearchedControlJustification(null);
                break;
            case "CTClear":
                setSelectedControlHierachies(null);
                break;
            case "COClear":
                setSelectedControlOwners(null);
                break;
            case "CRClear":
                setSelectedControlRecommendations(null);
                break;
        }
        constructHtml(new ArrayList<>(), new ArrayList<>());
        enableQueryDescr = false;
    }

    public void rateHazard(String hazardId, int rating) {
        if (dbQualityFacade.getUserHazardRating(activeUser.getUserId(), hazardId).size() > 0) {
            qualityObject = dbQualityFacade.getUserHazardRating(activeUser.getUserId(), hazardId).get(0);
            qualityObject.setRating(rating);
            if (rating == 0) {
                qualityObject.setWeighting(0);
            } else if (activeUser.getRoleId().getRoleWFApprover().equals("Y")) {
                qualityObject.setWeighting(2);
            } else {
                qualityObject.setWeighting(1);
            }
            dbQualityFacade.edit(qualityObject);
        } else {
            qualityPKObject = new DbQualityPK();
            qualityPKObject.setHazardId(hazardId);
            qualityPKObject.setUserId(getActiveUser().getUserId());
            qualityObject = new DbQuality();
            qualityObject.setDbQualityPK(qualityPKObject);
            qualityObject.setRating(rating);
            if (rating == 0) {
                qualityObject.setWeighting(0);
            } else if (activeUser.getRoleId().getRoleWFApprover().equals("Y")) {
                qualityObject.setWeighting(2);
            } else {
                qualityObject.setWeighting(1);
            }
            dbQualityFacade.create(qualityObject);
        }
        updateSearchList(hazardId);
    }

    public double getHazardRating(String hazardId) {
        return dbQualityFacade.getHazardRating(hazardId);
    }

    public long getRatingCount(String hazardId) {
        return dbQualityFacade.getNumberOfRatings(hazardId);
    }

    public int getUserRating(String hazardId) {
        if (dbQualityFacade.getUserHazardRating(activeUser.getUserId(), hazardId).size() > 0) {
            return dbQualityFacade.getUserHazardRating(activeUser.getUserId(), hazardId).get(0).getRating();
        }
        return 0;
    }

    public String getRatingScore(String sumOfRateTimesWeighting, String sumOfWeight) {
        try {
            if (!sumOfRateTimesWeighting.equals("0") || !sumOfWeight.equals("0")) {
                Double ratePerWeight = Double.parseDouble(sumOfRateTimesWeighting);
                Double Weight = Double.parseDouble(sumOfWeight);
                Double rate = ratePerWeight / Weight;
                DecimalFormat df = new DecimalFormat("#.00");
                return df.format(rate);
            } else {
                return "0.00";
            }
        } catch (NumberFormatException e) {
            System.out.println(e);
            return null;
        }
    }

    public void sendSuggestion() {
        System.out.println(getSuggestionComment());
        if (getSuggestionComment().length() < 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "You must leave a comment justifying your change suggestion."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The suggestion has been sent for review."));
            List<DbUser> listApprovers = dbUserFacade.getUsersByRole("Core user");
            DbwfHeader wfObj = new DbwfHeader();
            wfObj.setWfTypeId(new DbwfType("W3"));
            wfObj.setWfStatus("O");
            wfObj.setWfAddedDateTime(new Date());
            wfObj.setWfUserIdAdd((DbUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("activeUser"));
            wfObj.setWfObjectId(getDetailHazard().getHazardId());
            wfObj.setWfObjectName("Hazard");
            wfObj.setWfComment1("A change to this hazard was suggested by a user. Please review and manually edit, then accept this workflow to complete.");
            wfObj.setWfComment2(getSuggestionComment());
            wfObj.setWfCompleteMethod("HazardSuggestionWF");
            validateIdObject result = dbwfHeaderFacade.newWorkFlow(listApprovers, wfObj, "WKF-SUG");
            setSuggestionComment(null);
            setDetailHazard(null);
        }
    }

    public void sendDeletion() {
        if (getDeletionComment().length() < 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "You must leave a comment justifying your mark for deletion."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The deletion mark has been sent for review."));
            List<DbUser> listApprovers = dbUserFacade.getUsersByRole("Core user");
            DbwfHeader wfObj = new DbwfHeader();
            wfObj.setWfTypeId(new DbwfType("W3"));
            wfObj.setWfStatus("O");
            wfObj.setWfAddedDateTime(new Date());
            wfObj.setWfUserIdAdd((DbUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("activeUser"));
            wfObj.setWfObjectId(getDetailHazard().getHazardId());
            wfObj.setWfObjectName("Hazard");
            wfObj.setWfComment1("This hazard was marked for deletion by a user. Please review and approve this workflow to delete or reject this workflow to cancel.");
            wfObj.setWfComment2(getDeletionReason());
            wfObj.setWfComment2(getDeletionComment());
            wfObj.setWfCompleteMethod("HazardDeletionWF");
            validateIdObject result = dbwfHeaderFacade.newWorkFlow(listApprovers, wfObj, "WKF-DEL");
            setDeletionComment(null);
            setDeletionReason(null);
            setDetailHazard(null);
        }
    }

    private List<defaultViewSrchObject> castToDefaultObj(List<Object[]> tmpList) {
        List<defaultViewSrchObject> tmpListOut = new ArrayList<>();
        tmpList.stream().map((x) -> {
            DbHazard tmpHaz = (DbHazard) x[0];
            Long tmpOne = null;
            Long tmpTwo = null;
            Long tmpThree = null;
            String tmpFour = null;
            if (x[1] != null) {
                tmpOne = (Long) x[1];
            }
            if (x[2] != null) {
                tmpTwo = (Long) x[2];
            }
            if (x[3] != null) {
                tmpThree = (Long) x[3];
            }
            if (x[4] != null) {
                tmpFour = x[4].toString();
            }
            defaultViewSrchObject tmpDflt = new defaultViewSrchObject(tmpHaz, tmpOne, tmpTwo, tmpThree, tmpFour);
            return tmpDflt;
        }).forEachOrdered((tmpDflt) -> {
            tmpListOut.add(tmpDflt);
        });
        return tmpListOut;
    }

    private void updateSearchList(String hazardId) {
        List<treeNodeObject> treeCheckedNodesList = new ArrayList<>();
        List<searchObject> searchCompositeList = new ArrayList<>();
        if (hazardId != null) {
            searchCompositeList.add(new searchObject("hazardId", hazardId, "string", "DbHazard", null, null, null, "like", "Hazard Id"));
            List<defaultViewSrchObject> tmpResult
                    = castToDefaultObj((List<Object[]>) (Object) dbHazardFacade.findHazards(searchCompositeList, treeCheckedNodesList, hazardsQuality, "DefaultView"));
            if (!tmpResult.isEmpty()) {
                for (int i = 0; i < listSearchedHazards.size(); i++) {
                    if (listSearchedHazards.get(i).getHazardObj().getHazardId().equals(hazardId)) {
                        listSearchedHazards.get(i).setSumOfRateTimesWeighting(tmpResult.get(0).getSumOfRateTimesWeighting());
                        listSearchedHazards.get(i).setSumOfWeight(tmpResult.get(0).getSumOfWeight());
                        listSearchedHazards.get(i).setRateCount(tmpResult.get(0).getRateCount());
                        break;
                    }
                }
            }
        }
    }

    public void exportExcel() {
        List<String> hazardsToExport = getCheckedHazards().stream().map(h -> h.getHazardObj().getHazardId()).collect(Collectors.toList());
        // Setting up the file
        boolean complete = false;
        String filename = "SSD_Export.xls";

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Register");
        sheet.setZoom(80);
        
        String[][] columnsHeaders = {{"Risk ID", "Blue", "14"},
        {"Drawing Package", "Yellow", "14"},
        {"Location", "Blue", "26"},
        {"Risk Source ", "Blue", "14"},
        {"Cause / Precursor / Events", "Blue", "26"},
        {"Risk Description", "Blue", "14"},
        {"Risk Owner", "Blue", "14"},
        {"Historic Impacts", "Blue", "14"},
        {"Potential Impacts", "Blue", "14"},
        {"Project Risk Owner", "Yellow", "14"},
        {"Risk Type", "Yellow", "14"},
        {"Current Controls / Treatments", "Blue", "29"},
        {"Requirement ID", "Pink", "14"},
        {"Control / Treatment Owner", "Blue", "28"},
        {"Control Implementation Status", "Yellow", "15"},
        {"Risk Context ", "Red", "14"},
        {"Current Residual Severity", "Red", "14"},
        {"Current Residual Frequency", "Red", "14"},
        {"", "Red", "14"},
        {"Calculated Risk Score ", "Red", "14"},
        {"", "Red", "14"},
        {"", "Red", "14"},
        {"Proposed / Possible Controls", "Blue", "28"},
        {"New Requirement ID", "Pink", "14"},
        {"Type of Control (Hierarchy)", "Blue", "14"},
        {"Proposed Control Owner ", "Blue", "28"},
        {"Workshop recommendation for proposed control", "Blue", "16"},
        {"Control Implementation Status", "Yellow", "15"},
        {"Target / Desired Residual Severity", "Red", "14"},
        {"Target / Desired Residual Frequency", "Red", "14"},
        {"Target / Desired Calculated Risk Score ", "Red", "14"},
        {"Comments / Assumptions", "Blue", "30"},
        {"Date Raised ", "Yellow", "14"},
        {"Workshop/Source ", "Yellow", "25"},
        {"Close Out Commentary", "Grey", "14"},
        {"Risk Status ", "Yellow", "14"},
        {"Human Factors review required? ", "Yellow", "14"}};

        // Defining the colours palette
        HSSFPalette palette = workbook.getCustomPalette();
        palette.setColorAtIndex((short) 22, (byte) 172, (byte) 185, (byte) 202); // Grey

        // Creating headers from the row number 6
        Row headerRow = sheet.createRow(5);
        headerRow.setHeight((short) 1200);

        // Setting up the column sizes
        for (int i = 0; i < columnsHeaders.length; i++) {
            int width = ((int) (Integer.parseInt(columnsHeaders[i][2]) * 1.14388 * 256));
            sheet.setColumnWidth(i, width);
        }

        // Create Header cells
        for (int i = 0; i < columnsHeaders.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnsHeaders[i][0]);
            cell.setCellStyle(styleHeaderGenerator(columnsHeaders[i][1], workbook.createCellStyle(), workbook.createFont()));
        }

        // Getting all the data from the database
        List<Object[]> exportedInfo = dbHazardFacade.exportHazards(hazardsToExport);

        // This is the row from where the hazards information will be written
        int currentRow = 6;
        CellStyle bodyCellStyle = workbook.createCellStyle();
        Font bodyFont = workbook.createFont();
        int charColExsCntl = getCharPerLine("Current Controls / Treatments", columnsHeaders);
        int charColProCntl = getCharPerLine("Proposed / Possible Controls", columnsHeaders);

        for (int i = 0; i < exportedInfo.size(); i++) {
            //Calculate what is the required number of rows
            int numberOfExsControls = ((BigInteger) exportedInfo.get(i)[0]).intValue();
            int numberOfProControls = ((BigInteger) exportedInfo.get(i)[1]).intValue();
            int numberOfRows = calcNumberOfRows(numberOfExsControls, numberOfProControls);

            if (numberOfRows != 0) {
                // Getting the hazard information

                String hazardId1 = checkNullString(exportedInfo.get(i)[2]);
                String locationName = checkNullString(exportedInfo.get(i)[3]);
                String hazardContextName = checkNullString(exportedInfo.get(i)[4]);
                String hazardCauses = checkNullString(exportedInfo.get(i)[5]);
                String hazardDescription = checkNullString(exportedInfo.get(i)[6]);
                String ownerName = checkNullString(exportedInfo.get(i)[7]);
                String hazardConsequences = checkNullString(exportedInfo.get(i)[8]);
                String hazardTypeName = checkNullString(exportedInfo.get(i)[9]);
                String riskClassName = checkNullString(exportedInfo.get(i)[13]);
                String severityScore = checkNullString(exportedInfo.get(i)[19]);
                String frequencyScore = checkNullString(exportedInfo.get(i)[20]);
                String riskScore = exportedInfo.get(i)[21].toString();
                String hazardComment = checkNullString(exportedInfo.get(i)[22]);
                String hazardDate = checkNullDate(exportedInfo.get(i)[23]);
                String hazardWorkshop = checkNullString(exportedInfo.get(i)[24]);
                String hazardStatusName = checkNullString(exportedInfo.get(i)[25]);

                exportedHazard curHazard = new exportedHazard(hazardId1, locationName, hazardContextName, hazardCauses, hazardDescription, ownerName, hazardConsequences,
                        hazardTypeName, riskClassName, severityScore, frequencyScore, riskScore, hazardComment, hazardDate, hazardWorkshop, hazardStatusName);

                // Getting the controls information
                List<exportedExsControl> listExsCtl = new ArrayList<>();
                List<exportedProControl> listProCtl = new ArrayList<>();
                for (int j = i; j < (i + numberOfRows); j++) {
                    if (exportedInfo.get(j)[10] != null && !listExsCtl.contains(new exportedExsControl((int) exportedInfo.get(j)[10]))) {
                        listExsCtl.add(new exportedExsControl((int) exportedInfo.get(j)[10], exportedInfo.get(j)[11].toString(), exportedInfo.get(j)[12].toString()));
                    }
                    if (exportedInfo.get(j)[14] != null && !listProCtl.contains(new exportedProControl((int) exportedInfo.get(j)[14]))) {
                        listProCtl.add(new exportedProControl((int) exportedInfo.get(j)[14], exportedInfo.get(j)[15].toString(), exportedInfo.get(j)[17].toString(),
                                exportedInfo.get(j)[16].toString(), exportedInfo.get(j)[18].toString()));
                    }
                }

                // -------------------------------> This section writes the information in the Microsoft Excel file. <---------------------------------------
                Row bodyRow = sheet.createRow(currentRow);
                int numberOfRowsperHazard = calcNumberOfRowsPerHazard(numberOfExsControls, numberOfProControls);
                String column;

                // Getting the calculated size per row
                int sizePerRow = calculateSizePerRow(columnsHeaders, curHazard, numberOfRowsperHazard);

                // Defining the style of the hazards
                bodyFont.setFontHeightInPoints((short) 10);
                bodyCellStyle.setFont(bodyFont);
                bodyCellStyle.setWrapText(true);
                bodyCellStyle.setAlignment(HorizontalAlignment.CENTER);
                bodyCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

                // -------------------------------> First step, writing the hazards cells without controls. <---------------------------------------
                // Hazard id is in column A
                column = "A";
                Cell BodyCell = bodyRow.createCell(0);
                BodyCell.setCellValue(curHazard.hazardId);
                BodyCell.setCellStyle(bodyCellStyle);
                if (numberOfRowsperHazard > 1) {
                    sheet.addMergedRegion(CellRangeAddress.valueOf(column + (currentRow + 1) + ":" + column + (currentRow + numberOfRowsperHazard)));
                }

                // Hazard location is in column C
                column = "C";
                BodyCell = bodyRow.createCell(2);
                BodyCell.setCellValue(curHazard.locationName);
                BodyCell.setCellStyle(bodyCellStyle);
                if (numberOfRowsperHazard > 1) {
                    sheet.addMergedRegion(CellRangeAddress.valueOf(column + (currentRow + 1) + ":" + column + (currentRow + numberOfRowsperHazard)));
                }

                // Hazard context is in column D
                column = "D";
                BodyCell = bodyRow.createCell(3);
                BodyCell.setCellValue(curHazard.hazardContextName);
                BodyCell.setCellStyle(bodyCellStyle);
                if (numberOfRowsperHazard > 1) {
                    sheet.addMergedRegion(CellRangeAddress.valueOf(column + (currentRow + 1) + ":" + column + (currentRow + numberOfRowsperHazard)));
                }

                // Hazard causes is in column E
                column = "E";
                BodyCell = bodyRow.createCell(4);
                BodyCell.setCellValue(curHazard.hazardCauses);
                BodyCell.setCellStyle(bodyCellStyle);
                if (numberOfRowsperHazard > 1) {
                    sheet.addMergedRegion(CellRangeAddress.valueOf(column + (currentRow + 1) + ":" + column + (currentRow + numberOfRowsperHazard)));
                }

                // Hazard description is in column F
                column = "F";
                BodyCell = bodyRow.createCell(5);
                BodyCell.setCellValue(curHazard.hazardDescription);
                BodyCell.setCellStyle(bodyCellStyle);
                if (numberOfRowsperHazard > 1) {
                    sheet.addMergedRegion(CellRangeAddress.valueOf(column + (currentRow + 1) + ":" + column + (currentRow + numberOfRowsperHazard)));
                }

                // Hazard owner is in column G
                column = "G";
                BodyCell = bodyRow.createCell(6);
                BodyCell.setCellValue(curHazard.ownerName);
                BodyCell.setCellStyle(bodyCellStyle);
                if (numberOfRowsperHazard > 1) {
                    sheet.addMergedRegion(CellRangeAddress.valueOf(column + (currentRow + 1) + ":" + column + (currentRow + numberOfRowsperHazard)));
                }

                // Hazard consequences is in column I
                column = "I";
                BodyCell = bodyRow.createCell(8);
                BodyCell.setCellValue(curHazard.hazardConsequences);
                BodyCell.setCellStyle(bodyCellStyle);
                if (numberOfRowsperHazard > 1) {
                    sheet.addMergedRegion(CellRangeAddress.valueOf(column + (currentRow + 1) + ":" + column + (currentRow + numberOfRowsperHazard)));
                }

                // Hazard type is in column K
                column = "K";
                BodyCell = bodyRow.createCell(10);
                BodyCell.setCellValue(curHazard.hazardTypeName);
                BodyCell.setCellStyle(bodyCellStyle);
                if (numberOfRowsperHazard
                        > 1) {
                    sheet.addMergedRegion(CellRangeAddress.valueOf(column + (currentRow + 1) + ":" + column + (currentRow + numberOfRowsperHazard)));
                }

                // Hazard type is in column P
                column = "P";
                BodyCell = bodyRow.createCell(15);
                BodyCell.setCellValue(curHazard.riskClassName);
                BodyCell.setCellStyle(bodyCellStyle);
                if (numberOfRowsperHazard
                        > 1) {
                    sheet.addMergedRegion(CellRangeAddress.valueOf(column + (currentRow + 1) + ":" + column + (currentRow + numberOfRowsperHazard)));
                }

                // Hazard severity is in column AC
                column = "AC";
                BodyCell = bodyRow.createCell(28);
                BodyCell.setCellValue(curHazard.severityScore);
                BodyCell.setCellStyle(bodyCellStyle);
                if (numberOfRowsperHazard > 1) {
                    sheet.addMergedRegion(CellRangeAddress.valueOf(column + (currentRow + 1) + ":" + column + (currentRow + numberOfRowsperHazard)));
                }

                // Hazard severity is in column AD
                column = "AD";
                BodyCell = bodyRow.createCell(29);
                BodyCell.setCellValue(curHazard.frequencyScore);
                BodyCell.setCellStyle(bodyCellStyle);
                if (numberOfRowsperHazard > 1) {
                    sheet.addMergedRegion(CellRangeAddress.valueOf(column + (currentRow + 1) + ":" + column + (currentRow + numberOfRowsperHazard)));
                }

                // Hazard score is in column AD
                column = "AE";
                BodyCell = bodyRow.createCell(30);
                BodyCell.setCellValue(curHazard.riskScore);
                BodyCell.setCellStyle(bodyCellStyle);
                if (numberOfRowsperHazard > 1) {
                    sheet.addMergedRegion(CellRangeAddress.valueOf(column + (currentRow + 1) + ":" + column + (currentRow + numberOfRowsperHazard)));
                }

                // Hazard comments is in column AF
                column = "AF";
                BodyCell = bodyRow.createCell(31);
                BodyCell.setCellValue(curHazard.hazardComment);
                BodyCell.setCellStyle(bodyCellStyle);
                if (numberOfRowsperHazard > 1) {
                    sheet.addMergedRegion(CellRangeAddress.valueOf(column + (currentRow + 1) + ":" + column + (currentRow + numberOfRowsperHazard)));
                }

                // Hazard date is in column AG
                column = "AG";
                BodyCell = bodyRow.createCell(32);
                BodyCell.setCellValue(curHazard.hazardDate);
                BodyCell.setCellStyle(bodyCellStyle);
                if (numberOfRowsperHazard > 1) {
                    sheet.addMergedRegion(CellRangeAddress.valueOf(column + (currentRow + 1) + ":" + column + (currentRow + numberOfRowsperHazard)));
                }

                // Hazard Workshop is in column AH
                column = "AH";
                BodyCell = bodyRow.createCell(33);
                BodyCell.setCellValue(curHazard.hazardWorkshop);
                BodyCell.setCellStyle(bodyCellStyle);
                if (numberOfRowsperHazard > 1) {
                    sheet.addMergedRegion(CellRangeAddress.valueOf(column + (currentRow + 1) + ":" + column + (currentRow + numberOfRowsperHazard)));
                }

                // Hazard status is in column AG
                column = "AJ";
                BodyCell = bodyRow.createCell(35);
                BodyCell.setCellValue(curHazard.hazardStatusName);
                BodyCell.setCellStyle(bodyCellStyle);
                if (numberOfRowsperHazard > 1) {
                    sheet.addMergedRegion(CellRangeAddress.valueOf(column + (currentRow + 1) + ":" + column + (currentRow + numberOfRowsperHazard)));
                }

                // -------------------------------> Second step, writing the controls. <---------------------------------------
                Iterator<exportedExsControl> exsIterator = listExsCtl.iterator();
                Iterator<exportedProControl> proIterator = listProCtl.iterator();
                boolean firstCycle = true;

                // In case there are not existing and proposed controls, the row will be increment by 1
                if (!exsIterator.hasNext() && !proIterator.hasNext()) {
                    currentRow++;
                    // Autosize the row height 
                    bodyRow.setHeight((short) sizePerRow);
                }

                // This loop writes the existing and proposed controls
                while (exsIterator.hasNext() || proIterator.hasNext()) {
                    exportedExsControl tmpExtCtl = new exportedExsControl();
                    exportedProControl tmpProCtl = new exportedProControl();
                    int finalHeight = sizePerRow;
                    int heightExsCtl = 0;
                    int heightProCtl = 0;
                    
                    // From the second iteration we will create a new row per cycle
                    if (firstCycle) {
                        firstCycle = false;
                    } else {
                        bodyRow = sheet.createRow(currentRow);
                    }

                    if (exsIterator.hasNext()) {
                        tmpExtCtl = exsIterator.next();

                        // Current control descr is in column L
                        BodyCell = bodyRow.createCell(11);
                        BodyCell.setCellValue(tmpExtCtl.controlDescription);
                        BodyCell.setCellStyle(bodyCellStyle);

                        // Current control owner is in column N
                        BodyCell = bodyRow.createCell(13);
                        BodyCell.setCellValue(tmpExtCtl.controlOwnerName);
                        BodyCell.setCellStyle(bodyCellStyle);
                        
                        heightExsCtl = (int) Math.ceil((double) tmpExtCtl.controlDescription.length() / charColExsCntl) * 300;
                    }

                    if (proIterator.hasNext()) {
                        tmpProCtl = proIterator.next();

                        // Proposed control descr is in column W
                        BodyCell = bodyRow.createCell(22);
                        BodyCell.setCellValue(tmpProCtl.controlDescription);
                        BodyCell.setCellStyle(bodyCellStyle);

                        // Proposed control hierarchy is in column Y
                        BodyCell = bodyRow.createCell(24);
                        BodyCell.setCellValue(tmpProCtl.controlHierarchyName);
                        BodyCell.setCellStyle(bodyCellStyle);

                        // Proposed control owner is in column Z
                        BodyCell = bodyRow.createCell(25);
                        BodyCell.setCellValue(tmpProCtl.controlOwnerName);
                        BodyCell.setCellStyle(bodyCellStyle);

                        // Proposed control recomendation is in column AA
                        BodyCell = bodyRow.createCell(26);
                        BodyCell.setCellValue(tmpProCtl.controlRecommendName);
                        BodyCell.setCellStyle(bodyCellStyle);
                        
                        heightProCtl = (int) Math.ceil((double) tmpProCtl.controlDescription.length() / charColProCntl) * 300;
                    }

                    // After each iteration the row number will incresed by 1
                    currentRow++;
                    
                    // Setting the row height 
                    if (heightExsCtl > finalHeight){
                        finalHeight = heightExsCtl;
                    }
                    if (heightProCtl > finalHeight){
                        finalHeight = heightProCtl;
                    }
                    bodyRow.setHeight((short) finalHeight);
                }

                // We move i to the next hazard, we reduce it on -1 because in the next for cycle will increased it on 1
                i = i + numberOfRows - 1;
            } else {
                System.out.println("managedBeans.trees_MB.exportExcel() -> There is an error calculating the number of rows based on the existing and proposed controls.");
                break;
            }
        }

        try {
            // Prepare response.
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.setResponseContentType("application/vnd.ms-excel");
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

            // Write file to response body.
            workbook.write(externalContext.getResponseOutputStream());

            // Inform JSF that response is completed and it thus doesn"t have to navigate.
            facesContext.responseComplete();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ex.toString());
        } catch (IOException ex) {
            Logger.getLogger(ex.toString());
        }
    }

    private CellStyle styleHeaderGenerator(String style, CellStyle headerCellStyle, Font headerFont) {
        switch (style) {
            case "Blue":
                headerFont.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
                headerCellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.DARK_BLUE.getIndex());
                break;
            case "Grey":
                headerFont.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
                headerCellStyle.setFillForegroundColor((short) 22);
                break;
            case "Pink":
                headerFont.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
                headerCellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.PINK.getIndex());
                break;
            case "Red":
                headerFont.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
                headerCellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.BROWN.getIndex());
                break;
            case "Yellow":
                headerCellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());
                break;
            default:
                headerCellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
                break;
        }

        headerFont.setFontHeightInPoints((short) 11);
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setWrapText(true);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        return headerCellStyle;
    }

    private int calcNumberOfRows(int exsControls, int proControls) {
        if (exsControls == 0 && proControls == 0) {
            return 1;
        } else if (exsControls == 0 && proControls != 0) {
            return proControls;
        } else if (exsControls != 0 && proControls == 0) {
            return exsControls;
        } else if (exsControls != 0 && proControls != 0) {
            return exsControls * proControls;
        }
        return 0;
    }

    private int calcNumberOfRowsPerHazard(int exsControls, int proControls) {
        if (exsControls == 0 && proControls == 0) {
            return 1;
        } else if (exsControls == 0 && proControls != 0) {
            return proControls;
        } else if (exsControls != 0 && proControls == 0) {
            return exsControls;
        } else if (exsControls != 0 && proControls != 0) {
            return Math.max(exsControls, proControls);
        }
        return 0;
    }

    private String checkNullString(Object valueToCheck) {
        if (valueToCheck == null) {
            return "";
        } else {
            return valueToCheck.toString();
        }
    }

    private String checkNullDate(Object valueToCheck) {
        if (valueToCheck == null) {
            return "";
        } else {
            return new SimpleDateFormat("yyyy-MM-dd").format(valueToCheck);
        }
    }

    private int calculateSizePerRow(String[][] listOfHeaders, exportedHazard hazardInfo, int numberOfRows) {
        int maxNoLines = (int) Math.ceil((double) hazardInfo.hazardCauses.length() / getCharPerLine("Cause / Precursor / Events", listOfHeaders));
        int tmpNoLines;
        
        tmpNoLines = (int) Math.ceil((double)hazardInfo.hazardDescription.length() / getCharPerLine("Risk Description", listOfHeaders));
        if (tmpNoLines > maxNoLines) {
            maxNoLines = tmpNoLines;
        }
        
        tmpNoLines = (int) Math.ceil((double)hazardInfo.hazardConsequences.length() / getCharPerLine("Potential Impacts", listOfHeaders));
        if (tmpNoLines > maxNoLines) {
            maxNoLines = tmpNoLines;
        }
        
        tmpNoLines = (int) Math.ceil((double)hazardInfo.hazardComment.length() / getCharPerLine("Comments / Assumptions", listOfHeaders));
        if (tmpNoLines > maxNoLines) {
            maxNoLines = tmpNoLines;
        }
        
        // Each line Height is around 300 so the number of lines required multiplied by 300 divided by the required rows for controls
        int minHeightRequired = (int) Math.round((maxNoLines * 300) / numberOfRows);
        
        if (minHeightRequired > 600){
            return minHeightRequired;
        } else {
            return 600;
        }
    }
    
    private int getCharPerLine(String lookedValue, String[][] listOfHeaders){
        for (String[] listOfHeader : listOfHeaders) {
            if (listOfHeader[0].equals(lookedValue)) {
                return Integer.parseInt(listOfHeader[2]);
            }
        }
        return 1;
    }

    class exportedHazard {

        public String hazardId;
        public String locationName;
        public String hazardContextName;
        public String hazardCauses;
        public String hazardDescription;
        public String ownerName;
        public String hazardConsequences;
        public String hazardTypeName;
        public String riskClassName;
        public String severityScore;
        public String frequencyScore;
        public String riskScore;
        public String hazardComment;
        public String hazardDate;
        public String hazardWorkshop;
        public String hazardStatusName;

        public exportedHazard(String hazard, String locationName, String hazardContextName, String hazardCauses, String hazardDescription, String ownerName, String hazardConsequences, String hazardTypeName, String riskClassName, String severityScore, String frequencyScore, String riskScore, String hazardComment, String hazardDate, String hazardWorkshop, String hazardStatusName) {
            this.hazardId = hazard;
            this.locationName = locationName;
            this.hazardContextName = hazardContextName;
            this.hazardCauses = hazardCauses;
            this.hazardDescription = hazardDescription;
            this.ownerName = ownerName;
            this.hazardConsequences = hazardConsequences;
            this.hazardTypeName = hazardTypeName;
            this.riskClassName = riskClassName;
            this.severityScore = severityScore;
            this.frequencyScore = frequencyScore;
            this.riskScore = riskScore;
            this.hazardComment = hazardComment;
            this.hazardDate = hazardDate;
            this.hazardWorkshop = hazardWorkshop;
            this.hazardStatusName = hazardStatusName;
        }
    }

    class exportedExsControl {

        public int controlId;
        public String controlDescription;
        public String controlOwnerName;

        public exportedExsControl() {
        }

        public exportedExsControl(int controlId) {
            this.controlId = controlId;
        }

        public exportedExsControl(int controlId, String controlDescription, String controlOwnerName) {
            this.controlId = controlId;
            this.controlDescription = controlDescription;
            this.controlOwnerName = controlOwnerName;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final exportedExsControl other = (exportedExsControl) obj;
            return this.controlId == other.controlId;
        }
    }

    class exportedProControl extends exportedExsControl {

        public String controlHierarchyName;
        public String controlRecommendName;

        public exportedProControl() {
        }

        public exportedProControl(int controlId) {
            super(controlId);
        }

        public exportedProControl(int controlId, String controlDescription, String controlOwnerName, String controlHierarchyName, String controlRecommendName) {
            super(controlId, controlDescription, controlOwnerName);
            this.controlHierarchyName = controlHierarchyName;
            this.controlRecommendName = controlRecommendName;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final exportedExsControl other = (exportedProControl) obj;
            return this.controlId == other.controlId;
        }

    public List<String> getIdsForExport() {
        return getCheckedHazards().stream().map(h -> h.getHazardObj().getHazardId()).collect(Collectors.toList());
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
}
