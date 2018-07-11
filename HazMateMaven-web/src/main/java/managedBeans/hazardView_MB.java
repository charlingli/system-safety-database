package managedBeans;

import customObjects.searchObject;
import customObjects.treeNodeObject;
import ejb.DbHazardFacadeLocal;
import entities.*;
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
import ejb.DbtreeLevel1FacadeLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.context.FacesContext;
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
    private DbtreeLevel1FacadeLocal dbtreeLevel1Facade;
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

    private String searchedCauses;
    private String searchedConsequences;
    private String searchedHazardDescription;
    private String searchedHazardComments;
    private String searchedControlDescription;
    private String searchedControlJustification;
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
    private List<DbHazard> hazardSearchedlist;
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
    
    public hazardView_MB() {
    }

    @PostConstruct
    public void init() {
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
        enableQueryDescr = false;
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

    public List<DbHazard> getHazardSearchedlist() {
        return hazardSearchedlist;
    }

    public void setHazardSearchedlist(List<DbHazard> hazardSearchedlist) {
        this.hazardSearchedlist = hazardSearchedlist;
    }

    public boolean isEnableQueryDescr() {
        return enableQueryDescr;
    }

    public void setEnableQueryDescr(boolean enableQueryDescr) {
        this.enableQueryDescr = enableQueryDescr;
    }

    //This method creates the search object, based on the selected parameters.
    public void constructSearchObject() {
        // Initialising a couple of variables
        List<treeNodeObject> treeCheckedNodesList = new ArrayList<>();
        List<searchObject> searchCompositeList = new ArrayList<>();

        // Start the grind of finding each entry to each field
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

        if (!searchCompositeList.isEmpty() && !treeCheckedNodesList.isEmpty()) {
            hazardSearchedlist = dbHazardFacade.findHazardsByFieldsAndSbs(searchCompositeList, treeCheckedNodesList);
        } else if (!searchCompositeList.isEmpty() && treeCheckedNodesList.isEmpty()) {
            hazardSearchedlist = dbHazardFacade.findHazardsByFields(searchCompositeList);
        } else if (searchCompositeList.isEmpty() && !treeCheckedNodesList.isEmpty()) {
            hazardSearchedlist = dbHazardFacade.findHazardsBySbs(treeCheckedNodesList);
        } else {
            hazardSearchedlist = dbHazardFacade.findAllHazards();
        }

        if (!hazardSearchedlist.isEmpty()) {
            RequestContext.getCurrentInstance().execute("PF('widget_hazardsForm_fieldset').toggle()");
        }
    }
    
    public String popUpWindow(){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("hazardList", hazardSearchedlist);
        return "viewHazardDetail";
    }

    public void resetFields() {
        setSearchedHazardDescription(null);
        setSearchedCauses(null);
        setSearchedConsequences(null);
        setSearchedHazardComments(null);
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
        constructHtml(new ArrayList<>(), new ArrayList<>());
        enableQueryDescr = false;
    }

    private void constructHtml(List<searchObject> listFields, List<treeNodeObject> listSbs) {
        if (listFields.isEmpty() && listSbs.isEmpty()) {
            enableQueryDescr = true;
            htmlCode = "<h3><span class=\"queryDescr\">Showing all hazards asociated at least to one cause, consequence and control.</span></h3>";
        } else {
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
}
