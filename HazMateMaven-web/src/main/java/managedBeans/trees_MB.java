/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import customObjects.searchObject;
import ejb.DbHazardSbsFacadeLocal;
import ejb.DbtreeLevel1FacadeLocal;
import ejb.DbtreeLevel2FacadeLocal;
import ejb.DbtreeLevel3FacadeLocal;
import ejb.DbtreeLevel4FacadeLocal;
import ejb.DbtreeLevel5FacadeLocal;
import ejb.DbtreeLevel6FacadeLocal;
import entities.DbtreeLevel1;
import entities.DbtreeLevel2;
import entities.DbtreeLevel3;
import entities.DbtreeLevel4;
import entities.DbtreeLevel5;
import entities.DbtreeLevel6;
import entities.DbHazardSbs;
import entities.DbwfHeader;
import customObjects.treeNodeObject;
import customObjects.validateIdObject;
import ejb.DbHazardFacadeLocal;
import ejb.DbwfHeaderFacadeLocal;
import ejb.DbwfLineFacadeLocal;
import entities.DbHazard;
import entities.DbUser;
import entities.DbwfDecision;
import entities.DbwfLine;
import entities.DbwfLinePK;
import entities.DbwfType;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
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
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Juan David
 */
@Named(value = "trees_MB")
@ViewScoped
public class trees_MB implements Serializable {

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
                builder.append("<br />");
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
}
