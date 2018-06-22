/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbConsequence;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbConsequenceFacadeLocal {

    void create(DbConsequence dbConsequence);

    void edit(DbConsequence dbConsequence);

    void remove(DbConsequence dbConsequence);

    DbConsequence find(Object id);

    List<DbConsequence> findAll();

    List<DbConsequence> findRange(int[] range);

    int count();
    
    List<DbConsequence> findByName(String fieldName, String fieldValue);
    
    List<DbConsequence> findUnrelatedByHazardId(String hazardId);
    
}
