/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbwfType;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author lxra
 */
@Local
public interface DbwfTypeFacadeLocal {

    void create(DbwfType dbwfType);

    void edit(DbwfType dbwfType);

    void remove(DbwfType dbwfType);

    DbwfType find(Object id);

    List<DbwfType> findAll();

    List<DbwfType> findRange(int[] range);

    int count();
    
}
