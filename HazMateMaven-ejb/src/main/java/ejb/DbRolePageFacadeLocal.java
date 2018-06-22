/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbRolePage;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbRolePageFacadeLocal {

    void create(DbRolePage dbRolePage);

    void edit(DbRolePage dbRolePage);

    void remove(DbRolePage dbRolePage);

    DbRolePage find(Object id);

    List<DbRolePage> findAll();

    List<DbRolePage> findRange(int[] range);
    
    List<DbRolePage> checkRolePage(Integer roleId, Integer pageId);

    int count();
    
    List<DbRolePage> findByName(String fieldName, String fieldValue);
    
}
