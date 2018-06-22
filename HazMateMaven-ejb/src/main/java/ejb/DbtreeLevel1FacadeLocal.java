/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbtreeLevel1;
import entities.DbtreeLevel2;
import entities.DbtreeLevel3;
import entities.DbtreeLevel4;
import entities.DbtreeLevel5;
import entities.DbtreeLevel6;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbtreeLevel1FacadeLocal {

    void create(DbtreeLevel1 dbtreeLevel1);

    void edit(DbtreeLevel1 dbtreeLevel1);

    void remove(DbtreeLevel1 dbtreeLevel1);

    DbtreeLevel1 find(Object id);

    List<DbtreeLevel1> findAll();

    List<DbtreeLevel1> findRange(int[] range);

    int count();
    
    List<DbtreeLevel1> findByName(String fieldName, String fieldValue);
    
    DbtreeLevel1 findByName(String nodeName);
   
    DbtreeLevel1 findByIndex(int indexLv1);
    
    DbtreeLevel2 findByIndex(int indexLv1, int indexLv2);
    
    DbtreeLevel3 findByIndex(int indexLv1, int indexLv2, int indexLv3);
    
    DbtreeLevel4 findByIndex(int indexLv1, int indexLv2, int indexLv3, int indexLv4);
    
    DbtreeLevel5 findByIndex(int indexLv1, int indexLv2, int indexLv3, int indexLv4, int indexLv5);
    
    DbtreeLevel6 findByIndex(int indexLv1, int indexLv2, int indexLv3, int indexLv4, int indexLv5, int indexLv6);
}
