/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import customObjects.similarityObject;
import customObjects.treeNodeObject;
import ejb.DbCauseFacadeLocal;
import ejb.DbConsequenceFacadeLocal;
import ejb.DbControlFacadeLocal;
import ejb.DbHazardFacadeLocal;
import ejb.DbHazardSbsFacadeLocal;
import ejb.DbtreeLevel1FacadeLocal;
import entities.DbCause;
import entities.DbConsequence;
import entities.DbControl;
import entities.DbHazard;
import entities.DbHazardSbs;
import entities.DbtreeLevel1;
import entities.DbtreeLevel2;
import entities.DbtreeLevel3;
import entities.DbtreeLevel4;
import entities.DbtreeLevel5;
import entities.DbtreeLevel6;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author lxra
 */
@Named(value = "wordSimilarity_MB")
@ViewScoped
public class wordSimilarity_MB implements Serializable {

    @EJB
    private DbControlFacadeLocal dbControlFacade;

    @EJB
    private DbConsequenceFacadeLocal dbConsequenceFacade;

    @EJB
    private DbCauseFacadeLocal dbCauseFacade;

    @EJB
    private DbtreeLevel1FacadeLocal dbtreeLevel1Facade;

    @EJB
    private DbHazardSbsFacadeLocal dbHazardSbsFacade;

    @EJB
    private DbHazardFacadeLocal dbHazardFacade;

    private List<similarityWrapperObject> listSimilarityObjects;
    private String similarityObjectType;

    public List<similarityWrapperObject> getListSimilarityObjects() {
        return listSimilarityObjects;
    }

    public void setListSimilarityObjects(List<similarityWrapperObject> listSimilarityObjects) {
        this.listSimilarityObjects = listSimilarityObjects;
    }

    public String getSimilarityObjectType() {
        return similarityObjectType;
    }

    public void setSimilarityObjectType(String similarityObjectType) {
        this.similarityObjectType = similarityObjectType;
    }
    
    public wordSimilarity_MB() {
        
    }
    
    @PostConstruct
    public void init() {
    }
    
    public void constructSimilarityObject(List<similarityObject> listObjects, String objectType, String objectId) {
        listSimilarityObjects = new ArrayList<>();
        for (similarityObject object : listObjects) {
            System.out.println(object.getObjectId());
            if (!object.getObjectId().equals(objectId)) {
                listSimilarityObjects.add(new similarityWrapperObject(object.getObjectId(), object.getAverageDistance(), object.getCoincidentWords(), objectType));
            }
        }
        similarityObjectType = objectType;
        if (listSimilarityObjects.size() > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Potential duplicates have been detected for the proposed submission."));
            RequestContext.getCurrentInstance().execute("PF('similarityOverlay').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "No potential duplicates were found."));
            RequestContext.getCurrentInstance().execute("processPage()");
        }
    }
    
    public class similarityWrapperObject extends similarityObject {
        public String objectDescription;
        public List<String> objectExtra;

        public String getObjectDescription() {
            return objectDescription;
        }

        public void setObjectDescription(String objectDescription) {
            this.objectDescription = objectDescription;
        }

        public List<String> getObjectExtra() {
            return objectExtra;
        }

        public void setObjectExtra(List<String> objectExtra) {
            this.objectExtra = objectExtra;
        }
        
        public similarityWrapperObject(String objectId, int averageDistance, String[] coincidentWords, String objectType) {
            super(objectId, averageDistance, coincidentWords);
            if (objectType.equals("hazard")) {
                DbHazard tempObject = dbHazardFacade.findByName("hazardId", objectId).get(0);
                this.objectDescription = tempObject.getHazardDescription();
                List<String> detailSbsNames = new ArrayList<>();
                List<DbHazardSbs> listSbsObj = dbHazardSbsFacade.findDistinctSbs(objectId);
                for (DbHazardSbs detailSbsObj : listSbsObj) {
                    detailSbsNames.add(getNodeNameById(detailSbsObj.getDbHazardSbsPK().getSbsId()));
                }
                this.objectExtra = detailSbsNames;
            } else if (objectType.equals("cause")) {
                this.objectExtra = new ArrayList<>();
                DbCause tempObject = dbCauseFacade.findByName("causeId", objectId).get(0);
                this.objectDescription = tempObject.getCauseDescription();
            } else if (objectType.equals("consequence")) {
                this.objectExtra = new ArrayList<>();
                DbConsequence tempObject = dbConsequenceFacade.findByName("consequenceId", objectId).get(0);
                this.objectDescription = tempObject.getConsequenceDescription();
            } else if (objectType.equals("control")) {
                this.objectExtra = new ArrayList<>();
                DbControl tempObject = dbControlFacade.findByName("controlId", objectId).get(0);
                this.objectDescription = tempObject.getControlDescription();
                this.objectExtra.add(tempObject.getOwnerId().getOwnerName());
            }
        }
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
    
    public void confirmContinue() {
        RequestContext.getCurrentInstance().execute("PF('spinnerOverlay').hide()");
    }
}
