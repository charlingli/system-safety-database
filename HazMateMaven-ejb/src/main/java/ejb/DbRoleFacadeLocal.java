/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbRole;
import entities.DbUser;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbRoleFacadeLocal {

    void create(DbRole dbRole);

    void edit(DbRole dbRole);

    void remove(DbRole dbRole);

    DbRole find(Object id);

    List<DbRole> findAll();

    List<DbRole> findRange(int[] range);

    int count();
    
    List<DbRole> findByName(String fieldName, String fieldValue);
    
    List<DbRole> listActiveRoles();
    
    List<DbUser> checkUser(Integer roleId);
}
