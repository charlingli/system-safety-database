/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbHazard;
import entities.DbhazardActivity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author alan8
 */
@Local
public interface DbhazardActivityFacadeLocal {

    void create(DbhazardActivity dbhazardActivity);

    void edit(DbhazardActivity dbhazardActivity);

    void remove(DbhazardActivity dbhazardActivity);

    DbhazardActivity find(Object id);

    List<DbhazardActivity> findAll();

    List<DbhazardActivity> findRange(int[] range);

    int count();
    
    List<DbhazardActivity> findByName(String fieldName, String fieldValue);
    
    List<DbHazard> checkHazardActivity(int hazardActivityId);
    
}
