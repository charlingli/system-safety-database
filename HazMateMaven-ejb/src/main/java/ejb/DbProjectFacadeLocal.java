/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbLocation;
import entities.DbProject;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Juan David
 */
@Local
public interface DbProjectFacadeLocal {

    void create(DbProject dbProject);

    void edit(DbProject dbProject);

    void remove(DbProject dbProject);

    DbProject find(Object id);

    List<DbProject> findAll();

    List<DbProject> findRange(int[] range);

    int count();
    
    List<DbProject> findByName(String fieldName, String fieldValue);

    List<DbLocation> checkProject(int projectId);

    List<DbProject> checkProjectAbbrev(String projectAbbrev);

}
