/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbControlHazard;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbControlHazardFacadeLocal {

    void create(DbControlHazard dbControlHazard);

    void edit(DbControlHazard dbControlHazard);

    void remove(DbControlHazard dbControlHazard);

    DbControlHazard find(Object id);

    List<DbControlHazard> findAll();

    List<DbControlHazard> findRange(int[] range);

    int count();
    
    List<DbControlHazard> findByName(String fieldName, String fieldValue);
    
    List<DbControlHazard> findByHazardId(String hazardId);
    
    int customRemove(DbControlHazard dbControlHazard);
    
}
