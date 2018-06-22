/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbtreeLevel6;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbtreeLevel6FacadeLocal {

    void create(DbtreeLevel6 dbtreeLevel6);

    void edit(DbtreeLevel6 dbtreeLevel6);

    void remove(DbtreeLevel6 dbtreeLevel6);

    DbtreeLevel6 find(Object id);

    List<DbtreeLevel6> findAll();

    List<DbtreeLevel6> findRange(int[] range);

    int count();
    
    List<DbtreeLevel6> findByName(String fieldName, String fieldValue);
    
    List<DbtreeLevel6> findByLevel5Id(int level5Id);
    
}
