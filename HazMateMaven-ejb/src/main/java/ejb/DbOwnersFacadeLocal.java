/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbControl;
import entities.DbHazard;
import entities.DbOwners;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author alan8
 */
@Local
public interface DbOwnersFacadeLocal {

    void create(DbOwners dbOwners);

    void edit(DbOwners dbOwners);

    void remove(DbOwners dbOwners);

    DbOwners find(Object id);

    List<DbOwners> findAll();

    List<DbOwners> findRange(int[] range);

    int count();
    
    List<DbOwners> findByName(String fieldName, String fieldValue);
    
    List<DbHazard> checkHazardOwners(int ownerId);
    
    List<DbControl> checkControlOwners(int ownerId);
}
