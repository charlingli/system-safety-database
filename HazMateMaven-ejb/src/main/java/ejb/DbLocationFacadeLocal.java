/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbHazard;
import entities.DbLocation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author alan8
 */
@Local
public interface DbLocationFacadeLocal {

    void create(DbLocation dbLocation);

    void edit(DbLocation dbLocation);

    void remove(DbLocation dbLocation);

    DbLocation find(Object id);

    List<DbLocation> findAll();

    List<DbLocation> findRange(int[] range);

    int count();
    
    List<DbLocation> findByName(String fieldName, String fieldValue);

    List<DbHazard> checkLocation(int locationId);

    List<DbLocation> getLocationAbbrev(int locationId);

    List<DbLocation> checkLocationAbbrev(String locationAbbrev);

}
