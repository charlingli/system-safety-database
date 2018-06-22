/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbuserPreferences;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbuserPreferencesFacadeLocal {

    void create(DbuserPreferences dbuserPreferences);

    void edit(DbuserPreferences dbuserPreferences);

    void remove(DbuserPreferences dbuserPreferences);

    DbuserPreferences find(Object id);

    List<DbuserPreferences> findAll();

    List<DbuserPreferences> findRange(int[] range);

    int count();
    
    List<DbuserPreferences> findByName(String fieldName, String fieldValue);

    List<DbuserPreferences> getUserPreferences(int userId);

    List<DbuserPreferences> getSpecificPreference(int userId, String pageName, String tableName);

}
