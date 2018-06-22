/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbLocation;
import entities.DbchangeType;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbchangeTypeFacadeLocal {

    void create(DbchangeType dbchangeType);

    void edit(DbchangeType dbchangeType);

    void remove(DbchangeType dbchangeType);

    DbchangeType find(Object id);

    List<DbchangeType> findAll();

    List<DbchangeType> findRange(int[] range);

    int count();
    
    List<DbchangeType> findByName(String fieldName, String fieldValue);
    
    List<DbLocation> checkChangeType(int changeTypeId);
    
}
