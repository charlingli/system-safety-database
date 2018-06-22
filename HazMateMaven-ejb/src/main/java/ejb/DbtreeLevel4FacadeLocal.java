/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbtreeLevel4;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbtreeLevel4FacadeLocal {

    void create(DbtreeLevel4 dbtreeLevel4);

    void edit(DbtreeLevel4 dbtreeLevel4);

    void remove(DbtreeLevel4 dbtreeLevel4);

    DbtreeLevel4 find(Object id);

    List<DbtreeLevel4> findAll();

    List<DbtreeLevel4> findRange(int[] range);

    int count();
    
    List<DbtreeLevel4> findByName(String fieldName, String fieldValue);
    
    List<DbtreeLevel4> findByLevel3Id(int level3Id);
    
}
