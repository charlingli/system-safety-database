/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import customObjects.fileHeaderObject;
import entities.DbHazard;
import entities.DbHazardFiles;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author lxra
 */
@Local
public interface DbHazardFilesFacadeLocal {

    void create(DbHazardFiles dbHazardFiles);

    void edit(DbHazardFiles dbHazardFiles);

    void remove(DbHazardFiles dbHazardFiles);

    DbHazardFiles find(Object id);

    List<DbHazardFiles> findAll();

    List<DbHazardFiles> findRange(int[] range);

    int count();
    
    List<fileHeaderObject> findHeadersForHazard(String hazardId);
    
    int customRemove(String hazardId, int fileId);
    
    boolean hasHazardsLinked(int fileId);
    
    List<DbHazard> findLinkedHazards(int fileId);
}
