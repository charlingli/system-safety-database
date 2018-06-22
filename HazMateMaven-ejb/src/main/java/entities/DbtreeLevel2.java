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
@Table(name = "db_treeLevel2")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbtreeLevel2.findAll", query = "SELECT d FROM DbtreeLevel2 d")
    , @NamedQuery(name = "DbtreeLevel2.findByTreeLevel2Id", query = "SELECT d FROM DbtreeLevel2 d WHERE d.dbtreeLevel2PK.treeLevel2Id = :treeLevel2Id")
    , @NamedQuery(name = "DbtreeLevel2.findByTreeLevel1Id", query = "SELECT d FROM DbtreeLevel2 d WHERE d.dbtreeLevel2PK.treeLevel1Id = :treeLevel1Id")
    , @NamedQuery(name = "DbtreeLevel2.findByTreeLevel2Index", query = "SELECT d FROM DbtreeLevel2 d WHERE d.treeLevel2Index = :treeLevel2Index")
    , @NamedQuery(name = "DbtreeLevel2.findByTreeLevel2Name", query = "SELECT d FROM DbtreeLevel2 d WHERE d.treeLevel2Name = :treeLevel2Name")})
public class DbtreeLevel2 implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DbtreeLevel2PK dbtreeLevel2PK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "treeLevel2Index")
    private int treeLevel2Index;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "treeLevel2Name")
    private String treeLevel2Name;
    @JoinColumn(name = "treeLevel1Id", referencedColumnName = "treeLevel1Id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DbtreeLevel1 dbtreeLevel1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dbtreeLevel2")
    private List<DbtreeLevel3> dbtreeLevel3List;

    public DbtreeLevel2() {
    }

    public DbtreeLevel2(DbtreeLevel2PK dbtreeLevel2PK) {
        this.dbtreeLevel2PK = dbtreeLevel2PK;
    }

    public DbtreeLevel2(DbtreeLevel2PK dbtreeLevel2PK, int treeLevel2Index, String treeLevel2Name) {
        this.dbtreeLevel2PK = dbtreeLevel2PK;
        this.treeLevel2Index = treeLevel2Index;
        this.treeLevel2Name = treeLevel2Name;
    }

    public DbtreeLevel2(int treeLevel2Id, int treeLevel1Id) {
        this.dbtreeLevel2PK = new DbtreeLevel2PK(treeLevel2Id, treeLevel1Id);
    }

    public DbtreeLevel2PK getDbtreeLevel2PK() {
        return dbtreeLevel2PK;
    }

    public void setDbtreeLevel2PK(DbtreeLevel2PK dbtreeLevel2PK) {
        this.dbtreeLevel2PK = dbtreeLevel2PK;
    }

    public int getTreeLevel2Index() {
        return treeLevel2Index;
    }

    public void setTreeLevel2Index(int treeLevel2Index) {
        this.treeLevel2Index = treeLevel2Index;
    }

    public String getTreeLevel2Name() {
        return treeLevel2Name;
    }

    public void setTreeLevel2Name(String treeLevel2Name) {
        this.treeLevel2Name = treeLevel2Name;
    }

    public DbtreeLevel1 getDbtreeLevel1() {
        return dbtreeLevel1;
    }

    public void setDbtreeLevel1(DbtreeLevel1 dbtreeLevel1) {
        this.dbtreeLevel1 = dbtreeLevel1;
    }

    @XmlTransient
    public List<DbtreeLevel3> getDbtreeLevel3List() {
        return dbtreeLevel3List;
    }

    public void setDbtreeLevel3List(List<DbtreeLevel3> dbtreeLevel3List) {
        this.dbtreeLevel3List = dbtreeLevel3List;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dbtreeLevel2PK != null ? dbtreeLevel2PK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbtreeLevel2)) {
            return false;
        }
        DbtreeLevel2 other = (DbtreeLevel2) object;
        if ((this.dbtreeLevel2PK == null && other.dbtreeLevel2PK != null) || (this.dbtreeLevel2PK != null && !this.dbtreeLevel2PK.equals(other.dbtreeLevel2PK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbtreeLevel2[ dbtreeLevel2PK=" + dbtreeLevel2PK + " ]";
    }
    
}
