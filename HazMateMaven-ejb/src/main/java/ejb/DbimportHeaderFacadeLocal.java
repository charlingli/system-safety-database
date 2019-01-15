/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbimportHeader;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author lxra
 */
@Local
public interface DbimportHeaderFacadeLocal {

    void create(DbimportHeader dbimportHeader);

    void edit(DbimportHeader dbimportHeader);

    void remove(DbimportHeader dbimportHeader);

    DbimportHeader find(Object id);

    List<DbimportHeader> findAll();

    List<DbimportHeader> findRange(int[] range);

    int count();
    
}
