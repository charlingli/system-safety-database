/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbMenu;
import entities.DbPage;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbMenuFacadeLocal {

    void create(DbMenu dbMenu);

    void edit(DbMenu dbMenu);

    void remove(DbMenu dbMenu);

    DbMenu find(Object id);

    List<DbMenu> findAll();

    List<DbMenu> findRange(int[] range);

    int count();
    
    List<DbMenu> findByName(String fieldName, String fieldValue);

    List<DbPage> customUserMenu (int userId);
    
    DbMenu findOneMenu (int menuId);
    
    List<DbMenu> findAllMainMenus ();
    
    DbMenu findOneMenuByIndex (int indexMenu);
    
    List<DbMenu> findMenusByParent (int menuParent);
    
    List<DbMenu> findPagesByParent (int pageParent);
}
