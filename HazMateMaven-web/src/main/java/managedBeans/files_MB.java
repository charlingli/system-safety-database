/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import customObjects.fileHeaderObject;
import ejb.DbFilesFacadeLocal;
import ejb.DbHazardFilesFacadeLocal;
import entities.DbFiles;
import entities.DbHazard;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import static org.apache.commons.io.IOUtils.toByteArray;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Charling Li
 */
@Named(value = "files_MB")
@ViewScoped
public class files_MB implements Serializable {

    @EJB
    private DbHazardFilesFacadeLocal dbHazardFilesFacade;

    @EJB
    private DbFilesFacadeLocal dbFilesFacade;
    private List<fileHeaderObject> listHeaders;
    private DbFiles fileObject = new DbFiles();
    private String originalName;
    private List<DbHazard> linkedHazards;

    private boolean addFlag = false;
    private boolean editFlag = false;

    public List<fileHeaderObject> getListHeaders() {
        return listHeaders;
    }

    public void setListHeaders(List<fileHeaderObject> listHeaders) {
        this.listHeaders = listHeaders;
    }

    public DbFiles getFileObject() {
        return fileObject;
    }

    public void setFileObject(DbFiles fileObject) {
        this.fileObject = fileObject;
    }

    public List<DbHazard> getLinkedHazards() {
        return linkedHazards;
    }

    public void setLinkedHazards(List<DbHazard> linkedHazards) {
        this.linkedHazards = linkedHazards;
    }

    public boolean isAddFlag() {
        return addFlag;
    }

    public void setAddFlag(boolean addFlag) {
        this.addFlag = addFlag;
    }

    public boolean isEditFlag() {
        return editFlag;
    }

    public void setEditFlag(boolean editFlag) {
        this.editFlag = editFlag;
    }
    
    public files_MB() {
    }

    @PostConstruct
    public void init() {
        listHeaders = dbFilesFacade.listAllHeaders();
    }

    public void renameFile() {
        if (dbFilesFacade.findHeadersForDuplicate(fileObject.getFileName(), fileObject.getFileExtension()).size() >= 1 && !originalName.equals(fileObject.getFileName())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "'" + fileObject.getFileName() + "." + fileObject.getFileExtension() + "' already exists in the database!"));
            return;
        } else {
            dbFilesFacade.edit(fileObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "'" + fileObject.getFileName() + "." + fileObject.getFileExtension() + "' has been successfully updated."));
        }
        fileObject = new DbFiles();
        init();
        editFlag = false;
    }

    public void deleteFile(int fileId) {
        fileObject = dbFilesFacade.findFileFromId(fileId).get(0);
        if (dbHazardFilesFacade.hasHazardsLinked(fileId)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Hazards have already been linked to '" + fileObject.getFileName() + "." + fileObject.getFileExtension() + "'!"));
            return;
        } else {
            dbFilesFacade.remove(fileObject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "The file has been successfully deleted."));
        }
        init();
    }

    public void showAdd() {
        addFlag = true;
    }

    public void showEdit(fileHeaderObject file) {
        editFlag = true;
        this.fileObject = dbFilesFacade.findFileFromId(file.getFileId()).get(0);
        this.originalName = dbFilesFacade.findFileFromId(file.getFileId()).get(0).getFileName();
    }

    public void cancel() {
        addFlag = false;
        editFlag = false;
        this.fileObject = new DbFiles();
    }
    
    public void handleUpload(FileUploadEvent event) {
        try {
            UploadedFile rawFile = event.getFile();
            InputStream fileStream = rawFile.getInputstream();
            String[] rawName = rawFile.getFileName().split("\\.");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < rawName.length - 1; i++) {
                sb.append(rawName[i]);
            }
            String fileName = sb.toString();
            String fileExtension = rawName[rawName.length - 1];
            
            if (dbFilesFacade.findHeadersForDuplicate(fileName, fileExtension).size() >= 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "'" + fileName + "." + fileExtension + "' already exists in the database!"));
            } else {
                DbFiles newFile = new DbFiles();
                newFile.setFileName(fileName);
                newFile.setFileExtension(fileExtension);
                newFile.setFileSize(rawFile.getContents().length);
                newFile.setFileBlob(toByteArray(fileStream));
                dbFilesFacade.create(newFile);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "'" + fileName + "." + fileExtension + "' has been successfully uploaded."));
            }
            
        listHeaders = dbFilesFacade.listAllHeaders(); 
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", e.getMessage()));
        }
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
    
    public void showLinkedHazards(int fileId) {
        setFileObject(dbFilesFacade.findFileFromId(fileId).get(0));
        linkedHazards = dbHazardFilesFacade.findLinkedHazards(fileId);
    }
}
