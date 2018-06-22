/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbtreeLevel2;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbtreeLevel2FacadeLocal {

    void create(DbtreeLevel2 dbtreeLevel2);

    void edit(DbtreeLevel2 dbtreeLevel2);

    void remove(DbtreeLevel2 dbtreeLevel2);

    DbtreeLevel2 find(Object id);

    List<DbtreeLevel2> findAll();

    List<DbtreeLevel2> findRange(int[] range);

    int count();
    
    List<DbtreeLevel2> findByName(String fieldName, String fieldValue);
    
    List<DbtreeLevel2> findByLevel1Id(int leve1Id);
    
}
