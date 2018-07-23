/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customObjects;

import java.util.Objects;

/**
 *
 * @author Juan David
 */
public class treeNodeObject {

    private String nodeId;
    private String nodeName;
    private String rowKey;

    public treeNodeObject() {
    }

    public treeNodeObject(String nodeId, String nodeName) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
    }

    public treeNodeObject(String nodeId, String nodeName, String rowKey) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
        this.rowKey = rowKey;
    }
    
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final treeNodeObject other = (treeNodeObject) obj;
        if (!Objects.equals(this.nodeId, other.nodeId)) {
            return false;
        }
        if (!Objects.equals(this.nodeName, other.nodeName)) {
            return false;
        }
        if (!Objects.equals(this.rowKey, other.rowKey)) {
            return false;
        }
        return true;
    }
    

    
    

}
