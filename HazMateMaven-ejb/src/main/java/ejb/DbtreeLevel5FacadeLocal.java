/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbtreeLevel5;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbtreeLevel5FacadeLocal {

    void create(DbtreeLevel5 dbtreeLevel5);

    void edit(DbtreeLevel5 dbtreeLevel5);

    void remove(DbtreeLevel5 dbtreeLevel5);

    DbtreeLevel5 find(Object id);

    List<DbtreeLevel5> findAll();

    List<DbtreeLevel5> findRange(int[] range);

    int count();
    
    List<DbtreeLevel5> findByName(String fieldName, String fieldValue);
    
    List<DbtreeLevel5> findByLevel4Id(int level4Id);
    
}
