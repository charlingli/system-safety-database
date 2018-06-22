/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbsystemParameters;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbsystemParametersFacadeLocal {

    void create(DbsystemParameters dbsystemParameters);

    void edit(DbsystemParameters dbsystemParameters);

    void remove(DbsystemParameters dbsystemParameters);

    DbsystemParameters find(Object id);

    List<DbsystemParameters> findAll();

    List<DbsystemParameters> findRange(int[] range);

    int count();
    
    List<DbsystemParameters> findByName(String fieldName, String fieldValue);
    
}
