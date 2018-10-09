/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbhazardSystemStatus;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author lxra
 */
@Local
public interface DbhazardSystemStatusFacadeLocal {

    void create(DbhazardSystemStatus dbhazardSystemStatus);

    void edit(DbhazardSystemStatus dbhazardSystemStatus);

    void remove(DbhazardSystemStatus dbhazardSystemStatus);

    DbhazardSystemStatus find(Object id);

    List<DbhazardSystemStatus> findAll();

    List<DbhazardSystemStatus> findRange(int[] range);

    int count();
    
}
