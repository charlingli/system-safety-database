/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbindexedWord;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author David Ortega <david.ortega@levelcrossings.vic.gov.au>
 */
@Local
public interface DbindexedWordFacadeLocal {

    void create(DbindexedWord dbindexedWord);

    void edit(DbindexedWord dbindexedWord);

    void remove(DbindexedWord dbindexedWord);

    DbindexedWord find(Object id);

    List<DbindexedWord> findAll();

    List<DbindexedWord> findRange(int[] range);

    int count();
    
    List<Object[]> findSimilarities(String objectType, List<String> listOfValues);
    
}
