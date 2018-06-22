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
public class DbtreeLevel2PK implements Serializable {

    @Basic(optional = false)
    @Column(name = "treeLevel2Id")
    private int treeLevel2Id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "treeLevel1Id")
    private int treeLevel1Id;

    public DbtreeLevel2PK() {
    }

    public DbtreeLevel2PK(int treeLevel2Id, int treeLevel1Id) {
        this.treeLevel2Id = treeLevel2Id;
        this.treeLevel1Id = treeLevel1Id;
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
        hash += (int) treeLevel2Id;
        hash += (int) treeLevel1Id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbtreeLevel2PK)) {
            return false;
        }
        DbtreeLevel2PK other = (DbtreeLevel2PK) object;
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
        return "entities.DbtreeLevel2PK[ treeLevel2Id=" + treeLevel2Id + ", treeLevel1Id=" + treeLevel1Id + " ]";
    }
    
}
