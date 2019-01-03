/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import customObjects.fileHeaderObject;
import customObjects.treeNodeObject;
import ejb.DbFilesFacadeLocal;
import ejb.DbHazardFacadeLocal;
import ejb.DbHazardFilesFacadeLocal;
import ejb.DbHazardSbsFacadeLocal;
import ejb.DbtreeLevel1FacadeLocal;
import entities.DbControlHazard;
import entities.DbHazard;
import entities.DbHazardCause;
import entities.DbHazardConsequence;
import entities.DbHazardSbs;
import entities.DbtreeLevel1;
import entities.DbtreeLevel2;
import entities.DbtreeLevel3;
import entities.DbtreeLevel4;
import entities.DbtreeLevel5;
import entities.DbtreeLevel6;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 *
 * @author lxra
 */
@Named(value = "hazardDetail_MB")
@ViewScoped
public class hazardDetail_MB implements Serializable {

    @EJB
    private DbtreeLevel1FacadeLocal dbtreeLevel1Facade;

    @EJB
    private DbHazardSbsFacadeLocal dbHazardSbsFacade;

    @EJB
    private DbFilesFacadeLocal dbFilesFacade;

    @EJB
    private DbHazardFilesFacadeLocal dbHazardFilesFacade;

    @EJB
    private DbHazardFacadeLocal dbHazardFacade;
    
    private DbHazard detailHazard;
    private List<DbHazardCause> detailCauses;
    private List<DbHazardConsequence> detailConsequences;
    private List<DbControlHazard> detailControls;
    private List<String> detailSbsNames;
    private List<fileHeaderObject> fileHeaders;
    
    public DbHazard getDetailHazard() {
        return detailHazard;
    }

    public void setDetailHazard(DbHazard detailHazard) {
        this.detailHazard = detailHazard;
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

    public List<String> getDetailSbsNames() {
        return detailSbsNames;
    }

    public void setDetailSbsNames(List<String> detailSbsNames) {
        this.detailSbsNames = detailSbsNames;
    }

    public List<fileHeaderObject> getFileHeaders() {
        return fileHeaders;
    }

    public void setFileHeaders(List<fileHeaderObject> fileHeaders) {
        this.fileHeaders = fileHeaders;
    }
    
    public hazardDetail_MB() {
    }
    
    @PostConstruct
    private void init() {
        
    }
    
    public void showDetail(String hazardId) {
        setDetailHazard(dbHazardFacade.findByName("hazardId", hazardId).get(0));
        detailCauses = dbHazardFacade.getHazardCause(getDetailHazard().getHazardId());
        detailConsequences = dbHazardFacade.getHazardConsequence(getDetailHazard().getHazardId());
        detailControls = dbHazardFacade.getControlHazard(getDetailHazard().getHazardId());
        fileHeaders = dbHazardFilesFacade.findHeadersForHazard(hazardId);
        
        detailSbsNames = new ArrayList<>();
        List<DbHazardSbs> listSbsObj = dbHazardSbsFacade.findDistinctSbs(hazardId);
        for (DbHazardSbs detailSbsObj : listSbsObj) {
            detailSbsNames.add(getNodeNameById(detailSbsObj.getDbHazardSbsPK().getSbsId()));
        }
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

}
