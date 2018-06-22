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
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan David
 */
@Entity
@Table(name = "db_treeLevel6")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbtreeLevel6.findAll", query = "SELECT d FROM DbtreeLevel6 d")
    , @NamedQuery(name = "DbtreeLevel6.findByTreeLevel6Id", query = "SELECT d FROM DbtreeLevel6 d WHERE d.dbtreeLevel6PK.treeLevel6Id = :treeLevel6Id")
    , @NamedQuery(name = "DbtreeLevel6.findByTreeLevel5Id", query = "SELECT d FROM DbtreeLevel6 d WHERE d.dbtreeLevel6PK.treeLevel5Id = :treeLevel5Id")
    , @NamedQuery(name = "DbtreeLevel6.findByTreeLevel4Id", query = "SELECT d FROM DbtreeLevel6 d WHERE d.dbtreeLevel6PK.treeLevel4Id = :treeLevel4Id")
    , @NamedQuery(name = "DbtreeLevel6.findByTreeLevel3Id", query = "SELECT d FROM DbtreeLevel6 d WHERE d.dbtreeLevel6PK.treeLevel3Id = :treeLevel3Id")
    , @NamedQuery(name = "DbtreeLevel6.findByTreeLevel2Id", query = "SELECT d FROM DbtreeLevel6 d WHERE d.dbtreeLevel6PK.treeLevel2Id = :treeLevel2Id")
    , @NamedQuery(name = "DbtreeLevel6.findByTreeLevel1Id", query = "SELECT d FROM DbtreeLevel6 d WHERE d.dbtreeLevel6PK.treeLevel1Id = :treeLevel1Id")
    , @NamedQuery(name = "DbtreeLevel6.findByTreeLevel6Index", query = "SELECT d FROM DbtreeLevel6 d WHERE d.treeLevel6Index = :treeLevel6Index")
    , @NamedQuery(name = "DbtreeLevel6.findByTreeLevel6Name", query = "SELECT d FROM DbtreeLevel6 d WHERE d.treeLevel6Name = :treeLevel6Name")})
public class DbtreeLevel6 implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DbtreeLevel6PK dbtreeLevel6PK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "treeLevel6Index")
    private int treeLevel6Index;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "treeLevel6Name")
    private String treeLevel6Name;
    @JoinColumns({
        @JoinColumn(name = "treeLevel5Id", referencedColumnName = "treeLevel5Id", insertable = false, updatable = false)
        , @JoinColumn(name = "treeLevel4Id", referencedColumnName = "treeLevel4Id", insertable = false, updatable = false)
        , @JoinColumn(name = "treeLevel3Id", referencedColumnName = "treeLevel3Id", insertable = false, updatable = false)
        , @JoinColumn(name = "treeLevel2Id", referencedColumnName = "treeLevel2Id", insertable = false, updatable = false)
        , @JoinColumn(name = "treeLevel1Id", referencedColumnName = "treeLevel1Id", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private DbtreeLevel5 dbtreeLevel5;

    public DbtreeLevel6() {
    }

    public DbtreeLevel6(DbtreeLevel6PK dbtreeLevel6PK) {
        this.dbtreeLevel6PK = dbtreeLevel6PK;
    }

    public DbtreeLevel6(DbtreeLevel6PK dbtreeLevel6PK, int treeLevel6Index, String treeLevel6Name) {
        this.dbtreeLevel6PK = dbtreeLevel6PK;
        this.treeLevel6Index = treeLevel6Index;
        this.treeLevel6Name = treeLevel6Name;
    }

    public DbtreeLevel6(int treeLevel6Id, int treeLevel5Id, int treeLevel4Id, int treeLevel3Id, int treeLevel2Id, int treeLevel1Id) {
        this.dbtreeLevel6PK = new DbtreeLevel6PK(treeLevel6Id, treeLevel5Id, treeLevel4Id, treeLevel3Id, treeLevel2Id, treeLevel1Id);
    }

    public DbtreeLevel6PK getDbtreeLevel6PK() {
        return dbtreeLevel6PK;
    }

    public void setDbtreeLevel6PK(DbtreeLevel6PK dbtreeLevel6PK) {
        this.dbtreeLevel6PK = dbtreeLevel6PK;
    }

    public int getTreeLevel6Index() {
        return treeLevel6Index;
    }

    public void setTreeLevel6Index(int treeLevel6Index) {
        this.treeLevel6Index = treeLevel6Index;
    }

    public String getTreeLevel6Name() {
        return treeLevel6Name;
    }

    public void setTreeLevel6Name(String treeLevel6Name) {
        this.treeLevel6Name = treeLevel6Name;
    }

    public DbtreeLevel5 getDbtreeLevel5() {
        return dbtreeLevel5;
    }

    public void setDbtreeLevel5(DbtreeLevel5 dbtreeLevel5) {
        this.dbtreeLevel5 = dbtreeLevel5;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dbtreeLevel6PK != null ? dbtreeLevel6PK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbtreeLevel6)) {
            return false;
        }
        DbtreeLevel6 other = (DbtreeLevel6) object;
        if ((this.dbtreeLevel6PK == null && other.dbtreeLevel6PK != null) || (this.dbtreeLevel6PK != null && !this.dbtreeLevel6PK.equals(other.dbtreeLevel6PK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbtreeLevel6[ dbtreeLevel6PK=" + dbtreeLevel6PK + " ]";
    }
    
}
