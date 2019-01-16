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
import javax.validation.constraints.Size;

/**
 *
 * @author lxra
 */
@Embeddable
public class DbimportLinePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "processId")
    private String processId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "processIdLine")
    private int processIdLine;

    public DbimportLinePK() {
    }

    public DbimportLinePK(String processId, int processIdLine) {
        this.processId = processId;
        this.processIdLine = processIdLine;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public int getProcessIdLine() {
        return processIdLine;
    }

    public void setProcessIdLine(int processIdLine) {
        this.processIdLine = processIdLine;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (processId != null ? processId.hashCode() : 0);
        hash += (int) processIdLine;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbimportLinePK)) {
            return false;
        }
        DbimportLinePK other = (DbimportLinePK) object;
        if ((this.processId == null && other.processId != null) || (this.processId != null && !this.processId.equals(other.processId))) {
            return false;
        }
        if (this.processIdLine != other.processIdLine) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbimportLinePK[ processId=" + processId + ", processIdLine=" + processIdLine + " ]";
    }
    
}
