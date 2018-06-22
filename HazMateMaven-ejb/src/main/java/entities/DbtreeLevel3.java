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
@Table(name = "db_treeLevel3")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbtreeLevel3.findAll", query = "SELECT d FROM DbtreeLevel3 d")
    , @NamedQuery(name = "DbtreeLevel3.findByTreeLevel3Id", query = "SELECT d FROM DbtreeLevel3 d WHERE d.dbtreeLevel3PK.treeLevel3Id = :treeLevel3Id")
    , @NamedQuery(name = "DbtreeLevel3.findByTreeLevel2Id", query = "SELECT d FROM DbtreeLevel3 d WHERE d.dbtreeLevel3PK.treeLevel2Id = :treeLevel2Id")
    , @NamedQuery(name = "DbtreeLevel3.findByTreeLevel1Id", query = "SELECT d FROM DbtreeLevel3 d WHERE d.dbtreeLevel3PK.treeLevel1Id = :treeLevel1Id")
    , @NamedQuery(name = "DbtreeLevel3.findByTreeLevel3Index", query = "SELECT d FROM DbtreeLevel3 d WHERE d.treeLevel3Index = :treeLevel3Index")
    , @NamedQuery(name = "DbtreeLevel3.findByTreeLevel3Name", query = "SELECT d FROM DbtreeLevel3 d WHERE d.treeLevel3Name = :treeLevel3Name")})
public class DbtreeLevel3 implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DbtreeLevel3PK dbtreeLevel3PK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "treeLevel3Index")
    private int treeLevel3Index;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "treeLevel3Name")
    private String treeLevel3Name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dbtreeLevel3")
    private List<DbtreeLevel4> dbtreeLevel4List;
    @JoinColumns({
        @JoinColumn(name = "treeLevel2Id", referencedColumnName = "treeLevel2Id", insertable = false, updatable = false)
        , @JoinColumn(name = "treeLevel1Id", referencedColumnName = "treeLevel1Id", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private DbtreeLevel2 dbtreeLevel2;

    public DbtreeLevel3() {
    }

    public DbtreeLevel3(DbtreeLevel3PK dbtreeLevel3PK) {
        this.dbtreeLevel3PK = dbtreeLevel3PK;
    }

    public DbtreeLevel3(DbtreeLevel3PK dbtreeLevel3PK, int treeLevel3Index, String treeLevel3Name) {
        this.dbtreeLevel3PK = dbtreeLevel3PK;
        this.treeLevel3Index = treeLevel3Index;
        this.treeLevel3Name = treeLevel3Name;
    }

    public DbtreeLevel3(int treeLevel3Id, int treeLevel2Id, int treeLevel1Id) {
        this.dbtreeLevel3PK = new DbtreeLevel3PK(treeLevel3Id, treeLevel2Id, treeLevel1Id);
    }

    public DbtreeLevel3PK getDbtreeLevel3PK() {
        return dbtreeLevel3PK;
    }

    public void setDbtreeLevel3PK(DbtreeLevel3PK dbtreeLevel3PK) {
        this.dbtreeLevel3PK = dbtreeLevel3PK;
    }

    public int getTreeLevel3Index() {
        return treeLevel3Index;
    }

    public void setTreeLevel3Index(int treeLevel3Index) {
        this.treeLevel3Index = treeLevel3Index;
    }

    public String getTreeLevel3Name() {
        return treeLevel3Name;
    }

    public void setTreeLevel3Name(String treeLevel3Name) {
        this.treeLevel3Name = treeLevel3Name;
    }

    @XmlTransient
    public List<DbtreeLevel4> getDbtreeLevel4List() {
        return dbtreeLevel4List;
    }

    public void setDbtreeLevel4List(List<DbtreeLevel4> dbtreeLevel4List) {
        this.dbtreeLevel4List = dbtreeLevel4List;
    }

    public DbtreeLevel2 getDbtreeLevel2() {
        return dbtreeLevel2;
    }

    public void setDbtreeLevel2(DbtreeLevel2 dbtreeLevel2) {
        this.dbtreeLevel2 = dbtreeLevel2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dbtreeLevel3PK != null ? dbtreeLevel3PK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbtreeLevel3)) {
            return false;
        }
        DbtreeLevel3 other = (DbtreeLevel3) object;
        if ((this.dbtreeLevel3PK == null && other.dbtreeLevel3PK != null) || (this.dbtreeLevel3PK != null && !this.dbtreeLevel3PK.equals(other.dbtreeLevel3PK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbtreeLevel3[ dbtreeLevel3PK=" + dbtreeLevel3PK + " ]";
    }
    
}
