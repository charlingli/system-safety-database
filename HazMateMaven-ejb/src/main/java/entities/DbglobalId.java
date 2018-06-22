/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "db_globalId")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DbglobalId.findAll", query = "SELECT d FROM DbglobalId d")
    , @NamedQuery(name = "DbglobalId.findByGlobalIdConsecutive", query = "SELECT d FROM DbglobalId d WHERE d.globalIdConsecutive = :globalIdConsecutive")
    , @NamedQuery(name = "DbglobalId.findByKey1", query = "SELECT d FROM DbglobalId d WHERE d.key1 = :key1")
    , @NamedQuery(name = "DbglobalId.findByKey2", query = "SELECT d FROM DbglobalId d WHERE d.key2 = :key2")
    , @NamedQuery(name = "DbglobalId.findByKey3", query = "SELECT d FROM DbglobalId d WHERE d.key3 = :key3")
    , @NamedQuery(name = "DbglobalId.findByCurrentNumber", query = "SELECT d FROM DbglobalId d WHERE d.currentNumber = :currentNumber")})
public class DbglobalId implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "globalIdConsecutive")
    private Integer globalIdConsecutive;
    @Size(max = 10)
    @Column(name = "key1")
    private String key1;
    @Size(max = 10)
    @Column(name = "key2")
    private String key2;
    @Size(max = 10)
    @Column(name = "key3")
    private String key3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "currentNumber")
    private int currentNumber;

    public DbglobalId() {
    }

    public DbglobalId(String key1, int currentNumber) {
        this.key1 = key1;
        this.currentNumber = currentNumber;
    }

    public DbglobalId(String key1, String key2, int currentNumber) {
        this.key1 = key1;
        this.key2 = key2;
        this.currentNumber = currentNumber;
    }

    public DbglobalId(String key1, String key2, String key3, int currentNumber) {
        this.key1 = key1;
        this.key2 = key2;
        this.key3 = key3;
        this.currentNumber = currentNumber;
    }

    public DbglobalId(Integer globalIdConsecutive) {
        this.globalIdConsecutive = globalIdConsecutive;
    }

    public DbglobalId(Integer globalIdConsecutive, int currentNumber) {
        this.globalIdConsecutive = globalIdConsecutive;
        this.currentNumber = currentNumber;
    }

    public Integer getGlobalIdConsecutive() {
        return globalIdConsecutive;
    }

    public void setGlobalIdConsecutive(Integer globalIdConsecutive) {
        this.globalIdConsecutive = globalIdConsecutive;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public String getKey3() {
        return key3;
    }

    public void setKey3(String key3) {
        this.key3 = key3;
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (globalIdConsecutive != null ? globalIdConsecutive.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DbglobalId)) {
            return false;
        }
        DbglobalId other = (DbglobalId) object;
        if ((this.globalIdConsecutive == null && other.globalIdConsecutive != null) || (this.globalIdConsecutive != null && !this.globalIdConsecutive.equals(other.globalIdConsecutive))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DbglobalId[ globalIdConsecutive=" + globalIdConsecutive + " ]";
    }
    
}
