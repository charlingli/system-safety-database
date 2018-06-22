/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbtreeLevel3;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbtreeLevel3FacadeLocal {

    void create(DbtreeLevel3 dbtreeLevel3);

    void edit(DbtreeLevel3 dbtreeLevel3);

    void remove(DbtreeLevel3 dbtreeLevel3);

    DbtreeLevel3 find(Object id);

    List<DbtreeLevel3> findAll();

    List<DbtreeLevel3> findRange(int[] range);

    int count();
    
    List<DbtreeLevel3> findByName(String fieldName, String fieldValue);
    
    List<DbtreeLevel3> findByLevel2Id(int level2Id);
    
}
