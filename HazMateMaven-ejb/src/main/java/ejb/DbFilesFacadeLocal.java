/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import customObjects.fileHeaderObject;
import entities.DbFiles;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author lxra
 */
@Local
public interface DbFilesFacadeLocal {

    void create(DbFiles dbFiles);

    void edit(DbFiles dbFiles);

    void remove(DbFiles dbFiles);

    DbFiles find(Object id);

    List<DbFiles> findAll();

    List<DbFiles> findRange(int[] range);

    int count();
    
    List<DbFiles> findFileFromId(int fileId);
    
    List<fileHeaderObject> findHeadersForDuplicate(String fileName, String fileExtension);
    
    List<fileHeaderObject> listAllHeaders();
    
}
