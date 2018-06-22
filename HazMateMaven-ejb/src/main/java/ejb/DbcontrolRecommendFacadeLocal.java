/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbControlHazard;
import entities.DbcontrolRecommend;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author alan8
 */
@Local
public interface DbcontrolRecommendFacadeLocal {

    void create(DbcontrolRecommend dbcontrolRecommend);

    void edit(DbcontrolRecommend dbcontrolRecommend);

    void remove(DbcontrolRecommend dbcontrolRecommend);

    DbcontrolRecommend find(Object id);

    List<DbcontrolRecommend> findAll();

    List<DbcontrolRecommend> findRange(int[] range);

    int count();
    
    List<DbcontrolRecommend> findByName(String fieldName, String fieldValue);
    
    List<DbControlHazard> checkControlRecommend(int controlRecommendId);
    
}
