/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customObjects;

/**
 *
 * @author lxra
 */
public class fileHeaderObject {
    private int fileId;
    private String fileName;
    private String fileExtension;
    private int fileSize;
    private String fileDescription;
    
    public fileHeaderObject() {
    }
    
    public fileHeaderObject(Integer fileId, String fileName, String fileExtension, Integer fileSize, String fileDescription) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        this.fileSize = fileSize;
        this.fileDescription = fileDescription;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }
    
    @Override
    public String toString() {
        return "fileHeaderObject{" + "fileId: " + fileId + ", fileName: " + fileName + ", fileExtension: " + fileExtension + ", fileSize: " + fileSize + ", fileDescription: " + fileDescription + '}';
    }
}
