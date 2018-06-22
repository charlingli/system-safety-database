/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbLocation;
import entities.DbgradeSeparation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author alan8
 */
@Local
public interface DbgradeSeparationFacadeLocal {

    void create(DbgradeSeparation dbgradeSeparation);

    void edit(DbgradeSeparation dbgradeSeparation);

    void remove(DbgradeSeparation dbgradeSeparation);

    DbgradeSeparation find(Object id);

    List<DbgradeSeparation> findAll();

    List<DbgradeSeparation> findRange(int[] range);

    int count();
    
    List<DbgradeSeparation> findByName(String fieldName, String fieldValue);
    
    List<DbLocation> checkGradeSeparation(int gradeSeparationId);
}
