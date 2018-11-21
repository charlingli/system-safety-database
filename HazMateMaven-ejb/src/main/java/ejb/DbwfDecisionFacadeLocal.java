/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.DbwfDecision;
import entities.DbwfLine;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author lxra
 */
@Local
public interface DbwfDecisionFacadeLocal {

    void create(DbwfDecision dbwfDecision);

    void edit(DbwfDecision dbwfDecision);

    void remove(DbwfDecision dbwfDecision);

    DbwfDecision find(Object id);

    List<DbwfDecision> findAll();

    List<DbwfDecision> findRange(int[] range);

    int count();
    
    List<DbwfDecision> findByName(String fieldName, String fieldValue);
    
    List<DbwfLine> checkWfDecision(String riskClassId);
}
