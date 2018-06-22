/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbCause;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbCauseFacadeLocal {

    void create(DbCause dbCause);

    void edit(DbCause dbCause);

    void remove(DbCause dbCause);

    DbCause find(Object id);

    List<DbCause> findAll();

    List<DbCause> findRange(int[] range);

    int count();
    
    List<DbCause> findByName(String fieldName, String fieldValue);
    
    List<DbCause> findUnrelatedByHazardId(String hazardId);
    
}
