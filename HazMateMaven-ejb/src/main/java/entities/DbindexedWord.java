/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author David Ortega <david.ortega@levelcrossings.vic.gov.au>
 */
@Entity
@Table(name = "db_indexedWord")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbindexedWord.findAll", query = "SELECT d FROM DbindexedWord d")
    , @NamedQuery(name = "DbindexedWord.findByObjectId", query = "SELECT d FROM DbindexedWord d WHERE d.dbindexedWordPK.objectId = :objectId")
    , @NamedQuery(name = "DbindexedWord.findByObjectLineNo", query = "SELECT d FROM DbindexedWord d WHERE d.dbindexedWordPK.objectLineNo = :objectLineNo")
    , @NamedQuery(name = "DbindexedWord.findByObjectType", query = "SELECT d FROM DbindexedWord d WHERE d.dbindexedWordPK.objectType = :objectType")
    , @NamedQuery(name = "DbindexedWord.findByIndexedWord", query = "SELECT d FROM DbindexedWord d WHERE d.indexedWord = :indexedWord")})
public class DbindexedWord implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DbindexedWordPK dbindexedWordPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "indexedWord")
    private String indexedWord;

    public DbindexedWord() {
    }

    public DbindexedWord(DbindexedWordPK dbindexedWordPK) {
        this.dbindexedWordPK = dbindexedWordPK;
    }

    public DbindexedWord(DbindexedWordPK dbindexedWordPK, String indexedWord) {
        this.dbindexedWordPK = dbindexedWordPK;
        this.indexedWord = indexedWord;
    }

    public DbindexedWord(String objectId, int objectLineNo, String objectType) {
        this.dbindexedWordPK = new DbindexedWordPK(objectId, objectLineNo, objectType);
    }

    public DbindexedWordPK getDbindexedWordPK() {
        return dbindexedWordPK;
    }

    public void setDbindexedWordPK(DbindexedWordPK dbindexedWordPK) {
        this.dbindexedWordPK = dbindexedWordPK;
    }

    public String getIndexedWord() {
        return indexedWord;
    }

    public void setIndexedWord(String indexedWord) {
        this.indexedWord = indexedWord;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dbindexedWordPK != null ? dbindexedWordPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbindexedWord)) {
            return false;
        }
        DbindexedWord other = (DbindexedWord) object;
        if ((this.dbindexedWordPK == null && other.dbindexedWordPK != null) || (this.dbindexedWordPK != null && !this.dbindexedWordPK.equals(other.dbindexedWordPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbindexedWord[ dbindexedWordPK=" + dbindexedWordPK + " ]";
    }
    
}
