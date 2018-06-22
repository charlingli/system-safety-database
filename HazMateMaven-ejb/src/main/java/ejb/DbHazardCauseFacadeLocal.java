/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbHazardCause;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbHazardCauseFacadeLocal {

    void create(DbHazardCause dbHazardCause);

    void edit(DbHazardCause dbHazardCause);

    void remove(DbHazardCause dbHazardCause);

    DbHazardCause find(Object id);

    List<DbHazardCause> findAll();

    List<DbHazardCause> findRange(int[] range);

    int count();
    
    List<DbHazardCause> findByName(String fieldName, String fieldValue);
    
    List<DbHazardCause> findByHazardId(String hazardId);
    
    int customRemove(DbHazardCause dbHazardCause);
    
}
