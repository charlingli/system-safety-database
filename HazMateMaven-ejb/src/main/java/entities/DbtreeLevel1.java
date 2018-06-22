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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "db_treeLevel1")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbtreeLevel1.findAll", query = "SELECT d FROM DbtreeLevel1 d")
    , @NamedQuery(name = "DbtreeLevel1.findByTreeLevel1Id", query = "SELECT d FROM DbtreeLevel1 d WHERE d.treeLevel1Id = :treeLevel1Id")
    , @NamedQuery(name = "DbtreeLevel1.findByTreeLevel1Index", query = "SELECT d FROM DbtreeLevel1 d WHERE d.treeLevel1Index = :treeLevel1Index")
    , @NamedQuery(name = "DbtreeLevel1.findByTreeLevel1Name", query = "SELECT d FROM DbtreeLevel1 d WHERE d.treeLevel1Name = :treeLevel1Name")})
public class DbtreeLevel1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "treeLevel1Id")
    private Integer treeLevel1Id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "treeLevel1Index")
    private int treeLevel1Index;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "treeLevel1Name")
    private String treeLevel1Name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dbtreeLevel1")
    private List<DbtreeLevel2> dbtreeLevel2List;

    public DbtreeLevel1() {
    }

    public DbtreeLevel1(Integer treeLevel1Id) {
        this.treeLevel1Id = treeLevel1Id;
    }

    public DbtreeLevel1(Integer treeLevel1Id, int treeLevel1Index, String treeLevel1Name) {
        this.treeLevel1Id = treeLevel1Id;
        this.treeLevel1Index = treeLevel1Index;
        this.treeLevel1Name = treeLevel1Name;
    }

    public Integer getTreeLevel1Id() {
        return treeLevel1Id;
    }

    public void setTreeLevel1Id(Integer treeLevel1Id) {
        this.treeLevel1Id = treeLevel1Id;
    }

    public int getTreeLevel1Index() {
        return treeLevel1Index;
    }

    public void setTreeLevel1Index(int treeLevel1Index) {
        this.treeLevel1Index = treeLevel1Index;
    }

    public String getTreeLevel1Name() {
        return treeLevel1Name;
    }

    public void setTreeLevel1Name(String treeLevel1Name) {
        this.treeLevel1Name = treeLevel1Name;
    }

    @XmlTransient
    public List<DbtreeLevel2> getDbtreeLevel2List() {
        return dbtreeLevel2List;
    }

    public void setDbtreeLevel2List(List<DbtreeLevel2> dbtreeLevel2List) {
        this.dbtreeLevel2List = dbtreeLevel2List;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (treeLevel1Id != null ? treeLevel1Id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbtreeLevel1)) {
            return false;
        }
        DbtreeLevel1 other = (DbtreeLevel1) object;
        if ((this.treeLevel1Id == null && other.treeLevel1Id != null) || (this.treeLevel1Id != null && !this.treeLevel1Id.equals(other.treeLevel1Id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbtreeLevel1[ treeLevel1Id=" + treeLevel1Id + " ]";
    }
    
}
