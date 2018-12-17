/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbcommonWord;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author David Ortega <david.ortega@levelcrossings.vic.gov.au>
 */
@Local
public interface DbcommonWordFacadeLocal {

    void create(DbcommonWord dbcommonWord);

    void edit(DbcommonWord dbcommonWord);

    void remove(DbcommonWord dbcommonWord);

    DbcommonWord find(Object id);

    List<DbcommonWord> findAll();

    List<DbcommonWord> findRange(int[] range);

    int count();
    
}
