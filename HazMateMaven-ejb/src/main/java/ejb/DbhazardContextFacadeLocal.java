/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbHazard;
import entities.DbhazardContext;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbhazardContextFacadeLocal {

    void create(DbhazardContext dbhazardContext);

    void edit(DbhazardContext dbhazardContext);

    void remove(DbhazardContext dbhazardContext);

    DbhazardContext find(Object id);

    List<DbhazardContext> findAll();

    List<DbhazardContext> findRange(int[] range);

    int count();
    
    List<DbhazardContext> findByName(String fieldName, String fieldValue);
    
    List<DbHazard> checkHazardContext(int hazardContextId);
    
}
