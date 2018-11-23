/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;


import customObjects.searchObject;
import customObjects.treeNodeObject;
import ejb.DbHazardFacadeLocal;
import ejb.DbHazardSbsFacadeLocal;
import ejb.DbLocationFacadeLocal;
import ejb.DbOwnersFacadeLocal;
import ejb.DbProjectFacadeLocal;
import ejb.DbchangeTypeFacadeLocal;
import ejb.DbconstructionTypeFacadeLocal;
import ejb.DbgradeSeparationFacadeLocal;
import ejb.DbhazardActivityFacadeLocal;
import ejb.DbhazardContextFacadeLocal;
import ejb.DbhazardStatusFacadeLocal;
import ejb.DbhazardSystemStatusFacadeLocal;
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
import entities.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Charling Li
 */
@Named(value = "editHazard_MB")
@ViewScoped
public class editHazard_MB implements Serializable {

    @EJB
    private DbhazardSystemStatusFacadeLocal dbhazardSystemStatusFacade;
    
    @EJB
    private DbHazardSbsFacadeLocal dbHazardSbsFacade;

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
    private DbchangeTypeFacadeLocal dbchangeTypeFacade;

    @EJB
    private DbconstructionTypeFacadeLocal dbconstructionTypeFacade;

    @EJB
    private DbgradeSeparationFacadeLocal dbgradeSeparationFacade;

    @EJB
    private DbProjectFacadeLocal dbProjectFacade;

    @EJB
    private DbLocationFacadeLocal dbLocationFacade;
    
    // Construct lists to display as search options
    private List<DbLocation> listHL;
    private List<DbProject> listHP;
    private List<DbgradeSeparation> listGS;
    private List<DbconstructionType> listCN;
    private List<DbchangeType> listCH;
    private List<DbhazardActivity> listHA;
    private List<DbhazardContext> listHC;
    private List<DbhazardType> listHT;
    private List<DbhazardStatus> listHS;
    private List<DbOwners> listHO;
    private List<DbhazardSystemStatus> listSS;
    private List<DbriskClass> listRC;
    private List<DbriskFrequency> listRF;
    private List<DbriskSeverity> listRS;
    
    // Get the current active user to check for role permissions
    private DbUser activeUser;
    private boolean adminUser;

    // Construct query
    private String htmlCode;
    private boolean showQuery;
    private List<searchObject> listSearchObject;
    
    // Hazard list
    private List<DbHazard> listHazards;
    
    // Create variables to save search terms
    private String searchHI;
    private Date searchDT;
    private String searchHD;
    private String searchHM;
    private String[] searchHL;
    private String[] searchHP;
    private String[] searchGS;
    private String[] searchCN;
    private String[] searchCH;
    private String[] searchHA;
    private String[] searchHC;
    private String[] searchHT;
    private String[] searchHS;
    private String[] searchHO;
    private String[] searchSS;
    private String[] searchMD;
    
    // Check redirection source
    private String redirectionSource;

    // Create an object to save current hazard being edited
    private DbHazard currentHazard = new DbHazard();
    private DbHazard savedHazard = new DbHazard();
    private boolean showEdit;
    private TreeNode[] currentTree;
    private TreeNode[] savedTree;
    private TreeNode root;
    private List<treeNodeObject> newTree;
    
    // Create display variables since objects within the hazard object cannot be edited directly
    private int editingHA;
    private int editingHC;
    private int editingHT;
    private int editingHS;
    private int editingHO;
    private int editingRC;
    private int editingRF;
    private int editingRS;
    private int editingSS;

    public editHazard_MB() {
    }

    public String getRedirectionSource() {
        return redirectionSource;
    }

    public void setRedirectionSource(String redirectionSource) {
        this.redirectionSource = redirectionSource;
    }
    
    public boolean isAdminUser() {
        return adminUser;
    }

    public void setAdminUser(boolean adminUser) {
        this.adminUser = adminUser;
    }

    public String getSearchHI() {
        return searchHI;
    }

    public void setSearchHI(String searchHI) {
        this.searchHI = searchHI;
    }

    public Date getSearchDT() {
        return searchDT;
    }

    public void setSearchDT(Date searchDT) {
        this.searchDT = searchDT;
    }

    public String getSearchHD() {
        return searchHD;
    }

    public void setSearchHD(String searchHD) {
        this.searchHD = searchHD;
    }

    public String getSearchHM() {
        return searchHM;
    }

    public void setSearchHM(String searchHM) {
        this.searchHM = searchHM;
    }

    public String[] getSearchHL() {
        return searchHL;
    }

    public void setSearchHL(String[] searchHL) {
        this.searchHL = searchHL;
    }

    public String[] getSearchHP() {
        return searchHP;
    }

    public void setSearchHP(String[] searchHP) {
        this.searchHP = searchHP;
    }

    public String[] getSearchGS() {
        return searchGS;
    }

    public void setSearchGS(String[] searchGS) {
        this.searchGS = searchGS;
    }

    public String[] getSearchCN() {
        return searchCN;
    }

    public void setSearchCN(String[] searchCN) {
        this.searchCN = searchCN;
    }

    public String[] getSearchCH() {
        return searchCH;
    }

    public void setSearchCH(String[] searchCH) {
        this.searchCH = searchCH;
    }

    public String[] getSearchHA() {
        return searchHA;
    }

    public void setSearchHA(String[] searchHA) {
        this.searchHA = searchHA;
    }

    public String[] getSearchHC() {
        return searchHC;
    }

    public void setSearchHC(String[] searchHC) {
        this.searchHC = searchHC;
    }

    public String[] getSearchHT() {
        return searchHT;
    }

    public void setSearchHT(String[] searchHT) {
        this.searchHT = searchHT;
    }

    public String[] getSearchHS() {
        return searchHS;
    }

    public void setSearchHS(String[] searchHS) {
        this.searchHS = searchHS;
    }

    public String[] getSearchHO() {
        return searchHO;
    }

    public void setSearchHO(String[] searchHO) {
        this.searchHO = searchHO;
    }

    public String[] getSearchSS() {
        return searchSS;
    }

    public void setSearchSS(String[] searchSS) {
        this.searchSS = searchSS;
    }

    public String[] getSearchMD() {
        return searchMD;
    }

    public void setSearchMD(String[] searchMD) {
        this.searchMD = searchMD;
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

    public List<DbgradeSeparation> getListGS() {
        return listGS;
    }

    public void setListGS(List<DbgradeSeparation> listGS) {
        this.listGS = listGS;
    }

    public List<DbconstructionType> getListCN() {
        return listCN;
    }

    public void setListCN(List<DbconstructionType> listCN) {
        this.listCN = listCN;
    }

    public List<DbchangeType> getListCH() {
        return listCH;
    }

    public void setListCH(List<DbchangeType> listCH) {
        this.listCH = listCH;
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

    public List<DbhazardSystemStatus> getListSS() {
        return listSS;
    }

    public void setListSS(List<DbhazardSystemStatus> listSS) {
        this.listSS = listSS;
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

    public String getHtmlCode() {
        return htmlCode;
    }

    public void setHtmlCode(String htmlCode) {
        this.htmlCode = htmlCode;
    }

    public boolean isShowQuery() {
        return showQuery;
    }

    public void setShowQuery(boolean showQuery) {
        this.showQuery = showQuery;
    }
    
    public DbHazard getCurrentHazard() {
        return currentHazard;
    }

    public void setCurrentHazard(DbHazard currentHazard) {    
        this.currentHazard = currentHazard;
    }

    public boolean isShowEdit() {
        return showEdit;
    }

    public void setShowEdit(boolean showEdit) {
        this.showEdit = showEdit;
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

    public int getEditingHA() {
        return editingHA;
    }

    public void setEditingHA(int editingHA) {
        this.editingHA = editingHA;
    }

    public int getEditingHC() {
        return editingHC;
    }

    public void setEditingHC(int editingHC) {
        this.editingHC = editingHC;
    }

    public int getEditingHT() {
        return editingHT;
    }

    public void setEditingHT(int editingHT) {
        this.editingHT = editingHT;
    }

    public int getEditingHS() {
        return editingHS;
    }

    public void setEditingHS(int editingHS) {
        this.editingHS = editingHS;
    }

    public int getEditingHO() {
        return editingHO;
    }

    public void setEditingHO(int editingHO) {
        this.editingHO = editingHO;
    }

    public int getEditingRC() {
        return editingRC;
    }

    public void setEditingRC(int editingRC) {
        this.editingRC = editingRC;
    }

    public int getEditingRF() {
        return editingRF;
    }

    public void setEditingRF(int editingRF) {
        this.editingRF = editingRF;
    }

    public int getEditingRS() {
        return editingRS;
    }

    public void setEditingRS(int editingRS) {
        this.editingRS = editingRS;
    }

    public int getEditingSS() {
        return editingSS;
    }

    public void setEditingSS(int editingSS) {
        this.editingSS = editingSS;
    }
    
    public List<DbHazard> getListHazards() {
        return listHazards;
    }

    public void setListHazards(List<DbHazard> listHazards) {    
        this.listHazards = listHazards;
    }
    
    @PostConstruct
    public void init() {
        activeUser = (DbUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("activeUser");
        if (activeUser.getRoleId().getRoleWFApprover().equals("Y")) {
            setAdminUser(true);
        } else {
            setAdminUser(false);
        }
        setListHL(dbLocationFacade.findAll());
        setListHP(dbProjectFacade.findAll());
        setListGS(dbgradeSeparationFacade.findAll());
        setListCN(dbconstructionTypeFacade.findAll());
        setListCH(dbchangeTypeFacade.findAll());
        setListHA(dbhazardActivityFacade.findAll());
        setListHC(dbhazardContextFacade.findAll());
        setListHT(dbhazardTypeFacade.findAll());
        setListHS(dbhazardStatusFacade.findAll());
        setListHO(dbOwnersFacade.findAll());
        setListRC(dbriskClassFacade.findAll());
        setListRF(dbriskFrequencyFacade.findAll());
        setListRS(dbriskSeverityFacade.findAll());
        setListSS(dbhazardSystemStatusFacade.findAll());
        setShowEdit(false);
        checkRedirectionSource();
    }
    
    private void checkRedirectionSource() {
        DbHazard initialHazard = (DbHazard) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("hazardRelObj");
        redirectionSource = "";
        if (initialHazard != null) {
            editHazard(initialHazard);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("hazardRelObj");
            redirectionSource = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("redirectionSource");
        }
    }
    
    public void editHazard(DbHazard hazardObject) {
        showEdit = true;
        currentHazard = hazardObject;
        
        setEditingHA(currentHazard.getHazardActivity().getActivityId());
        setEditingHC(currentHazard.getHazardContextId().getHazardContextId());
        setEditingHT(currentHazard.getHazardTypeId().getHazardTypeId());
        setEditingHS(currentHazard.getHazardStatusId().getHazardStatusId());
        setEditingHO(currentHazard.getOwnerId().getOwnerId());
        setEditingRC(currentHazard.getRiskClassId().getRiskClassId());
        setEditingRF(currentHazard.getRiskFrequencyId().getRiskFrequencyId());
        setEditingRS(currentHazard.getRiskSeverityId().getRiskSeverityId());
        setEditingSS(currentHazard.getHazardSystemStatus().getSystemStatusId());
        
        savedHazard = new DbHazard();
        savedHazard.setHazardId(currentHazard.getHazardId());
        savedHazard.setHazardDate(currentHazard.getHazardDate());
        savedHazard.setHazardDescription(currentHazard.getHazardDescription());
        savedHazard.setHazardComment(currentHazard.getHazardComment());
        savedHazard.setHazardWorkshop(currentHazard.getHazardWorkshop());
        savedHazard.setHazardReview(currentHazard.getHazardReview());
        savedHazard.setLegacyId(currentHazard.getLegacyId());
        
        savedHazard.setHazardActivity(currentHazard.getHazardActivity());
        savedHazard.setHazardContextId(currentHazard.getHazardContextId());
        savedHazard.setHazardTypeId(currentHazard.getHazardTypeId());
        savedHazard.setHazardStatusId(currentHazard.getHazardStatusId());
        savedHazard.setOwnerId(currentHazard.getOwnerId());
        savedHazard.setRiskClassId(currentHazard.getRiskClassId());
        savedHazard.setRiskFrequencyId(currentHazard.getRiskFrequencyId());
        savedHazard.setRiskSeverityId(currentHazard.getRiskSeverityId());
        savedHazard.setHazardSystemStatus(currentHazard.getHazardSystemStatus());
        populateTree();
    }
    
    public void constructSearchObject() {
        listSearchObject = new ArrayList<>();
        listHazards = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (!getSearchHI().isEmpty()) {
            listSearchObject.add(new searchObject("hazardId", getSearchHI(), "string", "DbHazard", null, null, null, "like", "Hazard ID"));
        }
        if (getSearchDT() != null) {
        listSearchObject.add(new searchObject("hazardDate", df.format(getSearchDT()), "date", "DbHazard", null, null, null, "=", "Hazard Date"));
        }
        if (getSearchHD() != null && getSearchHD().length() > 0) {
            listSearchObject.add(new searchObject("hazardDescription", getSearchHD(), "string", "DbHazard", null, null, null, "like", "Hazard Description"));
        }
        if (getSearchHM() != null && getSearchHM().length() > 0) {
            listSearchObject.add(new searchObject("hazardComment", getSearchHM(), "string", "DbHazard", null, null, null, "like", "Hazard Comment"));
        }
        if (getSearchHL() != null && getSearchHL().length > 0) {
            String terms = String.join(",", getSearchHL());
            listSearchObject.add(new searchObject("locationId", terms, "int", "DbHazard", "hazardLocation", null, null, "in", "Hazard Location"));
        }
        if (getSearchHP() != null && getSearchHP().length > 0) {
            String terms = String.join(",", getSearchHP());
            listSearchObject.add(new searchObject("projectId", terms, "int", "DbHazard", "hazardLocation", "projectId", null, "in", "Hazard Project"));
        }
        if (getSearchGS() != null && getSearchGS().length > 0) {
            String terms = String.join(",", getSearchGS());
            listSearchObject.add(new searchObject("gradeSeparationId", terms, "int", "DbHazard", "hazardLocation", "locationGradeSeparation", null, "in", "Grade Separation"));
        }
        if (getSearchCN() != null && getSearchCN().length > 0) {
            String terms = String.join(",", getSearchCN());
            listSearchObject.add(new searchObject("constructionTypeId", terms, "int", "DbHazard", "hazardLocation", "locationConstructionType", null, "in", "Construction Type"));
        }
        if (getSearchCH() != null && getSearchCH().length > 0) {
            String terms = String.join(",", getSearchCH());
            listSearchObject.add(new searchObject("changeTypeId", terms, "int", "DbHazard", "hazardLocation", "locationChangeType", null, "in", "Change Type"));
        }
        if (getSearchHA() != null && getSearchHA().length > 0) {
            String terms = String.join(",", getSearchHA());
            listSearchObject.add(new searchObject("activityId", terms, "int", "DbHazard", "hazardActivity", null, null, "in", "Hazard Activity"));
        }
        if (getSearchHC() != null && getSearchHC().length > 0) {
            String terms = String.join(",", getSearchHC());
            listSearchObject.add(new searchObject("hazardContextId", terms, "int", "DbHazard", "hazardContextId", null, null, "in", "Hazard Context"));
        }
        if (getSearchHT() != null && getSearchHT().length > 0) {
            String terms = String.join(",", getSearchHT());
            listSearchObject.add(new searchObject("hazardTypeId", terms, "int", "DbHazard", "hazardTypeId", null, null, "in", "Hazard Type"));
        }
        if (getSearchHS() != null && getSearchHS().length > 0) {
            String terms = String.join(",", getSearchHS());
            listSearchObject.add(new searchObject("hazardStatusId", terms, "int", "DbHazard", "hazardStatusId", null, null, "in", "Hazard Staus"));
        }
        if (getSearchHO() != null && getSearchHO().length > 0) {
            String terms = String.join(",", getSearchHO());
            listSearchObject.add(new searchObject("ownerId", terms, "int", "DbHazard", "ownerId", null, null, "in", "Hazard Owner"));
        }
        if (getSearchSS() != null && getSearchSS().length > 0) {
            String terms = String.join(",", getSearchSS());
            listSearchObject.add(new searchObject("systemStatusId", terms, "int", "DbHazard", "hazardSystemStatus", null, null, "in", "Hazard System Status"));
        }
        constructHtml(listSearchObject);
//        listHazards = dbHazardFacade.findHazardsByFieldsOnly(listSearchObject);
    }
    
    public void cancelEdit() {
        setShowEdit(false);
    }
    
    private boolean hazardChanged() {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd");
        if (ft.format(currentHazard.getHazardDate()).equals(ft.format(savedHazard.getHazardDate())) && 
                currentHazard.getHazardDescription().equals(savedHazard.getHazardDescription()) &&
                currentHazard.getHazardComment().equals(savedHazard.getHazardComment()) &&
                currentHazard.getHazardActivity().getActivityId().equals(savedHazard.getHazardActivity().getActivityId()) &&
                currentHazard.getHazardContextId().getHazardContextId().equals(savedHazard.getHazardContextId().getHazardContextId()) &&
                currentHazard.getHazardTypeId().getHazardTypeId().equals(savedHazard.getHazardTypeId().getHazardTypeId()) &&
                currentHazard.getHazardStatusId().getHazardStatusId().equals(savedHazard.getHazardStatusId().getHazardStatusId()) &&
                currentHazard.getOwnerId().getOwnerId().equals(savedHazard.getOwnerId().getOwnerId()) &&
                currentHazard.getHazardWorkshop().equals(savedHazard.getHazardWorkshop()) &&
                currentHazard.getRiskClassId().getRiskClassId().equals(savedHazard.getRiskClassId().getRiskClassId()) &&
                currentHazard.getRiskFrequencyId().getRiskFrequencyId().equals(savedHazard.getRiskFrequencyId().getRiskFrequencyId()) &&
                currentHazard.getRiskSeverityId().getRiskSeverityId().equals(savedHazard.getRiskSeverityId().getRiskSeverityId()) &&
                currentHazard.getLegacyId().equals(savedHazard.getLegacyId()) &&
                currentHazard.getHazardReview().equals(savedHazard.getHazardReview()) &&
                currentHazard.getHazardSystemStatus().getSystemStatusId().equals(savedHazard.getHazardSystemStatus().getSystemStatusId())) {
            return false;
        }
        return true;
    }
    
    public String editHazard() {
        fillCurrentHazard();
        String responseStr = "No changes have been made.";
        if (!Arrays.equals(currentTree, savedTree)) {
            dbHazardSbsFacade.removeHazardSbs(currentHazard.getHazardId());
            if (currentTree != null && currentTree.length > 0) {
                collectSbs();
                addSbs();
                currentHazard.setUpdatedDateTime(new Date());
                currentHazard.setUserIdUpdate(activeUser.getUserId());
                dbHazardFacade.edit(currentHazard);
                responseStr = "The sbs tree has been edited successfully.";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Please select at least one SBS node!"));
                return null;
            }
        }
        if (hazardChanged()) {
            currentHazard.setRiskScore(dbHazardFacade.calculateRiskScore(currentHazard.getRiskFrequencyId().getFrequencyValue(), currentHazard.getRiskSeverityId().getSeverityValue()));
            currentHazard.setUpdatedDateTime(new Date());
            currentHazard.setUserIdUpdate(activeUser.getUserId());
            dbHazardFacade.edit(currentHazard);
            responseStr = "The hazard object has been edited successfully.";
            if (!Arrays.equals(currentTree, savedTree) && currentTree != null && currentTree.length > 0) {
                responseStr = "The hazard object and sbs tree have been edited successfully.";
            }
        }
        if (redirectionSource.equals("UserWorkflow")) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("hazardRelObj", getCurrentHazard());
            return "/data/relations/hazardsRelation";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", responseStr));
        }
        constructSearchObject();
        setShowEdit(false);
        return "";
    }
    
    public void fillCurrentHazard() {
        DbhazardActivity activityObject = new DbhazardActivity();
        DbhazardContext contextObject = new DbhazardContext();
        DbhazardType typeObject = new DbhazardType();
        DbhazardStatus statusObject = new DbhazardStatus();
        DbOwners ownerObject = new DbOwners();
        DbriskClass classObject = new DbriskClass();
        DbriskFrequency frequencyObject = new DbriskFrequency();
        DbriskSeverity severityObject = new DbriskSeverity();
        DbhazardSystemStatus systemObject = new DbhazardSystemStatus();
        
        activityObject.setActivityId(editingHA);
        contextObject.setHazardContextId(editingHC);
        typeObject.setHazardTypeId(editingHT);
        statusObject.setHazardStatusId(editingHS);
        ownerObject.setOwnerId(editingHO);
        classObject.setRiskClassId(editingRC);
        frequencyObject.setRiskFrequencyId(editingRF);
        frequencyObject.setFrequencyValue(dbriskFrequencyFacade.getRiskFrequency(editingRF).get(0).getFrequencyValue());
        severityObject.setRiskSeverityId(editingRS);
        severityObject.setSeverityValue(dbriskSeverityFacade.getRiskSeverity(editingRS).get(0).getSeverityValue());
        systemObject.setSystemStatusId(editingSS);
        
        currentHazard.setHazardActivity(activityObject);
        currentHazard.setHazardContextId(contextObject);
        currentHazard.setHazardTypeId(typeObject);
        currentHazard.setHazardStatusId(statusObject);
        currentHazard.setOwnerId(ownerObject);
        currentHazard.setRiskClassId(classObject);
        currentHazard.setRiskFrequencyId(frequencyObject);
        currentHazard.setRiskSeverityId(severityObject);
        savedHazard.setHazardSystemStatus(systemObject);
        
    }
    
    public void collectSbs() {
        newTree = new ArrayList<>();
        DbtreeLevel1 tmpTreeNode = dbtreeLevel1Facade.findByName(root.getChildren().get(0).toString());
        Integer rootId = tmpTreeNode.getTreeLevel1Index();
        for (TreeNode node : currentTree) {
            if (node.getParent().toString().equals("Root")) {
                //Add the tree Node and include the root index in each child node
                rootId = tmpTreeNode.getTreeLevel1Index();
                treeNodeObject tmpNode = new treeNodeObject();
                tmpNode.setNodeId(rootId.toString() + ".");
                tmpNode.setNodeName(node.getData().toString());
                newTree.add(tmpNode);
            } else {
                String parts[] = node.getData().toString().split(" ", 2);
                treeNodeObject tmpNode = new treeNodeObject();
                tmpNode.setNodeId(rootId.toString() + "." + parts[0]);
                tmpNode.setNodeName(parts[1]);
                newTree.add(tmpNode);
            }
        }
    } 
    
    public void addSbs() {
        DbHazardSbsPK tempSbsPKObject = new DbHazardSbsPK();
        DbHazardSbs tempSbsObject = new DbHazardSbs();
        DbHazard tempFKObject = new DbHazard();
        
        tempSbsPKObject.setHazardId(currentHazard.getHazardId());
        tempFKObject.setHazardId(currentHazard.getHazardId());
        tempSbsObject.setDbHazard(tempFKObject);
        for (int i = 0; i < newTree.size(); i++) {
            treeNodeObject selectedTreeNodeObject = newTree.get(i);
            tempSbsPKObject.setSbsId(selectedTreeNodeObject.getNodeId());
            tempSbsObject.setDbHazardSbsPK(tempSbsPKObject);
            dbHazardSbsFacade.create(tempSbsObject);
        }
    }
    
    private void constructHtml(List<searchObject> listFields) {
        if (listFields.isEmpty()) {
            showQuery = true;
            htmlCode = "<h3><span class=\"queryDescr\">Showing all hazards associated to at least one cause, consequence and control.</span></h3>";
        } else {
            showQuery = true;
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
                    } else if (tmpSrch.getRelationType().equals("=")) {
                        if (includeAnd) {
                            htmlCode += "<p class=\"queryDescr_and\"><span>AND</span></p>";
                        }
                        htmlCode += "<h4 class=\"queryDescr\"><span class=\"queryDescr\">" + tmpSrch.getFieldDescription() + "</span> is equal to:</h4>";
                        htmlCode += "<p class=\"queryDescr_field\">" + tmpSrch.getUserInput() + "</p>";
                    }
                    if (!includeAnd) {
                        includeAnd = true;
                    }
                }
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
                        List<DbLocation> result = listHL.stream()
                                .filter(a -> a.getLocationId().equals(Integer.parseInt(tmpId)))
                                .collect(Collectors.toList());
                        resultantString.append(result.get(0).getLocationName());
                        resultantString.append(", ");
                    }
                    break;
                case "projectId":
                    for (String tmpId : stringListIds) {
                        List<DbProject> result = listHP.stream()
                                .filter(a -> a.getProjectId().equals(Integer.parseInt(tmpId)))
                                .collect(Collectors.toList());
                        resultantString.append(result.get(0).getProjectName());
                        resultantString.append(", ");
                    }
                    break;
                case "gradeSeparationId":
                    for (String tmpId : stringListIds) {
                        List<DbgradeSeparation> result = listGS.stream()
                                .filter(a -> a.getGradeSeparationId().equals(Integer.parseInt(tmpId)))
                                .collect(Collectors.toList());
                        resultantString.append(result.get(0).getGradeSeparationName());
                        resultantString.append(", ");
                    }
                    break;
                case "constructionTypeId":
                    for (String tmpId : stringListIds) {
                        List<DbconstructionType> result = listCN.stream()
                                .filter(a -> a.getConstructionTypeId().equals(Integer.parseInt(tmpId)))
                                .collect(Collectors.toList());
                        resultantString.append(result.get(0).getConstructionTypeName());
                        resultantString.append(", ");
                    }
                    break;
                case "changeTypeId":
                    for (String tmpId : stringListIds) {
                        List<DbchangeType> result = listCH.stream()
                                .filter(a -> a.getChangeTypeId().equals(Integer.parseInt(tmpId)))
                                .collect(Collectors.toList());
                        resultantString.append(result.get(0).getChangeTypeName());
                        resultantString.append(", ");
                    }
                    break;
                case "activityId":
                    for (String tmpId : stringListIds) {
                        List<DbhazardActivity> result = listHA.stream()
                                .filter(a -> a.getActivityId().equals(Integer.parseInt(tmpId)))
                                .collect(Collectors.toList());
                        resultantString.append(result.get(0).getActivityName());
                        resultantString.append(", ");
                    }
                    break;
                case "hazardContextId":
                    for (String tmpId : stringListIds) {
                        List<DbhazardContext> result = listHC.stream()
                                .filter(a -> a.getHazardContextId().equals(Integer.parseInt(tmpId)))
                                .collect(Collectors.toList());
                        resultantString.append(result.get(0).getHazardContextName());
                        resultantString.append(", ");
                    }
                    break;
                case "hazardTypeId":
                    for (String tmpId : stringListIds) {
                        List<DbhazardType> result = listHT.stream()
                                .filter(a -> a.getHazardTypeId().equals(Integer.parseInt(tmpId)))
                                .collect(Collectors.toList());
                        resultantString.append(result.get(0).getHazardTypeName());
                        resultantString.append(", ");
                    }
                    break;
                case "hazardStatusId":
                    for (String tmpId : stringListIds) {
                        List<DbhazardStatus> result = listHS.stream()
                                .filter(a -> a.getHazardStatusId().equals(Integer.parseInt(tmpId)))
                                .collect(Collectors.toList());
                        resultantString.append(result.get(0).getHazardStatusName());
                        resultantString.append(", ");
                    }
                    break;
                case "ownerId":
                    for (String tmpId : stringListIds) {
                        List<DbOwners> result = listHO.stream()
                                .filter(a -> a.getOwnerId().equals(Integer.parseInt(tmpId)))
                                .collect(Collectors.toList());
                        resultantString.append(result.get(0).getOwnerName());
                        resultantString.append(", ");
                    }
                    break;
                default:
                    break;
            }
        }
        return resultantString.toString().substring(0, resultantString.toString().length() - 2);
    }
    
    public void cancel() {
        showEdit = false;
    }

    public void resetFields() {
        setSearchHI(null);
        setSearchHD(null);
        setSearchDT(null);
        setSearchHM(null);
        setSearchHL(null);
        setSearchHP(null);
        setSearchGS(null);
        setSearchCN(null);
        setSearchCH(null);
        setSearchHA(null);
        setSearchHC(null);
        setSearchHT(null);
        setSearchHS(null);
        setSearchHO(null);
        constructHtml(new ArrayList<>());
        showQuery = false;
    }

    public void populateTree() {
        String editHazardId = currentHazard.getHazardId();
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
        currentTree = listTreeNode.toArray(new TreeNode[listTreeNode.size()]);
        savedTree = listTreeNode.toArray(new TreeNode[listTreeNode.size()]);
    }

    public Date currentDate() {
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }
    
    public void clearField(String id) {
        switch (id) {
            case "HIClear":
                setSearchHI(null);
                break;
            case "HDClear":
                setSearchHD(null);
                break;
            case "HMClear":
                setSearchHM(null);
                break;
            case "DTClear":
                setSearchDT(null);
                break;
            case "HLClear":
                setSearchHL(null);
                break;
            case "HPClear":
                setSearchHP(null);
                break;
            case "GSClear":
                setSearchGS(null);
                break;
            case "CNClear":
                setSearchCN(null);
                break;
            case "CHClear":
                setSearchCH(null);
                break;
            case "HAClear":
                setSearchHA(null);
                break;
            case "HCClear":
                setSearchHC(null);
                break;
            case "HTClear":
                setSearchHT(null);
                break;
            case "HSClear":
                setSearchHS(null);
                break;
            case "HOClear":
                setSearchHO(null);
                break;
        }
    }
}
