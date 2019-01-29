/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import customObjects.importObject;
import customObjects.similarityObject;
import customObjects.treeNodeObject;
import customObjects.validateIdObject;
import ejb.DbCauseFacadeLocal;
import ejb.DbConsequenceFacadeLocal;
import ejb.DbControlFacadeLocal;
import ejb.DbControlHazardFacadeLocal;
import ejb.DbHazardCauseFacadeLocal;
import ejb.DbHazardConsequenceFacadeLocal;
import ejb.DbHazardFacadeLocal;
import ejb.DbHazardSbsFacadeLocal;
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
import ejb.DbimportHeaderFacadeLocal;
import ejb.DbimportLineErrorFacadeLocal;
import ejb.DbimportLineFacadeLocal;
import ejb.DbindexedWordFacadeLocal;
import ejb.DbriskClassFacadeLocal;
import ejb.DbriskFrequencyFacadeLocal;
import ejb.DbriskSeverityFacadeLocal;
import ejb.DbsystemParametersFacadeLocal;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
//import javax.faces.el.ValueBinding;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;
import org.primefaces.util.ArrayUtils;

/**
 *
 * @author Charling Li
 */
@Named(value = "importHazard_MB")
@ViewScoped
public class importHazard_MB implements Serializable {

    @EJB
    private DbHazardSbsFacadeLocal dbHazardSbsFacade;

    @EJB
    private DbindexedWordFacadeLocal dbindexedWordFacade;

    @EJB
    private DbsystemParametersFacadeLocal dbsystemParametersFacade;

    @EJB
    private DbimportLineErrorFacadeLocal dbimportLineErrorFacade;

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
    
    private List<importObject> listLoadedLines;
    private List<importObject> listCheckedLines;
    
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

    private boolean showUpload;

    private wordProcessing_MB wordProcessing_MB;

    public importHazard_MB() {

    }

    public List<importObject> getListLoadedLines() {
        return listLoadedLines;
    }

    public void setListLoadedLines(List<importObject> listLoadedLines) {
        this.listLoadedLines = listLoadedLines;
    }

    public List<importObject> getListCheckedLines() {
        return listCheckedLines;
    }

    public void setListCheckedLines(List<importObject> listCheckedLines) {
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

    public boolean isShowUpload() {
        return showUpload;
    }

    public void setShowUpload(boolean showUpload) {
        this.showUpload = showUpload;
    }

    @PostConstruct
    public void init() {
        activeUser = (DbUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("activeUser");
        
        listLoadedLines = dbimportLineFacade.findNextLinesByUser(activeUser.getUserId()).stream().map(i -> new importObject(i, dbimportLineErrorFacade.listErrorsByLine(i.getDbimportLinePK().getProcessId(), i.getDbimportLinePK().getProcessIdLine()))).collect(Collectors.toList());
        listCheckedLines = new ArrayList<>();
        
        if (listLoadedLines.size() > 0) {
            setShowUpload(false);
        } else {
            setShowUpload(true);
        }
        
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

        wordProcessing_MB = (wordProcessing_MB) FacesContext.getCurrentInstance().getApplication().createValueBinding("#{wordProcessing_MB}").getValue(FacesContext.getCurrentInstance());
    }

    public void editCell(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        String editedRow = event.getRowKey();
        String[] lineIndex = editedRow.split("\\.");

        String processId = lineIndex[0];
        int processIdLine = Integer.valueOf(lineIndex[1]);

        DbimportLine lineObject = dbimportLineFacade.findLineById(processId, processIdLine).get(0);
        DbimportLineError errorObject;
        List<DbimportLineError> errorList;

        if (newValue != null && !newValue.equals(oldValue)) {
            boolean existingLine;
            switch (event.getColumn().getClientId().split(":")[3]) {
                case "DTCol":
                    Date newDate = (Date) newValue;
                    errorList = dbimportLineErrorFacade.findErrorByCell(processId, processIdLine, "hazardDate");
                    existingLine = false;
                    if (errorList.size() > 0) {
                        errorObject = errorList.get(0);
                        existingLine = true;
                    } else {
                        errorObject = new DbimportLineError();
                        errorObject.setDbimportLine(lineObject);
                        errorObject.setDbimportLineErrorPK(new DbimportLineErrorPK(processId, processIdLine, 30));
                    }
                    errorObject.setProcessErrorLocation("hazardDate");
                    if (!"".equals(newValue.toString())) {
                        lineObject.setHazardDate(newDate);
                        errorObject.setProcessErrorStatus("F");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        }
                    } else {
                        errorObject.setProcessErrorCode(new DbimportErrorCode(1));
                        errorObject.setProcessErrorStatus("P");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "A date is required."));
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        } else {
                            dbimportLineErrorFacade.create(errorObject);
                        }
                    }
                    break;
                case "LICol":
                    lineObject.setHazardLegacyId(newValue.toString());
                    break;
                case "HDCol":
                    List<DbimportLineError> dErrorList = dbimportLineErrorFacade.findErrorByCell(processId, processIdLine, "hazardDescription");
                    List<DbimportLineError> warningList = dbimportLineErrorFacade.findErrorByCell(processId, processIdLine, "hazard");
                    existingLine = false;
                    if (dErrorList.size() > 0) {
                        errorObject = dErrorList.get(0);
                        existingLine = true;
                    } else if (warningList.size() > 0) {
                        errorObject = warningList.get(0);
                        existingLine = true;
                    } else {
                        errorObject = new DbimportLineError();
                        errorObject.setDbimportLine(lineObject);
                        errorObject.setDbimportLineErrorPK(new DbimportLineErrorPK(processId, processIdLine, 31));
                    }
                    if (!"".equals(newValue.toString())) {
                        lineObject.setHazardDescription(newValue.toString());
                        List<similarityObject> similarityList = dbindexedWordFacade.findPotentialDuplicates(newValue.toString(), "hazard");
                        errorObject.setProcessErrorLocation("hazard");
                        if (similarityList.size() > 0) { // The hazard has similar entries
                            errorObject.setProcessErrorCode(new DbimportErrorCode(3));
                            errorObject.setProcessErrorStatus("P");
                            if (existingLine) {
                                dbimportLineErrorFacade.edit(errorObject);
                            } else {
                                dbimportLineErrorFacade.create(errorObject);
                            }
                        } else { // The hazard is entirely new
                            if (existingLine) {
                                errorObject.setProcessErrorStatus("F");
                                dbimportLineErrorFacade.edit(errorObject);
                            }
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "A hazard description is required."));
                        errorObject.setProcessErrorCode(new DbimportErrorCode(1));
                        errorObject.setProcessErrorLocation("hazardDescription");
                        errorObject.setProcessErrorStatus("P");
                        dbimportLineErrorFacade.edit(errorObject);
                    }
                    break;
                case "HMCol":
                    lineObject.setHazardComment(newValue.toString());
                    break;
                case "HWCol":
                    errorList = dbimportLineErrorFacade.findErrorByCell(processId, processIdLine, "hazardWorkshop");
                    existingLine = false;
                    if (errorList.size() > 0) {
                        errorObject = errorList.get(0);
                        existingLine = true;
                    } else {
                        errorObject = new DbimportLineError();
                        errorObject.setDbimportLine(lineObject);
                        errorObject.setDbimportLineErrorPK(new DbimportLineErrorPK(processId, processIdLine, 32));
                    }
                    errorObject.setProcessErrorLocation("hazardWorkshop");
                    if (!"".equals(newValue.toString())) {
                        lineObject.setHazardWorkshop(newValue.toString());
                        errorObject.setProcessErrorStatus("F");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "A hazard workshop is required."));
                        errorObject.setProcessErrorCode(new DbimportErrorCode(1));
                        errorObject.setProcessErrorStatus("P");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        } else {
                            dbimportLineErrorFacade.create(errorObject);
                        }
                    }
                    break;
                case "HCCol":
                    errorList = dbimportLineErrorFacade.findErrorByCell(processId, processIdLine, "hazardContext");
                    existingLine = false;
                    if (errorList.size() > 0) {
                        errorObject = errorList.get(0);
                        existingLine = true;
                    } else {
                        errorObject = new DbimportLineError();
                        errorObject.setDbimportLine(lineObject);
                        errorObject.setDbimportLineErrorPK(new DbimportLineErrorPK(processId, processIdLine, 33));
                    }
                    errorObject.setProcessErrorLocation("hazardContext");
                    if (!"".equals(newValue.toString())) {
                        lineObject.setHazardContextId(Integer.valueOf(newValue.toString()));
                        lineObject.setHazardContext(dbhazardContextFacade.findByName("hazardContextId", newValue.toString()).get(0).getHazardContextName());
                        errorObject.setProcessErrorStatus("F");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "A hazard context is required."));
                        errorObject.setProcessErrorCode(new DbimportErrorCode(1));
                        errorObject.setProcessErrorStatus("P");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        } else {
                            dbimportLineErrorFacade.create(errorObject);
                        }
                    }
                    break;
                case "HLCol":
                    errorList = dbimportLineErrorFacade.findErrorByCell(processId, processIdLine, "hazardLocation");
                    existingLine = false;
                    if (errorList.size() > 0) {
                        errorObject = errorList.get(0);
                        existingLine = true;
                    } else {
                        errorObject = new DbimportLineError();
                        errorObject.setDbimportLine(lineObject);
                        errorObject.setDbimportLineErrorPK(new DbimportLineErrorPK(processId, processIdLine, 34));
                    }
                    errorObject.setProcessErrorLocation("hazardLocation");
                    if (!"".equals(newValue.toString())) {
                        lineObject.setHazardLocationId(Integer.valueOf(newValue.toString()));
                        lineObject.setHazardLocation(dbLocationFacade.findByName("locationId", newValue.toString()).get(0).getLocationName());
                        errorObject.setProcessErrorStatus("F");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "A hazard location is required."));
                        errorObject.setProcessErrorCode(new DbimportErrorCode(1));
                        errorObject.setProcessErrorStatus("P");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        } else {
                            dbimportLineErrorFacade.create(errorObject);
                        }
                    }
                    break;
                case "HACol":
                    errorList = dbimportLineErrorFacade.findErrorByCell(processId, processIdLine, "hazardActivity");
                    existingLine = false;
                    if (errorList.size() > 0) {
                        errorObject = errorList.get(0);
                        existingLine = true;
                    } else {
                        errorObject = new DbimportLineError();
                        errorObject.setDbimportLine(lineObject);
                        errorObject.setDbimportLineErrorPK(new DbimportLineErrorPK(processId, processIdLine, 35));
                    }
                    errorObject.setProcessErrorLocation("hazardActivity");
                    if (!"".equals(newValue.toString())) {
                        lineObject.setHazardActivityId(Integer.valueOf(newValue.toString()));
                        lineObject.setHazardActivity(dbhazardActivityFacade.findByName("activityId", newValue.toString()).get(0).getActivityName());
                        errorObject.setProcessErrorStatus("F");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "A hazard activity is required."));
                        errorObject.setProcessErrorCode(new DbimportErrorCode(1));
                        errorObject.setProcessErrorStatus("P");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        } else {
                            dbimportLineErrorFacade.create(errorObject);
                        }
                    }
                    break;
                case "HTCol":
                    errorList = dbimportLineErrorFacade.findErrorByCell(processId, processIdLine, "hazardType");
                    existingLine = false;
                    if (errorList.size() > 0) {
                        errorObject = errorList.get(0);
                        existingLine = true;
                    } else {
                        errorObject = new DbimportLineError();
                        errorObject.setDbimportLine(lineObject);
                        errorObject.setDbimportLineErrorPK(new DbimportLineErrorPK(processId, processIdLine, 36));
                    }
                    errorObject.setProcessErrorLocation("hazardType");
                    if (!"".equals(newValue.toString())) {
                        lineObject.setHazardTypeId(Integer.valueOf(newValue.toString()));
                        lineObject.setHazardType(dbhazardTypeFacade.findByName("hazardTypeId", newValue.toString()).get(0).getHazardTypeName());
                        errorObject.setProcessErrorStatus("F");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "A hazard type is required."));
                        errorObject.setProcessErrorCode(new DbimportErrorCode(1));
                        errorObject.setProcessErrorStatus("P");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        } else {
                            dbimportLineErrorFacade.create(errorObject);
                        }
                    }
                    break;
                case "HSCol":
                    errorList = dbimportLineErrorFacade.findErrorByCell(processId, processIdLine, "hazardStatus");
                    existingLine = false;
                    if (errorList.size() > 0) {
                        errorObject = errorList.get(0);
                        existingLine = true;
                    } else {
                        errorObject = new DbimportLineError();
                        errorObject.setDbimportLine(lineObject);
                        errorObject.setDbimportLineErrorPK(new DbimportLineErrorPK(processId, processIdLine, 37));
                    }
                    errorObject.setProcessErrorLocation("hazardStatus");
                    if (!"".equals(newValue.toString())) {
                        lineObject.setHazardStatusId(Integer.valueOf(newValue.toString()));
                        lineObject.setHazardStatus(dbhazardStatusFacade.findByName("hazardStatusId", newValue.toString()).get(0).getHazardStatusName());
                        errorObject.setProcessErrorStatus("F");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "A hazard status is required."));
                        errorObject.setProcessErrorCode(new DbimportErrorCode(1));
                        errorObject.setProcessErrorStatus("P");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        } else {
                            dbimportLineErrorFacade.create(errorObject);
                        }
                    }
                    break;
                case "HOCol":
                    errorList = dbimportLineErrorFacade.findErrorByCell(processId, processIdLine, "hazardOwner");
                    existingLine = false;
                    if (errorList.size() > 0) {
                        errorObject = errorList.get(0);
                        existingLine = true;
                    } else {
                        errorObject = new DbimportLineError();
                        errorObject.setDbimportLine(lineObject);
                        errorObject.setDbimportLineErrorPK(new DbimportLineErrorPK(processId, processIdLine, 38));
                    }
                    errorObject.setProcessErrorLocation("hazardOwner");
                    if (!"".equals(newValue.toString())) {
                        lineObject.setHazardOwnerId(Integer.valueOf(newValue.toString()));
                        lineObject.setHazardOwner(dbOwnersFacade.findByName("ownerId", newValue.toString()).get(0).getOwnerName());
                        errorObject.setProcessErrorStatus("F");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "A hazard owner is required."));
                        errorObject.setProcessErrorCode(new DbimportErrorCode(1));
                        errorObject.setProcessErrorStatus("P");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        } else {
                            dbimportLineErrorFacade.create(errorObject);
                        }
                    }
                    break;
                case "HFCol":
                    lineObject.setHazardHFReview(newValue.toString());
                case "RCCol":
                    errorList = dbimportLineErrorFacade.findErrorByCell(processId, processIdLine, "hazardRiskClass");
                    existingLine = false;
                    if (errorList.size() > 0) {
                        errorObject = errorList.get(0);
                        existingLine = true;
                    } else {
                        errorObject = new DbimportLineError();
                        errorObject.setDbimportLine(lineObject);
                        errorObject.setDbimportLineErrorPK(new DbimportLineErrorPK(processId, processIdLine, 39));
                    }
                    errorObject.setProcessErrorLocation("hazardRiskClass");
                    if (!"".equals(newValue.toString())) {
                        lineObject.setHazardRiskClassId(Integer.valueOf(newValue.toString()));
                        lineObject.setHazardRiskClass(dbriskClassFacade.findByName("riskClassId", newValue.toString()).get(0).getRiskClassName());
                        errorObject.setProcessErrorStatus("F");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "A risk class is required."));
                        errorObject.setProcessErrorCode(new DbimportErrorCode(1));
                        errorObject.setProcessErrorStatus("P");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        } else {
                            dbimportLineErrorFacade.create(errorObject);
                        }
                    }
                    break;
                case "CFCol":
                    errorList = dbimportLineErrorFacade.findErrorByCell(processId, processIdLine, "hazardCurrentFreq");
                    existingLine = false;
                    if (errorList.size() > 0) {
                        errorObject = errorList.get(0);
                        existingLine = true;
                    } else {
                        errorObject = new DbimportLineError();
                        errorObject.setDbimportLine(lineObject);
                        errorObject.setDbimportLineErrorPK(new DbimportLineErrorPK(processId, processIdLine, 40));
                    }
                    errorObject.setProcessErrorLocation("hazardCurrentFreq");
                    if (!"".equals(newValue.toString())) {
                        lineObject.setHazardCurrentFrequencyId(Integer.valueOf(newValue.toString()));
                        errorObject.setProcessErrorStatus("F");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "The current risk frequency is required."));
                        errorObject.setProcessErrorCode(new DbimportErrorCode(1));
                        errorObject.setProcessErrorStatus("P");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        } else {
                            dbimportLineErrorFacade.create(errorObject);
                        }
                    }
                    break;
                case "CSCol":
                    errorList = dbimportLineErrorFacade.findErrorByCell(processId, processIdLine, "hazardCurrentSev");
                    existingLine = false;
                    if (errorList.size() > 0) {
                        errorObject = errorList.get(0);
                        existingLine = true;
                    } else {
                        errorObject = new DbimportLineError();
                        errorObject.setDbimportLine(lineObject);
                        errorObject.setDbimportLineErrorPK(new DbimportLineErrorPK(processId, processIdLine, 41));
                    }
                    errorObject.setProcessErrorLocation("hazardCurrentSev");
                    if (!"".equals(newValue.toString())) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "The current risk severity is required."));
                        lineObject.setHazardCurrentSeverityId(Integer.valueOf(newValue.toString()));
                        errorObject.setProcessErrorStatus("F");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        }
                    } else {
                        errorObject.setProcessErrorCode(new DbimportErrorCode(1));
                        errorObject.setProcessErrorStatus("P");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        } else {
                            dbimportLineErrorFacade.create(errorObject);
                        }
                    }
                    break;
                case "TFCol":
                    errorList = dbimportLineErrorFacade.findErrorByCell(processId, processIdLine, "hazardTargetFreq");
                    existingLine = false;
                    if (errorList.size() > 0) {
                        errorObject = errorList.get(0);
                        existingLine = true;
                    } else {
                        errorObject = new DbimportLineError();
                        errorObject.setDbimportLine(lineObject);
                        errorObject.setDbimportLineErrorPK(new DbimportLineErrorPK(processId, processIdLine, 42));
                    }
                    errorObject.setProcessErrorLocation("hazardTargetFreq");
                    if (!"".equals(newValue.toString())) {
                        lineObject.setHazardTargetFrequencyId(Integer.valueOf(newValue.toString()));
                        errorObject.setProcessErrorStatus("F");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "The target risk frequency is required."));
                        errorObject.setProcessErrorCode(new DbimportErrorCode(1));
                        errorObject.setProcessErrorStatus("P");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        } else {
                            dbimportLineErrorFacade.create(errorObject);
                        }
                    }
                    break;
                case "TSCol":
                    errorList = dbimportLineErrorFacade.findErrorByCell(processId, processIdLine, "hazardTargetSev");
                    existingLine = false;
                    if (errorList.size() > 0) {
                        errorObject = errorList.get(0);
                        existingLine = true;
                    } else {
                        errorObject = new DbimportLineError();
                        errorObject.setDbimportLine(lineObject);
                        errorObject.setDbimportLineErrorPK(new DbimportLineErrorPK(processId, processIdLine, 43));
                    }
                    errorObject.setProcessErrorLocation("hazardTargetFreq");
                    if (!"".equals(newValue.toString())) {
                        lineObject.setHazardTargetSeverityId(Integer.valueOf(newValue.toString()));
                        errorObject.setProcessErrorStatus("F");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "The target risk severity is required."));
                        errorObject.setProcessErrorCode(new DbimportErrorCode(1));
                        errorObject.setProcessErrorStatus("P");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        } else {
                            dbimportLineErrorFacade.create(errorObject);
                        }
                    }
                    break;
                case "RTCol":
                    lineObject.setRelationType(newValue.toString());
                    break;
                case "RDCol":
                    String newString = ((List<String>) newValue).get(0);
                    existingLine = false;
                    List<DbimportLineError> relationErrorList = dbimportLineErrorFacade.findErrorByCell(processId, processIdLine, "relationDescription");
                    List<DbimportLineError> causeErrorList = dbimportLineErrorFacade.findErrorByCell(processId, processIdLine, "Cause");
                    List<DbimportLineError> consequenceErrorList = dbimportLineErrorFacade.findErrorByCell(processId, processIdLine, "Consequence");
                    List<DbimportLineError> controlErrorList = dbimportLineErrorFacade.findErrorByCell(processId, processIdLine, "Control");
                    if (relationErrorList.size() > 0) {
                        errorObject = relationErrorList.get(0);
                        existingLine = true;
                    } else if (causeErrorList.size() > 0) {
                        errorObject = causeErrorList.get(0);
                        existingLine = true;
                    } else if (consequenceErrorList.size() > 0) {
                        errorObject = consequenceErrorList.get(0);
                        existingLine = true;
                    } else if (controlErrorList.size() > 0) {
                        errorObject = controlErrorList.get(0);
                        existingLine = true;
                    } else {
                        errorObject = new DbimportLineError();
                        errorObject.setDbimportLine(lineObject);
                        errorObject.setDbimportLineErrorPK(new DbimportLineErrorPK(processId, processIdLine, 44));
                    }

                    if (!"".equals(newString)) {
                        lineObject.setRelationDescription(newString);
                        List<similarityObject> similarityList;
                        switch (lineObject.getRelationType()) {
                            case "Cause":
                                List<DbCause> causeList = dbCauseFacade.findByName("causeDescription", newString);
                                similarityList = dbindexedWordFacade.findPotentialDuplicates(newString, "cause");
                                errorObject.setProcessErrorLocation("Cause");
                                if (causeList.size() > 0) { // The cause already exists
                                    lineObject.setRelationId(causeList.get(0).getCauseId());
                                    if (existingLine) {
                                        errorObject.setProcessErrorStatus("F");
                                        dbimportLineErrorFacade.edit(errorObject);
                                    }
                                } else { // The cause does not already exist in the DB
                                    lineObject.setRelationId(processIdLine);
                                    if (similarityList.size() > 0) { // The cause has similar entries
                                        errorObject.setProcessErrorCode(new DbimportErrorCode(3));
                                        errorObject.setProcessErrorStatus("P");
                                        if (existingLine) {
                                            dbimportLineErrorFacade.edit(errorObject);
                                        } else {
                                            dbimportLineErrorFacade.create(errorObject);
                                        }
                                    } else { // The cause is entirely new
                                        if (existingLine) {
                                            errorObject.setProcessErrorStatus("F");
                                            dbimportLineErrorFacade.edit(errorObject);
                                        }
                                    }
                                }
                                break;
                            case "Consequence":
                                List<DbConsequence> consequenceList = dbConsequenceFacade.findByName("consequenceDescription", newString);
                                similarityList = dbindexedWordFacade.findPotentialDuplicates(newString, "consequence");
                                errorObject.setProcessErrorLocation("Consequence");
                                if (consequenceList.size() > 0) {
                                    lineObject.setRelationId(consequenceList.get(0).getConsequenceId());
                                    if (existingLine) {
                                        errorObject.setProcessErrorStatus("F");
                                        dbimportLineErrorFacade.edit(errorObject);
                                    }
                                } else {
                                    lineObject.setRelationId(processIdLine);
                                    if (similarityList.size() > 0) {
                                        errorObject.setProcessErrorCode(new DbimportErrorCode(3));
                                        errorObject.setProcessErrorStatus("P");
                                        if (existingLine) {
                                            dbimportLineErrorFacade.edit(errorObject);
                                        } else {
                                            dbimportLineErrorFacade.create(errorObject);
                                        }
                                    } else { // The cause is entirely new
                                        if (existingLine) {
                                            errorObject.setProcessErrorStatus("F");
                                            dbimportLineErrorFacade.edit(errorObject);
                                        }
                                    }
                                }
                                break;
                            case "Control":
                                List<DbControl> controlList = dbControlFacade.findByName("controlDescription", newString);
                                similarityList = dbindexedWordFacade.findPotentialDuplicates(newString, "control");
                                errorObject.setProcessErrorLocation("Control");
                                if (controlList.size() > 0) {
                                    lineObject.setRelationId(controlList.get(0).getControlId());
                                    if (existingLine) {
                                        errorObject.setProcessErrorStatus("F");
                                        dbimportLineErrorFacade.edit(errorObject);
                                    }
                                } else {
                                    lineObject.setRelationId(processIdLine);
                                    if (similarityList.size() > 0) {
                                        errorObject.setProcessErrorCode(new DbimportErrorCode(3));
                                        errorObject.setProcessErrorStatus("P");
                                        if (existingLine) {
                                            dbimportLineErrorFacade.edit(errorObject);
                                        } else {
                                            dbimportLineErrorFacade.create(errorObject);
                                        }
                                    } else { // The cause is entirely new
                                        if (existingLine) {
                                            errorObject.setProcessErrorStatus("F");
                                            dbimportLineErrorFacade.edit(errorObject);
                                        }
                                    }
                                }
                                break;
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "The relation description is required."));
                        errorObject.setProcessErrorCode(new DbimportErrorCode(1));
                        errorObject.setProcessErrorLocation("relationDescription");
                        errorObject.setProcessErrorStatus("P");
                        dbimportLineErrorFacade.edit(errorObject);
                    }
                    break;
                case "COCol":
                    errorList = dbimportLineErrorFacade.findErrorByCell(processId, processIdLine, "controlOwner");
                    existingLine = false;
                    if (errorList.size() > 0) {
                        errorObject = errorList.get(0);
                        existingLine = true;
                    } else {
                        errorObject = new DbimportLineError();
                        errorObject.setDbimportLine(lineObject);
                        errorObject.setDbimportLineErrorPK(new DbimportLineErrorPK(processId, processIdLine, 45));
                    }
                    errorObject.setProcessErrorLocation("controlOwner");
                    if (!"".equals(newValue.toString())) {
                        lineObject.setControlOwnerId(Integer.valueOf(newValue.toString()));
                        lineObject.setControlOwner(dbOwnersFacade.findByName("ownerId", newValue.toString()).get(0).getOwnerName());
                        errorObject.setProcessErrorStatus("F");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "The control owner is required."));
                        errorObject.setProcessErrorCode(new DbimportErrorCode(1));
                        errorObject.setProcessErrorStatus("P");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        } else {
                            dbimportLineErrorFacade.create(errorObject);
                        }
                    }
                    break;
                case "CHCol":
                    errorList = dbimportLineErrorFacade.findErrorByCell(processId, processIdLine, "controlHierarchy");
                    existingLine = false;
                    if (errorList.size() > 0) {
                        errorObject = errorList.get(0);
                        existingLine = true;
                    } else {
                        errorObject = new DbimportLineError();
                        errorObject.setDbimportLine(lineObject);
                        errorObject.setDbimportLineErrorPK(new DbimportLineErrorPK(processId, processIdLine, 46));
                    }
                    errorObject.setProcessErrorLocation("controlHierarchy");
                    if (!"".equals(newValue.toString())) {
                        lineObject.setControlHierarchyId(Integer.valueOf(newValue.toString()));
                        lineObject.setControlHierarchy(dbcontrolHierarchyFacade.findByName("controlHierarchyId", newValue.toString()).get(0).getControlHierarchyName());
                        errorObject.setProcessErrorStatus("F");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "The control hierarchy is required."));
                        errorObject.setProcessErrorCode(new DbimportErrorCode(1));
                        errorObject.setProcessErrorStatus("P");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        } else {
                            dbimportLineErrorFacade.create(errorObject);
                        }
                    }
                    break;
                case "CTCol":
                    errorList = dbimportLineErrorFacade.findErrorByCell(processId, processIdLine, "controlType");
                    existingLine = false;
                    if (errorList.size() > 0) {
                        errorObject = errorList.get(0);
                        existingLine = true;
                    } else {
                        errorObject = new DbimportLineError();
                        errorObject.setDbimportLine(lineObject);
                        errorObject.setDbimportLineErrorPK(new DbimportLineErrorPK(processId, processIdLine, 47));
                    }
                    errorObject.setProcessErrorLocation("controlType");
                    if (!"".equals(newValue.toString())) {
                        lineObject.setControlType(newValue.toString());
                        errorObject.setProcessErrorStatus("F");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "The control type is required."));
                        errorObject.setProcessErrorCode(new DbimportErrorCode(1));
                        errorObject.setProcessErrorStatus("P");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        } else {
                            dbimportLineErrorFacade.create(errorObject);
                        }
                    }
                    break;
                case "CRCol":
                    errorList = dbimportLineErrorFacade.findErrorByCell(processId, processIdLine, "controlRecommend");
                    existingLine = false;
                    if (errorList.size() > 0) {
                        errorObject = errorList.get(0);
                        existingLine = true;
                    } else {
                        errorObject = new DbimportLineError();
                        errorObject.setDbimportLine(lineObject);
                        errorObject.setDbimportLineErrorPK(new DbimportLineErrorPK(processId, processIdLine, 48));
                    }
                    errorObject.setProcessErrorLocation("controlRecommend");
                    if (!"".equals(newValue.toString())) {
                        lineObject.setControlRecommendId(Integer.valueOf(newValue.toString()));
                        lineObject.setControlRecommend(dbcontrolRecommendFacade.findByName("controlRecommendId", newValue.toString()).get(0).getControlRecommendName());
                        errorObject.setProcessErrorStatus("F");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "The control recommendation is required."));
                        errorObject.setProcessErrorCode(new DbimportErrorCode(1));
                        errorObject.setProcessErrorStatus("P");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        } else {
                            dbimportLineErrorFacade.create(errorObject);
                        }
                    }
                    newValue = "";
                case "CJCol":
                    errorList = dbimportLineErrorFacade.findErrorByCell(processId, processIdLine, "controlJustify");
                    existingLine = false;
                    if (errorList.size() > 0) {
                        errorObject = errorList.get(0);
                        existingLine = true;
                    } else {
                        errorObject = new DbimportLineError();
                        errorObject.setDbimportLine(lineObject);
                        errorObject.setDbimportLineErrorPK(new DbimportLineErrorPK(processId, processIdLine, 49));
                    }
                    errorObject.setProcessErrorLocation("controlJustify");
                    if (!"".equals(newValue.toString())) {
                        lineObject.setControlJustify(newValue.toString());
                        errorObject.setProcessErrorStatus("F");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        }
                    } else {
                        if (dbcontrolRecommendFacade.findByName("controlRecommendId", lineObject.getControlRecommendId().toString()).get(0).getControlJustifyRequired().equals("Y")) {
                            // Only add a new error if the field is blank AND if the recommendation type requires a justification
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "The control justification is required."));
                            errorObject.setProcessErrorCode(new DbimportErrorCode(1));
                            errorObject.setProcessErrorStatus("P");
                            if (existingLine) {
                                dbimportLineErrorFacade.edit(errorObject);
                            } else {
                                dbimportLineErrorFacade.create(errorObject);
                            }
                        } else {
                            lineObject.setControlJustify("");
                        }
                    }
                    break;
                case "CUCol":
                    errorList = dbimportLineErrorFacade.findErrorByCell(processId, processIdLine, "controlStatus");
                    existingLine = false;
                    if (errorList.size() > 0) {
                        errorObject = errorList.get(0);
                        existingLine = true;
                    } else {
                        errorObject = new DbimportLineError();
                        errorObject.setDbimportLine(lineObject);
                        errorObject.setDbimportLineErrorPK(new DbimportLineErrorPK(processId, processIdLine, 50));
                    }
                    errorObject.setProcessErrorLocation("controlStatus");
                    if (!"".equals(newValue.toString())) {
                        lineObject.setControlExistingOrProposed(newValue.toString());
                        errorObject.setProcessErrorStatus("F");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "The control status is required."));
                        errorObject.setProcessErrorCode(new DbimportErrorCode(1));
                        errorObject.setProcessErrorStatus("P");
                        if (existingLine) {
                            dbimportLineErrorFacade.edit(errorObject);
                        } else {
                            dbimportLineErrorFacade.create(errorObject);
                        }
                    }
                    break;
                default:
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Old: " + oldValue + ", New: " + newValue));
                    break;

            }
            dbimportLineFacade.edit(lineObject);

        }
        listLoadedLines = dbimportLineFacade.findNextLinesByUser(activeUser.getUserId()).stream().map(i -> new importObject(i, dbimportLineErrorFacade.listErrorsByLine(i.getDbimportLinePK().getProcessId(), i.getDbimportLinePK().getProcessIdLine()))).collect(Collectors.toList());
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

        // Add words to indexed table
        wordProcessing_MB.indexDescription(hazardObject.getHazardId(), hazardObject.getHazardDescription(), "hazard");

        // Add SBS nodes
        DbHazardSbsPK hazardSbsPKObject = new DbHazardSbsPK();
        DbHazardSbs hazardSbsObject = new DbHazardSbs();
        DbHazard hazardFKObject = new DbHazard();

        hazardSbsPKObject.setHazardId(hazardObject.getHazardId());
        hazardFKObject.setHazardId(hazardObject.getHazardId());
        hazardSbsObject.setDbHazard(hazardFKObject);

        List<TreeNode> treeNodesList = populateTree(lineObject);

        List<treeNodeObject> treeNodeObjectsList = new ArrayList<>();
        DbtreeLevel1 tmpTreeNode = dbtreeLevel1Facade.findByName(root.getChildren().get(0).toString());
        Integer rootId = tmpTreeNode.getTreeLevel1Index();

        for (TreeNode node : treeNodesList) {
            if (node.getParent().toString().equals("Root")) {
                //Add the tree Node and include the root index in each child node
                rootId = tmpTreeNode.getTreeLevel1Index();
                treeNodeObject tmpNode = new treeNodeObject();
                tmpNode.setNodeId(rootId.toString() + ".");
                tmpNode.setNodeName(node.getData().toString());
                tmpNode.setRowKey(node.getRowKey());
                treeNodeObjectsList.add(tmpNode);
            } else {
                String parts[] = node.getData().toString().split(" ", 2);
                treeNodeObject tmpNode = new treeNodeObject();
                tmpNode.setNodeId(rootId.toString() + "." + parts[0]);
                tmpNode.setNodeName(parts[1]);
                tmpNode.setRowKey(node.getRowKey());
                treeNodeObjectsList.add(tmpNode);
            }
        }

        for (int i = 0; i < treeNodeObjectsList.size(); i++) {
            hazardSbsPKObject.setSbsId(treeNodeObjectsList.get(i).getNodeId());
            hazardSbsObject.setDbHazardSbsPK(hazardSbsPKObject);
            dbHazardSbsFacade.create(hazardSbsObject);
        }

        return hazardObject;
    }

    public void createRelation(DbimportLine lineObject, DbHazard hazardObject) {
        switch (lineObject.getRelationType()) {
            case "Cause":
                DbCause tmpCause;
                if (Optional.ofNullable(lineObject.getRelationId()).orElse(0) > 0) { // The cause is found in the database by ID
                    tmpCause = dbCauseFacade.find(lineObject.getRelationId());
                } else {
                    if (dbCauseFacade.findByName("causeDescription", lineObject.getRelationDescription()).size() > 0) { // The cause is found in the database by description
                        tmpCause = dbCauseFacade.findByName("causeDescription", lineObject.getRelationDescription()).get(0);
                    } else { // The cause doesn't exist and needs to be created
                        tmpCause = new DbCause();
                        tmpCause.setCauseDescription(lineObject.getRelationDescription());
                        dbCauseFacade.create(tmpCause);
                    }
                }
                DbHazardCause tmpHazardCause = new DbHazardCause();
                DbHazardCausePK tmpHazardCausePK = new DbHazardCausePK(hazardObject.getHazardId(), tmpCause.getCauseId());
                tmpHazardCause.setDbHazardCausePK(tmpHazardCausePK);
                tmpHazardCause.setDbCause(tmpCause);
                tmpHazardCause.setDbHazard(hazardObject);
                tmpHazardCause.setDbHazardCauseDummyvar(null);
                dbHazardCauseFacade.create(tmpHazardCause);

                wordProcessing_MB.indexDescription(tmpCause.getCauseId().toString(), tmpCause.getCauseDescription(), "cause");
                break;
            case "Consequence":
                DbConsequence tmpConsequence;
                if (Optional.ofNullable(lineObject.getRelationId()).orElse(0) > 0) {
                    tmpConsequence = dbConsequenceFacade.find(lineObject.getRelationId());
                } else {
                    if (dbConsequenceFacade.findByName("consequenceDescription", lineObject.getRelationDescription()).size() > 0) {
                        tmpConsequence = dbConsequenceFacade.findByName("consequenceDescription", lineObject.getRelationDescription()).get(0);
                    } else {
                        tmpConsequence = new DbConsequence();
                        tmpConsequence.setConsequenceDescription(lineObject.getRelationDescription());
                        dbConsequenceFacade.create(tmpConsequence);
                    }
                }
                DbHazardConsequence tmpHazardConsequence = new DbHazardConsequence();
                DbHazardConsequencePK tmpHazardConsequencePK = new DbHazardConsequencePK(hazardObject.getHazardId(), tmpConsequence.getConsequenceId());
                tmpHazardConsequence.setDbHazardConsequencePK(tmpHazardConsequencePK);
                tmpHazardConsequence.setDbConsequence(tmpConsequence);
                tmpHazardConsequence.setDbHazard(hazardObject);
                tmpHazardConsequence.setDbHazardConsequenceDummyvar(null);
                dbHazardConsequenceFacade.create(tmpHazardConsequence);

                wordProcessing_MB.indexDescription(tmpConsequence.getConsequenceId().toString(), tmpConsequence.getConsequenceDescription(), "consequence");
                break;
            case "Control":
                DbControl tmpControl;
                if (Optional.ofNullable(lineObject.getRelationId()).orElse(0) > 0) {
                    tmpControl = dbControlFacade.find(lineObject.getRelationId());
                } else {
                    if (dbControlFacade.findByName("controlDescription", lineObject.getRelationDescription()).size() > 0) {
                        tmpControl = dbControlFacade.findByName("controlDescription", lineObject.getRelationDescription()).get(0);
                    } else {
                        tmpControl = new DbControl();
                        tmpControl.setControlDescription(lineObject.getRelationDescription());
                        tmpControl.setOwnerId(dbOwnersFacade.find(lineObject.getControlOwnerId()));
                        tmpControl.setControlHierarchyId(dbcontrolHierarchyFacade.find(lineObject.getControlHierarchyId()));
                        dbControlFacade.create(tmpControl);
                    }
                }
                DbControlHazard controlHazardObject = new DbControlHazard();
                DbcontrolRecommend tmpCtlRecommend = dbcontrolRecommendFacade.find(lineObject.getControlRecommendId());
                controlHazardObject.setDbHazard(hazardObject);
                controlHazardObject.setDbControl(tmpControl);
                controlHazardObject.setControlType(lineObject.getControlType().substring(0, 1));
                controlHazardObject.setControlExistingOrProposed(lineObject.getControlExistingOrProposed().substring(0, 1));
                controlHazardObject.setDbControlHazardPK(new DbControlHazardPK(hazardObject.getHazardId(), tmpControl.getControlId()));
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

                wordProcessing_MB.indexDescription(tmpControl.getControlId().toString(), tmpControl.getControlDescription(), "control");
                break;
        }
    }

    public void submitTable() {
        DbHazard currentHazard = new DbHazard();
        for (int i = 0; i < listCheckedLines.size(); i ++) {
            DbimportLine lineObject = listCheckedLines.get(i).getLineObject();
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
                    triggerWorkflow(currentHazard);
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
        init();
    }

    public boolean sameLineObject(DbimportLine lineObject, DbHazard hazardObject) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd");
        if (ft.format(hazardObject.getHazardDate()).equals(ft.format(lineObject.getHazardDate()))
                && hazardObject.getHazardDescription().equals(lineObject.getHazardDescription())
                && hazardObject.getHazardComment().equals(lineObject.getHazardComment())
                && hazardObject.getHazardActivity().getActivityId().equals(lineObject.getHazardActivityId())
                && hazardObject.getHazardContextId().getHazardContextId().equals(lineObject.getHazardContextId())
                && hazardObject.getHazardTypeId().getHazardTypeId().equals(lineObject.getHazardTypeId())
                && hazardObject.getHazardStatusId().getHazardStatusId().equals(lineObject.getHazardStatusId())
                && hazardObject.getOwnerId().getOwnerId().equals(lineObject.getHazardOwnerId())
                && hazardObject.getHazardWorkshop().equals(lineObject.getHazardWorkshop())
                && hazardObject.getRiskClassId().getRiskClassId().equals(lineObject.getHazardRiskClassId())
                && hazardObject.getRiskCurrentFrequencyId().getRiskFrequencyId().equals(lineObject.getHazardCurrentFrequencyId())
                && hazardObject.getRiskCurrentSeverityId().getRiskSeverityId().equals(lineObject.getHazardCurrentSeverityId())
                && hazardObject.getRiskTargetFrequencyId().getRiskFrequencyId().equals(lineObject.getHazardTargetFrequencyId())
                && hazardObject.getRiskTargetSeverityId().getRiskSeverityId().equals(lineObject.getHazardTargetSeverityId())
                && hazardObject.getLegacyId().equals(lineObject.getHazardLegacyId())) {
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

    public List<TreeNode> populateTree(DbimportLine lineObject) {
        selectedLine = lineObject;
        List<String> sbsCodes;
        if (lineObject.getHazardSbs() == null) {
            sbsCodes = new ArrayList<>();
        } else {
            sbsCodes = Arrays.asList(lineObject.getHazardSbs().replace(" ", "").split(","));
        }

        // To account for the lack of dots, add them if the code terminates in a number before a comma
        // This is necessary because the tree tables all have full stops (e.g. 1., 2.1.) but users might
        //  not enter full stops at the end. So the below stream goes through all the codes and adds them
        //  if they're missing to allow for a good comparison
        sbsCodes = sbsCodes.stream().map(i -> {
            if (i.endsWith(".")) {
                return i;
            } else {
                return i += ".";
            }
        }).collect(Collectors.toList());

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
        return listTreeNode;
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
        List<DbimportLineError> errorList = dbimportLineErrorFacade.findErrorByCell(selectedLine.getDbimportLinePK().getProcessId(), selectedLine.getDbimportLinePK().getProcessIdLine(), "hazardsbs");
        if (currentTree.length > 1) {
            String sbsCodes = Arrays.asList(currentTree).stream().map(i -> "1." + i.getData().toString().replaceAll("[^\\d+\\.]", "")).reduce("", (a, b) -> a + "," + b);
            selectedLine.setHazardSbs(sbsCodes.substring(1));
            dbimportLineFacade.edit(selectedLine);
            if (errorList.size() > 0) {
                DbimportLineError errorObject = errorList.get(0);
                errorObject.setProcessErrorStatus("F");
                dbimportLineErrorFacade.edit(errorObject);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "The SBS tree must not be empty!"));
        }
        listLoadedLines = dbimportLineFacade.findNextLinesByUser(activeUser.getUserId()).stream().map(i -> new importObject(i, dbimportLineErrorFacade.listErrorsByLine(i.getDbimportLinePK().getProcessId(), i.getDbimportLinePK().getProcessIdLine()))).collect(Collectors.toList());
        RequestContext.getCurrentInstance().update("hazardsForm:hazardsTable");
    }

    public void cancelImport() {
        DbimportHeader headerObject = dbimportHeaderFacade.find(listLoadedLines.get(0).getLineObject().getDbimportHeader().getProcessId());
        headerObject.setProcessStatus("C");
        dbimportHeaderFacade.edit(headerObject);
        init();
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
            case "Gold":
                headerCellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GOLD.getIndex());
                break;
            case "LightOrange":
                headerCellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_ORANGE.getIndex());
                break;
            case "Tan":
                headerCellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.TAN.getIndex());
                break;
            case "LightTurq":
                headerCellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_TURQUOISE.getIndex());
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

    class sbsNodes {

        public String levelTwo;
        public int columnSize;
        public List<String> levelThreeList;

        public sbsNodes() {
            levelTwo = "";
            columnSize = 0;
            levelThreeList = new ArrayList<>();
        }

        public sbsNodes(String levelTwo, int columnSize, List<String> levelThreeList) {
            this.levelTwo = levelTwo;
            this.columnSize = columnSize;
            this.levelThreeList = levelThreeList;
        }
    }
    
    public void generateLayout() {
        //The produced excel will be on xslx format.
        String filename = "SSD_Import.xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Format");
        XSSFSheet hidden = workbook.createSheet("hidden");
        XSSFSheet sbs = workbook.createSheet("Sbs_Codes");
        sheet.setZoom(90);

        // ---------------------------> Creating the spreadsheet headers <--------------------------------------
        // Creating headers from the row number 0
        Row headerRow = sheet.createRow(0);
        headerRow.setHeight((short) 600);
        IndexedColorMap colorMap = workbook.getStylesSource().getIndexedColors();

        // Creating the Initial headers
        Cell cell = headerRow.createCell(0);
        cell.setCellValue("Hazard Columns");
        cell.setCellStyle(styleHeaderGenerator("Blue", workbook.createCellStyle(), getHeaderFont(workbook.createFont())));
        sheet.addMergedRegion(CellRangeAddress.valueOf("A1:R2"));

        cell = headerRow.createCell(18);
        cell.setCellValue("Hazard Relations");
        cell.setCellStyle(styleHeaderGenerator("Red", workbook.createCellStyle(), getHeaderFont(workbook.createFont())));
        sheet.addMergedRegion(CellRangeAddress.valueOf("S1:Z1"));

        headerRow = sheet.createRow(1);
        headerRow.setHeight((short) 600);

        cell = headerRow.createCell(18);
        cell.setCellValue("Causes - Consequences - Controls");
        cell.setCellStyle(styleHeaderGenerator("LightOrange", workbook.createCellStyle(), getHeaderFont(workbook.createFont())));
        sheet.addMergedRegion(CellRangeAddress.valueOf("S2:T2"));

        cell = headerRow.createCell(20);
        cell.setCellValue("Fields just for controls");
        cell.setCellStyle(styleHeaderGenerator("Gold", workbook.createCellStyle(), getHeaderFont(workbook.createFont())));
        sheet.addMergedRegion(CellRangeAddress.valueOf("U2:Z2"));

        // Setting borders for nall merged regions
        int numMerged = sheet.getNumMergedRegions();
        for (int i = 0; i < numMerged; i++) {
            CellRangeAddress mergedRegions = sheet.getMergedRegion(i);
            RegionUtil.setBorderLeft(BorderStyle.THIN, mergedRegions, sheet);
            RegionUtil.setBorderRight(BorderStyle.THIN, mergedRegions, sheet);
            RegionUtil.setBorderTop(BorderStyle.THIN, mergedRegions, sheet);
            RegionUtil.setBorderBottom(BorderStyle.THIN, mergedRegions, sheet);
        }

        // Creating the title for each column
        headerRow = sheet.createRow(2);

        String[][] columnsHeaders = {{"Context", "PaleBlue", "12"},
        {"Description", "PaleBlue", "24"},
        {"Location", "PaleBlue", "12"},
        {"Activity", "PaleBlue", "15"},
        {"Owner", "PaleBlue", "25"},
        {"Type", "PaleBlue", "16"},
        {"Status", "PaleBlue", "12"},
        {"Class", "PaleBlue", "12"},
        {"Cur. Freq", "PaleBlue", "12"},
        {"Cur. Sev.", "PaleBlue", "11"},
        {"Tar. Freq", "PaleBlue", "11"},
        {"Tar. Sev", "PaleBlue", "11"},
        {"Comment", "PaleBlue", "24"},
        {"Date", "PaleBlue", "12"},
        {"Workshop", "PaleBlue", "19"},
        {"Legacy Id", "PaleBlue", "12"},
        {"HF Review", "PaleBlue", "12"},
        {"Sbs Codes", "PaleBlue", "20"},
        {"Type", "Tan", "11"},
        {"Description", "Tan", "35"},
        {"Owner", "Yellow", "25"},
        {"Hierarchy", "Yellow", "13"},
        {"Type", "Yellow", "9"},
        {"Recommendation", "Yellow", "24"},
        {"Justification", "Yellow", "20"},
        {"Status", "Yellow", "9"}};

        // Setting up the column sizes
        for (int i = 0; i < columnsHeaders.length; i++) {
            int width = ((int) (Integer.parseInt(columnsHeaders[i][2]) * 1.14388 * 256));
            sheet.setColumnWidth(i, width);
        }

        // Setting up the column headers style and content
        for (int i = 0; i < columnsHeaders.length; i++) {
            Cell cellTmp = headerRow.createCell(i);
            cellTmp.setCellValue(columnsHeaders[i][0]);
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFont(getHeaderFont(workbook.createFont()));
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            switch (columnsHeaders[i][1]) {
                case "Yellow":
                    cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());
                    break;
                case "Tan":
                    cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.TAN.getIndex());
                    break;
                case "PaleBlue":
                    cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.PALE_BLUE.getIndex());
                    break;
                default:
                    cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
                    break;
            }
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellTmp.setCellStyle(cellStyle);
        }

        // ---------------------------> Creating the spreadsheet content <--------------------------------------
        // Creating list of context in the hidden sheet
        int numberOfRows = dbsystemParametersFacade.find(1).getExcelLayoutRows();
        List<Integer> numberOfItems = new ArrayList<>();

        // Getting all the required lists to populated drop down lists
        List<DbhazardContext> listOfContexts = dbhazardContextFacade.findAll();
        numberOfItems.add(listOfContexts.size());
        List<DbLocation> listOfLocations = dbLocationFacade.findAll();
        numberOfItems.add(listOfLocations.size());
        List<DbhazardActivity> listOfActivities = dbhazardActivityFacade.findAll();
        numberOfItems.add(listOfActivities.size());
        List<DbOwners> listOfOwners = dbOwnersFacade.findAll();
        numberOfItems.add(listOfOwners.size());
        List<DbhazardType> listOfTypes = dbhazardTypeFacade.findAll();
        numberOfItems.add(listOfTypes.size());
        List<DbhazardStatus> listOfStatuses = dbhazardStatusFacade.findAll();
        numberOfItems.add(listOfStatuses.size());
        List<DbriskClass> listOfRiskClasses = dbriskClassFacade.findAll();
        numberOfItems.add(listOfRiskClasses.size());
        List<DbriskFrequency> listOfFrequencies = dbriskFrequencyFacade.findAll();
        numberOfItems.add(listOfFrequencies.size());
        List<DbriskSeverity> listOfSeverities = dbriskSeverityFacade.findAll();
        numberOfItems.add(listOfSeverities.size());
        List<String> listOfHFReview = new ArrayList<>();
        listOfHFReview.add("Yes");
        listOfHFReview.add("No");
        List<String> listOfRelationTypes = new ArrayList<>();
        listOfRelationTypes.add("Cause");
        listOfRelationTypes.add("Consequence");
        listOfRelationTypes.add("Control");
        numberOfItems.add(listOfRelationTypes.size());
        List<DbcontrolHierarchy> listOfHierarchies = dbcontrolHierarchyFacade.findAll();
        numberOfItems.add(listOfHierarchies.size());
        List<String> listOfControlTypes = new ArrayList<>();
        listOfControlTypes.add("Mitigative");
        listOfControlTypes.add("Preventive");
        numberOfItems.add(listOfControlTypes.size());
        List<DbcontrolRecommend> listOfRecommendations = dbcontrolRecommendFacade.findAll();
        numberOfItems.add(listOfRecommendations.size());
        List<String> listOfControlStatuses = new ArrayList<>();
        listOfControlStatuses.add("Existing");
        listOfControlStatuses.add("Proposed");
        numberOfItems.add(listOfControlStatuses.size());

        Integer max = numberOfItems
                .stream()
                .mapToInt(v -> v)
                .max().orElseThrow(NoSuchElementException::new);

        // Creating the dropdown lists values in the hidden sheet
        for (int i = 0; i < max; i++) {
            XSSFRow row = hidden.createRow(i);
            if (i < listOfContexts.size()) {
                XSSFCell cell1 = row.createCell(0);
                cell1.setCellValue(listOfContexts.get(i).getHazardContextName());
            }

            if (i < listOfLocations.size()) {
                XSSFCell cell1 = row.createCell(1);
                cell1.setCellValue(listOfLocations.get(i).getLocationName());
            }

            if (i < listOfActivities.size()) {
                XSSFCell cell1 = row.createCell(2);
                cell1.setCellValue(listOfActivities.get(i).getActivityName());
            }

            if (i < listOfOwners.size()) {
                XSSFCell cell1 = row.createCell(3);
                cell1.setCellValue(listOfOwners.get(i).getOwnerName());
            }

            if (i < listOfTypes.size()) {
                XSSFCell cell1 = row.createCell(4);
                cell1.setCellValue(listOfTypes.get(i).getHazardTypeName());
            }

            if (i < listOfStatuses.size()) {
                XSSFCell cell1 = row.createCell(5);
                cell1.setCellValue(listOfStatuses.get(i).getHazardStatusName());
            }

            if (i < listOfRiskClasses.size()) {
                XSSFCell cell1 = row.createCell(6);
                cell1.setCellValue(listOfRiskClasses.get(i).getRiskClassName());
            }

            if (i < listOfFrequencies.size()) {
                XSSFCell cell1 = row.createCell(7);
                cell1.setCellValue(listOfFrequencies.get(i).getFrequencyScore());
            }

            if (i < listOfSeverities.size()) {
                XSSFCell cell1 = row.createCell(8);
                cell1.setCellValue(listOfSeverities.get(i).getSeverityScore());
            }

            if (i < listOfHFReview.size()) {
                XSSFCell cell1 = row.createCell(9);
                cell1.setCellValue(listOfHFReview.get(i));
            }

            if (i < listOfRelationTypes.size()) {
                XSSFCell cell1 = row.createCell(10);
                cell1.setCellValue(listOfRelationTypes.get(i));
            }

            if (i < listOfHierarchies.size()) {
                XSSFCell cell1 = row.createCell(11);
                cell1.setCellValue(listOfHierarchies.get(i).getControlHierarchyName());
            }

            if (i < listOfControlTypes.size()) {
                XSSFCell cell1 = row.createCell(12);
                cell1.setCellValue(listOfControlTypes.get(i));
            }

            if (i < listOfRecommendations.size()) {
                XSSFCell cell1 = row.createCell(13);
                cell1.setCellValue(listOfRecommendations.get(i).getControlRecommendName());
            }

            if (i < listOfControlStatuses.size()) {
                XSSFCell cell1 = row.createCell(14);
                cell1.setCellValue(listOfControlStatuses.get(i));
            }

        }

        // Setting up dropdown lists
        sheet.addValidationData(generateDropDownList(sheet, new CellRangeAddressList(3, numberOfRows + 2, 0, 0), "hidden!$A$1:$A$" + listOfContexts.size()));
        sheet.addValidationData(generateDropDownList(sheet, new CellRangeAddressList(3, numberOfRows + 2, 2, 2), "hidden!$B$1:$B$" + listOfLocations.size()));
        sheet.addValidationData(generateDropDownList(sheet, new CellRangeAddressList(3, numberOfRows + 2, 3, 3), "hidden!$C$1:$C$" + listOfActivities.size()));
        sheet.addValidationData(generateDropDownList(sheet, new CellRangeAddressList(3, numberOfRows + 2, 4, 4), "hidden!$D$1:$D$" + listOfOwners.size()));
        sheet.addValidationData(generateDropDownList(sheet, new CellRangeAddressList(3, numberOfRows + 2, 5, 5), "hidden!$E$1:$E$" + listOfTypes.size()));
        sheet.addValidationData(generateDropDownList(sheet, new CellRangeAddressList(3, numberOfRows + 2, 6, 6), "hidden!$F$1:$F$" + listOfStatuses.size()));
        sheet.addValidationData(generateDropDownList(sheet, new CellRangeAddressList(3, numberOfRows + 2, 7, 7), "hidden!$G$1:$G$" + listOfRiskClasses.size()));
        sheet.addValidationData(generateDropDownList(sheet, new CellRangeAddressList(3, numberOfRows + 2, 8, 8), "hidden!$H$1:$H$" + listOfFrequencies.size()));
        sheet.addValidationData(generateDropDownList(sheet, new CellRangeAddressList(3, numberOfRows + 2, 9, 9), "hidden!$I$1:$I$" + listOfSeverities.size()));
        sheet.addValidationData(generateDropDownList(sheet, new CellRangeAddressList(3, numberOfRows + 2, 10, 10), "hidden!$H$1:$H$" + listOfFrequencies.size()));
        sheet.addValidationData(generateDropDownList(sheet, new CellRangeAddressList(3, numberOfRows + 2, 11, 11), "hidden!$I$1:$I$" + listOfSeverities.size()));
        sheet.addValidationData(generateDropDownList(sheet, new CellRangeAddressList(3, numberOfRows + 2, 11, 11), "hidden!$I$1:$I$" + listOfSeverities.size()));
        sheet.addValidationData(generateDropDownList(sheet, new CellRangeAddressList(3, numberOfRows + 2, 16, 16), "hidden!$J$1:$J$" + listOfHFReview.size()));
        sheet.addValidationData(generateDropDownList(sheet, new CellRangeAddressList(3, numberOfRows + 2, 18, 18), "hidden!$K$1:$K$" + listOfRelationTypes.size()));
        sheet.addValidationData(generateDropDownList(sheet, new CellRangeAddressList(3, numberOfRows + 2, 20, 20), "hidden!$D$1:$D$" + listOfOwners.size()));
        sheet.addValidationData(generateDropDownList(sheet, new CellRangeAddressList(3, numberOfRows + 2, 21, 21), "hidden!$L$1:$L$" + listOfHierarchies.size()));
        sheet.addValidationData(generateDropDownList(sheet, new CellRangeAddressList(3, numberOfRows + 2, 22, 22), "hidden!$M$1:$M$" + listOfControlTypes.size()));
        sheet.addValidationData(generateDropDownList(sheet, new CellRangeAddressList(3, numberOfRows + 2, 23, 23), "hidden!$N$1:$N$" + listOfRecommendations.size()));
        sheet.addValidationData(generateDropDownList(sheet, new CellRangeAddressList(3, numberOfRows + 2, 25, 25), "hidden!$O$1:$O$" + listOfControlStatuses.size()));

        // Creating the comment for the Sbs Codes
        addComment(workbook, sheet, 2, 17, "LXRA", "The sbs codes should be provided according to the node id and be separed by commas in case of multiple entries.\n"
                + "e.g. 1.2, 1.1.3, 2");

        // Setting up date validation
        sheet.addValidationData(generateDateValidation(sheet, new CellRangeAddressList(3, numberOfRows + 2, 13, 13)));

        // Defining body cell borders for template
        CellStyle cellBodyStyle = workbook.createCellStyle();
        cellBodyStyle.setBorderBottom(BorderStyle.THIN);
        cellBodyStyle.setBorderLeft(BorderStyle.THIN);
        cellBodyStyle.setBorderRight(BorderStyle.THIN);
        cellBodyStyle.setBorderTop(BorderStyle.THIN);
        cellBodyStyle.setLocked(false);

        CellRangeAddress region = CellRangeAddress.valueOf("A4:Z" + (numberOfRows + 4));
        for (int i = region.getFirstRow(); i < region.getLastRow(); i++) {
            XSSFRow row = sheet.createRow(i);
            for (int j = region.getFirstColumn(); j < region.getLastColumn() + 1; j++) {
                Cell cellTmp = row.createCell(j);
                cellTmp.setCellStyle(cellBodyStyle);
            }
        }

        // ---------------------------> Creating the sbs sheet content <--------------------------------------
        // Creating headers from the row number 0
        sbs.setZoom(90);
        Row headerSbsRow = sbs.createRow(0);

        // Creating the Initial headers
        Cell cellSbs = headerSbsRow.createCell(0);
        cellSbs.setCellValue("1. Rail");
        cellSbs.setCellStyle(styleHeaderGenerator("Teal", workbook.createCellStyle(), getHeaderFont(workbook.createFont())));
        sbs.addMergedRegion(CellRangeAddress.valueOf("A1:H1"));

        cellSbs = headerSbsRow.createCell(8);
        cellSbs.setCellValue("2. Non-Rail");
        cellSbs.setCellStyle(styleHeaderGenerator("Green", workbook.createCellStyle(), getHeaderFont(workbook.createFont())));
        sbs.addMergedRegion(CellRangeAddress.valueOf("I1:N1"));

        //Creating the levels 2 and 3 data structure
        List<importHazard_MB.sbsNodes> sbsList = new ArrayList<>();
        sbsList.add(new importHazard_MB.sbsNodes("1.1 Civil Infrastructure", 20, Arrays.asList(new String[]{"1.1.1 Corridor - civil elements", "1.1.2 Embankments", "1.1.3 Retaining walls / Ramps",
            "1.1.4 Bridges", "1.1.5 Culverts", "1.1.6 Utilities", "1.1.7 Drainage", "1.1.8 Access roads", "1.1.9 Walkways", "1.1.10 Fencing"})));
        sbsList.add(new importHazard_MB.sbsNodes("1.2 Track", 18, Arrays.asList(new String[]{"1.2.1 Formation", "1.2.2 Trackbed (ballast or slab)", "1.2.3 Sleepers", "1.2.4 Rail",
            "1.2.5 Fixings", "1.2.6 Monuments & Signage"})));
        sbsList.add(new importHazard_MB.sbsNodes("1.3 Station", 18, Arrays.asList(new String[]{"1.3.1 Station buildings (architecture & urban design), access stairs & ramps",
            "1.3.2 Platforms, platform extensions", "1.3.3 Carparks, drainage, landscaping, etc", "1.3.4 Vertical Transport", "1.3.5 Services"})));
        sbsList.add(new importHazard_MB.sbsNodes("1.4 ICT/OCS", 16, Arrays.asList(new String[]{"1.4.1 OCS", "1.4.2 ICT System (ROMS, ORS, etc.)", "1.4.3 Intranet"})));
        sbsList.add(new importHazard_MB.sbsNodes("1.5 Communications", 18, Arrays.asList(new String[]{"1.5.1 Radio Frequency", "1.5.2 Backbone cabling ", "1.5.3 VicTrack Managed Services",
            "1.5.4 Communication Equipment Room (CER)"})));
        sbsList.add(new importHazard_MB.sbsNodes("1.6 Traction Power", 18, Arrays.asList(new String[]{"1.6.1 Traction Substation", "1.6.2 OHLE & cabling", "1.6.3 Electrolysis"})));
        sbsList.add(new importHazard_MB.sbsNodes("1.7 Signalling", 18, Arrays.asList(new String[]{"1.7.1 Centralised Train Control System (incl SCC (Signal Control Centre))",
            "1.7.2 Local Train Control System", "1.7.3 Interlocking", "1.7.4 Wayside (conventional)", "1.7.5 CBTC Wayside"})));
        sbsList.add(new importHazard_MB.sbsNodes("1.8 CSR - Civil", 18, Arrays.asList(new String[]{"1.8.1 CSR Above ground", "1.8.2 CSR Underground"})));
        sbsList.add(new importHazard_MB.sbsNodes("2.1 Buses", 14, Arrays.asList(new String[]{"2.1.1 Bus PIDS", "2.1.2 Signage"})));
        sbsList.add(new importHazard_MB.sbsNodes("2.2 Trams", 18, Arrays.asList(new String[]{"2.2.1 Tram civil works", "2.2.2 Tram PIDS", "2.2.3 Tram Signage"})));
        sbsList.add(new importHazard_MB.sbsNodes("2.3 Roads", 18, Arrays.asList(new String[]{"2.3.1 Road civil works",
            "2.3.2 Other (furniture, bus interchange, bus shelter, bike lane, fencing signage, etc)"})));
        sbsList.add(new importHazard_MB.sbsNodes("2.4 Pedestrians/Bicycles", 22, Arrays.asList(new String[]{"2.4.1 Pedestrian Structures", "2.4.2 Shared user path (SUP)", "2.4.3 Cycle path"})));
        sbsList.add(new importHazard_MB.sbsNodes("2.5 IDOs", 18, Arrays.asList(new String[]{"2.5.1 Enabling Works for IDO"})));
        sbsList.add(new importHazard_MB.sbsNodes("2.6 Others", 18, Arrays.asList(new String[]{"2.6.1 Linear Park", "2.6.2 Landscaping"})));

        // Setting up the columns width
        for (int i = 0; i < sbsList.size(); i++) {
            int width = ((int) (sbsList.get(i).columnSize * 1.14388 * 256));
            sbs.setColumnWidth(i, width);
        }

        //Creating the first level
        Row BodySbsRow = sbs.createRow(1);
        for (int i = 0; i < sbsList.size(); i++) {
            Cell lvl1SbsCell = BodySbsRow.createCell(i);
            lvl1SbsCell.setCellValue(sbsList.get(i).levelTwo);
            CellStyle cellStyle = workbook.createCellStyle();
            if (sbsList.get(i).levelTwo.startsWith("1")) {
                cellStyle.setFont(getBodySbsFont(workbook.createFont(), ""));
                cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
            } else if (sbsList.get(i).levelTwo.startsWith("2")) {
                cellStyle.setFont(getBodySbsFont(workbook.createFont(), "White"));
                cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.SEA_GREEN.getIndex());
            }
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setWrapText(true);
            lvl1SbsCell.setCellStyle(cellStyle);
        }

        // Getting the maximum rows number
        Integer maxSbs = sbsList
                .stream()
                .map(i -> i.columnSize)
                .collect(Collectors.toList())
                .stream()
                .mapToInt(v -> v)
                .max().orElseThrow(NoSuchElementException::new);

        //Creating the second level
        for (int row = 0; row < max; row++) {
            BodySbsRow = sbs.createRow(row + 2);
            for (int col = 0; col < sbsList.size(); col++) {
                if (row < sbsList.get(col).levelThreeList.size()) {
                    Cell lvl2SbsCell = BodySbsRow.createCell(col);
                    lvl2SbsCell.setCellValue(sbsList.get(col).levelThreeList.get(row));
                    CellStyle cellStyle = workbook.createCellStyle();
                    if (sbsList.get(col).levelThreeList.get(row).startsWith("1")) {
                        cellStyle.setFont(getBodySbsFont(workbook.createFont(), ""));
                        cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_TURQUOISE.getIndex());
                    } else if (sbsList.get(col).levelThreeList.get(row).startsWith("2")) {
                        cellStyle.setFont(getBodySbsFont(workbook.createFont(), ""));
                        cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_GREEN.getIndex());
                    }
                    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
                    cellStyle.setWrapText(true);
                    lvl2SbsCell.setCellStyle(cellStyle);
                }
            }
        }
        
        // Additional sheet configurations
        sheet.lockDeleteColumns(true);
        sheet.lockDeleteRows(true);
        sheet.lockFormatCells(true);
        sheet.lockFormatColumns(true);
        sheet.lockFormatRows(true);
        sheet.lockInsertColumns(true);
        sheet.lockInsertRows(true);
        sheet.protectSheet(dbsystemParametersFacade.find(1).getExcelLayoutPassword());
        sheet.enableLocking();
        //workbook.lockStructure();
        workbook.setSheetHidden(1, true);

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

    private Font getHeaderFont(Font headerFont) {
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 11);
        headerFont.setFontName("Arial");
        return headerFont;
    }

    private Font getBodySbsFont(Font bodyFont, String color) {
        bodyFont.setFontHeightInPoints((short) 11);
        bodyFont.setFontName("Arial");
        if (color.equals("White")) {
            bodyFont.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        }
        return bodyFont;
    }

    private DataValidation generateDropDownList(XSSFSheet sheet, CellRangeAddressList cellRange, String hiddenReference) {
        DataValidation dataValidation = null;
        DataValidationConstraint constraint = null;
        DataValidationHelper validationHelper = null;

        validationHelper = new XSSFDataValidationHelper(sheet);
        CellRangeAddressList hazardContext = cellRange;
        constraint = validationHelper.createFormulaListConstraint(hiddenReference);
        dataValidation = validationHelper.createValidation(constraint, hazardContext);
        dataValidation.setSuppressDropDownArrow(true);
        dataValidation.setShowErrorBox(true);

        return dataValidation;
    }

    private DataValidation generateDateValidation(XSSFSheet sheet, CellRangeAddressList cellRange) {
        DataValidation dataValidation = null;
        DataValidationConstraint constraint = null;
        DataValidationHelper validationHelper = null;

        validationHelper = new XSSFDataValidationHelper(sheet);
        CellRangeAddressList hazardContext = cellRange;
        constraint = validationHelper.createDateConstraint(7, "=TODAY()", null, "dd/MM/yyyy");
        dataValidation = validationHelper.createValidation(constraint, hazardContext);
        dataValidation.setSuppressDropDownArrow(true);
        dataValidation.setShowErrorBox(true);
        dataValidation.createErrorBox("Date Validation Error", "The hazard date should have the format dd/mm/yyyy i.e. 01/01/2019 and not be after the current date.");

        return dataValidation;
    }

    public void addComment(XSSFWorkbook workbook, XSSFSheet sheet, int rowIdx, int colIdx, String author, String commentText) {
        CreationHelper factory = workbook.getCreationHelper();
        //get an existing cell or create it otherwise:
        Cell cell = getOrCreateCell(sheet, rowIdx, colIdx);

        ClientAnchor anchor = factory.createClientAnchor();
        //i found it useful to show the comment box at the bottom right corner
        anchor.setCol1(cell.getColumnIndex() + 1); //the box of the comment starts at this given column...
        anchor.setCol2(cell.getColumnIndex() + 3); //...and ends at that given column
        anchor.setRow1(rowIdx + 1); //one row below the cell...
        anchor.setRow2(rowIdx + 5); //...and 4 rows high

        Drawing drawing = sheet.createDrawingPatriarch();
        Comment comment = drawing.createCellComment(anchor);
        //set the comment text and author
        comment.setString(factory.createRichTextString(commentText));
        comment.setAuthor(author);

        cell.setCellComment(comment);
    }

    public Cell getOrCreateCell(XSSFSheet sheet, int rowIdx, int colIdx) {
        Row row = sheet.getRow(rowIdx);
        if (row == null) {
            row = sheet.createRow(rowIdx);
        }

        Cell cell = row.getCell(colIdx);
        if (cell == null) {
            cell = row.createCell(colIdx);
        }

        return cell;
    }

    // This method processes the uploaded file.
    public void processFile(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            InputStream input = uploadedFile.getInputstream();
            XSSFWorkbook workbook = new XSSFWorkbook(input);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();

            // Defining key variables
            activeUser = (DbUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("activeUser");
            DbimportHeader importHeader = new DbimportHeader(dbglobalIdFacade.nextConsecutive("MAS", "IMP", "-", 4).getAnswerString(), new Date(), activeUser.getUserId(),
                    uploadedFile.getFileName(), 0, "P");
            List<DbimportLine> listOfImportedLines = new ArrayList<>();
            List<List<DbimportLineError>> listOfErrors = new ArrayList<>();
            int lineNo = 1;

            // Iterating over Excel file in Java
            while (itr.hasNext()) {
                Row row = itr.next();
                if (row.getRowNum() > 2) {
                    importLineObj processedLine = checkImportLine(row, importHeader.getProcessId(), lineNo);
                    if (processedLine != null) {
                        listOfImportedLines.add(processedLine.lineData);
                        lineNo++;
                        if (processedLine.lineError.size() > 0) {
                            listOfErrors.add(processedLine.lineError);
                        }
                    }
                } else if (row.getRowNum() == 0) {
                    if (!row.getCell(0).getStringCellValue().equals("Hazard Columns")) { // Lazy check of headers to make sure the file is the same as requested
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "The selected file is not in the correct format. Please download the template above and try again."));
                        return;
                    }
                }
            }
            importHeader.setTotalLines(listOfImportedLines.size());

            // Saving entites in the database
            if (listOfImportedLines.size() < 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "No hazards were found in the file. Please populate the template and reupload."));
            } else {
                dbimportHeaderFacade.create(importHeader);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Hazards have been read from the file. Please fix any identified errors and submit to send them to approvals."));
                if (listOfImportedLines.size() > 0) {
                    listOfImportedLines.forEach((line) -> {
                        dbimportLineFacade.create(line);
                    });
                }
                if (listOfErrors.size() > 0) {
                    listOfErrors.forEach((sublist) -> {
                        sublist.forEach((errorLine) -> {
                            dbimportLineErrorFacade.create(errorLine);
                        });
                    });
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(trees_MB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OLE2NotOfficeXmlFileException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "The selected file has been saved using an unsupported spreadsheet program. Please save this as a valid Microsoft Excel file and try again."));
            return;
        }
        init();
    }

    // Checking the file has some content and proccessing lines
    private importLineObj checkImportLine(Row row, String processId, int lineNo) {
        importLineObj tmpObj = null;
        boolean rowContent = false;
//        try {
        for (int i = 0; i < 26; i++) {
            if (!"".equals(row.getCell(i).toString())) {
                rowContent = true;
                break;
            }
        }

        if (rowContent) {
            for (int i = 0; i < 26; i++) {
                // tmpObj = processLine(tmpObj, i, row.getCell(i).toString(), processId, lineNo);
                if (tmpObj == null) {
                    tmpObj = new importLineObj();
                    tmpObj.lineData.setDbimportLinePK(new DbimportLinePK(processId, lineNo));
                }
                switch (i) {
                    // Processing hazard context
                    case 0:
                        if (!"".equals(row.getCell(i).toString())) {
                            DbhazardContext tmpVar = dbhazardContextFacade.findByName("hazardContextName", row.getCell(i).toString()).get(0);
                            if (tmpVar.getHazardContextId() != null) {
                                tmpObj.lineData.setHazardContextId(tmpVar.getHazardContextId());
                                tmpObj.lineData.setHazardContext(tmpVar.getHazardContextName());
                            } else {
                                createLineError(tmpObj, "hazardContext", 2);
                            }
                        } else {
                            createLineError(tmpObj, "hazardContext", 1);
                        }
                        break;
                    // Processing hazard description    
                    case 1:
                        if (!"".equals(row.getCell(i).toString())) {
                            tmpObj.lineData.setHazardDescription(row.getCell(i).toString());
                            if (dbindexedWordFacade.findPotentialDuplicates(row.getCell(i).toString(), "Hazard").size() > 0) {
                                createLineError(tmpObj, "hazard", 3);
                            }
                        } else {
                            createLineError(tmpObj, "hazardDescription", 1);
                        }
                        break;
                    // Processing hazard location
                    case 2:
                        if (!"".equals(row.getCell(i).toString())) {
                            DbLocation tmpVar = dbLocationFacade.findByName("locationName", row.getCell(i).toString()).get(0);
                            if (tmpVar.getLocationName() != null) {
                                tmpObj.lineData.setHazardLocationId(tmpVar.getLocationId());
                                tmpObj.lineData.setHazardLocation(tmpVar.getLocationName());
                            } else {
                                createLineError(tmpObj, "hazardLocation", 2);
                            }
                        } else {
                            createLineError(tmpObj, "hazardLocation", 1);
                        }
                        break;
                    // Processing hazard activity
                    case 3:
                        if (!"".equals(row.getCell(i).toString())) {
                            DbhazardActivity tmpVar = dbhazardActivityFacade.findByName("activityName", row.getCell(i).toString()).get(0);
                            if (tmpVar.getActivityName() != null) {
                                tmpObj.lineData.setHazardActivityId(tmpVar.getActivityId());
                                tmpObj.lineData.setHazardActivity(tmpVar.getActivityName());
                            } else {
                                createLineError(tmpObj, "hazardActivity", 2);
                            }
                        } else {
                            createLineError(tmpObj, "hazardActivity", 1);
                        }
                        break;
                    // Processing hazard owner
                    case 4:
                        if (!"".equals(row.getCell(i).toString())) {
                            DbOwners tmpVar = dbOwnersFacade.findByName("ownerName", row.getCell(i).toString()).get(0);
                            if (tmpVar.getOwnerName() != null) {
                                tmpObj.lineData.setHazardOwnerId(tmpVar.getOwnerId());
                                tmpObj.lineData.setHazardOwner(tmpVar.getOwnerName());
                            } else {
                                createLineError(tmpObj, "hazardOwner", 2);
                            }
                        } else {
                            createLineError(tmpObj, "hazardOwner", 1);
                        }
                        break;
                    // Processing hazard type
                    case 5:
                        if (!"".equals(row.getCell(i).toString())) {
                            DbhazardType tmpVar = dbhazardTypeFacade.findByName("hazardTypeName", row.getCell(i).toString()).get(0);
                            if (tmpVar.getHazardTypeName() != null) {
                                tmpObj.lineData.setHazardTypeId(tmpVar.getHazardTypeId());
                                tmpObj.lineData.setHazardType(tmpVar.getHazardTypeName());
                            } else {
                                createLineError(tmpObj, "hazardType", 2);
                            }
                        } else {
                            createLineError(tmpObj, "hazardType", 1);
                        }
                        break;
                    // Processing hazard status
                    case 6:
                        if (!"".equals(row.getCell(i).toString())) {
                            DbhazardStatus tmpVar = dbhazardStatusFacade.findByName("hazardStatusName", row.getCell(i).toString()).get(0);
                            if (tmpVar.getHazardStatusName() != null) {
                                tmpObj.lineData.setHazardStatusId(tmpVar.getHazardStatusId());
                                tmpObj.lineData.setHazardStatus(tmpVar.getHazardStatusName());
                            } else {
                                createLineError(tmpObj, "hazardStatus", 2);
                            }
                        } else {
                            createLineError(tmpObj, "hazardStatus", 1);
                        }
                        break;
                    // Processing hazard risk class
                    case 7:
                        if (!"".equals(row.getCell(i).toString())) {
                            DbriskClass tmpVar = dbriskClassFacade.findByName("riskClassName", row.getCell(i).toString()).get(0);
                            if (tmpVar.getRiskClassName() != null) {
                                tmpObj.lineData.setHazardRiskClassId(tmpVar.getRiskClassId());
                                tmpObj.lineData.setHazardRiskClass(tmpVar.getRiskClassName());
                            } else {
                                createLineError(tmpObj, "hazardRiskClass", 2);
                            }
                        } else {
                            createLineError(tmpObj, "hazardRiskClass", 1);
                        }
                        break;
                    // Processing current frequency Id
                    case 8:
                        if (!"".equals(row.getCell(i).toString())) {
                            DbriskFrequency tmpVar = dbriskFrequencyFacade.findByName("frequencyScore", row.getCell(i).toString()).get(0);
                            if (tmpVar.getFrequencyScore() != null) {
                                tmpObj.lineData.setHazardCurrentFrequencyId(tmpVar.getRiskFrequencyId());
                            } else {
                                createLineError(tmpObj, "hazardCurrentFreq", 2);
                            }
                        } else {
                            createLineError(tmpObj, "hazardCurrentFreq", 1);
                        }
                        break;
                    // Processing current severity Id
                    case 9:
                        if (!"".equals(row.getCell(i).toString())) {
                            DbriskSeverity tmpVar = dbriskSeverityFacade.findByName("severityScore", row.getCell(i).toString()).get(0);
                            if (tmpVar.getSeverityScore() != null) {
                                tmpObj.lineData.setHazardCurrentSeverityId(tmpVar.getRiskSeverityId());
                            } else {
                                createLineError(tmpObj, "hazardCurrentSev", 2);
                            }
                        } else {
                            createLineError(tmpObj, "hazardCurrentSev", 1);
                        }
                        break;
                    // Processing target frequency Id
                    case 10:
                        if (!"".equals(row.getCell(i).toString())) {
                            DbriskFrequency tmpVar = dbriskFrequencyFacade.findByName("frequencyScore", row.getCell(i).toString()).get(0);
                            if (tmpVar.getFrequencyScore() != null) {
                                tmpObj.lineData.setHazardTargetFrequencyId(tmpVar.getRiskFrequencyId());
                            } else {
                                createLineError(tmpObj, "hazardTargetFreq", 2);
                            }
                        } else {
                            createLineError(tmpObj, "hazardTargetFreq", 1);
                        }
                        break;
                    // Processing target severity Id
                    case 11:
                        if (!"".equals(row.getCell(i).toString())) {
                            DbriskSeverity tmpVar = dbriskSeverityFacade.findByName("severityScore", row.getCell(i).toString()).get(0);
                            if (tmpVar.getSeverityScore() != null) {
                                tmpObj.lineData.setHazardTargetSeverityId(tmpVar.getRiskSeverityId());
                            } else {
                                createLineError(tmpObj, "hazardTargetSev", 2);
                            }
                        } else {
                            createLineError(tmpObj, "hazardTargetSev", 1);
                        }
                        break;
                    // Processing hazard comment
                    case 12:
                        tmpObj.lineData.setHazardComment(row.getCell(i).toString());
                        break;
                    // Processing hazard date
                    case 13:
                        if (!"".equals(row.getCell(i).toString())) {
                            try {
                                tmpObj.lineData.setHazardDate(new SimpleDateFormat("dd-MMM-yyyy").parse(row.getCell(i).toString()));
                            } catch (ParseException ex) {
                                createLineError(tmpObj, "hazardDate", 4);
                                Logger.getLogger(trees_MB.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            createLineError(tmpObj, "hazardDate", 1);
                        }
                        break;
                    // Processing hazard workshop
                    case 14:
                        if (!"".equals(row.getCell(i).toString())) {
                            tmpObj.lineData.setHazardWorkshop(row.getCell(i).toString());
                        } else {
                            createLineError(tmpObj, "hazardWorkshop", 1);
                        }
                        break;
                    // Processing hazard legacy Id    
                    case 15:
                        tmpObj.lineData.setHazardLegacyId(row.getCell(i).toString());
                        break;
                    // Processing hazard HF Review
                    case 16:
                        if (!"".equals(row.getCell(i).toString())) {
                            tmpObj.lineData.setHazardHFReview(row.getCell(i).toString());
                        }
                        break;
                    // Processing hazard sbs codes 
                    case 17:
                        if (!"".equals(row.getCell(i).toString())) {
                            String[] sbsValues = row.getCell(i).toString().replaceAll("\\s+", "").split(",");
                            List<Integer> sbsInts = new ArrayList<>();
                            if (sbsValues.length == 0) {
                                createLineError(tmpObj, "hazardSbs", 2);
                            } else if (sbsValues.length > 0) {
                                boolean errorFound = false;
                                for (String sbsValue : sbsValues) {
                                    if (sbsValue.matches("^\\d+(\\.\\d+)*") && !errorFound) {
                                        sbsInts = Arrays.stream(sbsValue.split("\\.")).map(s -> Integer.valueOf(s)).collect(Collectors.toList());
                                        while (true) {
                                            if (sbsInts.get(sbsInts.size() - 1).equals(0) && sbsInts.get(sbsInts.size() - 2).equals(0)) {
                                                sbsInts.remove(sbsInts.size() - 1);
                                                continue;
                                            }
                                            if (sbsInts.get(sbsInts.size() - 1).equals(0)) {
                                                sbsInts.remove(sbsInts.size() - 1);
                                            }
                                            break;
                                        }
                                        switch (sbsInts.size()) {
                                            case 1:
                                                DbtreeLevel1 lvl1 = dbtreeLevel1Facade.findByIndex(sbsInts.get(0));
                                                if (lvl1.getTreeLevel1Name() == null) {
                                                    errorFound = true;
                                                }
                                                break;
                                            case 2:
                                                DbtreeLevel2 lvl2 = dbtreeLevel1Facade.findByIndex(sbsInts.get(0), sbsInts.get(1));
                                                if (lvl2.getTreeLevel2Name() == null) {
                                                    errorFound = true;
                                                }
                                                break;
                                            case 3:
                                                DbtreeLevel3 lvl3 = dbtreeLevel1Facade.findByIndex(sbsInts.get(0), sbsInts.get(1), sbsInts.get(2));
                                                if (lvl3.getTreeLevel3Name() == null) {
                                                    errorFound = true;
                                                }
                                                break;
                                            case 4:
                                                DbtreeLevel4 lvl4 = dbtreeLevel1Facade.findByIndex(sbsInts.get(0), sbsInts.get(1), sbsInts.get(2), sbsInts.get(3));
                                                if (lvl4.getTreeLevel4Name() == null) {
                                                    errorFound = true;
                                                }
                                                break;
                                            case 5:
                                                DbtreeLevel5 lvl5 = dbtreeLevel1Facade.findByIndex(sbsInts.get(0), sbsInts.get(1), sbsInts.get(2), sbsInts.get(3), sbsInts.get(4));
                                                if (lvl5.getTreeLevel5Name() == null) {
                                                    errorFound = true;
                                                }
                                                break;
                                            case 6:
                                                DbtreeLevel6 lvl6 = dbtreeLevel1Facade.findByIndex(sbsInts.get(0), sbsInts.get(1), sbsInts.get(2), sbsInts.get(3), sbsInts.get(4), sbsInts.get(5));
                                                if (lvl6.getTreeLevel6Name() == null) {
                                                    errorFound = true;
                                                }
                                                break;
                                            default:
                                                errorFound = true;
                                                break;
                                        }
                                        if (errorFound) {
                                            createLineError(tmpObj, "hazardSbs", 2);
                                            break;
                                        }
                                    } else {
                                        createLineError(tmpObj, "hazardSbs", 2);
                                        errorFound = true;
                                        break;
                                    }
                                }
                                if (!errorFound) {
                                    tmpObj.lineData.setHazardSbs(sbsInts.stream().map(n -> n.toString()).collect(Collectors.joining(".")));
                                }
                            }
                        } else {
                            createLineError(tmpObj, "hazardSbs", 1);
                        }
                        break;
                    // Processing relation type
                    case 18:
                        if (!"".equals(row.getCell(i).toString())) {
                            tmpObj.lineData.setRelationType(row.getCell(i).toString());
                        }
                        break;
                    // Processing relation description
                    case 19:
                        if (!"".equals(row.getCell(i).toString())) {
                            tmpObj.lineData.setRelationDescription(row.getCell(i).toString());
                            if (dbindexedWordFacade.findPotentialDuplicates(row.getCell(i).toString(), row.getCell(18).toString()).size() > 0) {
                                createLineError(tmpObj, row.getCell(18).toString(), 3);
                            }
                        } else if (!"".equals(row.getCell(18).toString())) {
                            createLineError(tmpObj, "relationDescription", 1);
                        }
                        break;
                    // Processing control owner
                    case 20:
                        if (!"".equals(row.getCell(i).toString())) {
                            DbOwners tmpVar = dbOwnersFacade.findByName("ownerName", row.getCell(i).toString()).get(0);
                            if (tmpVar.getOwnerName() != null) {
                                tmpObj.lineData.setControlOwnerId(tmpVar.getOwnerId());
                                tmpObj.lineData.setControlOwner(tmpVar.getOwnerName());
                            } else {
                                createLineError(tmpObj, "controlOwner", 2);
                            }
                        } else if (row.getCell(18).toString().equals("Control")) {
                            createLineError(tmpObj, "controlOwner", 1);
                        }
                        break;
                    // Processing control hierarchy
                    case 21:
                        if (!"".equals(row.getCell(i).toString())) {
                            DbcontrolHierarchy tmpVar = dbcontrolHierarchyFacade.findByName("controlHierarchyName", row.getCell(i).toString()).get(0);
                            if (tmpVar.getControlHierarchyName() != null) {
                                tmpObj.lineData.setControlHierarchyId(tmpVar.getControlHierarchyId());
                                tmpObj.lineData.setControlHierarchy(tmpVar.getControlHierarchyName());
                            } else {
                                createLineError(tmpObj, "controlHierarchy", 2);
                            }
                        } else if (row.getCell(18).toString().equals("Control")) {
                            createLineError(tmpObj, "controlHierarchy", 1);
                        }
                        break;
                    // Processing control type
                    case 22:
                        if (!"".equals(row.getCell(i).toString())) {
                            tmpObj.lineData.setControlType(row.getCell(i).toString());
                        } else if (row.getCell(18).toString().equals("Control")) {
                            createLineError(tmpObj, "controlType", 1);
                        }
                        break;
                    // Processing control recommendation
                    case 23:
                        if (!"".equals(row.getCell(i).toString())) {
                            DbcontrolRecommend tmpVar = dbcontrolRecommendFacade.findByName("controlRecommendName", row.getCell(i).toString()).get(0);
                            if (tmpVar.getControlRecommendName() != null) {
                                tmpObj.lineData.setControlRecommendId(tmpVar.getControlRecommendId());
                                tmpObj.lineData.setControlRecommend(tmpVar.getControlRecommendName());
                            } else {
                                createLineError(tmpObj, "controlRecommend", 2);
                            }
                        } else if (row.getCell(18).toString().equals("Control")) {
                            createLineError(tmpObj, "controlRecommend", 1);
                        }
                        break;
                    // Processing control Status
                    case 25:
                        if (!"".equals(row.getCell(i).toString())) {
                            tmpObj.lineData.setControlExistingOrProposed(row.getCell(i).toString().substring(0, 1));
                        } else if (row.getCell(18).toString().equals("Control")) {
                            createLineError(tmpObj, "controlStatus", 1);
                        }
                        break;
                    // Processing control justify
                    case 24:
                        if (row.getCell(18).toString().equals("Control") && dbcontrolRecommendFacade.findByName("controlRecommendName", row.getCell(23).toString()).get(0).getControlJustifyRequired().equals("Y")) {
                            if (!"".equals(row.getCell(i).toString())) {
                                tmpObj.lineData.setControlJustify(row.getCell(i).toString());
                            } else {
                                createLineError(tmpObj, "controlJustify", 1);
                            }
                        }
                        break;
                    default:
                        System.out.println("The value does not match with the expected columns. Value: " + row.getCell(i).toString() + " column index: " + i);
                        break;
                }
            }
        }

//        } catch (Exception e) {
//            System.err.println(e);
//        }
        return tmpObj;
    }

// Creating the list of errors per line
    private importLineObj createLineError(importLineObj tmpObj, String fieldName, int errorCode) {
        DbimportLineError tmpError = new DbimportLineError(tmpObj.lineData.getDbimportLinePK().getProcessId(),
                tmpObj.lineData.getDbimportLinePK().getProcessIdLine(), tmpObj.lineError.size() + 1);
        tmpError.setProcessErrorLocation(fieldName);
        tmpError.setProcessErrorStatus("P");
        switch (errorCode) {
            case 1:
                tmpError.setProcessErrorCode(new DbimportErrorCode(1));
                break;
            case 2:
                tmpError.setProcessErrorCode(new DbimportErrorCode(2));
                break;
            case 3:
                tmpError.setProcessErrorCode(new DbimportErrorCode(3));
                break;
        }
        tmpObj.lineError.add(tmpError);
        return tmpObj;

    }

    class importLineObj {

        public DbimportLine lineData;
        public List<DbimportLineError> lineError;

        public importLineObj() {
            lineData = new DbimportLine();
            lineError = new ArrayList<>();
        }

        public importLineObj(DbimportLine lineData, List<DbimportLineError> lineError) {
            this.lineData = lineData;
            this.lineError = lineError;
        }
    }
}
