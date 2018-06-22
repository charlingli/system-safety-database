/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbHazardSbs;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbHazardSbsFacadeLocal {

    void create(DbHazardSbs dbHazardSbs);

    void edit(DbHazardSbs dbHazardSbs);

    void remove(DbHazardSbs dbHazardSbs);

    DbHazardSbs find(Object id);

    List<DbHazardSbs> findAll();

    List<DbHazardSbs> findRange(int[] range);

    int count();
    
    List<DbHazardSbs> findByName(String fieldName, String fieldValue);

    List<DbHazardSbs> findByHazardId(String hazardId);

    void removeHazardSbs(String hazardId);

    List<DbHazardSbs> checkHazardSbs(String hazardId, String sbsId);

}
