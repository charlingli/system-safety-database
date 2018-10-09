/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbQuality;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author lxra
 */
@Local
public interface DbQualityFacadeLocal {

    void create(DbQuality dbQuality);

    void edit(DbQuality dbQuality);

    void remove(DbQuality dbQuality);

    DbQuality find(Object id);

    List<DbQuality> findAll();

    List<DbQuality> findRange(int[] range);

    int count();
    
    Double getHazardRating(String hazardId);
    
    List<DbQuality> getUserHazardRating(int userId, String hazardId);
    
    Long getNumberOfRatings(String hazardId);
}
