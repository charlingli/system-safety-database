/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbHazard;
import entities.DbhazardStatus;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author alan8
 */
@Local
public interface DbhazardStatusFacadeLocal {

    void create(DbhazardStatus dbhazardStatus);

    void edit(DbhazardStatus dbhazardStatus);

    void remove(DbhazardStatus dbhazardStatus);

    DbhazardStatus find(Object id);

    List<DbhazardStatus> findAll();

    List<DbhazardStatus> findRange(int[] range);

    int count();
    
    List<DbhazardStatus> findByName(String fieldName, String fieldValue);
    
    List<DbHazard> checkHazardStatus(int hazardStatusId);
    
}
