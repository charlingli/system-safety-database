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
@Table(name = "db_treeLevel4")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbtreeLevel4.findAll", query = "SELECT d FROM DbtreeLevel4 d")
    , @NamedQuery(name = "DbtreeLevel4.findByTreeLevel4Id", query = "SELECT d FROM DbtreeLevel4 d WHERE d.dbtreeLevel4PK.treeLevel4Id = :treeLevel4Id")
    , @NamedQuery(name = "DbtreeLevel4.findByTreeLevel3Id", query = "SELECT d FROM DbtreeLevel4 d WHERE d.dbtreeLevel4PK.treeLevel3Id = :treeLevel3Id")
    , @NamedQuery(name = "DbtreeLevel4.findByTreeLevel2Id", query = "SELECT d FROM DbtreeLevel4 d WHERE d.dbtreeLevel4PK.treeLevel2Id = :treeLevel2Id")
    , @NamedQuery(name = "DbtreeLevel4.findByTreeLevel1Id", query = "SELECT d FROM DbtreeLevel4 d WHERE d.dbtreeLevel4PK.treeLevel1Id = :treeLevel1Id")
    , @NamedQuery(name = "DbtreeLevel4.findByTreeLevel4Index", query = "SELECT d FROM DbtreeLevel4 d WHERE d.treeLevel4Index = :treeLevel4Index")
    , @NamedQuery(name = "DbtreeLevel4.findByTreeLevel4Name", query = "SELECT d FROM DbtreeLevel4 d WHERE d.treeLevel4Name = :treeLevel4Name")})
public class DbtreeLevel4 implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DbtreeLevel4PK dbtreeLevel4PK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "treeLevel4Index")
    private int treeLevel4Index;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "treeLevel4Name")
    private String treeLevel4Name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dbtreeLevel4")
    private List<DbtreeLevel5> dbtreeLevel5List;
    @JoinColumns({
        @JoinColumn(name = "treeLevel3Id", referencedColumnName = "treeLevel3Id", insertable = false, updatable = false)
        , @JoinColumn(name = "treeLevel2Id", referencedColumnName = "treeLevel2Id", insertable = false, updatable = false)
        , @JoinColumn(name = "treeLevel1Id", referencedColumnName = "treeLevel1Id", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private DbtreeLevel3 dbtreeLevel3;

    public DbtreeLevel4() {
    }

    public DbtreeLevel4(DbtreeLevel4PK dbtreeLevel4PK) {
        this.dbtreeLevel4PK = dbtreeLevel4PK;
    }

    public DbtreeLevel4(DbtreeLevel4PK dbtreeLevel4PK, int treeLevel4Index, String treeLevel4Name) {
        this.dbtreeLevel4PK = dbtreeLevel4PK;
        this.treeLevel4Index = treeLevel4Index;
        this.treeLevel4Name = treeLevel4Name;
    }

    public DbtreeLevel4(int treeLevel4Id, int treeLevel3Id, int treeLevel2Id, int treeLevel1Id) {
        this.dbtreeLevel4PK = new DbtreeLevel4PK(treeLevel4Id, treeLevel3Id, treeLevel2Id, treeLevel1Id);
    }

    public DbtreeLevel4PK getDbtreeLevel4PK() {
        return dbtreeLevel4PK;
    }

    public void setDbtreeLevel4PK(DbtreeLevel4PK dbtreeLevel4PK) {
        this.dbtreeLevel4PK = dbtreeLevel4PK;
    }

    public int getTreeLevel4Index() {
        return treeLevel4Index;
    }

    public void setTreeLevel4Index(int treeLevel4Index) {
        this.treeLevel4Index = treeLevel4Index;
    }

    public String getTreeLevel4Name() {
        return treeLevel4Name;
    }

    public void setTreeLevel4Name(String treeLevel4Name) {
        this.treeLevel4Name = treeLevel4Name;
    }

    @XmlTransient
    public List<DbtreeLevel5> getDbtreeLevel5List() {
        return dbtreeLevel5List;
    }

    public void setDbtreeLevel5List(List<DbtreeLevel5> dbtreeLevel5List) {
        this.dbtreeLevel5List = dbtreeLevel5List;
    }

    public DbtreeLevel3 getDbtreeLevel3() {
        return dbtreeLevel3;
    }

    public void setDbtreeLevel3(DbtreeLevel3 dbtreeLevel3) {
        this.dbtreeLevel3 = dbtreeLevel3;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dbtreeLevel4PK != null ? dbtreeLevel4PK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbtreeLevel4)) {
            return false;
        }
        DbtreeLevel4 other = (DbtreeLevel4) object;
        if ((this.dbtreeLevel4PK == null && other.dbtreeLevel4PK != null) || (this.dbtreeLevel4PK != null && !this.dbtreeLevel4PK.equals(other.dbtreeLevel4PK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbtreeLevel4[ dbtreeLevel4PK=" + dbtreeLevel4PK + " ]";
    }
    
}
