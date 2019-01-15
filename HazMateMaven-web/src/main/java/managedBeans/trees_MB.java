/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import customObjects.searchObject;
import customObjects.similarityObject;
import ejb.DbHazardSbsFacadeLocal;
import ejb.DbtreeLevel1FacadeLocal;
import ejb.DbtreeLevel2FacadeLocal;
import ejb.DbtreeLevel3FacadeLocal;
import ejb.DbtreeLevel4FacadeLocal;
import ejb.DbtreeLevel5FacadeLocal;
import ejb.DbtreeLevel6FacadeLocal;
import entities.*;
import customObjects.treeNodeObject;
import customObjects.validateIdObject;
import ejb.DbHazardFacadeLocal;
import ejb.DbLocationFacadeLocal;
import ejb.DbOwnersFacadeLocal;
import ejb.DbcommonWordFacadeLocal;
import ejb.DbcontrolHierarchyFacadeLocal;
import ejb.DbcontrolRecommendFacadeLocal;
import ejb.DbglobalIdFacadeLocal;
import ejb.DbhazardActivityFacadeLocal;
import ejb.DbhazardContextFacadeLocal;
import ejb.DbhazardStatusFacadeLocal;
import ejb.DbhazardTypeFacadeLocal;
import ejb.DbindexedWordFacadeLocal;
import ejb.DbriskClassFacadeLocal;
import ejb.DbriskFrequencyFacadeLocal;
import ejb.DbriskSeverityFacadeLocal;
import ejb.DbsystemParametersFacadeLocal;
import ejb.DbwfHeaderFacadeLocal;
import ejb.DbwfLineFacadeLocal;
import entities.DbHazard;
import entities.DbLocation;
import entities.DbUser;
import entities.DbhazardActivity;
import entities.DbhazardContext;
import entities.DbwfDecision;
import entities.DbwfLine;
import entities.DbwfLinePK;
import entities.DbwfType;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Juan David
 */
@Named(value = "trees_MB")
@ViewScoped
public class trees_MB implements Serializable {

    @EJB
    private DbglobalIdFacadeLocal dbglobalIdFacade;
    @EJB
    private DbsystemParametersFacadeLocal dbsystemParametersFacade;
    @EJB
    private DbcontrolRecommendFacadeLocal dbcontrolRecommendFacade;
    @EJB
    private DbcontrolHierarchyFacadeLocal dbcontrolHierarchyFacade;
    @EJB
    private DbOwnersFacadeLocal dbOwnersFacade;
    @EJB
    private DbriskSeverityFacadeLocal dbriskSeverityFacade;
    @EJB
    private DbriskFrequencyFacadeLocal dbriskFrequencyFacade;
    @EJB
    private DbriskClassFacadeLocal dbriskClassFacade;
    @EJB
    private DbhazardStatusFacadeLocal dbhazardStatusFacade;
    @EJB
    private DbhazardTypeFacadeLocal dbhazardTypeFacade;
    @EJB
    private DbhazardActivityFacadeLocal dbhazardActivityFacade;
    @EJB
    private DbLocationFacadeLocal dbLocationFacade;
    @EJB
    private DbhazardContextFacadeLocal dbhazardContextFacade;
    @EJB
    private DbindexedWordFacadeLocal dbindexedWordFacade;
    @EJB
    private DbcommonWordFacadeLocal dbcommonWordFacade;
    @EJB
    private DbwfLineFacadeLocal dbwfLineFacade;
    @EJB
    private DbwfHeaderFacadeLocal dbwfHeaderFacade;
    @EJB
    private DbHazardFacadeLocal dbHazardFacade;
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

    private List<DbtreeLevel1> listTreeLevel1;
    private String hazardId;
    private TreeNode root;
    private TreeNode[] selectedNodes;
    private List<treeNodeObject> treeCheckedNodesList;
    private List<treeNodeObject> treeHazardSbsList;
    private List<Object> experimentList;

    private String gotId;

    private String autoConsec;

    private UploadedFile uploadedFile;

    public trees_MB() {
    }

    public List<DbtreeLevel1> getListTreeLevel1() {
        return listTreeLevel1;
    }

    public void setListTreeLevel1(List<DbtreeLevel1> listTreeLevel1) {
        this.listTreeLevel1 = listTreeLevel1;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public String getHazardId() {
        return hazardId;
    }

    public void setHazardId(String hazardId) {
        this.hazardId = hazardId;
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

    //for deleting
    public TreeNode[] getSelectedNodes() {
        return selectedNodes;
    }

    public void setSelectedNodes(TreeNode[] selectedNodes) {
        this.selectedNodes = selectedNodes;
    }

    public String getGotId() {
        return gotId;
    }

    public void setGotId(String gotId) {
        this.gotId = gotId;
    }

    public String getAutoConsec() {
        return autoConsec;
    }

    public void setAutoConsec(String autoConsec) {
        this.autoConsec = autoConsec;
    }

    public List<Object> getExperimentList() {
        return experimentList;
    }

    public void setExperimentList(List<Object> experimentList) {
        this.experimentList = experimentList;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    @PostConstruct
    public void init() {
        listTreeLevel1 = dbtreeLevel1Facade.findAll();
        createTree();
    }

    public void displaySelectedMultiple(TreeNode[] nodes) {
        if (nodes != null && nodes.length > 0) {
            StringBuilder builder = new StringBuilder();

            for (TreeNode node : nodes) {
                builder.append(node.getData().toString());
                builder.append("<br/>");
            }

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", builder.toString());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
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

    public void findTreeByHazard(String hazardId) {
        List<DbHazardSbs> resultantSBS = dbHazardSbsFacade.findByHazardId(hazardId);
        treeHazardSbsList = new ArrayList<>();

        for (DbHazardSbs tmpHazSbs : resultantSBS) {
            String parts[] = tmpHazSbs.getDbHazardSbsPK().getSbsId().split("\\.");
            switch (parts.length) {
                case 1:
                    DbtreeLevel1 tmpDbLvl1 = dbtreeLevel1Facade.findByIndex(Integer.parseInt(parts[0]));
                    if (tmpDbLvl1.getTreeLevel1Name() != null) {
                        treeHazardSbsList.add(new treeNodeObject(tmpHazSbs.getDbHazardSbsPK().getSbsId(),
                                tmpDbLvl1.getTreeLevel1Name()));
                    }
                    break;
                case 2:
                    DbtreeLevel2 tmpDbLvl2 = dbtreeLevel1Facade.findByIndex(Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]));
                    if (tmpDbLvl2.getTreeLevel2Name() != null) {
                        treeHazardSbsList.add(new treeNodeObject(tmpHazSbs.getDbHazardSbsPK().getSbsId(),
                                tmpDbLvl2.getTreeLevel2Name()));
                    }
                    break;
                case 3:
                    DbtreeLevel3 tmpDbLvl3 = dbtreeLevel1Facade.findByIndex(Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                    if (tmpDbLvl3.getTreeLevel3Name() != null) {
                        treeHazardSbsList.add(new treeNodeObject(tmpHazSbs.getDbHazardSbsPK().getSbsId(),
                                tmpDbLvl3.getTreeLevel3Name()));
                    }
                    break;
                case 4:
                    DbtreeLevel4 tmpDbLvl4 = dbtreeLevel1Facade.findByIndex(Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
                    if (tmpDbLvl4.getTreeLevel4Name() != null) {
                        treeHazardSbsList.add(new treeNodeObject(tmpHazSbs.getDbHazardSbsPK().getSbsId(),
                                tmpDbLvl4.getTreeLevel4Name()));
                    }
                    break;
                case 5:
                    DbtreeLevel5 tmpDbLvl5 = dbtreeLevel1Facade.findByIndex(Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]),
                            Integer.parseInt(parts[4]));
                    if (tmpDbLvl5.getTreeLevel5Name() != null) {
                        treeHazardSbsList.add(new treeNodeObject(tmpHazSbs.getDbHazardSbsPK().getSbsId(),
                                tmpDbLvl5.getTreeLevel5Name()));
                    }
                    break;
                case 6:
                    DbtreeLevel6 tmpDbLvl6 = dbtreeLevel1Facade.findByIndex(Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]),
                            Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));
                    if (tmpDbLvl6.getTreeLevel6Name() != null) {
                        treeHazardSbsList.add(new treeNodeObject(tmpHazSbs.getDbHazardSbsPK().getSbsId(),
                                tmpDbLvl6.getTreeLevel6Name()));
                    }
                    break;
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

    public void testSearch() {
//        List<DbHazard> resultantList = dbHazardFacade.findAllHazards();
        searchObject tmpObj = new searchObject();
        List<searchObject> searchList = new ArrayList<>();
//        tmpObj.setFieldName("hazardComment");
//        tmpObj.setUserInput("Road");
//        tmpObj.setFieldType("string");
//        tmpObj.setEntity1Name("DbHazard");
//        tmpObj.setRelationType("like");
//        searchList.add(tmpObj);
//        tmpObj = new searchObject();
//
//        tmpObj.setFieldName("locationId");
//        tmpObj.setUserInput("1");
//        tmpObj.setFieldType("int");
//        tmpObj.setEntity2Name("hazardLocation");
//        tmpObj.setEntity1Name("DbHazard");
//        tmpObj.setRelationType("=");
//        searchList.add(tmpObj);
//        tmpObj = new searchObject();
//
//        tmpObj.setFieldName("controlId");
//        tmpObj.setUserInput("1,2,3");
//        tmpObj.setFieldType("int");
//        tmpObj.setEntity2Name("dbControlHazardPK");
//        tmpObj.setEntity1Name("DbControlHazard");
//        tmpObj.setRelationType("in");
//        searchList.add(tmpObj);
//        tmpObj = new searchObject();
//
//        tmpObj.setFieldName("hazardId");
//        tmpObj.setUserInput("1");
//        tmpObj.setFieldType("string");
//        tmpObj.setEntity1Name("DbHazard");
//        tmpObj.setRelationType("in");
//        searchList.add(tmpObj);
//        tmpObj = new searchObject();
//
//        tmpObj.setFieldName("causeDescription");
//        tmpObj.setUserInput("new");
//        tmpObj.setFieldType("string");
//        tmpObj.setEntity1Name("DbCause");
//        tmpObj.setRelationType("like");
//        searchList.add(tmpObj);
//        tmpObj = new searchObject();

        tmpObj.setFieldName("projectName");
        tmpObj.setUserInput("Test");
        tmpObj.setFieldType("string");
        tmpObj.setEntity3Name("projectId");
        tmpObj.setEntity2Name("hazardLocation");
        tmpObj.setEntity1Name("DbHazard");
        tmpObj.setRelationType("like");
        searchList.add(tmpObj);
        tmpObj = new searchObject();

        List<DbHazard> resultantList = (List<DbHazard>) (Object) dbHazardFacade.findHazards(searchList, new ArrayList<>(), "A", "Normal");
//        List<DbHazard> resultantList = dbHazardFacade.findHazardsByFields(searchList);

//        treeNodeObject tmpTreeObj= new treeNodeObject();
//        List<treeNodeObject> searchListTree = new ArrayList<>();
//        tmpTreeObj.setNodeId("1.1.7.4.12.");
//        tmpTreeObj.setNodeName("Abc");
//        searchListTree.add(tmpTreeObj);
//        tmpTreeObj = new treeNodeObject();
//          
//        tmpTreeObj.setNodeId("1.1.7.4.3.1.");
//        tmpTreeObj.setNodeName("Def");
//        searchListTree.add(tmpTreeObj);
//        tmpTreeObj = new treeNodeObject();
//          
//        List<DbHazard> resultantList = dbHazardFacade.findHazardsByFieldsAndSbs(searchList, searchListTree);
//        searchObject tmpObj = new searchObject();
//        List<searchObject> searchList = new ArrayList<>();
//        tmpObj.setFieldName("hazardComment");
//        tmpObj.setUserInput("Road");
//        tmpObj.setFieldType("string");
//        tmpObj.setEntity1Name("DbHazard");
//        tmpObj.setRelationType("like");
//        searchList.add(tmpObj);
//        tmpObj = new searchObject();
//
//        tmpObj.setFieldName("locationId");
//        tmpObj.setUserInput("1");
//        tmpObj.setFieldType("int");
//        tmpObj.setEntity2Name("hazardLocation");
//        tmpObj.setEntity1Name("DbHazard");
//        tmpObj.setRelationType("=");
//        searchList.add(tmpObj);
//        tmpObj = new searchObject();
//
//        tmpObj.setFieldName("hazardId");
//        tmpObj.setUserInput("1");
//        tmpObj.setFieldType("string");
//        tmpObj.setEntity1Name("DbHazard");
//        tmpObj.setRelationType("in");
//        searchList.add(tmpObj);
//        tmpObj = new searchObject();
//
//        List<DbHazard> resultantList = dbHazardFacade.findHazardsByFieldsOnly(searchList);
//
//        System.out.println("managedBeans.trees_MB.testSearch()");
    }

    public void testWorkFlow() {
        List<DbUser> listUsers = new ArrayList<>();
        listUsers.add(new DbUser(1));
        listUsers.add(new DbUser(2));

        DbwfHeader wfObj = new DbwfHeader();
        wfObj.setWfTypeId(new DbwfType("W1"));
        wfObj.setWfStatus("O");
        wfObj.setWfAddedDateTime(new Date());
        wfObj.setWfUserIdAdd(new DbUser(2));
        wfObj.setWfObjectId("NEP-ALL-0005");
        wfObj.setWfObjectName("Hazard");
        wfObj.setWfComment1("This an important hazard");
        wfObj.setWfComment2("It was disscused during the workshop 30-01");
        wfObj.setWfComment3("This is a testing comment");
        wfObj.setWfComment4("This is a testing comment2");
        wfObj.setWfCompleteMethod("HazardApprovalWF");

        validateIdObject result = dbwfHeaderFacade.newWorkFlow(listUsers, wfObj, autoConsec);
        System.out.println(result.getAnswerString());
    }

    public void testCompleteWorkFlow(String wfId, String userId, String trType, String dcsId) {
        DbwfLine tmpLine = dbwfLineFacade.findByIdAndUser(new DbwfLine(new DbwfLinePK(wfId, "")), Integer.parseInt(userId));
        tmpLine.setWfApproverDecisionId(new DbwfDecision(dcsId));
        tmpLine.setWfApprovalComment("Testing approvals");
        tmpLine.setWfDateTimeDecision(new Date());

        dbwfLineFacade.edit(tmpLine);

        dbwfHeaderFacade.reviewProcess(new DbwfHeader(wfId), trType);
    }

    public void validateNewHazardSearch() {
//        List<DbHazard> resultantList = dbHazardFacade.findAllHazards();
        searchObject tmpObj = new searchObject();
        List<searchObject> searchList = new ArrayList<>();
        tmpObj.setFieldName("hazardComment");
        tmpObj.setUserInput("as");
        tmpObj.setFieldType("string");
        tmpObj.setEntity1Name("DbHazard");
        tmpObj.setRelationType("like");
        searchList.add(tmpObj);
        tmpObj = new searchObject();

        tmpObj.setFieldName("locationId");
        tmpObj.setUserInput("1");
        tmpObj.setFieldType("int");
        tmpObj.setEntity2Name("hazardLocation");
        tmpObj.setEntity1Name("DbHazard");
        tmpObj.setRelationType("=");
        searchList.add(tmpObj);
        tmpObj = new searchObject();

        tmpObj.setFieldName("controlId");
        tmpObj.setUserInput("1,2,3");
        tmpObj.setFieldType("int");
        tmpObj.setEntity2Name("dbControlHazardPK");
        tmpObj.setEntity1Name("DbControlHazard");
        tmpObj.setRelationType("in");
        searchList.add(tmpObj);
        tmpObj = new searchObject();

        tmpObj.setFieldName("hazardId");
        tmpObj.setUserInput("1");
        tmpObj.setFieldType("string");
        tmpObj.setEntity1Name("DbHazard");
        tmpObj.setRelationType("in");
        searchList.add(tmpObj);
        tmpObj = new searchObject();

        tmpObj.setFieldName("causeDescription");
        tmpObj.setUserInput("new");
        tmpObj.setFieldType("string");
        tmpObj.setEntity1Name("DbCause");
        tmpObj.setRelationType("like");
        searchList.add(tmpObj);
        tmpObj = new searchObject();

        tmpObj.setFieldName("projectName");
        tmpObj.setUserInput("Mernda Rail");
        tmpObj.setFieldType("string");
        tmpObj.setEntity3Name("projectId");
        tmpObj.setEntity2Name("hazardLocation");
        tmpObj.setEntity1Name("DbHazard");
        tmpObj.setRelationType("like");
        searchList.add(tmpObj);
        tmpObj = new searchObject();

//        List<DbHazard> resultantList = dbHazardFacade.findHazards(searchList, new ArrayList<>(), "");
        treeNodeObject tmpTreeObj = new treeNodeObject();
        List<treeNodeObject> searchListTree = new ArrayList<>();
        tmpTreeObj.setNodeId("1.1.7.4.12.");
        tmpTreeObj.setNodeName("Abc");
        searchListTree.add(tmpTreeObj);
        tmpTreeObj = new treeNodeObject();

        tmpTreeObj.setNodeId("1.1.7.4.3.1.");
        tmpTreeObj.setNodeName("Def");
        searchListTree.add(tmpTreeObj);
        tmpTreeObj = new treeNodeObject();
//          
//        List<DbHazard> resultantList = dbHazardFacade.findHazardsByFieldsAndSbs(searchList, searchListTree);
//        searchObject tmpObj = new searchObject();
//        List<searchObject> searchList = new ArrayList<>();
//        tmpObj.setFieldName("hazardComment");
//        tmpObj.setUserInput("Road");
//        tmpObj.setFieldType("string");
//        tmpObj.setEntity1Name("DbHazard");
//        tmpObj.setRelationType("like");
//        searchList.add(tmpObj);
//        tmpObj = new searchObject();
//
//        tmpObj.setFieldName("locationId");
//        tmpObj.setUserInput("1");
//        tmpObj.setFieldType("int");
//        tmpObj.setEntity2Name("hazardLocation");
//        tmpObj.setEntity1Name("DbHazard");
//        tmpObj.setRelationType("=");
//        searchList.add(tmpObj);
//        tmpObj = new searchObject();
//
//        tmpObj.setFieldName("hazardId");
//        tmpObj.setUserInput("1");
//        tmpObj.setFieldType("string");
//        tmpObj.setEntity1Name("DbHazard");
//        tmpObj.setRelationType("in");
//        searchList.add(tmpObj);
//        tmpObj = new searchObject();
//
        List<Object> resultantList = dbHazardFacade.findHazards(searchList, searchListTree, "I", "DefaultView");
//        List<DbHazard> resultantList = (List<DbHazard>) (Object) dbHazardFacade.findHazards(searchList, searchListTree, "I", "DefaultView");
//
        System.out.println(resultantList.size());
    }

    public void experimentalTesting() {
        //experimentList = dbHazardFacade.testMethod();
    }

    public void exportExcel() {
        List<String> hazardsToExport = dbHazardFacade.findAll().stream().map(h -> h.getHazardId()).collect(Collectors.toList());
        // Setting up the file
        boolean complete = false;
        String filename = "SSD_Export.xls";

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Register");
        sheet.setZoom(90);

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
                String currentSeverityScore = checkNullString(exportedInfo.get(i)[14]);
                String currentFrequencyScore = checkNullString(exportedInfo.get(i)[15]);
                String currentRiskScore = exportedInfo.get(i)[16].toString();
                String targetSeverityScore = checkNullString(exportedInfo.get(i)[22]);
                String targetFrequencyScore = checkNullString(exportedInfo.get(i)[23]);
                String targetRiskScore = exportedInfo.get(i)[24].toString();
                String hazardComment = checkNullString(exportedInfo.get(i)[25]);
                String hazardDate = checkNullDate(exportedInfo.get(i)[26]);
                String hazardWorkshop = checkNullString(exportedInfo.get(i)[27]);
                String hazardStatusName = checkNullString(exportedInfo.get(i)[28]);
                String humanFactors = checkNullString(exportedInfo.get(i)[29]);

                exportedHazard curHazard = new exportedHazard(hazardId1, locationName, hazardContextName, hazardCauses, hazardDescription, ownerName, hazardConsequences,
                        hazardTypeName, riskClassName, currentSeverityScore, currentFrequencyScore, currentRiskScore, targetSeverityScore, targetFrequencyScore,
                        targetRiskScore, hazardComment, hazardDate, hazardWorkshop, hazardStatusName, humanFactors);

                // Getting the controls information
                List<exportedExsControl> listExsCtl = new ArrayList<>();
                List<exportedProControl> listProCtl = new ArrayList<>();
                for (int j = i; j < (i + numberOfRows); j++) {
                    if (exportedInfo.get(j)[10] != null && !listExsCtl.contains(new exportedExsControl((int) exportedInfo.get(j)[10]))) {
                        listExsCtl.add(new exportedExsControl((int) exportedInfo.get(j)[10], exportedInfo.get(j)[11].toString(), exportedInfo.get(j)[12].toString()));
                    }
                    if (exportedInfo.get(j)[17] != null && !listProCtl.contains(new exportedProControl((int) exportedInfo.get(j)[17]))) {
                        listProCtl.add(new exportedProControl((int) exportedInfo.get(j)[17], exportedInfo.get(j)[18].toString(), exportedInfo.get(j)[20].toString(),
                                exportedInfo.get(j)[19].toString(), exportedInfo.get(j)[21].toString()));
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

                // Hazard target severity is in column Q
                column = "Q";
                BodyCell = bodyRow.createCell(16);
                BodyCell.setCellValue(curHazard.currentSeverityScore);
                BodyCell.setCellStyle(bodyCellStyle);
                if (numberOfRowsperHazard > 1) {
                    sheet.addMergedRegion(CellRangeAddress.valueOf(column + (currentRow + 1) + ":" + column + (currentRow + numberOfRowsperHazard)));
                }

                // Hazard target frequency is in column R
                column = "R";
                BodyCell = bodyRow.createCell(17);
                BodyCell.setCellValue(curHazard.currentFrequencyScore);
                BodyCell.setCellStyle(bodyCellStyle);
                if (numberOfRowsperHazard > 1) {
                    sheet.addMergedRegion(CellRangeAddress.valueOf(column + (currentRow + 1) + ":" + column + (currentRow + numberOfRowsperHazard)));
                }

                // Hazard target score is in column T
                column = "T";
                BodyCell = bodyRow.createCell(19);
                BodyCell.setCellValue(curHazard.currentRiskScore);
                BodyCell.setCellStyle(bodyCellStyle);
                if (numberOfRowsperHazard > 1) {
                    sheet.addMergedRegion(CellRangeAddress.valueOf(column + (currentRow + 1) + ":" + column + (currentRow + numberOfRowsperHazard)));
                }

                // Hazard target severity is in column AC
                column = "AC";
                BodyCell = bodyRow.createCell(28);
                BodyCell.setCellValue(curHazard.targetSeverityScore);
                BodyCell.setCellStyle(bodyCellStyle);
                if (numberOfRowsperHazard > 1) {
                    sheet.addMergedRegion(CellRangeAddress.valueOf(column + (currentRow + 1) + ":" + column + (currentRow + numberOfRowsperHazard)));
                }

                // Hazard target frequency is in column AD
                column = "AD";
                BodyCell = bodyRow.createCell(29);
                BodyCell.setCellValue(curHazard.targetFrequencyScore);
                BodyCell.setCellStyle(bodyCellStyle);
                if (numberOfRowsperHazard > 1) {
                    sheet.addMergedRegion(CellRangeAddress.valueOf(column + (currentRow + 1) + ":" + column + (currentRow + numberOfRowsperHazard)));
                }

                // Hazard target score is in column AE
                column = "AE";
                BodyCell = bodyRow.createCell(30);
                BodyCell.setCellValue(curHazard.targetRiskScore);
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

                // Hazard status is in column AJ
                column = "AJ";
                BodyCell = bodyRow.createCell(35);
                BodyCell.setCellValue(curHazard.hazardStatusName);
                BodyCell.setCellStyle(bodyCellStyle);
                if (numberOfRowsperHazard > 1) {
                    sheet.addMergedRegion(CellRangeAddress.valueOf(column + (currentRow + 1) + ":" + column + (currentRow + numberOfRowsperHazard)));
                }

                // Human factors field is in column AK
                column = "AK";
                BodyCell = bodyRow.createCell(36);
                BodyCell.setCellValue(curHazard.humanFactors);
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
                    if (heightExsCtl > finalHeight) {
                        finalHeight = heightExsCtl;
                    }
                    if (heightProCtl > finalHeight) {
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

        tmpNoLines = (int) Math.ceil((double) hazardInfo.hazardDescription.length() / getCharPerLine("Risk Description", listOfHeaders));
        if (tmpNoLines > maxNoLines) {
            maxNoLines = tmpNoLines;
        }

        tmpNoLines = (int) Math.ceil((double) hazardInfo.hazardConsequences.length() / getCharPerLine("Potential Impacts", listOfHeaders));
        if (tmpNoLines > maxNoLines) {
            maxNoLines = tmpNoLines;
        }

        tmpNoLines = (int) Math.ceil((double) hazardInfo.hazardComment.length() / getCharPerLine("Comments / Assumptions", listOfHeaders));
        if (tmpNoLines > maxNoLines) {
            maxNoLines = tmpNoLines;
        }

        // Each line Height is around 300 so the number of lines required multiplied by 300 divided by the required rows for controls
        int minHeightRequired = (int) Math.round((maxNoLines * 300) / numberOfRows);

        if (minHeightRequired > 600) {
            return minHeightRequired;
        } else {
            return 600;
        }
    }

    private int getCharPerLine(String lookedValue, String[][] listOfHeaders) {
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
        public String currentSeverityScore;
        public String currentFrequencyScore;
        public String currentRiskScore;
        public String targetSeverityScore;
        public String targetFrequencyScore;
        public String targetRiskScore;
        public String hazardComment;
        public String hazardDate;
        public String hazardWorkshop;
        public String hazardStatusName;
        public String humanFactors;

        public exportedHazard(String hazardId, String locationName, String hazardContextName, String hazardCauses, String hazardDescription, String ownerName,
                String hazardConsequences, String hazardTypeName, String riskClassName, String currentSeverityScore, String currentFrequencyScore,
                String currentRiskScore, String targetSeverityScore, String targetFrequencyScore, String targetRiskScore, String hazardComment,
                String hazardDate, String hazardWorkshop, String hazardStatusName, String humanFactors) {
            this.hazardId = hazardId;
            this.locationName = locationName;
            this.hazardContextName = hazardContextName;
            this.hazardCauses = hazardCauses;
            this.hazardDescription = hazardDescription;
            this.ownerName = ownerName;
            this.hazardConsequences = hazardConsequences;
            this.hazardTypeName = hazardTypeName;
            this.riskClassName = riskClassName;
            this.currentSeverityScore = currentSeverityScore;
            this.currentFrequencyScore = currentFrequencyScore;
            this.currentRiskScore = currentRiskScore;
            this.targetSeverityScore = targetSeverityScore;
            this.targetFrequencyScore = targetFrequencyScore;
            this.targetRiskScore = targetRiskScore;
            this.hazardComment = hazardComment;
            this.hazardDate = hazardDate;
            this.hazardWorkshop = hazardWorkshop;
            this.hazardStatusName = hazardStatusName;
            this.humanFactors = humanFactors;
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
    }

    public void checkDuplicates(List<similarityObject> resultantList) {
        resultantList.sort(Comparator.comparingInt(similarityObject::getAverageDistance));
        resultantList.forEach((tmp) -> {
            System.out.println(tmp.toString());
        });
    }

    public void generateLayout() {
        //The produced excel will be on xslx format.
        String filename = "SSD_Import.xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Format");
        XSSFSheet hidden = workbook.createSheet("hidden");
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

        // Additional sheet configurations
        sheet.lockDeleteColumns(true);
        sheet.lockDeleteRows(true);
        sheet.lockFormatCells(true);
        sheet.lockFormatColumns(true);
        sheet.lockFormatRows(true);
        sheet.lockInsertColumns(true);
        sheet.lockInsertRows(true);
        sheet.protectSheet(dbsystemParametersFacade.find(1).getExcelLayoutPassword());  // Bring from the database
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
    public void processFile() {
        try {
            InputStream input = uploadedFile.getInputstream();
            XSSFWorkbook workbook = new XSSFWorkbook(input);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();

            // Defining key variables
            DbUser activeUser = (DbUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("activeUser");
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
                        listOfErrors.add(processedLine.lineError);
                    }
                }
            }
            importHeader.setTotalLines(listOfImportedLines.size());
            System.out.println("Header: " + importHeader.toString());
            System.out.println("Lines: " + listOfImportedLines.size());
            System.out.println("Errors: " + listOfErrors.size());

        } catch (IOException ex) {
            Logger.getLogger(trees_MB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Checking the file has some content and proccessing lines
    private importLineObj checkImportLine(Row row, String processId, int lineNo) {
        importLineObj tmpObj = null;
        boolean rowContent = false;
        try {
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
                                    checkLineError(tmpObj, "hazardContext", false, row.getCell(i).toString(), true, -1);
                                }
                            } else {
                                checkLineError(tmpObj, "hazardContext", false, row.getCell(i).toString(), false, 0);
                            }
                            break;
                        // Processing description    
                        case 1:
                            if ("".equals(row.getCell(i).toString())) {
                                checkLineError(tmpObj, "hazardDescription", false, row.getCell(i).toString(), false, 0);
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
                                    checkLineError(tmpObj, "hazardLocation", false, row.getCell(i).toString(), true, -1);
                                }
                            } else {
                                checkLineError(tmpObj, "hazardLocation", false, row.getCell(i).toString(), false, 0);
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
                                    checkLineError(tmpObj, "hazardActivity", false, row.getCell(i).toString(), true, -1);
                                }
                            } else {
                                checkLineError(tmpObj, "hazardActivity", false, row.getCell(i).toString(), false, 0);
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
                                    checkLineError(tmpObj, "hazardOwner", false, row.getCell(i).toString(), true, -1);
                                }
                            } else {
                                checkLineError(tmpObj, "hazardOwner", false, row.getCell(i).toString(), false, 0);
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
                                    checkLineError(tmpObj, "hazardType", false, row.getCell(i).toString(), true, -1);
                                }
                            } else {
                                checkLineError(tmpObj, "hazardType", false, row.getCell(i).toString(), false, 0);
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
                                    checkLineError(tmpObj, "hazardStatus", false, row.getCell(i).toString(), true, -1);
                                }
                            } else {
                                checkLineError(tmpObj, "hazardStatus", false, row.getCell(i).toString(), false, 0);
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
                                    checkLineError(tmpObj, "hazardRiskClass", false, row.getCell(i).toString(), true, -1);
                                }
                            } else {
                                checkLineError(tmpObj, "hazardRiskClass", false, row.getCell(i).toString(), false, 0);
                            }
                            break;
                        // Processing current frequency Id
                        case 8:
                            if (!"".equals(row.getCell(i).toString())) {
                                DbriskFrequency tmpVar = dbriskFrequencyFacade.findByName("frequencyScore", row.getCell(i).toString()).get(0);
                                if (tmpVar.getFrequencyScore() != null) {
                                    tmpObj.lineData.setHazardCurrentFrequencyId(tmpVar.getRiskFrequencyId());
                                } else {
                                    checkLineError(tmpObj, "hazardCurrentFrequency", false, row.getCell(i).toString(), true, -1);
                                }
                            } else {
                                checkLineError(tmpObj, "hazardCurrentFrequency", false, row.getCell(i).toString(), false, 0);
                            }
                            break;
                        // Processing current severity Id
                        case 9:
                            if (!"".equals(row.getCell(i).toString())) {
                                DbriskSeverity tmpVar = dbriskSeverityFacade.findByName("severityScore", row.getCell(i).toString()).get(0);
                                if (tmpVar.getSeverityScore() != null) {
                                    tmpObj.lineData.setHazardCurrentSeverityId(tmpVar.getRiskSeverityId());
                                } else {
                                    checkLineError(tmpObj, "hazardCurrentSeverity", false, row.getCell(i).toString(), true, -1);
                                }
                            } else {
                                checkLineError(tmpObj, "hazardCurrentSeverity", false, row.getCell(i).toString(), false, 0);
                            }
                            break;
                        // Processing target frequency Id
                        case 10:
                            if (!"".equals(row.getCell(i).toString())) {
                                DbriskFrequency tmpVar = dbriskFrequencyFacade.findByName("frequencyScore", row.getCell(i).toString()).get(0);
                                if (tmpVar.getFrequencyScore() != null) {
                                    tmpObj.lineData.setHazardTargetFrequencyId(tmpVar.getRiskFrequencyId());
                                } else {
                                    checkLineError(tmpObj, "hazardTargetFrequency", false, row.getCell(i).toString(), true, -1);
                                }
                            } else {
                                checkLineError(tmpObj, "hazardTargetFrequency", false, row.getCell(i).toString(), false, 0);
                            }
                            break;
                        // Processing target severity Id
                        case 11:
                            if (!"".equals(row.getCell(i).toString())) {
                                DbriskSeverity tmpVar = dbriskSeverityFacade.findByName("severityScore", row.getCell(i).toString()).get(0);
                                if (tmpVar.getSeverityScore() != null) {
                                    tmpObj.lineData.setHazardTargetSeverityId(tmpVar.getRiskSeverityId());
                                } else {
                                    checkLineError(tmpObj, "hazardTargetSeverity", false, row.getCell(i).toString(), true, -1);
                                }
                            } else {
                                checkLineError(tmpObj, "hazardTargetSeverity", false, row.getCell(i).toString(), false, 0);
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
                                    Logger.getLogger(trees_MB.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                checkLineError(tmpObj, "hazardDate", false, row.getCell(i).toString(), false, 0);
                            }
                            break;
                        // Processing hazard workshop
                        case 14:
                            if (!"".equals(row.getCell(i).toString())) {
                                tmpObj.lineData.setHazardWorkshop(row.getCell(i).toString());
                            } else {
                                checkLineError(tmpObj, "hazardWorkshop", false, row.getCell(i).toString(), false, 0);
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
                            } else {
                                checkLineError(tmpObj, "hazardHFReview", false, row.getCell(i).toString(), false, 0);
                            }
                            break;
                        // Processing hazard sbs codes 
                        case 17:
                            if (!"".equals(row.getCell(i).toString())) {
                                tmpObj.lineData.setHazardSbs(row.getCell(i).toString());
                            } else {
                                checkLineError(tmpObj, "hazardSbs", false, row.getCell(i).toString(), false, 0);
                            }
                            break;
                        // Processing relation type
                        case 18:
                            if (!"".equals(row.getCell(i).toString())) {
                                tmpObj.lineData.setRelationType(row.getCell(i).toString());
                            } else {
                                checkLineError(tmpObj, "relationType", false, row.getCell(i).toString(), false, 0);
                            }
                            break;
                        // Processing relation description
                        case 19:
                            if (!"".equals(row.getCell(i).toString())) {
                                tmpObj.lineData.setHazardDescription(row.getCell(i).toString());
                            } else {
                                checkLineError(tmpObj, "relationDescription", false, row.getCell(i).toString(), false, 0);
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
                                    checkLineError(tmpObj, "controlOwner", false, row.getCell(i).toString(), true, -1);
                                }
                            } else {
                                if (row.getCell(18).toString().equals("Control")) {
                                    checkLineError(tmpObj, "controlOwner", false, row.getCell(i).toString(), false, 0);
                                }
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
                                    checkLineError(tmpObj, "controlHierarchy", false, row.getCell(i).toString(), true, -1);
                                }
                            } else {
                                if (row.getCell(18).toString().equals("Control")) {
                                    checkLineError(tmpObj, "controlHierarchy", false, row.getCell(i).toString(), false, 0);
                                }
                            }
                            break;
                        // Processing control type
                        case 22:
                            if (!"".equals(row.getCell(i).toString())) {
                                tmpObj.lineData.setControlType(row.getCell(i).toString());
                            } else {
                                if (row.getCell(18).toString().equals("Control")) {
                                    checkLineError(tmpObj, "controlType", false, row.getCell(i).toString(), false, 0);
                                }
                            }
                            break;
                        // Processing control recommendation
                        case 23:
                            if (!"".equals(row.getCell(i).toString())) {
                                DbcontrolRecommend tmpVar = dbcontrolRecommendFacade.findByName("controlRecommendName", row.getCell(i).toString()).get(0);
                                if (tmpVar.getControlRecommendName() != null) {
                                    tmpObj.lineData.setControlHierarchyId(tmpVar.getControlRecommendId());
                                    tmpObj.lineData.setControlHierarchy(tmpVar.getControlRecommendName());
                                } else {
                                    checkLineError(tmpObj, "controlRecommend", false, row.getCell(i).toString(), true, -1);
                                }
                            } else {
                                if (row.getCell(18).toString().equals("Control")) {
                                    checkLineError(tmpObj, "controlRecommend", false, row.getCell(i).toString(), false, 0);
                                }
                            }
                            break;
                        // Processing control justify
                        case 24:
                            if (row.getCell(18).toString().equals("Control") && row.getCell(23).toString().equals("Refer to Control Justification")) {
                                tmpObj.lineData.setControlJustify(row.getCell(i).toString());
                            }
                            break;
                        // Processing control Status
                        case 25:
                            if (!"".equals(row.getCell(i).toString())) {
                                tmpObj.lineData.setControlExistingOrProposed(row.getCell(i).toString());
                            } else {
                                if (row.getCell(18).toString().equals("Control")) {
                                    checkLineError(tmpObj, "controlStatus", false, row.getCell(i).toString(), false, 0);
                                }
                            }
                            break;
                        default:
                            System.out.println("The value does not match with the expected columns. Value: " + row.getCell(i).toString() + " column index: " + i);
                            break;
                    }
                }
            }

        } catch (Exception e) {
            System.err.println(e);
        }
        return tmpObj;
    }

    // Creating the list of errors per line
    private importLineObj checkLineError(importLineObj tmpObj, String fieldName, boolean nullable, String value, boolean requiredId, int id) {
        if (!nullable && "".equals(value)) {
            DbimportLineError tmpError = new DbimportLineError(tmpObj.lineData.getDbimportLinePK().getProcessId(), tmpObj.lineData.getDbimportLinePK().getProcessIdLine(), tmpObj.lineError.size() + 1);
            tmpError.setProcessErrorCode(new DbimportErrorCode(1));
            tmpError.setProcessErrorLocation(fieldName);
            tmpObj.lineError.add(tmpError);
        }
        if (requiredId && id == -1) {
            DbimportLineError tmpError = new DbimportLineError(tmpObj.lineData.getDbimportLinePK().getProcessId(), tmpObj.lineData.getDbimportLinePK().getProcessIdLine(), tmpObj.lineError.size() + 1);
            tmpError.setProcessErrorCode(new DbimportErrorCode(2));
            tmpError.setProcessErrorLocation(fieldName);
            tmpObj.lineError.add(tmpError);
        }
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
