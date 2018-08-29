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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
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
    
    @PostConstruct
    public void init() {
        listTreeLevel1 = dbtreeLevel1Facade.findAll();
        createTree();
        System.out.println("managedBeans.trees_MB.init()");
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

        List<DbHazard> resultantList = dbHazardFacade.findHazardsByFields(searchList);

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
}
