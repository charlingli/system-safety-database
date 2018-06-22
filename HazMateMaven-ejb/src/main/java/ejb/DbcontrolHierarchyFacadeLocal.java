/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbControl;
import entities.DbcontrolHierarchy;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Charling
 */
@Local
public interface DbcontrolHierarchyFacadeLocal {

    void create(DbcontrolHierarchy dbcontrolHierarchy);

    void edit(DbcontrolHierarchy dbcontrolHierarchy);

    void remove(DbcontrolHierarchy dbcontrolHierarchy);

    DbcontrolHierarchy find(Object id);

    List<DbcontrolHierarchy> findAll();

    List<DbcontrolHierarchy> findRange(int[] range);

    int count();
    
    List<DbcontrolHierarchy> findByName(String fieldName, String fieldValue);
    
    List<DbControl> checkControlHierarchy(int controlHierarchyId);
    
}
