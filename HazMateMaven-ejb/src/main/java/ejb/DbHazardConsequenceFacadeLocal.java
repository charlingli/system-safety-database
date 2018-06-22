/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbHazardConsequence;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbHazardConsequenceFacadeLocal {

    void create(DbHazardConsequence dbHazardConsequence);

    void edit(DbHazardConsequence dbHazardConsequence);

    void remove(DbHazardConsequence dbHazardConsequence);

    DbHazardConsequence find(Object id);

    List<DbHazardConsequence> findAll();

    List<DbHazardConsequence> findRange(int[] range);

    int count();
    
    List<DbHazardConsequence> findByName(String fieldName, String fieldValue);
    
    List<DbHazardConsequence> findByHazardId(String hazardId);
    
    int customRemove(DbHazardConsequence dbHazardConsequence);
    
}
