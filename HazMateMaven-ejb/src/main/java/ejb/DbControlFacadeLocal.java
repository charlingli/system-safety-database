/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbControl;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbControlFacadeLocal {

    void create(DbControl dbControl);

    void edit(DbControl dbControl);

    void remove(DbControl dbControl);

    DbControl find(Object id);

    List<DbControl> findAll();

    List<DbControl> findRange(int[] range);

    int count();
    
    List<DbControl> findByName(String fieldName, String fieldValue);
    
    List<DbControl> findUnrelatedByHazardId(String hazardId);
}
