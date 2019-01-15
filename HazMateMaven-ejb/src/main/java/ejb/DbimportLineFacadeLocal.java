/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbimportLine;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author lxra
 */
@Local
public interface DbimportLineFacadeLocal {

    void create(DbimportLine dbimportLine);

    void edit(DbimportLine dbimportLine);

    void remove(DbimportLine dbimportLine);

    DbimportLine find(Object id);

    List<DbimportLine> findAll();

    List<DbimportLine> findRange(int[] range);

    int count();
    
    List<DbimportLine> findLineById(String processId, int processIdLine);
    
    List<DbimportLine> findNextLinesByUser(int userId);
    
}
