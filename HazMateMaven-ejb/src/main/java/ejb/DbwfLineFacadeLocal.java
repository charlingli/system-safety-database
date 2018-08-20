/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbwfLine;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author lxra
 */
@Local
public interface DbwfLineFacadeLocal {

    void create(DbwfLine dbwfLine);

    void edit(DbwfLine dbwfLine);

    void remove(DbwfLine dbwfLine);

    DbwfLine find(Object id);

    List<DbwfLine> findAll();

    List<DbwfLine> findRange(int[] range);

    int count();
    
}
