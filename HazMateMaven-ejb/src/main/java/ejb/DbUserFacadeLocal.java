/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbUser;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbUserFacadeLocal {

    void create(DbUser dbUser);

    void edit(DbUser dbUser);

    void remove(DbUser dbUser);

    DbUser find(Object id);

    List<DbUser> findAll();

    List<DbUser> findRange(int[] range);

    int count();
    
    List<DbUser> findByName(String fieldName, String fieldValue);
    
    DbUser initSession(String userEmail, String password);
    
    List<DbUser> getUsersByRole(String roleName);
    
    boolean getPageAccessForUser(int userId, String pageName);
    
    List<DbUser> getWfApproverUsers();

}
