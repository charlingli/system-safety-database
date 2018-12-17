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
@Table(name = "db_commonWord")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbcommonWord.findAll", query = "SELECT d FROM DbcommonWord d")
    , @NamedQuery(name = "DbcommonWord.findByCommonWordId", query = "SELECT d FROM DbcommonWord d WHERE d.commonWordId = :commonWordId")
    , @NamedQuery(name = "DbcommonWord.findByCommonWord", query = "SELECT d FROM DbcommonWord d WHERE d.commonWord = :commonWord")})
public class DbcommonWord implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "commonWordId")
    private Integer commonWordId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "commonWord")
    private String commonWord;

    public DbcommonWord() {
    }

    public DbcommonWord(Integer commonWordId) {
        this.commonWordId = commonWordId;
    }

    public DbcommonWord(Integer commonWordId, String commonWord) {
        this.commonWordId = commonWordId;
        this.commonWord = commonWord;
    }

    public Integer getCommonWordId() {
        return commonWordId;
    }

    public void setCommonWordId(Integer commonWordId) {
        this.commonWordId = commonWordId;
    }

    public String getCommonWord() {
        return commonWord;
    }

    public void setCommonWord(String commonWord) {
        this.commonWord = commonWord;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commonWordId != null ? commonWordId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbcommonWord)) {
            return false;
        }
        DbcommonWord other = (DbcommonWord) object;
        if ((this.commonWordId == null && other.commonWordId != null) || (this.commonWordId != null && !this.commonWordId.equals(other.commonWordId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbcommonWord[ commonWordId=" + commonWordId + " ]";
    }
    
}
