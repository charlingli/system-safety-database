/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Juan David
 */
@Entity
@Table(name = "db_treeLevel5")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbtreeLevel5.findAll", query = "SELECT d FROM DbtreeLevel5 d")
    , @NamedQuery(name = "DbtreeLevel5.findByTreeLevel5Id", query = "SELECT d FROM DbtreeLevel5 d WHERE d.dbtreeLevel5PK.treeLevel5Id = :treeLevel5Id")
    , @NamedQuery(name = "DbtreeLevel5.findByTreeLevel4Id", query = "SELECT d FROM DbtreeLevel5 d WHERE d.dbtreeLevel5PK.treeLevel4Id = :treeLevel4Id")
    , @NamedQuery(name = "DbtreeLevel5.findByTreeLevel3Id", query = "SELECT d FROM DbtreeLevel5 d WHERE d.dbtreeLevel5PK.treeLevel3Id = :treeLevel3Id")
    , @NamedQuery(name = "DbtreeLevel5.findByTreeLevel2Id", query = "SELECT d FROM DbtreeLevel5 d WHERE d.dbtreeLevel5PK.treeLevel2Id = :treeLevel2Id")
    , @NamedQuery(name = "DbtreeLevel5.findByTreeLevel1Id", query = "SELECT d FROM DbtreeLevel5 d WHERE d.dbtreeLevel5PK.treeLevel1Id = :treeLevel1Id")
    , @NamedQuery(name = "DbtreeLevel5.findByTreeLevel5Index", query = "SELECT d FROM DbtreeLevel5 d WHERE d.treeLevel5Index = :treeLevel5Index")
    , @NamedQuery(name = "DbtreeLevel5.findByTreeLevel5Name", query = "SELECT d FROM DbtreeLevel5 d WHERE d.treeLevel5Name = :treeLevel5Name")})
public class DbtreeLevel5 implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DbtreeLevel5PK dbtreeLevel5PK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "treeLevel5Index")
    private int treeLevel5Index;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "treeLevel5Name")
    private String treeLevel5Name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dbtreeLevel5")
    private List<DbtreeLevel6> dbtreeLevel6List;
    @JoinColumns({
        @JoinColumn(name = "treeLevel4Id", referencedColumnName = "treeLevel4Id", insertable = false, updatable = false)
        , @JoinColumn(name = "treeLevel3Id", referencedColumnName = "treeLevel3Id", insertable = false, updatable = false)
        , @JoinColumn(name = "treeLevel2Id", referencedColumnName = "treeLevel2Id", insertable = false, updatable = false)
        , @JoinColumn(name = "treeLevel1Id", referencedColumnName = "treeLevel1Id", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private DbtreeLevel4 dbtreeLevel4;

    public DbtreeLevel5() {
    }

    public DbtreeLevel5(DbtreeLevel5PK dbtreeLevel5PK) {
        this.dbtreeLevel5PK = dbtreeLevel5PK;
    }

    public DbtreeLevel5(DbtreeLevel5PK dbtreeLevel5PK, int treeLevel5Index, String treeLevel5Name) {
        this.dbtreeLevel5PK = dbtreeLevel5PK;
        this.treeLevel5Index = treeLevel5Index;
        this.treeLevel5Name = treeLevel5Name;
    }

    public DbtreeLevel5(int treeLevel5Id, int treeLevel4Id, int treeLevel3Id, int treeLevel2Id, int treeLevel1Id) {
        this.dbtreeLevel5PK = new DbtreeLevel5PK(treeLevel5Id, treeLevel4Id, treeLevel3Id, treeLevel2Id, treeLevel1Id);
    }

    public DbtreeLevel5PK getDbtreeLevel5PK() {
        return dbtreeLevel5PK;
    }

    public void setDbtreeLevel5PK(DbtreeLevel5PK dbtreeLevel5PK) {
        this.dbtreeLevel5PK = dbtreeLevel5PK;
    }

    public int getTreeLevel5Index() {
        return treeLevel5Index;
    }

    public void setTreeLevel5Index(int treeLevel5Index) {
        this.treeLevel5Index = treeLevel5Index;
    }

    public String getTreeLevel5Name() {
        return treeLevel5Name;
    }

    public void setTreeLevel5Name(String treeLevel5Name) {
        this.treeLevel5Name = treeLevel5Name;
    }

    @XmlTransient
    public List<DbtreeLevel6> getDbtreeLevel6List() {
        return dbtreeLevel6List;
    }

    public void setDbtreeLevel6List(List<DbtreeLevel6> dbtreeLevel6List) {
        this.dbtreeLevel6List = dbtreeLevel6List;
    }

    public DbtreeLevel4 getDbtreeLevel4() {
        return dbtreeLevel4;
    }

    public void setDbtreeLevel4(DbtreeLevel4 dbtreeLevel4) {
        this.dbtreeLevel4 = dbtreeLevel4;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dbtreeLevel5PK != null ? dbtreeLevel5PK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbtreeLevel5)) {
            return false;
        }
        DbtreeLevel5 other = (DbtreeLevel5) object;
        if ((this.dbtreeLevel5PK == null && other.dbtreeLevel5PK != null) || (this.dbtreeLevel5PK != null && !this.dbtreeLevel5PK.equals(other.dbtreeLevel5PK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbtreeLevel5[ dbtreeLevel5PK=" + dbtreeLevel5PK + " ]";
    }
    
}
