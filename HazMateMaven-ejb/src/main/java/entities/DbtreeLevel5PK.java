/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Juan David
 */
@Embeddable
public class DbtreeLevel5PK implements Serializable {

    @Basic(optional = false)
    @Column(name = "treeLevel5Id")
    private int treeLevel5Id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "treeLevel4Id")
    private int treeLevel4Id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "treeLevel3Id")
    private int treeLevel3Id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "treeLevel2Id")
    private int treeLevel2Id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "treeLevel1Id")
    private int treeLevel1Id;

    public DbtreeLevel5PK() {
    }

    public DbtreeLevel5PK(int treeLevel5Id, int treeLevel4Id, int treeLevel3Id, int treeLevel2Id, int treeLevel1Id) {
        this.treeLevel5Id = treeLevel5Id;
        this.treeLevel4Id = treeLevel4Id;
        this.treeLevel3Id = treeLevel3Id;
        this.treeLevel2Id = treeLevel2Id;
        this.treeLevel1Id = treeLevel1Id;
    }

    public int getTreeLevel5Id() {
        return treeLevel5Id;
    }

    public void setTreeLevel5Id(int treeLevel5Id) {
        this.treeLevel5Id = treeLevel5Id;
    }

    public int getTreeLevel4Id() {
        return treeLevel4Id;
    }

    public void setTreeLevel4Id(int treeLevel4Id) {
        this.treeLevel4Id = treeLevel4Id;
    }

    public int getTreeLevel3Id() {
        return treeLevel3Id;
    }

    public void setTreeLevel3Id(int treeLevel3Id) {
        this.treeLevel3Id = treeLevel3Id;
    }

    public int getTreeLevel2Id() {
        return treeLevel2Id;
    }

    public void setTreeLevel2Id(int treeLevel2Id) {
        this.treeLevel2Id = treeLevel2Id;
    }

    public int getTreeLevel1Id() {
        return treeLevel1Id;
    }

    public void setTreeLevel1Id(int treeLevel1Id) {
        this.treeLevel1Id = treeLevel1Id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) treeLevel5Id;
        hash += (int) treeLevel4Id;
        hash += (int) treeLevel3Id;
        hash += (int) treeLevel2Id;
        hash += (int) treeLevel1Id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbtreeLevel5PK)) {
            return false;
        }
        DbtreeLevel5PK other = (DbtreeLevel5PK) object;
        if (this.treeLevel5Id != other.treeLevel5Id) {
            return false;
        }
        if (this.treeLevel4Id != other.treeLevel4Id) {
            return false;
        }
        if (this.treeLevel3Id != other.treeLevel3Id) {
            return false;
        }
        if (this.treeLevel2Id != other.treeLevel2Id) {
            return false;
        }
        if (this.treeLevel1Id != other.treeLevel1Id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbtreeLevel5PK[ treeLevel5Id=" + treeLevel5Id + ", treeLevel4Id=" + treeLevel4Id + ", treeLevel3Id=" + treeLevel3Id + ", treeLevel2Id=" + treeLevel2Id + ", treeLevel1Id=" + treeLevel1Id + " ]";
    }
    
}
