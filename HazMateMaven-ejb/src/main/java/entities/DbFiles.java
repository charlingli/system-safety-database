/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lxra
 */
@Entity
@Table(name = "db_files")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbFiles.findAll", query = "SELECT d FROM DbFiles d")
    , @NamedQuery(name = "DbFiles.findByFileId", query = "SELECT d FROM DbFiles d WHERE d.fileId = :fileId")
    , @NamedQuery(name = "DbFiles.findByFileName", query = "SELECT d FROM DbFiles d WHERE d.fileName = :fileName")
    , @NamedQuery(name = "DbFiles.findByFileExtension", query = "SELECT d FROM DbFiles d WHERE d.fileExtension = :fileExtension")
    , @NamedQuery(name = "DbFiles.findByFileSize", query = "SELECT d FROM DbFiles d WHERE d.fileSize = :fileSize")})
public class DbFiles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "fileId")
    private Integer fileId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "fileName")
    private String fileName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "fileExtension")
    private String fileExtension;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fileSize")
    private int fileSize;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "fileBlob")
    private byte[] fileBlob;
    @Lob
    @Size(max = 65535)
    @Column(name = "fileDescription")
    private String fileDescription;

    public DbFiles() {
    }

    public DbFiles(Integer fileId) {
        this.fileId = fileId;
    }

    public DbFiles(Integer fileId, String fileName, String fileExtension, int fileSize, byte[] fileBlob) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        this.fileSize = fileSize;
        this.fileBlob = fileBlob;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
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

    public byte[] getFileBlob() {
        return fileBlob;
    }

    public void setFileBlob(byte[] fileBlob) {
        this.fileBlob = fileBlob;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fileId != null ? fileId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbFiles)) {
            return false;
        }
        DbFiles other = (DbFiles) object;
        if ((this.fileId == null && other.fileId != null) || (this.fileId != null && !this.fileId.equals(other.fileId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbFiles[ fileId=" + fileId + " ]";
    }
    
}
