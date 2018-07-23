/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbPage;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbPageFacadeLocal {

    void create(DbPage dbPage);

    void edit(DbPage dbPage);

    void remove(DbPage dbPage);

    DbPage find(Object id);

    List<DbPage> findAll();

    List<DbPage> findRange(int[] range);

    int count();
    
    List<DbPage> findAllSortedByInx();
    
    List<DbPage> findByName(String fieldName, String fieldValue);
    
    DbPage retrievePageMatch(int pageIndex);
    
    public DbPage retrievePageName(String viewTitle);
    
}
