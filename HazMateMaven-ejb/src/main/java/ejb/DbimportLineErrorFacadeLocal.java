/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbimportLineError;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author lxra
 */
@Local
public interface DbimportLineErrorFacadeLocal {

    void create(DbimportLineError dbimportLineError);

    void edit(DbimportLineError dbimportLineError);

    void remove(DbimportLineError dbimportLineError);

    DbimportLineError find(Object id);

    List<DbimportLineError> findAll();

    List<DbimportLineError> findRange(int[] range);

    int count();
    
    List<DbimportLineError> listErrorsByLine(String processId, int processIdLine);
}
