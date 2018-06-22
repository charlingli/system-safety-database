/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbHazard;
import entities.DbhazardType;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author alan8
 */
@Local
public interface DbhazardTypeFacadeLocal {

    void create(DbhazardType dbhazardType);

    void edit(DbhazardType dbhazardType);

    void remove(DbhazardType dbhazardType);

    DbhazardType find(Object id);

    List<DbhazardType> findAll();

    List<DbhazardType> findRange(int[] range);

    int count();
    
    List<DbhazardType> findByName(String fieldName, String fieldValue);
    
    List<DbHazard> checkHazardType(int hazardTypeId);
    
}
