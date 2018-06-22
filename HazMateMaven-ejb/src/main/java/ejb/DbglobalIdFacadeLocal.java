/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import customObjects.validateIdObject;
import entities.DbglobalId;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbglobalIdFacadeLocal {

    void create(DbglobalId dbglobalId);

    void edit(DbglobalId dbglobalId);

    void remove(DbglobalId dbglobalId);

    DbglobalId find(Object id);

    List<DbglobalId> findAll();

    List<DbglobalId> findRange(int[] range);

    int count();
    
    List<DbglobalId> findByName(String fieldName, String fieldValue);
    
    DbglobalId findByKey(String key1);
    
    DbglobalId findByKey(String key1, String key2);
    
    DbglobalId findByKey(String key1, String key2, String key3);
    
    validateIdObject nextConsecutive(String key1, String separator, int zeroPositions);
    
    validateIdObject nextConsecutive(String key1, String key2, String separator, int zeroPositions);
    
    validateIdObject nextConsecutive(String key1, String key2, String key3, String separator, int zeroPositions);
    
}
