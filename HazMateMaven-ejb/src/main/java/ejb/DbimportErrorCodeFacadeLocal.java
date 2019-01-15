/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbimportErrorCode;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author lxra
 */
@Local
public interface DbimportErrorCodeFacadeLocal {

    void create(DbimportErrorCode dbimportErrorCode);

    void edit(DbimportErrorCode dbimportErrorCode);

    void remove(DbimportErrorCode dbimportErrorCode);

    DbimportErrorCode find(Object id);

    List<DbimportErrorCode> findAll();

    List<DbimportErrorCode> findRange(int[] range);

    int count();
    
}
